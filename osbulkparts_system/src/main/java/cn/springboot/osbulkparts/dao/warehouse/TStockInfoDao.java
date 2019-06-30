package cn.springboot.osbulkparts.dao.warehouse;

import cn.springboot.osbulkparts.entity.TStockInfoEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TStockInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(TStockInfoEntity record);

    int insertSelective(TStockInfoEntity record);

    List<TStockInfoEntity> selectByPrimaryKey(TStockInfoEntity tstockInfoEntity);

    int updateByPrimaryKeySelective(TStockInfoEntity record);

    int updateByPrimaryKey(TStockInfoEntity record);
}