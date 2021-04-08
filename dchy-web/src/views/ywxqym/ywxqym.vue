<template>
  <div class="ywxqym" v-if="ywxqyminformation">
    <Header></Header>
    <NavBar></NavBar>
    <Bread :name="$route.meta.name"></Bread>
    <div class="content" v-if="ruleForm">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        :disabled="formDisabled"
      >
        <el-collapse v-model="activeNames">
          <!-- 基本信息 -->
          <el-collapse-item title="基本信息" name="1" class="collapse_item">
            <div style="margin-top: 20px">
              <el-col :span="12">
                <el-form-item label="业主单位" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.yzdw" disabled></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="测绘单位" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.chdwmc" disabled></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="业主单位联系人" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.yzdwlxr" disabled></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="联系电话" label-width="124px">
                  <el-input
                    v-model="ruleForm.jbxx.yzdwlxrdh"
                    disabled
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="项目负责人" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.xmfzr" disabled></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="项目负责人电话" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.xmfzrdh" disabled></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="项目名称" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.xmmc" disabled></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="项目详细地址" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.xmdz" disabled></el-input>
                </el-form-item>
              </el-col>

              <!-- <el-col :span="12">
                <el-form-item label="测绘工程师" label-width="124px">
                  <el-select
                    v-model="ruleForm.jbxx.chgcs"
                    placeholder="请选择"
                    filterable
                    @change="selectchange"
                    value-key="value"
                    :disabled="
                      ruleForm.jbxx.chgcs != '' && zcchsselect == false
                    "
                  >
                    <el-option
                      v-for="(item, index) in options"
                      :key="index"
                      :label="item.name"
                      :value="item.idcard"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col> -->

              <!-- <el-col :span="12">
                <el-form-item label="测绘工程师电话" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.chgcsdh" disabled></el-input>
                </el-form-item>
              </el-col> -->

              <el-col :span="12">
                <el-form-item label="测绘阶段" label-width="124px">
                  <el-input v-model="ruleForm.jbxx.chjd" disabled></el-input>
                </el-form-item>
              </el-col>
            </div>
          </el-collapse-item>

          <!-- 项目信息 -->
          <el-collapse-item
            title="成果信息"
            name="3"
            class="collapse_item"
            v-if="ruleForm.cgxx.length > 0"
          >
            <div v-for="(item, index) in ruleForm.cgxx" :key="index">
              <div class="item_title">
                <div class="item_title_left">
                  {{ item.name }}
                </div>
                <div class="item_title_right" v-if="showTJ">
                  <i
                    class="el-icon-circle-plus-outline"
                    @click="additem(index)"
                  ></i>
                </div>
              </div>

              <div
                v-if="item.children.length == 0"
                style="margin-top: 10px; font-size: 15px"
              >
                该事项暂无成果信息
              </div>

              <div
                class="item_content"
                v-for="(items, indexs) in item.children"
                :key="items.cgxx ? items.cgxx.id : indexs"
              >
                <div
                  class="global-sxtitle-danger"
                  style="color: red; font-weight: bold"
                  v-if="items.message"
                >
                  <span>不通过原因：</span>
                  <span>{{ items.message }}</span>
                </div>

                <div
                  class="global-sxtitle"
                  v-if="
                    items.cgxx &&
                    items.cgxx.cgshjlsid &&
                    items.cgxx.cgshjlsid != '' &&
                    userRole == 'yzdw'
                  "
                >
                  <span style="font-size: 16px">
                    ”多测合一“审核完成，点击下载</span
                  >
                  <span
                    style="font-size: 16px; color: #1d94fc; cursor: pointer"
                    @click="downloadCgshjls(items)"
                    >成果审核结论</span
                  >
                </div>
                <div class="dikuaier" v-if="item.children.length > 1">
                  <div>地块{{ indexs + 1 }}</div>
                  <div
                    v-if="
                      showTJ && (items.cgshhj == '成果汇交' || !items.cgshhj)
                    "
                    class="dikuai_right"
                  >
                    <i
                      class="el-icon-remove-outline"
                      @click="delitem(index, indexs, items)"
                    ></i>
                  </div>
                </div>
                <el-row>
                  <el-col
                    :span="12"
                    v-if="
                      item.name != '1：500地形图测绘' &&
                      item.name != '土地勘测定界'
                    "
                  >
                    <el-form-item
                      label="地籍编号"
                      label-width="124px"
                      :prop="
                        'cgxx[' + index + '].children[' + indexs + '].djbh'
                      "
                    >
                      <el-input
                        v-model="items.djbh"
                        placeholder="请输入地籍编号，例如XC6-7-59-1"
                        :disabled="items.cgxxbjqx"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col
                    :span="12"
                    v-if="
                      item.name != '1：500地形图测绘' &&
                      item.name != '土地勘测定界' &&
                      item.name != '规划用地定点测量'
                    "
                  >
                    <el-form-item
                      label="宗地代码"
                      label-width="124px"
                      :prop="
                        'cgxx[' + index + '].children[' + indexs + '].zddm'
                      "
                    >
                      <el-input
                        v-model="items.zddm"
                        placeholder="请输入宗地代码，例如 610101001001GB00001"
                        :disabled="items.cgxxbjqx"
                      ></el-input>
                    </el-form-item>
                  </el-col>

                  <el-col
                    :span="12"
                    v-if="items.cgshhj == '业主单位验收' && userRole == 'yzdw'"
                  >
                    <el-form-item label="分局经办人" label-width="124px">
                      <el-cascader
                        v-model="items.fjjbr"
                        :options="users"
                        ref="userSel"
                        :props="{ expandTrigger: 'hover' }"
                        :show-all-levels="false"
                        @change="
                          (val) => {
                            handleSelChange(val, items);
                          }
                        "
                        :disabled="items.cgshhj == '项目审批经办人审核'"
                      >
                      </el-cascader>
                    </el-form-item>
                  </el-col>
                  <el-col
                    :span="12"
                    v-if="
                      ywxqyminformation.LOGDQHJ != '成果汇交' &&
                      items.cgshhj &&
                      items.cgshhj != '成果汇交'
                    "
                  >
                    <el-form-item label="成果审核环节" label-width="124px">
                      <el-input v-model="items.cgshhj" disabled></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-row>
                  <el-col :span="24">
                    <el-form-item
                      label="测绘成果附件"
                      label-width="124px"
                      class="fjitem"
                    >
                      <Upload2
                        :download="{
                          ywxqyminformation: items.cgxx,
                          itemname: item.name,
                          index: index,
                          indexs: indexs,
                          fjid: items.cgfj,
                          FBXXCHJD: ywxqyminformation.FBXXCHJD,
                        }"
                        ref="cgfj"
                      ></Upload2>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row
                  style="margin: 20px 0"
                  type="flex"
                  justify="end"
                  v-if="
                    items.refuse == false &&
                    (items.cgshhj == '测绘工程师确认' ||
                      items.cgshhj == '业主单位验收' ||
                      items.cgshhj == '项目审批经办人审核') &&
                    items.showTG == true
                  "
                >
                  <el-button
                    type="danger"
                    @click="back2(item.name, indexs, items.cgxx)"
                    v-if="
                      items.cgshhj == '测绘工程师确认' ||
                      items.cgshhj == '业主单位验收' ||
                      items.cgshhj == '项目审批经办人审核'
                    "
                    >不通过</el-button
                  >
                  <el-button
                    type="primary"
                    @click="pass(item.name, indexs, items.cgxx)"
                    v-if="
                      items.cgshhj == '测绘工程师确认' ||
                      items.cgshhj == '业主单位验收' ||
                      items.cgshhj == '项目审批经办人审核'
                    "
                    >通过</el-button
                  >
                </el-row>
                <div class="fengexian"></div>
              </div>
            </div>
          </el-collapse-item>

          <el-collapse-item title="合同信息" name="2" class="collapse_item">
            <div style="margin-top: 20px">
              <el-col :span="12">
                <el-form-item label="测绘单位" label-width="124px">
                  <el-input v-model="ruleForm.htxx.chdwmc" disabled />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="签订日期" label-width="124px">
                  <el-date-picker
                    v-model="ruleForm.htxx.qdrq"
                    type="date"
                    disabled
                  ></el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="合同开始时间" label-width="124px">
                  <el-date-picker
                    v-model="ruleForm.htxx.ydkssj"
                    type="date"
                    disabled
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="合同结束时间" label-width="124px">
                  <el-date-picker
                    v-model="ruleForm.htxx.ydjssj"
                    type="date"
                    disabled
                  ></el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="项目编号" label-width="124px">
                  <el-input v-model="ruleForm.htxx.htbh" disabled />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="合同价款/元" label-width="124px">
                  <el-input v-model.number="ruleForm.htxx.htjk" disabled />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="补充说明" label-width="124px">
                  <el-input
                    type="textarea"
                    v-model="ruleForm.htxx.bcsm"
                    rows="4"
                    resize="none"
                    disabled
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="测量事项" label-width="124px">
                  <el-input
                    type="textarea"
                    v-model="ruleForm.htxx.chsx"
                    disabled
                  />
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item
                  label="合同信息附件"
                  label-width="124px"
                  class="fjitem"
                >
                  <Upload
                    v-if="htpathid"
                    :download="{
                      LOGDQHJ: '合同确认',

                      id: htpathid,
                      rootpath: this.htrootpath,
                    }"
                    ref="htfj"
                  ></Upload>
                </el-form-item>
              </el-col>
            </div>
          </el-collapse-item>
        </el-collapse>
      </el-form>

      <el-row style="margin: 20px 0" type="flex" justify="end">
        <el-button
          type="primary"
          @click="save()"
          style="margin-left: 20px"
          v-if="showTJ"
          >保存</el-button
        >
        <el-button
          type="primary"
          @click="submitForm('ruleForm')"
          style="margin-left: 20px"
          v-if="showTJ"
          >提交</el-button
        >
      </el-row>

      <el-dialog
        :title="dialogtitle"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose"
      >
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入内容"
          v-model="reason"
          v-if="reasonshow"
        >
        </el-input>
        <div style="display: flex; align-items: center; margin-top: 10px">
          <el-input
            v-if="userRole == 'chsgcs'"
            class="yzminput"
            placeholder="请输入手机上接收到的验证码"
            v-model="yzm"
          >
          </el-input>

          <el-button
            v-if="userRole == 'chsgcs'"
            class="yzmbtn"
            @click="getyzm($event)"
            :disabled="isbtndis"
            ref="yzmbtnref"
          >
            获取短信验证码
            <span v-show="!show" class="count">({{ count }}s)</span>
          </el-button>
        </div>

        <span slot="footer" class="dialog-footer">
          <el-button @click="qxdialog()">取 消</el-button>
          <el-button type="primary" @click="dialogqr()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <div class="bszn" @click="bsznClick">办事<br />指南</div>
    <Dropdown :propInfo="{ name: '.ywxqym', path: 'index' }"></Dropdown>
    <Bottom></Bottom>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import NavBar from "@/components/NavBar.vue";
import Bread from "@/components/Bread.vue";
import Upload2 from "@/components/Upload2.vue";
import Upload from "@/components/Upload.vue";
import Bottom from "@/components/Bottom.vue";
import Dropdown from "@/components/dropdown.vue";

import {
  doMessage,
  sendMessage,
  guid,
  downloadFile,
  deepClone,
} from "@/assets/js/common.js";
import {
  attachmentUpload,
  templateName,
  callService,
  requestConfig,
} from "@/assets/js/config.js";

class userObj {
  constructor(label, value, children) {
    this.value = value;
    this.label = label;
    this.children = children;
  }
}

var checkObjNull = (obj) => {
  let success = false;
  let count = 0;
  let sum = 0;
  Object.keys(obj).forEach((keys) => {
    if (keys != "cgid" && keys != "fjjbrid" && obj[keys] != "") {
      count++;
    }
    sum++;
  });
  // if (count == 0 || count == sum) {
  if (count == 0) {
    success = true;
  }
  return success;
};

// 不动产单元代码的验证
var bdcdyValid = (rule, value, callback) => {
  // let thirteen = ['G','L','Z'];
  let thirteen = "GLZ";
  // let fourteen = ['A','B','S','X','C','D','E','F','G','H','W','Y'];
  let fourteen = "ABSXCDEFGHWY";
  // let twenty = ['F','L','Q','W'];
  let twenty = "FLQW";
  let pass = true;

  if (value.length === 19) {
    for (let ind in value) {
      switch (parseInt(ind)) {
        case 12:
          if (thirteen.indexOf(value[12]) === -1) {
            pass = false;
            break;
          }
          break;
        case 13:
          if (fourteen.indexOf(value[13]) === -1) {
            pass = false;
            break;
          }
          break;
        default:
          if (isNaN(parseInt(value[ind]))) {
            pass = false;
            break;
          }
          break;
      }
    }
  } else {
    pass = false;
  }
  if (!pass) {
    // return false;
    callback(new Error("请输入19位符合规则的不动产单元代码"));
  } else {
    // return true;
    callback();
  }
};

export default {
  data() {
    return {
      // 表单数据
      ruleForm: {
        jbxx: {
          yzdw: "",
          chdwmc: "",
          yzdwlxr: "",
          yzdwlxrdh: "",
          xmmc: "",
          xmdz: "",
          chgcs: "",
          chgcsdh: "",
          xmfzr: "",
          xmfzrdh: "",
          chjd: "",
        },
        cgxx: [],
        htxx: {
          id: null,
          qdrq: null, //签订日期
          ydkssj: null, //约定开始时间
          ydjssj: null, //约定结束时间
          htjk: "", //合同价款
          chdwmc: null, //测绘单位名称
          bcsm: null, //补充说明
          chsx: null, //测绘事项
          htwj: null, //合同文件
          htbh: null, //合同编号
        },
      },
      rules: {
        cgxx: [],
      },
      userRole: "",
      jjsl: 0,
      timer: null, // 倒计时
      count: "", // 倒计时时间
      show: true, // 倒计时是否显示
      isbtndis: false, // 验证码按钮是否可以点击
      // 验证码
      yzm: "",
      formDisabled: false,
      // 折叠栏控制
      activeNames: ["1", "2", "3"],
      // 测绘工程师list
      options: [],
      // 测绘工程师身份证
      idcard: "",
      // 测绘工程师禁用
      zcchsselect: true,
      // 页面详情数据
      ywxqyminformation: "",
      // 是否提交
      isFinish: false,
      dialogtitle: "请输入原因",
      reasonshow: false,
      dialogVisible: false,

      // 不通过原因
      reason: "",

      // 不通过事项名
      name: "",
      // 不通过事项indexs
      indexs: "",
      // 暂存个体成果信息
      Itemscgxx: "",

      //控制 提交按钮显示
      showTJ: false,

      //经办人
      users: [],
      cgshr: "",
      cgshrid: Number,

      //保存拥有经办人选择权的成果list
      yscgList: [],
      //经办人渲染数量
      submit: Number,
      //控制dialog是通过还是不通过进来的
      dialogState: "",
      processList: [],
      DQJD: "",

      //合同rootpath
      htrootpath: "",
      htpathid: "",
    };
  },
  methods: {
    async getyzm(val) {
      // 生成随机6位验证码
      var vilidateNum = "";
      for (var i = 0; i < 6; i++) {
        vilidateNum += Math.floor(Math.random() * 10);
      }
      // 存储验证码
      window.localStorage.setItem("vilidateNum", vilidateNum);
      // 测试时用提示显示
      this.$message({
        message: "你本次的验证码是：" + vilidateNum,
        type: "success",
        duration: 4000,
        offset: 150,
      });
      const TIME_COUNT = 60; //更改倒计时时间
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.show = false;
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            // val.target.innerText = "重新获取";
            this.isbtndis = true;
            this.count--;
          } else {
            this.show = true;
            this.isbtndis = false;
            clearInterval(this.timer); // 清除定时器
            this.timer = null;
            // 重置清除存储的值
            window.localStorage.removeItem("vilidateNum");
          }
        }, 1000);
      }
      // 发送短信
      var content =
        "验证码：" +
        vilidateNum +
        "，2分钟内有效，您正在确认 “" +
        this.ruleForm.jbxx.xmmc +
        "项目”的测绘成果，请勿泄露给他人使用。";
      // 测绘工程师电话
      var userphone = this.ruleForm.jbxx.chgcsdh;
      // var content="验证码"+vilidateNum+"，您负责的【"+this.ruleForm.xmxx.xmmc+"】项目成果，需在西安市“多测合一”平台审核确认，确认请输入"+vilidateNum+"；如非本人操作，请注意账户安全，感谢您的支持。"
      // sendMessage(userphone, content); //给测绘工程师发短信，上线时请放开
    },

    downloadCgshjls(val) {
      // 查一下当前的zxtzsid
      let formData = new FormData();
      this.$confirm("是否下载成果审核结论书?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        formData.append("folderid", val.cgxx.cgshjlsid);
        this.$http
          .post("ftpService/listFile2", formData, requestConfig)
          .then((res) => {
            if (res.data) {
              let _data = { ...res.data };
              // 下载中选通知书，前提是已上传了
              var url = `${callService.downloadFile}`;
              var params = {};
              if (_data.dchyFjRecords && _data.dchyFjRecords.length > 0) {
                params.filename = _data.dchyFjRecords[0].filepath;
              } else {
                params.filename = `${_data.path}/${templateName.cgshjls}.pdf`;
              }
              downloadFile(url, params);
            } else {
              doMessage("下载成果审核结论书失败！,清检查文件路径", false);
            }
          });
      });
    },
    // 增加地块儿item
    additem(index) {
      debugger;
      let cgid = "";
      console.log(this.processList);
      if (this.ruleForm.cgxx[index].children[0].cghjzt == "提交") {
        if (this.ruleForm.cgxx[index].children[0].cgxx.yl3) {
          cgid = this.ruleForm.cgxx[index].children[0].cgxx.yl3;
        }
        if (this.ruleForm.cgxx[index].children[0].cgxx.cgid) {
          cgid = this.ruleForm.cgxx[index].children[0].cgxx.cgid;
        }
      } else {
        cgid = this.ruleForm.cgxx[index].children[0].cgid;
        debugger;
      }
      this.ruleForm.cgxx[index].children.push({
        djbh: "",
        zddm: "",
        fjjbr: "",
        fjjbrid: Number,
        cgshhj: "",
        cgfj: "",
        id: "",
        cgid: cgid,
      });
      // 验证规则增加
      this.rules.cgxx[index].children.push({
        djbh: [{ required: true, message: "请输入地籍编号", trigger: "blur" }],
        zddm: [
          {
            required: true,
            message: "请输入不动产单元代码",
            trigger: "blur",
          },
          {
            validator: bdcdyValid,
            trigger: "blur",
          },
        ],
      });
      this.showTJ = true;

      console.log(this.ruleForm.cgxx[index]);
      console.log(this.rules.cgxx[index]);
      console.log(this.ruleForm.cgxx);
      console.log(this.rules.cgxx);
    },
    // 删除地块儿item
    delitem(index, indexs, items) {
      console.log(this.$refs.cgfj);
      if (items.id) {
        var params = {
          id: items.id,
        };
        this.$http
          .get("dchy/dchyYwxx/deleteYwxxByKey", { params })
          .then((res) => {
            this.ruleForm.cgxx[index].children.splice(indexs, 1);
            this.rules.cgxx[index].children.splice(indexs, 1);
            for (let idx in this.$refs.cgfj) {
              if (
                this.$refs.cgfj[idx].download.index == index &&
                this.$refs.cgfj[idx].download.indexs == indexs
              ) {
                this.$refs.cgfj.splice(idx, 1);
              }
            }
          });
      } else {
        this.ruleForm.cgxx[index].children.splice(indexs, 1);
        this.rules.cgxx[index].children.splice(indexs, 1);
        for (let idx in this.$refs.cgfj) {
          if (
            this.$refs.cgfj[idx].download.index == index &&
            this.$refs.cgfj[idx].download.indexs == indexs
          ) {
            this.$refs.cgfj.splice(idx, 1);
          }
        }
        console.log(this.$refs.cgfj);
      }
    },
    // 获取测绘工程师列表
    // getcgfzrList() {
    //   var params = {
    //     did: this.userinfo.id,
    //   };
    //   this.$http.post("personal/selectpersonal", params).then(
    //     (res) => {
    //       let arr = [];
    //       for (let item of res.data.data) {
    //         if (item.type == "注册测绘师" || item.type == "高级工程师") {
    //           arr = [...arr, item];
    //         }
    //       }
    //       this.options = arr;
    //     },
    //     (err) => {
    //       console.log(err);
    //     }
    //   );
    // },
    // 测绘工程师下拉事件
    // selectchange(value) {
    //   for (let item of this.options) {
    //     if (item.idcard == value) {
    //       var params = {
    //         idcard: value,
    //       };
    //       this.$http.post("personal/getFbxxByPersonal", params).then((res) => {
    //         this.jjsl = res.data.data;
    //         // if(this.jjsl >= 3){
    //         if (false) {
    //           doMessage("该测绘工程师已承接3个测绘项目，无法继续承接！", false);
    //           this.ruleForm.jbxx.chgcsdh = null;
    //           this.ruleForm.jbxx.chgcs = null;
    //           this.idcard = null;
    //           return;
    //         } else {
    //           this.ruleForm.jbxx.chgcsdh = item.phonenum;
    //           this.ruleForm.jbxx.chgcs = item.name;
    //           this.idcard = item.idcard;
    //         }
    //       });
    //     }
    //   }
    // },
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
    // 根据传入测量阶段渲染成果信息
    getCLJD(params) {
      let nameList = [];
      for (let item of this.processList) {
        if (item.processAlias == this.DQJD) {
          nameList = item.children;
        }
      }

      for (let index in nameList) {
        let name = nameList[index].processAlias;
        let cgxx = {
          name: nameList[index].processAlias,
          djbhVal: true,
          zddmVal: true,
          children: [
            {
              djbh: "",
              zddm: "",
              fjjbr: "",
              cgshhj: "成果汇交",
              cgfj: "",
              id: "",
              cgid: nameList[index].id,
            },
          ],
        };
        let cgxxRule = {
          name: name,
          children: [
            {
              djbh: [
                { required: true, message: "请输入地籍编号", trigger: "blur" },
              ],
              zddm: [
                {
                  required: true,
                  message: "请输入宗地代码",
                  trigger: "blur",
                },
                {
                  validator: bdcdyValid,
                  trigger: "blur",
                },
              ],
            },
          ],
        };
        // 设置对什么事项进行那些校验
        switch (name) {
          case "1：500地形图测绘":
            cgxx.djbhVal = false;
            cgxx.zddmVal = false;
            break;
          case "土地勘测定界":
            cgxx.djbhVal = false;
            cgxx.zddmVal = false;
            break;
          case "规划用地定点测量":
            cgxx.djbhVal = true;
            cgxx.zddmVal = false;
            break;
        }
        this.ruleForm.cgxx = [...this.ruleForm.cgxx, cgxx];
        this.rules.cgxx = [...this.rules.cgxx, cgxxRule];
      }
      console.log(this.ruleForm.cgxx);
      console.log(this.rules.cgxx);
    },
    // yanzheng(idcard){
    //   this.jjsl=0;
    //  var params = {
    //     idcard: this.idcard,
    //   };
    //     this.$http.post("dchy/personal/getFbxxByPersonal", params).then((res) => {
    //       this.jjsl=this.res.data.data
    //       console.log(this.res.data.data)
    //     })
    // },
    //渲染页面数据
    async getContent() {
      this.ywxqyminformation = this.$store.state.ywxqyminformation;
      this.ruleForm.jbxx.yzdw = this.ywxqyminformation.WTDW;
      this.ruleForm.jbxx.id = this.ywxqyminformation.FBXXID;
      this.ruleForm.jbxx.dchybh = this.ywxqyminformation.FBXXDCHYBH;
      this.ruleForm.jbxx.chdwmc = this.ywxqyminformation.HTXXCHDWMC;
      this.ruleForm.jbxx.yzdwid = this.ywxqyminformation.FBXXWTDWID;
      this.ruleForm.jbxx.yzdwlxr = this.ywxqyminformation.FBXXYZDWLXR;
      this.ruleForm.jbxx.yzdwlxrdh = this.ywxqyminformation.FBXXYZDWLXRDH;
      this.ruleForm.jbxx.xmmc = this.ywxqyminformation.XMMC;
      this.ruleForm.jbxx.xmdz = this.ywxqyminformation.FBXXXMDZ;
      this.ruleForm.jbxx.chjd = this.ywxqyminformation.FBXXCHJD;

      // 渲染合同信息
      this.gethtxx();

      // 测绘阶段和下面地小环节
      console.log(this.processList);
      if (this.processList && this.processList.length > 0) {
        let _proList = this.processList;
        let a = _.filter(_proList, (item) => {
          return item.id === this.ruleForm.jbxx.chjd;
        })[0].processAlias;
        this.DQJD = a;
        this.ruleForm.jbxx.chjd = a;
      }

      if (!this.ywxqyminformation.FBXXCHCGFZR) {
        // this.getcgfzrList();
      } else {
        this.ruleForm.jbxx.chgcs = this.ywxqyminformation.FBXXCHCGFZR;
        this.ruleForm.jbxx.chgcsdh = this.ywxqyminformation.FBXXCHCGFZRDH;
      }
      // 获取成果数组值
      await this.getcgxxlist();

      if (this.userRole == "chdw") {
        await this.getlxr(this.ywxqyminformation.FBXXID, this.userinfo.id);
      } else {
        await this.getlxr(
          this.ywxqyminformation.FBXXID,
          this.ywxqyminformation.HTXXCHDWID
        );
      }
    },
    //获取项目负责人电话，姓名
    getlxr(id1, id2) {
      let params = {
        pid: id1,
        chdwid: id2,
      };
      this.$http.post("xmwt/dchySqyx/getByPidAndChdwid", params).then((res) => {
        // res.data.data[0]
        this.ruleForm.jbxx.xmfzr = res.data.data[0].lxr;
        this.ruleForm.jbxx.xmfzrdh = res.data.data[0].lxrdh;
      });
    },
    // 获取成果信息
    getcgxxlist() {
      this.ruleForm.cgxx = [];
      var params = {
        pid: this.ywxqyminformation.FBXXID,
        pageSize: "100",
        pageNum: "1",
      };
      this.$http.post("dchy/dchyYwxx/getYwxxList", params).then((res) => {
        // 保存查询结果
        let arr = res.data.data;
        // 1.初始化成果list
        this.getCLJD(this.ywxqyminformation.FBXXCHJD);
        // 声明临时数组用于检测是否存在成果汇交保存状态的成果信息
        var result = [];

        var cgidList = [];
        for (let item of this.ruleForm.cgxx) {
          cgidList = [
            ...cgidList,
            {
              name: item.name,
              cgid: item.children[0].cgid,
            },
          ];
          item.children = [];
        }

        if (this.userRole == "chdw") {
          result = arr;
        } else if (this.userRole == "成果验收经办人") {
          for (let item of arr) {
            if (
              item.cghjzt == "提交" &&
              item.logsList[0].dqhj == "分局成果审核"
            ) {
              result = [...result, item];
            }
          }
          if (result.length == 0) {
            return;
          }
        } else {
          for (let item of arr) {
            if (item.cghjzt == "提交") {
              result = [...result, item];
            }
          }
        }
        console.log(result);
        if (result.length != 0) {
          this.idcard = this.ywxqyminformation.FBXXCHCGFZRSFZH;
          for (let item of this.ruleForm.cgxx) {
            for (let value of result) {
              // 退回原因
              var message = "";
              if (this.userRole == "chdw") {
                if (value.cghjzt == "保存") {
                  if (item.name == value.ywmc) {
                    let params = {
                      djbh: value.djh,
                      zddm: value.bdcdyh,
                      fjjbr: value.cgshr,
                      cgshhj: "",
                      cgfj: value.cghjfj,
                      cgxx: value,
                      id: value.id,
                      cghjzt: value.cghjzt,
                      cgid: value.yl3,
                    };
                    item.children = [...item.children, params];
                  }
                  // console.log(this.ruleForm.cgxx);
                }
                if (value.cghjzt == "提交") {
                  this.zcchsselect = false;
                  var cgxxbjqx = true;
                  if (value.wqryy && value.logsList[0].dqhj == "成果汇交") {
                    message = value.wqryy;
                  }
                  if (value.wgsyy && value.logsList[0].dqhj == "成果汇交") {
                    message = value.wgsyy;
                  }

                  var cgshhj = value.logsList[0].dqhj;

                  if (cgshhj == "分局成果审核") {
                    cgshhj = "项目审批经办人审核";
                  }
                  if (item.name == value.ywmc) {
                    // console.log(value.cghjzt);
                    let params = {
                      djbh: value.djh,
                      zddm: value.bdcdyh,
                      fjjbr: value.cgshr,
                      cgshhj: cgshhj,
                      cgfj: value.cghjfj,
                      cgxx: value,
                      message: message,
                      cgxxbjqx: cgxxbjqx,
                      id: value.id,
                      cghjzt: value.cghjzt,
                      // cgid: value.logsList[0].ywlx,
                      cgid: value.yl3,
                    };
                    item.children = [...item.children, params];
                  }
                  // console.log(this.ruleForm.cgxx);
                }
                this.showTJ = true;
                // console.log(this.ruleForm.cgxx);
              } else {
                this.zcchsselect = false;
                if (value.logsList[0].dqhj) {
                  // 判断处于哪个环节
                  let hj = value.logsList[0].dqhj;
                  // 控制点击单行通过或不通过后 单行隐藏
                  var refuse = false;
                  // 控制单行通过不通过
                  var showTG = false;
                  // 控制测绘工程师及后续阶段的成果信息编辑权限
                  var cgxxbjqx = false;
                  if (this.userRole == "chdw") {
                    this.showTJ = true;
                  }
                  if (hj == "成果汇交") {
                    message = value.wqryy;
                    if (value.wgsyy) {
                      message = value.wgsyy;
                    }
                    if (
                      !value.cgshqr ||
                      value.cgshqr == "未通过" ||
                      value.cgyszt == "未通过"
                    ) {
                    } else {
                      refuse = true;
                    }
                  }
                  // if (hj == "测绘工程师确认") {
                  //   cgxxbjqx = true;
                  //   if (this.userRole == "chsgcs") {
                  //     showTG = true;
                  //   } else {
                  //     showTG = false;
                  //   }
                  //   if (!value.cgsfsh || value.cgsfsh == "未通过") {
                  //     refuse = false;
                  //   } else {
                  //     refuse = true;
                  //   }
                  // }
                  if (hj == "业主单位验收") {
                    cgxxbjqx = true;
                    if (this.userRole == "yzdw") {
                      showTG = true;
                    } else {
                      showTG = false;
                    }
                    this.submit = result.length;
                    message = value.chshyj;
                    if (!value.cgsfsh || value.cgsfsh == "未通过") {
                    } else {
                      refuse = true;
                    }

                    this.getJbry();
                  }
                  if (hj == "分局成果审核") {
                    cgxxbjqx = true;
                    if (this.userRole == "成果验收经办人") {
                      showTG = true;
                      refuse = false;
                    } else {
                      showTG = false;
                    }
                  }
                  if (hj == "审核通过") {
                    cgxxbjqx = true;
                  }
                }
                // 根据返回数据渲染成果信息
                var cgshhj = value.logsList[0].dqhj;
                if (cgshhj == "分局成果审核") {
                  cgshhj = "项目审批经办人审核";
                }
                if (item.name == value.ywmc) {
                  // console.log(value.cghjzt);

                  let params = {
                    djbh: value.djh,
                    zddm: value.bdcdyh,
                    fjjbr: value.cgshr,
                    cgshhj: cgshhj,
                    cgfj: value.cghjfj,
                    cgxx: value,
                    refuse: refuse,
                    showTG: showTG,
                    cgxxbjqx: cgxxbjqx,
                    message: message,
                    id: value.id,
                    cghjzt: value.cghjzt,
                    cgid: value.cgid,
                  };
                  item.children = [...item.children, params];
                }
              }
            }
          }
          console.log(this.ruleForm.cgxx);
          for (let item of this.ruleForm.cgxx) {
            for (let items of cgidList) {
              if (
                item.children.length == 0 &&
                this.userRole == "chdw" &&
                item.name == items.name
              ) {
                item.children = [
                  ...item.children,
                  {
                    djbh: "",
                    zddm: "",
                    fjjbr: "",
                    fjjbrid: Number,
                    cgshhj: "",
                    cgfj: "",
                    cgxx: "",
                    id: "",
                    cghjzt: "",
                    cgid: items.cgid,
                  },
                ];
              }
            }
          }

          // 初始化验证规则  先清空原来的，遍历加入新的规则
          let cgxxRule = {
            djbh: [
              { required: true, message: "请输入地籍编号", trigger: "blur" },
            ],
            zddm: [
              {
                required: true,
                message: "请输入宗地代码",
                trigger: "blur",
              },
              {
                validator: bdcdyValid,
                trigger: "blur",
              },
            ],
          };
          debugger;
          let cgxxList = this.ruleForm.cgxx;
          let cgxxRules = this.rules.cgxx;
          cgxxRules = [];
          cgxxRules = deepClone(cgxxList);
          for (let i = 0; i < cgxxList.length; i++) {
            let chsx = cgxxList[i]; //1:500
            // this.rules.cgxx[i].name = chsx.name;
            // this.rules.cgxx[i].children = [];
            // cgxxRules = [...cgxxRules, {name:chsx.name,children:[]}];
            for (let j = 0; j < chsx.children.length; j++) {
              let child = chsx.children[j];
              let rule = cgxxRules[i];
              rule.children[j] = cgxxRule;
              // cgxxRule.name = chsx.name;
              // cgxxRules[i].children[j].push(cgxxRule);
            }
          }
          this.rules.cgxx = cgxxRules;
        } else {
          // 如果有测绘工程师
          // if (this.ywxqyminformation.FBXXCHCGFZRSFZH) {
          //   this.idcard = this.ywxqyminformation.FBXXCHCGFZRSFZH;
          // }
          if (this.userRole == "chdw") {
            this.showTJ = true;
            // 如果该事项下没有成果信息，手动添加一条用于显示
            for (let item of this.ruleForm.cgxx) {
              for (let items of cgidList) {
                if (
                  item.children.length == 0 &&
                  this.userRole == "chdw" &&
                  item.name == items.name
                ) {
                  item.children = [
                    ...item.children,
                    {
                      djbh: "",
                      zddm: "",
                      fjjbr: "",
                      fjjbrid: Number,
                      cgshhj: "",
                      cgfj: "",
                      cgxx: "",
                      id: "",
                      cghjzt: "",
                      cgid: items.cgid,
                    },
                  ];
                }
              }
            }
          }
        }
        if (this.userRole == "yzdw") {
          //如果该事项为业主单位验收
          for (let item of this.ruleForm.cgxx) {
            for (let items of item.children) {
              if (items.cgxx.logsList[0].dqhj == "业主单位验收") {
                this.yscgList = [...this.yscgList, items];
              }
            }
          }
          console.log(this.yscgList);
        }
      });
    },
    gethtxx() {
      // 初始化合同信息
      this.$http
        .get("xmwt/dchyHtxx/getByPid?pid=" + this.ywxqyminformation.FBXXID)
        .then((res) => {
          let data = res.data;
          if (data) {
            console.log(this.ruleForm);
            this.ruleForm.htxx.chdwmc = data.chdwmc;
            this.ruleForm.htxx.qdrq = data.qdrq;
            this.ruleForm.htxx.ydkssj = data.ydkssj;
            this.ruleForm.htxx.ydjssj = data.ydjssj;
            this.ruleForm.htxx.htjk = data.htjk;
            this.ruleForm.htxx.htbh = data.htbh;
            this.ruleForm.htxx.htbh = data.htbh;
            this.ruleForm.htxx.bcsm = data.bcsm;
            this.ruleForm.htxx.htwj = data.htwj;
            if (this.ruleForm.jbxx.chjd) {
              let childs = [];
              console.log(this.processList);
              for (let item of this.processList) {
                if (item.processAlias == this.ruleForm.jbxx.chjd) {
                  for (let value of item.children) {
                    childs = [...childs, value.processAlias];
                  }
                }
              }
              console.log(childs);
              this.ruleForm.htxx.chsx = childs.toString();
            }

            let fileFormData = new FormData();
            let requestConfig = {
              headers: {
                "Content-Type": "multipart/form-data",
              },
            };
            fileFormData.append("folderid", data.htwj);
            this.$http
              .post("ftpService/listFile2", fileFormData, requestConfig)
              .then((res) => {
                this.htpathid = res.data.id;
                this.htrootpath = res.data.path;
                window.localStorage.setItem("xzfjid", data.htwj);
              })
              .catch((err) => {
                console.log(err);
              });
          }
        });
    },
    // 生成成果审核结论书
    createCgshjls(id) {
      // 生成中选通知书
      let formdata = new FormData();
      formdata.append("downloadPath", `${attachmentUpload.mbwj}`);
      formdata.append("downloadName", `${templateName.cgshjls}.ftl`);
      formdata.append("uploadPath", `${attachmentUpload.xmtz}/${guid()}`);
      formdata.append("uploadName", `${templateName.cgshjls}.pdf`);
      formdata.append("userid", this.ruleForm.jbxx.yzdwid);
      formdata.append("username", this.ruleForm.jbxx.yzdw);
      formdata.append("pid", this.ruleForm.jbxx.id); //fbxxid
      formdata.append("ywxxid", id); //业务信息id
      formdata.append("withQrcode", true);
      formdata.append("downloadType", `成果`);
      this.$http.post(
        `${callService.createNoticeFile}`,
        formdata,
        requestConfig
      );
    },
    // 发送通知短信
    sendNoticeMsg(id) {
      // 查询所需要的数据信息 xmdz,wtdw,xmmc,       cgbh,shjg
      this.$http
        .get("dchy/dchyYwxx/getNoticeMsgByYwxxid?ywxxid=" + id)
        .then((res) => {
          if (res.data.data) {
            let _data = res.data.data;
            let message = `【市资源规划局】 ${
              _data.WTDW
            } ：\n您单位提交的位于 ${_data.XMDZ}
        的${_data.XMMC}项目“多测合一”成果审核申请${
              _data.CGBH ? "（测绘成果编号：" + _data.CGBH + "）" : ""
            } 已收到。经核查，${
              _data.CGSFSH == "已通过" ? "符合" : "不符合：原因" + _data.CGSHYJ
            } “多测合一”相关配套文件要求。`;
            console.log(message);
            sendMessage(this.ruleForm.jbxx.yzdwlxrdh, message); //业主单位联系人
          }
        });
    },
    // 自定义控件验证（暂不用）
    validCgxx(val) {
      // 这个val是每一个小事项的数组
      let res = {
        msg: "",
        succ: true,
      };
      if (val) {
        _.each(val.children, (item) => {
          // 验证规则     地籍编号、宗地代码
          if (val.zddmVal) {
            if (!bdcdyValid(item.zddm)) {
              res.msg = "宗地代码不行";
              res.succ = false;
              return res;
            }
          }
          if (val.djbhVal) {
            if (!(item.djbh != "")) {
              //为空的情况
              res.msg = "地籍编号不行";
              res.succ = false;
              return res;
            }
          }
        });
      }
      return res;
    },
    save() {
      // 首先给每个添加过附件的成果添加对应附件id
      for (let item of this.$store.state.fjidList) {
        for (let value of item.fjlist) {
          if (value.children.length == 0) {
            this.$message({
              type: "error",
              message: `保存失败，请上传完整${value.filename}附件信息`,
            });
            return;
          }
        }
        for (let item2 of this.ruleForm.cgxx) {
          if (item.itemname == item2.name) {
            for (let ind in item2.children) {
              if (ind == item.indexs) {
                item2.children[ind].cgfj = item.id;
              }
            }
          }
        }
      }
      // let newArr = [];
      // console.log(this.ruleForm.cgxx);
      for (let item of this.ruleForm.cgxx) {
        for (let value of item.children) {
          if (value.cghjzt == "" || value.cghjzt == null) {
            value.cghjzt = "保存";
          }
        }
      }
      // console.log(this.ruleForm.cgxx);

      var params = {
        // chcgfzr: this.ruleForm.jbxx.chgcs,
        // fzrdh: this.ruleForm.jbxx.chgcsdh,
        // fzrsfz: this.idcard,
        yl2: this.ywxqyminformation.LOGID,
        xmjd: this.ywxqyminformation.FBXXCHJD,
        fszt: "1",
        dqhj: "成果汇交",
        xyhj: "业主单位验收",
        pid: this.ywxqyminformation.FBXXID,
        cgxx: this.ruleForm.cgxx,
      };
      // this.$store.commit("changefjidList");
      this.$http.post("dchy/dchyYwxx/ywxxSend", params).then(
        async (res) => {
          for (let index in this.$refs.cgfj) {
            await this.$refs.cgfj[index].uploadFun();
            await this.$refs.cgfj[index].delall();
          }
          console.log(res);
          doMessage("已保存!", true);
          this.ruleForm.cgxx = [];
          await this.getcgxxlist();
          // this.$router.push("myyw");
        },
        (err) => {
          console.log(err);
        }
      );
    },
    checkDjbhAndZddm(chsx, child, rule) {
      // 什么时候废除验证，  两处没有填值得时候
      if (
        (child.djbh == null || child.djbh == "") &&
        (child.zddm == null || child.zddm == "")
      ) {
        if (chsx.djbhVal) {
          rule.djbh[0].required = false;
        }
        if (chsx.zddmVal) {
          rule.zddm[0].required = false;
          rule.zddm.splice(1, 1);
        }
      }
    },
    //成果汇交提交方法
    async submitForm(formName) {
      let pass;
      /*
       * 现在需要做的就是，提交时验证下，是否有填写信息，没有填写就不进行验证
       * 或者将对应的required设置为false
       * */
      let cgxxList = this.ruleForm.cgxx;
      let ruleList = this.rules.cgxx;
      let _ruleList = [];
      _ruleList = deepClone(ruleList);
      for (let i = 0; i < cgxxList.length; i++) {
        let chsx = cgxxList[i]; //1:500
        for (let j = 0; j < chsx.children.length; j++) {
          let child = chsx.children[j];
          let chsxRule = ruleList[i];
          this.checkDjbhAndZddm(chsx, child, chsxRule.children[j]);
          // if (checkObjNull(child)) {            //对需要校验的地块，且全为空的，移除校验
          /*if (this.checkDjbhAndZddm(chsx,child,chsxRule)) {
            //如果为null 不计入统计，将该区域的验证取消
            let childRule = chsxRule.children[j];
            // console.log(childRule);
            childRule.djbh[0].required = false;
            childRule.zddm[0].required = false;
            childRule.zddm.splice(1, 1);
          }*/
        }
      }
      await this.$refs[formName].validate((valid, obj) => {
        console.log(obj);
        let cgxxList = this.ruleForm.cgxx;
        if (valid) {
          // 已通过，自然通过
        } else {
          // 未通过，判断信息是否填写，如果都没有填写那也算
          /*for (let key in obj) {
            let msg = obj[key][0].message;
            // 用正则取出 被中括号包裹 的下标
            let address = obj[key][0].field;
            let regex = /\[(.+?)\]/g;
            let options = address.match(regex);
            let str = cgxxList[options[0][1]].name;
            // alert(eval('cgxxList'+options[0]+'.name'));
            if (options[1]){
              msg = msg.replace('请输入','');
              str = '请输入'+str+'下，地块'+(parseInt(options[1][1])+1)+'的'+msg;
            }
            doMessage(str,false);
            return false;
          }*/
        }
        pass = valid;
      });
      console.log(_ruleList);
      console.log(this.rules.cgxx);
      this.rules.cgxx = _ruleList;
      if (!pass) {
        let isError = document.getElementsByClassName("is-error");
        if (isError.length > 0) isError[0].querySelector("input").focus();
        return;
      }
      /* 使用自定义验证（暂不使用）
      let _this = this;
      let cgxxList = _this.ruleForm.cgxx;
      for(let i=0;i<cgxxList.length;i++){
        if (cgxxList[i].djbhVal||cgxxList[i].zddm) {
          let res = this.validCgxx(cgxxList[i]);
          if (!res.succ) {
            doMessage(res.msg,false);
            return;
          }
        }
      }
      doMessage("通过",true);
      return;
      */

      // 用于判断是否选择测绘工程师
      // if (this.ruleForm.jbxx.chgcs == null || this.ruleForm.jbxx.chgcs == "") {
      //   doMessage("请选择测绘工程师", false);
      //   return;
      // }
      // console.log(this.$store.state.fjidList);
      // 首先给每个添加过附件的成果添加对应附件id
      for (let item of this.$store.state.fjidList) {
        for (let value of item.fjlist) {
          if (value.children.length == 0) {
            this.$message({
              type: "error",
              message: `请上传${value.filename}信息`,
            });
            return;
          }
        }
        for (let item2 of this.ruleForm.cgxx) {
          if (item.itemname == item2.name) {
            for (let ind in item2.children) {
              if (ind == item.indexs) {
                item2.children[ind].cgfj = item.id;
              }
            }
          }
        }
      }
      // 用于保存用于提交的完整数据
      let newArr = [];
      console.log(this.ruleForm.cgxx);

      for (let item of this.ruleForm.cgxx) {
        newArr = [
          ...newArr,
          {
            name: item.name,
            children: [],
          },
        ];
      }
      for (let item of this.ruleForm.cgxx) {
        for (let index in item.children) {
          // 通过判断哪一个数据有值，然后去验证完整性
          if (
            item.children[index].djbh ||
            item.children[index].zddm ||
            item.children[index].cgfj
          ) {
            // 判断该事项下成果信息是否完整

            if (
              item.name == "1：500地形图测绘" ||
              item.name == "土地勘测定界"
            ) {
              if (!item.children[index].cgfj) {
                let Idx = parseInt(index) + 1;
                this.$message({
                  type: "error",
                  message: `提交失败，请确认${item.name}下的地块${Idx}的完整成果信息`,
                });
                return;
              }
            } else if (item.name == "规划用地定点测量") {
              if (!item.children[index].cgfj || !item.children[index].cgfj) {
                let Idx = parseInt(index) + 1;
                this.$message({
                  type: "error",
                  message: `提交失败，请确认${item.name}下的地块${Idx}的完整成果信息`,
                });
                return;
              }
            } else {
              if (
                !item.children[index].djbh ||
                !item.children[index].zddm ||
                !item.children[index].cgfj
              ) {
                let Idx = parseInt(index) + 1;
                this.$message({
                  type: "error",
                  message: `提交失败，请确认${item.name}下的地块${Idx}的完整成果信息`,
                });
                return;
              }
            }
            // 当该事项下每个成果信息完整后去判断地籍号是否重复
            if (
              item.name != "1：500地形图测绘" &&
              item.name != "土地勘测定界"
            ) {
              for (let i = 0; i < index; i++) {
                if (item.children[i].djbh == item.children[i + 1].djbh) {
                  this.$message({
                    type: "error",
                    message: `${item.name}下的地块地籍号重复`,
                  });
                  return;
                }
                i++;
              }
            }
            // 各种验证通过就添加至数组
            for (let value of newArr) {
              if (value.name == item.name) {
                value.children = [...value.children, item.children[index]];
              }
            }
          }
        }
      }
      // 用于检测提交的newArr是否为空
      let iskong = true;
      for (let index in newArr) {
        if (newArr[index].children.length != 0) {
          iskong = false;
        }
      }

      if (iskong) {
        this.$message({
          type: "warning",
          message: "没有提交成果信息哦",
        });
        return;
      }

      for (let item of newArr) {
        for (let value of item.children) {
          value.cghjzt = "提交";
        }
      }

      var params = {
        // chcgfzr: this.ruleForm.jbxx.chgcs,
        // fzrdh: this.ruleForm.jbxx.chgcsdh,
        // fzrsfz: this.idcard,
        yl2: this.ywxqyminformation.LOGID,
        xmjd: this.ywxqyminformation.FBXXCHJD,
        fszt: "1",
        dqhj: "成果汇交",
        xyhj: "业主单位验收",
        pid: this.ywxqyminformation.FBXXID,
        cgxx: newArr,
      };
      console.log(newArr);
      console.log(this.processList);
      // 提交一次需要清理一次缓存
      this.$store.commit("changefjidList");
      this.$http.post("dchy/dchyYwxx/ywxxSend", params).then(
        async (res) => {
          for (let index in this.$refs.cgfj) {
            await this.$refs.cgfj[index].uploadFun();
            await this.$refs.cgfj[index].delall();
          }
          this.$store.commit("changeywxqym");
          doMessage("提交成功!", true);
          this.$router.push("myyw");
        },
        (err) => {
          console.log(err);
        }
      );
    },
    //  第四步加载初始化的人员
    getJbry() {
      let param = {
        role: "成果验收经办人",
      };
      this.$http.post("roleuser/findByRole", param).then((res) => {
        if (res.data && res.data.userlist) {
          let _data = res.data.userlist;
          // 用户按分局区分
          let _user = [];
          //生成外层集合    ——分局

          // 创建外层
          for (let i = 0; i < _data.length; i++) {
            // 判断target是否已包含
            if (_user.length > 0) {
              let has = false;
              for (let j = 0; j < _user.length; j++) {
                if (_data[i].ssbm != null && _user[j].value === _data[i].ssbm) {
                  // 已有,向里加人
                  var usr2 = new userObj(_data[i].name, _data[i].id);
                  _user[j].children.push(usr2);
                  has = true;
                  break;
                } else if (
                  _data[i].ssbm != null &&
                  _user[j].value !== _data[i].ssbm
                ) {
                  // 没有继续在，判断
                } else if (_data[i].ssbm == null) {
                  // _user.push(usr2);
                  var usr2 = new userObj(_data[i].name, [
                    _data[i].name,
                    _data[i].id,
                  ]);
                  _user.splice(0, 0, usr2);
                  break;
                }
              }
              if (!has) {
                //确定没有，创建   +   向里加人
                if (_data[i].ssbm && _data[i].ssbm != "") {
                  var usr = new userObj(_data[i].ssbm, _data[i].ssbm, []);
                  var usr2 = new userObj(_data[i].name, _data[i].id);
                  usr.children.push(usr2);
                  _user.push(usr);
                }
              }
            } else {
              if (_data[i].ssbm && _data[i].ssbm != null) {
                var usr = new userObj(_data[i].ssbm, _data[i].ssbm, []);
                var usr2 = new userObj(_data[i].name, _data[i].id);
                usr.children.push(usr2);
                _user.push(usr);
              } else if (_data[i].ssbm == null) {
                //无分局人
                var usr2 = new userObj(_data[i].name, [
                  _data[i].name,
                  _data[i].id,
                ]);
                // _user.push(usr2);
                _user.splice(0, 0, usr2); //数组头部插入    a.ubshift(a,b,c...);
              }
            }
          }
          this.users = _user;
        }
      });
    },
    // 经办人选择
    handleSelChange(val, items) {
      console.log(this.yscgList);
      console.log(items);
      console.log(this.$refs.userSel);

      for (let index in this.yscgList) {
        if (this.yscgList[index].id == items.id) {
          if (val.length > 1) {
            this.cgshr = this.$refs.userSel[0].getCheckedNodes()[0].pathLabels[1];
            this.cgshrid = val[1];
          } else {
            this.cgshr = val[0][0];
            this.cgshrid = val[0][1];
          }
          this.yscgList[index].cgshrid = this.cgshrid;
          console.log(this.cgshr);
          console.log(this.cgshrid);
        }
      }
    },
    // dialog提示
    handleClose(done) {
      done();
    },
    // 不通过
    back2(name, indexs, Itemscgxx) {
      this.name = name;
      this.indexs = indexs;
      this.Itemscgxx = Itemscgxx;
      this.dialogState = "不通过";
      this.dialogtitle = "请输入理由";
      this.reasonshow = true;
      this.dialogVisible = true;
    },
    // 通过方法
    pass(name, indexs, Itemscgxx) {
      this.name = name;
      this.indexs = indexs;
      this.Itemscgxx = Itemscgxx;
      this.dialogState = "通过";
      this.reasonshow = false;

      if (this.Itemscgxx.logsList[0].dqhj == "业主单位验收") {
        for (let item of this.yscgList) {
          if (item.id == this.Itemscgxx.id) {
            if (!item.cgshrid) {
              this.$message({
                type: "warning",
                message: "请选择分局经办人",
              });
              return;
            }
          }
        }
        for (let item of this.ruleForm.cgxx) {
          if (item.name == this.name) {
            var id = item.children[this.indexs].cgxx.id;
            var yl2 = item.children[this.indexs].cgxx.logsList[0].id;
          }
        }

        var state = "";
        if (this.dialogState == "通过") {
          state = "已通过";
        }
        if (this.dialogState == "不通过") {
          state = "未通过";
        }

        var params = {
          pid: this.ywxqyminformation.FBXXID,
          fszt: "3",
          dqhj: "业主单位验收",
          xyhj: "分局成果审核",
          yszt: state,
          cgyszt: state,
          wgsyy: "",
          id: id,
          yl2: yl2,
          cgshrid: this.cgshrid,
          cgshr: this.cgshr,
        };
        this.$http.post("dchy/dchyYwxx/ywxxSend", params).then(
          (res) => {
            // this.isFinish = true;
            // this.formDisabled = true;
            for (let item of this.ruleForm.cgxx) {
              if (item.name == this.name) {
                item.children[this.indexs].refuse = true;
              }
            }
            doMessage("成功!", true);
            this.getcgxxlist();
          },
          (err) => {
            console.log(err);
          }
        );
      } else {
        this.dialogtitle = "请输入验证码";
        if (this.Itemscgxx.logsList[0].dqhj == "分局成果审核") {
          this.reasonshow = true;
          this.dialogtitle = "请输入通过理由";
        }
        this.dialogVisible = true;
      }
    },
    // dialog取消
    qxdialog() {
      // 重置清除存储的值
      window.localStorage.removeItem("vilidateNum");
      clearInterval(this.timer); // 清除定时器
      this.timer = null;
      this.show = true;
      this.isbtndis = false;
      this.dialogVisible = false;
    },
    // dialog确定
    async dialogqr() {
      for (let item of this.ruleForm.cgxx) {
        if (item.name == this.name) {
          var id = item.children[this.indexs].cgxx.id;
          var yl2 = item.children[this.indexs].cgxx.logsList[0].id;
        }
      }
      var state = "";
      if (this.dialogState == "通过") {
        state = "已通过";
      }
      if (this.dialogState == "不通过") {
        state = "未通过";
      }

      // if (this.Itemscgxx.logsList[0].dqhj == "测绘工程师确认") {
      //   if (window.localStorage.getItem("vilidateNum")) {
      //     var vilidateNum = window.localStorage.getItem("vilidateNum");
      //   }
      //   var yzm = this.yzm;
      //   if (vilidateNum === yzm) {
      //     // 验证 验证码是否通过
      //     // 清除验证码
      //     this.yzm = "";
      //     this.show = true;
      //     this.isbtndis = false;
      //     clearInterval(this.timer); // 清除定时器
      //     this.timer = null;
      //     var params = {
      //       pid: this.ywxqyminformation.FBXXID,
      //       fszt: "2",
      //       dqhj: "测绘工程师确认",
      //       xyhj: "业主单位验收",
      //       cgshqr: state,
      //       wqryy: "",
      //       id: id,
      //       yl2: yl2,
      //     };
      //     if (state == "未通过") {
      //       params.wqryy = this.reason;
      //       if (!this.reason) {
      //         this.$message({
      //           type: "warning",
      //           message: "请填写未通过理由",
      //         });
      //         return;
      //       }
      //     }
      //     await this.$http.post("dchy/dchyYwxx/ywxxSend", params).then(
      //       (res) => {
      //         // this.isFinish = true;
      //         // this.formDisabled = true;
      //         if (res.data) {
      //           doMessage("成功!", true);
      //         }
      //         for (let item of this.ruleForm.cgxx) {
      //           if (item.name == this.name) {
      //             item.children[this.indexs].refuse = true;
      //           }
      //         }
      //       },
      //       (err) => {
      //         console.log(err);
      //       }
      //     );
      //   } else {
      //     this.$message({
      //       message: "验证码输入有误或已超时，请检查验证码！",
      //       type: "warning",
      //       duration: 2000,
      //       offset: 150,
      //     });
      //   }
      // }

      if (this.Itemscgxx.logsList[0].dqhj == "业主单位验收") {
        for (let item of this.ruleForm.cgxx) {
          if (item.name == this.name) {
            var id = item.children[this.indexs].cgxx.id;
            var yl2 = item.children[this.indexs].cgxx.logsList[0].id;
          }
        }
        var state = "";
        if (this.dialogState == "通过") {
          state = "已通过";
        }
        if (this.dialogState == "不通过") {
          state = "未通过";
        }

        var params = {
          pid: this.ywxqyminformation.FBXXID,
          fszt: "3",
          dqhj: "业主单位验收",
          xyhj: "分局成果审核",
          yszt: state,
          cgyszt: state,
          wgsyy: this.reason,
          id: id,
          yl2: yl2,
          cgshrid: this.cgshrid,
          cgshr: this.cgshr,
        };
        if (state == "未通过") {
          params.wqryy = this.reason;
          if (!this.reason) {
            this.$message({
              type: "warning",
              message: "请填写未通过理由",
            });
            return;
          }
        }
        this.$http.post("dchy/dchyYwxx/ywxxSend", params).then(
          (res) => {
            // this.isFinish = true;
            // this.formDisabled = true;
            for (let item of this.ruleForm.cgxx) {
              if (item.name == this.name) {
                item.children[this.indexs].refuse = true;
              }
            }
            doMessage("成功!", true);
            this.getcgxxlist();
          },
          (err) => {
            console.log(err);
          }
        );
      }

      if (this.Itemscgxx.logsList[0].dqhj == "分局成果审核") {
        var params = {
          pid: this.ywxqyminformation.FBXXID,
          fszt: "4",
          dqhj: "业主单位验收",
          xyhj: "审核通过",
          shzt: state,
          cgshyj: this.reason,
          id: id,
          yl2: yl2,
          cgsfsh: state,
        };
        if (!this.reason) {
          this.$message({
            type: "warning",
            message: `请填写${state}理由`,
          });
          return;
        }
        this.$http.post("dchy/dchyYwxx/ywxxSend", params).then(
          (res) => {
            for (let item of this.ruleForm.cgxx) {
              if (item.name == this.name) {
                item.children[this.indexs].refuse = true;
                item.children[this.indexs].showTG = true;
              }
            }
            doMessage("成功!", true);
            if (state == "已通过") {
              this.createCgshjls(id); //ywxxid
            }
            this.shjg = state;
            this.sendNoticeMsg(id);
            this.getcgxxlist();
          },
          (err) => {
            console.log(err);
          }
        );
      }
      this.reason = "";
      this.dialogVisible = false;
    },
  },
  components: {
    Header,
    NavBar,
    Bottom,
    Upload,
    Upload2,
    Bread,
    Dropdown,
  },
  created() {
    // 获取当前用户信息
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    // 获取当前用户角色
    if (window.localStorage.getItem("userRole")) {
      this.userRole = window.localStorage.getItem("userRole");
    }
    if (window.localStorage.getItem("processList")) {
      this.processList = JSON.parse(window.localStorage.getItem("processList"));
    }
    // console.log(this.userinfo);
    this.getContent();
    console.log(this.ruleForm);
  },
  destroyed() {
    // 用于清理
    this.$store.commit("changefjidList");
  },
};
</script>

<style scoped>
.ywxqym {
  height: 100%;
  overflow-x: hidden;
  display: flex;
  flex-direction: column;
}
.nav-title {
  padding: 20px 0px 16px 383px;
}
.content {
  padding: 0 383px;
  margin-top: 30px;
  flex: 1;
}
.el-collapse {
  border-bottom: 0px;
}
.collapse_item >>> .el-collapse-item__header {
  font-size: 20px;
  color: #0091ff;
  font-weight: bold;
  border-bottom: 1px solid black !important;
}
.collapse_item >>> .el-collapse-item__wrap {
  border-bottom: 0 !important;
}
.collapse_item >>> .el-collapse-item__content {
  padding-bottom: 0 !important;
}
.el-collapse {
  border-top: 0;
}
.el-date-picker .el-input {
  width: 100%;
}
.messageClass {
  font-size: 18px;
}
.fjitem >>> .el-form-item__content {
  line-height: 0px !important;
}
.yzminput {
  /* width: 60% !important; */
  display: inline-block;
}
/* .yzminput >>> .el-input__inner{
  width:50%;
  display: inline-block;
} */
.yzmbtn {
  color: #66b1ff;
  border: 1px solid #66b1ff;
  display: inline-block;
}
.yzmbtn:hover {
  color: #ffffff;
  background-color: #66b1ff;
  display: inline-block;
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

.item_title {
  font-weight: 600;
  font-size: 20px;
  color: #0091ff;
  height: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}
.item_content {
  margin-top: 20px;
}
.item_title_left {
  line-height: 20px;
  padding-left: 10px;
  border-left: 4px solid #0091ff;
}
.item_title_right {
  display: flex;
  align-items: center;
  font-size: 30px;
}
.fengexian {
  border-bottom: 1px solid #000;
}
.dikuaier {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 30px;
  font-weight: 600;
  font-size: 18px;
  color: #0091ff;
  padding-left: 14px;
  margin-bottom: 10px;
}
.dikuaier div {
  line-height: 30px;
}
.dikuai_right {
  font-size: 30px;
}
</style>