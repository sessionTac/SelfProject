package cn.springboot.osbulkparts.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

public interface SupplierInfoService {
	
	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews();
	
	CommonResultInfo<MSupplierInfoEntity> getSupplierInfoList(MSupplierInfoEntity msupplierInfoEntity, int pageNumber, int pageSize);
	
	CommonResultInfo<MSupplierInfoEntity> getSupplierInfo(String supplierId);

	CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions();
	
	CommonResultInfo<MSupplierInfoEntity> getUserCustomerRelationInfo(String supplierId);

	CommonResultInfo<?> addSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth);
	
	CommonResultInfo<?> updateSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth);
	
	CommonResultInfo<?> deleteSupplierInfo(String supplierId, Authentication auth);
	
}