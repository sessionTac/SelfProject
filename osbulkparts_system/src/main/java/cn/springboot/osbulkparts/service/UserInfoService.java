package cn.springboot.osbulkparts.service;

import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;

public interface UserInfoService {
	
	CommonResultInfo<MUserInfoEntity> getUserInfoList(MUserInfoEntity muserInfoEntity,int pageNumber,int pageSize);
	
	CommonResultInfo<MUserInfoEntity> getUserInfo(String userId);
	
	CommonResultInfo<MUserInfoEntity> getUserCustomerRelationInfo(String userId);

	CommonResultInfo<?> addUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);
	
	CommonResultInfo<?> updateUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);
	
	CommonResultInfo<?> deleteUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);

}
