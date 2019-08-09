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
import cn.springboot.osbulkparts.entity.TOrderInfoEntity;

public interface OrderInfoService {

	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(String lang, Locale locale);
	
	@Transactional
	CommonResultInfo<?> importExcel(MultipartFile excleFile,HttpServletRequest request,Authentication auth,int type,int isBalance,String lang,Locale locale);
	
	CommonResultInfo<TOrderInfoEntity> selectOrderInfoList(TOrderInfoEntity torderInfoEntity, int pageNumber, int pageSize, Authentication auth,Locale locale);
	
	CommonResultInfo<?> checkOrderInfo(TOrderInfoEntity torderInfoEntity,Locale locale);
	
	CommonResultInfo<TOrderInfoEntity> selectOrderInfo(TOrderInfoEntity torderInfoEntity,Locale locale);

	CommonResultInfo<?> insertOrderInfo(TOrderInfoEntity torderInfoEntity,Authentication auth,Locale locale);
	
	CommonResultInfo<?> updateOrderInfo(TOrderInfoEntity torderInfoEntity,Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteOrderInfo(String orderNo,Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteBatchOrderInfo(CommonEntity commonEntity,Authentication auth,Locale locale);
	
	@Transactional
	CommonResultInfo<?> generateOrderDetailInfo(CommonEntity commonEntity,Authentication auth,String lang,Locale locale);
	
	ResponseEntity<byte[]> downloadExcel(TOrderInfoEntity torderInfoEntity,Locale locale);
}
