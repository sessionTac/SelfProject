package cn.springboot.osbulkparts.common.security.dao;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SecurityDao {
	
	public MUserInfoEntity selectUserInfoToCheck(@Param("userName") String userName);
	
//	@Select(
//			"SELECT r.role_name as roleName,ur.role_id as id " 
//			+ " FROM t_user_role ur"
//			+ " INNER JOIN m_role r ON ur.role_id = r.id AND ur.user_id = #{id}"
//			+ " WHERE INSTR(r.platform, #{platform}) AND r.delete_flag = '0';"
//			)
//	public List<MRoleEntity> selectRoles(@Param("id") Integer id,@Param("platform") String platform);
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
