package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.TUserRoleRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TUserRoleRelationDao {
    int deleteByPrimaryKey(@Param("id") String id, @Param("userId") String userId, @Param("roleId") String roleId);

    int insert(TUserRoleRelationEntity record);

    int insertSelective(TUserRoleRelationEntity record);

    TUserRoleRelationEntity selectByPrimaryKey(@Param("id") String id, @Param("userId") String userId, @Param("roleId") String roleId);

    int updateByPrimaryKeySelective(TUserRoleRelationEntity record);

    int updateByPrimaryKeyWithBLOBs(TUserRoleRelationEntity record);

    int updateByPrimaryKey(TUserRoleRelationEntity record);

    List<TUserRoleRelationEntity> findRoleByUserId(String userId);
}