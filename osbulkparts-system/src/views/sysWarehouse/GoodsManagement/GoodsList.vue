<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
              <el-form-item label="成品编码">
                  <el-input placeholder="成品编码" v-model="search_keys.orderCode" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="物料专用号">
                <el-input placeholder="物料专用号" v-model="search_keys.materialCode" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="航次">
                <el-input placeholder="航次" v-model="search_keys.shipNo" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="集装箱号">
                <el-input placeholder="集装箱号" v-model="search_keys.containerNo" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="合同号">
                <el-input placeholder="合同号" v-model="search_keys.contractNo" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="search_keys.state" class="search-form-item-input" size="mini" knx>
                  <el-option value=""></el-option>
                  <el-option
                    size="mini"
                    v-for="item in goodsStatus"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
                <el-collapse accordion>
                    <el-collapse-item>
                        <template slot="title">
                            <i class="header-icon el-icon-s-operation">展开所有查询条件</i>
                        </template>
                        <div>
                            <!--<el-form-item label="库存数量">-->
                                <!--<el-input placeholder="HS海关编码" v-model="search_keys.stockAmount" class="search-form-item-input"></el-input>-->
                            <!--</el-form-item>-->
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
                                <!--<el-input placeholder="创建时间" v-model="search_keys.createTime" class="search-form-item-input"></el-input>-->
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
                <!--<el-form-item style="float: right">-->
                    <!--<el-button type="primary" v-if="subject.hasPermissions('*')" @click="exportData(old_search_keys)" size="mini" >-->
                        <!--<i class="fa fa-plus" aria-hidden="true"></i> 导出-->
                    <!--</el-button>-->
                <!--</el-form-item>-->
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:goods:list:send')" :disabled="multipleSelection.length==0" @click="sendGoods" icon="el-icon-delete" >
                        确认收货
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary"  @click="reset" icon="el-icon-error" >
                        清空
                    </el-button>
                </el-form-item>

                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:goods:list:view')" @click="exec_search({search_keys, pageNum:1})" native-type="submit" >
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
                  :row-class-name="({row,rowIndex}) => { return row.state==1 ? 'locked-row' : null; }"
                  @row-click="clickRow"
                  @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" fixed width="50" align="center"/>
<!--            <el-table-column prop="orderCode"  width="100" align="center" label="订单产品型号"/>-->
<!--            <el-table-column prop="orderDetailInfo.orderCodeDesc"  width="100" align="center" label="订单产品型号描述"  />-->
<!--            <el-table-column prop="orderDetailInfo.orderAmount"  width="100" align="center" label="订单数量"  />-->
            <el-table-column prop="orderDetailInfo.orderDate"  width="100" align="center" label="订单日期">
              <template slot-scope="scope">
                {{scope.row.orderDetailInfo.orderDate != null ?$moment(scope.row.orderDetailInfo.orderDate,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
              </template>
            </el-table-column>
<!--            <el-table-column prop="dictOrderUnit.name" width="100" align="center" label="订单型号单位" />-->
            <el-table-column prop="orderDetailInfo.orderId" width="100" align="center" label="订单号" />
            <el-table-column prop="orderDetailInfo.orderIdItem" width="100" align="center" label="订单行项目" />
            <el-table-column prop="materialCode"  width="100" align="center" label="物料专用号"/>
            <el-table-column prop="orderDetailInfo.materialDescCn"  :show-overflow-tooltip="true" align="center" label="物料中文描述"  />
            <el-table-column prop="orderDetailInfo.materialDescEn"  :show-overflow-tooltip="true" align="center" label="物料英文描述"  />
            <el-table-column prop="orderDetailInfo.materialDescRn"  :show-overflow-tooltip="true" align="center" label="物料俄文描述"  />
            <el-table-column prop="dictMaterialUnit.name"  :show-overflow-tooltip="true" align="center" label="物料单位"  />
            <el-table-column prop="orderDetailInfo.materialAmount"  :show-overflow-tooltip="true" align="center" label="物料数量"  />
            <el-table-column prop="dictMaterialCategory.name"  :show-overflow-tooltip="true" align="center" label="物料类别"  />
            <el-table-column prop="orderDetailInfo.materialRelation"  :show-overflow-tooltip="true" align="center" label="换算关系"  />
            <el-table-column prop="dictRelationUnit.name"  :show-overflow-tooltip="true" align="center" label="换算后单位"  />
            <el-table-column prop="materialRelationQuantity"  :show-overflow-tooltip="true" align="center" label="换算后数量"  />
            <el-table-column prop="shipNo" width="100" align="center" label="航次" />
            <el-table-column prop="sendAmount"  :show-overflow-tooltip="true" align="center" label="发货数量"  />
            <el-table-column prop="dictTransportation.name"  :show-overflow-tooltip="true" align="center" label="运输方式"  />
            <el-table-column prop="billNo"  :show-overflow-tooltip="true" align="center" label="提单号"  />
            <el-table-column prop="containerNo"  :show-overflow-tooltip="true" align="center" label="集装箱号"  />
            <el-table-column prop="contractNo" align="center" label="合同号"  />
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
            <el-table-column fixed="right" prop="dictGoodsStatus.name"   width="80" label="状态" />

        </el-table>
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
    import activityService from '@/api/warehouse/GoodsList'
    import ui_config from '@/config/ui_config'
    import ImportButton from '@/components/data-import/ImportButton'
    import {downloadBlobResponse} from '@/utils/request_utils'

    export default {
        data() {
            return {
                PAGE_SIZES : ui_config.PAGE_SIZES,
                link_modal_state      : {},
                //单位下拉框数据
                is_searching : true,
                goodsStatus:[],
                materialCategorys:[],
                old_search_keys:{},
                search_keys   : {
                    orderCode:"",
                    materialCode:'',
                    shipNo:"",
                    containerNo:"",
                    contractNo:"",
                    createUser:'',
                    createTime:'',
                    createTimeArray:'',
                    updateUser:'',
                    updateTimeArray:'',
                    updateTime:'',
                },
                search_keys_snap      : null,
                search_result         : {},
                multipleSelection:[],
                idsStr:[],
            };
        },
        components:{ImportButton},
        async mounted() {
            await this.init();
            this.exec_search({search_keys:this.search_keys, pageNum:1});
        },
        computed:{

        },
        methods: {
            reset(){
              this.search_keys  ={
                orderCode:"",
                materialCode:'',
                shipNo:"",
                containerNo:"",
                contractNo:"",
                createUser:'',
                createTime:'',
                createTimeArray:'',
                updateUser:'',
                updateTimeArray:'',
                updateTime:'',
              }
            },
            price(row, column, cellValue, index){
                if (cellValue) {
                    return cellValue.toFixed(2)
                }else {
                    return ""
                }
            },
            init(){
              return activityService.initView().then(resp=>{
                this.goodsStatus=resp.data.result.goodsStatus
              },error => {

              })
            },
            clickRow(row){
                this.$refs.tb.toggleRowSelection(row);
            },
            exec_search({
                            search_keys = JSON.parse(this.search_keys_snap),
                            pageNum = this.search_result.pageNum,
                            pageSize = this.search_result.pageSize,
                        }) {
                let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
                let data={
                    ...search_keys,
                    createTimeStart     :   search_keys.createTimeArray && search_keys.createTimeArray[0] || "",
                    createTimeEnd       :   search_keys.createTimeArray && this.$moment(search_keys.createTimeArray[1],'YYYYMMDDHHmmss').add(1, 'days').format('YYYYMMDDHHmmss')    || "",
                    updateTimeStart     :   search_keys.updateTimeArray && search_keys.updateTimeArray[0] || "",
                    updateTimeEnd       :   search_keys.updateTimeArray && this.$moment(search_keys.updateTimeArray[1],'YYYYMMDDHHmmss').add(1, 'days').format('YYYYMMDDHHmmss')    || "",
                };
                activityService.findGoodsList({...data, pageNum, pageSize}).then(resp => {
                    this.search_result = resp.data.resultInfo;                //视图展示查询结果
                    this.old_search_keys=data;
                    this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照
                }, err => {
                    console.error(err);
                })
            },
            // exportData(old_search_keys) {
            //     this.$confirm("确定导出数据吗？", "提示", {
            //         confirmButtonText: "是",
            //         cancelButtonText: "否",
            //         type: 'info',
            //         center: true
            //     }).then(() => {
            //         activityService.exportData({...old_search_keys}).then(resp=>{
            //             downloadBlobResponse(resp); // 文件下载
            //         });
            //     }).catch(() => {
            //         this.internal_activated = true;
            //     })//删除
            // },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            sendGoods() {
                this.$confirm("确认收货吗？", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
                    type: 'warning',
                    center: true
                }).then(() => {
                        this.idsStr=[];
                        this.multipleSelection.forEach(item=>{
                            this.idsStr.push(item.id)
                        });
                        activityService.sendGoods({idsStr:this.idsStr}).then(resp => {
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
                })//删除
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