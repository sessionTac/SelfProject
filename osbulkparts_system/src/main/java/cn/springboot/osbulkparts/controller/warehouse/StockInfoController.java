package cn.springboot.osbulkparts.controller.warehouse;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.OSLanguage;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TStockInfoEntity;
import cn.springboot.osbulkparts.service.MaterialDataService;
import cn.springboot.osbulkparts.service.StockInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/stock")
public class StockInfoController {
	@Autowired
	private StockInfoService stockInfoService;

	
	@ApiOperation(value="页面初始化", notes="获取页面初始化数据")
	@GetMapping("/init")
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(@RequestHeader String lang){
		return  stockInfoService.initViews(lang);
	}
	
	@ApiOperation(value="获取库存信息列表信息", notes="查询库存信息列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tStockInfoEntity", value = "库存信息实体对象", required = true, dataType = "body", paramType = "body"),
		@ApiImplicitParam(name = "pageNum", value = "分页-当前页码(默认1)", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "pageSize", value = "分页-总页数(默认10)", required = true, dataType = "String", paramType = "body")
	})
	@GetMapping("/getStockList")
	public CommonResultInfo<?> getMaterialList(
			TStockInfoEntity tStockInfoEntity,
			@RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="50") int pageSize,
			HttpServletRequest request, Authentication auth,@RequestHeader String lang){
		tStockInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
		return  stockInfoService.getStockInfoList(tStockInfoEntity,pageNum,pageSize,auth);
	}
	
	@ApiOperation(value="获取库存信息", notes="根据条件查询库存信息的详细数据")
	@ApiImplicitParam(name = "tStockInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body")
	@GetMapping("/getStockInfo")
	public CommonResultInfo<TStockInfoEntity> getMaterialInfo(TStockInfoEntity tStockInfoEntity,@RequestHeader String lang){
		tStockInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
		return  stockInfoService.getStockInfoInfo(tStockInfoEntity);
	}
	
    @ApiOperation(value="添加库存数据", notes="添加一条新的库存数据")
    @ApiImplicitParam(name = "tStockInfoEntity", value = "库存实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/addStockInfo")
	public CommonResultInfo<?> addMaterialInfo(@RequestBody TStockInfoEntity tStockInfoEntity,Authentication auth){
		return  stockInfoService.insertStockInfo(tStockInfoEntity,auth);
	}
    
    @ApiOperation(value="更新库存数据", notes="更新一条新的库存数据")
    @ApiImplicitParam(name = "tStockInfoEntity", value = "库存实体对象", required = true, dataType = "body", paramType = "body")
    @PutMapping("/updateStockInfo")
	public CommonResultInfo<?> updateMaterialInfo(@RequestBody TStockInfoEntity tStockInfoEntity,Authentication auth){
		return  stockInfoService.updateStockInfo(tStockInfoEntity,auth);
	}

    
    @ApiOperation(value="库存导入", notes="excel的库存数据文件导入")
    @ApiImplicitParam(name = "excleFile", value = "库存数据文件", required = true, dataType = "body", paramType = "body")
    @PostMapping("/importExcel")
    public CommonResultInfo<?> ImportExcelData(
            @RequestParam("file") MultipartFile excleFile,HttpServletRequest request,Authentication auth) {
		return  stockInfoService.importExcel(excleFile,request,auth);
    }
    

    
	@ApiOperation(value="删除库存数据", notes="删除库存数据")
	@ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
	@PutMapping("/deleteStock")
	public CommonResultInfo<?> batchDeletion(@RequestBody CommonEntity commonEntity, Authentication auth){
		return  stockInfoService.deleteBatchByIds(commonEntity,auth);
	}

	@ApiOperation(value="库存数据导出", notes="库存数据导出")
	@ApiImplicitParam(name = "tStockInfoEntity", value = "库存实体对象", required = true, dataType = "body", paramType = "body")
	@PostMapping("/exportData")
	public Object downExcel(@RequestBody TStockInfoEntity tStockInfoEntity,@RequestHeader String lang) {
		tStockInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
		return  stockInfoService.downloadExcel(tStockInfoEntity);
	}
}
