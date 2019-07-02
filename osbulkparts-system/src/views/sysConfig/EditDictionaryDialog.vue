<template>
  <div >
    <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      @closed="$emit('update:activated', false);$emit('refresh')"
      width="600px" >
      <el-card>
        <div class="dialogStyle" style="display: flex;flex-direction: column">
        <el-form ref="form" size="mini" :model="form" label-width="100px" :rules="rules" class="search-form search-form-normal">
          <!--     {{id}}-->
          <el-form-item label="所属分类">
            <el-input
              class="search-form-item-input" style="width: 200px"
              v-model="form.tdictTypeEntity.name"
              :disabled="true" size="mini" knx>
            </el-input>
          </el-form-item>

          <el-form-item label="名称" prop="name">
            <el-input  class="search-form-item-input" style="width: 200px" v-model="form.name"   size="mini" :maxlength="50"></el-input>
          </el-form-item>

          <el-form-item label="编号" prop="value">
            <el-input  class="search-form-item-input" style="width: 200px" v-model="form.value" size="mini" :maxlength="30"></el-input>
          </el-form-item>

          <el-form-item label="排序" prop="sortCode">
            <el-input  class="search-form-item-input" style="width: 200px" v-model="form.sortCode" clearable :maxlength="11" size="mini" knx></el-input>
          </el-form-item>

          <el-form-item label="是否有效" >
            <el-checkbox  v-model="form.isEnable"></el-checkbox>
          </el-form-item>


          <el-form-item label="备注">
            <el-input  class="search-form-item-input" style="width: 200px" v-model="form.remark"  clearable type="textarea" :rows="2" placeholder="请输入内容" size="mini" :maxlength="200"></el-input>
          </el-form-item>

        </el-form>
        </div>
      </el-card>


      <div class="dialogButton">
          <el-button type="primary" size="mini" @click="submit('form')">
            <i class="fa fa-check"></i> 确定</el-button>
          <el-button  size="mini" @click="cancel()">取消</el-button>
        </div>

    </el-dialog>
  </div>

</template>

<script>
  import service from '@/api/sysConfig/dictionary'
  export default {
    computed:{
      title:function () {
        let entityName = '数据字典'
        if(this.mode === 'ADD'){return '新增'+entityName}
        else if(this.mode === 'EDIT'){return '编辑'+entityName}
      }
    },

    props:{
      entity  : Object,
      mode : String,
      dictTypeCode:String,
    },
    data() {
      return {
        dialogFormVisible: true,
        //所属分类是否禁用
        flage: false,

        form: {
          tdictTypeEntity:{
            name:""
          },
          //本条数据id
          id:"",
          //字典分类id
          dictTypeCode: "",
          //字典分类名称
          dictTypeName:"",
          subordinate: '',
          //code名称
          name: '',
          //code值
          value: '',
          //排序
          sortCode: '',
          //是否有效
          isEnable: false,
          //是否默认
          isDefault: false,
          //备注
          remark: ''
        },
        classification: [
          {label: 1, value: "政府机构"},
          {label: 2, value: "集团企业"},
          {label: 3, value: "科研机构"},
        ],
        subordinate: [
          {label: 1, value: "根节点"},
          {label: 2, value: "单位A"},
          {label: 3, value: "单位B"},
        ],

        /*表单验证*/
        rules: {

          subordinate: [
            {required: true, message: '请选择所属上级'}
          ],
          name: [
            {required: true, message: '请选择单位名称'}
          ],
          value: [
            {required: true, message: '请选择编号', trigger: 'blur'}
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
          service.getTDictDataInfo({id:this.entity.id}).then(resp=>{
            if (resp.data.code=='200'){
              this.form=resp.data.result;
              this.form.isEnable=this.form.isEnable == 1 ? true:false;
            } else {
              this.$notify({type: 'error', message: resp.data.message});
            }

          })
        }
      },
      /*确定*/
      submit(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.mode === 'ADD'){
              let  addfrom =this.form.isEnable
              if (addfrom==true){
                this.form.isEnable = 1;
              }else {
                this.form.isEnable = 0;
              }
              let  addfrom1 =this.form.isDefault
              if (addfrom1 == true){
                this.form.isDefault = 1
              } else {
                this.form.isDefault = 0
              }
              let add  = this.form
              service.addDict(add).then(resp=>{
                this.$notify({title: '成功',type: 'success', message: resp.data.msg});
                this.dialogFormVisible = false
              })
            }else if (this.mode === 'EDIT'){

              let  isEnable =this.form.isEnable
              if (isEnable==true){
                this.form.isEnable = 1;
              }else {
                this.form.isEnable = 0;
              }
              let  isDefault =this.form.isDefault
              if (isDefault == true){
                this.form.isDefault = 1
              } else {
                this.form.isDefault = 0
              }
              let update  = this.form
              service.updateDict(update).then(resp=>{
                this.$notify({title: '成功',type: 'success', message: resp.data.msg});
                this.dialogFormVisible = false
              });
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

</style>
