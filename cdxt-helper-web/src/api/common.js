import request from '@/utils/request'
/**
 * 获取医院列表
 * @param {*} param 
 */
export function getHospitalList(param) {
    return request({
      url: '/common/hospitals',
      method: 'GET',
      params:param
    })
  }