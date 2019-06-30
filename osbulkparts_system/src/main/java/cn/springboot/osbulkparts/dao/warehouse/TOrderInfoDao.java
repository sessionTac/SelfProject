package cn.springboot.osbulkparts.dao.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.osbulkparts.entity.TOrderInfoEntity;

@Mapper
public interface TOrderInfoDao {
    int deleteByPrimaryKey(String id);
    
    int deleteByOrderNo(String orderNo);
    
    int deleteBatchData(@Param("ids") String[] ids,@Param("updateUser") String updateUser,@Param("isDelete") String isDelete);

    int insert(TOrderInfoEntity record);
    
    int insertList(List<TOrderInfoEntity> records);

    int insertSelective(TOrderInfoEntity record);

    TOrderInfoEntity selectByPrimaryKey(String id);
    
    List<TOrderInfoEntity> selectByIds(@Param("ids") String[] ids);
    
    List<TOrderInfoEntity> selectOrderInfoListByKeys(TOrderInfoEntity torderInfoEntity);

    int updateByPrimaryKeySelective(TOrderInfoEntity record);

    int updateByPrimaryKey(TOrderInfoEntity record);

    /**根据dataRoleAt查找对应的所有的订单号*/

    List<TOrderInfoEntity> getAllOrderCode(String dataRoleAt);

    /**根据订单号 将订单信息和所有的物料号找出*/
    List<TOrderInfoEntity> getOrderInfoByOrderCode(@Param("materialOrderCode")String materialOrderCode,
                                                   @Param("dataRoleAt")String dataRoleAt);
    /**查询订单表中是否存在该订单号下的数据*/
    List<TOrderInfoEntity> checkOrderCodeAndMaterialCode(@Param("orderCode")String orderCode,
                                                         @Param("dataRoleAt")String dataRoleAt);
}