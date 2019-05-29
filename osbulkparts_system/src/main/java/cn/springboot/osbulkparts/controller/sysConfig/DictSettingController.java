package cn.springboot.osbulkparts.controller.sysConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TDictTypeEntity;
import cn.springboot.osbulkparts.service.DictDataSettingService;
import cn.springboot.osbulkparts.service.DictTypeSettingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dict")
public class DictSettingController {

	@Autowired
	private DictTypeSettingService dictTypeSettingService;
	
	@Autowired
	private DictDataSettingService dictDataSettingService;
	
	@ApiOperation(value="获取数据字典类型列表信息", notes="查询所有数据字典类型的列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "TDictTypeEntity", value = "数据字典实体对象", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "pageNum", value = "分页-当前页码", required = true, dataType = "String", paramType = "path"),
		@ApiImplicitParam(name = "pageSize", value = "分页-总页数", required = true, dataType = "String", paramType = "path")
	})
	@GetMapping("/getDictTypeList")
	public CommonResultInfo<TDictTypeEntity> getDictTypeInfoList(
			TDictTypeEntity tdictTypeEntity,
			@RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="10") int pageSize){
		CommonResultInfo<TDictTypeEntity> result = dictTypeSettingService.getDictTypeList(tdictTypeEntity,pageNum,pageSize);
		return result;
	}
	
	@ApiOperation(value="获取所有数据字典类型", notes="查询所有数据字典类型")
	@GetMapping("/getDictType")
	public CommonResultInfo<TDictTypeEntity> getDictType(){
		CommonResultInfo<TDictTypeEntity> result = dictTypeSettingService.getDictType();
		return result;
	}
	
	@ApiOperation(value="获取数据字典类型的信息", notes="根据数据字典类型ID查询其信息")
	@ApiImplicitParam(name = "dictTypeId", value = "数据字典类型ID", required = true, dataType = "String", paramType = "path")
	@GetMapping("/getDictTypeInfo/{dictTypeId}")
	public CommonResultInfo<TDictTypeEntity> getDictTypeInfo(@PathVariable String dictTypeId){
		CommonResultInfo<TDictTypeEntity> result = dictTypeSettingService.getDictTypeInfo(dictTypeId);
		return result;
	}
	
	@ApiOperation(value="获取数据字典类型的数据信息", notes="根据数据字典类型ID查询其数据信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dictTypeId", value = "数据字典类型ID", required = true, dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "pageNum", value = "当前第几页默认1", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "一页多少数据 默认10", required = true, dataType = "String", paramType = "query")
	})
	@GetMapping("/getDictDataInfo/{dictTypeId}")
	public CommonResultInfo<TDictDataEntity> getDictDataInfo(@PathVariable String dictTypeId,@RequestParam(defaultValue = "1") int pageNum,
															 @RequestParam(defaultValue="10") int pageSize){
		CommonResultInfo<TDictDataEntity> result = dictDataSettingService.getDictDataInfo(dictTypeId,pageNum,pageSize);
		return result;
	}
}
