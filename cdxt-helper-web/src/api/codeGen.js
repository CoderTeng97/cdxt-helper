import request from '@/utils/request'

//generate-index

export function getLoadDataSource(data) {
  return request({
    url: '/api/codeGen/datasource/list',
    method: 'post',
    data:data
  })
}

export function getLoadTemplate(data) {
  return request({
    url: '/api/codeGen/template/list',
    method: 'post',
    data:data
  })
}

export function getLoadDbType(data) {
  return request({
    url: '/api/codeGen/datasource/dbtype',
    method: 'post',
    data:data
  })
}

export function postDataSourceChange(id,data) {
  return request({
    url: '/api/codeGen/datasource/table/${'+id+'}',
    method: 'post',
    data:data
  })
}

export function postDataSourceDelete(data) {
  return request({
    url: '/api/codeGen/datasource/del',
    method: 'post',
    data:data
  })
}

export function postDatasourceTest(data) {
  return request({
    url: '/api/codeGen/datasource/test',
    method: 'post',
    data:data
  })
}

export function datasourceUpdate(data) {
  return request({
    url: '/api/codeGen/datasource/update',
    method: 'post',
    data:data
  })
}

export function datasourceAdd(data) {
  return request({
    url: '/api/codeGen/datasource/add',
    method: 'post',
    data:data
  })
}

//generate-result

export function postGenerateCode(data) {
  return request({
    url: '/api/codeGen/generate/code',
    method: 'post',
    data:data
  })
}

//template-index

export function getTemplateList(data) {
  return request({
    url: '/api/codeGen/template/list',
    method: 'post',
    data:data
  })
} 

export function deleteTemplate(data) {
  return request({
    url: '/api/codeGen/template/del',
    method: 'post',
    data:data
  })
} 

//template-edit

export function getTemplateById(id,data) {
  return request({
    url: '/api/codeGen/template/get/${'+id+'}',
    method: 'post',
    data:data
  })
} 

export function saveTemplate(opt,data) {
  return request({
    url: '/api/codeGen/template/${'+opt+'}',
    method: 'post',
    data:data
  })
} 


