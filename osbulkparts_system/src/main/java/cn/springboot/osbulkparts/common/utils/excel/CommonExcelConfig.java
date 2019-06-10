package cn.springboot.osbulkparts.common.utils.excel;

import java.util.Arrays;

/**
 * 读取配置
 *
 */
public class CommonExcelConfig {

	/**
	 * 标题行号(多级标题填最多级，最子级标题跨列请自定义key)，默认第一个有数据的行
	 */
	private int titleRow;
	/**
	 * 数据开始行号，默认是标题行的下一行
	 */
	private int startRow;
	/**
	 * 数据结束行号，默认表最后一行
	 */
	private int endRow;
	/**
	 * 数据开始列号，默认标题行第一个有数据的列
	 */
	private int startColumn;
	/**
	 * 数据结束列号，默认标题行最后一个有数据的列
	 */
	private int endColumn;
	/**
	 * sheet页数，默认最后第一页
	 */
	private int sheetNum = 1;
	/**
	 * 不允许为空的列，设置的列如果为空则抛出空指针
	 */
	private int[] notNullColumn;
	/**
	 * 跳过的行
	 */
	private int[] skipOfRow;
	/**
	 * 跳过的列
	 */
	private int[] skipOfColumn;
	/**
	 * 自定义key 填写自定义key工具会使默认endColumn的长度为此（字段+startColumn-1）
	 */
	private String[] key;
	/**
	 * 简略快速读取模式，不允许行列跳过和不允许为空过滤，设置行列跳过与不允许为空过滤时会关闭
	 */
	private boolean briefRead=true;

	public boolean isBriefRead() {
		return briefRead;
	}

	public void setBriefRead(boolean briefRead) {
		this.briefRead = briefRead;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}

	public int getTitleRow() {
		return titleRow;
	}

	public void setTitleRow(int titleRow) {
		this.titleRow = titleRow;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public int getEndcolumn() {
		return endColumn;
	}

	public void setEndcolumn(int endcolumn) {
		this.endColumn = endcolumn;
	}

	public int getSheetNum() {
		return sheetNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public int[] getNotNullColumn() {
		return notNullColumn;
	}

	public void setNotNullColumn(int[] notNullColumn) {
		this.notNullColumn = notNullColumn;
		this.briefRead=false;
	}

	public int[] getSkipOfRow() {
		return skipOfRow;
	}

	public void setSkipOfRow(int[] skipOfRow) {
		this.skipOfRow = skipOfRow;
		this.briefRead=false;
	}

	public int[] getSkipOfColumn() {
		return skipOfColumn;
	}

	public void setSkipOfColumn(int[] skipOfColumn) {
		this.skipOfColumn = skipOfColumn;
		this.briefRead=false;
	}
	
	@Override
	public String toString() {
		return "配置信息： [标题行号=" + titleRow + ", 数据开始行号=" + startRow + ", 数据结束行号=" + endRow
				+ ", 数据开始列号=" + startColumn + ", 数据结束列号=" + endColumn + ", sheet页码=" + sheetNum
				+ ", 不可为空的列=" + Arrays.toString(notNullColumn) + ", 跳过读取的行=" + Arrays.toString(skipOfRow)
				+ ", 跳过读取的列=" + Arrays.toString(skipOfColumn) + ", 标题=" + Arrays.toString(key) + "]";
	}
}
