<template>
  <div class="chdwzc">
    <headers></headers>
    <navbar></navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <div>当前位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资质审核进度</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="content">
      <!-- 主体 -->
      <div class="chdwzc-content">
        <el-col :span="24">
          <div class="global-sxtitle-danger">
            <span style="color: red; font-weight: bold">{{
              bgwtgyy
                ? `您的资质审核不通过，原因：${bgwtgyy}`
                : "已提交,审核时间为3-5个工作日，请耐心等待结果。"
            }}</span>
          </div>
        </el-col>

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
                    :disabled="true"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="单位名称" prop="dwmc">
                  <el-input
                    type="dwmc"
                    v-model="jbxxForm.dwmc"
                    :disabled="true"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="单位地址" prop="qydz">
                  <el-input type="qydz" v-model="jbxxForm.qydz" :disabled="jbxxForm.bgwtgyy==null"/>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="注册时间" prop="cjsj">
                  <el-date-picker
                    v-model="jbxxForm.cjsj"
                    type="date"
                    :editable="false"
                    :disabled="jbxxForm.bgwtgyy==null"
                  ></el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人姓名" prop="frxm">
                  <el-input
                    type="frxm"
                    v-model="jbxxForm.frxm"
                    :disabled="true"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人身份证号" prop="frsfz">
                  <el-input
                    type="frsfz"
                    v-model="jbxxForm.frsfz"
                    :disabled="true"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="联系人" prop="lxr">
                  <el-input type="lxr" v-model="jbxxForm.lxr" :disabled="jbxxForm.bgwtgyy==null"/>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="联系电话" prop="lxdh">
                  <el-input type="lxdh" v-model="jbxxForm.lxdh" :disabled="jbxxForm.bgwtgyy==null"/>
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

              <!-- <el-col :span="12">
                <el-form-item label="资质等级" prop="bgzzdj">
                  <el-input
                    type="bgzzdj"
                    v-model="jbxxForm.bgzzdj"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col> -->

              <el-col :span="12">
                <el-form-item label="资质等级" prop="bgzzdj">
                  <el-select
                    v-model="jbxxForm.bgzzdj"
                    type="bgzzdj"
                    :disabled="readonly"
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
                      style="color: #000"
                      label="项目用地测量阶段"
                      :disabled="readonly"
                    ></el-checkbox>
                    <el-checkbox
                      label="工程实施测量阶段"
                      :disabled="readonly"
                    ></el-checkbox>
                    <el-checkbox
                      label="竣工及不动产登记测量"
                      :disabled="readonly"
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
                <el-form-item label="资质证书">
                  <Upload
                    :download="{
                      alldata,
                      LOGDQHJ: 'jdcx',
                      id: zzpathId,
                      rootpath: zzrootpath,
                    }"
                    v-if="zzrootpath && !state_pd"
                  ></Upload>
                  <Upload
                    :download="{
                      alldata,
                      LOGDQHJ: 'jdcx_tuihui',
                      id: zzpathId,
                      rootpath: zzrootpath,
                    }"
                    v-if="zzrootpath && state_pd"
                    ref="fjzzbg"
                  ></Upload>
                </el-form-item>
              </el-col>
              <!-- 人员信息 -->
              <div class="global-flag">人员信息</div>
              <el-col :span="24" v-if="!readonly">
                <div class="global-dc">
                  <div class="left-btn">
                    <el-button type="primary" @click="addPersonInfo">
                      <i class="el-icon-plus"></i>
                      添加人员
                    </el-button>
                  </div>
                </div>
              </el-col>
              <!-- 表格 -->
              <el-col :span="24">
                <el-table
                  border
                  ref="multipleTable"
                  :data="data"
                  style="width: 100%"
                  @selection-change="handleSelectionChange"
                  :header-cell-style="{ background: '#0091ff', color: '#fff' }"
                >
                  <el-table-column
                    type="selection"
                    width="55"
                    v-if="!readonly"
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
                  <el-table-column
                    label="操作"
                    align="center"
                    width="180"
                    v-if="readonly"
                  >
                    <template slot-scope="scope">
                      <div>
                        <el-button type="primary" @click="viewFj(scope.row)"
                          >查看附件</el-button
                        >
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="操作"
                    align="center"
                    width="180"
                    v-else
                  >
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

              <el-col :span="24" v-if="!readonly">
                <div class="global-right-btn">
                  <el-button
                    type="primary"
                    @click="handlePass1"
                    :disabled="flag ? true : false"
                    >提交修改</el-button
                  >
                  <!-- <el-button
                    size="mini"
                    type="danger"
                    @click="handleRefuse()"
                    :disabled="!readonly"
                    >拒绝</el-button
                  > -->
                </div>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
    </div>
    <el-dialog :title="title" :visible.sync="visible" width="45%">
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
                v-model="ryxxForm.bgtype"
                placeholder="请选择角色"
                @change="changeJs($event)"
                :disabled="readonly"
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
            <el-form-item label="资质证书">
              <Upload
                :download="{
                  alldata,
                  LOGDQHJ: 'jdcx_ryxx',
                  id: rypathId,
                  rootpath: ryrootpath,
                }"
                v-if="visible && !state_pd && rypathId"
              ></Upload>
              <Upload
                :download="{
                  alldata,
                  LOGDQHJ: 'jdcx_ryxx_tuihui',
                  id: rypathId,
                  rootpath: ryrootpath,
                }"
                v-if="visible && state_pd && rypathId"
                ref="fjzzbg_ryxx"
              ></Upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer" v-if="!readonly">
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
import Upload from "@/components/Upload.vue";
import { guid } from "@/assets/js/common.js";
import {
  attachmentUpload,
  templateName,
  callService,
} from "@/assets/js/config.js";
import {
  downloadFile,
  closePage,
  doMessage,
  uploadsearch,
  uploadcreate,
} from "@/assets/js/common.js";
// import { parse } from "qs";
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
    wjm: "身份证扫描件",
  },
  {
    wjm: "资格证扫描件",
  },
  {
    wjm: "作业证扫描件",
  },
  {
    wjm: "劳动合同扫描件",
  },
  {
    wjm: "社保证明扫描件",
  },
  {
    wjm: "其他材料",
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
      bgwtgyy: "",
      title: "",
      zzzsData,
      readonly: true,
      isShowFj: true,
      bgtypeArr,
      fileList: [],
      bgdchyywfw: [],
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      data: [],
      count: 3,
      dataSource,
      visible: false,
      ryxxForm,
      jbxxForm,
      rules,
      rulesRyxx,
      //用户信息，用来渲染注册页面上面基本信息
      userInfo: {},
      userRole: "",
      userinfo: {},
      companydate: {},

      alldata: "",

      // 进度或退回
      state_pd: "",

      zzrootpath: "",
      ryrootpath: "",
      zzpathId: "",
      rypathId: "",
    };
  },
  computed: {},
  created() {
    this.userInfo = this.$store.state.userInfo;

    // 获取个人信息
    if (window.localStorage.getItem("userRole")) {
      this.userRole = window.localStorage.getItem("userRole");
    }
    // 获取用户信息
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    // 附件信息
    if (window.localStorage.getItem("fjList")) {
      this.fjList = JSON.parse(window.localStorage.getItem("fjList"));
    }
  },
  mounted() {
    this.getJdcxDetail();
  },
  methods: {
    //  获取资质审核进度详情
    getJdcxDetail() {
      let did = this.userinfo.id;
      this.$http.post("zzxx/selectDwZzxx", { did: did }).then((res) => {
        this.alldata = res.data;

        if (res.data.zzxxdate.bgwtgyy == null) {
          this.state_pd = null;
        } else {
          this.state_pd = "退回";
        }

        this.zzfjcreate(this.alldata.zzxxdate.bgzzfj);

        let companydate = res.data.companydate;
        let zzxxdate = res.data.zzxxdate;
        let obj = { ...zzxxdate, ...companydate };
        this.data = res.data.personallist;
        this.jbxxForm = { ...obj };
        delete obj.zzxx;
        this.companydate = { ...res.data.companydate };
        this.bgdchyywfw = res.data.zzxxdate.bgdchyywfw
          ? res.data.zzxxdate.bgdchyywfw.split(",")
          : [];
        if (res.data.zzxxdate.bgwtgyy) {
          this.bgwtgyy = res.data.zzxxdate.bgwtgyy;
          this.readonly = !this.readonly;
        } else {
        }
      });
    },
    //  提交修改
    handlePass1() {
      let _this = this;

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
      }

      //  验证人员有没有

      if (this.data.length == 0) {
        this.$message({
          message: "请添加人员信息",
          type: "error",
        });
        return;
      }

      const bgpersonallistjson = _.cloneDeep(_this.data);
      const bgzzxxjson = _.cloneDeep(_this.jbxxForm);
      let jsrxm = this.companydate.dwmc;
      let jsrid = this.companydate.id;
      if (_this.bgdchyywfw.length === 0) {
        _this.$message.error("“多测合一”业务范围不能为空!");
        return false;
      }
      let dchyywfw = "";
      _.each(this.bgdchyywfw, (v) => {
        dchyywfw = dchyywfw ? dchyywfw + "," + v : v;
      });
      bgzzxxjson.bgdchyywfw = dchyywfw;
      let logjson = {
        fsrxm: "初审人",
        fsrid: "4",
        jsrxm: jsrxm,
        jsrid: jsrid,
        dqhj: "初审",
        clzt: "待办",
        sfyd: "未读",
      };
      let params = {
        bgpersonallistjson: bgpersonallistjson,
        bgzzxxjson: bgzzxxjson,
        logjson: logjson,
      };

      this.$confirm("是否提交修改?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          _this.$refs.jbxxForm.validate((valid) => {
            if (valid) {
              _this.$http
                .post("zzxx/updatewtBgzzxx", params)
                .then(async (res) => {
                  // _this.$message({
                  //   message: "提交成功",
                  //   type: "success",
                  //   duration: 2000,
                  // });
                  await this.$refs.fjzzbg.uploadFun();
                  await this.$refs.fjzzbg.delall();
                  _this.$nextTick(() => {
                    // const { href } = _this.$router.resolve({
                    //   name: "index",
                    // });
                    //window.open(href, "_blank");
                    this.flag = true;
                    doMessage("提交成功！", true);
                    this.$router.go(-1);
                  });
                });
            } else {
              return doMessage("请填写必填信息！", false);
            }
          });
        })
        .catch(() => {});
    },
    changeJs(val) {
      let obj = {};
      obj = this.bgtypeArr.find((item) => {
        //这里的bgtypeArr就是上面遍历的数据源
        return item.value === val; //筛选出匹配数据
      });
      this.ryxxForm.bgtype = obj.title;
    },

    //  选中复选框的数组值
    handleSelectionChange(val) {},
    //  查看附件
    viewFj(rowData) {
      this.ryrootpath = "";
      this.rypathId = "";
      this.title = "编辑信息";
      let fileFormData = new FormData();
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      fileFormData.append("folderid", rowData.bginfor);
      debugger;
      this.$http
        .post("ftpService/listFile2", fileFormData, requestConfig)
        .then((res) => {
          this.rypathId = res.data.id;
          this.ryrootpath = res.data.path;
          if (window.localStorage.getItem("xzfjids")) {
            window.localStorage.removeItem("xzfjids");
          }
          window.localStorage.setItem("xzfjids", rowData.bginfor);
          this.visible = true;
          this.isShowFj = false;
        })
        .catch((err) => {
          console.log(err);
        });
      this.$nextTick(() => {
        this.ryxxForm = rowData;
      });
    },

    //  添加人员
    async addPersonInfo() {
      window.localStorage.removeItem("xzfjids");
      this.rypathId = "";

      this.title = "添加人员信息";
      this.isShowFj = true;
      this.ryxxForm = {};

      //创建人员信息文件夹
      // this.ryrootpath = `DCHY/人员信息/${guid()}`;
      this.ryrootpath = `${attachmentUpload.ryxx}/${guid()}`;
      let result = uploadcreate(this.ryrootpath);
      result.then((res) => {
        this.rypathId = res.data.data.id;
        window.localStorage.setItem("xzfjids", this.rypathId);
        this.visible = true;
      });
    },
    editRyxx(record) {
      this.ryrootpath = "";
      this.rypathId = "";
      this.title = "编辑信息";

      for (let item of this.data) {
        if (record.name == item.name) {
          debugger;
          if (item.infor) {
            window.localStorage.setItem("xzfjids", item.infor);
            let result = uploadsearch(item.infor);
            result.then((res) => {
              this.rypathId = res.data.id;
              this.ryrootpath = res.data.path;
              // console.log(record);
              this.id = record.id;
              this.title = "编辑信息";
              this.$nextTick(() => {
                this.ryxxForm = record;
              });
              this.visible = true;
              this.isShowFj = false;
              return;
            });
          }
          if (item.bginfor) {
            window.localStorage.setItem("xzfjids", item.bginfor);
            let result = uploadsearch(item.bginfor);
            result.then((res) => {
              this.rypathId = res.data.id;
              this.ryrootpath = res.data.path;
              // console.log(record);
              this.id = record.id;
              this.title = "编辑信息";
              this.$nextTick(() => {
                this.ryxxForm = record;
              });
              this.visible = true;
              this.isShowFj = false;
              return;
            });
          }
        }
      }

      // let fileFormData = new FormData();
      // let requestConfig = {
      //   headers: {
      //     "Content-Type": "multipart/form-data",
      //   },
      // };
      // fileFormData.append("folderid", record.bginfor);
      // this.$http
      //   .post(
      //     // "http://222.90.207.221/dchy/dchy/ftpService/listFile2",
      //     "ftpService/listFile2",
      //     fileFormData,
      //     requestConfig
      //   )
      //   .then((res) => {
      //     this.rypathId = res.data.id;
      //     this.ryrootpath = res.data.path;
      //     if (window.localStorage.getItem("xzfjids")) {
      //       window.localStorage.removeItem("xzfjids");
      //     }
      //     window.localStorage.setItem("xzfjids", record.bginfor);
      //     this.visible = true;
      //     this.isShowFj = false;
      //   })
      //   .catch((err) => {
      //     console.log(err);
      //   });
      // this.$nextTick(() => {
      //   this.ryxxForm = record;
      // });
    },
    //  添加人员
    async handleForm() {
      debugger;
      if (window.localStorage.getItem("fjLists")) {
        debugger;
        let arr = JSON.parse(window.localStorage.getItem("fjLists"));
        for (let item of arr) {
          if (item.filename != "其他材料" && item.children.length == 0) {
            this.$message({
              message: `请上传${item.filename}`,
              type: "error",
            });
            return;
          }
        }
      }

      var _this = this;
      if (_this.title === "添加人员信息") {
        _this.ryxxForm.bginfor = _this.rypathId;
        _this.ryxxForm.infor = _this.rypathId;
        _this.ryxxForm.bgstate = "待审核";
        _this.ryxxForm.bgdid = _this.userinfo.id;

        let obj = _.cloneDeep(_this.ryxxForm);
        _this.data.push(obj);
        await this.$refs.fjzzbg_ryxx.uploadFun();
        await this.$refs.fjzzbg_ryxx.delall();
        _this.visible = false;
        // });
      } else if (_this.title === "编辑信息") {
        _this.ryxxForm.bginfor = _this.rypathId;
        _this.ryxxForm.infor = _this.rypathId;
        _this.ryxxForm.bgstate = "待审核";
        _this.ryxxForm.bgdid = _this.userinfo.id;

        let obj = _.cloneDeep(_this.ryxxForm);
        for (let item of _this.data) {
          if (item.name == obj.name) {
            item = obj;
          }
        }

        await this.$refs.fjzzbg_ryxx.uploadFun();
        await this.$refs.fjzzbg_ryxx.delall();
        _this.visible = false;
      } else {
        _this.visible = false;
      }
      // if (this.title.indexOf("添加人员信息") > -1) {
      //   this.ryxxForm.bginfor = this.rypathId;
      //   this.ryxxForm.bgbid = this.jbxxForm.bgbid;
      //   this.ryxxForm.logid = this.jbxxForm.logid;
      //   this.ryxxForm.bgdid = this.jbxxForm.bgdid;
      //   this.ryxxForm.bgstate = "待审核";
      //   this.data.push(this.ryxxForm);
      // }
      // this.visible = false;
    },

    //  取消
    clear() {
      this.visible = false;
    },
    //  删除
    delqlrInfo(record) {
      this.data = this.data.filter((item) => item.bgname !== record.bgname);
    },
    // 资质附件
    zzfjcreate(id) {
      let fileFormData = new FormData();
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      fileFormData.append("folderid", id);

      this.$http
        .post(
          // "http://222.90.207.221/dchy/dchy/ftpService/listFile2",
          "ftpService/listFile2",
          fileFormData,
          requestConfig
        )
        .then((res) => {
          this.zzpathId = res.data.id;
          this.zzrootpath = res.data.path;
          window.localStorage.setItem("xzfjid", id);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 人员附件
    rycreate(id) {
      let fileFormData = new FormData();
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      fileFormData.append("folderid", id);
      this.$http
        .post(
          // "http://222.90.207.221/dchy/dchy/ftpService/listFile2",
          "ftpService/listFile2",
          fileFormData,
          requestConfig
        )
        .then((res) => {
          this.rypathId = res.data.id;
          this.ryrootpath = res.data.path;
          if (window.localStorage.getItem("xzfjids")) {
            window.localStorage.removeItem("xzfjids");
          }
          window.localStorage.setItem("xzfjids", id);
        })
        .catch((err) => {
          console.log(err);
        });
    },
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
  padding: 0px 20% 20px;
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