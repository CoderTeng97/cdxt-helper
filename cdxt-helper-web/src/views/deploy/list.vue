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
                  <span
                    class="value"
                    v-for="arr in props.row.fileSrcList"
                    :key="arr.id"
                    style="display: block;"
                  >{{arr.src}}</span>
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
                :type="scope.row.postLevel == 0 ? 'primary' : 'danger'"
              >{{ scope.row.postLevel ==0 ? '一般':'紧急' }}</el-tag>
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
              <span>{{$moment(scope.row.gmtCreate).format('YYYY-MM-DD HH:MM:SS')}}</span>
            </template>
          </el-table-column>

          <el-table-column class-name="status-col" label="处理情况" align="center">
            <template slot-scope="scope">
              <el-tag
                :type="scope.row.state == 0 ? 'primary' : 'success'"
              >{{ scope.row.state ==0 ? '未处理':'已处理' }}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template slot-scope="scope">
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
                     v-if="scope.row.state == 0 && uid ==scope.row.duid"
                  >
                    <i
                      class="iconfont icon-wancheng5"
                      @click="stateClickEvent(scope.$index, scope.row)"
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
    statusFilter(status) {
      console.log("dsf" + status);
      let statusMap = null;
      if (status == 0) {
        statusMap = {
          type: "danger",
        };
      } else {
        statusMap = {
          type: "info",
        };
      }
      return statusMap[status];
    },
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
  methods: {
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
    stateClickEvent(index, row) {
      console.log(row);
      //设置已处理
      this.updateState(row.id, 1);
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
