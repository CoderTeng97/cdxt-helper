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
                <span v-if="item.type=='notice'">{{item.gmt_create +' ' + item.message}}</span>
                <span v-if="item.type=='op-log'">{{item.content}}</span>
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
                <i class="iconfont icon-zhibanrenyuan"></i>问题处理人员
              </el-col>
              <el-col :span="10"></el-col>
            </el-row>
          </div>
          <div class="box-main">
            <el-table
              :data="watchkeeperList"
              class="watcher-table"
              style="width: 100% ;height:250px;overflow:scroll;"
            >
              <el-table-column prop="username" label="工号"></el-table-column>
              <el-table-column prop="trueName" label="姓名"></el-table-column>
              <el-table-column prop="module" label="负责模块"></el-table-column>
              <el-table-column prop="qq" label="QQ"></el-table-column>
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
            <el-button @click="issuePostDialogVisible=true,issuePostIsAddtional=false">发布问题</el-button>
            <el-button @click="issuePostDialogVisible=true,issuePostIsAddtional=true">补录已完成问题</el-button>
            <i class="iconfont icon-shuaxincopy" style="margin-left:20px;"></i>
            <el-table :data="tableData" style="width: 100%" max-height="250">
              <el-table-column fixed prop="name" label="标题" width="300"></el-table-column>
              <el-table-column prop="zip" label="医院" width="120"></el-table-column>
              <el-table-column prop="city" label="所属模块" width="120"></el-table-column>
              <el-table-column prop="province" label="任务类型" width="120"></el-table-column>
              <el-table-column prop="address" label="紧急程度" width="120"></el-table-column>
              <el-table-column prop="address" label="发布时间" width="150"></el-table-column>
              <el-table-column prop="zip" label="处理时间" width="150"></el-table-column>
              <el-table-column prop="zip" label="发布人员" width="120"></el-table-column>
              <el-table-column prop="zip" label="处理人员" width="120"></el-table-column>
              <el-table-column fixed="right" label="操作" width="120">
                <template slot-scope="scope">
                  <el-button
                    @click.native.prevent="deleteRow(scope.$index, tableData)"
                    type="text"
                    size="small"
                  >移除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage4"
              :page-sizes="[100, 200, 300, 400]"
              :page-size="100"
              layout="total, sizes, prev, pager, next, jumper"
              :total="400"
            ></el-pagination>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 问题发布界面 -->
    <el-dialog :title="issuePostIsAddtional ?  '问题补录' : '问题发布'" :visible.sync="issuePostDialogVisible" :close-on-click-modal="false" width="80%">
      <el-form :model="issuePostForm" :rules="issuePostFormRules" >
        <el-row gutter="10">
          <el-col :span="7">
            <el-form-item prop="title">
              <el-input v-model="issuePostForm.title" placeholder="请输入标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item prop="hospitalId">
              <el-select v-model="issuePostForm.hospitalId" placeholder="请选择医院">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
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
          <el-col :span="4" v-if="issuePostIsAddtional">
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
      <div slot="footer" class="dialog-footer">
        <el-button>清空</el-button>
        <el-button @click="postIssue('issuePostForm')">发布</el-button>
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



export default {
  name: "issueManager",
  components: {
    //使用编辑器
    quillEditor,
  },
  data() {
    return {
      websock: null,
      issuePostDialogVisible: true, //问题发布
      logList: [], //日志模块数据,
      issuePostForm: {}, //问题发布表单对象
      issuePostIsAddtional:false,
      //值班人员列表
      watchkeeperList: [
        {
          username: "2020092",
          trueName: "滕刚",
          qq: "1510545794",
          module: "住院模块",
        },
        {
          username: "2020092",
          trueName: "滕刚",
          qq: "1510545794",
          module: "住院模块",
        },
        {
          username: "2020092",
          trueName: "滕刚",
          qq: "1510545794",
          module: "住院模块",
        },
      ],
      moduleList: [], // 模块下拉列表数据
      priorityCodeList: [], //紧急层度列表
      issuePostFormRules: {
          title: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
          ],
          hospitalId: [
            { required: true, message: '请选择医院', trigger: 'change' }
          ],
          priorityCode: [
            {  required: true, message: '请选择优先级代码', trigger: 'change' }
          ],
          module: [
            {  required: true, message: '请选择问题模块', trigger: 'change' }
          ]
        }
    };
  },
  methods: {
    /**
     * 问题处理实时websockt服务处理
     */
    initWebSocket() {
      //初始化weosocket
      const wsuri = "ws://127.0.0.1:9028/wss/afs/issue/1234577";
      this.websock = new WebSocket(wsuri);
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen() {
      //连接建立之后执行send方法发送数据
      let actions = { test: "12345" };
      this.websocketsend(JSON.stringify(actions));
    },
    websocketonerror() {
      //连接建立失败重连
      this.initWebSocket();
    },
    websocketonmessage(resData) {
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
    issueOpLogHandler(data) {},
    /**
     * wss提示处理
     */
    noticeHandler(data) {
      console.log("通知处理");
      console.log(data);
      let date = new Date();
      console.log(date); //Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)
      let dateformat =
        date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
      let message = {
        message: data,
        type: "notice",
        gmt_create: dateformat,
      };
      this.logList.push(message);
    },
    /**
     * wss 操作分发器 将不同类型的数据发送到不同的方法处理
     */
    wssDispatcher(data) {
      switch (data.type) {
        case "log":
          this.noticeHandler(data.data);
          break;
        case "notice":
          this.noticeHandler(data.data);
          break;
        default:
          console.log("未知的消息类型");
      }
    },
    /**
     * 发布问题
     * @param isAddtional 是否补录
     */
    issuePostSubmit(formName){
      this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
      });
    }
  },
  mounted() {
    this.initWebSocket();
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