import request from '@/utils/request'

/**
 * 发布问题任务
 * @param {*} data 
 */
export function postIssue(data) {
    return request({
      url: '/after-safe/issue/release',
      method: 'PUT',
      data:data
    })
  }