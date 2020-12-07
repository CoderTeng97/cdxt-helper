<template>
  <div class="app-container code-gen">
    <el-form ref="genForm" class="gen-form" :model="clientParam" size="mini" label-width="150px">
      <el-form-item label="选择数据源" prop="datasourceConfigId" :rules="{required: true, message: '请选择数据源'}">
        <el-select
          v-model="clientParam.datasourceConfigId"
          placeholder="选择数据源"
          @change="onDataSourceChange"
        >
          <el-option
            v-for="item in datasourceConfigList"
            :key="item.id"
            :label="`${item.dbName}（${item.host}）`"
            :value="item.id"
          >
            <span style="float: left">{{ `${item.dbName}（${item.host}）` }} </span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              <el-tooltip placement="top" content="Duplicate">
                <el-link type="primary" icon="el-icon-document-copy" style="margin-right: 20px;" @click.stop="onDataSourceDuplicate(item)"></el-link>
              </el-tooltip>
              <el-link type="primary" icon="el-icon-edit" style="margin-right: 20px;" @click.stop="onDataSourceUpdate(item)"></el-link>
              <el-link type="danger" icon="el-icon-delete" @click.stop="onDataSourceDelete(item)"></el-link>
            </span>
          </el-option>
        </el-select>
        <el-button type="text" @click="onDataSourceAdd">新建数据源</el-button>
      </el-form-item>
      <el-form-item v-show="showTable" label="包名（package）">
        <el-input v-model="clientParam.packageName" placeholder="可选，如：com.gitee.xxx" show-word-limit maxlength="100" />
      </el-form-item>
    </el-form>
    <el-row v-show="showTable" :gutter="20">
      <el-col :span="12">
        <h4>选择表</h4>
        <el-input
          v-model="tableSearch"
          prefix-icon="el-icon-search"
          clearable
          size="mini"
          placeholder="过滤表"
          style="margin-bottom: 10px;width: 100%;"
        />
        <el-table
          :data="tableListData"
          border
          :cell-style="cellStyleSmall()"
          :header-cell-style="headCellStyleSmall()"
          :row-class-name="tableRowClassName"
          @selection-change="onTableListSelect"
        >
          <el-table-column
            type="selection"
          />
          <el-table-column
            prop="tableName"
            label="表名"
          />
        </el-table>
      </el-col>
      <el-col :span="12">
        <h4>选择模板</h4>
        <el-table
          :data="templateListData"
          border
          :cell-style="cellStyleSmall()"
          :header-cell-style="headCellStyleSmall()"
          @selection-change="onTemplateListSelect"
        >
          <el-table-column
            type="selection"
          />
          <el-table-column
            prop="name"
            label="模板名称"
          />
        </el-table>
        <el-button v-show="showTable" type="primary" @click="onGenerate">生成代码</el-button>
      </el-col>
    </el-row>

    <el-dialog
      :title="datasourceTitle"
      :visible.sync="datasourceDlgShow"
    >
      <el-form
        ref="datasourceForm"
        :model="datasourceFormData"
        :rules="datasourceRule"
        size="mini"
        label-width="120px"
      >
        <el-form-item label="数据库类型">
          <el-select
            v-model="datasourceFormData.dbType"
            filterable
            default-first-option
          >
            <el-option
              v-for="item in dbTypeConfig"
              :key="item.dbType"
              :label="item.label"
              :value="item.dbType"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="Host" prop="host">
          <el-input v-model="datasourceFormData.host" placeholder="地址" show-word-limit maxlength="100" />
        </el-form-item>
        <el-form-item label="Port" prop="port">
          <el-input v-model="datasourceFormData.port" placeholder="端口" show-word-limit maxlength="10" />
        </el-form-item>
        <el-form-item label="Database" prop="dbName">
          <el-input v-model="datasourceFormData.dbName" placeholder="数据库" show-word-limit maxlength="64" />
        </el-form-item>
        <el-form-item label="Username" prop="username">
          <el-input v-model="datasourceFormData.username" placeholder="用户名" show-word-limit maxlength="100" />
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="datasourceFormData.password" type="password" placeholder="密码" show-word-limit maxlength="100" />
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="onDatasourceTest">测试连接</el-button>
          <el-button type="primary" @click="onDatasourceSave">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>
<style lang="scss">
  .code-gen {
    margin: 0 auto;
    width: 70%;
      .el-input { width: 450px;}
      .el-row h4 {
        text-align: center;
      }
      .el-row .el-button {
        margin-top: 20px;
      }
  }
  .el-table .hidden-row {
    display: none;
  }
</style>
<script>
import {
  getLoadDataSource,
  getLoadTemplate,
  getLoadDbType,
  postDataSourceChange,
  postDataSourceDelete,
  postDatasourceTest,
  datasourceUpdate,
  datasourceAdd
} from "../../../api/codeGen.js";
export default {
  data() {
    return {
      showTable: false,
      clientParam: {
        datasourceConfigId: '',
        tableNames: [],
        templateConfigIdList: [],
        packageName: ''
      },
      tableSearch: '',
      datasourceConfigList: [],
      tableListData: [],
      templateListData: [],
      // add datasource
      datasourceTitle: '新建连接',
      datasourceDlgShow: false,
      datasourceFormData: {
        id: 0,
        dbType: 1,
        host: '',
        port: '',
        username: '',
        password: '',
        dbName: ''
      },
      dbTypeConfig: [],
      datasourceRule: {
        host: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        port: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        dbName: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.loadDataSource()
    this.loadTemplate()
    this.loadDbType()
  },
  methods: {
    tableRowClassName: function ({row, index}) {
      row.hidden = false
      if (this.tableSearch.length === 0) {
        return ''
      }
      if (!(row.tableName && row.tableName.indexOf(this.tableSearch) > -1)) {
        row.hidden = true
        return 'hidden-row';
      }
      return ''
    },
    loadDataSource() {
      let res = getLoadDataSource({}).then(res =>{
			this.datasourceConfigList = res;
		});
	
    },
    loadTemplate() {
		let res = getLoadTemplate({}).then(res =>{
			this.templateListData = res;
		});
    },
    loadDbType() {
		let res = getLoadDbType({}).then(res =>{
			this.dbTypeConfig = res;
		});
    },
    onDataSourceAdd() {
      this.datasourceTitle = '新建连接'
      this.datasourceFormData.id = 0
      this.datasourceDlgShow = true
    },
    onTableListSelect(selectedRows) {
      this.clientParam.tableNames = selectedRows
        .filter(row => row.hidden === undefined || row.hidden === false)
        .map(row => row.tableName)
    },
    onTemplateListSelect(selectedRows) {
      this.clientParam.templateConfigIdList = selectedRows.map(row => row.id)
    },
    onDataSourceChange(datasourceConfigId) {
		this.clientParam.datasourceConfigId = datasourceConfigId
	  	let res = postDataSourceChange(datasourceConfigId,{}).then(res =>{
			this.showTable = true;
			this.tableListData = res;
		});
		
    },
    onDataSourceUpdate(item) {
      this.datasourceTitle = '修改连接'
      Object.assign(this.datasourceFormData, item)
      this.datasourceDlgShow = true
    },
    onDataSourceDuplicate(item) {
      this.datasourceTitle = `${item.host} Copy`
      Object.assign(this.datasourceFormData, item)
      this.datasourceFormData.id = 0
      this.datasourceDlgShow = true
    },
    onDataSourceDelete(row) {
      this.confirm(`确认要删除 ${row.dbName} 吗？`, function(done) {
        const data = {
          id: row.id
        }
		let res = postDataSourceDelete(data).then(res =>{
			done()
			location.reload()
		});
      })
    },
    onGenerate() {
      this.$refs.genForm.validate((valid) => {
        if (valid) {
          if (this.clientParam.tableNames.length === 0) {
            this.tip('请勾选表', 'error')
            return
          }
          if (this.clientParam.templateConfigIdList.length === 0) {
            this.tip('请勾选模板', 'error')
            return
          }
          const config = JSON.stringify(this.clientParam)
          this.goRoute(`result/${config}`)
        }
      })
    },
    onDatasourceTest() {
		let self = this;
      this.$refs.datasourceForm.validate((valid) => {
        if (valid) {
			let res = postDatasourceTest(this.datasourceFormData).then(res =>{
				self.tip('连接成功')
			});
			
        }
      })
    },
    onDatasourceSave() {
      this.$refs.datasourceForm.validate((valid) => {
        if (valid) {
			let res = postDatasourceTest(this.datasourceFormData).then(res =>{
				if (this.datasourceFormData.id) {
					let res = datasourceUpdate(this.datasourceFormData).then(res =>{
						location.reload()
					});
				} else {
					let res = datasourceAdd(this.datasourceFormData).then(res =>{
						this.tip('添加成功')
						this.loadDataSource()
					});
						this.datasourceDlgShow = false
					
				}
			});
			
        }
      })
    },
	cellStyleSmall: function() {
	    return { padding: '5px 0' }
	},
	 headCellStyleSmall: function() {
	    return { padding: '5px 0' }
	},
	goRoute: function(path) {
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
  },
}
</script>

