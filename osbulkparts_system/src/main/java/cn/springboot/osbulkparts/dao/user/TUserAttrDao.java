package cn.springboot.osbulkparts.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.TUserAttrEntity;

@Mapper
public interface TUserAttrDao {
    int deleteByPrimaryKey(String id);

    int insert(TUserAttrEntity record);

    int insertSelective(TUserAttrEntity record);

    TUserAttrEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TUserAttrEntity record);

    int updateByPrimaryKey(TUserAttrEntity record);
    
    int insertUsrAttrBatch(List<TUserAttrEntity> records);
}