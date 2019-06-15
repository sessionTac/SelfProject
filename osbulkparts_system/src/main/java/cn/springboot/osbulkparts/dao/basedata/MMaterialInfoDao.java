package cn.springboot.osbulkparts.dao.basedata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;

@Mapper
public interface MMaterialInfoDao {
    int deleteByPrimaryKey(String materialInfoId);

    int insert(MMaterialInfoEntity record);
    
    int insertList(List<MMaterialInfoEntity> records);
    
    int updateList(List<MMaterialInfoEntity> records);

    int insertSelective(MMaterialInfoEntity record);

    List<MMaterialInfoEntity> selectByPrimaryKey(MMaterialInfoEntity materialInfoEntity);

    int updateByPrimaryKeySelective(MMaterialInfoEntity record);

    int updateByPrimaryKey(MMaterialInfoEntity record);
    
    int lockedData(@Param("ids") String[] ids,@Param("updateUser") String updateUser,@Param("isLocked") String isLocked);
}