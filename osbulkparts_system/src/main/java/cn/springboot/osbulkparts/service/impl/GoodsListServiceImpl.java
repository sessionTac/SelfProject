package cn.springboot.osbulkparts.service.impl;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TDeliverInfoDao;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import cn.springboot.osbulkparts.entity.TStockInfoEntity;
import cn.springboot.osbulkparts.service.GoodsListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class GoodsListServiceImpl implements GoodsListService {
    @Autowired
    private TDeliverInfoDao tDeliverInfoDao;
    @Autowired
    private MRoleInfoDao mroleInfoDao;
    @Autowired
    private I18nMessageBean messageBean;
    @Override
    public CommonResultInfo<?> getGoodsList(TDeliverInfoEntity tDeliverInfoEntity, int pageNumber, int pageSize, Authentication auth) {
        CommonResultInfo<TDeliverInfoEntity> result = new CommonResultInfo<TDeliverInfoEntity>();
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
        try {
            tDeliverInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
            PageHelper.startPage(pageNumber, pageSize);
            PageInfo<TDeliverInfoEntity> pageInfo = new PageInfo<>(
                    tDeliverInfoDao.selectTDeliverInfoList(tDeliverInfoEntity));
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
    public CommonResultInfo<?> sendGoods(CommonEntity commonEntity, Authentication auth) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> downExcel(TDeliverInfoEntity tDeliverInfoEntity) {
        return null;
    }
}
