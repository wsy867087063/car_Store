import Vue from 'vue'  //这些引入都是直接从第三个文件夹里面引入的
import Vuex from 'vuex'  //index的名字不能改变！！！！
import createPersistedState from "vuex-persistedstate"//引入的是一个固定的函数名字，让变量持久化
//使用vuex插件
Vue.use(Vuex)
export default new Vuex.Store({  //前两个是导出命令，参数是对象类型
  //全局状态
  state: {  //对象参数类型里面依然是个对象类型属性
    //用户身份令牌 存在vuex中，所有组件（网页）都能用
    token: '',
    //当前登录用户名
    loginUserName: '',
    //激活的菜单
    menuIndex: '',
    //一级菜单
    breadcrumbTitle: '',
    //二级菜单
    breadcrumbIndex: '',
    //头像地址
    headImgUrl: '',
    //权限列表
    authUrlsList: '' ,
    //目前authid
  },
  //同步数据提交
  mutations: {  //同步提交数据属性，也是对象类型属性
    token(state, param) {
      state.token = param
    },
    loginUserName(state, param) {  //state就是上面那个对象，param是后台传的值
      state.loginUserName = param
    },
    menuIndex(state, param) {
      state.menuIndex = param
    },
    breadcrumbTitle(state, param) {
      state.breadcrumbTitle = param
    },
    breadcrumbIndex(state, param) {
      state.breadcrumbIndex = param
    },
    headImgUrl(state, param) {
      state.headImgUrl = param
    },
    authUrlsList(state, param) {
      state.authUrlsList = param
    },
  },

  //引入持久化组件
  plugins: [createPersistedState()]  //这是个数组属性，引入插件的数组属性
})
