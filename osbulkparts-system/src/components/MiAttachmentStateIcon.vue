<template>
  <span>
    <i v-if="asterisk && state!=='UPLOADING'"    title="New Attachment"  class="fa fa-asterisk" aria-hidden="true" style="color:orange;font-size: 9px"></i>
    <i v-if="state==='FROM_SERVER'"    title="Success"  class="fa fa-check-circle" aria-hidden="true" style="color:green;font-size: 9px"></i>
    <i v-if="state==='UPLOADING'"  title="Uploading..."  class="el-icon-loading" aria-hidden="true" style="color:blue;font-size: 9px"></i>
    <i v-if="state==='ERROR'"      title="Server Error"  class="fa fa-exclamation-circle" aria-hidden="true" style="color:red;font-size: 9px"></i>
    <i v-if="state==='INVALID'"   :title="message"  class="fa fa-exclamation-triangle" aria-hidden="true" style="color:red;font-size: 9px"></i>
  </span>
</template>

<script>
    export default {
        props: {
          state: {},
          asterisk: {},
          message: {},
        },
    }
</script>

<style scoped>

</style>
