<template>
  <div class="upLoad">
    <el-table
      :data="fileList"
      class="fileTable"
      border
      :highlight-current-row="true"
      row-key="id"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      default-expand-all
    >
      <el-table-column
        prop="filename"
        label="附件信息（请上传加盖公章原件）"
        min-width="70%"
      >
        <template slot-scope="scope">
          <div class="fjxxx">
            <div class="fj_name" :class="{ fj_name2: scope.row.children }">
              <div
                style="color: red; margin-right: 5px"
                v-if="
                  scope.row.children &&
                  scope.row.filename != '材料' &&
                  scope.row.filename != '其他' &&
                  scope.row.filename != '其他材料'
                "
              >
                *
              </div>
              <div :title="scope.row.filename">{{ scope.row.filename }}</div>
              <div v-if="scope.row.children && scope.row.filename != '材料'">
                <span
                  @click="mbxz(scope.row)"
                  style="color: blue"
                  class="mbspan"
                  v-if="
                    scope.row.children &&
                    (scope.row.filename == '合同信息' ||
                      scope.row.filename == '中介机构承诺书')
                  "
                  >模板下载 ,
                </span>
                <span
                  style="margin-left: 5px; margin-right: 5px"
                  v-if="scope.row.children && scope.row.format"
                  >（ 文件格式要求：{{ scope.row.format }} ）</span
                >

                <span
                  style="margin-left: 10px; margin-right: 5px"
                  v-if="
                    scope.row.filename == '宗地测量成果' ||
                    scope.row.filename == '权籍调查成果报告' ||
                    scope.row.filename == '权籍调查成果附件' ||
                    scope.row.filename == '测绘机构权籍调查审核申请表' ||
                    scope.row.filename == '权籍调查测绘委托合同' ||
                    scope.row.filename == '权调报告及宗地信息'
                  "
                  >说明：{{ scope.row.shuoming }}</span
                >
              </div>
            </div>
            <div class="fj_dx" v-if="scope.row.filesize">
              {{ scope.row.filesize }}
            </div>
            <div class="fj_dx" v-if="scope.row.size">
              {{ (scope.row.size / 1024).toFixed(2) }}KB
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="20%">
        <template slot-scope="scope">
          <el-upload
            v-loading="loading"
            class="upload-demo"
            multiple
            action=""
            :show-file-list="false"
            :file-list="fileList"
            :http-request="upload"
            :auto-upload="true"
            :data="rowInfo(scope)"
            v-if="scope.row.children"
          >
            <div class="upload-title" v-if="upisShow">选择文件上传</div>
          </el-upload>

          <div v-if="!scope.row.children">
            <span
              href="javascript:;"
              style="color: red; margin-right: 14px"
              class="item_span"
              @click="onDelete(scope, fileList)"
              v-if="upisShow"
            >
              删除
            </span>
            <span
              href="javascript:;"
              style="color: blue; margin-right: 14px"
              class="item_span"
              @click="yulan(scope, fileList)"
              v-if="
                ylShow &&
                scope.row.filename.split('.')[
                  scope.row.filename.split('.').length - 1
                ] != 'dwg'
              "
              >预览</span
            >
            <span
              href="javascript:;"
              style="color: blue; margin-right: 0px"
              class="item_span"
              @click="downloaditem(scope, fileList)"
              v-if="xzShow"
              >下载</span
            >
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { guid, downloadFile } from "@/assets/js/common.js";
import {
  attachmentUpload,
  templateName,
  callService,
} from "@/assets/js/config.js";
import http from "@/api/http.js";
export default {
  data() {
    return {
      stage: [],
      dqhj: "",
      fileList: [],
      userinfo: "",
      // 上传是否显示
      upisShow: false,
      ylShow: false,
      xzShow: false,
      // 成果根路径保存
      rootpath: "",
      parentid: "",
      format: "",
      loading: false,
      formdatalist: [],
      delparamslist: [],
      mbwjdz: "", //模板文件地址
      year: "", //当前年份
      processList: "",
    };
  },
  props: {
    download: {
      type: Object,
      default: {},
    },
  },
  methods: {
    drawtable() {
      //判断新建进入组件的环节
      var str = this.download.itemname;
      if (str == "1：500地形图测绘") {
        this.stage = ["1：500地形图测量"];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
        ];
      } else if (str == "规划用地定点测量") {
        this.stage = ["规划用地定点测量报告", "规划用地定点测量成果表附图"];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
        ];
      } else if (str == "权籍调查") {
        if (this.download.FBXXCHJD == "1234wer1234qwer3rq3") {
          this.stage = [
            "宗地测量成果",
            "权籍调查成果报告",
            "权籍调查成果附件",
            "测绘机构权籍调查审核申请表",
            "权籍调查测绘委托合同",
            "权调报告及宗地信息",
          ];
          this.dqhj = "成果汇交";

          this.fileList = [
            {
              filename: this.stage[0],
              children: [],
              id: 1,
            },
            {
              filename: this.stage[1],
              children: [],
              id: 2,
            },
            {
              filename: this.stage[2],
              children: [],
              id: 3,
            },
            {
              filename: this.stage[3],
              children: [],
              id: 4,
            },
            {
              filename: this.stage[4],
              children: [],
              id: 5,
            },
            {
              filename: this.stage[5],
              children: [],
              id: 6,
            },
          ];
        } else if (this.download.FBXXCHJD == "1234w12erqwer3rq323") {
          this.stage = ["不动产权籍调查报告"];
          this.dqhj = "成果汇交";

          this.fileList = [
            {
              filename: this.stage[0],
              children: [],
              id: 1,
            },
          ];
        }
      } else if (str == "土地勘测定界") {
        this.stage = ["勘测定界图", "土地勘测定界技术报告书", "用地范围略图"];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 3,
          },
        ];
      } else if (str == "建（构）筑物的定线测量") {
        this.stage = ["建（构）筑物定线报告单", "建（构）筑物定线示意图"];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
        ];
      } else if (str == "建（构）筑物的验线测量") {
        this.stage = ["建（构）筑物验线报告单", "建（构）筑物验线示意图"];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
        ];
      } else if (str == "道路、地下管线、管网、管沟的定线测量") {
        this.stage = [
          "道路、地下管线、管网、管沟定线报告表",
          "道路、地下管线、管网、管沟定线成果附图",
        ];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
        ];
      } else if (str == "道路、地下管线、管网、管沟的验线测量") {
        this.stage = [
          "道路、地下管线、管网、管沟验线报告表",
          "道路、地下管线、管网、管沟验线成果附图",
        ];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
        ];
      } else if (str == "房屋面积预测") {
        this.stage = ["房屋预测", "房屋预售备案面积测算报告"];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
        ];
      } else if (str == "用地复核、规划条件核实测量") {
        this.stage = [
          "竣工测量报告",
          "建（构）筑物实测成果表附图",
          "建（构）筑物位置尺寸对比图",
          "建（构）筑物立面示意图",
          "建（构）筑物实测面积成果表附图",
          "规划绿地实测面积成果表附图",
          "停车位测量成果表附图",
          "建（构）筑物实测竣工图",
        ];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 3,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 4,
          },
          {
            filename: this.stage[4],
            children: [],
            id: 5,
          },
          {
            filename: this.stage[5],
            children: [],
            id: 6,
          },
          {
            filename: this.stage[6],
            children: [],
            id: 7,
          },
          {
            filename: this.stage[7],
            children: [],
            id: 8,
          },
        ];
      } else if (str == "房产测绘") {
        this.stage = [
          "房屋预测图",
          "房屋预售备案面积测算报告",
          "房屋实测图",
          "房产测绘成果报告",
        ];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 3,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 4,
          },
        ];
      } else if (str == "绿地、人防、消防测量") {
        this.stage = ["绿地", "人防", "消防"];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 3,
          },
        ];
      } else if (str == "规划竣工测量") {
        this.stage = [
          "竣工测量报告",
          "建（构）筑物实测成果表附图",
          "建（构）筑物位置尺寸对比图",
          "建（构）筑物立面示意图",
          "建（构）筑物实测面积成果表附图",
          "规划绿地实测面积成果表附图",
          "停车位测量成果表附图",
          "建（构）筑物实测竣工图",
        ];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 3,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 4,
          },
          {
            filename: this.stage[4],
            children: [],
            id: 5,
          },
          {
            filename: this.stage[5],
            children: [],
            id: 6,
          },
          {
            filename: this.stage[6],
            children: [],
            id: 7,
          },
          {
            filename: this.stage[7],
            children: [],
            id: 8,
          },
        ];
      } else if (str == "庭院内管线及附属设施测量") {
        this.stage = [
          "庭院内管线及附属设施测量报告",
          "庭院内管线及附属设施测量成果附图",
        ];
        this.dqhj = "成果汇交";

        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 2,
          },
        ];
      }
      if (this.download.fjid) {
        // 通过文件id拿到父path，父id
        let fileFormData = new FormData();
        let requestConfig = {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        };
        fileFormData.append("folderid", this.download.fjid);
        this.$http
          .post(
            // "http://222.90.207.221/dchy/dchy/ftpService/listFile2",
            "ftpService/listFile2",
            fileFormData,
            requestConfig
          )
          .then((res) => {
            this.parentid = res.data.id;
            this.rootpath = res.data.path;
          })
          .catch((err) => {
            console.log(err);
          });
        if (
          this.download.ywxqyminformation &&
          this.download.ywxqyminformation.logsList[0] != null
        ) {
          if (
            this.download.ywxqyminformation.logsList[0].dqhj == "成果汇交" &&
            this.userRole == "chdw"
          ) {
            this.upisShow = true;
            this.ylShow = false;
            this.xzShow = false;
          } else {
            this.upisShow = false;
            this.ylShow = true;
            this.xzShow = true;
          }
        } else {
          this.upisShow = true;
          this.ylShow = false;
          this.xzShow = false;
        }
        this.xzfj1(this.download.fjid);
      } else {
        this.upisShow = true;
      }
      // console.log(this.fileList);
      for (let value of this.fileList) {
        if (
          value.filename == "1：500地形图测量" ||
          value.filename == "勘测定界图" ||
          value.filename == "用地范围略图" ||
          value.filename == "勘测定界图" ||
          value.filename == "规划用地定点测量成果表附图" ||
          value.filename == "房屋预测" ||
          value.filename == "建（构）筑物的定线测量" ||
          value.filename == "建（构）筑物的验线测量" ||
          value.filename == "建（构）筑物定线示意图" ||
          value.filename == "建（构）筑物验线示意图" ||
          value.filename == "道路、地下管线、管网、管沟定线成果附图" ||
          value.filename == "道路、地下管线、管网、管沟验线成果附图" ||
          value.filename == "庭院内管线及附属设施测量成果附图" ||
          value.filename == "房屋实测" ||
          value.filename == "建（构）筑物实测成果表附图" ||
          value.filename == "建（构）筑物位置尺寸对比图" ||
          value.filename == "建（构）筑物立面示意图" ||
          value.filename == "建（构）筑物实测面积成果表附图" ||
          value.filename == "规划绿地实测面积成果表附图" ||
          value.filename == "停车位测量成果表附图" ||
          value.filename == "建（构）筑物实测竣工图" ||
          value.filename == "房屋预测图" ||
          value.filename == "房屋实测图"
        ) {
          value.format = "dwg";
        } else if (
          value.filename == "材料" ||
          value.filename == "权调报告及宗地信息" ||
          value.filename == "宗地测量成果" ||
          value.filename == "宗地测量成果"
        ) {
          // value.format = "无";
        } else if (
          value.filename == "权籍调查成果报告" ||
          value.filename == "权籍调查成果附件" ||
          value.filename == "权籍调查测绘委托合同"
        ) {
          value.format = "png，img，jpg，jpeg";
        } else {
          value.format = "pdf";
        }
        if (value.filename == "宗地测量成果") {
          value.shuoming = "包含宗地图(.dwg)和宗地面积量算成果表(.pdf)";
        }
        if (value.filename == "权籍调查成果报告") {
          value.shuoming = "图片按顺序命名(报告1...报告n)";
        }
        if (value.filename == "权籍调查成果附件") {
          value.shuoming = "图片命名(合同1-n，划拨决定书1-n)";
        }
        if (value.filename == "测绘机构权籍调查审核申请表") {
          value.shuoming = "测绘机构权籍调查审核申请表";
        }
        if (value.filename == "权籍调查测绘委托合同") {
          value.shuoming = "图片按顺序命名(报告1...报告n)";
        }
        if (value.filename == "权调报告及宗地信息") {
          value.shuoming =
            "包含权籍调查报告(.docx)、宗地入库坐标(.txt)、 宗地信息(.txt)";
        }
      }
    },
    xzfj1(id) {
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
          // console.log(res);
          this.rootpath = res.data.path;

          this.parentid = res.data.id;
          for (let value of res.data.dchyFjFolders) {
            for (let values of this.fileList) {
              if (value.path == values.filename) {
                values.children = value.dchyFjRecords;
              } else {
              }
            }
          }
          // console.log(this.fileList);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //统一删除
    async delall() {
      console.log(this.delparamslist);
      const responses = [];
      for (let i = 0; i < this.delparamslist.length; i++) {
        responses.push(
          await this.$http
            .get("ftpService/deleteFiles", { params: this.delparamslist[i] })
            .then(
              (res) => {
                console.log(res);
              },
              (err) => {
                console.log(err);
              }
            )
        );
      }
      this.delparamslist = [];
      console.log(responses);
    },
    //点击删除
    onDelete(scope, fileList) {
      console.log(this.delparamslist);
      var params = {
        filename: "",
        filepath: "",
        fjid: "",
      };
      // console.log(scope);
      // console.log(fileList);
      // 记录之前存过数据库的附件的params
      for (let value of fileList) {
        for (let item of value.children) {
          if (scope.row.filepath == item.filepath) {
            if (item.filename == scope.row.filename && !item.uid) {
              params.filename = item.filename;
              if (this.rootpath) {
                params.filepath = `/${this.rootpath}/${item.filepath}`;
              }
              params.fjid = scope.row.id;
              this.delparamslist.push(params);
            }
          }
        }
      }

      // 删除视图上的文件
      let compare = scope.row.filename;

      for (let value of fileList) {
        if (value.children && value.children.length > 0) {
          if (scope.row.filepath == value.filename) {
            for (let index in value.children) {
              if (
                value.children[index].filename &&
                value.children[index].filename === compare
              ) {
                value.children.splice(index, 1);
              }
            }
          }
        }
      }
      // 删除上传预处理保存的formdatalist
      let compare2 = scope.row.id;
      for (let index in this.formdatalist) {
        if (compare2 == this.formdatalist[index].get("files").id) {
          this.formdatalist.splice(index, 1);
        }
      }
    },
    rowInfo(scope) {
      return scope;
    },

    async uploadFun() {
      // this.loading = true;
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      const responses = [];
      for (let i = 0; i < this.formdatalist.length; i++) {
        responses.push(
          await this.$http.post(
            "ftpService/fileUpload",
            this.formdatalist[i],
            requestConfig
          )
        );
      }
      this.formdatalist = [];
      // console.log(responses);
      // this.loading = false;
    },
    // 上传预处理
    async upload(val) {
      if (val.file.size == 0) {
        this.$message.error("不能上传空文件，请重新上传");
        this.loading = false;
        return;
      }
      if (val.data.row.format) {
        var arr = val.file.name.split(".");
        var hz = arr[arr.length - 1];
        if (val.data.row.format == "png，img，jpg，jpeg") {
          if (
            "png" != hz &&
            "jpeg" != hz &&
            "jpg" != hz &&
            "PNG" != hz &&
            "JPEG" != hz &&
            "JPG" != hz
          ) {
            this.$message.error("文件格式不正确，请重新上传");
            this.loading = false;
            return;
          }
        } else {
          var format1 = val.data.row.format.toUpperCase();
          // 转小写
          var format2 = val.data.row.format.toLowerCase();
          var arr = val.file.name.split(".");
          // console.log(arr);
          var hz = arr[arr.length - 1];
          if (format1 != hz && format2 != hz) {
            this.$message.error("文件格式不正确，请重新上传");
            this.loading = false;
            return;
          }
        }
      }

      if (!this.rootpath) {
        //
        // 没有附件id表示成果汇交进来需要创建成果根目录
        //创建成果信息文件夹
        this.rootpath = `${attachmentUpload.cgxx}/${guid()}`;
        let fileFormData = new FormData();
        let requestConfig = {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        };
        fileFormData.append("filepath", this.rootpath);
        await this.$http
          .post("ftpService/fileUpload", fileFormData, requestConfig)
          .then((res) => {
            this.parentid = res.data.data.id;
          });
      }

      let fileFormData = new FormData();
      fileFormData.append("parentid", this.parentid);
      var filepath = `${val.data.row.filename}`;
      fileFormData.append("filepath", filepath);
      fileFormData.append("files", val.file);
      fileFormData.append("userid", this.userinfo.id);
      fileFormData.append("username", this.userinfo.frxm);
      fileFormData.append("filename", val.file.name);
      this.formdatalist.push(fileFormData);
      val.file.filename = val.file.name;
      val.file.filepath = filepath;
      val.file.id = guid();
      for (let value of this.fileList) {
        if (value.filename == val.data.row.filename) {
          value.children = [...value.children, val.file];
        }
      }
      this.$store.commit("changefjidList", {
        id: this.parentid,
        index: this.download.index,
        indexs: this.download.indexs,
        itemname: this.download.itemname,
        fjlist: this.fileList,
      });
    },
    yulan(scope, fileList) {
      console.log("预览");
      var path = encodeURI(
        `${this.rootpath}/${scope.row.filepath}/${scope.row.filename}`
      );
      // console.log(path);
      window.open(
        `http://xagh.org.cn:8083/view/url?url=http://10.7.120.10/file/${path}`,
        "_blank"
      );
    },
    downloaditem(scope, fileList) {
      // var url = "http://222.90.207.221/dchy/dchy/ftpService/downloadfile";
      var url = `${callService.downloadFile}`;
      var params = {
        filename: `${this.rootpath}/${scope.row.filepath}/${scope.row.filename}`,
      };
      // console.log(params);
      downloadFile(url, params);
    },
    mbxz(row) {
      // console.log(row);
      // var url = "ftpService/downloadfile";
      var url = `${callService.downloadFile}`;
      if (row.filename == "合同信息") {
        var params = {
          filename: `${this.mbwjdz}/${templateName.htmb}.docx`,
        };
        downloadFile(url, params);
      }
      if (row.filename == "中介机构承诺书") {
        var params = {
          filename: `${this.mbwjdz}/${templateName.zjjgcns}.docx`,
        };
        downloadFile(url, params);
      }
    },
  },
  created() {
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    if (window.localStorage.getItem("userRole")) {
      this.userRole = window.localStorage.getItem("userRole");
    }
    if (window.localStorage.getItem("processList")) {
      this.processList = JSON.parse(window.localStorage.getItem("processList"));
    }
    console.log("传入附件的信息", this.download);

    this.drawtable();
  },
  mounted() {
    // console.log("传入附件的信息111111");
  },
  destroyed() {},
};
</script>

<style scoped>
.upload-demo {
  display: flex;
  align-items: center;
}
.upload-title {
  color: blue;
}

.upLoad >>> .el-table__body {
  width: 100%;
}
.el-table-column {
  /* height: 40px !important; */
}
.item_span:hover {
  cursor: pointer;
  color: red;
}
.upload_moban {
  margin-left: 5px;
  color: blue;
}
.upload_moban:hover {
  cursor: pointer;
}
.upLoad >>> .el-table thead {
  color: #909399;
}
.upLoad >>> .el-form-item__conten {
  line-height: 0px !important;
}

.fjxxx {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.fileTable >>> .cell {
  display: flex;
}
.fileTable .fjxxx {
  flex: 1;
}
.fj_name {
  width: 82%;
  display: flex;
  line-height: initial;
}
.mbspan:hover {
  cursor: pointer;
}
.fj_name2 {
  width: 100%;
}
</style>