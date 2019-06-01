
import {getRequest,postRequest} from "@/utils/request_utils";
// import SHA256 from "crypto-js/sha256";
// import Base64 from "crypto-js/enc-base64";
import axios from 'axios'
import {API_HOME} from '@/config/url_config'


let passportService = {

  /**
   * 登录
   * @param username
   * @param password
   * @param platform
   * @returns {Promise<AxiosResponse<any> | never>}
   */
  login : ({username, password}) => {

    // let hashed = SHA256('jx-re-bigdata-'+password).toString(Base64);

    // console.log('hashed', hashed);

    return axios({
      method: 'post',
      url: `${API_HOME}/auth/login`,
      data: {username, password}
    }).then(resp=>resp.data);

  },

  /**
   * 选取role
   * @param tempToken
   * @param role_id
   * @returns console_user_token
   */
  select_role({tempToken, roleId}) {
    return axios({
      method: 'get',
      url: `${API_HOME}/auth/token/${roleId}`,
      headers: {
        'Authorization': 'osbulkparts '+ tempToken,
      },
    }).then(resp=>resp.data);

  },

  /**
   * 取用户信息（用户名、角色、权限信息）
   * @returns {*}
   */
  user_info() {
    return getRequest(`~/passport/userinfo`).then(resp =>resp.data);
  },
};
export default passportService;
