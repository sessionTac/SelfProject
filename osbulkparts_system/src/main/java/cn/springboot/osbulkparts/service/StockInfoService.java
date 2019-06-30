package cn.springboot.osbulkparts.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TStockInfoEntity;

public interface StockInfoService {
	
	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews();

	CommonResultInfo<?> getStockInfoList(TStockInfoEntity stockInfoEntity, int pageNumber, int pageSize,Authentication auth); 
	
	CommonResultInfo<TStockInfoEntity> getStockInfoInfo(TStockInfoEntity stockInfoEntity); 
	
	CommonResultInfo<?> insertSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth);
	
	CommonResultInfo<?> updateSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth);
	
	CommonResultInfo<?> deleteSupplierInfo(String supplierId, Authentication auth);

	CommonResultInfo<?> deleteBatchMaterialInfo(CommonEntity commonEntity, Authentication auth);
	
	@Transactional
	CommonResultInfo<?> importExcel(MultipartFile excleFile,HttpServletRequest request,Authentication auth);
	
	ResponseEntity<byte[]> downloadExcel(MMaterialInfoEntity materialInfoEntity);
}
