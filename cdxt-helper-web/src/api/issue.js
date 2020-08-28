import request from '@/utils/request'

/**
 * 发布问题任务
 * @param {*} data 
 */
export function postIssue(data) {
    return request({
      url: '/after-safe/issue/release',
      method: 'POST',
      data:data
    })
}

/**
 * 更新问题任务
 * @param {*} data 
 */
export function updateIssue(data) {
    return request({
      url: '/after-safe/issue/updateState',
      method: 'POST',
      data:data
    })
}




/**
 * 搜索问题列表
 * @param {*} data 
 */
export function searchIssue(data){
    return request({
        url: '/after-safe/issue/list',
        method: 'POST',
        data:data
    })
}


/**
 * 获取值班人员列表
 * @param {*} data 
 */
export function getWatcherList(){
    return request({
        url: '/user/watcher/list',
        method: 'GET',
    })
}