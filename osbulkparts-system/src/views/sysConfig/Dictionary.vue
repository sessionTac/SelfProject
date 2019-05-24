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
                                <el-menu-item  v-for="(item,id) in system_menus" :index="item.code":key="id" @click="screenByMenu(item.id,item.name)">{{item.name}}</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>

                    </el-menu>
                </div>
            </div>
            <div style="flex: 4;display: flex;flex-direction: column;height: 100%;">
                <div style="padding-left: 30px;margin-top: 5px" >
                    <el-form :inline="true" size="mini"  @submit.native.prevent>
                        <el-form-item label="名称">
                            <el-input v-model="search_keys.name" placeholder="名称" size="mini" knx></el-input>
                        </el-form-item>

                        <el-form-item style="float: right;margin-right: 1px">
                            <el-button type="primary"  @click="typeId ='';exec_search({search_keys, pageNumber:1})"  native-type="submit" >
                                <i class="fa fa-search" aria-hidden="true"></i> 查询
                            </el-button>
                            <el-button type="" @click="add()" :disabled="disableFlag"  size="mini" >
                                <i class="fa fa-plus" aria-hidden="true"></i> 添加
                            </el-button>
                        </el-form-item>
                    </el-form>
                </div>

                <div style="padding-top: 10px;padding-left: 30px">
                    <el-form :inline="true" size="mini">
                        <el-form-item>
                            <el-button @click="$router.push({name:'SystemDictionaryCategory'})" style="width: 80px" size="mini">
                                字典分类
                            </el-button>
                        </el-form-item>
                    </el-form>
                </div>
                    <el-table size="mini"
                              class="search-result-table"
                              style="flex: 1"
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
                        <el-table-column prop="isDefault" sortable label="默认" :formatter="formatterisDefault"/>
                        <el-table-column prop="isEnable" sortable label="有效"  :formatter="formatterEnable">
                        </el-table-column>
                        <el-table-column prop="remark" sortable label="备注"/>
                        <el-table-column prop="createTime" sortable
                                         :formatter="(row,col,val)=>val && $moment(val,'YYYYMMDDHHmmss').format('YYYY-MM-DD')" label="创建时间"/>
                        <el-table-column label="操作" >
                            <template slot-scope="scope" >
                                <el-button title="编辑" type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row)">
                                    <i class="el-icon-edit"></i></el-button>
                                <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="remove(scope.row)">
                                    <i class="el-icon-delete"></i></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div style="text-align: center; margin-top: 10px">
                        <el-pagination
                                @current-change="exec_search({pageNumber:$event})"
                                @size-change="exec_search({pageNumber:1,pageSize:$event})"
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
                typeId:'',
                search_keys_snap: null,
                search_result: {},
                system_menus : [],
                openeds:['0','6','12','1'],
                disableFlag:true,
                dictTypeId :0,
                dictTypeName:'',
                is_searching : true,

            };
        },
        mounted() {
            this.init();
            this.exec_search({search_keys: this.search_keys, pageNumber: 1});
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
                this.screenByMenu(this.dictTypeId,this.dictTypeName);
            },
            // 分类初始化
            init() {
                // dictionaryService.findMDictionaryItemsTree().then(resp => {
                //     this.system_menus = resp.data
                // }, err => {
                // });
                this.system_menus=[
                  {id:"001",code:"001",name:"分类1"},{id:"002",code:"002",name:"分类2"},
                    {id:"003",code:"003",name:"分类3"},{id:"003",code:"003",name:"分类3"},
                    {id:"004",code:"004",name:"分类4"}, {id:"005",code:"005",name:"分类5"},
                    {id:"006",code:"006",name:"分类6"},{id:"007",code:"007",name:"分类7"},
                    {id:"008",code:"008",name:"分类8"}, {id:"009",code:"009",name:"分类9"},
                    {id:"010",code:"010",name:"分类10"},{id:"011",code:"011",name:"分类11"},
                    {id:"012",code:"012",name:"分类12"},{id:"013",code:"013",name:"分类13"},
                    {id:"014",code:"014",name:"分类14"},{id:"015",code:"015",name:"分类15"},
                    {id:"016",code:"016",name:"分类16"},{id:"017",code:"017",name:"分类17"}
                  ];
            },
            // 检索
            exec_search({
                            search_keys = JSON.parse(this.search_keys_snap),
                            pageNumber = this.search_result.pageNumber,
                            pageSize   = this.search_result.pageSize,
                        }) {
                let search_key=this.search_keys;
                let typeId = this.typeId;
                let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
                this.is_searching = true;
                dictionaryService.findMDictionaryItemsDetails({...search_key,typeId,pageNumber, pageSize}).then(resp => {
                    this.search_result    = resp.data;                //视图展示查询结果
                    this.search_keys      = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照      //存储查询条件快照
                    this.disableFlag=true;
                }, err => {
                    console.error(err);
                }).finally (()=>{
                    this.is_searching = false;
                })
            },
            /*新增数据字典*/
            add(){
                this.dialogState = {activated:true,mode:'ADD',id:this.dictTypeId,name:this.dictTypeName}
            },
            /*编辑数据字典*/
            edit(entity){

                this.dialogState = {activated:true,entity,mode:'EDIT',id:this.dictTypeId,name:this.dictTypeName}
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
                        this.screenByMenu(this.dictTypeId,this.dictTypeName);

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
            screenByMenu(id,name){
                this.typeId = id;
                let typeId = id;
                let pageNumber = this.search_result.pageNumber
                let pageSize   = this.search_result.pageSize
                dictionaryService.findMDictionaryItemsDetails({typeId, pageNumber, pageSize}).then(resp=>{
                    this.search_result = resp.data;
                    this.disableFlag=false;
                    this.dictTypeId = id;
                    this.dictTypeName = name;
                })
            }
        },
    }
</script>

<style scoped>

</style>