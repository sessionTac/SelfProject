<template>
  <div>
    <el-dialog title='个人信息' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="600px">
      <div class="dialogStyle" style="display: flex;flex-direction: column">
        <!--<el-row>-->
        <!--<el-col :span="4">-->
        <!--<el-form  size="mini">-->
        <!--<el-form-item label="头像" prop="avatar_attachment" label-width="50px">-->
        <!--<mi-avatar-attachment-editor-->
        <!--ref="avatarUploader" v-model="form.avatar_attachment" :dao="avatarDao" />-->
        <!--</el-form-item>-->
        <!--</el-form>-->
        <!--</el-col>-->
        <!--<el-col :span="20">-->
        <el-form ref="form" style="flex: 5" :model="form" class="search-form search-form-normal" label-width="100px"  :rules="rules"  size="mini">

          <el-form-item label="用户名" prop="userName">
            <el-input v-model="form.userName" class="search-form-item-input" style="width: 200px" size="mini"
                      :maxlength="500" disabled></el-input>
            <template slot="error" slot-scope="scope"  >
              <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
            </template>
          </el-form-item>
          <el-form-item label="真实姓名" prop="userRealName">
            <el-input v-model="form.userRealName" class="search-form-item-input" style="width: 200px" size="mini"
                      :maxlength="30"></el-input>
            <template slot="error" slot-scope="scope"  >
              <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
            </template>
          </el-form-item>
          <el-form-item label="电话号码" prop="userPhone">
            <el-input v-model="form.userPhone" class="search-form-item-input" style="width: 200px" size="mini"
                      :maxlength="11"></el-input>
            <template slot="error" slot-scope="scope"  >
              <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
            </template>
          </el-form-item>
          <el-form-item label="邮箱" prop="userMail">
            <el-input v-model="form.userMail" class="search-form-item-input" style="width: 200px" size="mini"
                      :maxlength="50"></el-input>
            <template slot="error" slot-scope="scope"  >
              <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
            </template>
          </el-form-item>
        </el-form>
        <!--</el-col>-->
        <!--</el-row>-->


        <div class="dialogButton">
          <el-button type="primary" size="mini" @click="submit('form')"><i class="fa fa-check"></i> 确定</el-button>
          <el-button size="mini" @click=" cancel">取消</el-button>
          <el-button size="mini" @click="changePassword">密码重置</el-button>
        </div>
      </div>
    </el-dialog>
    <EditPasswordDialog v-bind.sync="edit_pass" v-if="edit_pass.activated"></EditPasswordDialog>

  </div>
</template>

<script>
  import service from '@/api/users/users'
  import MiAvatarAttachmentEditor from "@/components/MiAvatarAttachmentEditor"
  import EditPasswordDialog from '@/views/sysUsers/EditUserPassword'

  export default {
    name: "PersonCenter",
    components: {
      MiAvatarAttachmentEditor, EditPasswordDialog
    },
    props: {
      id: {}
    },
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.form.checkPass !== '') {
            this.$refs.form.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        avatarDao: {
          uploader: (file) => {
            let form = new FormData();
            form.append('file', file);
            // return axios.post('~/passport/avatar', form).then(({data}) => data);
            return "";
          },
          srcGetter: attachment => `${API_HOME}/passport/avatar/${attachment.file_id}`,
          // thumbSrcGetter: attachment => `${API_HOME}/passport/avatar/${attachment.thumb_file_id}`,
        },
        disabled: false,
        edit_pass: {},
        regions: [],
        dialogFormVisible: true,
        search_keys: {},
        form: {
          avatar_attachment: null,
          userName: '',
          trueName: '',
          password: '',
          checkPass: '',
          userPhone: '',
          userEmail: '',
          memo: '',
          transferorEntity: null,
          orgEntity: null,
          region: null,
        },
        rules: {
          userName: [
            {required: true, message: '请填写用户名', trigger: 'blur'},
            {max: 30, message: '长度不超过30个字符', trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          ],
          userRealName: [
            {required: true, message: '请填写真实姓名', trigger: 'blur'},
            {max: 30, message: '长度不超过30个字符', trigger: 'blur'}
          ],
          password: [
            {validator: validatePass, trigger: 'blur'},
          ],
          checkPass: [
            {validator: validatePass2, trigger: 'blur'},
          ],
          userPhone: [
            {required: false, pattern: /^1(3|4|5|7|8)\d{9}$/, trigger: 'blur', message: '请输入正确的手机号',}
          ],
          userMail: [
            {required: false, pattern: /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/, message: '请输入正确的邮箱', trigger: ['blur']}
          ]
        },
      }
    },
    mounted() {
      this.init()
    },
    methods: {
      async init() {
        service.selectUserById(this.id).then(resp => {
          this.form = resp.data.result;
          this.form.userType = this.form.userType + "";
          this.form.userStatus = this.form.userStatus + "";
        })
      },
      cancel() {
        this.dialogFormVisible = false
      },
      /*确定*/
      submit(formName) {
        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            let data = {
              userId: this.id || undefined,
              userRealName: this.form.userRealName || undefined,
              userMail: this.form.userMail || undefined,
              userPhone: this.form.userPhone || undefined,
              version: this.form.version || undefined,
            };
            service.updateUser({...data}).then(resp => {
              if (resp.data.code == "201") {
                this.$notify({message: resp.data.message, type: 'success'});
                this.dialogFormVisible = false
              } else {
                this.$notify({message: resp.data.message, type: 'error'});
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      //修改密码
      changePassword() {
        this.edit_pass = {activated: true, userName: this.form.userName, id: this.id}
      }
    }
  }
</script>

<style scoped>

</style>