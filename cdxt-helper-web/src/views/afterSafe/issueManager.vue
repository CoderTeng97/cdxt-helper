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
              <el-col :span="24" class="content">XXX问题已被处理</el-col>
              <el-col :span="24" class="content">XXX问题已被处理</el-col>
              <el-col :span="24" class="content">XXX问题已被处理</el-col>
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
            <el-table :data="watchkeeperList" style="width: 100% ;height:250px;overflow:scroll">
              <el-table-column prop="username" label="工号" width="180"></el-table-column>
              <el-table-column prop="trueName" label="姓名" width="180"></el-table-column>
              <el-table-column prop="module" label="负责模块" width="180"></el-table-column>
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
            <el-button @click="issuePostDialogVisible=true">发布问题</el-button>
            <el-button @click="clearFilter">补录已完成问题</el-button>
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
    <el-dialog title="问题发布" :visible.sync="issuePostDialogVisible">
      <quillEditor></quillEditor>   
      <div slot="footer" class="dialog-footer">
        <el-button @click="issuePostDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="issuePostDialogVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { QuillEditor } from '@/components/QuillEditor' //调用编辑器
export default {
  name: "issueManager",
  components: {
    //使用编辑器
    QuillEditor,
  },
  data() {
    return {
      issuePostDialogVisible: true, //问题发布
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
    };
  },
  methods: {},
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
</style>