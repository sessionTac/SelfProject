export default [
  {
    title: '首页',
    requiresPermission: 'maintenance:index',
    iconClass:'iconfont icon-index',
    index: 'dashboard',

  },
  {
    title: '用户管理',
    requiresPermission: 'maintenance:system:users',
    index: 'users',
      children: [
          {
              title: '用户信息',
              requiresPermission: 'maintenance:system:user',
              index: 'userInfo',
          },
          {
              title: '角色权限',
              requiresPermission: 'maintenance:system:role',
              index: 'roleFunction',
          },
      ]
  },

  {
    title: '基础数据',
    requiresPermission: 'maintenance:basis:data',
    index: 'basicsData',
    children: [

      {
        title: '物料主数据',
        requiresPermission: 'maintenance:basis:matter',
        index: 'basicsDataMatter',
      },
    ]
  },

  {
      title: '出入库管理',
      requiresPermission: 'maintenance:basis:warehouse',
      index: 'basicsDataWarehouse',
      children: [

          {
              title: '订单计划',
              requiresPermission: 'maintenance:basis:orderplan',
              index: 'basicsDataWarehouseOrderplan',
          },
          {
              title: '收货管理',
              requiresPermission: 'maintenance:basis:collection',
              index: 'basicsDataWarehouseCollection',
          },
          {
              title: '发货管理',
              requiresPermission: 'maintenance:basis:delivery',
              index: 'basicsDataWarehouseDelivery',
          },
          {
              title: '滚动计划',
              requiresPermission: 'maintenance:basis:rollingplan',
              index: 'basicsDataWarehouseRollingplan',
          },
          {
              title: '财务模块',
              requiresPermission: 'maintenance:basis:finance',
              index: 'basicsDataWarehouseFinance',
          },
      ]
  }


]