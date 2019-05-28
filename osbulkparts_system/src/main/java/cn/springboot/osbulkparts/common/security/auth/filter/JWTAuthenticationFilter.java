package cn.springboot.osbulkparts.common.security.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.springboot.osbulkparts.common.security.config.JwtTokenProperty;
import cn.springboot.osbulkparts.common.security.entity.LoginRequestParamEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.security.exception.Authentication401Exception;
import cn.springboot.osbulkparts.common.security.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用/auth/login登录时，调用此Filter。其他情况不调用
 * 
 * @author liuhb
 *
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JwtTokenProperty jwtTokenProperty;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProperty jwtTokenProperty) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProperty = jwtTokenProperty;

		// 覆盖默认的post方式的login路径，当url为此地址时，springsecurity会拦截到，进行登录验证
		super.setFilterProcessesUrl("/auth/login");
	}

	/**
	 * 用户传入登录账号和密码 然后组成一个实现Authentication接口的token，
	 * 然后通过AuthenticationManager的authenticate(authentication)，对这个token进行验证，
	 * 实际调用的是实现这个接口的ProviderManager类的authenticate（）方法
	 * 
	 * 此方法如果抛出异常，便会调用此类中的unsuccessfulAuthentication方法，然后将自定义信息返回到页面
	 * 
	 * @return 
	 * 			认证成功后，生成一个实现了Authentication的Token（如usernamepasswordToken或者其他的子类）
	 * 			Token中保存用户相关信息，最后会放到security上下文中
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		LoginRequestParamEntity loginRequest;
		try {
			// 从输入流中获取到登录的信息
			loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestParamEntity.class);
		} catch (IOException e) {
			throw new Authentication401Exception("system authentication error");
		}
		
		if (StringUtils.isAllEmpty(loginRequest.getUsername())) {
			throw new Authentication401Exception("please input user name !");
		}
		
		if (StringUtils.isAllEmpty(loginRequest.getPassword())) {
			throw new Authentication401Exception("please input password !");
		}
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest, loginRequest.getPassword());
		return authenticationManager.authenticate(authentication);
	}

	/**
	 * 成功验证后调用的方法 如果验证成功，就生成token并返回
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		// 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象,所以就是JwtUser
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity) authResult.getPrincipal();
		log.info("principal:" + principal.toString());
		
		// 返回创建成功的token
		String token = JwtTokenUtils.createToken(principal, jwtTokenProperty, false);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("token", token);
		jsonObject.put("userRealName", principal.getUserRealName());
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().write(jsonObject.toJSONString());
	}

	/**
	 * 本类的attemptAuthentication方法中抛出异常时，便会调用此方法，将自定义的错误信息返回到页面
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		JSONObject json = new JSONObject();
		json.put("msg", failed.getMessage());
		json.put("from", "JWTAuthenticationFilter.class");

		response.getWriter().write(json.toJSONString());
	}
}
