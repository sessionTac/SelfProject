package cn.springboot.osbulkparts.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.TDictTypeEntity;

@Mapper
public interface TDictTypeDao {
    int deleteByPrimaryKey(String dictTypeId);

    int insert(TDictTypeEntity record);

    int insertSelective(TDictTypeEntity record);

    TDictTypeEntity selectByPrimaryKey(String dictTypeId);

    int updateByPrimaryKeySelective(TDictTypeEntity record);

    int updateByPrimaryKey(TDictTypeEntity record);
    
    List<TDictTypeEntity> getDictTypeList(TDictTypeEntity tdictTypeEntity);
}