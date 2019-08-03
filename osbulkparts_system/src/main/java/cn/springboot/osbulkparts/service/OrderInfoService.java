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
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TOrderInfoEntity;

public interface OrderInfoService {

	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(String lang);
	
	@Transactional
	CommonResultInfo<?> importExcel(MultipartFile excleFile,HttpServletRequest request,Authentication auth,int type,int isBalance);
	
	CommonResultInfo<TOrderInfoEntity> selectOrderInfoList(TOrderInfoEntity torderInfoEntity, int pageNumber, int pageSize, Authentication auth);
	
	CommonResultInfo<?> checkOrderInfo(TOrderInfoEntity torderInfoEntity);
	
	CommonResultInfo<TOrderInfoEntity> selectOrderInfo(TOrderInfoEntity torderInfoEntity);

	CommonResultInfo<?> insertOrderInfo(TOrderInfoEntity torderInfoEntity,Authentication auth);
	
	CommonResultInfo<?> updateOrderInfo(TOrderInfoEntity torderInfoEntity,Authentication auth);
	
	CommonResultInfo<?> deleteOrderInfo(String orderNo,Authentication auth);
	
	CommonResultInfo<?> deleteBatchOrderInfo(CommonEntity commonEntity,Authentication auth);
	
	@Transactional
	CommonResultInfo<?> generateOrderDetailInfo(CommonEntity commonEntity,Authentication auth,String lang);
	
	ResponseEntity<byte[]> downloadExcel(TOrderInfoEntity torderInfoEntity);
}
