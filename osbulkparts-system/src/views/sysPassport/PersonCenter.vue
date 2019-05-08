<template>
    <div>
        <el-dialog title='个人信息' :visible.sync="dialogFormVisible" @closed="$emit('update:activated', false)" width="600px">
            <el-form ref="form" :model="form" label-width="200px" size="mini">
                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="form.userName" class="search-form-item-input"  style="width: 200px"size="mini" :maxlength="500" disabled></el-input>
                </el-form-item>
                <template>
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="form.password" class="search-form-item-input" type="password" style="width: 200px" size="mini" :maxlength="64" autocomplete="new-password"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="checkPass">
                        <el-input v-model="form.checkPass" class="search-form-item-input" type="password" style="width: 200px" size="mini" :maxlength="64"></el-input>
                    </el-form-item>
                </template>
                <el-form-item label="真实姓名" prop="trueName">
                    <el-input v-model="form.trueName" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="30"></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="tel">
                    <el-input v-model="form.tel" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="11"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" class="search-form-item-input"  style="width: 200px" size="mini" :maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="地区" prop="region">
                    <el-cascader placeholder="江西省" :change-on-select="true"  clearable  size="mini"   :options="regions"   v-model="form.region"></el-cascader>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" class="search-form-item-input"  style="width: 200px" v-model="form.memo" size="mini" :maxlength="200"></el-input>
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
    export default {
        name: "PersonCenter",
        props:{
            id: {}
        },
        data() {
            return {
                disabled: false,
                edit_pass: {},
                regions: [],
                dialogFormVisible: true,
                search_keys: {},
                form: {
                    userName: '',
                    trueName: '',
                    password: '',
                    checkPass: '',
                    tel: '',
                    email: '',
                    memo: '',
                    transferorEntity: null,
                    orgEntity: null,
                    region: null,
                },
            }
        },
        methods:{
            cancel(){
                this.dialogFormVisible = false
            },
        }
    }
</script>

<style scoped>

</style>