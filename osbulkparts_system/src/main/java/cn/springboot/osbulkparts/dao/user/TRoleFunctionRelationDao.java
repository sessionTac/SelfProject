package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.TRoleFunctionRelationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TRoleFunctionRelationDao {
    int deleteByPrimaryKey(String id);

    int insert(TRoleFunctionRelationEntity record);

    int insertSelective(TRoleFunctionRelationEntity record);

    TRoleFunctionRelationEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TRoleFunctionRelationEntity record);

    int updateByPrimaryKey(TRoleFunctionRelationEntity record);
}