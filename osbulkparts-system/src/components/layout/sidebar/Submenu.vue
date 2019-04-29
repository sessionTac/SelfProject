<template>
  <div class="menu-wrapper" v-show="show">
    <el-submenu :index="menu.index" v-if="menu.children" >
      <template slot="title">
        <i class="el-icon-setting svg-icon"></i>
        <span>{{menu.title}}</span>
      </template>
      <Submenu :menu="item" v-for="item in menu.children" :key="item.index" :is-nest="true"/>
    </el-submenu>

    <app-link v-else :to="menu.index">
      <el-menu-item  :index="menu.index" :class="{'submenu-title-noDropdown':!isNest}">
        <i class="el-icon-location svg-icon"></i>
        <span slot="title">{{menu.title}}</span>
      </el-menu-item>
    </app-link>
  </div>


</template>

<script>
  import AppLink from './Link'
  export default {
    name: "Submenu",
    components: {  AppLink },
    props: {
      menu: {},
      isNest: {
        type: Boolean,
        default: false
      },
    },

    data() {
      return {}
    },
    computed: {
      show() {
        return this.subject.hasPermissions(this.menu.requiresPermission);
      }
    },
    methods:{

    },
  }
</script>

<style scoped>
  .hideSidebar i.svg-icon {
    margin-left: 15px !important;
  }
</style>