import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {
  //订单详情  获取下拉选信息
  initData: () =>
    getRequest(`~/orderDetailInfo/init`),
  //订单详情 获得list列表信息 模糊查询
  findOrderDetailInfoList:(params)=>
    getRequest(`~/orderDetailInfo/getList`,{params}),
  //订单详情 根据主键获得一条数据的详细信息
  findOrderDetailInfo: (params) =>
    getRequest(`~/orderDetailInfo/getOrderDetailInfo`,{params}),
  //订单详情 获取所有的订单号
  getAllOrderCode: () =>
    getRequest(`~/orderDetailInfo/getAllOrderCode`),
  //订单详情 根据订单号获取订单信息和关联的物料号
  getOrderInfoByOrderCode:(params)=>
    getRequest(`~/orderDetailInfo/getOrderInfoByOrderCode`,{params}),
  //订单详情 根据物料号 获取物料信息的详细信息
  getMaterialInfoByMaterialCode:(params)=>
    getRequest(`~/orderDetailInfo/getMaterialInfoByMaterialCode`,{params}),
  //订单详情 check 物料号和订单号是否存在
  checkOrderCodeAndMaterialCode:(params)=>
    getRequest(`~/orderDetailInfo/checkOrderCodeAndMaterialCode`,{params}),


  //订单详情 根据主键修改一条数据
  updateOrderDetailInfo:(data)=>
    putRequest(`~/orderDetailInfo/updateOrderDetailInfo`, {data}),
  //订单详情 添加一条数据
  addOrderDetailInfo:(data)=>
    postRequest(`~/orderDetailInfo/addOrderDetailInfo`, {data}),
  //订单详情 删除一组数据

  //订单详情 审批一组数据

  //订单详情 发货一组数据

  //订单详情 导出功能
}