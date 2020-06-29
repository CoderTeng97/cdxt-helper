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
              v-model="tempForm.hospital"
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
                :value="item"
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
            <el-select v-model="form.postLevel" placeholder="请选择活动区域">
              <el-option label="一般" value="0"></el-option>
              <el-option label="紧急" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="24" v-if="tempForm.dUser.id !=''">
          <el-form-item label="处理人员" prop="dUid">
            <el-select v-model="tempForm.dUser" placeholder="请选择活动区域">
              <el-option
                :label="tempForm.dUser.trueName"
                :key="tempForm.dUser.id"
                :value="tempForm"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="15" :xs="24">
          <el-form-item label="备注(功能说明)" prop="remark">
            <el-input type="textarea" v-model="form.remark" :autosize="{ minRows: 5, maxRows: 5}"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item>
            <el-button type="primary" @click="onSubmit('form')">发布</el-button>
            <el-button @click="resetForm">清空</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { deployPost, getHospital } from "../../api/deploy";
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
      form: {
        hospitalId: "",
        branch: "",
        remark: "",
        postLevel: "0",
        fileSrcList: [], //补丁路径列表
        dUid: "" //处理补丁用户
      },
      tempForm: {
        // 临时表单变量
        fileSrcList: [],
        fileSrc: "", //补丁路径 表达临时存储路径，为了方便值得转换
        hospital: {}, //医院
        dUser: {
          //处理用户
          id: "",
          trueName: ""
        }
      },
      hospitals: [], // 医院列表
      rules: {
        hospital: [
          { validator: validateHospital, trigger: "change" }
        ]
      } //校验规则
    };
  },

  methods: {
    //提交的时候
    onSubmit(formName) {
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
      this.form = {};
      this.tempForm = {};
    },
    /**
     * 医院选项值改变时间
     */
    hospitalSelectChangeEvt() {
      //根据医院获取处理用户
      this.tempForm.dUser = {
        id: "1265647429739417605",
        trueName: "唐文鑫"
      };
    },

    async post() {
      //处理分支
      this.form.hospitalId = this.tempForm.hospital.id;
      this.form.branch = this.tempForm.hospital.branch;
      //处理路径
      let tempFileSrcList = new Array();
      for (var item in this.tempForm.fileSrcList) {
        tempFileSrcList.push(this.tempForm.fileSrcList[item].src);
      }
      tempFileSrcList.push(this.tempForm.fileSrc);
      console.log(tempFileSrcList);
      this.form.fileSrcList = tempFileSrcList;
      //设置处理人
      this.form.dUid = this.tempForm.dUser.id;
      let res = await deployPost(this.form);
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
          pageSize: 1000
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


