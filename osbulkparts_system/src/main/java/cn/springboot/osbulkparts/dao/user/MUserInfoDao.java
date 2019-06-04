package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MUserInfoDao {
	
    int deleteByPrimaryKey(String userId);

    int insert(MUserInfoEntity record);

    int insertSelective(MUserInfoEntity record);

    MUserInfoEntity selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(MUserInfoEntity record);

    int updateByPrimaryKey(MUserInfoEntity record);

    /**
     * 根据 用户名和用户姓名进行模糊查询 查找用户信息列表  luka
     * @param userInfoEntity
     * @return
     */
    List<MUserInfoEntity> selectUserInfoList(MUserInfoEntity userInfoEntity);

    /**
     * 根据 userid 查找一条用户的详细信息  luka
     */
    MUserInfoEntity selectUserInfo(String userId);
    
    /**
     * 根据 用户名和角色ID 查找当前用户的权限信息
     */
    MUserInfoEntity selectUserWithRoleAndFunc(@Param("userName") String userName,@Param("roleId") String roleId);
    /**
     * 查重（用户名）和排他字段比较   luka
     */
    List<MUserInfoEntity> checkingAndVersion(MUserInfoEntity userInfoEntity);
}