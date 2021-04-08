<template>
  <div class="resources-manage" style="height:100%">
    <el-card>
      <el-button @click="createmod" type="primary" plain>创建模块资源</el-button>
      <el-dialog :visible.sync="res.dialogVisible" :title="res.dialogType === 'edit' ? '编辑资源' : '新建资源'">
        <el-form :model="res.form" label-width="80px" label-position="left">
          <el-form-item label="资源名称">
            <el-input v-model="res.form.resourcesName" placeholder="请输入资源名称" />
          </el-form-item>
          <el-form-item label="资源别名">
            <el-input v-model="res.form.resourcesAlias" placeholder="请输入资源别名" />
          </el-form-item>
          <el-form-item label="资源key">
            <el-input v-model="res.form.resourcesKey" placeholder="请输入资源key" />
          </el-form-item>
          <el-form-item label="图标">
            <el-input v-model="res.form.icon" placeholder="请输入图标名称" />
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="res.form.sort" placeholder="请输入下标" />
          </el-form-item>
          <el-form-item label="资源类型">
            <el-select v-model="res.form.resourcesType" placeholder="请选择资源类型">
              <el-option label="按钮资源" value="按钮资源"></el-option>
              <el-option label="链接资源" value="链接资源"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注">
            <el-input type="textarea" :rows="2" v-model="res.form.remark" placeholder="请输入备注信息" />
          </el-form-item>
        </el-form>
        <div style="text-align: right">
          <el-button type="danger" @click="res.dialogVisible = false">
            取消
          </el-button>
          <el-button type="primary" @click="confirmRes"> 提交 </el-button>
        </div>
      </el-dialog>

      <el-table :data="reslist" v-loading="loading" style="width: 100%;" max-height="750" row-key="id" :indent="40">
        <el-table-column prop="resourcesName" label="资源名称" width="300" align="center">
        </el-table-column>
        <el-table-column prop="resourcesAlias" label="资源别名" width="300" align="center">
        </el-table-column>
        <el-table-column prop="resourcesType" label="资源类型" width="200" align="center">
        </el-table-column>
        <el-table-column prop="remark" label="描述" show-overflow-tooltip align="center"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="300" align="center">
          <template slot-scope="scope">
            <el-button @click="handleCreate(scope.$index, scope.row)" type="primary" size="mini">新增</el-button>
            <el-button @click="handleEdit(scope.$index, scope.row)" type="primary" size="mini">编辑</el-button>
            <el-button @click.native.prevent="handleDelete(scope.$index, scope.row, scope,reslist)" type="primary" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
  import {  } from '@/api/resources'

  var count = 1000;
  // 这是默认的流程单独模块
  const defaultRes = {
     id:'',
     processName:'',
     processAlias:'',
     processSeqname:'',
     serialCode:'',
     pid:'',
     children:[],
  };
  export default {
    data() {
      return {
        loading: true,
        res: {
          dialogVisible: false,
          dialogType: "new",
          checkStrictly: false,
          form: Object.assign({}, defaultRes),
          currentRow: null,
        },
        reslist: [],
        formlist: [],
        deleteid: "",
      };
    },
    async created() {
      const reqdata = await getAllResourcesAndChildren('superparent');
      debugger
      this.reslist = [...reqdata.data];
      this.loading = false;
      debugger
    },
    methods: {
      async load(tree, treeNode, resolve) {
        console.log(tree, treeNode, resolve)
        let haha = await getAllResourcesAndChildren('superparent');
        resolve(haha.data)
        setTimeout(() => {

        }, 1000)
      },
      async getResources() {
        const reqdata = await getAllResourcesAndChildren('superparent');
        this.reslist = [...reqdata.data];
        this.loading = false;
      },
      async confirmRes() {
        console.log([].length)
        const isEdit = this.res.dialogType === "edit";
        let success = 0;
        if (isEdit) {
          //let req = await updateProcess(this.processForm.process)
          console.log('编辑',isEdit,"bbb", this.res.form);
          let data = await updateResources(this.res.form);
          if (data.state = 200) {
            this.res.form.id = data.id;
            this.updatawithcheckchildren(this.reslist);
            success = 1;
          }
        } else {
          console.log('xinzeng')
          let data = await createResources(this.res.form);
          if (data.state = 200) {
            this.res.form.id = data.id;
            this.createwithcheckparent(this.reslist);
            success = 1;
          }
        }
        this.res.dialogVisible = false;
        const {
          resourcesName,
          resourcesKey,
          icon,
          sort,
          resourcesType,
          resourcesAlias,
          remark,
        } = this.res.form;
        if (success == 1) {
          this.getResources();
          this.$notify({
            title: "提交成功",
            dangerouslyUseHTMLString: true,
            message: `
                <div>资源名称: ${resourcesName}</div>
                <div>资源别名: ${resourcesAlias}</div>
                <div>备注: ${remark}</div>
              `,
            type: "success",
          });
        } else {
          this.$notify({
            title: "提交失败",
            dangerouslyUseHTMLString: true,
            message: `
                 <div>资源名称: ${resourcesName}</div>
                <div>资源别名: ${resourcesAlias}</div>
                <div>备注: ${remark}</div>
              `,
            type: "warning",
          });
        }
      },
      createmod() {
        this.res.form = Object.assign({}, defaultRes);
        this.res.dialogType = "new";
        this.res.dialogVisible = true;
        this.res.form.parentid = "superparent";
      },
      handleCreate(index, row) {
        this.res.form = Object.assign({}, defaultRes);
        this.res.dialogType = "new";
        this.res.dialogVisible = true;
        this.res.form.parentid = row.id;
      },
      handleEdit(index, row) {
        this.res.dialogType = "edit";
        this.res.dialogVisible = true;
        this.res.checkStrictly = true;
        this.res.form = deepClone(row);
      },
      handleDelete(index, row, scope, datalist) {
        this.$confirm("是否删除该流程?", "提示", {
          confirmButtonText: "确认",
          cancelButtonText: "取消",
          type: "warning",
        })
                .then(async () => {
                  let success = await deleteResources(row.id)

                  console.log(index, row, scope, datalist,success);

                  if (success.count>0) {
                    this.getResources();
                    datalist.splice(index, 1);
                    this.deleteid = row.id;
                    this.deletewithcheckchildren(this.reslist);
                    this.formlist.splice(index, 1);
                    this.$message({
                      type: "success",
                      message: "删除成功!",
                    });
                  } else {
                    this.$message({
                      type: "warning",
                      message: "删除失败!",
                    });
                  }
                })
                .catch((err) => {
                  console.error(err);
                });
      },
      createwithcheckparent(children) {
        console.log("createwithcheckparent");
        if (this.res.form.parentid === "superparent") {
          this.reslist.push(this.res.form);
          return;
        }
        debugger
        for (let index = 0; index < children.length; index++) {
          if (children[index].id === this.res.form.parentid) {
            children[index].children.push(this.res.form);
            break;
          }
          if (children[index].children&&children[index].children.length > 0) {
            this.createwithcheckparent(children[index].children)
          }
        }
      },
      deletewithcheckchildren(children) {
        debugger;
        console.log(1,children)
        for (let index = 0; index < children.length; index++) {
          if (children[index].id === this.deleteid) {
            children.splice(index, 1);
            break;
          }

          if (children[index].children&&children[index].children.length > 0) {
            console.log(2,children[index].children)
            this.deletewithcheckchildren(children[index].children)
          }
        }
      },
      updatawithcheckchildren(children) {
        debugger;
        for (let index = 0; index < children.length; index++) {
          if (children[index].id === this.res.form.id) {
            children.splice(index, 1, Object.assign({}, this.res.form));
            return;
          }
          if (children[index].children&&children[index].children.length > 0) {
            this.updatawithcheckchildren(children[index].children)
          }
        }
      }
    },
  };

  function deepClone(source) {
    if (!source && typeof source !== "object") {
      throw new Error("error arguments", "deepClone");
    }
    const targetObj = source.constructor === Array ? [] : {};
    Object.keys(source).forEach((keys) => {
      if (source[keys] && typeof source[keys] === "object") {
        targetObj[keys] = deepClone(source[keys]);
      } else {
        targetObj[keys] = source[keys];
      }
    });
    return targetObj;
  }
</script>

<style lang="less" scoped>
  .resources-manage {
    height: calc(100%);
  }

  .icon-file {
    padding-right: 7px;
    padding-left: 18px;
  }

  .icon-folder,
  .icon-folder-open {
    padding-left: 7px;
    padding-right: 7px;
  }
</style>
