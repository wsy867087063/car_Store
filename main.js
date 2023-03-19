// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'//而且这里基本是引入什么，下面vue，new的时候就要加什么
import App from './App'
import router from './router'
import axios from 'axios'
import store from './store'   //注意：./的意思是当前目录这里就是src
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; 
Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.$axios = axios

//设置全局请求拦截器  既然是全局，那肯定要写在main里面  前置拦截
axios.interceptors.request.use(
  config => {
    //设置请求头Token  ,可以在浏览器请求头看是否建立成功
    config.headers.Token = store.state.token
    return config
  }, error => {
    return Promise.reject(error)
  })

//设置全局响应拦截器  既然是全局，那肯定要写在main里面  后置拦截
axios.interceptors.response.use(
  response => {
    return response
  }, err => {
    if (err.response.status == 401) {
      ElementUI.MessageBox.confirm('账号已过期, 是否跳转到登录页面?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //未登录，跳转到登录页页面
        store.state.token = ''
        router.push("Login")
      }).catch(() => {})
    } else if (err.response.status == 504) {
      ElementUI.Message.error('服务器无响应')
    } else {
      //输出全局异常信息
      ElementUI.Message.error(err.response.data.msg)
    }
    return Promise.reject(err) // 返回接口返回的错误信息
  })


//设置全局权限拦截    当某个人访问某个路由时，检查其是否有权限
router.beforeEach((to, from, next) => {//to是路由去向，from是来自的路由，next是到底去不去那个路由
  if (to.name == null) {
    ElementUI.Message.error('路由不存在!')
  } else if (to.name == 'Login' || to.name == 'Home' || to.name == 'NoAuth' || to.name == 'SysProfile'||to.name == 'UserLogin'||to.name == 'Register'||to.name == 'UserHome') {
    next()
  } else {
    if (store.state.authUrlsList.indexOf(to.name) != -1) {//有权限
      next()
    } else {
      next({
        path: '/NoAuth' //转到无权限页
      });
    }
  }
});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,   //引入了所以  加在这
  store,    //引入了所以  加在这  到此就将vuex加入了
  components: {
    App
  },
  template: '<App/>'
})
