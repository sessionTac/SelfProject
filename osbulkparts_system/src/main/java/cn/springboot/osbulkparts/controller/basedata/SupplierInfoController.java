package cn.springboot.osbulkparts.controller.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.SupplierInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/supplier")
public class SupplierInfoController {

	@Autowired
	private SupplierInfoService supplierInfoService;
	
	@ApiOperation(value="页面初始化", notes="获取页面初始化数据")
	@GetMapping("/init")
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(){
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = supplierInfoService.initViews();
		return result;
	}
	
	@ApiOperation(value="获取供应商列表信息", notes="查询所有供应商的列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "msupplierInfoEntity", value = "供应商实体对象", required = true, dataType = "body", paramType = "body"),
		@ApiImplicitParam(name = "pageNum", value = "分页-当前页码(默认1)", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "pageSize", value = "分页-总页数(默认10)", required = true, dataType = "String", paramType = "body")
	})
	@GetMapping("/getSupplierList")
	public CommonResultInfo<MSupplierInfoEntity> getSupplierList(
			MSupplierInfoEntity msupplierInfoEntity,
			@RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="10") int pageSize){
		CommonResultInfo<MSupplierInfoEntity> result = supplierInfoService.getSupplierInfoList(msupplierInfoEntity, pageNum, pageSize);
		return result;
	}
}
