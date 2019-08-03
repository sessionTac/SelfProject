package cn.springboot.osbulkparts.controller.user;

import java.util.List;
import java.util.Map;

import cn.springboot.osbulkparts.common.OSLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TUserRoleRelationEntity;
import cn.springboot.osbulkparts.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@ApiOperation(value="获取用户列表信息", notes="查询所有用户的列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mUserInfoEntity", value = "用户信息实体对象", required = true, dataType = "body", paramType = "body"),
			@ApiImplicitParam(name = "pageNum", value = "当前页码(默认1)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页数据条数(默认10)", required = true, dataType = "String", paramType = "query")
	})
	@GetMapping("/getUserInfoList")
	public CommonResultInfo<MUserInfoEntity> getUserInfoList(
			MUserInfoEntity muserInfoEntity,
			@RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="50") int pageSize,@RequestHeader String lang){
		muserInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
		CommonResultInfo<MUserInfoEntity> result = userInfoService.getUserInfoList(muserInfoEntity,pageNum,pageSize);
		return result;
	}
	
	@ApiOperation(value="获取用户详细信息", notes="根据用户Id来获取用户详细信息")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path")
	@GetMapping("/getUserInfo/{userId}")
	public CommonResultInfo<MUserInfoEntity> getUserInfo(@PathVariable String userId,@RequestHeader String lang){
		log.info("getUserInfo is started.Paramater is userID["+userId+"]");
		CommonResultInfo<MUserInfoEntity> result = userInfoService.getUserInfo(userId,lang);
		return result;
	}
	
	@ApiOperation(value="获取用户详细信息的下拉选信息", notes="获取下拉选信息")
	@GetMapping("/getOptions")
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> findOptions(@RequestHeader String lang){
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = userInfoService.getOptions(lang);
		return  result;
	}

	@ApiOperation(value="添加用户", notes="添加用户")
	@ApiImplicitParam(name = "muserInfoEntity", value = "用户信息实体对象", required = true, dataType = "body", paramType = "body")
	@PostMapping("/addUser")
	public CommonResultInfo<?> addUserInfo(@RequestBody MUserInfoEntity muserInfoEntity, Authentication auth){
		CommonResultInfo<?> result = userInfoService.addUserInfo(muserInfoEntity, auth);
		return result;
	}
	
	@ApiOperation(value="更新用户", notes="更新用户信息")
	@ApiImplicitParam(name = "muserInfoEntity", value = "用户信息实体对象", required = true, dataType = "body", paramType = "body")
	@PutMapping("/updateUser")
	public CommonResultInfo<?> updateUserInfo(@RequestBody MUserInfoEntity muserInfoEntity,Authentication auth){
		CommonResultInfo<?> result = userInfoService.updateUserInfo(muserInfoEntity, auth);
		return result;
	}
	
	@ApiOperation(value="删除用户", notes="删除用户（逻辑删除）")
	@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "path")
	@DeleteMapping("/deleteUser/{userId}")
	public CommonResultInfo<?> deleteUserInfoById(@PathVariable String userId,Authentication auth){
		CommonResultInfo<?> result = userInfoService.deleteUserInfo(userId, auth);
		return result;
	}

	@ApiOperation(value="获取该用户下角色列表信息", notes="根据用户Id来获取该用户下所有角色")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path")
	@GetMapping("/findRole/{userId}")
	public CommonResultInfo<TUserRoleRelationEntity> findRoleByUserId(@PathVariable String userId){
		return userInfoService.findRoleByUserId(userId);
	}
	@ApiOperation(value="获取所有角色列表信息", notes="获取所有角色")
	@ApiImplicitParam(name = "mRoleInfoEntity", value = "角色实体类", required = true, dataType = "body", paramType = "query")
	@GetMapping("/findAllRole")
	public CommonResultInfo<MRoleInfoEntity> findAllRole (MRoleInfoEntity mRoleInfoEntity,@RequestHeader String lang){
		mRoleInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
		return userInfoService.findAllRole(mRoleInfoEntity);
	}

	@Data
	public static class InsertRoleForm {
		List<String> roleIds;
		String userId;
	}
	/**
	 * 添加角色
	 * @return
	 */
	@ApiOperation(value="给用户添加角色", notes="给用户添加权限")
	@ApiImplicitParam(name = "InsertRoleForm", value = "添加权限内部类（user Id和roleIds集合）", required = true, dataType = "body", paramType = "body")
	@PostMapping("/insertRole")
	public Object insertPower(@RequestBody InsertRoleForm form, Authentication auth) {
		return userInfoService.insertRole(form.roleIds, form.userId,auth);
	}

	@ApiOperation(value="校验用户名是否重复", notes="校验用户名是否重复")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mRoleInfoEntity", value = "用户信息实体对象", required = true, dataType = "body", paramType = "body"),
			@ApiImplicitParam(name = "checkFlag", value = "判断是添加校验还是修改校验的标志", required = true, dataType = "body", paramType = "body"),
	})
	@GetMapping("/checkUserInfo")
	public CommonResultInfo<?> checkInfo(MUserInfoEntity mUserInfoEntity, String checkFlag){
		return userInfoService.checkInfo(mUserInfoEntity,checkFlag);
	}

	/**
	 * 重置密码
	 * @param userInfoEntity
	 * @return
	 */
	@PutMapping("/resetPass")
	public Object resetPass(@RequestBody MUserInfoEntity userInfoEntity, Authentication auth) {
		return userInfoService.resetPassword(userInfoEntity,auth);
	}
	@Data
	public static class ChangePassForm {
		MUserInfoEntity userInfoEntity;
		String inputPassword;//旧密码
	}

	/**
	 * 修改密码
	 * @return
	 */
	@PutMapping("/changePass")
	public CommonResultInfo<?> changePass(@RequestBody ChangePassForm form, Authentication auth) {
		return userInfoService.changePassword(form.userInfoEntity,form.inputPassword,auth);
	}

}
