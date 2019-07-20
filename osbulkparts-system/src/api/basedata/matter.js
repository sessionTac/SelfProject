import {getRequest, postRequest, putRequest,deleteRequest,postFormData} from "@/utils/request_utils";

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

  //配额设置 获得 所有物料号和所有 供应商信息
  findQuotaInitInfo: () =>
    getRequest(`~/material/findQuotaInitInfo`),
  //配额设置 根据用户输入内容 添加或者修改数据
  upsertQuotaInfo:(data)=>
    putRequest(`~/material/upsertQuotaInfo`, {data}),
  //配额设置 根据用户输入内容 查找数据
  findQuotaInfoList: (params) =>
    getRequest(`~/material/findQuotaInfoList`, {params}),
  //配额设置 根据主键id 删除一条数据
  deleteQuotaInfoById:(data)=>
    putRequest(`~/material/deleteQuotaInfoById` ,{data}),
  //图片部分
  avatarDao: {
    uploader: (file) => {
      let form = new FormData();
      form.append('file', file);
      return postRequest('~/material/setEnclosure',{data:form} ).then(({data}) => data);
    },
    // srcGetter: attachment => `${API_HOME}/passport/avatar/${attachment.file_id}`,
    srcGetter:(attachment)  =>
      getRequest(`~/material/getEnclosure/${attachment.file_id}`),
  },
}
