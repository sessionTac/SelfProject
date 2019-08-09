<template>
    <div>
        <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="700px">
            <el-card>
                <div class="dialogStyle" style="display: flex;flex-direction: column">
                    <el-form ref="form" style="flex: 5" :model="form" class="search-form search-form-normal" label-width="100px"  :rules="rules" size="mini">
                        <el-form-item :label="$t('pageTable.userName')" prop="userName" style="">
                            <el-input v-model="form.userName" class="search-form-item-input"  style="width: 200px"size="mini" :maxlength="500" ></el-input>
                            <template slot="error" slot-scope="scope"  >
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <template v-if="this.mode === 'ADD'">
                            <el-form-item :label="$t('pageTable.password')" prop="password">
                                <el-input v-model="form.password" class="search-form-item-input" type="password" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"></el-input>
                                <template slot="error" slot-scope="scope"  >
                                    <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                                </template>
                            </el-form-item>
                            <el-form-item :label="$t('pageTable.confirmPassword')" prop="checkPass">
                                <el-input v-model="form.checkPass" class="search-form-item-input" type="password" style="width: 200px" size="mini" :maxlength="64"></el-input>
                                <template slot="error" slot-scope="scope"  >
                                    <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                                </template>
                            </el-form-item>
                        </template>
                        <el-form-item :label="$t('pageTable.trueName')" prop="userRealName">
                            <el-input v-model="form.userRealName" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="30"></el-input>
                            <template slot="error" slot-scope="scope"  >
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.tel')" prop="userPhone">
                            <el-input v-model="form.userPhone" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="30"></el-input>
                            <template slot="error" slot-scope="scope"  >
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('searchFrom.email')" prop="userMail">
                            <el-input v-model="form.userMail" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="30"></el-input>
                            <template slot="error" slot-scope="scope"  >
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.userType')" prop="userType">
                            <el-select v-model="form.userType" style="width: 200px" :placeholder="$t('pageTable.choose')">
                                <el-option
                                  v-for="item in options.userType"
                                  :key="item.value"
                                  :label="item.name"
                                  :value="item.value">
                                </el-option>
                            </el-select>
                            <template slot="error" slot-scope="scope"  >
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                        <el-form-item :label="$t('pageTable.userState')" prop="userStatus">
                            <el-select v-model="form.userStatus" style="width: 200px" :placeholder="$t('pageTable.choose')">
                                <el-option
                                  v-for="item in options.userStatus"
                                  :key="item.value"
                                  :label="item.name"
                                  :value="item.value">
                                </el-option>
                            </el-select>
                            <template slot="error" slot-scope="scope"  >
                                <div style="float: right;margin-right: 100px;font-size: 10px;color: red">{{scope.error}}</div>
                            </template>
                        </el-form-item>
                    </el-form>

                </div>
            </el-card>
            <div class="dialogButton">
                <el-button type="primary" size="mini" @click="submit('form')"><i class="fa fa-check"></i> {{$t('searchFrom.confirm')}}</el-button>
                <el-button size="mini" @click=" cancel">{{$t('searchFrom.cancel')}}</el-button>
                <el-button v-if="this.mode === 'EDIT'" size="mini" @click="resetPass">{{$t('searchFrom.passwordReset')}}</el-button>
            </div>

            <!--<EditPasswordDialog v-bind.sync="edit_pass"  v-if="edit_pass.activated" ></EditPasswordDialog>-->
        </el-dialog>
    </div>

</template>

<script>
    import service from '@/api/users/users'
    import EditPasswordDialog from './EditUserPassword'
    export default {
        components:{EditPasswordDialog},
        computed:{
            title:function () {
                let entityName = this.$t('searchFrom.user')
                if(this.mode === 'ADD'){return this.$t('searchFrom.add')+entityName}
                else if(this.mode === 'EDIT'){return this.$t('searchFrom.edit')+entityName}
            }
        },
        props:{
            id: {},
            mode:String
        },
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error(this.$t('Tips.password')));
                } else {
                    if (this.form.checkPass !== '') {
                        this.$refs.form.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error( this.$t('Tips.passwordAgain')));
                } else if (value !== this.form.password) {
                    callback(new Error(this.$t('Tips.passwordError')));
                } else {
                    callback();
                }
            };
            return {
                disabled : false,
                edit_pass : {},
                regions:[],
                dialogFormVisible: true,
                search_keys:{},
                options:{

                },
                form: {
                    userName: '',
                    trueName: '',
                    password: '',
                    checkPass: '',
                    tel:'',
                    email: '',
                    memo:'',
                    transferorEntity : null,
                    orgEntity : null,
                    region : null,
                },
                /**表单的验证*/
                rules: {
                    userName: [
                        {  required: true, message: this.$t('Tips.userNameCheck'), trigger: 'blur' },
                        {max: 30, message:this.$t('Tips.lentghGT30'), trigger: 'blur' },
                        // { pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur',message: '请输入英文数字下划线',}
                    ],
                    userRealName: [
                        {  required: true, message:  this.$t('Tips.userTrueNameCheck'), trigger: 'blur' },
                        {max: 30, message:this.$t('Tips.lentghGT30'), trigger: 'blur' }
                    ],
                    password:[
                        { validator: validatePass, trigger: 'blur' },
                    ],
                    checkPass:[
                        { validator: validatePass2, trigger: 'blur' },
                    ],
                    userPhone:[
                        { required: false,pattern: /^1(3|4|5|7|8)\d{9}$/, trigger: 'blur',message: this.$t('Tips.phoneCheck'),}
                    ],
                    userMail: [
                        {required: false, pattern: /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/, message: this.$t('Tips.emailCheck'), trigger: ['blur']}
                    ],
                    userType: [
                        { required: true, message: this.$t('pageTable.choose')+" "+this.$t('pageTable.userType'), trigger: 'change' }
                    ],
                    userStatus: [
                        { required: true, message: this.$t('pageTable.choose')+" "+this.$t('pageTable.userState'), trigger: 'change' }
                    ],
                },
            }
        },

        mounted(){
            this.init()
        },
        methods: {

            async init(){
                await service.findOptions().then(resp=>{
                    this.options=resp.data.result
                });
                if(this.mode == 'EDIT'){
                    service.selectUserById(this.id).then(resp => {
                        this.form = resp.data.result ;
                        this.form.userType=this.form.userType+"";
                        this.form.userStatus=this.form.userStatus+"";

                    })
                }
            },
            /*确定*/
            submit(formName){
                this.$refs[formName].validate(async (valid) => {
                    if (valid) {
                        let data={
                            userId       : this.id            || undefined,
                            userName : this.form.userName || undefined,
                            userRealName : this.form.userRealName || undefined,
                            password : this.form.password || undefined,
                            userMail :  this.form.userMail || undefined,
                            userPhone: this.form.userPhone || undefined,
                            userType      : this.form.userType      || undefined,
                            userStatus     : this.form.userStatus     || undefined,
                            version   : this.form.version || undefined,
                        }
                        if(this.mode == 'EDIT'){  //编辑
                            let check = await service.checkUserInfo({...data,checkFlag:"edit"});
                            if (check.data.code== "201") {
                                service.updateUser({...data}).then(resp=>{
                                    if(resp.data.code == "201"){
                                        this.$notify({message: resp.data.message, type: 'success'});
                                        this.$emit("success");
                                        this.dialogFormVisible = false
                                    }else {
                                        this.$notify({message: resp.data.message, type: 'error'});
                                    }
                                })
                            }else {
                                this.$notify({message: check.data.message, type: 'error'});
                            }
                        }else{
                            let check = await service.checkUserInfo({...data,checkFlag:"add"});
                            if (check.data.code== "201") {
                                service.addUser({...data}).then(resp=>{  //添加
                                    if(resp.data.code == "201"){
                                        this.$notify({message: resp.data.message, type: 'success'});
                                        this.$emit("success");
                                        this.dialogFormVisible = false
                                    }else {
                                        this.$notify({message: resp.data.message, type: 'error'});
                                    }
                                })
                            }else {
                                this.$notify({message: check.data.message, type: 'error'});
                            }
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


            //重置密码
            resetPass(){
                this.$confirm(this.$t("Tips.passwordReset"), this.$t("Tips.tips"), {
                    confirmButtonText: this.$t("Tips.yes"),
                    cancelButtonText: this.$t("Tips.no"),
                    type: 'warning',
                    center: true
                }).then(() => {
                    let data ={
                        userId : this.id || undefined,
                    };
                    service.reset({...data}).then(resp =>{
                        if (resp.data.code=="201"){
                            this.$notify({message: resp.data.message, type: "success"});
                            this.dialogFormVisible = false
                        } else {
                            this.$notify({message: resp.data.message, type: "error"});
                        }

                    })
                  }
                ).catch(() => {
                    this.internal_activated = true;
                })//删除

                // this.edit_pass = {activated:true,userName:this.form.userName,id:this.id}

            }
        }
    }
</script>

<style scoped>

</style>
