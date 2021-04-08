<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政策法规</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="zcfg-content">
      <el-form :inline="true" :model="ywxxForm">
        <div style="margin-top: 15px">
          <el-input
            placeholder="请输入文件标题"
            v-model="ywxxForm.input"
            class="input"
          >
          </el-input>
          <el-button @click="getAllzcfg" class="btn" style="border-radius: 0"
            >搜索</el-button
          >
        </div>
      </el-form>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="zcfgData"
        style="width: 100%; cursor: pointer"
        @row-click="openDetails"
        :header-cell-style="{ background: '#0091ff' }"
      >
        <el-table-column
          prop="title"
          label=""
          show-overflow-tooltip
          style="height: 0"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.title)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="createtime"
          label=""
          align="center"
          width="200"
          style="height: 0; color: #ccc"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.createtime)"
            ></span>
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
        style="margin-bottom:20px"
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
        style="margin-bottom: 4px; text-align: center"
      ></el-pagination>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import Bottom from "@/components/Bottom.vue";
import Header from "@/components/Header.vue";
// import 'quill/dist/quill.core.css'
// import 'quill/dist/quill.snow.css'
// import 'quill/dist/quill.bubble.css'
import { formatDate } from "@/assets/js/common.js";
export default {
  name: "zcfg",
  components: {
    NavBar,
    Header,
    Bottom,
  },
  data() {
    return {
      // pagination: {
      //   current: 1,
      //   pageSize: 15,
      //   total: 100,
      // },
      paginationshow: false,
      //  分页组件
      page: {
        total: null,
        pageNum: 1,
        pageSize: 10,
        str: "",
      },
      total: 0,
      ywxxForm: {
        input: "",
      },
      zcfgData: [],
      loading: true,
    };
  },
  created() {
    this.getAllzcfg();
  },
  mounted() {},
  methods: {
    // 获取所有政策法规
    getAllzcfg() {
      var params = {
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
        type: "政策法规",
        state: "发布",
        str: this.ywxxForm.input,
      };
      this.$http.post("api/findPolicyList", params).then((res) => {
        if (res.data.data) {
          for (var value of res.data.data) {
            value.createtime = formatDate(new Date(value.createtime));
          }
        }
        if (res.data.count > 9) {
          this.paginationshow = true;
        } else {
          this.paginationshow = false;
        }
        this.zcfgData = res.data.data;
        this.page.total = res.data.count;
        this.loading = false;
      });
    },
    handleEdit(index, row) {},
    // 双击行跳转至政策法规详情页
    openDetails(row) {
      this.$router.push({
        name: "zcfgdetail",
        query: { id: row.id },
      });
    },
    // OnSearch() {
    //   let params = {
    //     pageNum: 1,
    //     pageSize: 10,
    //     type: "政策法规",
    //     state: "发布",
    //     str: this.ywxxForm.input
    //   };
    //   this.$http
    //     .post("api/findPolicyList", params)
    //     .then((res) => {
    //       this.zcfgData = [];
    //       this.zcfgData = res.data.data;
    //     });
    // },
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
    // 分页
    //监听页码值的变化
    handleCurr(page) {
      this.loading = true;
      this.page.pageNum = page;
      this.getAllzcfg();
    },
    // 页数的改变
    handleSize(val) {
      this.loading = true;
      this.page.pageSize = val;
      this.page.pageNum = 1;
      this.getAllzcfg();
    },
  },
};
</script>

<style scoped>
.zcfg-content {
  width: 100%;
  min-height: 572px;
  /* height: calc(100vh - 452px); */
  padding: 0 20.05% 0px 20.05%;
  height: auto;
}
.zcfg-content >>> .el-table__header-wrapper {
  height: 0;
}
.zcfg-content >>> .el-table td.is-center {
  /* color: #ccc; */
}
.zcfg-content >>> .el-input__inner {
  border-radius: 0;
}
.el-input {
  width: 400px !important;
  padding-bottom: 10px;
}
.pagination {
  margin-top: 25px;
  text-align: center;
}
.zcfgxq-content >>> .el-table th.is-leaf {
  border: none;
}
.zcfgxq-content >>> .el-table--border td {
  border: none;
}
.el-table >>> .cell {
  line-height: 21px;
}

.el-pagination {
  margin-top: 14px !important;
  padding: 2px 0px;
}
</style>