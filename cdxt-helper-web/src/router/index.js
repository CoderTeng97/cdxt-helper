import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */

 //公共的
export const commonRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: "Dashboard",
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

]

export const commonRoutes1 = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: "Dashboard",
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  {
    path: '/deploy/post',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/deploy/post.vue'),
        meta: { title: '补丁发布', icon: 'example',   },
        roles: ['admin', "developer"]
      }
    ]
  },

  {
    path: '/user/manager',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/user/index.vue'),
        meta: { title: '用户管理', icon: 'user',   },
        roles: ['admin', "developer"]
      }
    ]
  },

  {
    path: '/deploy/list',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/deploy/list.vue'),
        meta: { title: '补丁列表', icon: 'liebiao',  },
        roles: ['admin','developer','deployer']
      }
    ]
  },

  {
    path: '/deploy/branchManager',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/deploy/branchManager.vue'),
        meta: { title: '医院分支管理', icon: 'ziyuan', },
        roles: ['admin',"developer",'deployer']
      }
    ]
  },

  {
    path: '/aftersales',
    component: Layout,
    meta: { title: '运维管理', icon: 'shouhoufuwu-' },
    children: [
      {
        path: '/problemPost',
        name: 'Form',
        component: () => import('../views/afterSafe/issueManager.vue'),
        meta: { title: '售后问题管理', icon: 'dabianyanjiuwenti ',  },
        roles: ['admin', 'developer','aftersafo']
      }
    ]
  },
	{
	  path: '/template',
	  component: Layout,
	  meta: { title: '代码生成工具', icon: 'liebiao', },
	  children: [
	    {
	      path: 'config',
	      name: 'Config',
	      component: () => import('../views/codeGen/generate/index.vue'),
	      meta: { title: '生成代码', icon: 'liebiao',  },
	    },
		{
		  path: 'list',
		  name: 'List',
		   component: () => import('../views/codeGen/template/index.vue'),
		  meta: { title: '模板管理', icon: 'liebiao',  },
		},
		{
			path: 'result/:config',
			name: 'Result',
			component: () => import('../views/codeGen/generate/result.vue'),
			meta: { title: '生成结果' },
			hidden: true
		 },
		 {
			path: 'edit/:id(\\d+)',
			name: 'Edit',
			component: () => import('../views/codeGen/template/edit.vue'),
			meta: { 'title': '编辑模板' },
			hidden: true
		}
	  ]
	},

  { path: '*', redirect: '/404', hidden: true }
 
]

//具有权限的
export const jurisdictionRouters = [
 
  {
    path: '/deploy/post',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/deploy/post.vue'),
        meta: { title: '补丁发布', icon: 'form',   },
        roles: ['ADMIN', "DEVELOPER"]
      }
    ]
  },

  {
    path: '/deploy/list',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/deploy/list.vue'),
        meta: { title: '补丁列表', icon: 'table',  },
        roles: ['ADMIN','DEVELOPER','DEPLOYER']
      }
    ]
  },

  {
    path: '/deploy/branchManager',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('../views/deploy/branchManager.vue'),
        meta: { title: '医院分支管理', icon: 'table', },
        roles: ['ADMIN',"DEVELOPER",'DEPLOYER']
      }
    ]
  },

  {
    path: '/aftersales',
    component: Layout,
    meta: { title: '运维管理', icon: 'table' },
    children: [
      {
        path: '/problemPost',
        name: 'Form',
        component: () => import('../views/afterSafe/issueManager.vue'),
        meta: { title: '售后问题管理', icon: 'table',  },
        roles: ['ADMIN', 'DEVELOPER','AFTERSAFE']
      }
    ]
  },
]


export const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: commonRoutes1
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
