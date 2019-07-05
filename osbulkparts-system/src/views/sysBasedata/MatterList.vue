<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item label="订单型号">
                    <el-input placeholder="订单型号" v-model="search_keys.materialOrderCode" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料号">
                    <el-input placeholder="物料号" v-model="search_keys.materialCode" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料CKD号">
                    <el-input placeholder="物料CKD号" v-model="search_keys.materialCkdCode" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料类别">
                    <el-select v-model="search_keys.materialCategory" class="search-form-item-input" size="mini" knx>
                        <el-option value=""></el-option>
                        <el-option
                                size="mini"
                                v-for="item in materialCategorys"
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
                            <el-form-item label="订单型号描述">
                                <el-input placeholder="订单型号描述" v-model="search_keys.materialOrderCodeDesc" class="search-form-item-input"></el-input>
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
                            <el-form-item label="HS海关编码">
                                <el-input placeholder="HS海关编码" v-model="search_keys.hsNo" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <el-form-item label="供应商编号">
                                <el-input placeholder="供应商编号" v-model="search_keys.supplierCode" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <el-form-item label="币种">
                                <el-select v-model="search_keys.materialCurrency"  size="mini" class="search-form-item-input">
                                    <el-option value=""></el-option>
                                    <el-option
                                            size="mini"
                                            v-for="item in currencys"
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
                                    <!--<el-date-picker-->
                                      <!--class="search-form-item-input"-->
                                      <!--v-model="search_keys.createTime"-->
                                      <!--type="date"-->
                                      <!--value-format="yyyyMMddHHmmss"-->
                                      <!--placeholder="选择日期">-->
                                    <!--</el-date-picker>-->
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
                                <!--<el-date-picker-->
                                  <!--class="search-form-item-input"-->
                                  <!--v-model="search_keys.updateTime"-->
                                  <!--type="date"-->
                                  <!--value-format="yyyyMMddHHmmss"-->
                                  <!--placeholder="选择日期">-->
                                <!--</el-date-picker>-->
                                <el-date-picker
                                  class=""
                                  v-model="search_keys.updateTimeArray"
                                  type="daterange"
                                  range-separator="至"
                                  value-format="yyyyMMddHHmmss"
                                  start-placeholder="开始日期"
                                  end-placeholder="结束日期">
                                </el-date-picker>
                                <!--<el-input placeholder="最后修改时间" v-model="search_keys.updateTime" class="search-form-item-input"></el-input>-->
                            </el-form-item>
                        </div>
                    </el-collapse-item>
                </el-collapse>
                <el-form-item style="float: right">
                    <el-button type="primary" :disabled="unlockFlag " v-if="subject.hasPermissions('maintenance:basis:matter:info:unlocked')" @click="lockData(false)" icon="el-icon-s-check" >
                        解锁
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" :disabled="lockFlag " v-if="subject.hasPermissions('maintenance:basis:matter:info:locked')" @click="lockData(true)" icon="el-icon-s-check" >
                        锁定
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <import-button v-if="subject.hasPermissions('maintenance:basis:matter:info:import')" @saved="exec_search({search_keys, pageNum:1})" target = "MATTER"></import-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:basis:matter:info:export')" @click="exportData(old_search_keys)" size="mini" >
                        <i class="fa fa-plus" aria-hidden="true"></i> 导出
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:basis:matter:info:delete')" :disabled="multipleSelection.length==0" @click="deleteMatter" icon="el-icon-delete" >
                        删除
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary"  @click="reset" icon="el-icon-error" >
                        清空
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:basis:matter:info:add')" @click="add()" icon="el-icon-plus" >
                        添加
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:basis:matter:info:select')" @click="exec_search({search_keys, pageNum:1})" native-type="submit" >
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
            <el-table-column prop="materialOrderCode"  width="100" align="center" label="订单型号"  />
            <el-table-column prop="materialOrderCodeDesc"  width="100" align="center" label="订单型号描述"  />
            <el-table-column prop="materialCkdCode"  width="100" align="center" label="物料CKD号"  />
            <el-table-column prop="materialCode"  width="100" align="center" label="子件型号"/>
            <el-table-column prop="dictMaterialCategory.name" width="100" align="center" label="物料类别" />
            <el-table-column prop="materialDescCn" width="150"  :show-overflow-tooltip="true" align="center" label="物料中文描述"  />
            <el-table-column prop="materialDescEn"  width="150" :show-overflow-tooltip="true" align="center" label="物料英文描述"  />
            <el-table-column prop="materialDescRn"  width="150" :show-overflow-tooltip="true" align="center" label="物料俄文描述"  />
            <el-table-column prop="hsNo" align="center" label="HS海关编码"  />
            <el-table-column prop="supplierCode" align="center" label="供应商代码"  />
            <el-table-column prop="materialQuota" align="center" :formatter="price" label="配额"  />
            <el-table-column prop="materialAmount" align="center" label="单耗"  />
            <el-table-column prop="dictMaterialUnit.name" align="center" label="单位"  />
            <el-table-column prop="materialRelation" align="center" label="换算关系"  />
            <el-table-column prop="dictMaterialRelationUnit.name" align="center" label="换算后单位"  />
<!--            <el-table-column prop="materialMinpackageAmt" align="center" label="最小包装数量"  />-->
<!--	          <el-table-column prop="dictMinpackageType.name" align="center" label="最小包装类型"  />-->
            <el-table-column prop="materialTaxPrice" align="center" :formatter="price" label="未税单价"  />
            <el-table-column prop="materialVatPrice" align="center" :formatter="price" label="含税单价"  />
            <el-table-column prop="tax" align="center" :formatter="price" label="税率"  />
            <el-table-column prop="materialPrice" align="center" :formatter="price" label="单价"  />
            <el-table-column prop="materialRate" align="center" label="代理费率"  />
            <el-table-column prop="dictMaterialCurrency.name" align="center" label="币种"  />
            <el-table-column prop="factoryCode" align="center" label="代理商"  />
            <el-table-column prop="length" align="center" label="长"  />
            <el-table-column prop="width" align="center" label="宽"  />
            <el-table-column prop="height" align="center" label="高"  />
            <el-table-column prop="createUser" align="center" label="创建人"  />
<!--            <el-table-column prop="createTime" align="center" label="创建时间"  />-->
            <el-table-column label="创建时间" show-overflow-tooltip>
                <template slot-scope="scope">
                    {{scope.row.createTime != null ?$moment(scope.row.createTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
                </template>
            </el-table-column>
            <el-table-column prop="updateUser" align="center" label="最后修改人"  />
<!--            <el-table-column prop="updateTime" align="center" label="最后修改时间"  />-->
            <el-table-column label="最后修改时间" show-overflow-tooltip>
                <template slot-scope="scope">
                    {{scope.row.updateTime != null ?$moment(scope.row.updateTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
                </template>
            </el-table-column>
            <el-table-column fixed="right" prop="dictLockStatus.name" align="center" label="是否锁定"  />
            <el-table-column fixed="right"  width="80" label="操作" >
                <template slot-scope="scope" >
                    <el-button title="编辑与查看" v-if="subject.hasPermissions('maintenance:basis:matter:info:edit')"  type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row.materialInfoId)">
                        <i class="el-icon-news"></i></el-button>
<!--                    <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="deleteMatter(scope.row.uuid)">-->
<!--                        <i class="el-icon-delete"></i></el-button>-->
                </template>
            </el-table-column>
        </el-table>
        <edit-matter v-bind.sync="link_modal_state" @success="exec_search({search_keys, pageNum:1})" v-if="link_modal_state.activated"></edit-matter>
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
    import EditMatter from './EditMatter'
    import {downloadBlobResponse} from '@/utils/request_utils'

    export default {
        data() {
            return {
                PAGE_SIZES : ui_config.PAGE_SIZES,
                link_modal_state      : {},
                //单位下拉框数据
                is_searching : true,
                materialCategorys:[],
                old_search_keys:{},
                currencys:[],
                search_keys   : {
                    materialOrderCode:'',
                    materialOrderCodeDesc:'',
                    materialCode:'',
                    materialCkdCode:'',
                    materialCategory:'',
                    materialDescCn:'',
                    materialDescEn:'',
                    materialDescRn:'',
                    hsNo:'',
                    supplierCode:'',
                    materialCurrency:'',
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
        components:{ImportButton,EditMatter},
        mounted() {
            this.init();
            this.exec_search({search_keys:this.search_keys, pageNumber:1});
        },
        computed:{
            lockFlag(){
                return (this.multipleSelection.some(item=>{
                    return item.isLocked===1
                }) || (this.multipleSelection.length===0))
            },
            unlockFlag(){
                return (this.multipleSelection.some(item=>{
                    return item.isLocked===0
                }) || (this.multipleSelection.length===0))
            }
        },
        methods: {
            reset(){
                this.search_keys   = {
                    materialOrderCode:'',
                      materialOrderCodeDesc:'',
                      materialCode:'',
                      materialCkdCode:'',
                      materialCategory:'',
                      materialDescCn:'',
                      materialDescEn:'',
                      materialDescRn:'',
                      hsNo:'',
                      supplierCode:'',
                      materialCurrency:'',
                      createUser:'',
                      createTime:'',
                      createTimeArray:'',
                      updateUser:'',
                      updateTimeArray:'',
                      updateTime:'',
                };
            },
            price(row, column, cellValue, index){
                if (cellValue) {
                    return cellValue.toFixed(2)
                }else {
                    return ""
                }
            },
            init(){
                activityService.initData().then(resp =>{
                    this.currencys = resp.data.result.currencys;
                    this.materialCategorys = resp.data.result.materialCategorys;
                }, err => {
                    console.error(err);
                })
            },
            // toLocked(isLocked){
            //     if(isLocked  == 0){
            //         return false;
            //     }
            //     else{
            //         return true;
            //     }
            // },
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
                activityService.findMatterList({...data, pageNum, pageSize}).then(resp => {
                    this.search_result = resp.data.resultInfo;                //视图展示查询结果
                    this.old_search_keys=data;
                    this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照
                }, err => {
                    console.error(err);
                })
            },
            exportData(old_search_keys) {
                this.$confirm("确定导出数据吗？", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
                    type: 'info',
                    center: true
                }).then(() => {
                    activityService.exportData({...old_search_keys}).then(resp=>{
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
            deleteMatter(uuid) {
                this.$confirm("确定删除吗？", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
                    type: 'warning',
                    center: true
                }).then(() => {
                        this.idsStr=[];
                        this.multipleSelection.forEach(item=>{
                            this.idsStr.push(item.materialInfoId)
                        });
                        activityService.deleteById({idsStr:this.idsStr}).then(resp => {
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
            lockData(toLocked){
                this.$confirm("确定对数据进行锁定/解锁吗？", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
                    type: 'warning',
                    center: true
                }).then(() => {
                        this.idsStr=[];
                        this.multipleSelection.forEach(item=>{
                            this.idsStr.push(item.materialInfoId)
                        })
                        activityService.lockedById({idsStr:this.idsStr,toLocked:toLocked}).then(resp => {
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