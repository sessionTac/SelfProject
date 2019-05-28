package cn.springboot.osbulkparts.common.security.service;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;

/**
 * 登录账号验证等权限相关的数据库操作的业务
 * @author liuhb
 *
 */
public interface SecurityService {
	/**
	 * 获取指定平台的，指定用户名的用户信息
	 * @param username 登录的用户名
	 * @return 指定平台的用户信息
	 */
	public MUserInfoEntity getUserByUsername(String username);
	
	/**
	 * 根据角色id获取对应的有权限可以访问的菜单
	 * @param roleId 角色id
	 * @return 权限列表
	 */
//	public List<MFunctionEntity>getFunctions(String roleId);
	
//	public void writeLog(String id,String userName);
}
