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
          <el-table-column label="医院">
            <template slot-scope="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column label="所属分支" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.branch }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="created_at" label="发布时间">
            <template slot-scope="scope">
              <span>{{ scope.row.gmtCreate }}</span>
            </template>
          </el-table-column>

          <el-table-column align="center" prop="created_at" label="更新时间">
            <template slot-scope="scope">
              <span>{{ scope.row.gmtCreate }}</span>
            </template>
          </el-table-column>

          <el-table-column align="left">
            <template slot="header" slot-scope="scope">
              <el-input  v-model="rparams.text" size="medium" placeholder="输入关键字搜索"  @keyup.enter.native="fetchData">
                 <el-button slot="append" icon="el-icon-search" @click="fetchData"></el-button>
              </el-input>
            </template>
            <template slot-scope="scope">
              <el-tooltip
                    class="item"
                    effect="dark"
                    content="修改"
                    placement="top-start"
                  >
              <i class="iconfont icon-icon-xiugai" @click="showBranchDialog('update',scope.row)"></i>
              </el-tooltip>
              <!-- <i class="iconfont icon-shujuku"></i>
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
         <el-button type="primary"  size="mini" icon="el-icon-plus" @click="showBranchDialog('post',null)">新增</el-button>
      </el-col>
    </el-row>


     <!-- 医院发布及更新弹窗 -->
    <el-dialog  
    :title="branchUpdateFormModel=='update'?'分支更新' : '分支发布'" 
    :visible.sync="branchUpdateFormVisible"
    width="30%"
    >
      <el-form :model="branchUpdateForm" ref="branchUpdateForm" :rules="rules">
        <el-form-item  label="医院Id" :label-width="formLabelWidth" hidden>
          <el-input v-model="branchUpdateForm.id"></el-input>
        </el-form-item>
        <el-form-item  prop="name" label="医院名称" :label-width="formLabelWidth">
          <el-input v-model="branchUpdateForm.name" placeholder="请输入医院名称"
          ></el-input>
        </el-form-item>
        <el-form-item  prop="branch" label="对应分支" :label-width="formLabelWidth">
          <el-input v-model="branchUpdateForm.branch"  placeholder="请输入对应分支信息"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBranchUpdateForm('branchUpdateForm')">{{branchUpdateFormModel=='update'?'更 新' : '发 布'}}</el-button>
        <el-button @click="branchUpdateFormVisible = false">取 消</el-button>
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
import { deployList, getHospital,saveAndUpdateHospital } from "../../api/deploy";

export default {
  data() {
    return {
      resData: {},
      listLoading: true,
      isUpdate: false, //是否修改
      rparams: {
        text: '',
        pageNum: 1,
        pageSize: 10
      },
      branchUpdateFormVisible:false,//显示更新或发布分支弹窗
      branchUpdateFormModel:"post",//分支更新弹窗类型
      branchUpdateForm:{
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
    this.rparams.text='';
    this.fetchData();
  },
  methods: {
    /**
     * 打开更新或新增弹窗
     */
    showBranchDialog(model,data){
        this.branchUpdateFormVisible=true;
        this.branchUpdateFormModel = model!= 'post' && model!= 'update' ? 'post' : model;
        if(this.branchUpdateFormModel == 'update'){
          this.branchUpdateForm = data
        }else{
          this.branchUpdateForm={}
        }
        console.log(this.branchUpdateForm)
    },
    submitBranchUpdateForm(fromName){
      this.$refs[fromName].validate(valid => {
        if (valid) {
          this.saveAndUpdateBranch();
        } else {
          return false;
        }
      });
    },
    async saveAndUpdateBranch(){
      console.log(this.branchUpdateForm)
      let result = await saveAndUpdateHospital(this.branchUpdateForm)
      if(result){
        this.$message({
          message: "操作成功",
          type: "success"
        });
        this.branchUpdateFormVisible=false;
      }
    },
    fetchData() {
      this.listLoading = true;
      this.getHospitalList();
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
    async getHospitalList() {
      let res = await getHospital(this.rparams);
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
