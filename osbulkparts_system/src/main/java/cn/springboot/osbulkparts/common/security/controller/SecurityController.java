package cn.springboot.osbulkparts.common.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.springboot.osbulkparts.common.security.config.JwtTokenProperty;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.security.service.SecurityService;
import cn.springboot.osbulkparts.common.security.utils.JwtTokenUtils;

/***
 * 暂不考虑权限，方法暂不使用
 * @author session
 *
 */
@RestController
public class SecurityController {
	@Autowired
	private SecurityService service;
	
	@Autowired
    private JwtTokenProperty jwtTokenProperty;
//	/**
//	 * 获取登录账号的权限
//	 * 此处权限通过Spring Security中取得。后期可改成自己从数据库取值。spring security只负责基本信息
//	 * @param userId
//	 */
//	@GetMapping("/auth/user/functions")
//	public Object getFunctions(@RequestParam("roleId") String roleId,Authentication auth){
//		// 获取当前用户，在统计平台可以操作的菜单内容
//		List<MFunctionEntity> list = service.getFunctions(roleId);
//
//		// 将权限对象中的权限取出来，组成一个字符类型的列表
//		List<String> functions = null;
//		if(null!=list && list.size()!=0){
//			functions = list.stream().filter(p->p!=null).map(p->(p.getFunctionCode())).collect(Collectors.toList());
//		}else {
//			functions = new ArrayList<String>();
//		}
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("functions", functions);
//		return map;
//	}
	
	/**
	 * 根据登录时选择的角色，重新生成一个token
	 * @param roleId
	 * @param auth
	 * @return
	 */
	@GetMapping("/auth/token/{roleId}")
	public JSONObject refreshTokenByRoleId(@PathVariable("roleId") String roleId,Authentication auth){
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity) auth.getPrincipal();
		principal.setRoleIdSelected(roleId);
		
		String token = JwtTokenUtils.createTokenByRoleId(principal, jwtTokenProperty, false);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("token", token);
		return jsonObject;
	}
	
}
