import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router,{ resetRouter, constantRoutes, commonRoutes } from '@/router'
import {getRoutersInfo, unique} from '@/utils/utils'

const getDefaultState = () => {
  return {
    token: getToken(),
    uid:'',
    name: '',
    role:'',
    roleRouter: [], //新路由
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
    window.localStorage.setItem("name", name)
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLE: (state, role) => {
    state.role = role
    window.localStorage.setItem("role", role)
  },

  SET_ROLE_ROUTER(state, data) {
    state.roleRouter = data
    window.localStorage.setItem("roleRouter", JSON.stringify(data))
  },

  SET_UID(state, data) {
    state.uid = data
    window.localStorage.setItem("uid", data)
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { account, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ account: account.trim(), password: password }).then(response => {
        //const { data } = response.data
        commit('SET_TOKEN', response.token)
        commit("SET_ROLE", response.role)
        commit("SET_UID", response.id)
        commit("SET_NAME", response.username)
        state.uid = response.id
        state.name = response.username
        state.role = response.role
        setToken(response.token)
        resolve()
      }).catch(error => {
        console.log(error)
        reject(error)
      })
    })
  },

  //根据当前用户信息，获取新路由
  generateRoutes({commit}, userRoles) {
    return new Promise((resolve, reject) => {
      console.log(router, userRoles);
      let accessedRoutes = [];

      accessedRoutes = unique(getRoutersInfo(userRoles, constantRoutes))

      let res = commonRoutes.concat(accessedRoutes)

      res.push(
        // 404 page must be placed at the end !!!
        { path: '*', redirect: '/404', hidden: true }
      )
      commit('SET_ROLE_ROUTER', res)
      
      resolve(res)
    })
  },


  // user logout
  logout({ commit, state }) {
    removeToken() // must remove  token  first
    resetRouter()
    commit('SET_ROLE_ROUTER', [])
    window.localStorage.clear()
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      commit('SET_ROLE_ROUTER', [])
      window.localStorage.clear()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

