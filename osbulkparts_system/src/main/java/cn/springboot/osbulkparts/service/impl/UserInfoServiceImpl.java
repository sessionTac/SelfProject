package cn.springboot.osbulkparts.service.impl;

import cn.springboot.osbulkparts.common.ConstantMessageInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MUserInfoDao;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.service.UserInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private MUserInfoDao muserInfoDao;

	@Autowired
	private TDictDataDao tDictDataDao;
	
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
			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
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
			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
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
			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
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
			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
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
		//校验 用户名是否重复
		Map<String,String> checkMap=muserInfoDao.selectCountByUserName(mUserInfoEntity.getUserName());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			if (    ("0".equals(String.valueOf(checkMap.get("num"))))
					||
					( ( "1".equals(String.valueOf(checkMap.get("num"))) ) && ( mUserInfoEntity.getUserId().equals(String.valueOf(checkMap.get("id"))) ) )
			){
				mUserInfoEntity.setUpdateUser(principal.getUserId());
				int returnInt = muserInfoDao.updateByPrimaryKeySelective(mUserInfoEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage("修改成功");
				}
			} else {
				result.setMessage("用户名重复");
			}
		} catch (Exception e) {
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
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
				result.setMessage("删除成功");
			}

		} catch (Exception e) {
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
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
		//校验 用户名是否重复
		Map<String,String> checkMap=muserInfoDao.selectCountByUserName(mUserInfoEntity.getUserName());
		try {
			if ("0".equals(String.valueOf(checkMap.get("num")))){
				String userUUID = CommonSqlUtils.getUUID32();
				mUserInfoEntity.setUserId(userUUID);
				mUserInfoEntity.setPassword(encoder.encode(mUserInfoEntity.getPassword()));
				mUserInfoEntity.setCreateUser(principal.getUserId());
				mUserInfoEntity.setIsDelete(0);
				mUserInfoEntity.setVersion(1);
				int returnInt = muserInfoDao.insertSelective(mUserInfoEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage("添加成功");
				}
			}else {
				result.setMessage("已经存在用户名为："+mUserInfoEntity.getUserName()+"的用户");
			}
		} catch (Exception e) {
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

}
