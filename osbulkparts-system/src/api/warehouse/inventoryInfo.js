import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {

  /**
   * 查询物料数据列表
   */
  findStockInfoList: (params) =>
    getRequest(`~/stock/getStockList`, {params}),

  /**
   * 物料数据列表初始化
   */
  initData: () =>
    getRequest(`~/stock/init`),
  /**
   * 物料数据列表初始化
   */
  findStockInfo: (params) =>
    getRequest(`~/stock/getStockInfo`, {params}),

  /**新增一条物料数据*/
  addStockInfo:(data)=>
    postRequest(`~/stock/addStockInfo`, {data}),
  /**
   * 修改编辑
   */
  updateStockInfo:(data)=>
    putRequest(`~/stock/updateStockInfo`, {data}),

  /***根据id删除本条数据*/
  deleteById:(data)=>
    putRequest(`~/stock/deleteStock` ,{data}),

  /**数据导出**/
  exportData:(data) =>
    postRequest(`~/stock/exportData`,{data,responseType:'blob'}),
}
