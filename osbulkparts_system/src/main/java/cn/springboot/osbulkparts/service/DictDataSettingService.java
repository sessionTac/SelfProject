package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

public interface DictDataSettingService {
	
	CommonResultInfo<TDictDataEntity> getDictDataList(TDictDataEntity tdictTypeEntity,int pageNumber,int pageSize);
	
	CommonResultInfo<TDictDataEntity> getDictDataInfo(String dictDataId);
	
	CommonResultInfo<?> addUserInfo(TDictDataEntity tdictDataEntity, Authentication auth);
	
	CommonResultInfo<?> updateUserInfo(TDictDataEntity tdictDataEntity, Authentication auth);
	
	CommonResultInfo<?> deleteUserInfo(TDictDataEntity tdictDataEntity, Authentication auth);

}
