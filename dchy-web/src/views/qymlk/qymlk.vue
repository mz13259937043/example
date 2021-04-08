<template>
  <div class="index">
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>入驻机构</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="qymlk-content">
      <el-form :model="qyxxForm" class="demo-form-inline">
        <el-row type="flex">
          <el-form-item>
            <el-input
              v-model="page.str"
              size="large"
              placeholder="请输入企业名称、资质等级、“多测合一”业务范围"
            ></el-input>
          </el-form-item>

          <!-- <el-col :span="8" style="margin-left:20px">
            <el-form-item label="资质等级" label-width="70px">
              <el-select v-model="qyxxForm.zzdj" clearable placeholder="请选择资质等级"> -->
          <!-- <el-option label="请选择" value=""></el-option> -->
          <!-- <el-option label="甲级" value="甲级"></el-option>
                <el-option label="乙级" value="乙级"></el-option>
                <el-option label="丙级" value="丙级"></el-option>
                <el-option label="丁级" value="丁级"></el-option>
              </el-select>
            </el-form-item>
          </el-col> -->

          <el-form-item>
            <el-button @click="onSearch" class="btn" style="border-radius: 0"
              >搜索</el-button
            >
          </el-form-item>
        </el-row>
      </el-form>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="qymlkData"
        border
        style="width: 100%; cursor: pointer"
        :row-style="{ height: '32px', color: '#000' }"
        @row-click="openDetail"
        :header-cell-style="{
          fontWeight: 'bold',
          fontSize: '15px',
          background: '#0091ff',
        }"
      >
        <el-table-column prop="unit_NAME" label="企业名称" align="center">
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.unit_NAME)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="zzdj"
          width="110"
          label="测绘资质等级"
          align="center"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.zzdj)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="fbsj"
          width="100"
          label="入驻时间"
          align="center"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.fbsj)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="dchyywfw"
          label="“多测合一”业务范围"
          align="center"
          width="180"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.dchyywfw)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="unit_TYPE"
          width="100"
          label="企业性质"
          align="center"
        >
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.unit_TYPE)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column prop="bjl" width="70" label="办件量" align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.bjl)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="legal_NAME"
          width="70"
          label="联系人"
          align="center"
        >
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.legal_NAME)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="link_PHONE"
          width="120"
          label="联系电话"
          align="center"
        >
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.link_PHONE)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="xydj"
          width="115"
          label="服务质量指数"
          align="center"
        >
          <template slot-scope="scope">
            <span
              size="medium"
              style="color: red"
              v-html="changeColor(scope.row.xydj)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="operate"
          label="操作"
          align="center"
          width="90"
          v-if="roleshow"
        >
          <template slot-scope="scope">
            <el-button
              class="btn-danger"
              @click="handledj(scope.row, scope.$index, $event)"
              size="small"
              v-if="scope.row.state == '正常'"
              >冻结</el-button
            >
            <el-button
              class="btn"
              @click="handlejd(scope.row, scope.$index, $event)"
              size="small"
              v-else
              >解冻</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- <el-pagination
        background
        :page-sizes="[1, 2]"
        :page-size="1"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        align="center"
      >
      </el-pagination> -->
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
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import Bottom from "@/components/Bottom.vue";
import Header from "@/components/Header.vue";

export default {
  name: "qymlk",
  components: {
    NavBar,
    Header,
    Bottom,
  },
  data() {
    return {
      qymlkData: [],
      roleshow: false,
      paginationshow: false,
      pagination: {
        current: 1,
        pageSize: 15,
        total: 100,
      },
      //  分页组件
      page: {
        total: null,
        pageNum: 1,
        pageSize: 10,
        str: "",
        state: "正常",
      },
      total: 0,
      qyxxForm: {
        qymc: "",
        zzdj: "",
      },
      loading: true,
    };
  },
  mounted() {
    this.init();
    this.getlist();
  },
  methods: {
    init() {
      if (window.localStorage.getItem("userinfo")) {
        let userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
        if (userinfo.idcard === "admin") {
          this.roleshow = true;
          this.page.state = null;
        } else {
          this.roleshow = false;
          this.page.state = "正常";
        }
      }
    },
    handledj(record, index, e) {
      e.stopPropagation();
      let params = {
        zzxxjson: record,
      };
      this.$confirm("确定冻结该企业的“多测合一”业务办理资质?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // http://222.90.207.221/dchy/dchy/zzxx/djzzxx冻结
          this.$http.post("zzxx/djzzxx", params).then((res) => {
            this.$message.success(res.data.msg);
            this.getlist();
          });
        })
        .catch(() => {});
    },
    handlejd(record, index, e) {
      e.stopPropagation();
      let params = {
        zzxxjson: record,
      };
      // http://222.90.207.221/dchy/dchy/zzxx/jdzzxx解冻
      this.$http.post("zzxx/jdzzxx", params).then((res) => {
        this.$message.success(res.data.msg);
        this.getlist();
      });
    },
    getlist() {
      //获取所有企业信息目录
      let params = {
        pageSize: this.page.pageSize,
        pageNum: this.page.pageNum,
        state: this.page.state,
        qymc: this.qyxxForm.qymc,
        zzdj: this.qyxxForm.zzdj,
      };
      this.$http.post("zzxx/selectAllZzxx", params).then((res) => {
        if (res.data.listSize > 9) {
          this.paginationshow = true;
        } else {
          this.paginationshow = false;
        }
        this.page.total = res.data.listSize;
        _.each(res.data.data, (v) => {
          v.fbsj = this.$moment(v.cjsj).format("YYYY-MM-DD");
          //服务质量等级下个版本增加，默认五星
          v.xydj = res.data.data.xydj ? res.data.data.xydj : "★★★★★(5.0)";
          // this.qymlkData.push(v);
        });
        this.qymlkData = res.data.data;
        this.loading = false;
      });
    },
    // 双击行跳转至入驻机构详情页
    openDetail(rowData) {
      this.$router.push({
        name: "qymlkdetail",
        query: { did: rowData.did },
      });
    },
    onSearch() {
      // var qymc = this.qyxxForm.qymc;
      // var zzdj = this.qyxxForm.zzdj;
      let params = {
        pageSize: this.page.pageSize,
        pageNum: this.page.pageNum,
        // qymc: qymc,
        // zzdj: zzdj,
        str: this.page.str,
      };

      //根据搜索条件获取所有企业信息目录
      this.$http.post("zzxx/findZzxx", params).then((res) => {
        if (res.data.zzxxdate.length > 0) {
          // _.each(res.data.zzxxdate, (v) => {
          //   v.fbsj = this.$moment(v.cjsj).format("YYYY-MM-DD");
          // });
          this.page.total = res.data.listSize;
          _.each(res.data.zzxxdate, (v) => {
            v.fbsj = this.$moment(v.cjsj).format("YYYY-MM-DD");
            //服务质量等级下个版本增加，默认五星
            v.xydj = res.data.zzxxdate.xydj
              ? res.data.zzxxdate.xydj
              : "★★★★★(5.0)";
            // this.qymlkData.push(v);
          });
          this.qymlkData = res.data.zzxxdate;
        } else if (res.data.zzxxdate.length === 0) {
          this.qymlkData = [];
        }
      });
    },
    //关键字标色
    changeColor(val) {
      let tags = this.page.str;
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
  },
};
</script>

<style scoped>
.qymlk-content {
  width: 100%;
  /* height: calc(100vh - 452px); */
  overflow: auto;
  padding: 0 20.05% 10px 20.05%;
  height: auto;
  min-height: 572px;
}
.qymlk-content >>> .el-input__inner {
  border-radius: 0;
}
.qymlk-content >>> .el-table .cell {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}
.pagination {
  margin-top: 20px;
  text-align: center;
}
input.el-input__inner {
  width: 330px !important;
}
button.el-button.el-button--primary {
  margin-left: -60px;
}
.demo-form-inline >>> .el-input__inner {
  width: 400px;
}
</style>