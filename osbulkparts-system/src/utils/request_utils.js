import axios from 'axios'
import {API_HOME} from '@/config/url_config'


// import {API_HOME} from '@/configs/url_config'
// const API_HOME = 'http://127.0.0.1:8081'; //dev

let axiosService = axios.create();

// request拦截器
axiosService.interceptors.request.use(config => {
  // console.log('config.url=' + config.url);
  if (config.url.substr(0,2) === '~/') {
    if (localStorage.console_user_token) {
      config.headers['Authorization'] = 'Bearer '+ localStorage.console_user_token;
    }

    // 替换api前缀
    config.url = API_HOME + config.url.substr(1);

    // 用qs替代axios原配的paramsSerializer
    config.paramsSerializer = function(params) {
      return Qs.stringify(params, {arrayFormat: 'repeat'})
    };

    // 添加语言信息头
    // config.headers['KNX_UI_LOCALE'] = i18n.locale;


  }
  return config;
});

axiosService.interceptors.response.use(resp => {
    return Promise.resolve(resp);
}, err=> {
    // 断网
    // 500 通用国际化信息
    if (err.response && err.response.status == 400 ) {
        let ex = new BusinessException(err.response.data);
        Notification({type:'error', message:ex.errcode});
        console.error('服务器端返回业务异常:', ex);
        return Promise.reject(ex);
    } else if (err.response && (err.response.status == 500 || err.response.status == 404)) {
        let resp = err.response;
        let log_message = (resp.data.message!=='No message available' && resp.data.message) || err.message || '无错误信息';
        Notification.error({ title: '服务繁忙，请稍后'});
        console.error('服务器端返回状态码='+resp.status+'，message=', log_message , '，errcode=', resp.data.errcode,'，params=', resp.data.params,'，cause=', resp.data.cause,
        );
    } else if (err.response && err.response.status == 401) {
        Notification.error({ title: '', message: '请输入正确的账号和密码'});
    } else if (err.response && err.response.status == 403) {
        localStorage.console_user_token='';
        try{
            router.push({ name: 'Login', query: {redir: router.currentRoute.fullPath}});
            console.error('用户令牌无效，请重新登录', err);
        } catch(err) {
            console.error('req error',err);
        }
    } else {
        Notification.error({ title: '服务繁忙，请稍后'});
        console.error(err);
    }
    return Promise.reject(err);
});

let base = '';
// config.url =API_HOME + config.url.substr(1);
export function postRequest(url, {params, data, responseType} = {}) {
  return axiosService({

    method: 'post',
    url: `${base}${url}`,
    params,
    data,
    responseType,
  });
}

export function postFormData(url, {params, data, onUploadProgress, responseType, cancelToken}) {

  // Object类型转成FormData
  if (data instanceof Object && !(data instanceof FormData)) {
    let formData = new FormData();
    for (let key in data) {
      let val = data[key];
      if (val instanceof Array) {
        //数组val转成formData中多个相同的key
        val.forEach(it=>formData.append(key, it));
      } else {
        formData.append(key, val);
      }
    }
    data = formData;
  }

  return axiosService({
    method: 'post',
    url: `${base}${url}`,
    params,
    data,
    responseType,
    onUploadProgress,
    cancelToken,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
  });
}

export function putRequest(url, {params, data} = {}) {
  return axiosService({
    method: 'put',
    url: `${base}${url}`,
    params,
    data,
  });
}

export function deleteRequest(url, {params} = {}) {
  return axiosService({
    method: 'delete',
    url: `${base}${url}`,
    params
  });
}

// const getRequestSync = function(url, {params,responseType} = {}) {
//
//   log.error('暂时不支持同步调用');
//   return;
//
//   let toUrl;
//   if (url.substr(0,2) === '~/') {
//     toUrl = API_HOME + url.substr(1) + (params? '?' + Qs.stringify(params, {arrayFormat: 'repeat'}) : '');
//   } else {
//     toUrl = url;
//   }
//
//   let headers = {};
//
//   if (localStorage.console_user_token) {
//     headers['Authorization'] = 'Bearer '+ localStorage.console_user_token;
//   }
//
//   return $.ajax({
//     type: 'GET',
//     async: false,
//     url: toUrl,
//     headers,
//   });
//
// };

export function getRequest(url, {params, responseType, async=true} = {}) {

  if (!async) {
    return getRequestSync(...arguments);
  }
  // debugger
  return axiosService({
    method: 'get',
    url: `${base}${url}`,
    params,
    responseType
  });
}
