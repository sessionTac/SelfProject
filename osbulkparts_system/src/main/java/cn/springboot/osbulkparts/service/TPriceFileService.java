package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TPriceFileEntity;

public interface TPriceFileService {
	
	CommonResultInfo<?> upInsertPrice(TPriceFileEntity fileEntity,MultipartFile imgFile,Authentication auth);
	
	CommonResultInfo<?> updateMaterialPriceFileStatus();
	
	CommonResultInfo<TPriceFileEntity> getMaterialPriceAndFile(TPriceFileEntity fileEntity,Authentication auth);
	
	Object getFileByID(String fileID);
}
