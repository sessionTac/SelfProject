package cn.springboot.osbulkparts.service.impl;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import com.github.pagehelper.util.StringUtil;

import cn.springboot.osbulkparts.common.CommonBusinessException;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.warehouse.TOrderDetailInfoDao;
import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;
import cn.springboot.osbulkparts.service.ReportOrderDetailService;

@Service
public class ReportOrderDetailServiceImpl implements ReportOrderDetailService{

    @Autowired
    private I18nMessageBean messageBean;
    @Autowired
    private TOrderDetailInfoDao tOrderDetailInfoDao;
    
	@Override
	public ResponseEntity<byte[]> DownloadReportOrderDetail(TOrderDetailInfoEntity tOrderDetailInfoEntity) {
		String[] title = messageBean.getMessage("file.title.report.orderDetail").split(",");
		List<TOrderDetailInfoEntity> resultList = tOrderDetailInfoDao.getReportOrderDetailInfo(tOrderDetailInfoEntity);
		ResponseEntity<byte[]> result = educeExcel(title,resultList);
		return result;
	}
	/****Private Methods****/
	/**
	 * 订单详情报表导出
	 * @param titles 第一列名
	 * @param list 向单元格插入数据
	 * @return
	 */
	private ResponseEntity<byte[]> educeExcel(String[] titles,List<TOrderDetailInfoEntity> list){
		ResponseEntity<byte[]> response = null;
		//创建Excel对象
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();
		//输出Excel文件  
		try {
			//创建工作表单
			HSSFSheet sheet = workbook.createSheet(messageBean.getMessage("file.name.orderDetail"));  
			//创建HSSFRow对象 （行）第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow(0); 	
			row.setHeightInPoints(20);// 设备标题的高度
			//创建HSSFCell对象  （单元格）
			HSSFCell cell=null; 
			//设置第一列单元格的列
			for(int i = 0; i < titles.length; i++){
				cell = row.createCell(i);//列索引从0开始
				cell.setCellValue(titles[i]);//列名1
			};
			CellStyle  style =  workbook.createCellStyle();
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			style.setFillForegroundColor(IndexedColors.RED.getIndex());     
			CellStyle textStyle = workbook.createCellStyle();
			DataFormat format = workbook.createDataFormat();
			textStyle.setDataFormat(format.getFormat("@"));
			//设置单元格的值  
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i+1);
				TOrderDetailInfoEntity example = list.get(i);
				//物料专用号
				row.createCell(0).setCellValue(example.getMaterialCode());
				//物料中文描述
				row.createCell(1).setCellValue(example.getMaterialDescCn());
				//物料英文描述
				row.createCell(2).setCellValue(example.getMaterialDescEn());
				//物料俄文描述
				row.createCell(3).setCellValue(example.getMaterialDescRn());
				//供应商编码
				row.createCell(4).setCellValue(example.getMaterialSupplierNo());
				//供应商中文名称
				row.createCell(5).setCellValue(example.getMSupplierInfoEntity().getSupplierNameCn());
				//订单日期
				row.createCell(6).setCellValue(changeDate2Display(example.getOrderDate(),"yyyy/MM/dd"));
				//单位
				row.createCell(7).setCellValue(example.getDictMaterialUnit()!=null?example.getDictMaterialUnit().getName():"");
				//总计
				row.createCell(8).setCellValue(example.getMaterialAmount()!=null?example.getMaterialAmount().toString():"");
				//换算后单位
				row.createCell(9).setCellValue(example.getDictRelationUnit()!=null?example.getDictRelationUnit().getName():"");
				//换算后数量
				row.createCell(10).setCellValue(example.getMaterialRelationQuantity()!=null?example.getMaterialRelationQuantity().toString():"");
			}
			workbook.write(os);
			workbook.close();
			String filename_enc = UriUtils.encode(messageBean.getMessage("file.name.orderDetail"), "UTF-8");
			response = ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header("Access-Control-Expose-Headers","Content-Disposition")
				.header("Content-Disposition","attachment; filename*=UTF-8''" + filename_enc+".xlsx")
				.body(os.toByteArray());
			return response;
		} catch (Exception e) {
			throw new CommonBusinessException(e.getMessage().toString());
		}
	}
	/**
	 * 日期转换
	 * @return
	 */
	private static String changeDate2Display(String date,String patern) {
		try {
			if(StringUtil.isEmpty(date)) {return "";}
			Date time = new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
	    	SimpleDateFormat df = new SimpleDateFormat(patern);//设置日期格式
	        String dd = df.format(time);// new Date()为获取当前系统时间
	        return dd;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
