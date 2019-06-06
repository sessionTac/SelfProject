<template>
  <el-dialog title="角色设置" :visible.sync="user_role_setting"  @closed="$emit('update:activated', false)" width="800px" >

    <el-form class="search-form search-form-normal" :inline="true" label-width="100px" size="mini" ref="searchForm" :model="search_keys" @submit.native.prevent>
      <el-form-item label="角色名称">
        <el-input placeholder="角色名称" v-model="search_keys.roleName" size="mini" clearable></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <!--<el-button type="primary" @click=" inquire({search_keys})" native-type="submit" >-->
          <!--<i class="fa fa-search" aria-hidden="true"></i> 查询-->
        <!--</el-button>-->
      </el-form-item>
    </el-form>

    <el-button type="primary" class="btn-opt" style="float:right" size="mini" @click="user_role_setting = false">
      返回</el-button>
    <el-button type="primary" class="btn-opt" style="float:right;margin-right: 10px" size="mini" @click="OnSubmit">
      保存</el-button>

    <p> 请选择你的角色</p>
    <div >
      <el-table size="mini"
                class="search-result-table"
                :data="newList"
                tooltip-effect="dark"
                row-key="id"
                ref="roleTable">
        <el-table-column reserve-selection type="selection" width="55"> </el-table-column>
        <el-table-column prop="id" label="角色编号" ></el-table-column>
        <el-table-column prop="roleName" label="角色名称" ></el-table-column>
      </el-table>
    </div>
  </el-dialog>
</template>

<script>
  import activityService from '@/api/users/users'

    export default {
      props:{
        userId : {},
      },
      data(){
        return{
          user_role_setting     : true,
          search_keys:{
            roleName:null,
            /**隶属平台*/
            subjectionPlatform:''
          },
          search_result:null,
          search_key_snap:null,
          roleIds:[],
          selectRole : [],
          /**隶属平台接受数组*/
          subjectionPlatform:[],


        }
      },
      mounted(){
         this.init();
         // window.table1 = this.$refs.roleTable;
         // console.log('mounted setrooledialog')
      },
      computed:{
        //计算属性进行条件查询
        newList(){

          let roleName = this.search_keys.roleName ||'';
          let platform = this.search_keys.subjectionPlatform || '';
          if (this.search_result == null){
            // this.search_result =[];
            return [];
          }else {
            let newList = this.search_result.filter(item => {

              return  (item.platform && item.platform.indexOf(platform) !== -1 || '')  //platform安全检查
                      &&
                      (item.roleName && item.roleName.indexOf(roleName) !== -1 ||'')   //roleName
            });
            return newList
          }

        }

      },
      methods:{
        init(){

          //role列表
          let listPromise = activityService.findRole().then(resp =>this.search_result = resp.data);
          let userId = this.userId || undefined;
          //用户的role
          let findRolePromise =  activityService.findRoleById(userId);

          Promise.all([listPromise,findRolePromise]).then(([listResp,findRoleResp])=>{
            let userRoleIds = findRoleResp.data.map(r=>r.roleId) || [];//获取roleId
            listResp.filter(role=>(userRoleIds.indexOf(role.userId)!==-1)).forEach(role=>{
              this.$refs.roleTable.toggleRowSelection(role, true)
            });

          })
        },
        exec_search(){
          activityService.findRole().then(resp =>{
              this.search_result = resp.data;
          })
        },
        //保存
        OnSubmit(){
         this.roleIds = this.$refs.roleTable.store.states.selection.map(r => r.userId);
          let data={
            userId  :      this.userId || undefined,
            roleIds : this.roleIds || undefined,
          }
          activityService.insertRole({...data}).then(resp=>{
             this.$notify({message: resp.data.msg, type: resp.data.type});
             if(resp.data.type == "success"){
               this.$emit("success");
               this.user_role_setting = false;
             }
          })
        },
        // //查询
        // inquire({search_keys}){
        //
        //   let params={
        //     roleName:search_keys.roleName ||'',
        //     platform:search_keys.subjectionPlatform || ''
        //   };
        //   // activityService.findRole(params).then(resp =>this.search_result = resp.data);
        //
        //
        // },



      },
    }
</script>

<style scoped>

</style>
