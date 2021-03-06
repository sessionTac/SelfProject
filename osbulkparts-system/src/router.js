import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/components/layout/index'

Vue.use(Router)

export const constantRoutes = [
    {
        path: '/',
        name: 'Root',
        redirect: {name: 'Login'}
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/sysPassport/Login')
    },
    //404
    {
        path: '/404',
        name: 'NotFound',
        component: ()=>import('@/views/common/404'),
    },
    //token自动恢复登录
    {
      path: '/quick_token_login',
      name: 'QuickTokenLogin',
      component: require('@/views/sysPassport/QuickTokenLogin').default,
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
                meta: { title: 'dashboard', icon: 'dashboard',affix: true,
                    requiresPermission: 'maintenance:dashboard',}
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
                    noCache: true,
                    breadcrumb: true,
                    keepAlive: false,
                    requiresPermission: 'maintenance:system:users:info:view', }
            },
            {
                path: 'roleFunction',
                component: () => import('@/views/sysUsers/UserRole.vue'),
                name: 'roleFunction',
                meta: { title: 'rolePermission',
                  icon: 'rolePermission', noCache: true,breadcrumb: true,keepAlive: false,
                    requiresPermission: 'maintenance:system:users:role:view'}
            }
        ]
    },
    {
        path: '/system',
        name:'system',
        component: Layout,
        redirect: {name: 'dictionary'},
        meta: { title: 'config',breadcrumb: true},
        children: [
            {
                path: 'dictionary',
                component: () => import('@/views/sysConfig/Dictionary.vue'),
                name: 'dictionary',
                meta: { title: 'dictionary',icon: 'dictionary',
                    noCache: true,breadcrumb: true,keepAlive: false,
                    requiresPermission: 'maintenance:system:dictionaryinfo'}
            },
            {
                path: 'categories',
                name: 'SystemDictionaryCategory',
                component: ()=>import('@/views/sysConfig/Category'),
                meta: {
                    title: 'categories',
                    breadcrumbTitle: '字典分类',
                    requiresPermission: 'maintenance:system:categoriesinfo',
                    activeMenuKey: 'BasicsDictionary',keepAlive: false
                },
            }
        ]
    },
    {
        path: '/basicsData',
        name:'basicsData',
        component: Layout,
        redirect: {name: 'basicsDataMatter'},
        meta: { title: 'basicsData',requiresPermission: 'maintenance:basis:matter:info',breadcrumb: true},
        children: [
            {
                path: 'basicsDataMatter',
                component: () => import('@/views/sysBasedata/MatterList.vue'),
                name: 'basicsDataMatter',
                meta: { title: 'basicsDataMatter',  icon: 'basicsDataMatter', noCache: true,breadcrumb:true,keepAlive: false,
                    requiresPermission: 'maintenance:system:dictionaryinfo'},

            },
            {
                path: 'basicsDataSupplier',
                component: () => import('@/views/sysBasedata/SupplierList.vue'),
                name: 'basicsDataSupplier',
                meta: { title: 'basicsDataSupplier',  icon: 'basicsDataMatter', noCache: true,breadcrumb:true,keepAlive: false,
                requiresPermission: 'maintenance:basis:supplier:info:view', },
            }
        ]
    },

    {
        path: '/warehouse',
        name:'warehouse',
        component: Layout,
        redirect: {name: 'warehouseOrderplan'},
        meta: { title: 'Warehouse',breadcrumb: true},
        children: [
            {
                path: 'warehouseOrderInfo',
                component: () => import('@/views/sysWarehouse/OrderPlanManagement/OrderPlanList.vue'),
                name: 'warehouseOrderplan',
                meta: { title: 'warehouseOrderplan',  icon: 'warehouseOrderplan', noCache: true,breadcrumb:true,keepAlive: false ,
                    requiresPermission: 'maintenance:warehouse:order:info:view',}
            },
            {
              path: 'planDetail',
              component: () => import('@/views/sysWarehouse/OrderPlanManagement/PlanDetailList.vue'),
              name: 'warehousePlanDetail',
              meta: { title: 'warehousePlanDetail',  icon: 'warehouseOrderplan', noCache: true,breadcrumb:true,keepAlive: false ,
                requiresPermission: 'maintenance:warehouse:plan:detail:view',}
            },
            {
                path: 'planDetailYear',
                component: () => import('@/views/sysWarehouse/OrderPlanManagement/PlanDetailList.vue'),
                name: 'warehousePlanDetailYear',
                meta: { title: 'warehousePlanDetailYear',  icon: 'warehouseOrderplan', noCache: true,breadcrumb:true,keepAlive: false ,
                    requiresPermission: 'maintenance:warehouse:plan:detail:view',flag:"year"}

            },
            {
                path: 'planDetailMonth',
                component: () => import('@/views/sysWarehouse/OrderPlanManagement/PlanDetailList.vue'),
                name: 'warehousePlanDetailMonth',
                meta: { title: 'warehousePlanDetailMonth',  icon: 'warehouseOrderplan', noCache: true,breadcrumb:true,keepAlive: false ,
                    requiresPermission: 'maintenance:warehouse:plan:detail:view',flag:"month"}

            },
            {
                path: 'planDetailWeek',
                component: () => import('@/views/sysWarehouse/OrderPlanManagement/PlanDetailList.vue'),
                name: 'warehousePlanDetailWeek',
                meta: { title: 'warehousePlanDetailWeek',  icon: 'warehouseOrderplan', noCache: true,breadcrumb:true,keepAlive: false ,
                    requiresPermission: 'maintenance:warehouse:plan:detail:view',flag:"week"}

            },
            {
                path: 'goodsList',
                component: () => import('@/views/sysWarehouse/GoodsManagement/GoodsList.vue'),
                name: 'warehouseGoodsList',
                meta: { title: 'warehouseGoodsList',  icon: 'warehouseOrderplan', noCache: true,breadcrumb:true,keepAlive: false ,
                    requiresPermission: 'maintenance:warehouse:goods:list:view'}

            },
            {
                path: 'inventoryInfoList',
                component: () => import('@/views/sysWarehouse/InventoryInfo/InventoryInfoList.vue'),
                name: 'warehouseInventoryInfoList',
                meta: { title: 'warehouseInventoryInfoList',  icon: 'warehouseCollection',
                    noCache: true,breadcrumb:true,keepAlive: true,
                    requiresPermission: 'maintenance:warehouse:stock:info:view',
                }
            },
            {
                path: 'planningBalanceList',
                component: () => import('@/views/sysWarehouse/PlanningBalanceTable/PlanningBalanceList'),
                name: 'warehousePlanningBalanceList',
                meta: { title: 'warehousePlanningBalanceList',  icon: 'warehouseCollection',
                    noCache: true,breadcrumb:true,keepAlive: true,
                    requiresPermission: 'maintenance:warehouse:planningbalance:info:view',
                }
            },
            {
                path: 'planningBalanceDetailList',
                component: () => import('@/views/sysWarehouse/PlanningBalanceTable/PlanningBalanceDetailList'),
                name: 'warehousePlanningBalanceDetailList',
                meta: { title: 'warehousePlanningBalanceDetailList',  icon: 'warehouseCollection',
                    noCache: true,breadcrumb:true,keepAlive: true,
                    requiresPermission: 'maintenance:warehouse:planningbalance:detail:view',
                }
            },
        ]
    }
];

export default new Router({
  routes : constantRoutes
})
