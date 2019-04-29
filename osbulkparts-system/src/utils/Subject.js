

/*
 * 用户前端认证授权操作门面类Subject
 *
 * @author puzhongqiang
 */

import shiroTrie from 'shiro-trie'

class Subject {

  permission_trie = shiroTrie.newTrie();
  __permission_ver = 1;

  roles = [];
  __roles_ver = 1;

  /**
   * 是否已登录
   * @type {boolean}
   */
  logged_in = false;

  // status = null;

  /**
   * 当前用户的信息
   * @type {Object}
   */
  principal = null;



  /**
   * 当前用户是否拥有指定的权限
   * @param permissions
   * @returns {boolean}
   */
  hasPermissions(permissions) {
    let args = [].concat.apply([], arguments);
    return this.__permission_ver && args.every(p=>this.permission_trie.check(p));
  };

  hasAnyPermissions(permissions) {
    let args = [].concat.apply([], arguments);
    return this.__permission_ver && args.some(p=>this.permission_trie.check(p));
  };

  login(user_info) {

    this.__permission_ver = this.__permission_ver + 1;

    this.logged_in = true;
    this.status = user_info.status;
    this.principal = {
      username  : user_info.userName,
      trueName  : user_info.trueName,
      orgName    : user_info.orgName,
      tel  : user_info.tel,
      email : user_info.email,
      // map_region : {"code":"360100","name":"南昌市"}, //地图组件使用的区域代码
      map_region : {"code":"360000","name":"江西省"}, //地图组件使用的区域代码
    };

    if (user_info.roles) this.setRoles(user_info.roles);
    if (user_info.functions) this.setPermissions(user_info.functions);
  }


  logout() {
    this.__permission_ver++;
    this.permission_trie.reset();
    this.__roles_ver++;
    this.roles = [];
    this.status = null;
    this.logged_in = false;
    this.principal = null;

    localStorage.removeItem('sysuser_token');
  };

  setPermissions(permissions) {
    let args = [].concat.apply([], arguments);
    this.__permission_ver += 1;
    this.permission_trie.reset();
    this.permission_trie = shiroTrie.newTrie();
    this.permission_trie.add(args);
  };

  setRoles(roles) {
    this.__roles_ver += 1;
    this.roles = [].concat.apply([], arguments);
  };

  /**
   * 当前用户是否拥有指定的角色
   * @param roles
   * @returns {boolean}
   */
  hasRoles(roles) {
    let args = [].concat.apply([], arguments);
    return this.__roles_ver && args.every(role=>this.roles.includes(role));
  };



}

export default Subject;
