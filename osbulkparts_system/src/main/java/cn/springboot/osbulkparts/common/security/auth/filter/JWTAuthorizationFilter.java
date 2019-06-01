package cn.springboot.osbulkparts.common.security.auth.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.alibaba.fastjson.JSONObject;

import cn.springboot.osbulkparts.common.security.config.JwtTokenProperty;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.security.utils.JwtTokenUtils;

/**
 * 登录以外的所有url都会被此Filter拦截验证
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtTokenProperty jwtTokenProperty;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,JwtTokenProperty jwtTokenProperty) {
		super(authenticationManager);
		this.jwtTokenProperty = jwtTokenProperty;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
		// 如果请求头中没有Authorization信息则直接放行
		if (StringUtils.isEmpty(tokenHeader) || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		// 如果请求头中有token，则进行解析，并且设置认证信息
		try {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(tokenHeader);
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			super.doFilterInternal(request, response, chain);
		} catch (RuntimeException e) {
			JSONObject json = new JSONObject();
			json.put("msg", "token is invalid");
			json.put("exceptionMsg", e.getMessage());
			
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write(json.toJSONString());
		}
	}

	// 这里从token中获取用户信息并新建一个token
	private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
		String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
		SecurityUserInfoEntity principal = JwtTokenUtils.getPrincipal(token,jwtTokenProperty.getTokenSigningKey());

		if (principal != null) {
			return new UsernamePasswordAuthenticationToken(principal, null, new ArrayList<>());
		}

		return null;
	}
}
