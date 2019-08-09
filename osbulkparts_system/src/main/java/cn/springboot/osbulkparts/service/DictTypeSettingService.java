package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TDictTypeEntity;

import java.util.Locale;

public interface DictTypeSettingService {
	
	CommonResultInfo<TDictTypeEntity> getDictTypeList(TDictTypeEntity tdictTypeEntity,int pageNumber,int pageSize,Locale locale);
	
	CommonResultInfo<TDictTypeEntity> getDictTypeInfo(String dictTypeId,String lang,Locale locale);

	CommonResultInfo<TDictTypeEntity> getDictType(TDictTypeEntity tdictTypeEntity,Locale locale);
	
	CommonResultInfo<?> addDictTypeInfo(TDictTypeEntity tdictTypeEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> updateDictType(TDictTypeEntity tdictTypeEntity, Authentication auth,Locale locale);
	
	@Transactional
	CommonResultInfo<?> deleteDictType(String dictTypeId, Authentication auth,String lang,Locale locale);
	
	CommonResultInfo<?> checkNameRepeat(TDictTypeEntity tdictTypeEntity, String checkFlag, Locale locale);
	
	CommonResultInfo<?> checkCodeRepeat(TDictTypeEntity tdictTypeEntity, String checkFlag,Locale locale);
	
}
