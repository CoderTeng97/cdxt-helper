<template>
  <div class="app-container">
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
          <!-- <el-table-column label="ID">
            <template slot-scope="scope">{{ scope.row.id }}</template>
          </el-table-column> -->

           <el-table-column
            type="index"
            width="50">
          </el-table-column>
          <el-table-column label="用户名">
            <template slot-scope="scope">{{ scope.row.username }}</template>
          </el-table-column>
          <el-table-column label="真实姓名" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.trueName }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center"  label="部门">
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

          <el-table-column align="center"  label="用户权限">
            <template slot-scope="scope">
              <span>{{ scope.row.role }}</span>
            </template>
          </el-table-column>

          <el-table-column align="left">
            <template slot="header" slot-scope="scope">
              <el-input  v-model="rparams.trueName" size="medium" placeholder="输入关键字搜索"  >
                 <el-button slot="append" icon="el-icon-search" @click="fetchData"></el-button>
              </el-input>
            </template>
            <template slot-scope="scope">
              <!-- <i class="iconfont icon-xiugai" @click="showBranchDialog('update',scope.row)"></i>
              <i class="iconfont icon-shujuku"></i>
              <i class="iconfont icon-aistubiaozhizuo--copy"></i>
              <i class="iconfont icon-xianchangrenyuanweihu-copy"></i> -->
               <!--<el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
              >Delete</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </el-col>
     
    </el-row>
     
    <el-row type="flex" style="margin-top: 20px;">
      <el-col span="">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage2"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="rparams.pageSize"
          layout="total,sizes, prev, pager, next"
          :total="resData.total"
        >
        </el-pagination>
      </el-col>
      <el-col>
         <!-- <i class="iconfont icon-xinzeng" @click="showBranchDialog('post',null)" style="color: #66b1ff;">
         </i> -->
      </el-col>
    </el-row>


     <!-- 新增用户-->
    <el-dialog  
    :title="新增用户" 
    :visible.sync="userRegistrationFormVisible"
    width="30%"
    >
      <el-form :model="userRegistrationForm" ref="userRegistrationForm" :rules="rules">
        <el-form-item  label="用户名" :label-width="formLabelWidth" hidden>
          <el-input v-model="userRegistrationForm.username"></el-input>
        </el-form-item>
        <el-form-item  prop="name" label="真实姓名" :label-width="formLabelWidth">
          <el-input v-model="userRegistrationForm.name" placeholder="请输入姓名"
          ></el-input>
        </el-form-item>
        <el-form-item  prop="branch" label="部门" :label-width="formLabelWidth">
          <el-input v-model="userRegistrationForm.branch"  placeholder="请输入部门"></el-input>
        </el-form-item>
        <el-form-item  prop="branch" label="模块" :label-width="formLabelWidth">
          <el-input v-model="userRegistrationForm.branch"  placeholder="请输入部门"></el-input>
        </el-form-item>
        <el-form-item  prop="branch" label="权限" :label-width="formLabelWidth">
          <el-input v-model="userRegistrationForm.branch"  placeholder="请输入部门"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userRegistrationFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submituserRegistrationForm('userRegistrationForm')">{{userRegistrationFormModel=='update'?'更 新' : '发 布'}}</el-button>

      </div>
</el-dialog>
  </div>
</template>
<style lang="scss" scoped>
  .iconfont{
    font-size: 25px;
    cursor: pointer;
  }

  .m-color
  {
    color: #6cb6ff;
  }

</style>>

</style>

<script>
import { getUserPageInfo} from "@/api/user"
export default {
  data() {
    return {
      resData: {},
      listLoading: true,
      isUpdate: false, //是否修改
      rparams: {
        trueName: '',
        pageNum: 1,
        pageSize: 10
      },
      userRegistrationFormVisible:false,//显示更新或发布分支弹窗
      userRegistrationFormModel:"post",//分支更新弹窗类型
      userRegistrationForm:{
        id:"",
        name:"",
        branch:"",
      },
      rules:{
        name: [
            { required: true, message: '请输入医院名称', trigger: 'blur' },
          ],
         branch: [
            { required: true, message: '请输入分支信息', trigger: 'blur' },
          ]
      }
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
    this.rparams.trueName='';
    this.fetchData();
  },
  methods: {
    /**
     * 打开更新或新增弹窗
     */
    showBranchDialog(model,data){
        this.userRegistrationFormVisible=true;
        this.userRegistrationFormModel = model!= 'post' && model!= 'update' ? 'post' : model;
        if(this.userRegistrationFormModel == 'update'){
          this.userRegistrationForm = data
        }else{
          this.userRegistrationForm={}
        }
        console.log(this.userRegistrationForm)
    },
    submituserRegistrationForm(fromName){
      this.$refs[fromName].validate(valid => {
        if (valid) {
          this.saveAndUpdateBranch();
        } else {
          return false;
        }
      });
    },
    async saveAndUpdateBranch(){
      console.log(this.userRegistrationForm)
      let result = await saveAndUpdateHospital(this.userRegistrationForm)
      if(result){
        this.$message({
          message: "操作成功",
          type: "success"
        });
        this.userRegistrationFormVisible=false;
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
    /**
     * 获取医院列表
     */
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
