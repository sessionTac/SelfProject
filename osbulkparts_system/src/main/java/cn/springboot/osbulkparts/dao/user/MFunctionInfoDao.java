package cn.springboot.osbulkparts.dao.user;

import cn.springboot.osbulkparts.entity.MFunctionInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MFunctionInfoDao {
    int deleteByPrimaryKey(String functionId);

    int insert(MFunctionInfoEntity record);

    int insertSelective(MFunctionInfoEntity record);

    MFunctionInfoEntity selectByPrimaryKey(String functionId);

    int updateByPrimaryKeySelective(MFunctionInfoEntity record);

    int updateByPrimaryKey(MFunctionInfoEntity record);

    //查询维护平台权限树结构
    List<MFunctionInfoEntity> selectTree();
}