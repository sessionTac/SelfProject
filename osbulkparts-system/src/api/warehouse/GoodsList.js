import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {

  /**
   * 查询发货信息列表
   */
  findGoodsList: (params) =>
    getRequest(`~/goods/getGoodsList`, {params}),


  /***根据id确认发货*/
  sendGoods:(data)=>
    putRequest(`~/goods/sendGoods` ,{data}),

  /**数据导出**/
  exportData:(data) =>
    postRequest(`~/goods/exportData`,{data,responseType:'blob'}),
}
