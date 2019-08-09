package cn.springboot.osbulkparts.service;

import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TUserRoleRelationEntity;
import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface UserInfoService {
	
	CommonResultInfo<MUserInfoEntity> getUserInfoList(MUserInfoEntity muserInfoEntity, int pageNumber, int pageSize, Locale locale);
	
	CommonResultInfo<MUserInfoEntity> getUserInfo(String userId,String lang);

	CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions(String lang);
	
	CommonResultInfo<MUserInfoEntity> getUserCustomerRelationInfo(String userId);

	CommonResultInfo<?> addUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);
	
	CommonResultInfo<?> updateUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth);
	
	CommonResultInfo<?> deleteUserInfo(String userId, Authentication auth);
	
	CommonResultInfo<MUserInfoEntity> findUserWithRoleAndFunc(String userName, String roleId, Authentication auth,String lang);

	CommonResultInfo<TUserRoleRelationEntity> findRoleByUserId(String userId);

    CommonResultInfo<MRoleInfoEntity> findAllRole(MRoleInfoEntity mRoleInfoEntity);

    /**
     * 添加角色
     * @param roleIds
     * @param userId
     * @return
     */
    Object insertRole(List<String> roleIds, String userId,Authentication auth);

    /**
     * 校验角色信息
     */
    CommonResultInfo<?> checkInfo(MUserInfoEntity mUserInfoEntity,String checkFlag);

	/**
	 * 重置密码
	 * @return
	 */
	CommonResultInfo<?> resetPassword(MUserInfoEntity userInfoEntity, Authentication auth);

	/**
	 * 修改密码
	 * @return
	 */
	CommonResultInfo<?> changePassword(MUserInfoEntity userInfoEntity,String oldPassword, Authentication auth);
}
