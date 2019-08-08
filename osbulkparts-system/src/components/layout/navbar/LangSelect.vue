<template>
  <el-dropdown trigger="click" class="international" @command="handleSetLanguage">
    <div>
      <i class="iconfont icon-language svg-icon" />
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item :disabled="language=='zh'" command="zh">
        中文
      </el-dropdown-item>
      <el-dropdown-item :disabled="language=='en'" command="en">
        English
      </el-dropdown-item>
      <el-dropdown-item :disabled="language=='ru'" command="ru">
        русский язык
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>

  import { mapGetters,mapState } from 'vuex'
export default {

  computed: {
    // language() {
    //   return this.$store.getters.language
    // },
    ...mapState({
      language:state=>state.app.language
    }),
  },
  methods: {
    handleSetLanguage(lang) {
      this.$i18n.locale = lang
      this.$store.dispatch('app/setLanguage', lang)
      localStorage.lang = lang
    }
  }
}
</script>
<style scoped>
  >>>.iconfont{
    font-size: 20px;
  }

</style>
