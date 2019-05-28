package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MUserInfoDao {
    int deleteByPrimaryKey(String userId);

    int insert(MUserInfoEntity record);

    int insertSelective(MUserInfoEntity record);

    MUserInfoEntity selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(MUserInfoEntity record);

    int updateByPrimaryKey(MUserInfoEntity record);
}