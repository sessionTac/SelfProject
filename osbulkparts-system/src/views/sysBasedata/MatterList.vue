<template>
    <div>
        <div class="el-header">
            <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
                <el-form-item label="所属单位">
                    <company-selector-input
                            target="UNIT"

                            input-class="search-form-item-input"
                            :lock-search-keys="search_keys"
                            v-model="search_keys.unit"
                    />
                </el-form-item>

                <el-form-item label=" 物料HNR号">
                    <el-input placeholder="物料HNR号" v-model="search_keys.userName" class="search-form-item-input" ></el-input>
                </el-form-item>

                <el-form-item label="物料专用号">
                    <el-input placeholder="物料专用号" v-model="search_keys.trueName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料类别">
                    <company-selector-input
                            target="UNIT"
                            input-class="search-form-item-input"
                            :lock-search-keys="search_keys"
                            v-model="search_keys.unit"
                    />
                </el-form-item>
                <el-form-item label="物料中文描述">
                    <el-input placeholder="物料中文描述" v-model="search_keys.trueName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="物料英文描述">
                    <el-input placeholder="物料英文描述" v-model="search_keys.trueName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="单位">
                    <el-input placeholder="单位" v-model="search_keys.trueName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="换算关系">
                    <el-input placeholder="换算关系" v-model="search_keys.trueName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="换算后单位">
                    <el-input placeholder="换算后单位" v-model="search_keys.trueName" class="search-form-item-input"></el-input>
                </el-form-item>
                <el-form-item label="币种">
                    <el-input placeholder="币种" v-model="search_keys.trueName" class="search-form-item-input"></el-input>
                </el-form-item>

                <el-form-item label-width="40px">
                    <el-button type="primary" class="btn-opt" @click="exec_search({search_keys:search_keys, pageNumber:1})"><i class="el-icon-search"></i> 查询</el-button>
                </el-form-item>

                <el-form-item class="btn-opt" style="float: right" >
                    <el-button @click="addUser()">模板下载</el-button>
                </el-form-item>
                <el-form-item class="btn-opt" style="float: right" >
                    <el-button @click="addUser()">导入</el-button>
                </el-form-item>
                <el-form-item class="btn-opt" style="float: right" >
                    <el-button @click="addUser()">Exceld导出</el-button>
                </el-form-item>
            </el-form>
        </div>
        <el-table size="mini"
                  class="search-result-table"
                  :data="search_result.list" row-key="id"
                  :stripe="true"
        >
            <el-table-column prop="compName" align="center" label="所属单位"  />
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
                    <el-button title="密码重置" type="primary" size="mini" class="btn-opt" plain @click="reset(scope.row.uuid)">
                        <i class="el-icon-refresh"></i></el-button>
                    <el-button title="编辑与查看" type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row.uuid)">
                        <i class="el-icon-news"></i></el-button>
                    <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="deleteUser(scope.row.uuid)">
                        <i class="el-icon-delete"></i></el-button>
                </template>
            </el-table-column>

        </el-table>

        <!--分页-->
        <div style="text-align: center; margin-top: 10px">
            <el-pagination @current-change="exec_search({pageNumber:$event})"
                           :total="search_result.total" :current-page="search_result.pageNum"
                           :page-size="search_result.pageSize" :disabled="!search_result.total"
                           background layout="prev, pager, next"/>
        </div>
    </div>
</template>

<script>
    export default {
        name: "MatterList"
    }
</script>

<style scoped>

</style>