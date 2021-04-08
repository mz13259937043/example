<template>
  <div class="myyw">
    <Header></Header>
    <NavBar></NavBar>
    <div class="nav-title">
      <div>当前所在位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我的项目</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container myyw-content">
      <div
        style="
          display: inline-block;
          border-right: 1px solid#e4e7ed;
          border-left: 1px solid#e4e7ed;
          border-top: 1px solid#e4e7ed;
        "
      >
        <div
          id="wbdiv"
          class="item wbdiv"
          @click="wbdivclick"
          style="display: inline-block"
        >
          我的项目
        </div>
        <!--<div
          id="ybdiv"
          class="item"
          @click="ybdivclick"
          style="display: inline-block"
        >
          进度查询
        </div>-->
        <div
          v-if="ischdw"
          id="bmjldiv"
          class="item"
          @click="jldivclick"
          style="display: inline-block"
        >
          报名记录
        </div>
        <!--<div
          v-if="ischdw"
          id="cxjldiv"
          class="item"
          @click="jldivclick"
          style="display: inline-block"
        >
          撤销记录
        </div>-->
      </div>
      <el-input
        placeholder="请输入内容"
        style="float: right; margin-bottom: 20px; border-radius: 0 !important"
        v-model="listparams.serachStr"
        class="input-with-select input"
      >
        <el-button
          slot="append"
          class="btn"
          style="border-radius: 0"
          @click="search()"
          >搜索</el-button
        >
      </el-input>
      <el-button
        slot="append"
        class="btn"
        style="border-radius: 0; visibility: hidden"
        @click="tosendbook()"
        >发送通知书</el-button
      >
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        v-show="show"
        style="width: 100%"
        :row-style="{ cursor: 'pointer' }"
        :data="tableData.projectList"
        @row-click="toDetailProject"
        border
        stripe
        :header-cell-style="{ background: '#0091ff' }"
      >
        <el-table-column prop="XMMC" label="项目名称" align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMMC)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="FBXXCHJD2"
          label="测绘阶段"
          align="center"
          width="150"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.FBXXCHJD2)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="LOGDQHJ2"
          label="当前环节"
          align="center"
          width="120"
          v-if="listZT == '待办'"
        >
          <template slot-scope="scope">
            <span
              size="medium"
              v-if="scope.row.STAGE === '成果'"
              v-html="changeColor(scope.row.LOGDQHJ2)"
            ></span>
            <span
              size="medium"
              v-else-if="scope.row.LOGDQHJ2 !== scope.row.dqhj"
              v-html="changeColor(scope.row.dqhj)"
            ></span>
            <span
              size="medium"
              v-else
              v-html="changeColor(scope.row.LOGDQHJ2)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="WTDW"
          label="采购单位"
          align="center"
          width="250"
          v-if="role !== 'yzdw'"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.WTDW)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="HTXXCHDWMC"
          label="测绘单位"
          align="center"
          width="250"
          v-if="role !== 'chdw'"
        >
          <template slot-scope="scope">
            <span
              size="medium"
              v-html="changeColor(scope.row.HTXXCHDWMC)"
            ></span>
          </template>
        </el-table-column>

        <!--<el-table-column
          prop="LOGDQHJ"
          label="当前环节"
          align="center"
          width="120"
          v-if="listZT == '待办'"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.LOGDQHJ)"></span>
            &lt;!&ndash;<span size="medium" v-if="scope.row.STATE=== '成果'" v-html="changeColor(scope.row.LOGDQHJ)"></span>
            <span size="medium" v-else-if="scope.row.LOGDQHJ !== scope.row.dqhj" v-html="changeColor(scope.row.dqhj)"></span>
            <span size="medium" v-else v-html="changeColor(scope.row.LOGDQHJ)"></span>&ndash;&gt;
            &lt;!&ndash;            <span size="medium" v-html="changeColor(scope.row.dqhj)"></span>&ndash;&gt;
          </template>
        </el-table-column>-->

        <el-table-column
          prop="JSSJ"
          label="接收时间"
          align="center"
          width="100"
          :formatter="formatter"
          format="yyyy-MM-dd HH:mm:ss"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.JSSJ)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="LOGCLZT" label="状态" align="center" width="80">
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
        <el-table-column
          prop="operate"
          label="操作"
          align="center"
          width="80"
          v-if="role === 'yzdw'"
        >
          <template slot-scope="scope">
            <el-button
              v-if="
                scope.row.STAGE === '成果' &&
                role === 'yzdw' &&
                scope.row.LOGDQHJ2 !== '已办结'
              "
              class="btn"
              type="small"
              @click="handleFinish(scope.$index, scope.row)"
              >办结</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-table
        v-show="show2"
        :data="tableData2"
        border
        style="width: 100%; cursor: pointer"
        :row-style="{ color: '#000' }"
        @cell-click="gotowtggdetail2"
        :header-cell-style="{
          fontWeight: 'bold',
          fontSize: '15px',
          background: '#0091ff',
        }"
      >
        <el-table-column
          prop="XMMC"
          label="项目名称"
          align="center"
          label-class-name="zt"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMMC)"></span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="XMDZ" label="项目地址" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMDZ)"></span>
          </template>
        </el-table-column> -->
        <el-table-column
          prop="CHJD"
          label="测绘阶段"
          align="center"
          min-width="60"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.CHJD)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="SQYXLXR"
          label="报名联系人"
          align="center"
          width="70"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.SQYXLXR)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="YSJE"
          :label="'预算金额（元）'"
          align="center"
          width="110"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.YSJE)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="SQYXXMBJ"
          :label="'项目报价（元）'"
          align="center"
          width="110"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.SQYXXMBJ)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="XMWCSX"
          :label="'完成时限（天）'"
          align="center"
          width="110"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMWCSX)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="WTDW" label="采购单位" align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.WTDW)"></span>
          </template>
        </el-table-column>
        <!--<el-table-column
          prop="BMKSSJ"
          label="发布时间"
          align="center"
          width="150"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.BMKSSJ)"></span>
          </template>
        </el-table-column>-->
        <el-table-column
          prop="BMJSSJ"
          label="截止时间"
          align="center"
          width="100"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.BMJSSJ)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="operate" label="状态" align="center" width="95">
          <template slot-scope="scope">
            <span v-if="scope.row.STATE == '废标'">已废标</span>
            <span v-else-if="scope.row.SQYXWTQR == 'X'">已撤销</span>
            <span v-else-if="scope.row.SQYXWTQR.indexOf('Y') != -1"
              >已中选</span
            >
            <span v-else-if="scope.row.SQYXWTQR.indexOf('N') != -1"
              >未中选</span
            >
            <span v-else-if="scope.row.SQYXWTQR == 'M'">未选取</span>
            <!--<el-tooltip :content="scope.row.msg" >
              <el-button :type="scope.row.buttonType" :disabled="false" @click="handleShenQing(scope.row,scope.$index,$event)">申请</el-button>
            </el-tooltip>-->
            <!--            <el-button class="btn1" @click="handleShenQing(scope.row,$event)">申请</el-button>-->
          </template>
        </el-table-column>
        <el-table-column
          prop="operate"
          label="操作"
          align="center"
          width="95"
          v-if="wtqr !== 'X'"
        >
          <template slot-scope="scope">
            <div>
              <el-button
                v-if="scope.row.SQYXWTQR == 'X' && scope.row.STEP === 1"
                @click="toModify(scope.$index, scope.row, $event)"
                class="btn"
                type="small"
                >修改</el-button
              >
              <!--修改提交信息-->
            </div>
            <div>
              <el-button
                v-if="scope.row.SQYXWTQR == 'M'"
                @click="openYzmdiag(scope.$index, scope.row, $event)"
                class="btn"
                type="small"
                >撤回</el-button
              >
              <!--撤回提交信息-->
            </div>
          </template>
        </el-table-column>
      </el-table>
<!--      :title="'点击获取短信验证码，系统将发送一条短信到'+this.telphone.replace(/(\d{3})\d{4}(\d{4})/,'$1****$2')"-->
      <el-dialog
        ref="eld"
        :title="'点击获取短信验证码，系统将发送一条短信到'+this.telphone.replace(/(\d{3})\d{4}(\d{4})/,'$1****$2')"
        :visible.sync="yzmdialog.visible"
        :before-close="yzmdialogClose"
        width="30%"
      >
        <el-input
          class="yzminput"
          placeholder="请输入手机上接收到的验证码"
          v-model="yzmdialog.yzmcode"
        >
        </el-input>
        <el-button @click="togetValCode" :disabled="yzmdialog.btnuse">
          获取短信验证码
          <span v-show="yzmdialog.countdown"
            >(仅剩 {{ yzmdialog.countdown }} 秒)</span
          >
        </el-button>
        <span slot="footer" class="dialog-footer">
          <el-button @click="yzmdialogClose">取 消</el-button>
          <el-button type="primary" @click="toRevokeSqyx(yzmdialog.yzmcode)"
            >确 定</el-button
          >
          <!--触发撤销-->
        </span>
      </el-dialog>
      <Apply
        :propInfo="{
          pid: $store.state.applyArg.pid,
          visible: $store.state.applyArg.visible,
          sqyxid: $store.state.applyArg.sqyxid,
        }"
        v-if="$store.state.applyArg.visible"
      ></Apply>
      <el-pagination
        v-if="tableData.pageInfo.total > 9 && show"
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="tableData.pageInfo.currentPage"
        :page-sizes="[10, 20]"
        :page-size="tableData.pageInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.pageInfo.total"
      >
      </el-pagination>
      <el-pagination
        background
        v-if="page.total > 9 && show2"
        @current-change="handleCurr"
        @size-change="handleSize"
        :current-page="page.pageIndex"
        :page-size="page.pageSize"
        layout="total,sizes, prev, pager, next, jumper"
        :total="page.total"
        :page-sizes="[10, 20]"
        style="margin-bottom: 20px; text-align: center"
      ></el-pagination>
    </div>
    <div class="bszn" @click="bsznClick">办事<br />指南</div>
    <Dropdown :propInfo="{ name: '.myyw', path: 'index' }"></Dropdown>
    <Bottom></Bottom>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Bottom from "@/components/Bottom.vue";
import NavBar from "@/components/NavBar.vue";
import Dropdown from "@/components/dropdown.vue";
import {
  formatDate,
  getValCode,
  doMessage,
  vilidateCode,
  downloadFile,
  sendMessage,
  guid,
} from "@/assets/js/common.js";
import {
  attachmentUpload,
  templateName,
  callService,
} from "@/assets/js/config.js";
import Apply from "@/components/FbxxApply.vue";

export default {
  data() {
    return {
      yzmdialog: {
        visible: false,
        yzmcode: "",
        btnuse: false,
        countdown: "",
      },
      listparams: {
        wtdwid: "1231231231231",
        pageNum: "1",
        pageSize: "1",
        serachStr: "",
      },
      total: 0,
      tableData: {
        projectList: [],
        pageInfo: {
          currentPage: 1,
          pageSize: 10,
          total: 25,
        },
      },
      tableData2: [],
      page: {
        total: null,
        pageIndex: 1,
        pageSize: 10,
        str: null,
      },
      loading: true,
      radio: 3,
      xmid: "",
      // 业务大阶段
      ywdjd: "",
      // 业务事项
      ywsx: "",

      // 用户个人信息
      userinfo: "",
      // 列表状态  初始默认为待办
      listZT: "待办",
      show: true,
      show2: false,
      ischdw: false,
      role: "",
      applyShow: false,
      wtqr: "",
      validiatecode: "",
      telphone: "",   //电话
    };
  },
  components: {
    Header,
    Bottom,
    NavBar,
    Apply,
    Dropdown,
  },
  methods: {
    // 办结项目
    async handleFinish(value1, value2) {
      /*
      没办完的、没开始的都不可以办结
       */
      debugger;
      let param = {
        pid: value2.FBXXID,
        pageNum: 1,
        pageSize: 100,
      };
      let banjie = true;
      let msg = "";
      await this.$http.post("/dchy/dchyYwxx/getYwxxList", param).then((res) => {
        // 只要有没办完的都不允许办结
        if (res.data.data && res.data.data.length > 0) {
          for (let item of res.data.data) {
            if (item.cgsfsh !== "已通过") {
              banjie = false;
              // msg = "该业务还有未办结的事项";
              msg = "该项目还有未完成的成果验收";
              break;
            }
          }
        } else if (res.data.data && res.data.data.length == 0) {
          banjie = false;
          msg = "该项目未进行成果汇交";
        }
      });
      if (!banjie) {
        return doMessage(msg, false);
      }
      // 办结操作
      this.$confirm("确认所有成果都已验收合格，并办结该事项?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          var fbxxid = value2.FBXXID;
          let param = {
            id: fbxxid,
            xmzt: "已办结",
          };
          this.$http.post("/xmwt/dchyFbxx/renewFbxx", param).then((res) => {
            if (res.data.data) {
              for (let index = 0; index < this.tableData.length; index++) {
                // 将该项变为已办结
              }
              doMessage("办结成果！", true);
              this.reloadTableData(); //再查一次
            }
          });
        })
        .catch(() => {});
    },
    // 办事指南click
    bsznClick() {
      this.$router.push({
        name: "zcfgdetail",
        query: {
          item:
            (this.ruleForm && this.ruleForm.jbxx && this.ruleForm.jbxx.chsx) ||
            "多测合一总指南",
        },
      });
    },
    tosendbook() {
      let now = new Date();
      let nowArr = now.toLocaleDateString().split("/");
      let date = nowArr[0] + "年" + nowArr[1] + "月" + nowArr[2] + "日";
      let param = {
        downloadPath: `${attachmentUpload.mbwj}`,
        downloadName: `${templateName.cgshjls}.ftl`,
        uploadPath: `${attachmentUpload.xmtz}/${guid()}`,
        uploadName: `${templateName.cgshjls}.pdf`,
        userid: this.userinfo.id,
        username: this.userinfo.id,
        htxxid: "dsa",
        downloadType: "成果",
        cgdw: "采购单位", //采购单位
        zxjg: "项目地址", //中选单位--项目地址
        fwmc: "项目名称", //服务名称--项目名称
        xmbh: "项目编号", //项目编号
        fjmc: "分局名称", //项目编号
        zxje: "符合", //中选金额--符合/不符合
        date: date, //日期
      };
      let url = `${callService.downloadTemplate}`;
      downloadFile(url, param);
      let message = `【市资源规划局】  ：您单位提交的位于  的
                  “多测合一”成果审核申请（测绘成果编号： ）已收到。
                经核查，符合“多测合一”相关配套文件要求。`;
      sendMessage(`13259937043`, message);
    },
    /*parLinsten(val){
    },*/
    yzmdialogClose() {
      this.$confirm("是否关闭获取验证码窗口?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.yzmdialog.visible = false;
        this.validiatecode = "";
        this.yzmdialog.countdow = 0;
      });
    },
    togetValCode() {
      this.validiatecode = getValCode([this.telphone,this.xmmc]); //业主联系人电话
      this.yzmdialog.btnuse = true;
      this.yzmdialog.countdown = 60;
    },
    async operValid(val, valid1, valid2) {
      let param1 = {
        pid: val.ID,
        chdwid: this.userinfo.id,
      };
      let res = await this.$http
        .post("xmwt/dchySqyx/getByPidAndChdwid", param1)
        .then((res) => {
          if (res.data.data) {
            let _data = [...res.data.data];
            if (valid1 && _data && _data.length > 0) {
              for (let item of _data) {
                if (item.wtqr && item.wtqr.indexOf("M") != -1) {
                  doMessage("该项目已报名，请先撤销申请重新提交报名！", false);
                  return false;
                }
              }
            }
            if (
              valid2 &&
              new Date(val.BMJSSJ).getTime() < new Date().getTime()
            ) {
              doMessage("已经超过项目截止期限！", false);
              return false;
            }
            return true;
          }
        });
      return res;
    },
    // 打开验证码视图
    async openYzmdiag(ind, val, event) {
      // 验证时间上是否仍可操作
      debugger;
      if ((await this.operValid(val, false, true)) === false) {
        return;
      }
      this.yzmdialog.visible = true;
      this.yzmdialog.btnuse = false; //可用
      this.yzmdialog.yzmcode = "";
      this.yzmdialog.countdown = null;
      this.validiatecode = "";
      this.sqyxid = val.SQYXID;
      this.telphone = val.SQYXLXRDH; //有效的申请人电话
      this.xmmc = val.XMMC; //有效的申请人电话
    },
    //撤销报名
    revokeApply(val) {
      let _sqyxid = this.sqyxid;
      // // 二次确认
      let param = {
        id: _sqyxid,
        wtqr: "X",
      };
      //修改申请记录为X,表示已撤销
      this.$http.post("xmwt/dchySqyx/modifySqyx", param).then((res) => {
        if (res.data.data) {
          doMessage("撤销成功", true);
          this.yzmdialog.visible = false;
          // this.yzmdialogClose();
          this.getJlList();
        } else {
          doMessage("撤销失败", false);
        }
      });
    },
    // 撤销申请意向
    toRevokeSqyx(val) {
      if (val == "") {
        //  触发组件的撤销事件
        doMessage("请输入手机上获取到的验证码！", false);
      } else if (this.validiatecode == "") {
        doMessage("验证码无效，请先获取验证码！", false);
      } else if (this.validiatecode !== val) {
        doMessage("验证码错误", false);
      } else {
        this.revokeApply(val);
      }
    },
    // 修改报名
    async toModify(index, val, event) {
      // 1、查询我企业对该公告是否有已报名的；  2、时间上是否仍可以报名
      debugger;
      let param1 = {
        pid: val.ID,
        chdwid: this.userinfo.id,
      };

      if ((await this.operValid(val, true, true)) === false) {
        return;
      }
      // this.$store.commit("changeApplyArg",{applyShow:true}
      let param = {
        applyArg: {
          pid: val.ID,
          sqyxid: val.SQYXID,
          visible: true,
        },
      };
      debugger;
      this.$store.commit("changeApplyArg", param);
    },
    // 条件搜索
    search() {
      let source = document.getElementById("wbdiv");
      if (source.innerText === "我的项目") {
        this.reloadTableData(this.listparams.serachStr);
      } else {
        this.getJlList();
      }
    },

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
    // 分页操作
    handleSizeChange(val) {
      this.loading = true;
      this.tableData.pageInfo.pageSize = val;
      this.tableData.pageInfo.currentPage = 1;
      this.reloadTableData();
    },
    handleCurrentChange(val) {
      this.loading = true;
      this.tableData.pageInfo.currentPage = val;
      this.reloadTableData();
    },
    reloadTableData(data) {
      debugger;
      if (this.userinfo) {
        let param = {
          jsrid: this.userinfo.id,
          clzt: "已办",
          searchStr: this.listparams.serachStr,
          pageNum: this.tableData.pageInfo.currentPage,
          pageSize: this.tableData.pageInfo.pageSize,
          ...data,
        };
        // 我的业务，采购单位的待办列表
        this.$http.post("dchy/dchyLog/getDealingList", param).then((res) => {
          // res.data.data.sort(this.compare("JSSJ"));
          let _resData = [...res.data.data];
          for (let i = 0; i < _resData.length; i++) {
            debugger;
            //  测绘阶段
            if (this.processList) {
              /*for(let item of this.chsxs){
                if (item.id === _resData[i].FBXXCHJD2){
                  _resData[i].FBXXCHJD2 = item.processAlias;
                }
              }*/
              let a = _.filter(this.processList, (item) => {
                return item.id === _resData[i].FBXXCHJD;
              });
              if (a.length > 0) {
                _resData[i].FBXXCHJD2 = a[0].processAlias;
              }
            }
            if (_resData[i].ywxxLastLog) {
              _resData[i].dqhj = _resData[i].ywxxLastLog.dqhj;
            } else if (_resData[i].fbxxLastLog) {
              _resData[i].dqhj = _resData[i].fbxxLastLog.dqhj;
            } else {
              _resData[i].dqhj = "";
            }
            // 对于成果阶段部分，当前环节自定义 成果确认、成果汇交、已办结
            // 首先得先进入成果环节
            if (_resData[i].STAGE === "成果") {
              switch (this.role) {
                case "yzdw":
                  if (_resData[i].FBXXXMZT === "已办结") {
                    _resData[i].LOGDQHJ2 = "已办结";
                  } else {
                    _resData[i].LOGDQHJ2 = "成果验收";
                  }
                  break;
                case "chdw":
                  if (_resData[i].FBXXXMZT === "已办结") {
                    _resData[i].LOGDQHJ2 = "已办结";
                  } else {
                    _resData[i].LOGDQHJ2 = "成果汇交";
                  }
                  break;
              }
            }
            if (_resData[i].JSSJ) {
              _resData[i].JSSJ = this.$moment(_resData[i].JSSJ).format(
                "YYYY-MM-DD HH:mm:ss"
              );
            }
          }
          this.tableData.projectList = _resData;
          this.tableData.pageInfo.total = res.data.count;
          this.loading = false;
        });
      }
    },
    // compare(property) {  // 根据某字段的值排序的方法
    //   return function (a, b) {
    //     var value1 = new Date(a[property]);
    //     var value2 = new Date(b[property]);
    //     return value2 - value1;
    //   };
    // },
    // 点击进入项目，准备确认事情
    toDetailProject(val, column) {
      if (column.property === "operate") {
        return;
      }
      // 跳转那个页面的标识
      var toywxqym = false;
      if (
        val.LOGDQHJ == "项目发布" ||
        val.LOGDQHJ == "采购确认" ||
        val.LOGDQHJ == "合同上传" ||
        val.LOGDQHJ == "合同确认"
      ) {
        toywxqym = false;
      } else {
        this.$store.commit("changeywxqym", val);

        toywxqym = true;
      }
      this.listZT = val.LOGCLZT;

      if (val.LOGDQHJ == "合同上传") {
        window.localStorage.setItem("xzfjid", val.HTXXHTWJ);
      }
      window.localStorage.setItem("listZT", this.listZT);
      if (this.listZT == "已办") {
        var obj = JSON.stringify(val);
        window.localStorage.setItem("ybListItem", obj);
      } else {
        // // console.log(val);
        // if (val.LOGDQHJ == "成果审核") {
        //   toywxqym = true;
        //   var obj = JSON.stringify(val);
        //   window.localStorage.setItem("cgsh", obj);
        //   window.localStorage.setItem("xzfjid", val.YWXXCGHJFJ);
        // }
        // if (val.LOGDQHJ == "成果验收") {
        //   toywxqym = true;
        //   var obj = JSON.stringify(val);
        //   window.localStorage.setItem("cgys", obj);
        //   window.localStorage.setItem("xzfjid", val.YWXXCGHJFJ);
        // }
        // if (val.LOGDQHJ == "成果负责人确认") {
        //   toywxqym = true;
        //   var obj = JSON.stringify(val);
        //   window.localStorage.setItem("cgqrItem", obj);
        // }
        // if (val.LOGDQHJ == "成果汇交") {
        //   toywxqym = true;
        //   var obj = JSON.stringify(val);
        //   window.localStorage.setItem("ywdqhjall", obj);
        //   if (val.YWXXCGHJFJ) {
        //     window.localStorage.setItem("xzfjid", val.YWXXCGHJFJ);
        //   }
        //   if (val.YWXXSSFJ) {
        //     window.localStorage.setItem("xzfjids", val.YWXXSSFJ);
        //   }
        // }
        // if (val.LOGDQHJ == "测绘实施") {
        //   if (val.YWXXSSFJ) {
        //     window.localStorage.setItem("xzfjids", val.YWXXSSFJ);
        //   }
        // }
      }

      debugger;
      if (toywxqym) {
        this.$router.push({
          path: "ywxqym",
        });
      } else {
        this.$router.push({
          path: "xmfb/xxfb",
          query: {
            pid: val.FBXXID, //发布信息id
            fsrxm: val.LOGFSRXM, //谁发的日志
            fsrid: val.LOGFSRID,
            lastLogId: val.LOGID, //那个日志
            htxxId: val.HTXXID, //那个日志
            dqhj: this.listZT == "待办" ? val.LOGDQHJ : val.dqhj, //当前环节
          },
        });
      }
    },
    gotowtggdetail2(val, column) {
      // const { href } = this.$router.resolve({
      //   name: "wtggdetail",
      //   query: {
      //     pid: val.ID,
      //     wtqr: val.SQYXWTQR,
      //     flag: 1,
      //   },
      // });

      /*if (event){
        if(event.target.innerText === '修改'){
          return;
        }
      }*/
      if (column.property !== "operate") {
        this.$router.push({
          name: "wtggdetail",
          query: {
            pid: val.ID,
            wtqr: val.SQYXWTQR,
            flag: 1,
          },
        });
      }
    },
    // 待办记录
    wbdivclick() {
      this.loading = true;
      this.show = true;
      this.show2 = false;
      // document.getElementById("ybdiv").className = "item";
      if (this.ischdw) {
        document.getElementById("bmjldiv").className = "item";
        // document.getElementById("cxjldiv").className = "item";
      }

      document.getElementById("wbdiv").className = "ybdiv";
      this.listZT = "待办";
      this.tableData.pageInfo.currentPage = 1;
      this.reloadTableData(this.listparams.serachStr);
    },
    ybdivclick(data) {
      debugger;
      this.loading = true;
      this.show = true;
      this.show2 = false;
      // document.getElementById("ybdiv").className = "ybdiv";
      document.getElementById("wbdiv").className = "item";
      if (this.ischdw) {
        document.getElementById("bmjldiv").className = "item";
        document.getElementById("cxjldiv").className = "item";
      }
      this.listZT = "已办";
      this.tableData.pageInfo.currentPage = 1;
      this.reloadTableData(this.listparams.serachStr);
    },
    jldivclick(event) {
      debugger;
      // 已中选、未中选、已报名、已撤销
      let el = event.currentTarget;
      if (el.innerText === "报名记录") {
        this.wtqr = "";
        document.getElementById("bmjldiv").className = "ybdiv";
        // document.getElementById("cxjldiv").className = "item";
      } else {
        this.wtqr = "X";
        // document.getElementById("cxjldiv").className = "ybdiv";
        document.getElementById("bmjldiv").className = "item";
      }
      this.loading = true;
      this.show = false;
      this.show2 = true;
      // document.getElementById("ybdiv").className = "item";
      document.getElementById("wbdiv").className = "item";
      this.page.pageIndex = 1;
      this.getJlList();
    },
    handleCurr(page) {
      this.loading = true;
      this.page.pageIndex = page;
      this.getJlList();
    },
    handleSize(val) {
      this.loading = true;
      this.page.pageSize = val;
      this.page.pageIndex = 1;
      this.getJlList();
    },
    getJlList() {
      // 报名记录/撤销记录
      this.tableData2 = [];
      let params = {
        pageIndex: this.page.pageIndex,
        pageSize: this.page.pageSize,
        str: this.listparams.serachStr,
        did: this.userinfo.id,
        wtqr: this.wtqr,
      };
      let userinfo = this.userinfo;
      this.$http.post("xmwt/dchyFbxx/getFbxxGgByDid", params).then(
        (res) => {
          let resData = "";
          // 日期转换
          debugger;
          if (res.data.data) {
            resData = res.data.data;
            let count = 0; //计数看for循环的次数
            let param = {
              fbxxid: "",
              step: 0, //标识该条所属时间阶段
            };
            for (let i = 0; i < resData.length; i++) {
              resData[i].BMKSSJ = this.$moment(resData[i].BMKSSJ).format(
                "YYYY-MM-DD HH:mm:ss"
              );
              resData[i].BMJSSJ = this.$moment(resData[i].BMJSSJ).format(
                "YYYY-MM-DD HH:mm:ss"
              );
              //测绘阶段
              if (this.processList) {
                let a = _.filter(this.processList, (item) => {
                  return item.id === resData[i].CHJD;
                });
                if (a.length > 0) {
                  resData[i].CHJD = a[0].processAlias;
                }
              }
              //判断该申请的状态，是否公告已经选取
              count++; //仅仅计数
              if (resData[i].ID === param.fbxxid) {
                resData[i].STEP = param.step;
                continue;
              }
              param.fbxxid = resData[i].ID;
              for (let j = 0; j < resData.length; j++) {
                count++;
                if (
                  resData[j].ID === param.fbxxid &&
                  (resData[j].SQYXWTQR.indexOf("Y") != -1 ||
                    resData[j].SQYXWTQR.indexOf("N") != -1)
                ) {
                  param.step = 2;
                  break;
                } else {
                  param.step = 1;
                }
              }
              resData[i].STEP = param.step;
            }
            // alert(count);
          }

          this.tableData2 = resData;
          this.page.total = res.data.count;
          this.loading = false;
        },
        (err) => {
          console.log(err);
        }
      );
    },
    // 渲染表头换行
    renderHeader(h, para, row1, row2) {
      debugger;
      return h("div", {
        attrs: {
          class: "cell",
        },
        domProps: {
          innerHTML: `${row1}</br>${row2}`,
        },
      });
    },
    //关键字标色
    changeColor(val) {
      if (!val) {
        return "";
      }
      let tags = this.listparams.serachStr;
      if (tags !== null && tags !== "" && val !== null && val !== "") {
        let reg = new RegExp("(" + tags + ")", "g");
        return val.replace(reg, "<font style='color:red'>$1</font>");
      } else {
        return val;
      }
    },
  },
  async created() {
    // 先获取个人信息
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    } else {
      console.log("我的业务没有获得用户信息");
    }
    if (window.localStorage.getItem("processList")) {
      this.processList = JSON.parse(window.localStorage.getItem("processList"));
    }
    if (window.localStorage.getItem("userRole")) {
      this.role = window.localStorage.getItem("userRole");
      if (this.role == "chdw") {
        this.ischdw = true;
      }
    }
    debugger;
    this.reloadTableData();
  },
  watch: {
    "yzmdialog.countdown": {
      handler() {
        let _this = this;
        if (_this.yzmdialog.countdown === 0) {
          this.yzmdialog.btnuse = false;
          this.validiatecode = "";
        }

        if (_this.yzmdialog.countdown > 0) {
          setTimeout(function () {
            if (_this.yzmdialog.countdown > 0) _this.yzmdialog.countdown--;
          }, 1000);
        }
      },
    },
    "$store.state.applyArg.visible": {
      //dia组件消失触发
      handler() {
        let _this = this;
        if (!_this.$store.state.applyArg.visible) {
          this.getJlList();
        }
      },
    },
  },
};
</script>

<style  scoped>
.myyw {
  height: 100vh;
  overflow-x: hidden;
}
.bszn {
  width: 50px;
  height: 50px;
  position: fixed;
  bottom: 270px;
  right: 170px;
  color: #fff;
  text-align: center;
  line-height: 25px;
  font-size: 16px;
  background: #66b1ff;
}
.bszn:hover {
  cursor: pointer;
}
.el-table .cell {
  white-space: pre-line;
}
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
  border-color: red;
  border: 1px solid #626262;
  color: #000;
  outline: 0;
}

.cz-btn:hover {
  background-color: #f3f3f3;
}

.container {
  padding: 0 385px;
  height: auto;
  min-height: 572px;
}

.container .el-input {
  width: 40% !important;
}
.el-table {
  width: 100% !important;

  margin-top: 20px;
}
.el-pagination {
  margin-bottom: 15px;
  display: flex;
  justify-content: center;
}
.wdyw-content {
  width: 100%;
  height: auto;
  padding: 0 20.05% 100px 20.05%;
}
.item {
  height: 40px;
  line-height: 40px;
  color: #909399;
  padding: 0 15px;
  border: 1px solid#e4e7ed;
  background-color: rgba(0, 0, 0, 0.1);
}
.item:hover {
  color: #409eff;
  cursor: pointer;
}
.wbdiv {
  height: 40px;
  line-height: 40px;
  padding: 0 15px;
  color: #409eff;
  background-color: #fff;
  border-right: 0px;
  border-bottom: 0px;
  border-bottom: 2px solid #006cff;
}
.ybdiv {
  height: 40px;
  line-height: 40px;
  padding: 0 15px;
  color: #409eff;
  background-color: #fff;
  border-left: 0px;
  border-bottom: 0px;
  border-bottom: 2px solid #006cff;
}
.jldiv {
  height: 40px;
  line-height: 40px;
  padding: 0 15px;
  color: #409eff;
  background-color: #fff;
  border-left: 0px;
  border-bottom: 0px;
  border-bottom: 2px solid #006cff;
}
.myyw-content >>> .el-input__inner {
  border-radius: 0;
}
.btn {
  color: #66b1ff !important;
  border: 1px solid #66b1ff !important;
  background: #fff !important;
}
.btn:hover {
  color: #fff !important;
  border: 1px solid #66b1ff;
  background: #006cff !important;
}
</style>