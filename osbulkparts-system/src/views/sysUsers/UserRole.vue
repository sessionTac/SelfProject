<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item :label="$t('searchFrom.roleName')">
                    <el-input :placeholder="$t('searchFrom.roleName')" v-model="search_keys.roleName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item :label="$t('searchFrom.roleExplain')">
                    <el-input :placeholder="$t('searchFrom.roleExplain')" v-model="search_keys.roleDesc" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item :label="$t('searchFrom.roleAt')" >
                    <el-select v-model="search_keys.roleAt" :placeholder="$t('searchFrom.choose')" class="search-form-item-input">
                        <el-option
                          v-for="item in options.roleAt"
                          :key="item.value"
                          :label="item.name"
                          :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button v-if="subject.hasPermissions('maintenance:system:users:role:add')" type="primary" @click="add" size="mini" >
                        <i class="fa fa-plus" aria-hidden="true"></i> {{$t('searchFrom.add')}}
                    </el-button>
                </el-form-item>

                <el-form-item style="float: right">
                    <el-button v-if="subject.hasPermissions('maintenance:system:users:role:select')" type="primary" @click="exec_search({search_keys, pageNum:1})"  >
                        <i class="fa fa-search" aria-hidden="true"></i> {{$t('searchFrom.search')}}
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
        <SetAuthority v-bind.sync="link_modal_state"  v-if="link_modal_state.activated"></SetAuthority>
        <edit-role v-bind.sync="link_modal_state_edit" @success="exec_search({search_keys, pageNum:1})"  v-if="link_modal_state_edit.activated"></edit-role>
        <el-table size="mini"
                  style="flex: 1"
                  :height="600"
                  border
                  class="search-result-table"
                  :data="search_result.list" row-key="id"
                  :stripe="true"
        >
            <el-table-column prop="roleName" align="center" :label="$t('pageTable.roleName')"  />
            <el-table-column prop="roleDesc" align="center" :label="$t('pageTable.roleExplain')" />
            <el-table-column prop="dictRoleAt.name" align="center" :label="$t('pageTable.roleAt')"  />

            <el-table-column label="操作" >
                <template slot-scope="scope" >
                    <el-button  type="primary" v-if="subject.hasPermissions('maintenance:system:users:role:funset')" size="mini" class="btn-opt smallButton" @click="setFunction(scope.row.roleId)">
                        {{$t('pageTable.powerSet')}}</el-button>
                    <el-button  type="primary" v-if="subject.hasPermissions('maintenance:system:users:role:edit')" size="mini" class="btn-opt" plain @click="edit(scope.row.roleId)">
                        <i class="el-icon-news"></i></el-button>
                    <el-button  type="danger" v-if="subject.hasPermissions('maintenance:system:users:role:delete')" size="mini" class="btn-opt" plain  @click="deleteUser(scope.row.roleId)">
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
    // import SetAuthority from './SetAuthority'
    import activityService from '@/api/users/users.js'
    import ui_config from '@/config/ui_config'
    import SetAuthority from '@/views/sysUsers/SetAuthority'
    import EditRole from './EditRole'
    import { mapGetters,mapState } from 'vuex'

    export default {
        components: { SetAuthority,EditRole},
        data() {
            return {
                options:{},
                PAGE_SIZES : ui_config.PAGE_SIZES,
                set_role_dialog       : {},
                see_role_dialog       : {},
                link_modal_state      : {},
                link_modal_state_edit : {},
                open_options          : [],
                active_state_options  : [],
                modal_state           : {},
                roles                 :[],
                //角色选中
                multipleSelection: [],
                //单位下拉框数据
                is_searching : true,
                search_keys   : {
                    roleDesc:"",
                    roleName:"",
                    roleAt:""
                },
                search_keys_snap      : null,
                search_result         : {},

            };
        },
        computed: {
            ...mapState({
                language:state=>state.app.language
            }),

        },
        watch:{
            async language(val,val1){
                // alert(val+val1)
                await this.init();
                this.exec_search({search_keys:this.search_keys, pageNum:1});
            }
        },
        async mounted() {
            await this.init();
            this.exec_search({search_keys:this.search_keys, pageNum:1});
        },
        methods: {
            init(){
              return  activityService.findRoleOptions().then(resp=>{
                  this.options=resp.data.result || []
              })
            },
            exec_search({
                            search_keys = JSON.parse(this.search_keys_snap),
                            pageNum = this.search_result.pageNum,
                            pageSize = this.search_result.pageSize,
                        }) {
                let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
                let roleDesc = search_keys.roleDesc || undefined;
                let roleName = search_keys.roleName || undefined;
                let roleAt   = search_keys.roleAt   || undefined;
                activityService.findRoleList({roleDesc, roleName,roleAt, pageNum, pageSize}).then(resp => {
                    this.search_result = resp.data.resultInfo;                //视图展示查询结果
                    this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照
                }, err => {
                    console.error(err);
                })
            },
            //新增角色
            add() {
                this.link_modal_state_edit={activated:true,mode:'ADD'};
            },
            //编辑角色
            edit(id) {
                this.link_modal_state_edit={activated:true,roleId:id,mode:'EDIT'};
            },
            //权限设置
            setFunction(id) {
                this.link_modal_state={activated:true,roleId:id,mode:'EDIT'};
            },
            //删除
            deleteUser(roleId) {
                this.$confirm(this.$t('Tips.deleteQueries'), this.$t('Tips.tips'), {
                    confirmButtonText:  this.$t('Tips.yes'),
                    cancelButtonText:  this.$t('Tips.no'),
                    type: 'warning',
                    center: true
                }).then(() => {
                        activityService.deleteRoleById(roleId).then(resp => {
                            if (resp.data.code == "201") {
                                this.$notify({message: resp.data.message, type: 'success'});
                                this.exec_search({search_keys:this.search_keys, pageNum:1})
                            } else {
                                this.$notify({message: resp.data.message, type: 'error'});
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