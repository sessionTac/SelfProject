package cn.springboot.osbulkparts.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.springboot.osbulkparts.common.OSLanguage;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.util.UriUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;

import cn.springboot.osbulkparts.common.CommonBusinessException;
import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.basedata.MMaterialInfoDao;
import cn.springboot.osbulkparts.dao.basedata.TMaterialRecordInfoDao;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TDeliverInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TOrderDetailInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TOrderInfoDao;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TMaterialRecordInfoEntity;
import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;
import cn.springboot.osbulkparts.entity.TOrderInfoEntity;
import cn.springboot.osbulkparts.service.OrderDetailInfoService;

@Service
public class OrderDetailInfoServiceImpl implements OrderDetailInfoService {

    @Autowired
    private TOrderDetailInfoDao tOrderDetailInfoDao;
    @Autowired
    private TDictDataDao tDictDataDao;
    @Autowired
    private MRoleInfoDao mroleInfoDao;
    @Autowired
    private TMaterialRecordInfoDao materialRecordInfoDao;
    @Autowired
    private TOrderInfoDao tOrderInfoDao;
    @Autowired
    private TDeliverInfoDao tdeliverInfoDao;
    @Autowired
    private MMaterialInfoDao mMaterialInfoDao;
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
            tDictDataEntity.setDictTypeCode("unit");
            tDictDataEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
            map.put("orderUnits",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
            tDictDataEntity.setDictTypeCode("mattertype");
            map.put("mattertype",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
            tDictDataEntity.setDictTypeCode("minpackageType");
            map.put("minpackageType",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
            tDictDataEntity.setDictTypeCode("currency");
            map.put("currency",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
            tDictDataEntity.setDictTypeCode("countryCode");
            map.put("countryCode",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
            tDictDataEntity.setDictTypeCode("orderStatus");
            map.put("orderStatus",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
            tDictDataEntity.setDictTypeCode("orderDetailType");
            map.put("orderDetailType",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
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
    public CommonResultInfo<TOrderDetailInfoEntity> selectOrderDetailInfoList(TOrderDetailInfoEntity tOrderDetailInfoEntity, int pageNumber, int pageSize, Authentication auth,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<TOrderDetailInfoEntity> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
        	MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),tOrderDetailInfoEntity.getLanguageFlag());
            if(!principal.getUserType().equals("1")) {
            	tOrderDetailInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
            }
            PageHelper.startPage(pageNumber, pageSize);
            PageInfo<TOrderDetailInfoEntity> pageInfo = new PageInfo<>(
                    tOrderDetailInfoDao.getOrderDetailInfoList(tOrderDetailInfoEntity));
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
    public CommonResultInfo<TOrderDetailInfoEntity> selectOrderDetailInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<TOrderDetailInfoEntity> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        try {
            List<TOrderDetailInfoEntity> resultList = tOrderDetailInfoDao.getOrderDetailInfoList(tOrderDetailInfoEntity);
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
    public CommonResultInfo<TOrderInfoEntity> getAllOrderCode(String isBalance,Authentication auth,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<TOrderInfoEntity> result = new CommonResultInfo<TOrderInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
        	MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),"");
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            result.setResultList(tOrderInfoDao.getAllOrderCode(isBalance,roleInfoEntity.getRoleAt()));
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
    public CommonResultInfo<TOrderInfoEntity> getOrderInfoByOrderCode(String materialOrderCode,String isBalance,Authentication auth,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<TOrderInfoEntity> result = new CommonResultInfo<TOrderInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
        	MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),"");
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            result.setResultList(tOrderInfoDao.getOrderInfoByOrderCode(materialOrderCode,isBalance,roleInfoEntity.getRoleAt()));
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
    public CommonResultInfo<MMaterialInfoEntity> getMaterialInfoByMaterialCode(String materialCode, Authentication auth,String lang,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<MMaterialInfoEntity> result = new CommonResultInfo<MMaterialInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
        	MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),OSLanguage.localeToTableSuffix(lang));
            MMaterialInfoEntity mMaterialInfoEntity = new MMaterialInfoEntity();
            mMaterialInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
            mMaterialInfoEntity.setMaterialCode(materialCode);
            mMaterialInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            List<MMaterialInfoEntity> resultList=mMaterialInfoDao.selectByPrimaryKey(mMaterialInfoEntity);
            if(resultList.size()>0) {
                result.setResult(resultList.get(0));
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

    @Override
    public CommonResultInfo<?> checkOrderCodeAndMaterialCode(String orderCode, String isBalance, String materialCode,Authentication auth,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
            MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),"");
            if (orderCode != null){
                List<TOrderInfoEntity> list = tOrderInfoDao.checkOrderCodeAndMaterialCode(orderCode,isBalance,roleInfoEntity.getRoleAt());
                if (list.size()>0){
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                }else {
                    result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
                    result.setMessage(messageBean.getMessage("bussiness.order.exist.no",orderCode));
                    return result;
                }
            }
            if(materialCode != null){
                List<MMaterialInfoEntity> list = mMaterialInfoDao.checkOrderCodeAndMaterialCode(materialCode,roleInfoEntity.getRoleAt());
                if (list.size()>0){
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                }else {
                    result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
                    result.setMessage(messageBean.getMessage("bussiness.material.exist.no",materialCode));
                    return result;
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
        }
        return result;
    }

    @SuppressWarnings("finally")
	@Override
    public CommonResultInfo<?> updateOrderDetailInfoInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity, Authentication auth,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<?> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
            TOrderDetailInfoEntity orderInfoList = tOrderDetailInfoDao.selectByPrimaryKey(tOrderDetailInfoEntity.getId());
            if(orderInfoList != null && (orderInfoList.getVersion() != tOrderDetailInfoEntity.getVersion())) {
                result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.ORDERDETAILINFO_DATA.getTypeName(locale)));
            }else {
                tOrderDetailInfoEntity.setUpdateUser(principal.getUserName());
                tOrderDetailInfoEntity.setVersion(tOrderDetailInfoEntity.getVersion()+1);
                tOrderDetailInfoEntity.setIsDelete(0);
                int returnInt = tOrderDetailInfoDao.updateByPrimaryKey(tOrderDetailInfoEntity);
                if (returnInt > 0) {
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                    result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.ORDERDETAILINFO_DATA.getTypeName(locale)));
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
    public CommonResultInfo<?> insertOrderDetailInfoInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity, Authentication auth,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<?> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
	        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
	        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),tOrderDetailInfoEntity.getLanguageFlag());
        
            tOrderDetailInfoEntity.setId(CommonSqlUtils.getUUID32());
            tOrderDetailInfoEntity.setCreateUser(principal.getUserName());
            tOrderDetailInfoEntity.setIsDelete(0);
            tOrderDetailInfoEntity.setVersion(1);
            tOrderDetailInfoEntity.setIsBalance("0");
            tOrderDetailInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
            tOrderDetailInfoEntity.setDateFlag("week");
            tOrderDetailInfoEntity.setOrderDetailType("1");
            int returnInt = tOrderDetailInfoDao.insertSelective(tOrderDetailInfoEntity);
            if (returnInt > 0) {
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.ORDERINFO_DATA.getTypeName(locale)));
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
    public CommonResultInfo<?> deleteBatchOrderInfo(CommonEntity commonEntity, Authentication auth,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
            int returnInt = tOrderDetailInfoDao.deleteBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_DELETE.getTypeName(locale));
            if (returnInt > 0) {
                result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.ORDERDETAILINFO_DATA.getTypeName(locale)));
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
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
    public CommonResultInfo<?> approvalBatchOrderInfo(CommonEntity commonEntity, Authentication auth,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
            int returnInt = tOrderDetailInfoDao.approvalBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_APPROVAL.getTypeName(locale));
            if (returnInt > 0) {
                result.setMessage(messageBean.getMessage("common.approval.success", CommonConstantEnum.ORDERDETAILINFO_DATA.getTypeName(locale)));
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
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
	public CommonResultInfo<?> selectDeliveryInfo(CommonEntity commonEntity,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<TOrderDetailInfoEntity> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        try {
            List<TOrderDetailInfoEntity> resultList = tOrderDetailInfoDao.selectDeliveryInfo(commonEntity.getIdsStr(),commonEntity.getDateFlag());
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            if(resultList.size()>0) {
                result.setResultList(resultList);
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
	public CommonResultInfo<?> excuteDeliveryInfo(CommonEntity commonEntity, Authentication auth,String lang,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<?> result = new CommonResultInfo<TDeliverInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        String dateFlag = commonEntity.getDateFlag();
        try {
	        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
	        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),OSLanguage.localeToTableSuffix(lang));
	        List<TDeliverInfoEntity> tdeliveryInfoListParam = new ArrayList<TDeliverInfoEntity>();
	        List<TDeliverInfoEntity> deliverInfoList= new ArrayList<TDeliverInfoEntity>();
	        List<TOrderDetailInfoEntity> orderDetailInfoEntity = tOrderDetailInfoDao.selectDeliveryInfo(commonEntity.getIdsStr(),commonEntity.getDateFlag());
	        for(int i=0;i<orderDetailInfoEntity.size();i++) {
	        	BigDecimal sendAmountDec = new BigDecimal(commonEntity.getAmouts()[i]);
	        	TDeliverInfoEntity deliveryInfo = new TDeliverInfoEntity();
	        	deliveryInfo.setOrderCode(orderDetailInfoEntity.get(i).getOrderCode());
	        	deliveryInfo.setMaterialCode(orderDetailInfoEntity.get(i).getMaterialCode());
	        	deliveryInfo.setDataRoleAt(orderDetailInfoEntity.get(i).getDataRoleAt());
	        	deliveryInfo.setSendAmount(sendAmountDec);
	        	deliverInfoList.add(deliveryInfo);
	        }
	        // 通过订单型号从订单详细表中取得数据
	        for(TDeliverInfoEntity deliveryInfo:deliverInfoList) {
	        	TOrderDetailInfoEntity orderDetailParam = new TOrderDetailInfoEntity();
	        	orderDetailParam.setOrderCode(deliveryInfo.getOrderCode());
	        	orderDetailParam.setMaterialCode(deliveryInfo.getMaterialCode());
	        	orderDetailParam.setDataRoleAt(deliveryInfo.getDataRoleAt());
	        	orderDetailParam.setDateFlag(dateFlag);
	        	orderDetailParam.setIsBalance("0");
                orderDetailParam.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
	        	List<TOrderDetailInfoEntity> orderDetailInfoList = tOrderDetailInfoDao.getOrderDetailInfoList(orderDetailParam);
	        	if(orderDetailInfoList.size()==0) {
	        		result.setMessage(messageBean.getMessage("bussiness.order.delivery.del.error",deliveryInfo.getOrderCode()));
	        		return result;
	        	}else {
	        		for(TOrderDetailInfoEntity orderInfo:orderDetailInfoList) {
	        			orderInfo.setDeliveryAmount(deliveryInfo.getSendAmount());
	        			orderInfo.setUpdateUser(principal.getUserName());
	        			orderInfo.setVersion(orderInfo.getVersion()+1);
	        			tOrderDetailInfoDao.updateByPrimaryKeySelective(orderInfo);
	        		}
	        	}
            	//更新物料记录表的发货数量
            	TMaterialRecordInfoEntity recordParam = new TMaterialRecordInfoEntity();
            	recordParam.setMaterialCode(deliveryInfo.getMaterialCode());
            	recordParam.setDataRoleAt(deliveryInfo.getDataRoleAt());
            	recordParam.setSupperAmount(deliveryInfo.getSendAmount());
            	materialRecordInfoDao.updateByPrimaryKeySelective(recordParam);
            	
//            	// 更新物料表中的单耗
//            	MMaterialInfoEntity materialParam = new MMaterialInfoEntity();
//            	materialParam.setMaterialOrderCode(deliveryInfo.getOrderCode());
//            	materialParam.setMaterialCode(deliveryInfo.getMaterialCode());
//            	List<MMaterialInfoEntity>  materialInfoList = mMaterialInfoDao.selectByPrimaryKey(materialParam);
//            	for(MMaterialInfoEntity materialInfo :materialInfoList) {
//                	materialInfo.setMaterialAmount(materialInfo.getMaterialAmount().subtract(deliveryInfo.getSendAmount()));
//                	materialInfo.setUpdateUser(principal.getUserName());
//            	}
//            	mMaterialInfoDao.updateList(materialInfoList);
            	
	        	deliveryInfo.setId(CommonSqlUtils.getUUID32());
	        	deliveryInfo.setCreateUser(principal.getUserName());
	        	deliveryInfo.setIsDelete(0);
	        	deliveryInfo.setState("0");
	        	deliveryInfo.setVersion(1);
	        	deliveryInfo.setContainerNo(commonEntity.getContainerNo());
	        	deliveryInfo.setContractNo(commonEntity.getContractNo());
	        	deliveryInfo.setShipNo(commonEntity.getShipNo());
	        	deliveryInfo.setBillNo(commonEntity.getBillNo());
	        	deliveryInfo.setTransportation(commonEntity.getTransportation());
	        	deliveryInfo.setDataRoleAt(roleInfoEntity.getRoleAt());
	        	tdeliveryInfoListParam.add(deliveryInfo);
	        }
            int returnInt = tdeliverInfoDao.insertList(tdeliveryInfoListParam);
            if (returnInt > 0) {
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("bussiness.order.delivery.success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
        } finally {
            return result;
        }
	}
    @SuppressWarnings("finally")
    @Override
    public CommonResultInfo<Map<String, List<TDictDataEntity>>> sendGoodsInit(String lang,Locale locale) {
        messageBean.setLocale(null,null,locale);
        CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
        try {
            Map<String,List<TDictDataEntity>> map = new HashMap<>();
            TDictDataEntity tDictDataEntity = new TDictDataEntity();
            tDictDataEntity.setDictTypeCode("transportation");
            tDictDataEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
            map.put("transportation",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
            result.setResult(map);
        } catch (Exception e) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
        } finally {
            return result;
        }
    }
    
	@Override
	public ResponseEntity<byte[]> downloadExcel(TOrderDetailInfoEntity tOrderDetailInfoEntity,Locale locale) {
        messageBean.setLocale(null,null,locale);
		String[] title = messageBean.getMessage("file.title.orderDetail").split(",");
		List<TOrderDetailInfoEntity> resultList = tOrderDetailInfoDao.getOrderDetailInfoList(tOrderDetailInfoEntity);
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
	private ResponseEntity<byte[]> educeExcel(String[] titles,List<TOrderDetailInfoEntity> list,Locale locale){
        messageBean.setLocale(null,null,locale);
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
            HSSFCellStyle numstyle = workbook.createCellStyle();
            numstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.000000"));
			DataFormat format = workbook.createDataFormat();
			textStyle.setDataFormat(format.getFormat("@"));
			//设置单元格的值  
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i+1);
				TOrderDetailInfoEntity example = list.get(i);
				//成品型号
				row.createCell(0).setCellValue(example.getOrderCode());
				//成品型号描述
				row.createCell(1).setCellValue(example.getOrderCodeDesc());
				//订单数量
				row.createCell(2).setCellValue(example.getOrderAmount()!=null?example.getOrderAmount().toString():"");
				//订单日期
				row.createCell(3).setCellValue(changeDate2Display(example.getOrderDate(),"yyyy/MM/dd"));
				//订单型号单位
				row.createCell(4).setCellValue(example.getDictOrderUnit()!=null?example.getDictOrderUnit().getName():"");
				//订单号
				row.createCell(5).setCellValue(example.getOrderId());
				//订单行项目
				row.createCell(6).setCellValue(example.getOrderIdItem());
				//物料专用号
				row.createCell(7).setCellValue(example.getMaterialCode());
				//物料中文描述
				row.createCell(8).setCellValue(example.getMaterialDescCn());
				//物料英文描述
				row.createCell(9).setCellValue(example.getMaterialDescEn());
				//物料俄文描述
				row.createCell(10).setCellValue(example.getMaterialDescRn());
				//供应商编码
				row.createCell(11).setCellValue(example.getMaterialSupplierNo());
				//供应商中文名称
				row.createCell(12).setCellValue(example.getMSupplierInfoEntity().getSupplierNameCn());
				//单位
				row.createCell(13).setCellValue(example.getDictMaterialUnit()!=null?example.getDictMaterialUnit().getName():"");
				//计划数量
				row.createCell(14).setCellStyle(numstyle);
				row.createCell(14).setCellType(CellType.NUMERIC);
				row.createCell(14).setCellValue(example.getMaterialAmount()!=null?Double.parseDouble(example.getMaterialAmount().toString()):null);
				//渠道
				row.createCell(15).setCellValue(example.getDictMaterialCategory()!=null?example.getDictMaterialCategory().getName():"");
				//换算关系
				row.createCell(16).setCellValue(example.getMaterialRelation());
				//换算后单位
				row.createCell(17).setCellValue(example.getDictMaterialUnit()!=null?example.getDictMaterialUnit().getName():"");
				//换算后数量
				row.createCell(18).setCellStyle(numstyle);
				row.createCell(18).setCellType(CellType.NUMERIC);
				row.createCell(18).setCellValue(example.getMaterialRelationQuantity()!=null?Double.parseDouble(example.getMaterialRelationQuantity().toString()):null);
				//不含税单价
				row.createCell(19).setCellStyle(numstyle);
				row.createCell(19).setCellType(CellType.NUMERIC);
				row.createCell(19).setCellValue(
						example.getMaterialVatPrice()!=null?Double.parseDouble(example.getMaterialVatPrice().toString()):null);
				// 不含税总价
				row.createCell(20).setCellStyle(numstyle);
				row.createCell(20).setCellType(CellType.NUMERIC);
				row.createCell(20).setCellValue(
						example.getMaterialVatTotalprice()!=null?Double.parseDouble(example.getMaterialVatTotalprice().toString()):null);
				//含税总价
				row.createCell(21).setCellStyle(numstyle);
				row.createCell(21).setCellType(CellType.NUMERIC);
				row.createCell(21).setCellValue(
						example.getMaterialTaxTotalprice() != null?Double.parseDouble(example.getMaterialTaxTotalprice().toString()):null);
				//税率
				row.createCell(22).setCellStyle(numstyle);
				row.createCell(22).setCellType(CellType.NUMERIC);
				row.createCell(22).setCellValue(
						example.getTax()!=null?Double.parseDouble(example.getTax().toString()):null);
				//代理费率
				row.createCell(23).setCellStyle(numstyle);
				row.createCell(23).setCellType(CellType.NUMERIC);
				row.createCell(23).setCellValue(
						example.getMaterialRate() != null?Double.parseDouble(example.getMaterialRate().toString()):null);
				//币种
				row.createCell(24).setCellValue(example.getDictMaterialCurrency() != null? example.getDictMaterialCurrency().getName():"");
				//国家标识
				row.createCell(25).setCellValue(example.getDictCountryCode()!=null?example.getDictCountryCode().getName():"");
				// 收货数量
				row.createCell(26).setCellStyle(numstyle);
				row.createCell(26).setCellType(CellType.NUMERIC);
				row.createCell(26).setCellValue(
						example.getTakeOverAmount() != null?Double.parseDouble(example.getTakeOverAmount().toString()):null);
				// 发货数量
				row.createCell(27).setCellStyle(numstyle);
				row.createCell(27).setCellType(CellType.NUMERIC);
				row.createCell(27).setCellValue(
						example.getDeliveryAmount() != null?Double.parseDouble(example.getDeliveryAmount().toString()):null);
				// 订单详细类型
				row.createCell(28).setCellValue(example.getDictOrderDetailType()!=null?example.getDictOrderDetailType().getName():"");
				// 创建人
				row.createCell(29).setCellValue(example.getCreateUser());
				// 创建时间
				row.createCell(30).setCellValue(changeDate2Display(example.getCreateTime(),"yyyy/MM/dd HH:mm:ss"));
				// 最后修改人
				row.createCell(31).setCellValue(example.getUpdateUser());
				// 最后修改时间
				row.createCell(32).setCellValue(changeDate2Display(example.getUpdateTime(),"yyyy/MM/dd HH:mm:ss"));
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
	
	/******************Private Methods***********************/
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
