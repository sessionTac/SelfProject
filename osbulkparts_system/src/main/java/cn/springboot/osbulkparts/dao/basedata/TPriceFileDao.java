package cn.springboot.osbulkparts.dao.basedata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.TPriceFileEntity;

@Mapper
public interface TPriceFileDao {
    int deleteByPrimaryKey(String id);

    int insert(TPriceFileEntity record);
    
    int insertLst(List<TPriceFileEntity> priceFileLst);

    int insertSelective(TPriceFileEntity record);

    List<TPriceFileEntity> selectByPrimaryKey(TPriceFileEntity record);
    
    List<TPriceFileEntity> selectByMaterialCode(TPriceFileEntity record);

    int updateByPrimaryKeySelective(TPriceFileEntity record);

    int updateByPrimaryKey(TPriceFileEntity record);
}