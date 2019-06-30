package cn.springboot.osbulkparts.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TStockInfoDao;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TStockInfoEntity;
import cn.springboot.osbulkparts.service.StockInfoService;

public class StockInfoServiceImpl implements StockInfoService {

	@Autowired
	private TDictDataDao tDictDataDao;
	
	@Autowired
	private TStockInfoDao tstockInfoDao;
	
	@Autowired
	private MRoleInfoDao mroleInfoDao;
	
    @Autowired
    private I18nMessageBean messageBean;
    
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews() {
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
		try {
			Map<String,List<TDictDataEntity>> map = new HashMap<>();
			TDictDataEntity tDictDataEntity = new TDictDataEntity();
			tDictDataEntity.setDictTypeCode("mattertype");
			map.put("materialCategorys",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
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
	public CommonResultInfo<?> getStockInfoList(TStockInfoEntity stockInfoEntity, int pageNumber, int pageSize,Authentication auth) {
		CommonResultInfo<TStockInfoEntity> result = new CommonResultInfo<TStockInfoEntity>();
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
		try {
			stockInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<TStockInfoEntity> pageInfo = new PageInfo<>(
					tstockInfoDao.selectByPrimaryKey(stockInfoEntity));
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
	public CommonResultInfo<TStockInfoEntity> getStockInfoInfo(TStockInfoEntity stockInfoEntity) {
		CommonResultInfo<TStockInfoEntity> result = new CommonResultInfo<TStockInfoEntity>();
		try {
			List<TStockInfoEntity> resultList = tstockInfoDao.selectByPrimaryKey(stockInfoEntity);
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
	public CommonResultInfo<?> insertSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth) {
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

	@Override
	public CommonResultInfo<?> deleteBatchMaterialInfo(CommonEntity commonEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> importExcel(MultipartFile excleFile, HttpServletRequest request, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<byte[]> downloadExcel(MMaterialInfoEntity materialInfoEntity) {
		// TODO Auto-generated method stub
		return null;
	}

}
