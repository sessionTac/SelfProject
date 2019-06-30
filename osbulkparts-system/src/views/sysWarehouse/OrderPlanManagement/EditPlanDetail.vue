<template>
  <div>
    <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
               width="600px">
      <el-card>
        <div class="dialogStyle" style="display: flex;flex-direction: column">
          <el-form  class="search-form search-form-normal" label-width="110px" ref="form"
                    style="flex: 5" :model="form" size="mini" :rules="rules">
            <el-form-item label="订单产品型号" prop="orderCode">
              <el-autocomplete v-model="form.orderCode" :disabled="mode ==='EDIT'" class="search-form-item-input" style="width: 160px" size="mini"
                            :fetch-suggestions="searchOrderCode"    clearable></el-autocomplete>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 30px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
              <div style="float: right;margin-right: 40px;"><el-button :disabled="form.orderCode==''" @click="checkOrderCode(form.orderCode)">校验并获取</el-button></div>
            </el-form-item>
            <el-form-item label="订单产品型号描述" prop="orderCodeDesc">
              <el-input type="textarea" autosize="" v-model="form.orderCodeDesc" class="search-form-item-input" style="width: 160px" size="mini"
                       :disabled="true" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="订单数量" prop="orderAmount">
              <el-input v-model="form.orderAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"  :maxlength="18" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="订单日期" prop="orderDate">
              <el-date-picker
                :disabled="true"
                v-model="form.orderDate"
                type="date"
                style="width:160px"
                value-format="yyyyMMddHHmmss"
                placeholder="选择日期">
              </el-date-picker>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="订单型号单位" prop="orderUnit">
              <el-select :disabled="true" v-model="form.orderUnit" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in orderUnits"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="订单号" prop="orderId">
              <el-input v-model="form.orderId" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"    :maxlength="20"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="订单行项目" prop="orderIdItem">
              <el-input v-model="form.orderIdItem" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"    :maxlength="5" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料号" prop="materialCode">
              <el-autocomplete v-model="form.materialCode" class="search-form-item-input" style="width:160px" size="mini"
                               :fetch-suggestions="searchMaterialCode"  :disabled="form.orderCode==''"   clearable></el-autocomplete>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 30px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
              <div style="float: right;margin-right: 40px;"><el-button :disabled="form.materialCode==''" @click="checkMaterialCode(form.materialCode)">校验并获取</el-button></div>

            </el-form-item>
            <el-form-item label="物料中文描述" prop="materialDescCn">
              <el-input v-model="form.materialDescCn" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料英文描述" prop="materialDescEn">
              <el-input v-model="form.materialDescEn" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料俄文描述" prop="materialDescRn">
              <el-input v-model="form.materialDescRn" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>

            <el-form-item label="物料单位" prop="materialUnit">
              <el-select :disabled="true" v-model="form.materialUnit" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialUnit"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料数量" prop="materialAmount">
              <el-input v-model="form.materialAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"      :maxlength="18" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料类别" prop="materialCategory">
              <el-select :disabled="true" v-model="form.materialCategory" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialCategory"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="换算关系" prop="materialRelation">
              <el-input v-model="form.materialRelation" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="换算后单位" prop="materialRelationUnit">
              <el-select :disabled="true" v-model="form.materialRelationUnit" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialRelationUnit"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="换算后数量" prop="materialRelationQuantity">
              <el-input v-model="form.materialRelationQuantity" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"  :maxlength="18" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="最小包装类型" prop="materialMinpackageType">
              <el-select :disabled="true" v-model="form.materialMinpackageType" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialMinpackageType"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="最小包装数量" prop="materialMinpackageAmt">
              <el-input v-model="form.materialMinpackageAmt" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"   :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="最小包装总量" prop="materialMinpackageTotalamt">
              <el-input v-model="form.materialMinpackageTotalamt" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"   :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="未税单价" prop="materialTaxPrice">
              <el-input v-model="form.materialTaxPrice" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"   :maxlength="18"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="未税总价" prop="materialTaxTotalprice">
              <el-input v-model="form.materialTaxTotalprice" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"    :maxlength="18"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="含税单价" prop="materialVatPrice">
              <el-input v-model="form.materialVatPrice" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"    :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="含税总价" prop="materialVatTotalprice">
              <el-input v-model="form.materialVatTotalprice" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"     :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="代理费率" prop="materialRate">
              <el-input v-model="form.materialRate" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="true"   :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="币种" prop="materialCurrency">
              <el-select :disabled="true" v-model="form.materialCurrency" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialCurrency"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="国家标志" prop="countryCode">
              <el-select :disabled="form.orderCode==''" v-model="form.countryCode" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in countryCode"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="状态" prop="confirmStatus">
              <el-select :disabled="form.orderCode==''" v-model="form.confirmStatus" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in confirmStatus"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="型号发货总数量" prop="orderOutTotalAmount">
              <el-input v-model="form.orderOutTotalAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"  :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="子件发货总数量" prop="materOutTotalAmount">
              <el-input v-model="form.materOutTotalAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"   :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="订单剩余数量" prop="residualAmount">
              <el-input v-model="form.residualAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"   :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="调整后数量" prop="trimAmount">
              <el-input v-model="form.trimAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"   :maxlength="18"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="库存数量" prop="stockAmount">
              <el-input v-model="form.stockAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"  :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="差异数量" prop="differAmount">
              <el-input v-model="form.differAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"  :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="收货数量" prop="takeOverAmount">
              <el-input v-model="form.takeOverAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"  :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="发货数量" prop="deliveryAmount">
              <el-input v-model="form.deliveryAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"   :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料剩余数量" prop="surplusAmount">
              <el-input v-model="form.surplusAmount" class="search-form-item-input" style="width:160px" size="mini"
                        :disabled="form.orderCode==''"   :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>


          </el-form>

        </div>

      </el-card>
      <div class="dialogButton">
        <el-button type="primary" size="mini" :disabled="form.isLocked==1" @click="submit('form')"><i
          class="fa fa-check"></i> 确定
        </el-button>
        <el-button size="mini" @click=" cancel">取消</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
  import activityService from '@/api/warehouse/planDetail'
  import ui_config from '@/config/ui_config'

  export default {
    name: "EditOrderPlan",
    props: {
      id: {},
      mode: "",
    },
    computed: {
      title: function () {
        let entityName = '订单详情'
        if (this.mode === 'ADD') {
          return '新增' + entityName
        } else if (this.mode === 'EDIT') {
          return '编辑' + entityName
        }
      }
    },
    data() {
      return {
        dialogFormVisible: true,
        search_keys: {},
        orderUnits:[],
        materialUnit:[],
        materialCategory:[],
        materialRelationUnit:[],
        materialMinpackageType:[],
        materialCurrency:[],
        countryCode:[],
        confirmStatus:[],
        form: {
          orderCode:"",
          orderCodeDesc:"",
          orderAmount:"",
          orderDate:"",
          orderUnit:"",
          orderId:"",
          orderIdItem:"",
          materialCode:"",
          materialDescCn:"",
          materialDescEn:"",
          materialDescRn:"",
          materialUnit:"",
          materialAmount:"",
          materialCategory:"",
          materialRelation:"",
          materialRelationUnit:"",
          materialRelationQuantity:"",
          materialMinpackageType:"",
          materialMinpackageAmt:"",
          materialMinpackageTotalamt:"",
          materialTaxPrice:"",
          materialTaxTotalprice:"",
          materialVatPrice:"",
          materialVatTotalprice:"",
          materialRate:"",
          materialCurrency:"",
          countryCode:"",
          confirmStatus:"",
          orderOutTotalAmount:"",
          materOutTotalAmount:"",
          residualAmount:"",
          trimAmount:"",
          stockAmount:"",
          differAmount:"",
          takeOverAmount:"",
          deliveryAmount:"",
          surplusAmount:"",
        },
        orderCodeList:[],
        materialCodeList:[],
        /**表单的验证*/
        rules: {
          orderCode: [
            {required: true, message: '请填写订单型号', trigger: 'blur'},
            {max: 32, message: '长度不超过20个字符', trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          ],
          orderCodeDesc: [
            {required: true, message: '请填写订单型号描述', trigger: 'blur'},
            {max: 200, message: '长度不超过200个字符', trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          ],
          orderAmount: [
            {required: true, message: '请填写订单数量', trigger: 'blur'},
            // {pattern: /^[0-9]*$/ , trigger: 'blur', message: '请输入数字',}
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          orderDate:[
            {required: true, message: '请填写订单日期', trigger: 'blur'},
          ],
          orderUnit: [
            {required: true, message: '请选择订单型号单位', trigger: 'change'}
          ],
          orderId:[
            {max: 20, message: '长度不超过20个字符', trigger: 'blur'},
          ],
          orderIdItem:[
            {max: 5, message: '长度不超过5个字符', trigger: 'blur'},
          ],
          materialCode: [
            {required: true, message: '请填写物料号', trigger: 'blur'},
            {max: 20, message: '长度不超过20个字符', trigger: 'blur'},
          ],
          materialDescCn: [
            {required: true, message: '请填写物料中文描述', trigger: 'blur'},
            {max: 200, message: '长度不超过200个字符', trigger: 'blur'},
          ],
          materialDescEn: [
            {required: true, message: '请填写物料英文描述', trigger: 'blur'},
            {max: 200, message: '长度不超过200个字符', trigger: 'blur'},
          ],
          materialDescRn: [
            {required: true, message: '请填写物料俄文描述', trigger: 'blur'},
            {max: 200, message: '长度不超过200个字符', trigger: 'blur'},
          ],
          materialUnit: [
            {required: true, message: '请选择物料单位', trigger: 'change'}
          ],
          materialAmount: [
            {required: true, message: '请填写物料数量', trigger: 'blur'},
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialCategory: [
            {required: true, message: '请选择物料类别', trigger: 'change'}
          ],
          materialRelation: [
            {max: 30, message: '长度不超过30个字符', trigger: 'blur'},
          ],
          materialRelationUnit: [

          ],
          materialRelationQuantity: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialMinpackageType: [

          ],
          materialMinpackageAmt: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialMinpackageTotalamt: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialTaxPrice: [
            {required: true, message: '请填写未税单价', trigger: 'blur'},
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialTaxTotalprice: [
            {required: true, message: '请填写未税总价', trigger: 'blur'},
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialVatPrice: [
            {required: true, message: '请填写含税单价', trigger: 'blur'},
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialVatTotalprice: [
            {required: true, message: '请填写含税总价', trigger: 'blur'},
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialRate: [
            {required: true, message: '请填写代理费率', trigger: 'blur'},
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialCurrency: [
            {required: true, message: '请选择币种', trigger: 'change'}
          ],
          countryCode: [
            {required: true, message: '请选择国家标志', trigger: 'change'}
          ],
          confirmStatus: [

          ],
          orderOutTotalAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materOutTotalAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          residualAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          trimAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          stockAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          differAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          takeOverAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          deliveryAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          surplusAmount: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],



        },
      }
    },
    mounted() {
      this.init()
    },
    methods: {
      async init() {
        await activityService.initData().then(resp=>{
          this.orderUnits=resp.data.result.orderUnits
          this.materialUnit=resp.data.result.orderUnits
          this.materialCategory=resp.data.result.mattertype
          this.materialRelationUnit=resp.data.result.orderUnits
          this.materialMinpackageType=resp.data.result.minpackageType
          this.materialCurrency=resp.data.result.currency
          this.countryCode=resp.data.result.countryCode
          this.confirmStatus=resp.data.result.orderStatus
        },error=>{});
        await activityService.getAllOrderCode().then(resp=>{
          this.orderCodeList=resp.data.resultList
        });
        if (this.mode == "EDIT") {
          await activityService.findOrderDetailInfo({id: this.id}).then(resp => {
            this.form = resp.data.result;
          }, err => {
            console.error(err);
          });
        }

      },
      async checkMaterialCode(materialCode){
        await activityService.checkOrderCodeAndMaterialCode({materialCode:materialCode}).then(resp=>{
          if (resp.data.code == "201") {
            activityService.getMaterialInfoByMaterialCode({materialCode:materialCode}).then(resp=>{
              this.form.materialCategory=resp.data.result.materialCategory;
              this.form.materialDescCn=resp.data.result.materialDescCn;
              this.form.materialDescEn=resp.data.result.materialDescEn;
              this.form.materialDescRn=resp.data.result.materialDescRn;
              this.form.materialUnit=resp.data.result.materialUnit;
              this.form.materialRelation=resp.data.result.materialRelation;
              this.form.materialRelationUnit=resp.data.result.materialRelationUnit;
              this.form.materialMinpackageType=resp.data.result.materialMinpackageType;
              this.form.materialMinpackageAmt=resp.data.result.materialMinpackageAmt;
              this.form.materialTaxPrice=resp.data.result.materialTaxPrice;
              this.form.materialVatPrice=resp.data.result.materialVatPrice;
              this.form.materialRate=resp.data.result.materialRate;
              this.form.materialCurrency=resp.data.result.materialCurrency;

            });
          } else {
            this.form.materialCategory="";
            this.form.materialDescCn="";
            this.form.materialDescEn="";
            this.form.materialDescRn="";
            this.form.materialUnit="";
            this.form.materialRelation="";
            this.form.materialRelationUnit="";
            this.form.materialMinpackageType="";
            this.form.materialMinpackageAmt="";
            this.form.materialTaxPrice="";
            this.form.materialVatPrice="";
            this.form.materialRate="";
            this.form.materialCurrency="";
            this.$notify({message: resp.data.message, type: "error"});
          }
        })
      },
      async checkOrderCode(orderCode){
        await activityService.checkOrderCodeAndMaterialCode({orderCode:orderCode}).then(resp=>{
          if (resp.data.code == "201") {
            activityService.getOrderInfoByOrderCode({orderCode:orderCode}).then(resp=>{
              this.materialCodeList=[];
              this.form.orderCodeDesc=resp.data.resultList[0].orderCodeDesc;
              this.form.orderDate=resp.data.resultList[0].orderDate;
              this.form.orderUnit=resp.data.resultList[0].orderUnit;
              this.form.orderAmount=resp.data.resultList[0].orderAmount;
              resp.data.resultList.forEach(item=>{
                this.materialCodeList.push(item.materialInfoEntity)
              });
            });
          } else {
            this.form.orderCodeDesc="";
            this.form.orderDate="";
            this.form.orderUnit="";
            this.form.orderAmount="";
            this.materialCodeList=[];
            this.$notify({message: resp.data.message, type: "error"});
          }
        })
      },
      searchOrderCode(queryString, cb){
        let result=this.orderCodeList.filter(item=>{
          return item.orderCode.indexOf(queryString) >= 0
        }).map(item=>{
          let data={};
          data.value=item.orderCode;
          return data
        });
        cb(result);
      },
      searchMaterialCode(queryString, cb){
        let result=this.materialCodeList.filter(item=>{
          return item.materialCode.indexOf(queryString) >= 0
        }).map(item=>{
          let data={};
          data.value=item.materialCode;
          return data
        });
        cb(result);
      },
      /*确定*/
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = {
              id                        :this.id                              || undefined,
              orderCode                 :this.form.orderCode                  || undefined,
              orderCodeDesc             :this.form.orderCodeDesc              || undefined,
              orderAmount               :this.form.orderAmount                || undefined,
              orderDate                 :this.form.orderDate                  || undefined,
              orderUnit                 :this.form.orderUnit                  || undefined,
              orderId                   :this.form.orderId                    || undefined,
              orderIdItem               :this.form.orderIdItem                || undefined,
              materialCode              :this.form.materialCode               || undefined,
              materialDescCn            :this.form.materialDescCn             || undefined,
              materialDescEn            :this.form.materialDescEn             || undefined,
              materialDescRn            :this.form.materialDescRn             || undefined,
              materialUnit              :this.form.materialUnit               || undefined,
              materialAmount            :this.form.materialAmount             || undefined,
              materialCategory          :this.form.materialCategory           || undefined,
              materialRelation          :this.form.materialRelation           || undefined,
              materialRelationUnit      :this.form.materialRelationUnit       || undefined,
              materialRelationQuantity  :this.form.materialRelationQuantity   || undefined,
              materialMinpackageType    :this.form.materialMinpackageType     || undefined,
              materialMinpackageAmt     :this.form.materialMinpackageAmt      || undefined,
              materialMinpackageTotalamt:this.form.materialMinpackageTotalamt || undefined,
              materialTaxPrice          :this.form.materialTaxPrice           || undefined,
              materialTaxTotalprice     :this.form.materialTaxTotalprice      || undefined,
              materialVatPrice          :this.form.materialVatPrice           || undefined,
              materialVatTotalprice     :this.form.materialVatTotalprice      || undefined,
              materialRate              :this.form.materialRate               || undefined,
              materialCurrency          :this.form.materialCurrency           || undefined,
              countryCode               :this.form.countryCode                || undefined,
              confirmStatus             :this.form.confirmStatus              || undefined,
              orderOutTotalAmount       :this.form.orderOutTotalAmount        || undefined,
              materOutTotalAmount       :this.form.materOutTotalAmount        || undefined,
              residualAmount            :this.form.residualAmount             || undefined,
              trimAmount                :this.form.trimAmount                 || undefined,
              stockAmount               :this.form.stockAmount                || undefined,
              differAmount              :this.form.differAmount               || undefined,
              takeOverAmount            :this.form.takeOverAmount             || undefined,
              deliveryAmount            :this.form.deliveryAmount             || undefined,
              surplusAmount             :this.form.surplusAmount              || undefined,
              version                   :this.form.version                    || undefined,
              dataRoleAt                :this.form.dataRoleAt                 || undefined,

            };
            activityService.checkOrderCodeAndMaterialCode({orderCode:this.form.orderCode,materialCode:this.form.materialCode}).then(resp=>{
              if (resp.data.code == "201") {
                if (this.mode == 'EDIT') {  //编辑
                    activityService.updateOrderDetailInfo({...data}).then(resp => {
                      if (resp.data.code == "201") {
                        this.$notify({message: resp.data.message, type: "success"});
                        this.$emit("success");
                        this.dialogFormVisible = false
                      } else {
                        this.$notify({message: resp.data.message, type: "error"});
                      }
                    })
                } else {
                    activityService.addOrderDetailInfo({...data}).then(resp => {  //添加
                      if (resp.data.code == "201") {
                        this.$notify({message: resp.data.message, type: "success"});
                        this.$emit("success");
                        this.dialogFormVisible = false
                      } else {
                        this.$notify({message: resp.data.message, type: "error"});
                      }
                    })
                }
              }else {
                this.$notify({message: resp.data.message, type: "error"});
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      cancel() {
        this.dialogFormVisible = false
      },
    }
  }
</script>

<style scoped>

</style>