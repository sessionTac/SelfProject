package cn.springboot.osbulkparts.common.security.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class SecurityUserInfoEntity {
	
	private String userId;
	
	private String userName;
	
	private String passWord;
	
	private Integer userType;
	
	private String userRealName;
	
	private Collection<? extends GrantedAuthority> authorities;

}
