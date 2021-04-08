<template>
  <div class="chdwzc">
    <headers></headers>
    <navbar></navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <div>当前位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资质变更</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="content">
      <!-- 主体 -->
      <div class="chdwzc-content">
        <!--<el-col :span="24" v-if="!readonly">
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
            > -->
            <el-button
              v-if="readonly"
              size="small"
              type="primary"
              @click="bgeng"
              >变更</el-button
            >
            <el-button
              v-if="!readonly"
              size="small"
              type="primary"
              @click="qxbgeng"
              >取消变更</el-button
            >
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
                    disabled
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="单位名称" prop="dwmc">
                  <el-input type="dwmc" v-model="jbxxForm.dwmc" disabled />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="单位地址" prop="qydz">
                  <el-input
                    type="qydz"
                    :disabled="readonly"
                    v-model="jbxxForm.qydz"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="注册时间" prop="cjsj">
                  <el-date-picker
                    v-model="jbxxForm.cjsj"
                    type="date"
                    :editable="false"
                    :disabled="readonly"
                  ></el-date-picker>
                  <!-- <el-input type="cjsj" v-model="jbxxForm.cjsj" /> -->
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人姓名" prop="frxm">
                  <el-input type="frxm" v-model="jbxxForm.frxm" disabled />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人身份证号" prop="frsfz">
                  <el-input type="frsfz" v-model="jbxxForm.frsfz" disabled />
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
                <el-form-item label="测绘资质证书编号" prop="zsbh">
                  <el-input
                    type="zsbh"
                    :disabled="readonly"
                    v-model="jbxxForm.zsbh"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="证书有效期" prop="zsyxq">
                  <el-date-picker
                    v-model="jbxxForm.zsyxq"
                    type="date"
                    :disabled="readonly"
                    :editable="false"
                  ></el-date-picker>
                  <!-- <el-input type="bgzsyxq" v-model="jbxxForm.bgzsyxq" /> -->
                </el-form-item>
              </el-col>

              <!-- <el-col :span="12">
                <el-form-item label="资质等级" prop="zzdj">
                  <el-input type="zzdj" v-model="jbxxForm.zzdj" />
                </el-form-item>
              </el-col> -->
              <el-col :span="12">
                <el-form-item label="资质等级" prop="bgzzdj">
                  <el-select
                    v-model="jbxxForm.zzdj"
                    clearable
                    :disabled="readonly"
                    type="zzdj"
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
                <el-form-item label="发证部门" prop="fzbm">
                  <el-input
                    type="fzbm"
                    :disabled="readonly"
                    v-model="jbxxForm.fzbm"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item
                  label="“多测合一”业务范围"
                  prop="dchyywfw"
                  class="dchyywfw"
                >
                  <el-checkbox-group v-model="dchyywfw">
                    <el-checkbox
                      :disabled="readonly"
                      label="项目用地测量阶段"
                    ></el-checkbox>
                    <el-checkbox
                      :disabled="readonly"
                      label="工程实施测量阶段"
                    ></el-checkbox>
                    <el-checkbox
                      :disabled="readonly"
                      label="竣工及不动产登记测量"
                    ></el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="专业范围" prop="zyfw" class="zyfw">
                  <el-input
                    placeholder="专业范围"
                    type="textarea"
                    :disabled="readonly"
                    v-model="jbxxForm.zyfw"
                    :rows="6"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="资质证书" class="zzzsstyle">
                  <Upload
                    :download="{
                      jbxxForm,
                      LOGDQHJ: 'zzbg',
                      id: qypathId,
                      rootpath: qyrootpath,
                    }"
                    v-if="qyrootpath && !$store.state.isbg"
                    ref="fjzzbg"
                  ></Upload>
                  <Upload
                    :download="{
                      jbxxForm,
                      LOGDQHJ: 'zzbg_bg',
                      id: qypathId,
                      rootpath: qyrootpath,
                    }"
                    v-if="qyrootpath && $store.state.isbg"
                    ref="fjzzbg"
                  ></Upload>
                </el-form-item>
              </el-col>

              <!-- 人员信息 -->
              <div class="global-flag">人员信息</div>
              <el-col v-if="!readonly" :span="24">
                <div class="global-dc">
                  <div class="left-btn">
                    <el-button type="primary" @click="addPersonInfo">
                      <i class="el-icon-plus"></i>
                      添加人员
                    </el-button>
                    <!-- <el-button type="warning">
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
                  style="width: 100%"
                  :header-cell-style="{ background: '#0091ff', color: '#fff' }"
                  @selection-change="handleSelectionChange"
                >
                  <el-table-column
                    type="selection"
                    width="55"
                  ></el-table-column>
                  <el-table-column label="姓名" prop="name"></el-table-column>
                  <el-table-column
                    prop="idcard"
                    label="身份证号"
                  ></el-table-column>
                  <el-table-column
                    prop="phonenum"
                    label="联系电话"
                    show-overflow-tooltip
                  ></el-table-column>
                  <el-table-column prop="type" label="角色"></el-table-column>

                  <el-table-column label="操作" align="center" width="180">
                    <template slot-scope="scope">
                      <div v-if="readonly">
                        <el-button
                          type="primary"
                          @click="viewFj(scope.row)"
                          size="small"
                          >查看附件</el-button
                        >
                      </div>
                      <div v-else>
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
                  <el-button
                    v-if="!readonly"
                    type="primary"
                    @click="tjzc"
                    :disabled="flag ? true : false"
                    >提交变更</el-button
                  >
                </div>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
    </div>
    <el-dialog :title="title" :visible.sync="visible" width="40%">
      <el-form
        ref="ryxxForm"
        :model="ryxxForm"
        :rules="rulesRyxx"
        autocomplete="off"
        label-width="100px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input
                type="name"
                :disabled="readonly"
                v-model="ryxxForm.name"
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="身份证号" prop="idcard">
              <el-input
                type="idcard"
                :disabled="readonly"
                v-model="ryxxForm.idcard"
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="联系电话" prop="phonenum">
              <el-input
                type="phonenum"
                :disabled="readonly"
                v-model="ryxxForm.phonenum"
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="角色" prop="type">
              <el-select
                v-model="ryxxForm.type"
                placeholder="请选择角色"
                @change="changeJs($event)"
                :disabled="readonly"
              >
                <el-option
                  v-for="item in typeArr"
                  :key="item.value"
                  :label="item.title"
                  :value="item.value"
                ></el-option>
              </el-select>
              <!-- <el-input type="type" v-model="ryxxForm.type" /> -->
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="资质证书" class="zzzsstyle">
              <Upload
                :download="{
                  data,
                  LOGDQHJ: 'zzbg_ryxx',
                  id: rypathId,
                  rootpath: ryrootpath,
                }"
                v-if="rypathId && !$store.state.isbg"
              ></Upload>
              <Upload
                :download="{
                  data,
                  LOGDQHJ: 'zzbg_ryxx_bg',
                  id: rypathId,
                  rootpath: ryrootpath,
                }"
                v-if="rypathId && $store.state.isbg"
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
import {
  downloadFile,
  closePage,
  doMessage,
  uploadsearch,
  uploadcreate,
  guid,
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
const rulesRyxx = {
  name: [{ required: true, message: "不能为空", trigger: "blur" }],
  idcard: [{ required: true, message: "不能为空", trigger: "blur" }],
  phonenum: [{ required: true, message: "不能为空", trigger: "blur" }],
  type: [{ required: true, message: "不能为空", trigger: "blur" }],
};
const ryxxForm = {
  name: "",
  idcard: "",
  phonenum: "",
  type: "",
  infor: "",
  did: "",
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
  zsbh: "",
  zsyxq: "",
  zzdj: "",
  fzbm: "",
  dchyywfw: "",
  zyfw: "",
  zzfj: "",
};
const typeArr = [
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
      zzzsData,
      typeArr,
      fileList: [],
      dchyywfw: [],
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      id: null,
      title: null,
      data: [],
      count: 3,
      rulesRyxx,
      dataSource,
      visible: false,
      ryxxForm,
      jbxxForm,
      //用户信息，用来渲染注册页面上面基本信息
      userInfo: {},
      userinfo: {},

      // 人员附件相关
      ryrootpath: "",
      rypathId: "",

      // 企业单位资质
      qypathId: "",
      qyrootpath: "",

      readonly: this.$store.state.readonly,
      isbg: false,
    };
  },
  computed: {},
  mounted() {
    this.getAllInfo();
  },
  methods: {
    //  选择角色
    changeJs(val) {
      let obj = {};
      obj = this.typeArr.find((item) => {
        //这里的typeArr就是上面遍历的数据源
        return item.value === val; //筛选出匹配数据
      });
      this.ryxxForm.type = obj.title;
    },
    //  查看附件
    viewFj(rowData) {
      this.ryrootpath = "";
      this.title = "编辑信息";
      let result = uploadsearch(rowData.infor);
      result.then((res) => {
        this.rypathId = res.data.id;
        this.ryrootpath = res.data.path;
        if (window.localStorage.getItem("xzfjids")) {
          window.localStorage.removeItem("xzfjids");
        }
        window.localStorage.setItem("xzfjids", rowData.infor);
        this.visible = true;
        this.isShowFj = false;
      });
      this.$nextTick(() => {
        this.ryxxForm = rowData;
      });
    },
    bgeng() {
      this.$store.commit("changereadonly", { readonly: false });
      this.$store.commit("changebg", { isbg: true });
      location.reload();
    },
    qxbgeng() {
      this.$store.commit("changereadonly", { readonly: true });
      this.$store.commit("changebg", { isbg: false });
      location.reload();
    },
    //  人员资料下载
    downloadRyzl() {},
    //  选中复选框的数组值
    handleSelectionChange(val) {
      console.log(val);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    //  获取资质信息
    getAllInfo() {
      let _this = this;
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
      let id = this.userinfo.id;
      let params = {
        did: id,
      };
      Promise.all([
        _this.$http.post("zzxx/selectZzxx", params),
        _this.$http.post("personal/selectpersonal", params),
      ]).then((res) => {
        let companydate = res[0].data.companydate;
        let zzxxdate = res[0].data.zzxxdate;
        let obj = { ...companydate, ...zzxxdate };
        _this.jbxxForm = obj;

        console.log(this.jbxxForm.zzfj);
        debugger;
        let result = uploadsearch(this.jbxxForm.zzfj);
        result.then((res) => {
          debugger;
          this.qypathId = res.data.id;
          this.qyrootpath = res.data.path;
          window.localStorage.setItem("xzfjid", this.jbxxForm.zzfj);
        });
        _this.data = res[1].data.data;
        _this.dchyywfw = zzxxdate.dchyywfw ? zzxxdate.dchyywfw.split(",") : [];
      });
    },
    //  提交注册
    tjzc() {
      //  验证人员有没有
      console.log(this.data);
      debugger;
      if (this.data.length == 0) {
        this.$message({
          message: "请添加人员信息",
          type: "error",
        });
        return;
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
      }

      this.$confirm("是否提交变更?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          console.log(this.userinfo);
          let _this = this;
          //  人员参数
          let bgpersonallistjson = _.cloneDeep(_this.data);

          let bgpersonallistjsonArr = [];
          _.each(bgpersonallistjson, (v) => {
            let obj = {};
            obj.bgname = v.name;
            obj.bgidcard = v.idcard;
            obj.bgphonenum = v.phonenum;
            // obj.bgpassword = v.password;
            // v.createtime = _this.$moment(v.createtime).format("YYYY-MM-DD");
            // obj.bgcreatetime = v.createtime;
            obj.bginfor = v.infor;
            obj.bgdid = this.userinfo.id;
            obj.bgtype = v.type;
            obj.bgstate = "待审核";
            obj.roleid = "1";
            bgpersonallistjsonArr.push(obj);
          });

          //  资质信息和基本信息参数
          let bgzzxxjson = _.cloneDeep(_this.jbxxForm);
          let bgzzxxjsonObj = {};
          bgzzxxjsonObj.bgdid = bgzzxxjson.did;
          bgzzxxjsonObj.bgzsbh = bgzzxxjson.zsbh;
          bgzzxxjsonObj.bgzsyxq = bgzzxxjson.zsyxq;
          bgzzxxjsonObj.bgzzdj = bgzzxxjson.zzdj;
          bgzzxxjsonObj.bgfzbm = bgzzxxjson.fzbm;
          bgzzxxjsonObj.bgzzfj = bgzzxxjson.zzfj;
          bgzzxxjsonObj.bgzyfw = bgzzxxjson.zyfw;
          bgzzxxjsonObj.bgstate = "待审核";
          bgzzxxjsonObj.bgsqsj = bgzzxxjson.bgsqsj;
          if (_this.dchyywfw.length === 0) {
            _this.$message.error("“多测合一”业务范围不能为空!");
            return false;
          }
          let dchyywfw = "";
          _.each(this.dchyywfw, (v) => {
            dchyywfw = dchyywfw ? dchyywfw + "," + v : v;
          });
          bgzzxxjsonObj.bgdchyywfw = dchyywfw;
          // bgzzxxjsonObj.logid = bgzzxxjson.logid;
          // bgzzxxjsonObj.bgbid = bgzzxxjson.bgbid;
          bgzzxxjson.zsyxq = _this
            .$moment(bgzzxxjson.zsyxq)
            .format("YYYY-MM-DD");
          bgzzxxjsonObj.bgzsyxq = bgzzxxjson.zsyxq;
          _this.jbxxForm.cjsj = new Date(_this.jbxxForm.cjsj).getTime();

          // _this.jbxxForm.bgsqsj = bgsqsj;
          //  日志参数
          let fsrxm = _this.userinfo.dwmc;
          let fsrid = _this.userinfo.id;
          let logjson = {
            fsrxm: fsrxm,
            fsrid: fsrid,
            jsrxm: "初审人",
            jsrid: "4",
            dqhj: "变更",
            clzt: "待办",
            sfyd: "未读",
          };
          let params = {
            bgpersonallistjson: bgpersonallistjsonArr,
            bgzzxxjson: bgzzxxjsonObj,
            logjson: logjson,
            companyjson: this.jbxxForm,
          };
          _this.$http.post("zzxx/addUpdateZzxx", params).then(async (res) => {
            if (res.status === 200) {
              this.$store.commit("changereadonly", { readonly: true });
              this.$store.commit("changebg", { isbg: false });
              this.flag = true;
              await this.$refs.fjzzbg.uploadFun();
              await this.$refs.fjzzbg.delall();
              debugger;
              doMessage("提交成功!", true);
              this.$router.go(-1);
            }
          });
        })
        .catch(() => {});
    },
    // 添加人员信息
    async handleForm() {
      if (window.localStorage.getItem("fjLists")) {
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
      debugger;
      let _this = this;
      if (_this.title === "添加人员信息") {
        _this.ryxxForm.bginfor = _this.rypathId;
        _this.ryxxForm.infor = _this.rypathId;
        _this.ryxxForm.bgstate = "待审核";
        _this.ryxxForm.bgdid = _this.userinfo.id;

        let obj = _.cloneDeep(_this.ryxxForm);
        _this.data.push(obj);
        debugger;
        await this.$refs.fjzzbg_ryxx.uploadFun();
        debugger;
        await this.$refs.fjzzbg_ryxx.delall();
        debugger;
        _this.visible = false;
        // });
      } else if (_this.title === "编辑信息") {
        _this.ryxxForm.bginfor = _this.rypathId;
        _this.ryxxForm.infor = _this.rypathId;
        _this.ryxxForm.bgstate = "待审核";
        _this.ryxxForm.bgdid = _this.userinfo.id;

        debugger;
        let obj = _.cloneDeep(_this.ryxxForm);
        for (let item of _this.data) {
          if (item.name == obj.name) {
            item = obj;
          }
        }

        await this.$refs.fjzzbg_ryxx.uploadFun();
        debugger;
        await this.$refs.fjzzbg_ryxx.delall();
        _this.visible = false;
      } else {
        _this.visible = false;
      }
    },
    editRyxx(record) {
      // return
      this.rypathId = "";
      for (let item of this.data) {
        if (record.name == item.name) {
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
      this.data = this.data.filter((item) => item.name !== record.name);
    },
    //  取消
    clear() {
      this.visible = false;
    },
    // 添加人员
    async addPersonInfo() {
      window.localStorage.removeItem("xzfjids");
      this.rypathId = "";
      // this.ryrootpath = `DCHY/人员信息/${guid()}`;
      this.ryrootpath = `${attachmentUpload.ryxx}/${guid()}`;

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
  },
  created() {
    this.userInfo = this.$store.state.userInfo;
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