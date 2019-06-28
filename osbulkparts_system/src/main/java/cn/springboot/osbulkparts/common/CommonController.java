package cn.springboot.osbulkparts.common;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.springboot.osbulkparts.common.utils.CommonMethods;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/common")
public class CommonController {
	
	private String PATH_MATERIAL="materialData";
	
	private String NAME_MATERIAL= "物料数据导入模板.xlsx";
	
	private String PATH_ORDER="order";
	
	private String NAME_ORDER= "订单计划导入模板.xlsx";
	
	private String PATH_SUPPLIER="supplider";

	@ApiOperation(value="模板下载", notes="模板下载")
	@ApiImplicitParam(name = "name", value = "需要下载的模板名", required = true, dataType = "String", paramType = "path")
	@PostMapping("/downloadTemp/{name}")
	public ResponseEntity<InputStreamResource> downExcel(@PathVariable String name) throws Exception {
		ResponseEntity<InputStreamResource>  resultresponse = null;
		if(name.equals("materialDataTemp")) {
			resultresponse = CommonMethods.download(PATH_MATERIAL, name, NAME_MATERIAL);
		}
		else if(name.equals("orderPlanTemp")){
			resultresponse = CommonMethods.download(PATH_ORDER, name, NAME_ORDER);
		}
        return resultresponse;
	}
}
