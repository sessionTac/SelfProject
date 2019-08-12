<template>
  <div style="display: flex;flex-direction: column;height: 100%">
    <!-- 检索条件部分Start -->
    <div style="margin-left: 5px">
      <el-form :inline="true" size="mini" class="searchArea search-form search-form-normal" @submit.native.prevent>

        <el-form-item  :label="$t('pageTable.dictionaryName')">
          <el-input v-model="search_keys.name" class="search-form-item-input"></el-input>
        </el-form-item>

        <el-form-item style="float: right">
          <el-button type="primary" @click="Return()"  size="mini" >
            <i class="fa fa-back" aria-hidden="true"></i> {{$t('searchFrom.back')}}
          </el-button>
        </el-form-item>

        <el-form-item style="float: right">
          <el-button type="primary" @click="add()" v-if="subject.hasPermissions('maintenance:system:categories:info:add')"  size="mini" >
            <i class="fa fa-plus" aria-hidden="true"></i> {{$t('searchFrom.add')}}
          </el-button>
        </el-form-item>

        <el-form-item label=" " label-width="40px" style="float: right">
          <el-button type="primary" v-if="subject.hasPermissions('maintenance:system:categoriesinfo')"  @click="exec_search({search_keys, pageNum:1})"  >
            <i class="fa fa-search" aria-hidden="true"></i> {{$t('searchFrom.search')}}
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
              border
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
        prop="code"
        sortable
        :label="$t('pageTable.dictionaryNo')">
      </el-table-column>
      <el-table-column
        prop="name"
        sortable
        :label="$t('pageTable.dictionaryName')">
      </el-table-column>
      <el-table-column
        prop="sortCode"
        sortable
        :label="$t('pageTable.dictionarySort')">
      </el-table-column>
      <el-table-column
        prop="parentId"
        sortable
        :label="$t('pageTable.Parental')">
      </el-table-column>
      <el-table-column
        prop="isEnable" :formatter="formatterEnable"
        sortable
        :label="$t('pageTable.dictionaryEnable')">
      </el-table-column>
      <el-table-column
        prop="remark"
        sortable
        :label="$t('pageTable.dictionaryRemark')" show-tooltip-when-overflow />

      <el-table-column
        prop="createTime"
        sortable
        :label="$t('pageTable.createTime')">
      </el-table-column>
      <el-table-column :label="$t('pageTable.operate')" >
        <template slot-scope="scope" >
          <el-button  type="primary" size="mini" class="btn-opt" v-if="subject.hasPermissions('maintenance:system:categories:info:edit')" plain @click="edit(scope.row,'编辑')">
            <i class="el-icon-edit"></i></el-button>
          <el-button  type="danger" size="mini" class="btn-opt" v-if="subject.hasPermissions('maintenance:system:categories:info:delete')" plain  @click="remove(scope.row)">
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
        @current-change="exec_search({pageNum:$event})"
        @size-change="exec_search({pageNum:1,pageSize:$event})"
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
  import EditCategoryDialog from './EditCategoryDialog'
  import service from '@/api/sysConfig/dictionary'
  import { mapGetters,mapState } from 'vuex'

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
    watch:{
      async language(val,val1){
        // alert(val+val1)
        await this.init();
        this.exec_search({search_keys:this.search_keys, pageNum:1});
      }
    },
    async mounted() {
      await this.init();
      this.exec_search({search_keys: this.search_keys, pageNum: 1});
    },
    methods: {
      init() {

      },
      // 查询处理
      exec_search({
                    search_keys = JSON.parse(this.search_keys_snap),
                    pageNum = this.search_result.pageNum,
                    pageSize = this.search_result.pageSize,
                  }) {
        let search_key = this.search_keys;
        let search_keys_snap = JSON.stringify(search_keys);     //抓查询条件快照
        this.is_searching = true;
        service.findDictTypes({...search_key, pageNum, pageSize}).then(resp => {
          this.search_result = resp.data.resultInfo;                //视图展示查询结果
          this.search_keys = JSON.parse(search_keys_snap); //还原查询条件
          this.search_keys_snap = search_keys_snap;             //存储查询条件快照      //存储查询条件快照
        }, err => {
          // this.$notify.error({ title: '服务器被吃了⊙﹏⊙∥', message: err.response && err.response.data.message || err.message }); //异常处理
          console.error(err);
        }).finally (()=>{
          this.is_searching = false;
        })

      },

      //页面刷新
      refresh(){
        this.exec_search({search_keys: this.search_keys, pageNum: 1});
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
        this.$router.push({name:'dictionary'})
      },
      /*删除*/
      remove(row) {
        this.$confirm("此操作将删除该记录, 是否继续?","提示！", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: 'warning',
          center: true
        }).then(() => {
          service.deleteDictType({dictTypeId:row.dictTypeId}).then(resp=>{
            if (resp.data.code=='201'){
              this.$notify({type: 'success', message: resp.data.message});
              this.refresh()
            } else {
              this.$notify({type: 'error', message: resp.data.message});
            }


          })
        }).catch(() => {
          this.$notify({title: '消息',type: 'info',message: "已取消删除"
          });
        });
      },
      formatterEnable(row,col,val){
        if(val==0){
          if (this.language=='zh'){
            return "无效"
          } else if(this.language=='en'){
            return "disable"
          } else if(this.language=='es'){
            return "disable1"
          }
        }else {
          if (this.language=='zh'){
            return "有效"
          } else if(this.language=='en'){
            return "enable"
          } else if(this.language=='es'){
            return "enable1"
          }
        }
      },
    },
    computed: {
      ...mapState({
        language:state=>state.app.language
      }),

    },
  }
</script>

<style scoped>
  .searchArea .el-form-item {
    margin-bottom: 5px;
  }
</style>
