<template>
  <div>
    <el-dialog
      :title="title"
      width= "600px"
      :close-on-click-modal= '!isUploading'
      :show-close="!isUploading"
      :visible.sync="dialogFormVisible"
      @closed="$emit('update:activated', false)"
    >
      <el-form class="search-form search-form-normal" ref="form" size="mini" :model="form" label-width="200px" :rules="rules" >
        <div  v-if="target=='ORDER_PLAN'" style="text-align: center">
          <el-radio v-model="radio" label="1" border>周</el-radio>
          <el-radio  v-model="radio" label="2" border>月</el-radio>
        </div>


        <!--上传-->
        <el-form-item label="文件">
          <el-input class="txt-brower" readonly v-model="fileName" style="width: 200px" size="mini "></el-input>
          <el-upload
            action=""
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :on-change="handleChange"
            :on-remove="beforeRemove"
            accept=".xls,.xlsx"
            style="width: 20px;float: right;margin-left: 10px"
            :auto-upload="false"
            ref="upload">
            <el-button slot="trigger" size="mini" type="primary" class="btn-brower" >浏览</el-button>
          </el-upload>
        </el-form-item>


        <el-form-item label=" " v-if="uploadState.state==='uploading'">
          <div style="width: 300px">
            <el-progress :percentage="uploadState.percentage" :status="uploadState.progressStatus"></el-progress>
            {{uploadState.stateMessage}}
          </div>
        </el-form-item>


        <el-form-item label=" " v-if="!!entity || !!message">
          <div style="width: 350px">
            <div v-if="message" style="font-size: 12px;color:red">{{message}}</div>
<!--            <div v-if="entity" style=""><el-button @click="downloadFile" type="text" style="color: red"> 插入失败，请<u>下载附件</u>检查出错原因</el-button></div>-->
          </div>
        </el-form-item>
        <el-form-item label="下载模板">
          <div>
            <el-link type="primary" @click="downloadTemp">点击这里下载模板</el-link>
          </div>
        </el-form-item>
      </el-form>
      <span slot="footer">
          <el-button type="primary" size="mini" @click="submit(form)" :disabled="!currentFile">
            <i class="fa fa-check"></i> 确定</el-button>
          <el-button  size="mini" @click="cancel()">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import service from '@/api/import'
  import {downloadBlobResponse} from '@/utils/request_utils'

  export default {
    name: "ImportDataComponents",
    components:{},
    computed:{
      title() {
        return this.targetName + "信息导入"
      }
    },

    props:{
      target: String,
      targetName: String,
    },
    data(){
      return{
        disabled : false,
        dialogFormVisible  : true,
        regions            : [],
        form:{
        },
        loading: true,
        internal_activated : true,

        isUploading: false,
        radio:"1",
        uploadState: {
          state: null,
          percentage : 0,
        },

        fileName: '', //上传的文件名
        currentFile: null, //当前选择的文件
        fileNameErr:"",
        value:'',
        entity:"",
        message:"",
        timeOut:'',
        rules:{
          document:[
            {required: true, message: '请上传文件'},
          ],

        }

      }
    },

    mounted(){
      this.init();
    },

    methods:{
      cleanUploadErrorMessage() {
        this.uploadState = {
          state: null,
          percentage : 0,
        };
        this.message = "";
        this.entity = null;
        this.fileNameErr = "";
      },

      /*上传前验证文件大小*/
      beforeAvatarUpload(file) {
        console.log("文件大小:"+file.size)
        const fileSize = file.size /1024< 45;
        if (!fileSize) {
          this.$message.error('上传文件大小不能超过 10MB!');
        }
        return fileSize;
      },

      handleChange(file) {
        this.cleanUploadErrorMessage();
        this.currentFile = file;
        this.fileName = file.name;
      },

      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },

      init(){

      },

      onUploadProgress(evt) {

        if (evt.lengthComputable) {

          if (evt.loaded !== evt.total)

            this.uploadState = {
              state: 'uploading',
              progressStatus: undefined,
              percentage: Number.parseFloat((80 * evt.loaded / evt.total).toFixed(1)),
              stateMessage: '正在传输文件...',
            };
          else {

            this.uploadState = {
              state: 'uploading',
              progressStatus: undefined,
              percentage: 80,
              stateMessage: '服务器处理中...',
            };
          }
        }
      },
      downloadTemp(){
        switch(this.target) {
          case 'MATTER' :
            return service.downloadExcelTemp('materialDataTemp').then(resp=>{
              downloadBlobResponse(resp); // 文件下载
            });
          case 'ORDER_PLAN' :
            return service.downloadExcelTemp('orderPlanTemp').then(resp=>{
              downloadBlobResponse(resp); // 文件下载
            });
          case 'STOCK_INFO':
            return service.downloadExcelTemp('stockInfoTemp').then(resp=>{
              downloadBlobResponse(resp); // 文件下载
            });
        }
      },

      doPost() {
        switch(this.target) {
          case 'MATTER' : return service.importData('/material/importExcel', {}, {file:this.currentFile.raw}, this.onUploadProgress);

          case 'ORDER_PLAN' : return service.importData('/orderInfo/importExcel', {importType:this.radio}, {file:this.currentFile.raw}, this.onUploadProgress);

          case 'STOCK_INFO' : return service.importData('/stock/importExcel', {}, {file:this.currentFile.raw}, this.onUploadProgress);

          // case 'ORGANIZATION'                : return service.importData('/basis/organization/leadExcel', {categoryNo:this.form.categoryNo && this.form.categoryNo || ""}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'POWER_STATION'               : return service.importData('/basis/power-station/leadExcel', {transferorNo:this.form.transferor && this.form.transferor.transferorNo ||""}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'DEVICE'                      : return service.importData('/basis/deviceInfo/leadExcel', {pwStationNo : this.form.powerStation && this.form.powerStation.no || undefined,
          //                                                                                                       transferorNo: this.form.transferor && this.form.transferor.transferorNo || undefined,},
          //                                                                                                     {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'TRANSFORMER_SUBSTATION'      : return service.importData('/basis/transformer-station/leadExcel', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'POVERTY_STRICKEN_COUNTIES'   : return service.importData('url....', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'DEVICE_TYPE'                 : return service.importData('/basis/equip-type-management/leadExcel', {pwStationNo:this.form.powerStation && this.form.powerStation.no || "", transferorNo: this.form.transferor && this.form.transferor.transferorNo || ""}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'DEVICE_MODEL'                : return service.importData('/basis/device-model/leadExcel', {transferorNo: this.form.transferor && this.form.transferor.transferorNo || undefined,
          //
          //                                                                                                 deviceVenderNo:this.form.deviceVender && this.form.deviceVender.no || undefined, },
          //                                                                                                 {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'DEVICE_MANUFACTOR'           : return service.importData('/basis/equipment-manufacturer/leadExcel', {pwStationNo : this.form.powerStation && this.form.powerStation.no || undefined,
          //                                                                                                            transferorNo: this.form.transferor && this.form.transferor.transferorNo || undefined,},
          //                                                                                                           {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'EQUIPMENT_MANUFACTURING'     : return service.importData('url....', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'POOR_COUNTY_INFO'            : return service.importData('/basis/poor-county-info/leadExcel','params....', {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'POWER_GENERATION_MANAGEMENT' : return service.importData('/data/power-generation/leadExcel',{type:1}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'POWER_LIMITATION_MANAGEMENT' : return service.importData('/data/power-generation/leadExcel',{type:3}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'POWER_USE_MANAGEMENT' : return service.importData('/data/power-generation/leadExcel',{type:2}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'INVESTMENT_ENTITY'           : return service.importData('/basis/invest/leadExcel', {transferorNo: this.form.transferor && this.form.transferor.transferorNo || undefined,},
          //                                                                                                   {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'ADMINISTRATIVE_DIVISION'     : return service.importData('/basis/administrative/leadExcel', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'PROJECT_INFORMATION'         : return service.importData('url....', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // // case 'PROJECT_PLANNING'            : return service.importData('/data/planning-project/leadExcel', {cityAreaCode:this.form.region && this.form.region[0] || undefined,countyAreaCode:this.form.region && this.form.region[1] || undefined},
          // //                                                                                                    {file:this.currentFile.raw}, this.onUploadProgress);
          // case 'PROJECT_PLANNING'            : return service.importData('/data/planning-project/leadExcel', {}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'POVERTY_DISTRIBUTION'        : return service.importData('/data/povertyAlleviationDistribution/leadExcel',{},{file:this.currentFile.raw}, this.onUploadProgress);
          //
          // // case 'NATIONAL_INDICATORS'         : return service.importData('/data/stateIndex/leadExcel', {energyType:this.form.resourceType,
          // //                                                                                               cityAreaCode:this.form.region && this.form.region[0] || undefined,
          // //                                                                                               countyAreaCode:this.form.region && this.form.region[1] || undefined,},
          // //                                                                                              {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'NATIONAL_INDICATORS'         : return service.importData('/data/stateIndex/leadExcel',{}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'PROVINCE_POWER_INSTALLATION' : return service.importData('url....', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'PROVINCE_POWER'              : return service.importData('url....', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'FIVE_YEARS_PLANNING'         : return service.importData('/data/projectplan/five_years_plan/leadExcel', {}, {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'RESOURCE_DATA'               : return service.importData('/maintenance-data-resource-data-develop-quantity/leadExcel', {},{file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'WIND_RESOURCE_DATA'          : return service.importData('/maintenance-data/wind-resource/leadExcel', {},{file:this.currentFile.raw}, this.onUploadProgress);
          // case 'WIND_RESOURCE_MAINTAIN_DATA'          : return service.importData('/maintenance-data/wind-resource/leadExcel', {},{file:this.currentFile.raw}, this.onUploadProgress);
          // case 'LIGHT_RESOURCE_DATA'         : return service.importData('/maintenance-data/wind-resource/leadExcel', {},{file:this.currentFile.raw}, this.onUploadProgress);
          // case 'BIOMASS_RESOURCE_DATA'       : return service.importData('/maintenance-data/wind-resource/leadExcel', {},{file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'POINT_TABLE_MANAGEMENT'      : return service.importData('/basis/point-table/importExcel', {transferorNo:this.form.transferor && this.form.transferor.transferorNo || null,
          //                                                                                                   powerStation:this.form.powerStation && this.form.powerStation.power_station_no || null,
          //                                                                                                   powerStationTransferor:this.form.powerStation && this.form.powerStation.power_station_no || null},
          //                                                                                                   {file:this.currentFile.raw}, this.onUploadProgress);
          //
          // case 'TRANSFEROR'                  : return service.importData('/basis/transferor/leadExcel', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
          // case 'INSTALL_EQUIPMENT'           : return service.importData('/data/equipment/leadExcel', 'params....', {file:this.currentFile.raw}, this.onUploadProgress);
        }
      },

      /*确定*/
      submit(){

        const fileSize = this.currentFile.size /1024/1024< 10;
        if (!fileSize) {
          this.$message.error('上传文件大小不能超过 10MB!');
        }
        this.isUploading = !this.isUploading
        this.doPost().then(resp => {
          this.isUploading = !this.isUploading
          this.currentFile = false
          let status = resp.data.status

          if(status){
            this.message = resp.data.message;
            this.$emit('saved');
            this.timeOut = setTimeout(() => {
              this.dialogFormVisible = false;
            }, 5000);

            this.uploadState = {
              state: 'uploading',
              progressStatus: 'success',
              percentage: 100,
              stateMessage: undefined,
            };

          } else {

            this.uploadState = {
              state: 'uploading',
              progressStatus: 'exception',
              percentage: 100,
              stateMessage: undefined,
            };
            this.message = resp.data.message;
            this.entity = resp.data.entity;
            this.fileNameErr = resp.data.fileName;
            this.$refs.upload.clearFiles();
            this.$refs.upload.abort();
            this.currentFile = null;
            this.fileName = '';
          }
        }, err => {
          this.uploadState.progressStatus = 'exception';
          this.uploadState.stateMessage   = '服务器端异常';

          console.error('上传失败', err);
        })
      },

      downloadFile(){
        // download(this.entity,this.fileNameErr);
      },


      /*取消*/
      cancel(){
        this.$refs.upload.clearFiles();
        this.$refs.upload.abort();
        this.currentFile = null;
        this.fileName = '';
        this.dialogFormVisible = false
      },
    }
  }
</script>

<style scoped>
  >>>.el-dialog__body {
    padding: 0px 0px;
    color: #606266;
    font-size: 14px;
  }

  >>> .el-dialog__footer {
    /*margin-top: -30px;*/
    text-align: center;
  }

  >>>.el-form-item__content{
    margin-left: 0px !important;
  }

</style>
