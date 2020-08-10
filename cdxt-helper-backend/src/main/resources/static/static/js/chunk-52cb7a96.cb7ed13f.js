(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-52cb7a96"],{"26cf":function(e,t,r){},"41db":function(e,t,r){"use strict";r.d(t,"b",(function(){return s})),r.d(t,"c",(function(){return i})),r.d(t,"a",(function(){return l})),r.d(t,"d",(function(){return a}));var o=r("b775");function s(e){return Object(o["a"])({url:"/deploy/post",method:"PUT",data:e})}function i(e){return Object(o["a"])({url:"/deploy/hospitals",method:"GET",params:e})}function l(e){return Object(o["a"])({url:"/deploy/list",method:"post",data:e})}function a(e){return Object(o["a"])({url:"/deploy/update/state",method:"post",params:e})}},"54fb":function(e,t,r){"use strict";r.r(t);var o=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"app-container"},[r("el-form",{ref:"form",staticClass:"demo-ruleForm",attrs:{rules:e.rules,model:e.form,"label-width":"120px","label-position":"left"}},[r("el-row",[r("el-col",{attrs:{span:24}},[r("el-form-item",{attrs:{label:"医院",prop:"hospital"}},[r("el-select",{attrs:{filterable:"",remote:"","reserve-keyword":"",placeholder:"请输入关键词搜索","remote-method":e.searchHospital,loading:e.loading},on:{change:e.hospitalSelectChangeEvt},model:{value:e.tempForm.hospital.id,callback:function(t){e.$set(e.tempForm.hospital,"id",t)},expression:"tempForm.hospital.id"}},e._l(e.hospitals,(function(e){return r("el-option",{key:e.name,attrs:{label:e.name,value:e.id}})})),1)],1)],1),e._v(" "),r("el-col",{attrs:{span:15,xs:24}},[r("el-form-item",{attrs:{label:"补丁路径",prop:e.fileSrc}},[r("el-input",{model:{value:e.tempForm.fileSrc,callback:function(t){e.$set(e.tempForm,"fileSrc",t)},expression:"tempForm.fileSrc"}},[r("template",{slot:"prepend"},[e._v(e._s(e.tempForm.hospital.branch)+"/")]),e._v(" "),r("el-button",{attrs:{slot:"append",icon:"el-icon-plus"},on:{click:function(t){return t.preventDefault(),e.addFileSrcListDomain(t)}},slot:"append"})],2)],1),e._v(" "),e._l(e.tempForm.fileSrcList,(function(t,o){return r("el-form-item",{key:t.key,attrs:{prop:"tempForm.fileSrcList."+o+".value"}},[r("el-input",{model:{value:t.src,callback:function(r){e.$set(t,"src",r)},expression:"domain.src"}},[r("template",{slot:"prepend"},[e._v(e._s(e.tempForm.hospital.branch)+"/")]),e._v(" "),r("el-button",{attrs:{slot:"append",icon:"el-icon-minus"},on:{click:function(r){return r.preventDefault(),e.removeFileSrcListDomain(t)}},slot:"append"})],2)],1)}))],2),e._v(" "),r("el-col",{attrs:{span:24}},[r("el-form-item",{attrs:{label:"紧急程度",prop:"postLevel"}},[r("el-select",{attrs:{placeholder:"请选择活动区域"},model:{value:e.form.postLevel,callback:function(t){e.$set(e.form,"postLevel",t)},expression:"form.postLevel"}},[r("el-option",{attrs:{label:"一般",value:"0"}}),e._v(" "),r("el-option",{attrs:{label:"紧急",value:"1"}})],1)],1)],1),e._v(" "),""!=e.tempForm.dUser.id?r("el-col",{attrs:{span:24}},[r("el-form-item",{attrs:{label:"处理人员",prop:"dUid"}},[r("el-select",{attrs:{placeholder:"请选择活动区域"},model:{value:e.tempForm.dUser,callback:function(t){e.$set(e.tempForm,"dUser",t)},expression:"tempForm.dUser"}},[r("el-option",{key:e.tempForm.dUser.id,attrs:{label:e.tempForm.dUser.trueName,value:e.tempForm}})],1)],1)],1):e._e(),e._v(" "),r("el-col",{attrs:{span:15,xs:24}},[r("el-form-item",{attrs:{label:"备注(功能说明)",prop:"remark"}},[r("el-input",{attrs:{type:"textarea",autosize:{minRows:5,maxRows:5}},model:{value:e.form.remark,callback:function(t){e.$set(e.form,"remark",t)},expression:"form.remark"}})],1)],1),e._v(" "),r("el-col",{attrs:{span:24}},[r("el-form-item",[r("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.onSubmit("form")}}},[e._v("发布")]),e._v(" "),r("el-button",{on:{click:e.resetForm}},[e._v("清空")])],1)],1)],1)],1)],1)},s=[],i=(r("6a61"),r("cf7f")),l=r("41db"),a={data:function(){var e=this,t=function(t,r,o){void 0==e.tempForm.hospital.id||""==e.tempForm.hospital.id?o(new Error("请输入医院信息")):o()};return{loading:!1,form:{hospitalId:"",branch:"",remark:"",postLevel:"0",fileSrcList:[],dUid:""},tempForm:{fileSrcList:[],fileSrc:"",hospital:{},dUser:{id:"",trueName:""}},hospitals:[],rules:{hospital:[{validator:t,trigger:"change"}]}}},methods:{onSubmit:function(e){var t=this;this.$refs[e].validate((function(e){return!!e&&(console.log(t.tempForm),void 0==t.tempForm.fileSrc||""==t.tempForm.fileSrc?(t.$message("路径信息不能为空"),!1):void t.post())}))},resetForm:function(e){this.form={},this.tempForm={}},hospitalSelectChangeEvt:function(){var e=this;this.tempForm.hospital=this.hospitals.filter((function(t){return t.id==e.tempForm.hospital.id}))[0],console.log("医院触发"),console.log(this.tempForm.hospital.id),this.tempForm.dUser={id:"1265647429739417605",trueName:"唐文鑫"}},post:function(){var e=Object(i["a"])(regeneratorRuntime.mark((function e(){var t,r,o;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:for(r in this.form.hospitalId=this.tempForm.hospital.id,this.form.branch=this.tempForm.hospital.branch,t=new Array,this.tempForm.fileSrcList)t.push(this.tempForm.fileSrcList[r].src);return t.push(this.tempForm.fileSrc),console.log(t),this.form.fileSrcList=t,this.form.dUid=this.tempForm.dUser.id,e.next=10,Object(l["b"])(this.form);case 10:o=e.sent,o?(this.$message({message:"上传成功！",type:"success"}),this.resetForm("form")):this.$message("上传失败");case 12:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),addFileSrcListDomain:function(){this.tempForm.fileSrcList.push({src:""})},removeFileSrcListDomain:function(e){console.log(e);var t=this.tempForm.fileSrcList.indexOf(e);-1!==t&&this.tempForm.fileSrcList.splice(t,1)},searchHospital:function(){var e=Object(i["a"])(regeneratorRuntime.mark((function e(t){var r;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(console.log(t),""===t){e.next=11;break}return this.loading=!0,e.next=5,Object(l["c"])({text:t,pageNum:1,pageSize:1e3});case 5:r=e.sent,this.hospitals=r.records,console.log(r),this.loading=!1,e.next=12;break;case 11:this.options=[];case 12:case"end":return e.stop()}}),e,this)})));function t(t){return e.apply(this,arguments)}return t}()}},n=a,c=(r("5826"),r("9ca4")),m=Object(c["a"])(n,o,s,!1,null,"951cbf18",null);t["default"]=m.exports},5826:function(e,t,r){"use strict";var o=r("26cf"),s=r.n(o);s.a}}]);