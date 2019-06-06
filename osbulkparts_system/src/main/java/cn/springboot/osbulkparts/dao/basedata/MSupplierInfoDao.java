package cn.springboot.osbulkparts.dao.basedata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;

@Mapper
public interface MSupplierInfoDao {
    int deleteByPrimaryKey(String supplierId);

    int insert(MSupplierInfoEntity record);

    int insertSelective(MSupplierInfoEntity record);

    MSupplierInfoEntity selectByPrimaryKey(String supplierId);

    int updateByPrimaryKeySelective(MSupplierInfoEntity record);

    int updateByPrimaryKey(MSupplierInfoEntity record);
    
    List<MSupplierInfoEntity> selectSupplierInfoList(MSupplierInfoEntity record);
}