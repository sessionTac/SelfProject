import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {

  //---------------------------------用户------------------------------------------------------
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

  /**新增用户*/
  addUser:(data)=>
    postRequest(`~/user/addUser`, {data}),
  /**
   * 修改用户
   */
  updateUser:(data)=>
    putRequest(`~/user/updateUser`, {data}),

  /***根据id删除本条数据*/
  deleteById:(id)=>
    deleteRequest(`~/user/deleteUser/`+id),


  /**根据用户id查询用户所属角色*/
  findRoleById:(userId)=>
    getRequest(`~/user/findRole/`+userId),

  /**查询所有Juese角色*/
  findRole:(params)=>
    getRequest(`~/user/findAllRole`, {params}),

  /**给用户添加角色*/
  insertRole:(data)=>
    postRequest(`~/user/insertRole`, {data}),

  /**
   * 添加或者修改用户 进行校验查重
   */
  checkUserInfo:(params)=>
    getRequest(`~/user/checkUserInfo`, {params}),


  /**密码重置*/
  reset:(data)=>
    putRequest(`~/user/resetPass`,{data}),

  //-------------------------------------------------------------------------------------------------

  //--------------------------------角色----------------------------------------------------------------
  /**
   * 获取所有下拉选信息
   * @returns {*|*}
   */
  findRoleOptions:()=>
    getRequest(`~/role/getOptions`),
  /**
   * 查询角色列表
   */
  findRoleList: (params) =>
    getRequest(`~/role/getRoleInfoList`, {params}),
  /**根据id查询这条数据*/
  selectRoleById:(id)=>
    getRequest(`~/role/getRoleInfo/`+id),
  /**
   * 获得权限树
   */
  findFunctionTree:()=>
    getRequest(`~/role/getTree`),
  //查询该角色拥有的权限id
  findRoleDetail: (roleId) =>
    getRequest(`~/role/getPower/`+roleId),
  //添加权限
  insertPower:(data)=>
    postRequest(`~/role/insertPower`,{data}),

  /**
   * 修改角色
   */
  updateRole:(data)=>
    putRequest(`~/role/updateRole`, {data}),

  /**新增角色*/
  addRole:(data)=>
    postRequest(`~/role/addRole`, {data}),
  /**
   * 添加或者修改角色 进行校验查重
   */
  checkRoleInfo:(params)=>
    getRequest(`~/role/checkRoleInfo`, {params}),
  /***根据id删除本条数据*/
  deleteRoleById:(id)=>
    deleteRequest(`~/role/deleteRole/`+id),


  //---------------------------------------------------------------------------------------------------

















  /**文件下载**/
  installdownloadExcel:(params) =>
    postRequest(`~/maintenance/basis/user/excel`,{params,responseType:'blob'}),
}
