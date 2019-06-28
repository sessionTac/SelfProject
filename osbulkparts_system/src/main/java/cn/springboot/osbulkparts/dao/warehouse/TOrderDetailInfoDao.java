package cn.springboot.osbulkparts.dao.warehouse;

import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TOrderDetailInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(TOrderDetailInfoEntity record);

    int insertSelective(TOrderDetailInfoEntity record);

    TOrderDetailInfoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TOrderDetailInfoEntity record);

    int updateByPrimaryKey(TOrderDetailInfoEntity record);
}