package cn.springboot.osbulkparts.config.i18n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 国际化封装类
 * 现在使用的语言为中文
 * 后期如需要针对多国，修改此类
 *
 */
public class I18nMessageBean implements LocaleResolver {
	
	@Autowired
	private MessageSource messageSource;
	
	private Locale locale;
	
	public I18nMessageBean() {
//		this.locale = Locale.CHINA;
		this.locale = LocaleContextHolder.getLocale();
	}
	
	/**
	 * 获取国际化信息
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(String code, String ...args) {
		return messageSource.getMessage(code, args, locale);
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		return request.getLocale();
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		this.locale = locale;
		
	}
}
