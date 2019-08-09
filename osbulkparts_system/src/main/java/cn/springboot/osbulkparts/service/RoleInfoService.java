package cn.springboot.osbulkparts.service;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface RoleInfoService {
	CommonResultInfo<MRoleInfoEntity> getRoleInfoList(MRoleInfoEntity mRoleInfoEntity, int pageNumber, int pageSize, String lang, Locale locale);

	CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions(String lang,Locale locale);

	/**
	 * 查询树结构
	 * @return
	 */
	Object getTree( String lang,Locale locale);

	/**
	 * 根据角色id查询权限
	 * @param roleId
	 * @return
	 */
	Object findPowerByRoleId(String roleId,Locale locale);

	/**
	 * 添加权限
	 * @param list
	 * @return
	 */
	Object insertPower(List<Integer> functionIds, String roleId, HttpServletRequest request, Authentication auth,Locale locale);

    /**
     * 根据角色id获取角色的详细信息
     */
    CommonResultInfo<MRoleInfoEntity> getRoleInfo(String roleId,String lang,Locale locale);
	/**
	 * 校验角色信息
	 */
	CommonResultInfo<?> checkInfo(MRoleInfoEntity mRoleInfoEntity,String checkFlag,Locale locale);
    /**
     * 更新角色信息
     */
    CommonResultInfo<?> updateRoleInfo(MRoleInfoEntity mRoleInfoEntity,Authentication auth,Locale locale);

    CommonResultInfo<?> deleteRoleInfo(String roleId, Authentication auth,Locale locale);

    CommonResultInfo<?> addRoleInfo(MRoleInfoEntity mRoleInfoEntity, Authentication auth,Locale locale);
}
