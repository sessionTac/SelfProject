package cn.springboot.osbulkparts.service.impl;

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

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.basedata.MSupplierInfoDao;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.SupplierInfoService;

@Service
public class SupplierInfoServiceImpl implements SupplierInfoService{

	@Autowired
	private MSupplierInfoDao msupplierInfoDao;
	
	@Autowired
	private I18nMessageBean messageBean;
	
	@Autowired
	private TDictDataDao tDictDataDao;
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews() {
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
		try {
			Map<String,List<TDictDataEntity>> map = new HashMap<>();
			TDictDataEntity tDictDataEntity = new TDictDataEntity();
			tDictDataEntity.setDictTypeCode("supplierCata");
			map.put("supplierCatas",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			tDictDataEntity.setDictTypeCode("supplierLevel");
			map.put("supplierLevels",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			tDictDataEntity.setDictTypeCode("roleAt");
			map.put("supplierAts",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
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
	public CommonResultInfo<MSupplierInfoEntity> getSupplierInfoList(MSupplierInfoEntity msupplierInfoEntity,
			int pageNumber, int pageSize) {
		CommonResultInfo<MSupplierInfoEntity> result = new CommonResultInfo<MSupplierInfoEntity>();
		try {
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<MSupplierInfoEntity> pageInfo = new PageInfo<>(
					msupplierInfoDao.selectSupplierInfoList(msupplierInfoEntity));
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResultInfo(pageInfo);
		} catch (Exception e) {
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@Override
	public CommonResultInfo<MSupplierInfoEntity> getSupplierInfo(String supplierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<MSupplierInfoEntity> getUserCustomerRelationInfo(String supplierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> addSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> updateSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> deleteSupplierInfo(String supplierId, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

}
