<template>
  <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="800px">
    <el-card>
      <div class="dialogStyle" style="display: flex;flex-direction: column">
        <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm">
          <el-form-item label="物料专用号">
            <el-input placeholder="物料专用号" v-model="search_keys.materialCode" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="供应商编号">
            <el-input placeholder="供应商编号" v-model="search_keys.supplierCode" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="供应商名称">
            <el-input placeholder="供应商名称" v-model="search_keys.supplierName" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="配额">
            <el-input placeholder="配额" v-model="search_keys.materialQuota" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item style="float: right">
            <el-button type="primary" :disabled="false"  @click="upsertQuotaInfo({search_keys})" icon="el-icon-s-check" >
              更新配额
            </el-button>
          </el-form-item>
          <el-form-item style="float: right">
            <el-button type="primary" :disabled="false"  @click="exec_search({search_keys})" icon="el-icon-s-check" >
              查询
            </el-button>
          </el-form-item>
        </el-form>
        <el-table
                ref="multipleTable"
                :data="search_result"
                size="mini"
                :height="600"
                class="search-result-table"
                @cell-click="cellClick"
                style="flex: 1"
                border
                tooltip-effect="dark">
          <el-table-column prop="materialCode"  width="170" align="center" label="物料专用号"  />
          <el-table-column prop="supplierCode"  width="170" align="center" label="供应商编码"  />
          <el-table-column prop="supplierName" width="250" :show-overflow-tooltip="true" align="center" label="供应商名称"  />
          <el-table-column prop="materialQuota" label="配额" align="center" width="100"/>
<!--          <el-table-column fixed="right" width="120" label="操作" >-->
<!--          <template slot-scope="scope" >-->
<!--          <el-button title="删除" type="primary" size="mini" class="btn-opt" plain  @click="deleteQuota(scope.row.materialCode)">-->
<!--          删除</el-button>-->
<!--          </template>-->
<!--          </el-table-column>-->
        </el-table>
      </div>
    </el-card>
    <div class="dialogButton">
<!--      <el-button type="primary" size="mini"  @click="confirmDeliery()"><i-->
<!--              class="fa fa-check"></i> 配置配额-->
<!--      </el-button>-->
      <el-button type="primary" size="mini" @click="cancel ">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import ui_config from '@/config/ui_config'
  import activityService from '@/api/basedata/matter'
  export default {
    name: "QuotaEdit",
    props:{
      multipleSelection: {},
    },
    data(){
      return{
        dialogFormVisible: true,
        title:"配额维护",
        materialCode:"",
        supplierCode:"",
        supplierName:"",
        materialQuota:"",
        search_keys:{
          materialCode:"",
          supplierCode:'',
          supplierName:"",
          materialQuota:""
        },
        old_search_keys:{},
        search_result         : [],
      }
    },
    async mounted(){
      // this.exec_search({search_keys:this.search_keys});
    },
    methods:{
      cellClick(row,column,cell,event){
        this.search_keys={
          materialCode:row.materialCode,
          supplierCode:row.supplierCode,
          supplierName:row.supplierName,
          materialQuota:row.materialQuota
        }
      },
      exec_search({search_keys = JSON.parse(this.search_keys_snap)}) {
        let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
        let data={
          ...search_keys
        };
        return activityService.findQuotaInfoList({...data}).then(resp => {
          this.search_result = resp.data.resultList;                //视图展示查询结果
          this.old_search_keys=JSON.stringify(data);
          this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
          this.search_keys_snap = search_keys_snap;             //存储查询条件快照
        }, err => {
          console.error(err);
        })
      },
      cancel() {
        this.dialogFormVisible = false
      },
      upsertQuotaInfo({search_keys}){
        this.$confirm("确定配置吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'warning',
          center: true
        }).then(() => {
            let data = {
              ...search_keys
            // materialCode: this.form.materialCode || undefined,
            // supplierCode: this.form.supplierCode || undefined,
            // supplierName: this.form.supplierName || undefined,
            // materialQuota: this.form.materialQuota || undefined,
          }
           activityService.upsertQuotaInfo({...data}).then(async resp=>{
            if (resp.data.code=="201"){
              this.$notify({message: resp.data.message, type: "success"});
              // this.dialogFormVisible = false
              let result={
                materialCode:data.materialCode,
              }
              await this.exec_search({search_keys:result});
              let sum=0;
              this.search_result.filter(item=>{
                return item.materialCode=result.materialCode
              }).forEach(item=>{
                sum =sum+ Number(item.materialQuota)
              })

              if (sum != 1 ){
                this.$notify({message: "同物料不同供应商的配额不等于1，请及时修改。", type: "warning"});
              }
            } else {
              this.$notify({message: resp.data.message, type: "error"});
            }
          })
        }
        ).catch(() => {
          this.internal_activated = true;
        })
      },
      deleteQuota(materialCode){
        this.$confirm("确定删除吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'warning',
          center: true
        }).then(() => {
          activityService.deleteQuotaInfoById({materialCode}).then(resp=>{
            if (resp.data.code=="201"){
              this.$notify({message: resp.data.message, type: "success"});
              // this.dialogFormVisible = false
            } else {
              this.$notify({message: resp.data.message, type: "error"});
            }
          })
        }
        ).catch(() => {
          this.internal_activated = true;
        })
      }
    }
  }
</script>

<style scoped>

</style>