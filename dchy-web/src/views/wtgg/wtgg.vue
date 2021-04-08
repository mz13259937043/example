<template>
  <div style="height: 100%">
    <Header></Header>
    <Navbar></Navbar>
    <!-- <div class="breadcrumb">
      <div style="color:white;">当前所在位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>采购列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div> -->
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>采购公告</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="wtgg-content">
      <div class="global-sxtitle" style="margin-bottom: 0;display: none">
        温馨提示：测绘单位所能承受的测绘作业范围以测绘资质证书登记的为准！
      </div>
      <el-form :inline="true">
        <el-form-item>
          <!-- <div style="margin-top: 15px;">           -->
          <el-input
            style="margin-top: 15px;"
            placeholder="请输入项目名称、项目地址、业务阶段、采购单位"
            v-model="page.str"
            class="input"
          >
          </el-input>
          <el-button @click="getList" class="btn" style="border-radius: 0"
            >搜索</el-button
          >
          <!-- </div> -->
        </el-form-item>
      </el-form>

      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="tableData"
        border
        style="width: 100%; cursor: pointer"
        :row-style="{ color: '#000' }"
        @row-click="gotowtggdetail"
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
          min-width="200px"
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
          width="110"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.CHJD)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="ZZDJ"
          label="最低资质等级"
          align="center"
          width="115"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.ZZDJ)"></span>
          </template>
        </el-table-column>

        <!-- <el-table-column
          prop="BZ"
          label="备注"
          align="center"
          width="70"

        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.BZ)"></span>
          </template>
        </el-table-column> -->

        <el-table-column
          prop="YSJE"
          label="预算金额(元)"
          align="center"
          width="110"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.YSJE)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="XMWCSX"
          label="完成时限(天)"
          align="center"
          width="110"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMWCSX)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="wtdw" label="采购单位" align="center" min-width="200px">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.WTDW)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="BMKSSJ"
          label="报名开始时间"
          align="center"
          width="115"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.BMKSSJ)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="BMJSSJ"
          label="报名结束时间"
          align="center"
          width="115"
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.BMJSSJ)"></span>
          </template>
        </el-table-column>
        <!--<el-table-column prop="operate" label="操作" align="center" width="95">
          <template slot-scope="scope">
            <span
              v-if="scope.row.state !== null"
              v-html="scope.row.state"
            ></span>
            <el-button
              v-else
              class="btn"
              :key="scope.$index"
              size="small"
              @click="handleShenQing(scope.row, scope.$index, $event)"
            >
              报名</el-button
            >
            &lt;!&ndash;<el-tooltip :content="scope.row.msg" >
              <el-button :type="scope.row.buttonType" :disabled="false" @click="handleShenQing(scope.row,scope.$index,$event)">申请</el-button>
            </el-tooltip>&ndash;&gt;
            &lt;!&ndash;            <el-button class="btn1" @click="handleShenQing(scope.row,$event)">申请</el-button>&ndash;&gt;
          </template>
        </el-table-column>-->
      </el-table>
      <Apply
        :propInfo="{
          pid: $store.state.applyArg.pid,
          visible: $store.state.applyArg.visible,
          sqyxid: $store.state.applyArg.sqyxid,
        }"
        v-if="$store.state.applyArg.visible"
      ></Apply>
      <el-pagination
        background
        v-if="paginationshow"
        @current-change="handleCurr"
        @size-change="handleSize"
        :current-page="page.pageIndex"
        :page-size="page.pageSize"
        layout="total,sizes, prev, pager, next, jumper"
        :total="page.total"
        :page-sizes="[10, 20]"
        style="margin-bottom: 20px; text-align: center"
      ></el-pagination>
      <!--      <el-dialog title="申请信息" :visible.sync="visible" width="30%">-->
      <!--        <el-form-->
      <!--          ref="sqxxForm"-->
      <!--          :model="sqxxForm"-->
      <!--          autocomplete="off"-->
      <!--          :rules="rules"-->
      <!--          class="xxxform"-->
      <!--        >-->
      <!--          <el-row>-->
      <!--            <el-col :span="24">-->
      <!--              <el-form-item label="项目报价/元" prop="xmbj" label-width="100px">-->
      <!--                <el-input-->
      <!--                  type="xm"-->
      <!--                  v-model="sqxxForm.xmbj"-->
      <!--                  placeholder="请输入报价"-->
      <!--                />-->
      <!--              </el-form-item>-->
      <!--            </el-col>-->
      <!--            <el-col :span="24">-->
      <!--              <el-form-item label="联系人" prop="lxr" label-width="100px">-->
      <!--                <el-input-->
      <!--                  type="xm"-->
      <!--                  v-model="sqxxForm.lxr"-->
      <!--                  placeholder="请输入联系人"-->
      <!--                />-->
      <!--              </el-form-item>-->
      <!--            </el-col>-->
      <!--            <el-col :span="24">-->
      <!--              <el-form-item label="联系电话" prop="lxrdh" label-width="100px">-->
      <!--                <el-input-->
      <!--                  type="xm"-->
      <!--                  v-model="sqxxForm.lxrdh"-->
      <!--                  placeholder="请输入联系电话"-->
      <!--                />-->
      <!--              </el-form-item>-->
      <!--            </el-col>-->
      <!--          </el-row>-->
      <!--        </el-form>-->
      <!--        <div slot="footer" class="dialog-footer">-->
      <!--          <el-button @click="visible = false">取 消</el-button>-->
      <!--          <el-button type="primary" @click="handelOk(pid,$event)">确 定</el-button>-->
      <!--        </div>-->
      <!--      </el-dialog>-->
    </div>
    <Buttom></Buttom>
  </div>
</template>

<script>
import Buttom from "@/components/Bottom.vue";
import Header from "@/components/Header.vue";
import Navbar from "@/components/NavBar.vue";
import Apply from "@/components/FbxxApply.vue";
import { formatDate, doMessage } from "@/assets/js/common.js";
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
  name: "wtgg",
  components: {
    Buttom,
    Header,
    Navbar,
    Apply,
  },

  data() {
    return {
      rules,
      buttonList: [],
      userinfo: "",
      visible: false,
      checkNick: false,
      formItemLayout,
      formTailLayout,
      paginationshow: false,
      // 表格信息
      tableData: [],
      //  分页组件
      page: {
        total: null,
        pageIndex: 1,
        pageSize: 10,
        str: null,
      },
      // sqxxForm: this.$form.createForm(this, { name: "sqxx_form" }),
      sqxxForm: {
        chdwid: null, //测绘单位id
        dxxz: null, //是否定向选择
        pid: "", //主项目的id
        xmbj: null, //项目报价
        lxr: "", //联系人
        lxrdh: "", //联系人电话
      },
      button: {
        msg: "",
        type: "",
      },
      fileList: [],
      pid: "",
      loading: true,
    };
  },
  created() {

    this.getDetail();
    this.getList();
    //页面需要触发的函数
  },
  methods: {
    getDetail() {
      // alert(makePy("用地复核、规划条件核实测量"));
      if (window.localStorage.getItem("userinfo")) {
        this.userinfo = {
          ...JSON.parse(window.localStorage.getItem("userinfo")),
        };
      }
      if (window.localStorage.getItem("processList")) {
        this.processList = JSON.parse(window.localStorage.getItem("processList"));
      }
    },
    // 获取列表
    getList() {
      let params = {
        pageIndex: this.page.pageIndex,
        pageSize: this.page.pageSize,
        str: this.page.str,
      };
      let userinfo = this.userinfo;
      this.$http.post("xmwt/dchyFbxx/getFbxxGg", params).then(
        (res) => {
          //在函数里面取不到this.userinfo对象
          let resData = "";
          // 日期转换
          if (res.data.data) {
            resData = res.data.data;
            for (let item of resData) {
              let msg = "";
              let state = "报名";
              item.BMKSSJ = this.$moment(item.BMKSSJ).format(
                "YYYY-MM-DD HH:mm:ss"
              );
              item.BMJSSJ = this.$moment(item.BMJSSJ).format(
                "YYYY-MM-DD HH:mm:ss"
              );
              item.CHJD = _.filter(this.processList,item2=>{
                return item2.id === item.CHJD;
              })[0].processAlias;
              // 在此展示资格判定结果
              for (let item2 of item.sqyxList) {
                if (item2.CHDWID === userinfo.id) {
                  if (item2.WTQR && item2.WTQR.indexOf("Y") != -1) {
                    msg = "您已经申请过该项目，不可以重复申请";
                    state = "已中选";
                    break;
                  } else if (item2.WTQR && item2.WTQR.indexOf("N") != -1) {
                    msg = "您已经申请过该项目，不可以重复申请";
                    state = "未中选";
                    break;
                  } else if (item2.WTQR && item2.WTQR == "X") {
                    msg = "您已经申请过该项目，不可以重复申请";
                    state = "已撤销";
                    break;
                  } else if (item2.WTQR && item2.WTQR == "M") {
                    msg = "您已经申请过该项目，不可以重复申请";
                    state = "已报名";
                    break;
                  }
                }
              }
              debugger;
              if (userinfo.zzxx) {
                if (!userinfo.zzxx.id) {
                  msg = "您还没有资质信息";
                } else if (!this.judge(item.ZZDJ, userinfo.zzxx.zzdj)) {
                  // 甲级>乙级>丙级>丁级
                  msg = "您不符合业主的资质等级";
                } else if (userinfo.zzxx.state !== "正常") {
                  msg = "您的资质信息不正常，请联系管理员纠正";
                } else if (userinfo.zzxx.dchyywfw.indexOf(item.CHJD) == -1) {
                  msg = "您不符合订单的事项范围";
                }
              }
              debugger;
              if (msg != "") {
                item.buttonType = "info";
                // item.msg = '您已经申请过该项目，不可以重复申请';
                item.msg = msg;
                item.state = state;
                item.disable = true;
              } else {
                item.buttonType = "primary";
                item.state = null;
                item.msg = "点击申请该项目";
              }
            }
          }
          if (res.data.count > 9) {
            this.paginationshow = true;
          } else {
            this.paginationshow = false;
          }
          this.tableData = [...resData];
          this.page.total = res.data.count;
          this.loading = false;
        },
        (err) => {
          console.log(err);
          this.loading = false;
        }
      );
    },
    // 甲乙丙丁判定规则
    judge(arg1, arg2) {
      //arg1，要求，arg2自己
      //  甲>乙>丙>丁
      // 不允许    甲乙，甲丙，甲丁  乙丙，乙丁   丙丁
      if (arg2) {
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
      }
      return false;
    },
    // 双击行跳转至采购公告详情页
    gotowtggdetail(val) {
      this.$router.push({
        name: "wtggdetail",
        query: {
          pid: val.ID,
        },
      });
    },
    check() {
      this.form.validateFields((err) => {
        if (!err) {
        }
      });
    },
    /*handleChange(e) {
      this.checkNick = e.target.checked;
      this.$nextTick(() => {
        this.form.validateFields(["nickname"], { force: true });
      });
    },*/
    dateFormat(row, column) {
      let rowdate = row[column.property];
      if (rowdate) {
        return formatDate(new Date(rowdate));
      } else {
        return "";
      }
    },
    // 提交申请
    handleShenQing(val, ind, event) {
      // 在这块判断可否申请
      debugger;
      let el = event.currentTarget;
      this.pid = val.ID;
      event.stopPropagation();
      // 判断可否申请，
      /*    项目有资质等级，有事项范围，
单位也有这些，当事项范围包含，等级包含
      判断您还没有资质信息（注册）*/
      // 在此展示资格判定结果
      if (!window.localStorage.getItem("userinfo")) {
        doMessage("请登录后使用", false);
        return false;
      } else if (window.localStorage.getItem("userRole") != "chdw") {
        doMessage("您不是测绘机构，不能报名！", false);
        return false;
      } else if (new Date(val.BMKSSJ) > new Date()) {
        return doMessage("尚未到报名时间，不能报名！", false);
      }
      let userinfo = this.userinfo;
      let msg = "";

      for (let item2 of val.sqyxList) {
        if (item2.CHDWID === userinfo.id) {
          msg = "您已经申请过该项目，不可以重复申请";
          break;
        }
      }

      if (!userinfo.zzxx.id) {
        msg = "报名失败，请补充测绘资质！";
      } else if (!this.judge(val.ZZDJ, userinfo.zzxx.zzdj)) {
        // 甲级>乙级>丙级>丁级
        msg = "报名失败，您不符合该项目要求的测绘资质等级！";
      } else if (userinfo.zzxx.state !== "正常") {
        msg = "您的资质信息异常，请联系管理员";
      } else if (userinfo.zzxx.dchyywfw.indexOf(val.CHJD) == -1) {
        msg = "报名失败，您不符合该项目要求的“多测合一”业务范围！";
      }
      if (msg != "") {
        doMessage(msg, false);
        return;
      }
      let param = {
        applyArg: {
          pid: val.ID,
          sqyxid: val.SQYXID,
          visible: true,
        },
      };
      this.$store.commit("changeApplyArg", param);

      this.title = "申请信息";
      this.sqxxForm = {};
    },
    handelOk(val, event) {
      let el = event.currentTarget;
      this.sqxxForm.chdwid = this.userinfo.id;
      this.sqxxForm.pid = val;
      let _this = this;
      // 数据是否填写完整验证
      let pass = true;
      if (!_this.sqxxForm.lxr) {
        pass = false;
      } else if (!/^[1][3,4,5,7,8][0-9]{9}$/.test(_this.sqxxForm.lxrdh)) {
        pass = false;
      } else if (
        !/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(
          _this.sqxxForm.xmbj
        )
      ) {
        pass = false;
      }
      if (!pass) {
        doMessage("输入信息格式有误！", false);
        return;
      }
      if (_this.title === "申请信息") {
        let obj = _this.sqxxForm;
        let params = { ...obj };
        this.$http.post("xmwt/dchySqyx/saveSqyx", params).then((res) => {
          if (res.data.data) {
            // 申请成功，
          }
          if (res.data) {
            // list 中添加一条 伪数据  避免重复申请
            if (this.tableData.sqyxList) {
              this.tableData.sqyxList.push({ chdwid: this.userinfo.id });
            } else {
              this.tableData.sqyxList = [{ chdwid: this.userinfo.id }];
            }
            doMessage("申请成功！", true);
            this.getList();
          } else {
            doMessage("申请失败！", false);
          }
          _this.visible = false;
        });
      } else {
        _this.visible = false;
      }
    },
    /**
     * 分页部分
     */
    //监听页码值的变化
    handleCurr(page) {
      this.loading = true;
      this.page.pageIndex = page;
      this.getList();
    },
    handleSize(val) {
      this.loading = true;
      this.page.pageSize = val;
      this.page.pageIndex = 1;
      this.getList();
    },
    //关键字标色
    changeColor(val) {
      let tags = this.page.str;
      if (tags !== null && tags !== "" && val !== null && val !== "") {
        let reg = new RegExp("(" + tags + ")", "g");
        return val.replace(reg, "<font style='color:red'>$1</font>");
      } else {
        return val;
      }
    },
    // 搜索高亮
    changeColor2(val) {
      let tags = this.page.str;
      if (tags !== null && tags !== "") {
        if (val.indexOf(tags) !== 1) {
          // return val.replace(val.charAt(tags),tags.length-1,"<font color='red'>"+tags+"</font>>");
          return val.replace(tags, `<font color="#409EFF">${tags}</font>`);
        }
        return val;
      } else {
        return val;
      }
    },
  },
  watch: {
    "$store.state.applyArg.visible": {
      //dia组件消失触发
      handler() {
        let _this = this;
        if (!_this.$store.state.applyArg.visible) {
          this.getList();
        }
      },
    },
  },
};

const formItemLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 8 },
};
const formTailLayout = {
  labelCol: { span: 4 },
  wrapperCol: { span: 8, offset: 4 },
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
.wxts {
  padding: 10px;
  height: 40px;
  background-color: rgb(141, 224, 250);
}

.chdwcx-inp {
  width: 40%;
}

.wtgg-content {
  width: 100%;
  height: auto;
  padding: 0 20.05% 0 20.05%;
  min-height: 572px;
}
.el-input {
  width: 400px !important;
}
/* .wtgg-content>>>.el-input__inner {
  border-radius: 0;
} */

.pagination {
  margin-top: 20px;
  text-align: center;
}
.xxxform >>> .el-form-item__content {
  margin-left: 0;
}
.zt {
  font-size: 15px !important;
}

.el-input >>> .el-input__inner {
  border-radius: 0px;
}
.el-table .cell {
  white-space: pre-line;
}
</style>