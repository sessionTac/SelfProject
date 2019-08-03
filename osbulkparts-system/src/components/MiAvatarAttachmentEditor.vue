<template>
  <table class="mi-avatar-attachment-editor">
    <tr>
      <td>
        <el-upload
          action=""
          :accept="accept"
          :show-file-list="false"
          :auto-upload="false"
          :on-change="onSelectAttachmentFile">
          <img v-if="image_url" :src="image_url" class="avatar-uploader-icon" ref="img">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </td>
      <td valign="middle">
        <el-button
            title="Preview" icon="el-icon-zoom-in" @click="openViewer"
            class="btn-opt" plain v-if="value" style="display: block;"/>
        <el-button
            title="Remove" icon="el-icon-delete" @click="removeAttachment"
            class="btn-opt" plain v-if="value" style="display: block"/>
        <el-button
            class="btn-opt" plain v-if="value" style="display: block">

          <attachment-upload-state-icon
            v-if="value"
            :state="value['mi-editor.upload_state']"
            :asterisk="value['mi-editor.asterisk']"
            :message="value['mi-editor.validate_msg']" />
        </el-button>

      </td>
    </tr>
  </table>
</template>

<script>

  import emitter from 'element-ui/src/mixins/emitter';
  import uuid from 'uuid/v4';
  import Viewer from 'viewerjs';
  // import Viewer from '@/components/viewerjs';
  import AttachmentUploadStateIcon from './MiAttachmentStateIcon'

  /**
   * 头像表单项上传组件
   *
   * @author puzhongqiang
   */
  export default {

    name: 'MiAvatarAttachmentEditor',

    mixins: [emitter],

    components: {
      AttachmentUploadStateIcon
    },

    props: {
      accept: {type: String, default: "image/*"},
      entity:Object,
      value: Object,
      dao: Object,    // 需要包含：uploader, srcGetter, thumbSrcGetter
      friends: Array, //TODO 点击放大镜时，下方缩略列表中出现的图片。用uuid识别当前元素在数组中的位置。
    },

    data() {
      return {
        image_url: null,
        viewer_instance: null,
      };
    },


    watch: {
      value: {
        handler: 'initAttachment',
        immediate: true,
      },
    },

    methods: {

      openViewer() {

        let options = {
          hidden: evt => viewer.destroy(),
        };
        //取当前最大的z-index
        let wrappers = Array.from(document.getElementsByClassName('el-dialog__wrapper'));
        let max_zIndex = wrappers.map(el => el.style.zIndex || 0).reduce(Math.max, 0);
        let zIndex = max_zIndex ? max_zIndex + 10 : undefined;

        if (zIndex) {
          options.zIndex = zIndex;
        }

        let el = document.createElement('img');
        el.src = (this.value['mi-editor.upload_state'] === 'FROM_SERVER') ?
          this.dao.srcGetter(this.value) : this.$refs.img.getAttribute("src");


        const viewer = new Viewer(el, options);
        viewer.show();
        this.viewer_instance = viewer;
      },

      initAttachment(newValue) {
        console.log('initAttachment : watching value');
        if (!this.value) {
          this.image_url = null;
          return;
        }

        let attachment = this.value;

        if (!attachment['mi-editor.uuid']) {
          this.$set(attachment, 'mi-editor.uuid', uuid());
        }
        if (!attachment['mi-editor.upload_state']) {
          this.$set(attachment, 'mi-editor.upload_state', 'FROM_SERVER');
        }
        if (!attachment['mi-editor.asterisk']) {
          this.$set(attachment, 'mi-editor.asterisk', false);
        }

        if (attachment['mi-editor.upload_state'] === 'FROM_SERVER') {
          let srcGetter = this.dao.thumbSrcGetter || this.dao.srcGetter;
          this.image_url = srcGetter(this.value);
        }
      },

      onSelectAttachmentFile(file) {
        // debugger
        // console.log('选择了文件', file);
        this.image_url = URL.createObjectURL(file.raw);
        this.$emit('input', {
          'mi-editor.uuid': uuid(),
          'mi-editor.upload_state': 'STANDBY',
          'mi-editor.name': file.name,
          'mi-editor.raw':  file.raw,
          'mi-editor.asterisk': true,
        });
        this.$emit('change');
        this.dispatch('ElFormItem', 'el.form.change');
      },

      removeAttachment() {
        this.image_url = null;
        this.$emit('input', null);
        this.dispatch('ElFormItem', 'el.form.change');
      },

      async validateAndUpload() {
        await this.validate();
        return this.upload();
      },

      async validate() {

        if (this.value && (
          this.value['mi-editor.upload_state'] === 'STANDBY'
          || this.value['mi-editor.upload_state'] === 'ERROR'
          || this.value['mi-editor.upload_state'] === 'INVALID')) {

          let bad_list = [];
          let currentAttachment = this.value;

          let maxFileSize = 1024 * 1024;

          if (currentAttachment['mi-editor.raw'].size > maxFileSize) {
            this.$set(currentAttachment, 'mi-editor.upload_state', 'INVALID');
            this.$set(currentAttachment, 'mi-editor.validate_msg', '上传文件大小不能超过 1MB!');
            bad_list.push(currentAttachment);
          } else {
            this.$set(currentAttachment, 'mi-editor.upload_state', 'STANDBY');
            this.$set(currentAttachment, 'mi-editor.validate_msg', null);
          }

          if (bad_list.length) {
            throw (bad_list.map(attachment =>
              attachment['mi-editor.name'] + ':' + attachment['mi-editor.validate_msg']).join('；')
            );
          }

        }

      },

      async upload() {

        let currentAttachment = null;

        try {

          currentAttachment = this.value;

          if (!currentAttachment || !['STANDBY','ERROR'].includes(currentAttachment['mi-editor.upload_state'])) {
            console.log('不需要上传图片');
            return;
          }

          currentAttachment['mi-editor.upload_state'] = 'UPLOADING';

          let attachment = await this.dao.uploader(currentAttachment['mi-editor.raw'],this.entity);

          this.$emit('input', attachment);

          console.log('图片上传完毕');

        } catch (e) {
          // this.$notify({type:'error', message:'上传文件时出现错误'});

          currentAttachment && (currentAttachment['mi-editor.upload_state'] = 'ERROR');
          console.error('上传文件时出现错误', e);
          throw e;
        }


      }
    },

    destroyed() {
      if (this.viewer_instance) {
        this.viewer_instance.destroy();
      }
    },
  }
</script>

<style scoped>
  .mi-avatar-attachment-editor {
    display: block;
  }

  .mi-avatar-attachment-editor>>> .el-upload {
    line-height: 0;
    display: block;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader-icon:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 107px;
    text-align: center;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .btn-opt {
    width: 25px;
    height: 25px;
    padding: 0;
    border: 0;
    margin: 0 !important;
  }

</style>
