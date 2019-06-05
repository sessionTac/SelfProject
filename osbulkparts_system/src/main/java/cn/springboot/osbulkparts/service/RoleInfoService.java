package cn.springboot.osbulkparts.service;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
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

	/**
	 * 根据角色id查询权限
	 * @param roleId
	 * @return
	 */
	Object findPowerByRoleId(String roleId);

	/**
	 * 添加权限
	 * @param list
	 * @return
	 */
	Object insertPower(List<Integer> functionIds, String roleId, HttpServletRequest request, Authentication auth);

    /**
     * 根据角色id获取角色的详细信息
     */
    CommonResultInfo<MRoleInfoEntity> getRoleInfo(String roleId);
}
