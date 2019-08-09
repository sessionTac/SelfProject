package cn.springboot.osbulkparts.service.impl;

import java.util.List;
import java.util.Locale;

import cn.springboot.osbulkparts.common.OSLanguage;
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
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TDictTypeEntity;
import cn.springboot.osbulkparts.service.DictDataSettingService;

@Service
public class DictDataServiceImpl implements DictDataSettingService {

	@Autowired
	private TDictDataDao tdictDataDao;
	
	@Autowired
	private I18nMessageBean messageBean;
	
	@Override
	public CommonResultInfo<TDictDataEntity> getDictDataList(TDictDataEntity tdictDataEntity, int pageNumber,
															 int pageSize, Locale locale) {
		messageBean.setLocale(null,null,locale);
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TDictDataEntity> getDictDataInfo(String dictTypeCode, int pageNumber,
															 int pageSize, String lang,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<TDictDataEntity> result = new CommonResultInfo<TDictDataEntity>();
		try {
			PageHelper.startPage(pageNumber, pageSize);
			TDictDataEntity tdictDataEntityParam = new TDictDataEntity();
			tdictDataEntityParam.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
			tdictDataEntityParam.setDictTypeCode(dictTypeCode);
			PageInfo<TDictDataEntity> pageInfo = new PageInfo<>(
					tdictDataDao.selectByPrimaryKey(tdictDataEntityParam));
			result.setResultInfo(pageInfo);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
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
	public CommonResultInfo<TDictDataEntity> getDictDataInfoDetail(String id,String lang,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<TDictDataEntity> result = new CommonResultInfo<TDictDataEntity>();
		try {

			TDictDataEntity tdictDataEntityParam = new TDictDataEntity();
			tdictDataEntityParam.setId(id);
			tdictDataEntityParam.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
			List<TDictDataEntity> resultList = tdictDataDao.selectByPrimaryKey(tdictDataEntityParam);
			if (resultList.size()>0){
				result.setResult(resultList.get(0));
				result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			}else {
				result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.info.empty"));
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
	public CommonResultInfo<?> addDictData(TDictDataEntity tdictDataEntity, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TDictDataEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			String dictUUID = CommonSqlUtils.getUUID32();
			tdictDataEntity.setId(dictUUID);
			tdictDataEntity.setCreateUser(principal.getUserName());
			tdictDataEntity.setDeleteFlg(0);
			tdictDataEntity.setVersion(1);
			int returnInt = tdictDataDao.insertSelective(tdictDataEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.DICT_DATA.getTypeName(locale)));
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
	public CommonResultInfo<?> updateDictData(TDictDataEntity tdictDataEntity, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			// 验证版本号
			if(checkVersion(tdictDataEntity)) {
				// 更新处理
				tdictDataEntity.setUpdateUser(principal.getUserName());
				tdictDataEntity.setVersion(tdictDataEntity.getVersion()+1);
				int returnInt = tdictDataDao.updateByPrimaryKeySelective(tdictDataEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.DICT_DATA.getTypeName(locale)));
				}
			}
			else {
				result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.DICT_DATA.getTypeName(locale)));
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
	public CommonResultInfo<?> deleteDictData(TDictDataEntity tdictDataEntity, Authentication auth,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TDictDataEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			TDictDataEntity dictDataParam = new TDictDataEntity();
			dictDataParam.setId(tdictDataEntity.getId());
			// 确认删除对象存在
			List<TDictDataEntity> tdictDataEntityLst = tdictDataDao.selectByPrimaryKey(dictDataParam);
			if(tdictDataEntityLst.size() == 0) {
				result.setMessage(messageBean.getMessage("common.delete.failed", CommonConstantEnum.DICT_DATA.getTypeName(locale)));
			}else {
				// 删除处理（逻辑）
				dictDataParam.setUpdateUser(principal.getUserName());
				dictDataParam.setDeleteFlg(1);
				int returnInt = tdictDataDao.updateByPrimaryKeySelective(dictDataParam);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.DICT_DATA.getTypeName(locale)));
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

	@Override
	public CommonResultInfo<?> checkValue(TDictDataEntity tDictDataEntity,Locale locale) {
		messageBean.setLocale(null,null,locale);
		CommonResultInfo<?> result = new CommonResultInfo<TDictDataEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		try {
			List<TDictDataEntity> resultList=tdictDataDao.selectByPrimaryKey(tDictDataEntity);
			if(resultList.size()==0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
			}else if (resultList.size() != 0 && resultList.get(0).getId().equals(tDictDataEntity.getId())){
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
			} else {
				result.setMessage(messageBean.getMessage("common.info.empty"));
			}
		} catch (Exception e) {
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	/****Private Method****/
	/**
	 * 验证版本号
	 */
	private boolean checkVersion(TDictDataEntity tdictDataEntity) {
		TDictDataEntity tdictDataEntityParam = new TDictDataEntity();
		tdictDataEntityParam.setId(tdictDataEntity.getId());
		tdictDataEntityParam.setVersion(tdictDataEntity.getVersion());
		tdictDataEntityParam.setLanguageFlag(tdictDataEntity.getLanguageFlag());
		List<TDictDataEntity> resultVersionLst=tdictDataDao.selectByPrimaryKey(tdictDataEntityParam);
		if(resultVersionLst.size()>0 && resultVersionLst.get(0) != null) {
			return true;
		}
		return false;
	}
}
