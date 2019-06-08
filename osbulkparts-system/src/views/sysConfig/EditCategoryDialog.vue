<template>
  <div >
    <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      @closed="$emit('update:activated', false);$emit('refresh')"
      width="35%">

    <el-form ref="form" :model="form" size="mini" label-width="200px" style="width:400px" :rules="rules" class="search-form search-form-normal">

      <el-form-item label="父级"  >
        <el-select  :disabled="true" v-model="form.parentId"   size="mini" >
          <el-option v-for="item in parentUuid" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name"  clearable knx size="mini" :maxlength="50"></el-input>
      </el-form-item>

      <el-form-item label="编号" prop="code">
        <el-input v-model="form.code" clearable knx size="mini" :maxlength="30"></el-input>
      </el-form-item>

      <el-form-item label="排序" prop="sortCode">
        <el-input v-model="form.sortCode" clearable knx size="mini" :maxlength="11"></el-input>
      </el-form-item>

      <el-form-item label="是否有效" prop="sort">
        <el-checkbox v-model="form.isEnable"></el-checkbox>
      </el-form-item>

      <el-form-item label="备注">
        <el-input v-model="form.remark"  type="textarea" :rows="2" placeholder="请输入内容" size="mini" :maxlength="200"></el-input>
      </el-form-item>

    </el-form>
      <span slot="footer">
          <el-button type="primary" size="mini" @click="submit('form')">
            <i class="fa fa-check"></i> 确定</el-button>
          <el-button  size="mini" @click="cancel()">取消</el-button>
        </span>
    </el-dialog>
  </div>

</template>

<script>
  import service from '@/api/sysConfig/dictionary'
  export default {
    computed:{
      title:function () {
        let entityName = '字典分类'
        if(this.mode === 'ADD'){return '新增'+entityName}
        else if(this.mode === 'EDIT'){return '编辑'+entityName}
      }
    },
    props:{
      entity  : Object,
      mode : String
    },
    data() {
      return {
        dialogFormVisible: true,
        form: {
          //id
          id:0,
          //code名称
          name: '',
          //code值
          code: '',
          //排序
          sortCode: '',
          //是否有效
          isEnable: false,
          //备注
          remark: ''
        },
        parentUuid: [
          {value: "0", label: "根节点"},
          {value: "1", label: "通用分类"},
          {value: "2", label: "婚姻"},
          {value: "2", label: "性别"},
          {value: "2", label: "民族"},
          {value: "2", label: "学历"},
        ],
        subordinate: [
          {label: 1, value: "根节点"},
          {label: 2, value: "单位A"},
          {label: 3, value: "单位B"},
        ],

        /*表单验证*/
        rules: {
          classification: [
            {required: true, message: '请选择所属分类'},
          ],
          subordinate: [
            {required: true, message: '请选择数据字典所属上级'}
          ],
          name: [
            {required: true, message: '请填写数据字典名称'}
          ],
          code: [
            {required: true, message: '请填写数据字典编号', trigger: 'blur'}
          ],
        }
      }
    },

    mounted(){
      this.init()
    },
    methods: {
      init() {
        if (this.mode === 'EDIT') {
          service.findTypeDetails({dictTypeId:this.entity.dictTypeId}).then(resp=>{
            this.form=resp.data.result;
            if (this.form.isEnable==='0'){
              this.form.isEnable =false
            } else {
              this.form.isEnable =true
            }
          },error => {

          });
        }
      },
      /*确定*/
      submit(formName){
        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            if (this.mode === 'ADD'){
              let  addfrom =this.form.isEnable
              if (addfrom==true){
                this.form.isEnable = 1;
              }else {
                this.form.isEnable = 0;
              }
              let add  = this.form;

              let checkName = await service.checkNameRepeat({...add,checkFlag:"add"});
              let checkCode = await service.checkCodeRepeat({...add,checkFlag:"add"});
              if (checkName.data.code== "201" && checkCode.data.code=='201') {
                service.addDictType({...add}).then(resp=>{
                  if(resp.data.code == "201"){
                    this.$notify({type: 'success', message: resp.data.message});
                    this.dialogFormVisible = false
                  }else {
                    this.$notify({message: resp.data.message, type: 'error'});
                  }
                })
              }else {
                if (checkName.data.code== "201") {
                  this.$notify({message: checkCode.data.message, type: 'error'});
                }else {
                  this.$notify({message: checkName.data.message, type: 'error'});
                }

              }
            }else if (this.mode === 'EDIT'){

              let  isEnable =this.form.isEnable
              if (isEnable==true){
                this.form.isEnable = 1;
              }else {
                this.form.isEnable = 0;
              }
              let update  = this.form;
              let checkName = await service.checkNameRepeat({...update,checkFlag:"edit"});
              let checkCode = await service.checkCodeRepeat({...update,checkFlag:"edit"});
              if (checkName.data.code== "201" && checkCode.data.code=='201') {
                service.updateDictType({...update}).then(resp=>{
                  if(resp.data.code == "201"){
                    this.$notify({type: 'success', message: resp.data.message});
                    this.dialogFormVisible = false
                  }else {
                    this.$notify({message: resp.data.message, type: 'error'});
                  }
                });
              }else {
                if (checkName.data.code== "201") {
                  this.$notify({message: checkCode.data.message, type: 'error'});
                }else {
                  this.$notify({message: checkName.data.message, type: 'error'});
                }
              }
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      cancel(){
        this.dialogFormVisible = false
      }
    }
  }
</script>

<style scoped>
  >>>.el-dialog__body {
    padding: 0px 0px;
    color: #606266;
    font-size: 14px;
  }

  >>> .el-dialog__footer {
    /*margin-top: -30px;*/
    text-align: center;
  }
</style>
