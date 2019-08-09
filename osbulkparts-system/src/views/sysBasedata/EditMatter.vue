<template>
  <div>
    <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
               width="750px">
      <el-card>
        <div class="dialogStyle" style="display: flex;flex-direction: column">
          <el-form :disabled="form.isLocked==1" class="search-form search-form-normal" label-width="110px" ref="form"
                   style="flex: 5" :model="form" size="mini" :rules="rules">
            <el-form-item :label="$t('pageTable.MatterProductNO')" prop="materialOrderCode">
              <el-input v-model="form.materialOrderCode" class="search-form-item-input" style="width: 160px" size="mini"
                        autocomplete="new-password" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterProductDescription')" prop="materialDescCn">
              <el-input type="textarea" v-model="form.materialOrderCodeDesc" class="search-form-item-input"
                        style="width: 160px" size="mini"
                        clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterMaterialSpecificNumber')" prop="materialCode">
              <el-input v-model="form.materialCode" class="search-form-item-input" style="width: 160px" size="mini"
                         autocomplete="new-password" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
<!--            <el-form-item label="物料CKD号" prop="materialCkdCode">-->
<!--              <el-input v-model="form.materialCkdCode" class="search-form-item-input" style="width:160px" size="mini"-->
<!--                         clearable></el-input>-->
<!--              <template slot="error" slot-scope="scope">-->
<!--                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>-->
<!--              </template>-->
<!--            </el-form-item>-->
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
            <el-form-item :label="$t('pageTable.MatterHSCustomsNumber')" prop="hsNo">
              <el-input v-model="form.hsNo" class="search-form-item-input" style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterSupplierCode')" prop="supplierCode">
              <el-input v-model="form.supplierCode" class="search-form-item-input" style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
          <el-form-item :label="$t('pageTable.MatterSpecificConsumption')"  prop="materialAmount">
              <el-input v-model="form.materialAmount" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                  <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
          </el-form-item>
            <el-form-item :label="$t('pageTable.MatterCompany')" prop="materialUnit">
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
            <el-form-item :label="$t('pageTable.MatterConversionRelationship')" prop="materialRelation">
              <el-input v-model="form.materialRelation" class="search-form-item-input" style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterConvertedUnit')" prop="materialRelationUnit">
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
<!--            <el-form-item label="最小包装数量" prop="materialMinpackageAmt">-->
<!--              <el-input v-model="form.materialMinpackageAmt" class="search-form-item-input" style="width: 160px"-->
<!--                       :maxlength="30" size="mini"  clearable></el-input>-->
<!--              <template slot="error" slot-scope="scope">-->
<!--                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>-->
<!--              </template>-->
<!--            </el-form-item>-->
            <el-form-item :label="$t('pageTable.MatterUnitPriceWithTax')" prop="materialTaxPrice">
              <el-input v-model="form.materialTaxPrice" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterUntaxedUnitPrice')" prop="materialVatPrice">
              <el-input v-model="form.materialVatPrice" class="search-form-item-input" style="width: 160px"  size="mini"
                        :maxlength="18"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterTaxRate')" prop="tax">
              <el-input v-model="form.tax" class="search-form-item-input" style="width: 160px"  size="mini"
                        :maxlength="18"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterLossRate')" prop="tax">
              <el-input v-model="form.materialLossRate" class="search-form-item-input" style="width: 160px"  size="mini"
                        :maxlength="18"   clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
<!--            <el-form-item label="单价" prop="materialPrice">-->
<!--              <el-input v-model="form.materialPrice" class="search-form-item-input" style="width: 160px" size="mini"-->
<!--                        :maxlength="18"  clearable></el-input>-->
<!--              <template slot="error" slot-scope="scope">-->
<!--                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>-->
<!--              </template>-->
<!--            </el-form-item>-->
            <el-form-item :label="$t('pageTable.MatterAgencyRate')" prop="materialRate">
              <el-input v-model="form.materialRate" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="18" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterCurrency')" prop="materialCurrency">
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
            <el-form-item :label="$t('pageTable.MatterFactoryCode')" prop="factoryCode">
              <el-input v-model="form.factoryCode" class="search-form-item-input" style="width: 160px" size="mini"
                         clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterLong')" prop="length">
              <el-input v-model="form.length" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="18" clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterWide')" prop="width">
              <el-input v-model="form.width" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterHigh')" prop="height">
              <el-input v-model="form.height" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="18"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.MatterRemark')" prop="userDefined1">
              <el-input type="textarea" v-model="form.userDefined1" class="search-form-item-input" style="width: 160px" size="mini"
                        :maxlength="100"  clearable></el-input>
              <template slot="error" slot-scope="scope">
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>

            <el-form-item :label="$t('pageTable.portrait')" prop="avatar_attachment" >
              <mi-avatar-attachment-editor
              ref="avatarUploader" v-model="form.avatar_attachment" :dao="avatarDao" :entity="entity"/>
            </el-form-item>
<!--            <el-form-item label="分级BOM编码" prop="levelBomCode">-->
<!--              <el-input v-model="form.levelBomCode" class="search-form-item-input" style="width: 160px" size="mini"-->
<!--                        :maxlength="10"  clearable></el-input>-->
<!--              <template slot="error" slot-scope="scope">-->
<!--                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>-->
<!--              </template>-->
<!--            </el-form-item>-->
<!--            <el-form-item label="物料供货模式分类标识" prop="materialSupplyMode">-->
<!--              <el-select v-model="form.materialSupplyMode" style="width: 160px" size="mini" clearable>-->
<!--                <el-option value=""></el-option>-->
<!--                <el-option-->
<!--                  size="mini"-->
<!--                  v-for="item in materialSupplyModes"-->
<!--                  :key="item.value"-->
<!--                  :label="item.name"-->
<!--                  :value="item.value">-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--              <template slot="error" slot-scope="scope">-->
<!--                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>-->
<!--              </template>-->
<!--            </el-form-item>-->
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
  import activityService from '@/api/basedata/matter.js'
  import ui_config from '@/config/ui_config'
  import MiAvatarAttachmentEditor from "@/components/MiAvatarAttachmentEditor"

  export default {
    name: "EditMatter",
    props: {
      id: {},
      mode: "",
    },
    components: {
      MiAvatarAttachmentEditor
    },
    computed: {
      title: function () {
        let entityName = this.$t('searchFrom.matter')
        if(this.mode === 'ADD'){return this.$t('searchFrom.add')+entityName}
        else if(this.mode === 'EDIT'){return this.$t('searchFrom.edit')+entityName}
      },
      entity:function () {
        return {
          materialCode:this.form.materialCode,
          materialVatPrice:this.form.materialVatPrice,
          supplierCode:this.form.supplierCode,
        }
      },
    },
    data() {
      return {
        avatarDao: activityService.avatarDao,

        dialogFormVisible: true,
        search_keys: {},
        currencys: [],
        materialUnits: [],
        materialRelationUnits: [],
        materialCategorys: [],
        materialSupplyModes: [],
        form: {
          materialOrderCode:'',
          materialOrderCodeDesc:'',
          materialCode: '',
          materialCkdCode: '',
          materialCategory: '',
          materialDescCn: '',
          materialDescEn: '',
          materialDescRn: '',
          materialUnit: '',
          materialAmount:'',
          hsNo: '',
          supplierCode: '',
          materialRelation: '',
          materialRelationUnit: '',
          materialMinpackageAmt: '',
          materialTaxPrice: '',
          materialVatPrice: '',
          tax:'',
          materialPrice: '',
          materialLossRate:'',
          materialCurrency: '',
          materialRate: '',
          levelBomCode: '',
          materialSupplyMode: '',
          factoryCode: '',
          length:"",
          width:"",
          height:"",
          userDefined1:"",
          dataRoleAt: "",
          isLocked:"",
        },
        /**表单的验证*/
        rules: {
          materialOrderCode: [
            {required: true, message: this.$t('Tips.checkMaterialOrderCode'), trigger: 'blur'},
            {max: 20, message: this.$t('Tips.lentghGT20'), trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message:  this.$t('Tips.formatNames'),}
          ],
          materialCode: [
            {required: true, message: this.$t('Tips.checkMaterialCode'), trigger: 'blur'},
            {max: 20, message: this.$t('Tips.lentghGT20'), trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          ],
          // materialCkdCode: [
          //   {required: true, message: '请填写物料CKD号', trigger: 'blur'},
          //   {max: 20, message: '长度不超过20个字符', trigger: 'blur'},
          //   {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          // ],
          materialCategory: [
            {required: true, message: this.$t('Tips.checkMaterialCategory'), trigger: 'change'}
          ],
          materialDescCn: [
            {required: false, message:  this.$t('Tips.checkMaterialDescCn'), trigger: 'blur'},
            {max: 200, message:  this.$t('Tips.lentghGT200'), trigger: 'blur'},
          ],
          supplierCode: [
            {required: true, message: this.$t('Tips.checkSupplierCode'), trigger: 'blur'},
            {max: 50, message:  this.$t('Tips.lentghGT50'), trigger: 'blur'},
          ],
          materialAmount: [
              {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,3}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          materialUnit: [
            {required: true, message: this.$t('Tips.checkMaterialUnit'), trigger: 'change'}
          ],
          materialRelation: [
            {max: 5, message:  this.$t('Tips.lentghGT5'), trigger: 'blur'},
          ],
          materialRelationUnit: [
            // {required: true, message: '请选择单位', trigger: 'change'}
          ],
          materialMinpackageAmt: [
          ],
          materialTaxPrice: [
            {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,6}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          // materialVatPrice: [
          //   {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          // ],
          tax: [
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,6}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          materialLossRate: [
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,6}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          materialPrice: [
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,6}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          materialRate: [
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,6}$/, trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          materialCurrency: [
            // {required: true, message: '请选择单位', trigger: 'change'}
          ],
          factoryCode: [
            {max: 32, message: this.$t('Tips.lentghGT32'), trigger: 'blur'},
          ],
          length:[
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,6}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          wide:[
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,6}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          height:[
            {pattern: /^([0-9]*)+\.{0,1}[0-9]{1,6}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
          ],
          levelBomCode: [
            {pattern: /^[0-9]*$/ , trigger: 'blur', message:  this.$t('Tips.numberCheck'),}
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

        this.$refs[formName].validate( (valid) => {
          if (valid) {
            let data = {
              materialInfoId: this.id || undefined,
              materialOrderCode: this.form.materialOrderCode || undefined,
              materialOrderCodeDesc: this.form.materialOrderCodeDesc || undefined,
              materialCode: this.form.materialCode || undefined,
              materialCkdCode: this.form.materialCkdCode || undefined,
              materialCategory: this.form.materialCategory || undefined,
              materialDescCn: this.form.materialDescCn || undefined,
              materialDescEn: this.form.materialDescEn || undefined,
              materialDescRn: this.form.materialDescRn || undefined,
              materialUnit: this.form.materialUnit || undefined,
              materialAmount: this.form.materialAmount || undefined,
              hsNo: this.form.hsNo || undefined,
              supplierCode: this.form.supplierCode || undefined,
              materialRelation: this.form.materialRelation || undefined,
              materialRelationUnit: this.form.materialRelationUnit || undefined,
              materialMinpackageAmt: this.form.materialMinpackageAmt || undefined,
              materialTaxPrice: this.form.materialTaxPrice || undefined,
              materialVatPrice: this.form.materialVatPrice || undefined,
              tax: this.form.tax || undefined,
              materialLossRate: this.form.materialLossRate || undefined,
              materialPrice: this.form.materialPrice || undefined,
              materialCurrency: this.form.materialCurrency || undefined,
              materialRate: this.form.materialRate || undefined,
              levelBomCode: this.form.levelBomCode || undefined,
              materialSupplyMode: this.form.materialSupplyMode || undefined,
              factoryCode: this.form.factoryCode || undefined,
              length:this.form.length || undefined,
              width:this.form.width  || undefined,
              height:this.form.height || undefined,
              userDefined1:this.form.userDefined1 || undefined,
              version: this.form.version || undefined,
              dataRoleAt: this.form.dataRoleAt || undefined,
              isLocked: this.form.isLocked,
            };

            if (this.mode == 'EDIT') {  //编辑
                // debugger
               activityService.updateMatter({...data}).then(resp => {
                if (resp.data.code == "201") {
                  this.$notify({message: resp.data.message, type: "success"});
                  this.$refs.avatarUploader.upload().then(resp=>{
                    if (resp.data.code == "200"){
                      this.$notify({message: resp.data.message, type: "success"});
                      this.$emit("success");
                      this.dialogFormVisible = false;
                    }else {
                      this.$notify({message: resp.data.message, type: "error"});
                    }
                  });

                } else {
                  this.$notify({message: resp.data.message, type: "error"});
                }
              })

            } else {
              activityService.addMatter({...data}).then(resp => {  //添加
                if (resp.data.code == "201") {
                  this.$notify({message: resp.data.message, type: "success"});
                  // this.$emit("success");
                  // this.dialogFormVisible = false
                  this.$refs.avatarUploader.upload().then(resp=>{
                    if (resp.data.code == "200"){
                      this.$notify({message: resp.data.message, type: "success"});
                      this.$emit("success");
                      this.dialogFormVisible = false;
                    }else {
                      this.$notify({message: resp.data.message, type: "error"});
                    }
                  });
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