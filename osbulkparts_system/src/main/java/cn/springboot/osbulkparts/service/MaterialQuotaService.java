package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TMaterialQuotaEntity;

import java.util.Locale;

public interface MaterialQuotaService {

	CommonResultInfo<TMaterialQuotaEntity> selectMaterialQuotaList(TMaterialQuotaEntity MaterialQuotaEntity, Authentication auth, Locale locale);
	
	CommonResultInfo<TMaterialQuotaEntity> selectMaterialQuota(TMaterialQuotaEntity MaterialQuotaEntity,Locale locale);
	
	CommonResultInfo<?> insertMaterialQuota(TMaterialQuotaEntity MaterialQuotaEntity,Authentication auth,Locale locale);
	
	CommonResultInfo<?> updateMaterialQuota(TMaterialQuotaEntity MaterialQuotaEntity,Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteMaterialQuota(String materialCode,Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteBatchMaterialQuota(CommonEntity commonEntity,Authentication auth,Locale locale);
}
