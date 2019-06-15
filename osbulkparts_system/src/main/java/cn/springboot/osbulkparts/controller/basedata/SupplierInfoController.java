package cn.springboot.osbulkparts.controller.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@ApiOperation(value="获取供应商信息", notes="根据查询供应商的列表")
	@ApiImplicitParam(name = "supplierId", value = "供应商ID", required = true, dataType = "String", paramType = "path")
	@GetMapping("/getSupplierInfo")
	public CommonResultInfo<MSupplierInfoEntity> getSupplierInfo(@PathVariable String supplierId){
    	CommonResultInfo<MSupplierInfoEntity> result = supplierInfoService.getSupplierInfo(supplierId);
    	return result;
	}
	
    @ApiOperation(value="添加供应商信息", notes="添加一条新的供应商信息")
    @ApiImplicitParam(name = "mSupplierInfoEntity", value = "供应商信息实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/addSupplierInfo")
	public CommonResultInfo<?> addSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity,Authentication auth){
    	CommonResultInfo<?> result = supplierInfoService.insertSupplierInfo(mSupplierInfoEntity, auth);
    	return result;
	}
    
    @ApiOperation(value="更新供应商信息", notes="更新一条新的供应商信息")
    @ApiImplicitParam(name = "mSupplierInfoEntity", value = "供应商信息实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/updateSupplierInfo")
	public CommonResultInfo<?> updateSupplierInfo(MSupplierInfoEntity mSupplierInfoEntity,Authentication auth){
    	CommonResultInfo<?> result = supplierInfoService.updateSupplierInfo(mSupplierInfoEntity, auth);
    	return result;
	}
    
    @ApiOperation(value="删除供应商信息", notes="删除一条新的供应商信息")
    @ApiImplicitParam(name = "mSupplierInfoEntity", value = "供应商信息实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/deleteSupplierInfo/{SupplierId}")
	public CommonResultInfo<?> deleteSupplierInfo(@PathVariable String SupplierId,Authentication auth){
    	CommonResultInfo<?> result = supplierInfoService.deleteSupplierInfo(SupplierId, auth);
    	return result;
	}
}
