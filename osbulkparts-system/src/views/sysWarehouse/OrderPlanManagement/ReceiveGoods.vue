<template>
  <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="800px">
    {{multipleSelection}}
    <el-card>
      <div class="dialogStyle" style="display: flex;flex-direction: column">
        <el-form :inline="true" class="search-form search-form-normal" size="mini" ref="searchForm" :model="search_keys">
          <el-form-item label="航次">
            <el-input placeholder="航次" v-model="search_keys.a" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="集装箱号">
            <el-input placeholder="集装箱号" v-model="search_keys.b" class="search-form-item-input"></el-input>
          </el-form-item>
          <el-form-item label="合同号">
            <el-input placeholder="合同号" v-model="search_keys.c" class="search-form-item-input"></el-input>
          </el-form-item>
        </el-form>
        <el-table
          ref="multipleTable"
          :data="tableData"
          size="mini"
          :height="600"
          class="search-result-table"
          style="flex: 1"
          border
          tooltip-effect="dark">
          <el-table-column label="日期" width="120">
            <template slot-scope="scope">{{ scope.row.date }}</template>
          </el-table-column>
          <el-table-column prop="name" label="订单型号" width="120"/>
          <el-table-column prop="name" label="订单数量" width="120"/>
          <el-table-column prop="name" label="物料号" width="120"/>
          <el-table-column prop="num" label="单耗数量" width="120">
            <template slot-scope="scope">
              <el-input v-model="scope.row.num"></el-input>
              {{ scope.row.num }}
            </template>
          </el-table-column>
          <el-table-column prop="name" label="换算后数量" width="120"/>
          <el-table-column prop="name" label="超发数量" width="120"/>
          <el-table-column prop="name" label="建议数量" width="120"/>
          <!--<el-table-column fixed="right" width="120" label="操作" >-->
          <!--<template slot-scope="scope" >-->
          <!--<el-button title="收货" type="primary" size="mini" class="btn-opt" plain  @click="deleteMatter(scope.row.uuid)">-->
          <!--确认收货</el-button>-->
          <!--</template>-->
          <!--</el-table-column>-->
        </el-table>
      </div>
    </el-card>


    <div class="dialogButton">
      <el-button type="primary" size="mini"  @click=""><i
        class="fa fa-check"></i> 确定
      </el-button>
      <el-button size="mini" @click=" ">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "ReceiveGoods",
    props:{
      multipleSelection: {}
    },
    data(){
      return {
        search_keys:{

        },
        dialogFormVisible: true,
        title:"发货明细",
        tableData: [{
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-08',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-06',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-07',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-07',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-07',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-07',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-07',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-07',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }],

      }
    }
  }
</script>

<style scoped>

</style>