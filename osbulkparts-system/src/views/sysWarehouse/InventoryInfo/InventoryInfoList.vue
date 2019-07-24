<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item :label="$t('pageTable.MatterMaterialSpecificNumber')">
                    <el-input :placeholder="$t('pageTable.MatterMaterialSpecificNumber')" v-model="search_keys.materialCode" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item :label="$t('pageTable.MatterChannel')">
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
                            <i class="header-icon el-icon-s-operation">{{$t('Tips.allSearchKey')}}</i>
                        </template>
                        <div>
                            <el-form-item :label="$t('pageTable.MatterChineseDescriptionOfMaterials')">
                                <el-input :placeholder="$t('pageTable.MatterChineseDescriptionOfMaterials')" v-model="search_keys.materialDescCn" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <el-form-item :label="$t('pageTable.MatterEnglishDescriptionOfMaterials')">
                                <el-input :placeholder="$t('pageTable.MatterEnglishDescriptionOfMaterials')" v-model="search_keys.materialDescEn" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <el-form-item :label="$t('pageTable.MatterRussianDescriptionOfMaterials')">
                                <el-input :placeholder="$t('pageTable.MatterRussianDescriptionOfMaterials')" v-model="search_keys.materialDescRn" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <!--<el-form-item label="库存数量">-->
                                <!--<el-input placeholder="HS海关编码" v-model="search_keys.stockAmount" class="search-form-item-input"></el-input>-->
                            <!--</el-form-item>-->
                            <el-form-item :label="$t('pageTable.createUser')">
                                <el-input :placeholder="$t('pageTable.createUser')" v-model="search_keys.createUser" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <el-form-item :label="$t('pageTable.createTime')">
                                    <el-date-picker
                                      class=""
                                      v-model="search_keys.createTimeArray"
                                      type="daterange"
                                      value-format="yyyyMMddHHmmss"
                                      :start-placeholder="$t('pageTable.startTime')"
                                      :end-placeholder="$t('pageTable.endTime')">
                                    </el-date-picker>
                                <!--<el-input placeholder="创建时间" v-model="search_keys.createTime" class="search-form-item-input"></el-input>-->
                            </el-form-item>
                            <el-form-item :label="$t('pageTable.updateUser')">
                                <el-input :placeholder="$t('pageTable.updateUser')" v-model="search_keys.updateUser" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <el-form-item :label="$t('pageTable.updateTime')">
                                <el-date-picker
                                  class=""
                                  v-model="search_keys.updateTimeArray"
                                  type="daterange"
                                  value-format="yyyyMMddHHmmss"
                                  :start-placeholder="$t('pageTable.startTime')"
                                  :end-placeholder="$t('pageTable.endTime')">
                                </el-date-picker>
                            </el-form-item>
                        </div>
                    </el-collapse-item>
                </el-collapse>
                <el-form-item style="float: right">
                    <import-button v-if="subject.hasPermissions('maintenance:warehouse:stock:info:import')" @saved="exec_search({search_keys, pageNum:1})" target = "STOCK_INFO"></import-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:stock:info:export')" @click="exportData(old_search_keys)" size="mini" >
                        <i class="fa fa-plus" aria-hidden="true"></i> {{$t('searchFrom.export')}}
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:stock:info:delete')" :disabled="multipleSelection.length==0" @click="deleteMatter" icon="el-icon-delete" >
                        {{$t('searchFrom.delete')}}
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary"  @click="reset" icon="el-icon-error" >
                        {{$t('searchFrom.reset')}}
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:stock:info:add')" @click="add()" icon="el-icon-plus" >
                        {{$t('searchFrom.add')}}
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:warehouse:stock:info:view')" @click="exec_search({search_keys, pageNum:1})" native-type="submit" >
                        <i class="fa fa-search" aria-hidden="true"></i>  {{$t('searchFrom.search')}}
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
            <el-table-column prop="materialCode"  width="100" align="center" :label="$t('pageTable.MatterMaterialSpecificNumber')"/>
            <el-table-column prop="dictMaterialCategory.name" width="100" align="center" :label="$t('pageTable.MatterChannel')"/>
            <el-table-column prop="materialDescCn"  :show-overflow-tooltip="true" align="center" :label="$t('pageTable.MatterChineseDescriptionOfMaterials')"  />
            <el-table-column prop="materialDescEn"  :show-overflow-tooltip="true" align="center" :label="$t('pageTable.MatterEnglishDescriptionOfMaterials')"  />
            <el-table-column prop="materialDescRn"  :show-overflow-tooltip="true" align="center" :label="$t('pageTable.MatterRussianDescriptionOfMaterials')"  />
            <el-table-column prop="stockAmount" align="center" :label="$t('pageTable.inventoryNumber')"  />
            <el-table-column prop="createUser" align="center" :label="$t('pageTable.createUser')"  />
            <el-table-column :label="$t('pageTable.createTime')" show-overflow-tooltip>
                <template slot-scope="scope">
                    {{scope.row.createTime != null ?$moment(scope.row.createTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
                </template>
            </el-table-column>
            <el-table-column prop="updateUser" align="center" :label="$t('pageTable.updateUser')" />
            <el-table-column :label="$t('pageTable.updateTime')" show-overflow-tooltip>
                <template slot-scope="scope">
                    {{scope.row.updateTime != null ?$moment(scope.row.updateTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
                </template>
            </el-table-column>
            <el-table-column   width="80" :label="$t('pageTable.operate')">
                <template slot-scope="scope" >
                    <el-button  v-if="subject.hasPermissions('maintenance:warehouse:stock:info:edit')"  type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row.id)">
                        <i class="el-icon-news"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <Edit-inventory-info v-bind.sync="link_modal_state" @success="exec_search({search_keys, pageNum:1})" v-if="link_modal_state.activated"></Edit-inventory-info>
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
    import activityService from '@/api/warehouse/inventoryInfo'
    import ui_config from '@/config/ui_config'
    import ImportButton from '@/components/data-import/ImportButton'
    import EditInventoryInfo from './EditInventoryInfo'
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
                search_keys   : {
                    materialCode:'',
                    materialCategory:'',
                    materialDescCn:'',
                    materialDescEn:'',
                    materialDescRn:'',
                    stockAmount:"",
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
        components:{ImportButton,EditInventoryInfo},
        mounted() {
            this.init();
            this.exec_search({search_keys:this.search_keys, pageNum:1});
        },
        computed:{

        },
        methods: {
            reset(){
              this.search_keys  ={
                  materialCode:'',
                  materialCategory:'',
                  materialDescCn:'',
                  materialDescEn:'',
                  materialDescRn:'',
                  stockAmount:"",
                  createUser:'',
                  createTime:'',
                  createTimeArray:"",
                  updateUser:'',
                  updateTimeArray:"",
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
                activityService.initData().then(resp =>{
                    this.materialCategorys = resp.data.result.materialCategorys;
                }, err => {
                    console.error(err);
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
                activityService.findStockInfoList({...data, pageNum, pageSize}).then(resp => {
                    this.search_result = resp.data.resultInfo;                //视图展示查询结果
                    this.old_search_keys=data;
                    this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照
                }, err => {
                    console.error(err);
                })
            },
            exportData(old_search_keys) {
                this.$confirm(this.$t("Tips.exportQueries"), this.$t("Tips.tips"), {
                    confirmButtonText: this.$t("Tips.yes"),
                    cancelButtonText: this.$t("Tips.no"),
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
                this.$confirm(this.$t("Tips.deleteQueries"), this.$t("Tips.tips"), {
                    confirmButtonText:  this.$t("Tips.yes"),
                    cancelButtonText:  this.$t("Tips.no"),
                    type: 'warning',
                    center: true
                }).then(() => {
                        this.idsStr=[];
                        this.multipleSelection.forEach(item=>{
                            this.idsStr.push(item.id)
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