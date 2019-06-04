package cn.springboot.osbulkparts.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.TDictDataEntity;

@Mapper
public interface TDictDataDao {
    int deleteByPrimaryKey(String id);

    int insert(TDictDataEntity record);

    int insertSelective(TDictDataEntity record);

    List<TDictDataEntity> selectByPrimaryKey(String dictTypeCode);

    int updateByPrimaryKeySelective(TDictDataEntity record);

    int updateByPrimaryKey(TDictDataEntity record);
    
    int updateForDeleteLogicByTypeCode(TDictDataEntity record);
}