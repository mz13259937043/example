<template>
  <div>
    <Header></Header>
    <Navbar></Navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>采购公告</el-breadcrumb-item>
        <el-breadcrumb-item>采购公告详情</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体 -->
    <div class="wtggdetail-content">
      <div style="overflow: hidden">
        <div class="global-sxtitle1">
          提示！报名结束时间：{{ formData.bmjssj }}已有
          {{ formData.sqsl }} 家测绘单位报名
        </div>
        <div v-if="flag != 1">
          <el-button v-if="wybm === '我要报名'" class="btn" @click="openApply"
            >我要报名</el-button
          >
          <el-button
            v-else
            style="background-color: #dcdfe6; color: white"
            disabled
            >{{ wybm }}</el-button
          >
        </div>
        <div v-if="flag == 1">
          <el-button v-if="wtqr == 'Y'" class="btn" disabled>已中选</el-button>
          <el-button v-else-if="wtqr == 'N'" class="btn" disabled
            >未中选</el-button
          >
          <el-button v-else-if="wtqr == 'X'" class="btn" disabled
            >已撤销</el-button
          >
          <el-button v-else class="btn" disabled>已报名</el-button>
        </div>
      </div>
      <el-card style="border: 2px solid #ccc; background: #efefea42">
        <div class="companyTitle">
          关于为【{{ formData.wtdw }}】公开选取{{
            formData.chjd.replace("测量", "服务")
          }}机构的公告
        </div>
        <div style="text-align: center; padding-right: 10px; margin: 10px 0">
          发布时间：{{ formData.cjsj }}
        </div>
        <table
          style="width: 100%; border-color: #ccc; border-collapse: collapse"
          v-loading="loading"
          element-loading-text="拼命加载中"
          cellspacing="0"
          element-loading-spinner="el-icon-loading"
        >
          <tr>
            <td class="td1">项目名称:</td>
            <td class="td2">{{ formData.xmmc }}</td>
          </tr>
          <tr>
            <td class="td1">采购单位:</td>
            <td class="td2">{{ formData.wtdw }}</td>
          </tr>
          <tr>
            <td class="td1">预算金额/元:</td>
            <td class="td2">{{ formData.ysje }}</td>
          </tr>
          <!-- <tr>
            <td class="td1">资金来源:</td>
            <td class="td2">{{ formData.zjly }}</td>
          </tr> -->
          <tr>
            <td class="td1">联系人:</td>
            <td class="td2">{{ formData.yzdwlxr }}</td>
          </tr>
          <tr>
            <td class="td1">联系电话:</td>
            <td class="td2">{{ formData.yzdwlxrdh }}</td>
          </tr>
          <tr>
            <td class="td1">项目地址:</td>
            <td class="td2">{{ formData.xmdz }}</td>
          </tr>
          <!--<tr>
            <td class="td1">地籍编号:</td>
            <td class="td2">{{formData.djbh}}</td>
          </tr>-->
          <tr>
            <td class="td1">测绘阶段:</td>
            <td class="td2">{{ formData.chjd }}</td>
          </tr>
          <tr>
            <td class="td1">最低资质等级:</td>
            <td class="td2">{{ formData.zzdj }}</td>
          </tr>
          <!--<tr>
            <td class="td1">要求服务质量指数:</td>
            <td class="td2">{{formData.yqxydj}}</td>
          </tr>
          <tr>
            <td class="td1">交易方式:</td>
            <td class="td2">{{formData.jyfs}}</td>
          </tr>-->

          <!-- <tr>
            <td class="td1">立项依据:</td>
            <td class="td2">
              <el-input
                type="textarea"
                v-model="formData.lxyj"
                readonly
                resize="none"
                autosize
              />
            </td>
          </tr> -->
          <tr>
            <td class="td1">服务内容:</td>
            <td class="td2">{{ formData.fwnr }}</td>
          </tr>
          <tr>
            <td class="td1">完成时限/天:</td>
            <td class="td2">{{ formData.xmwcsx }}</td>
          </tr>
          <tr>
            <td class="td1">报名开始时间:</td>
            <td class="td2">{{ formData.bmkssj }}</td>
          </tr>
          <tr>
            <td class="td1">报名结束时间:</td>
            <td class="td2">{{ formData.bmjssj }}</td>
          </tr>
          <tr>
            <td class="td1">附加条件:</td>
            <td class="td2">
              <el-input
                type="textarea"
                v-model="formData.fjtj"
                readonly
                resize="none"
                autosize
              />
            </td>
          </tr>
          <tr>
            <td class="td1">备注:</td>
            <td class="td2">
              <el-input
                type="textarea"
                v-model="formData.bz"
                readonly
                resize="none"
                autosize
              />
            </td>
          </tr>
        </table>
        <div style="margin-top: 10px">
          提示：如对该公告内容存在异议可致电西安市“多测合一”信息平台管理部门
        </div>
      </el-card>
      <Apply
        :propInfo="{
          pid: $store.state.applyArg.pid,
          visible: $store.state.applyArg.visible,
          sqyxid: $store.state.applyArg.sqyxid,
        }"
        v-if="$store.state.applyArg.visible"
      ></Apply>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Bottom from "@/components/Bottom.vue";
import Navbar from "@/components/NavBar.vue";
import Apply from "@/components/FbxxApply.vue";
import { formatDate, closePage, doMessage,parseFwnr } from "@/assets/js/common.js";
const rules = {
  lxr: [{ required: true, message: "不能为空", trigger: "blur" }],
  lxrdh: [
    { required: true, message: "不能为空", trigger: "blur" },
    { pattern: /^1[3456789]\d{9}$/, message: "请输入正确的手机号" },
  ],
  xmbj: [
    { required: true, message: "不能为空", trigger: "blur" },
    {
      pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
      message: "请输入正确的数值",
    },
  ],
};
export default {
  name: "wtggdetail",
  components: {
    Header,
    Bottom,
    Navbar,
    Apply,
  },
  created() {
    //获取项目详情
    this.getDetail();
  },

  data() {
    return {
      wybm: "我要报名",
      rules,
      visible: false,
      userinfo: "",
      isSub: false,
      sqxxForm: {
        chdwid: null, //测绘单位id
        dxxz: null, //是否定向选择
        pid: "", //主项目的id
        xmbj: null, //项目报价
        lxr: "", //联系人
        lxrdh: "", //联系人电话
      },
      pid: this.$route.query.pid,
      flag: this.$route.query.flag,
      wtqr: this.$route.query.wtqr,
      formData: {
        sqsl: null,
        zxdw: "",
        yqzzdj: "",
        bmjssj: "",
        bmkssj: "",
        bz: null,
        chjd: "",
        cjr: null,
        cjrid: null,
        cjsj: null,
        dchybh: null,
        djbh: null,
        fjtj: null,
        fwnr: null,
        htxxList: [],
        id: null,
        jyfs: null,
        logsList: [],
        lxyj: null,
        pageNum: 0,
        pageSize: 0,
        searchStr: null,
        sqyxList: [],
        zxsj: "",
        wtdw: null,
        wtdwid: null,
        xmdz: null,
        xmmc: null,
        xmwcsx: null,
        xmzt: null,
        yqxydj: null,
        ysje: null,
        ywxxList: null,
        yzdwlxr: null,
        yzdwlxrdh: null,
        zjly: null,
        zzdj: null,
      },
      loading: true,
    };
  },
  methods: {
    //点击申请按钮，输入数据提交
    openApply() {
      debugger;
      //    进行资格判定
      if (!window.localStorage.getItem("userinfo")) {
        return doMessage("请登录后使用", false);
      } else if (window.localStorage.getItem("userRole") != "chdw") {
        doMessage("您不是测绘机构，不能报名！", false);
        return false;
      } else if (new Date(this.formData.bmkssj) > new Date()) {
        return doMessage("尚未到报名时间，不能报名！", false);
      }
      let msg = "";
      let userinfo = this.userinfo;
      let item = this.formData;

      debugger;
      if (!userinfo.zzxx.id) {
        msg = "报名失败，请补充测绘资质！";
      } else if (!this.judge(item.zzdj, userinfo.zzxx.zzdj)) {
        // 甲级>乙级>丙级>丁级
        msg = "报名失败，您不符合该项目要求的测绘资质等级！";
      } else if (userinfo.zzxx.state !== "正常") {
        msg = "您的资质信息异常，请联系管理员";
      } else if (userinfo.zzxx.dchyywfw.indexOf(item.chjd) == -1) {
        msg = "报名失败，您不符合该项目要求的“多测合一”业务范围！";
      } else if (this.sfsq(item) !== "我要报名") {
        msg = this.sfsq(item);
      }
      if (msg !== "") {
        return doMessage(msg, false);
      }

      // this.visible = true;
      let param = {
        applyArg: {
          pid: this.pid,
          sqyxid: "",
          visible: true,
        },
      };
      debugger;
      this.$store.commit("changeApplyArg", param);

      event.stopPropagation();
      this.title = "申请信息";
      // this.sqxxForm = {};
    },
    // 判断该单位是否已经申请过
    sfsq(arg) {
      let state = "我要报名";
      if (arg && arg.sqyxList) {
        let sqyxList = [...arg.sqyxList];
        for (let item of sqyxList) {
          if (item.chdwid === this.userinfo.id) {
            if (item.wtqr && item.wtqr.indexOf("Y") != -1) {
              state = "已中选";
              break;
            } else if (item.wtqr && item.wtqr.indexOf("N") != -1) {
              state = "未中选";
              break;
            } else if (item.wtqr && item.wtqr == "X") {
              state = "已撤销";
              break;
            } else if (item.wtqr && item.wtqr == "M") {
              state = "已报名";
              break;
            }
          }
        }
      }
      return state;
    },
    // 甲乙丙丁判定规则
    judge(arg1, arg2) {
      //arg1，要求，arg2自己
      //  甲>乙>丙>丁
      // 不允许    甲乙，甲丙，甲丁  乙丙，乙丁   丙丁
      let judge = (arg1 + arg2).split("级").join("");
      let succ = true;
      switch (judge) {
        case "甲乙":
          succ = false;
          break;
        case "甲丙":
          succ = false;
          break;
        case "甲丁":
          succ = false;
          break;
        case "乙丙":
          succ = false;
          break;
        case "乙丁":
          succ = false;
          break;
        case "丙丁":
          succ = false;
          break;
        default:
          succ = true;
          break;
      }
      return succ;
    },
    submitApply(formName) {
      let isSub = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          isSub = true;
        }
      });

      this.sqxxForm.chdwid = this.userinfo.id;
      this.sqxxForm.pid = this.pid;
      let _this = this;
      // // 数据是否填写完整验证
      // if(!_this.sqxxForm.lxr){
      //     this.$message({
      //       message: "请填写必填信息",
      //       type: "warning",
      //       offset: 200,
      //     });
      //    return
      // }
      // if(!_this.sqxxForm.lxrdh){
      //   this.$message({
      //       message: "请填写必填信息",
      //       type: "warning",
      //       offset: 200,
      //     });
      //    return
      // }
      // if(!_this.sqxxForm.xmbj){
      //   this.$message({
      //       message: "请填写必填信息",
      //       type: "warning",
      //       offset: 200,
      //     });
      //    return
      // }
      if (!isSub) {
        this.$message({
          message: "输入信息格式有误！",
          type: "warning",
          offset: 200,
          duration: 2000,
        });
        return;
      }

      if (_this.title === "申请信息") {
        let obj = _this.sqxxForm; //申请意向对象
        let params = { ...obj };
        this.$http.post("xmwt/dchySqyx/saveSqyx", params).then((res) => {
          if (res.data) {
            // 申请后申请数量+1
            this.formData.sqsl += 1;
            // list 中添加一条 伪数据  避免重复申请
            if (this.formData.sqyxList) {
              this.formData.sqyxList.push({ chdwid: this.userinfo.id });
            } else {
              this.formData.sqyxList = [{ chdwid: this.userinfo.id }];
            }
            doMessage("报名成功！", true);
            location.reload();
          } else {
            doMessage("报名失败！", false);
          }
          _this.visible = false;
        });
      } else {
        _this.visible = false;
      }

      // let param = {
      //   xmbj:this.sqxxForm.bj,
      //   pid:this.pid,
      //   lxr:this.sqxxForm.lxr,
      //   chdwid:'dddd'       //当前用户id
      // };
      // this.$http.post("xmwt/dchySqyx/saveSqyx",param).then((res) => {
      //   console.log(res);
      //   this.visible = false;
      // });
    },

    // 获取项目详情
    async getDetail() {
      debugger
      const _this = this;
      if (window.localStorage.getItem("userinfo")) {
        this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
      }
      if (window.localStorage.getItem("processList")) {
        this.processList = JSON.parse(window.localStorage.getItem("processList"));
      }
      this.$http
        .get("xmwt/dchyFbxx/findAllInfoById?fbxxId=" + this.pid)
        .then(async (res) => {
          if (res.data.data) {
            let wtgg = res.data.data;
            wtgg.bmkssj = _this
              .$moment(wtgg.bmkssj)
              .format("YYYY-MM-DD HH:mm:ss");
            wtgg.bmjssj = _this
              .$moment(wtgg.bmjssj)
              .format("YYYY-MM-DD HH:mm:ss");
            wtgg.cjsj = _this.$moment(wtgg.cjsj).format("YYYY-MM-DD HH:mm:ss");

            // 测绘阶段     卧槽有点复杂
            wtgg.chjd = _.filter(this.processList,item=>{
              return item.id === wtgg.chjd;
            })[0].processAlias;
            // 服务内容
            let fwnr = JSON.parse(wtgg.fwnr);
            let fw = "";
            /*for (let item of fwnr) {
              fw += item + "，";
            }
            wtgg.fwnr = fw.slice(0, fw.length - 1);*/
            let fwnrList = await parseFwnr(fwnr,this.processList);
            _.each(fwnrList,item =>{
              fw += item.processAlias + "，";
            })
            wtgg.fwnr = fw.slice(0, fw.length - 1);

            this.formData = wtgg;
            this.wybm = this.sfsq(this.formData);
            // 多少家申请单位
            this.formData.sqsl = 0;
            if (wtgg.sqyxList && wtgg.sqyxList.length > 0) {
              for (let item2 of wtgg.sqyxList) {
                if (item2.wtqr.indexOf("M") != -1) {
                  this.formData.sqsl += 1;
                }
              }
            }
            let param = {
              pageIndex: 1,
              pageSize: 100,
              id: this.pid,
              str: "",
            };
            return this.$http.post("xmwt/dchySqyx/getSqyxListByFbxxid", param);
          }
        })
        .then((res) => {
          if (res.data.data) {
            this.loading = false;
          }
        });
    },
  },
  watch: {
    "$store.state.applyArg.visible": {
      //dia组件消失触发
      handler() {
        let _this = this;
        if (!_this.$store.state.applyArg.visible) {
          this.getDetail();
        }
      },
    },
  },
};
</script>

<style scoped>
html {
  font-size: 10px;
}

.wtggdetail-content {
  width: 100%;
  height: auto;
  padding: 0 20.05% 20px 20.05%;
}
.wtggdetail-content >>> .el-card__body {
  padding: 20px 40px 40px 40px;
}
.breadcrumb {
  height: 50px;
  display: flex;
  margin-bottom: 10px;
  padding: 0px 3rem;
  align-items: center;
  background: #0068d1;
}
.global-flag {
  margin-bottom: 0px;
}
.xmxqxx-div {
  width: 100%;
  text-align: right;
  margin-right: 30px;
  margin-top: 16px;
}
.xmxq-div {
  width: 100%;
  margin-right: 30px;
  margin-top: 16px;
}
.wtggxq-content {
  width: 100%;
  height: auto;
  padding: 0 20.05% 100px 20.05%;
}
.el-input {
  width: 400px;
  padding-bottom: 20px;
}
.companyTitle {
  /* font-weight: 600; */
  font-size: 24px;
  text-align: center;
}
.wtggdetail-content .el-card .is-always-shadow .el-card__header {
  padding: 30px 20px 0px 20px !important;
  border-bottom: none;
}
.clearfix {
  text-align: center;
  margin-bottom: 10px;
}
tr {
  height: 37px;
}
.td1 {
  border: 1px solid #ccc;
  /* width:17%; */
  text-align: right;
  padding-right: 10px;
  font-weight: 700;
}
.td2 {
  border: 1px solid #ccc;
  width: 900px;
  padding-left: 10px;
}
/*.td2 >>> .el-textarea {
  font-size: 16px;
  padding-left: 10px;
}*/
.global-sxtitle1 {
  /* width: 40%; */
  width: 40%;
  height: 40px;
  color: #000;
  font-size: 14px;
  position: relative;
  line-height: 40px;
  padding-left: 20px;
  margin-bottom: 20px;
  background: #efefea42;
  float: left;
}
.yibaoming {
  /* width: 40%; */
  width: 40%;
  height: 40px;
  color: #000;
  font-size: 14px;
  line-height: 40px;
  padding-left: 20px;
  margin-bottom: 20px;
  align: center;
}
.global-sxtitle1::after {
  content: "";
  width: 5px;
  height: 40px;
  position: absolute;
  top: 0;
  left: 0;
  background: #4c8bfd;
}
.el-form-item {
  margin-bottom: 0px;
}
.el-textarea >>> .el-textarea__inner {
  background: local;
  color: #303133;
  border: none;
  padding-left: 0px;
}
</style>