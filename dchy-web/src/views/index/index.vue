<template>
  <div class="index">
    <Header></Header>
    <NavBar></NavBar>

    <div class="banner">
      <img src="@/assets/img/bj.png" />
    </div>
    <!-- 提示有无资质信息 -->
    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <span>您没有填写资质信息，是否进行填写 ？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="qrdialogVisible">确 定</el-button>
      </span>
    </el-dialog>

    <div class="content">
      <div class="first">
        <div class="xmgg">
          <div class="leixing1">
            <div style="display: flex">
              <div
                class="lefts"
                :class="{ leftActive: wtgg == true, leftActives: zxgg == true }"
                @click="iswtgg()"
              >
                <img src="@/assets/icons/cggg.svg" v-if="wtgg == true" />
                <div class="title">采购公告</div>
                <div
                  style="
                    border-right: 4px solid #006cff;
                    margin-left: 20px;
                    margin-top: 4px;
                    height: 18px;
                  "
                ></div>
              </div>
              <div
                class="add"
                @click="iszxgg()"
                :class="{ zxggActive: zxgg == true }"
              >
                <img src="@/assets/icons/cggg.svg" v-if="zxgg" />
                <div class="title">中选公告</div>
              </div>
            </div>
            <div
              style="
                flex: 1;
                border-bottom: 2px solid #dedede;
                display: flex;
                justify-content: flex-end;
              "
            >
              <div class="right" @click="gotowtgglist()" v-if="wtgg">
                查看全部 >
              </div>
              <div class="right" @click="gotozxgglist()" v-if="zxgg">
                查看全部 >
              </div>
            </div>
          </div>
          <!-- <div class="title_xian1">
            <div :class="{ isActive: wtgg == true }"></div>
            <div
              :class="{ isActive: zxgg == true }"
              class="title_xian1_center"
            ></div>
            <div></div>
          </div> -->

          <div class="list" v-if="wtgg">
            <div
              v-if="wtggList.length == 0"
              style="text-align: center; color: #9b9b9b"
            >
              暂无数据
            </div>
            <div v-else>
              <div
                class="list_item"
                v-for="(item, index) in wtggList"
                :key="index"
                @click="gotodetail(item)"
              >
                <div class="list_item_left">
                  <div v-if="index == 0 || index == 1" class="zuixin">最新</div>
                  <div
                    class="item_headline"
                    :title="item.XMMC"
                    :class="{ iszuixin: index == 0 || index == 1 }"
                  >
                    {{ item.XMMC }}
                  </div>
                </div>
                <div class="list_item_right">
                  <div :title="item.WTDW">{{ item.WTDW }}</div>
                  <div :title="'发布时间：' + item.BMKSSJ">
                    {{ item.BMKSSJ }}
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="list" v-if="zxgg">
            <div
              v-if="zxggList.length == 0"
              style="text-align: center; color: #9b9b9b"
            >
              暂无数据
            </div>
            <div v-else>
              <div
                class="list_item"
                v-for="(item, index) in zxggList"
                :key="index"
                @click="gotozxdetail(item)"
              >
                <div class="list_item_left">
                  <div v-if="index == 0 || index == 1" class="zuixin">最新</div>
                  <div
                    :title="item.XMMC"
                    class="item_headline"
                    :class="{ iszuixin: index == 0 || index == 1 }"
                  >
                    {{ item.XMMC }}
                  </div>
                </div>
                <div class="list_item_right">
                  <div :title="item.WTDW">{{ item.WTDW }}</div>
                  <div :title="'中选时间：' + item.WTSJ" style="width: 160px">
                    {{ item.WTSJ }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="user">
          <div class="leixing">
            <div class="left">
              <img src="@/assets/icons/yh.svg" />
              <div class="title">用户登录</div>
            </div>
          </div>
          <div class="title_xian">
            <div></div>
            <div></div>
          </div>

          <div class="login_list" v-if="!userinfo">
            <el-tooltip
              class="item"
              effect="dark"
              content="请使用法人登录"
              placement="bottom"
            >
              <div
                class="login_item"
                @mouseover="yzdwcolor = true"
                @mouseout="yzdwcolor = false"
                v-if="!yzdwcolor"
                @click="tologin('yzdw')"
              >
                <img src="@/assets/img/yzdw.png" alt="" />

                <div>项目业主登录</div>
              </div>
              <div
                class="login_item1"
                v-if="yzdwcolor"
                @mouseover="yzdwcolor = true"
                @mouseout="yzdwcolor = false"
                @click="tologin('yzdw')"
              >
                <img src="@/assets/img/yzdwactive.png" alt="" />

                <div>项目业主登录</div>
              </div>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="dark"
              content="请使用法人登录"
              placement="bottom"
            >
              <div
                class="login_item"
                style="margin-left: 20px"
                @mouseover="chdwcolor = true"
                @mouseout="chdwcolor = false"
                v-if="!chdwcolor"
                @click="tologin('chdw')"
              >
                <img src="@/assets/img/chdw.png" alt="" />

                <div>测绘机构登录</div>
              </div>
              <div
                class="login_item1"
                style="margin-left: 20px"
                v-if="chdwcolor"
                @mouseover="chdwcolor = true"
                @mouseout="chdwcolor = false"
                @click="tologin('chdw')"
              >
                <img src="@/assets/img/chdwactive.png" alt="" />
                <div>测绘机构登录</div>
              </div>
            </el-tooltip>
          </div>

          <div class="login_list" v-if="!userinfo">
            <el-tooltip
              class="item"
              effect="dark"
              content="请使用个人登录"
              placement="bottom"
            >
              <div
                class="login_item"
                @mouseover="jgdwcolor = true"
                @mouseout="jgdwcolor = false"
                v-if="!jgdwcolor"
                @click="tologin('chsgcs')"
              >
                <img src="@/assets/img/jgdw.png" alt="" />

                <div>测绘工程师登录</div>
              </div>
              <div
                class="login_item1"
                v-if="jgdwcolor"
                @mouseover="jgdwcolor = true"
                @mouseout="jgdwcolor = false"
                @click="tologin('chsgcs')"
              >
                <img src="@/assets/img/jgdwactive.png" alt="" />

                <div>测绘工程师登录</div>
              </div>
            </el-tooltip>
            <div
              class="login_item"
              style="margin-left: 20px"
              @mouseover="grdlcolor = true"
              @mouseout="grdlcolor = false"
              v-if="!grdlcolor"
              @click="$router.push('login')"
            >
              <img src="@/assets/img/grdl.png" alt="" />
              <div>监管单位登录</div>
            </div>
            <div
              class="login_item1"
              style="margin-left: 20px"
              v-if="grdlcolor"
              @mouseover="grdlcolor = true"
              @mouseout="grdlcolor = false"
              @click="$router.push('login')"
            >
              <img src="@/assets/img/grdlactive.png" alt="" />
              <div>监管单位登录</div>
            </div>
          </div>

          <div class="after_login" v-if="userinfo">
            <div class="login_top">
              <img src="@/assets/img/afterlogin.png" />
              <div class="login_top_content">
                <div class="login_top_welcome">
                  您好！ {{ userinfo.name || userinfo.dwmc }}
                </div>
                <!-- <div class="login_top_person">个人信息</div> -->
              </div>
            </div>

            <div class="login_bottom" @click="gotogrzx()">进入个人中心</div>
          </div>
        </div>
      </div>
      <Second :need-ref="needRef" @ref-ok="refOK"></Second>
    </div>

    <!-- <div class="thrid">
      <div class="thrid_title">企业信用公示</div>
      <div class="thrid_list">
        <div class="thrid_list_item">
          <div class="thrid_list_item_top">
            <div class="price">2652</div>
            <div class="danwei">条</div>
          </div>
          <div class="thrid_list_item_bottom">评价数</div>
        </div>

        <div class="thrid_list_item">
          <div class="thrid_list_item_top">
            <div class="price">2612</div>
            <div class="danwei">条</div>
          </div>
          <div class="thrid_list_item_bottom">好评总数</div>
        </div>

        <div class="thrid_list_item">
          <div class="thrid_list_item_top">
            <div class="price">99.9</div>
            <div class="danwei">%</div>
          </div>
          <div class="thrid_list_item_bottom">好评率</div>
        </div>

        <div class="thrid_list_item">
          <div class="thrid_list_item_top">
            <div class="price">662</div>
            <div class="danwei">个</div>
          </div>
          <div class="thrid_list_item_bottom">五星企业</div>
        </div>
      </div>
    </div> -->

    <div class="fourth">
      <div class="zcfg">
        <div class="leixing">
          <div class="left">
            <img src="@/assets/icons/zcfg.svg" alt="" />
            <div class="title">政策法规</div>
          </div>
          <div class="right" @click="gotozcfglist()">查看全部 ></div>
        </div>
        <div class="title_xian">
          <div></div>
          <div></div>
        </div>

        <div class="list">
          <div
            class="list_item"
            v-for="(item, index) in zcfglist"
            :key="index"
            @click="gotozcfgdetail(item)"
          >
            <div class="list_item_left">
              <div class="item_headline" :title="item.title">
                {{ item.title }}
              </div>
            </div>
            <div
              class="list_item_right"
              :title="'发布时间：' + item.createtime"
            >
              {{ item.createtime }}
            </div>
          </div>
        </div>
      </div>
      <div class="qyml">
        <div class="leixing">
          <div class="left">
            <img src="@/assets/icons/chjg.svg" />
            <div class="title">入驻机构</div>
          </div>
          <div class="right" @click="gotoqymlk()">查看全部 ></div>
        </div>
        <div class="title_xian">
          <div></div>
          <div></div>
        </div>

        <div class="list">
          <div
            class="list_item"
            v-for="(item, index) in qymlkList"
            :key="index"
            @click="gotoqymlkdetail(item)"
          >
            <div class="list_item_left">
              <div class="item_headline" :title="item.unit_NAME">
                {{ item.unit_NAME }}
              </div>
            </div>
            <div class="list_item_right" :title="'入驻时间：' + item.cjsj">
              {{ item.cjsj }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Bottom from "@/components/Bottom.vue";
import NavBar from "@/components/NavBar.vue";
import Second from "@/components/secondist.vue";

import { formatDate, getAllProcess } from "@/assets/js/common.js";
export default {
  components: {
    Header,
    Bottom,
    NavBar,
    Second,
  },
  data() {
    return {
      //获取登录请求ID后用户信息
      userinfo: "",
      //获取url拼接ID
      userID: "",
      // 角色信息
      userRole: "",
      //是否存在资质信息 0 存在，1 不存在
      existZZ: "",
      zcfglist: [],
      wtggList: [],
      zxggList: [],
      qymlkList: [],
      // 资质信息显示
      dialogVisible: false,
      zxgg: false,
      wtgg: true,
      test: "",
      test2: "",
      //登录框
      yzdwcolor: false,
      chdwcolor: false,
      jgdwcolor: false,
      grdlcolor: false,
      //新增弹窗
      newdialogVisible: false,
      newdialogVisible1: false,
      newdialogVisible2: false,
      newdialogVisible3: false,
      newdialogVisible4: false,
      radio: "",
      radioCompany: "",
      person: "",
      company: "",
      msgCompany: false,
      msgPerson: false,
      needRef: false,
    };
  },
  methods: {
    // 去登录
    tologin(info) {
      this.userRole = info;
      // 点击登录跳转政务网
      window.open(
        // `http://www.xadchy.cn/DchyService/login?info=${this.userRole}`,
        `http://127.0.0.1:8888/login?info=${this.userRole}`,
        "_self"
      );
    },
    Ref() {
      //更新完成数据后，执行后面这句即可
      this.needRef = true;
    },
    //刷新触发
    refOK() {
      if (result) {
        this.needRef = false;
      }
    },
    // 资质信息dialog关闭之前
    handleClose() {},
    // 资质信息弹出框确认点击事件
    qrdialogVisible() {
      this.dialogVisible = false;
      const { href } = this.$router.push({
        name: "chdwzc",
        // query: {
        //   pid: val.id,
        // },
      });
    },
    iswtgg() {
      this.wtgg = true;
      this.zxgg = false;
    },
    iszxgg() {
      this.wtgg = false;
      this.zxgg = true;
    },
    // 获取采购公告列表
    getwtgglist() {
      const _this = this;
      var params = {
        pageIndex: 1,
        pageSize: 100,
        str: "",
      };
      this.$http.post("xmwt/dchyFbxx/getFbxxGg", params).then(
        (res) => {
          for (var value of res.data.data) {
            value.CJSJ = formatDate(new Date(value.CJSJ));
            //value.BMKSSJ = formatDate(new Date(value.BMKSSJ));
            value.BMKSSJ = _this
              .$moment(value.BMKSSJ)
              .format("YYYY-MM-DD HH:mm:ss");
          }
          this.wtggList = res.data.data;
        },
        (err) => {
          console.log(err);
        }
      );
    },

    // 获取所有中选公告信息
    getAllzxgg() {
      const _this = this;
      let params = {
        pageIndex: 1,
        pageSize: 100,
        str: "",
      };

      this.$http.post("xmwt/dchySqyx/getZbGg", params).then((res) => {
        debugger;
        for (var value of res.data.data) {
          if (value.WTSJ) {
            value.WTSJ = _this
              .$moment(value.WTSJ)
              .format("YYYY-MM-DD HH:mm:ss");
          } else {
            value.WTSJ = "";
          }
        }
        this.zxggList = res.data.data;
      });
    },

    //获取政策法规列表
    getzcfglist() {
      var params = {
        pageNum: 1,
        pageSize: 10,
        type: "政策法规",
        state: "发布",
      };
      this.$http.post("api/findPolicyList", params).then((res) => {
        if (res.data.data) {
          for (var value of res.data.data) {
            value.createtime = formatDate(new Date(value.createtime));
          }
        }
        this.zcfglist = res.data.data;
      });
    },
    // 获取入驻机构列表
    getqymlk() {
      var params = {
        pageSize: 6,
        pageNum: 1,
        state: "正常",
      };
      this.$http.post("zzxx/selectAllZzxx", params).then(
        (res) => {
          this.qymlkList = res.data.data;
        },
        (err) => {
          console.log(err);
        }
      );
    },
    // 获取登录后用户信息
    getUserInfo() {
      if (this.userID) {
        var params = {
          id: this.userID,
        };
      } else if (window.localStorage.getItem("thisUserid")) {
        var params = {
          id: window.localStorage.getItem("thisUserid"),
        };
      } else {
        var params = {
          id: "",
        };
      }

      // 测绘单位进入
      if (this.userRole == "chdw") {
        //存角色信息至localStorage

        this.$http
          .post("zzxx/selectZzxx", {
            did: window.localStorage.getItem("thisUserid")
              ? window.localStorage.getItem("thisUserid")
              : "",
          })
          .then((res) => {
            if (res.data.bgzzxxdate && res.data.zzxxdate) {
              // 有变更数据，显示进度查询
              this.bgzzxxdate = 0;
              this.existZZ = 0;
              window.localStorage.setItem("bgzzxxdate", this.bgzzxxdate);
              window.localStorage.setItem("existZZ", this.existZZ);
              res.data.companydate.zzxx = { ...res.data.zzxxdate };
            } else if (res.data.zzxxdate && !res.data.bgzzxxdate) {
              //显示资质变更
              this.existZZ = 0;
              this.bgzzxxdate = 1;
              window.localStorage.setItem("existZZ", this.existZZ);
              window.localStorage.setItem("bgzzxxdate", this.bgzzxxdate);
            } else if (!res.data.zzxxdate && !res.data.bgzzxxdate) {
              //显示资质注册
              this.existZZ = 1;
              this.bgzzxxdate = 1;
              window.localStorage.setItem("existZZ", this.existZZ);
              window.localStorage.setItem("bgzzxxdate", this.bgzzxxdate);
            } else {
              this.bgzzxxdate = 0;
              this.existZZ = 1;
              window.localStorage.setItem("bgzzxxdate", this.bgzzxxdate);
              window.localStorage.setItem("existZZ", this.existZZ);
            }
            res.data.companydate.zzxx = { ...res.data.zzxxdate };
            //存用户信息至localStorage
            this.userinfo = res.data.companydate;
            var obj = JSON.stringify(res.data.companydate);
            window.localStorage.setItem("userinfo", obj);
            if (!window.localStorage.getItem("userRole")) {
              window.localStorage.setItem("userRole", "chdw");
              location.reload();
            }
          });
      }
      //个人进这个
      else if (this.userRole == "gr") {
        this.$http.get("user/findPersonalbyId", { params }).then((res) => {
          this.userinfo = res.data;
          //存用户信息至localStorage
          var obj = JSON.stringify(res.data);
          window.localStorage.setItem("userinfo", obj);

          // 循环角色信息
          let roleStr = "";
          for (const r of res.data.rolelist) {
            roleStr += r.role + ",";
          }
          if (window.localStorage.getItem("userRole") === "gr") {
            // if (res.data.rolelist.length > 0) {
            //   if (res.data.rolelist[0].role == "管理员") {
            //     window.localStorage.setItem("userRole", "管理员");
            //   } else if (res.data.rolelist[0].role == "初审人") {
            //     window.localStorage.setItem("userRole", "初审人");
            //   } else if (res.data.rolelist[0].role == "终审人") {
            //     window.localStorage.setItem("userRole", "终审人");
            //   } else if (res.data.rolelist[0].role == "监管人员") {
            //     window.localStorage.setItem("userRole", "jgdw");
            //   } else if (res.data.rolelist[0].role == "成果验收经办人") {
            //     window.localStorage.setItem("userRole", "成果验收经办人");
            //   }
            // } else {
            //   this.$message({
            //     message: "监管单位登录查无此角色",
            //     type: "error",
            //     offset: 160,
            //   });
            // }
            // 避免多角色时的问题
            if (roleStr) {
              if (roleStr.indexOf("管理员") != -1) {
                window.localStorage.setItem("userRole", "管理员");
              } else if (roleStr.indexOf("初审人") != -1) {
                window.localStorage.setItem("userRole", "初审人");
              } else if (roleStr.indexOf("终审人") != -1) {
                window.localStorage.setItem("userRole", "终审人");
              } else if (roleStr.indexOf("监管人员") != -1) {
                window.localStorage.setItem("userRole", "jgdw");
              } else if (roleStr.indexOf("成果验收经办人") != -1) {
                window.localStorage.setItem("userRole", "成果验收经办人");
              }
            } else {
              this.$message({
                message: "监管单位登录查无此角色",
                type: "error",
                offset: 160,
              });
            }
            location.reload();
          }
        });
      } else if (this.userRole == "chsgcs") {
        this.$http.get("user/findPersonalbyId", { params }).then((res) => {
          this.userinfo = res.data;
          //存用户信息至localStorage
          var obj = JSON.stringify(res.data);
          window.localStorage.setItem("userinfo", obj);

          if (!window.localStorage.getItem("userRole")) {
            window.localStorage.setItem("userRole", "chsgcs");
            location.reload();
          }
        });
      }
      // 业主单位进这个
      else if (this.userRole == "yzdw") {
        this.$http.get("user/findCompanybyId", { params }).then((res) => {
          this.userinfo = res.data;
          //存用户信息至localStorage
          var obj = JSON.stringify(res.data);
          window.localStorage.setItem("userinfo", obj);

          //存角色信息至localStorage  待修改

          if (!window.localStorage.getItem("userRole")) {
            window.localStorage.setItem("userRole", this.userRole);
            location.reload();
          }
        });
      }
      //注明： 此处jgdw是之前的，现在放到gr里面了
      // else if (this.userRole == "jgdw") {
      //   this.$http.get("user/findCompanybyId", { params }).then((res) => {
      //     this.userinfo = res.data;
      //     //存用户信息至localStorage
      //     var obj = JSON.stringify(res.data);
      //     window.localStorage.setItem("userinfo", obj);

      //     if (!window.localStorage.getItem("userRole")) {
      //       window.localStorage.setItem("userRole", this.userRole);
      //       location.reload();
      //     }
      //   });
      // }
    },
    // 去采购公告详情页
    gotodetail(val) {
      const { href } = this.$router.push({
        name: "wtggdetail",
        query: {
          pid: val.ID,
        },
      });
    },

    // 去中选公告详情页
    gotozxdetail(val) {
      const { href } = this.$router.push({
        name: "zxggdetail",
        query: {
          id: val.ID,
        },
      });
    },
    // 去采购公告列表页
    gotowtgglist() {
      const { href } = this.$router.push({
        name: "wtgg",
        // query: { timeType: "55555555" },
      });
    },

    gotozxgglist() {
      const { href } = this.$router.push({
        name: "zxgg",
        // query: { timeType: "55555555" },
      });
    },
    // 政策法规列表页
    gotozcfglist() {
      const { href } = this.$router.push({
        name: "zcfg",
        // query: { timeType: "55555555" },
      });
    },

    // 政策法规详情页
    gotozcfgdetail(item) {
      const { href } = this.$router.push({
        name: "zcfgdetail",
        query: { id: item.id },
      });
    },

    // 入驻机构列表页
    gotoqymlk() {
      const { href } = this.$router.push({
        name: "qymlk",
        // query: { timeType: "55555555" },
      });
    },

    // 入驻机构详情页
    gotoqymlkdetail(item) {
      const { href } = this.$router.push({
        name: "qymlkdetail",
        query: { did: item.did },
      });
    },
    // 跳转个人中心
    gotogrzx() {
      const { href } = this.$router.push({
        name: "grzx",
      });
    },
    // 监听跳转
    async loginoutDefault() {
      var url = window.location.href;
      var reg = url.split("?logout=");
      if (reg[1]) {
        await this.$http
          .post("/loginout")
          .then((res) => {
            if (res.status == 200) {
              // window.location.href = url;
              window.location.href = this.$http.defaults.redirectURL;
            }
          })
          .catch((err) => {
            this.$message.error("请求出错！请稍后再试");
            console.log(err);
          });
      }
    },
  },

  async created() {
    this.getwtgglist();
    this.getAllzxgg();
    this.getqymlk();
    this.getzcfglist();
    //是否退出政务网登录
    this.loginoutDefault();
    // 获取url拼接id字段
    if (this.$route.query.id) {
      this.userID = this.$route.query.id;
      window.localStorage.setItem("thisUserid", this.userID);
      //如果是个人登录,还要获取query后的个人判断是局内审批还是高级或测绘
      this.userRole = this.$route.query.info;
      window.localStorage.setItem("zwwid", this.$route.query.zwwid);
      this.getUserInfo();
    }
    if (window.localStorage.getItem("xzfjids")) {
      window.localStorage.removeItem("xzfjids");
    }
    if (window.localStorage.getItem("xzfjid")) {
      window.localStorage.removeItem("xzfjid");
    }
    let processList = await getAllProcess();
    if (processList && processList.length > 0) {
      window.localStorage.setItem("processList", JSON.stringify(processList));
    }
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }

    // 获得点击监管单位登录定义的角色信息
    if (window.localStorage.getItem("userRole")) {
      if (window.localStorage.getItem("userID")) {
        this.userID = window.localStorage.getItem("userID");
      }
      this.userRole = window.localStorage.getItem("userRole");
      this.getUserInfo();
    }

    if (this.userRole == "chdw") {
      if (window.localStorage.getItem("bgzzxxdate")) {
        this.bgzzxxdate = window.localStorage.getItem("bgzzxxdate");
        if (this.bgzzxxdate == 0) {
          this.dialogVisible = false;
          return;
        }
      }
    }
  },
  mounted() {
    if (window.localStorage.getItem("existZZ")) {
      this.existZZ = window.localStorage.getItem("existZZ");
      if (this.existZZ == 0) {
        this.dialogVisible = false;
      }
      if (this.existZZ == 1 && this.bgzzxxdate == 1) {
        this.dialogVisible = true;
      }
    }
  },
};
</script>

<style scoped>
.index {
  height: 100%;
}
.banner {
  width: 100%;
  height: 400px;
}
.banner img {
  width: 100%;
  height: 400px;
}
.el-dialog__wrapper {
  top: 203px;
}
.content {
  padding: 0 376px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.first {
  display: flex;
  width: 100%;
}

/* 项目公告 */
.xmgg {
  /* width: 723px; */
  flex: 1.7;
  height: 340px;
  border: 1px solid #dedede;
  margin-top: 30px;
  padding: 20px;
}
.leixing {
  height: 24px;
  display: flex;
  justify-content: space-between;
}
.leixing1 {
  height: 50px;
  display: flex;
  justify-content: space-between;
}
.left {
  display: flex;
  align-items: center;
}
.lefts {
  display: flex;
  align-items: center;
  padding-bottom: 24px;
  height: 50px;
  border-bottom: 2px solid #dedede;
}
.left:hover {
  cursor: pointer;
}
.lefts:hover {
  cursor: pointer;
}
.add:hover {
  cursor: pointer;
}
.add {
  display: flex;
  align-items: center;

  padding-left: 20px;
  padding-bottom: 24px;
  /* border-left: 5px solid #1890ff; */
  border-bottom: 2px solid #dedede;
}
.add img {
  width: 24px;
  height: 24px;
  margin-right: 10px;
}

.leftActive {
  border-bottom: 2px solid #006cff;
}
.leftActives {
  padding-right: 20px;
}
.zxggActive {
  border-bottom: 2px solid #006cff;
  padding-left: 0px;
}
.left img {
  width: 24px;
  height: 24px;
  margin-right: 10px;
}
.lefts img {
  width: 24px;
  height: 24px;
  margin-right: 10px;
}
.title {
  height: 24px;
  font-size: 22px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  color: #4a4a4a;
  line-height: 24px;
}
.right {
  height: 14px;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #9b9b9b;
  line-height: 14px;
  margin-top: 9px;
  cursor: pointer;
}
.right:hover {
  color: #0091ff;
}

.title_xian {
  display: flex;
}
.title_xian :first-child {
  width: 128px;
  border-bottom: 2px solid #006cff;
  margin-top: 24px;
}
.title_xian :last-child {
  flex: 1;
  border-bottom: 2px solid #dedede;
  margin-top: 24px;
}
.title_xian1 {
  display: flex;
}
.title_xian1 :first-child {
  width: 140px;
  border-bottom: 2px solid #dedede;
  margin-top: 24px;
}
.title_xian1_center {
  width: 140px;
  border-bottom: 2px solid #dedede;
  margin-top: 24px;
}
.title_xian1 :last-child {
  flex: 1;
  border-bottom: 2px solid #dedede;
  margin-top: 24px;
}
.isActive {
  border-bottom: 2px solid #006cff !important;
}
.list {
  margin-top: 9px;
  height: 252px;
  overflow-x: auto;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #4a4a4a;
  line-height: 42px;
  overflow: hidden;
}
.list_item {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px dashed #dedede;
  cursor: pointer;
}
.list_item:hover {
  color: #0091ff;
}
.list_item_left {
  display: flex;
  align-items: center;
}
.list_item_right {
  display: flex;
}
.list_item_right :first-child {
  width: 144px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.list_item_right :last-child {
  margin-left: 20px;
}
.zuixin {
  width: 34px;
  height: 24px;
  background: #fa6400;
  border-radius: 3px;
  text-align: center;
  line-height: 20px;
  font-size: 10px !important;
  padding: 2px 3px;
  color: #fff;
}
.iszuixin {
  margin-left: 5px;
}
.item_headline {
  width: 270px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
/* 用户信息 */
.user {
  /* width: 425px; */
  flex: 1;
  height: 340px;
  margin-top: 30px;
  margin-left: 20px;
  border: 1px solid #dedede;
  padding: 20px;
}
.login_list {
  height: 104px;
  display: flex;
  margin-top: 20px;
}
.login_item {
  width: 185px;
  height: 104px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  background: #f8f8f8;
  color: #000;
  border-radius: 2px;
  cursor: pointer;
}
.login_item img {
  width: 34px;
  height: 38px;
}
.login_item div {
  font-size: 16px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  /* color: #ffffff; */
  line-height: 16px;
  margin-top: 15px;
}

.login_item1 {
  width: 185px;
  height: 104px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  background: #0088ff;
  color: #fff;
  border-radius: 2px;
  cursor: pointer;
}
.login_item1 img {
  width: 34px;
  height: 38px;
}
.login_item1 div {
  font-size: 16px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
  /* color: #ffffff; */
  line-height: 16px;
  margin-top: 15px;
}

.after_login {
}
.after_login img {
  width: 100px;
  height: 100px;
}
.login_top {
  display: flex;
  align-items: center;
  margin: 50px 0px 25px 50px;
}
.login_top_welcome {
  font-size: 18px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #4a4a4a;
  line-height: 18px;
  margin-left: 10px;
  line-height: inherit;
}
.login_top_person {
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #6d7278;
  line-height: 14px;
  margin-top: 15px;
  margin-left: 10px;
}

.login_bottom {
  margin-left: 50px;
  width: 280px;
  height: 40px;
  background: #1d94fc;
  border-radius: 4px;
  line-height: 40px;
  text-align: center;
  font-size: 15px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 500;
  color: #ffffff;
  cursor: pointer;
}

/* 便民服务 */
.second_title {
  margin-top: 60px;
  height: 26px;
  font-size: 26px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #4a4a4a;
  line-height: 26px;
  text-align: center;
}
/* 企业信用公示 */
.thrid {
  width: 100%;
  margin-top: 30px;
  height: 240px;
  background: #0091ff;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.thrid_title {
  margin-top: 40px;
  height: 26px;
  font-size: 26px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #ffffff;
  line-height: 26px;
}
.thrid_list {
  width: 100%;
  display: flex;
  padding: 0 440px;
  margin-top: 40px;
}
.thrid_list_item {
  flex: 1;
  align-items: center;
  justify-content: center;
}
.thrid_list_item_top {
  display: flex;
  margin-left: 42px;
}
.thrid_list_item_bottom {
  padding-left: 42px;
  margin-top: 12px;
  height: 16px;
  font-size: 16px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #ffffff;
  line-height: 16px;
}
.price {
  height: 40px;
  font-size: 40px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #ffffff;
  line-height: 40px;
}
.danwei {
  height: 20px;
  font-size: 20px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #ffffff;
  line-height: 20px;
  margin-top: 16px;
}

/* 政策法规合企业名录 */
.fourth {
  width: 100%;
  padding: 0 376px;
  display: flex;
  margin-bottom: 30px;
}

.zcfg {
  width: 574px;
  margin-right: 20px;
  height: 370px;
  border: 1px solid #dedede;
  margin-top: 30px;
  padding: 20px;
  overflow: hidden;
}
.zcfg >>> .item_headline {
  width: 400px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.qyml {
  width: 574px;
  height: 370px;
  border: 1px solid #dedede;
  margin-top: 30px;
  padding: 20px;
  overflow: hidden;
}
</style>