<template>
  <div class="second">
    <!-- 默认登录显示 -->
    <div class="second_list">
      <!-- 测绘工程师/高级工程师显示 -->
      <div
        class="second_list_gr"
        v-if="userRole == 'chsgcs' || userRole == '成果验收经办人'"
      >
        <div class="tablecssssss">
          <div class="divInput" v-if="this.$route.path == '/grzx'">
            <el-input
              placeholder="请输入项目名称、测量事项、建设单位"
              v-model="tabs.searchStr"
              class="input"
            >
            </el-input>
            <el-button
              class="btn"
              @click="reloadTableData"
              style="border-radius: 0"
              >搜索</el-button
            >
          </div>
          <div class="divTop" v-if="this.$route.path == '/index'">
            <div
              v-for="(item, index) in tabs.titles"
              :class="{ activepane: item.dbyb === tabs.title }"
              :key="index"
            >
              <img
                :id="item.name"
                class="titleImg"
                src="@/assets/icons/dbyb.svg"
                v-if="item.dbyb != 'blueDiv'"
              />
              <div class="pane" @click="tabSwitch(item)">
                <div :id="item.dbyb">
                  <div class="title2">{{ item.title }}</div>
                </div>
              </div>
            </div>
            <div
              v-if="this.$route.path == '/index'"
              class="right"
              @click="gotogrzx()"
            >
              查看全部 >
            </div>
          </div>

          <el-row>
            <el-table
              element-loading-text="拼命加载中"
              element-loading-spinner="el-icon-loading"
              class="tableClass"
              v-loading="tabs.loading"
              :data="tabs.tableData"
              style="width: 100%"
              :show-header="this.$route.path != '/index'"
              @row-click="gotodetail"
              :row-style="{ height: '32px', color: '#000' }"
              :header-cell-style="{
                fontWeight: 'bold',
                fontSize: '15px',
                background: '#0091ff',
              }"
            >
              <el-table-column
                prop="XMMC"
                label="项目名称"
                :show-overflow-tooltip="true"
                align="center"
              >
                <template slot-scope="scope">
                  <span
                    size="medium"
                    v-html="changeColor(scope.row.XMMC)"
                  ></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="WTDW"
                label="采购单位"
                width="250"
                :show-overflow-tooltip="true"
                align="center"
              >
                <template slot-scope="scope">
                  <span
                    size="medium"
                    v-html="changeColor(scope.row.WTDW)"
                  ></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="FBXXCHJD2"
                label="测绘阶段"
                width="150"
                :show-overflow-tooltip="true"
                align="center"
              >
                <template slot-scope="scope">
                  <span
                    size="medium"
                    v-html="changeColor(scope.row.FBXXCHJD2)"
                  ></span>
                </template>
              </el-table-column>

              <el-table-column
                prop="JSSJ"
                label="接收时间"
                align="center"
                width="200"
                format="yyyy-MM-dd HH:mm:ss"
                :show-overflow-tooltip="true"
              >
                <template slot-scope="scope">
                  <span
                    size="medium"
                    v-html="changeColor(scope.row.BMJSSJ)"
                  ></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="LOGCLZT"
                label="状态"
                align="center"
                width="100"
              >
                <template slot-scope="scope">
                  <el-tag
                    type="primary"
                    v-if="scope.row.LOGCLZT === '待办'"
                    disable-transitions
                    >待处理</el-tag
                  >
                  <el-tag
                    type="danger"
                    v-else-if="scope.row.LOGCLZT.indexOf('废标') != -1"
                    disable-transitions
                    >已废标</el-tag
                  >
                  <el-tag
                    type="success"
                    v-else-if="scope.row.LOGCLZT === '已办'"
                    disable-transitions
                    >已处理</el-tag
                  >
                  <!--            <span size="medium" v-html="changeColor(scope.row.LOGCLZT)"></span>-->
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              background
              @current-change="handleCurr"
              @size-change="handleSize"
              :current-page="tabs.currentPage"
              :page-size="tabs.pageSize"
              layout="total,sizes, prev, pager, next, jumper"
              :total="tabs.total"
              :page-sizes="[10, 20]"
              style="margin-bottom: 20px; text-align: center"
              v-if="this.$route.path == '/grzx'"
            ></el-pagination>
          </el-row>
        </div>
      </div>
      <div
        class="second_list_item"
        @click="goto(item.path)"
        v-for="(item, index) in userlist"
        :key="index"
        v-else
      >
        <span
          v-if="item.title == '我的项目' && dbCount > 0"
          class="el-badge__content"
          :title="'您有' + dbCount + '条待办待处理'"
          >{{ dbCount > 9 ? "9+" : dbCount }}
        </span>
        <span
          v-if="item.title == '委托测绘' && blCount > 0"
          class="el-badge__content"
          :title="'您有' + blCount + '条待办待处理'"
          >{{ blCount > 9 ? "9+" : blCount }}
        </span>
        <img
          :src="item.icon"
          style="margin-top: 10px"
          v-if="
            (item.title == '我的项目' && dbCount > 0) ||
            (item.title == '委托测绘' && blCount > 0)
          "
        />
        <img :src="item.icon" v-else />

        <div class="second_list_item_title">{{ item.title }}</div>
        <div class="second_list_item_detail">{{ item.detail }}。</div>
      </div>
    </div>
  </div>
</template>

<script>
import { formatDate, formatDate2 } from "@/assets/js/common.js";
export default {
  data() {
    return {
      tabs: {
        activeName: "activeName",
        currentPage: 1,
        pageSize: this.$route.path == "/grzx" ? 10 : 6,
        searchStr: "",
        total: null,
        jsrid: "", //人员
        tableData: [],
        loading: true,
        titles: [
          { title: "我的项目", dbyb: "待办", name: "dbxm" },
          // { title: "l", dbyb: "blueDiv", name: "" },
          // { title: "已办项目", dbyb: "已办", name: "ybxm" },
        ],
        title: "待办",
      },
      userinfo: "",
      userlist: [],
      userRole: "",
      cglist: [],
      yqrlist: [],
      existZZ: "",
      bgzzxxdate: "",
      dbCount: "",
      blCount: "",
    };
  },
  methods: {
    changeColor(val) {
      let tags = this.tabs.searchStr;
      if (tags !== null && tags !== "" && val !== null && val !== "") {
        let reg = new RegExp("(" + tags + ")", "g");
        return val.replace(reg, "<font style='color:red'>$1</font>");
      } else {
        return val;
      }
    },
    // 跳转个人中心查看全部
    gotogrzx() {
      this.$router.push({
        name: "grzx",
      });
    },
    /**
     * 分页部分
     */
    //监听页码值的变化
    handleCurr(page) {
      // this.loading=true;
      this.tabs.currentPage = page;
      this.reloadTableData();
    },
    handleSize(val) {
      // this.loading=true;
      this.tabs.pageSize = val;
      this.tabs.currentPage = 1;
      this.reloadTableData();
    },
    // 切换已办
    tabSwitch(val) {
      // 每切换一次，分页重置
      this.tabs.title = val.dbyb;
      if (this.$route.path == "/grzx") {
        this.tabs.pageSize = 10;
      } else {
        this.tabs.pageSize = 6;
      }
      this.tabs.currentPage = 1;
      this.reloadTableData();
    },
    gotodetail(item) {
      console.log(item);
      this.$store.commit("changeywxqym", item);
      if (window.localStorage.getItem("ywblItem")) {
        window.localStorage.removeItem("ywblItem");
      }
      if (window.localStorage.getItem("ywdqhjall")) {
        window.localStorage.removeItem("ywdqhjall");
      }
      if (window.localStorage.getItem("cgqrItem")) {
        window.localStorage.removeItem("cgqrItem");
      }
      if (window.localStorage.getItem("cgys")) {
        window.localStorage.removeItem("cgys");
      }
      if (window.localStorage.getItem("cgsh")) {
        window.localStorage.removeItem("cgsh");
      }
      if (window.localStorage.getItem("ybListItem")) {
        window.localStorage.removeItem("ybListItem");
      }
      if (window.localStorage.getItem("listZT")) {
        window.localStorage.removeItem("listZT");
      }
      if (window.localStorage.getItem("loadStage")) {
        window.localStorage.removeItem("loadStage");
      }

      debugger;
      window.localStorage.setItem("xzfjid", item.YWXXCGHJFJ);
      var obj = JSON.stringify(item);
      if (item.LOGCLZT == "已办") {
        window.localStorage.setItem("listZT", "已办");
        window.localStorage.setItem("ybListItem", obj);
        this.$router.push({
          name: "ywxqym",
        });
        return;
      }
      if (item.LOGDQHJ == "测绘工程师确认") {
      }
      if (item.LOGDQHJ == "分局成果审核") {
      }
      // window.open(href, "_blank");
      this.$router.push({
        name: "ywxqym",
      });
    },
    goto(path) {
      if (window.localStorage.getItem("userinfo")) {
        if (
          path == "tjfx" ||
          path == "xygl" ||
          path == "xygs" ||
          path == "ssjgk" ||
          path == "wdxy" ||
          path == "xypj"
        ) {
          this.$message({
            message: "该功能待开放",
            offset: 200,
            type: "warning",
          });
        } else {
          debugger;
          if (
            this.userRole == "chdw" &&
            this.userinfo.zzxx.state &&
            this.userinfo.zzxx.state == "冻结" &&
            path == "zzbg"
          ) {
            this.$message({
              message: "您的资质状态异常，请联系管理员",
              offset: 200,
              type: "warning",
            });
            return;
          }
          const { href } = this.$router.push({
            name: path,
            query: { pid: "" },
          });
        }
      } else {
        this.$message({
          message: "请登录后使用",
          offset: 200,
          type: "warning",
        });
      }
    },
    showOrHideImg() {
      debugger;
      let zt = this.tabs.title;
      if (zt == "待办") {
        document.getElementById("dbxm").style.display = "inline";
        document.getElementById("ybxm").style.display = "none";
        document.getElementById("blueDiv").parentNode.parentNode.style.display =
          "inline";
      } else if (zt == "已办") {
        document.getElementById("ybxm").style.display = "inline";
        document.getElementById("dbxm").style.display = "none";
        document.getElementById("blueDiv").parentNode.parentNode.style.display =
          "none";
      }
    },
    // 二级菜单列表的  自定义的功能，待办已办列表
    reloadTableData() {
      // 想用一个接口完成这个功能
      //  待办已办，用户id，分页参数，模糊搜索
      let _this = this;
      _this.tabs.loading = true;
      let param = {
        jsrid: _this.userinfo.id,
        clzt: "已办", //待办已办
        searchStr: _this.tabs.searchStr,
        pageNum: _this.tabs.currentPage,
        pageSize: _this.tabs.pageSize,
      };
      _this.$http.post("dchy/dchyLog/getDealingList", param).then((res) => {
        debugger;
        _this.tabs.loading = false;
        let resData = res.data.data;
        if (resData.length > 0) {
          for (let value of resData) {
            value.BMJSSJ = formatDate2(new Date(value.FBXXBMJSSJ)) || "";
            value.BMKSSJ = formatDate2(new Date(value.FBXXBMKSSJ)) || "";
            value.LOGJSSJ = formatDate2(new Date(value.LOGJSSJ)) || "";
            value.JSSJ = formatDate2(new Date(value.JSSJ)) || "";
            for (let i = 0; i < resData.length; i++) {
              let a = _.filter(this.processList, (item) => {
                return item.id === resData[i].FBXXCHJD;
              });
              if (a.length > 0) {
                resData[i].FBXXCHJD2 = a[0].processAlias;
              }
            }
            // value.BMKSSJ = !value.FBXXBMKSSJ||formatDate2(new Date(value.FBXXBMKSSJ));
            /*if ( value.bmkssj){
              value.bmkssj = formatDate2(new Date(value.bmkssj));
            }*/
          }
          this.tabs.tableData = resData;
          this.tabs.total = parseInt(res.data.count);
          // Object.assign(this.tabs.count,resData.count);
          // Object.assign(this.tabs.tableData,resData);
        } else {
          this.tabs.tableData = [];
          this.tabs.total = 0;
        }
        // this.notAllowDivClick();
        // this.showOrHideImg();
      });
    },

    reloadTableData1(role) {
      let _this = this;
      if (_this.userinfo) {
        let param = {
          jsrid: _this.userinfo.id,
          clzt: "待办",
          searchStr: "",
          pageNum: 1,
          pageSize: 10,
        };
        let url =
          role == "zcchs"
            ? "dchy/dchyLog/getDealingList"
            : "dchy/dchyLog/getDealingList";
        // 我的项目，采购单位的待办列表
        _this.$http.post(url, param).the;
        n((res) => {
          for (let value of res.data.data) {
            value.LOGFSSJ = formatDate2(new Date(value.LOGFSSJ));
          }
          _this.cglist = res.data.data;
        });
        if (_this.userinfo) {
          let param = {
            jsrid: _this.userinfo.id,
            clzt: "已办",
            searchStr: "",
            pageNum: 1,
            pageSize: 10,
          };
          // 我的项目，采购单位的待办列表
          _this.$http.post("dchy/dchyLog/getDealingList", param).then((res) => {
            for (let value of res.data.data) {
              value.LOGFSSJ = formatDate2(new Date(value.LOGFSSJ));
            }
            _this.yqrlist = res.data.data;
          });
        }
      }
    },
    notAllowDivClick() {
      debugger;
      // 待办和已办中间的蓝点点禁止点击
      var notClickDiv = document.getElementById("blueDiv").parentNode;
      notClickDiv.style.setProperty("pointer-events", "none");
      if (this.$route.path != "/grzx") {
        // 设置首页固定高度
        var tablecssssss = document.getElementsByClassName("tablecssssss");
        tablecssssss[0].style.height = "380px";
        var gutter = document.getElementsByClassName("has-gutter");
        gutter[0].style.display = "none";
      }
    },
  },
  created() {
    // console.log(this.$route);

    // 获取用户信息
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
      // console.log(this.userinfo);
    }
    if (window.localStorage.getItem("processList")) {
      this.processList = JSON.parse(window.localStorage.getItem("processList"));
    }
    //获取是否存在资质注册进度
    if (window.localStorage.getItem("bgzzxxdate")) {
      this.bgzzxxdate = window.localStorage.getItem("bgzzxxdate");
    }
    // 获取是否存在资质信息
    if (window.localStorage.getItem("existZZ")) {
      this.existZZ = window.localStorage.getItem("existZZ");
      if (this.existZZ == 0) {
        console.log("有资质信息");
      }
      if (this.existZZ == 1) {
        console.log("没有资质信息");
      }
    }

    if (window.localStorage.getItem("userRole")) {
      this.userRole = window.localStorage.getItem("userRole");
      // console.log("从local获取userRole");
      // console.log(this.userRole);
      if (this.userRole == "chsgcs") {
        this.reloadTableData();
      } else if (this.userRole == "成果验收经办人") {
        this.reloadTableData();
      }
    } else {
      this.userRole = "";
      // console.log("没有获取到 userRole");
    }

    if (
      this.userRole == "chdw" ||
      this.userRole == "yzdw" ||
      this.userRole == "成果验收经办人"
    ) {
      let param = {
        jsrid: this.userinfo.id,
        clzt: "已办",
        searchStr: "",
        pageNum: 1,
        pageSize: 100,
      };
      this.$http.post("dchy/dchyLog/getDealingList", param).then((res) => {
        debugger;
        var items = [];
        if (res.data && res.data.data) {
          let _resData = [...res.data.data];
          items = _resData.filter(function (item) {
            return item.LOGCLZT == "待办";
          });
        }
        this.dbCount = items.length;
      });
    }
    if (this.userRole == "yzdw") {
      this.$http
        .post("xmwt/dchyFbxx/findMyProByFbxx", { wtdwid: this.userinfo.id })
        .then((res) => {
          this.blCount = res.data.count;
        });
    }
    if (this.userRole.indexOf("初审人") > -1) {
      let _this = this;
      let params = {
        pageNum: 1,
        pageSize: 200,
      };
      _this.$http.post("zzxx/selectAllCsZzxx", params).then((res) => {
        _this.dbCount = res.data.data.length;
      });
    } else if (this.userRole.indexOf("终审人") > -1) {
      let _this = this;
      let params = {
        pageNum: 1,
        pageSize: 200,
      };
      _this.$http.post("zzxx/selectAllZsZzxx", params).then((res) => {
        _this.dbCount = res.data.data.length;
      });
    }

    if (this.userRole == "") {
      this.userlist = [
        {
          icon: require("@/assets/img/xmfb1.png"),
          title: "项目发布",
          detail: "填写项目基本信息、业务信息，进行在线项目发布",
          path: "xmfb",
        },
        {
          icon: require("@/assets/img/myyw.png"),
          title: "项目管理",
          detail: "查看测绘事项进展情况",
          path: "htqzq",
        },
        {
          icon: require("@/assets/img/chdwzc.png"),
          title: "测绘单位注册",
          detail: "对测绘机构资质等级、人员信息等进行注册",
          path: "chdwzc",
        },
        {
          icon: require("@/assets/img/blyw1.png"),
          title: "服务评价",
          detail: "对测绘机构的服务质量进行评价",
          path: "xypj",
        },
      ];
    } else if (this.userRole == "yzdw") {
      this.userlist = [
        {
          icon: require("@/assets/img/xmfb1.png"),
          title: "项目发布",
          detail: "填写项目基本信息、业务信息，进行在线项目发布",
          path: "xmfb",
        },
        {
          icon: require("@/assets/img/myyw.png"),
          title: "我的项目",
          detail: "处理待办事项，查看项目进度",
          path: "myyw",
          count: this.dbCount,
        },
        {
          icon: require("@/assets/img/chdwzc.png"),
          title: "项目管理",
          detail: "查看测绘事项进展情况",
          path: "htqzq",
        },
        {
          icon: require("@/assets/img/blyw1.png"),
          title: "服务评价",
          detail: "对测绘机构的服务质量进行评价",
          path: "xypj",
        },
      ];
    } else if (this.userRole == "chdw") {
      console.log(this.existZZ);
      if (this.existZZ == 1 && this.bgzzxxdate == 1) {
        this.userlist = [
          {
            icon: require("@/assets/img/xmfb1.png"),
            title: "项目申请",
            detail: "符合资质的测绘机构进行项目申请",
            path: "wtgg",
          },
          {
            icon: require("@/assets/img/blyw1.png"),
            title: "我的项目",
            detail: "处理待办事项，查看项目进度",
            path: "myyw",
            count: this.dbCount,
          },
          {
            icon: require("@/assets/img/myyw.png"),
            title: "资质注册",
            detail: "对测绘机构资质等级、人员信息等进行注册",
            path: "chdwzc",
          },
          {
            icon: require("@/assets/img/chdwzc.png"),
            title: "我的评价",
            detail: "查看项目业主、监管单位服务质量评价",
            path: "wdxy",
          },
        ];
      }
      if (this.existZZ == 0 && this.bgzzxxdate == 1) {
        this.userlist = [
          {
            icon: require("@/assets/img/xmfb1.png"),
            title: "项目申请",
            detail: "符合资质的测绘机构进行项目申请",
            path: "wtgg",
          },
          {
            icon: require("@/assets/img/blyw1.png"),
            title: "我的项目",
            detail: "处理待办事项，查看项目进度",
            path: "myyw",
            count: this.dbCount,
          },
          {
            icon: require("@/assets/img/myyw.png"),
            title: "资质变更",
            detail: "对测绘机构资质等级、人员信息等进行变更",
            path: "zzbg",
          },
          {
            icon: require("@/assets/img/chdwzc.png"),
            title: "我的评价",
            detail: "查看项目业主、监管单位服务质量评价",
            path: "wdxy",
          },
        ];
      }
      if (this.bgzzxxdate == 0) {
        this.userlist = [
          {
            icon: require("@/assets/img/xmfb1.png"),
            title: "项目申请",
            detail: "符合资质的测绘机构进行项目申请",
            path: "wtgg",
          },
          {
            icon: require("@/assets/img/blyw1.png"),
            title: "我的项目",
            detail: "处理待办事项，查看项目进度",
            path: "myyw",
            count: this.dbCount,
          },
          {
            icon: require("@/assets/img/myyw.png"),
            title: "资质审核进度",
            detail: "查看提交的资质注册/变更审核进度及状态",
            path: "jdcx",
          },
          {
            icon: require("@/assets/img/chdwzc.png"),
            title: "我的评价",
            detail: "查看项目业主、监管单位服务质量评价",
            path: "wdxy",
          },
        ];
      }
    } else if (this.userRole == "jgdw") {
      this.userlist = [
        {
          icon: require("@/assets/img/xmfb1.png"),
          title: "项目监管",
          detail: "串联同一地块所有测绘事项，进行土地全生命周期监管",
          path: "xmqzq",
        },
        {
          icon: require("@/assets/img/blyw1.png"),
          title: "统计分析",
          detail: "支持项目及用地台账统计分析，辅助管理决策",
          path: "tjfx",
        },
        {
          icon: require("@/assets/img/myyw.png"),
          title: "服务评价",
          detail: "对测绘机构的服务质量进行评价",
          path: "xygs",
        },
        {
          icon: require("@/assets/img/chdwzc.png"),
          title: "项目抽查",
          detail: "对所有已完成项目进行随机抽查",
          path: "ssjgk",
        },
      ];
    } else if (this.userRole == "初审人" || this.userRole == "终审人") {
      this.userlist = [
        {
          icon: require("@/assets/img/xmfb1.png"),
          title: "我的项目",
          detail: "处理待办事项，查看项目进度",
          path: "zzsh",
          count: this.dbCount,
        },
        {
          icon: require("@/assets/img/blyw1.png"),
          title: "入驻机构管理",
          detail: "对入驻机构承接项目权限进行管理",
          path: "qymlk",
        },
        {
          icon: require("@/assets/img/myyw.png"),
          title: "项目监管",
          detail: "串联同一地块所有测绘事项，进行土地全生命周期监管",
          path: "xmqzq",
        },
        {
          icon: require("@/assets/img/chdwzc.png"),
          title: "统计分析",
          detail: "支持项目及用地台账统计分析，辅助管理决策",
          path: "tjfx",
        },
      ];
    } else if (this.userRole == "管理员") {
      if (this.$route.path == "/grzx") {
        this.userlist = [
          {
            icon: require("@/assets/img/xmfb1.png"),
            title: "公告管理",
            detail: "对业主单位发布的公共进行管理",
            path: "gggl",
          },
          {
            icon: require("@/assets/img/myyw.png"),
            title: "信息管理",
            detail: "对所有发布信息进行管理",
            path: "xxgl",
          },
          {
            icon: require("@/assets/img/qymlk.png"),
            title: "入驻机构管理",
            detail: "对入驻机构冻结、解冻的权限进行管理",
            path: "qymlk",
          },
          {
            icon: require("@/assets/img/jgxm.png"),
            title: "项目监管",
            detail: "串联同一地块所有测绘事项，进行土地全生命周期监管",
            path: "xmqzq",
          },
          {
            icon: require("@/assets/img/tjfx.png"),
            title: "统计分析",
            detail: "支持项目及用地台账统计分析，辅助管理决策",
            path: "tjfx",
          },
          {
            icon: require("@/assets/img/xyjg.png"),
            title: "信用管理",
            detail: "对项目业主、监管单位服务的服务质量等级进行管理",
            path: "xygl",
          },
          {
            icon: require("@/assets/img/jsgl.png"),
            title: "角色管理",
            detail: "查看人员、角色信息，并对其进行管理",
            path: "rygl",
          },
          {
            icon: require("@/assets/img/yhgl.png"),
            title: "人员管理",
            detail: "查看资质人员、审批人员列表，并对其进行管理",
            path: "yhgl",
          },
          {
            icon: require("@/assets/img/yhgl.png"),
            title: "项目管理",
            detail: "查看资质人员、审批人员列表，并对其进行管理",
            path: "htqzq",
          },
        ];
      } else {
        this.userlist = [
          {
            icon: require("@/assets/img/myyw.png"),
            title: "信息管理",
            detail: "对所有发布信息进行管理",
            path: "xxgl",
          },
          {
            icon: require("@/assets/img/qymlk.png"),
            title: "入驻机构管理",
            detail: "对入驻机构冻结、解冻的权限进行管理",
            path: "qymlk",
          },
          {
            icon: require("@/assets/img/xmfb1.png"),
            title: "项目监管",
            detail: "串联同一地块所有测绘事项，进行土地全生命周期监管",
            path: "xmqzq",
          },
          {
            icon: require("@/assets/img/blyw1.png"),
            title: "统计分析",
            detail: "支持项目及用地台账统计分析，辅助管理决策",
            path: "tjfx",
          },
        ];
      }
    } else {
      this.userlist = [
        {
          icon: require("@/assets/img/xmfb1.png"),
          title: "项目发布",
          detail: "填写项目基本信息、业务信息，进行在线项目发布",
          path: "xmfb",
        },
        {
          icon: require("@/assets/img/blyw1.png"),
          title: "委托测绘",
          detail: "查看项目信息，发起测绘事项",
          path: "ywbl",
        },
        {
          icon: require("@/assets/img/myyw.png"),
          title: "我的项目",
          detail: "处理待办事项，查看项目进度",
          path: "myyw",
          count: this.dbCount,
        },
        {
          icon: require("@/assets/img/chdwzc.png"),
          title: "测绘单位注册",
          detail: "对测绘机构资质等级、人员信息等进行注册",
          path: "chdwzc",
        },
      ];
    }
  },
};
</script>

<style scoped>
.second_list_gr {
  width: 100%;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #4a4a4a;
  padding-top: 20px;
}
.title {
  padding: 10px 10px;
  border-left: 5px solid rgb(0 120 173);
  background: #f5f5f5;
  font-weight: 600;
  font-size: 18px;
}
.content {
  height: 280px;
  overflow-y: auto;
}
.content_item {
  display: flex;
  padding: 15px 10px;
  justify-content: space-between;
  border-bottom: 1px dashed #dedede;
  cursor: pointer;
}
.content_item:hover {
  color: #0091ff;
}
.item_name {
  flex: 6;
}
.item_right {
  flex: 4;
  display: flex;
  justify-content: space-between;
}

.second {
  width: 100%;
}
.second_list {
  display: flex;
  flex-wrap: wrap;
}

.second_list_item {
  margin-top: 30px;
  flex: 1;
  min-width: 250px;
  height: 280px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #ffffff;
  /* box-shadow: 0px 2px 3px 0px rgba(181, 194, 204, 0.3); */
  /* border-radius: 4px; */
  border: 1px solid #dedede;
  margin-left: 20px;
  cursor: pointer;
}

.second_list :first-child {
  margin-left: 0;
}
.second_list_item:nth-child(4) {
  /* margin-right: 0px; */
}
.second_list_item:nth-child(5) {
  margin-left: 0px;
  /* margin-right: 0px; */
}
.second_list_item:nth-child(6) {
  /* margin-left: 20px; */
  /* margin-right: 0px; */
}
.second_list_item:nth-child(9) {
  margin-left: 0px;
  /* margin-left: 20px;
  margin-right: 0px; */
}
/* .second_list :last-child {
  margin-left: 20px;
  margin-right: 0px;
} */
.second_list_item:hover {
  background: #40a9ff38;
}
.second_list_item img {
  width: 88px;
  height: 88px;
  margin-top: 40px;
}
.second_list_item_title {
  height: 18px;
  font-size: 18px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #4a4a4a;
  line-height: 18px;
  text-align: center;
  margin-top: 25px;
}
.second_list_item_detail {
  width: 200px;
  height: 42px;
  font-size: 14px;
  font-family: PingFangSC-Light, PingFang SC;
  font-weight: 300;
  color: #9b9b9b;
  line-height: 21px;
  text-align: center;
  /*text-indent: 2em;*/
  margin-top: 20px;
}
.el-badge__content {
  position: relative;
  font-size: 14px;
  border-radius: 50%;
  width: 20px;
  height: 30px;
  line-height: 30px;
  position: relative;
  left: 110px;
  top: 10px;
}
.content .table {
}
.content >>> .el-table thead {
  color: #606266 !important;
}

.content >>> .el-table td {
  border-bottom: 1px dashed #dedede;
  cursor: pointer;
}
.content >>> .el-table tbody tr:hover > td {
  background-color: #fff !important;
  color: #0091ff;
}
.tabsClass {
  display: flex;
}
.activepane {
  border-bottom: 2px solid #006cff;
  display: flex;
  align-items: center;
}
.divTop {
  display: flex;
  justify-content: space-between;
}
.el-table thead {
  color: #000;
}

.title2 {
  /* height: 24px; */
  font-size: 20px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  color: #4a4a4a;
  /* line-height: 50px; */
  height: 24px;
  font-size: 22px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  color: #4a4a4a;
  line-height: 24px;
}
.el-row > div {
  padding-right: 12px;
  cursor: pointer;
}
#blueDiv .title2 {
  color: #006cff;
  font-weight: bolder;
  padding: 3px;
}
.pane {
  /* padding-bottom: 24px; */
  /* height: 50px; */
  /* display: inline-block; */
  /* margin-bottom: 10px; */
  /* margin-left: 10px; */
}
.titleImg {
  width: 24px;
  height: 24px;
  margin-right: 10px;
}
.tablecssssss {
  padding-left: 15px;
  padding-right: 15px;
  padding-bottom: 15px;
  padding-top: 8px;
  border: 1px solid #dedede;
}
.tablecssssss >>> .el-table thead {
  /* display: none; */
  background: rgb(0, 145, 255);
  /*color: #000 !important;*/
}
.tableClass {
  /* margin-top: 20px; */
}
.divInput {
  display: flex;
  width: 470px;
  height: 40px;
  align-items: center;
  margin-top: 15px;
  margin-bottom: 20px;
}
.divInput >>> .el-input__inner {
  width: 400px !important;
  border-radius: 0px !important;
}
.right {
  text-align: right;
  height: 14px;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #9b9b9b;
  line-height: 14px;
  padding-top: 28px;
  padding-bottom: 34px;
  cursor: pointer;
  flex: 1;
  border-bottom: 2px solid rgb(222, 222, 222);
}
</style>