<template>
  <div>
    <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
               width="700px">
      <el-card>
        <div class="dialogStyle" style="display: flex;flex-direction: column">
          <el-form ref="form" style="flex: 5" class="search-form search-form-normal" :model="form" label-width="100px" :rules="rules" size="mini">
            <el-form-item :label="$t('pageTable.roleName')" prop="roleName">
              <el-input v-model="form.roleName" class="search-form-item-input" style="width: 200px" size="mini"
                        :maxlength="500"></el-input>
              <template slot="error" slot-scope="scope"  >
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.roleExplain')" prop="roleDesc">
              <el-input v-model="form.roleDesc" class="search-form-item-input" style="width: 200px" size="mini"
                        :maxlength="30"></el-input>
              <template slot="error" slot-scope="scope"  >
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
            <el-form-item :label="$t('pageTable.roleAt')" prop="roleAt">
              <el-select v-model="form.roleAt" :placeholder="$t('pageTable.choose')" style="width: 200px" class="">
                <el-option
                  v-for="item in options.roleAt"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
              <template slot="error" slot-scope="scope"  >
                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
              </template>
            </el-form-item>
          </el-form>

        </div>
      </el-card>
      <div class="dialogButton">
        <el-button type="primary" size="mini" @click="submit('form')"><i class="fa fa-check"></i> {{$t('searchFrom.confirm')}}</el-button>
        <el-button size="mini" @click=" cancel">{{$t('searchFrom.cancel')}}</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
  import service from '@/api/users/users'

  export default {
    computed: {
      title: function () {
        let entityName = this.$t('pageTable.role')
        if (this.mode === 'ADD') {
          return this.$t('searchFrom.add') + entityName
        } else if (this.mode === 'EDIT') {
          return this.$t('searchFrom.edit') + entityName
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
            {required: true, message: this.$t('Tips.roleName'), trigger: 'blur'},
            {max: 30, message: this.$t('Tips.lentghGT30'), trigger: 'blur'},
            {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: this.$t('Tips.formatNames'),}
          ],
          roleDesc: [
            {max: 30, message: this.$t('Tips.lentghGT30'), trigger: 'blur'}
          ],
          roleAt: [
            { required: true, message: this.$t('pageTable.choose')+" "+this.$t('pageTable.roleAt'), trigger: 'change' }
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
