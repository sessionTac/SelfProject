package cn.springboot.osbulkparts.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cn.springboot.osbulkparts.common.security.auth.filter.JWTAuthenticationFilter;
import cn.springboot.osbulkparts.common.security.auth.filter.JWTAuthorizationFilter;
import cn.springboot.osbulkparts.common.security.auth.handler.JWTAuthenticationEntryPoint;
import cn.springboot.osbulkparts.common.security.auth.provider.JWTAuthenticationProvider;
import cn.springboot.osbulkparts.common.security.config.JwtTokenProperty;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTAuthenticationProvider jwtAuthenticationProvider;
	
	@Autowired
    private JwtTokenProperty jwtTokenProperty;

	/**
	 * 加密密码的
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				// 需要验证了的用户才能访问
				.antMatchers("/**").authenticated()
//				.antMatchers("/sys1/**").authenticated()
//				.antMatchers("/portal1/**").authenticated()
				// 其他都放行了
				.anyRequest().permitAll().and()
				// 这两个加验证时放开
				.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtTokenProperty))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtTokenProperty))
				// 不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 加一句这个
				.exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());
	}

}
