package cn.springboot.osbulkparts.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import cn.springboot.osbulkparts.dao.basedata.MMaterialInfoDao;
import cn.springboot.osbulkparts.dao.basedata.MSupplierInfoDao;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.MaterialDataService;

@Service
public class MaterialDataServiceImpl implements MaterialDataService{

	@Autowired
	private MMaterialInfoDao mmaterialInfoDao;
	
	@Autowired
	private MRoleInfoDao mroleInfoDao;
	
	@Autowired
	private MSupplierInfoDao msupplierInfoDao;
	
	@Autowired
	private TDictDataDao tDictDataDao;
	
    @Autowired
    private I18nMessageBean messageBean;
    
    private int version;
    
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(){
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
		try {
			Map<String,List<TDictDataEntity>> map = new HashMap<>();
			TDictDataEntity tDictDataEntity = new TDictDataEntity();
			tDictDataEntity.setDictTypeCode("currency");
			map.put("currencys",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			tDictDataEntity.setDictTypeCode("mattertype");
			map.put("materialCategorys",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			tDictDataEntity.setDictTypeCode("unit");
			map.put("materialUnits",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			map.put("materialRelationUnits",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			tDictDataEntity.setDictTypeCode("supplyMode");
			map.put("materialSupplyModes",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			tDictDataEntity.setDictTypeCode("minpackageType");
			map.put("materialMinpackageTypes",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			tDictDataEntity.setDictTypeCode("converRelation");
			map.put("materialConverRelation",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
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
	public CommonResultInfo<?> importExcel(MultipartFile excleFile, HttpServletRequest request, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
        	int resultInt = 0;
        	Map<String,List<MMaterialInfoEntity>> materialInfoParams = resolvExcelToDb(excleFile,auth);
        	if(materialInfoParams.size() == 0) {
        		result.setMessage(messageBean.getMessage("common.excel.error"));
        	}else {
        		List<MMaterialInfoEntity> paramList = new ArrayList<MMaterialInfoEntity>();
        		if(materialInfoParams.containsKey("insertList")) {
        			paramList = materialInfoParams.get("insertList");
        			if(paramList.size()>0) {resultInt = resultInt + mmaterialInfoDao.insertList(paramList);}
        		}
        		if(materialInfoParams.containsKey("updateList")) {
        			paramList = materialInfoParams.get("updateList");
        			if(paramList.size()>0) {resultInt = resultInt + mmaterialInfoDao.updateList(paramList);}
        		}
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
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<MMaterialInfoEntity> selectMaterialInfoList(
			MMaterialInfoEntity materialInfoEntity, int pageNumber, int pageSize,Authentication auth) {
		CommonResultInfo<MMaterialInfoEntity> result = new CommonResultInfo<MMaterialInfoEntity>();
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
		try {
			materialInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<MMaterialInfoEntity> pageInfo = new PageInfo<>(
					mmaterialInfoDao.selectByPrimaryKey(materialInfoEntity));
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
	public CommonResultInfo<MMaterialInfoEntity> selectMaterialInfo(MMaterialInfoEntity materialInfoEntity){
		CommonResultInfo<MMaterialInfoEntity> result = new CommonResultInfo<MMaterialInfoEntity>();
		try {
			List<MMaterialInfoEntity> resultList = mmaterialInfoDao.selectByPrimaryKey(materialInfoEntity);
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
	public CommonResultInfo<?> insertMaterialInfo(MMaterialInfoEntity materialInfoEntity,Authentication auth){
		CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
		try {
			String dictUUID = CommonSqlUtils.getUUID32();
			materialInfoEntity.setMaterialInfoId(dictUUID);
			materialInfoEntity.setCreateUser(principal.getUserName());
			materialInfoEntity.setIsDelete(0);
			materialInfoEntity.setIsLocked(0);
			materialInfoEntity.setVersion(1);
			materialInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
			int returnInt = mmaterialInfoDao.insertSelective(materialInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.MATERIAL_DATA.getTypeName()));
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
	public CommonResultInfo<?> updateMaterialInfo(MMaterialInfoEntity materialInfoEntity,Authentication auth){
		CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			MMaterialInfoEntity materialInfoEntityVersion= new MMaterialInfoEntity();
			materialInfoEntityVersion.setVersion(materialInfoEntity.getVersion());
			materialInfoEntityVersion.setMaterialInfoId(materialInfoEntity.getMaterialInfoId());
			//校验 version 排他  (根据id和version)
			List<MMaterialInfoEntity> checkListVersion=mmaterialInfoDao.checkingAndVersion(materialInfoEntityVersion);
			if(checkListVersion.size()==1){
				materialInfoEntity.setUpdateUser(principal.getUserName());
				materialInfoEntity.setVersion(materialInfoEntity.getVersion()+1);
				materialInfoEntity.setIsDelete(0);
				int returnInt = mmaterialInfoDao.updateByPrimaryKey(materialInfoEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.MATERIAL_DATA.getTypeName()));
				}
			}else {
				result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.MATERIAL_DATA.getTypeName()));
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
	public CommonResultInfo<?> deleteMaterialInfo(String materialId,Authentication auth){
		CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			MMaterialInfoEntity materialInfoEntity = new MMaterialInfoEntity();
			materialInfoEntity.setMaterialInfoId(materialId);
			materialInfoEntity.setUpdateUser(principal.getUserName());
			materialInfoEntity.setIsDelete(1);
			int returnInt = mmaterialInfoDao.updateByPrimaryKey(materialInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.MATERIAL_DATA.getTypeName()));
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
	public CommonResultInfo<?> lockMaterialInfo(CommonEntity commonEntity,Authentication auth){
		CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			if(commonEntity.isToLocked()) {
				int returnInt = mmaterialInfoDao.lockedData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.LOCK_TRUE.getTypeName());
				if (returnInt > 0) {
					result.setMessage(messageBean.getMessage("common.locked.success", CommonConstantEnum.MATERIAL_DATA.getTypeName()));
				}
			}
			else {
				int returnInt = mmaterialInfoDao.lockedData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.LOCK_FALSE.getTypeName());
				if (returnInt > 0) {
					result.setMessage(messageBean.getMessage("common.unlocked.success", CommonConstantEnum.MATERIAL_DATA.getTypeName()));
				}
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
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> deleteBatchMaterialInfo(CommonEntity commonEntity,Authentication auth){
		CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			int returnInt = mmaterialInfoDao.deleteBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_DELETE.getTypeName());
			if (returnInt > 0) {
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.MATERIAL_DATA.getTypeName()));
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
	
	@Override
	public ResponseEntity<byte[]> downloadExcel(MMaterialInfoEntity materialInfoEntity) {
		String[] title = messageBean.getMessage("file.title.material").split(",");
		List<MMaterialInfoEntity> resultList = mmaterialInfoDao.selectByPrimaryKey(materialInfoEntity);
		ResponseEntity<byte[]> result = educeExcel(title,resultList);
		return result;
	}
	
	/****Private Methods****/
	/**
	 * 
	 * @param titles 第一列名
	 * @param list 向单元格插入数据
	 * @return
	 */
	private ResponseEntity<byte[]> educeExcel(String[] titles,List<MMaterialInfoEntity> list){
		ResponseEntity<byte[]> response = null;
		//创建Excel对象
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();
		//输出Excel文件  
		try {
			//创建工作表单
			HSSFSheet sheet = workbook.createSheet(messageBean.getMessage("file.name.material"));  
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
				MMaterialInfoEntity example = list.get(i);
				//成品型号
				row.createCell(0).setCellValue(example.getMaterialOrderCode());
				//成品型号描述
				row.createCell(1).setCellValue(example.getMaterialOrderCodeDesc());
				//子件型号
				row.createCell(2).setCellValue(example.getMaterialCode());
				//物料CKD号
				row.createCell(3).setCellValue(example.getMaterialCkdCode());
				//物料类别
				row.createCell(4).setCellValue(example.getDictMaterialCategory().getName());
				//物料中文描述
				row.createCell(5).setCellValue(example.getMaterialDescCn());
				//物料英文描述
				row.createCell(6).setCellValue(example.getMaterialDescEn());
				//物料俄文描述
				row.createCell(7).setCellValue(example.getMaterialDescRn());
				//单位
				row.createCell(8).setCellValue(example.getDictMaterialUnit().getName());
				//币种
				row.createCell(9).setCellValue(example.getDictMaterialCurrency().getName());
				//换算关系
				row.createCell(10).setCellValue(example.getMaterialRelation());
				//换算后单位
				row.createCell(11).setCellValue(example.getDictMaterialRelationUnit().getName());
				//含税单价
				row.createCell(12).setCellValue(
						example.getMaterialTaxPrice() != null?example.getMaterialVatPrice().toString():"");
				//不含税单价
				row.createCell(13).setCellValue(
						example.getMaterialVatPrice()!=null?example.getMaterialVatPrice().toString():"");
				//不含税单价
				row.createCell(14).setCellValue(
						example.getTax()!=null?example.getTax().toString():"");
				//最小包装数量
				row.createCell(15).setCellValue(
						example.getMaterialMinpackageAmt() != null?example.getMaterialMinpackageAmt().toString():"");
				//代理费率
				row.createCell(16).setCellValue(
						example.getMaterialRate() != null?example.getMaterialRate().toString():"");
				//HS海关编码
				row.createCell(17).setCellValue(example.getHsNo());
				//供应商编码
				row.createCell(18).setCellValue(example.getSupplierCode());
				//工厂号
				row.createCell(19).setCellValue(example.getFactoryCode());
				//长
				row.createCell(20).setCellValue(
						example.getLength()!=null?example.getLength().toString():"");
				//宽
				row.createCell(21).setCellValue(
						example.getWidth()!=null?example.getWidth().toString():"");
				//高
				row.createCell(22).setCellValue(
						example.getHeight()!=null?example.getHeight().toString():"");

			}
			workbook.write(os);
			workbook.close();
			String filename_enc = UriUtils.encode(messageBean.getMessage("file.name.material"), "UTF-8");
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
	private Map<String,List<MMaterialInfoEntity>> resolvExcelToDb(MultipartFile excleFile,Authentication auth) throws NullPointerException,Exception{
		try {
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
			List<MMaterialInfoEntity> insertResultLst = new ArrayList<MMaterialInfoEntity>();
			List<MMaterialInfoEntity> updateResultLst = new ArrayList<MMaterialInfoEntity>();
			
			Map<String,List<MMaterialInfoEntity>> returnMap = new HashMap<String,List<MMaterialInfoEntity>>();
			
			File file = CommonPoiReadUtil.MultipartFileToFile(excleFile);
			CommonPoiReadUtil poiUtil=new CommonPoiReadUtil(file,true);
			CommonExcelConfig config=new CommonExcelConfig();
			
			//设置读取参数
			poiUtil.setReplenishNull(false);
			poiUtil.setPrintLog(false);
			
			// 必须项[成品型号,子件型号]
			config.setNotNullColumn(new int[]{1,2});
			// 不需要快读
			config.setBriefRead(false);
			
			List<Map<String, Object>> dataLst=poiUtil.readExcel(config);
			//文件解析后数据放入实体对象List
			for(Map<String, Object> mapData:dataLst) {
				MMaterialInfoEntity mmaterialInfoEntity = new MMaterialInfoEntity();
				// 主键ID
				mmaterialInfoEntity.setMaterialInfoId(CommonSqlUtils.getUUID32());
				// 订单型号，成品型号
				mmaterialInfoEntity.setMaterialOrderCode((String)mapData.get("成品型号"));
				// 物料HNR号
//				mmaterialInfoEntity.setMaterialHnrCode();
				// 物料CKD号
				mmaterialInfoEntity.setMaterialCkdCode((String)mapData.get("物料CKD号"));
				// 物料专用号，子件型号
				mmaterialInfoEntity.setMaterialCode((String)mapData.get("子件型号"));
				// 物料类别
				String materCateVle = getFromDictDataByName(
						(String)mapData.get("物料类别"),"mattertype","物料类别");
				mmaterialInfoEntity.setMaterialCategory(materCateVle);
				// 物料中文描述
				mmaterialInfoEntity.setMaterialDescCn((String)mapData.get("物料中文描述"));
				// 物料英文描述
				mmaterialInfoEntity.setMaterialDescEn((String)mapData.get("物料英文描述"));
				// 物料俄文描述
				mmaterialInfoEntity.setMaterialDescRn((String)mapData.get("物料俄文描述"));
				// HS海关编码
				mmaterialInfoEntity.setHsNo((String)mapData.get("HS海关编码"));
				// 供应商编码
				MSupplierInfoEntity supplierInfo = msupplierInfoDao.selectByCode((String)mapData.get("供应商编码"));
				if(supplierInfo == null) {
					throw new NullPointerException(messageBean.getMessage("common.check.isExist", "供应商信息"));
				}
				mmaterialInfoEntity.setSupplierCode((String)mapData.get("供应商编码"));
				// 单位
				String unitVle = getFromDictDataByName(
						(String)mapData.get("单位"),"unit","单位");
				mmaterialInfoEntity.setMaterialUnit(unitVle);
				// 换算关系
				mmaterialInfoEntity.setMaterialRelation((String)mapData.get("换算关系"));
				// 换算后单位
				String relationUnitVle = getFromDictDataByName(
						(String)mapData.get("换算后单位"),"unit","换算后单位");
				mmaterialInfoEntity.setMaterialRelationUnit(relationUnitVle);
				// 最小包装数量
				String minPackageAmt = (String)mapData.get("最小包装数量");
				mmaterialInfoEntity.setMaterialMinpackageAmt(
						CommonMethods.changeToDouble(minPackageAmt.trim()));
				// 不含税单价
				String materialTaxPrice = (String)mapData.get("不含税单价");
				mmaterialInfoEntity.setMaterialTaxPrice(
						CommonMethods.changeToBigdecimal(materialTaxPrice.trim()));
				// 含税单价
				String materialVatPrice = (String)mapData.get("含税单价");
				mmaterialInfoEntity.setMaterialVatPrice(
						CommonMethods.changeToBigdecimal(materialVatPrice.trim()));
				// 含税单价
				String tax = (String)mapData.get("税率");
				mmaterialInfoEntity.setMaterialVatPrice(
						CommonMethods.changeToBigdecimal(tax.trim()));
				// 代理费率
				String materialRate = (String)mapData.get("代理费率");
				mmaterialInfoEntity.setMaterialRate(
						CommonMethods.changeToBigdecimal(materialRate.trim()));
				// 币种
				String currencyVle = getFromDictDataByName(
						(String)mapData.get("币种"),"currency","币种");
				mmaterialInfoEntity.setMaterialCurrency(currencyVle);
				// 单价
//				mmaterialInfoEntity.setMaterialPrice();
				// 分级BOM编码
//				mmaterialInfoEntity.setLevelBomCode();
				// 物料供货模式分类标识
//				mmaterialInfoEntity.setMaterialSupplyMode();
				// 工厂号
				mmaterialInfoEntity.setFactoryCode((String)mapData.get("工厂号"));
				// 长
				String length = (String)mapData.get("长");
				mmaterialInfoEntity.setLength(
						CommonMethods.changeToBigdecimal(length.trim()));
				// 宽
				String width = (String)mapData.get("宽");
				mmaterialInfoEntity.setWidth(
						CommonMethods.changeToBigdecimal(width.trim()));
				// 高
				String height = (String)mapData.get("高");
				mmaterialInfoEntity.setMaterialRate(
						CommonMethods.changeToBigdecimal(height.trim()));
				// 数据所属
				mmaterialInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
				// 成品型号和子件型号组合判定是否存在，存在时更新，不存在时插入
				if(isExist(mmaterialInfoEntity.getMaterialOrderCode(),mmaterialInfoEntity.getMaterialCode())) {
					// 更新者
					mmaterialInfoEntity.setUpdateUser(principal.getUserName());
					mmaterialInfoEntity.setVersion(version+1);
					mmaterialInfoEntity.setIsLocked(0);
					updateResultLst.add(mmaterialInfoEntity);
				}
				else {
					// 创建者
					mmaterialInfoEntity.setCreateUser(principal.getUserName());
					mmaterialInfoEntity.setIsDelete(0);
					mmaterialInfoEntity.setVersion(1);
					mmaterialInfoEntity.setIsLocked(0);
					insertResultLst.add(mmaterialInfoEntity);
				}
			}
			returnMap.put("insertList", insertResultLst);
			returnMap.put("updateList", updateResultLst);
			return returnMap;
		}
		catch(NullPointerException se) {
			throw se;
		}catch(Exception e) {
			throw e;
		}
	}
	
	/***
	 * 成品型号和子件型号共同判定是否存在
	 * @param materialOrderCode
	 * @param materialCode
	 * @return true/false
	 */
	private boolean isExist(String materialOrderCode,String materialCode) {
		MMaterialInfoEntity materialInfoEntity = new MMaterialInfoEntity();
		materialInfoEntity.setMaterialOrderCode(materialOrderCode);
		materialInfoEntity.setMaterialCode(materialCode);
		List<MMaterialInfoEntity> resultList = mmaterialInfoDao.selectByPrimaryKey(materialInfoEntity);
		if(resultList.size()>0) {
			version = resultList.get(0).getVersion();
			return true;
		}
		return false;
	}
	
	/***
	 * 根据Excel中的文字内容匹配字典数据，取得对应的值
	 * @param nameValue
	 * @param dictType
	 * @return
	 */
	private String getFromDictDataByName(String nameValue,String dictType,String dictTypeCn) {
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
