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
		MUserInfoEntity user = new MUserInfoEntity();
		try {
			user = service.getUserByUsername(param.getUsername());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if ( null == user) {
			throw new Authentication401Exception("user name is invalid!");
		}
		
		boolean passwordIsRight = encoder.matches(param.getPassword(), user.getPassword());
		if(!passwordIsRight) {
			throw new Authentication401Exception("password is invalid!");
		}
		
		SecurityUserInfoEntity principal = new SecurityUserInfoEntity();
		principal.setUserId(user.getUserId());
		principal.setUserName(user.getUserName());
		principal.setUserRealName(user.getUserRealName());
		principal.setUserType(user.getUserType());
		principal.setRoleList(user.getRoleList());
		principal.setUserStauts(user.getUserStatus());
		
		// 添加操作日志
//		service.writeLog(String.valueOf(user.getId()),user.getUserName());
		return new UsernamePasswordAuthenticationToken(principal,null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
