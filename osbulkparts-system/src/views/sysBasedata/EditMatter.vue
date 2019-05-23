<template>
    <div>
        <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="600px">
            <el-form ref="form" :model="form" label-width="200px"  size="mini">
                <el-form-item label="物料HNR号" prop="matterHNRNo">
                    <el-input v-model="form.matterHNRNo" class="search-form-item-input"  style="width: 200px"size="mini" :maxlength="500" ></el-input>
                </el-form-item>
                <el-form-item label="物料专用号" prop="matterNo">
                    <el-input v-model="form.matterNo" class="search-form-item-input" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"></el-input>
                </el-form-item>
                <el-form-item label="物料类别" prop="matterType">
                    <el-select v-model="form.matterType"  size="mini">
                        <el-option value=""></el-option>
                        <el-option
                                size="mini"
                                v-for="item in matterTypes"
                                :key="item.code"
                                :label="item.name"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="物料中文描述" prop="matterCnDec">
                    <el-input v-model="form.matterCnDec" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="200"></el-input>
                </el-form-item>
                <el-form-item label="物料英文描述" prop="matterEnDec">
                    <el-input v-model="form.matterEnDec" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="200"></el-input>
                </el-form-item>
                <el-form-item label="单位" prop="unit">
                    <el-select v-model="form.unit"  size="mini">
                        <el-option value=""></el-option>
                        <el-option
                                size="mini"
                                v-for="item in units"
                                :key="item.code"
                                :label="item.name"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="换算关系" prop="scalerRela">
                    <el-input v-model="form.scalerRela" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="换算后单位">
                    <el-input class="search-form-item-input"  style="width: 200px" v-model="form.scalerUnit" size="mini" :maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="币种" prop="currency">
                    <el-select v-model="form.currency"  size="mini">
                        <el-option value=""></el-option>
                        <el-option
                                size="mini"
                                v-for="item in currencys"
                                :key="item.code"
                                :label="item.name"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button type="primary" size="mini" @click="submit('form')"><i class="fa fa-check"></i> 确定</el-button>
                <el-button size="mini" @click=" cancel">取消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import activityService from '@/api/basedata/matter.js'
    import ui_config from '@/config/ui_config'

    export default {
        name: "EditMatter",
        props:{
            id: {}
        },
        data() {
            return {
                title:"物料主数据信息编辑",
                dialogFormVisible: true,
                search_keys:{},
                currencys:[],
                units:[],
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
                await activityService.init().then(resp =>{
                    this.currencys = resp.data.currencys;
                    this.units = resp.data.units;
                    this.matterTypes = resp.data.matterTypes;
                }, err => {
                    console.error(err);
                });
                await activityService.init().then(resp =>{
                    this.form = resp.data.list[0];
                }, err => {
                    console.error(err);
                });
            },
            /*确定*/
            submit(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let data={
                            id       : this.id            || undefined,
                            userName : this.form.userName || undefined,
                            trueName : this.form.trueName || undefined,
                            password : this.form.password || undefined,
                            tel      : this.form.tel      || undefined,
                            email    : this.form.email    || undefined,
                            memo     : this.form.memo     || undefined,
                            transferorNo : this.form.transferorEntity && this.form.transferorEntity.transferorNo || undefined,
                            orgNo    : this.form.orgEntity && this.form.orgEntity.orgNo  || undefined,
                            cityAreaCode   : this.form.region && this.form.region[0]   || undefined,
                            countyAreaCode : this.form.region && this.form.region[1]   || undefined,
                        }
                        // if(this.mode == 'EDIT'){  //编辑
                        //     service.updateUser({...data}).then(resp=>{
                        //         this.$notify({message: resp.data.msg, type: resp.data.type});
                        //         if(resp.data.type == "success"){
                        //             this.$emit("success");
                        //             this.dialogFormVisible = false
                        //         }
                        //     })
                        // }else{
                        //     service.addUser({...data}).then(resp=>{  //添加
                        //         this.$notify({message: resp.data.msg, type: resp.data.type});
                        //         if(resp.data.type == "success"){
                        //             this.$emit("success");
                        //             this.dialogFormVisible = false
                        //         }
                        //     })
                        // }

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