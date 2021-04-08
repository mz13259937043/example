<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="rygl-content">
      <div>
        <el-form :inline="true" :model="ywxxForm">
          <div style="margin: 15px 0">
            <el-input
              placeholder="请输入身份证号、手机号、姓名查询"
              v-model="ywxxForm.input"
              class="input"
            ></el-input>
            <el-button @click="searchList" class="btn" style="border-radius: 0"
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
          :data="jsglData"
          border
          tooltip-effect="dark"
          style="width: 100%; cursor: pointer; margin-bottom: 20px"
          :row-style="{ height: '40px' }"
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
            prop="name"
            show-overflow-tooltip
            label="姓名"
            align="center"
          >
            <template slot-scope="scope">
              <span size="medium" v-html="changeColor(scope.row.name)"></span>
            </template>
          </el-table-column>

          <el-table-column
            prop="idcard"
            show-overflow-tooltip
            label="身份证号"
            align="center"
          >
            <template slot-scope="scope">
              <span size="medium" v-html="changeColor(scope.row.idcard)"></span>
            </template>
          </el-table-column>

          <el-table-column
            prop="phonenum"
            show-overflow-tooltip
            label="手机"
            align="center"
          >
            <template slot-scope="scope">
              <span
                size="medium"
                v-html="changeColor(scope.row.phonenum)"
              ></span>
            </template>
          </el-table-column>

          <el-table-column
            prop="type"
            show-overflow-tooltip
            label="人员类型"
            align="center"
          >
            <template slot-scope="scope">
              <span size="medium" v-html="changeColor(scope.row.type)"></span>
            </template>
          </el-table-column>

          <el-table-column
            prop="createtime"
            show-overflow-tooltip
            label="创建时间"
            align="center"
          >
            <template slot-scope="scope">
              <span
                size="medium"
                v-html="changeColor(scope.row.createtime)"
              ></span>
            </template>
          </el-table-column>

          <el-table-column
            prop="operate"
            label="操作"
            align="center"
            width="95"
          >
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
      </div>
      <!-- 抽屉 -->
      <!-- 上面表单 -->
      <el-drawer
        class="openDrawer"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose"
      >
        <div class="content">
          <div class="top">
            <div class="global-sxtitle" style="width: 100%; margin-bottom: 0">
              用户详情
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
              :model="ryForm"
              autocomplete="off"
              style="clear: both; width: 100%; padding-right: 7%"
            >
              <el-row>
                <el-col :span="24">
                  <el-form-item label="标识" prop="id" label-width="80px">
                    <el-input
                      v-model="ryForm.id"
                      placeholder="请输入标识"
                      :disabled="true"
                    />
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="姓名" prop="name" label-width="80px">
                    <el-input v-model="ryForm.name" placeholder="请输入姓名" />
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item
                    label="身份证号"
                    prop="idcard"
                    label-width="80px"
                  >
                    <el-input
                      v-model="ryForm.idcard"
                      placeholder="请输入身份证号"
                    />
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item
                    label="手机号"
                    prop="phonenum"
                    label-width="80px"
                  >
                    <el-input
                      v-model="ryForm.phonenum"
                      placeholder="请输入手机号"
                    />
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="所属部门" prop="ssbm" label-width="80px">
                    <el-input
                      v-if="
                        ryForm.type == '注册测绘师' ||
                        ryForm.type == '高级工程师'
                      "
                      disabled
                      v-model="ryForm.ssbm"
                      placeholder="请输入所属部门"
                    />
                    <el-input
                      v-else
                      v-model="ryForm.ssbm"
                      placeholder="请输入所属部门"
                    />
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <el-form-item label="人员类型" prop="type" label-width="80px">
                    <el-select
                      v-if="
                        ryForm.type == '注册测绘师' ||
                        ryForm.type == '高级工程师'
                      "
                      disabled
                      v-model="ryForm.type"
                      placeholder="请选择"
                    >
                      <el-option label="审批人员" value="审批人员"></el-option>
                      <el-option label="管理人员" value="管理人员"></el-option>
                      <el-option label="监管人员" value="监管人员"></el-option>
                    </el-select>
                    <el-select
                      v-else
                      v-model="ryForm.type"
                      placeholder="请选择"
                    >
                      <el-option label="审批人员" value="审批人员"></el-option>
                      <el-option label="管理人员" value="管理人员"></el-option>
                      <el-option label="监管人员" value="监管人员"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <!-- 下面table -->
          <div class="table-bottom">
            <div
              class="global-sxtitle"
              style="width: 100%; margin-bottom: 15px"
            >
              角色
            </div>
            <el-table
              :data="ryglData"
              border
              max-height="320"
              style="width: 100%; cursor: pointer"
              :row-style="{ height: '40px' }"
              :header-cell-style="{ background: '#0091ff' }"
            >
              <el-table-column type="index" label="" align="center" width="50">
              </el-table-column>

              <el-table-column
                prop="role"
                show-overflow-tooltip
                label="名称"
                align="center"
              >
              </el-table-column>

              <el-table-column
                prop="count"
                show-overflow-tooltip
                label="说明"
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
                    @click="getjsDel(scope.row, scope.$index, $event)"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
              -->
            </el-table>

            <!-- 分页
      <el-pagination
        background
        v-if="paginationshow"
        @current-change="handleCurr"
        @size-change="handleSize"
        :current-page="page.pageNum"
        :page-size="page.pageSize"
        layout="total,sizes, prev, pager, next, jumper"
        :total="page.total"
        :page-sizes="[10,20]"
        style="margin-bottom:20px;text-align:center"
      ></el-pagination> -->
          </div>
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
      <el-dialog title="添加用户" :visible.sync="visible" width="30%">
        <el-form ref="addryxxForm" :model="addryxxForm" autocomplete="off">
          <el-row>
            <el-col :span="24">
              <el-form-item label="姓名" prop="name" label-width="100px">
                <el-input v-model="addryxxForm.name" placeholder="请输入姓名" />
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item label="身份证号" prop="idcard" label-width="100px">
                <el-input
                  v-model="addryxxForm.idcard"
                  placeholder="请输入身份证号"
                />
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item label="手机" prop="phonenum" label-width="100px">
                <el-input
                  v-model="addryxxForm.phonenum"
                  placeholder="请输入手机号"
                />
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item label="所属部门" prop="ssbm" label-width="100px">
                <el-input
                  v-model="addryxxForm.ssbm"
                  placeholder="请输入所属部门"
                />
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item label="人员类型" prop="type" label-width="100px">
                <el-select v-model="addryxxForm.type" placeholder="请选择">
                  <el-option label="审批人员" value="审批人员"></el-option>
                  <el-option label="管理人员" value="管理人员"></el-option>
                  <el-option label="监管人员" value="监管人员"></el-option>
                  <!-- <el-option label="注册测绘师" value="注册测绘师"></el-option>
                  <el-option label="高级工程师" value="高级工程师"></el-option>
                  <el-option
                    label="专业测绘人员"
                    value="专业测绘人员"
                  ></el-option> -->
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="info" @click="visible = false">取 消</el-button>
          <el-button type="primary" @click="handelOk">确 定</el-button>
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
      addryxxForm: {
        name: "",
        idcard: "",
        phonenum: "",
        ssbm: "",
        type: undefined,
      },
      ryForm: {
        id: "",
        name: "",
        idcard: "",
        phonenum: "",
        ssbm: "",
        type: undefined,
      },
    };
  },
  mounted() {
    //获取所有用户列表
    this.getlist();
  },
  methods: {
    // 获取所有用户信息
    getlist(url, data) {
      let params = {
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
        str: data,
      };
      this.$http
        .post(data ? url : "/personal/selectAllPersonal", params)
        .then((res) => {
          if (res.data.listSize > 10) {
            this.paginationshow = true;
          } else {
            this.paginationshow = false;
          }
          this.page.total = res.data.listSize;
          this.jsglData = res.data.data;
        });
    },

    //  搜索条件查询
    searchList() {
      let str = this.ywxxForm.input;
      this.getlist("/personal/findUser", str);
    },
    // 双击行跳转至中选公告详情页
    openDetail(rowData) {
      this.getlist();

      this.ryForm = { ...rowData };
      this.ryglData = rowData.rolelist;
      this.drawer = true;
      this.ryid = rowData.id;
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
      this.addryxxForm = {};
    },
    // 删除人员
    getDel(val, index, e) {
      this.$confirm("此操作将永久删除该人员?", "提示", {
        confirmButtonText: "继续",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          e.stopPropagation();
          let valArr = [];
          valArr.push(val);
          this.deleteRy(valArr);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    deleteRy(paramsArr) {
      let params = [];
      _.each(paramsArr, (item) => {
        params.push(item.id);
      });
      this.$http.post("personal/deletePersonal", params).then(
        (res) => {
          this.$message.success(res.data.msg);
          this.getlist();
        },
        (err) => {
          this.$message.error("删除失败！");
        }
      );
    },
    // 批量删除
    getAllDel() {
      this.$confirm("此操作将永久删除选中人员, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deleteRy(this.multipleSelection);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 添加人员
    handelOk() {
      let params = {
        personallistjson: [
          {
            ...this.addryxxForm,
            roleidlist: [],
          },
        ],
      };

      this.$http.post("personal/addUser", params).then(
        (res) => {
          this.$message.success(res.data.msg);
          this.visible = false;
          this.getlist();
        },
        (err) => {
          this.$message.error("添加失败！");
        }
      );
    },
    // 用户的角色删除
    getjsDel(val, index, e) {
      let params = {
        userid: this.ryForm.id,
        roleid: val.id,
      };
      let arr = [];
      arr.push(params);
      // http://222.90.207.221/dchy/dchy/roleuser/deleteRoleByUser

      this.$confirm("是否从用户中移除角色?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$http.post("roleuser/deleteRoleByUser", arr).then((res) => {
            this.$message.success(res.data.msg);
            for (let i = 0; i < this.ryglData.length; i++) {
              if ((val.id = this.ryglData[i].id)) {
                this.ryglData.splice(i, 1);
              }
            }
            this.getlist();
            //this.openDetail(this.ryForm)
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
    // 抽屉
    handleClose(done) {
      this.drawer = false;
    },
    // 保存
    handeSave() {
      let _this = this;
      let params = {
        personallistjson: [
          {
            ..._this.ryForm,
          },
        ],
      };

      _this.$http.post("personal/updateUser", params).then((res) => {
        _this.$message.success(res.data.msg);
        _this.drawer = false;
        _this.$nextTick(() => {
          _this.getlist();
        });
      });
    },

    // 分页
    //监听页码值的变化
    handleCurr(page) {
      this.page.pageNum = page;
      this.getlist();
    },
    // 页数的改变
    handleSize(val) {
      this.page.pageSize = val;
      this.page.pageNum = 1;
      this.getlist();
    },
    //监听页码值的变化
    handleryCurr(page) {
      this.page.pageNum = page;
      this.getlist();
    },
    // 页数的改变
    handlerySize(val) {
      this.page.pageSize = val;
      this.page.pageNum = 1;
      this.getlist();
    },
  },
};
</script>

<style scoped>
.rygl-content {
  width: 100%;
  /* height: calc(100vh - 345px); */
  min-height: 650px;
  overflow: auto;
  padding: 0 20.05% 0px 20.05%;
}
.rygl-content >>> .el-input__inner {
  border-radius: 0;
}
.input {
  width: 400px !important;
  /* padding-bottom: 20px; */
}
.el-select {
  /* width: 400px !important; */
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

.content {
  width: 100%;
  height: 100%;
  padding: 0 5%;
}

.table-bottom {
  width: 100%;
  height: auto;
}
</style>