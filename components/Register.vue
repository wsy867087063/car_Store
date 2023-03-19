<template>
  <!-- 这是登录页面 -->
  <div class="register">
    <!-- rules是表单校验，检查你是否填了值 -->
    <el-form ref="form" :model="form" :rules="rules">
      <!-- ref是校验用的 -->
      <el-form-item prop="custumer_id">
        <!-- prop是表单校验的一个东西 -->
        <!-- model绑定的form是一个对象，里面的这两个属性分别绑定一个输入 -->
        <el-input v-model="form.custumerId" placeholder="手机号码" clearable></el-input>
      </el-form-item> <!-- clearable是可清空的意思 -->
      <el-form-item prop="code">
        <el-input v-model="form.code" placeholder="手机验证码" clearable></el-input>
      </el-form-item>
      <el-form-item prop="name">
        <el-input v-model="form.name" placeholder="姓名" type="password" clearable></el-input>
      </el-form-item>
      <el-form-item prop="id">
        <el-input v-model="form.id" placeholder="身份证号码" clearable></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="form.password" placeholder="输入密码" type="password" clearable></el-input>
      </el-form-item>
      <el-form-item prop="Respassword">
        <el-input v-model="form.Respassword" placeholder="确认密码" type="password" clearable></el-input>
      </el-form-item>
      <el-form-item prop="contact">
        <el-input v-model="form.contact" placeholder="紧急联系人姓名" clearable></el-input>
      </el-form-item>
      <el-form-item prop="ctel">
        <el-input v-model="form.ctel" placeholder="紧急联系人手机号" clearable></el-input>
      </el-form-item>
    </el-form>
    <div>
      <el-button @click="submit" type="success" style="width: 100%;">{{title}}</el-button>
    </div>
    <p></p>
    <div>
      <el-button @click="tran" type="success" style="width: 100%;">{{title2}}</el-button>
    </div>
  </div>

</template>

<script>
  export default {
    name: "Register",
    data() {
      return {
        //绑定的form对象
        form: {

        },

        title: '提交',
        title2: '返回登录页面',
        rules: { //rules对象的属性都是prop的值（这个值对应着后端的User的UserId）
          custumer_id: [{
            required: true,
            message: '请输入手机号', //触发的东西
            trigger: 'blur' //触发时机，这里是离开焦点
          }],
          code: [{
            required: true,
            message: '请输入验证码', //触发的东西
            trigger: 'blur' //触发时机，这里是离开焦点
          }],
          name: [{
            required: true,
            message: '请输入姓名', //触发的东西
            trigger: 'blur' //触发时机，这里是离开焦点
          }],
          id: [{
            required: true,
            message: '请输入身份证号码', //触发的东西
            trigger: 'blur' //触发时机，这里是离开焦点
          }],
          password: [{
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }],
          Respassword: [{
            required: true,
            message: '请确认密码',
            trigger: 'blur'
          }],
          contact: [{
            required: true,
            message: '请输入紧急联系人姓名',
            trigger: 'blur'
          }],
          ctel: [{
            required: true,
            message: '请输入紧急联系人手机号',
            trigger: 'blur'
          }]
        }
      }
    },

    methods: {

      submit() {

      },

      tran() {
        this.$router.push('UserLogin') //Login主页
      }


    }
  }
</script>

<style>
  .register{
    width: 260px;
    margin: 50px auto;
    /* 垂直居中 */
  }
</style>
