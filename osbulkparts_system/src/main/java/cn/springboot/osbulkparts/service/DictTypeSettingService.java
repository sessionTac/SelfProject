package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TDictTypeEntity;

public interface DictTypeSettingService {
	
	CommonResultInfo<TDictTypeEntity> getDictTypeList(TDictTypeEntity tdictTypeEntity,int pageNumber,int pageSize);
	
	CommonResultInfo<TDictTypeEntity> getDictTypeInfo(String dictTypeId,String lang);

	CommonResultInfo<TDictTypeEntity> getDictType(TDictTypeEntity tdictTypeEntity);
	
	CommonResultInfo<?> addDictTypeInfo(TDictTypeEntity tdictTypeEntity, Authentication auth);
	
	CommonResultInfo<?> updateDictType(TDictTypeEntity tdictTypeEntity, Authentication auth);
	
	@Transactional
	CommonResultInfo<?> deleteDictType(String dictTypeId, Authentication auth,String lang);
	
	CommonResultInfo<?> checkNameRepeat(TDictTypeEntity tdictTypeEntity, String checkFlag);
	
	CommonResultInfo<?> checkCodeRepeat(TDictTypeEntity tdictTypeEntity, String checkFlag);
	
}
