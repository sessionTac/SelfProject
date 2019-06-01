package cn.springboot.osbulkparts.common.security.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cn.springboot.osbulkparts.common.security.entity.LoginRequestParamEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.security.exception.Authentication401Exception;
import cn.springboot.osbulkparts.common.security.service.SecurityService;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider{
	private SecurityService service;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public JWTAuthenticationProvider(SecurityService service) {
		super();
		this.service = service;
	}

	/**
	 * 通过数据库对登录的用户名密码进行验证。
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LoginRequestParamEntity param = (LoginRequestParamEntity)authentication.getPrincipal();
		MUserInfoEntity user = service.getUserByUsername(param.getUsername());
		
		if ( null == user) {
			throw new Authentication401Exception("user name is invalid!");
		}
		
//		boolean passwordIsRight = encoder.matches(param.getPassword(), user.getPassword());
		boolean passwordIsRight = false;
		if(param.getPassword().equals(user.getPassword())) {
			passwordIsRight = true;
		}
		if(!passwordIsRight) {
			throw new Authentication401Exception("password is invalid!");
		}
		
		SecurityUserInfoEntity principal = new SecurityUserInfoEntity();
//		List<TUserAttrEntity> tuserAttrEntity = user.getUserAttr();
		String userRealName = null;
		String userType = null;
//		for(TUserAttrEntity userAttr:tuserAttrEntity) {
//			if(CommonSqlUtils.isNotBlank(userAttr.getAttrId())) {
//				String[] attg = userAttr.getAttrId().split(":");
//				if(("用户类型").equals(attg[0])||("1").equals(attg[1])) {
//					userType = userAttr.getAttrValue().split(":")[1].toString();
//				}
//				if(("用户真实姓名").equals(attg[0])||("2").equals(attg[1])) {
//					userRealName = userAttr.getAttrValue().split(":")[1].toString();
//				}
//			}
//		}
		principal.setUserId(user.getUserId());
		principal.setUserName(user.getUserName());
		principal.setUserRealName(user.getUserRealName());
		principal.setUserType(user.getUserType());
//		principal.setRoles(getRolesToJsonArray(user.getRoles()));
		
		// 添加操作日志
//		service.writeLog(String.valueOf(user.getId()),user.getUserName());
		return new UsernamePasswordAuthenticationToken(principal,null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
//	/**
//	 * 将数据库查出的角色实体类中的角色名称和id组成json对象
//	 * @param roles
//	 * @return
//	 */
//	private JSONArray getRolesToJsonArray(List<MRoleEntity> roles) {
//		JSONArray jsonArray = new JSONArray();
//		for (MRoleEntity role : roles) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("roleId",role.getId());
//			jsonObject.put("roleName",role.getRoleName());
//			jsonArray.add(jsonObject);
//		}
//		return jsonArray;
//	}

}
