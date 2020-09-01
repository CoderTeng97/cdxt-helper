<template>
  <div class="app-container">
    <el-row>
      <el-col :span="24">
        <el-table
          v-loading="listLoading"
          border
          :data="resData.records"
          element-loading-text="Loading"
          :fit="true"
          highlight-current-row
        >
          <!-- <el-table-column label="ID">
            <template slot-scope="scope">{{ scope.row.id }}</template>
          </el-table-column>-->

          <el-table-column type="index" width="50"></el-table-column>
          <el-table-column label="用户名">
            <template slot-scope="scope">{{ scope.row.username }}</template>
          </el-table-column>
          <el-table-column label="真实姓名" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.trueName }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="部门">
            <template slot-scope="scope">
              <span>{{ scope.row.dept }}</span>
            </template>
          </el-table-column>

          <el-table-column align="center" prop="created_at" label="负责模块">
            <template slot-scope="scope">
              <span>{{ scope.row.module}}</span>
            </template>
          </el-table-column>

          <el-table-column align="center" prop="created_at" label="电话">
            <template slot-scope="scope">
              <span>{{ scope.row.phone}}</span>
            </template>
          </el-table-column>

          <el-table-column align="center" label="用户权限">
            <template slot-scope="scope">
              <span>{{ scope.row.role }}</span>
            </template>
          </el-table-column>

          <el-table-column align="left">
            <template slot="header" slot-scope="scope">
              <el-input v-model="rparams.trueName" size="medium" placeholder="输入关键字搜索">
                <el-button slot="append" icon="el-icon-search" @click="fetchData"></el-button>
              </el-input>
            </template>
            <template slot-scope="scope">
              <el-button type="text" @click="showBranchDialog('update',scope.row)">修改</el-button>
              <!-- <i class="iconfont icon-xiugai" @click="showBranchDialog('update',scope.row)">修改1</i> -->
              <!-- <i class="iconfont icon-shujuku"></i>
              <i class="iconfont icon-aistubiaozhizuo--copy"></i>
              <i class="iconfont icon-xianchangrenyuanweihu-copy"></i>
               <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
              >Delete</el-button>-->
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <el-row type="flex" style="margin-top: 20px;">
      <el-col :span="24">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage2"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="rparams.pageSize"
          layout="total,sizes, prev, pager, next"
          :total="resData.total"
        ></el-pagination>
      </el-col>
      <el-col>
        <!-- <i class="iconfont icon-xinzeng" @click="showBranchDialog('post',null)" style="color: #66b1ff;">
        </i>-->
      </el-col>
    </el-row>

    <!-- 新增用户-->
    <el-dialog title="修改信息" :visible.sync="userSaveAndUpdateFormVisible" width="30%">
      <el-form :model="userSaveAndUpdateForm" ref="userSaveAndUpdateForm" :rules="rules">
        <el-form-item label="用户名" label-width="80px" >
          <el-input v-model="userSaveAndUpdateForm.username"   disabled="false"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" label-width="80px" >
          <el-input v-model="userSaveAndUpdateForm.trueName"  disabled="false"></el-input>
        </el-form-item>
        <el-form-item prop="branch" label="部门" label-width="80px">
          <el-input v-model="userSaveAndUpdateForm.dept" placeholder="请输入部门"></el-input>
        </el-form-item>
        <el-form-item prop="branch" label="模块" label-width="80px">
          <!-- <el-input v-model="userSaveAndUpdateForm.module"  placeholder="请输入模块"></el-input> -->
          <el-select
            v-model="userSaveAndUpdateForm.module"
            placeholder="请选择"
            label-width="80px"
            style="width: 100%;"
          >
            <el-option
              v-for="item in moduleList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="branch" label="权限" label-width="80px">
          <el-select
            v-model="userSaveAndUpdateForm.role"
            placeholder="请选择"
            label-width="80px"
            style="width: 100%;"
          >
            <el-option v-for="(item, index) in branchList" :key="index" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userSaveAndUpdateFormVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="submituserSaveAndUpdateForm('userSaveAndUpdateForm')"
        >{{userSaveAndUpdateFormModel=='update'?'更 新' : '发 布'}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style lang="scss" scoped>
.iconfont {
  font-size: 25px;
  cursor: pointer;
}

.m-color {
  color: #6cb6ff;
}
</style>

<script>
import { getUserPageInfo,userUpdate } from "@/api/user";
export default {
  data() {
    return {
      currentPage2: 1,

      resData: {},
      listLoading: true,
      isUpdate: false, //是否修改
      rparams: {
        trueName: "",
        pageNum: 1,
        pageSize: 10
      },
      userSaveAndUpdateFormVisible: false, //显示更新或发布分支弹窗
      userSaveAndUpdateFormModel: "post", //分支更新弹窗类型
      userSaveAndUpdateForm: {
        id: "",
        name: "",
        module: "",
        role: "",
        dept: ""
      },
      rules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        module: [{ required: true, message: "请选择模块", trigger: "change" }]
      },

      //修改弹框
      //用户权限
      branchList: ["ADMIN", "IMPLEMENTER", "DEVELOPER","AFTERSAFE"],
      moduleList: [
        { label: "住院", value: "1" },
        { label: "护士", value: "2" },
        { label: "药品", value: "3" },
        { label: "门诊", value: "4" }
      ]
    };
  },
  filters: {
    statusFilter(status) {
      let statusMap = null;
      if (status == 0) {
        statusMap = {
          type: "danger"
        };
      } else {
        statusMap = {
          type: "info"
        };
      }
      return statusMap[status];
    }
  },
  created() {
    this.rparams.trueName = "";
    this.fetchData();
  },
  methods: {
    /**
     * 打开更新或新增弹窗
     */
    showBranchDialog(model, data) {
      this.userSaveAndUpdateFormVisible = true;
      this.userSaveAndUpdateForm.id = data.id || 0;
      this.userSaveAndUpdateForm.trueName = data.trueName || "";
      this.userSaveAndUpdateForm.username = data.username || "";
      this.userSaveAndUpdateForm.modulemodule = data.module || "";
      this.userSaveAndUpdateForm.role = data.role || "";
      this.userSaveAndUpdateForm.dept = data.dept || "";

      this.userSaveAndUpdateFormModel =
        model != "post" && model != "update" ? "post" : model;
      if (this.userSaveAndUpdateFormModel == "update") {
        this.userSaveAndUpdateForm = data;
      } else {
        this.userSaveAndUpdateForm = {};
      }
      console.log(this.userSaveAndUpdateForm);
    },
    submituserSaveAndUpdateForm(fromName) {
      this.$refs[fromName].validate(valid => {
        if (valid) {
          this.userSaveAndUpdate();
        } else {
          return false;
        }
      });
    },
    async userSaveAndUpdate() {
      console.log(this.userSaveAndUpdateForm);
      let result = await userUpdate(this.userSaveAndUpdateForm);
      if (result) {
        this.$message({
          message: "操作成功",
          type: "success"
        });
        this.userSaveAndUpdateFormVisible = false;
      }
    },
    fetchData() {
      this.listLoading = true;
      this.getUserList();
      this.listLoading = false;
    },
    stateClickEvent(index, row) {
      console.log(row);
      //设置已处理
      this.updateState(row.id, 1);
      this.fetchData();
    },

    async getUserList() {
      let res = await getUserPageInfo(this.rparams);
      this.resData = res;
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
    }
  }
};
</script>
