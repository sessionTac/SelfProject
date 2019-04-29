<template>
    <div>
        <el-dialog title="新增用户" :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" style="height: 100%;">
            <el-form ref="form" :model="form" label-width="200px" style="width:600px;margin-left: 130px" :rules="rules" size="mini">
                <el-form-item label="用户名:" prop="userName">
                    <el-input v-model="form.userName" class="search-form-item-input" style="width:60%  " size="mini" ></el-input>
                </el-form-item>

                <el-form-item label="真实姓名:" prop="trueName">
                    <el-input v-model="form.trueName" class="search-form-item-input" style="width: 60%  " size="mini" maxlength="5"></el-input>
                </el-form-item>

                <el-form-item label="电话号码:" prop="phone">
                    <el-input v-model="form.phone" class="search-form-item-input" style="width: 60%  " size="mini" ></el-input>
                </el-form-item>

                <el-form-item label="联系电话:" prop="tel">
                    <el-input v-model="form.tel" class="search-form-item-input" style="width: 60%  " size="mini" ></el-input>
                </el-form-item>

                <el-form-item label="邮箱:" prop="email">
                    <el-input v-model="form.email" class="search-form-item-input" style="width: 60%  " size="mini" ></el-input>
                </el-form-item>

                <el-form-item label="联系地址:" prop="adderss">
                    <el-input type="textarea" class="search-form-item-input" style="width: 60%  " v-model="form.adderss" size="mini" ></el-input>
                </el-form-item>

                <el-form-item label="备注:">
                    <el-input type="textarea" class="search-form-item-input" style="width: 60%  " v-model="form.memo" size="mini" ></el-input>
                </el-form-item>

                <el-form-item class="btn-opt">
                    <el-button type="success" @click="save('form')" class="btn-opt" size="mini">
                        <i class="fa fa-check" aria-hidden="true"></i> 保存
                    </el-button  >
                    <el-button size="mini" @click="dialogFormVisible = false">取消</el-button>
                </el-form-item>

            </el-form>
        </el-dialog>
    </div>

</template>

<script>
    import service from '@/api/users/users.js'
    export default {
        props:{
            editMode: String
        },
        data() {
            return {
                dialogFormVisible: true,
                unit:null,
                search_keys:{},
                form: {
                    compName:null,
                    username: '',
                    password: '',
                    phone: '',
                    tel:'',
                    adderss:'',
                    email: '',
                    memo:'',
                },
                /**表单的验证*/
                rules: {
                    compName: [
                        { required: true, message: '请选择所属单位' },
                    ],
                    address: [
                        { required: true, message: '请填写联系地址', trigger: 'blur' }
                    ],
                    userName: [
                        {  required: true, message: '请填写用户名', trigger: 'blur' }
                    ],
                    trueName: [
                        {  required: true, message: '请填写真实姓名', trigger: 'blur' },
                        { min: 2, max: 4, message: '长度在 2 到 4 个字符', trigger: 'blur' }
                    ],
                    phone: [
                        {  required: true, message: '请输入手机号', trigger: 'blur' },
                        {pattern: /^1[34578]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
                    ],
                    tel: [
                        { required: true, message: '请输入联系电话', trigger: 'blur' },
                        {pattern: /0\d{2,3}-\d{7,8}/, message: '请输入正确的座机号', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur' },
                        { type: 'email', message: '请输入正确的邮箱', trigger: ['blur'] }
                    ]
                }
            }
        },
        methods: {
            cancel(){
                this.$router.push({name:"SettingsUserList"});
            },
            //保存
            save(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {

                        this.$confirm("是否保存","提示", {
                            confirmButtonText:"是",
                            cancelButtonText:"否",
                            type: 'warning',
                            center: true
                        }).then(()=>{
                                debugger
                                let data=this.form
                                service.addUser({...data}).then(resp=>{
                                    if (resp.data==0){
                                        this.$notify({message: "添加失败", type: 'error'});

                                    }else{
                                        this.$notify({message: "添加成功", type: 'success'});
                                        this.$emit("refush");
                                        this.dialogFormVisible = false;

                                    }
                                })
                            }
                        ).catch(()=>{
                            this.internal_activated = true;
                        })//添加
                    } else {
                        this.$notify.error({title: this.$t('M1036')});
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>
