<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>人员管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="rygl-content">
      <el-form :inline="true" :model="ywxxForm">
        <div style="margin-top: 15px">
          <el-input
            placeholder="请输入内容"
            v-model="ywxxForm.input"
            class="input"
            style="margin-bottom: -5px"
          ></el-input>
          <el-button @click="getjslist" class="btn" style="border-radius: 0"
            >搜索</el-button
          >
          <el-button
            @click="getAllDel"
            class="btn"
            style="border-radius: 0; float: right"
            >批量删除</el-button
          >
          <el-button
            @click="getAdd"
            class="btn"
            style="border-radius: 0; float: right"
            >添加</el-button
          >
        </div>
      </el-form>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="jsglData"
        border
        tooltip-effect="dark"
        style="width: 100%; cursor: pointer; margin-bottom: 0px"
        :row-style="{ height: '5px' }"
        @row-click="openDetail"
        :header-cell-style="{ background: '#0091ff', color: '#fff' }"
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          type="selection"
          width="55"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="role"
          show-overflow-tooltip
          label="名称"
          align="center"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.role)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="type"
          show-overflow-tooltip
          label="角色类型"
          align="center"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.type)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="count"
          show-overflow-tooltip
          label="备注"
          align="center"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.count)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="operate" label="操作" align="center" width="95">
          <template slot-scope="scope">
            <el-button
              class="btn-danger"
              @click="getDel(scope.row, scope.$index, $event)"
              size="small"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 抽屉 -->
      <!-- 上面表单 -->
      <el-drawer
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose"
      >
        <div>
          <div
            class="global-sxtitle"
            style="margin-left: 28px; width: 520px; margin-bottom: 0"
          >
            角色详情
            <el-button
              @click="handeSave()"
              style="float: right; margin: 6px 10px 20px 0"
              class="btn"
              size="mini"
              >保存</el-button
            >
          </div>

          <el-form
            ref="ryForm"
            :rules="rules"
            :model="ryForm"
            autocomplete="off"
            class="xxxform"
            style="clear: both"
          >
            <el-row>
              <el-col :span="24">
                <el-form-item label="名称" prop="role" label-width="100px">
                  <el-input
                    v-model="ryForm.role"
                    placeholder="请输入名称"
                    style="padding-bottom: 0"
                  />
                </el-form-item>
              </el-col>
              <!-- <el-col :span="24">
                <el-form-item label="别名" prop="bm" label-width="100px">
                  <el-input v-model="ryForm.bm" placeholder="请输入别名" />
                </el-form-item>
              </el-col> -->
              <el-col :span="24">
                <el-form-item label="类型" prop="type" label-width="100px">
                  <el-select
                    v-model="ryForm.type"
                    clearable
                    placeholder="请选择类型"
                  >
                    <el-option label="审批角色" value="审批角色"></el-option>
                    <el-option label="管理角色" value="管理角色"></el-option>
                    <el-option label="监管角色" value="监管角色"></el-option>
                    <!-- <el-option label="甲级" value="甲级"></el-option>
                    <el-option label="乙级" value="乙级"></el-option>
                    <el-option label="丙级" value="丙级"></el-option>
                    <el-option label="丁级" value="丁级"></el-option> -->
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="备注" prop="count" label-width="100px">
                  <el-input type="textarea" :rows="3" v-model="ryForm.count" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <!-- 下面table -->
        <div>
          <div
            class="global-sxtitle"
            style="margin-left: 28px; width: 520px; margin-bottom: 0"
          >
            用户
            <el-button
              class="btn"
              size="mini"
              @click="handeryAdd()"
              style="float: right; margin: 6px 10px 20px 0"
              >添加</el-button
            >
          </div>

          <el-table
            :data="ryglData"
            border
            style="margin-left: 28px; width: 520px; cursor: pointer"
            :row-style="{ height: '20px' }"
            max-height="440"
            :header-cell-style="{ background: '#0091ff', color: '#fff' }"
          >
            <el-table-column type="index" label="" align="center" width="50">
            </el-table-column>
            <el-table-column
              prop="name"
              show-overflow-tooltip
              label="姓名"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="ssbm"
              show-overflow-tooltip
              label="所属部门"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="operate"
              label="操作"
              align="center"
              width="95"
            >
              <template slot-scope="scope">
                <el-button
                  style="float: right; margin: 2px 10px 2px 0"
                  class="btn-danger"
                  size="mini"
                  @click="getryDel(scope.row, scope.$index, $event)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页
            <el-pagination
                @current-change="handleryCurr"
                @size-change="handlerySize"
                :current-page="page.pageNum"
                :page-size="page.pageSize"
                layout="total,sizes, prev, pager, next, jumper"
                :total="page.total"
                :page-sizes="[10,20]"
                style="margin-bottom:20px;text-align:center"
            ></el-pagination> -->
          <!-- 分页 -->
          <el-pagination
            background
            v-if="paginationryshow"
            @current-change="handleryCurr"
            @size-change="handlerySize"
            :current-page="rypage.pageNum"
            :page-size="rypage.pageSize"
            layout="total, prev, pager, next, jumper"
            :total="rypage.total"
            :page-sizes="[10, 20]"
            style="margin-bottom: 20px; text-align: center"
          ></el-pagination>
          <!-- 人员添加弹出框 -->
          <el-dialog
            title="添加用户"
            :visible.sync="ryvisible"
            width="30%"
            :append-to-body="true"
          >
            <el-form :inline="true" :model="ywxxForm">
              <div style="margin-top: 15px">
                <el-input
                  placeholder="请输入内容"
                  v-model="ywxxForm.ryinput"
                  class="input"
                ></el-input>
                <el-button
                  @click="OnSearch"
                  class="btn"
                  style="border-radius: 0"
                  >搜索</el-button
                >
              </div>
            </el-form>
            <el-table
              :data="rytjData"
              border
              max-height="500"
              tooltip-effect="dark"
              style="width: 100%; cursor: pointer"
              :row-style="{ height: '40px' }"
              :header-cell-style="{ background: '#0091ff' }"
              @selection-change="handleSelectionryChange"
            >
              <el-table-column
                type="selection"
                width="55"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="name"
                show-overflow-tooltip
                label="名称"
                align="center"
              >
                <template slot-scope="scope">
                  <span
                    size="medium"
                    v-html="changeColor(scope.row.name)"
                  ></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="ssbm"
                show-overflow-tooltip
                label="所属部门"
                align="center"
              >
                <template slot-scope="scope">
                  <span
                    size="medium"
                    v-html="changeColor(scope.row.ssbm)"
                  ></span>
                </template>
              </el-table-column>
            </el-table>
            <div slot="footer" class="dialog-footer">
              <el-button type="info" @click="ryvisible = false"
                >取 消</el-button
              >
              <el-button type="primary" @click="handelrytj()">确 定</el-button>
            </div>
          </el-dialog>
        </div>
      </el-drawer>
      <!-- 分页 -->
      <el-pagination
        background
        v-if="paginationshow"
        @current-change="handleCurr"
        @size-change="handleSize"
        :current-page="page.pageNum"
        :page-size="page.pageSize"
        layout="total,sizes, prev, pager, next, jumper"
        :total="page.total"
        :page-sizes="[10, 20]"
        style="margin-bottom: 20px; text-align: center"
      ></el-pagination>
      <!-- 弹出框 -->
      <el-dialog title="添加角色" :visible.sync="visible" width="30%">
        <el-form
          ref="sqxxForm"
          :rules="rules"
          :model="sqxxForm"
          autocomplete="off"
          class="xxxform"
        >
          <el-row>
            <el-col :span="24">
              <el-form-item label="名称" prop="role" label-width="100px">
                <el-input
                  v-model="sqxxForm.role"
                  placeholder="请输入名称"
                  style="padding-bottom: 0"
                />
              </el-form-item>
            </el-col>
            <!-- <el-col :span="24">
              <el-form-item label="别名" prop="bm" label-width="100px">
                <el-input v-model="sqxxForm.bm" placeholder="请输入别名" />
              </el-form-item>
            </el-col> -->
            <el-col :span="24">
              <el-form-item label="类型" prop="type" label-width="100px">
                <el-select
                  v-model="sqxxForm.type"
                  clearable
                  placeholder="请选择类型"
                >
                  <el-option label="审批角色" value="审批角色"></el-option>
                  <el-option label="管理角色" value="管理角色"></el-option>
                  <el-option label="监管角色" value="监管角色"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item label="备注" prop="count" label-width="100px">
                <el-input type="textarea" :rows="3" v-model="sqxxForm.count" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="visible = false">取 消</el-button>
          <el-button type="primary" @click="handelOk()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import Bottom from "@/components/Bottom.vue";
import Header from "@/components/Header.vue";

export default {
  name: "rygl",
  components: {
    NavBar,
    Header,
    Bottom,
  },
  data() {
    return {
      total: 0,
      paginationshow: false,
      paginationryshow: false,
      //  分页组件
      page: {
        total: null,
        pageNum: 1,
        pageSize: 10,
        str: "",
      },
      //  分页组件
      rypage: {
        total: null,
        pageNum: 1,
        pageSize: 10,
        str: "",
      },
      ywxxForm: {
        input: "",
        ryinput: "",
      },
      jsglData: [],
      ryglData: [],
      rytjData: [],
      multipleSelection: [],
      rylist: [],
      ryid: "",
      drawer: false,
      direction: "rtl",
      visible: false,
      ryvisible: false,
      sqxxForm: {
        role: "",
        bm: "",
        type: "",
        count: undefined,
      },
      ryForm: {
        role: "",
        bm: "",
        type: "",
        count: "",
        type: undefined,
      },
      rules: {
        role: [{ required: true, message: "请输入名称", trigger: "blur" }],
      },
      loading: true,
    };
  },
  mounted() {
    //获取角色列表
    this.getlist();
  },

  methods: {
    // 获取所有角色信息
    getlist() {
      let params = {
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
      };
      this.$http.post("/roleuser/selectRole", params).then((res) => {
        if (res.data.listSize > 9) {
          this.paginationshow = true;
        } else {
          this.paginationshow = false;
        }
        this.page.total = res.data.listSize;
        this.jsglData = res.data.rolelist;
        this.loading = false;
      });
    },
    // 条件查询
    getjslist() {
      let params = {
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
        str: this.ywxxForm.input,
      };
      // http://222.90.207.221/dchy/dchy/roleuser/findRole
      this.$http.post("roleuser/findRole", params).then((res) => {
        if (res.data.listSize > 9) {
          this.paginationshow = true;
        } else {
          this.paginationshow = false;
        }
        this.page.total = res.data.listSize;
        this.jsglData = res.data.rolelist;
      });
    },
    // 双击行跳转至角色详情页
    openDetail(rowData) {
      this.ryForm = { ...rowData };
      this.drawer = true;
      this.ryid = rowData.id;
      this.getrylist(rowData.id);
    },
    //关键字标色
    changeColor(val) {
      let tags = this.ywxxForm.input;
      if (
        tags !== null &&
        tags !== "" &&
        tags !== undefined &&
        val !== null &&
        val !== "" &&
        val !== undefined
      ) {
        let reg = new RegExp("(" + tags + ")", "g");
        return val.replace(reg, "<font style='color:red'>$1</font>");
      } else {
        return val;
      }
    },
    // 选中操作
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 添加
    getAdd() {
      this.visible = true;
      this.sqxxForm = {
        role: "",
        bm: "",
        type: "",
        count: undefined,
      };
    },

    // 删除
    getDel(val, index, e) {
      e.stopPropagation();
      let params = [];
      params.push(val);
      this.$confirm("此操作将永久删除该角色, 是否继续??", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$http.post("roleuser/deleteRole", params).then((res) => {
            this.$message.success(res.data.msg);
            this.getlist();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    // 批量删除
    getAllDel() {
      // http://222.90.207.221/dchy/dchy/roleuser/deleteRole
      if (this.multipleSelection.length) {
        let params = [];
        _.each(this.multipleSelection, function (v) {
          params.push(v);
        });
        this.$confirm("此操作将永久删除选中角色, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$http.post("roleuser/deleteRole", params).then((res) => {
              this.$message.success(res.data.msg);
              this.getlist();
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.$message.info("请选择数据");
      }
    },
    // 添加
    handelOk() {
      // http://222.90.207.221/dchy/dchy/roleuser/addRole
      this.$refs["sqxxForm"].validate((valid) => {
        if (valid) {
          let params = [];
          params.push(this.sqxxForm);
          this.$http.post("roleuser/addRole", params).then((res) => {
            this.$message.success(res.data.msg);
            this.getlist();
            this.visible = false;
          });
        }
      });
    },
    // 抽屉
    handleClose(done) {
      this.drawer = false;
    },
    // 保存
    handeSave() {
      this.$refs["ryForm"].validate((valid) => {
        if (valid) {
          let params = [];
          params.push(this.ryForm);
          // http://222.90.207.221/dchy/dchy/roleuser/updateRole
          this.$http.post("roleuser/updateRole", params).then((res) => {
            this.$message.success(res.data.msg);
            this.getlist();
            this.visible = false;
          });
        }
      });
    },
    // 获取人员list
    getrylist(id) {
      let params = {
        pageNum: this.rypage.pageNum,
        pageSize: this.rypage.pageSize,
        roleid: id ? id : this.ryid,
      };
      this.$http.post("/roleuser/selectByRole", params).then((res) => {
        if (res.data.listSize > 9) {
          this.paginationryshow = true;
        } else {
          this.paginationryshow = false;
        }
        this.rypage.total = res.data.listSize;
        this.ryglData = res.data.roleuserlist;
        _.each(this.ryglData, function (v) {
          v.name = v.personal.name;
          v.ssbm = v.personal.ssbm;
        });
      });
    },
    // 人员新增
    handeryAdd() {
      this.getryAlllist();
      this.ryvisible = true;
      this.ywxxForm.ryinput = "";
    },
    // 人员获取
    getryAlllist() {
      // http://222.90.207.221/dchy/dchy/roleuser/selectAllUser
      this.$http.post("roleuser/selectAllUser").then((res) => {
        this.rytjData = res.data.userdata;
      });
    },
    // 人员条件查询
    OnSearch() {
      // http://222.90.207.221/dchy/dchy/personal/selectUser
      let params = {
        str: this.ywxxForm.ryinput,
      };
      this.$http.post("personal/selectUser", params).then((res) => {
        this.rytjData = res.data.data;
      });
    },
    // 人员删除
    getryDel(val, index, e) {
      let arr = [];
      arr.push(val.id);
      // http://222.90.207.221/dchy/dchy/roleuser/deleteUser

      this.$confirm("是否从角色中移除该人员?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$http.post("roleuser/deleteUser", arr).then((res) => {
            this.$message.success(res.data.msg);
            this.getrylist();
          });
          // this.$message({
          //   type: 'success',
          //   message: '删除成功!'
          // });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 获取选中的人员
    handleSelectionryChange(val) {
      const _this = this;
      _this.rylist = val;
      _.each(_this.rylist, function (v) {
        v.roleidlist = [_this.ryid];
      });
    },
    // 人员添加
    handelrytj() {
      let params = {
        personallistjson: this.rylist,
      };
      // http://222.90.207.221/dchy/dchy/personal/addUser
      this.$http.post("personal/addUser", params).then((res) => {
        if (res) {
          this.ryvisible = false;
        }
        this.getrylist();
      });
    },
    // 分页
    //监听页码值的变化
    handleCurr(page) {
      this.loading = true;
      this.page.pageNum = page;
      this.getlist();
    },
    // 页数的改变
    handleSize(val) {
      this.loading = true;
      this.page.pageSize = val;
      this.page.pageNum = 1;
      this.getlist();
    },
    //监听页码值的变化
    handleryCurr(page) {
      this.rypage.pageNum = page;
      this.getrylist();
    },
    // 页数的改变
    handlerySize(val) {
      this.rypage.pageSize = val;
      this.rypage.pageNum = 1;
      this.getrylist();
    },
  },
};
</script>

<style scoped>
.rygl-content {
  width: 100%;
  /*height: calc(100vh - 438px);*/
  min-height: 572px;
  overflow: auto;
  padding: 0 20.05% 0px 20.05%;
  height: auto;
}
.rygl-content >>> .el-input__inner {
  border-radius: 0;
}
.el-input {
  width: 400px !important;
  padding-bottom: 20px;
}
.el-select {
  width: 400px !important;
}
.el-textarea {
  width: 400px;
}
.input .el-input-group__prepend {
  background-color: #fff;
}
.pagination {
  margin-top: 20px;
  text-align: center;
}
.btn {
  color: #66b1ff;
  border: 1px solid #66b1ff;
}
.btn:hover {
  color: #fff;
  border: 1px solid #66b1ff;
  background: #006cff;
}
</style>