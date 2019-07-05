<template>
  <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="900px">
    <el-card>
      <div class="dialogStyle" style="display: flex;flex-direction: column">
        <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm">
          <el-form-item label="物料号">
            <el-input placeholder="物料号" v-model="materialCode" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="供应商代码">
            <el-input placeholder="供应商代码" v-model="supplierCode" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="供应商名称">
            <el-input placeholder="供应商名称" v-model="supplierName" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="配额">
            <el-input placeholder="配额" v-model="materialQuota" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item style="float: right">
            <el-button type="primary" :disabled="false"  @click="upsertQuotaInfo" icon="el-icon-s-check" >
              更新配额
            </el-button>
          </el-form-item>
          <el-form-item style="float: right">
            <el-button type="primary" :disabled="false"  @click="exec_search" icon="el-icon-s-check" >
              搜索配额
            </el-button>
          </el-form-item>
        </el-form>
        <el-table
                ref="multipleTable"
                :data="search_result"
                size="mini"
                :height="600"
                class="search-result-table"
                style="flex: 1"
                border
                tooltip-effect="dark">
          <el-table-column prop="materialCode"  width="170" align="center" label="物料号"  />
          <el-table-column prop="supplierCode"  width="170" align="center" label="供应商代码"  />
          <el-table-column prop="supplierName" width="250" :show-overflow-tooltip="true" align="center" label="供应商名称"  />
          <el-table-column prop="materialQuota" label="配额" align="center" width="100"/>
          <el-table-column fixed="right" width="120" label="操作" >
          <template slot-scope="scope" >
          <el-button title="删除" type="primary" size="mini" class="btn-opt" plain  @click="deleteQuota(scope.row.uuid)">
          删除</el-button>
          </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
    <div class="dialogButton">
      <el-button type="primary" size="mini"  @click="confirmDeliery()"><i
              class="fa fa-check"></i> 配置配额
      </el-button>
      <el-button size="mini" @click="cancel ">取消</el-button>
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
        PAGE_SIZES : ui_config.PAGE_SIZES,
        idsStr:[],
        materialCode:"",
        supplierCode:"",
        supplierName:"",
        materialQuota:"",
        search_result         : [],
      }
    },
    async mounted(){

    },
    methods:{
      exec_search() {
        activityService.findQuota({idsStr:this.multipleSelection}).then(resp => {
          this.search_result = resp.data.resultList;                //视图展示查询结果
        }, err => {
          console.error(err);
        })
      },
      cancel() {
        this.dialogFormVisible = false
      },
      upsertQuotaInfo(){
        this.$confirm("确定更新吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'warning',
          center: true
        }).then(() => {
            let data = {
            materialCode: this.form.materialCode || undefined,
            supplierCode: this.form.supplierCode || undefined,
            supplierName: this.form.supplierName || undefined,
            materialQuota: this.form.materialQuota || undefined,
          }
          activityService.addQuota({...data}).then(resp=>{
            if (resp.data.code=="201"){
              this.$notify({message: resp.data.message, type: "success"});
              this.dialogFormVisible = false
            } else {
              this.$notify({message: resp.data.message, type: "error"});
            }
          })
        }
        ).catch(() => {
          this.internal_activated = true;
        })
      },
      deleteQuota(uuid){
        this.$confirm("确定删除吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'warning',
          center: true
        }).then(() => {
          activityService.deleteQuota({uuid}).then(resp=>{
            if (resp.data.code=="201"){
              this.$notify({message: resp.data.message, type: "success"});
              this.dialogFormVisible = false
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