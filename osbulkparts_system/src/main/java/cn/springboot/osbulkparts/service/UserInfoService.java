package cn.springboot.osbulkparts.service;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoService {
	
	CommonResultInfo<MUserInfoEntity> getUserInfoList(MUserInfoEntity muserInfoEntity, int pageNumber, int pageSize);
	
	CommonResultInfo<MUserInfoEntity> getUserInfo(String userId);

	CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions();
	
	CommonResultInfo<MUserInfoEntity> getUserCustomerRelationInfo(String userId);

	CommonResultInfo<?> addUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);
	
	CommonResultInfo<?> updateUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);
	
	CommonResultInfo<?> deleteUserInfo(String userId, Authentication auth);
	
	CommonResultInfo<MUserInfoEntity> findUserWithRoleAndFunc(String userName, String roleId, Authentication auth);

}
