package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MRoleInfoDao {
    int deleteByPrimaryKey(String roleId);

    int insert(MRoleInfoEntity record);

    int insertSelective(MRoleInfoEntity record);

    MRoleInfoEntity selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(MRoleInfoEntity record);

    int updateByPrimaryKey(MRoleInfoEntity record);
}