package cn.springboot.osbulkparts.service.impl;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.warehouse.TOrderDetailInfoDao;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.OrderDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
