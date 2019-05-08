<template>
  <div class="app-main" style="overflow-y: auto">
    <transition name="fade-transform" mode="out-in">
      <div style="position: absolute;top:0;bottom:0;left:0;right:0;overflow: auto">
        <keep-alive  >
          <router-view :key="key" v-if="$route.meta.keepAlive" />
        </keep-alive>
        <router-view :key="key" v-if="!$route.meta.keepAlive" />
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },
    key() {
      return this.$route.fullPath
    }
  }
}
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header+.app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
  }

  .fixed-header+.app-main {
    padding-top: 84px;
  }
}
</style>

