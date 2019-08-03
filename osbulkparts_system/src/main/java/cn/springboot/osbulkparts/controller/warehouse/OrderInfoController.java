package cn.springboot.osbulkparts.controller.warehouse;

import javax.servlet.http.HttpServletRequest;

import cn.springboot.osbulkparts.common.OSLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TOrderInfoEntity;
import cn.springboot.osbulkparts.service.OrderInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;


    @ApiOperation(value="页面初始化", notes="获取页面初始化数据")
    @GetMapping("/init")
    public Object initViews(@RequestHeader String lang){
        return orderInfoService.initViews(lang);
    }

    @ApiOperation(value="获取订单计划列表信息", notes="查询所有订单计划的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tOrderInfoEntity", value = "订单计划实体对象", required = true, dataType = "body", paramType = "body"),
            @ApiImplicitParam(name = "pageNum", value = "分页-当前页码(默认1)", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "pageSize", value = "分页-总页数(默认10)", required = true, dataType = "String", paramType = "body")
    })
    @GetMapping("/getOrderInfoList")
    public Object getOrderInfoList(
            TOrderInfoEntity tOrderInfoEntity,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="50") int pageSize,
            HttpServletRequest request, Authentication auth,@RequestHeader String lang){
        tOrderInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
        return orderInfoService.selectOrderInfoList(tOrderInfoEntity,pageNum,pageSize,auth);
    }

    @ApiOperation(value="获取订单计划信息", notes="根据条件查询订单计划的详细数据")
    @ApiImplicitParam(name = "tOrderInfoEntity", value = "订单计划实体对象", required = true, dataType = "body", paramType = "body")
    @GetMapping("/getOrderInfo")
    public CommonResultInfo<TOrderInfoEntity> getOrderInfo(TOrderInfoEntity tOrderInfoEntity,@RequestHeader String lang){
        tOrderInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
        return orderInfoService.selectOrderInfo(tOrderInfoEntity);
    }

    @ApiOperation(value="添加订单计划", notes="添加一条新的订单计划")
    @ApiImplicitParam(name = "tOrderInfoEntity", value = "订单计划实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/addOrderInfo")
    public CommonResultInfo<?> addOrderInfo(@RequestBody TOrderInfoEntity tOrderInfoEntity,Authentication auth){
        return orderInfoService.insertOrderInfo(tOrderInfoEntity,auth);
    }

    @ApiOperation(value="更新订单计划", notes="更新一条新的订单计划")
    @ApiImplicitParam(name = "tOrderInfoEntity", value = "订单计划实体对象", required = true, dataType = "body", paramType = "body")
    @PutMapping("/updateOrderInfo")
    public CommonResultInfo<?> updateOrderInfo(@RequestBody TOrderInfoEntity tOrderInfoEntity,Authentication auth){
        return orderInfoService.updateOrderInfo(tOrderInfoEntity,auth);
    }

    @ApiOperation(value="删除订单计划", notes="删除一条新的订单计划")
    @ApiImplicitParam(name = "tOrderInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body")
    @DeleteMapping("/deleteOrderInfo/{OrderId}")
    public CommonResultInfo<?> deleteOrderInfo(@PathVariable String OrderId,Authentication auth){
        return null;
    }

    @ApiOperation(value="订单计划导入", notes="excel的订单计划文件导入")
    @ApiImplicitParam(name = "excleFile", value = "订单计划文件", required = true, dataType = "body", paramType = "body")
    @PostMapping("/importExcel")
    public CommonResultInfo<?> ImportExcelData(
    		int importType,int isBalance,
            @RequestParam("file") MultipartFile excleFile, HttpServletRequest request, Authentication auth) {
        return orderInfoService.importExcel(excleFile,request,auth,importType,isBalance);
    }

    @ApiOperation(value="生成订单", notes="生成订单")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/generate")
    public CommonResultInfo<?> generateInfo(@RequestBody CommonEntity commonEntity, Authentication auth,@RequestHeader String lang){
        return orderInfoService.generateOrderDetailInfo(commonEntity,auth,lang);
    }

    @ApiOperation(value="订单计划数据", notes="删除订单计划")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/deleteOrderInfo")
    public CommonResultInfo<?> batchDeletion(@RequestBody CommonEntity commonEntity, Authentication auth){
        return orderInfoService.deleteBatchOrderInfo(commonEntity,auth);
    }

    @ApiOperation(value="订单计划导出", notes="订单计划导出")
    @ApiImplicitParam(name = "mOrderInfoEntity", value = "物料数据实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/exportData")
    public Object downExcel(@RequestBody TOrderInfoEntity tOrderInfoEntity,@RequestHeader String lang) {
        tOrderInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
        return orderInfoService.downloadExcel(tOrderInfoEntity);
    }
}
