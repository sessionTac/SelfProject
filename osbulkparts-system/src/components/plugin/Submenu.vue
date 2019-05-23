<template>

  <component :is="menuType"  v-bind="bind" v-if="show">

    <!--二级菜单（可展开型）-->
    <template v-if="menuType==='ElSubmenu'">
      <template slot="title">
        <i :class="[menuData.iconClass || 'el-icon-menu']" v-if="level==0"></i><span>{{menuData.title}}</span>
      </template>
      <submenu :menu-data="submenu" :level="level+1"  v-for="(submenu, submenu_idx) in menuData.children" :key="submenu_idx"/>
    </template>

    <!--二级菜单-->
    <template v-if="menuType==='ElMenuItem'">
      <i :class="[menuData.iconClass || 'el-icon-menu']" v-if="level==0"></i><span slot="title">{{menuData.title}}</span>
    </template>

  </component>

</template>

<script>


  export default {
    name: 'submenu',
    data() {
      return {
      };
    },
    props: {
      menuData: Object,
      level: Number,
      skipPermissionCheck: Boolean
    },
    computed: {

      show() {
        return this.skipPermissionCheck || (!this.menuData.requiresPermission || this.subject.hasPermissions(this.menuData.requiresPermission));
      },
      menuType() {
        return this.menuData.children ? 'ElSubmenu' : 'ElMenuItem';
      },
      bind() {

        let props = {
          index: this.menuData.index,
        };
        if (!this.menuData.children) {
          props.route = {name:this.menuData.index};
        }
        return props;
      },
    },
  }
</script>

<style scoped>

</style>
