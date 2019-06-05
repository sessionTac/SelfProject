package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.TRoleFunctionRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TRoleFunctionRelationDao {
    int deleteByPrimaryKey(String id);

    int insert(TRoleFunctionRelationEntity record);

    int insertSelective(TRoleFunctionRelationEntity record);

    TRoleFunctionRelationEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TRoleFunctionRelationEntity record);

    int updateByPrimaryKey(TRoleFunctionRelationEntity record);

    List<TRoleFunctionRelationEntity> selectPowerByRoleId(String roleId);
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(String id);
    /**
     * 添加权限
     * @param list
     * @return
     */
    int insertList(@Param("list") List<Integer> list, @Param("roleId") String roleId, @Param("createUser") String createUser);
}