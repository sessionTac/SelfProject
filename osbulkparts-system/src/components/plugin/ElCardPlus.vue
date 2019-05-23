<template>
  <div class="el-card is-always-shadow">
    <div class="el-card__image" v-if="$slots.image || image">
      <slot name="image">{{ image }}</slot>
    </div>
    <div class="el-card__header" v-if="$slots.header || header">
      <slot name="header">{{ header }}</slot>
    </div>
    <div class="el-card__body" :style="bodyStyle">
      <slot></slot>
    </div>
    <div class="el-card__footer" v-if="$slots.footer || footer" :style="footerStyle">
      <slot name="footer">{{ footer }}</slot>
    </div>
  </div>
</template>

<script>
  //element-ui card升级版，支持slot#footer、footerStyle
  export default {
    props: ['header', 'image', 'bodyStyle', 'footer', 'footerStyle']
  }
</script>

<style scoped>
  >>> .el-card__footer {
    /*text-align: center;*/
    padding: 16px 20px;
    border-top: 1px solid #ebeef5;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    background-color: #f9f9f9;
  }
</style>
<style scoped>
  >>> .el-card__footer .el-pagination.is-background .btn-next, >>> .el-pagination.is-background .btn-prev, >>> .el-pagination.is-background .el-pager li {
    background-color: white;
  }
</style>
