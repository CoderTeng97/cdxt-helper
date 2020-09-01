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

export function resetPassword(data) {
  return request({
    url: '/user/resetPassword',
    method: 'put',
    data: data
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

/**
 * 获取用户分页信息
 */
export function getUserPageInfo(req) {
  return request({
    url: '/user/page',
    method: 'get',
    params: req
  })
}


export function setWatcher(data) {
  return request({
    url: '/user/watcher/set',
    method: 'get',
    params:data
  })
}