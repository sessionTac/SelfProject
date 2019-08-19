<template>
  <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="800px">
    <el-card>
      <div class="dialogStyle" style="display: flex;flex-direction: column">
        <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm">
          <el-form-item :label="$t('pageTable.VoyageNumber')">
            <el-input :placeholder="$t('pageTable.VoyageNumber')" v-model="shipNo" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item :label="$t('pageTable.containerNo')">
            <el-input :placeholder="$t('pageTable.containerNo')" v-model="containerNo" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item :label="$t('pageTable.contractNo')">
            <el-input :placeholder="$t('pageTable.contractNo')" v-model="contractNo" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item :label="$t('pageTable.billNo')">
            <el-input :placeholder="$t('pageTable.billNo')" v-model="billNo" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item :label="$t('pageTable.TypeOfShipping')">
            <el-select v-model="transportation"  size="mini" class="search-form-item-input">
              <el-option value=""></el-option>
              <el-option
                size="mini"
                v-for="item in transportations"
                :key="item.value"
                :label="item.name"
                :value="item.value">
              </el-option>
            </el-select>
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
<!--          <el-table-column prop="orderCode"  width="100" align="center" :label="成品编码"  />-->
<!--          <el-table-column prop="orderAmount"  width="80" align="center" :label="订单数量"  />-->
          <el-table-column prop="orderDate"  width="100" align="center" :label="$t('pageTable.orderDate')">
            <template slot-scope="scope">
              {{scope.row.orderDate != null ?$moment(scope.row.orderDate,'YYYYMMDDHHmmss').format('YYYY-MM-DD') : ''}}
            </template>
          </el-table-column>
          <el-table-column prop="materialCode" width="120" :show-overflow-tooltip="true" align="center" :label="$t('pageTable.MatterMaterialSpecificNumber')"  />
          <el-table-column prop="materialAmount" :label="$t('pageTable.UnitConsumptionQuantity')" width="100">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialAmount" @blur="checkPrice(scope.row.materialAmount)"></el-input>
            </template>
          </el-table-column>
          <el-table-column prop="supperAmountQut.supperAmount" :label="$t('pageTable.supperAmount')" width="120"/>
          <el-table-column prop="suggestedAmount" :label="$t('pageTable.suggestedAmount')" width="120"></el-table-column>
            <el-table-column prop="materialRelationQuantity"  :show-overflow-tooltip="true" width="100" align="center" :label="$t('pageTable.ConvertedQuantity')"  />

          <!--<el-table-column fixed="right" width="120" :label="操作" >-->
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
        class="fa fa-check"></i> {{$t('pageTable.ConfirmationOfDelivery')}}
      </el-button>
      <el-button size="mini" @click="cancel ">{{$t('searchFrom.cancel')}}</el-button>
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
      multipleSelectionT:{},
      dateFlag:{},
    },
    data(){
      return{
        dialogFormVisible: true,
        title:this.$t('pageTable.DeliveryDetails'),
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
        transportation:"",
        billNo:"",
        transportations:[],
      }
    },
    async mounted(){
      await activityService.sendGoodsInit().then(resp=>{
        this.transportations=resp.data.result.transportation
      });
      this.exec_search();
    },
    methods:{
      exec_search() {
        activityService.finddeliverGoodsList({idsStr:this.multipleSelection,idsStrT:this.multipleSelectionT,dateFlag:this.dateFlag}).then(resp => {
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
        checkPrice(amount){
          let pattern = /^([0-9]*)+\.{0,1}[0-9]{1,3}$/;
          if(!pattern.test(amount)){
              this.$notify({message: this.$t('Tips.floatCheck'), type: "error"});
          }
        },
      cancel() {
        this.dialogFormVisible = false
      },
      confirmDeliery(){
        this.$confirm(this.$t("Tips.AreShipmentsConfirmed"), this.$t("Tips.tips"), {
          confirmButtonText: this.$t("Tips.yes"),
          cancelButtonText: this.$t("Tips.no"),
          type: 'warning',
          center: true
        }).then(() => {
          // activityService.excuteDeliver({shipNo:this.shipNo,containerNo:this.containerNo,contractNo:this.contractNo,deliverInfoList:this.search_result}).then(resp=>{
          let amouts=[];
          this.search_result.forEach(item=>{
            amouts.push(item.materialAmount);
          })
          activityService.excuteDeliver({idsStr:this.multipleSelection,idsStrT:this.multipleSelectionT,billNo:this.billNo,transportation:this.transportation,shipNo:this.shipNo,containerNo:this.containerNo,contractNo:this.contractNo,amouts:amouts,dateFlag:this.dateFlag}).then(resp=>{
            if (resp.data.code=="201"){
              this.$notify({message: resp.data.message, type: "success"});
              this.$emit("success");
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