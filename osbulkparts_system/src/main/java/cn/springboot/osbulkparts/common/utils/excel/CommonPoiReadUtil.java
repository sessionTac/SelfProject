package cn.springboot.osbulkparts.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;

public class CommonPoiReadUtil {

	/**
	 * 是否打印日志
	 */
	private boolean isPrintLog = false;

	/**
	 * excel文件Workbook
	 */
	private Workbook wb;
	/**
	 * 是否补充空单元格为空字符串
	 */
	private boolean isReplenishNull = true;

	public CommonPoiReadUtil(String excelUrl) throws Exception {
		readExcel(excelUrl);
		printLog("【初始化】：读取excel文件地址:" + excelUrl);
		printLog("【初始化】：初始化成功");
	}

	public CommonPoiReadUtil(File excelFile) throws Exception {
		if (excelFile == null) {
			throw new NullPointerException("传入的File为空");
		}
		printLog("【初始化】：读取excel文件地址:" + excelFile.getPath());
		readExcel(excelFile);
		printLog("【初始化】：初始化成功");
	}

	public CommonPoiReadUtil(String excelUrl, Boolean isPrintLog) throws Exception {
		this(excelUrl);
		this.isPrintLog = isPrintLog;
	}

	public CommonPoiReadUtil(File excelFile, Boolean isPrintLog) throws Exception {
		this(excelFile);
		this.isPrintLog = isPrintLog;
	}

//	public PoiReadUtil(MultipartFile excelFile) throws Exception {
//		if(excelFile ==null) {
//			throw new NullPointerException("传入的MultipartFile为空");
//		}
//		String  fileName = excelFile.getOriginalFilename();
//		printLog("【初始化】：读取excel文件名字:" + fileName);
//		readExcel(fileName.substring(fileName.lastIndexOf(".")), excelFile.getInputStream());
//		printLog("【初始化】：初始化成功");
//	}
//	public PoiReadUtil(MultipartFile excelFile, Boolean isPrintLog) throws Exception {
//		this.isPrintLog = isPrintLog;
//		this(excelFile);
//	}

	public List<Map<String, Object>> readExcel() throws Exception {
		return readExcel(new CommonExcelConfig());
	}
	
	/***
	 * 将MultipartFile类型的文件数据读取为File
	 * @param multiFile
	 * @return
	 * @throws Exception
	 */
	public static File MultipartFileToFile(MultipartFile multiFile) throws IOException {
		try {
	        // 获取文件名
			String fileName = multiFile.getOriginalFilename();
			// 获取文件后缀
			String prefix = fileName.substring(fileName.lastIndexOf("."));
			// 用当前时间作为文件名，防止生成的临时文件重复
			File file = File.createTempFile(System.currentTimeMillis() + "", prefix);
	        multiFile.transferTo(file);
	        return file;
		}
		catch(IOException e) {
			throw e;
		}
	}
	
	/**
	 * 读取excel表格内容
	 * 
	 * @param config 读取配置
	 * @return 表格内容
	 * @throws Exception
	 */
	public List<Map<String, Object>> readExcel(CommonExcelConfig config) throws Exception {
		if (wb != null) {
			// 获取sheet
			Sheet sheet = wb.getSheetAt(config.getSheetNum() - 1);
			// 获取标题行号
			if (config.getTitleRow() == 0) {
				config.setTitleRow(sheet.getFirstRowNum() + 1);
			}
			// 标题行内容
			Row titleRowData = sheet.getRow(config.getTitleRow() - 1);

			// 数据开始读取行
			if (config.getStartRow() == 0) {
				config.setStartRow(config.getTitleRow() + 1);
			}
			// 读取结束行
			if (config.getEndRow() == 0) {
				config.setEndRow(sheet.getLastRowNum() + 1);
			}
			// 获取mapkay,读取表格返回数据map的key
			if (config.getKey() == null) {
				if (titleRowData == null) {
					printLog("【读取准备失败】：指定的标题行[" + config.getTitleRow() + "]为空行，且没有自定义key！");
					throw new NullPointerException("指定的标题行[" + config.getTitleRow() + "]为空行，且没有自定义key！");
				}
				config.setKey(getColumnName(titleRowData, config.getStartColumn(), config.getEndcolumn()));
			}

			// 获取开始列
			if (config.getStartColumn() == 0) {
				if (titleRowData == null) {
					printLog("【读取准备失败】：" + "指定的标题列为空列时，请指定数据开始列!");
					throw new NullPointerException("指定的标题列为空列时,请指定数据开始列!");
				}
				config.setStartColumn(titleRowData.getFirstCellNum() + 1);
			}

			// 获取最大列数
			if (config.getEndcolumn() == 0) {
				if (config.getKey() == null) {
					if (titleRowData == null) {
						printLog("【读取准备失败】：指定的标题行为空行，且没有自定义key！");
						throw new NullPointerException("指定的标题行为空行，且没有自定义key！");
					}
					config.setEndcolumn(titleRowData.getLastCellNum());
				} else {
					config.setEndcolumn(config.getStartColumn() + config.getKey().length - 1);
				}
			}

			printLog("【读取准备完成】：" + config.toString());
			// 执行读取
			if (config.isBriefRead()) {// 是否简略快速读取
				return BriefReadExcelData(config, sheet);
			}
			return readExcelData(config, sheet);
		}
		return null;
	}

	private List<Map<String, Object>> BriefReadExcelData(CommonExcelConfig config, Sheet sheet) {
		String[] mapKey = config.getKey();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = config.getStartRow(); i <= config.getEndRow(); i++) {
			Row currentRow = sheet.getRow(i - 1);
			// 跳过空行
			if (currentRow == null) {
				printLog("【读取跳过】：" + "[第" + i + "行] 空行跳过");
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			int x = 0;
			for (int j = config.getStartColumn(); j < config.getEndcolumn(); j++) {
				Object cellData = getCellFormatValue(currentRow.getCell(j - 1));
				map.put(mapKey[x], cellData);
				x++;
			}
			printLog("【读取内容】：" + "[第" + i + "行]" + map);
			list.add(map);
		}
		printLog("【读取表格完成】：-------------------");
		return list;
	}

	private List<Map<String, Object>> readExcelData(CommonExcelConfig config, Sheet sheet) {
		String[] mapKey = config.getKey();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = config.getStartRow(); i <= config.getEndRow(); i++) {
			// 判断读取是否需要跳过行
			if (config.getSkipOfRow() != null) {
				boolean isSkip = false;
				for (int skipRow : config.getSkipOfRow()) {
					if (skipRow == i) {
						isSkip = true;
						break;
					}
				}
				if (isSkip) {
					printLog("【读取跳过】：" + "[第" + i + "行] 配置跳过读取");
					continue;
				}
			}

			Row currentRow = sheet.getRow(i - 1);
			// 跳过空行
			if (currentRow == null) {
				printLog("【读取跳过】：" + "[第" + i + "行] 空行跳过");
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			int x = 0;
			for (int j = config.getStartColumn(); j <= config.getEndcolumn(); j++) {
				// 判断读取是否需要跳过列
				if (config.getSkipOfColumn() != null) {
					boolean isSkip = false;
					for (int skipColumn : config.getSkipOfColumn()) {
						if (skipColumn == j) {
							isSkip = true;
							break;
						}
					}
					if (isSkip) {
						x++;
						continue;
					}
				}
				Object cellData = getCellFormatValue(currentRow.getCell(j - 1));
				// 判断是否不允许为空
				if (config.getNotNullColumn() != null) {
					for (int notNull : config.getNotNullColumn()) {
						if (notNull == j && StringUtil.isEmpty((String)cellData)) {
							printLog("【读取异常】：" + "[第" + i + "行]" + map);
							throw new NullPointerException("不允许为空的第" + j + "列数据为空 ：第" + i + "行");
						}
					}
				}
				map.put(mapKey[x], cellData);
				x++;
			}
			printLog("【读取内容】：" + "[第" + i + "行]" + map);
			list.add(map);
		}
		if (config.getSkipOfColumn() != null) {
			printLog("【读取跳过】：数据列" + Arrays.toString(config.getSkipOfColumn()) + " 配置跳过读取");
		}
		printLog("【读取表格完成】：-------------------");
		return list;
	}

	/**
	 * 取出这一列数据到一个数组里面
	 * 
	 * @param row   行
	 * @param first 开始列 0则默认第一有数据列
	 * @param last  结束列 0则默认最后有数据列
	 */
	public String[] getColumnName(Row row, int first, int last) {
		if (row == null) {
			return null;
		}
		if (first == 0) {
			first = row.getFirstCellNum() + 1;
		}
		if (last == 0) {
			last = row.getLastCellNum();
		}
		String[] columnName = new String[last - first + 1];

		for (int j = 0; j < columnName.length; j++) {
			Object cellData = getCellFormatValue(row.getCell(j + first - 1));
			// 去除标题列空字段造成的bug，补充为column+列号
			String cellDataString;
			if (cellData == null || "".equals(cellData.toString())) {
				cellDataString = "column" + (j + first);
			} else {
				cellDataString = cellData.toString();
			}

			columnName[j] = cellDataString;
		}

		return columnName;
	}

	/**
	 * 判断是xls还是xlsx，返回不同的Workbook
	 * 
	 * @param filePath 文件名
	 */
	private void readExcel(String filePath) throws Exception {
		if (filePath == null) {
			printLog("【初始化失败】：参数路径为空！");
			throw new Exception("初始化时异常！");
		}
		String extString = filePath.substring(filePath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filePath);
			readExcel(extString, is);
		} catch (Exception e) {
			printLog("【初始化失败】：流创建时异常！");
			throw new Exception("流创建时异常！");
		}
	}

	/**
	 * 判断是xls还是xlsx，创建不同的Workbook
	 * 
	 * @param filePath 文件名
	 */
	private void readExcel(File file) throws Exception {
		if (file == null) {
			printLog("【初始化失败】：参数路径为空！");
			throw new Exception("初始化时异常！");
		}
		String fileName = file.getName();
		String extString = fileName.substring(fileName.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(file);
			readExcel(extString, is);
		} catch (Exception e) {
			printLog("【初始化失败】：流创建时异常！");
			throw new Exception("流创建时异常！");
		}
	}

	/**
	 * 判断是xls还是xlsx，返回不同的Workbook
	 * 
	 * @param InputStream 文件流
	 */

	public void readExcel(String extString, InputStream fis) throws Exception {
		if (fis == null) {
			return;
		}
		InputStream is = fis;
		try {
			
			if (".xls".equals(extString)) {
//				wb = new HSSFWorkbook(is);
				wb = WorkbookFactory.create(is);
			} else if (".xlsx".equals(extString)) {
//				wb = new XSSFWorkbook(is);
				wb = WorkbookFactory.create(is);
			} else {
				is.close();
				printLog("【初始化失败】：文件后缀异常！需要为.xls或者.xlsx");
				throw new Exception("文件后缀异常！需要为.xls或者.xlsx");
			}
		} catch (Exception e) {
			printLog("【初始化失败】：文件异常！Workbook创建失败！");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 单元格转java类型
	 */
	private Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		// 判断cell类型
		if (cell != null) {
			if (cell.getCellTypeEnum() == CellType.NUMERIC) {
				// 判断单元格是否属于日期格
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = cell.getDateCellValue();
					// 转换为日期格式YYYY-mm-dd
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
					cellValue = df.format(cellValue);
				} else {
					// 解决数据变成科学计数法值
					DecimalFormat df = new DecimalFormat("#.######");
					cellValue = df.format(cell.getNumericCellValue());
				}

			} else if (cell.getCellTypeEnum() == CellType.FORMULA) {
				// 数字
				// 解决数据变成科学计数法值
				DecimalFormat df = new DecimalFormat("0");
				cellValue = df.format(cell.getNumericCellValue());

			} else if (cell.getCellTypeEnum() == CellType.STRING) {
				// 字符串
				cellValue = cell.getRichStringCellValue().getString();
			} else if (cell.getCellTypeEnum() == CellType.BLANK) {
				if (isReplenishNull) {
					cellValue = "";
				}
			}
		} else {
			if (isReplenishNull) {
				cellValue = "";
			}
		}
		return cellValue;
	}

	private void printLog(String log) {
		if (isPrintLog) {
			System.out.println(log);
		}
	}

	/**
	 * 读取一行数据
	 * 
	 * @param sheetNum sheet页码
	 * @param rowNum   行号
	 */
	public List<Object> getRowList(int sheetNum, int rowNum) {
		List<Object> list = new ArrayList<Object>();
		// 获取sheet
		Sheet sheet = wb.getSheetAt(sheetNum - 1);
		Row row = sheet.getRow(rowNum - 1);
		if (row != null) {
			for (Iterator<Cell> cellIterator = row.iterator(); cellIterator.hasNext();) {
				Cell cell = cellIterator.next();
				list.add(getCellFormatValue(cell));
			}
		}
		return list;
	}

	/**
	 * 读取一行数据默认第一页sheet
	 * @param rowNum   行号
	 */
	public List<Object> getRowList(int rowNum) {
		return getRowList(1, rowNum);
	}

	public boolean isPrintLog() {
		return isPrintLog;
	}

	public void setPrintLog(boolean isPrintLog) {
		this.isPrintLog = isPrintLog;
	}

	public boolean isReplenishNull() {
		return isReplenishNull;
	}

	public void setReplenishNull(boolean isReplenishNull) {
		this.isReplenishNull = isReplenishNull;
	}

	public void close() throws IOException {
		this.wb.close();
	}
}
