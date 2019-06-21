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

  /***根据id锁定本条数据*/
  lockedById:(data)=>
      putRequest(`~/material/lockMaterialInfo` ,{data}),

  /**文件下载**/
  installdownloadExcel:(data) =>
    postRequest(`~/material/downloadexcel`,{responseType:'blob'}),

  /**数据导出**/
  exportData:(data) =>
    postRequest(`~/material/exportData`,{data,responseType:'blob'}),
}
