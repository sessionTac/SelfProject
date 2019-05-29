package cn.springboot.osbulkparts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.ConstantMessageInfo;
import cn.springboot.osbulkparts.dao.system.TDictTypeDao;
import cn.springboot.osbulkparts.entity.TDictTypeEntity;
import cn.springboot.osbulkparts.service.DictTypeSettingService;

@Service
public class DictTypeServiceImpl implements DictTypeSettingService {

	@Autowired
	private TDictTypeDao tdictTypeDao;
	
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
			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
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
			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TDictTypeEntity> getDictTypeInfo(String dictTypeId) {
		CommonResultInfo<TDictTypeEntity> result = new CommonResultInfo<TDictTypeEntity>();
		try {
			TDictTypeEntity tdictTypeInfo = tdictTypeDao.selectByPrimaryKey(dictTypeId);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResult(tdictTypeInfo);
		} catch (Exception e) {
			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@Override
	public CommonResultInfo<?> addDictTypeInfo(TDictTypeEntity tdictTypeEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
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
