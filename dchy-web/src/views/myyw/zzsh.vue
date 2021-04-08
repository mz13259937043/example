<template>
  <div class="index">
    <Header></Header>
    <NavBar></NavBar>
    <div class="nav-title">
      <div>当前所在位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
        <el-breadcrumb-item>资质审核</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <!-- <el-input
        placeholder="请输入测绘单位名称"
        v-model="listparams.serachStr"
      >
      </el-input>
      <el-button
          slot="append"
          icon="el-icon-search"
          @click="search()"
          class="btn"
        ></el-button> -->

      <el-table
        :data="tableData"
        border
        stripe
        :header-cell-style="{ background: '#0091ff', color: '#fff' }"
        @row-click="openDetail"
      >
        <el-table-column
          prop="unit_NAME"
          label="测绘单位名称"
          align="center"
          width="200"
        ></el-table-column>
        <el-table-column
          prop="bgzzdj"
          label="资质等级"
          align="center"
          width="90"
        ></el-table-column>
        <el-table-column
          prop="bgzsbh"
          label="测绘资质证书编号"
          align="center"
          width="140"
        ></el-table-column>
        <el-table-column
          prop="bgdchyywfw"
          label="“多测合一”业务范围"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="link_MAN"
          label="联系人"
          align="center"
          width="90"
        ></el-table-column>
        <el-table-column
          prop="phone"
          label="联系电话"
          align="center"
          width="120"
        ></el-table-column>
        <el-table-column
          prop="bgsqsj"
          label="接收时间"
          align="center"
          width="100"
          :formatter="formatter"
        ></el-table-column>
      </el-table>

      <el-pagination
        background
        v-if="paginationshow"
        @current-change="handleCurr"
        @size-change="handleSize"
        :current-page="listparams.pageNum"
        :page-size="listparams.pageSize"
        layout="total,sizes, prev, pager, next, jumper"
        :total="total"
        :page-sizes="[10, 20]"
        style="margin-bottom: 20px; text-align: center"
      ></el-pagination>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Bottom from "@/components/Bottom.vue";
import NavBar from "@/components/NavBar.vue";
import { formatDate } from "@/assets/js/common.js";
const tableData = [
  {
    chdwmc: "",
    zzdj: "",
    chzzzsbh: "",
    dchyywfw: "",
    lxr: "",
    lxdh: "",
  },
];
export default {
  data() {
    return {
      listparams: {
        total: null,
        //chdwid: "",
        pageNum: 1,
        pageSize: 10,
        //serachStr: "",
      },
      paginationshow: false,
      total: 0,
      tableData,

      // 用户信息
      userinfo: "",
      //角色信息
      userRole: "",
    };
  },
  components: {
    Header,
    Bottom,
    NavBar,
  },
  created() {
    //this.getList();

    // 获取个人信息
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    // 获得角色信息
    if (window.localStorage.getItem("userRole")) {
      this.userRole = window.localStorage.getItem("userRole");
    }
  },
  mounted() {
    if (this.userRole.indexOf("初审人") > -1) {
      this.chushenList();
    } else if (this.userRole.indexOf("终审人") > -1) {
      this.zhongshenList();
    }
  },
  methods: {
    chushenList() {
      let _this = this;
      let params = {
        pageNum: 1,
        pageSize: 10,
      };
      _this.$http.post("zzxx/selectAllCsZzxx", params).then((res) => {
        _this.total = res.data.listSize;
        _this.tableData = res.data.data;
      });
    },
    zhongshenList(data) {
      let _this = this;
      let params = {
        pageNum: 1,
        pageSize: 10,
        ...data,
      };
      _this.$http.post("zzxx/selectAllZsZzxx", params).then((res) => {
        _this.total = res.data.listSize;
        _this.tableData = res.data.data;
      });
    },
    // 条件搜索
    // search() {
    //   let chdwmc = this.listparams.serachStr;
    //   let obj = {
    //     chdwmc: chdwmc,
    //   };
    //   console.log(this.listparams.serachStr);
    //   this.zhongshenList(obj);
    //   // this.getList();
    // },

    //格式化
    formatter(row, column) {
      var rowdate = row[column.property];
      if (rowdate) {
        return this.formatDateTime(new Date(rowdate));
      } else {
        return "";
      }
    },
    formatTen(num) {
      return num > 9 ? num + "" : "0" + num;
    },
    formatDateTime(date) {
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var hour = date.getHours();
      var minute = date.getMinutes();
      var second = date.getSeconds();
      return (
        year +
        "-" +
        this.formatTen(month) +
        "-" +
        this.formatTen(day) +
        " " +
        this.formatTen(hour) +
        ":" +
        this.formatTen(minute) +
        ":" +
        this.formatTen(second)
      );
    },
    handleSize(val) {
      // 每页多少条数据
      // console.log(`每页 ${val} 条`);
      this.listparams.pageSize = val;
      //this.getList();
    },
    handleCurr(val) {
      // 当前是第几页
      this.listparams.pageNum = val;
      //this.getList();
    },
    getList() {
      this.$http.post("zzxx/selectAllCsZzxx", this.listparams).then((res) => {
        if (res.data.listSize > 9) {
          this.paginationshow = true;
        } else {
          this.paginationshow = false;
        }
        this.total = res.data.listSize;
        this.tableData = res.data.data;
      });
    },
    openDetail(rowData) {
      // window.localStorage.setItem("xzfjid", rowData.bgzzfj);
      let params = {
        bgdid: rowData.bgdid,
      };
      this.$router.push({
        name: "chdwzcdetail",
        query: params,
      });
    },
    handleRefuse(index, rowData) {},
  },
};
</script>

<style  scoped>
.breadcrumb {
  height: 50px;
  display: flex;
  margin-bottom: 10px;
  padding: 0px 3rem;
  align-items: center;
  background: #0068d1;
}

.chdwcx-inp {
  width: 40%;
}

.cz-btn {
  width: 100%;
  background-color: #f3f3f3;
  border-color: 0;
  border: 1px solid #626262;
  color: #000;
  outline: 0;
}

.cz-btn:hover {
  background-color: #f3f3f3;
}

.container {
  padding: 0 20%;
  height: auto;
  min-height: 567px;
}

.el-input {
  width: 400px !important;
}
.el-table {
  width: 100% !important;

  margin-top: 20px;
}
.el-pagination {
  margin-bottom: 15px;
}
.wdyw-content {
  width: 100%;
  height: auto;
  padding: 0 20.05% 100px 20.05%;
}
.el-input >>> .el-input__inner {
  border-radius: 0px;
}
</style>