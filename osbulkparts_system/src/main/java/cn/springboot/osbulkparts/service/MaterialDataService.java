package cn.springboot.osbulkparts.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

public interface MaterialDataService {

	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(String lang, Locale locale);
	
	@Transactional
	CommonResultInfo<?> importExcel(MultipartFile excleFile,HttpServletRequest request,Authentication auth,Locale locale);
	
	CommonResultInfo<MMaterialInfoEntity> selectMaterialInfoList(MMaterialInfoEntity materialInfoEntity, int pageNum,int pageSize,Authentication auth,Locale locale);
	
	CommonResultInfo<MMaterialInfoEntity> selectMaterialInfo(MMaterialInfoEntity materialInfoEntity,Locale locale);
	
	CommonResultInfo<?> insertMaterialInfo(MMaterialInfoEntity materialInfoEntity,Authentication auth,Locale locale);
	
	CommonResultInfo<?> updateMaterialInfo(MMaterialInfoEntity materialInfoEntity,Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteMaterialInfo(String materialId,Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteBatchMaterialInfo(CommonEntity commonEntity,Authentication auth,Locale locale);
	
	CommonResultInfo<?> lockMaterialInfo(CommonEntity commonEntity,Authentication auth,Locale locale);
	
	ResponseEntity<byte[]> downloadExcel(MMaterialInfoEntity materialInfoEntity,Locale locale);
}
