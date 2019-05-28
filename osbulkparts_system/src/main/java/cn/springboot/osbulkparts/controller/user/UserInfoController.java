package cn.springboot.osbulkparts.controller.user;

import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.activemq.CommonActiveMqMange;
import cn.springboot.osbulkparts.service.UserInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

//	@Value("${activemq.topic.realtime}")
//	private String topicRealtime;
//	
//	@Value("${activemq.topic.delay}")
//	private String topic_delay;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private CommonActiveMqMange commonActiveMqMange; // ActiveMQ公共类
	
	@ApiOperation(value="获取用户列表信息", notes="查询所有用户的列表")
	@GetMapping("/getUserInfoList")
	public CommonResultInfo<MUserInfoEntity> getUserInfoList(
			MUserInfoEntity muserInfoEntity,
			@RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue="5") int pageSize){
		CommonResultInfo<MUserInfoEntity> result = userInfoService.getUserInfoList(muserInfoEntity,pageNumber,pageSize);
		return result;
	}
	
	@ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
	@GetMapping("/getUserInfo/{userId}")
	public CommonResultInfo<MUserInfoEntity> getUserInfo(@PathVariable String userId){
		log.info("getUserInfo is started.Paramater is userID["+userId+"]");
		CommonResultInfo<MUserInfoEntity> result = userInfoService.getUserInfo(userId);
//        Destination destination = new ActiveMQQueue(topicRealtime);
//        //传入队列以及值
//        commonActiveMqMange.send(destination, result.getResult().toString());
		return result;
	}
	
	@ApiOperation(value="获取用户的客户详细信息", notes="根据url的id来获取用户的客户详细信息")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
	@GetMapping("/getUserCustomerInfo/{userId}")
	public CommonResultInfo<MUserInfoEntity> getUserCustomerInfo(@PathVariable String userId){
		CommonResultInfo<MUserInfoEntity> result = userInfoService.getUserCustomerRelationInfo(userId);
		return result;
	}
}
