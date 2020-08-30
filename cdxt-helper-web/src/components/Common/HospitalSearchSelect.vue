<template>
  <div>
    <el-select
      v-model="hospitalId"
      filterable
      remote
      reserve-keyword
      placeholder="请输入医院名称搜索"
      :remote-method="searchHospitalList"
      :loading="loading"
      @change="valueChangeEvent"
    >
      <el-option v-for="item in hospitalList" :key="item.id" :label="item.name" :value="item.id" ></el-option>
    </el-select>
  </div>
</template>


<script>
import {getHospitalList} from '@/api/common'
export default {
  name: "HospitalSearchSelect",
  data() {
      return {
        hospitalList:[],
        hospitalId:'',
        loading: false,
      }
  },
  props:['value'],
  model:{
    prop:'value',
    event:'change'
  },
  methods:{
    async searchHospitalList(text){
        this.loading = true;
        let res = await getHospitalList(text);
        this.hospitalList = res.records;
        this.loading = false;
    },
    valueChangeEvent(event){
        this.$emit("change", event);
    }
  }
};
</script>