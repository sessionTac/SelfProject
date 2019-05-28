package cn.springboot.osbulkparts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 */
@SpringBootApplication
@ServletComponentScan
@EnableSwagger2
@EnableTransactionManagement
public class ApplicationRun
{
    public static void main( String[] args )
    {
        SpringApplication.run(ApplicationRun.class, args);
        System.out.println("OsbulkpartsSystem is Started.");
    }
}
