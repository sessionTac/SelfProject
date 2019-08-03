<template>
  <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="800px">
    <el-card>
      <div class="dialogStyle" style="display: flex;flex-direction: column">
        <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm">
          <el-form-item :label="$t('pageTable.MatterMaterialSpecificNumber')">
            <el-input :placeholder="$t('pageTable.MatterMaterialSpecificNumber')" v-model="search_keys.materialCode" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item :label="$t('pageTable.MatterSupplierCode')">
            <el-input :placeholder="$t('pageTable.MatterSupplierCode')" v-model="search_keys.supplierCode" class="search-form-item-input"></el-input>
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

          <el-table-column prop="materialTaxPrice" width="120" :show-overflow-tooltip="true" align="center" :label="$t('pageTable.MatterUntaxedUnitPrice')"  />
          <el-table-column prop="createUser" align="center" :label="$t('pageTable.createUser')"  />
          <!--            <el-table-column prop="createTime" align="center" label="创建时间"  />-->
          <el-table-column :label="$t('pageTable.createTime')" show-overflow-tooltip>
            <template slot-scope="scope">
              {{scope.row.createTime != null ?$moment(scope.row.createTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
            </template>
          </el-table-column>
          <el-table-column prop="fileName" align="center" :formatter="price" :label="$t('pageTable.img')"  >
            <template slot-scope="scope">
              <a :disabled="subject.hasPermissions('*')" style="text-decoration: underline;" @click="toImg(scope.row.filePath,scope.row.fileName)">{{scope.row.fileName}}</a>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
    <!--<div class="dialogButton">-->
      <!--<el-button type="primary" size="mini"  @click="confirmDeliery()"><i-->
        <!--class="fa fa-check"></i> {{$t('pageTable.ConfirmationOfDelivery')}}-->
      <!--</el-button>-->
      <!--<el-button size="mini" @click="cancel ">{{$t('searchFrom.cancel')}}</el-button>-->
    <!--</div>-->
  </el-dialog>
</template>

<script>
  import ui_config from '@/config/ui_config'
  import activityService from '@/api/basedata/matter'
  export default {
    name: "ReceiveGoods",
    props:{
      multipleSelection: {},
      dateFlag:{},
      materialCode:String,
      supplierCode:String,
    },
    data(){
      return{
        dialogFormVisible: true,
        title:this.$t('pageTable.PriceRecord'),
        search_keys:{
          materialCode:"",
          supplierCode:"",
        },
        search_result         : [],
      }
    },
    async mounted(){
      this.search_keys.materialCode=this.materialCode;
      this.search_keys.supplierCode=this.supplierCode;
      // await activityService.sendGoodsInit().then(resp=>{
      //   this.transportations=resp.data.result.transportation
      // });
      this.exec_search();
    },
    methods:{
      toImg(filePath,fileName) {

      },
      exec_search() {
        // activityService.finddeliverGoodsList({idsStr:this.multipleSelection,dateFlag:this.dateFlag}).then(resp => {
        //   this.search_result = resp.data.resultList;                //视图展示查询结果
        //   this.search_result.forEach(item=>{
        //     item.suggestedAmount=item.materialAmount-item.supperAmountQut.supperAmount
        //     if (item.suggestedAmount<0) {
        //       item.suggestedAmount=0
        //     }
        //   })
        // }, err => {
        //   console.error(err);
        // })
      },
      cancel() {
        this.dialogFormVisible = false
      },
      confirmDeliery(){
        // this.$confirm(this.$t("Tips.AreShipmentsConfirmed"), this.$t("Tips.tips"), {
        //   confirmButtonText: this.$t("Tips.yes"),
        //   cancelButtonText: this.$t("Tips.no"),
        //   type: 'warning',
        //   center: true
        // }).then(() => {
        //   // activityService.excuteDeliver({shipNo:this.shipNo,containerNo:this.containerNo,contractNo:this.contractNo,deliverInfoList:this.search_result}).then(resp=>{
        //   let amouts=[];
        //   this.search_result.forEach(item=>{
        //     amouts.push(item.materialAmount);
        //   })
        //   activityService.excuteDeliver({idsStr:this.multipleSelection,billNo:this.billNo,transportation:this.transportation,shipNo:this.shipNo,containerNo:this.containerNo,contractNo:this.contractNo,amouts:amouts,dateFlag:this.dateFlag}).then(resp=>{
        //     if (resp.data.code=="201"){
        //       this.$notify({message: resp.data.message, type: "success"});
        //       this.dialogFormVisible = false
        //     } else {
        //       this.$notify({message: resp.data.message, type: "error"});
        //     }
        //   })
        //   }
        // ).catch(() => {
        //   this.internal_activated = true;
        // })
      }
    }
  }
</script>

<style scoped>

</style>