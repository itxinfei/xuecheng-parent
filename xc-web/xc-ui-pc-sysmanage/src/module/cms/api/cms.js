//public是对axios的工具类封装，定义了http请求方法
import http from './../../../base/api/public'
import querystring from 'querystring'
let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xcApiUrlPre;

//页面查询
export const page_list = (page,size,params) => {
  //将json对象转为key/value键值对
  let query = querystring.stringify(params);
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl+'/cms/page/list/'+page+'/'+size+'?'+query)
}

//站点内容查询
export const site_list = () => {
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl+'/cms/site/list')
}

//页面模板查询
export const template_list = () => {
  //请求服务端的页面查询接口
  return http.requestQuickGet(apiUrl+'/cms/template/list')
}

//页面添加
export const page_add = params => {
  return http.requestPost(apiUrl+'/cms/page/add',params)
}

//查询页面信息
export const page_get = id => {
  return http.requestQuickGet(apiUrl+'/cms/page/get/'+id)
}

//修改页面信息
export const page_edit = (id,params) => {
  return http.requestPut(apiUrl+'/cms/page/edit/'+id,params)
}

//删除页面信息
export const page_del = id => {
  return http.requestDelete(apiUrl+'/cms/page/del/'+id)
}

//发布页面
export const page_postPage = id => {
  return http.requestPost(apiUrl+'/cms/page/postPage'+id)
}