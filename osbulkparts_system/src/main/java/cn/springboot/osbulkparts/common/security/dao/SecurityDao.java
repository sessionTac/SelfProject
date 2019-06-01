package cn.springboot.osbulkparts.common.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;

@Mapper
public interface SecurityDao {
	
	public MUserInfoEntity selectUserInfoToCheck(@Param("userName") String userName);
	
	public List<MRoleInfoEntity> selectRoles(@Param("roleId") Integer roleId);
//	
//	@Select(
//			"SELECT f.function_code AS functionCode ,f.function_name AS functionName" 
//			+ " FROM ("
//			+ " SELECT function_id FROM t_role_function WHERE role_id = #{roleId} AND delete_flag = '0'"
//			+ " ) rf "
//			+ " INNER JOIN m_function f ON rf.function_id = f.id"
//			)
//	public List<MFunctionEntity> selectFunctions(String roleId);
}
