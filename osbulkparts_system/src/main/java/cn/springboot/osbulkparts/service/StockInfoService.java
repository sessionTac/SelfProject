package cn.springboot.osbulkparts.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TStockInfoEntity;

public interface StockInfoService {
	
	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(String lang, Locale locale);

	CommonResultInfo<?> getStockInfoList(TStockInfoEntity stockInfoEntity, int pageNumber, int pageSize,Authentication auth,Locale locale);
	
	CommonResultInfo<TStockInfoEntity> getStockInfoInfo(TStockInfoEntity stockInfoEntity,Locale locale);
	
	CommonResultInfo<?> insertStockInfo(TStockInfoEntity stockInfoEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> updateStockInfo(TStockInfoEntity stockInfoEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteStockInfo(String stock, Authentication auth,Locale locale);

	CommonResultInfo<?> deleteBatchByIds(CommonEntity commonEntity, Authentication auth,Locale locale);
	
	@Transactional
	CommonResultInfo<?> importExcel(MultipartFile excleFile,HttpServletRequest request,Authentication auth,Locale locale);
	
	ResponseEntity<byte[]> downloadExcel(TStockInfoEntity stockInfoEntity,Locale locale);
}
