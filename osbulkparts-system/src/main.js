import Vue from 'vue'
import Cookies from 'js-cookie'
import App from './App.vue'
import router from './router'
import store from './store/store'
import i18n from './lang' // internationalization
// import './plugins/element.js'

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})
import 'font-awesome/css/font-awesome.css'

// import 'viewerjs/dist/viewer.css'
import '@/components/viewerjs/css/viewer.scss'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import '@/styles/index.scss' // global css

import '@/assets/css/commen.css'
import '@/assets/css/flex-box.css'

import Subject from "@/utils/Subject"
Vue.config.productionTip = false

let subject = new Subject();

// subject.setPermissions('*')
Vue.mixin({ data() { return {subject};}});

router.beforeEach((to, from, next) => {

  let requiresPermission = to.meta.requiresPermission;

  if (!requiresPermission) {

    next();

  } else if (requiresPermission && subject.logged_in) {

    if (subject.hasPermissions(requiresPermission)) {
      console.log("1");
      next();
    } else {
      console.log("2");
      next({name: 'NotFound', query: {redir: to.fullPath}})
    }

  } else {

    next({name: 'QuickTokenLogin', query: {redir: to.fullPath}})

  }

});

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
