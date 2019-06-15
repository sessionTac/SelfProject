package cn.springboot.osbulkparts.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
        			resultInt = resultInt + mmaterialInfoDao.insertList(paramList);
        		}
        		if(materialInfoParams.containsKey("updateList")) {
        			paramList = materialInfoParams.get("updateList");
        			resultInt = resultInt + mmaterialInfoDao.updateList(paramList);
        		}
            	if(resultInt >0) {
            		result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
            		result.setMessage(messageBean.getMessage("common.excel.success"));
            	}
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
	public CommonResultInfo<MMaterialInfoEntity> selectMaterialInfoList(
			MMaterialInfoEntity materialInfoEntity, int pageNumber, int pageSize) {
		CommonResultInfo<MMaterialInfoEntity> result = new CommonResultInfo<MMaterialInfoEntity>();
		try {
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
			String dictUUID = CommonSqlUtils.getUUID32();
			materialInfoEntity.setMaterialInfoId(dictUUID);
			materialInfoEntity.setCreateUser(principal.getUserName());
			materialInfoEntity.setIsDelete(0);
			materialInfoEntity.setVersion(1);
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
			materialInfoEntity.setUpdateUser(principal.getUserName());
			materialInfoEntity.setVersion(materialInfoEntity.getVersion()+1);
			materialInfoEntity.setIsDelete(0);
			int returnInt = mmaterialInfoDao.updateByPrimaryKey(materialInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.MATERIAL_DATA.getTypeName()));
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
				int returnInt = mmaterialInfoDao.lockedData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.LOCK_TRUE.getTypeName());
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
	
	/****Private Methods****/
	/***
	 * Excel文件解析
	 * @throws Exception 
	 */
	private Map<String,List<MMaterialInfoEntity>> resolvExcelToDb(MultipartFile excleFile,Authentication auth) throws Exception{
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
				String materCateVle = CommonMethods.getFromDictDataByName(
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
				String unitVle = CommonMethods.getFromDictDataByName(
						(String)mapData.get("单位"),"unit","单位");
				mmaterialInfoEntity.setMaterialUnit(unitVle);
				// 换算关系
				mmaterialInfoEntity.setMaterialRelation((String)mapData.get("换算关系"));
				// 换算后单位
				String relationUnitVle = CommonMethods.getFromDictDataByName(
						(String)mapData.get("换算后单位"),"unit","换算后单位");
				mmaterialInfoEntity.setMaterialRelationUnit(relationUnitVle);
				// 最小包装数量
				mmaterialInfoEntity.setMaterialMinpackageAmt(
						CommonMethods.changeToDouble((String)mapData.get("最小包装数量")));
				// 不含税单价
				mmaterialInfoEntity.setMaterialTaxPrice(
						CommonMethods.changeToBigdecimal((String)mapData.get("不含税单价")));
				// 含税单价
				mmaterialInfoEntity.setMaterialVatPrice(
						CommonMethods.changeToBigdecimal((String)mapData.get("含税单价")));
				// 代理费率
				mmaterialInfoEntity.setMaterialRate(
						CommonMethods.changeToBigdecimal((String)mapData.get("代理费率")));
				// 币种
				String currencyVle = CommonMethods.getFromDictDataByName(
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
				// 数据所属
				mmaterialInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
				// 成品型号和子件型号组合判定是否存在，存在时更新，不存在时插入
				if(isExist(mmaterialInfoEntity.getMaterialOrderCode(),mmaterialInfoEntity.getMaterialCode())) {
					// 更新者
					mmaterialInfoEntity.setUpdateUser(principal.getUserName());
					mmaterialInfoEntity.setVersion(mmaterialInfoEntity.getVersion()+1);
					updateResultLst.add(mmaterialInfoEntity);
				}
				else {
					// 创建者
					mmaterialInfoEntity.setCreateUser(principal.getUserName());
					mmaterialInfoEntity.setIsDelete(0);
					mmaterialInfoEntity.setVersion(1);
					insertResultLst.add(mmaterialInfoEntity);
				}
			}
			returnMap.put("insertList", insertResultLst);
			returnMap.put("updateList", updateResultLst);
			return returnMap;
		}
		catch(Exception e) {
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
			return true;
		}
		return false;
	}
}
