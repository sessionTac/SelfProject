<template>
  <div style="display: flex;flex-direction: column;height: 100%">
    <div class="el-header">
      <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
        <el-form-item label="订单产品型号">
          <el-input placeholder="订单产品型号" v-model="search_keys.orderCode" class="search-form-item-input"></el-input>
        </el-form-item>
        <el-form-item label="订单日期">
          <el-date-picker
            class=""
            v-model="search_keys.orderDateArray"
            type="daterange"
            range-separator="至"
            value-format="yyyyMMddHHmmss"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="物料号">
          <el-input placeholder="物料号" v-model="search_keys.materialCode" class="search-form-item-input"></el-input>
        </el-form-item>
        <el-collapse accordion>
          <el-collapse-item>
            <template slot="title">
              <i class="header-icon el-icon-s-operation">展开所有查询条件</i>
            </template>
            <div>
              <el-form-item label="订单描述">
                <el-input placeholder="订单描述" v-model="search_keys.orderCodeDesc" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="订单号">
                <el-input placeholder="订单号" v-model="search_keys.orderId" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="物料中文描述">
                <el-input placeholder="物料中文描述" v-model="search_keys.materialDescCn" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="物料英文描述">
                <el-input placeholder="物料英文描述" v-model="search_keys.materialDescEn" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="物料俄文描述">
                <el-input placeholder="物料俄文描述" v-model="search_keys.materialDescRn" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="search_keys.confirmStatus"  size="mini" class="search-form-item-input">
                  <el-option value=""></el-option>
                  <el-option
                    size="mini"
                    v-for="item in confirmStatus"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="创建人">
                <el-input placeholder="创建人" v-model="search_keys.createUser" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="创建时间">
                <el-date-picker
                  class=""
                  v-model="search_keys.createTimeArray"
                  type="daterange"
                  range-separator="至"
                  value-format="yyyyMMddHHmmss"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="最后修改人">
                <el-input placeholder="最后修改人" v-model="search_keys.updateUser" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="最后修改时间">
                <el-date-picker
                  class=""
                  v-model="search_keys.updateTimeArray"
                  type="daterange"
                  range-separator="至"
                  value-format="yyyyMMddHHmmss"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
            </div>
          </el-collapse-item>
        </el-collapse>
        <el-form-item style="float: right">
          <el-button type="primary"  v-if="subject.hasPermissions('maintenance:warehouse:planningbalance:detail:approval')" :disabled="approvalFlag" @click="approval" icon="el-icon-s-check" >
            审批
          </el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary"  v-if="subject.hasPermissions('maintenance:warehouse:planningbalance:detail:goods')" @click="deliverGoods" icon="el-icon-s-check" >
            发货
          </el-button>
        </el-form-item>

        <el-form-item style="float: right">
          <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:planningbalance:detail:export')" @click="exportData(search_keys)" size="mini" >
            <i class="fa fa-plus" aria-hidden="true"></i> 导出
          </el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:planningbalance:detail:delete')" :disabled="multipleSelection.length==0" @click="deleteMatter" icon="el-icon-delete" >
            删除
          </el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary"  @click="reset" icon="el-icon-error" >
            清空
          </el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:planningbalance:detail:add')" @click="add()" icon="el-icon-plus" >
            添加
          </el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:planningbalance:detail:view')" @click="exec_search({search_keys, pageNum:1})" native-type="submit" >
            <i class="fa fa-search" aria-hidden="true"></i> 查询
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table size="mini"
              style="flex: 1"
              :height="600"
              ref="tb"
              border
              class="search-result-table"
              :data="search_result.list" row-key="id"
              :stripe="true"
              :row-class-name="({row,rowIndex}) => { return row.isLocked ? 'locked-row' : null; }"
              @row-click="clickRow"
              @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" fixed width="50" align="center"/>
      <el-table-column prop="orderCode"  width="100" align="center" label="订单产品型号"  />
      <el-table-column prop="orderCodeDesc"  width="100" align="center" label="订单产品型号描述"  />
      <el-table-column prop="orderAmount"  width="100" align="center" label="订单数量"  />
      <el-table-column prop="orderDate"  width="100" align="center" label="订单日期">
        <template slot-scope="scope">
          {{scope.row.orderDate != null ?$moment(scope.row.orderDate,'YYYYMMDDHHmmss').format('YYYY-MM-DD') : ''}}
        </template>
      </el-table-column>
      <!--<el-table-column prop="dictOrderUnit.name" width="100" align="center" label="订单型号单位" />-->
      <!--<el-table-column prop="orderId" width="100" align="center" label="订单号" />-->
      <!--<el-table-column prop="orderIdItem" width="100" align="center" label="订单行项目" />-->
      <el-table-column prop="materialCode"  :show-overflow-tooltip="true" align="center" label="物料号"  />
      <el-table-column prop="materialDescCn"  :show-overflow-tooltip="true" align="center" label="物料中文描述"  />
      <el-table-column prop="materialDescEn"  :show-overflow-tooltip="true" align="center" label="物料英文描述"  />
      <el-table-column prop="materialDescRn"  :show-overflow-tooltip="true" align="center" label="物料俄文描述"  />
      <!--<el-table-column prop="dictMaterialUnit.name"  :show-overflow-tooltip="true" align="center" label="物料单位"  />-->
      <el-table-column prop="materialAmount"  :show-overflow-tooltip="true" align="center" label="物料数量"  />
      <!--<el-table-column prop="dictMaterialCategory.name"  :show-overflow-tooltip="true" align="center" label="物料类别"  />-->
      <!--<el-table-column prop="materialRelation"  :show-overflow-tooltip="true" align="center" label="换算关系"  />-->
      <!--<el-table-column prop="dictRelationUnit.name"  :show-overflow-tooltip="true" align="center" label="换算后单位"  />-->
      <!--<el-table-column prop="materialRelationQuantity"  :show-overflow-tooltip="true" align="center" label="换算后数量"  />-->
<!--      <el-table-column prop="dictMinPackageType.name"  :show-overflow-tooltip="true" align="center" label="最小包装类型"  />-->
<!--      <el-table-column prop="materialMinpackageAmt"  :show-overflow-tooltip="true" align="center" label="最小包装数量"  />-->
<!--      <el-table-column prop="materialMinpackageTotalamt"  :show-overflow-tooltip="true" align="center" label="最小包装总量"  />-->
      <!--<el-table-column prop="materialTaxPrice"  :show-overflow-tooltip="true" align="center" label="未税单价"  />-->
      <!--<el-table-column prop="materialTaxTotalprice"  :show-overflow-tooltip="true" align="center" label="未税总价"  />-->
      <!--<el-table-column prop="materialVatPrice"  :show-overflow-tooltip="true" align="center" label="含税单价"  />-->
      <!--<el-table-column prop="materialVatTotalprice"  :show-overflow-tooltip="true" align="center" label="含税总价"  />-->
      <!--<el-table-column prop="materialRate"  :show-overflow-tooltip="true" align="center" label="代理费率"  />-->
      <!--<el-table-column prop="dictMaterialCurrency.name"  :show-overflow-tooltip="true" align="center" label="币种"  />-->
      <!--<el-table-column prop="dictCountryCode.name"  :show-overflow-tooltip="true" align="center" label="国家标志"  />-->

      <!--<el-table-column prop="orderOutTotalAmount"  :show-overflow-tooltip="true" align="center" label="型号发货总数量"  />-->
      <!--<el-table-column prop="materOutTotalAmount"  :show-overflow-tooltip="true" align="center" label="子件发货总数量"  />-->
      <!--<el-table-column prop="residualAmount"  :show-overflow-tooltip="true" align="center" label="订单剩余数量"  />-->
      <!--<el-table-column prop="trimAmount"  :show-overflow-tooltip="true" align="center" label="调整后数量"  />-->
      <el-table-column prop="stockAmount"  :show-overflow-tooltip="true" align="center" label="库存数量"  />
      <el-table-column prop="differAmount"  :show-overflow-tooltip="true" align="center" label="差异数量"  />
      <!--<el-table-column prop="takeOverAmount"  :show-overflow-tooltip="true" align="center" label="收货数量"  />-->
      <!--<el-table-column prop="deliveryAmount"  :show-overflow-tooltip="true" align="center" label="发货数量"  />-->
      <!--<el-table-column prop="surplusAmount"  :show-overflow-tooltip="true" align="center" label="物料剩余数量"  />-->


      <el-table-column prop="createUser" align="center" label="创建人"  />
      <el-table-column label="创建时间" show-overflow-tooltip>
        <template slot-scope="scope">
          {{scope.row.createTime != null ?$moment(scope.row.createTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
        </template>
      </el-table-column>
      <el-table-column prop="updateUser" align="center" label="最后修改人"  />
      <el-table-column label="最后修改时间" show-overflow-tooltip>
        <template slot-scope="scope">
          {{scope.row.updateTime != null ?$moment(scope.row.updateTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
        </template>
      </el-table-column>
        <el-table-column fixed="right" prop="dictConfirmStatus.name"  align="center" label="状态"  />
      <el-table-column fixed="right"  width="80" label="操作" >
        <template slot-scope="scope" >
          <el-button title="编辑与查看" v-if="subject.hasPermissions('maintenance:warehouse:planningbalance:detail:edit')"  type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row.id)">
            <i class="el-icon-news"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <edit-plan-detail v-bind.sync="link_modal_state" @success="exec_search({search_keys, pageNum:1})" v-if="link_modal_state.activated"></edit-plan-detail>
    <!--分页-->
    <div style="text-align: center">
      <el-pagination @current-change="exec_search({pageNum:$event})"
                     @size-change="exec_search({search_keys,pageSize:$event})"
                     :total="search_result.total"
                     :current-page="search_result.pageNum"
                     :page-size="search_result.pageSize"
                     :disabled="!search_result.total"
                     :page-sizes="PAGE_SIZES"
                     layout="total, sizes, prev, pager, next, jumper"
                     background/>
    </div>
  </div>
</template>

<script>
  import activityService from '@/api/warehouse/planningBalanceDetail'
  import ImportButton from '@/components/data-import/ImportButton'
  import EditPlanDetail from './EditPlanningBalanceDetail'
  import {downloadBlobResponse} from '@/utils/request_utils'
  import ui_config from '@/config/ui_config'
  export default {
    name: "PlanDetailList",
    data(){
      return{
        PAGE_SIZES : ui_config.PAGE_SIZES,
        link_modal_state      : {},
        multipleSelection:[],
        idsStr:[],
        search_keys:{
          orderCode:"",
          orderDateArray:'',
          materialCode:"",
          orderCodeDesc:"",
          orderId:"",
          materialDescCn:"",
          materialDescEn:"",
          materialDescRn:"",
          confirmStatus:"",
          createUser:"",
          createTimeArray:'',
          updateUser:"",
          updateTimeArray:'',
          isBalance:1
        },
        confirmStatus:[],
        options:{
          
        },
        old_search_keys:{},
        search_result         : {},

      }
    },
    components:{ImportButton,EditPlanDetail},

    computed:{
      approvalFlag(){
        return (this.multipleSelection.some(item=>{
          return item.confirmStatus==1
        }) || (this.multipleSelection.length===0))
      },
      // unlockFlag(){
      //   return (this.multipleSelection.some(item=>{
      //     return item.isLocked===0
      //   }) || (this.multipleSelection.length===0))
      // }
    },
    async mounted(){
      this.search_keys.orderCode=this.$route.query.orderCode;
      await activityService.initData().then(resp=>{
        this.confirmStatus=resp.data.result.orderStatus
      },error=>{})
      this.exec_search({search_keys:this.search_keys, pageNum:1});
    },
    methods:{
      reset(){
        this.search_keys= {

        };
      },
      price(row, column, cellValue, index){
        // if (cellValue) {
        //   return cellValue.toFixed(2)
        // }else {
        //   return ""
        // }
      },
      init(){
        // activityService.initData().then(resp =>{
        //   this.orderUnits = resp.data.result.orderUnits;
        //   this.orderStatus = resp.data.result.orderStatus;
        // }, err => {
        //   console.error(err);
        // })
      },
      exec_search({
                    search_keys = JSON.parse(this.search_keys_snap),
                    pageNum = this.search_result.pageNum,
                    pageSize = this.search_result.pageSize,
                  }) {
        let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
        let data={
          ...search_keys,
          orderDateStart      :   search_keys.orderDateArray  && search_keys.orderDateArray[0]    || "",
          orderDateEnd        :   search_keys.orderDateArray  && this.$moment(search_keys.orderDateArray[1],'YYYYMMDDHHmmss').add(1, 'days').format('YYYYMMDDHHmmss')    || "",
          createTimeStart     :   search_keys.createTimeArray && search_keys.createTimeArray[0],
          createTimeEnd       :   search_keys.createTimeArray && this.$moment(search_keys.createTimeArray[1],'YYYYMMDDHHmmss').add(1, 'days').format('YYYYMMDDHHmmss')    || "",
          updateTimeStart     :   search_keys.updateTimeArray && search_keys.updateTimeArray[0],
          updateTimeEnd       :   search_keys.updateTimeArray && this.$moment(search_keys.updateTimeArray[1],'YYYYMMDDHHmmss').add(1, 'days').format('YYYYMMDDHHmmss')    || "",
        };
        activityService.findOrderDetailInfoList({...data, pageNum, pageSize}).then(resp => {
          this.search_result = resp.data.resultInfo;                //视图展示查询结果
          this.old_search_keys=JSON.stringify(data);
          this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
          this.search_keys_snap = search_keys_snap;             //存储查询条件快照
        }, err => {
          console.error(err);
        })
      },
      clickRow(row){
        this.$refs.tb.toggleRowSelection(row);
      },
      exportData(search_keys) {
        this.$confirm("确定导出数据吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'info',
          center: true
        }).then(() => {
          let data=JSON.parse(this.old_search_keys);
          activityService.exportData({...data}).then(resp=>{
            downloadBlobResponse(resp); // 文件下载
          });
        }).catch(() => {
          this.internal_activated = true;
        })//删除
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      //添加
      add() {
        this.link_modal_state={activated:true,mode:"ADD"};
      },
      //编辑
      edit(id) {
        this.link_modal_state={activated:true,id,mode:"EDIT"};
      },
      //删除
      deleteMatter() {
        this.$confirm("确定删除吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'warning',
          center: true
        }).then(() => {
            this.idsStr=[];
            this.multipleSelection.forEach(item=>{
              this.idsStr.push(item.id)
            });
            activityService.deleteByIds({idsStr:this.idsStr}).then(resp => {
              if (resp.data.code=="201"){
                this.$notify({message: resp.data.message, type: "success"});
                this.exec_search({search_keys:this.search_keys, pageNum:1})
              } else {
                this.$notify({message: resp.data.message, type: "error"});
              }
            })
          }
        ).catch(() => {
          this.internal_activated = true;
        })
      },
      //发货
      deliverGoods(){
        this.$confirm("确定发货吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'warning',
          center: true
        }).then(() => {
            this.idsStr=[];
            this.multipleSelection.forEach(item=>{
              this.idsStr.push(item.id)
            });
            activityService.deliverGoodsByIds({idsStr:this.idsStr}).then(resp => {
              if (resp.data.code=="201"){
                this.$notify({message: resp.data.message, type: "success"});
                this.exec_search({search_keys:this.search_keys, pageNum:1})
              } else {
                this.$notify({message: resp.data.message, type: "error"});
              }
            })
          }
        ).catch(() => {
          this.internal_activated = true;
        })
      },
      //审批
      approval(){
        this.$confirm("确定审批吗？", "提示", {
          confirmButtonText: "是",
          cancelButtonText: "否",
          type: 'warning',
          center: true
        }).then(() => {
            this.idsStr=[];
            this.multipleSelection.forEach(item=>{
              this.idsStr.push(item.id)
            });
            activityService.approvalByIds({idsStr:this.idsStr}).then(resp => {
              if (resp.data.code=="201"){
                this.$notify({message: resp.data.message, type: "success"});
                this.exec_search({search_keys:this.search_keys, pageNum:1})
              } else {
                this.$notify({message: resp.data.message, type: "error"});
              }
            })
          }
        ).catch(() => {
          this.internal_activated = true;
        })
      },

    },


  }
</script>

<style scoped>
  .search-result-table >>> .el-table__body-wrapper::-webkit-scrollbar {
    width: 10px;
    height: 15px;
  }
  .search-result-table >>>.el-table__body-wrapper::-webkit-scrollbar-thumb {
    background-color: #a1a3a9;
    border-radius: 3px;
  }
</style>