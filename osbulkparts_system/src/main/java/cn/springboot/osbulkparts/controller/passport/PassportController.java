package cn.springboot.osbulkparts.controller.passport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/passport")
public class PassportController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 查询用户的权限详情
	 * @param Authorization
	 * @return
	 */
	@ApiOperation(value="获取用户的权限详细信息", notes="根据Authorization中的用户名和角色ID获取用户的权限详细信息")
	@ApiImplicitParam(name = "Authorization", value = "登录用户信息", required = true, dataType = "header", paramType = "header")
	@GetMapping("/userinfo")
	public CommonResultInfo<MUserInfoEntity> findUserInfo(@RequestHeader("Authorization") String Authorization,Authentication auth) {
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		CommonResultInfo<MUserInfoEntity> result = userInfoService.findUserWithRoleAndFunc(principal.getUserName(), principal.getRoleIdSelected(),auth);
		return result;
	}
}
