package cn.springboot.osbulkparts.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.TDictTypeEntity;

@Mapper
public interface TDictTypeDao {
    int deleteByPrimaryKey(String dictTypeId);

    int insert(TDictTypeEntity tdictTypeEntity);

    int insertSelective(TDictTypeEntity tdictTypeEntity);

    List<TDictTypeEntity> selectByPrimaryKey(TDictTypeEntity tdictTypeEntity);

    int updateByPrimaryKeySelective(TDictTypeEntity tdictTypeEntity);

    int updateByPrimaryKey(TDictTypeEntity tdictTypeEntity);
    
    List<TDictTypeEntity> getDictTypeList(TDictTypeEntity tdictTypeEntity);
}