package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.MFunctionInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MFunctionInfoDao {
    int deleteByPrimaryKey(String functionId);

    int insert(MFunctionInfoEntity record);

    int insertSelective(MFunctionInfoEntity record);

    MFunctionInfoEntity selectByPrimaryKey(String functionId);

    int updateByPrimaryKeySelective(MFunctionInfoEntity record);

    int updateByPrimaryKey(MFunctionInfoEntity record);
}