<template>
  <!-- 这是登录页面 -->
  <div class="userlogin">
    <!-- rules是表单校验，检查你是否填了值 -->
    <el-form ref="form" :model="form" :rules="rules">
      <!-- ref是校验用的 -->
      <el-form-item prop="customerId">
        <!-- prop是表单校验的一个东西 -->
        <!-- model绑定的form是一个对象，里面的这两个属性分别绑定一个输入 -->
        <el-input v-model="form.customerId" placeholder="请输入账号" clearable></el-input>
      </el-form-item> <!-- clearable是可清空的意思 -->
      <el-form-item prop="password">
        <el-input v-model="form.password" placeholder="请输入密码" type="password" clearable></el-input>
      </el-form-item>
    </el-form>
    <div>
      <el-button @click="login" type="success" style="width: 100%;" v-loading="loading">{{title}}</el-button>
    </div>
    <p></p>
    <div>
      <el-button @click="register" type="success" style="width: 100%;">{{title2}}</el-button>
    </div>
    <p></p>
    <div>
      <el-button @click="tran" type="success" style="width: 100%;">{{title3}}</el-button>
    </div>
  </div>

</template>

<script>
  export default {
    name: "UserLogin",
    data() {
      return {
        //绑定的form对象
        form: {},
        //加载动画
        loading: false,
        title: '登录',
        title2: '注册',
        title3: '前往管理员登录',
        rules: { //rules对象的属性都是prop的值（这个值对应着后端的User的UserId）
          customerId: [{
            required: true,
            message: '请输入账号', //触发的东西
            trigger: 'blur' //触发时机，这里是离开焦点
          }],
          password: [{
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }]
        }
      }
    },

    methods: {
      login: function() {
        this.$refs['form'].validate((valid) => { //form对应这ref那个值
          if (valid) { //如果校验成功则提交
            this.loading = true; //点击就让加载动画开始
            this.title = "登录中...";
            this.$axios({ //axios是用来请求服务的，记得下载插件并且引用这个并且挂在vue上
              method: 'post',
              url: 'api/lease/customer/cuslogin',
              data: this.form //提交的数据，输入给了谁，一般就提交谁
            }).then(res => { //res这个参数可以理解为一个Object。 res.data指服务器返回的内容。res参数还有其他的属性
              if (res.data.success) { //实际调用commit函数的时候，是经过mutations里面相应的函数进行赋值的

                this.$router.push('UserHome') //跳到Home主页
                /* console.info("登录成功")  //这是在控制台输出的*/
              } else {
                this.title = "登录";
                this.$message.error(res.data.msg); //账号密码错误时候，后端传的msg
              }
              this.loading = false; //不论成功还是失败，都要关闭加载动画
            }).catch(err => {
              this.loading = false;
              this.title = "登录";
              console.info(err) //后台报异常输出err对象
            });
          }
        });
      },

      register() {
        this.$router.push('Register') //跳到注册页面
      },

      tran() {
        this.$router.push('Login') //Login主页
      }


    }
  }
</script>

<style>
  .userlogin {
    width: 260px;
    margin: 0 auto;
    margin-top: 20%;
    /* 垂直居中 */
  }
</style>
