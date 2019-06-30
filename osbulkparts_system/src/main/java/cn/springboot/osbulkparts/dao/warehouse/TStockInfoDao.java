package cn.springboot.osbulkparts.dao.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.osbulkparts.entity.TStockInfoEntity;

@Mapper
public interface TStockInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(TStockInfoEntity record);
    
    int insertList(List<TStockInfoEntity> records);

    int insertSelective(TStockInfoEntity record);

    List<TStockInfoEntity> selectByPrimaryKey(TStockInfoEntity tstockInfoEntity);

    int updateByPrimaryKeySelective(TStockInfoEntity record);

    int updateByPrimaryKey(TStockInfoEntity record);
    
    List<TStockInfoEntity> checkingAndVersion(TStockInfoEntity record);
    
    int importExcelData(List<TStockInfoEntity> records);
    
    int deleteBatchData(@Param("ids") String[] ids,@Param("updateUser") String updateUser,@Param("isDelete") String isDelete);
    
    int deleteBatachByNo(List<TStockInfoEntity> records);
}