package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TFileEntity;

public interface TFileService {
	
	CommonResultInfo<?> uploadFileToFtp(TFileEntity fileEntity,MultipartFile imgFile,Authentication auth);
	
	CommonResultInfo<?> updateMaterialPriceFileStatus();
	
	Object getFileByID(String fileID);

}
