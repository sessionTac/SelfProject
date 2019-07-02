<template>
    <!--<div style="width: 100%;height: 99%;display: flex">-->
        <div style=";height: 100%;width: 100%;display: flex;flex-direction: row">
            <div style="flex: 1;display: flex;flex-direction: column; border-right: 1px solid lightGray;height: 100%">
                <div style="height: 99%;flex: 1;display: flex;flex-direction: column;">
                    <el-menu
                            style=";border-right:0;margin-top: 5px;height: 100%;flex: 1;display: flex;flex-direction: column;"
                            :default-openeds="openeds"
                            :router="false"
                            :collapse-transition="false">
                        <!--<submenu :menu-data="menu" :level="0" v-for="(menu, menu_idx) in system_menus" :key="'sys:'+menu_idx"/>-->
                        <el-submenu index="1" style="overflow-x:hidden;;flex: 1;display: flex;flex-direction: column;height: 100%">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>通用分类</span>
                            </template>
                            <el-menu-item-group style="">
                                <el-menu-item  v-for="(item,index) in system_menus" :index="item.code":key="index" @click="screenByMenu(item.code,item.name)">{{item.name}}</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>

                    </el-menu>
                </div>
            </div>
            <div style="flex: 4;display: flex;flex-direction: column;height: 100%;">
                <div style="padding-left: 30px;margin-top: 5px" >
                    <el-form :inline="true" size="mini"  @submit.native.prevent>
                        <el-form-item style="float: right;margin-right: 20px;margin-top: 10px">
                            <el-button type="primary" @click="add()" :disabled="disableFlag"  size="mini" >
                                <i class="fa fa-plus" aria-hidden="true"></i> 添加
                            </el-button>
                        </el-form-item>
                    </el-form>
                </div>

                <div style="padding-top: 10px;padding-left: 30px">
                    <el-form :inline="true" size="mini">
                        <el-form-item>
                            <el-button type="primary" @click="$router.push({name:'SystemDictionaryCategory'})" style="width: 80px" size="mini">
                                字典分类
                            </el-button>
                        </el-form-item>
                    </el-form>
                </div>
                    <el-table size="mini"
                              class="search-result-table"
                              style="flex: 1"
                              border
                              :height="650"
                              :data="search_result.list" row-key="uuId"
                              :cell-class-name="({row,rowIndex,columnIndex}) => { return null; }"
                              :row-class-name="({row,rowIndex}) => { return row.summary_line ? 'summary-row' : null; }"
                              :default-sort = "{prop:'code',order: 'ascending'}"
                              :stripe="true"
                              v-loading="is_searching"
                    >
                        <el-table-column prop="value" sortable label="值"/>
                        <el-table-column prop="name" sortable label="名称"/>
                        <el-table-column prop="sortCode" sortable label="排序"/>
                        <el-table-column prop="desc" sortable label="描述" />
                        <el-table-column prop="isEnable" sortable label="有效"  :formatter="formatterEnable">
                        </el-table-column>
                        <el-table-column prop="remark" sortable label="备注"/>
                        <el-table-column prop="createTime" sortable
                                         :formatter="(row,col,val)=>val && $moment(val,'YYYYMMDDHHmmss').format('YYYY-MM-DD')" label="创建时间"/>
                        <el-table-column label="操作" >
                            <template slot-scope="scope" >
                                <el-button title="编辑" type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row)">
                                    <i class="el-icon-edit"></i></el-button>
<!--                                <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="remove(scope.row)">-->
<!--                                    <i class="el-icon-delete"></i></el-button>-->
                            </template>
                        </el-table-column>
                    </el-table>
                    <div style="text-align: center; margin-top: 10px">
                        <el-pagination
                                @current-change="screenByMenu({pageNum:$event})"
                                @size-change="screenByMenu({pageNum:1,pageSize:$event})"
                                :page-size="search_result.pageSize"
                                :currentPage="search_result.pageNum"
                                layout="total, sizes, prev, pager, next, jumper"
                                :total="search_result.total">
                        </el-pagination>
                    </div>


                <div>
                    <EditDictionaryDialog v-bind.sync="dialogState" v-if="dialogState.activated" @refresh="refresh()"></EditDictionaryDialog>
                </div>
            </div>
        </div>
    <!--</div>-->
</template>

<script>
    import ElCardPlus from '@/components/plugin/ElCardPlus'
    import Submenu from '@/components/plugin/Submenu'
    import dictionaryService from '@/api/sysConfig/dictionary'
    import EditDictionaryDialog from './EditDictionaryDialog'

    export default {
        name: "Dictionary",
        components: {ElCardPlus, Submenu,EditDictionaryDialog},
        data() {
            return {
                dialogState:{
                    activated:false
                },
                search_keys: {
                    name:"",
                },
                search_keys_snap: null,
                search_result: {},
                system_menus : [],
                openeds:['0','6','12','1'],
                disableFlag:true,
                dictTypeCode :0,
                dictTypeName:'',
                is_searching : false,

            };
        },
        async mounted() {
            await this.init();
            if(this.system_menus.length != 0){
                this.screenByMenu(this.system_menus[0].code,this.system_menus[0].name)
            }
        },
        methods: {
            //
            formatterisDefault(row,col,val){
                if(val==0){
                    return "否"
                }else {
                    return "是"
                }
            },
            //
            formatterEnable(row,col,val){
                if(val==0){
                    return "无效"
                }else {
                    return "有效"
                }
            },
            //刷新
            refresh(){
                this.screenByMenu(this.dictTypeCode,this.dictTypeName);
            },
            // 分类初始化
            init() {
               return dictionaryService.findAllDictTypes().then(resp => {
                    this.system_menus = resp.data.resultList
                }, err => {
                });
            },
            /*新增数据字典*/
            add(){
                this.dialogState = {activated:true,mode:'ADD',dictTypeCode:this.dictTypeCode,name:this.dictTypeName}
            },
            /*编辑数据字典*/
            edit(entity){

                this.dialogState = {activated:true,entity,mode:'EDIT',dictTypeCode:this.dictTypeCode}
            },

            /*删除*/
            remove(row) {
                this.$confirm("此操作将删除该记录, 是否继续?","提示！", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: 'warning',
                    center: true
                }).then(()=> {
                    dictionaryService.deleteDict(row.id).then(resp=>{

                        this.$notify({title: '成功',type: 'success', message: resp.data.msg});
                        this.screenByMenu(this.dictTypeCode,this.dictTypeName);

                    })

                }).catch(() => {
                    this.$notify({
                        title: '消息',
                        type: 'info',
                        message: "已取消删除"
                    });
                });
            },
            //点击菜单查询对应数据
            screenByMenu(code,name,
                         pageNum = this.search_result.pageNum,
                         pageSize   = this.search_result.pageSize,            
            ){
                this.is_searching=true;
                let dictTypeCode = code;
                dictionaryService.findDataByDictTypeId({dictTypeCode, pageNum, pageSize}).then(resp=>{
                    this.search_result = resp.data.resultInfo;
                    this.is_searching=false;
                    this.disableFlag=false;
                    this.dictTypeCode = dictTypeCode;
                    this.dictTypeName = name;
                })
            }
        },
    }
</script>

<style scoped>

</style>