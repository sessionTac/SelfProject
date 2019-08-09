package cn.springboot.osbulkparts.controller.basedata;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.springboot.osbulkparts.common.OSLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TMaterialQuotaEntity;
import cn.springboot.osbulkparts.entity.TPriceFileEntity;
import cn.springboot.osbulkparts.service.MaterialDataService;
import cn.springboot.osbulkparts.service.MaterialQuotaService;
import cn.springboot.osbulkparts.service.TPriceFileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/material")
public class MaterialDataController {

	@Autowired
	private MaterialDataService materialDataService;
	@Autowired
	private MaterialQuotaService materialQuotaService;
	@Autowired
	private TPriceFileService tfileService;
	
	@ApiOperation(value="页面初始化", notes="获取页面初始化数据")
	@GetMapping("/init")
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(@RequestHeader String lang){
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = materialDataService.initViews(lang,OSLanguage.localeToVueSuffix(lang));
		return result;
	}
	
	@ApiOperation(value="获取物料数据列表信息", notes="查询所有物料数据的列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "mmaterialInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body"),
		@ApiImplicitParam(name = "pageNum", value = "分页-当前页码(默认1)", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "pageSize", value = "分页-总页数(默认10)", required = true, dataType = "String", paramType = "body")
	})
	@GetMapping("/getMaterialList")
	public CommonResultInfo<MMaterialInfoEntity> getMaterialList(
			MMaterialInfoEntity mmaterialInfoEntity,
			@RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="50") int pageSize,
			HttpServletRequest request, Authentication auth,@RequestHeader String lang){
		mmaterialInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
		CommonResultInfo<MMaterialInfoEntity> result = materialDataService.selectMaterialInfoList(mmaterialInfoEntity, pageNum, pageSize,auth,OSLanguage.localeToVueSuffix(lang));
		return result;
	}
	
	@ApiOperation(value="获取物料数据信息", notes="根据条件查询物料数据的详细数据")
	@ApiImplicitParam(name = "mmaterialInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body")
	@GetMapping("/getMaterialInfo")
	public CommonResultInfo<MMaterialInfoEntity> getMaterialInfo(MMaterialInfoEntity mmaterialInfoEntity,@RequestHeader String lang){
		mmaterialInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
		CommonResultInfo<MMaterialInfoEntity> result = materialDataService.selectMaterialInfo(mmaterialInfoEntity,OSLanguage.localeToVueSuffix(lang));
		return result;
	}
	
    @ApiOperation(value="添加物料数据", notes="添加一条新的物料数据")
    @ApiImplicitParam(name = "mmaterialInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/addMaterialInfo")
	public CommonResultInfo<?> addMaterialInfo(@RequestBody MMaterialInfoEntity mmaterialInfoEntity,Authentication auth,@RequestHeader String lang){
    	CommonResultInfo<?> result = materialDataService.insertMaterialInfo(mmaterialInfoEntity, auth,OSLanguage.localeToVueSuffix(lang));
    	return result;
	}
    
    @ApiOperation(value="更新物料数据", notes="更新一条新的物料数据")
    @ApiImplicitParam(name = "mmaterialInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body")
    @PutMapping("/updateMaterialInfo")
	public CommonResultInfo<?> updateMaterialInfo(@RequestBody MMaterialInfoEntity mmaterialInfoEntity,Authentication auth,@RequestHeader String lang){
    	CommonResultInfo<?> result = materialDataService.updateMaterialInfo(mmaterialInfoEntity, auth,OSLanguage.localeToVueSuffix(lang));
    	return result;
	}
    
    @ApiOperation(value="删除物料数据", notes="删除一条新的物料数据")
    @ApiImplicitParam(name = "mmaterialInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body")
    @DeleteMapping("/deleteMaterialInfo/{materialId}")
	public CommonResultInfo<?> deleteMaterialInfo(@PathVariable String materialId,Authentication auth,@RequestHeader String lang){
    	CommonResultInfo<?> result = materialDataService.deleteMaterialInfo(materialId, auth,OSLanguage.localeToVueSuffix(lang));
    	return result;
	}
    
    @ApiOperation(value="物料数据导入", notes="excel的物料数据文件导入")
    @ApiImplicitParam(name = "excleFile", value = "物料数据文件", required = true, dataType = "body", paramType = "body")
    @PostMapping("/importExcel")
    public CommonResultInfo<?> ImportExcelData(
            @RequestParam("file") MultipartFile excleFile,HttpServletRequest request,Authentication auth,@RequestHeader String lang) {
        CommonResultInfo<?> result = materialDataService.importExcel(excleFile, request, auth,OSLanguage.localeToVueSuffix(lang));
        return result;
    }
    
    @ApiOperation(value="锁定或解锁物料数据", notes="锁定或解锁一条新的物料数据")
	@ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/lockMaterialInfo")
    public CommonResultInfo<?> lockMaterialInfo(@RequestBody CommonEntity commonEntity, Authentication auth,@RequestHeader String lang){
    	CommonResultInfo<?> result = materialDataService.lockMaterialInfo(commonEntity,auth,OSLanguage.localeToVueSuffix(lang));
    	return result;
    }
    
	@ApiOperation(value="删除物料数据", notes="删除新的物料数据")
	@ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
	@PutMapping("/deleteMater")
	public CommonResultInfo<?> batchDeletion(@RequestBody CommonEntity commonEntity, Authentication auth,@RequestHeader String lang){
    	CommonResultInfo<?> result = materialDataService.deleteBatchMaterialInfo(commonEntity,auth,OSLanguage.localeToVueSuffix(lang));
    	return result;
	}

	@ApiOperation(value="物料数据导出", notes="物料数据导出")
	@ApiImplicitParam(name = "mmaterialInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body")
	@PostMapping("/exportData")
	public Object downExcel(@RequestBody MMaterialInfoEntity mmaterialInfoEntity,@RequestHeader String lang) {
		mmaterialInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
		ResponseEntity<byte[]> response = materialDataService.downloadExcel(mmaterialInfoEntity,OSLanguage.localeToVueSuffix(lang));
		return response;
	}

	@ApiOperation(value="配额设置页面初始化", notes="配额设置获取页面初始化数据(所有物料号和所有供应商信息)")
	@GetMapping("/findQuotaInitInfo")
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> findQuotaInitInfo(){
//		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = materialDataService.initViews();
		return null;
	}

	@ApiOperation(value="配额设置 根据用户输入内容 添加或者修改数据", notes="配额设置 根据用户输入内容 添加或者修改数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tMaterialQuotaEntity", value = "配额实体对象", required = true, dataType = "body", paramType = "body"),
	})
	@PutMapping("/upsertQuotaInfo")
	public CommonResultInfo<?> upsertQuotaInfo(
			@RequestBody TMaterialQuotaEntity tMaterialQuotaEntity,Authentication auth,@RequestHeader String lang){
		CommonResultInfo<?> result = materialQuotaService.insertMaterialQuota(tMaterialQuotaEntity, auth,OSLanguage.localeToVueSuffix(lang));
		return result;
	}
	@ApiOperation(value="配额设置 根据用户输入内容 查找数据", notes="配额设置 根据用户输入内容 查找数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tMaterialQuotaEntity", value = "配额实体对象", required = true, dataType = "body", paramType = "body"),
	})
	@GetMapping("/findQuotaInfoList")
	public CommonResultInfo<TMaterialQuotaEntity> findQuotaInfoList(TMaterialQuotaEntity tMaterialQuotaEntity,
			HttpServletRequest request, Authentication auth,@RequestHeader String lang){
		CommonResultInfo<TMaterialQuotaEntity> result = materialQuotaService.selectMaterialQuotaList(tMaterialQuotaEntity, auth,OSLanguage.localeToVueSuffix(lang));
		return result;
	}
	@ApiOperation(value="配额设置 根据主键id 删除一条数据", notes="配额设置 根据主键id 删除一条数据")
	@ApiImplicitParam(name = "materialCode", value = "物料号", required = true, dataType = "path", paramType = "path")
	@PutMapping("/deleteQuotaInfoById")
	public CommonResultInfo<?> deleteQuotaInfoById(@RequestBody TMaterialQuotaEntity tMaterialQuotaEntity, Authentication auth,@RequestHeader String lang){
		CommonResultInfo<?> result = materialQuotaService.deleteMaterialQuota(tMaterialQuotaEntity.getMaterialCode(), auth,OSLanguage.localeToVueSuffix(lang));
		return result;
	}

	@ApiOperation(value="物料单价列表查询", notes="根据物料信息查询该物料的所有单价及图片")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tMaterialQuotaEntity", value = "配额实体对象", required = true, dataType = "body", paramType = "body"),
	})
	@GetMapping("/findMaterialPriceAndFile")
	public CommonResultInfo<TPriceFileEntity> findPriceAndFile(TPriceFileEntity tpriceFileEntity,
			HttpServletRequest request, Authentication auth,@RequestHeader String lang){
		CommonResultInfo<TPriceFileEntity> result = tfileService.getMaterialPriceAndFile(tpriceFileEntity, auth,OSLanguage.localeToVueSuffix(lang));
		return result;
	}
	
	@ApiOperation(value="附件数据设定", notes="附件数据设定")
	@ApiImplicitParam(name = "imgFile", value = "物料附件文件", required = true, dataType = "body", paramType = "body")
	@PostMapping("/setEnclosure")
	public CommonResultInfo<?> setEnclosure(TPriceFileEntity fileEntity,
			@RequestParam("file") MultipartFile imgFile,HttpServletRequest request,Authentication auth,@RequestHeader String lang) {
		CommonResultInfo<?> result = tfileService.upInsertPrice(fileEntity, imgFile, auth,OSLanguage.localeToVueSuffix(lang));
		return result;
	}

	@ApiOperation(value="附件数据读取", notes="附件数据读取")
	@GetMapping("/getEnclosure/{fileId}")
	public Object getEnclosure(@PathVariable String fileId,Authentication auth){
		return null;
	}
}
