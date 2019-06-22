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

	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews();
	
	@Transactional
	CommonResultInfo<?> importExcel(MultipartFile excleFile,HttpServletRequest request,Authentication auth,int type);
	
	CommonResultInfo<TOrderInfoEntity> selectOrderInfoList(TOrderInfoEntity torderInfoEntity, int pageNumber, int pageSize);
	
	CommonResultInfo<TOrderInfoEntity> selectOrderInfo(TOrderInfoEntity torderInfoEntity);

	CommonResultInfo<?> insertMaterialInfo(TOrderInfoEntity torderInfoEntity,Authentication auth);
	
	CommonResultInfo<?> updateMaterialInfo(TOrderInfoEntity torderInfoEntity,Authentication auth);
	
	CommonResultInfo<?> deleteMaterialInfo(String orderNo,Authentication auth);
	
	CommonResultInfo<?> deleteBatchMaterialInfo(CommonEntity commonEntity,Authentication auth);
	
	CommonResultInfo<?> generateOrderDetailInfo(CommonEntity commonEntity,Authentication auth);
	
	ResponseEntity<byte[]> downloadExcel(TOrderInfoEntity torderInfoEntity,int exportType);
}
