package cn.springboot.osbulkparts.dao.basedata;

import cn.springboot.osbulkparts.entity.TMaterialQuotaEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TMaterialQuotaDao {
    int insert(TMaterialQuotaEntity record);

    int insertSelective(TMaterialQuotaEntity record);
}