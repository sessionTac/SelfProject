package cn.springboot.osbulkparts.service;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;

public interface UserInfoService {
	
	CommonResultInfo<MUserInfoEntity> getUserInfoList(MUserInfoEntity muserInfoEntity, int pageNumber, int pageSize);
	
	CommonResultInfo<MUserInfoEntity> getUserInfo(String userId);
	
	CommonResultInfo<MUserInfoEntity> getUserCustomerRelationInfo(String userId);

	CommonResultInfo<?> addUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);
	
	CommonResultInfo<?> updateUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);
	
	CommonResultInfo<?> deleteUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);

}
