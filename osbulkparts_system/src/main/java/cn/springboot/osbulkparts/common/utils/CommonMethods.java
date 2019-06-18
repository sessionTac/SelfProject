package cn.springboot.osbulkparts.common.utils;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.pagehelper.util.StringUtil;

public class CommonMethods {
    
    public static final String separator = File.separator;
    
    public static final String extension_excel = ".xlsx";
    
    public static final String route = "template";
	
	/***
	 * 将excel中的文字转为Bigdecimal格式数据
	 * @param value
	 * @return
	 */
	public static BigDecimal changeToBigdecimal(String value) {
		if(StringUtil.isNotEmpty(value)) {
			BigDecimal returnValue = new BigDecimal(value);
			return returnValue;
		}
		else {
			BigDecimal returnValueNull = new BigDecimal(0);
			return returnValueNull;
		}
	}
	
	/***
	 * 将excel中的文字转换为Double格式数据，文字为空返回0.0
	 * @param value
	 * @return
	 */
	public static Double changeToDouble(String value) {
		if(StringUtil.isNotEmpty(value)) {
			return Double.valueOf(value);
		}
		else {
			return new Double(0);
		}
	}
	
    /**
     * 下载样表
     * @param filePath 文件上级目录
     * @param fileName 文件名
     * @param newName  下载的展示文件名
     * @return 响应
     * @throws Exception 
     */
    public static ResponseEntity<InputStreamResource> download(String filePath, String fileName, String newName) throws Exception {
        String path = null;
        ResponseEntity<InputStreamResource> response = null;
        try {
            path = route + separator  + filePath + separator + fileName + extension_excel;
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();

            response = ResponseEntity.ok()
            		.header("Access-Control-Expose-Headers", "Content-Disposition")
            		.header("Cache-Control", "no-cache, no-store, must-revalidate")
            		.header("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(newName, "UTF-8"))
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(inputStream));
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return response;
    }
}
