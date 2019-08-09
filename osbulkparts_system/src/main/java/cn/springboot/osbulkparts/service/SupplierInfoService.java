package cn.springboot.osbulkparts.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.springboot.osbulkparts.common.entity.CommonEntity;
import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

public interface SupplierInfoService {
	
	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(String lang, Locale locale);
	
	CommonResultInfo<MSupplierInfoEntity> getSupplierInfoList(MSupplierInfoEntity msupplierInfoEntity, int pageNumber, int pageSize,Locale locale);
	
	CommonResultInfo<MSupplierInfoEntity> getSupplierInfo(String supplierId,Locale locale);

	CommonResultInfo<?> insertSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> updateSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteSupplierInfo(String supplierId, Authentication auth,Locale locale);

	CommonResultInfo<?> deleteBatchMaterialInfo(CommonEntity commonEntity, Authentication auth,Locale locale);
}
