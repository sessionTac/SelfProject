package cn.springboot.osbulkparts.service.impl;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.user.TUserRoleRelationDao;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.TUserRoleRelationEntity;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private MUserInfoDao muserInfoDao;

	@Autowired
	private TDictDataDao tDictDataDao;

	@Autowired
	private TUserRoleRelationDao tUserRoleRelationDao;

    @Autowired
    private MRoleInfoDao mRoleInfoDao;

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
			e.printStackTrace();
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
			TDictDataEntity tDictDataEntity = new TDictDataEntity();
			tDictDataEntity.setDictTypeCode("usertype");
			map.put("userType",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			tDictDataEntity.setDictTypeCode("userstatus");
			map.put("userStatus",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
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
	public CommonResultInfo<TUserRoleRelationEntity> findRoleByUserId(String userId) {
		CommonResultInfo<TUserRoleRelationEntity> result = new CommonResultInfo<TUserRoleRelationEntity>();
		try {
			List<TUserRoleRelationEntity> userInfo = tUserRoleRelationDao.findRoleByUserId(userId);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResultList(userInfo);
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
    public CommonResultInfo<MRoleInfoEntity> findAllRole(MRoleInfoEntity mRoleInfoEntity) {
        CommonResultInfo<MRoleInfoEntity> result = new CommonResultInfo<MRoleInfoEntity>();
        try {
            List<MRoleInfoEntity> list = mRoleInfoDao.selectRoleInfoList(mRoleInfoEntity);
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            result.setResultList(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage(""));
            result.setException(e.getMessage().toString());
        } finally {
            return result;
        }
    }
    @SuppressWarnings("finally")
    @Transactional
    @Override
    public Object insertRole(List<Integer> roleIds, String userId, Authentication auth) {
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
        CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
            tUserRoleRelationDao.deleteById(userId);
            int r = 0;
            if (!roleIds.isEmpty()) {
                r = tUserRoleRelationDao.insertList(roleIds, userId, principal.getUserId());
            }
            if (r == roleIds.size()) {
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.ROLE.getTypeName()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
            throw new RuntimeException();
        } finally {
            return result;
        }
    }
    @SuppressWarnings("finally")
    @Override
    public CommonResultInfo<?> checkInfo(MUserInfoEntity mUserInfoEntity, String checkFlag) {
        CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
            MUserInfoEntity checkEntity = new MUserInfoEntity();
            checkEntity.setUserName(mUserInfoEntity.getUserName());
            List<MUserInfoEntity> checkList = muserInfoDao.checkingAndVersion(checkEntity);
            if (checkFlag.equals("add")) {
                if (checkList.size() == 0) {
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                } else {
                    result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.USER_NAME.getTypeName()));
                }
            } else if (checkFlag.equals("edit")) {
                if (
                        (checkList.size() == 0)
                                ||
                                ((checkList.size() == 1) && (mUserInfoEntity.getUserId().equals(checkList.get(0).getUserId())))
                ) {
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                } else {
                    result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.USER_NAME.getTypeName()));
                }
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
	public CommonResultInfo<?> resetPassword(MUserInfoEntity userInfoEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MUserInfoEntity mUserInfoEntity = new MUserInfoEntity();
		try {
			mUserInfoEntity.setUserId(userInfoEntity.getUserId());
			mUserInfoEntity.setPassword(encoder.encode("123456"));
			mUserInfoEntity.setUpdateUser(principal.getUserId());
			int returnInt = muserInfoDao.updateByPrimaryKeySelective(mUserInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.resetPassword.success"));
			}else {
				result.setMessage(messageBean.getMessage("common.resetPassword.failed"));
			}
		} catch (Exception e) {
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}
	@Transactional
	@Override
	public CommonResultInfo<?> changePassword(MUserInfoEntity userInfoEntity, String oldPassword, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MUserInfoEntity mUserInfoEntity = new MUserInfoEntity();
		mUserInfoEntity.setUpdateUser(principal.getUserId());

		try {
			String password = muserInfoDao.selectPass(userInfoEntity.getUserId());//获取数据库中的加密的password
			//判断是否一致
			BCryptPasswordEncoder encryptor = new BCryptPasswordEncoder(10);
			boolean flag = encryptor.matches(oldPassword,password);
			if(!flag) {
				result.setMessage(messageBean.getMessage("common.changePassword.error"));
			}else {
				String plainPassword = userInfoEntity.getPassword();
				String encryptedPassword = encryptor.encode(plainPassword);//加密后的密码
				int num = muserInfoDao.changePass(userInfoEntity.getUserId(), encryptedPassword);
				if(num == 1) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.changePassword.success"));
				}else {
					result.setMessage(messageBean.getMessage("common.changePassword.failed"));
				}
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
		try {
            MUserInfoEntity mUserInfoEntityVersion= new MUserInfoEntity();
            mUserInfoEntityVersion.setVersion(mUserInfoEntity.getVersion());
            mUserInfoEntityVersion.setUserId(mUserInfoEntity.getUserId());
            //校验 version 排他  (根据id和version)
            List<MUserInfoEntity> checkListVersion=muserInfoDao.checkingAndVersion(mUserInfoEntityVersion);
			if(checkListVersion.size()==1){
                mUserInfoEntity.setUpdateUser(principal.getUserId());
                int returnInt = muserInfoDao.updateByPrimaryKeySelective(mUserInfoEntity);
                if (returnInt > 0) {
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                    result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.USER.getTypeName()));
                }
			}else {
				result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.USER.getTypeName()));
			}

		} catch (Exception e) {
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}
	@Transactional
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
				tUserRoleRelationDao.deleteById(userId);
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.USER.getTypeName()));
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
            String userUUID = CommonSqlUtils.getUUID32();
            mUserInfoEntity.setUserId(userUUID);
            mUserInfoEntity.setPassword(encoder.encode(mUserInfoEntity.getPassword()));
            mUserInfoEntity.setCreateUser(principal.getUserId());
            mUserInfoEntity.setIsDelete(0);
            mUserInfoEntity.setVersion(1);
            int returnInt = muserInfoDao.insertSelective(mUserInfoEntity);
            if (returnInt > 0) {
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.USER.getTypeName()));
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
