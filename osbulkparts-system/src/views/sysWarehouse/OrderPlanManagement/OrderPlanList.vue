<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item label="HOPE号">
                    <el-input placeholder="HOPE号" v-model="search_keys.matterHNRNo" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="订单号">
                    <el-input placeholder="订单号" v-model="search_keys.matterNo" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="订单日期">
                    <el-date-picker
                      v-model="search_keys.date"
                      type="date"
                      placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="行项目">
                    <el-input placeholder="行项目" v-model="search_keys.matterCnDec" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料专用号">
                    <el-input placeholder="物料专用号" v-model="search_keys.matterEnDec" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料中文描述">
                    <el-input placeholder="物料中文描述" v-model="search_keys.matterEnDec" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料英文描述">
                    <el-input placeholder="物料英文描述" v-model="search_keys.matterEnDec" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料俄文描述">
                    <el-input placeholder="物料俄文描述" v-model="search_keys.matterEnDec" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="供应商编号">
                    <el-input placeholder="供应商编号" v-model="search_keys.matterEnDec" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="供应商名称">
                    <el-input placeholder="供应商名称" v-model="search_keys.scalerRela" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="单位">
                    <el-input placeholder="单位" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="计划数量">
                    <el-input placeholder="计划数量" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="确认数量">
                    <el-input placeholder="确认数量" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="国家标志">
                    <el-input placeholder="国家标志" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="收货数量">
                    <el-input placeholder="收货数量" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="发货数量">
                    <el-input placeholder="发货数量" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="差异数量">
                    <el-input placeholder="差异数量" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="剩余数量">
                    <el-input placeholder="剩余数量" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item style="float: right">
                    <import-button target = "ORDER_PLAN"></import-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="" @click="exportData" size="mini" >
                        <i class="fa fa-plus" aria-hidden="true"></i> 导出
                    </el-button>
                </el-form-item>

                <el-form-item style="float: right">
                    <el-button type="primary" @click="exec_search({search_keys, pageNum:1})" native-type="submit" >
                        <i class="fa fa-search" aria-hidden="true"></i> 查询
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button  @click="" icon="el-icon-download" >
                        模板下载
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button  @click="edit(multipleSelection)" icon="el-icon-download" >
                        修改
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button  @click="receive(multipleSelection)" icon="el-icon-download" >
                        收货
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button  @click="alert('发货')" icon="el-icon-download" >
                        发货
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button  @click="" icon="el-icon-delete" >
                        清空
                    </el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-table size="mini"
                  style="flex: 1"
                  :height="600"
                  class="search-result-table"
                  :data="search_result.list" row-key="id"
                  :stripe="true"
                  @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55"/>
            <el-table-column prop="matterHNRNo" fixed width="100" align="center" label="HOPE号"  />
            <el-table-column prop="matterNo" fixed width="100" align="center" label="订单号"  />
            <el-table-column prop="matterType" fixed width="100" align="center" label="订单日期" />
            <el-table-column prop="matterCnDec"  :show-overflow-tooltip="true" align="center" label="行项目"  />
            <el-table-column prop="matterEnDec"  :show-overflow-tooltip="true" align="center" label="物料专用号"  />
            <el-table-column prop="matterRuDec"  :show-overflow-tooltip="true" align="center" label="物料中文描述"  />
            <el-table-column prop="matterRuDec" align="center" label="物料英文描述"  />
            <el-table-column prop="matterRuDec" align="center" label="物料俄文描述"  />
            <el-table-column prop="unit" align="center" label="供应商编号"  />
            <el-table-column prop="scalerRela" align="center" label="供应商名称"  />
            <el-table-column prop="scalerUnit" align="center" label="单位"  />
            <el-table-column prop="scalerUnit" align="center" label="计划数量"  />
            <el-table-column prop="scalerUnit" align="center" label="确认数量"  />
            <el-table-column prop="scalerUnit" align="center" label="国家标志"  />
            <el-table-column prop="scalerUnit" align="center" label="收货数量"  />
            <el-table-column prop="currency" align="center" label="发货数量"  />
            <el-table-column prop="currency" align="center" label="差异数量"  />
            <el-table-column prop="currency" align="center" label="剩余数量"  />

            <el-table-column fixed="right" width="120" label="操作" >
                <template slot-scope="scope" >
                    <!--<el-button title="编辑与查看" type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row.uuid)">-->
                        <!--<i class="el-icon-news"></i></el-button>-->
                    <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="deleteMatter(scope.row.uuid)">
                        <i class="el-icon-delete"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <edit-order-plan v-bind.sync="link_modal_state" @success="reSearch" v-if="link_modal_state.activated"></edit-order-plan>
        <receive-goods v-bind.sync="link_modal_state_receive" @success="reSearch" v-if="link_modal_state_receive.activated"></receive-goods>
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
        <!--<div style="border: solid 1px red;flex: 1;height: 100%">1</div>-->
        <!--<div style="border: solid 1px green;flex: 1;height: 100%">2</div>-->
    </div>
</template>

<script>
    import activityService from '@/api/basedata/matter.js'
    import ui_config from '@/config/ui_config'
    import ImportButton from '@/components/data-import/ImportButton'
    import EditOrderPlan from './EditOrderPlan'
    import ReceiveGoods from './ReceiveGoods'

    export default {
        data() {
            return {
                PAGE_SIZES : ui_config.PAGE_SIZES,
                link_modal_state      : {},
                link_modal_state_receive:{},
                //单位下拉框数据
                is_searching : true,
                matterTypes:[],
                units:[],
                currencys:[],
                search_keys   : {
                    matterHNRNo:'',
                    matterNo:'',
                    matterType:'',
                    matterCnDec:'',
                    matterEnDec:'',
                    unit:'',
                    scalerRela:'',
                    scalerUnit:'',
                    currency:'',
                },
                multipleSelection: [],
                search_keys_snap      : null,
                search_result         : {},
            };
        },
        components:{ImportButton,EditOrderPlan,ReceiveGoods},
        mounted() {
            this.init();
            this.exec_search({search_keys:this.search_keys, pageNumber:1});
        },
        methods: {
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            init(){
                activityService.init().then(resp =>{
                    this.currencys = resp.data.currencys;
                    this.units = resp.data.units;
                    this.matterTypes = resp.data.matterTypes;
                }, err => {
                    console.error(err);
                })
            },
            exec_search({
                            search_keys = JSON.parse(this.search_keys_snap),
                            pageNumber = this.search_result.pageNum,
                            pageSize = this.search_result.pageSize,
                        }) {
                let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
                activityService.findMatterList({search_keys, pageNumber, pageSize}).then(resp => {
                    this.search_result = resp.data;                //视图展示查询结果
                    this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照
                }, err => {
                    console.error(err);
                })
            },
            exportData() {
                console("excel export")
            },
            //编辑
            edit(multipleSelection) {
                this.link_modal_state={activated:true,multipleSelection};
            },
            //收货
            receive(multipleSelection) {
                this.link_modal_state_receive={activated:true,multipleSelection};
            },
            //删除
            deleteMatter(uuid) {
                this.$confirm("确定删除吗？", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
                    type: 'warning',
                    center: true
                }).then(() => {
                      activityService.deleteById(uuid).then(resp => {
                          if (resp.data == 1) {
                              this.$notify({message: "删除成功", type: 'success'});
                          } else {
                              this.$notify({message: "删除失败", type: 'error'});
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