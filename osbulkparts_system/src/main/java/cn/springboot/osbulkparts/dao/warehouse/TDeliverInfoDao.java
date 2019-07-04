package cn.springboot.osbulkparts.dao.warehouse;

import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TDeliverInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(TDeliverInfoEntity record);
    
    int insertList(List<TDeliverInfoEntity> record);

    int insertSelective(TDeliverInfoEntity record);

    TDeliverInfoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TDeliverInfoEntity record);

    int updateByPrimaryKey(TDeliverInfoEntity record);
    
    int updateListForDelivery(@Param("ids") String[] ids,@Param("updateUser") String updateUser,@Param("state") String state);

    List<TDeliverInfoEntity> selectTDeliverInfoList(TDeliverInfoEntity tDeliverInfoEntity);
}