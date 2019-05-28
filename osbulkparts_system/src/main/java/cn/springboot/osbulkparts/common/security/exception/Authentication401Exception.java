package cn.springboot.osbulkparts.common.security.exception;

import org.springframework.security.core.AuthenticationException;

public class Authentication401Exception extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Authentication401Exception(String message) {
		super(message);
	}
	
	public Authentication401Exception(String message, Throwable cause) {
		super(message, cause);
    }

}
