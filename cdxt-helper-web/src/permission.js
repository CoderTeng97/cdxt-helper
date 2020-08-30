import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import {getRoutersInfo, unique} from '@/utils/utils'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      //取用户信息
      const hasGetUserInfo = store.getters.name;
      const userRoles = store.getters.role;
      const userRolesList = store.getters.roleRouter;

      console.log(hasGetUserInfo, userRoles, userRolesList);
      

      // // get user info，未获取用户名或者 用户权限
      // const accessRoutes = await store.dispatch('user/generateRoutes', userRoles)

      // //重置路由
      // router.options.routes = accessRoutes
      // router.addRoutes(accessRoutes)
      // console.log("ddd",accessRoutes);
      
      
      if (hasGetUserInfo && userRoles && userRolesList.length > 0) {

        next()

      } else {
        try {

          console.log("重新获取权限");

          // // get user info，未获取用户名或者 用户权限
          // const accessRoutes = await store.dispatch('user/generateRoutes', userRoles)
          // //重置路由
          // router.options.routes = accessRoutes
          // router.addRoutes(accessRoutes)

          // console.log("ddd",accessRoutes)
       
          next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
