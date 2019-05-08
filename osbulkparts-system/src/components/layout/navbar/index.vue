<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />
    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />

    <div class="right-menu">
      <template>
        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <lang-select class="right-menu-item hover-effect" />
      </template>
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click" @command="handleCommand">
        <div class="avatar-wrapper">
          <img src="https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="person" >
                {{ $t('navbar.person') }}
            </el-dropdown-item>
          <el-dropdown-item divided>
            <span style="display:block;" @click="">{{ $t('navbar.logOut') }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  <person-dialog v-bind.sync="edit_pass"  v-if="edit_pass.activated" ></person-dialog>
  </div>
</template>

<script>
  import { mapGetters,mapState } from 'vuex'
  import Breadcrumb from '@/components/layout/navbar/Breadcrumb'
  import Hamburger from '@/components/Hamburger'
  // import ErrorLog from '@/components/layout/navbar/ErrLog.vue'
  import Screenfull from '@/components/layout/navbar/Screefull'
  // import SizeSelect from '@/components/SizeSelect'
  import LangSelect from '@/components/layout/navbar/LangSelect'
  // import Search from '@/components/HeaderSearch'
  import PersonDialog from '@/views/sysPassport/PersonCenter'
  export default {
    components: {
      Breadcrumb,
      Hamburger,
      // ErrorLog,
      Screenfull,
      // SizeSelect,
      LangSelect,
      // Search
    PersonDialog
    },
    name: "Index",
    data(){
      return{
          edit_pass : {},
      }
    },
    computed: {
      ...mapState({
        sidebar: state => state.app.sidebar,
      }),
    },
    methods: {
      toggleSideBar() {
        this.$store.dispatch('app/toggleSideBar')
      },
        // openPerson(){
        //     this.edit_pass = {activated:true,id:'1'}
        // },
        handleCommand(command){
            if(command == 'person'){
                this.edit_pass = {activated:true,id:'1'}
            }
        }
      // async logout() {
      //   await this.$store.dispatch('user/logout')
      //   this.$router.push(`/login?redirect=${this.$route.fullPath}`)
      // }
    }
  }
</script>

<style scoped>

</style>


<style lang="scss" scoped>
  .navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0,21,41,.08);

    .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background .3s;
      -webkit-tap-highlight-color:transparent;

      &:hover {
        background: rgba(0, 0, 0, .025)
      }
    }

    .breadcrumb-container {
      float: left;
    }

    .errLog-container {
      display: inline-block;
      vertical-align: top;
    }

    .right-menu {
      float: right;
      height: 100%;
      line-height: 50px;

      &:focus {
        outline: none;
      }

      .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }

      .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
          margin-top: 5px;
          position: relative;

          .user-avatar {
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }
  }
</style>