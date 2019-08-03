package cn.springboot.osbulkparts.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
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
import com.github.pagehelper.util.StringUtil;

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
import cn.springboot.osbulkparts.dao.basedata.TMaterialQuotaDao;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TMaterialQuotaEntity;
import cn.springboot.osbulkparts.service.MaterialDataService;

@Service
public class MaterialDataServiceImpl implements MaterialDataService{

	@Autowired
	private MMaterialInfoDao mmaterialInfoDao;
	
	@Autowired
	private TMaterialQuotaDao tmaterialQuotaDao;
	
	@Autowired
	private MRoleInfoDao mroleInfoDao;
	
	@Autowired
	private MSupplierInfoDao msupplierInfoDao;
	
	@Autowired
	private TDictDataDao tDictDataDao;
	
    @Autowired
    private I18nMessageBean messageBean;
    
    private int version;
    private int quotaVersion;
    
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
			
			tDictDataEntity.setDictTypeCode("minpackageType");
			map.put("materialMinpackageTypes",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			
			tDictDataEntity.setDictTypeCode("converRelation");
			map.put("materialConverRelation",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			
			map.put("versions",change2DictData(mmaterialInfoDao.selectAllVersion()));
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
	
	private List<TDictDataEntity> change2DictData(List<MMaterialInfoEntity>  objectEntity){
		List<TDictDataEntity> resultList = new ArrayList<TDictDataEntity>();
		if(objectEntity.size() == 0) {
			return null;
		}else {
			for(MMaterialInfoEntity object:objectEntity) {
				TDictDataEntity dictData = new TDictDataEntity();
				dictData.setValue(object.getVersion().toString());
				dictData.setName("V"+object.getVersion().toString());
				resultList.add(dictData);
			}
			return resultList;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	@Override
	public CommonResultInfo<?> importExcel(MultipartFile excleFile, HttpServletRequest request, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
        	int resultInt = 0;
        	int quotaResultInt = 0;
        	Map<String,Object> materialInfoParams = resolvExcelToDb(excleFile,auth);
        	if(materialInfoParams.size() == 0) {
        		result.setMessage(messageBean.getMessage("common.excel.error"));
        	}else {
        		List<MMaterialInfoEntity> paramList = new ArrayList<MMaterialInfoEntity>();
        		List<TMaterialQuotaEntity> quotaParamList = new ArrayList<TMaterialQuotaEntity>();
        		if(materialInfoParams.containsKey("insertList")) {
        			paramList = (List<MMaterialInfoEntity>)materialInfoParams.get("insertList");
        			if(paramList.size()>0) {resultInt = resultInt + mmaterialInfoDao.insertList(paramList);}
        		}
        		if(materialInfoParams.containsKey("updateList")) {
        			paramList = (List<MMaterialInfoEntity>)materialInfoParams.get("updateList");
        			if(paramList.size()>0) {resultInt = resultInt + mmaterialInfoDao.insertList(paramList);}
        		}
        		if(materialInfoParams.containsKey("quotaInsertList")) {
        			quotaParamList = (List<TMaterialQuotaEntity>)materialInfoParams.get("quotaInsertList");
        			if(quotaParamList.size()>0) {quotaResultInt = quotaResultInt + tmaterialQuotaDao.insertList(quotaParamList);}
        		}
        		if(materialInfoParams.containsKey("quotaUpdateList")) {
        			quotaParamList = (List<TMaterialQuotaEntity>)materialInfoParams.get("quotaUpdateList");
        			if(quotaParamList.size()>0) {quotaResultInt = quotaResultInt + tmaterialQuotaDao.updateList(quotaParamList);}
        		}
            	if(resultInt >0 && quotaResultInt>0) {
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
			if(principal.getUserType()==4) {
				materialInfoEntity.setFactoryCode(principal.getUserName());
			}
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
		try {
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
			if(isExist(materialInfoEntity.getMaterialOrderCode(),materialInfoEntity.getMaterialCode(),roleInfoEntity.getRoleAt())) {
				materialInfoEntity.setVersion(version +1);
			}else {
				materialInfoEntity.setVersion(1);
			}
			String dictUUID = CommonSqlUtils.getUUID32();
			materialInfoEntity.setMaterialInfoId(dictUUID);
			materialInfoEntity.setCreateUser(principal.getUserName());
			materialInfoEntity.setIsDelete(0);
			materialInfoEntity.setIsLocked(0);
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
				result.setMessage(messageBean.getMessage("bussiness.material.version.update"));
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
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
			int returnInt = mmaterialInfoDao.deleteBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_DELETE.getTypeName());
			if (returnInt > 0) {
				for(int i =0;i<commonEntity.getIdsStr().length;i++) {
					TMaterialQuotaEntity param = new TMaterialQuotaEntity();
					MMaterialInfoEntity rntRes = mmaterialInfoDao.selectInfoById(commonEntity.getIdsStr()[i],roleInfoEntity.getRoleAt());
					param.setMaterialCode(rntRes.getMaterialCode());
					param.setSupplierCode(rntRes.getSupplierCode());
					param.setDataRoleAt(roleInfoEntity.getRoleAt());
					tmaterialQuotaDao.deleteData(param);
				}
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
            HSSFCellStyle numstyle = workbook.createCellStyle();
            numstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.000000"));
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
				//物料专用号
				row.createCell(2).setCellValue(example.getMaterialCode());
				//渠道
				row.createCell(3).setCellValue(example.getDictMaterialCategory().getName());
				//物料中文描述
				row.createCell(4).setCellValue(example.getMaterialDescCn());
				//物料英文描述
				row.createCell(5).setCellValue(example.getMaterialDescEn());
				//物料俄文描述
				row.createCell(6).setCellValue(example.getMaterialDescRn());
				//单耗
				if(example.getMaterialAmount()!=null) {
					row.createCell(7).setCellStyle(numstyle);
					row.createCell(7).setCellType(CellType.NUMERIC);
					row.createCell(7).setCellValue(Double.parseDouble(example.getMaterialAmount().toString()));
				}else {
					row.createCell(7).setCellValue("");
				}
				//单位
				row.createCell(8).setCellValue(example.getDictMaterialUnit().getName());
				//币种
				row.createCell(9).setCellValue(example.getDictMaterialCurrency().getName());
				//换算关系
				row.createCell(10).setCellValue(example.getMaterialRelation());
				//换算后单位
				if(example.getDictMaterialRelationUnit()!=null) {
					row.createCell(11).setCellValue(example.getDictMaterialRelationUnit().getName());
				}else {
					row.createCell(11).setCellValue("");
				}
				//含税单价
				if(example.getMaterialTaxPrice()!=null) {
					row.createCell(12).setCellStyle(numstyle);
					row.createCell(12).setCellType(CellType.NUMERIC);
					row.createCell(12).setCellValue(Double.parseDouble(example.getMaterialTaxPrice().toString()));
				}else {
					row.createCell(12).setCellValue("");
				}
				//不含税单价
				if(example.getMaterialVatPrice()!=null) {
					row.createCell(13).setCellStyle(numstyle);
					row.createCell(13).setCellType(CellType.NUMERIC);
					row.createCell(13).setCellValue(Double.parseDouble(example.getMaterialVatPrice().toString()));
				}else {
					row.createCell(13).setCellValue("");
				}
				//税率
				if(example.getTax()!=null) {
					row.createCell(14).setCellStyle(numstyle);
					row.createCell(14).setCellType(CellType.NUMERIC);
					row.createCell(14).setCellValue(Double.parseDouble(example.getTax().toString()));
				}else {
					row.createCell(14).setCellValue("");
				}
				//最小包装数量
				if(example.getMaterialMinpackageAmt() != null) {
					row.createCell(15).setCellStyle(numstyle);
					row.createCell(15).setCellType(CellType.NUMERIC);
					row.createCell(15).setCellValue(Double.parseDouble(example.getMaterialMinpackageAmt().toString()));
				}else {
					row.createCell(15).setCellValue("");
				}
				//代理费率
				if(example.getMaterialRate() != null) {
					row.createCell(16).setCellStyle(numstyle);
					row.createCell(16).setCellType(CellType.NUMERIC);
					row.createCell(16).setCellValue(Double.parseDouble(example.getMaterialRate().toString()));
				}else {
					row.createCell(16).setCellValue("");
				}
				//HS海关编码
				row.createCell(17).setCellValue(example.getHsNo());
				//供应商编码
				row.createCell(18).setCellValue(example.getSupplierCode());
				//配额
				if(example.getMaterialQuota()!=null && example.getMaterialQuota().getMaterialQuota()!=null) {
					row.createCell(19).setCellStyle(numstyle);
					row.createCell(19).setCellType(CellType.NUMERIC);
					row.createCell(19).setCellValue(Double.parseDouble(example.getMaterialQuota().getMaterialQuota().toString()));	
				}else {
					row.createCell(19).setCellValue("");
				}
				//损耗
				if(example.getMaterialLossRate()!=null) {
					row.createCell(20).setCellStyle(numstyle);
					row.createCell(20).setCellType(CellType.NUMERIC);
					row.createCell(20).setCellValue(Double.parseDouble(example.getMaterialLossRate().toString()));
				}else {
					row.createCell(20).setCellValue("");
				}
				//长
				if(example.getLength()!=null) {
					row.createCell(21).setCellStyle(numstyle);
					row.createCell(21).setCellType(CellType.NUMERIC);
					row.createCell(21).setCellValue(Double.parseDouble(example.getLength().toString()));
				}else {
					row.createCell(21).setCellValue("");
				}
				//宽
				if(example.getWidth()!=null) {
					row.createCell(22).setCellStyle(numstyle);
					row.createCell(22).setCellType(CellType.NUMERIC);
					row.createCell(22).setCellValue(Double.parseDouble(example.getWidth().toString()));
				}else {
					row.createCell(22).setCellValue("");
				}

				//高
				if(example.getHeight()!=null) {
					row.createCell(23).setCellStyle(numstyle);
					row.createCell(23).setCellType(CellType.NUMERIC);
					row.createCell(23).setCellValue(Double.parseDouble(example.getHeight().toString()));
				}else {
					row.createCell(23).setCellValue("");
				}
				// 备注
				row.createCell(24).setCellValue(example.getUserDefined1());

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
	private Map<String,Object> resolvExcelToDb(MultipartFile excleFile,Authentication auth) throws NullPointerException,Exception{
		try {
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
			List<MMaterialInfoEntity> insertResultLst = new ArrayList<MMaterialInfoEntity>();
			List<MMaterialInfoEntity> updateResultLst = new ArrayList<MMaterialInfoEntity>();
			List<TMaterialQuotaEntity> quotaInsertResultLst = new ArrayList<TMaterialQuotaEntity>();
			List<TMaterialQuotaEntity> quotaUpdateResultLst = new ArrayList<TMaterialQuotaEntity>();
			
			Map<String,Object> returnMap = new HashMap<String,Object>();
			
			File file = CommonPoiReadUtil.MultipartFileToFile(excleFile);
			CommonPoiReadUtil poiUtil=new CommonPoiReadUtil(file,true);
			CommonExcelConfig config=new CommonExcelConfig();
			
			//设置读取参数
			poiUtil.setReplenishNull(false);
			poiUtil.setPrintLog(false);
			
			// 必须项[成品型号,子件型号]
			config.setNotNullColumn(new int[]{1,2,3,4,5,8,9,19,20});
			// 不需要快读
			config.setBriefRead(false);
			//默认第一行为实例
			config.setStartRow(2);
			
			List<Map<String, Object>> dataLst=poiUtil.readExcel(config);
			//文件解析后数据放入实体对象List
			for(Map<String, Object> mapData:dataLst) {
				MMaterialInfoEntity mmaterialInfoEntity = new MMaterialInfoEntity();
				TMaterialQuotaEntity materialQuotaEntity = new TMaterialQuotaEntity();
				// 主键ID
				mmaterialInfoEntity.setMaterialInfoId(CommonSqlUtils.getUUID32());
				// 订单型号，成品型号
				mmaterialInfoEntity.setMaterialOrderCode((String)mapData.get("成品型号"));
				mmaterialInfoEntity.setMaterialOrderCodeDesc((String)mapData.get("成品描述"));
				// 物料专用号，子件型号
				mmaterialInfoEntity.setMaterialCode((String)mapData.get("物料专用号"));
				// 物料类别
				String materCateVle = getFromDictDataByName(
						(String)mapData.get("渠道"),"mattertype","渠道");
				mmaterialInfoEntity.setMaterialCategory(materCateVle);
				// 物料中文描述
				mmaterialInfoEntity.setMaterialDescCn((String)mapData.get("物料中文描述"));
				// 物料英文描述
				mmaterialInfoEntity.setMaterialDescEn((String)mapData.get("物料英文描述"));
				// 物料俄文描述
				mmaterialInfoEntity.setMaterialDescRn((String)mapData.get("物料俄文描述"));

				// 单耗
				String materialAmount = (String)mapData.get("单耗");
				mmaterialInfoEntity.setMaterialAmount(
						CommonMethods.changeToBigdecimal(materialAmount.trim()));
				// 单位
				String unitVle = getFromDictDataByName(
						(String)mapData.get("单位"),"unit","单位");
				mmaterialInfoEntity.setMaterialUnit(unitVle);

				// 币种
				if(StringUtil.isNotEmpty((String)mapData.get("币种"))) {
					String currencyVle = getFromDictDataByName(
							(String)mapData.get("币种"),"currency","币种");
					mmaterialInfoEntity.setMaterialCurrency(currencyVle);
				}else {
					mmaterialInfoEntity.setMaterialCurrency(null);
				}
				// 换算关系
				mmaterialInfoEntity.setMaterialRelation((String)mapData.get("换算关系"));
				// 换算后单位
				if(StringUtil.isNotEmpty((String)mapData.get("换算后单位"))) {
					String relationUnitVle = getFromDictDataByName(
							(String)mapData.get("换算后单位"),"unit","换算后单位");
					mmaterialInfoEntity.setMaterialRelationUnit(relationUnitVle);
				}else {
					mmaterialInfoEntity.setMaterialRelationUnit(null);
				}
				// 最小包装数量
				String minPackageAmt = (String)mapData.get("最小包装数量");
				mmaterialInfoEntity.setMaterialMinpackageAmt(minPackageAmt !=null?
						CommonMethods.changeToBigdecimal(minPackageAmt.trim()):null);
				// 含税单价
				String materialVatPrice = (String)mapData.get("含税单价");
				mmaterialInfoEntity.setMaterialVatPrice(materialVatPrice != null?
						CommonMethods.changeToBigdecimal(materialVatPrice.trim()):null);
				// 不含税单价
				String materialTaxPrice = (String)mapData.get("不含税单价");
				mmaterialInfoEntity.setMaterialTaxPrice(materialTaxPrice!=null?
						CommonMethods.changeToBigdecimal(materialTaxPrice.trim()):null);
				// 税率
				String tax = (String)mapData.get("税率");
				mmaterialInfoEntity.setTax(tax!=null?CommonMethods.changeToBigdecimal(tax.trim()):null);
				if(CommonMethods.changeToBigdecimal(tax).equals(1)) {
					throw new NullPointerException(messageBean.getMessage("bussiness.material.tax.error", (String)mapData.get("物料专用号"),CommonConstantEnum.RATE.getTypeName()));
				}
				// 代理费率
				String materialRate = (String)mapData.get("代理费率");
				mmaterialInfoEntity.setMaterialRate(materialRate!=null?CommonMethods.changeToBigdecimal(materialRate.trim()):null);
				// HS海关编码
				mmaterialInfoEntity.setHsNo((String)mapData.get("HS海关编码"));
				// 供应商编码
				MSupplierInfoEntity supplierInfo = msupplierInfoDao.selectByCode((String)mapData.get("供应商编码"));
				if(supplierInfo == null) {
					throw new NullPointerException(messageBean.getMessage("bussiness.material.supplier.exist.no", (String)mapData.get("供应商编码")));
				}
				mmaterialInfoEntity.setSupplierCode((String)mapData.get("供应商编码"));
				// 物料配额表
				// 物料号
				materialQuotaEntity.setMaterialCode((String)mapData.get("物料专用号"));
				// 供应商编码
				materialQuotaEntity.setSupplierCode((String)mapData.get("供应商编码"));
				materialQuotaEntity.setSupplierName(supplierInfo.getSupplierNameCn());
				// 配额
				String quota = (String)mapData.get("配额");
				materialQuotaEntity.setMaterialQuota(quota!=null?CommonMethods.changeToBigdecimal(quota.trim()):null);
				
				//损耗率
				String lossRate = (String)mapData.get("损耗率");
				if(CommonMethods.changeToBigdecimal(lossRate).equals(1)) {
					throw new NullPointerException(messageBean.getMessage("bussiness.material.tax.error", (String)mapData.get("物料专用号"),CommonConstantEnum.LOSSRATE.getTypeName()));
				}
				mmaterialInfoEntity.setMaterialLossRate(lossRate!=null?CommonMethods.changeToBigdecimal(lossRate.trim()):null);
				// 长
				String length = (String)mapData.get("长");
				mmaterialInfoEntity.setLength(
						CommonMethods.changeToBigdecimal(length!=null?length.trim():length));
				// 宽
				String width = (String)mapData.get("宽");
				mmaterialInfoEntity.setWidth(
						CommonMethods.changeToBigdecimal(width!=null?width.trim():width));
				// 高
				String height = (String)mapData.get("高");
				mmaterialInfoEntity.setHeight(
						CommonMethods.changeToBigdecimal(height!=null?height.trim():height));
				// 备注
				mmaterialInfoEntity.setUserDefined1((String)mapData.get("备注"));
				// 数据所属
				mmaterialInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
				// 成品型号和子件型号组合判定是否存在，存在时更新，不存在时插入
				if(isExist(mmaterialInfoEntity.getMaterialOrderCode(),mmaterialInfoEntity.getMaterialCode(),roleInfoEntity.getRoleAt())) {
					// 更新者
					mmaterialInfoEntity.setCreateUser(principal.getUserName());
					mmaterialInfoEntity.setVersion(version+1);
					mmaterialInfoEntity.setIsLocked(0);
					mmaterialInfoEntity.setIsDelete(0);
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
				//物料号和供应商编码是否存在，存在是更新，不存在时插入
				if(isExistQuota(materialQuotaEntity.getMaterialCode(),materialQuotaEntity.getSupplierCode(),roleInfoEntity.getRoleAt())) {
					// 更新者
					materialQuotaEntity.setUpdateUser(principal.getUserName());
					materialQuotaEntity.setVersion(quotaVersion+1);
					materialQuotaEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
					quotaUpdateResultLst.add(materialQuotaEntity);
				}
				else {
					// 创建者
					materialQuotaEntity.setCreateUser(principal.getUserName());
					materialQuotaEntity.setIsDelete(0);
					materialQuotaEntity.setVersion(1);
					materialQuotaEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
					quotaInsertResultLst.add(materialQuotaEntity);
				}
			}
			// 去除重复对象
			quotaInsertResultLst = deleteItear(quotaInsertResultLst);
			quotaUpdateResultLst = deleteItear(quotaUpdateResultLst);
			returnMap.put("insertList", insertResultLst);
			returnMap.put("updateList", updateResultLst);
			returnMap.put("quotaInsertList", quotaInsertResultLst);
			returnMap.put("quotaUpdateList", quotaUpdateResultLst);
			return returnMap;
		}
		catch(NullPointerException se) {
			throw se;
		}catch(Exception e) {
			throw e;
		}
	}
	
	private List<TMaterialQuotaEntity> deleteItear(List<TMaterialQuotaEntity> quotaInsertResultLst){
		List<TMaterialQuotaEntity> list = quotaInsertResultLst.stream().distinct().collect(Collectors.toList());
		return list;
	}
	
	/***
	 * 成品型号和子件型号共同判定是否存在
	 * @param materialOrderCode
	 * @param materialCode
	 * @return true/false
	 */
	private boolean isExist(String materialOrderCode,String materialCode,String roleAt) {
		MMaterialInfoEntity materialInfoEntity = new MMaterialInfoEntity();
		materialInfoEntity.setMaterialOrderCode(materialOrderCode);
		materialInfoEntity.setMaterialCode(materialCode);
		materialInfoEntity.setDataRoleAt(roleAt);
		List<MMaterialInfoEntity> resultList = mmaterialInfoDao.selectByPrimaryKey(materialInfoEntity);
		if(resultList.size()>0) {
			version = resultList.get(0).getVersion();
			return true;
		}
		return false;
	}
	
	/***
	 * 物料号和供应商编码共同判定是否存在
	 * @param materialCode
	 * @param supplierNo
	 * @return true/false
	 */
	private boolean isExistQuota(String materialCode,String supplierNo,String roleAt) {
		TMaterialQuotaEntity materialQuotaEntity = new TMaterialQuotaEntity();
		materialQuotaEntity.setMaterialCode(materialCode);
		materialQuotaEntity.setSupplierCode(supplierNo);
		materialQuotaEntity.setDataRoleAt(roleAt);
		List<TMaterialQuotaEntity> resultList = tmaterialQuotaDao.selectByPrimaryKey(materialQuotaEntity);
		if(resultList.size()>0) {
			quotaVersion = resultList.get(0).getVersion();
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
				throw new NullPointerException(messageBean.getMessage("common.dict.emptyerror", dictTypeCn,nameValue));
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
}
