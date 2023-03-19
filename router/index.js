import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import UserLogin from '@/components/UserLogin'
import UserHome from '@/components/UserHome'
import Register from '@/components/Register'
import Home from '@/components/Home'
import NoAuth from '@/components/NoAuth'
import SysUser from '@/components/sys/User'
import SysProfile from '@/components/sys/Profile'
import SysAuth from '@/components/sys/Auth'
import SysRole from '@/components/sys/Role'
//只有配置了路由，网页才会显示，跳转了才会显示东西
Vue.use(Router)

export default new Router({
  routes: [{
      path: '/', //默认路由是'/'
      redirect: 'Login' //这里是跳转到Login页面，整合一下就是默认跳转到Login页面最开始
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/UserLogin',
      name: 'UserLogin',
      component: UserLogin
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register
    },
    {
      path: '/UserHome',
      name: 'UserHome',
      component: UserHome
    },
    {
      path: '/Home', //Home页面路由和组件名，这些都要配置
      name: 'Home',
      component: Home,
      children: [{ //包含在home页面的子页面或者说子组件
          path: '/NoAuth',
          name: 'NoAuth',
          component: NoAuth
        },
        {
          path: '/SysUser',
          name: 'SysUser',
          component: SysUser
        },
        {
          path: '/SysProfile',
          name: 'SysProfile',
          component: SysProfile
        },
        {
          path: '/SysAuth',
          name: 'SysAuth',
          component: SysAuth
        },
        {
          path: "/SysRole",
          name: 'SysRole',
          component: SysRole
        }
      ]
    }
  ]
})
