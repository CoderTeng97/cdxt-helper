(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-d7fc0afe"],{"3e4e":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-row",{staticStyle:{"margin-bottom":"20px"}},[a("el-col",{attrs:{xs:24,md:6,lg:3,xl:3}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",remote:"","reserve-keyword":"",placeholder:"请输入医院关键词搜索","remote-method":e.searchHospital,loading:e.loading},on:{change:e.hospitalSelectChangeEvt},model:{value:e.hospital,callback:function(t){e.hospital=t},expression:"hospital"}},e._l(e.hospitals,(function(e){return a("el-option",{key:e.name,attrs:{label:e.name,value:e}})})),1)],1),e._v(" "),a("el-col",{attrs:{xs:24,md:11,lg:9,xl:6,offset:1}},[a("el-date-picker",{attrs:{type:"datetimerange","picker-options":e.pickerOptions,"range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",format:"yyyy-MM-dd HH:mm:ss","value-format":"yyyy-MM-dd HH:mm:ss",align:"right"},model:{value:e.postTime,callback:function(t){e.postTime=t},expression:"postTime"}})],1),e._v(" "),a("el-button",{attrs:{icon:"el-icon-search",circle:""},on:{click:e.searchBtnEvent}})],1),e._v(" "),a("el-row",[a("el-col",{attrs:{span:24}},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],attrs:{border:"",data:e.resData.records,"element-loading-text":"Loading",fit:"true","highlight-current-row":""}},[a("el-table-column",{attrs:{type:"expand"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"top"}},[a("el-form-item",{attrs:{label:"更新路径"}},e._l(t.row.fileSrcList,(function(t){return a("span",{key:t.id,staticClass:"value",staticStyle:{display:"block"}},[e._v(e._s(t.src))])})),0),e._v(" "),a("el-form-item",{attrs:{label:"备注"}},[a("div",{domProps:{innerHTML:e._s(t.row.remark)}})])],1)]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"ID"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.id))]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"医院"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.hospitalName))]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"所属分支",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.branch))])]}}])}),e._v(" "),a("el-table-column",{attrs:{"class-name":"status-col",label:"紧急程度",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tag",{attrs:{type:0==t.row.postLevel?"primary":"danger"}},[e._v(e._s(0==t.row.postLevel?"一般":"紧急"))])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"created_at",label:"发布人"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.pusername))]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"created_at",label:"处理人"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.dusername))]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"created_at",label:"发布时间"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.gmtCreate))])]}}])}),e._v(" "),a("el-table-column",{attrs:{"class-name":"status-col",label:"处理情况",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tag",{attrs:{type:0==t.row.state?"primary":"success"}},[e._v("\n          "+e._s(0==t.row.state?"未处理":"已处理")+"\n        ")])]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[0==t.row.state&&e.uid==t.row.duid?a("el-button",{attrs:{type:"",size:"mini"},on:{click:function(a){return e.stateClickEvent(t.$index,t.row)}}},[e._v("处理")]):e._e()]}}])})],1)],1),e._v(" "),a("el-col",{staticStyle:{"margin-top":"20px"},attrs:{span:12}},[a("el-pagination",{attrs:{"current-page":e.rparams.pageNum,"page-sizes":[10,20,50,100],"page-size":e.rparams.pageSize,layout:"total,sizes, prev, pager, next",total:e.resData.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange,"update:currentPage":function(t){return e.$set(e.rparams,"pageNum",t)},"update:current-page":function(t){return e.$set(e.rparams,"pageNum",t)}}})],1)],1)],1)},r=[],s=(a("6a61"),a("cf7f")),i=a("41db"),o={data:function(){return{resData:{},listLoading:!0,uid:"",rparams:{hospitalId:"",pageNum:1,pageSize:10,postStartTime:null,postEndTime:null},hospital:"",postTime:[],hospitals:[],pickerOptions:{shortcuts:[{text:"最近一周",onClick:function(e){var t=new Date,a=new Date;a.setTime(a.getTime()-6048e5),e.$emit("pick",[a,t])}},{text:"最近一个月",onClick:function(e){var t=new Date,a=new Date;a.setTime(a.getTime()-2592e6),e.$emit("pick",[a,t])}},{text:"最近三个月",onClick:function(e){var t=new Date,a=new Date;a.setTime(a.getTime()-7776e6),e.$emit("pick",[a,t])}}]},value1:[new Date(2e3,10,10,10,10),new Date(2e3,10,11,10,10)],value2:""}},filters:{statusFilter:function(e){console.log("dsf"+e);var t=null;return t=0==e?{type:"danger"}:{type:"info"},t[e]}},created:function(){this.uid=this.$store.state.user.uid,this.fetchData()},methods:{downloadPatches:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(t){var a;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:a=document.createElement("a"),a.href="http://39.106.183.121/svn/"+t.id+".zip",a.click();case 3:case"end":return e.stop()}}),e)})));function t(t){return e.apply(this,arguments)}return t}(),fetchData:function(){this.listLoading=!0,this.getDeployList(),this.listLoading=!1},stateClickEvent:function(e,t){console.log(t),this.updateState(t.id,1),this.fetchData()},getDeployList:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return this.rparams.postStartTime=this.postTime[0],this.rparams.postEndTime=this.postTime[1],this.rparams.hospitalId=this.hospital.id,e.next=5,Object(i["a"])(this.rparams);case 5:t=e.sent,this.resData=t,console.log(this.resData),console.log(this.rparams);case 9:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),updateState:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(t,a){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:n=Object(i["e"])({did:t,state:a}),n?this.$message({message:"更新成功！",type:"success"}):this.$message({message:"更新失败！",type:"info"});case 2:case"end":return e.stop()}}),e,this)})));function t(t,a){return e.apply(this,arguments)}return t}(),handleSizeChange:function(e){this.rparams.pageSize=e,this.fetchData()},handleCurrentChange:function(e){this.rparams.pageNum=e,this.fetchData()},hospitalSelectChangeEvt:function(){},getHospitalList:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(i["c"])(this.rparams);case 2:t=e.sent,this.resData=t;case 4:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),searchHospital:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(t){var a;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(console.log(t),""===t){e.next=11;break}return this.loading=!0,e.next=5,Object(i["c"])({text:t,pageNum:1,pageSize:1e3});case 5:a=e.sent,this.hospitals=a.records,console.log(a),this.loading=!1,e.next=12;break;case 11:this.options=[];case 12:case"end":return e.stop()}}),e,this)})));function t(t){return e.apply(this,arguments)}return t}(),searchBtnEvent:function(){this.getDeployList()}}},l=o,c=a("9ca4"),u=Object(c["a"])(l,n,r,!1,null,null,null);t["default"]=u.exports},"41db":function(e,t,a){"use strict";a.d(t,"b",(function(){return r})),a.d(t,"c",(function(){return s})),a.d(t,"a",(function(){return i})),a.d(t,"e",(function(){return o})),a.d(t,"d",(function(){return l}));var n=a("b775");function r(e){return Object(n["a"])({url:"/deploy/post",method:"PUT",data:e})}function s(e){return Object(n["a"])({url:"/deploy/hospitals",method:"GET",params:e})}function i(e){return Object(n["a"])({url:"/deploy/list",method:"post",data:e})}function o(e){return Object(n["a"])({url:"/deploy/update/state",method:"post",params:e})}function l(e){return Object(n["a"])({url:"/deploy/savaOrUpdateHospital",method:"post",data:e})}}}]);