<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <!--{{language}}{{$t('route.ceshi') }}-->
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item :label="$t('searchFrom.userName')">
                    <el-input :placeholder="$t('searchFrom.userName')" v-model="search_keys.userName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item :label="$t('searchFrom.trueName')">
                    <el-input :placeholder="$t('searchFrom.trueName')" v-model="search_keys.userRealName" class="search-form-item-input"></el-input>
                </el-form-item>

                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:system:users:info:add')" @click="add" size="mini" >
                        <i class="fa fa-plus" aria-hidden="true"></i> {{$t('searchFrom.add') }}
                    </el-button>
                </el-form-item>

                <el-form-item style="float: right">
                    <el-button type="primary" v-if="subject.hasPermissions('maintenance:system:users:info:select')" @click="exec_search({search_keys, pageNum:1})"  >
                        <i class="fa fa-search" aria-hidden="true"></i> {{$t('searchFrom.search') }}
                    </el-button>
                </el-form-item>
<!--                <el-form-item style="float: right">-->
<!--                    <import-button @saved="exec_search({search_keys, pageNum:1})" v-if="subject.hasPermissions('maintenance:system:users:info:add')" target = "USER_INFO"></import-button>-->
<!--                </el-form-item>-->
            </el-form>
        </div>
        <EditUserDialog v-bind.sync="link_modal_state" @success="exec_search({search_keys, pageNum:1})" v-if="link_modal_state.activated"></EditUserDialog>
        <set-role-dialog v-bind.sync="set_role_dialog" @success="reSearch" v-if="set_role_dialog.activated"></set-role-dialog>
        <see-role-dialog v-bind.sync="see_role_dialog" v-if="see_role_dialog.activated"></see-role-dialog>
        <el-table size="mini"
                  style="flex: 1"
                  :height="600"
                  border
                  class="search-result-table"
                  :data="search_result.list" row-key="id"
                  :stripe="true"
        >
            <el-table-column prop="userName" align="center" :label="$t('pageTable.userName')" width="140"  />
            <el-table-column prop="userRealName" align="center" :label="$t('pageTable.trueName')"  width="140" />
            <el-table-column prop="dictUserType.name" align="center" :label="$t('pageTable.userType')"  width="140" />
            <el-table-column prop="dictUserStatus.name" align="center" :label="$t('pageTable.userState')"  />
            <el-table-column prop="dictUserLevel.name" align="center" :label="$t('pageTable.userOwner')"    />

            <el-table-column :label="$t('pageTable.operate')"  >
                <template slot-scope="scope" >
                    <el-button  type="primary" v-if="subject.hasPermissions('maintenance:system:users:info:roleset')" size="mini" class="btn-opt smallButton" @click="setRole(scope.row.userId)">
                        {{$t('pageTable.roleSet')}}</el-button>
                    <el-button v-if="subject.hasPermissions('maintenance:system:users:info:roleview')" size="mini" class="btn-opt smallButton"  @click="viewRole(scope.row.userId)">
                        {{$t('pageTable.roleSee')}}</el-button>
                    <el-button  v-if="subject.hasPermissions('maintenance:system:users:info:edit')" type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row.userId)">
                        <i class="el-icon-news"></i></el-button>
                    <el-button  type="danger" v-if="subject.hasPermissions('maintenance:system:users:info:delete')" size="mini" class="btn-opt" plain  @click="deleteUser(scope.row.userId)">
                        <i class="el-icon-delete"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <div style="text-align: center">
            <el-pagination
              @current-change="exec_search({pageNum:$event})"
              @size-change="exec_search({pageNum:1,pageSize:$event})"
              :page-size="search_result.pageSize"
              :currentPage="search_result.pageNum"
              layout="total, sizes, prev, pager, next, jumper"
              :total="search_result.total">
            </el-pagination>
        </div>
        <!--<div style="border: solid 1px red;flex: 1;height: 100%">1</div>-->
        <!--<div style="border: solid 1px green;flex: 1;height: 100%">2</div>-->
    </div>
</template>

<script>
    import EditUserDialog from './EditUser'
    import activityService from '@/api/users/users.js'
    import ui_config from '@/config/ui_config'
    import SetRoleDialog from  './SetRoleDialog'
    import SeeRoleDialog from './SeeRoleDialog'
    import { mapGetters,mapState } from 'vuex'

    import ImportButton from '@/components/data-import/ImportButton'

    export default {
        components:{EditUserDialog,ImportButton,SetRoleDialog,SeeRoleDialog},
        data() {
            return {
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
                    userName:'',
                    userRealName:'',
                },
                search_keys_snap      : null,
                search_result         : {},

            };
        },
        mounted() {
            this.exec_search({search_keys:this.search_keys, pageNum:1});
        },
        computed: {
            ...mapState({
                language:state=>state.app.language
            }),

        },
        watch:{
            language(val,val1){
                // alert(val+val1)
                this.exec_search({search_keys:this.search_keys, pageNum:1});
            }
        },
        methods: {
            exec_search({
                            search_keys = JSON.parse(this.search_keys_snap),
                            pageNum = this.search_result.pageNum,
                            pageSize = this.search_result.pageSize,
                        }) {
                let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
                activityService.findUserList({...search_keys, pageNum, pageSize}).then(resp => {
                    this.search_result = resp.data.resultInfo;                //视图展示查询结果
                    this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
                    this.search_keys_snap = search_keys_snap;             //存储查询条件快照
                }, err => {
                    console.error(err);
                })
            },
            //新增用户
            add() {
                this.link_modal_state={activated:true,mode:'ADD'};
            },
            //编辑用户
            edit(id) {
                this.link_modal_state={activated:true,id,mode:'EDIT'};
            },
            reSearch(){
                this.exec_search({search_keys:this.search_keys, pageNum:1});
            },
            //设置角色
            setRole(userId){
                this.set_role_dialog = {activated:true,userId};
            },
            //角色查看
            viewRole(userId){
                this.see_role_dialog={activated:true,userId};
            },
            //删除
            deleteUser(uuid) {
                this.$confirm(this.$t("Tips.deleteQueries"), this.$t("Tips.tips"), {
                    confirmButtonText: this.$t("Tips.yes"),
                    cancelButtonText: this.$t("Tips.no"),
                    type: 'warning',
                    center: true
                }).then(() => {
                        activityService.deleteById(uuid).then(resp => {
                            if (resp.data.code == "201") {
                                this.$notify({message:  this.$t("Tips.deleteSuccess"), type: 'success'});
                                this.exec_search({search_keys:this.search_keys, pageNum:1})
                            } else {
                                this.$notify({message: this.$t("Tips.deleteFail"), type: 'error'});
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