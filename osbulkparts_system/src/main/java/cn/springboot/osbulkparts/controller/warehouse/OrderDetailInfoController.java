package cn.springboot.osbulkparts.controller.warehouse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;
import cn.springboot.osbulkparts.service.OrderDetailInfoService;
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

    @ApiOperation(value="页面初始化", notes="获取页面初始化数据")
    @GetMapping("/init")
    public Object initViews(){
        return orderDetailInfoService.initViews();
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
            @RequestParam(defaultValue="10") int pageSize,
            HttpServletRequest request, Authentication auth){
        return orderDetailInfoService.selectOrderDetailInfoList(tOrderDetailInfoEntity,pageNum,pageSize,auth);
    }

    @ApiOperation(value="获取订单详情信息", notes="根据条件查询订单详情的详细数据")
    @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情实体对象", required = true, dataType = "body", paramType = "body")
    @GetMapping("/getOrderDetailInfo")
    public CommonResultInfo<TOrderDetailInfoEntity> getOrderDetailInfo(TOrderDetailInfoEntity tOrderDetailInfoEntity){
        return orderDetailInfoService.selectOrderDetailInfo(tOrderDetailInfoEntity);
    }

    @ApiOperation(value="获取所有的订单号", notes="获取所有的订单号")
    @GetMapping("/getAllOrderCode")
    public Object getAllOrderCode( String isBalance,Authentication auth){
        return orderDetailInfoService.getAllOrderCode(isBalance,auth);
    }

    @ApiOperation(value="根据订单号获取该订单的所有信息和所有关联的物料号", notes="根据订单号获取该订单的所有信息和所有关联的物料号")
    @ApiImplicitParam(name = "OrderCode", value = "订单号", required = true, dataType = "body", paramType = "body")
    @GetMapping("/getOrderInfoByOrderCode")
    public Object getOrderInfoByOrderCode(String orderCode,String isBalance,Authentication auth){
        return orderDetailInfoService.getOrderInfoByOrderCode(orderCode,isBalance,auth);
    }

    @ApiOperation(value="根据物料号获取该物料的所有信息", notes="根据物料号获取该物料的所有信息")
    @ApiImplicitParam(name = "MaterialCode", value = "物料号", required = true, dataType = "body", paramType = "body")
    @GetMapping("/getMaterialInfoByMaterialCode")
    public Object getMaterialInfoByMaterialCode(String materialCode,Authentication auth){
        return orderDetailInfoService.getMaterialInfoByMaterialCode(materialCode,auth);
    }

    @ApiOperation(value="校验订单号和物料号是否存在", notes="校验订单号和物料号是否存在")
    @GetMapping("/checkOrderCodeAndMaterialCode")
    public Object checkOrderCodeAndMaterialCode(String orderCode,
    											String isBalance,
                                                String materialCode,Authentication auth){
        return orderDetailInfoService.checkOrderCodeAndMaterialCode(orderCode,isBalance,materialCode,auth);
    }

    @ApiOperation(value="更新订单详情信息", notes="更新一条新的订单详情信息")
    @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情实体对象", required = true, dataType = "body", paramType = "body")
    @PutMapping("/updateOrderDetailInfo")
    public CommonResultInfo<?> updateOrderDetailInfo(@RequestBody TOrderDetailInfoEntity tOrderDetailInfoEntity,Authentication auth){
        return orderDetailInfoService.updateOrderDetailInfoInfo(tOrderDetailInfoEntity,auth);
    }

    @ApiOperation(value="添加订单详情信息", notes="添加一条新的订单详情信息")
    @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/addOrderDetailInfo")
    public CommonResultInfo<?> addOrderDetailInfo(@RequestBody TOrderDetailInfoEntity tOrderDetailInfoEntity,Authentication auth){
        return orderDetailInfoService.insertOrderDetailInfoInfo(tOrderDetailInfoEntity,auth);
    }

    @ApiOperation(value="删除订单详情信息", notes="删除订单详情信息")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/deleteOrderDetailInfo")
    public CommonResultInfo<?> batchDeletion(@RequestBody CommonEntity commonEntity, Authentication auth){
        return orderDetailInfoService.deleteBatchOrderInfo(commonEntity,auth);
    }

    @ApiOperation(value="审批数据", notes="审批数据")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/approval")
    public CommonResultInfo<?> approval(@RequestBody CommonEntity commonEntity, Authentication auth){
        return orderDetailInfoService.approvalBatchOrderInfo(commonEntity,auth);
    }

    @ApiOperation(value="发货", notes="发货")
    @ApiImplicitParam(name = "commonEntity", value = "共同实体类", required = true, dataType = "body", paramType = "body")
    @PutMapping("/deliverGoods")
    public CommonResultInfo<?> deliverGoods(@RequestBody CommonEntity commonEntity, Authentication auth){
        return null;
    }

    @ApiOperation(value="订单计划导出", notes="订单计划导出")
    @ApiImplicitParam(name = "tOrderDetailInfoEntity", value = "订单详情信息实体对象", required = true, dataType = "body", paramType = "body")
    @PostMapping("/exportData")
    public Object downExcel(@RequestBody TOrderDetailInfoEntity tOrderDetailInfoEntity) {
        return null;
    }


}
