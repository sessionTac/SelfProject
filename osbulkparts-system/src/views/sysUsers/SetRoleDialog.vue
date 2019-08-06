<template>
  <el-dialog :title="$t('pageTable.roleSet')" :visible.sync="user_role_setting" @closed="$emit('update:activated', false)" width="800px">
    <div class="dialogStyle" >
      <el-form class="search-form search-form-normal" :inline="true" label-width="100px" size="mini" ref="searchForm"
               :model="search_keys" @submit.native.prevent>
        <el-form-item :label="$t('searchFrom.roleName')">
          <el-input :placeholder="$t('searchFrom.roleName')" v-model="search_keys.roleName" size="mini" clearable></el-input>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" @click=" inquire({search_keys})" >
            <i class="fa fa-search" aria-hidden="true"></i>{{$t('searchFrom.search')}}
          </el-button>
        </el-form-item>
      </el-form>


      <el-button type="primary" class="btn-opt" style="float:right;margin-right: 10px" size="mini" @click="OnSubmit">
        {{$t('searchFrom.save')}}
      </el-button>

      <p>{{$t('Tips.ChoosingRoles')}}</p>
      <div>
        <el-table size="mini"
                  class="search-result-table"
                  :data="search_result"
                  tooltip-effect="dark"
                  row-key="roleId"
                  @selection-change="handleSelectionChange"
                  ref="roleTable">
          <el-table-column reserve-selection type="selection" width="55"></el-table-column>
          <el-table-column prop="roleName" :label="$t('pageTable.roleName')"></el-table-column>
          <el-table-column prop="roleDesc" :label="$t('pageTable.roleExplain')"></el-table-column>
        </el-table>
      </div>
    </div>
  </el-dialog>
</template>

<script>
  import activityService from '@/api/users/users'

  export default {
    props: {
      userId: {},
    },
    data() {
      return {
        user_role_setting: true,
        search_keys: {
          roleName: null,
          /**隶属平台*/
          subjectionPlatform: ''
        },
        search_result: null,
        search_key_snap: null,
        roleIds: [],
        selectRole: [],
        /**隶属平台接受数组*/
        subjectionPlatform: [],
        multipleSelection: [],

      }
    },
    mounted() {
      this.init();
      // window.table1 = this.$refs.roleTable;
      // console.log('mounted setrooledialog')
    },
    computed: {},
    methods: {
      init() {

        //role列表
        let listPromise = activityService.findRole({roleName: this.search_keys.roleName || undefined}).then(resp => this.search_result = resp.data.resultList);
        // //用户的role
        let findRolePromise = activityService.findRoleById(this.userId);
        //
        Promise.all([listPromise, findRolePromise]).then(([listResp, findRoleResp]) => {
          let userRoleIds = findRoleResp.data.resultList.map(r => r.roleId) || [];//获取roleId
          listResp.filter(role => (userRoleIds.indexOf(role.roleId) !== -1)).forEach(role => {
            this.$refs.roleTable.toggleRowSelection(role, true)
          });

        })
      },
      exec_search() {
        activityService.findRole().then(resp => {
          this.search_result = resp.data;
        })
      },
      //保存
      OnSubmit() {
        this.roleIds = this.$refs.roleTable.store.states.selection.map(r => r.roleId);
        let data = {
          userId: this.userId || undefined,
          roleIds: this.roleIds || undefined,
        }
        activityService.insertRole({...data}).then(resp => {
          if (resp.data.code == '201') {
            this.$notify({message: resp.data.message, type: 'success'});
            this.user_role_setting = false
          } else {
            this.$notify({message: resp.data.message, type: 'error'});
          }
        })
      },
      //查询
      inquire({search_keys}) {

        let listPromise = activityService.findRole({roleName: this.search_keys.roleName || undefined}).then(resp => this.search_result = resp.data.resultList);


      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      }


    },
  }
</script>

<style scoped>

</style>
