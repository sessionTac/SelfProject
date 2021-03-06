package cn.springboot.osbulkparts.controller.user;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.OSLanguage;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.RoleInfoService;
import cn.springboot.osbulkparts.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> findOptions(@RequestHeader String lang){
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = roleInfoService.getOptions(lang, OSLanguage.localeToVueSuffix(lang));
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
			@RequestParam(defaultValue="50") int pageSize,@RequestHeader String lang){
		CommonResultInfo<MRoleInfoEntity> result = roleInfoService.getRoleInfoList(mRoleInfoEntity,pageNum,pageSize,lang,OSLanguage.localeToVueSuffix(lang));
		return result;
	}
	/**
	 * 查询权限树结构
	 * @return
	 */
	@GetMapping("/getTree")
	public Object getTreeForMaintain(@RequestHeader String lang) {
		Map<String, Object> map = new HashMap<>();
		map.put("maintainTree", roleInfoService.getTree(lang,OSLanguage.localeToVueSuffix(lang)));//维护平台树结构
		return map;
	}

	/**
	 *  根据角色id查询权限
	 * @param roleId
	 * @return
	 */
	@GetMapping("/getPower/{roleId}")
	public Object getPower(@PathVariable String roleId,@RequestHeader String lang) {
		return roleInfoService.findPowerByRoleId(roleId,OSLanguage.localeToVueSuffix(lang));
	}


	@Data
	public static class InsertFunctionForm {
		List<Integer> functionIds;
		String roleId;
	}
	/**
	 * 添加权限
	 */
	@PostMapping("/insertPower")
	public Object insertPower(@RequestBody InsertFunctionForm form, HttpServletRequest request, Authentication auth,@RequestHeader String lang) {

		return roleInfoService.insertPower(form.functionIds, form.roleId,request,auth,OSLanguage.localeToVueSuffix(lang));
	}

    @ApiOperation(value="获取角色详细信息", notes="根据角色Id来获取角色详细信息")
    @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/getRoleInfo/{roleId}")
    public CommonResultInfo<MRoleInfoEntity> getRoleInfo(@PathVariable String roleId,@RequestHeader String lang){
        log.info("getUserInfo is started.Paramater is userID["+roleId+"]");
        CommonResultInfo<MRoleInfoEntity> result = roleInfoService.getRoleInfo(roleId,lang,OSLanguage.localeToVueSuffix(lang));
        return result;
    }

	@ApiOperation(value="校验角色名是否重复", notes="校验角色名是否重复")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mRoleInfoEntity", value = "角色信息实体对象", required = true, dataType = "body", paramType = "body"),
			@ApiImplicitParam(name = "checkFlag", value = "判断是添加校验还是修改校验的标志", required = true, dataType = "body", paramType = "body"),
	})
	@GetMapping("/checkRoleInfo")
	public CommonResultInfo<?> checkInfo(MRoleInfoEntity mRoleInfoEntity, String checkFlag,@RequestHeader String lang){
		return roleInfoService.checkInfo(mRoleInfoEntity,checkFlag,OSLanguage.localeToVueSuffix(lang));
	}
    @ApiOperation(value="更新角色", notes="更新角色信息")
    @ApiImplicitParam(name = "mRoleInfoEntity", value = "用户信息实体对象", required = true, dataType = "body", paramType = "body")
    @PutMapping("/updateRole")
    public CommonResultInfo<?> updateRoleInfo(@RequestBody MRoleInfoEntity mRoleInfoEntity,Authentication auth,@RequestHeader String lang){
        CommonResultInfo<?> result = roleInfoService.updateRoleInfo(mRoleInfoEntity, auth,OSLanguage.localeToVueSuffix(lang));
        return result;
    }
    @ApiOperation(value="删除角色", notes="删除角色（逻辑删除）")
    @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/deleteRole/{roleId}")
    public CommonResultInfo<?> deleteRoleInfoById(@PathVariable String roleId,Authentication auth,@RequestHeader String lang){
        CommonResultInfo<?> result = roleInfoService.deleteRoleInfo(roleId, auth,OSLanguage.localeToVueSuffix(lang));
        return result;
    }
    @ApiOperation(value="添加角色", notes="添加角色")
    @ApiImplicitParam(name = "mRoleInfoEntity", value = "角色信息实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/addRole")
    public CommonResultInfo<?> addRoleInfo(@RequestBody MRoleInfoEntity mRoleInfoEntity, Authentication auth,@RequestHeader String lang){
        CommonResultInfo<?> result = roleInfoService.addRoleInfo(mRoleInfoEntity, auth,OSLanguage.localeToVueSuffix(lang));
        return result;
    }
}
