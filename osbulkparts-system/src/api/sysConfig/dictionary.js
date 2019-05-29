import {getRequest, postRequest,putRequest,deleteRequest} from "@/utils/request_utils";

export default {

  /**
   * 查询字典类型
   * @param data
   * @returns {*}
   */
  findDictTypes:(params)=>
    getRequest(`~/dict/getDictTypeList`, {params}),
  /**
   * 查询字典类型的全部信息
   */
  findAllDictTypes:()=>
    getRequest(`~/dict/getDictType`),
  /**
   * 根据typeId 获取该 typeId下的所有data
   */
  findDataByDictTypeId:(params)=>
    getRequest(`~/dict/getDictDataInfo/`+params.dictTypeId, {params}),
  /**
   * 根据dictTypeId 获取一条type 详细信息
   */
  findTypeDetails:(params)=>
    getRequest('~/dict/getDictTypeInfo/'+params.dictTypeId)
  //增加数据
  // addDict:(data)=>
  //   postRequest(`~/maintenance/basis/dict/`, {data}),
  //修改字典类型数据
  // updateDictType:(data)=>
  //   putRequest(`~/maintenance/basis/dict/type`,{data})

}
