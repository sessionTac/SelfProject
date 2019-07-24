package cn.springboot.osbulkparts.dao.basedata;

import cn.springboot.osbulkparts.entity.TFileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TFileDao {
    int deleteByPrimaryKey(String id);

    int insert(TFileEntity record);

    int insertSelective(TFileEntity record);

    TFileEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TFileEntity record);

    int updateByPrimaryKey(TFileEntity record);
}