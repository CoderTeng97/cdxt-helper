<template>
  <div class="app-container">
    <!-- 条件筛选表达 -->
    <el-row style="margin-bottom: 20px">
      <el-col :xs="24" :md="6" :lg="3" :xl="3">
        <el-select
          style="width:100%;"
          v-model="hospital"
          filterable
          remote
          clearable
          reserve-keyword
          placeholder="请输入医院关键词搜索"
          :remote-method="searchHospital"
          :loading="loading"
          @change="hospitalSelectChangeEvt"
        >
          <el-option v-for="item in hospitals" :key="item.name" :label="item.name" :value="item"></el-option>
        </el-select>
      </el-col>
      <el-col :xs="24" :md="11" :lg="9" :xl="6" :offset="1">
        <el-date-picker
          v-model="postTime"
          type="datetimerange"
          :picker-options="pickerOptions"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          align="right"
        ></el-date-picker>
      </el-col>

      <el-button icon="el-icon-search" @click="searchBtnEvent" circle></el-button>
    </el-row>
    <!-- 数据展示列表 -->
    <el-row>
      <el-col :span="24">
        <el-table
          v-loading="listLoading"
          border
          :data="resData.records"
          element-loading-text="Loading"
          fit="true"
          highlight-current-row
        >
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="top" class="demo-table-expand">
                <el-form-item label="更新路径">
                  <p  v-html="props.row.detail" style="line-height: 25px;"></p>
                </el-form-item>
                <el-form-item label="备注">
                  <div v-html="props.row.remark"></div>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="ID">
            <template slot-scope="scope">{{ scope.row.id }}</template>
          </el-table-column>
          <el-table-column label="医院">
            <template slot-scope="scope">{{ scope.row.hospitalName }}</template>
          </el-table-column>
          <el-table-column label="所属分支" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.branch }}</span>
            </template>
          </el-table-column>
          <el-table-column class-name="status-col" label="紧急程度" align="center">
            <template slot-scope="scope">
              <el-tag
                :type="formatPostLevel(scope.row.postLevel)"
              >{{ formatPostLevelText(scope.row.postLevel) }}</el-tag>
            </template>
          </el-table-column>

          <el-table-column align="center" prop="created_at" label="发布人">
            <template slot-scope="scope">{{ scope.row.pusername}}</template>
          </el-table-column>

          <el-table-column align="center" prop="created_at" label="处理人">
            <template slot-scope="scope">{{ scope.row.dusername}}</template>
          </el-table-column>

          <el-table-column align="center" prop="created_at" label="发布时间">
            <template slot-scope="scope">
              <span>{{$moment(scope.row.gmtCreate).format('YYYY-MM-DD HH:mm:ss')}}</span>
            </template>
          </el-table-column>

          <el-table-column class-name="status-col" label="处理情况" align="center">
            <template slot-scope="scope">
              <el-tag :type="formatStatus(scope.row.state)">{{ formatStatusText(scope.row.state)}}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template slot-scope="scope">
                <el-tooltip
                    class="item"
                    effect="dark"
                    content="开始处理 "
                    placement="top-start"
                     v-if="scope.row.state == 0 && uid ==scope.row.duid"
                  >
                    <i
                      class="iconfont icon-xiaoyan"
                      @click="stateClickEvent(scope.$index, scope.row,1)"
                    ></i>
                  </el-tooltip>
                  <el-tooltip
                    class="item"
                    effect="dark"
                    content="指派"
                    placement="top-start"
                    v-if="scope.row.state == 0 && uid ==scope.row.duid"
                  >
                    <i
                      class="iconfont icon-zhipai"
                      @click="designatedUserBtnClickEvent(scope.$index, scope.row)"
                    ></i>
                  </el-tooltip>

                  <el-tooltip
                    class="item"
                    effect="dark"
                    content="完成"
                    placement="top-start"
                     v-if="scope.row.state == 1 && uid ==scope.row.duid"
                  >
                    <i
                      class="iconfont icon-wanchenggongzuo"
                      @click="stateClickEvent(scope.$index, scope.row,2)"
                    ></i>
                  </el-tooltip>

                  <el-tooltip
                    class="item"
                    effect="dark"
                    content="废弃"
                    placement="top-start"
                     v-if="scope.row.state == 0 && uid ==scope.row.duid"
                  >
                    <i
                      class="iconfont icon-shanchu"
                      @click="stateClickEvent(scope.$index, scope.row,3)"
                    ></i>
                  </el-tooltip>
              <!-- <el-button v-if="scope.row.state == 0" type="" size="mini" @click="downloadPatches(scope.row)">下载补丁</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12" style="margin-top: 20px;">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="rparams.pageNum"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="rparams.pageSize"
          layout="total,sizes, prev, pager, next"
          :total="resData.total"
        ></el-pagination>
      </el-col>
    </el-row>



    <!-- 值班人员设置弹窗 -->
    <el-dialog title="处理人员设置" :visible.sync="dUserSetDialogVisible" width="30%">
          <el-row   type="flex" justify=" space-around">
             <el-col :span="24">
               <UserSearchSelect :role="IMPLEMENTER" v-model="designatedUserParams.duid" name="designatedUserSelect"></UserSearchSelect>
            </el-col>
          </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="designateDUser()" >指派</el-button>
        <el-button @click="dUserSetDialogVisible=false" >取消</el-button>
      </div>
    </el-dialog>
  </div>
  
</template>

<script>
import {
  deployList,
  udpateState,
  downloadPatches,
  getHospital,
  designateDUser
} from "../../api/deploy";
import { mapGetters } from "vuex";
import { formatTime } from "../../utils";
import UserSearchSelect from "@/components/Common/UserSearchSelect";

export default {
  components:{
    UserSearchSelect
  },
  data() {
    return {
      intervalId: null,//定时Id
      dUserSetDialogVisible:false,//处理人员设置
      designatedUserParams:{
        duid:'',//处理用户id
        deployPostId:'' //问题id
      },
      resData: {},
      listLoading: true,
      rparams: {
        hospitalId: "",
        pageNum: 1,
        pageSize: 10,
        postStartTime: null,
        postEndTime: null,
      },
      hospital: "",
      postTime: [],
      hospitals: [], // 医院列表
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      value1: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
      value2: "",
    };
  },
  filters: {
    
  },
  // postLevelFilter(level) {
  //   let statusMap = null;
  //   if (level == 0) {
  //     statusMap = {
  //       published: "danger",
  //       draft: "gray",
  //       deleted: "danger"
  //     };
  //   } else {
  //     statusMap = {
  //       published: "info",
  //       draft: "gray",
  //       deleted: "danger"
  //     };
  //   }
  // },
  created() {
    this.fetchData();
  },
  computed: {
     ...mapGetters({
      uid: "uid",
      username: "name",
      role: "role",
      token: "token"
    }),
  },
  destroyed(){

  },
  
  methods: {
    /**
     * 定时刷新页面
     */
    dataRefreh() {
      // 计时器正在进行中，退出函数
      if (this.intervalId != null) {
        return;
      }
      // 计时器为空，操作
      this.intervalId = setInterval(() => {
        console.log("刷新" + new Date());
        this.getDeployList();
      }, 60000);
    }, 
    // 停止定时器
    clear() {
      clearInterval(this.intervalId); //清除计时器
      this.intervalId = null; //设置为null
    },
    /**
     * 表格状态处理方法
     */
    formatStatus(status) {
      console.log("状态" + status)
      let tagType = 'danger';
      switch(status){
        case 0 : tagType='';break;
        case 1 : tagType='warning';break;
        case 2 : tagType='success';break;
        case 3 : tagType='info';break;
        default :tagType='';
      }
      return tagType;
    },
    formatStatusText(status) {
      let tagText = '';
      switch(status){
        case 0 : tagText='未处理';break;
        case 1 : tagText='处理中';break;
        case 2 : tagText='已处理'; break;
        case 3 : tagText='已废弃'; break;
        default :tagText='';
      }
      return tagText;
    },

    /**
     * 表格发布等级处理方法 
     */
    formatPostLevel(status) {
      let tagType = 'danger';
      switch(status){
        case 0 : tagType='';break;
        case 1 : tagType='danger';break;
        case 2 : tagType='warning';break;
        default :tagType='';
      }
      return tagType;
    },
    formatPostLevelText(status) {
      console.log("状态" + status)
      let tagText = '';
      switch(status){
        case 0 : tagText='一般';break;
        case 1 : tagText='紧急';break;
        case 2 : tagText='弱'; break;
        default :tagText='';
      }
      return tagText;
    },
    /**
     * 下载补丁
     */
    async downloadPatches(row) {
      let a = document.createElement("a");
      a.href = "http://39.106.183.121/svn/" + row.id + ".zip";
      a.click();
    },
    fetchData() {
      this.listLoading = true;
      this.getDeployList();
      this.listLoading = false;
    },
    stateClickEvent(index, row,status) {
      console.log(row);
      //设置已处理
      this.updateState(row.id, status);
    },
    /**
     * 获取部署列表
     */
    async getDeployList() {
      this.rparams.postStartTime = this.postTime[0];
      this.rparams.postEndTime = this.postTime[1];
      this.rparams.hospitalId = this.hospital.id;
      let res = await deployList(this.rparams);
      this.resData = res;
      console.log(this.resData);
      console.log(this.rparams);
    },
    /**
     * 更新记录状态
     */
    async updateState(id, state) {
      let res = await udpateState({ did: id, state: state });
      if (res) {
        this.$message({
          message: "更新成功！",
          type: "success",
        });
        this.fetchData();
      } else {
        this.$message({
          message: "更新失败！",
          type: "info",
        });
      }
    },

    /**
     * 分页器处理
     */
    handleSizeChange(val) {
      this.rparams.pageSize = val;
      this.fetchData();
    },
    handleCurrentChange(val) {
      this.rparams.pageNum = val;
      this.fetchData();
    },
    /**
     * 医院选项值改变时间
     */
    hospitalSelectChangeEvt() {},

    /**
     * 获取医院列表
     */
    async getHospitalList() {
      let res = await getHospital(this.rparams);
      this.resData = res;
    },

    /**
     * 搜索医院
     */
    async searchHospital(query) {
      console.log(query);
      if (query !== "") {
        this.loading = true;
        let res = await getHospital({
          text: query,
          pageNum: 1,
          pageSize: 1000,
        });
        this.hospitals = res.records;
        console.log(res);
        this.loading = false;
      } else {
        this.options = [];
      }
    },
    /**
     * 搜索事件
     */
    searchBtnEvent() {
      this.getDeployList();
    },
    designatedUserBtnClickEvent(index, row){
        this.designatedUserParams.deployPostId=row.id;
        this.dUserSetDialogVisible = true;
    },
    /**
     * 指派用户
     */
    async designateDUser(){
        let isSet = await designateDUser(this.designatedUserParams);
        if (isSet){
          this.$message({
          message: "指派成功！",
          type: "success",
          });
          this.fetchData();
        }
    }
  },
};
</script>
