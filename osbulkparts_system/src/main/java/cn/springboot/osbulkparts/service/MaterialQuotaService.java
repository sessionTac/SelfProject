package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TMaterialQuotaEntity;

public interface MaterialQuotaService {

	CommonResultInfo<TMaterialQuotaEntity> selectMaterialQuotaList(TMaterialQuotaEntity MaterialQuotaEntity, Authentication auth);
	
	CommonResultInfo<TMaterialQuotaEntity> selectMaterialQuota(TMaterialQuotaEntity MaterialQuotaEntity);
	
	CommonResultInfo<?> insertMaterialQuota(TMaterialQuotaEntity MaterialQuotaEntity,Authentication auth);
	
	CommonResultInfo<?> updateMaterialQuota(TMaterialQuotaEntity MaterialQuotaEntity,Authentication auth);
	
	CommonResultInfo<?> deleteMaterialQuota(String materialCode,Authentication auth);
	
	CommonResultInfo<?> deleteBatchMaterialQuota(CommonEntity commonEntity,Authentication auth);
}
