package cn.springboot.osbulkparts.service.impl;

import java.util.List;
import java.util.Locale;

import cn.springboot.osbulkparts.common.OSLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.system.TDictTypeDao;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TDictTypeEntity;
import cn.springboot.osbulkparts.service.DictTypeSettingService;

@Service
public class DictTypeServiceImpl implements DictTypeSettingService {

	@Autowired
	private TDictTypeDao tdictTypeDao;
	
	@Autowired
	private TDictDataDao tdictDataDao;
	
	@Autowired
	private I18nMessageBean messageBean;
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TDictTypeEntity> getDictTypeList(TDictTypeEntity tdictTypeEntity, int pageNumber,
															 int pageSize, Locale locale) {
		messageBean.setLocale(null,null,locale);
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
	public CommonResultInfo<TDictTypeEntity> getDictType(TDictTypeEntity tdictTypeEntity,Locale locale){
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<TDictTypeEntity> result = new CommonResultInfo<TDictTypeEntity>();
		try {
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
	public CommonResultInfo<TDictTypeEntity> getDictTypeInfo(String dictTypeId,String lang,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<TDictTypeEntity> result = new CommonResultInfo<TDictTypeEntity>();
		TDictTypeEntity dicpTypeEntity = new TDictTypeEntity();
		dicpTypeEntity.setDictTypeId(dictTypeId);
		dicpTypeEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
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
	public CommonResultInfo<?> addDictTypeInfo(TDictTypeEntity tdictTypeEntity, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			String dictUUID = CommonSqlUtils.getUUID32();
			tdictTypeEntity.setDictTypeId(dictUUID);
			tdictTypeEntity.setCreateUser(principal.getUserId());
			tdictTypeEntity.setIsDelete(0);
			tdictTypeEntity.setVersion(1);
			int returnInt = tdictTypeDao.insertSelective(tdictTypeEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.DICT_TYPE.getTypeName(locale)));
			}
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
	public CommonResultInfo<?> updateDictType(TDictTypeEntity tdictTypeEntity, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			// 验证版本号
			if(checkVersion(tdictTypeEntity)) {
				// 更新处理
				tdictTypeEntity.setUpdateUser(principal.getUserId());
				tdictTypeEntity.setVersion(tdictTypeEntity.getVersion()+1);
				int returnInt = tdictTypeDao.updateByPrimaryKeySelective(tdictTypeEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.DICT_TYPE.getTypeName(locale)));
				}
			}
			else {
				result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.DICT_TYPE.getTypeName(locale)));
			}
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
	@Transactional
	@Override
	public CommonResultInfo<?> deleteDictType(String dictTypeId, Authentication auth,String lang,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			TDictTypeEntity dictTypeParam = new TDictTypeEntity();
			dictTypeParam.setDictTypeId(dictTypeId);
			dictTypeParam.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
			// 确认删除对象存在
			List<TDictTypeEntity> tdictTypeEntityLst = tdictTypeDao.selectByPrimaryKey(dictTypeParam);
			if(tdictTypeEntityLst.size() == 0) {
				result.setMessage(messageBean.getMessage("common.delete.failed", CommonConstantEnum.DICT_TYPE.getTypeName(locale)));
			}else {
				// 删除处理（逻辑）
				dictTypeParam.setUpdateUser(principal.getUserId());
				dictTypeParam.setIsDelete(1);
				int returnInt = tdictTypeDao.updateByPrimaryKeySelective(dictTypeParam);
				if (returnInt > 0) {
					// 删除字典类型下所有字典数据
					TDictDataEntity tdictDataEntity = new TDictDataEntity();
					tdictDataEntity.setDictTypeCode(tdictTypeEntityLst.get(0).getCode());
					tdictDataEntity.setDeleteFlg(1);
					tdictDataEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
					tdictDataDao.updateForDeleteLogicByTypeCode(tdictDataEntity);
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.DICT_TYPE.getTypeName(locale)));
				}
			}
		} catch (Exception e) {
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
			throw new RuntimeException();
		} finally {
			return result;
		}
	}
	
	/**
	 * 名称重复验证
	 */
	@Override
	public CommonResultInfo<?> checkNameRepeat(TDictTypeEntity tdictTypeEntity, String checkFlag,Locale locale){
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		TDictTypeEntity tdictTypeEntityCheckName = new TDictTypeEntity();
		tdictTypeEntityCheckName.setName(tdictTypeEntity.getName());
		tdictTypeEntityCheckName.setLanguageFlag(tdictTypeEntity.getLanguageFlag());
		List<TDictTypeEntity> resultLst=tdictTypeDao.selectByPrimaryKey(tdictTypeEntityCheckName);
		if (checkFlag.equals("add")) {
			if (resultLst.size() == 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
			} else {
				result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.DICT_TYPE.getTypeName(locale)));
			}
		} else if (checkFlag.equals("edit")) {
			if (
					(resultLst.size() == 0)
							||
							((resultLst.size() == 1) && (tdictTypeEntity.getDictTypeId().equals(resultLst.get(0).getDictTypeId())))
			) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
			} else {
				result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.DICT_TYPE.getTypeName(locale)));
			}
		}
		return result;
	}
	
	/**
	 * 编码重复验证
	 */
	@Override
	public CommonResultInfo<?> checkCodeRepeat(TDictTypeEntity tdictTypeEntity, String checkFlag,Locale locale){
		messageBean.setLocale(null,null,locale);

		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		TDictTypeEntity tdictTypeEntityCheckCode = new TDictTypeEntity();
		tdictTypeEntityCheckCode.setCode(tdictTypeEntity.getCode());
		tdictTypeEntityCheckCode.setLanguageFlag(tdictTypeEntity.getLanguageFlag());
		List<TDictTypeEntity> resultLst=tdictTypeDao.selectByPrimaryKey(tdictTypeEntityCheckCode);
		if (checkFlag.equals("add")) {
			if (resultLst.size() == 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
			} else {
				result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.DICT_TYPE_CODE.getTypeName(locale)));
			}
		} else if (checkFlag.equals("edit")) {
			if (
					(resultLst.size() == 0)
							||
							((resultLst.size() == 1) && (tdictTypeEntity.getDictTypeId().equals(resultLst.get(0).getDictTypeId())))
			) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
			} else {
				result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.DICT_TYPE_CODE.getTypeName(locale)));
			}
		}
		return result;
	}
	
	/****Private Method****/
	/**
	 * 验证版本号
	 */
	private boolean checkVersion(TDictTypeEntity tdictTypeEntity) {
		TDictTypeEntity tdictTypeEntityParam = new TDictTypeEntity();
		tdictTypeEntityParam.setDictTypeId(tdictTypeEntity.getDictTypeId());
		tdictTypeEntityParam.setVersion(tdictTypeEntity.getVersion());
		tdictTypeEntityParam.setLanguageFlag(tdictTypeEntity.getLanguageFlag());
		List<TDictTypeEntity> resultVersionLst=tdictTypeDao.selectByPrimaryKey(tdictTypeEntityParam);
		if(resultVersionLst.size()>0 && resultVersionLst.get(0) != null) {
			return true;
		}
		return false;
	}
}
