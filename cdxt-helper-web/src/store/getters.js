import router from "@/router"

const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => {
    if(state.user.name) {
      return state.user.name
    }
    return window.localStorage.getItem("name")
  },
  role: state => {
    if(state.user.role) {
      return state.user.name
    }
    return window.localStorage.getItem("role")
  },
  roleRouter: state => {
    if(state.user.roleRouter.lenght) {
      return state.user.roleRouter
    }
    let res = window.localStorage.getItem("roleRouter")
    if(res) {
      return JSON.parse(res)
    }else {
      return []
    }
  },
  uid: state => {
    if(state.user.uid) {
      return state.user.uid
    }
    return window.localStorage.getItem("uid")
  }
}
export default getters
