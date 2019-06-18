package cn.springboot.osbulkparts.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import com.github.pagehelper.PageInfo;

import lombok.Data;

@Data
public class CommonResultInfo<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7602902271941158699L;

	private int code;
	
	private String message;
	
	private String exception;
	
	private PageInfo<T> resultInfo;
	
	private List<T> resultList;
	
	private T result; 
	
	private ResponseEntity<InputStreamResource> response;
	
	public CommonResultInfo() {
	}
	
	@Override
	public String toString() {
		final StringBuilder sber = new StringBuilder("CommonResultInfo{");
		sber.append("code=").append(code);
		sber.append("message=").append(message);
		sber.append("exception=").append(exception);
		if(resultInfo != null && resultInfo.getSize()>0) {
			sber.append("resultInfo=").append(resultInfo.toString());
		}else {
			sber.append("null");
		}
		sber.append('}');
		return sber.toString();
	}
	
}
