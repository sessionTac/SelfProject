export default [
  {
    title: 'dashboard',
    requiresPermission: 'maintenance:dashboard',
    iconClass:'iconfont icon-index',
    index: 'dashboard',

  },
  {
    title: 'users',
    requiresPermission: 'maintenance:systemusers',
    index: 'users',
      children: [
          {
              title: 'userinfo',
              requiresPermission: 'maintenance:system:usersinfo',
              index: 'userInfo',
          },
          {
              title: 'rolePermission',
              requiresPermission: 'maintenance:system:usersrole',
              index: 'roleFunction',
          },
      ]
  },
  {
    title: 'config',
    requiresPermission: 'maintenance:systemconfig',
    index: 'config',
    children: [
        {
            title: 'dictionary',
            requiresPermission: 'maintenance:system:dictionaryinfo',
            index: 'dictionary',
        }
    ]
  },
  {
    title: 'basicsData',
    requiresPermission: 'maintenance:basisdata',
    index: 'basicsData',
    children: [
      {
        title: 'basicsDataMatter',
        requiresPermission: 'maintenance:basis:matterinfo',
        index: 'basicsDataMatter',
      },
      {
        title: 'basicsDataSupplier',
        requiresPermission: 'maintenance:basis:supplierinfo',
        index: 'basicsDataSupplier',
      },
    ]
  },

  {
      title: 'Warehouse',
      requiresPermission: 'maintenance:basis:warehouse',
      index: 'warehouse',
      children: [

          {
              title: 'warehouseOrderplan',
              requiresPermission: 'maintenance:warehouse:orderinfo',
              index: 'warehouseOrderplan',
          },
          {
            title: 'warehousePlanDetailYear',
            requiresPermission: 'maintenance:warehouse:order:info:view',
            index: 'warehousePlanDetailYear',
          },
          {
            title: 'warehousePlanDetailMonth',
            requiresPermission: 'maintenance:warehouse:order:info:view',
            index: 'warehousePlanDetailMonth',
          },
          {
            title: 'warehousePlanDetailWeek',
            requiresPermission: 'maintenance:warehouse:order:info:view',
            index: 'warehousePlanDetailWeek',
          },
          {
            title: 'warehouseGoodsList',
            requiresPermission: 'maintenance:warehouse:goodslist',
            index: 'warehouseGoodsList',
          },
          {
            title: 'warehouseInventoryInfoList',
            requiresPermission: 'maintenance:warehouse:stockinfo',
            index: 'warehouseInventoryInfoList',
          },
          {
            title: 'warehousePlanningBalanceList',
            requiresPermission: 'maintenance:warehouse:planningbalanceinfo',
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