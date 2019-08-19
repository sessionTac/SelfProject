package cn.springboot.osbulkparts.dao.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;

@Mapper
public interface TOrderDetailInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(TOrderDetailInfoEntity record);
    
    int insertList(List<TOrderDetailInfoEntity> records);

    int insertSelective(TOrderDetailInfoEntity record);
    
    int deleteByOrderCode(@Param("orderCodes") String[] orderCode);

    TOrderDetailInfoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TOrderDetailInfoEntity record);

    int updateByPrimaryKey(TOrderDetailInfoEntity record);

    List<TOrderDetailInfoEntity> getOrderDetailInfoList(TOrderDetailInfoEntity tOrderDetailInfoEntity);
    
    List<TOrderDetailInfoEntity> getReportOrderDetailInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity);
    
    List<TOrderDetailInfoEntity> selectDeliveryInfo(@Param("ids") String[] ids,@Param("idsT") String[] idsT,@Param("dateFlag") String dateFlag);
    
    int deleteByOrderCodeAndDate(TOrderDetailInfoEntity record);

    int deleteBatchData(@Param("ids") String[] ids, @Param("updateUser") String updateUser, @Param("isDelete") String isDelete);

    int approvalBatchData(@Param("ids") String[] ids, @Param("updateUser") String updateUser, @Param("confirmStatus") String confirmStatus);
    
    int updateDeliveryAmount(@Param("orderCode") String orderCode,@Param("materialCode") String materialCode, @Param("updateUser") String updateUser, 
    		                 @Param("dateFlag") String dateFlag);

}