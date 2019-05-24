<template>
  <div style="display: flex;flex-direction: column;height: 100%">
    <!-- 检索条件部分Start -->
    <div style="margin-left: 5px">
      <el-form :inline="true" size="mini" class="searchArea search-form search-form-normal" @submit.native.prevent>

        <el-form-item  label="名称:">
          <el-input v-model="search_keys.name" class="search-form-item-input"></el-input>
        </el-form-item>

        <el-form-item style="float: right">
          <el-button type="" @click="Return()"  size="mini" >
            <i class="fa fa-back" aria-hidden="true"></i> 返回
          </el-button>
        </el-form-item>

        <el-form-item style="float: right">
          <el-button type="" @click="add()"  size="mini" >
            <i class="fa fa-plus" aria-hidden="true"></i> 添加
          </el-button>
        </el-form-item>

        <el-form-item label=" " label-width="40px" style="float: right">
          <el-button type="primary"  @click="exec_search({search_keys, pageNumber:1})" native-type="submit" >
            <i class="fa fa-search" aria-hidden="true"></i> 查询
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 检索条件部分End -->

    <!--<div style="padding-top: 10px">-->
      <!--<el-form :inline="true" size="mini" class="search-form search-form-normal">-->
        <!--<el-form-item label-width="0px">-->
          <!--<el-button type="primary" style="float: right; width: 80px"-->
                     <!--@click="()=>{ $refs.searchForm.validate(); exec_search({search_keys,page_number:1}); }">新增-->
          <!--</el-button>-->
        <!--</el-form-item>-->
      <!--</el-form>-->
    <!--</div>-->
    <el-table size="mini"
              style="flex: 1"
              :height="600"
              class="search-result-table"
              :data="search_result.list" row-key="id"
              :cell-class-name="({row,rowIndex,columnIndex}) => { return null; }"
              :row-class-name="({row,rowIndex}) => { return row.summary_line ? 'summary-row' : null; }"
              :default-sort = "{prop:'code',order: 'ascending'}"
              :stripe="true"
              v-loading="is_searching"
    >
      <el-table-column
        prop="id"
        sortable
        label="编号">
      </el-table-column>
      <el-table-column
        prop="name"
        sortable
        label="名称">
      </el-table-column>
      <el-table-column
        prop="sortCode"
        sortable
        label="排序">
      </el-table-column>
      <el-table-column
        prop="parentUuid"
        sortable
        label="父级分类">
      </el-table-column>
      <el-table-column
        prop="isEnable" :formatter="formatterEnable"
        sortable
        label="有效">
      </el-table-column>
      <el-table-column
        prop="remark"
        sortable
        label="备注" show-tooltip-when-overflow />

      <el-table-column
        prop="createTime"
        sortable
        :formatter="(row,col,val)=>val&&$moment(val,'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm:ss')"
        label="创建时间">
      </el-table-column>
      <el-table-column label="操作" >
        <template slot-scope="scope" >
          <el-button title="编辑" type="primary" size="mini" class="btn-opt" plain @click="edit(scope.row,'编辑')">
            <i class="el-icon-edit"></i></el-button>
          <el-button title="删除" type="danger" size="mini" class="btn-opt" plain  @click="remove(scope.row)">
            <i class="el-icon-delete"></i></el-button>
        </template>
      </el-table-column>
      <!--<el-table-column fixed="right" label="操作" width="300">-->
        <!--<template slot-scope="scope">-->
          <!--<el-button title="编辑" type="primary" size="mini" class="btn-opt" plain @click="edit(scope)">-->
            <!--<i class="el-icon-edit"></i></el-button>-->
          <!--<el-button title="删除" type="danger" size="mini" class="btn-opt" plain @click="delete(scope.row)">-->
            <!--<i class="el-icon-delete"></i></el-button>-->
        <!--</template>-->
      <!--</el-table-column>-->
    </el-table>

    <div style="text-align: center; margin-top: 10px">
      <el-pagination
        :currentPage="search_result.pageNum"
        @current-change="exec_search({pageNumber:$event})"
        @size-change="exec_search({pageNumber:1,pageSize:$event})"
        :page-size="search_result.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="search_result.total">
      </el-pagination>
    </div>
    <div>
      <EditCategoryDialog v-bind.sync="dialogState" v-if="dialogState.activated" @refresh="refresh()"></EditCategoryDialog>
    </div>
  </div>
</template>

<script>
  import {getRequest, postRequest} from "@/utils/request_utils";
  import EditCategoryDialog from './EditCategoryDialog'
  export default {
    components:{
      EditCategoryDialog
    },
    data() {
      return {
        dialogState:{
          activated:false
        },
        search_keys: {
          name: "",
        },
        search_keys_snap: null,
        search_result: {},
        is_searching : false,
      }
    },
    mounted() {
      this.init();
      // this.exec_search({search_keys: this.search_keys, pageNumber: 1});
    },
    methods: {
      init() {

      },
      // 查询处理
      exec_search({
                    search_keys = JSON.parse(this.search_keys_snap),
                    pageNumber = this.search_result.pageNumber,
                    pageSize = this.search_result.pageSize,
                  }) {
        let search_key = this.search_keys;
        let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
        // this.is_searching = true;
        // service.findDictTypes({...search_key, pageNumber, pageSize}).then(resp => {
        //   debugger
        //   this.search_result = resp.data;                //视图展示查询结果
        //   this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
        //   this.search_keys_snap = search_keys_snap;             //存储查询条件快照      //存储查询条件快照
        // }, err => {
        //   // this.$notify.error({ title: '服务器被吃了⊙﹏⊙∥', message: err.response && err.response.data.message || err.message }); //异常处理
        //   console.error(err);
        // }).finally (()=>{
        //   this.is_searching = false;
        // })
        /*getRequest(`~/setting/dictionaries/findMDictionaryItems`, {
          params: {
            ...search_key,
            page_number,
            page_size
          }
        }).then(resp => {
          this.search_result = resp.data;                //视图展示查询结果
          this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
          this.search_keys_snap = search_keys_snap;             //存储查询条件快照      //存储查询条件快照
        }, err => {
          // this.$notify.error({ title: '服务器被吃了⊙﹏⊙∥', message: err.response && err.response.data.message || err.message }); //异常处理
          console.error(err);
        })*/
      },

      //页面刷新
      refresh(){
        this.exec_search({search_keys: this.search_keys, pageNumber: 1});
      },


      /*新增*/
      add(){
          this.dialogState = {activated:true,mode:'ADD'}
      },
      /*编辑*/
      edit(entity){
        this.dialogState = {activated:true,entity,mode:'EDIT'}
      },
      Return(){
        this.$router.push({name:'BasicsDictionaryIndex'})
      },
      /*删除*/
      remove(row) {
        this.$confirm("此操作将删除该记录, 是否继续?","提示！", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: 'warning',
          center: true
        }).then(() => {
          service.deleteDictType(row.id).then(resp=>{

            this.$notify({title: '成功',type: 'success', message: resp.data.msg});
              this.refresh()

          })
        }).catch(() => {
          this.$notify({title: '消息',type: 'info',message: "已取消删除"
          });
        });
      },
      formatterEnable(row,col,val){
        if(val==0){
          return "无效"
        }else {
          return "有效"
        }
      },
    }
  }
</script>

<style scoped>
  .searchArea .el-form-item {
    margin-bottom: 5px;
  }
</style>
