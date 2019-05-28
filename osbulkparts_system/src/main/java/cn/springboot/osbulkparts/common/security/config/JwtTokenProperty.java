package cn.springboot.osbulkparts.common.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "osbulkparts.security.jwt")
public class JwtTokenProperty {
	 /**
     * token过期时间
     */
    private Integer expirationTime;

    /**
     * Token issuer.
     */
    private String issuer;
    
    /**
     * token秘钥
     */
    private String signKey;
}
