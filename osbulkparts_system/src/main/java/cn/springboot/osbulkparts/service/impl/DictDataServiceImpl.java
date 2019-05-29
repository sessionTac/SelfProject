package cn.springboot.osbulkparts.service.impl;

import java.util.List;

import cn.springboot.osbulkparts.entity.TDictTypeEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.ConstantMessageInfo;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.DictDataSettingService;

@Service
public class DictDataServiceImpl implements DictDataSettingService {

	@Autowired
	private TDictDataDao tdictDataDao;
	
	@Override
	public CommonResultInfo<TDictDataEntity> getDictDataList(TDictDataEntity tdictTypeEntity, int pageNumber,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TDictDataEntity> getDictDataInfo(String dictTypeId, int pageNumber,
															 int pageSize) {
		CommonResultInfo<TDictDataEntity> result = new CommonResultInfo<TDictDataEntity>();
		try {
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<TDictDataEntity> pageInfo = new PageInfo<>(
					tdictDataDao.selectByPrimaryKey(dictTypeId));
			result.setResultInfo(pageInfo);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
		} catch (Exception e) {
			result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			result.setMessage(ConstantMessageInfo.SERVICE_ERROR);
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@Override
	public CommonResultInfo<?> addUserInfo(TDictDataEntity tdictDataEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> updateUserInfo(TDictDataEntity tdictDataEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonResultInfo<?> deleteUserInfo(TDictDataEntity tdictDataEntity, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}
}
