package cn.springboot.osbulkparts.common.security.entity;

import lombok.Data;

@Data
public class LoginRequestParamEntity {
	private String username;
	private String password;
	private String platform;
	private Integer rememberMe;
}
