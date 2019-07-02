package cn.springboot.osbulkparts.controller.sysConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
		@ApiImplicitParam(name = "TDictTypeEntity", value = "数据字典实体对象", required = true, dataType = "body", paramType = "body"),
		@ApiImplicitParam(name = "pageNum", value = "分页-当前页码(默认1)", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "pageSize", value = "分页-总页数(默认10)", required = true, dataType = "String", paramType = "body")
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
			@ApiImplicitParam(name = "dictTypeCode", value = "数据字典类型编码", required = true, dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "pageNum", value = "分页-当前页码(默认1)", required = true, dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "pageSize", value = "分页-总页数(默认10)", required = true, dataType = "String", paramType = "path")
	})
	@GetMapping("/getDictDataInfo/{dictTypeCode}")
	public CommonResultInfo<TDictDataEntity> getDictDataInfoList(@PathVariable String dictTypeCode,@RequestParam(defaultValue = "1") int pageNum,
															 @RequestParam(defaultValue="10") int pageSize){
		CommonResultInfo<TDictDataEntity> result = dictDataSettingService.getDictDataInfo(dictTypeCode,pageNum,pageSize);
		return result;
	}
	
	@ApiOperation(value="添加数据字典类型", notes="添加数据字典类型")
	@ApiImplicitParam(name = "TDictTypeEntity", value = "数据字典类型实体对象", required = true, dataType = "body", paramType = "body")
	@PostMapping("/addDictType")
	public CommonResultInfo<?> addDictType(@RequestBody TDictTypeEntity tdictTypeEntity, Authentication auth){
		CommonResultInfo<?> result = dictTypeSettingService.addDictTypeInfo(tdictTypeEntity, auth);
		return result;
	}

	@ApiOperation(value="编辑数据字典类型", notes="修改数据字典类型的信息")
	@ApiImplicitParam(name = "TDictTypeEntity", value = "数据字典类型实体对象", required = true, dataType = "body", paramType = "body")
	@PutMapping("/updateDictType")
	public CommonResultInfo<?> updateDictType(@RequestBody TDictTypeEntity tdictTypeEntity,Authentication auth){
		CommonResultInfo<?> result = dictTypeSettingService.updateDictType(tdictTypeEntity, auth);
		return result;
	}
	
	@ApiOperation(value="删除数据字典类型", notes="删除数据字典类型")
	@ApiImplicitParam(name = "dictTypeId", value = "数据字典类型ID", required = true, dataType = "String", paramType = "path")
	@DeleteMapping("/deleteDictType/{dictTypeId}")
	public CommonResultInfo<?> deleteDictType(@PathVariable String dictTypeId,Authentication auth){
		CommonResultInfo<?> result = dictTypeSettingService.deleteDictType(dictTypeId, auth);
		return result;
	}
	
	@ApiOperation(value="验证数据字典类型名称", notes="验证数据字典类型名称")
	@ApiImplicitParam(name = "TDictTypeEntity", value = "数据字典类型实体对象", required = true, dataType = "body", paramType = "body")
	@GetMapping("/checkNameRepeat")
	public CommonResultInfo<?> checkNameRepeat(TDictTypeEntity tdictTypeEntity, String checkFlag){
		CommonResultInfo<?> result = dictTypeSettingService.checkNameRepeat(tdictTypeEntity,checkFlag);
		return result;
	}
	
	@ApiOperation(value="验证数据字典类型编码", notes="验证数据字典类型编码")
	@ApiImplicitParam(name = "TDictTypeEntity", value = "数据字典类型实体对象", required = true, dataType = "body", paramType = "body")
	@GetMapping("/checkCodeRepeat")
	public CommonResultInfo<?> checkCodeRepeat(TDictTypeEntity tdictTypeEntity, String checkFlag){
		CommonResultInfo<?> result = dictTypeSettingService.checkCodeRepeat(tdictTypeEntity,checkFlag);
		return result;
	}

	@ApiOperation(value="根据id获取tdictdata的详细信息", notes="根据id获取tdictdata的详细信息")
	@ApiImplicitParam(name = "id", value = "tdictdata表主键", required = true, dataType = "body", paramType = "body")
	@GetMapping("/getTDictDataInfo/{id}")
	public CommonResultInfo<?> getTDictDataInfo(@PathVariable String id){
		return dictDataSettingService.getDictDataInfoDetail(id);
	}
}
