package cn.springboot.osbulkparts.dao.warehouse;

import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TDeliverInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(TDeliverInfoEntity record);

    int insertSelective(TDeliverInfoEntity record);

    TDeliverInfoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TDeliverInfoEntity record);

    int updateByPrimaryKey(TDeliverInfoEntity record);
}