package cn.springboot.osbulkparts.dao.basedata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.TMaterialQuotaEntity;

@Mapper
public interface TMaterialQuotaDao {
	
	List<TMaterialQuotaEntity> selectByPrimaryKey(TMaterialQuotaEntity record);
	
    int insert(TMaterialQuotaEntity record);
    
    int insertList(List<TMaterialQuotaEntity> record);

    int insertSelective(TMaterialQuotaEntity record);
    
    int updateList(List<TMaterialQuotaEntity> record);
}