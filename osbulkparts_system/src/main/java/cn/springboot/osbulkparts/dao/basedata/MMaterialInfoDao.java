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
    
    List<MMaterialInfoEntity> selectAllVersion();

    List<MMaterialInfoEntity> selectByPrimaryKey(MMaterialInfoEntity materialInfoEntity);

    int updateByPrimaryKeySelective(MMaterialInfoEntity record);

    int updateByPrimaryKey(MMaterialInfoEntity record);
    
    int lockedData(@Param("ids") String[] ids,@Param("updateUser") String updateUser,@Param("isLocked") String isLocked);
    
    int deleteBatchData(@Param("ids") String[] ids,@Param("updateUser") String updateUser,@Param("isDelete") String isDelete);

    MMaterialInfoEntity selectInfoById(@Param("id")String id,@Param("dataRoleAt")String dataRoleAt);
    /**
     * 查重（用户名）和排他字段比较   luka
     */
    List<MMaterialInfoEntity> checkingAndVersion(MMaterialInfoEntity record);
    /**查询物料表中是否存在该物料号的数据*/
    List<MMaterialInfoEntity> checkOrderCodeAndMaterialCode(@Param("materialCode")String materialCode,
                                                         @Param("dataRoleAt")String dataRoleAt);

}