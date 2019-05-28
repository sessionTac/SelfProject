package cn.springboot.osbulkparts.service.impl;

import cn.springboot.osbulkparts.dao.user.MUserInfoDao;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private MUserInfoDao muserInfoEntityMapper;

	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<MUserInfoEntity> getUserInfoList(MUserInfoEntity muserInfoEntity, int pageNumber,
															 int pageSize) {
//		CommonResultInfo<MUserInfoEntity> result = new CommonResultInfo<MUserInfoEntity>();
//		try {
//			PageHelper.startPage(pageNumber, pageSize);
//			PageInfo<MUserInfoEntity> pageInfo = new PageInfo<>(
//					muserInfoEntityMapper.selectUserInfoList(muserInfoEntity));
//			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
//			result.setResultInfo(pageInfo);
//		} catch (Exception e) {
//			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
//			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
//			result.setException(e.getMessage().toString());
//		} finally {
//			return result;
//		}
		return  null;
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<MUserInfoEntity> getUserInfo(String userId) {
//		CommonResultInfo<MUserInfoEntity> result = new CommonResultInfo<MUserInfoEntity>();
//		try {
//			MUserInfoEntity userInfo = muserInfoEntityMapper.selectUserInfoByUserId(userId);
//			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
//			result.setResult(userInfo);
//		} catch (Exception e) {
//			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
//			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
//			result.setException(e.getMessage().toString());
//		} finally {
//			return result;
//		}
		return  null;
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<MUserInfoEntity> getUserCustomerRelationInfo(String userId) {
//		CommonResultInfo<MUserInfoEntity> result = new CommonResultInfo<MUserInfoEntity>();
//		try {
//			List<MUserInfoEntity> userInfo = muserInfoEntityMapper.selectUserCustomerRelation(userId);
//			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
//			result.setResultList(userInfo);
//		} catch (Exception e) {
//			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
//			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
//			result.setException(e.getMessage().toString());
//		} finally {
//			return result;
//		}
		return null;
	}

	@Override
	public CommonResultInfo<?> updateUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> deleteUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> addUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

}
