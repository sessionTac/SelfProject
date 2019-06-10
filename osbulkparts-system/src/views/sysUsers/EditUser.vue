<template>
    <div>
        <el-dialog :title='title' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="600px">
            <el-form ref="form" :model="form" label-width="200px"  :rules="rules" size="mini">
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="form.userName" class="search-form-item-input"  style="width: 200px"size="mini" :maxlength="500" ></el-input>
                </el-form-item>
                <template v-if="this.mode === 'ADD'">
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="form.password" class="search-form-item-input" type="password" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="checkPass">
                        <el-input v-model="form.checkPass" class="search-form-item-input" type="password" style="width: 200px" size="mini" :maxlength="64"></el-input>
                    </el-form-item>
                </template>
                <el-form-item label="真实姓名" prop="userRealName">
                    <el-input v-model="form.userRealName" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="30"></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="userPhone">
                    <el-input v-model="form.userPhone" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="30"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="userMail">
                    <el-input v-model="form.userMail" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="30"></el-input>
                </el-form-item>
                <el-form-item label="用户类型" >
                    <el-select v-model="form.userType" placeholder="请选择">
                        <el-option
                          v-for="item in options.userType"
                          :key="item.value"
                          :label="item.name"
                          :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用户状态" >
                    <el-select v-model="form.userStatus" placeholder="请选择">
                        <el-option
                          v-for="item in options.userStatus"
                          :key="item.value"
                          :label="item.name"
                          :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer">
                <el-button type="primary" size="mini" @click="submit('form')"><i class="fa fa-check"></i> 确定</el-button>
                <el-button size="mini" @click=" cancel">取消</el-button>
                <el-button v-if="this.mode === 'EDIT'" size="mini" @click="resetPass">密码重置</el-button>
            </span>
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
                let entityName = '用户'
                if(this.mode === 'ADD'){return'新增'+entityName}
                else if(this.mode === 'EDIT'){return '编辑'+entityName}
            }
        },
        props:{
            id: {},
            mode:String
        },
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.form.checkPass !== '') {
                        this.$refs.form.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.form.password) {
                    callback(new Error('两次输入密码不一致!'));
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
                        {  required: true, message: '请填写用户名', trigger: 'blur' },
                        {max: 30, message: '长度不超过30个字符', trigger: 'blur' },
                        { pattern: /^[a-z|A-Z|0-9|_]+$/, trigger: 'blur',message: '请输入英文数字下划线',}
                    ],
                    userRealName: [
                        {  required: true, message: '请填写真实姓名', trigger: 'blur' },
                        {max: 30, message: '长度不超过30个字符', trigger: 'blur' }
                    ],
                    password:[
                        { validator: validatePass, trigger: 'blur' },
                    ],
                    checkPass:[
                        { validator: validatePass2, trigger: 'blur' },
                    ],
                    userPhone:[
                        { required: false,pattern: /^1(3|4|5|7|8)\d{9}$/, trigger: 'blur',message: '请输入正确的手机号',}
                    ],
                    userMail: [
                        { required: false, type: 'email', message: '请输入正确的邮箱', trigger: ['blur'] }
                    ]
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
                this.$confirm("确定重置密码吗？", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
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
    >>>.el-dialog__body {
        padding: 0px 0px;
        color: #606266;
        font-size: 14px;
    }

    >>> .el-dialog__footer {
        /*margin-top: -30px;*/
        text-align: center;
    }
</style>
