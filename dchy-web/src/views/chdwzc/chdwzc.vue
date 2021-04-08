<template>
  <div class="chdwzc">
    <headers></headers>
    <navbar></navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <div>当前位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>测绘单位注册</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="content">
      <!-- 主体 -->
      <div class="chdwzc-content">
        <!-- <el-col :span="24" v-if="readonly">
          <div class="global-sxtitle">
            <span
              >您的资质审核已提交至相应处室审核，审核时间为3-5个工作日，请耐心等待结果。</span
            >
          </div>
        </el-col> -->

        <div>
          <div class="global-flag">
            <span style="color: #0091ff">基本信息</span>
            <!-- <el-button
              type="primary"
              style="height: 30px; line-height: 6px"
              v-if="state.indexOf('正常') > 0"
              >变更</el-button
            > -->
          </div>
          <el-form
            ref="jbxxForm"
            :model="jbxxForm"
            :rules="rules"
            autocomplete="off"
            label-width="180px"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="统一社会信用代码" prop="tyshxydm">
                  <el-input
                    type="tyshxydm"
                    v-model="jbxxForm.tyshxydm"
                    :disabled="isReadonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="单位名称" prop="dwmc">
                  <el-input
                    type="dwmc"
                    v-model="jbxxForm.dwmc"
                    :disabled="isReadonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="单位地址" prop="qydz">
                  <el-input
                    type="qydz"
                    v-model="jbxxForm.qydz"
                    :disabled="isqydz"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="注册时间" prop="cjsj">
                  <el-date-picker
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="选择日期"
                    v-model="jbxxForm.cjsj"
                    :disabled="iscjsj"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人姓名" prop="frxm">
                  <el-input
                    type="frxm"
                    v-model="jbxxForm.frxm"
                    :disabled="isReadonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人身份证号" prop="frsfz">
                  <el-input
                    type="frsfz"
                    v-model="jbxxForm.frsfz"
                    :disabled="isReadonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="联系人" prop="lxr">
                  <el-input
                    type="lxr"
                    v-model="jbxxForm.lxr"
                    :disabled="islxr"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="联系电话" prop="lxdh">
                  <el-input
                    type="lxdh"
                    v-model="jbxxForm.lxdh"
                    :disabled="islxdh"
                  />
                </el-form-item>
              </el-col>

              <!-- 测绘资质信息 -->
              <div class="global-flag" style="color: #0091ff">测绘资质信息</div>
              <el-col :span="12">
                <el-form-item label="测绘资质证书编号" prop="bgzsbh">
                  <el-input type="bgzsbh" v-model="jbxxForm.bgzsbh" />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="证书有效期" prop="bgzsyxq">
                  <el-date-picker
                    v-model="jbxxForm.bgzsyxq"
                    :picker-options="pickerOptions"
                    type="date"
                    :editable="false"
                  ></el-date-picker>
                  <!-- <el-input type="bgzsyxq" v-model="jbxxForm.bgzsyxq" /> -->
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="资质等级" prop="bgzzdj">
                  <el-select
                    v-model="jbxxForm.bgzzdj"
                    clearable
                    placeholder="请选择资质等级"
                  >
                    <el-option label="甲级" value="甲级"></el-option>
                    <el-option label="乙级" value="乙级"></el-option>
                    <el-option label="丙级" value="丙级"></el-option>
                    <el-option label="丁级" value="丁级"></el-option>
                  </el-select>
                  <!-- <el-input type="bgzzdj" v-model="jbxxForm.bgzzdj" /> -->
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="发证部门" prop="bgfzbm">
                  <el-input type="bgfzbm" v-model="jbxxForm.bgfzbm" />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="“多测合一”业务范围" prop="bgdchyywfw">
                  <el-checkbox-group v-model="bgdchyywfw">
                    <el-checkbox label="项目用地测量阶段"></el-checkbox>
                    <el-checkbox label="工程实施测量阶段"></el-checkbox>
                    <el-checkbox label="竣工及不动产登记测量"></el-checkbox>
                  </el-checkbox-group>
                  <!-- <el-input type="bgdchyywfw" v-model="jbxxForm.bgdchyywfw" /> -->
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="专业范围" prop="bgzyfw">
                  <el-input
                    placeholder="专业范围"
                    type="textarea"
                    v-model="jbxxForm.bgzyfw"
                    :rows="6"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="资质证书" class="zzzsstyle">
                  <Upload
                    v-if="this.pathId"
                    :download="{
                      LOGDQHJ: 'chdwzc_zzzs',
                      id: pathId,
                      rootpath: qyrootpath,
                    }"
                    ref="fjzzbg"
                  ></Upload>
                </el-form-item>
              </el-col>

              <!-- 人员信息 -->
              <div class="global-flag" style="color: #0091ff">人员信息</div>
              <el-col :span="24">
                <div class="global-dc">
                  <div class="left-btn">
                    <el-button type="primary" @click="addPersonInfo">
                      <i class="el-icon-plus"></i>
                      添加人员
                    </el-button>
                    <!-- <el-button type="danger" >
                      <i class="el-icon-close"></i>
                      批量删除
                    </el-button> -->
                  </div>
                  <!-- <div class="right-btn">
                    <el-button type="primary" @click="downloadRyzl"
                      >人员资料下载</el-button
                    >
                  </div> -->
                </div>
              </el-col>

              <!-- 表格 -->
              <el-col :span="24">
                <el-table
                  border
                  ref="multipleTable"
                  :data="data"
                  tooltip-effect="dark"
                  :header-cell-style="{ background: '#0091ff', color: '#fff' }"
                  style="width: 100%"
                  @selection-change="handleSelectionChange"
                >
                  <el-table-column
                    type="selection"
                    width="55"
                  ></el-table-column>
                  <el-table-column
                    label="姓名"
                    prop="bgname"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="bgidcard"
                    label="身份证号"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="bgphonenum"
                    label="联系电话"
                    align="center"
                    show-overflow-tooltip
                  ></el-table-column>
                  <el-table-column
                    prop="bgtype"
                    label="角色"
                    align="center"
                  ></el-table-column>
                  <el-table-column label="操作" align="center" width="180">
                    <template slot-scope="scope">
                      <div class="operation-style">
                        <span
                          @click="editRyxx(scope.row)"
                          style="cursor: pointer"
                        >
                          <i class="el-icon-edit-outline"></i>
                          <span>编辑</span>
                        </span>
                        <span
                          @click="delqlrInfo(scope.row)"
                          style="cursor: pointer"
                        >
                          <img src="@/assets/img/del.png" class="img" />删除
                        </span>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>

              <el-col :span="24">
                <div class="global-right-btn">
                  <!-- <el-button type="primary" @click="saveChdwzc()"
                    >保存</el-button
                  > -->
                  <el-button
                    type="primary"
                    @click="tjzc"
                    :disabled="flag ? true : false"
                    >提交注册</el-button
                  >
                </div>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
    </div>
    <el-dialog
      :title="title"
      :visible.sync="visible"
      width="45%"
      :destroy-on-close="true"
      v-if="visible"
    >
      <el-form
        ref="ryxxForm"
        :model="ryxxForm"
        :rules="rulesRyxx"
        autocomplete="off"
        label-width="100px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="bgname">
              <el-input type="bgname" v-model="ryxxForm.bgname" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="身份证号" prop="bgidcard">
              <el-input type="bgidcard" v-model="ryxxForm.bgidcard" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="联系电话" prop="bgphonenum">
              <el-input type="bgphonenum" v-model="ryxxForm.bgphonenum" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="角色" prop="bgtype">
              <el-select
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
            <el-form-item label="资质证书" class="zzzsstyle">
              <Upload
                :download="{
                  LOGDQHJ: 'chdwzc_zzzs_tjry',
                  id: rypathId,
                  rootpath: ryrootpath,
                }"
                ref="fjzzbg_ryxx"
                v-if="rypathId"
              ></Upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="info" @click="clear">取消</el-button>
        <el-button type="primary" @click="handleForm">确认</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt />
    </el-dialog>
    <bottom></bottom>
  </div>
</template>

<script>
import headers from "@/components/Header.vue";
import bottom from "@/components/Bottom.vue";
import navbar from "@/components/NavBar.vue";
import { guid } from "@/assets/js/common.js";
import Upload from "@/components/Upload.vue";
import {
  downloadFile,
  closePage,
  doMessage,
  uploadsearch,
  uploadcreate,
} from "@/assets/js/common.js";
import {
  attachmentUpload,
  templateName,
  callService,
} from "@/assets/js/config.js";

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
const rules = {
  tyshxydm: [{ required: true, message: "不能为空", trigger: "blur" }],
  dwmc: [{ required: true, message: "不能为空", trigger: "blur" }],
  qydz: [{ required: true, message: "不能为空", trigger: "blur" }],
  cjsj: [{ required: true, message: "不能为空", trigger: "blur" }],
  frxm: [{ required: true, message: "不能为空", trigger: "blur" }],
  frsfz: [
    { required: true, message: "不能为空", trigger: "blur" },
    {
      pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
      message: "证件号码格式有误！",
      trigger: "blur",
    },
  ],
  lxr: [{ required: true, message: "不能为空", trigger: "blur" }],
  lxdh: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgzsbh: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgzsyxq: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgzzdj: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgfzbm: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgdchyywfw: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgzyfw: [{ required: true, message: "不能为空", trigger: "blur" }],
};

const rulesRyxx = {
  bgname: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgidcard: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgphonenum: [{ required: true, message: "不能为空", trigger: "blur" }],
  bgtype: [{ required: true, message: "不能为空", trigger: "blur" }],
};
const zzzsData = [
  {
    name: "身份证扫描件",
  },
  {
    name: "资格证扫描件",
  },
  {
    name: "作业证扫描件",
  },
  {
    name: "劳动合同扫描件",
  },
  {
    name: "社保证明扫描件",
  },
  {
    name: "其他材料",
  },
];
const ryzzzsData = [
  {
    name: "身份证扫描件",
  },
  {
    name: "资格证扫描件",
  },
  {
    name: "作业证扫描件",
  },
  {
    name: "劳动合同扫描件",
  },
  {
    name: "社保证明扫描件",
  },
  {
    name: "其他材料",
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
      zzzsData,
      ryzzzsData,
      rules,
      rulesRyxx,
      isqydz: false,
      iscjsj: false,
      islxr: false,
      islxdh: false,
      isReadonly: true,
      readonly: false,
      bgtypeArr,
      fileList: [],
      bgdchyywfw: [],
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      id: null,
      title: null,
      data: [],
      count: 3,
      dataSource,
      visible: false,
      ryxxForm,
      jbxxForm,
      //用户信息，用来渲染注册页面上面基本信息
      userInfo: {},
      userinfo: {},
      state: "",

      //企业根目录路径
      qyrootpath: "",
      //个人根目录
      ryrootpath: "",
      // 资质目录id
      pathId: "",
      // 人员目录id
      rypathId: "",
    };
  },
  computed: {
    pickerOptions() {
      let _this = this;
      const value = _this
        .$moment(new Date() - 24 * 60 * 60 * 1000)
        .format("YYYY-MM-DD 23:59:59");
      return {
        disabledDate(zsyxq) {
          zsyxq = _this.$moment(new Date(zsyxq)).format("YYYY-MM-DD 00:00:00");
          return value > zsyxq;
        },
      };
    },
  },
  mounted() {
    // 获取用户信息
    if (window.localStorage.getItem("userinfo")) {
      debugger;
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    this.getJbxxInfo();
  },
  methods: {
    //  获取基本信息
    getJbxxInfo() {
      let did = this.userinfo.id;
      this.$http.post("zzxx/selectZzxx", { did: did }).then((res) => {
        let obj = { ...res.data.bgzzxxdate, ...res.data.companydate };
        this.state = res.data.bgzzxxdate ? res.data.bgzzxxdate.bgstate : "";
        delete obj.id;
        delete obj.zzxx;
        this.jbxxForm = { ...obj };

        if (this.jbxxForm.qydz != null && this.jbxxForm.qydz != "") {
          this.isqydz = true;
        }
        if (this.jbxxForm.cjsj != null && this.jbxxForm.cjsj != "") {
          this.iscjsj = true;
        }
        if (this.jbxxForm.lxr != null && this.jbxxForm.lxr != "") {
          this.islxr = true;
        }
        if (this.jbxxForm.lxdh != null && this.jbxxForm.lxdh != "") {
          this.islxdh = true;
        }
      });
    },
    uploadZzzs(row) {},
    saveChdwzc(formName) {
      this.$refs.jbxxForm.validate((valid) => {
        if (valid) {
        } else {
          if (this.bgdchyywfw.length === 0) return false;
        }
      });
    },
    changeJs(val) {
      let obj = {};
      obj = this.bgtypeArr.find((item) => {
        //这里的bgtypeArr就是上面遍历的数据源
        return item.value === val; //筛选出匹配数据
      });
      this.ryxxForm.bgtype = obj.title;
    },
    //  人员资料下载
    downloadRyzl() {},
    //  选中复选框的数组值
    handleSelectionChange(val) {},
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    //  上传文件
    beforeUpload(file) {
      this.zzzsData.push(file);
    },

    //提交注册
    tjzc() {
      let _this = this;
      // 做必填验证
      // 三部分，人员+附件+表单
      let check = true;
      if (window.localStorage.getItem("fjList")) {
        _this.fjList = JSON.parse(window.localStorage.getItem("fjList"));
      }
      //人员
      if (_this.data.length < 1) {
        check = false;
        return doMessage("人员不能为空", false);
      }

      // 验证资质证书相关
      if (window.localStorage.getItem("fjList")) {
        let arr = JSON.parse(window.localStorage.getItem("fjList"));
        for (let item of arr) {
          if (item.filename != "其他材料" && item.children.length == 0) {
            this.$message({
              message: `请上传${item.filename}`,
              type: "error",
            });
            return;
          }
        }
      } else {
        this.$message({
          message: `请上传资质附件`,
          type: "error",
        });
      }

      //  人员参数
      let bgpersonallistjson = _.cloneDeep(_this.data);
      //  资质信息参数
      _this.jbxxForm.bgzzfj = _this.pathId;
      _this.jbxxForm.bgstate = "待审核";
      _this.jbxxForm.bgdid = this.userinfo.id;
      _this.jbxxForm.bgzsyxq = _this
        .$moment(_this.jbxxForm.bgzsyxq)
        .format("YYYY-MM-DD");
      let bgsqsj = _this.$moment(new Date()).format("YYYY-MM-DD");
      let dchyywfw = "";
      if (_this.bgdchyywfw.length === 0) {
        _this.$message.error("“多测合一”业务范围不能为空!");
        return false;
      }
      _.each(_this.bgdchyywfw, (v) => {
        dchyywfw = dchyywfw ? dchyywfw + "," + v : v;
      });
      _this.jbxxForm.bgdchyywfw = dchyywfw;
      _this.jbxxForm.bgsqsj = bgsqsj;
      let bgzzxxjson = _this.jbxxForm;
      _this.jbxxForm.cjsj = new Date(_this.jbxxForm.cjsj).getTime();
      //  日志参数
      let fsrxm = _this.userinfo.dwmc;
      let fsrid = _this.userinfo.id;
      let logjson = {
        fsrxm: fsrxm,
        fsrid: fsrid,
        jsrxm: "初审人",
        jsrid: "4",
        dqhj: "申请",
        clzt: "待办",
        sfyd: "未读",
      };
      let params = {
        bgpersonallistjson: bgpersonallistjson,
        bgzzxxjson: bgzzxxjson,
        logjson: logjson,
        companyjson: this.jbxxForm,
      };

      _this.$refs.jbxxForm.validate((valid) => {
        if (valid) {
          this.$confirm("是否提交注册?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              _this.$http.post("zzxx/addBgZzxx", params).then(
                async (res) => {
                  // _this.$message({
                  //   message: "注册成功！",
                  //   type: "success",
                  //   duration: 2000,
                  // });
                  await this.$refs.fjzzbg.uploadFun();
                  await this.$refs.fjzzbg.delall();
                  _this.$nextTick(() => {
                    // const { href } = _this.$router.resolve({
                    //   name: "index",
                    // });
                    this.flag = true;
                    doMessage("注册成功！", true);
                    window.localStorage.setItem("bgzzxxdate", 0);
                    this.$router.go(-1);
                  });
                },
                (err) => {
                  _this.$message.error("注册失败！");
                }
              );
            })
            .catch(() => {});
        }
      });
    },
    //验证人员材料必填
    valHuman() {
      let _this = this;
      if (window.localStorage.getItem("fjLists")) {
        _this.fjLists = JSON.parse(window.localStorage.getItem("fjLists"));
      }

      if (_this.fjLists) {
        if (_this.fjLists.length && _this.fjLists.length > 0) {
          for (let item of _this.fjLists) {
            if (
              item.filename != "其他材料" &&
              item.children &&
              item.children.length == 0
            ) {
              doMessage(`${item.filename}必上传`, false);
              return false;
            }
          }
        }
      } else {
        doMessage("人员附件材料必填", false);
        return false;
      }
      return true;
    },
    // 添加人员信息
    handleForm() {
      let _this = this;
      if (_this.title === "添加人员信息" || _this.title === "编辑信息") {
        _this.$refs.ryxxForm.validate(async (valid) => {
          if (valid) {
            // 验证人员材料必填
            if (!this.valHuman()) {
              return;
            }
            _this.ryxxForm.bginfor = _this.rypathId;
            _this.ryxxForm.bgstate = "待审核";
            _this.ryxxForm.bgdid = _this.userinfo.id;
            _this.ryxxForm.roleid = "0";
            let obj = _.cloneDeep(_this.ryxxForm);
            if (_this.title === "添加人员信息") {
              _this.data.push(obj);
            }
            await this.$refs.fjzzbg_ryxx.uploadFun();
            debugger;
            await this.$refs.fjzzbg_ryxx.delall();
            _this.visible = false;
          } else {
            return false;
          }
        });
      } else {
        _this.visible = false;
      }
    },
    editRyxx(record) {
      this.id = record.id;
      this.title = "编辑信息";
      this.rypathId = "";

      for (let item of this.data) {
        if (record.bgname == item.bgname) {
          if (item.infor) {
            window.localStorage.setItem("xzfjids", item.infor);
            let result = uploadsearch(item.infor);
            result.then((res) => {
              this.rypathId = res.data.id;
              this.ryrootpath = res.data.path;

              this.id = record.id;
              this.title = "编辑信息";
              this.$nextTick(() => {
                this.ryxxForm = record;
              });
              this.visible = true;
              return;
            });
          }
          if (item.bginfor) {
            window.localStorage.setItem("xzfjids", item.bginfor);
            let result = uploadsearch(item.bginfor);
            result.then((res) => {
              this.rypathId = res.data.id;
              this.ryrootpath = res.data.path;

              this.id = record.id;
              this.title = "编辑信息";
              this.$nextTick(() => {
                this.ryxxForm = record;
              });
              this.visible = true;
              return;
            });
          }
        }
      }
    },
    //  删除
    delqlrInfo(record) {
      this.data = this.data.filter((item) => item.bgname !== record.bgname);
      this.$message({
        type: "success",
        message: "删除成功！",
        duration: 2000,
      });
    },
    //  取消
    clear() {
      this.visible = false;
    },
    //  添加人员
    async addPersonInfo() {
      // 创建人员信息文件夹
      // this.ryrootpath = `DCHY/人员信息/${guid()}`;
      this.ryrootpath = `${attachmentUpload.ryxx}/${guid()}`;
      this.rypathId = "";
      let result = uploadcreate(this.ryrootpath);
      result.then((res) => {
        this.rypathId = res.data.data.id;
        window.localStorage.setItem("xzfjids", this.rypathId);
        this.id = null;
        this.title = "添加人员信息";
        this.ryxxForm = {};
        this.visible = true;
      });
    },
    cjzzwjj() {
      //创建资质证书文件夹
      // this.qyrootpath = `DCHY/资质信息/${guid()}`;
      this.qyrootpath = `${attachmentUpload.zzxx}/${guid()}`;
      let fileFormData = new FormData();
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      fileFormData.append("filepath", this.qyrootpath);
      this.$http
        .post("ftpService/fileUpload", fileFormData, requestConfig)
        .then((res) => {
          this.pathId = res.data.data.id;
        });
    },
  },
  created() {
    this.cjzzwjj();
    this.userInfo = this.$store.state.userInfo;
    // 附件信息
    if (window.localStorage.getItem("fjList")) {
      this.fjList = JSON.parse(window.localStorage.getItem("fjList"));
    }
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
  /* padding-bottom: 150px; */
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
</style>