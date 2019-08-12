<template>
  <div style="display: flex;flex-direction: column;height: 100%">
    <div class="el-header">
      <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
        <el-form-item :label="$t('pageTable.SupplierCode')">
          <el-input :placeholder="$t('pageTable.SupplierCode')" v-model="search_keys.supplierCode" class="search-form-item-input"></el-input>
        </el-form-item>
        <el-form-item :label="$t('pageTable.SupplierNameCn')">
          <el-input :placeholder="$t('pageTable.SupplierNameCn')" v-model="search_keys.supplierNameCn" class="search-form-item-input"></el-input>
        </el-form-item>
        <el-form-item :label="$t('pageTable.SupplierNameEn')">
          <el-input :placeholder="$t('pageTable.SupplierNameEn')" v-model="search_keys.supplierNameEn" class="search-form-item-input"></el-input>
        </el-form-item>
        <el-collapse accordion>
          <el-collapse-item>
            <template slot="title">
              <i class="header-icon el-icon-s-operation">{{$t('Tips.allSearchKey')}}</i>
            </template>
            <div>
              <el-form-item :label="$t('pageTable.SupplierDescCn')">
                <el-input :placeholder="$t('pageTable.SupplierDescCn')" v-model="search_keys.supplierDescCn" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item :label="$t('pageTable.SupplierDescEn')">
                <el-input :placeholder="$t('pageTable.SupplierDescEn')" v-model="search_keys.supplierDescEn" class="search-form-item-input"></el-input>
              </el-form-item>
              <el-form-item :label="$t('pageTable.SupplierCata')">
                <el-select v-model="search_keys.supplierCata"  size="mini" class="search-form-item-input">
                  <el-option value=""></el-option>
                  <el-option
                          size="mini" knx
                          v-for="item in supplierCatas"
                          :key="item.value"
                          :label="item.name"
                          :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item :label="$t('pageTable.SupplierLevel')">
                <el-select v-model="search_keys.supplierLevel"  size="mini" class="search-form-item-input">
                  <el-option value=""></el-option>
                  <el-option
                    size="mini" knx
                    v-for="item in supplierLevels"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item :label="$t('pageTable.SupplierAt')">
                <el-select v-model="search_keys.supplierAt"  size="mini" class="search-form-item-input">
                  <el-option value=""></el-option>
                  <el-option
                          size="mini"
                          v-for="item in supplierAts"
                          :key="item.value"
                          :label="item.name"
                          :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </div>
          </el-collapse-item>
        </el-collapse>
        <el-form-item style="float: right">
          <el-button type="primary" :disabled="multipleSelection.length==0" v-if="subject.hasPermissions('maintenance:basis:supplier:info:delete')"  @click="deleteMatter" icon="el-icon-delete" >
           {{$t('searchFrom.delete')}}
          </el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary"  @click="reset" icon="el-icon-error" >
            {{$t('searchFrom.reset')}}
          </el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" v-if="subject.hasPermissions('maintenance:basis:supplier:info:add')"  @click="add()" icon="el-icon-plus" >
            {{$t('searchFrom.add')}}
          </el-button>
        </el-form-item>


        <el-form-item style="float: right">
          <el-button type="primary" v-if="subject.hasPermissions('maintenance:basis:supplier:info:select')" @click="exec_search({search_keys, pageNum:1})"  >
            <i class="fa fa-search" aria-hidden="true"></i> {{$t('searchFrom.search')}}
          </el-button>
        </el-form-item>

      </el-form>
    </div>

    <el-table size="mini"
              style="flex: 1"
              :height="600"
              border
              ref="tb"
              class="search-result-table"
              :data="search_result.list" row-key="id"
              :stripe="true"
              @row-click="clickRow"
              @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" fixed width="50" align="center"/>
      <el-table-column prop="supplierCode"  width="100" align="center" :label="$t('pageTable.SupplierCode')"  />
      <el-table-column prop="supplierNameCn"  width="220" align="center" :label="$t('pageTable.SupplierNameCn')" />
      <el-table-column prop="supplierNameEn"  width="220" align="center" :label="$t('pageTable.SupplierNameEn')" />
<!--      <el-table-column prop="supplierQuo" width="100" align="center" label="供应商配额"  />-->
      <el-table-column prop="supplierDescCn" width="150" :show-overflow-tooltip="true" align="center" :label="$t('pageTable.SupplierDescCn')"  />
      <el-table-column prop="supplierDescEn" width="150" :show-overflow-tooltip="true" align="center" :label="$t('pageTable.SupplierDescEn')"  />
      <el-table-column prop="address"  :show-overflow-tooltip="true" align="center" :label="$t('pageTable.SupplierAddress')"  />
      <el-table-column prop="contact"  :show-overflow-tooltip="true" align="center" :label="$t('pageTable.SupplierContact')"  />
      <el-table-column prop="accountBank" align="center" :label="$t('pageTable.SupplierAccountBank')"  />
      <el-table-column prop="bankAddress" align="center" :show-overflow-tooltip="true" width="150" :label="$t('pageTable.SupplierBankAddress')"  />
      <el-table-column prop="accountNo" align="center" :label="$t('pageTable.SupplierBankAddress')"  />
      <el-table-column prop="accountant" align="center" :label="$t('pageTable.SupplierAccountNo')"  />
      <el-table-column prop="contactWays" align="center" :label="$t('pageTable.SupplierAccountant')"  />
      <el-table-column prop="dictSupplierCata.name" align="center" :label="$t('pageTable.SupplierCata')"  />
      <el-table-column prop="dictSupplierLevel.name" align="center" :label="$t('pageTable.SupplierLevel')"  />
      <el-table-column prop="dictSupplierAt.name" align="center" :label="$t('pageTable.SupplierAt')"  />
      <el-table-column prop="createUser" align="center" :label="$t('pageTable.createUser')"  />
      <el-table-column align="center" width="150" :label="$t('pageTable.createTime')"  >
        <template slot-scope="scope">
            {{scope.row.createTime != null ?$moment(scope.row.createTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
        </template>
      </el-table-column>
      <el-table-column prop="updateUser" align="center" :label="$t('pageTable.updateUser')"  />
      <el-table-column align="center" width="150" :label="$t('pageTable.updateTime')" >
        <template slot-scope="scope">
            {{scope.row.updateTime != null ?$moment(scope.row.updateTime,'YYYYMMDDHHmmss').format('YYYY-MM-DD h:mm:ss a') : ''}}
        </template>
      </el-table-column>
      <el-table-column fixed="right" width="120" :label="$t('pageTable.operate')" >
        <template slot-scope="scope" >
          <el-button  type="primary" size="mini"  v-if="subject.hasPermissions('maintenance:basis:supplier:info:edit')"  class="btn-opt smallButton" plain @click="edit(scope.row.supplierId)">
            <i class="el-icon-news"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <edit-supplier v-bind.sync="link_modal_state" @success="exec_search({search_keys, pageNum:1})" v-if="link_modal_state.activated"></edit-supplier>
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
  import activityService from '@/api/basedata/supplier.js'
  import ui_config from '@/config/ui_config'
  import ImportButton from '@/components/data-import/ImportButton'
  import EditSupplier from './EditSupplier'
  import { mapGetters,mapState } from 'vuex'

  export default {
    data() {
      return {
        PAGE_SIZES : ui_config.PAGE_SIZES,
        link_modal_state      : {},
        //单位下拉框数据
        is_searching : true,
        supplierCatas:[],
        supplierLevels:[],
        supplierAts:[],
        search_keys   : {
          supplierCode:'',
          supplierNameCn:'',
          supplierNameEn:'',
          supplierDescCn:'',
          supplierDescEn:'',
          supplierCata:'',
          supplierLevel:'',
          supplierAt:'',
        },
        search_keys_snap      : null,
        search_result         : {},
        multipleSelection:[],
        idsStr:[],
      };
    },
    components:{ImportButton,EditSupplier},
    async mounted() {
      await this.init();
      this.exec_search({search_keys:this.search_keys, pageNum:1});
    },
    computed:{
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
    methods: {
      reset(){
        this.search_keys   = {
          supplierCode:'',
            supplierNameCn:'',
            supplierNameEn:'',
            supplierDescCn:'',
            supplierDescEn:'',
            supplierCata:'',
            supplierLevel:'',
            supplierAt:'',
        }
      },
      init(){
        activityService.initData().then(resp =>{
          this.supplierCatas = resp.data.result.supplierCatas;
          this.supplierLevels = resp.data.result.supplierLevels;
          this.supplierAts = resp.data.result.supplierAts;
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
        activityService.findSupplierList({...search_keys, pageNum, pageSize}).then(resp => {
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
      //
      add() {
        this.link_modal_state={activated:true,mode:"ADD"};
      },
      //编辑
      edit(id) {
        this.link_modal_state={activated:true,id,mode:"EDIT"};
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      clickRow(row){
        this.$refs.tb.toggleRowSelection(row);
      },
      //删除
      deleteMatter(uuid) {
        this.$confirm(this.$t("Tips.deleteQueries"), this.$t("Tips.tips"), {
          confirmButtonText:this.$t("Tips.yes"),
          cancelButtonText: this.$t("Tips.no"),
          type: 'warning',
          center: true
        }).then(() => {
            this.idsStr=[];
            this.multipleSelection.forEach(item=>{
              this.idsStr.push(item.supplierId)
            })
            activityService.deleteById({idsStr:this.idsStr}).then(resp => {
              if (resp.data.code=="201"){
                this.$notify({message: resp.data.message, type: "success"});
                this.exec_search({search_keys:this.search_keys, pageNum:1})
              } else {
                this.$notify({message: resp.data.message, type: "error"});
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
  .search-result-table >>> .el-table__body-wrapper::-webkit-scrollbar {
    width: 10px;
    height: 15px;
  }
  .search-result-table >>>.el-table__body-wrapper::-webkit-scrollbar-thumb {
    background-color: #a1a3a9;
    border-radius: 3px;
  }

</style>