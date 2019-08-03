package cn.springboot.osbulkparts.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;
import cn.springboot.osbulkparts.entity.TOrderInfoEntity;

public interface OrderDetailInfoService {

    CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(String lang);

    CommonResultInfo<TOrderDetailInfoEntity> selectOrderDetailInfoList(TOrderDetailInfoEntity tOrderDetailInfoEntity, int pageNumber, int pageSize, Authentication auth);

    CommonResultInfo<TOrderDetailInfoEntity> selectOrderDetailInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity);

    /**
     * 获取全部的订单号
     */
    CommonResultInfo<TOrderInfoEntity> getAllOrderCode(String isBalance,Authentication auth);
    /**
     * 根据订单号将所有订单信息和所有物料号取出
     */
    CommonResultInfo<TOrderInfoEntity> getOrderInfoByOrderCode(String materialOrderCode,String isBalance,Authentication auth);

    /**
     * 根据物料号找到物料的所有信息
     */
    CommonResultInfo<MMaterialInfoEntity> getMaterialInfoByMaterialCode(String materialCode,Authentication auth,String lang);

    /**
     * 校验订单号和物料号是否存在
     */
    CommonResultInfo<?> checkOrderCodeAndMaterialCode(String orderCode,String isBalance,String materialCode,Authentication auth);

    CommonResultInfo<?> updateOrderDetailInfoInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity,Authentication auth);

    CommonResultInfo<?> insertOrderDetailInfoInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity,Authentication auth);

    CommonResultInfo<?> deleteBatchOrderInfo(CommonEntity commonEntity, Authentication auth);

    CommonResultInfo<?> approvalBatchOrderInfo(CommonEntity commonEntity, Authentication auth);
    
    CommonResultInfo<?> selectDeliveryInfo(CommonEntity commonEntity);
    
    @Transactional
    CommonResultInfo<?> excuteDeliveryInfo(CommonEntity commonEntity, Authentication auth,String lang);

    CommonResultInfo<Map<String, List<TDictDataEntity>>> sendGoodsInit(String lang);
    
    ResponseEntity<byte[]> downloadExcel(TOrderDetailInfoEntity tOrderDetailInfoEntity);
}
