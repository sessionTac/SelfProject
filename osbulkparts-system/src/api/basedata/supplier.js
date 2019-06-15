import {getRequest, postRequest, putRequest,deleteRequest} from "@/utils/request_utils";

export default {

    /**
     * 查询供应商数据列表
     */
    findSupplierList: (params) =>
        getRequest(`~/supplier/getSupplierList`, {params}),

    /**
     * 供应商数据列表初始化
     */
    initData: () =>
        getRequest(`~/supplier/init`),

    /**
     *
     */
    findSupplierInfo: (params) =>
      getRequest(`~/supplier/getSupplierInfo/`+params.supplierId),
    /**
     * 修改编辑
     */
    updateSupplier:(data)=>
      putRequest(`~/supplier/updateSupplierInfo`, {data}),
    /**新增一条物料数据*/
    addSupplier:(data)=>
      postRequest(`~/supplier/addSupplierInfo`, {data}),
    /***根据id删除本条数据*/
    deleteById:(data)=>
      putRequest(`~/supplier/deleteSupplier` ,{data}),











}
