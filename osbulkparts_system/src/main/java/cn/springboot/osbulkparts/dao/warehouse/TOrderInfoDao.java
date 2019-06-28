package cn.springboot.osbulkparts.dao.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.osbulkparts.entity.TOrderInfoEntity;

@Mapper
public interface TOrderInfoDao {
    int deleteByPrimaryKey(String id);
    
    int deleteByOrderNo(String orderNo);
    
    int deleteBatchData(@Param("ids") String[] ids,@Param("updateUser") String updateUser,@Param("isDelete") String isDelete);

    int insert(TOrderInfoEntity record);
    
    int insertList(List<TOrderInfoEntity> records);

    int insertSelective(TOrderInfoEntity record);

    TOrderInfoEntity selectByPrimaryKey(String id);
    
    List<TOrderInfoEntity> selectByIds(@Param("ids") String[] ids);
    
    List<TOrderInfoEntity> selectOrderInfoListByKeys(TOrderInfoEntity torderInfoEntity);

    int updateByPrimaryKeySelective(TOrderInfoEntity record);

    int updateByPrimaryKey(TOrderInfoEntity record);
}