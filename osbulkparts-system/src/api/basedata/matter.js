import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {

  /**
   * 查询物料数据列表
   */
  findMatterList: (params) =>
     // getRequest(`~/maintenance/basis/user/page`, {params}),
    getRequest(`./static/mock-api/basticData/matterList.json`, {params}),

  /**
   * 物料数据列表初始化
   */
  initData: () =>
        // getRequest(`~/maintenance/basis/user/page`, {params}),
        getRequest(`./static/mock-api/basticData/matterListInit.json`),
  /**
   * 物料数据列表初始化
   */
  init: () =>
      // getRequest(`~/maintenance/basis/user/page`, {params}),
      getRequest(`./static/mock-api/basticData/matterListInit.json`),

  /**
   * 修改编辑
   */
  updateMatter:(data)=>
    putRequest(`~/maintenance/basis/user/update`, {data}),

  /***根据id删除本条数据*/
  deleteById:(id)=>
    deleteRequest(`~/maintenance/basis/user/`+id),

  /**文件下载**/
  installdownloadExcel:(params) =>
    postRequest(`~/maintenance/basis/user/excel`,{params,responseType:'blob'}),
}
