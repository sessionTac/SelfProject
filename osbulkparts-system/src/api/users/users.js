import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {

  /**
   * 查询用户列表
   */
  findUserList: (params) =>
    getRequest(`~/user/getUserInfoList`, {params}),
  /**根据id查询这条数据*/
  selectUserById:(id)=>
    getRequest(`~/user/getUserInfo/`+id),
  /**
   * 获取所有下拉选信息
   * @returns {*|*}
   */
  findOptions:()=>
    getRequest(`~/user/getOptions`),


  selectUnit:()=>
    getRequest(``),

  /**新增用户*/
  addUser:(data)=>
    postRequest(`~/maintenance/basis/user/insertInfo`, {data}),


  /**
   * 修改编辑
   */
  updateUser:(data)=>
    putRequest(`~/maintenance/basis/user/update`, {data}),

  /***根据id删除本条数据*/
  deleteById:(id)=>
    deleteRequest(`~/maintenance/basis/user/`+id),

  /**密码重置*/
  reset:(data)=>
    putRequest(`~/maintenance/basis/user/changePass`,{data}),

  /**查询角色*/
  findRole:(params)=>
     // getRequest(`~/maintenance/basis/user/rolePage`),
      getRequest(`./static/mock-api/users/roleList.json`, {params}),

  /**添加角色*/
  insertRole:(data)=>
    postRequest(`~/maintenance/basis/user/insertRole`, {data}),

  /**查询角色*/
  findFunctionTree:()=>
      // getRequest(`~/maintenance/basis/user/rolePage`),
      getRequest(`./static/mock-api/users/roleList.json`),

    /**根据用户id查询用户所属角色*/
  findRoleById:(userId)=>
     getRequest(`~/maintenance/basis/user/findRole/`+userId),
  /**文件下载**/
  installdownloadExcel:(params) =>
    postRequest(`~/maintenance/basis/user/excel`,{params,responseType:'blob'}),
}
