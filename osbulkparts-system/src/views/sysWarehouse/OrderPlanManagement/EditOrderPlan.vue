<template>
    <div>
        <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)"
                   width="600px">
            <el-card>
                <div class="dialogStyle" style="display: flex;flex-direction: column">
                    <el-form  class="search-form search-form-normal" label-width="110px" ref="form"
                              style="flex: 5" :model="form" size="mini" :rules="rules">
                        <el-form-item label="订单产品型号" prop="orderCode">
                            <el-input v-model="form.orderCode" class="search-form-item-input" style="width: 160px" size="mini"
                                      clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item label="订单产品型号描述" prop="orderCodeDesc">
                            <el-input v-model="form.orderCodeDesc" class="search-form-item-input" style="width: 160px" size="mini"
                                      clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item label="订单数量" prop="orderAmount">
                            <el-input v-model="form.orderAmount" class="search-form-item-input" style="width:160px" size="mini"
                                      clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item label="订单日期" prop="orderDate">
                            <el-date-picker
                              v-model="form.orderDate"
                              type="date"
                              style="width:160px"
                              value-format="yyyyMMddHHmmss"
                              placeholder="选择日期">
                            </el-date-picker>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item label="订单型号单位" prop="orderUnit">
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
                        <el-form-item label="计划状态" prop="orderStatus">
                            <el-select v-model="form.orderStatus" style="width: 160px" size="mini" clearable>
                                <el-option value=""></el-option>
                                <el-option
                                  size="mini"
                                  v-for="item in orderStatus"
                                  :key="item.value"
                                  :label="item.name"
                                  :value="item.value">
                                </el-option>
                            </el-select>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>


                    </el-form>

                </div>

            </el-card>
            <div class="dialogButton">
                <el-button type="primary" size="mini" :disabled="form.isLocked==1" @click="submit('form')"><i
                  class="fa fa-check"></i> 确定
                </el-button>
                <el-button size="mini" @click=" cancel">取消</el-button>
            </div>

        </el-dialog>
    </div>
</template>

<script>
    import activityService from '@/api/warehouse/orderInfo'
    import ui_config from '@/config/ui_config'

    export default {
        name: "EditOrderPlan",
        props: {
            id: {},
            mode: "",
        },
        computed: {
            title: function () {
                let entityName = '订单计划'
                if (this.mode === 'ADD') {
                    return '新增' + entityName
                } else if (this.mode === 'EDIT') {
                    return '编辑' + entityName
                }
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
                        {required: true, message: '请填写订单产品型号', trigger: 'blur'},
                        {max: 20, message: '长度不超过20个字符', trigger: 'blur'},
                        {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
                    ],
                    orderCodeDesc: [
                        {required: true, message: '请填写订单产品型号描述', trigger: 'blur'},
                        {max: 200, message: '长度不超过200个字符', trigger: 'blur'},
                        {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: '请输入英文数字下划线',}
                    ],
                    orderAmount: [
                        {required: true, message: '请填写订单数量', trigger: 'blur'},
                        {pattern: /^[0-9]*$/ , trigger: 'blur', message: '请输入数字',}
                    ],
                    orderDate:[
                        {required: true, message: '请填写订单日期', trigger: 'blur'},
                    ],
                    orderUnit: [
                        {required: true, message: '请选择物料类别', trigger: 'change'}
                    ],
                    orderStatus: [
                        {required: true, message: '请选择物料类别', trigger: 'change'}
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
                    await activityService.findMatterInfo({materialInfoId: this.id}).then(resp => {
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
                            version: this.form.version || undefined,
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