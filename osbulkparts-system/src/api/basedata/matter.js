import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {

  /**
   * 查询物料数据列表
   */
  findMatterList: (params) =>
    getRequest(`~/material/getMaterialList`, {params}),

  /**
   * 物料数据列表初始化
   */
  initData: () =>
        getRequest(`~/material/init`),
  /**
   * 物料数据列表初始化
   */
  findMatterInfo: (params) =>
      getRequest(`~/material/getMaterialInfo`, {params}),

  /**新增一条物料数据*/
  addMatter:(data)=>
    postRequest(`~/material/addMaterialInfo`, {data}),
  /**
   * 修改编辑
   */
  updateMatter:(data)=>
    putRequest(`~/material/updateMaterialInfo`, {data}),

  /***根据id删除本条数据*/
  deleteById:(data)=>
    putRequest(`~/material/deleteMater` ,{data}),

  /**文件下载**/
  installdownloadExcel:(data) =>
    postRequest(`~/material/excel`,{data,responseType:'blob'}),
}
