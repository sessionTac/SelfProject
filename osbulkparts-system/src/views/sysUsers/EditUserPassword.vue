<template>
  <div>
    <el-dialog :title="title" append-to-body :visible.sync="dialogFormVisible"
               @closed="$emit('update:activated', false)" width="600px">

      <el-form ref="form" size="mini" class="" :rules="rules" :model="form"
               label-width="200px" style="width:400px">

        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="form.oldPassword" class="search-form-item-input" placeholder="请输入原密码" type="password"
                    autocomplete="off" clearable size="mini"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newpassword">
          <el-input v-model="form.newpassword" class="search-form-item-input" placeholder="请输入新密码" type="password"
                    autocomplete="off" clearable size="mini"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="surepassword">
          <el-input v-model="form.surepassword" class="search-form-item-input" placeholder="请再次输入新密码" type="password"
                    autocomplete="off" clearable size="mini"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="submit('form')">
          <i class="fa fa-check"></i> 确定</el-button>
        <el-button size="mini" @click="cancel">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import activityService from '@/api/users/users'

  export default {
    computed: {
      title: function () {
        let entityName = "密码修改"
        return this.userName + entityName
      }
    },
    props: {
      id: {},
      userName: String
    },
    data() {
      var validateNewPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'));
        } else {
          if (this.form.surepassword !== '') {
            this.$refs.form.validateField('surepassword');
          }
          callback();
        }
      };
      var validateSurePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入新密码'));
        } else if (value !== this.form.newpassword) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };

      return {
        dialogFormVisible: true,
        form: {
          oldPassword: '',
          newpassword: '',
          surepassword: '',
        },
        rules: {
          oldPassword: [
            {required: true, message: '请输入旧密码'}
          ],
          newpassword: [
            {validator: validateNewPass, trigger: 'blur'}
          ],
          surepassword: [
            {validator: validateSurePass, trigger: 'blur'}
          ]

        },
      };

    },
    mounted() {
      console.log("userName:", this.userName, "id:", this.id)
    },
    methods: {
      /*确定*/
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = {
              userInfoEntity: {
                userId: this.id || undefined,
                password: this.form.surepassword || undefined,
              },
              inputPassword: this.form.oldPassword || undefined,
            }
            activityService.changePass({...data}).then(resp => {
              if (resp.data.code=="201"){
                this.$notify({message: resp.data.message, type: "success"});
                this.dialogFormVisible = false
              } else {
                this.$notify({message: resp.data.message, type: "error"});
              }
            })

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      cancel() {
        this.dialogFormVisible = false
      }
    }
  }
</script>

<style scoped>
  >>> .el-dialog__body {
    padding: 0px 0px;
    color: #606266;
    font-size: 14px;
  }

  >>> .el-dialog__footer {
    /*margin-top: -30px;*/
    text-align: center;
  }
</style>
