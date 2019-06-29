package cn.springboot.osbulkparts.service.impl;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.basedata.MMaterialInfoDao;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TOrderDetailInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TOrderInfoDao;
import cn.springboot.osbulkparts.entity.*;
import cn.springboot.osbulkparts.service.OrderDetailInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailInfoServiceImpl implements OrderDetailInfoService {

    @Autowired
    private TOrderDetailInfoDao tOrderDetailInfoDao;
    @Autowired
    private TDictDataDao tDictDataDao;
    @Autowired
    private MRoleInfoDao mroleInfoDao;
    @Autowired
    private TOrderInfoDao tOrderInfoDao;
    @Autowired
    private MMaterialInfoDao mMaterialInfoDao;
    @Autowired
    private I18nMessageBean messageBean;

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
    public CommonResultInfo<TOrderDetailInfoEntity> selectOrderDetailInfoList(TOrderDetailInfoEntity tOrderDetailInfoEntity, int pageNumber, int pageSize, Authentication auth) {
        CommonResultInfo<TOrderDetailInfoEntity> result = new CommonResultInfo<TOrderDetailInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
        try {
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

    @Override
    public CommonResultInfo<TOrderInfoEntity> getAllOrderCode(Authentication auth) {
        CommonResultInfo<TOrderInfoEntity> result = new CommonResultInfo<TOrderInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
        try {
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            result.setResultList(tOrderInfoDao.getAllOrderCode(roleInfoEntity.getRoleAt()));
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
    public CommonResultInfo<TOrderInfoEntity> getOrderInfoByOrderCode(String materialOrderCode,Authentication auth) {
        CommonResultInfo<TOrderInfoEntity> result = new CommonResultInfo<TOrderInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
        try {
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            result.setResult(tOrderInfoDao.getOrderInfoByOrderCode(materialOrderCode,roleInfoEntity.getRoleAt()));
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
    public CommonResultInfo<MMaterialInfoEntity> getMaterialInfoByMaterialCode(String materialCode, Authentication auth) {
        CommonResultInfo<MMaterialInfoEntity> result = new CommonResultInfo<MMaterialInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
        try {
            MMaterialInfoEntity mMaterialInfoEntity = new MMaterialInfoEntity();
            mMaterialInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
            mMaterialInfoEntity.setMaterialCode(materialCode);
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            result.setResultList(mMaterialInfoDao.selectByPrimaryKey(mMaterialInfoEntity));
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
    public CommonResultInfo<?> checkOrderCodeAndMaterialCode(String orderCode, String materialCode,Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
        if (!("".equals(orderCode))){
            List<TOrderInfoEntity> list = tOrderInfoDao.checkOrderCodeAndMaterialCode(orderCode,roleInfoEntity.getRoleAt());
            if (list.size()>0){
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
            }else {
                result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("common.info.empty"));
            }
        }else if(!("".equals(materialCode))){
            List<MMaterialInfoEntity> list = mMaterialInfoDao.checkOrderCodeAndMaterialCode(orderCode,roleInfoEntity.getRoleAt());
            if (list.size()>0){
                result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            }else {
                result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("common.info.empty"));
            }
        }
        return result;
    }
}
