package cn.springboot.osbulkparts.common.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springboot.osbulkparts.common.security.dao.SecurityDao;
import cn.springboot.osbulkparts.common.security.service.SecurityService;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private SecurityDao dao;

	@Override
	public MUserInfoEntity getUserByUsername(String username) {
		MUserInfoEntity user = dao.selectUserInfoToCheck(username);
		if (user == null) {
			return null;
		}
		return user;
//		List<MRoleEntity> roles = dao.selectRoles(user.getId(), platform);
//		user.setRoles(roles);
	}

//	@Override
//	public List<MFunctionEntity> getFunctions(String roleId) {
//		List<MFunctionEntity> fucntions = dao.selectFunctions(roleId);
//		return fucntions;
//	}
//
//	@Override
//	public void writeLog(String id, String userName) {
//		TOperationLogEntity logEntity = new TOperationLogEntity();
//		logEntity.setUserInfoId(id);
//		logEntity.setAction("0");
//		logEntity.setUserName(userName);
//		logEntity.setContent(userName+"登录了维护平台");
//		logEntity.setClientIp("0.0.0.0");
//
//		logDao.insertSelective(logEntity);
//	}

}
