package cn.springboot.osbulkparts.controller.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.springboot.osbulkparts.common.OSLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;
import cn.springboot.osbulkparts.service.OrderDetailInfoService;
import cn.springboot.osbulkparts.service.ReportOrderDetailService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/orderDetailInfo")
public class OrderDetailInfoController {

    @Autowired
    private OrderDetailInfoService orderDetailInfoService;
    
    @Autowired
    private ReportOrderDetailService reportOrderDetailService;

    @ApiOperation(value="页面初始化", notes="获取页面初始化数据")
    @GetMapping("/init")
    public Object initViews(@RequestHeader String lang){
        return orderDetailInfoService.initViews(lang,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="获取订单详情列表信息", notes="查询所有订单详情的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情实体对象", required = true, dataType = "body", paramType = "body"),
            @ApiImplicitParam(name = "pageNum", value = "分页-当前页码(默认1)", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "pageSize", value = "分页-总页数(默认10)", required = true, dataType = "String", paramType = "body")
    })
    @GetMapping("/getList")
    public Object getOrderDetailInfoList(
            TOrderDetailInfoEntity tOrderDetailInfoEntity,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue="50") int pageSize,
            HttpServletRequest request, Authentication auth,@RequestHeader String lang){
        tOrderDetailInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
        return orderDetailInfoService.selectOrderDetailInfoList(tOrderDetailInfoEntity,pageNum,pageSize,auth,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="获取订单详情信息", notes="根据条件查询订单详情的详细数据")
    @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情实体对象", required = true, dataType = "body", paramType = "body")
    @GetMapping("/getOrderDetailInfo")
    public CommonResultInfo<TOrderDetailInfoEntity> getOrderDetailInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity,@RequestHeader String lang){
        tOrderDetailInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
        return orderDetailInfoService.selectOrderDetailInfo(tOrderDetailInfoEntity,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="获取所有的订单号", notes="获取所有的订单号")
    @GetMapping("/getAllOrderCode")
    public Object getAllOrderCode( String isBalance,Authentication auth,@RequestHeader String lang){
        return orderDetailInfoService.getAllOrderCode(isBalance,auth,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="根据订单号获取该订单的所有信息和所有关联的物料号", notes="根据订单号获取该订单的所有信息和所有关联的物料号")
    @ApiImplicitParam(name = "OrderCode", value = "订单号", required = true, dataType = "body", paramType = "body")
    @GetMapping("/getOrderInfoByOrderCode")
    public Object getOrderInfoByOrderCode(String orderCode,String isBalance,Authentication auth,@RequestHeader String lang){
        return orderDetailInfoService.getOrderInfoByOrderCode(orderCode,isBalance,auth,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="根据物料号获取该物料的所有信息", notes="根据物料号获取该物料的所有信息")
    @ApiImplicitParam(name = "MaterialCode", value = "物料号", required = true, dataType = "body", paramType = "body")
    @GetMapping("/getMaterialInfoByMaterialCode")
    public Object getMaterialInfoByMaterialCode(String materialCode,Authentication auth,@RequestHeader String lang){
        return orderDetailInfoService.getMaterialInfoByMaterialCode(materialCode,auth,lang,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="校验订单号和物料号是否存在", notes="校验订单号和物料号是否存在")
    @GetMapping("/checkOrderCodeAndMaterialCode")
    public Object checkOrderCodeAndMaterialCode(String orderCode,
    											String isBalance,
                                                String materialCode,Authentication auth,@RequestHeader String lang){
        return orderDetailInfoService.checkOrderCodeAndMaterialCode(orderCode,isBalance,materialCode,auth,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="更新订单详情信息", notes="更新一条新的订单详情信息")
    @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情实体对象", required = true, dataType = "body", paramType = "body")
    @PutMapping("/updateOrderDetailInfo")
    public CommonResultInfo<?> updateOrderDetailInfo(@RequestBody TOrderDetailInfoEntity tOrderDetailInfoEntity,Authentication auth,@RequestHeader String lang){
        return orderDetailInfoService.updateOrderDetailInfoInfo(tOrderDetailInfoEntity,auth,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="添加订单详情信息", notes="添加一条新的订单详情信息")
    @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/addOrderDetailInfo")
    public CommonResultInfo<?> addOrderDetailInfo(@RequestBody TOrderDetailInfoEntity tOrderDetailInfoEntity,Authentication auth,@RequestHeader String lang){
        return orderDetailInfoService.insertOrderDetailInfoInfo(tOrderDetailInfoEntity,auth,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="删除订单详情信息", notes="删除订单详情信息")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/deleteOrderDetailInfo")
    public CommonResultInfo<?> batchDeletion(@RequestBody CommonEntity commonEntity, Authentication auth,@RequestHeader String lang){
        return orderDetailInfoService.deleteBatchOrderInfo(commonEntity,auth,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="审批数据", notes="审批数据")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/approval")
    public CommonResultInfo<?> approval(@RequestBody CommonEntity commonEntity, Authentication auth,@RequestHeader String lang){
        return orderDetailInfoService.approvalBatchOrderInfo(commonEntity,auth,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="发货信息数据初始化", notes="发货信息数据初始化")
    @GetMapping("/sendGoodsInit")
    public CommonResultInfo<?> sendGoodsInit(@RequestHeader String lang){
        return orderDetailInfoService.sendGoodsInit(lang,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="发货信息查询", notes="根据订单号查询需要发货的订单信息")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @GetMapping("/deliverGoods")
    public CommonResultInfo<?> deliverGoods(CommonEntity commonEntity,@RequestHeader String lang){
        return orderDetailInfoService.selectDeliveryInfo(commonEntity,OSLanguage.localeToVueSuffix(lang));
    }
    
    @ApiOperation(value="执行发货", notes="将选中数据进行发货处理")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/excuteDeliver")
    public CommonResultInfo<?> excuteDeliver(@RequestBody CommonEntity commonEntity,@RequestHeader String lang, Authentication auth){
        return orderDetailInfoService.excuteDeliveryInfo(commonEntity,auth,lang,OSLanguage.localeToVueSuffix(lang));
    }

    @ApiOperation(value="订单计划导出", notes="订单计划导出")
    @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情信息实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/exportData")
    public Object downExcel(@RequestBody TOrderDetailInfoEntity tOrderDetailInfoEntity,@RequestHeader String lang, Authentication auth) {
        tOrderDetailInfoEntity.setLanguageFlag(OSLanguage.localeToTableSuffix(lang));
//		ResponseEntity<byte[]> response = orderDetailInfoService.downloadExcel(tOrderDetailInfoEntity);
		ResponseEntity<byte[]> response = reportOrderDetailService.DownloadReportOrderDetail(tOrderDetailInfoEntity,OSLanguage.localeToVueSuffix(lang), auth);
		return response;
    }


}
