package cn.springboot.osbulkparts.service.impl;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.utils.CommonMethods;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.basedata.TFileDao;
import cn.springboot.osbulkparts.entity.TFileEntity;
import cn.springboot.osbulkparts.service.TFileService;

@Service
public class TFileServiceImpl implements TFileService
{
	
    @Autowired
    private TFileDao tfileDao;
	
    @Autowired
    private I18nMessageBean messageBean;

	private static String FTP_FILE_PATH = "C:\\ftp";
	
	private static String PRIVICE_FILE ="price_pic";
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> uploadFileToFtp(TFileEntity fileEntity, MultipartFile imgFile, Authentication auth) {
		CommonResultInfo<TFileEntity> result = new CommonResultInfo<TFileEntity>();
		try {
            String nowTime = CommonMethods.getNowTimeForString();
            StringBuffer filePath = new StringBuffer();
            filePath.append(FTP_FILE_PATH);
            filePath.append("\\");
            filePath.append(PRIVICE_FILE);
            fileEntity.setId(CommonSqlUtils.getUUID32());
            uploadFile(imgFile.getBytes(),filePath.toString(),imgFile.getOriginalFilename()+"_"+nowTime);
            fileEntity.setFilePath(filePath.toString());
            fileEntity.setFileName(imgFile.getOriginalFilename()+"_"+nowTime);
            fileEntity.setFileType(1);
            fileEntity.setIsDelete("0");
            //文件信息存储
            tfileDao.insert(fileEntity);
		}catch(Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@Override
	public CommonResultInfo<?> updateMaterialPriceFileStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getFileByID(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

	/*******************Private Methods***********************/
	/**
	 * 保存图片至服务器
	 * @param file
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception { 
		File targetFile = new File(filePath); 
		if(!targetFile.exists()){ 
			targetFile.mkdirs(); 
		} 
		FileOutputStream out = new FileOutputStream(filePath+fileName);
		out.write(file);
		out.flush();
		out.close();
	}
}
