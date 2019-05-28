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
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dict")
public class DictSettingController {

	@Autowired
	private DictTypeSettingService dictTypeSettingService;
	
	@Autowired
	private DictDataSettingService dictDataSettingService;
	
	@ApiOperation(value="获取数据字典类型列表信息", notes="查询所有数据字典类型的列表")
	@GetMapping("/getDictTypeList")
	public CommonResultInfo<TDictTypeEntity> getDictTypeInfoList(
			TDictTypeEntity tdictTypeEntity,
			@RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="10") int pageSize){
		CommonResultInfo<TDictTypeEntity> result = dictTypeSettingService.getDictTypeList(tdictTypeEntity,pageNum,pageSize);
		return result;
	}
	
	@ApiOperation(value="获取数据字典类型的数据信息", notes="根据数据字典类型ID查询其数据信息")
	@ApiImplicitParam(name = "dictTypeId", value = "数据字典类型ID", required = true, dataType = "String", paramType = "path")
	@GetMapping("/getDictDataInfo/{dictTypeId}")
	public CommonResultInfo<TDictDataEntity> getDictDataInfo(@PathVariable String dictTypeId){
		CommonResultInfo<TDictDataEntity> result = dictDataSettingService.getDictDataInfo(dictTypeId);
		return result;
	}
}
