<template>
    <div class="home-layer flex flex-v back-office">
        <div class="flex" style="height:50px;width:100%;background-color:white;z-index:99999999;box-shadow: 0 1px 5px #666; ">
            <div class="flex-1">
                <div class="flex" style="width:20%;margin-left:15%;height:50px;">
                    <div class="" style='width:100%;background-color:rgba(220,255,207,0);text-align:center;position:relative;'>
                        <img style="width: 80%;z-index: 1300;display:inline-block;position:absolute;top:50%;left:50%;transform:translateY(-50%) translateX(-50%);" src="../../static/images/login/mainLogo.png" />
                    </div>
                </div>
            </div>
            <div class="" style="width:15px;height:100%;"></div>
        </div>
        <div class="register-bg flex flex-pack-center flex-align-center flex-1">
            <div class="form-div" style="">
                <form id="form" class="form-horizontal flex flex-v flex-align-center" method="post">
                    <div class=" flex flex-v flex-align-center" style="height:240px;width:100%;">
                        <div class="" style="font-size:30px;color:#333;">
                            用户登录
                        </div>
                        <div class="form-group" style="width:100%;">
                            <div class="flex flex-pack-center" style="width:100%;height:50px;">
                                <div style="height:48px;width:300px;margin-top:-1px;position:relative;margin-left:-55px;">
                                    <div class="flex flex-pack-center flex-align-center" style="height:50px;width:50px;position:absolute;left:3px;">
                                        <img src="../../static/images/login/user3.png" style="height: 70%;" />
                                    </div>
                                    <input type="text" class="form-control" name="username" v-model="username" placeholder="用户名" autocomplete="username" maxlength="20" style="height:100%;padding-left:55px;">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="flex flex-pack-center" style="width:100%;height:50px;">
                                <div style="height:48px;width:300px;margin-top:-1px;position:relative;margin-left:-55px;">
                                    <div class="flex flex-pack-center flex-align-center" style="height:50px;width:50px;position:absolute;left:3px;">
                                        <img src="../../static/images/login/password3.png" style="height: 70%;" />
                                    </div>
                                    <input type="password" class="form-control" v-model="password" name="password" placeholder="密码" autocomplete="current-password" maxlength="20" style="height:100%;padding-left:55px;">
                                </div>
                            </div>
                        </div>
                        <div>
                            <a  href="" download >忘记密码？</a>
                        </div>
                    </div>
                    <div style="background-color:#f9f9f9;height:130px;width:100%;border-top:1px solid #dedede;">
                        <div class="form-group" style="width: 100%;padding-top:10px;" align="center">
                            <button id="login-submit" type="button" style="" @click="usersLogin">登&nbsp;录</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <select-role-dialog  v-bind.sync="selectRoleDialogState" v-if="selectRoleDialogState.activated" @roleSelected="roleSelected" @cancelled="cancelled" />

    </div>

</template>

<script>

    import passportService from '@/api/passport'
    import SelectRoleDialog from "./SelectRoleDialog"


    import {
        Notification
    } from 'element-ui'
    // import subject from "@/utils/Subject"

    export default {

        components: {
            SelectRoleDialog
        },
        data() {
            return {
                username: 'security',
                password: '123456',
                registerReadmeDialogState : {},
                selectRoleDialogState : {},
            }
        },

        methods: {
            roleSelected({console_user_token}) {
                localStorage.console_user_token = console_user_token;
                this.$router.push({ name: 'QuickTokenLogin', query: { redir: this.$route.query.redir || undefined }});
            },

            async usersLogin() {

                try {
                    let loginRet = await passportService.login({username: this.username, password: this.password, platform: '2',});

                    let trueName      = loginRet.trueName;
                    let roles         = loginRet.roles;
                    let tempToken     = loginRet.token;

                    this.selectRoleDialogState = {activated: true, trueName, tempToken, roles};

                } catch (err) {
                    // let error = err;
                    //  debugger
                    console.log(err);
                    // if (err.response && err.response.status === 401) return;
                    if (err.response && err.response.status === 401){
                        Notification.error({title:"账号或者密码错误"})
                    }else {
                        Notification.error({title:"网络异常"})
                    }
                    // console.error('登录操作异常', err);
                    // Notification.error({message:err.message})
                    // Notification.error({title:"网络异常"})
                }

            },
            cancelled() {
                this.username = '';
                this.password = '';
            },

            cancel() {
                this.$router.push({
                    name: 'SystemHomeRootIndex'
                });
            }
        },

    }
</script>

<style scoped>
    .navbar-header,
    .navbar-brand,
    .navbar-sarnath {
        height: 70px;
    }

    .navbar-nav>li>a {
        padding-top: 25px;
        padding-bottom: 25px;
    }

    .navbar.navbar-default.navbar-sarnath .navbar-quicklinks li>a {
        padding-left: 15px;
        padding-right: 15px;
    }

    .navbar-default .navbar-nav>li>a {
        color: #333333;
    }

    .navbar-default .navbar-nav>li>a:hover {
        color: #333333;
        background-color: rgba(0, 0, 0, .05);
    }

    .navbar-default .navbar-nav>li>a:active,
    .navbar-default .navbar-nav>li>a:visited {
        color: #333333;
    }

    .nav>li>a {
        padding-left: 30px;
        padding-right: 30px;
    }

    .navbar-right {
        margin-right: 0;
    }

    .navbar.navbar-default.navbar-sarnath .navbar-right>li>a.nav-sign-up {
        /* background-color: #08baff; */
        color: #fff;
    }

    .navbar.navbar-default.navbar-sarnath .navbar-right>li>a.nav-sign-up:hover {
        background-color: #0a8ec1;
    }

    .navbar.navbar-default.navbar-sarnath .dropdown-menu {
        background-color: #eff0ef;
        border: 0;
        border-radius: 0;
        -webkit-box-shadow: 0 3px 3px rgba(0, 0, 0, 0.1);
        box-shadow: 0 3px 3px rgba(0, 0, 0, 0.1);
        margin-top: 0;
        padding-bottom: 0;
        padding-top: 0;
        top: 70px;
        width: 100%;
    }

    .dropdown-menu>li>a {
        display: block;
        padding: 3px 20px;
        clear: both;
        font-weight: normal;
        line-height: 1.42857143;
        color: #404040;
        white-space: nowrap;
        height: auto;
    }

    .navbar.navbar-default.navbar-sarnath .dropdown-menu>li>a {
        font-size: 13px;
        padding: 15px;
        text-transform: uppercase;
    }

    .navbar.navbar-default.navbar-sarnath .dropdown-menu>li>a:active,
    .navbar.navbar-default.navbar-sarnath .dropdown-menu>li>a:hover {
        background-color: #e8eae8;
        color: black;
    }

    #carousel-generic {
        width: 100%;
        height: 465px;
        overflow: hidden;
    }

    #carousel-generic .item {
        height: 465px;
        text-align: center;
    }

    #carousel-generic .item img {
        height: 100%;
        bottom: 0;
        position: absolute;
        right: 0;
    }

    #products {
        height: 686px;
    }

    #platform {
        height: 525px;
    }

    #features {
        height: 699px;
    }

    #help {
        background-color: #373534;
        color: #fff;
        font-weight: 300;
        padding-bottom: 20px;
        height: 406px;
    }

    #footer {
        background-color: #010101;
        font-size: 12px;
        height: 60px;
        padding: 15px 0;
        width: 100%;
    }

    .footer a {
        color: #DEE0DC;
        margin-left: 30px;
        position: relative;
        top: 7px;
        text-decoration: none;
    }

    .footer a:hover {
        text-decoration: none;
    }

    #police {}

    #police a img {
        float: left;
        position: absolute;
        top: 2px;
        left: 0;
    }

    #police a {
        position: relative;
        padding-left: 16px;
    }

    #police a p {
        float: left;
        margin-bottom: 0;
        color: #939393;
    }

    h2 {
        font-size: 26px;
        margin-top: 60px;
    }

    #products .lead {
        font-size: 16px;
        margin-bottom: 50px;
    }

    #products .col-sm-4 {
        padding: 0 7.5px 15px 7.5px;
    }

    #products .product,
    #products .product {
        margin-top: 15px;
        padding: 30px 45px;
    }

    .bg-gray {
        background-color: #e7e7e7;
    }

    .text-center {
        text-align: center;
    }

    h3 {
        margin-top: 0;
        margin-bottom: 10px;
        color: #08baff;
        font-size: 15px;
    }

    #products .product img {
        height: 112px;
    }

    #products .product h5 {
        font-size: 16px;
    }

    .bg-gray-dark {
        background-color: #373534;
        color: #fff;
        font-weight: 300;
    }

    .bg-gray-dark h3 {
        color: #42dcbd;
    }

    #products .product.bg-gray-dark {
        padding: 45px 45px;
        margin-top: 0px;
    }

    #products .product .description {
        line-height: 1.42857;
        margin-bottom: 20px;
    }

    #platform .container {
        padding-top: 50px;
        padding-bottom: 60px;
        text-align: center;
    }

    #platform img {
        height: 200px;
    }

    .navbar-default .navbar-text {
        line-height: 40px;
        vertical-align: middle;
        color: black;
        font-size: 16px;
    }

    .register-bg {
        width: 100%;
        height: 800px;
        /*background: url('/Images/login/bg_03.png') no-repeat;*/
        background-size: auto 120%;
        background-position-x: 50%;
    }

    body {
        background-color: white;
    }

    #footer {
        background-color: white;
        border-top: 1px solid #e0e0e0;
    }

    .footer a,
    #police a p {
        color: #333;
    }

    .control-label {
        color: white;
        font-weight: normal;
    }

    .form-control-note {
        margin-top: 5px;
        margin-bottom: 0;
        font-size: 12px;
        color: rgba(255, 255, 255, 0.6);
    }

    .form-control-note a {
        color: white;
    }

    .form-control-note a:hover {
        color: white;
    }

    .text-primary {
        color: #08baff;
    }

    .error {
        font-weight: normal;
        color: #d82c2c;
    }



    /*每行输入框*/

    .form-control {
        height: 50px;
        width: 300px;
        font-size: 18px;
        border: solid 1px #ccc;
        box-shadow: 0 0 3px #ccc;
        background: transparent;
        border-radius: 0;
        color: #333;
    }



    /*图片*/

    .input-group-addon img {
        width: 50px;
        height: 50px;
    }



    /*span*/

    .input-group-addon {
        background-color: white;
        border-radius: 0;
        border: 0;

        border: solid 1px red;
        height: 50px;
        width: 50px;
    }

    .form-control:focus {
        border-color: white;
        /* outline: none; */
        /* box-shadow: none; */
    }

    #form {
        margin-top: 50px;
    }

    .form-group {
        margin-top: 30px;
    }


    input[placeholder] {
        color: #999 !important;
        font-size: 18px;
    }

    input::-webkit-input-placeholder {
        color: #999 !important;
        font-size: 18px;
    }

    input:-moz-placeholder {
        color: #999 !important;
        font-size: 18px;
    }



    /*登录*/

    #login-submit {
        font-size: 26px;
        font-weight: 900;
        text-shadow: 1px 1px 1px #a8a7a7;
        padding: 5px;
        border-radius: 0;
        color: #08baff;
        font-family: Microsoft JhengHei;
        width: 220px;
        background-color: white;
        border: solid 1px #ccc;
        box-shadow: 0 0 3px #ccc;
        cursor: pointer;
    }



    /*取消*/

    #login-cancel {
        font-size: 26px;
        font-weight: 900;
        text-shadow: 1px 1px 1px #a8a7a7;
        padding: 5px;
        border-radius: 0;
        color: #08baff;
        font-family: Microsoft JhengHei;
        width: 160px;
    }


    #login-submit:hover {
        box-shadow: 0 1px 2px rgba(0, 0, 0, .3);
    }

    input {
        background-color: #666;
        border: 1px solid black;
    }

    input:-webkit-autofill,
    input:-webkit-autofill:hover,
    input:-webkit-autofill:focus,
    input:-webkit-autofill::selection {
        /* -webkit-box-shadow: 0 0 0 1000px #08baff inset; */
        -webkit-box-shadow: 0 0 0 1000px #ffffff inset;
        -webkit-text-fill-color: #333;
    }

    .input-group-addon .glyphicon {
        font-size: 30px;
        color: #04A6E2;
    }

    select option {
        color: #04A6E2;
    }

    .home-layer.back-office {
        width: 100%;
        height: 100%;
        margin-top: 0px;
        background-repeat: no-repeat;
        background-size: 100% 100%;
        background-image: url("../../static/images/login/backgroud-light.jpg");
        /* background-image: url("../../static/mockdata/system_home/user_login.jpg"); */
    }

    .form-div {
        width: 500px;
        height: 420px;
        background-color: white;
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 0 5px #999;
    }

    .input-group {
        border: solid 1px red;
    }
</style>