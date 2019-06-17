<template>
    <div>
        <el-dialog  :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="600px">
            <el-card>
                <div class="dialogStyle" style="display: flex;flex-direction: column">
                    <el-form class="search-form search-form-normal" ref="form" style="flex: 5"  :model="form" label-width="200px"  size="mini">
                        <el-form-item label="供应商代码" prop="supplierCode">
                            <el-input v-model="form.supplierCode" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="供应商中文名称" prop="supplierNameCn">
                            <el-input type="textarea" v-model="form.supplierNameCn" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="供应商英文名称" prop="supplierNameEn">
                            <el-input type="textarea" v-model="form.supplierNameEn" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="供应商中文说明" prop="supplierDescCn">
                            <el-input type="textarea" v-model="form.supplierDescCn" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="供应商英文说明" prop="supplierDescEn">
                            <el-input type="textarea" v-model="form.supplierDescEn" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="地址" prop="address">
                            <el-input type="textarea" v-model="form.address" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="联系人" prop="contact">
                            <el-input v-model="form.contact" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="开户银行" prop="accountBank">
                            <el-input v-model="form.accountBank" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="开户银行地址" prop="bankAddress">
                            <el-input type="textarea" v-model="form.bankAddress" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="帐号信息" prop="accountNo">
                            <el-input v-model="form.accountNo" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="账户人" prop="accountant">
                            <el-input v-model="form.accountant" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"clearable></el-input>
                        </el-form-item>
                        <el-form-item label="联系方式" prop="contactWays">
                            <el-input v-model="form.contactWays" placeholder="联系人手机号码" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="11" autocomplete="new-password"></el-input>
                        </el-form-item>
                        <el-form-item label="供应商分类" prop="supplierCata">
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
                        </el-form-item>
                        <el-form-item label="供应商等级" prop="supplierLevel">
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
                        </el-form-item>
                        <el-form-item label="供应商所属" prop="supplierAt">
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
                let entityName = '供应商信息'
                if(this.mode === 'ADD'){return'新增'+entityName}
                else if(this.mode === 'EDIT'){return '编辑'+entityName}
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
                },
                /**表单的验证*/
                // rules: {
                //     userName: [
                //         {  required: true, message: '请填写用户名', trigger: 'blur' },
                //         {max: 30, message: '长度不超过30个字符', trigger: 'blur' },
                //         { pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur',message: '请输入英文数字下划线',}
                //     ],
                //     trueName: [
                //         {  required: true, message: '请填写真实姓名', trigger: 'blur' },
                //         {max: 30, message: '长度不超过30个字符', trigger: 'blur' }
                //     ],
                //     password:[
                //         { validator: validatePass, trigger: 'blur' },
                //     ],
                //     checkPass:[
                //         { validator: validatePass2, trigger: 'blur' },
                //     ],
                //     tel:[
                //         { required: false,pattern: /^1(3|4|5|7|8)\d{9}$/, trigger: 'blur',message: '请输入正确的手机号',}
                //     ],
                //     email: [
                //         { required: false, type: 'email', message: '请输入正确的邮箱', trigger: ['blur'] }
                //     ]

                // },
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