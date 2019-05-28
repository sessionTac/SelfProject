package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TDictTypeEntity;

public interface DictTypeSettingService {
	
	CommonResultInfo<TDictTypeEntity> getDictTypeList(TDictTypeEntity tdictTypeEntity,int pageNumber,int pageSize);
	
	CommonResultInfo<TDictTypeEntity> getDictTypeInfo(String dictTypeId);
	
	CommonResultInfo<?> addDictTypeInfo(TDictTypeEntity tdictTypeEntity, Authentication auth);
	
	CommonResultInfo<?> updateDictType(TDictTypeEntity tdictTypeEntity, Authentication auth);
	
	CommonResultInfo<?> deleteDictType(TDictTypeEntity tdictTypeEntity, Authentication auth);

}
