package cn.springboot.osbulkparts.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.springboot.osbulkparts.common.entity.CommonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.basedata.MSupplierInfoDao;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
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

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<MSupplierInfoEntity> getSupplierInfo(String supplierId) {
		CommonResultInfo<MSupplierInfoEntity> result = new CommonResultInfo<MSupplierInfoEntity>();
		try {
			MSupplierInfoEntity supplierInfo = msupplierInfoDao.selectByPrimaryKey(supplierId);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResult(supplierInfo);
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
	public CommonResultInfo<?> insertSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MSupplierInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			MSupplierInfoEntity supplierInfo = msupplierInfoDao.selectByCode(mSupplierInfoEntity.getSupplierCode());
			if(supplierInfo != null) {
				result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.SUPPLIER.getTypeName()));
			}else {
				String dictUUID = CommonSqlUtils.getUUID32();
				mSupplierInfoEntity.setSupplierId(dictUUID);
				mSupplierInfoEntity.setCreateUser(principal.getUserName());
				mSupplierInfoEntity.setIsDelete(0);
				mSupplierInfoEntity.setVersion(1);
				int returnInt = msupplierInfoDao.insertSelective(mSupplierInfoEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.SUPPLIER.getTypeName()));
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
	public CommonResultInfo<?> updateSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MSupplierInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			mSupplierInfoEntity.setUpdateUser(principal.getUserName());
			mSupplierInfoEntity.setVersion(mSupplierInfoEntity.getVersion()+1);
			mSupplierInfoEntity.setIsDelete(0);
			int returnInt = msupplierInfoDao.updateByPrimaryKey(mSupplierInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.SUPPLIER.getTypeName()));
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
	public CommonResultInfo<?> deleteSupplierInfo(String supplierId, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			MSupplierInfoEntity mSupplierInfoEntity = new MSupplierInfoEntity();
			mSupplierInfoEntity.setSupplierId(supplierId);
			mSupplierInfoEntity.setUpdateUser(principal.getUserName());
			mSupplierInfoEntity.setIsDelete(1);
			int returnInt = msupplierInfoDao.updateByPrimaryKey(mSupplierInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.SUPPLIER.getTypeName()));
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
	public CommonResultInfo<?> deleteBatchMaterialInfo(CommonEntity commonEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MMaterialInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			int returnInt = msupplierInfoDao.deleteBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_DELETE.getTypeName());
			if (returnInt > 0) {
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.SUPPLIER.getTypeName()));
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

}
