import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/components/layout/index'

Vue.use(Router)

export const constantRoutes = [
    {
        path: '/',
        name: 'Root',
        redirect: {name: 'login'}
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/sysPassport/Login')
    },

    {
        path: '/dashboard',
        name: 'dashboard',
        component: Layout,
        redirect: {name: 'dashboard'},
        children: [
            {
                path: 'dashboard',
                component: () => import('@/views/About.vue'),
                name: 'dashboard',
                meta: { title: 'dashboard', icon: 'dashboard',affix: true}
            }
        ]
    },

    {
        path: '/users',
        name:'users',
        component: Layout,
        redirect: {name: 'userInfo'},
      meta: { title: 'userinfo',breadcrumb: true},
        children: [
            {
                path: 'userInfo',
                component: () => import('@/views/sysUsers/UserList.vue'),
                name: 'userInfo',
                meta: { title: 'userinfo',
                  icon: 'userinfo',
                  noCache: true,breadcrumb: true,
                  keepAlive: true }
            },
            {
                path: 'roleFunction',
                component: () => import('@/views/sysUsers/UserRole.vue'),
                name: 'roleFunction',
                meta: { title: 'rolePermission',
                  icon: 'rolePermission', noCache: true,breadcrumb: true,keepAlive: true  }
            }
        ]
    },

    {
        path: '/basicsData',
        name:'basicsData',
        component: Layout,
        children: [
            {
                path: 'basicsDataMatter',
                component: () => import('@/views/sysBasedata/MatterList.vue'),
                name: 'basicsDataMatter',
                meta: { title: 'basicsDataMatter',  icon: 'basicsDataMatter', noCache: true,breadcrumb:true }
            }
        ]
    },

    {
        path: '/basicsDataWarehouse',
        name:'basicsDataWarehouse',
        component: Layout,
        children: [
            {
                path: 'basicsDataWarehouseOrderplan',
                component: () => import('@/views/sysWarehouse/OrderPlanManagement/OrderPlanList.vue'),
                name: 'basicsDataWarehouseOrderplan',
                meta: { title: 'basicsDataWarehouseOrderplan',  icon: 'basicsDataWarehouseOrderplan', noCache: true,breadcrumb:true }
            },
            {
                path: 'basicsDataWarehouseCollection',
                component: () => import('@/views/sysWarehouse/CollectionDeliveryManagement/CollectionList.vue'),
                name: 'basicsDataWarehouseCollection',
                meta: { title: 'basicsDataWarehouseCollection',  icon: 'basicsDataWarehouseCollection', noCache: true,breadcrumb:true }
            },
            {
                path: 'basicsDataWarehouseDelivery',
                component: () => import('@/views/sysWarehouse/CollectionDeliveryManagement/DeliveryList.vue'),
                name: 'basicsDataWarehouseDelivery',
                meta: { title: 'basicsDataWarehouseDelivery',  icon: 'basicsDataWarehouseDelivery', noCache: true,breadcrumb:true }
            },
            {
                path: 'basicsDataWarehouseRollingplan',
                component: () => import('@/views/sysWarehouse/RollingPlanManagement/RollingPlanList.vue'),
                name: 'basicsDataWarehouseRollingplan',
                meta: { title: 'basicsDataWarehouseRollingplan',  icon: 'basicsDataWarehouseRollingplan', noCache: true,breadcrumb:true }
            },
            {
                path: 'basicsDataWarehouseFinance',
                component: () => import('@/views/sysWarehouse/FinanceManangement/FinanceList.vue'),
                name: 'basicsDataWarehouseFinance',
                meta: { title: 'basicsDataWarehouseFinance',  icon: 'basicsDataWarehouseFinance', noCache: true,breadcrumb:true }
            }
        ]
    }
];

export default new Router({
  routes : constantRoutes
})
