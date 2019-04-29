<template>
    <div style="display: flex;flex-direction: column;height: 100%">
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item label="用户名">
                    <el-input  placeholder="用户名" v-model="search_keys.userName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input  placeholder="姓名" v-model="search_keys.trueName" class="search-form-item-input"></el-input>
                </el-form-item>

                <el-form-item style="float: right">
                    <el-button type="" @click="add" size="mini" >
                        <i class="fa fa-plus" aria-hidden="true"></i> 添加
                    </el-button>
                </el-form-item>

                <el-form-item style="float: right">
                    <el-button type="primary" @click="exec_search({search_keys, pageNum:1})" native-type="submit" >
                        <i class="fa fa-search" aria-hidden="true"></i> 查询
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
        <EditUserDialog v-bind.sync="link_modal_state" @success="reSearch" v-if="link_modal_state.activated"></EditUserDialog>

        <el-table size="mini"
                  style="flex: 1"
                  :height="600"
                  class="search-result-table"
                  :data="search_result.list" row-key="id"
                  :stripe="true"
        >
            <el-table-column prop="userName" align="center" label="用户名"  />
            <el-table-column prop="trueName" align="center" label="姓名"  />
            <el-table-column prop="phone" align="center" label="手机号" />
            <el-table-column prop="email" align="center" label="邮箱"  />

            <el-table-column label="操作" >
                <template slot-scope="scope" >
                    <el-button title="角色设置" type="primary" size="mini" class="btn-opt" @click="setRole(scope)">
                        角色设置</el-button>
                    <el-button title="查看" size="mini" class="btn-opt"  @click="viewRole(scope)">
                        角色查看</el-button>
                    <el-button title="编辑与查看" type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row.uuid)">
                        <i class="el-icon-news"></i></el-button>
                    <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="deleteUser(scope.row.uuid)">
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
    import EditUserDialog from './EditUser'
    import activityService from '@/api/users/users.js'
    import ui_config from '@/config/ui_config'

    export default {
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
                    orgEntity:null,
                    userName:'',
                    trueName:'',
                },
                search_keys_snap      : null,
                search_result         : {},

            };
        },
        components: {EditUserDialog},
        mounted() {
            this.exec_search({search_keys:this.search_keys, pageNumber:1});
        },
        methods: {
            exec_search({
                            search_keys = JSON.parse(this.search_keys_snap),
                            pageNumber = this.search_result.pageNum,
                            pageSize = this.search_result.pageSize,
                        }) {
                debugger
                let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
                let userName = search_keys.userName;
                let trueName = search_keys.trueName;
                let compName
                activityService.findUserList({userName, trueName, pageNumber, pageSize}).then(resp => {
                    this.search_result = resp.data;                //视图展示查询结果
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
            //删除
            deleteUser(uuid) {
                this.$confirm("确定删除吗？", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
                    type: 'warning',
                    center: true
                }).then(() => {
                        activityService.delete(uuid).then(resp => {
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
            //密码重置
            reset(uuid) {
                activityService.reset(uuid).then(resp => {
                    if (resp.data == 1) {
                        this.$notify({message: "密码已重置", type: 'success'});
                    } else {
                        this.$notify({message: "密码重置失败，请重试", type: 'error'});
                    }
                })
            },
        },
    }
</script>

<style scoped>

</style>