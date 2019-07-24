<template>
    <div>
        <el-dialog  :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="750px">
            <el-card>
                <div class="dialogStyle" style="display: flex;flex-direction: column">
                    <el-form class="search-form search-form-normal" ref="form" style="flex: 5"  :model="form"  label-width="110px" :rules="rules"  size="mini">
                        <el-form-item :label="$t('pageTable.SupplierCode')" prop="supplierCode">
                            <el-input v-model="form.supplierCode" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierNameCn')" prop="supplierNameCn">
                            <el-input type="textarea" v-model="form.supplierNameCn" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierNameEn')" prop="supplierNameEn">
                            <el-input type="textarea" v-model="form.supplierNameEn" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierDescCn')" prop="supplierDescCn">
                            <el-input type="textarea" v-model="form.supplierDescCn" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierDescEn')" prop="supplierDescEn">
                            <el-input type="textarea" v-model="form.supplierDescEn" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierAddress')" prop="address">
                            <el-input type="textarea" v-model="form.address" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierContact')" prop="contact">
                            <el-input v-model="form.contact" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierAccountBank')" prop="accountBank">
                            <el-input v-model="form.accountBank" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierBankAddress')" prop="bankAddress">
                            <el-input type="textarea" v-model="form.bankAddress" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierAccountNo')" prop="accountNo">
                            <el-input v-model="form.accountNo" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierAccountant')" prop="accountant">
                            <el-input v-model="form.accountant" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierContactWays')" prop="contactWays">
                            <el-input v-model="form.contactWays" placeholder="联系人手机号码" class="search-form-item-input" style="width: 160px" size="mini"  autocomplete="new-password"></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierCata')" prop="supplierCata">
                            <el-select v-model="form.supplierCata"  size="mini" clearable>
                                <el-option value=""></el-option>
                                <el-option
                                  size="mini"
                                  v-for="item in supplierCatas"
                                  :key="item.value"
                                  :label="item.name"
                                  :value="item.value">
                                </el-option>
                            </el-select>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierLevel')" prop="supplierLevel">
                            <el-select v-model="form.supplierLevel"  size="mini" clearable>
                                <el-option value=""></el-option>
                                <el-option
                                  size="mini"
                                  v-for="item in supplierLevels"
                                  :key="item.value"
                                  :label="item.name"
                                  :value="item.value">
                                </el-option>
                            </el-select>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierAt')" prop="supplierAt">
                            <el-select v-model="form.supplierAt"  size="mini" clearable>
                                <el-option value=""></el-option>
                                <el-option
                                  size="mini"
                                  v-for="item in supplierAts"
                                  :key="item.value"
                                  :label="item.name"
                                  :value="item.value">
                                </el-option>
                            </el-select>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.SupplierQuo')" prop="supplierQuo">
                            <el-input   v-model="form.supplierQuo" class="search-form-item-input" style="width: 160px" size="mini" :maxlength="18" autocomplete="new-password"clearable></el-input>
                            <template slot="error" slot-scope="scope">
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                    </el-form>

                </div>
            </el-card>
            <div class="dialogButton">
                <el-button type="primary" size="mini" @click="submit('form')"><i class="fa fa-check"></i> 确定</el-button>
                <el-button size="mini" @click=" cancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import activityService from '@/api/basedata/supplier.js'
    import ui_config from '@/config/ui_config'

    export default {
        name: "EditMatter",
        components: {},
        computed:{
            title:function () {
                let entityName = this.$t('searchFrom.supplier')
                if(this.mode === 'ADD'){return this.$t('searchFrom.add')+entityName}
                else if(this.mode === 'EDIT'){return this.$t('searchFrom.edit')+entityName}
            }
        },
        props:{
            id: {},
            mode:"",
        },
        data() {
            return {
                dialogFormVisible: true,
                search_keys:{},
                currencys:[],
                units:[],
                supplierCatas:[],
                supplierLevels:[],
                supplierAts:[],
                matterTypes:[],
                form: {
                    matterHNRNo: '',
                    matterNo: '',
                    matterType: '',
                    matterCnDec: '',
                    matterEnDec:'',
                    unit: '',
                    scalerRela:'',
                    scalerUnit:'',
                    currency:'',
                    supplierQuo:"",
                },
                /**表单的验证*/
                rules: {
                    supplierCode: [
                        {required: true, message: this.$t('Tips.checkSupplierCode'), trigger: 'blur'},
                        {max: 50, message:  this.$t('Tips.lentghGT50'), trigger: 'blur'},
                        {pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur', message: this.$t('Tips.formatNames'),}
                    ],
                    supplierNameCn: [
                        {required: true, message: this.$t('Tips.checkSupplierNameCn'), trigger: 'blur'},
                        {max: 100, message: this.$t('Tips.lentghGT100'), trigger: 'blur'},
                    ],
                    supplierNameEn: [
                        // {required: true, message: '请填写供应商英文名称', trigger: 'blur'},
                        {max: 100, message: this.$t('Tips.lentghGT100'), trigger: 'blur'},
                    ],
                    supplierDescCn: [
                        // {required: true, message: '请填写供应商中文说明', trigger: 'blur'},
                        {max: 200, message: this.$t('Tips.lentghGT200'), trigger: 'blur'},
                    ],
                    supplierDescEn: [
                        // {required: true, message: '请填写供应商英文说明', trigger: 'blur'},
                        {max: 200, message: this.$t('Tips.lentghGT200'), trigger: 'blur'},
                    ],
                    address: [
                        {max: 200, message: this.$t('Tips.lentghGT200'), trigger: 'blur'},
                    ],
                    contact: [
                        {max: 30, message: this.$t('Tips.lentghGT30'), trigger: 'blur'},
                    ],
                    accountBank: [
                        {max: 20, message: this.$t('Tips.lentghGT20'), trigger: 'blur'},
                    ],
                    bankAddress: [
                        {max: 200, message: this.$t('Tips.lentghGT200'), trigger: 'blur'},
                    ],
                    accountNo: [
                        {max: 30, message: this.$t('Tips.lentghGT30'), trigger: 'blur'},
                    ],
                    accountant: [
                        {max: 30, message: this.$t('Tips.lentghGT30'), trigger: 'blur'},
                    ],
                    contactWays:[
                        {max: 20, message: this.$t('Tips.lentghGT20'), trigger: 'blur'},
                        { required: false,pattern: /^\+?-?[1-9][0-9]*$/, trigger: 'blur',message: this.$t('Tips.checkContactWays'),}
                    ],
                    supplierCata: [
                        // {required: true, message: '请选择供应商分类', trigger: 'change'}
                    ],
                    supplierLevel: [
                        // {required: true, message: '请选择供应商等级', trigger: 'change'}
                    ],
                    supplierAt: [
                        // {required: true, message: '请选择供应商所属', trigger: 'change'}
                    ],
                    supplierQuo:[
                        {pattern:  /^([0-9]*)+\.{0,1}[0-9]{1,2}$/ , trigger: 'blur', message: this.$t('Tips.floatCheck'),}
                    ]

                },
            }
        },
        mounted(){
            this.init()
        },
        methods: {
            async init(){
                await activityService.initData().then(resp =>{
                    this.supplierCatas = resp.data.result.supplierCatas;
                    this.supplierLevels = resp.data.result.supplierLevels;
                    this.supplierAts = resp.data.result.supplierAts;
                }, err => {
                    console.error(err);
                })
                if (this.mode=="EDIT"){
                    await activityService.findSupplierInfo({supplierId: this.id}).then(resp => {
                        this.form = resp.data.result;
                    }, err => {
                        console.error(err);
                    });
                }
            },
            /*确定*/
            submit(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let data={
                            supplierId          : this.id                   || undefined,
                            supplierCode        : this.form.supplierCode    || undefined,
                            supplierNameCn      : this.form.supplierNameCn  || undefined,
                            supplierNameEn      : this.form.supplierNameEn  || undefined,
                            supplierDescCn      : this.form.supplierDescCn  || undefined,
                            supplierDescEn      : this.form.supplierDescEn  || undefined,
                            address             : this.form.address         || undefined,
                            contact             : this.form.contact         || undefined,
                            accountBank         : this.form.accountBank     || undefined,
                            bankAddress         : this.form.bankAddress     || undefined,
                            accountNo           : this.form.accountNo       || undefined,
                            accountant          : this.form.accountant      || undefined,
                            contactWays         : this.form.contactWays     || undefined,
                            supplierCata        : this.form.supplierCata    || undefined,
                            supplierLevel       : this.form.supplierLevel   || undefined,
                            supplierAt          : this.form.supplierAt      || undefined,
                            supplierQuo         : this.form.supplierQuo     || undefined,
                            version             : this.form.version       || undefined,
                        }
                        if(this.mode == 'EDIT'){  //编辑
                            activityService.updateSupplier({...data}).then(resp=>{
                                if (resp.data.code=="201"){
                                    this.$notify({message: resp.data.message, type: "success"});
                                    this.$emit("success");
                                    this.dialogFormVisible = false
                                } else {
                                    this.$notify({message: resp.data.message, type: "error"});
                                }
                            })
                        }else{
                            activityService.addSupplier({...data}).then(resp=>{  //添加
                                if (resp.data.code=="201"){
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
            cancel(){
                this.dialogFormVisible = false
            },
        }
    }
</script>

<style scoped>

</style>