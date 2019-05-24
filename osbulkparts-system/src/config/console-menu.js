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
    title: '系统设置',
    requiresPermission: 'maintenance:system:config',
    index: 'config',
    children: [
        {
            title: '数据字典',
            requiresPermission: 'maintenance:system:dictionary',
            index: 'dictionary',
        }
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
      index: 'warehouse',
      children: [

          {
              title: '订单计划',
              requiresPermission: 'maintenance:warehouse:orderplan',
              index: 'warehouseOrderplan',
          },
          {
              title: '收货管理',
              requiresPermission: 'maintenance:warehouse:collection',
              index: 'warehouseCollection',
          },
          {
              title: '发货管理',
              requiresPermission: 'maintenance:warehouse:delivery',
              index: 'warehouseDelivery',
          },
          {
              title: '滚动计划',
              requiresPermission: 'maintenance:warehouse:rollingplan',
              index: 'warehouseRollingplan',
          },
          {
              title: '财务模块',
              requiresPermission: 'maintenance:warehouse:finance',
              index: 'warehouseFinance',
          },
      ]
  }


]