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
 * 更新状态
 * @param {*} data 
 */
export function updateIssueState(data) {
    return request({
      url: '/after-safe/issue/updateState',
      method: 'PUT',
      data:data
    })
}


/**
 * 重新指派任务处理人
 * @param {*} data 
 */
export function issueAssign(data) {
    return request({
      url: '/after-safe/issue/assignUser',
      method: 'PUT',
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


/**
 * 获取问题详情
 */
export function getIssueDetail(issueId){
    return request({
        url: '/after-safe/issue/detail/' + issueId,
        method: 'GET',
    })
}

/**
 * 获取值班人员在线状态信息
 */
export function getOnlineUserList(){
    return request({
        url: '/after-safe/issue/onlineUser/state',
        method: 'GET',
    })
}