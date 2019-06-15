package cn.springboot.osbulkparts.common.entity;

import lombok.Data;

@Data
public class CommonEntity {

	private int[] idsInt;
	
	private String[] idsStr;
	
	private boolean toLocked;
}
