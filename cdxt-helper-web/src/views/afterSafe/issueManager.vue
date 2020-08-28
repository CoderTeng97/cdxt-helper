<template>
  <div>
    <el-row type="flex" gutter="2">
      <!-- 日志界面 -->
      <el-col :span="12">
        <div class="baseBox logBox">
          <div class="box-header">
            <el-row>
              <el-col :span="14">
                <i class="iconfont icon-renwu"></i>问题处理日志
              </el-col>
              <el-col :span="10"></el-col>
            </el-row>
          </div>
          <div class="box-main">
            <el-row>
              <el-col v-for="item in logList" :key="item" :span="24" class="content">
                <span
                  v-if="item.type=='ISSUE_OP_LOG'"
                >{{ $moment(item.data.gmtCreate).format("HH:MM:SS") +' ' + item.data.content}}</span>
                <span v-if="item.type=='LOG'">{{item.gmt_create +' ' + item.message}}</span>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-col>
      <!-- 人员列表 -->
      <el-col :span="12">
        <div class="baseBox personnelBox">
          <div class="box-header">
            <el-row>
              <el-col :span="14">
                <i class="iconfont icon-zhibanrenyuan"></i> 问题处理人员
              </el-col>
              <el-col :span="10"></el-col>
            </el-row>
          </div>
          <div class="box-main">
            <el-table
              :data="watcherList"
              class="watcher-table"
              style="width: 100% ;height:250px;overflow:scroll;"
            >
              <el-table-column prop="trueName" label="姓名"></el-table-column>
              <el-table-column prop="module" label="负责模块"></el-table-column>
              <el-table-column prop="gmtEndTime" label="值班结束时间"></el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 问题管理列表 -->
    <el-row>
      <el-col :span="24">
        <div class="baseBox issueBox">
          <div class="box-header">
            <el-row>
              <el-col :span="14">
                <i class="iconfont icon-imagevector"></i>问题处理日志
              </el-col>
              <el-col :span="10"></el-col>
            </el-row>
          </div>
          <div class="box-main">
            <el-button @click="showDiaglog('issuePost')">发布问题</el-button>
            <el-button @click="showDiaglog('issueAdditional')">补录已完成问题</el-button>
            <i class="iconfont icon-shuaxincopy" style="margin-left:20px;"></i>
            <el-table :data="issueTable.records" style="width: 100%" max-height="250">
              <el-table-column fixed prop="title" label="标题" width="300"></el-table-column>
              <el-table-column prop="hospitalName" label="医院" width="200"></el-table-column>
              <el-table-column prop="hospitalBranch" label="所属分支" width="120"></el-table-column>
              <el-table-column prop="module" label="所属模块" width="120"></el-table-column>
              <el-table-column prop="state" label="任务状态" width="120"></el-table-column>
              <el-table-column prop="priorityCode" label="紧急程度" width="120"></el-table-column>
              <el-table-column prop="gmtCreate" label="发布时间" width="150"></el-table-column>
              <el-table-column prop="gmtCompletion" label="处理时间" width="150"></el-table-column>
              <el-table-column prop="puser" label="发布人员" width="120"></el-table-column>
              <el-table-column prop="duser" label="处理人员" width="120"></el-table-column>
              <el-table-column fixed="right" label="操作" width="120">
                <template slot-scope="scope">
                  <el-tooltip class="item" effect="dark" content="指派" placement="top-start">
                    <i class="iconfont icon-zhipai" @click="showDiaglog('issueAssignUser')"></i>
                  </el-tooltip>

                  <el-tooltip class="item" effect="dark" content="完成" placement="top-start">
                    <i class="iconfont icon-wancheng5" @click="showDiaglog('issueCompletion')" ></i>
                  </el-tooltip>

                  <el-tooltip class="item" effect="dark" content="详情" placement="top-start">
                    <i class="iconfont icon-ziyuan" @click="showDiaglog('issueDetail')"></i>
                  </el-tooltip>

                  <el-tooltip class="item" effect="dark" content="校验" placement="top-start">
                    <i class="iconfont icon-xiaoyanbaocun" @click="showDiaglog('issueAuth')"></i>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="issuePagehandleSizeChange"
              @current-change="issuePagehandleCurrentChange"
              :current-page="issueTable.pageNum"
              :page-sizes="[10, 20, 50, 100]"
              :page-size="issueTable.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="issueTable.total"
            ></el-pagination>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 问题发布界面 -->
    <el-dialog
      :title="issueDialogConfig.title"
      :visible.sync="commonDialogVisible"
      :close-on-click-modal="false"
      :width="issueDialogConfig.width"
    >
      <el-form :model="issuePostForm" :rules="issuePostFormRules" ref="issuePostForm" v-if="issueDialogConfig.name == 'issuePost' || issueDialogConfig.name == 'issueAdditional'">
        <el-row gutter="10">
          <el-col :span="7">
            <el-form-item prop="title">
              <el-input v-model="issuePostForm.title" placeholder="请输入标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item prop="hospitalId">
              <el-select
                v-model="issuePostForm.hospitalId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入关键词搜索"
                :remote-method="searchHospital"
                :loading="hospitalSelectLoading"
                @change="hospitalSelectChangeEvt"
              >
                <el-option
                  v-for="item in hospitalList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4" prop="module">
            <el-form-item>
              <el-select v-model="issuePostForm.module" placeholder="请选择问题所在模块">
                <el-option
                  v-for="item in moduleList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4" prop="priorityCode">
            <el-form-item>
              <el-select v-model="issuePostForm.priorityCode" placeholder="请选择优先级">
                <el-option
                  v-for="item in priorityCodeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4" v-if="issueDialogConfig.name=='issueAdditional'">
            <el-form-item>
              <el-select v-model="issuePostForm.dUid" placeholder="处理人员">
                <el-option
                  v-for="item in priorityCodeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <quill-editor
              v-model="issuePostForm.detail"
              ref="quillEditor"
              :options="editorOption"
              @focus="onEditorFocus($event)"
              @blur="onEditorBlur($event)"
              @change="onEditorChange($event)"
            ></quill-editor>
          </el-col>
        </el-row>
      </el-form>

      <el-form :model="issueUpdateForm" ref="issueUpdateForm" v-if="issueDialogConfig.name == 'issueAssignUser' || issueDialogConfig.name == 'issueCompletion' || issueDialogConfig.name == 'issueAuth'" >
        <el-row gutter="10"> 
          <el-col :span="15" v-if="issueDialogConfig.name=='issueAssignUser'" >
            <el-form-item>
              <el-select v-model="issuePostForm.dUid" placeholder="处理人员">
                <el-option
                  v-for="item in priorityCodeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
              
            </el-form-item>
          </el-col>
          
          <el-col :span="24">
            <quill-editor
              v-model="issueUpdateForm.feedBackText"
              ref="quillEditor"
              :options="editorOption"
              @focus="onEditorFocus($event)"
              @blur="onEditorBlur($event)"
              @change="onEditorChange($event)"
            ></quill-editor>
          </el-col>
          <el-col :span="24" style="color:red;margin-top:20px;" v-if="issueDialogConfig.name=='issueAssignUser'">
            <span > * 请仔细确认是否指派，指派之后任务会从您的列表中移除</span>
          </el-col>
        </el-row>

      </el-form>


      <div slot="footer" class="dialog-footer">
        <el-button>清空</el-button>
        <el-button @click="commonSubmit('issuePostForm')" v-if="issueDialogConfig.name == 'issuePost' || issueDialogConfig.name == 'issueAdditional'">发布</el-button>
        <el-button  v-if="issueDialogConfig.name == 'issueAuth'" @click="issueReturn">退回</el-button>
        <el-button  v-if="issueDialogConfig.name == 'issueAuth'" @click="issueAuthAccess">审核通过</el-button>
        <el-button  v-if="issueDialogConfig.name == 'issueAssignUser'" @click="issueAssign()">确认指派</el-button>
        <el-button  v-if="issueDialogConfig.name == 'issueCompletion'" @click="issueComplet">确认完成</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { quillEditor } from "vue-quill-editor"; // 调用富文本编辑器
import "quill/dist/quill.snow.css"; // 富文本编辑器外部引用样式  三种样式三选一引入即可
//import 'quill/dist/quill.core.css'
//import 'quill/dist/quill.bubble.css'
import * as Quill from "quill"; // 富文本基于quill
import { postIssue, searchIssue, getWatcherList } from "@/api/issue";
import { getHospital } from "../../api/deploy";

const issueDialogConfigs = {
  issuePost: {
    width: "80%",
    title: "问题发布",
  },
  issueAdditional: {
    width: "80%",
    title: "问题补录",
  },
  issueAssignUser:{
     width: "50%",
     title: "问题指派",
  },
  issueCompletion:{
     width: "50%",
     title: "问题完成确认",
  },
  issueAuth:{
     width: "50%",
     title: "问题审核",
  }
};
export default {
  name: "issueManager",
  components: {
    //使用编辑器
    quillEditor,
  },
  data() {
    return {
      websock: null,
      commonDialogVisible: false, //通用弹窗对象
      logList: [], //日志模块数据,
      watcherList: [], //值班人员列表
      issueUpdateForm:{},//问题更新表单
      issuePostForm: {}, //问题发布表单对象
      issuePostIsAddtional: false,
      hospitalSelectLoading: false, //医院搜索加载
      moduleList: [], // 模块下拉列表数据
      priorityCodeList: [], //紧急层度列表
      hospitalList: [], //医院列表
      issuePostFormRules: {
        title: [{ required: true, message: "请输入活动名称", trigger: "blur" }],
        hospitalId: [
          { required: true, message: "请输入医院名称", trigger: "change" },
        ],
        priorityCode: [
          { required: true, message: "请选择优先级代码", trigger: "change" },
        ],
        module: [
          { required: true, message: "请选择问题模块", trigger: "change" },
        ],
      },
      issueSearchParams: {
        // 问题列表查询参数
        pageNum: 1,
        pageSize: 10,
      },
      issueTable: {
        //问题列表参数
        pageNum: 1,
        pageSize: 10,
        total: 0,
        records: [],
      }, // 问题列表
      issueDialogConfig: {
      }, //弹窗配置
    };
  },
  methods: {
    /**
     * 问题处理实时websockt服务处理
     */
    initWebSocket() {
      //初始化weosocket
      const wsuri = "ws://127.0.0.1:9028/wss/afs/issue/1265647429739417600";
      this.websock = new WebSocket(wsuri);
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen() {
      //连接建立之后执行send方法发送数据
    },
    websocketonerror() {
      //连接建立失败重连
      this.initWebSocket();
    },
    websocketonmessage(resData) {
      console.log("wss接受消息错");
      console.log(resData);
      //数据接收
      let data = JSON.parse(resData.data);
      console.log(data);
      if (data.code == "200") {
        this.wssDispatcher(data.data);
      } else {
        console.log("wss接受消息错误: " + data.msg);
      }
    },
    websocketsend(data) {
      //数据发送
      this.websock.send(data);
    },
    websocketclose(e) {
      //关闭
      console.log("断开连接", e);
    },
    /**
     * wss问题操作日志处理
     */
    issueOpLogHandler(data) {
      console.log("操作消息处理");
      console.log(data);
      this.logList.push(data);
      this.searchIssue((this.issueSearchParams = {}));
    },
    /**
     * wss提示处理
     */
    logHandler(data) {
      let message = {
        message: data,
        type: "LOG",
        gmt_create: this.$moment(new Date()).format("HH:MM:SS"),
      };
      this.logList.push(message);
    },
    /**
     * wss 操作分发器 将不同类型的数据发送到不同的方法处理
     */
    wssDispatcher(data) {
      console.log(data);
      switch (data.type) {
        case "LOG":
          this.logHandler(data.data);
          break;
        case "ISSUE_OP_LOG":
          this.issueOpLogHandler(data);
          break;
        default:
          console.log("未知的消息类型");
      }
    },
    /**
     * 发布问题
     * @param isAddtional 是否补录
     */
    commonSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          switch (formName) {
            case "issuePostForm":
              this.postIssue();
              break;
            default:
          }
          return true;
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    /**
     * 发布问题
     */
    async postIssue() {
      let res = postIssue(this.issuePostForm);
      if (res) {
        this.commonDialogVisible = false;
        this.$message({
          message: "上传成功！",
          type: "success",
        });
      } else {
        this.$message({
          message: "上传成功！",
          type: "error",
        });
      }
    },
    async updateIssue(){
      let res = updateIssue(this.issueUpdateForm);
      if (res) {
        this.commonDialogVisible = false;
        this.$message({
          message: "更新成功！",
          type: "success",
        });
      } else {
        this.$message({
          message: "更新成功！",
          type: "error",
        });
      }
    },
    /**
     * 搜索问题列表
     */
    async searchIssue() {
      console.log(this.issueSearchParams)
      console.log("ces")
      let res = await searchIssue(this.issueSearchParams);
      this.issueTable = res;
    },

    /**
     * 搜索医院
     */
    async searchHospital(query) {
      console.log(query);
      if (query !== "") {
        this.hospitalSelectLoading = true;
        let res = await getHospital({
          text: query,
          pageNum: 1,
          pageSize: 1000,
        });
        this.hospitalList = res.records;
        this.hospitalSelectLoading = false;
      } else {
        this.hospitalList = [];
      }
    },
    /**
     * 医院选项值改变时间
     */
    hospitalSelectChangeEvt() {
      this.tempForm.hospital = this.hospitals.filter(
        (item) => item.id == this.tempForm.hospital.id
      )[0];
      this.showDUserSelect = true;
    },
    /**
     * 问题分页器处理
     */
    issuePagehandleSizeChange(val) {
      this.issueSearchParams.pageSize = val;
      this.searchIssue();
    },
    issuePagehandleCurrentChange(val) {
      this.issueSearchParams.pageNum = val;
      this.searchIssue();
    },
    /**
     * 获取值班人员列表
     */
    async getWatcherList() {
      let res = await getWatcherList();
      this.watcherList = res;
    },
    /**
     * 通用展示弹窗
     */
    showDiaglog(name) {
      console.log(name);
      console.log(issueDialogConfigs);
      this.issueDialogConfig = issueDialogConfigs[name];
      this.commonDialogVisible = true;
      this.issueDialogConfig.name = name;
    },
    
    /**
     * 开始处理问题
     */
    issueStart(){
        this.issueUpdateForm.state = 1;
        
    },
    /**
     * 问题任务处理完成
     */
    issueComplet(){
        this.issueUpdateForm.state = 2;
    },
    /**
     * 退回问题任务
     */
    issueReturn(){
      this.issueUpdateForm.state = 1;
    },
    /**
     * 问题任务审核完成
     */
    issueAuthAccess(){
      this.issueUpdateForm.state = 3;
    },
    /**
     * 任务指派
     */
    issueAssign(){

    }
  },
  mounted() {
    //初始化websocket服务
    this.initWebSocket();
    //初始化字典
    this.moduleList = [
      { label: "住院", value: "1" },
      { label: "护士", value: "2" },
      { label: "药品", value: "3" },
      { label: "门诊", value: "4" },
    ];
    this.priorityCodeList = [
      { label: "低", value: "1" },
      { label: "中", value: "2" },
      { label: "高", value: "3" },
      { label: "紧急", value: "4" },
    ];
    //初始化表格
    this.searchIssue((this.issueSearchParams = {}));
    this.getWatcherList();
  },
  destroyed() {
    this.websock.close(); //离开路由之后断开websocket连接
  },
};
</script>


<style scoped>
.baseBox {
  box-shadow: 3px 6px 5px 0px #d0d0d0a8;
  border-radius: 4px;
}

.logBox {
  height: 300px;
  margin: 0 0 0 10px;
}

.logBox .content {
  line-height: 20px;
  cursor: pointer;
  color: #909399;
}

.logBox .content:active {
  color: #66b1ff;
}

.personnelBox {
  height: 300px;
  margin: 0 10px 0 0;
}

.issueBox {
  height: 500px;
  margin: 0 10px;
}

.box-header {
  height: 50px;
  padding: 10px 10px 0px 10px;
}
.box-header .iconfont {
  margin: 0 5px;
  font-size: 20px;
  color: #409eff;
}
.box-main {
  padding: 0px 10px 10px 10px;
}
.watcher-table >>> .el-table__row > td {
  /* 去除表格线 */
  border: none;
}
</style>