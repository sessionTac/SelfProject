package cn.springboot.osbulkparts.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.basedata.TMaterialQuotaDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.TMaterialQuotaEntity;
import cn.springboot.osbulkparts.service.MaterialQuotaService;

@Service
public class MaterialQuotaServiceImpl implements MaterialQuotaService{

	@Autowired
	private TMaterialQuotaDao tmaterialQuotaDao;
	
	@Autowired
	private MRoleInfoDao mroleInfoDao;
	
    @Autowired
    private I18nMessageBean messageBean;
    
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TMaterialQuotaEntity> selectMaterialQuotaList(TMaterialQuotaEntity materialQuotaEntity, Authentication auth) {
		CommonResultInfo<TMaterialQuotaEntity> result = new CommonResultInfo<TMaterialQuotaEntity>();
		try {
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),materialQuotaEntity.getLanguageFlag());
			materialQuotaEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
			List<TMaterialQuotaEntity> pageInfo = tmaterialQuotaDao.selectByPrimaryKey(materialQuotaEntity);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResultList(pageInfo);
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
	public CommonResultInfo<TMaterialQuotaEntity> selectMaterialQuota(TMaterialQuotaEntity MaterialQuotaEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> insertMaterialQuota(TMaterialQuotaEntity materialQuotaEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TMaterialQuotaEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		try {
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected(),materialQuotaEntity.getLanguageFlag());
			TMaterialQuotaEntity param = new TMaterialQuotaEntity();
			param.setMaterialCode(materialQuotaEntity.getMaterialCode());
			param.setSupplierCode(materialQuotaEntity.getSupplierCode());
			param.setDataRoleAt(roleInfoEntity.getRoleAt());
			List<TMaterialQuotaEntity> quotaList = tmaterialQuotaDao.selectByPrimaryKey(param);
			int returnInt = 0;
			if(quotaList.size()>0) {
				materialQuotaEntity.setUpdateUser(principal.getUserName());
				materialQuotaEntity.setIsDelete(0);
				materialQuotaEntity.setVersion(quotaList.get(0).getVersion()+1);
				materialQuotaEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
				List<TMaterialQuotaEntity> paramList = new ArrayList<TMaterialQuotaEntity>();
				paramList.add(materialQuotaEntity);
				returnInt = tmaterialQuotaDao.updateList(paramList);
			}else {
				materialQuotaEntity.setCreateUser(principal.getUserName());
				materialQuotaEntity.setIsDelete(0);
				materialQuotaEntity.setVersion(1);
				materialQuotaEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
				returnInt = tmaterialQuotaDao.insert(materialQuotaEntity);
			}
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.MATERIAL_QUOTA_DATA.getTypeName()));
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
	public CommonResultInfo<?> updateMaterialQuota(TMaterialQuotaEntity MaterialQuotaEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> deleteMaterialQuota(String materialCode, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TMaterialQuotaEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		try {
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();

			TMaterialQuotaEntity materialInfoEntity = new TMaterialQuotaEntity();
			materialInfoEntity.setMaterialCode(materialCode);
			materialInfoEntity.setUpdateUser(principal.getUserName());
			materialInfoEntity.setIsDelete(1);
			int returnInt = tmaterialQuotaDao.deleteBatchData(materialInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.MATERIAL_QUOTA_DATA.getTypeName()));
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
	public CommonResultInfo<?> deleteBatchMaterialQuota(CommonEntity commonEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}
}
