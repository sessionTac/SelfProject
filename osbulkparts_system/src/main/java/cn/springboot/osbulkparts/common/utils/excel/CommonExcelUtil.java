package cn.springboot.osbulkparts.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * poi读取Excel表的工具类
 */
public class CommonExcelUtil {
	/**
	 * 把一个表格数据取出取出到一个ListMap里面，直接以第一列做key
	 * 
	 * @param excel 数据表
	 * @return 可以当场josn格式用的ListMap
	 * @throws Exception
	 */
	public static List<Map<String, String>> readArrearageExcel(File excel) throws Exception {
		return readArrearageExcel(1, 1, null, excel);
	}

	/**
	 * 把一个表格数据取出取出到一个ListMap里面，默认第一行是标题，可以当json用，key是传进来的数组，value是单元格数据.会忽略第一行
	 * 
	 * @param columns map的key，一般用数据列名
	 * @param excel   数据表
	 * @return 可以当场josn格式用的ListMap
	 * @throws Exception
	 */
	public static List<Map<String, String>> readArrearageExcel(String columns[], File excel) throws Exception {
		return readArrearageExcel(1, 1, columns, excel);
	}

	/**
	 * 
	 * 把一个表格数据取出取出到一个ListMap里面，可以当json用，key是传进来的数组，value是单元格数据.会忽略第一行
	 * 
	 * @param titleLine 标题所在行数
	 * @param columns   map的key，一般用数据列名
	 * @param excel     数据表
	 * @return 可以当场josn格式用的ListMap
	 * @throws Exception
	 */
	public static List<Map<String, String>> readArrearageExcel(int titleLine, String columns[], File excel)
			throws Exception {
		return readArrearageExcel(1, titleLine, columns, excel);
	}

	/**
	 * 
	 * 把一个表格数据取出取出到一个ListMap里面，可以当json用，key是传进来的数组，value是单元格数据.会忽略第一行
	 * 
	 * @param titleLine 标题所在行数
	 * @param sheetNum  sheet页数
	 * @param excel     数据表
	 * @return 可以当场josn格式用的ListMap
	 * @throws Exception
	 */
	public static List<Map<String, String>> readArrearageExcel(int sheetNum, int titleLine, File excel)
			throws Exception {
		return readArrearageExcel(sheetNum, titleLine, null, excel);
	}

	/**
	 * 
	 * 把一个表格数据取出取出到一个ListMap里面，可以当json用，key是titleLine参数的列，value是单元格数据.会忽略第一行
	 * 
	 * @param titleLine 标题所在行数
	 * @param excel     数据表
	 * @return 可以当场josn格式用的ListMap
	 * @throws Exception
	 */
	public static List<Map<String, String>> readArrearageExcel(int titleLine, File excel) throws Exception {
		return readArrearageExcel(1, titleLine, null, excel);
	}

	/**
	 * 把一个表格数据取出取出到一个ListMap里面，可以当json用，key是传进来的数组，value是单元格数据.会忽略第一行
	 * 
	 * @param sheetNum  sheet页数
	 * @param titleLine 标题所在行数
	 * @param columns   map的key，一般用数据列名
	 * @param excel     数据表
	 * @return 可以当场josn格式用的ListMap
	 * @throws Exception
	 */
	public static List<Map<String, String>> readArrearageExcel(int sheetNum, int titleLine, String columns[],
			File excel) throws Exception {
		Workbook wb = null;
		String filePath = excel.getPath();
		System.out.println("读取excel，文件地址:" + filePath);
		wb = readExcel(filePath);
		return readExcel(sheetNum - 1, titleLine - 1, columns, wb);
	}

	/**
	 * MultipartFile版
	 * 
	 * @param titleLine 标题所在行数
	 * @param sheetNum  sheet页数
	 * @param columns   map的key，一般用数据列名
	 * @param excel     数据表MultipartFile
	 * @return 可以当场josn格式用的ListMap
	 * @throws Exception
	 */
//	public static List<Map<String, String>> readArrearageExcel(int sheetNum, int titleLine, String columns[],
//			MultipartFile excel) throws Exception {
//
//		Workbook wb = null;
//
//		// 判断是否为空
//		if (excel.isEmpty()) {
//			System.out.println("文档为空");
//			throw new Exception("MultipartFile为空");
//		}
//
//		String fileName = excel.getOriginalFilename();
//		wb = readExcel(fileName, excel.getInputStream());
//		if (wb != null) {
//			return readExcel(sheetNum - 1, titleLine - 1, columns, wb);
//		}
//
//		return null;
//	}

	public static Workbook setRowColor(Workbook wb, int Sheetnum, int rownum, int colnum1, int colnum2) {
		CellStyle style = wb.createCellStyle();
		Sheet sheet = wb.getSheetAt(Sheetnum);
		Row row = sheet.getRow(rownum);
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		row.getCell(colnum1).setCellStyle(style);
		row.getCell(colnum2).setCellStyle(style);
		// row.setRowStyle(style);
		return wb;
	}

	public static void main(String[] args) throws IOException {
		String filePath = "D:/工作簿1.xlsx";
		System.out.println("读取excel，文件地址:" + filePath);
		Workbook wb = readExcel(filePath);
		wb = setRowColor(wb, 0, 0, 1, 2);
		FileOutputStream fileOut = new FileOutputStream("D:/POIFillAndColorExample.xlsx");
		wb.write(fileOut);
	}

	private static List<Map<String, String>> readExcel(int sheetNum, int titleLine, String columns[], Workbook wb)
			throws Exception {

		Sheet sheet = null;
		Row row = null;
		List<Map<String, String>> list = null;
		String cellData = null;

		if (wb != null) {
			// 用来存放表中数据
			list = new ArrayList<Map<String, String>>();
			// 获取第一个sheet
			sheet = wb.getSheetAt(sheetNum);
			// 获取最大行数
			int rownum = sheet.getPhysicalNumberOfRows() - titleLine;
			// 获取标题列
			row = sheet.getRow(titleLine);

			// 获取最大列数
			int colnum = 0;
			if (columns == null) {
				colnum = row.getPhysicalNumberOfCells();
			} else {
				colnum = columns.length;
			}
			// 取出第一行数组，准备作为key
			String[] ColumnName = null;
			if (columns == null) {
				ColumnName = getColumnName(row);
			} else {
				ColumnName = columns;
			}
			for (int i = titleLine + 1; i < rownum; i++) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j < colnum; j++) {
						cellData = getCellFormatValue(row.getCell(j)).toString(); 
						map.put(ColumnName[j], cellData);
					}
				} else {
					break;
				}
				list.add(map);
			}
		}
		// 遍历解析出来的list
		for (Map<String, String> map : list) {
			for (Entry<String, String> entry : map.entrySet()) {
				System.out.print(entry.getKey() + ":" + entry.getValue() + " | ");
			}
			System.out.println();
			System.out.println("----------------------------------------------------");
		}
		return list;
	}

	/**
	 * 取出excel表里面一列数据到一个数组里面，取标题用的
	 * 
	 * @param excel 表格
	 * 
	 */
	public static String[] getColumnName(File excel) {
		Sheet sheet = null;
		Row row = null;
		String filePath = excel.getPath();
		Workbook wb = readExcel(filePath);
		if (wb != null) {
			sheet = wb.getSheetAt(0);
			row = sheet.getRow(0);
			int colnum = row.getPhysicalNumberOfCells();
			String[] columnName = new String[colnum];
			if (row != null) {
				for (int j = 0; j < colnum; j++) {
					String cellData = getCellFormatValue(row.getCell(j)).toString();
					columnName[j] = cellData;
				}
				return columnName;
			}
		}

		return new String[0];

	}

	/**
	 * 取出这一列数据到一个数组里面
	 * 
	 * @param row 行
	 * 
	 */
	public static String[] getColumnName(Row row) {

		int colnum = row.getPhysicalNumberOfCells();
		String[] columnName = new String[colnum];
		if (row != null) {
			for (int j = 0; j < colnum; j++) {
				String cellData = getCellFormatValue(row.getCell(j)).toString();
				columnName[j] = cellData;
			}

		}
		return columnName;

	}

	/**
	 * 单元格转java类型
	 */
	public static Object getCellFormatValue(Cell cell) {
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
					DecimalFormat df = new DecimalFormat("0");
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
				cellValue = "";
			}
		} else {
			cellValue = "";
		}
		return cellValue;
	}

	/**
	 * 判断是xls还是xlsx，返回不同的Workbook
	 * 
	 * @param filePath 文件名
	 * @return Workbook表
	 */

	public static Workbook readExcel(String filePath) {
		Workbook wb = null;
		if (filePath == null) {
			return null;
		}
		String extString = filePath.substring(filePath.lastIndexOf("."));
		InputStream is = null;

		try {
			is = new FileInputStream(filePath);
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				return wb = new XSSFWorkbook(is);
			} else {
				is.close();
			}

		} catch (Exception e) {
			wb = null;
			return wb;
		}
		return wb;
	}

	/**
	 * 判断是xls还是xlsx，返回不同的Workbook
	 * 
	 * @param filePath 文件名
	 * @return Workbook表
	 */

	public static Workbook readExcel(String name, InputStream fis) {
		Workbook wb = null;
		if (fis == null) {
			return null;
		}
		String extString = name.substring(name.lastIndexOf("."));
		InputStream is = null;
		try {
			is = fis;
			if (".xls".equals(extString)) {
				return wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(extString)) {
				return wb = new XSSFWorkbook(is);
			} else {
				is.close();
			}

		} catch (Exception e) {

			wb = null;
			return wb;
		}
		return wb;
	}

	/**
	 * 修改excel文本框的内容
	 * 
	 * @param name 文本框名字
	 * @param text 要覆盖原的内容的text
	 * @param file excel文件
	 * @return 成功修改的数据数量
	 */
	public static int updateExcelTextbox(String name, String text, File file)
			throws FileNotFoundException, IOException {

		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFDrawing pat = sheet.getDrawingPatriarch();
		List<XSSFShape> shapes = pat.getShapes();
		int i = 0;
		for (XSSFShape shape : shapes) {
			if (shape instanceof XSSFSimpleShape) { // 判断文本框
				XSSFSimpleShape textbox = (XSSFSimpleShape) shape;// 强转成textbox类型
				if (textbox.getShapeName().equals(name)) {
					textbox.setText(text);
					i++;
				}

			}
		}
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		wb.close();
		fos.close();
		return i;
	}

	/**
	 * 数值批量修改excel文本框的内容，文本框名字数组下标要和内容数组对应
	 * 
	 * @param name 文本框名字数组
	 * @param text 内容数组
	 * @param file excel文件
	 * @return 成功修改的数据数量
	 */

	public static int updateExcelTextbox(String[] name, String[] text, File file)
			throws FileNotFoundException, IOException {

		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFDrawing pat = sheet.getDrawingPatriarch();
		List<XSSFShape> shapes = pat.getShapes();
		int i = 0;
		for (int x = 0; x < name.length; x++) {
			for (XSSFShape shape : shapes) {
				if (shape instanceof XSSFSimpleShape) { // 判断文本框
					XSSFSimpleShape textbox = (XSSFSimpleShape) shape;// 强转成textbox类型
					if (textbox.getShapeName().equals(name[x])) {
						textbox.setText(text[x]);
						i++;
					}
				}
			}
		}

		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		wb.close();
		fos.close();
		return i;
	}
}
