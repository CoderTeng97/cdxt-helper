import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function resetPassword(oldPassword,newPassword) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { 
      oldPassword,
      newPassword
    }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}


export function getUserList(role,text) {
  return request({
    url: '/user/list',
    method: 'get',
    params:{
      role,
      text
    }
  })
}

export function setWatcher(data) {
  return request({
    url: '/user/watcher/set',
    method: 'get',
    params:data
  })
}