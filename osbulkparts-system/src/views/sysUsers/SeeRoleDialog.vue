<template>
  <el-dialog :title="$t('pageTable.roleSee')" :visible.sync="user_role_view" @closed="$emit('update:activated', false)" width="600px">
    <div class="dialogStyle">
      <el-table size="mini"
                class="search-result-table"
                :data="search_result"
                tooltip-effect="dark"
                ref="table">
        <el-table-column prop="roleId" :label="$t('pageTable.roleNo')" ></el-table-column>
        <el-table-column prop="mroleInfoEntity.roleName" :label="$t('pageTable.roleName')" ></el-table-column>
        <el-table-column prop="mroleInfoEntity.roleDesc" :label="$t('pageTable.roleExplain')" ></el-table-column>
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
        return {
          user_role_view:true,
          search_result:null,
        }
      },
      mounted(){
          this.init();
      },
      methods:{
        init(){
          activityService.findRoleById(this.userId).then(resp => this.search_result = resp.data.resultList);
        }
      },
    }
</script>

<style scoped>

</style>
