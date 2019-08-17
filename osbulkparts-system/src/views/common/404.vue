<template>
  <div>
    <h1>404</h1>
    <span>{{$t('Tips.thisPageCantBeFound')}}。{{$t('Tips.try')}}<router-link :to="{name:'Login', query:{redir:$route.query.redir}}">{{$t('Tips.login')}}</router-link>？</span>

  </div>
</template>

<script>
  export default {
  }
</script>

<style scoped>

</style>
