<template>
  <div>
    <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
               width="600px">
      <el-card>
        <div class="dialogStyle" style="display: flex;flex-direction: column">
          <el-form  class="search-form search-form-normal" label-width="110px" ref="form"
                   style="flex: 5" :model="form" size="mini" :rules="rules">

            <el-form-item label="物料号" prop="materialCode">
              <el-input v-model="form.materialCode" class="search-form-item-input" style="width: 160px" size="mini"
                         autocomplete="new-password" clearable></el-input>
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
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料英文描述" prop="materialDescEn">
              <el-input type="textarea" v-model="form.materialDescEn" class="search-form-item-input"
                        style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="物料俄文描述" prop="materialDescRn">
              <el-input type="textarea" v-model="form.materialDescRn" class="search-form-item-input"
                        style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item label="库存数量" prop="stockAmount">
              <el-input v-model="form.stockAmount" class="search-form-item-input" style="width: 160px" size="mini"
                       :maxlength="18"  clearable></el-input>
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
  import activityService from '@/api/warehouse/inventoryInfo'
  import ui_config from '@/config/ui_config'

  export default {
    props: {
      id: {},
      mode: "",
    },
    computed: {
      title: function () {
        let entityName = '库存信息'
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
        materialCategorys: [],
        form: {
          materialCode: '',
          materialCategory: '',
          materialDescCn: '',
          materialDescEn: '',
          materialDescRn: '',
          stockAmount:"",

        },
        /**表单的验证*/
        rules: {

          materialCode: [
            {required: true, message: '请填写物料号', trigger: 'blur'},
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
          stockAmount: [

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
        await activityService.initData().then(resp => {
          this.materialCategorys = resp.data.result.materialCategorys;
        }, err => {
          console.error(err);
        });
        if (this.mode == "EDIT") {
          await activityService.findStockInfo({materialInfoId: this.id}).then(resp => {
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
              materialCode: this.form.materialCode || undefined,
              materialCategory: this.form.materialCategory || undefined,
              materialDescCn: this.form.materialDescCn || undefined,
              materialDescEn: this.form.materialDescEn || undefined,
              materialDescRn: this.form.materialDescRn || undefined,
              stockAmount: this.form.stockAmount || undefined,
              version: this.form.version || undefined,
              dataRoleAt: this.form.dataRoleAt || undefined,
            }
            if (this.mode == 'EDIT') {  //编辑
              activityService.updateStockInfo({...data}).then(resp => {
                if (resp.data.code == "201") {
                  this.$notify({message: resp.data.message, type: "success"});
                  this.$emit("success");
                  this.dialogFormVisible = false
                } else {
                  this.$notify({message: resp.data.message, type: "error"});
                }
              })
            } else {
              activityService.addStockInfo({...data}).then(resp => {  //添加
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