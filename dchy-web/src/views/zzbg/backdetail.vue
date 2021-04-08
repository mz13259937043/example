<template>
  <div class="chdwzc">
    <headers></headers>
    <navbar></navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <div>当前位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>修改信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="content">
      <!-- 主体 -->
      <div class="chdwzc-content">
        <el-col :span="24" v-if="!readonly">
          <div class="global-sxtitle">
            <span
              >您的资质审核已提交至相应处室审核，审核时间为3-5个工作日，请耐心等待结果。</span
            >
          </div>
        </el-col>

        <div>
          <div class="global-flag">
            <span>基本信息</span>
            <!-- <el-button type="primary" style="height: 30px; line-height: 6px"
              >变更</el-button
            > -->
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
                <el-form-item label="单位地址" prop="dwdz">
                  <el-input
                    type="dwdz"
                    v-model="jbxxForm.dwdz"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="注册时间" prop="dwzcsj">
                  <el-input
                    type="dwzcsj"
                    v-model="jbxxForm.dwzcsj"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人姓名" prop="frmc">
                  <el-input
                    type="frmc"
                    v-model="jbxxForm.frmc"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="法定代表人身份证号" prop="frzgzh">
                  <el-input
                    type="frzgzh"
                    v-model="jbxxForm.frzgzh"
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
                <el-form-item label="联系电话" prop="lxrdh">
                  <el-input
                    type="lxrdh"
                    v-model="jbxxForm.lxrdh"
                    :disabled="readonly"
                  />
                </el-form-item>
              </el-col>

              <!-- 测绘资质信息 -->
              <div class="global-flag">测绘资质信息</div>
              <el-col :span="12">
                <el-form-item label="测绘资质证书编号" prop="bgzsbh">
                  <el-input type="bgzsbh" v-model="jbxxForm.bgzsbh" />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="证书有效期" prop="bgzsyxq">
                  <el-date-picker
                    v-model="jbxxForm.bgzsyxq"
                    type="date"
                    :editable="false"
                  ></el-date-picker>
                </el-form-item>
              </el-col>

              <!-- <el-col :span="12">
                <el-form-item label="资质等级" prop="bgzzdj">
                  <el-input type="bgzzdj" v-model="jbxxForm.bgzzdj" />
                </el-form-item>
              </el-col> -->
              <el-col :span="12">
                <el-form-item label="资质等级" prop="bgzzdj">
                  <el-select
                    v-model="jbxxForm.bgzzdj"
                    clearable
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
                <el-form-item label="发证部门" prop="bgfzbm">
                  <el-input type="bgfzbm" v-model="jbxxForm.bgfzbm" />
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="“多测合一”业务范围" prop="bgdchyywfw">
                  <el-checkbox-group v-model="dchyywfw">
                    <el-checkbox label="项目用地测量"></el-checkbox>
                    <el-checkbox label="工程实施测量"></el-checkbox>
                    <el-checkbox label="竣工及不动产登记测量"></el-checkbox>
                  </el-checkbox-group>
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

              <el-col :span="12" class="zzzs">
                <el-form-item label="资质证书">
                  <el-upload
                    action="#"
                    list-type="picture-card"
                    :auto-upload="false"
                    :on-change="handleChange"
                    multiple
                    :file-list="fileList"
                  >
                    <i slot="default" class="el-icon-plus"></i>
                    <div slot="file" slot-scope="{ file }">
                      <img
                        class="el-upload-list__item-thumbnail"
                        :src="file.url"
                        alt
                      />
                      <span class="el-upload-list__item-actions">
                        <span
                          class="el-upload-list__item-preview"
                          @click="handlePictureCardPreview(file)"
                        >
                          <i class="el-icon-zoom-in"></i>
                        </span>
                        <span
                          v-if="!disabled"
                          class="el-upload-list__item-delete"
                          @click="handleDownload(file)"
                        >
                          <i class="el-icon-download"></i>
                        </span>
                        <span
                          v-if="!disabled"
                          class="el-upload-list__item-delete"
                          @click="handleRemove(file)"
                        >
                          <i class="el-icon-delete"></i>
                        </span>
                      </span>
                    </div>
                  </el-upload>
                </el-form-item>
              </el-col>

              <!-- 仪器设备清单表格 -->
              <el-col :span="24" style="margin-bottom: 20px">
                <el-form-item label="仪器设备清单表格">
                  <el-table :data="dataSource" style="width: 100%" border>
                    <el-table-column
                      prop="xuhao"
                      label="序号"
                    ></el-table-column>
                    <el-table-column
                      prop="fileName"
                      label="文件名称"
                    ></el-table-column>
                    <el-table-column label="操作">
                      <template slot-scope="scope">
                        <div @click="onDelete(scope.$index, scope.row.key)">
                          <span href="javascript:;">
                            <img src="@/assets/img/del.png" class="img" />删除
                          </span>
                        </div>
                      </template>
                    </el-table-column>
                    <div slot="append" style="text-align: center">
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :show-file-list="false"
                        :limit="3"
                      >
                        <div style="color: red">
                          <i class="el-icon-plus">上传文件</i>
                        </div>
                      </el-upload>
                    </div>
                  </el-table>
                </el-form-item>
              </el-col>

              <!-- 人员信息 -->
              <div class="global-flag">人员信息</div>
              <el-col :span="24">
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
                  <el-table-column label="操作">
                    <template slot-scope="scope">
                      <div>
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
                  <!-- <el-button type="primary">保存</el-button> -->
                  <el-button
                    type="primary"
                    @click="tjzc"
                    :disabled="flag ? true : false"
                    >提交信息</el-button
                  >
                </div>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
    </div>
    <el-dialog :title="title" :visible.sync="visible" width="30%">
      <el-form
        ref="ryxxForm"
        :model="ryxxForm"
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
              <!-- <el-input type="bgtype" v-model="ryxxForm.bgtype" /> -->
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="上传资质证书">
              <el-upload
                action="#"
                list-type="picture-card"
                :auto-upload="false"
              >
                <i slot="default" class="el-icon-plus"></i>
                <div slot="file" slot-scope="{ file }">
                  <img
                    class="el-upload-list__item-thumbnail"
                    :src="file.url"
                    alt
                  />
                  <span class="el-upload-list__item-actions">
                    <span
                      class="el-upload-list__item-preview"
                      @click="handlePictureCardPreview(file)"
                    >
                      <i class="el-icon-zoom-in"></i>
                    </span>
                    <span
                      v-if="!disabled"
                      class="el-upload-list__item-delete"
                      @click="handleDownload(file)"
                    >
                      <i class="el-icon-download"></i>
                    </span>
                    <span
                      v-if="!disabled"
                      class="el-upload-list__item-delete"
                      @click="handleRemove(file)"
                    >
                      <i class="el-icon-delete"></i>
                    </span>
                  </span>
                </div>
              </el-upload>
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
import { downloadFile, closePage, doMessage } from "@/assets/js/common.js";

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
  dwdz: "",
  dwzcsj: "",
  frmc: "",
  frzgzh: "",
  lxr: "",
  lxrdh: "",
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
export default {
  name: "chdwzc",
  components: {
    headers,
    bottom,
    navbar,
  },
  data() {
    return {
      flag: false,
      readonly: true,
      bgtypeArr,
      fileList: [],
      dchyywfw: [],
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
    };
  },
  computed: {},
  mounted() {
    this.getAllInfo();
  },
  methods: {
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
    //  上传资质证书
    handleChange(file, fileList) {
      this.fileList.push(file);
      let fileFormData = new FormData();
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      // _.each(this.fileList, v => {
      //   tempData.append("files", v.raw)
      // })
      for (var i = 0; i < this.fileList.length; i++) {
        fileFormData.append("files", this.fileList[i].raw);
      }

      let filepath = "DCHY/资质等级/2389238490233482383";
      // fileFormData.append('files', this.fileList);
      // fileFormData.append("userid", this.userId);
      // fileFormData.append("userName", this.username);
      fileFormData.append("filepath", filepath);

      this.$http
        .post("ftpService/multFileUpload", fileFormData, requestConfig)
        .then((res) => {});
    },
    handleRemove(file) {},
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {},
    getAllInfo() {
      let status = "";
      let _this = this;
      _this.$http.post("chdw/getAllChdw?status=" + status).then((res) => {
        _this.jbxxForm = res;
      });
    },
    //  提交注册
    tjzc() {
      let _this = this;
      //  人员参数
      let bgpersonallistjson = _.cloneDeep(_this.data);
      //  资质信息参数
      _this.jbxxForm.bgzzfj = "资质附件";
      _this.jbxxForm.bgstate = "待审核";
      _this.jbxxForm.bgdid = "1";
      _this.jbxxForm.bgzsyxq = _this
        .$moment(_this.jbxxForm.bgzsyxq)
        .format("YYYY-MM-DD");
      let bgsqsj = _this.$moment(new Date()).format("YYYY-MM-DD");
      let dchyywfw = "";
      _.each(this.dchyywfw, (v) => {
        dchyywfw = dchyywfw ? dchyywfw + "," + v : v;
      });
      _this.jbxxForm.dchyywfw = dchyywfw;
      _this.jbxxForm.bgsqsj = bgsqsj;
      let bgzzxxjson = _this.jbxxForm;
      //  日志参数
      let logjson = {
        fsrxm: "测绘单位",
        fsrid: "1",
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
      };
      _this.$http.post("zzxx/addBgZzxx", params).then((res) => {
        // _this.$message({
        //   message: "注册成功！",
        //   type: "success",
        // });
        this.flag = true;
        doMessage("提交成功！", true);
        this.$router.go(-1);
      });
    },
    // 添加人员信息
    handleForm() {
      let _this = this;
      if (_this.title === "添加人员信息") {
        _this.ryxxForm.bginfor = "001434355";
        _this.ryxxForm.bgstate = "待审核";
        _this.ryxxForm.bgdid = "1";
        _this.ryxxForm.roleid = "1";

        let obj = _.cloneDeep(_this.ryxxForm);
        _this.data.push(obj);
        // _this.$http.post("chdw/addDwry",   ).then((res) => {
        _this.visible = false;

        // });
      } else {
        // _this.$http.post("chdw/updateDwry", params).then((res) => {
        // });
        _this.visible = false;
      }
    },
    editRyxx(record) {
      this.id = record.id;
      this.title = "编辑信息";
      this.visible = true;
      this.$nextTick(() => {
        this.ryxxForm = record;
      });
    },
    //  删除
    delqlrInfo(record) {
      this.$http.post("chdw/delDwry", { id: record.id }).then((res) => {});
      this.data = this.data.filter((item) => item.bsm !== record.bsm);
    },
    //  取消
    clear() {
      this.visible = false;
    },
    //  添加人员
    addPersonInfo() {
      this.id = null;
      this.title = "添加人员信息";
      this.visible = true;
      this.ryxxForm = {};
    },
  },
  created() {
    this.userInfo = this.$store.state.userInfo;
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
</style>