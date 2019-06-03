package cn.springboot.osbulkparts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictTypeDao;
import cn.springboot.osbulkparts.entity.TDictTypeEntity;
import cn.springboot.osbulkparts.service.DictTypeSettingService;

@Service
public class DictTypeServiceImpl implements DictTypeSettingService {

	@Autowired
	private TDictTypeDao tdictTypeDao;
	
	@Autowired
	private I18nMessageBean messageBean;
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TDictTypeEntity> getDictTypeList(TDictTypeEntity tdictTypeEntity, int pageNumber,
			int pageSize) {
		CommonResultInfo<TDictTypeEntity> result = new CommonResultInfo<TDictTypeEntity>();
		try {
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<TDictTypeEntity> pageInfo = new PageInfo<>(
					tdictTypeDao.getDictTypeList(tdictTypeEntity));
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
	public CommonResultInfo<TDictTypeEntity> getDictType(){
		CommonResultInfo<TDictTypeEntity> result = new CommonResultInfo<TDictTypeEntity>();
		try {
			TDictTypeEntity tdictTypeEntity = new TDictTypeEntity();
			List<TDictTypeEntity> tdictTypeInfoLst = tdictTypeDao.getDictTypeList(tdictTypeEntity);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResultList(tdictTypeInfoLst);
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
	public CommonResultInfo<TDictTypeEntity> getDictTypeInfo(String dictTypeId) {
		CommonResultInfo<TDictTypeEntity> result = new CommonResultInfo<TDictTypeEntity>();
		TDictTypeEntity dicpTypeEntity = new TDictTypeEntity();
		dicpTypeEntity.setDictTypeId(dictTypeId);
		try {
			List<TDictTypeEntity> tdictTypeInfoLst = tdictTypeDao.selectByPrimaryKey(dicpTypeEntity);
			if(tdictTypeInfoLst.size()>0) {dicpTypeEntity = tdictTypeInfoLst.get(0);}else {dicpTypeEntity = new TDictTypeEntity();}
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResult(dicpTypeEntity);
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
	public CommonResultInfo<?> addDictTypeInfo(TDictTypeEntity tdictTypeEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			//校验 用户名是否重复
			List<TDictTypeEntity> resultLst=tdictTypeDao.selectByPrimaryKey(tdictTypeEntity);
			if(resultLst.size()>0 && resultLst.get(0) != null) {
				result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.DICT_TYPE.getTypeName()));
			}
			String ticpUUID = CommonSqlUtils.getUUID32();
			tdictTypeEntity.setDictTypeId(ticpUUID);
			tdictTypeEntity.setCreateUser(principal.getUserId());
			tdictTypeEntity.setIsDelete(0);
			tdictTypeEntity.setVersion(1);
			int returnInt = tdictTypeDao.insertSelective(tdictTypeEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.add.sucess", CommonConstantEnum.DICT_TYPE.getTypeName()));
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
	public CommonResultInfo<?> updateDictType(TDictTypeEntity tdictTypeEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> deleteDictType(TDictTypeEntity tdictTypeEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
