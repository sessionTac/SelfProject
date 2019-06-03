package cn.springboot.osbulkparts.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@ApiOperation(value="获取用户列表信息", notes="查询所有用户的列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "muserInfoEntity", value = "用户信息实体对象", required = true, dataType = "body", paramType = "body"),
			@ApiImplicitParam(name = "pageNum", value = "当前页码(默认1)", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页数据条数(默认10)", required = true, dataType = "String", paramType = "query")
	})
	@GetMapping("/getUserInfoList")
	public CommonResultInfo<MUserInfoEntity> getUserInfoList(
			MUserInfoEntity muserInfoEntity,
			@RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="10") int pageSize){
		CommonResultInfo<MUserInfoEntity> result = userInfoService.getUserInfoList(muserInfoEntity,pageNum,pageSize);
		return result;
	}
	
	@ApiOperation(value="获取用户详细信息", notes="根据用户Id来获取用户详细信息")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path")
	@GetMapping("/getUserInfo/{userId}")
	public CommonResultInfo<MUserInfoEntity> getUserInfo(@PathVariable String userId){
		log.info("getUserInfo is started.Paramater is userID["+userId+"]");
		CommonResultInfo<MUserInfoEntity> result = userInfoService.getUserInfo(userId);
		return result;
	}
	
	@ApiOperation(value="获取用户详细信息的下拉选信息", notes="获取下拉选信息")
	@GetMapping("/getOptions")
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> findOptions(){
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = userInfoService.getOptions();
		return  result;
	}

	@ApiOperation(value="添加用户", notes="添加用户")
	@ApiImplicitParam(name = "muserInfoEntity", value = "用户信息实体对象", required = true, dataType = "body", paramType = "body")
	@PostMapping("/addUser")
	public CommonResultInfo<?> addUserInfo(MUserInfoEntity muserInfoEntity,Authentication auth){
		CommonResultInfo<?> result = userInfoService.addUserInfo(muserInfoEntity, auth);
		return result;
	}
	
	@ApiOperation(value="更新用户", notes="更新用户信息")
	@ApiImplicitParam(name = "muserInfoEntity", value = "用户信息实体对象", required = true, dataType = "body", paramType = "body")
	@PutMapping("/updateUser")
	public CommonResultInfo<?> updateUserInfo(MUserInfoEntity muserInfoEntity,Authentication auth){
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
}
