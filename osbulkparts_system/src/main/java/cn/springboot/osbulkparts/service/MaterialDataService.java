package cn.springboot.osbulkparts.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

public interface MaterialDataService {

	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews();
	
	CommonResultInfo<?> importExcel(MultipartFile excleFile,HttpServletRequest request,Authentication auth);
	
	CommonResultInfo<MMaterialInfoEntity> selectMaterialInfoList(MMaterialInfoEntity materialInfoEntity, int pageNumber, int pageSize);
	
	CommonResultInfo<MMaterialInfoEntity> selectMaterialInfo(MMaterialInfoEntity materialInfoEntity);
	
	CommonResultInfo<?> insertMaterialInfo(MMaterialInfoEntity materialInfoEntity,Authentication auth);
	
	CommonResultInfo<?> updateMaterialInfo(MMaterialInfoEntity materialInfoEntity,Authentication auth);
	
	CommonResultInfo<?> deleteMaterialInfo(String materialId,Authentication auth);
}
