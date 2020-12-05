<template>
  <div class="app-container">
    <el-form
      :rules="rules"
      ref="form"
      :model="form"
      label-width="120px"
      label-position="left"
      class="demo-ruleForm"
    >
      <el-row>
        <el-col :span="24">
          <el-form-item label="医院" prop="hospital">
            <el-select
              v-model="tempForm.hospital.id"
              filterable
              remote
              reserve-keyword
              placeholder="请输入关键词搜索"
              :remote-method="searchHospital"
              :loading="loading"
              @change="hospitalSelectChangeEvt"
            >
              <el-option
                v-for="item in hospitals"
                :key="item.name"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="15" :xs="24">
          <el-form-item label="补丁路径" :prop="fileSrc" >
            <el-input v-model="tempForm.fileSrc">
              <template slot="prepend">{{tempForm.hospital.branch}}/</template>
              <el-button slot="append" icon="el-icon-plus" @click.prevent="addFileSrcListDomain"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item
            v-for="(domain, index) in tempForm.fileSrcList"
            :key="domain.key"
            :prop="'tempForm.fileSrcList.' + index + '.value'"
          >
            <el-input v-model="domain.src">
              <template slot="prepend">{{tempForm.hospital.branch}}/</template>
              <el-button
                slot="append"
                icon="el-icon-minus"
                @click.prevent="removeFileSrcListDomain(domain)"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="紧急程度" prop="postLevel">
            <el-select v-model="form.postLevel" placeholder="请选择紧急程度">
              <el-option label="弱" value="2"></el-option>
              <el-option label="一般" value="0"></el-option>
              <el-option label="紧急" value="1"></el-option>
              
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="24" v-if="showDUserSelect">
          <el-form-item label="处理人员" prop="dUid">
            <el-select v-model="form.dUid" placeholder="请选择处理人员">
              <el-option
                v-for="item in deployerList"
                :key="item.id"
                :label="item.true_name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="15" :xs="24">
          <el-form-item label="备注(功能说明)" prop="remark">
            <el-input type="textarea"   v-model="form.remark"  :autosize="{ minRows: 5, maxRows: 5}"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="">
            <el-link type="danger" disabled>Ps:补丁发布不对发布用户提供修改删除功能,如果补丁处于未处理状态用户可进行废弃补丁操作，则补丁不再发布！</el-link>
          </el-form-item>
          
        </el-col>

        <el-col :span="24">
          <el-form-item>
            <el-button type="primary" @click="onSubmit('form')" :loading="postLoading">发布</el-button>
            <el-button @click="resetForm">清空</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { deployPost, getHospital } from "../../api/deploy";
import {getUserList} from "../../api/user"
export default {
  data() {
    let validateHospital = (rule, value, callback) => {
    
       if ( this.tempForm.hospital.id == undefined || this.tempForm.hospital.id == "" ) {
        callback(new Error("请输入医院信息"));
      } else {
        callback();
      }
    };

    return {
      loading: false,
      postLoading:false,//发布加载
      form: {
        hospitalId: "",
        branch: "",
        remark: "",
        postLevel: "2",
        detail: "", //补丁路径列表
        dUid: "" //处理补丁用户
      },
      tempForm: {
        // 临时表单变量
        fileSrcList: [],
        fileSrc: "", //补丁路径 表达临时存储路径，为了方便值得转换
        hospital: {}, //医院
      },
      hospitals: [], // 医院列表
      deployerList: [],//部署人员列表,
      showDUserSelect: false,//显示处理人员下拉框
      rules: {
        hospital: [
          { validator: validateHospital, trigger: "change" }
        ],
        dUid: [
          { required: true, message: '请选择处理人员', trigger: 'change' }
        ],
        remark: [
          { required: true, message: '备注不能为空', trigger: 'blur' }
        ]
      } //校验规则
    };
  },

  mounted () {
    this.initPage();
  },
  methods: {
    /**
     * 初始化页面
     */
    async initPage(){
      //获取部署人员列表
      this.deployerList = await getUserList('DEPLOYER','')
      console.log(this.deployerList)
    },
    //提交的时候
    onSubmit(formName) {
      console.log(this.form)
      this.$refs[formName].validate(valid => {
        if (valid) {
          console.log(this.tempForm)
          if(this.tempForm.fileSrc == undefined ||  this.tempForm.fileSrc  ==''){
              this.$message("路径信息不能为空");
              return false;
          }
          this.post();
        } else {
          return false;
        }
      });
    },

    /**
     * 重置表单
     */
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.tempForm.fileSrcList=[];
      this.form.fileSrcList=[];
    },
    /**
     * 医院选项值改变时间
     */
    hospitalSelectChangeEvt() {
      this.tempForm.hospital = this.hospitals.filter(item=>item.id==this.tempForm.hospital.id)[0];
      this.showDUserSelect = true;
    },

    async post() {
      this.postLoading = true;
      //处理分支
      this.form.hospitalId = this.tempForm.hospital.id;
      this.form.branch = this.tempForm.hospital.branch;
      //处理路径
      this.form.detail = this.tempForm.fileSrc +"<br/>";
      for (var item in this.tempForm.fileSrcList) {
        this.form.detail +=  this.tempForm.fileSrcList[item].src + "<br/>";
      }
      let res = await deployPost(this.form);
      this.postLoading = false;
      if (res) {
        this.$message({
          message: "上传成功！",
          type: "success"
        });
        this.resetForm("form");
      } else {
        this.$message("上传失败");
      }
    },
    /**
     * 新增文件路径URL
     */
    addFileSrcListDomain() {
      this.tempForm.fileSrcList.push({
        src: ""
      });
    },
    /**
     * 移除文件路径URL
     */
    removeFileSrcListDomain(item) {
      console.log(item);
      var index = this.tempForm.fileSrcList.indexOf(item);
      if (index !== -1) {
        this.tempForm.fileSrcList.splice(index, 1);
      }
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
          pageSize: 50
        });
        this.hospitals = res.records;
        console.log(res);
        this.loading = false;
      } else {
        this.options = [];
      }
    }
  }
};
</script>

<style scoped>
.line {
  text-align: center;
}
</style>


