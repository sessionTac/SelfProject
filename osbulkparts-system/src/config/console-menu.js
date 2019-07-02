export default [
  {
    title: '首页',
    requiresPermission: 'maintenance:index',
    iconClass:'iconfont icon-index',
    index: 'dashboard',

  },
  {
    title: '用户管理',
    requiresPermission: 'maintenance:system:users:manages',
    index: 'users',
      children: [
          {
              title: '用户信息',
              requiresPermission: 'maintenance:system:users:info:view',
              index: 'userInfo',
          },
          {
              title: '角色权限',
              requiresPermission: 'maintenance:system:users:role:view',
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
    requiresPermission: 'maintenance:basis:matter:info',
    index: 'basicsData',
    children: [
      {
        title: '物料主数据',
        requiresPermission: 'maintenance:basis:matter:info:view',
        index: 'basicsDataMatter',
      },
      {
        title: '供应商信息',
        requiresPermission: 'maintenance:basis:supplier:info:view',
        index: 'basicsDataSupplier',
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
              requiresPermission: 'maintenance:warehouse:order:info:view',
              index: 'warehouseOrderplan',
          },
          {
            title: '库存信息',
            requiresPermission: 'maintenance:warehouse:order:info:view',
            index: 'warehouseInventoryInfoList',
          },
          {
            title: '计划平衡表',
            requiresPermission: 'maintenance:warehouse:order:info:view',
            index: 'warehousePlanningBalanceList',
          },
          // {
          //     title: '预配发货',
          //     requiresPermission: 'maintenance:warehouse:collection',
          //     index: 'warehouseCollection',
          // },
          // {
          //     title: '发货明细',
          //     requiresPermission: 'maintenance:warehouse:delivery',
          //     index: 'warehouseDelivery',
          // },
          // {
          //     title: '发货综合查询',
          //     requiresPermission: 'maintenance:warehouse:rollingplan',
          //     index: 'warehouseRollingplan',
          // },
          // {
          //     title: '发货统计信息',
          //     requiresPermission: 'maintenance:warehouse:finance',
          //     index: 'warehouseFinance',
          // },
      ]
  }


]