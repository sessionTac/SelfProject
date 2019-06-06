import {getRequest, postRequest,postFormData} from "@/utils/request_utils";

export default {

  importData: (url,params,data,onUploadProgress) =>

    postFormData('~'+url,{params,data,onUploadProgress}),



}