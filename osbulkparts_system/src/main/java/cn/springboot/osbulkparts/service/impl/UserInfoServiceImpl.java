package cn.springboot.osbulkparts.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MUserInfoDao;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private MUserInfoDao muserInfoDao;

	@Autowired
	private TDictDataDao tDictDataDao;
	
	@Autowired
	private I18nMessageBean messageBean;
	
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<MUserInfoEntity> getUserInfoList(MUserInfoEntity muserInfoEntity, int pageNumber,
															 int pageSize) {
		CommonResultInfo<MUserInfoEntity> result = new CommonResultInfo<MUserInfoEntity>();
		try {
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<MUserInfoEntity> pageInfo = new PageInfo<>(
					muserInfoDao.selectUserInfoList(muserInfoEntity));
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
	public CommonResultInfo<MUserInfoEntity> getUserInfo(String userId) {
		CommonResultInfo<MUserInfoEntity> result = new CommonResultInfo<MUserInfoEntity>();
		try {
			MUserInfoEntity userInfo = muserInfoDao.selectUserInfo(userId);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResult(userInfo);
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
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions() {
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
		try {
			Map<String,List<TDictDataEntity>> map = new HashMap<>();
			map.put("userType",tDictDataDao.selectByPrimaryKey("5"));
			map.put("userStatus",tDictDataDao.selectByPrimaryKey("6"));
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
	public CommonResultInfo<MUserInfoEntity> findUserWithRoleAndFunc(String userName, String roleId, Authentication auth){
		CommonResultInfo<MUserInfoEntity> result = new CommonResultInfo<MUserInfoEntity>();
		try {
			MUserInfoEntity userInfo = muserInfoDao.selectUserWithRoleAndFunc(userName, roleId);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResult(userInfo);
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

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> updateUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MUserInfoEntity mUserInfoEntityVersion= new MUserInfoEntity();
		mUserInfoEntityVersion.setVersion(mUserInfoEntity.getVersion());
		mUserInfoEntityVersion.setUserId(mUserInfoEntity.getUserId());
		//校验 version 排他  (根据id和version)
		List<MUserInfoEntity> checkListVersion=muserInfoDao.checkingAndVersion(mUserInfoEntityVersion);
		try {

			if(checkListVersion.size()==1){
				MUserInfoEntity mUserInfoEntityUserName= new MUserInfoEntity();
				mUserInfoEntityUserName.setUserName(mUserInfoEntity.getUserName());
				//校验 用户名是否重复（只根据username）
				List<MUserInfoEntity> checkListName=muserInfoDao.checkingAndVersion(mUserInfoEntityUserName);
				if (    (checkListName.size()==0)
						||
						( ( checkListName.size()==1 ) && ( mUserInfoEntity.getUserId().equals(checkListName.get(0).getUserId()) ) )
				){
					mUserInfoEntity.setUpdateUser(principal.getUserId());
					int returnInt = muserInfoDao.updateByPrimaryKeySelective(mUserInfoEntity);
					if (returnInt > 0) {
						result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
						result.setMessage(messageBean.getMessage("common.update.sucess", CommonConstantEnum.USER_NAME.getTypeName()));
					}
				} else {
					result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.USER_NAME.getTypeName()));
				}
			}else {
				result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.USER_NAME.getTypeName()));
			}

		} catch (Exception e) {
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@Override
	public CommonResultInfo<?> deleteUserInfo(String userId, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MUserInfoEntity mUserInfoEntity =new MUserInfoEntity();
		try {
			mUserInfoEntity.setUserId(userId);
			mUserInfoEntity.setUpdateUser(principal.getUserId());
			mUserInfoEntity.setIsDelete(1);
			int returnInt = muserInfoDao.updateByPrimaryKeySelective(mUserInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.delete.sucess", CommonConstantEnum.USER_NAME.getTypeName()));
			}
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
	public CommonResultInfo<?> addUserInfo(MUserInfoEntity mUserInfoEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			//校验 用户名是否重复
			List<MUserInfoEntity> checkList=muserInfoDao.checkingAndVersion(mUserInfoEntity);
			if (checkList.size()==0){
				String userUUID = CommonSqlUtils.getUUID32();
				mUserInfoEntity.setUserId(userUUID);
				mUserInfoEntity.setPassword(encoder.encode(mUserInfoEntity.getPassword()));
				mUserInfoEntity.setCreateUser(principal.getUserId());
				mUserInfoEntity.setIsDelete(0);
				mUserInfoEntity.setVersion(1);
				int returnInt = muserInfoDao.insertSelective(mUserInfoEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.add.sucess", CommonConstantEnum.USER_NAME.getTypeName()));
				}
			}else {
				result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.USER_NAME.getTypeName()));
			}
		} catch (Exception e) {
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

}
