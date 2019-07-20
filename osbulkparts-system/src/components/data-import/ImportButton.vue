<template>
  <div>
    <div>
      <el-button type="primary" @click="Import()"><i class="fa fa-cloud-upload" aria-hidden="true"></i> {{$t('searchFrom.import') }}</el-button>
    </div>
    <div>
      <import-data-components-dialog @saved="$emit('saved')" :target="target" :targetName="targetName" v-bind.sync="dialogState" v-if="dialogState.activated"></import-data-components-dialog>
    </div>
  </div>
</template>

<script>

  import ImportDataComponentsDialog from './ImportDataComponentsDialog.vue'

    export default {
        name: "",
      components:{
        ImportDataComponentsDialog
      },
      computed:{
        targetName() {
          return {
              USER_INFO                   : 'userinfo',
              MATTER                      : 'basicsDataMatter',
              ORDER_PLAN                  : 'warehouseOrderplan',
              ORDER_PLAN_BALANCE          : 'warehousePlanningBalanceList',
              STOCK_INFO                  : 'warehouseInventoryInfoList'
          }[this.target] || '未知导入类型';
        },
      },
      props:{
        target:String
      },
      data(){
          return{
            dialogState:{
              activated:false
            }
          }
      },

      methods:{
        Import(){
          this.dialogState = {activated:true,}
        },

      }
    }
</script>

<style scoped>

</style>
