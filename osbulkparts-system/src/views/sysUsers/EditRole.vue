<template>
  <div>
    <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
               width="600px">
      <el-card>
        <div class="dialogStyle" style="display: flex;flex-direction: column">
          <el-form ref="form" style="flex: 5" class="search-form search-form-normal" :model="form" label-width="200px" :rules="rules" size="mini">
            <el-form-item label="角色名" prop="roleName">
              <el-input v-model="form.roleName" class="search-form-item-input" style="width: 200px" size="mini"
                        :maxlength="500"></el-input>
            </el-form-item>
            <el-form-item label="角色说明" prop="roleDesc">
              <el-input v-model="form.roleDesc" class="search-form-item-input" style="width: 200px" size="mini"
                        :maxlength="30"></el-input>
            </el-form-item>
            <el-form-item label="角色所属">
              <el-select v-model="form.roleAt" placeholder="请选择" class="">
                <el-option
                  v-for="item in options.roleAt"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>

        </div>
      </el-card>
      <div class="dialogButton">
        <el-button type="primary" size="mini" @click="submit('form')"><i class="fa fa-check"></i> 确定</el-button>
        <el-button size="mini" @click=" cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
  import service from '@/api/users/users'

  export default {
    computed: {
      title: function () {
        let entityName = '角色'
        if (this.mode === 'ADD') {
          return '新增' + entityName
        } else if (this.mode === 'EDIT') {
          return '编辑' + entityName
        }
      }
    },
    props: {
      roleId: {},
      mode: String
    },
    data() {
      return {
        disabled: false,
        dialogFormVisible: true,
        options: {},
        form: {
          roleName: '',
          roleDesc: '',
          roleAt: '',
        },
        /**表单的验证*/
        rules: {
          roleName: [
            {required: true, message: '请填写用户名', trigger: 'blur'},
            {max: 30, message: '长度不超过30个字符', trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
          ],
          userRealName: [
            {required: true, message: '请填写真实姓名', trigger: 'blur'},
            {max: 30, message: '长度不超过30个字符', trigger: 'blur'}
          ],
        },
      }
    },

    mounted() {
      this.init()
    },
    methods: {

      async init() {
        await service.findRoleOptions().then(resp => {
          this.options = resp.data.result
        });
        if (this.mode == 'EDIT') {
          service.selectRoleById(this.roleId).then(resp => {
            this.form = resp.data.result;
          })
        }
      },
      /*确定*/
      submit(formName) {
        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            let data = {
              roleId: this.roleId || undefined,
              roleName: this.form.roleName || undefined,
              roleDesc: this.form.roleDesc || undefined,
              roleAt: this.form.roleAt || undefined,
              version: this.form.version || undefined,
            }
            if (this.mode == 'EDIT') {  //编辑
              let check = await service.checkRoleInfo({...data, checkFlag: "edit"});
              if (check.data.code == "201") {
                service.updateRole({...data}).then(resp => {
                  if (resp.data.code == "201") {
                    this.$notify({message: resp.data.message, type: 'success'});
                    this.$emit("success");
                    this.dialogFormVisible = false
                  } else {
                    this.$notify({message: resp.data.message, type: 'error'});
                  }
                })
              } else {
                this.$notify({message: check.data.message, type: 'error'});
              }

            } else {
              let check = await service.checkRoleInfo({...data, checkFlag: "add"});
              if (check.data.code == "201") {
                service.addRole({...data}).then(resp => {  //添加
                  if (resp.data.code == "201") {
                    this.$notify({message: resp.data.message, type: 'success'});
                    this.$emit("success");
                    this.dialogFormVisible = false
                  } else {
                    this.$notify({message: resp.data.message, type: 'error'});
                  }
                })
              } else {
                this.$notify({message: check.data.message, type: 'error'});
              }

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
