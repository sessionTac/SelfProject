package cn.springboot.osbulkparts.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cn.springboot.osbulkparts.common.OSLanguage;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonBusinessException;
import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonMethods;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.common.utils.excel.CommonExcelConfig;
import cn.springboot.osbulkparts.common.utils.excel.CommonPoiReadUtil;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TStockInfoDao;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TStockInfoEntity;
import cn.springboot.osbulkparts.service.StockInfoService;
@Service
public class StockInfoServiceImpl implements StockInfoService {

	@Autowired
	private TDictDataDao tDictDataDao;
	
	@Autowired
	private TStockInfoDao tstockInfoDao;
	
	@Autowired
	private MRoleInfoDao mroleInfoDao;
	
    @Autowired
    private I18nMessageBean messageBean;
    
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(String lang, Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
		try {
			Map<String,List<TDictDataEntity>> map = new HashMap<>();
			TDictDataEntity tDictDataEntity = new TDictDataEntity();
			tDictDataEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
			tDictDataEntity.setDictTypeCode("mattertype");
			map.put("materialCategorys",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			result.setResult(map);
		} catch (Exception e) {
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> getStockInfoList(TStockInfoEntity stockInfoEntity, int pageNumber, int pageSize,Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<TStockInfoEntity> result = new CommonResultInfo<TStockInfoEntity>();
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),stockInfoEntity.getLanguageFlag());
		try {
			if(principal.getUserType()!=1) {
				stockInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
			}
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<TStockInfoEntity> pageInfo = new PageInfo<>(
					tstockInfoDao.selectByPrimaryKey(stockInfoEntity));
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResultInfo(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TStockInfoEntity> getStockInfoInfo(TStockInfoEntity stockInfoEntity,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<TStockInfoEntity> result = new CommonResultInfo<TStockInfoEntity>();
		try {
			List<TStockInfoEntity> resultList = tstockInfoDao.selectByPrimaryKey(stockInfoEntity);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			if(resultList.size()>0) {
				result.setResult(resultList.get(0));
			}
			else {
				result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.info.empty"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> insertStockInfo(TStockInfoEntity stockInfoEntity, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TStockInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),stockInfoEntity.getLanguageFlag());
		try {
			String dictUUID = CommonSqlUtils.getUUID32();
			stockInfoEntity.setId(dictUUID);
			stockInfoEntity.setCreateUser(principal.getUserName());
			stockInfoEntity.setIsDelete(0);
			stockInfoEntity.setVersion(1);
			stockInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
			int returnInt = tstockInfoDao.insertSelective(stockInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.STOCK_DATA.getTypeName(locale)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> updateStockInfo(TStockInfoEntity stockInfoEntity, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TStockInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			TStockInfoEntity stockInfoEntityVersion= new TStockInfoEntity();
			stockInfoEntityVersion.setVersion(stockInfoEntity.getVersion());
			stockInfoEntityVersion.setId(stockInfoEntity.getId());
			//校验 version 排他  (根据id和version)
			List<TStockInfoEntity> checkListVersion=tstockInfoDao.checkingAndVersion(stockInfoEntityVersion);
			if(checkListVersion.size()==1){
				stockInfoEntity.setUpdateUser(principal.getUserName());
				stockInfoEntity.setVersion(stockInfoEntity.getVersion()+1);
				stockInfoEntity.setIsDelete(0);
				int returnInt = tstockInfoDao.updateByPrimaryKey(stockInfoEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.STOCK_DATA.getTypeName(locale)));
				}
			}else {
				result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.STOCK_DATA.getTypeName(locale)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> deleteStockInfo(String stockId, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TStockInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			TStockInfoEntity stockInfoEntity = new TStockInfoEntity();
			stockInfoEntity.setId(stockId);
			stockInfoEntity.setUpdateUser(principal.getUserName());
			stockInfoEntity.setIsDelete(1);
			int returnInt = tstockInfoDao.updateByPrimaryKey(stockInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.STOCK_DATA.getTypeName(locale)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> deleteBatchByIds(CommonEntity commonEntity, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TStockInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			int returnInt = tstockInfoDao.deleteBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_DELETE.getTypeName(locale));
			if (returnInt > 0) {
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.STOCK_DATA.getTypeName(locale)));
			}
			result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings({ "finally"})
	@Override
	public CommonResultInfo<?> importExcel(MultipartFile excleFile, HttpServletRequest request, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TStockInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
        	
        	List<TStockInfoEntity> stockInfoParams = resolvExcelToDb(excleFile,auth,locale);
        	if(stockInfoParams.size() == 0) {
        		result.setMessage(messageBean.getMessage("common.excel.error"));
        	}else {
        		tstockInfoDao.deleteBatachByNo(stockInfoParams);
        		
        		int resultInt = tstockInfoDao.importExcelData(stockInfoParams);
            	if(resultInt >0) {
            		result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
            		result.setMessage(messageBean.getMessage("common.excel.success"));
            	}
        	}
        } catch (NullPointerException se) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(se.getMessage().toString());
        } catch (Exception e) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
        } finally {
            return result;
        }
	}

	@Override
	public ResponseEntity<byte[]> downloadExcel(TStockInfoEntity stockInfoEntity,Locale locale) {
		messageBean.setLocale(null,null,locale);
		String[] title = messageBean.getMessage("file.title.stock").split(",");
		List<TStockInfoEntity> resultList = tstockInfoDao.selectByPrimaryKey(stockInfoEntity);
		ResponseEntity<byte[]> result = educeExcel(title,resultList,locale);
		return result;
	}

	/****Private Methods****/
	/**
	 * 
	 * @param titles 第一列名
	 * @param list 向单元格插入数据
	 * @return
	 */
	private ResponseEntity<byte[]> educeExcel(String[] titles,List<TStockInfoEntity> list,Locale locale){
		messageBean.setLocale(null,null,locale);
		ResponseEntity<byte[]> response = null;
		//创建Excel对象
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();
		//输出Excel文件  
		try {
			//创建工作表单
			HSSFSheet sheet = workbook.createSheet(messageBean.getMessage("file.name.stock"));  
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
				TStockInfoEntity example = list.get(i);
				//物料号
				row.createCell(0).setCellValue(example.getMaterialCode());
				//物料类别
				row.createCell(1).setCellValue(example.getDictMaterialCategory().getName());
				//物料中文描述
				row.createCell(2).setCellValue(example.getMaterialDescCn());
				//物料英文描述
				row.createCell(3).setCellValue(example.getMaterialDescEn());
				//物料俄文描述
				row.createCell(4).setCellValue(example.getMaterialDescRn());
				//库存数量
				row.createCell(5).setCellValue(example.getStockAmount().toString());
			}
			workbook.write(os);
			workbook.close();
			String filename_enc = UriUtils.encode(messageBean.getMessage("file.name.stock"), "UTF-8");
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
	
	/***
	 * Excel文件解析
	 * @throws Exception 
	 */
	private List<TStockInfoEntity> resolvExcelToDb(MultipartFile excleFile,Authentication auth,Locale locale) throws NullPointerException,Exception{
		messageBean.setLocale(null,null,locale);
		try {
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),"");
			List<TStockInfoEntity> insertResultLst = new ArrayList<TStockInfoEntity>();
			
			File file = CommonPoiReadUtil.MultipartFileToFile(excleFile);
			CommonPoiReadUtil poiUtil=new CommonPoiReadUtil(file,true);
			CommonExcelConfig config=new CommonExcelConfig();
			
			//设置读取参数
			poiUtil.setReplenishNull(false);
			poiUtil.setPrintLog(false);
			
			// 必须项[全部]
			config.setNotNullColumn(new int[]{1,2,3,4,5,6});
			// 不需要快读
			config.setBriefRead(false);
			config.setStartRow(2);
			
			List<Map<String, Object>> dataLst=poiUtil.readExcel(config);
			//文件解析后数据放入实体对象List
			for(Map<String, Object> mapData:dataLst) {
				TStockInfoEntity stockInfoEntity = new TStockInfoEntity();
				// 主键ID
				stockInfoEntity.setId(CommonSqlUtils.getUUID32());
				// 物料号
				stockInfoEntity.setMaterialCode((String)mapData.get("物料号"));
				// 物料类别
				String materCateVle = getFromDictDataByName(
						(String)mapData.get("物料类别"),"mattertype","物料类别",locale);
				stockInfoEntity.setMaterialCategory(materCateVle);
				// 物料中文描述
				stockInfoEntity.setMaterialDescCn((String)mapData.get("物料中文描述"));
				// 物料英文描述
				stockInfoEntity.setMaterialDescEn((String)mapData.get("物料英文描述"));
				// 物料俄文描述
				stockInfoEntity.setMaterialDescRn((String)mapData.get("物料俄文描述"));
				// 库存数量
				String stockAmount = (String)mapData.get("库存数量");
				stockInfoEntity.setStockAmount(
						CommonMethods.changeToBigdecimal(stockAmount.trim()));
				// 数据所属
				stockInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
				// 创建者
				stockInfoEntity.setCreateUser(principal.getUserName());
				stockInfoEntity.setIsDelete(0);
				stockInfoEntity.setVersion(1);
				insertResultLst.add(stockInfoEntity);
			}
			return insertResultLst;
		}
		catch(NullPointerException se) {
			throw se;
		}catch(Exception e) {
			throw e;
		}
	}
	
	/***
	 * 根据Excel中的文字内容匹配字典数据，取得对应的值
	 * @param nameValue
	 * @param dictType
	 * @return
	 */
	private String getFromDictDataByName(String nameValue,String dictType,String dictTypeCn,Locale locale) {
		messageBean.setLocale(null,null,locale);
		TDictDataEntity dictDataParam = new TDictDataEntity();
		try {
			dictDataParam.setName(nameValue);
			dictDataParam.setDictTypeCode(dictType);
			List<TDictDataEntity> dictDataLst = tDictDataDao.selectByPrimaryKey(dictDataParam);
			if(dictDataLst.size()>0) {
				return dictDataLst.get(0).getValue();
			}else {
				throw new NullPointerException(messageBean.getMessage("common.dict.emptyerror", dictTypeCn));
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
}
