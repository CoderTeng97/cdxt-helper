<template>
  <div class="app-container">
    <el-button type="primary" size="mini" icon="el-icon-plus" style="margin-bottom: 10px;" @click="onAdd">新增模板</el-button>
    <el-table
      :data="tableData"
      border
      highlight-current-row
    >
      <el-table-column
        prop="name"
        label="模板名称"
        width="200"
      />
      <el-table-column
        prop="fileName"
        label="文件名称"
      />
      <el-table-column
        label="操作"
        width="150"
      >
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="onTableUpdate(scope.row)">修改</el-button>
          <el-button type="text" size="mini" @click="onTableDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {
  getTemplateList,
  deleteTemplate
} from "../../../api/codeGen.js";
export default {
  data() {
    return {
      tableData: []
    }
  },
  created() {
    this.loadTable()
  },
  methods: {
    loadTable: function() {
		let resp = getTemplateList({}).then(res =>{
				 this.tableData = res;
			});
		
    },
    onTableUpdate: function(row) {
      this.goRoute(`edit/${row.id}`)
    },
    onTableDelete: function(row) {
      this.confirm(`确认要删除【${row.name}】吗？`, function(done) {
		  let resp = deleteTemplate(row).then(res =>{
				 done()
				 this.tip('删除成功')
				 this.loadTable()
			});
		 
      })
    },
    onAdd: function() {
      this.goRoute('edit/0')
    },
	goRoute: function(path) {
		console.log(path);
		this.$router.push({ path: path })
	},
	tip: function(msg, type, stay) {
	  stay = parseInt(stay) || 3
	  this.$message({
	    message: msg,
	    type: type || 'success',
	    duration: stay * 1000
	  })
	},
	confirm: function(msg, okHandler, cancelHandler) {
	  const that = this
	  this.$confirm(msg, '提示', {
	    confirmButtonText: '确定',
	    cancelButtonText: '取消',
	    type: 'warning',
	    beforeClose: (action, instance, done) => {
	      if (action === 'confirm') {
	        okHandler.call(that, done)
	      } else if (action === 'cancel') {
	        if (cancelHandler) {
	          cancelHandler.call(that, done)
	        } else {
	          done()
	        }
	      } else {
	        done()
	      }
	    }
	  }).catch(function() {})
	},
	/**
	 * 重置表单
	 * @param formName 表单元素的ref
	 */
	resetForm(formName) {
	  const frm = this.$refs[formName]
	  frm && frm.resetFields()
	},
	downloadText(filename, text) {
	  const element = document.createElement('a')
	  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text))
	  element.setAttribute('download', filename)
	
	  element.style.display = 'none'
	  document.body.appendChild(element)
	
	  element.click()
	
	  document.body.removeChild(element);
	},
  }
}
</script>
