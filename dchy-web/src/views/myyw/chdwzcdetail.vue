<template>
  <div class="chdwzc">
    <headers></headers>
    <navbar></navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <div>当前位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>测绘单位注册信息详情页</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="content">
      <!-- 主体 -->
      <div class="chdwzc-content">
        <!-- <el-col :span="24">
          <div class="global-sxtitle">
            <span
              >您的资质审核已提交至相应处室审核，审核时间为3-5个工作日，请耐心等待结果。</span
            >
          </div>
        </el-col> -->

        <div>
          <div class="global-flag">
            <span>基本信息</span>
            <!-- <el-button type="primary" style="height: 30px; line-height: 6px"
              >变更</el-button
            >-->
          </div>
          <el-form
            ref="jbxxForm"
            :model="jbxxForm"
            autocomplete="off"
            label-width="180px"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="统一社会信用代码" prop="tyshxydm">
                  <el-input
                    type="tyshxydm"
                    v-model="jbxxForm.tyshxydm"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="单位名称" prop="dwmc">
                  <el-input
                    type="dwmc"
                    v-model="jbxxForm.dwmc"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="单位地址" prop="qydz">
                  <el-input
                    type="qydz"
                    v-model="jbxxForm.qydz"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="注册时间" prop="cjsj">
                  <el-input
                    type="cjsj"
                    v-model="jbxxForm.cjsj"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人姓名" prop="frxm">
                  <el-input
                    type="frxm"
                    v-model="jbxxForm.frxm"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人身份证号" prop="frsfz">
                  <el-input
                    type="frsfz"
                    v-model="jbxxForm.frsfz"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="联系人" prop="lxr">
                  <el-input
                    type="lxr"
                    v-model="jbxxForm.lxr"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="联系电话" prop="lxdh">
                  <el-input
                    type="lxdh"
                    v-model="jbxxForm.lxdh"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <!-- 测绘资质信息 -->
              <div class="global-flag">测绘资质信息</div>
              <el-col :span="12">
                <el-form-item label="测绘资质证书编号" prop="bgzsbh">
                  <el-input
                    type="bgzsbh"
                    v-model="jbxxForm.bgzsbh"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="证书有效期" prop="bgzsyxq">
                  <el-date-picker
                    v-model="jbxxForm.bgzsyxq"
                    type="date"
                    :editable="false"
                    :disabled="readonly"
                  ></el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="资质等级" prop="bgzzdj">
                  <el-input
                    type="bgzzdj"
                    v-model="jbxxForm.bgzzdj"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="发证部门" prop="bgfzbm">
                  <el-input
                    type="bgfzbm"
                    v-model="jbxxForm.bgfzbm"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item
                  label="“多测合一”业务范围"
                  prop="bgdchyywfw"
                  class="dchyywfw"
                >
                  <el-checkbox-group v-model="bgdchyywfw">
                    <el-checkbox
                      label="项目用地测量阶段"
                      :disabled="!readonly"
                    ></el-checkbox>
                    <el-checkbox
                      label="工程实施测量阶段"
                      :disabled="!readonly"
                    ></el-checkbox>
                    <el-checkbox
                      label="竣工及不动产登记测量"
                      :disabled="!readonly"
                    ></el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="专业范围" prop="bgzyfw" class="zyfw">
                  <el-input
                    placeholder="专业范围"
                    type="textarea"
                    v-model="jbxxForm.bgzyfw"
                    :rows="6"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item
                  label="资质证书"
                  class="zzzsstyle"
                  v-if="jbxxForm.bgzzfj"
                >
                  <Upload
                    :download="{
                      LOGDQHJ: 'zzsh',
                      id: qypathId,
                      rootpath: qyrootpath,
                    }"
                    v-if="qyrootpath"
                  ></Upload>
                </el-form-item>
              </el-col>

              <!-- 人员信息 -->
              <div class="global-flag">人员信息</div>

              <!-- 表格 -->
              <el-col :span="24">
                <el-table
                  border
                  ref="multipleTable"
                  :data="data"
                  :header-cell-style="{ background: '#0091ff', color: '#fff' }"
                  style="width: 100%"
                >
                  <el-table-column label="姓名" prop="bgname"></el-table-column>
                  <el-table-column
                    prop="bgidcard"
                    label="身份证号"
                  ></el-table-column>
                  <el-table-column
                    prop="bgphonenum"
                    label="联系电话"
                    show-overflow-tooltip
                  ></el-table-column>
                  <el-table-column prop="bgtype" label="角色"></el-table-column>
                  <el-table-column label="操作" align="center" width="180">
                    <template slot-scope="scope">
                      <div>
                        <el-button type="primary" @click="viewFj(scope.row)"
                          >查看附件</el-button
                        >
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>

              <el-col :span="24">
                <div class="global-right-btn">
                  <el-button
                    type="danger"
                    @click="handleRefuse('退回')"
                    :disabled="flag ? true : false"
                    >不通过</el-button
                  >
                  <el-button
                    type="primary"
                    @click="handlePass1('')"
                    :disabled="flag ? true : false"
                    >通过</el-button
                  >
                </div>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
    </div>
    <!-- 查看附件弹框 -->
    <el-dialog title="查看附件" :visible.sync="visible" width="45%">
      <el-form
        ref="ryxxForm"
        :model="ryxxForm"
        autocomplete="off"
        label-width="100px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="bgname">
              <el-input
                type="bgname"
                v-model="ryxxForm.bgname"
                :disabled="readonly"
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="身份证号" prop="bgidcard">
              <el-input
                type="bgidcard"
                v-model="ryxxForm.bgidcard"
                :disabled="readonly"
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="联系电话" prop="bgphonenum">
              <el-input
                type="bgphonenum"
                v-model="ryxxForm.bgphonenum"
                :disabled="readonly"
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="角色" prop="bgtype">
              <el-select
                :disabled="readonly"
                v-model="ryxxForm.bgtype"
                placeholder="请选择角色"
                @change="changeJs($event)"
              >
                <el-option
                  v-for="item in bgtypeArr"
                  :key="item.value"
                  :label="item.title"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="资质证书" class="zzzsstyle" v-if="visible">
              <Upload
                :download="{
                  LOGDQHJ: 'zzsh_ryxx',
                  id: rypathId,
                  rootpath: ryrootpath,
                }"
                v-if="rypathId"
              ></Upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- <span slot="footer" class="dialog-footer">
        <el-button type="info" @click="handleForm">确认</el-button>
        <el-button type="primary" @click="clear">取消</el-button>
      </span> -->
    </el-dialog>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt />
    </el-dialog>

    <!-- 拒绝通过弹框 -->
    <el-dialog title="填写原因" :visible.sync="jjVisibleDialog" width="30%">
      <el-form
        ref="jjyyForm"
        :model="jjyyForm"
        autocomplete="off"
        label-width="120px"
        :rules="rules"
      >
        <!-- <el-row>
          <el-col :span="24">
            <el-form-item label="不通过原因" prop="bgwtgyy"> -->
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入内容"
          v-model="jjyyForm.bgwtgyy"
        />
        <!-- </el-form-item>
          </el-col>
        </el-row> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="clearJjyy">取消</el-button>
        <el-button type="primary" @click="handleFormJjyy">确认</el-button>
      </span>
    </el-dialog>
    <bottom></bottom>
  </div>
</template>

<script>
import headers from "@/components/Header.vue";
import bottom from "@/components/Bottom.vue";
import navbar from "@/components/NavBar.vue";
import Upload from "@/components/Upload.vue";
import {
  downloadFile,
  closePage,
  doMessage,
  uploadsearch,
  uploadcreate,
} from "@/assets/js/common.js";

const dataSource = [
  {
    key: "0",
    xuhao: "1",
    fileName: "文件1",
  },
  {
    key: "1",
    xuhao: "2",
    fileName: "文件2",
  },
];
const ryxxForm = {
  bgname: "",
  bgidcard: "",
  bgphonenum: "",
  bgtype: "",
};
const jbxxForm = {
  tyshxydm: "",
  dwmc: "",
  qydz: "",
  cjsj: "",
  frxm: "",
  frsfz: "",
  lxr: "",
  lxdh: "",
  bgzsbh: "",
  bgzsyxq: "",
  bgzzdj: "",
  bgfzbm: "",
  bgdchyywfw: "",
  bgzyfw: "",
};
const rules = {
  bgwtgyy: [{ required: true, message: "不能为空", trigger: "blur" }],
};
const jjyyForm = {
  bgwtgyy: "",
};
const bgtypeArr = [
  {
    title: "注册测绘师",
    value: "0",
  },
  {
    title: "高级工程师",
    value: "1",
  },
];

const zzzsData = [
  {
    grzl: "身份证扫描件",
  },
  {
    grzl: "资格证扫描件",
  },
  {
    grzl: "作业证扫描件",
  },
  {
    grzl: "劳动合同扫描件",
  },
  {
    grzl: "社保证明扫描件",
  },
  {
    grzl: "其他材料",
  },
];
export default {
  name: "chdwzc",
  components: {
    headers,
    bottom,
    navbar,
    Upload,
  },
  data() {
    return {
      flag: false,
      rules,
      zzzsData,
      readonly: true,
      bgtypeArr,
      fileList: [],
      bgdchyywfw: [],
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      id: null,
      data: [],
      count: 3,
      dataSource,
      visible: false,
      jjVisibleDialog: false,
      ryxxForm,
      jbxxForm,
      jjyyForm,
      //用户信息，用来渲染注册页面上面基本信息
      userInfo: {},
      userRole: "",
      logjson: {},
      companydate: {},
      bgstate: "",
      msg: "",
      val: "",

      // 人员附件相关
      ryrootpath: "",
      rypathId: "",

      // 企业单位资质
      qypathId: "",
      qyrootpath: "",
    };
  },
  computed: {},
  created() {
    this.userInfo = this.$store.state.userInfo;
    // 获取个人信息
    if (window.localStorage.getItem("userRole")) {
      this.userRole = window.localStorage.getItem("userRole");
    }
  },
  mounted() {
    if (this.userRole.indexOf("初审人") > -1) {
      this.chushenDetail();
    } else if (this.userRole.indexOf("终审人") > -1) {
      this.zhongshenDetail();
    }
  },
  methods: {
    //  初审详情
    chushenDetail() {
      let bgdid = this.$route.query.bgdid;
      this.$http.post("zzxx/selectBgZzxx", { bgdid: bgdid }).then((res) => {
        let personallist = _.cloneDeep(res.data.personallist);
        _.each(personallist, (v) => {
          v.bgbid = res.data.zzxxdate.bgbid;
          v.logid = res.data.zzxxdate.logid;
        });
        let obj = { ...res.data.zzxxdate, ...res.data.companydate };
        delete obj.zzxx;
        this.data = personallist;
        this.jbxxForm = { ...obj };

        let result = uploadsearch(this.jbxxForm.bgzzfj);
        result.then((res) => {
          this.qypathId = res.data.id;
          this.qyrootpath = res.data.path;
          window.localStorage.setItem("xzfjid", this.jbxxForm.bgzzfj);
        });

        this.bgstate = res.data.zzxxdate.bgstate;
        this.companydate = { ...res.data.companydate };
        this.bgdchyywfw = res.data.zzxxdate.bgdchyywfw
          ? res.data.zzxxdate.bgdchyywfw.split(",")
          : [];
      });
    },
    //  终审详情
    zhongshenDetail() {
      let bgdid = this.$route.query.bgdid;
      this.$http.post("zzxx/selectcsBgZzxx", { bgdid: bgdid }).then((res) => {
        let personallist = _.cloneDeep(res.data.personallist);
        _.each(personallist, (v) => {
          v.bgbid = res.data.zzxxdate.bgbid;
          v.logid = res.data.zzxxdate.logid;
        });
        let zzxxdate = res.data.zzxxdate;
        let companydate = res.data.companydate;
        let obj = { ...companydate, ...zzxxdate };
        delete obj.zzxx;
        this.jbxxForm = { ...obj };

        let result = uploadsearch(this.jbxxForm.bgzzfj);
        result.then((res) => {
          this.qypathId = res.data.id;
          this.qyrootpath = res.data.path;
          window.localStorage.setItem("xzfjid", this.jbxxForm.bgzzfj);
        });

        this.data = personallist;
        this.bgdchyywfw = zzxxdate.bgdchyywfw
          ? zzxxdate.bgdchyywfw.split(",")
          : [];
      });
    },
    commonParsm(url, bgstate, val) {
      let _this = this;
      const bgpersonallistjson = _.cloneDeep(_this.data);
      const bgzzxxjson = _.cloneDeep(_this.jbxxForm);
      let jsrxm = this.companydate.dwmc;
      let jsrid = this.companydate.id;
      if (bgstate.indexOf("待审核") > -1) {
        let logjson = {
          fsrxm: "初审人",
          fsrid: "4",
          jsrxm: val ? jsrxm : "终审人",
          jsrid: val ? jsrid : "5",
          dqhj: val ? val : "初审",
          clzt: "待办",
          sfyd: "未读",
        };
        _this.logjson = logjson;
        this.msg = val ? `初审${val}` : "初审";
      } else if (bgstate.indexOf("初审") > -1) {
        let logjson1 = {
          fsrxm: "终审人",
          fsrid: "5",
          jsrxm: val ? jsrxm : "办结",
          jsrid: val ? jsrid : "0",
          dqhj: val ? val : "办结",
          clzt: "已办",
          sfyd: "已读",
        };
        _this.logjson = logjson1;
        this.msg = "终审";
      }
      if (_this.bgdchyywfw.length === 0) {
        _this.$message.error("“多测合一”业务范围不能为空!");
        return false;
      }
      let params = {
        bgpersonallistjson: bgpersonallistjson,
        bgzzxxjson: bgzzxxjson,
        logjson: _this.logjson,
      };

      _this.$http.post(url, params).then((res) => {
        // _this.$message({
        //   message: `${this.msg}通过`,
        //   type: "success",
        // });
        // const { href } = _this.$router.resolve({
        //   name: "zzsh",
        // });
        // window.open(href, "_blank");
      });
    },
    handlePass1(val) {
      this.$confirm("是否确认执行同意操作?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let _this = this;
          const bgpersonallistjson = _.cloneDeep(_this.data);
          const bgzzxxjson = _.cloneDeep(_this.jbxxForm);
          if (_this.bgdchyywfw.length === 0) {
            _this.$message.error("“多测合一”业务范围不能为空!");
            return false;
          }
          //  初审
          if (bgzzxxjson.bgstate.indexOf("待审核") > -1) {
            let logjson = {
              fsrxm: "初审人",
              fsrid: "4",
              jsrxm: "终审人",
              jsrid: "5",
              dqhj: "初审",
              clzt: "待办",
              sfyd: "未读",
            };
            let params = {
              bgpersonallistjson: bgpersonallistjson,
              bgzzxxjson: bgzzxxjson,
              logjson: logjson,
            };
            _this.$http.post("zzxx/updatecsBgzzxx", params).then((res) => {
              // _this.$message({
              //   message: "初审通过",
              //   type: "success",
              // });
              // const { href } = _this.$router.resolve({
              //   name: "zzsh",
              // });
              this.flag = true;
              doMessage("审核成功！", true);
              this.$router.go(-1);
            });
            //  终审
          } else if (bgzzxxjson.bgstate.indexOf("初审") > -1) {
            let logjson1 = {
              fsrxm: "终审人",
              fsrid: "5",
              jsrxm: "办结",
              jsrid: "0",
              dqhj: "办结",
              clzt: "已办",
              sfyd: "已读",
            };
            let zsparams = {
              bgpersonallistjson: bgpersonallistjson,
              bgzzxxjson: bgzzxxjson,
              logjson: logjson1,
            };
            _this.$http.post("zzxx/updateBgzzxx", zsparams).then((res) => {
              // _this.$message({
              //   message: "终审通过！",
              //   type: "success",
              // });
              // const { href } = _this.$router.resolve({
              //   name: "zzsh",
              // });
              // window.open(href, "_blank");
              this.flag = true;
              doMessage("审核通过成功！", true);
              this.$router.go(-1);
            });
          }
        })
        .catch(() => {});
    },

    //  拒绝
    handleRefuse(val) {
      this.$confirm("是否继续执行不通过操作?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.jjVisibleDialog = true;
          this.val = val;
        })
        .catch(() => {});
    },

    // 填写拒绝原因
    handleFormJjyy() {
      let _this = this;
      this.$refs.jjyyForm.validate((valid) => {
        if (valid) {
          let bgstate = _this.bgstate;
          let val = _this.val;
          _this.jbxxForm.bgwtgyy = _this.jjyyForm.bgwtgyy;
          _this.$nextTick(() => {
            _this.commonParsm("zzxx/updatethBgzzxx", bgstate, val);
            this.flag = true;
            doMessage("拒绝成功!", true);
            this.$router.go(-1);
          });
          _this.jjVisibleDialog = false;
        } else {
          return false;
        }
      });
    },

    // 取消填写拒绝原因弹框
    clearJjyy() {
      this.jjVisibleDialog = false;
    },
    changeJs(val) {
      let obj = {};
      obj = this.bgtypeArr.find((item) => {
        //这里的bgtypeArr就是上面遍历的数据源
        return item.value === val; //筛选出匹配数据
      });
      this.ryxxForm.bgtype = obj.title;
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    //  查看附件
    viewFj(rowData) {
      debugger;
      this.ryxxForm = rowData;
      if (rowData.id == this.ryxxForm.id) {
        let result = uploadsearch(this.ryxxForm.bginfor);
        result.then((res) => {
          this.rypathId = res.data.id;
          this.ryrootpath = res.data.path;
          if (window.localStorage.getItem("xzfjids")) {
            window.localStorage.removeItem("xzfjids");
          }
          window.localStorage.setItem("xzfjids", this.ryxxForm.bginfor);
          this.visible = true;
        });
      }
    },
  },

  onDelete(index, key) {
    const dataSource = [...this.dataSource];
    this.dataSource = dataSource.filter((item) => item.key !== key);
  },
};
</script>

<style scoped>
.chdwzc {
  width: 100%;
  height: 100%;
  overflow: auto;
}
.content {
  width: 100%;
  padding: 0 20%;
}
.breadcrumb {
  height: 40px;
  display: flex;
  margin-bottom: 18px;
  padding: 0px 3rem;
  align-items: center;
  background: #269fd6;
}

.chdwzc-content {
  padding: 0 0%;
}

.htxx-titlee {
  padding: 0 0 10px 0px;
  border-bottom: 1px solid;
}

.htxx-titlee {
  margin-bottom: 20px;
}
.left-btn button:last-child {
  margin: 0 10px;
}

.img {
  padding-left: 10px;
  width: 30px;
  padding-right: 0px;
}

.global-right-btn button:last-child {
  margin-right: 0;
}

.zzzs {
  margin-top: -70px;
}
.zzzsstyle >>> .el-form-item__content {
  line-height: 20px;
}
.dchyywfw >>> .el-checkbox__input.is-disabled + span.el-checkbox__label {
  color: rgb(102, 96, 96);
}
.zyfw >>> .el-textarea.is-disabled .el-textarea__inner {
  background-color: #f5f7fa;
  border-color: #e4e7ed;
  color: rgb(102, 96, 96);
}
.dchyywfw
  >>> .el-checkbox__input.is-disabled.is-checked
  .el-checkbox__inner::after {
  border-color: #000;
}
</style>