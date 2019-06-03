package cn.springboot.osbulkparts.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
