package cn.springboot.osbulkparts.common.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class CommonSqlUtils {
	/**
	 * Auto Create UUID 32
	 * 
	 */
	public static String getUUID32(){
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
	/**
	 * 字符串是否非null且trim后非空字符串<br>
	 * 
	 * 示例：<code>&lt;if test="@com.jxre.hbase.utils.SqlUtils@isNotBlank(username)"&gt;</code>
	 * @param cs
	 * @return
	 */
	public static boolean isNotBlank(CharSequence cs) {
		return StringUtils.isNotBlank(cs);
	}
	
	/**
	 * 包装、转义模糊查询的内容成为安全的like参数，在mapper的bind中使用。<br>
	 * 
	 * 示例：<code>&lt;bind name="username_like" value="@com.jxre.hbase.utils.SqlUtils@toLike(username)" /&gt;</code>
	 * @param pat
	 * @return
	 */
	public static String toLike(String pat) {

		StringBuilder sb = new StringBuilder();

		sb.append("%");
		sb.append(pat.trim().replace("\\","\\\\").replaceAll("[%|_|'|\"]","\\\\$0"));
		sb.append("%");

		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(toLike("qq\\q%qq"));
	}
}
