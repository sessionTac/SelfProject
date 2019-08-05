package cn.springboot.osbulkparts.common.entity;

import lombok.Data;

@Data
public class CommonEntity {

	private int[] idsInt;
	
	private String[] idsStr;
	
	private boolean toLocked;
	
	private String dateFlag;

	private String[] amouts;

	private String contractNo;
	private String containerNo;
	private String shipNo;
	private String transportation;
	private String billNo;
	
	private String isReset;
}
