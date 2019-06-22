<template>
  <div>
    <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
               width="600px">
      <el-card>
        <div class="dialogStyle" style="display: flex;flex-direction: column">
          <el-form :disabled="form.isLocked==1" class="search-form search-form-normal" label-width="110px" ref="form"
                   style="flex: 5" :model="form" size="mini" :rules="rules">
            <el-form-item label="订单号" prop="materialOrderCode">
              <el-input v-model="form.materialOrderCode" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="64" autocomplete="new-password" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料号" prop="materialCode">
              <el-input v-model="form.materialCode" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="64" autocomplete="new-password" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料CKD号" prop="materialCkdCode">
              <el-input v-model="form.materialCkdCode" class="search-form-item-input" style="width:160px" size="mini"
                        :maxlength="500" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料类别" prop="materialCategory">
              <el-select v-model="form.materialCategory" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialCategorys"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料中文描述" prop="materialDescCn">
              <el-input type="textarea" v-model="form.materialDescCn" class="search-form-item-input"
                        style="width: 160px" size="mini"
                        :maxlength="200" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料英文描述" prop="materialDescEn">
              <el-input type="textarea" v-model="form.materialDescEn" class="search-form-item-input"
                        style="width: 160px" size="mini"
                        :maxlength="200" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料俄文描述" prop="materialDescRn">
              <el-input type="textarea" v-model="form.materialDescRn" class="search-form-item-input"
                        style="width: 160px" size="mini"
                        :maxlength="200" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="HS海关编码" prop="hsNo">
              <el-input v-model="form.hsNo" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="供应商编号" prop="supplierCode">
              <el-input v-model="form.supplierCode" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="单位" prop="materialUnit">
              <el-select v-model="form.materialUnit" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialUnits"
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
              <el-input v-model="form.materialRelation" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="换算后单位" prop="materialRelationUnit">
              <el-select v-model="form.materialRelationUnit" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialRelationUnits"
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
              <el-input v-model="form.materialMinpackageAmt" class="search-form-item-input" style="width: 160px"
                        size="mini" :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="未税单价" prop="materialTaxPrice">
              <el-input v-model="form.materialTaxPrice" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="含税单价" prop="materialVatPrice">
              <el-input class="search-form-item-input" style="width: 160px" v-model="form.materialVatPrice" size="mini"
                        :maxlength="10" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="单价" prop="materialPrice">
              <el-input v-model="form.materialPrice" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="代理费率" prop="materialRate">
              <el-input v-model="form.materialRate" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="币种" prop="materialCurrency">
              <el-select v-model="form.materialCurrency" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in currencys"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="工厂号" prop="factoryCode">
              <el-input v-model="form.factoryCode" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="分级BOM编码" prop="levelBomCode">
              <el-input v-model="form.levelBomCode" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="50" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料供货模式分类标识" prop="materialSupplyMode">
              <el-select v-model="form.materialSupplyMode" style="width: 160px" size="mini" clearable>
                <el-option value=""></el-option>
                <el-option
                  size="mini"
                  v-for="item in materialSupplyModes"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
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
  import activityService from '@/api/basedata/matter.js'
  import ui_config from '@/config/ui_config'

  export default {
    name: "EditMatter",
    props: {
      id: {},
      mode: "",
    },
    computed: {
      title: function () {
        let entityName = '物料信息'
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
        currencys: [],
        materialUnits: [],
        materialRelationUnits: [],
        materialCategorys: [],
        materialSupplyModes: [],
        form: {
          materialCode: '',
          materialCkdCode: '',
          materialCategory: '',
          materialDescCn: '',
          materialDescEn: '',
          materialDescRn: '',
          materialUnit: '',
          hsNo: '',
          supplierCode: '',
          materialRelation: '',
          materialRelationUnit: '',
          materialMinpackageAmt: '',
          materialTaxPrice: '',
          materialVatPrice: '',
          materialPrice: '',
          materialCurrency: '',
          materialRate: '',
          levelBomCode: '',
          materialSupplyMode: '',
          factoryCode: '',

        },
        /**表单的验证*/
        rules: {
          materialOrderCode: [
            {required: true, message: '请填写订单号', trigger: 'blur'},
            {max: 20, message: '长度不超过20个字符', trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          ],
          materialCode: [
            {required: true, message: '请填写物料号', trigger: 'blur'},
            {max: 20, message: '长度不超过20个字符', trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          ],
          materialCkdCode: [
            {required: true, message: '请填写物料CKD号', trigger: 'blur'},
            {max: 20, message: '长度不超过20个字符', trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          ],
          materialCategory: [
            {required: true, message: '请选择物料类别', trigger: 'change'}
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
          hsNo: [
            {required: true, message: '请填写HS海关编码', trigger: 'blur'},
            {max: 100, message: '长度不超过100个字符', trigger: 'blur'},
          ],
          supplierCode: [
            {required: true, message: '请填写物供应商编号', trigger: 'blur'},
            {max: 50, message: '长度不超过50个字符', trigger: 'blur'},
          ],
          materialUnit: [
            {required: true, message: '请选择单位', trigger: 'change'}
          ],
          materialRelation: [
            {max: 5, message: '长度不超过5个字符', trigger: 'blur'},
          ],
          materialRelationUnit: [
            // {required: true, message: '请选择单位', trigger: 'change'}
          ],
          materialMinpackageAmt: [
            {max: 30, message: '长度不超过30个字符', trigger: 'blur'},
          ],
          materialTaxPrice: [
            {max: 18, message: '长度不超过18个字符', trigger: 'blur'},
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialVatPrice: [
            {max: 18, message: '长度不超过18个字符', trigger: 'blur'},
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialPrice: [
            {max: 18, message: '长度不超过18个字符', trigger: 'blur'},
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialRate: [
            {max: 18, message: '长度不超过18个字符', trigger: 'blur'},
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,2}$/, trigger: 'blur', message: '请输入数字且最多保留2位',}
          ],
          materialCurrency: [
            // {required: true, message: '请选择单位', trigger: 'change'}
          ],
          factoryCode: [
            {max: 32, message: '长度不超过32个字符', trigger: 'blur'},
          ],
          levelBomCode: [
            {max: 10, message: '长度不超过10个字符', trigger: 'blur'},
            {pattern: /^[0-9]*$/ , trigger: 'blur', message: '请输入数字',}
          ],

          materialSupplyMode: [
            // {required: true, message: '请选择单位', trigger: 'change'}
          ],


        },
      }
    },
    mounted() {
      this.init()
    },
    methods: {
      async init() {
        await activityService.initData().then(resp => {
          this.materialRelationUnits = resp.data.result.materialRelationUnits;
          this.materialUnits = resp.data.result.materialUnits;
          this.currencys = resp.data.result.currencys;
          this.materialCategorys = resp.data.result.materialCategorys;
          this.materialSupplyModes = resp.data.result.materialSupplyModes;
        }, err => {
          console.error(err);
        });
        if (this.mode == "EDIT") {
          await activityService.findMatterInfo({materialInfoId: this.id}).then(resp => {
            this.form = resp.data.result;
          }, err => {
            console.error(err);
          });
        }

      },
      /*确定*/
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = {
              materialInfoId: this.id || undefined,
              materialOrderCode: this.form.materialOrderCode || undefined,
              materialCode: this.form.materialCode || undefined,
              materialCkdCode: this.form.materialCkdCode || undefined,
              materialCategory: this.form.materialCategory || undefined,
              materialDescCn: this.form.materialDescCn || undefined,
              materialDescEn: this.form.materialDescEn || undefined,
              materialDescRn: this.form.materialDescRn || undefined,
              materialUnit: this.form.materialUnit || undefined,
              hsNo: this.form.hsNo || undefined,
              supplierCode: this.form.supplierCode || undefined,
              materialRelation: this.form.materialRelation || undefined,
              materialRelationUnit: this.form.materialRelationUnit || undefined,
              materialMinpackageAmt: this.form.materialMinpackageAmt || undefined,
              materialTaxPrice: this.form.materialTaxPrice || undefined,
              materialVatPrice: this.form.materialVatPrice || undefined,
              materialPrice: this.form.materialPrice || undefined,
              materialCurrency: this.form.materialCurrency || undefined,
              materialRate: this.form.materialRate || undefined,
              levelBomCode: this.form.levelBomCode || undefined,
              materialSupplyMode: this.form.materialSupplyMode || undefined,
              factoryCode: this.form.factoryCode || undefined,
              version: this.form.version || undefined,
            }
            if (this.mode == 'EDIT') {  //编辑
              activityService.updateMatter({...data}).then(resp => {
                if (resp.data.code == "201") {
                  this.$notify({message: resp.data.message, type: "success"});
                  this.$emit("success");
                  this.dialogFormVisible = false
                } else {
                  this.$notify({message: resp.data.message, type: "error"});
                }
              })
            } else {
              activityService.addMatter({...data}).then(resp => {  //添加
                if (resp.data.code == "201") {
                  this.$notify({message: resp.data.message, type: "success"});
                  this.$emit("success");
                  this.dialogFormVisible = false
                } else {
                  this.$notify({message: resp.data.message, type: "error"});
                }
              })
            }

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