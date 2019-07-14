<template>
  <span>
    载入中...
  </span>
</template>

<script>

  import passportService from '@/api/passport'

  export default {

    async mounted() {

      let console_user_token = localStorage.console_user_token;
      let redir = this.$route.query.redir;

      if (!console_user_token) {
        console.log('无token');
        this.$router.replace({name:'Login', query:{redir:redir||undefined}});
        return;
      }

      if (redir) {
        if (!this.$router.resolve(redir).route.name) {
          this.$router.replace({name:'NotFound'});
          return;
        }
      }




      try {
        let user_info = await passportService.user_info();
        this.subject.login(user_info);
        console.log('成功恢复登录状态');
        if (this.subject.principal.userStatus == 1 ) {
          // debugger
           this.$notify({message: "用户被禁用", type: 'error'});
           this.$router.replace({name:'Login', query:{redir:redir||undefined}});
           return
        }
        let to = null;

        // this.$router.push({name:"Root"})
        if (redir) {
          let toRoute = this.$router.resolve(redir).route;
          if (toRoute.meta.requiresPermission && !this.subject.hasPermissions(toRoute.meta.requiresPermission)) {
            to = null;
          } else {
            to = {path:redir};
          }

        }

        if (to === null) {

          if (this.subject.principal.level === 'COUNTY' ) {
            to = {name: 'HomeCountyView'}
          // } else if (this.subject.hasPermissions('homepage:biomass:view')) {
          //   to = {name:'Homepage'};
          } else {
            to = {name:'dashboard'};
          }
        }

        this.$nextTick(()=>{
          this.$router.replace(to);
        })

      } catch (e) {

        console.log('恢复登录状态时遇到问题', e);
        this.$router.replace({name:'Login', query:{redir:redir||undefined}});
      }
    },
  }
</script>

<style scoped>

</style>
