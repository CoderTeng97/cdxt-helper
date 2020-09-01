import request from '@/utils/request'

/**
 * 发布补丁
 * @param {*} data 
 */
export function deployPost(data) {
    return request({
      url: '/api/deploy/post',
      method: 'PUT',
      data:data
    })
  }
/**
 * 获取医院列表
 * @param {*} param 
 */
export function getHospital(param) {
    return request({
      url: '/api/common/hospitals',
      method: 'GET',
      params:param
    })
  }

  /**
   * 获取部署补丁列表
   * @param {*} param 
   */
  export function deployList(param) {
    return request({
      url: '/api/deploy/list',
      method: 'post',
      data:param
    })
  }

  /**
   * 更新补丁发布记录状态
   * @param {*} data 
   */
  export function udpateState(data) {
    return request({
      url: '/api/deploy/update/state',
      method: 'post',
      params:data
    })
  }


  /**
   *  更新医院
   * @param {*} data 
   */
  export function saveAndUpdateHospital(data) {
    return request({
      url: '/api/deploy/savaOrUpdateHospital',
      method: 'post',
      data:data
    })
  }


    /**
   *  下载补丁
   * @param {*} data 
   */
  export function downloadPatches(patchId) {
    return request({
      url: '/api/deploy/patches/download?patchId=' + patchId,
      method: 'get',
    })
  }