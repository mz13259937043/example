<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
        <el-breadcrumb-item>项目监管</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="xmqzq-content">
      <el-form :inline="true">
        <div style="margin-top: 15px">
          <el-input
            placeholder="请输入地籍编号、项目名称、项目地址、项目所属行政区"
            v-model="page.str"
            class="input"
          >
          </el-input>
          <el-button @click="OnSearch" class="btn" style="border-radius: 0"
            >搜索</el-button
          >
        </div>
      </el-form>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="xmqzqData"
        border
        style="width: 100%; cursor: pointer"
        :row-style="{ height: '40px' }"
        @row-click="openDetail"
        :header-cell-style="{ background: '#0091ff' }"
      >
        <el-table-column prop="djbh" label="地籍编号" align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.djbh)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="xmmc" label="项目名称" align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.xmmc)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="xmdz" label="项目地址" align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.xmdz)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="xmssxzq" label="项目所属行政区" align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.xmssxzq)"></span>
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
      > -->
      <!-- </el-pagination> -->
      <el-pagination
        background
        v-if="page.total > 9"
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
  name: "xmqzq",
  components: {
    NavBar,
    Header,
    Bottom,
  },
  data() {
    return {
      total: 0,
      // pagination: {
      //   current: 1,
      //   pageSize: 15,
      //   total: 100,
      // },
      //  分页组件
      page: {
        total: 1,
        pageNum: 1,
        pageSize: 10,
        str: "",
      },
      loading: true,
      xmqzqData: [
        // {
        //   djbh: "JK-123456",
        //   xmmc: "5GW单晶光伏电池生产基地项目测绘工程",
        //   xmdz: "西安经发产业园建设有限公司",
        //   chjd: "项目用地测量",
        //   chsx: "土地勘测定界",
        //   xmshxzq: "经开区"
        // },
        // {
        //   djbh: "XC-89076",
        //   xmmc: "西安前海人寿金融中心项目规划验收测量",
        //   xmdz: "	西安前海置业有限公司",
        //   chjd: "项目用地测量",
        //   chsx: "1：500地形图测绘",
        //   xmshxzq: "新城区"
        // },
        // {
        //   djbh: "JK-243235",
        //   xmmc: "政府公屋项目规划验收测量",
        //   xmdz: "西安经开城建集团有限责任公司",
        //   chjd: "竣工及不动产登记测量",
        //   chsx: "权籍调查",
        //   xmshxzq: "经开区"
        // }
      ],
    };
  },
  mounted() {
    //获取中选公告列表
    this.getAllxmqzq();
  },
  methods: {
    // 根据条件搜索中选公告信息
    OnSearch() {
      //this.getAllxmqzq()
    },
    // 获取所有中选公告信息
    getAllxmqzq() {
      let params = {
        // pageIndex: 1,
        // pageSize: 10,
        pageIndex: this.page.pageNum,
        pageSize: this.page.pageSize,
        str: this.page.str,
      };

      this.$http.post("xmwt/dchyFbxx/getGroundLifecycle").then((res) => {
        this.xmqzqData = res.data.data;
        this.page.total = res.data.data.length;
        this.loading = false;
      });
    },
    // 单击行跳转至中选公告详情页
    openDetail(rowData) {
      let xmqzqJson = JSON.stringify(rowData);
      window.localStorage.setItem("xmqzqJson", xmqzqJson);
      this.$router.push({
        name: "xmqzqdetail",
        // query: { id: rowData.ID },
        // query: {
        //   chjd: "项目用地测量",
        //   chsx: "1：500地形图测绘",
        //   chjz: "测绘实施"
        // }
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
      this.getAllxmqzq();
    },
    // 页数的改变
    handleSize(val) {
      this.loading = true;
      this.page.pageSize = val;
      this.page.pageNum = 1;
      this.getAllxmqzq();
    },
  },
};
</script>

<style scoped>
.xmqzq-content {
  width: 100%;
  /* height: calc(100vh - 438px); */
  min-height: 572px;
  padding: 0 20.05% 0px 20.05%;
  height: auto;
}
.el-input {
  width: 400px !important;
  padding-bottom: 20px;
}
.input .el-input-group__prepend {
  background-color: #fff;
}
.xmqzq-content >>> .el-input__inner {
  border-radius: 0;
}
.pagination {
  margin-top: 20px;
  text-align: center;
}
</style>