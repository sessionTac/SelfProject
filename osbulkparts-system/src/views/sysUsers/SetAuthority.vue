<template>
  <el-dialog title="权限设置" width="650px"
             :visible.sync="internal_activated" @closed="$emit('update:activated', false)">
    <!--<p>{{this.$route.query.row.username}} 权限设置</p>-->
    <!--<el-button type="primary" size="mini" style="float: right" class="btn-opt" @click="Return()">-->
    <!--返回</el-button>-->
    <div style="height: 500px;;width: 100%">
      <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
        <el-form-item>
          系统菜单
        </el-form-item>
        <div style="height: 450px;padding-left: 10%">
          <el-scrollbar style="height: 100%">
            <el-tree :data="maintainTree"
                     class="filter-tree"
                     default-expand-all
                     ref="maintainTree"
                     :filter-node-method="filterNode"
                     :props="defaultProps"
                     :show-checkbox="true"
                     :default-checked-keys="maintainFunctions"
                     node-key="functionId"
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
          label: 'functionName'
        },

        maintainFunctions:null,
        maintainTree: [],
        maintainLeafNodeIds: null,
      }
    },
    mounted() {
      this.init();
    },
    methods: {
      getCheckedKeys() {
        // console.log("tree",this.$refs.tree2.getCheckedKeys())

        let functionIds = [].concat(this.$refs.maintainTree.getCheckedKeys())
          .concat(this.$refs.maintainTree.getHalfCheckedKeys());

        console.log('functionIds', functionIds);

        let data = {
          functionIds,
          roleId: this.roleId || undefined,
        }
        service.insertPower({...data}).then(resp=>{
          this.$notify({type:resp.data.type, message:resp.data.msg});
          if(resp.data.type == "success"){
            this.internal_activated = false
          }
        })

      },
      init() {
        let treePromise = service.findFunctionTree().then(resp => {
          this.maintainTree = resp.data.maintainTree.tree;
          this.maintainLeafNodeIds = resp.data.maintainTree.leafNodeIds;
          return resp;
        });
        let roleFunctionPromise = service.findRoleDetail(this.roleId);//根据roleId查询functionId

        Promise.all([treePromise, roleFunctionPromise]).then(([treeResp, roleFunctionResp]) => {
          this.maintainFunctions = roleFunctionResp.data
            .map(e => Number.parseInt(e.functionId))
            .filter(id => {
              return treeResp.data.maintainTree.leafNodeIds.indexOf(id) !== -1;
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
