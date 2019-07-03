package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

public interface DictDataSettingService {
	
	CommonResultInfo<TDictDataEntity> getDictDataList(TDictDataEntity tdictTypeEntity,int pageNumber,int pageSize);
	
	CommonResultInfo<TDictDataEntity> getDictDataInfo(String dictDataId,int pageNumber,int pageSize);

	CommonResultInfo<TDictDataEntity> getDictDataInfoDetail(String id);
	
	CommonResultInfo<?> addDictData(TDictDataEntity tdictDataEntity, Authentication auth);
	
	CommonResultInfo<?> updateDictData(TDictDataEntity tdictDataEntity, Authentication auth);
	
	CommonResultInfo<?> deleteDictData(TDictDataEntity tdictDataEntity, Authentication auth);


	CommonResultInfo<?> checkValue(TDictDataEntity tDictDataEntity);
}
