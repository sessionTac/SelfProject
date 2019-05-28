package cn.springboot.osbulkparts.config.durid;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * druid数据源状态监控.
 * @author Administrator
 *
 */
  
@WebServlet(urlPatterns="/druid/*",
           initParams={
                   @WebInitParam(name="allow",value="127.0.0.1"),// IP白名单(没有配置或者为空，则允许所有访问)
                    @WebInitParam(name="loginUsername",value="admin"),// 用户名
                    @WebInitParam(name="loginPassword",value="sbsingle"),// 密码
                    @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
           }
)
public class DuridServlet extends StatViewServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
