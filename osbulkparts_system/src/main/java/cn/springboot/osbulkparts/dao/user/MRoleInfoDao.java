package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MRoleInfoDao {
    int deleteByPrimaryKey(String roleId);

    int insert(MRoleInfoEntity record);

    int insertSelective(MRoleInfoEntity record);

    MRoleInfoEntity selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(MRoleInfoEntity record);

    int updateByPrimaryKey(MRoleInfoEntity record);

    /**
     * 根据 角色名 来查找角色列表 分页
     */
    List<MRoleInfoEntity> selectRoleInfoList(MRoleInfoEntity mRoleInfoEntity);
    /**
     * 根据 roleid 来查找一条数据的详细信息
     */
    MRoleInfoEntity selectRoleInfo(@Param("roleId") String roleId,@Param("lang") String lang);
    /**
     * 校验角色名是否重复
     */
    List<MRoleInfoEntity> checkInfo(MRoleInfoEntity mRoleInfoEntity);
    /**
     * 根据role 删除一条数据 逻辑删除
     */
    int deleteByRoleId(MRoleInfoEntity mRoleInfoEntity);
}