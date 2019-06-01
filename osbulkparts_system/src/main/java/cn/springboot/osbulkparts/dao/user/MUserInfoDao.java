package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
}