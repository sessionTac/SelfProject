package cn.springboot.osbulkparts.dao.system;

import cn.springboot.osbulkparts.entity.TDictDataEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TDictDataDao {
    int deleteByPrimaryKey(String id);

    int insert(TDictDataEntity record);

    int insertSelective(TDictDataEntity record);

    TDictDataEntity selectByPrimaryKey(String dictTypeId);

    int updateByPrimaryKeySelective(TDictDataEntity record);

    int updateByPrimaryKey(TDictDataEntity record);
}