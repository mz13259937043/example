<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>中选公告</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="zxgg-content">
      <el-form :inline="true" :model="ywxxForm">
        <div>
          <el-input
            placeholder="请输入项目名称、采购单位、测绘阶段、中选单位"
            v-model="ywxxForm.input"
            class="input"
          >
          </el-input>
          <el-button @click="getAllzxgg" class="btn" style="border-radius: 0"
            >搜索</el-button
          >
        </div>
      </el-form>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="zxggData"
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
        <el-table-column prop="XMMC" label="项目名称" align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMMC)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="WTDW"
          label="采购单位"
          align="center"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.WTDW)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="chjd"
          label="测绘阶段"
          align="center"
          min-width="50"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.CHJD)"></span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="bmkssj" show-overflow-tooltip label="报名开始时间" align="center">
           <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.bmkssj)"></span>
          </template>
        </el-table-column> -->

        <el-table-column
          prop="CHDWmc"
          label="中选单位"
          align="center"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.CHDWMC)"></span>
          </template>
        </el-table-column>

        <el-table-column
          prop="FB"
          label="中选结果"
          align="center"
          width="95"
        >
          <template slot-scope="scope">
            <span
              size="medium"
              v-if="scope.row.FB && scope.row.FB.indexOf('废标') != -1"
              v-html="changeColor('废标')"
            ></span>
            <span
              size="medium"
              v-else
              v-html="changeColor(scope.row.FB)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column
                prop="zxsj"
                label="中选时间"
                align="center"
                width="110"
        >
          <template slot-scope="scope">
            <span size="medium" v-if="scope.row.FB && scope.row.FB.indexOf('废标') != -1" v-html=""></span>
            <span size="medium" v-else v-html="changeColor(scope.row.zxsj)"></span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="CHDW" show-overflow-tooltip label="操作" align="center" width="95">
           <template slot-scope="scope">
            <el-button class="btn-danger" size="small" @click="toRevoke(scope.row,$event)">撤销</el-button>
          </template>
        </el-table-column> -->
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
      <el-dialog
        title="撤销中标项目"
        :visible.sync="revokeDiag.visib"
        width="30%"
      >
        <el-input placeholder="请输撤销原因" v-model="revokeDiag.czsm" />
        <span slot="footer" class="dialog-footer">
          <el-button @click="revokeDiag.visib = false">取 消</el-button>
          <el-button type="primary" @click="revokeProject">确 定</el-button>
        </span>
      </el-dialog>
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
import { doMessage } from "@/assets/js/common.js";

export default {
  name: "zxgg",
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
      userinfo: "",
      paginationshow: false,
      //  分页组件
      page: {
        total: null,
        pageNum: 1,
        pageSize: 10,
        str: "",
      },
      ywxxForm: {
        input: "",
      },
      zxggData: [],
      revokeDiag: {
        visib: false,
        data: null,
        czsm: "",
      },
      loading: true,
    };
  },
  mounted() {
    //获取中选公告列表
    this.getAllzxgg();
  },
  methods: {
    toRevoke(val, event) {
      event.stopPropagation();
      this.revokeDiag.visib = true;
      this.revokeDiag.data = val;
    },
    //项目撤销
    revokeProject() {
      // 再此判断，是否是需要操作日志
      let val = { ...this.revokeDiag.data };
      // 查当前数据的最新日志
      let lastLogId = "";
      this.$http
        .get("dchy/dchyLog/getLastLog?pid=" + val.ID)
        .then((res) => {
          let lastLog = { ...res.data.data };
          lastLogId = lastLog.id;
          let param = {
            log: {
              fsrxm: this.userinfo.dwmc,
              fsrid: this.userinfo.id,
              jsrxm: val.WTDW,
              jsrid: val.WTDWID,
              pid: val.ID,
              dqhj: "采购确认",
              clzt: "待办",
              ywlx: "信息发布",
              czsm: this.revokeDiag.czsm,
            },
            lastLogId: lastLogId,
          };

          if (lastLog && lastLog.dqhj === "合同上传") {
            //中标公告那块只在下一环节合同上传前允许撤回
            param.log.dqhj = "采购确认";
          } else if (lastLog && lastLog.dqhj === "采购确认") {
            param.log.dqhj = "草稿";
          } else {
            param.log.dqhj = "不知道发为哪个环节";
          }
          return this.$http.post("dchy/dchyLog/revokeProject", param);
          // return  this.$http.post("dchy/dchyLog/revokeProject", {id:'',xmzt:'草稿'});
        })
        .then((res) => {
          if (res.data) {
            doMessage("撤销", true);
          } else {
            doMessage("撤销", false);
          }
        });
    },
    // 获取所有中选公告信息
    getAllzxgg() {
      if (window.localStorage.getItem("processList")) {
        this.processList = JSON.parse(window.localStorage.getItem("processList"));
      }
      if (window.localStorage.getItem("userinfo")) {
        this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
      }
      let params = {
        // pageIndex: 1,
        // pageSize: 10,
        pageIndex: this.page.pageNum,
        pageSize: this.page.pageSize,
        str: this.ywxxForm.input,
      };
      this.$http.post("xmwt/dchySqyx/getZbGg", params).then((res) => {
        if (res.data.count > 9) {
          this.paginationshow = true;
        } else {
          this.paginationshow = false;
        }
        this.page.total = res.data.count;
        _.each(res.data.data, (v) => {
          v.fbsj = this.$moment(v.cjsj).format("YYYY-MM-DD");
          v.bmkssj = this.$moment(v.BMKSSJ).format("YYYY-MM-DD");
          v.zxsj = this.$moment(v.WTSJ).format("YYYY-MM-DD HH:mm:ss");

          let chjd = _.filter(this.processList,item=>{
            return item.id === v.CHJD;
          })[0];
          if (chjd){
            v.CHJD = chjd.processAlias;
          }
          // 测绘阶段
          // this.zxggData.push(v);
        });
        this.zxggData = res.data.data;
        this.loading = false;
      });
    },
    // 双击行跳转至中选公告详情页
    openDetail(rowData) {
      // const { href } = this.$router.resolve({
      //   name: "zxggdetail",
      //   query: { id: rowData.ID },
      // });
      // window.open(href, "_blank");
      this.$router.push({
        name: "zxggdetail",
        query: { id: rowData.ID, xmmc: rowData.XMMC },
      });
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
    // 分页
    //监听页码值的变化
    handleCurr(page) {
      this.loading = true;
      this.page.pageNum = page;
      this.getAllzxgg();
    },
    // 页数的改变
    handleSize(val) {
      this.loading = true;
      this.page.pageSize = val;
      this.page.pageNum = 1;
      this.getAllzxgg();
    },
  },
};
</script>

<style scoped>
.zxgg-content {
  width: 100%;
  /* height: calc(100vh - 438px); */
  height: auto;
  min-height: 572px;
  overflow: auto;
  padding: 0 20.05% 0px 20.05%;
}
.zxgg-content >>> .el-input__inner {
  border-radius: 0;
}
.el-input {
  width: 400px !important;
  padding-bottom: 20px;
}
.input .el-input-group__prepend {
  background-color: #fff;
}
.pagination {
  margin-top: 20px;
  text-align: center;
}
</style>