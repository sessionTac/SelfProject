package osbulkpartsSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cn.springboot.osbulkparts.common.CommonConstantEnum;

public class MethodTest {

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        String dateString = null;
        try {
            date = dateFormat.parse("2019-07-01");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHssmm");
            dateString = formatter.format(date);
        } catch (ParseException e) {
        }
		System.out.println(dateString);
		
		
	}
}
