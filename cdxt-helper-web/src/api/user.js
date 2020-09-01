import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/api/user/info',
    method: 'get',
    params: { token }
  })
}

export function resetPassword(data) {
  return request({
    url: '/api/user/resetPassword',
    method: 'put',
    data: data
  })
}

export function logout() {
  return request({
    url: '/api/user/logout',
    method: 'post'
  })
}


export function getUserList(role,text) {
  return request({
    url: '/api/user/list',
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
    url: '/api/user/page',
    method: 'get',
    params: req
  })
}


export function setWatcher(data) {
  return request({
    url: '/api/user/watcher/set',
    method: 'get',
    params:data
  })
}