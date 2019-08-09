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
	
	CommonResultInfo<MUserInfoEntity> getUserInfo(String userId,String lang, Locale locale);

	CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions(String lang,Locale locale);
	
	CommonResultInfo<MUserInfoEntity> getUserCustomerRelationInfo(String userId,Locale locale);

	CommonResultInfo<?> addUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> updateUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth,Locale locale);
	
	CommonResultInfo<?> deleteUserInfo(String userId, Authentication auth,Locale locale);
	
	CommonResultInfo<MUserInfoEntity> findUserWithRoleAndFunc(String userName, String roleId, Authentication auth,String lang,Locale locale);

	CommonResultInfo<TUserRoleRelationEntity> findRoleByUserId(String userId,Locale locale);

    CommonResultInfo<MRoleInfoEntity> findAllRole(MRoleInfoEntity mRoleInfoEntity,Locale locale);

    /**
     * 添加角色
     * @param roleIds
     * @param userId
     * @return
     */
    Object insertRole(List<String> roleIds, String userId,Authentication auth,Locale locale);

    /**
     * 校验角色信息
     */
    CommonResultInfo<?> checkInfo(MUserInfoEntity mUserInfoEntity,String checkFlag,Locale locale);

	/**
	 * 重置密码
	 * @return
	 */
	CommonResultInfo<?> resetPassword(MUserInfoEntity userInfoEntity, Authentication auth,Locale locale);

	/**
	 * 修改密码
	 * @return
	 */
	CommonResultInfo<?> changePassword(MUserInfoEntity userInfoEntity,String oldPassword, Authentication auth,Locale locale);
}
