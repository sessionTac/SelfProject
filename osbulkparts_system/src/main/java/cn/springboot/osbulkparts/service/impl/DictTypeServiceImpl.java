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
			String dictUUID = CommonSqlUtils.getUUID32();
			tdictTypeEntity.setDictTypeId(dictUUID);
			tdictTypeEntity.setCreateUser(principal.getUserId());
			tdictTypeEntity.setIsDelete(0);
			tdictTypeEntity.setVersion(1);
			int returnInt = tdictTypeDao.insertSelective(tdictTypeEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.DICT_TYPE.getTypeName()));
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
	public CommonResultInfo<?> updateDictType(TDictTypeEntity tdictTypeEntity, Authentication auth) {
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
					result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.DICT_TYPE.getTypeName()));
				}
			}
			else {
				result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.DICT_TYPE.getTypeName()));
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
	public CommonResultInfo<?> deleteDictType(String dictTypeId, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			TDictTypeEntity dictTypeParam = new TDictTypeEntity();
			dictTypeParam.setDictTypeId(dictTypeId);
			// 确认删除对象存在
			List<TDictTypeEntity> tdictTypeEntityLst = tdictTypeDao.selectByPrimaryKey(dictTypeParam);
			if(tdictTypeEntityLst.size() == 0) {
				result.setMessage(messageBean.getMessage("common.delete.failed", CommonConstantEnum.DICT_TYPE.getTypeName()));
			}else {
				// 删除处理（逻辑）
				dictTypeParam.setUpdateUser(principal.getUserId());
				dictTypeParam.setIsDelete(1);
				int returnInt = tdictTypeDao.updateByPrimaryKeySelective(dictTypeParam);
				if (returnInt > 0) {
					// 删除字典类型下所有字典数据
					TDictDataEntity tdictDataEntity = new TDictDataEntity();
					tdictDataEntity.setDictTypeCode(tdictTypeEntityLst.get(0).getCode());
					tdictDataDao.updateForDeleteLogicByTypeCode(tdictDataEntity);
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.DICT_TYPE.getTypeName()));
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
	
	/**
	 * 名称重复验证
	 */
	@Override
	public CommonResultInfo<?> checkNameRepeat(TDictTypeEntity tdictTypeEntity){
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		TDictTypeEntity tdictTypeEntityCheckName = new TDictTypeEntity();
		tdictTypeEntityCheckName.setName(tdictTypeEntity.getName());
		List<TDictTypeEntity> resultLst=tdictTypeDao.selectByPrimaryKey(tdictTypeEntityCheckName);
		if(resultLst.size()>0 && resultLst.get(0) != null) {
			result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.DICT_TYPE.getTypeName()));
		}else {
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
		}
		return result;
	}
	
	/**
	 * 编码重复验证
	 */
	@Override
	public CommonResultInfo<?> checkCodeRepeat(TDictTypeEntity tdictTypeEntity){
		CommonResultInfo<?> result = new CommonResultInfo<TDictTypeEntity>();
		TDictTypeEntity tdictTypeEntityCheckName = new TDictTypeEntity();
		tdictTypeEntityCheckName.setCode(tdictTypeEntity.getCode());
		List<TDictTypeEntity> resultLst=tdictTypeDao.selectByPrimaryKey(tdictTypeEntityCheckName);
		if(resultLst.size()>0 && resultLst.get(0) != null) {
			result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.DICT_TYPE_CODE.getTypeName()));
		}else {
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
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
		List<TDictTypeEntity> resultVersionLst=tdictTypeDao.selectByPrimaryKey(tdictTypeEntityParam);
		if(resultVersionLst.size()>0 && resultVersionLst.get(0) != null) {
			return true;
		}
		return false;
	}
}
