package cn.springboot.osbulkparts.common.security.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.springboot.osbulkparts.common.security.config.JwtTokenProperty;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtils {
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

	// 选择了记住我之后的过期时间为7天
	private static final long EXPIRATION_REMEMBER = 604800L;

	/**
	 * 此token返回前台后迅速过期。然后通过下面的createTokenByRoleId来获取访问资源用的token
	 * @param principal
	 * @param jwtTokenProperty
	 * @param isRememberMe
	 * @return
	 */
	public static String createToken(SecurityUserInfoEntity principal, JwtTokenProperty jwtTokenProperty, boolean isRememberMe) {
		long expiration = isRememberMe ? EXPIRATION_REMEMBER : jwtTokenProperty.getRefreshTokenExpTime();

		Claims claims = Jwts.claims().setSubject(principal.getUserName());
		claims.put("userRealName", principal.getUserRealName());
		claims.put("userType", principal.getUserType());
		claims.put("userId", principal.getUserId());
		String token = Jwts
				.builder()
				.signWith(SignatureAlgorithm.HS512, jwtTokenProperty.getTokenSigningKey())
				.setIssuer(jwtTokenProperty.getTokenIssuer())
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 30 * 1000)) 
				.compact();

		return token;
	}
	
	/**
	 * 根据登录是用户选择的角色来创建一个真正的token。每次访问资源时带着此token进行验证
	 * @param principal
	 * @param jwtTokenProperty
	 * @param isRememberMe
	 * @return
	 */
	public static String createTokenByRoleId(SecurityUserInfoEntity principal, JwtTokenProperty jwtTokenProperty, boolean isRememberMe) {
		long expiration = isRememberMe ? EXPIRATION_REMEMBER : jwtTokenProperty.getRefreshTokenExpTime();

		Claims claims = Jwts.claims().setSubject(principal.getUserName());
		claims.put("userRealName", principal.getUserRealName());
		claims.put("userType", principal.getUserType());
		claims.put("userId", principal.getUserId());
		claims.put("roleId",StringUtils.isEmpty(principal.getRoleIdSelected()) ? StringUtils.EMPTY : principal.getRoleIdSelected());
		String token = Jwts
				.builder()
				.signWith(SignatureAlgorithm.HS512, jwtTokenProperty.getTokenSigningKey())
				.setIssuer(jwtTokenProperty.getTokenIssuer())
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 60 * 1000))
				.compact();

		return token;
	}

	/**
	 * 从token中获取用户相关内容
	 * 
	 * @param token
	 * @param signKey
	 * @return
	 */
	public static SecurityUserInfoEntity getPrincipal(String token, String signKey) {
		Claims claims = getTokenBody(token, signKey);

		SecurityUserInfoEntity principal = new SecurityUserInfoEntity();
		principal.setUserName(String.valueOf(claims.getSubject()));
		principal.setUserRealName(String.valueOf(claims.get("userRealName")));
		principal.setUserType((Integer)(claims.get("userType")));
		principal.setUserId(String.valueOf(claims.get("userId")));
		principal.setRoleIdSelected(String.valueOf(claims.get("roleId")));
		return principal;
	}

	// 是否已过期
	public static boolean isExpiration(String token, String signKey) {
		return getTokenBody(token, signKey).getExpiration().before(new Date());
	}

	private static Claims getTokenBody(String token, String signKey) {
		return Jwts.parser().setSigningKey(signKey).parseClaimsJws(token).getBody();
	}
}
