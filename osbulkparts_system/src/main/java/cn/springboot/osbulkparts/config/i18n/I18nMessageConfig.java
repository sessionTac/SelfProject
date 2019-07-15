package cn.springboot.osbulkparts.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


@Configuration
public class I18nMessageConfig {

	  /**
     * 配置自己的国际化语言解析器
     * @return
     */
    @Bean
    public I18nMessageBean localeResolver() {
        return new I18nMessageBean();
    }
    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     */
    @Bean
    public WebMvcConfigurer localeInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                localeInterceptor.setParamName("UI_LOCALE");
                registry.addInterceptor(localeInterceptor);
            }
        };
    }


}



