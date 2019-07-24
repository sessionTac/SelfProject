package osbulkpartsSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cn.springboot.osbulkparts.common.CommonConstantEnum;

public class MethodTest {

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        String dateString = null;
//        try {
        	String str= "20190724120000";
//            date = dateFormat.parse("20190724120000");
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHssmm");
//            dateString = formatter.format(date);
            String returnRes = null;
            returnRes = str.substring(0,4)+"/"+str.substring(4,6)+"/"+str.substring(6,8);
            System.out.println(returnRes);
//        } catch (ParseException e) {
//        }
        
//    	Date now = new Date();
//    	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//设置日期格式
//        String dd = df.format(now);// new Date()为获取当前系统时间
//        System.out.println(dd);
//        String password = "osadmin@123";
//        encoder.encode(password);
//		System.out.println(encoder.encode(password));
		
		
	}
}
