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

          <el-table-column align="right">
            <template slot="header" slot-scope="scope">
              <el-input  v-model="rparams.text" size="medium" placeholder="输入关键字搜索"  >
                 <el-button slot="append" icon="el-icon-search" @click="fetchData"></el-button>
              </el-input>
            </template>
            <template slot-scope="scope">
              <el-button size="mini" type="danger" >放置医院对象VPN、DB、分支信息并管理（未开发）</el-button>
               <!--<el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
              >Delete</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12" style="margin-top: 20px;">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage2"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="rparams.pageSize"
          layout="total,sizes, prev, pager, next"
          :total="resData.pages"
        ></el-pagination>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { deployList, getHospital } from "../../api/deploy";

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
    };
  },
  filters: {
    statusFilter(status) {
      console.log("dsf" + status);
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
