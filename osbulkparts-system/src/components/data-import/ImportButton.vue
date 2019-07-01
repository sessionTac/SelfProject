<template>
  <div>
    <div>
      <el-button type="primary" @click="Import()"><i class="fa fa-cloud-upload" aria-hidden="true"></i> 导入</el-button>
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
              USER_INFO                   : '用户信息',
              MATTER                      : '物料数据',
              ORDER_PLAN                  : '订单计划',
              STOCK_INFO                  : '库存信息'
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
        }
      }
    }
</script>

<style scoped>

</style>
