<template>
  <el-dialog
             :visible.sync="dialogFormVisible"
             :show-close="false"
             :close-on-click-modal="false"
             append-to-body
             width="600px"
             @closed="$emit('update:activated', false)"
  >

    <template v-slot:title>
      <span  class="el-dialog__title">{{$t('Tips.hello')}}，<b>{{trueName}}</b></span>
    </template>

    <div v-loading="loading" :element-loading-text="loading && selectedRoleId && ($t('Tips.loggingInToTheRole')+','+roles.find(r=>r.roleId===selectedRoleId).roleName+','+$t('Tips.wait'))">
      <template v-if="roles.length>=1">
        <div style="font-weight: bold">
          {{$t('Tips.pleaseSelectTheRolesToLogIn')}}：
        </div>

        <div style="padding: 20px;">
          <el-radio-group v-model="selectedRoleId">
            <el-radio v-for="(role, idx) in roles" :label="role.roleId" :key="role.roleId">
              <i class="fa fa-id-card-o" aria-hidden="true"></i>
              {{role.roleDesc}}
            </el-radio>
          </el-radio-group>
        </div>

      </template>
      <template v-else>
        <div style="font-weight: bold">
          {{$t('Tips.currentUserHasNoAvailableRole')}}
        </div>
      </template>

    </div>
    <span slot="footer">
      <el-button type="primary" size="mini" @click="submit" :disabled="loading || !selectedRoleId">
        <i class="fa fa-check"></i> {{$t('searchFrom.confirm')}}
      </el-button>
      <el-button size="mini" @click="cancel" :disabled="loading">{{$t('searchFrom.cancel')}}</el-button>
    </span>

  </el-dialog>
</template>

<script>

  import passportService from '@/api/passport'
  import { Notification } from 'element-ui'

  export default {

    props: {
      trueName:  { type: String },
      roles:     { type: Array },
      tempToken: { type: String, required: true },
    },

    computed: {
    },
    mounted() {
      if (this.roles.length===1) {
        this.selectedRoleId = this.roles[0].roleId;
        this.submit();
      }
    },

    data() {
      return {
        dialogFormVisible: true,
        loading: false,
        selectedRoleId: null,
      };
    },

    methods: {
      async submit() {
        this.loading = true;
        try {

          let console_user_token = await passportService.select_role({tempToken: this.tempToken, roleId: this.selectedRoleId});
          this.$emit('roleSelected', {console_user_token: console_user_token.token});
          this.dialogFormVisible = false;
        } catch(err) {
          if (err.response){
            let message = err.response.data.msg;
            // Notification.error({message})
            Notification.error({title:"网络异常"})
          }

        } finally {
          this.loading = false;
        }

      },

      cancel() {
        this.$emit('cancelled');
        this.dialogFormVisible = false;
      },

    },
  }
</script>

<style scoped>

</style>
