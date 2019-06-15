<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item label="成品型号">
                    <el-input placeholder="成品型号" v-model="search_keys.materialOrderCode" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="子件型号">
                    <el-input placeholder="子件型号" v-model="search_keys.materialCode" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料CKD号">
                    <el-input placeholder="物料CKD号" v-model="search_keys.materialCkdCode" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料类别">
                    <el-select v-model="search_keys.materialCategory"  size="mini" class="search-form-item-input">
                        <el-option value=""></el-option>
                        <el-option
                                size="mini" knx
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
                                <el-input placeholder="创建时间" v-model="search_keys.createTime" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <el-form-item label="最后修改人">
                                <el-input placeholder="最后修改人" v-model="search_keys.updateUser" class="search-form-item-input"></el-input>
                            </el-form-item>
                            <el-form-item label="最后修改时间">
                                <el-input placeholder="最后修改时间" v-model="search_keys.updateTime" class="search-form-item-input"></el-input>
                            </el-form-item>
                        </div>
                    </el-collapse-item>
                </el-collapse>
                <el-form-item style="float: right">
                    <el-button  @click="" icon="el-icon-s-check" >
                        锁定
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <import-button target = "MATTER"></import-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="" @click="excel" size="mini" >
                        <i class="fa fa-plus" aria-hidden="true"></i> 导出
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button  @click="deleteMatter" icon="el-icon-delete" >
                        删除
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button  @click="reset" icon="el-icon-error" >
                        清空
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button  @click="add()" icon="el-icon-plus" >
                        添加
                    </el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" @click="exec_search({search_keys, pageNum:1})" native-type="submit" >
                        <i class="fa fa-search" aria-hidden="true"></i> 查询
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
            <el-table-column type="selection" fixed width="50" align="center"/>
            <el-table-column prop="materialOrderCode" fixed width="100" align="center" label="成品型号"  />
            <el-table-column prop="materialCkdCode" fixed width="100" align="center" label="物料CKD号"  />
            <el-table-column prop="materialCode" fixed width="100" align="center" label="子件型号"/>
            <el-table-column prop="dictMaterialCategory.name" fixed width="100" align="center" label="物料类别" />
            <el-table-column prop="materialDescCn"  :show-overflow-tooltip="true" align="center" label="物料中文描述"  />
            <el-table-column prop="materialDescEn"  :show-overflow-tooltip="true" align="center" label="物料英文描述"  />
            <el-table-column prop="materialDescRn"  :show-overflow-tooltip="true" align="center" label="物料俄文描述"  />
            <el-table-column prop="hsNo" align="center" label="HS海关编码"  />
            <el-table-column prop="supplierCode" align="center" label="供应商代码"  />
            <el-table-column prop="dictMaterialUnit.name" align="center" label="单位"  />
            <el-table-column prop="materialRelation" align="center" label="换算关系"  />
            <el-table-column prop="dictMaterialRelationUnit.name" align="center" label="换算后单位"  />
            <el-table-column prop="materialMinpackageAmt" align="center" label="最小包装数量"  />
            <el-table-column prop="materialTaxPrice" align="center" label="未税单价"  />
            <el-table-column prop="materialVatPrice" align="center" label="含税单价"  />
            <el-table-column prop="materialPrice" align="center" label="单价"  />
            <el-table-column prop="materialRate" align="center" label="代理费率"  />
            <el-table-column prop="dictMaterialCurrency.name" align="center" label="币种"  />
            <el-table-column prop="factoryCode" align="center" label="工厂号"  />
            <el-table-column prop="currency" align="center" label="创建人"  />
            <el-table-column prop="currency" align="center" label="创建时间"  />
            <el-table-column prop="currency" align="center" label="最后修改人"  />
            <el-table-column prop="currency" align="center" label="最后修改时间"  />

            <el-table-column fixed="right" width="80" label="操作" >
                <template slot-scope="scope" >
                    <el-button title="编辑与查看" type="primary" size="mini" class="btn-opt smallButton" plain @click="edit(scope.row.materialInfoId)">
                        <i class="el-icon-news"></i></el-button>
<!--                    <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="deleteMatter(scope.row.uuid)">-->
<!--                        <i class="el-icon-delete"></i></el-button>-->
                </template>
            </el-table-column>
        </el-table>
        <edit-matter v-bind.sync="link_modal_state" @success="reSearch" v-if="link_modal_state.activated"></edit-matter>
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
                currencys:[],
                search_keys   : {
                    materialOrderCode:'',
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
                    updateUser:'',
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
        methods: {
            reset(){
                console.log("11");
                this.search_keys   = {
                    materialOrderCode:'',
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
                      updateUser:'',
                      updateTime:'',
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
            exec_search({
                            search_keys = JSON.parse(this.search_keys_snap),
                            pageNum = this.search_result.pageNum,
                            pageSize = this.search_result.pageSize,
                        }) {
                let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
                activityService.findMatterList({...search_keys, pageNum, pageSize}).then(resp => {
                    this.search_result = resp.data.resultInfo;                //视图展示查询结果
                    this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照
                }, err => {
                    console.error(err);
                })
            },
            reSearch({
                            search_keys = JSON.parse(this.search_keys_snap),
                            pageNum = this.search_result.pageNum,
                            pageSize = this.search_result.pageSize,
                        }) {
                let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
                activityService.findMatterList({...search_keys, pageNum, pageSize}).then(resp => {
                    this.search_result = resp.data.resultInfo;                //视图展示查询结果
                    this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照
                }, err => {
                    console.error(err);
                })
            },
            exportData() {
                console("excel export")
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
                        })
                        activityService.deleteById({idsStr:this.idsStr}).then(resp => {
                            if (resp.data.code=="201"){
                                this.$notify({message: resp.data.message, type: "success"});
                            } else {
                                this.$notify({message: resp.data.message, type: "error"});
                            }
                        })
                    }
                ).catch(() => {
                    this.internal_activated = true;
                })//删除
            },
            //导出
            excel(){
                // let data={
                //   deviceTypeNo : search_keys.deviceType || undefined,
                //   powerStationNo : search_keys.powerStationEntity && search_keys.powerStationEntity.no || undefined ,
                //   powerStationName:search_keys.powerStationName  || "",
                //   name: search_keys.name || undefined,
                // }
                let data={name:"123"}
                activityService.installdownloadExcel({...data}).then(resp=>{
                    downloadBlobResponse(resp); // 文件下载
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