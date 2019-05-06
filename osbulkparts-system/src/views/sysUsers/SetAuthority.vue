<template>
  <el-dialog title="权限设置" width="700px"
             :visible.sync="internal_activated" @closed="$emit('update:activated', false)">
    <!--<p>{{this.$route.query.row.username}} 权限设置</p>-->
    <!--<el-button type="primary" size="mini" style="float: right" class="btn-opt" @click="Return()">-->
    <!--返回</el-button>-->
    <div style="height: 500px;;width: 260px">
      <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
        <el-form-item>
          系统菜单
        </el-form-item>
        <div style="height: 450px">
          <el-scrollbar style="height: 100%">
            <el-tree :data="analysisTree"
                     class="filter-tree"
                     default-expand-all
                     ref="analysisTree"
                     :filter-node-method="filterNode"
                     :props="defaultProps"
                     :show-checkbox="true"
                     :default-checked-keys="analysisFunctions"
                     node-key="id"
                     @node-click="handleNodeClick">
            </el-tree>
          </el-scrollbar>
        </div>
      </el-form>
    </div>

    <span style="margin-left: 230px;">
      <el-button type="primary" size="mini" @click="getCheckedKeys">
        <i class="fa fa-check"></i> 确定</el-button>
      <el-button size="mini" @click="internal_activated = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import service from '@/api/users/users'

  export default {
    props: {
      editMark: Object,
      roleId: {},
      activated: {type: Boolean, default: false},
    },
    data() {
      return {
        internal_activated: true,
        search_keys: {
          checked: false
        },
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        //分析平台
        analysisFunctions: null,
        analysisTree: [
          {
            id:"1",
            label: "首页"
          }, {
            id:"2",
            label: '用户管理',
            children: [{
              id:"2-1",
              label: '用户信息',
              children: [{
                id:"2-1-1",
                label: '用户信息 查询按钮'
              }, {
                id:"2-1-2",
                label: '用户信息 修改按钮'
              }]
            },{
              id:"2-2",
              label: '角色权限',
              children: [{
                id:"2-2-1",
                label: '角色权限 查询按钮'
              }, {
                id:"2-2-2",
                label: '角色权限 修改按钮'
              }]
            }]
          }, {
            id:"3",
            label: '基础数据',
            children: [{
              id:"3-1",
              label: '物料主数据',
              children: [{
                id:"3-1-1",
                label: '物料主数据 查询按钮'
              }, {
                id:"3-1-2",
                label: '物料主数据 修改按钮'
              }]
            }]
          }, {
            id:"4",
            label: '出入库管理',
            children: [{
              id:"4-1",
              label: '订单计划',
              children: [{
                id:"4-1-1",
                label: '订单计划 查询按钮'
              }, {
                id:"4-1-2",
                label: '订单计划 修改按钮'
              }]
            }, {
              id:"4-2",
              label: '收货管理',
              children: [{
                id:"4-2-1",
                label: '收货管理 查询按钮'
              }, {
                id:"4-2-2",
                label: '收货管理 修改按钮'
              }]
            },{
              id:"4-3",
              label: '发货管理',
              children: [{
                id:"4-3-1",
                label: '发货管理 查询按钮'
              }, {
                id:"4-3-2",
                label: '发货管理 修改按钮'
              }]
            },{
              id:"4-4",
              label: '滚动计划',
              children: [{
                id:"4-4-1",
                label: '滚动计划 查询按钮'
              }, {
                id:"4-4-2",
                label: '滚动计划 修改按钮'
              }]
            },{
              id:"4-5",
              label: '财务模块',
              children: [{
                id:"4-5-1",
                label: '财务模块 查询按钮'
              }, {
                id:"4-5-2",
                label: '财务模块 修改按钮'
              }]
            }]
          }],
        analysisLeafNodeIds: null,
      }
    },
    mounted() {
      // this.init();
    },
    methods: {
      getCheckedKeys() {
        // console.log("tree",this.$refs.tree2.getCheckedKeys())

        let functionIds = [].concat(this.$refs.analysisTree.getCheckedKeys())
          .concat(this.$refs.analysisTree.getHalfCheckedKeys());

        console.log('functionIds', functionIds);

        alert(functionIds)
        let data = {
          functionIds,
          roleId: this.roleId || undefined,
        }
        // service.insertPower({...data}).then(resp=>{
        //   this.$notify({type:resp.data.type, message:resp.data.msg});
        //   if(resp.data.type == "success"){
        //     this.internal_activated = false
        //   }
        // })

      },
      init() {
        let treePromise = service.findFunctionTree().then(resp => {
          this.analysisTree = resp.data.tree;
          this.analysisLeafNodeIds = resp.data.leafNodeIds;
          return resp;
        });
        let roleFunctionPromise = service.findRoleDetail(this.roleId);//根据roleId查询functionId

        Promise.all([treePromise, roleFunctionPromise]).then(([treeResp, roleFunctionResp]) => {
          this.analysisFunctions = roleFunctionResp.data
            .map(e => Number.parseInt(e.functionId))
            .filter(id => {
              return treeResp.data.leafNodeIds.indexOf(id) !== -1;
            });
        });

      },

      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },

      handleCheckChange(data, checked, indeterminate) {
        console.log("check", data.id);
      },
      handleNodeClick(data) {
        console.log("data", data);
      },
      loadNode(node, resolve) {
        console.log(node, resolve);
        if (node.level === 0) {
          return resolve([{id: 'A01', label: 'region1'}, {id: 'A02', label: 'region2'}]);
        }
        if (node.level > 3) return resolve([]);

        var hasChild;
        if (node.data.label === 'region1') {
          hasChild = true;
        } else if (node.data.label === 'region2') {
          hasChild = false;
        } else {
          hasChild = Math.random() > 0.5;
        }

        setTimeout(() => {
          var data;
          if (hasChild) {
            data = [{
              label: 'zone' + this.count++
            }, {
              label: 'zone' + this.count++
            }];
          } else {
            data = [];
          }

          resolve(data);
        }, 500);
      },
      Return() {
        this.internal_activated = false;
        // this.$router.push({name:'SettingsRolesRoleList'});
      }
    },
    name: "SetAuthority"
  }
</script>

<style scoped>
  .span {
    font-size: 16px;
    font-weight: bolder;
  }

  .span-item {
    font-size: 14px;
    margin-left: 30px;
  }

  .checkAll {
    float: right;
    margin-right: 10px;
  }

  >>> .el-scrollbar__wrap {
    overflow-x: hidden;
  }
</style>
