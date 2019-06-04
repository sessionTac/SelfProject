package cn.springboot.osbulkparts.service;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface RoleInfoService {
	CommonResultInfo<MRoleInfoEntity> getRoleInfoList(MRoleInfoEntity mRoleInfoEntity, int pageNumber, int pageSize);

	CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions();

	/**
	 * 查询树结构
	 * @return
	 */
	Object getTree();
}
