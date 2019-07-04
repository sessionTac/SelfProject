package cn.springboot.osbulkparts.dao.warehouse;

import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TOrderDetailInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(TOrderDetailInfoEntity record);
    
    int insertList(List<TOrderDetailInfoEntity> records);

    int insertSelective(TOrderDetailInfoEntity record);

    TOrderDetailInfoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TOrderDetailInfoEntity record);

    int updateByPrimaryKey(TOrderDetailInfoEntity record);

    List<TOrderDetailInfoEntity> getOrderDetailInfoList(TOrderDetailInfoEntity tOrderDetailInfoEntity);
    
    List<TOrderDetailInfoEntity> selectDeliveryInfo(@Param("ids") String[] ids,@Param("dateFlag") String dateFlag);

    int deleteBatchData(@Param("ids") String[] ids, @Param("updateUser") String updateUser, @Param("isDelete") String isDelete);

    int approvalBatchData(@Param("ids") String[] ids, @Param("updateUser") String updateUser, @Param("confirmStatus") String confirmStatus);

}