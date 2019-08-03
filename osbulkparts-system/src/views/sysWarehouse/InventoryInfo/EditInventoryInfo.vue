<template>
  <div>

    <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
               width="750px">
      <el-card>
        <div class="dialogStyle" style="display: flex;flex-direction: column">
          <!--{{id}}-->
          <el-form  class="search-form search-form-normal" label-width="110px" ref="form"
                   style="flex: 5" :model="form" size="mini" :rules="rules">
            <el-form-item :label="$t('pageTable.MatterMaterialSpecificNumber')" prop="materialCode">
              <el-input v-model="form.materialCode" class="search-form-item-input" style="width: 160px" size="mini"
                         autocomplete="new-password" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>

            <el-form-item :label="$t('pageTable.MatterChannel')" prop="materialCategory">
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
            <el-form-item :label="$t('pageTable.MatterChineseDescriptionOfMaterials')" prop="materialDescCn">
              <el-input type="textarea" v-model="form.materialDescCn" class="search-form-item-input"
                        style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterEnglishDescriptionOfMaterials')" prop="materialDescEn">
              <el-input type="textarea" v-model="form.materialDescEn" class="search-form-item-input"
                        style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterRussianDescriptionOfMaterials')" prop="materialDescRn">
              <el-input type="textarea" v-model="form.materialDescRn" class="search-form-item-input"
                        style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.inventoryNumber')" prop="stockAmount">
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
          class="fa fa-check"></i> {{$t('searchFrom.confirm')}}
        </el-button>
        <el-button size="mini" @click=" cancel">{{$t('searchFrom.cancel')}}</el-button>
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
        let entityName = this.$t('searchFrom.inventory')
        if(this.mode === 'ADD'){return this.$t('searchFrom.add')+entityName}
        else if(this.mode === 'EDIT'){return this.$t('searchFrom.edit')+entityName}
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
            {required: true, message: this.$t('Tips.checkMaterialCode'), trigger: 'blur'},
            {max: 20, message:  this.$t('Tips.lentghGT20'), trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: this.$t('Tips.formatNames'),}
          ],

          materialCategory: [
            {required: true, message: this.$t('Tips.checkMaterialCategory'), trigger: 'change'}
          ],
          materialDescCn: [
            {required: true, message:this.$t('Tips.checkMaterialDescCn'), trigger: 'blur'},
            {max: 200, message:  this.$t('Tips.lentghGT200'), trigger: 'blur'},
          ],
          materialDescEn: [
            {required: true, message: this.$t('Tips.checkMaterialDescEn'), trigger: 'blur'},
            {max: 200, message:  this.$t('Tips.lentghGT200'), trigger: 'blur'},
          ],
          materialDescRn: [
            {required: true, message: this.$t('Tips.checkMaterialDescRn'), trigger: 'blur'},
            {max: 200, message:  this.$t('Tips.lentghGT200'), trigger: 'blur'},
          ],
          stockAmount: [

            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,3}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
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
          await activityService.findStockInfo({id: this.id}).then(resp => {
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
              id: this.id || undefined,
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