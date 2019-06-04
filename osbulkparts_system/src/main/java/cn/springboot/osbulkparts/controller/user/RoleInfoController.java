package cn.springboot.osbulkparts.controller.user;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.RoleInfoService;
import cn.springboot.osbulkparts.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleInfoController {
	@Autowired
	private RoleInfoService roleInfoService;

	@ApiOperation(value="获取角色权限中角色所属的下拉选信息", notes="获取下拉选信息")
	@GetMapping("/getOptions")
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> findOptions(){
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = roleInfoService.getOptions();
		return  result;
	}

	@ApiOperation(value="获取用户列表信息", notes="查询所有用户的列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mRoleInfoEntity", value = "角色信息实体对象", required = true, dataType = "body", paramType = "body"),
			@ApiImplicitParam(name = "pageNum", value = "当前页码(默认1)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页数据条数(默认10)", required = true, dataType = "String", paramType = "query")
	})
	@GetMapping("/getRoleInfoList")
	public CommonResultInfo<MRoleInfoEntity> getUserInfoList(
			MRoleInfoEntity mRoleInfoEntity,
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue="10") int pageSize){
		CommonResultInfo<MRoleInfoEntity> result = roleInfoService.getRoleInfoList(mRoleInfoEntity,pageNum,pageSize);
		return result;
	}
	/**
	 * 查询权限树结构
	 * @return
	 */
	@GetMapping("/getTree")
	public Object getTreeForMaintain() {
		Map<String, Object> map = new HashMap<>();
		map.put("maintainTree", roleInfoService.getTree());//维护平台树结构
		return map;
	}

}
