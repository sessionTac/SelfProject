package cn.springboot.osbulkparts.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;

@Mapper
public interface MUserInfoDao {
    int deleteByPrimaryKey(String userId);

    int insert(MUserInfoEntity record);

    int insertSelective(MUserInfoEntity record);

    MUserInfoEntity selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(MUserInfoEntity record);

    int updateByPrimaryKey(MUserInfoEntity record);
    
    List<MUserInfoEntity> selectUserInfoList(MUserInfoEntity record);
    
    MUserInfoEntity selectUserInfoByUserId(String userId);
    
    List<MUserInfoEntity> selectUserCustomerRelation(String userId);
}