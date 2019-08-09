package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

import java.util.Locale;

public interface DictDataSettingService {

	CommonResultInfo<TDictDataEntity> getDictDataList(TDictDataEntity tdictTypeEntity, int pageNumber, int pageSize, Locale locale);
	
	CommonResultInfo<TDictDataEntity> getDictDataInfo(String dictDataId,int pageNumber,int pageSize, String lang,Locale locale);

	CommonResultInfo<TDictDataEntity> getDictDataInfoDetail(String id,String lang,Locale locale);
	
	CommonResultInfo<?> addDictData(TDictDataEntity tdictDataEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> updateDictData(TDictDataEntity tdictDataEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteDictData(TDictDataEntity tdictDataEntity, Authentication auth,Locale locale);


	CommonResultInfo<?> checkValue(TDictDataEntity tDictDataEntity,Locale locale);
}
