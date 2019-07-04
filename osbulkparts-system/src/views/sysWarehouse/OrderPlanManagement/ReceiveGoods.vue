<template>
  <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="800px">
    <el-card>
      <div class="dialogStyle" style="display: flex;flex-direction: column">
        <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm">
          <el-form-item label="航次">
            <el-input placeholder="航次" v-model="shipNo" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="集装箱号">
            <el-input placeholder="集装箱号" v-model="containerNo" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="合同号">
            <el-input placeholder="合同号" v-model="contractNo" class="search-form-item-input"></el-input>
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
          <el-table-column prop="orderCode"  width="100" align="center" label="订单产品型号"  />
          <el-table-column prop="orderAmount"  width="80" align="center" label="订单数量"  />
          <el-table-column prop="orderDate"  width="100" align="center" label="订单日期">
            <template slot-scope="scope">
              {{scope.row.orderDate != null ?$moment(scope.row.orderDate,'YYYYMMDDHHmmss').format('YYYY-MM-DD') : ''}}
            </template>
          </el-table-column>
          <el-table-column prop="materialCode" width="120" :show-overflow-tooltip="true" align="center" label="物料号"  />
          <el-table-column prop="materialAmount" label="单耗数量" width="100">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialAmount"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="materialRelationQuantity"  :show-overflow-tooltip="true" width="100" align="center" label="换算后数量"  />
          <el-table-column prop="supperAmountQut.supperAmount" label="超发数量" width="120"/>
          <el-table-column prop="suggestedAmount" label="建议数量" width="120">
          </el-table-column>
          <!--<el-table-column fixed="right" width="120" label="操作" >-->
          <!--<template slot-scope="scope" >-->
          <!--<el-button title="收货" type="primary" size="mini" class="btn-opt" plain  @click="deleteMatter(scope.row.uuid)">-->
          <!--确认收货</el-button>-->
          <!--</template>-->
          <!--</el-table-column>-->
        </el-table>
      </div>
    </el-card>
    <div class="dialogButton">
      <el-button type="primary" size="mini"  @click="confirmDeliery()"><i
        class="fa fa-check"></i> 确定发货
      </el-button>
      <el-button size="mini" @click="cancel ">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import ui_config from '@/config/ui_config'
  import activityService from '@/api/warehouse/planDetail'
  export default {
    name: "ReceiveGoods",
    props:{
      multipleSelection: {},
      dateFlag:{},
    },
    data(){
      return{
        dialogFormVisible: true,
        title:"发货明细",
        PAGE_SIZES : ui_config.PAGE_SIZES,
        idsStr:[],
        shipNo:"",
        containerNo:"",
        contractNo:"",
        orderCode:"",
        orderDate:'',
        orderAmount:"",
        materialCode:"",
        materialAmount:"",
        materialRelationQuantity:"",
        supperAmount:"",
        suggestedAmount:"",
        search_result         : [],
      }
    },
    async mounted(){
      this.exec_search();
    },
    methods:{
      exec_search() {
        activityService.finddeliverGoodsList({idsStr:this.multipleSelection,dateFlag:this.dateFlag}).then(resp => {
          this.search_result = resp.data.resultList;                //视图展示查询结果
          this.search_result.forEach(item=>{
            item.suggestedAmount=item.materialAmount-item.supperAmountQut.supperAmount
            if (item.suggestedAmount<0) {
              item.suggestedAmount=0
            }
          })
        }, err => {
          console.error(err);
        })
      },
      cancel() {
        this.dialogFormVisible = false
      },
      confirmDeliery(){
        this.$confirm("确定发货吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'warning',
          center: true
        }).then(() => {
          // activityService.excuteDeliver({shipNo:this.shipNo,containerNo:this.containerNo,contractNo:this.contractNo,deliverInfoList:this.search_result}).then(resp=>{
          let amouts=[];
          this.search_result.forEach(item=>{
            amouts.push(item.materialAmount);
          })
          activityService.excuteDeliver({idsStr:this.multipleSelection,shipNo:this.shipNo,containerNo:this.containerNo,contractNo:this.contractNo,amouts:amouts}).then(resp=>{
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