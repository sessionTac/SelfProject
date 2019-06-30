import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {


  /**
   * 列表初始化
   */
  initData: () =>
        getRequest(`~/orderInfo/init`),
  
  /**
   * 查询列表
   */
  findOrderInfoList: (params) =>
    getRequest(`~/orderInfo/getOrderInfoList`, {params}),
  /**新增一条物料数据*/
  addOrderInfo:(data)=>
    postRequest(`~/orderInfo/addOrderInfo`, {data}),

  /**
   * 物料数据列表初始化
   */
  findOrderInfo: (params) =>
      getRequest(`~/orderInfo/getOrderInfo`, {params}),


  /**
   * 修改编辑
   */
  updateMatter:(data)=>
    putRequest(`~/orderInfo/updateOrderInfo`, {data}),

  /***根据id删除本条数据*/
  deleteById:(data)=>
    putRequest(`~/orderInfo/deleteOrderInfo` ,{data}),
  /**数据导出**/
  exportData:(data) =>
    postRequest(`~/orderInfo/exportData`,{data,responseType:'blob'}),

  /***根据id生成订单详细*/
  generateById:(data)=>
      putRequest(`~/orderInfo/generate` ,{data}),
  // /**文件下载**/
  // installdownloadExcel:(data) =>
  //   postRequest(`~/material/downloadexcel`,{responseType:'blob'}),
  //
  // /***根据id锁定本条数据*/
  // lockedById:(data)=>
  //     putRequest(`~/material/lockMaterialInfo` ,{data}),
  //

}
