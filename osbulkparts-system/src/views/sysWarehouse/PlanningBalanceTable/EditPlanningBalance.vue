<template>
    <div>
        <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
                   width="750px">
            <el-card>
                <div class="dialogStyle" style="display: flex;flex-direction: column">
                    <el-form  class="search-form search-form-normal" label-width="110px" ref="form"
                              style="flex: 5" :model="form" size="mini" :rules="rules">
                        <el-form-item :label="$t('pageTable.OrderInfoOrderCode')" prop="orderCode">
                            <el-input v-model="form.orderCode" class="search-form-item-input" style="width: 160px" size="mini"
                                      clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.OrderInfoOrderCodeDesc')" prop="orderCodeDesc">
                            <el-input type="textarea" autosize="" v-model="form.orderCodeDesc" class="search-form-item-input" style="width: 160px" size="mini"
                                      clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.OrderInfoOrderAmount')" prop="orderAmount">
                            <el-input v-model="form.orderAmount" class="search-form-item-input" style="width:160px" size="mini"
                                      clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.OrderInfoOrderDate')" prop="orderDate">
                            <el-date-picker
                              v-model="form.orderDate"
                              type="date"
                              style="width:160px"
                              value-format="yyyyMMddHHmmss">
                            </el-date-picker>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.OrderInfoOrderUnit')" prop="orderUnit">
                            <el-select v-model="form.orderUnit" style="width: 160px" size="mini" clearable>
                                <el-option value=""></el-option>
                                <el-option
                                  size="mini"
                                  v-for="item in orderUnits"
                                  :key="item.value"
                                  :label="item.name"
                                  :value="item.value">
                                </el-option>
                            </el-select>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <!--<el-form-item :label="计划状态" prop="orderStatus">-->
                            <!--<el-select v-model="form.orderStatus" style="width: 160px" size="mini" clearable>-->
                                <!--<el-option value=""></el-option>-->
                                <!--<el-option-->
                                  <!--size="mini"-->
                                  <!--v-for="item in orderStatus"-->
                                  <!--:key="item.value"-->
                                  <!--:label="item.name"-->
                                  <!--:value="item.value">-->
                                <!--</el-option>-->
                            <!--</el-select>-->
                            <!--<template slot="error" slot-scope="scope">-->
                                <!--<div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>-->
                            <!--</template>-->
                        <!--</el-form-item>-->


                    </el-form>

                </div>

            </el-card>
            <div class="dialogButton">
                <el-button type="primary" size="mini" :disabled="form.isLocked==1" @click="submit('form')"><i
                  class="fa fa-check"></i> {{$t('searchFrom.confirm')}}
                </el-button>
                <el-button size="mini" @click=" cancel">{{$t('searchFrom.cancel')}}</el-button>
            </div>

        </el-dialog>
    </div>
</template>

<script>
    import activityService from '@/api/warehouse/planningBalance'
    import ui_config from '@/config/ui_config'

    export default {
        name: "EditOrderPlan",
        props: {
            id: {},
            mode: "",
        },
        computed: {
            title: function () {
                let entityName = this.$t('searchFrom.balanceList')
                if(this.mode === 'ADD'){return this.$t('searchFrom.add')+entityName}
                else if(this.mode === 'EDIT'){return this.$t('searchFrom.edit')+entityName}
            }
        },
        data() {
            return {
                dialogFormVisible: true,
                search_keys: {},
                materialUnits: [],
                orderUnits:[],
                orderStatus:[],
                materialRelationUnits: [],
                materialCategorys: [],
                materialSupplyModes: [],
                form: {
                    orderCode: '',
                    orderCodeDesc: '',
                    orderAmount: '',
                    orderDate: '',
                    orderUnit: '',
                    orderStatus: '',
                },
                /**表单的验证*/
                rules: {
                    orderCode: [
                        {required: true, message:  this.$t('Tips.checkMaterialOrderCode'), trigger: 'blur'},
                        {max: 20, message: this.$t('Tips.lentghGT20'), trigger: 'blur'},
                        {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: this.$t('Tips.formatNames'),}
                    ],
                    orderCodeDesc: [
                        {required: true, message: this.$t('Tips.checkOrderCodeDesc'), trigger: 'blur'},
                        {max: 200, message: this.$t('Tips.lentghGT200'), trigger: 'blur'},
                        {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: this.$t('Tips.formatNames'),}
                    ],
                    orderAmount: [
                        {required: true, message: this.$t('Tips.checkOrderAmount'), trigger: 'blur'},
                        {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,3}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
                    ],
                    orderDate:[
                        {required: true, message: this.$t('Tips.checkOrderDate'), trigger: 'blur'},
                    ],
                    orderUnit: [
                        {required: true, message: this.$t('Tips.modelUnitOfTheOrder'), trigger: 'change'}
                    ],
                    orderStatus: [
                        {required: true, message: this.$t('Tips.planStatus'), trigger: 'change'}
                    ],
                },
            }
        },
        mounted() {
            this.init()
        },
        methods: {
            async init() {
                await activityService.initData().then(resp => {
                    this.orderUnits = resp.data.result.orderUnits;
                    this.orderStatus = resp.data.result.orderStatus;
                    // this.currencys = resp.data.result.currencys;
                    // this.materialCategorys = resp.data.result.materialCategorys;
                    // this.materialSupplyModes = resp.data.result.materialSupplyModes;
                }, err => {
                    console.error(err);
                });
                if (this.mode == "EDIT") {
                    await activityService.findOrderInfo({id: this.id}).then(resp => {
                        this.form = resp.data.result;
                    }, err => {
                        console.error(err);
                    });
                }

            },
            /*确定*/
            submit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let data = {
                            id: this.id || undefined,
                            orderCode: this.form.orderCode || undefined,
                            orderCodeDesc: this.form.orderCodeDesc || undefined,
                            orderAmount: this.form.orderAmount || undefined,
                            orderDate: this.form.orderDate || undefined,
                            orderUnit: this.form.orderUnit || undefined,
                            orderStatus: this.form.orderStatus || undefined,
                            dataRoleAt:this.form.dataRoleAt || undefined,
                            version: this.form.version || undefined,
                            isBalance                 :1,
                            orderType:1,
                        }
                        if (this.mode == 'EDIT') {  //编辑
                            activityService.updateMatter({...data}).then(resp => {
                                if (resp.data.code == "201") {
                                    this.$notify({message: resp.data.message, type: "success"});
                                    this.$emit("success");
                                    this.dialogFormVisible = false
                                } else {
                                    this.$notify({message: resp.data.message, type: "error"});
                                }
                            })
                        } else {
                            activityService.addOrderInfo({...data}).then(resp => {  //添加
                                if (resp.data.code == "201") {
                                    this.$notify({message: resp.data.message, type: "success"});
                                    this.$emit("success");
                                    this.dialogFormVisible = false
                                } else {
                                    this.$notify({message: resp.data.message, type: "error"});
                                }
                            })
                        }

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            cancel() {
                this.dialogFormVisible = false
            },
        }
    }
</script>

<style scoped>

</style>