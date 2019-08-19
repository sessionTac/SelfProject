package cn.springboot.osbulkparts.dao.basedata;

import cn.springboot.osbulkparts.entity.TMaterialRecordInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TMaterialRecordInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(TMaterialRecordInfoEntity record);

    int insertSelective(TMaterialRecordInfoEntity record);

    TMaterialRecordInfoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TMaterialRecordInfoEntity record);
    
    int upsert(TMaterialRecordInfoEntity record);

    int updateByPrimaryKey(TMaterialRecordInfoEntity record);
}