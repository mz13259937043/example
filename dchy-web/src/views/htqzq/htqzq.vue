<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="htqzq-content">
      <el-form :inline="true">
        <div style="margin-top: 15px">
          <el-input
            placeholder="请输入项目名称、项目地址、测绘阶段、测绘单位"
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
        :data="htqzqData"
        border
        style="width: 100%; cursor: pointer"
        :row-style="{ height: '40px' }"
        @row-click="openDetail"
        :header-cell-style="{ background: '#0091ff' }"
      >
        <el-table-column prop="fbxx.xmmc" label="项目名称" align="center">
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.fbxx.xmmc)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column prop="fbxx.xmdz" label="项目地址" align="center" width="150">
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.fbxx.xmdz)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column prop="fbxx.chjd" label="测绘阶段" align="center" width="120">
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.fbxx.chjd)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column prop="chdwmc" label="测绘单位" align="center" width="200">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.chdwmc)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="ydkssj" label="约定开始时间" align="center" width="100">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.ydkssj)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="ydjssj" label="约定结束时间" align="center" width="100">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.ydjssj)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="qdrq" label="合同签订时间" align="center" width="100">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.qdrq)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="qdrq" label="状态" align="center" width="100">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.fbxx&&scope.row.fbxx.xmzt==='办结'" disable-transitions>已办结</el-tag>
            <el-tag type="primary" v-else disable-transitions>正在处理</el-tag>
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
  name: "htqzq",
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
        total: null,
        pageNum: 1,
        pageSize: 10,
        str: "",
      },
      htqzqData: [
        // {
        //   xmmc: "西安市航空基地元和置业有限公司项目用地测量项目",
        //   xmdz: "西安市航空基地元和置业有限公司",
        //   chjd: "项目用地测量",
        //   chsx: "1：500地形图测绘",
        //   chjz: "测绘实施",
        //   chdw: "爬山虎科技股份有限公司",
        //   ydkssj: "2020-10-01",
        //   ydjssj: "2020-10-20"
        // },
        // {
        //   xmmc: "西安市航空基地元和置业有限公司竣工验收测量项目",
        //   xmdz: "西安市航空基地元和置业有限公司",
        //   chjd: "竣工及不动产登记测量",
        //   chsx: "房屋面积预测",
        //   chjz: "业务单位确认",
        //   chdw: "爬山虎科技股份有限公司",
        //   ydkssj: "2020-10-05",
        //   ydjssj: "2020-10-25"
        // },
        // {
        //   xmmc: "西安高新区基础设施配套建设开发有限责任公司竣工验收测量项目",
        //   xmdz: "西安高新区基础设施配套建设开发有限责任公司",
        //   chjd: "竣工及不动产登记测量",
        //   chjz: "成果上传",
        //   chdw: "爬山虎科技股份有限公司",
        //   ydkssj: "2020-11-05",
        //   ydjssj: "2020-11-25"
        // }
      ],
      loading: true,
    };
  },
  // mounted() {
  //   //获取合同列表
  //   this.getAllhtqzq();
  // },
  created() {
    if (window.localStorage.getItem("processList")) {
      this.processList = JSON.parse(window.localStorage.getItem("processList"));
    }

    this.getAllhtqzq();
  },
  methods: {
    // 根据条件搜索中选公告信息
    OnSearch() {
      this.getAllhtqzq();
    },
    // 获取所有中选公告信息
    getAllhtqzq() {
      let userId = "";
      if (window.localStorage.getItem("userinfo")) {
        let userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
        userId = userinfo.id;
      }
      let params = {
        wtdwid: userId,
        sfqr: "Y",
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
        searchStr: this.page.str,
      };
      this.$http.post("xmwt/dchyHtxx/getHtQzqList", params).then((res) => {
        this.page.total = res.data.count;

        _.each(res.data.data, (v) => {
          if (v.ydkssj) {
            v.ydkssj = this.$moment(v.ydkssj).format("YYYY-MM-DD");
          } else {
            v.ydkssj = "";
          }
          if (v.ydjssj) {
            v.ydjssj = this.$moment(v.ydjssj).format("YYYY-MM-DD");
          } else {
            v.ydjssj = "";
          }
          if (v.qdrq) {
            v.qdrq = this.$moment(v.qdrq).format("YYYY-MM-DD");
          } else {
            v.qdrq = "";
          }
          let chjd1 = _.filter(this.processList,item=>{
            return item.id === v.fbxx.chjd;
          })[0];
          if (chjd1){
            v.fbxx.chjd = chjd1.processAlias;
          }

        });
        this.htqzqData = res.data.data;
        this.loading = false;
      });
    },
    // 单击行跳转至中选公告详情页
    openDetail(rowData) {
      let htqzqJson = JSON.stringify(rowData);

      window.localStorage.setItem("htqzqJson", htqzqJson);
      this.$router.push({
        name: "htqzqdetail",
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
      //console.log(val)
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
        //console.log(val.replace(reg, "<font style='color:red'>$1</font>"))
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
      this.getAllhtqzq();
    },
    // 页数的改变
    handleSize(val) {
      // console.log(val);
      this.loading = true;
      this.page.pageSize = val;
      this.page.pageNum = 1;
      this.getAllhtqzq();
    },
  },
};
</script>

<style scoped>
.htqzq-content {
  width: 100%;
  height: auto;
  min-height: 572px;
  padding: 0 20.05% 0px 20.05%;
}
.el-input {
  width: 400px !important;
  padding-bottom: 20px;
}
.input .el-input-group__prepend {
  background-color: #fff;
}
.htqzq-content >>> .el-input__inner {
  border-radius: 0;
}
.pagination {
  margin-top: 20px;
  text-align: center;
}
</style>