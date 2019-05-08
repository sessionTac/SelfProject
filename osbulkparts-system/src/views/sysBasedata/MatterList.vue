<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item label="物料HNR号">
                    <el-input placeholder="物料HNR号" v-model="search_keys.matterHNRNo" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料专用号">
                    <el-input placeholder="物料专用号" v-model="search_keys.matterNo" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料类别">
                    <el-select v-model="search_keys.matterType"  size="mini" knx>
                        <el-option value=""></el-option>
                        <el-option
                                size="mini" knx
                                v-for="item in matterTypes"
                                :key="item.code"
                                :label="item.name"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="物料中文描述">
                    <el-input placeholder="物料中文描述" v-model="search_keys.matterCnDec" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料英文描述">
                    <el-input placeholder="物料英文描述" v-model="search_keys.matterEnDec" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="单位">
                    <el-select v-model="search_keys.unit"  size="mini" knx>
                        <el-option value=""></el-option>
                        <el-option
                                size="mini" knx
                                v-for="item in units"
                                :key="item.code"
                                :label="item.name"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="换算关系">
                    <el-input placeholder="换算关系" v-model="search_keys.scalerRela" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="换算后单位">
                    <el-input placeholder="换算后单位" v-model="search_keys.scalerUnit" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="币种">
                    <el-select v-model="search_keys.currency"  size="mini" knx>
                        <el-option value=""></el-option>
                        <el-option
                                size="mini"
                                v-for="item in currencys"
                                :key="item.code"
                                :label="item.name"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item style="float: right">
                    <import-button target = "MATTER"></import-button>
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
            </el-form>
        </div>

        <el-table size="mini"
                  style="flex: 1"
                  :height="600"
                  class="search-result-table"
                  :data="search_result.list" row-key="id"
                  :stripe="true"
        >
            <el-table-column prop="matterHNRNo" align="center" label="物料HNR号"  />
            <el-table-column prop="matterNo" align="center" label="物料专用号"  />
            <el-table-column prop="matterType" align="center" label="物料类别" />
            <el-table-column prop="matterCnDec" align="center" label="物料中文描述"  />
            <el-table-column prop="matterEnDec" align="center" label="物料英文描述"  />
            <el-table-column prop="unit" align="center" label="单位"  />
            <el-table-column prop="scalerRela" align="center" label="换算关系"  />
            <el-table-column prop="scalerUnit" align="center" label="换算后单位"  />
            <el-table-column prop="currency" align="center" label="币种"  />

            <el-table-column label="操作" >
                <template slot-scope="scope" >
                    <el-button title="编辑与查看" type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row.uuid)">
                        <i class="el-icon-news"></i></el-button>
                    <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="delete(scope.row.uuid)">
                        <i class="el-icon-delete"></i></el-button>
                </template>
            </el-table-column>
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
        <!--<div style="border: solid 1px red;flex: 1;height: 100%">1</div>-->
        <!--<div style="border: solid 1px green;flex: 1;height: 100%">2</div>-->
    </div>
</template>

<script>
    import activityService from '@/api/basedata/matter.js'
    import ui_config from '@/config/ui_config'
    import ImportButton from '@/components/data-import/ImportButton'

    export default {
        data() {
            return {
                PAGE_SIZES : ui_config.PAGE_SIZES,
                link_modal_state      : {},
                //单位下拉框数据
                is_searching : true,
                matterTypes:[],
                units:[],
                currencys:[{'name':'人民币','code':'1'}],
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
                search_keys_snap      : null,
                search_result         : {},
            };
        },
        components:{ImportButton},
        mounted() {
            this.exec_search({search_keys:this.search_keys, pageNumber:1});
        },
        methods: {
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
            edit(id) {
                console("edit")
            },
            //删除
            deleteUser(uuid) {
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

</style>