package cn.springboot.osbulkparts.service.impl;

import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TDeliverInfoDao;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import cn.springboot.osbulkparts.service.GoodsListService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsListServiceImpl implements GoodsListService {
    @Autowired
    private TDeliverInfoDao tDeliverInfoDao;
    @Autowired
    private MRoleInfoDao mroleInfoDao;
    @Autowired
    private TDictDataDao tDictDataDao;
    @Autowired
    private I18nMessageBean messageBean;

    @SuppressWarnings("finally")
    @Override
    public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews() {
        CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
        try {
            Map<String,List<TDictDataEntity>> map = new HashMap<>();
            TDictDataEntity tDictDataEntity = new TDictDataEntity();
            tDictDataEntity.setDictTypeCode("goodsStatus");
            map.put("goodsStatus",tDictDataDao.selectByPrimaryKey(tDictDataEntity));

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

    @SuppressWarnings("finally")
	@Override
    public CommonResultInfo<?> sendGoods(CommonEntity commonEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TDeliverInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			String updateUser = principal.getUserName();
			String state = "1";//已收货
			int returnInt = tDeliverInfoDao.updateListForDelivery(commonEntity.getIdsStr(), updateUser, state);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("bussiness.order.delivery.take.success"));
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
    public ResponseEntity<byte[]> downExcel(TDeliverInfoEntity tDeliverInfoEntity) {
        return null;
    }
}
