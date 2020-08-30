<template>
  <div>
    <el-select
      v-model="uid"
      filterable
      remote
      reserve-keyword
      placeholder="请输入用户名称搜索"
      :remote-method="searchUserList"
      :loading="loading"
      @change="valueChangeEvent"
    >
      <el-option v-for="item in userList" :key="item.id" :label="item.true_name" :value="item.id"></el-option>
    </el-select>
  </div>
</template>


<script>
import {getUserList} from '@/api/user'
export default {
  name: "UserSearchSelect",
  props:['value'],
  model:{
    prop:'value',
    event:'change'
  },
  data() {
      return {
        userList:[],
        uid:'',
        loading: false,
      }
  },
  methods:{
    async searchUserList(text){
       
        this.loading = true;
        let res = await getUserList(this.role,text);
         console.log(res)
        this.userList = res;
        this.loading = false;
    },
    valueChangeEvent(event){
        this.$emit("change", event);
    }
  }
};
</script>