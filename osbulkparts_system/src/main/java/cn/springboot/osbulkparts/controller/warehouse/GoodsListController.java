package cn.springboot.osbulkparts.controller.warehouse;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TStockInfoEntity;
import cn.springboot.osbulkparts.service.GoodsListService;
import cn.springboot.osbulkparts.service.StockInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsListController {

	@Autowired
	private GoodsListService goodsListService;

	@ApiOperation(value="页面初始化", notes="获取页面初始化数据")
	@GetMapping("/init")
	public Object initViews(){
		return goodsListService.initViews();
	}

	
	@ApiOperation(value="获取发货信息列表信息", notes="查询发货信息列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tDeliverInfoEntity", value = "发货信息实体对象", required = true, dataType = "body", paramType = "body"),
		@ApiImplicitParam(name = "pageNum", value = "分页-当前页码(默认1)", required = true, dataType = "String", paramType = "body"),
		@ApiImplicitParam(name = "pageSize", value = "分页-总页数(默认10)", required = true, dataType = "String", paramType = "body")
	})
	@GetMapping("/getGoodsList")
	public CommonResultInfo<?> getMaterialList(
			TDeliverInfoEntity tDeliverInfoEntity,
			@RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="100") int pageSize,
			HttpServletRequest request, Authentication auth){
		return  goodsListService.getGoodsList( tDeliverInfoEntity,  pageNum,  pageSize,  auth);
	}

	@ApiOperation(value="发货数据", notes="发货数据")
	@ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
	@PutMapping("/sendGoods")
	public CommonResultInfo<?> sendGoods(@RequestBody CommonEntity commonEntity, Authentication auth){

		return  goodsListService.sendGoods(commonEntity,auth);
	}

	@ApiOperation(value="发货数据导出", notes="发货数据导出")
	@ApiImplicitParam(name = "tStockInfoEntity", value = "库存实体对象", required = true, dataType = "body", paramType = "body")
	@PostMapping("/exportData")
	public Object downExcel(@RequestBody TDeliverInfoEntity tStockInfoEntity) {
		return  null;
	}
}
