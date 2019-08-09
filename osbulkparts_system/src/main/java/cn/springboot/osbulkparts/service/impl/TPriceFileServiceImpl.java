package cn.springboot.osbulkparts.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonMethods;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.basedata.TPriceFileDao;
import cn.springboot.osbulkparts.entity.TPriceFileEntity;
import cn.springboot.osbulkparts.service.TPriceFileService;

@Service
public class TPriceFileServiceImpl implements TPriceFileService
{
	
    @Autowired
    private TPriceFileDao tfileDao;
	
    @Autowired
    private I18nMessageBean messageBean;

	private static String FTP_FILE_PATH = "C:\\ftp";
	
	private static String PRIVICE_FILE ="price_pic";
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> upInsertPrice(TPriceFileEntity fileEntity, MultipartFile imgFile, Authentication auth,Locale locale) {
		CommonResultInfo<TPriceFileEntity> result = new CommonResultInfo<TPriceFileEntity>();
		try {
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
            String nowTime = CommonMethods.getNowTimeForString();
            StringBuffer filePath = new StringBuffer();
            filePath.append(FTP_FILE_PATH);
            filePath.append("\\");
            filePath.append(PRIVICE_FILE);
            filePath.append("\\");
            boolean rnt = uploadFile(imgFile.getBytes(),filePath.toString(),imgFile.getOriginalFilename());
            if(rnt) {
            	List<TPriceFileEntity> priceResult = tfileDao.selectByMaterialCode(fileEntity);
            	if(priceResult.size()==0) {
                	//主键
                    fileEntity.setId(CommonSqlUtils.getUUID32());
//                    fileEntity.setMaterialCode(materialCode);
//                    fileEntity.setSupplierCode(supplierCode);
//                    fileEntity.setPrice(price);
                    //操作者
                    fileEntity.setCreateUser(principal.getUserName());
                    // 文件路径
                    fileEntity.setFilePath(filePath.toString());
                    // 文件名
                    fileEntity.setFileName(imgFile.getOriginalFilename());
                    // 文件类型
                    fileEntity.setFileType(1);
                    fileEntity.setIsDelete(0);
                    fileEntity.setVersion(1);
                    //文件信息存储
                    tfileDao.insert(fileEntity);
            	}else {
                    // 文件路径
                    fileEntity.setFilePath(filePath.toString());
                    // 文件名
                    fileEntity.setFileName(imgFile.getOriginalFilename());
                    // 文件类型
                    fileEntity.setFileType(1);
                    tfileDao.updateByPrimaryKeySelective(fileEntity);
            	}
            	result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            	result.setMessage(messageBean.getMessage("common.file.upload.success"));
            }
            else {
    			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
    			result.setMessage(messageBean.getMessage("common.server.error"));
            }
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
	public Object getFileByID(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TPriceFileEntity> getMaterialPriceAndFile(TPriceFileEntity fileEntity,
																	  Authentication auth, Locale locale) {
		CommonResultInfo<TPriceFileEntity> result = new CommonResultInfo<TPriceFileEntity>();
		try {
			List<TPriceFileEntity> pageInfo = tfileDao.selectByMaterialCode(fileEntity);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResultList(pageInfo);
		} catch (Exception e) {
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
	/*******************Private Methods***********************/
	/**
	 * 保存图片至服务器
	 * @param file
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static boolean uploadFile(byte[] file, String filePath, String fileName) throws IOException { 
		boolean rnt = false;
		File targetFile = new File(filePath); 
		if(!targetFile.exists()){ 
			targetFile.mkdirs(); 
		} 
		FileOutputStream out = new FileOutputStream(filePath+fileName);
		try {
			out.write(file);
			rnt = true;
		}catch(IOException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(out != null) {
				out.flush();
				out.close();
			}
		}
		return rnt;
	}
}
