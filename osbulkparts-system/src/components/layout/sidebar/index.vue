<template>
  <div :class="{'has-logo':showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          :collapse="isCollapse"
          @open="handleOpen"
          @close="handleClose"
          :background-color="variables.menuBg"
          :text-color="variables.menuText"
          :active-text-color="variables.menuActiveText">
        <submenu  v-for="(item,menu_index) in menus" :menu="item" :key="item.index" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
  import { mapGetters,mapState } from 'vuex'
  import menus from "@/config/console-menu"
  import variables from '@/styles/variables.scss'
  import Submenu from "@/components/layout/sidebar/Submenu"
  import Logo from './Logo'

  export default {
    components:{Submenu, Logo },
    data(){
      return{
        variables,
        menus,
        showLogo: true,
      }
    },
    methods:{
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    },
    computed: {
      ...mapState({
        sidebar: state => state.app.sidebar,
      }),
      isCollapse() {
        return !this.sidebar.opened;
      }
    }
  }
</script>

<style scoped>
  .home-layer { position:absolute; left:0; top:0; width:100%; height:100%; }
  .el-menu-vertical-demo:not(.el-menu--collapse) { min-width:220px; max-width:220px; }
</style>