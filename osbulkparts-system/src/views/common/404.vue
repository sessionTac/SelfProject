<template>
  <div>
    <h1>404</h1>
    <span>这个页面找不到了。试试<router-link :to="{name:'Login', query:{redir:$route.query.redir}}">登录</router-link>？</span>

  </div>
</template>

<script>
  export default {
  }
</script>

<style scoped>

</style>
