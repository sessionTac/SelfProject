package cn.springboot.osbulkparts.controller.passport;

import cn.springboot.osbulkparts.entity.MFunctionInfoEntity;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
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

import java.util.*;
import java.util.stream.Collectors;

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
	public Map<String,Object> findUserInfo(@RequestHeader("Authorization") String Authorization, Authentication auth,@RequestHeader String lang) {
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		CommonResultInfo<MUserInfoEntity> result = userInfoService.findUserWithRoleAndFunc(principal.getUserName(), principal.getRoleIdSelected(),auth,lang);
		MUserInfoEntity userInfo=result.getResult();
		//获取当前用户的权限列表、地图区域权限、数据权限
		List<String> functions = new ArrayList<>();

		if(userInfo.getRoleList()!= null && userInfo.getRoleList().size() != 0) {
			MRoleInfoEntity role = userInfo.getRoleList().get(0);
			if(null != role.getFunctionList() && role.getFunctionList().size() != 0) {
				functions = role.getFunctionList().stream().map(MFunctionInfoEntity::getFunctionCode).filter(Objects::nonNull).collect(Collectors.toList());

			}
		}
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("functions", functions);
		resultMap.put("userName", userInfo.getUserName());
		resultMap.put("userId", userInfo.getUserId());
		resultMap.put("trueName", userInfo.getUserRealName());
		resultMap.put("tel", userInfo.getUserPhone());
		resultMap.put("email", userInfo.getUserMail());
		resultMap.put("userType", userInfo.getUserType());
		resultMap.put("userStatus", userInfo.getUserStatus());

		return  resultMap;
//		return new Document()
//				.append("functions", functions)
//				.append("userName", userInfo.getUserName())
//				.append("trueName", userInfo.getTrueName())
//				.append("map_region", map_region)
//				.append("data_region", data_region)
//				.append("tel",      userInfo.getTel())
//				.append("email", userInfo.getEmail())
//				.append("orgName", userInfo.getOrgName());
	}
}
