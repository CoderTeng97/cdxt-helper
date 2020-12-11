import request from '@/utils/request'
import axios from 'axios'

let http = "";
//generate-index

export function getLoadDataSource(data) {
  return request({
    url: http+'/codeGen/datasource/list',
    method: 'post',
    data:data
  })
}

export function getLoadTemplate(data) {
  return request({
    url: http+'/codeGen/template/list',
    method: 'post',
    data:data
  })
}

export function getLoadDbType(data) {
  return request({
    url: http+'/codeGen/datasource/dbtype',
    method: 'post',
    data:data
  })
}

export function postDataSourceChange(id,data) {
  return request({
    url: http+'/codeGen/datasource/table/'+id+'',
    method: 'post',
    data:data
  })
}

export function postDataSourceDelete(data) {
  return request({
    url: http+'/codeGen/datasource/del',
    method: 'post',
    data:data
  })
}

export function postDatasourceTest(data) {
  return request({
    url: http+'/codeGen/datasource/test',
    method: 'post',
    data:data
  })
}

export function datasourceUpdate(data) {
  return request({
    url: http+'/codeGen/datasource/update',
    method: 'post',
    data:data
  })
}

export function datasourceAdd(data) {
  return request({
    url: http+'/codeGen/datasource/add',
    method: 'post',
    data:data
  })
}

//generate-result

export function postGenerateCode(data) {
  return request({
    url: http+'/codeGen/generate/code',
    method: 'post',
    data:data
  })
}

//template-index

export function getTemplateList(data) {
  return request({
    url: http+'/codeGen/template/list',
    method: 'post',
    data:data
  })
} 

export function deleteTemplate(data) {
  return request({
    url: http+'/codeGen/template/del',
    method: 'post',
    data:data
  })
} 

//template-edit

export function getTemplateById(id,data) {
  return request({
    url: http+'/codeGen/template/get/'+id,
    method: 'post',
    data:data
  })
} 

export function saveTemplate(opt,data) {
  return request({
    url: http+'/codeGen/template/'+opt,
    method: 'post',
    data:data
  })
} 

export function getJavaFile(time,callback) {
	axios.get('/codeGen/velocity/java.json?q='+time)
	    .then(function(response) {
	      callback.call(this, response.data)
	})
}


export function getCsharpFile(time,callback) {
	axios.get('/codeGen/velocity/csharp.json?q='+time)
	    .then(function(response) {
	      callback.call(this, response.data)
	})
}


