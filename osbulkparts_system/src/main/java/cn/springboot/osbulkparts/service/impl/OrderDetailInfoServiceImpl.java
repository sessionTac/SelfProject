package cn.springboot.osbulkparts.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
    public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews() {
        CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
        try {
            Map<String,List<TDictDataEntity>> map = new HashMap<>();
            TDictDataEntity tDictDataEntity = new TDictDataEntity();
            tDictDataEntity.setDictTypeCode("unit");
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
    public CommonResultInfo<TOrderDetailInfoEntity> selectOrderDetailInfoList(TOrderDetailInfoEntity tOrderDetailInfoEntity, int pageNumber, int pageSize, Authentication auth) {
        CommonResultInfo<TOrderDetailInfoEntity> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
        	MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
            tOrderDetailInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
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
    public CommonResultInfo<TOrderDetailInfoEntity> selectOrderDetailInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity) {
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
    public CommonResultInfo<TOrderInfoEntity> getAllOrderCode(String isBalance,Authentication auth) {
        CommonResultInfo<TOrderInfoEntity> result = new CommonResultInfo<TOrderInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
        	MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
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
    public CommonResultInfo<TOrderInfoEntity> getOrderInfoByOrderCode(String materialOrderCode,String isBalance,Authentication auth) {
        CommonResultInfo<TOrderInfoEntity> result = new CommonResultInfo<TOrderInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
        	MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
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
    public CommonResultInfo<MMaterialInfoEntity> getMaterialInfoByMaterialCode(String materialCode, Authentication auth) {
        CommonResultInfo<MMaterialInfoEntity> result = new CommonResultInfo<MMaterialInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
        	MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
            MMaterialInfoEntity mMaterialInfoEntity = new MMaterialInfoEntity();
            mMaterialInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
            mMaterialInfoEntity.setMaterialCode(materialCode);
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
    public CommonResultInfo<?> checkOrderCodeAndMaterialCode(String orderCode, String isBalance, String materialCode,Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
            MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
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
    public CommonResultInfo<?> updateOrderDetailInfoInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
            TOrderDetailInfoEntity orderInfoList = tOrderDetailInfoDao.selectByPrimaryKey(tOrderDetailInfoEntity.getId());
            if(orderInfoList != null && (orderInfoList.getVersion() != tOrderDetailInfoEntity.getVersion())) {
                result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.ORDERDETAILINFO_DATA.getTypeName()));
            }else {
                tOrderDetailInfoEntity.setUpdateUser(principal.getUserName());
                tOrderDetailInfoEntity.setVersion(tOrderDetailInfoEntity.getVersion()+1);
                tOrderDetailInfoEntity.setIsDelete(0);
                int returnInt = tOrderDetailInfoDao.updateByPrimaryKey(tOrderDetailInfoEntity);
                if (returnInt > 0) {
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                    result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.ORDERDETAILINFO_DATA.getTypeName()));
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
    public CommonResultInfo<?> insertOrderDetailInfoInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
	        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
	        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
        
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
                result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.ORDERINFO_DATA.getTypeName()));
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
    public CommonResultInfo<?> deleteBatchOrderInfo(CommonEntity commonEntity, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
            int returnInt = tOrderDetailInfoDao.deleteBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_DELETE.getTypeName());
            if (returnInt > 0) {
                result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.ORDERDETAILINFO_DATA.getTypeName()));
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
    public CommonResultInfo<?> approvalBatchOrderInfo(CommonEntity commonEntity, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        try {
            int returnInt = tOrderDetailInfoDao.approvalBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_APPROVAL.getTypeName());
            if (returnInt > 0) {
                result.setMessage(messageBean.getMessage("common.approval.success", CommonConstantEnum.ORDERDETAILINFO_DATA.getTypeName()));
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
	public CommonResultInfo<?> selectDeliveryInfo(CommonEntity commonEntity) {
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
	public CommonResultInfo<?> excuteDeliveryInfo(CommonEntity commonEntity, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<TDeliverInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        String dateFlag = commonEntity.getDateFlag();
        try {
	        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
	        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
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
            	
            	// 更新物料表中的单耗
//            	MMaterialInfoEntity materialInfo = new MMaterialInfoEntity();
//            	materialInfo.setMaterialOrderCode(materialOrderCode);
//            	materialInfo.setMaterialCode(materialCode);
//            	materialInfo.setMaterialAmount(materialAmount);
//            	materialInfo.setUpdateUser(updateUser);
//            	materialInfo.setDataRoleAt(dataRoleAt);
//            	materialInfo.set
//            	mMaterialInfoDao

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
        } finally {
            return result;
        }
	}
    @SuppressWarnings("finally")
    @Override
    public CommonResultInfo<Map<String, List<TDictDataEntity>>> sendGoodsInit() {
        CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
        try {
            Map<String,List<TDictDataEntity>> map = new HashMap<>();
            TDictDataEntity tDictDataEntity = new TDictDataEntity();
            tDictDataEntity.setDictTypeCode("transportation");	
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
}
