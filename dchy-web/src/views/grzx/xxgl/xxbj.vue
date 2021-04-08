<template>
  <div class="xxbj">
    <Header></Header>
    <NavBar></NavBar>
    <div class="nav-title">
      <div>当前所在位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
        <el-breadcrumb-item>信息编辑</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="xxbj-content">
      <el-form :model="xxbjForm" :rules="rules" ref="xxbjForm">
        <el-row v-if="false">
          <el-col :span="10">
            <el-form-item label="标题标识码" prop="id" label-width="100px">
              <el-input v-model="xxbjForm.id" type="id"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="标题" prop="title" label-width="60px">
              <el-input v-model="xxbjForm.title" type="title"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="信息类型" prop="type" label-width="80px">
              <el-select
                v-model="xxbjForm.type"
                type="type"
                placeholder="请选择信息类型"
                @change="changexxlx($event)"
              >
                <el-option
                  v-for="item in xxlxArr"
                  :key="item.value"
                  :label="item.title"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="事项类型" prop="item" label-width="80px">
              <el-select
                v-model="xxbjForm.item"
                placeholder="请选择事项类型"
                v-if="sxlxshow"
              >
              </el-select>
              <el-select
                v-model="xxbjForm.item"
                placeholder="请选择事项类型"
                v-else
                @change="changexxlx($event)"
              >
                <el-option
                  v-for="item in sxlxArr"
                  :key="item.value"
                  :label="item.title"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <Upload
            v-if="file.path"
            :download="{
              LOGDQHJ: '信息管理',
              CLSX: '',
              id: file.id,
              rootpath: file.path,
            }"
            ref="xxgl"
          ></Upload>
        </el-row>
        <el-form-item prop="content">
          <!-- 用于替换编辑器base64上传 -->
          <div>
            <!-- <el-upload 
                          style="display:none"
                          class="edit-uploader"
                          :data="{ zbBsm: upbsm }"
                          action="http://222.90.207.221/dchy/dchy/ftpService/multFileUpload"
                          :show-file-list="false"
                          multiple
                          :headers="header"
                          :on-progress="changeimage" 
                          :on-success="editorUploadSuccess"
                          :on-error="editorUploadError"
                          :before-upload="beforeEditorUpload" >
                              <i class="el-icon-plus avatar-uploader-icon" ref="aUpload">上传图片</i>
                      </el-upload>  -->
            <input
              class="filds"
              type="file"
              accept="image/png, image/jpeg, image/gif, image/jpg"
              @change="getImg"
              style="display: none"
              ref="fileBtn"
            />
          </div>
          <quill-editor
            v-model="xxbjForm.editorhtml"
            ref="myQuillEditor"
            :options="editorOption"
            @blur="onEditorBlur($event)"
            @focus="onEditorFocus($event)"
            @change="onEditorChange($event)"
          >
          </quill-editor>
        </el-form-item>

        <el-form-item align="right">
          <el-button class="btn" type="primary" @click="submitForm('xxbjForm')"
            >保存</el-button
          >
          <el-button
            class="btn"
            type="primary"
            @click="subAndPubForm('xxbjForm')"
            :disabled="flag ? true : false"
            >保存并发布</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Bottom from "@/components/Bottom.vue";
import NavBar from "@/components/NavBar.vue";
import Upload from "@/components/Upload.vue";
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
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import * as Quill from "quill"; //引入编辑器
import { quillEditor } from "vue-quill-editor";
import { formatDate, guid } from "@/assets/js/common.js";
import { imgurl } from "@/api/imgurl.js";
var fonts = [
  "SimSun",
  "SimHei",
  "Microsoft-YaHei",
  "KaiTi",
  "FangSong_GB2312",
  "FZXiaoBiaoSong-B05S",
];
var Font = Quill.import("formats/font");
Font.whitelist = fonts; //将字体加入到白名单
Quill.register(Font, true);
let Size = Quill.import("attributors/style/size");
Size.whitelist = [
  "10px",
  "12px",
  "14px",
  "16px",
  "18px",
  "19px",
  "20px",
  "21px",
  "22px",
  "23px",
  "24px",
  "25px",
  "26px",
  "27px",
  "28px",
  "29px",
  "30px",
  "32px",
  "36px",
];
Quill.register(Size, true);
const xxlxArr = [
  {
    title: "政策法规",
    value: "政策法规",
  },
  {
    title: "办事指南",
    value: "办事指南",
  },
];
const sxlxArr = [
  {
    title: "1：500地形图测绘",
    value: "1：500地形图测绘",
  },
  {
    title: "土地勘测定界",
    value: "土地勘测定界",
  },
  {
    title: "权籍调查（项目用地测量）",
    value: "权籍调查（项目用地测量）",
  },
  {
    title: "规划用地定点测量",
    value: "规划用地定点测量",
  },
  {
    title: "建（构）筑物的定线测量",
    value: "建（构）筑物的定线测量",
  },
  {
    title: "建（构）筑物的验线测量",
    value: "建（构）筑物的验线测量",
  },
  {
    title: "道路、地下管线、管网、管沟的定线测量",
    value: "道路、地下管线、管网、管沟的定线测量",
  },
  {
    title: "道路、地下管线、管网、管沟的验线测量",
    value: "道路、地下管线、管网、管沟的验线测量",
  },
  {
    title: "规划竣工测量",
    value: "规划竣工测量",
  },
  {
    title: "房屋面积预测",
    value: "房屋面积预测",
  },
  {
    title: "权籍调查（竣工及不动产登记测量）",
    value: "权籍调查（竣工及不动产登记测量）",
  },
  {
    title: "庭院内管线及附属设施测量",
    value: "庭院内管线及附属设施测量",
  },
];
var requestConfig = {
  headers: {
    "Content-Type": "multipart/form-data",
  },
};
export default {
  data() {
    return {
      xxbjForm: {
        id: "",
        title: "",
        type: undefined,
        item: undefined,
        editorhtml: "",
      },
      xxlxArr,
      sxlxArr,
      flag: false,
      sxlxshow: true,
      rules: {
        title: [
          { required: true, message: "请输入标题", trigger: "blur" },
          {
            min: 3,
            max: 100,
            message: "长度在 3 到 100 个字符",
            trigger: "blur",
          },
        ],
        type: [
          { required: true, message: "请选择信息类型", trigger: "change" },
        ],
        item: [
          { required: false, message: "请选择事项类型", trigger: "change" },
        ],
      },
      editorOption: {
        placeholder: "请输入文章内容",
        modules: {
          toolbar: {
            container: [
              ["bold", "italic", "underline", "strike"],
              ["blockquote", "code-block"],
              [{ header: 1 }, { header: 2 }],
              [{ list: "ordered" }, { list: "bullet" }],
              [{ script: "sub" }, { script: "super" }],
              [{ indent: "-1" }, { indent: "+1" }],
              [{ direction: "rtl" }],
              [
                {
                  size: [
                    "10px",
                    "12px",
                    "14px",
                    "16px",
                    "18px",
                    "19px",
                    "20px",
                    "21px",
                    "22px",
                    "23px",
                    "24px",
                    "25px",
                    "26px",
                    "27px",
                    "28px",
                    "29px",
                    "30px",
                    "32px",
                    "36px",
                  ],
                },
              ],
              // [{ 'size': ['small', false, 'large', 'huge'] }],
              [{ header: [1, 2, 3, 4, 5, 6, false] }],
              [{ font: fonts }],
              [{ color: [] }, { background: [] }],
              [{ align: [] }],
              ["clean"],
              ["link", "image"],
            ],
            handlers: {
              image: function (value) {
                if (value) {
                  // document.querySelector('.avatar-uploader2 input').click()
                  document.querySelector(".filds").click();
                } else {
                  this.quill.format("image", false);
                }
                // this.$refs.aUpload.click() //自定义图片上传回调
              },
            },
          },
          syntax: {
            highlight: (text) => hljs.highlightAuto(text).value,
          },
        },
        theme: "snow",
      },
      record: this.$route.query,
      header: "",
      content: "",
      userinfo: "",
      // 文件夹对象
      file: {
        id: "",
        path: "",
        guid: "",
        filename: "",
      },
      // editorOption: {
      //   placeholder: '编辑文章内容1'
      // },
    };
  },
  components: {
    Header,
    NavBar,
    Bottom,
    Upload,
    quillEditor,
  },
  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill;
    },
    upbsm() {
      return 111;
    },
  },
  created() {
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    if (this.record.id) {
      // 非空
      this.xxbjForm = this.record;
      // <img src="http://10.7.120.10/file/DCHY/信息管理/545eb448-a02a-82b9-31e9-7e7a5f2865b6/信息管理/中选通知书 - 副本.png">
      this.rules.item = [];
    }

    let fileFormData = new FormData();
    if (this.xxbjForm.fjid && this.xxbjForm.fjid != "") {
      // 查一下源目录
      fileFormData.append("folderid", this.xxbjForm.fjid);
      this.$http
        .post("ftpService/listFile2", fileFormData, requestConfig)
        .then((res) => {
          if (res.data) {
            this.file = res.data;
            window.localStorage.setItem("xzfjid", this.file.id);
          }
        });
    } else {
      // 无目录创建目录
      this.file.path = `${attachmentUpload.xxgl}/${guid()}`;
      fileFormData.append("filepath", this.file.path);
      fileFormData.append("filename", "");
      fileFormData.append("folderid", "");
      fileFormData.append("parentid", "");
      fileFormData.append("userid", this.userinfo.id);
      fileFormData.append("username", this.userinfo.name);
      this.$http
        .post("ftpService/fileUpload", fileFormData, requestConfig)
        .then((res) => {
          let data = res.data.data;
          if (data) {
            // this.file.id = data.id;
            // this.file.path = data.path;
            this.file = data;
            this.xxbjForm.fjid = data.id;
            window.localStorage.setItem("xzfjid", this.file.id);
          }
        });
    }
  },
  methods: {
    // 这就是那个详情页，用一下附件组件
    async getImg(e) {
      let file = e.target.files[0];
      let fileFormData = new FormData();

      /*
       * 先上传文件夹，完成后再上传文件
       * 退出时候，直接递归删除文件夹内的东西
       *
       * */

      if (!this.file.path != "") {
        // 无文件夹先传文件夹
        // this.file.path = `DCHY/信息管理/${guid()}`;
        this.file.path = `${attachmentUpload.xxgl}/${guid()}`;
        fileFormData.append("filepath", this.file.path);
        fileFormData.append("filename", "");
        fileFormData.append("folderid", "");
        fileFormData.append("parentid", "");
        fileFormData.append("userid", this.userinfo.id);
        fileFormData.append("username", this.userinfo.name);
        await this.$http
          .post("ftpService/fileUpload", fileFormData, requestConfig)
          .then((res) => {
            let data = res.data.data;
            if (data) {
              // this.file.id = data.id;
              // this.file.path = data.path;
              this.file = data;
              this.xxbjForm.fjid = data.id;
            }
          });
      }

      fileFormData = new FormData();
      // 上传文件，
      fileFormData.append("folderid", this.file.id);
      fileFormData.append("parentid", this.file.id);
      fileFormData.append("filepath", `信息管理/images`);
      fileFormData.append("files", file);
      fileFormData.append("userid", this.userinfo.id);
      fileFormData.append("username", this.userinfo.name);
      fileFormData.append("filename", file.name);
      this.$http
        .post(`ftpService/fileUpload`, fileFormData, requestConfig)
        .then((res) => {
          // 将这块上传的东西展示预览一下
          // let filePath = res.data.data.dchyFjRecords[0].filepath;
          // let fileName = res.data.data.dchyFjRecords[0].filename;
          //let imgUrl = "http://222.90.207.222:9009" + "/" + filePath + fileName;

          let imgUrl =
            imgurl() +
            this.file.path +
            "/" +
            res.data.data.filepath +
            "/" +
            res.data.data.filename;
          this.getImgUrl(imgUrl);
        })
        .catch((err) => {
          //console.log("图片插入失败");
          this.$message.error("图片上传失败");
        });
      // this.getImgSever(imgurl);
    },
    getImgUrl(res) {
      let quill = this.$refs.myQuillEditor.quill;
      if (res) {
        // 获取光标所在位置
        let length = quill.getSelection().index;
        // 插入图片，res为服务器返回的图片链接地址
        quill.insertEmbed(length, "image", res);
        // 调整光标到最后
        quill.setSelection(length + 1);
        this.$refs["fileBtn"].value = "";
      } else {
        // 提示信息，需引入Message
        this.$message.error("图片插入失败");
      }
    },
    onEditorChange({ editor, html, text }) {
      this.content = html;
    },
    // 选择框联动
    changexxlx(value) {
      if (value === "政策法规") {
        this.sxlxshow = true;
        this.xxbjForm.item = undefined;
        this.rules.item = [];
      } else {
        this.sxlxshow = false;
      }
    },
    changesxlx() {},
    // 上传时
    changeimage(event, file, fileList) {
      file.filepath = file.name;
      this.$http.post("api/ftpService/multFileUpload", file).then((res) => {
        if (res.status === 200 && _this.record.id) {
          _this.$message.success("更新成功");
        } else {
          _this.$message.success("新增成功");
        }
      });
    },
    // 保存
    submitForm(formName) {
      let rstUrl = "api/addPolicy";
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this;
          _this.xxbjForm.state = "草稿";
          _this.xxbjForm.username = "admin";
          // _this.id = _this.record?_this.record.id:''
          // console.log("_this.xxbjForm.id", _this.xxbjForm.id);
          if (_this.xxbjForm.id != "") {
            rstUrl = "api/updatePolicy";
          }
          _this.$http.post(rstUrl, _this.xxbjForm).then(async (res) => {
            await this.$refs.xxgl.uploadFun();
            await this.$refs.xxgl.delall();
            if (res.status === 200 && _this.xxbjForm.id) {
              _this.$message.success("更新成功");
            } else {
              _this.$message.success("新增成功");
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 保存并发布
    subAndPubForm(formName) {
      let rstUrl = "api/addPolicy";
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm("是否立即发布?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            const _this = this;
            _this.xxbjForm.state = "发布";
            _this.xxbjForm.username = "admin";
            // _this.id = _this.record?_this.record.id:''
            if (_this.xxbjForm.id != "") {
              rstUrl = "api/updatePolicy";
            }

            _this.$http.post(rstUrl, _this.xxbjForm).then(async (res) => {
              await this.$refs.xxgl.uploadFun();
              await this.$refs.xxgl.delall();
              if (res.status === 200 && _this.xxbjForm.id) {
                doMessage("更新成功", true);
                this.flag = true;
                this.$router.push("xxgl");
              } else {
                doMessage("新增成功", true);
                this.flag = true;
                this.$router.push("xxgl");
              }
            });
            // _this.$http.post("zzxx/addBgZzxx", params).then(
            //   async (res) => {
            //     console.log(res);
            //     // _this.$message({
            //     //   message: "注册成功！",
            //     //   type: "success",
            //     //   duration: 2000,
            //     // });
            //     await this.$refs.fjzzbg.uploadFun();
            //     await this.$refs.fjzzbg.delall();
            //     _this.$nextTick(() => {
            //       // const { href } = _this.$router.resolve({
            //       //   name: "index",
            //       // });
            //       this.flag = true;
            //       doMessage("注册成功！", true);
            //       window.localStorage.setItem("bgzzxxdate", 0);
            //       this.$router.go(-1);
            //     });
            //   },
            //   (err) => {
            //     _this.$message.error("注册失败！");
            //   }
            // );
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //富文本编辑器相关方法
    onEditorReady(editor) {
      // 准备编辑器
    },
    onEditorBlur() {}, // 失去焦点事件
    onEditorFocus() {}, // 获得焦点事件
    beforeEditorUpload() {
      // 显示loading动画
      this.quillUpdateImg = true;
    },
    editorUploadSuccess(res, file) {
      // 获取富文本组件实例
      let quill = this.$refs.myQuillEditor.quill;
      // 如果上传成功
      if (res.code === 0) {
        // 获取光标所在位置
        let length = quill.getSelection().index;
        // 插入图片  res.info为服务器返回的图片地址
        quill.insertEmbed(length, "image", res.data.filepath);
        // 调整光标到最后
        quill.setSelection(length + 1);
      } else {
        this.$message.error("图片插入失败");
      }
      // loading动画消失
      this.quillUpdateImg = false;
    },
    // 富文本图片上传失败
    editorUploadError(file) {
      // loading动画消失

      this.quillUpdateImg = false;
      this.$message.error("图片插入失败");
    },
  },
};
</script>

<style scoped>
.xxbj {
  width: 100%;
  height: 100%;
}
.xxbj >>> .ql-editor {
  min-height: 260px;
  height: 400px;
}
.xxbj-content {
  padding: 0 20%;
  min-height: 572px;
  height: auto;
}
.xxbj >>> p {
  margin: 10px;
}

.btn {
  color: #f8f9fa;
  border: 1px solid #66b1ff;
}
.btn:hover {
  color: #fff;
  border: 1px solid #66b1ff;
  background: #006cff;
}
/* .xxbj>>>.edit_container,
.xxbj>>>.quill-editor {
  height: 300px;
} */
.xxbj >>> .ql-snow .ql-picker.ql-size .ql-picker-label::before,
.xxbj >>> .ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="small"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="small"]::before {
  content: "10px";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="large"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="large"]::before {
  content: "18px";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="huge"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}

.xxbj >>> .ql-snow .ql-picker.ql-header .ql-picker-label::before,
.xxbj >>> .ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: "文本";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="1"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="1"]::before {
  content: "标题1";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="2"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="2"]::before {
  content: "标题2";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="3"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="3"]::before {
  content: "标题3";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="4"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="4"]::before {
  content: "标题4";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="5"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="5"]::before {
  content: "标题5";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="6"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="6"]::before {
  content: "标题6";
}

.xxbj >>> .ql-snow .ql-picker.ql-font .ql-picker-label::before,
.xxbj >>> .ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: "标准字体";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="serif"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="serif"]::before {
  content: "衬线字体";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="monospace"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="monospace"]::before {
  content: "等宽字体";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="SimSun"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="SimSun"]::before {
  content: "宋体";
  font-family: "SimSun";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="SimHei"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="SimHei"]::before {
  content: "黑体";
  font-family: "SimHei";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="Microsoft-YaHei"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="Microsoft-YaHei"]::before {
  content: "微软雅黑";
  font-family: "Microsoft YaHei";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="KaiTi"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="KaiTi"]::before {
  content: "楷体";
  font-family: "KaiTi";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="FangSong_GB2312"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="FangSong_GB2312"]::before {
  content: "仿宋GB2312";
  font-family: "FangSong_GB2312";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="FZXiaoBiaoSong-B05S"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="FZXiaoBiaoSong-B05S"]::before {
  content: "方正小标宋简体";
  font-family: "FZXiaoBiaoSong-B05S";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="Times-New-Roman"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="Times-New-Roman"]::before {
  content: "Times New Roman";
  font-family: "Times New Roman";
}
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="sans-serif"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="sans-serif"]::before {
  content: "sans-serif";
  font-family: "sans-serif";
}
.xxbj >>> .ql-font-SimSun {
  font-family: "SimSun";
}
.xxbj >>> .ql-font-SimHei {
  font-family: "SimHei";
}
.xxbj >>> .ql-font-Microsoft-YaHei {
  font-family: "Microsoft YaHei";
}
.xxbj >>> .ql-font-KaiTi {
  font-family: "KaiTi";
}
.xxbj >>> .ql-font-FangSong_GB2312 {
  font-family: "FangSong_GB2312";
}
.xxbj >>> .ql-font-FZXiaoBiaoSong-B05S {
  font-family: "FZXiaoBiaoSong B05S";
}
.xxbj >>> .ql-font-Times-New-Roman {
  font-family: "Times New Roman";
}
.xxbj >>> .ql-font-sans-serif {
  font-family: "sans-serif";
}
.xxbj >>> .ql-snow .ql-picker.ql-size,
.xxbj >>> .ql-snow .ql-picker.ql-header {
  width: 75px !important;
}

.xxbj >>> .ql-snow .ql-picker.ql-font {
  width: 80px !important;
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="10px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="10px"]::before {
  content: "10px";
  font-size: 16px;
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="12px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="12px"]::before {
  content: "12px";
  font-size: 16px;
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="14px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="14px"]::before {
  content: "14px";
  font-size: 16px;
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="16px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="16px"]::before {
  content: "16px";
  font-size: 16px;
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="18px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="18px"]::before {
  content: "18px";
  /*font-size: 18px;*/
  font-size: 16px;
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="20px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="20px"]::before {
  content: "20px";
  font-size: 16px;
  /*font-size: 20px;*/
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="24px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="24px"]::before {
  content: "24px";
  font-size: 16px;
  /*font-size: 24px;*/
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="32px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="32px"]::before {
  content: "32px";
  font-size: 16px;
  /*font-size: 32px;*/
}

.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="36px"]::before,
.xxbj
  >>> .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="36px"]::before {
  content: "36px";
  font-size: 16px;
  /*font-size: 36px;*/
}

/* .xxbj>>>.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="48px"]::before,
.xxbj>>>.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="48px"]::before {
    content: '48px';
    font-size: 16px; */
/*font-size: 48px;*/
/* }
 
.xxbj>>>.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="64px"]::before,
.xxbj>>>.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="64px"]::before {
    content: '64px';
    font-size: 16px; */
/*font-size: 64px;*/
/* }
 
.xxbj>>>.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="72px"]::before,
.xxbj>>>.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="72px"]::before {
    content: '72px';
    font-size: 16px; */
/*font-size: 72px;*/
/* } */
</style>
