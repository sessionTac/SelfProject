import {getRequest, postRequest,putRequest,deleteRequest} from "@/utils/request_utils";

export default {

  /**
   * 分类初始化
   */
  findMDictionaryItemsTree: () =>
    getRequest(`~/maintenance/basis/dict/down_menu`),

  /**
   * 查询字典
   */
  findMDictionaryItemsDetails: (params) =>
    getRequest(`~/maintenance/basis/dict/page`, {params}),

  /**
   * 查询字典类型
   * @param data
   * @returns {*}
   */
  findDictTypes:(params)=>
    getRequest(`~/dict/getDictTypeList`, {params}),

  // findMDictionaryItemsDetails: () =>
  //   getRequest('./static/mockdata/basics/dictionary/dictionary_education.json'),

  //增加数据
  addDict:(data)=>
    postRequest(`~/maintenance/basis/dict/`, {data}),
  //增加字典类型数据
  addDictType:(data)=>
    postRequest(`~/maintenance/basis/dict/type`, {data}),
  //删除数据
  deleteDict:(id)=>
    deleteRequest(`~/maintenance/basis/dict/${id}`),
//删除字典类型数据
  deleteDictType:(id)=>
    deleteRequest(`~/maintenance/basis/dict/type/${id}`),
  //修改数据字典
  updateDict:(data)=>
    putRequest(`~/maintenance/basis/dict/`,{data}),

  //修改字典类型数据
  updateDictType:(data)=>
    putRequest(`~/maintenance/basis/dict/type`,{data})

}
