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
      upisShow: true,
      ylShow: false,
      xzShow: false,

      // 统一保存预览外层路径
      waipath: "",
      // 用于人员相关的
      waipath2: "",

      // 成果根路径保存
      rootpath: "",

      parentid: "",
      path: "",
      format: "",
      loading: false,

      formdatalist: [],
      delparamslist: [],

      mbwjdz: "", //模板文件地址
      year: "", //当前年份
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
      if (
        this.download.LOGDQHJ == "成果汇交" ||
        this.download.LOGDQHJ == "成果负责人确认" ||
        this.download.LOGDQHJ == "成果验收" ||
        this.download.LOGDQHJ == "成果审核"
      ) {
        var str = this.download.CLSX;
        if (str == "1：500地形图测绘") {
          this.stage = ["1：500地形图测量"];
          this.dqhj = "成果汇交";
          this.upisShow = true;
          this.fileList = [
            {
              filename: this.stage[0],
              children: [],
              id: 1,
            },
          ];

          if (window.localStorage.getItem("xzfjid")) {
            //
            if (!this.download.ywblItem) {
              this.upisShow = true;
              this.ylShow = true;
              this.xzShow = true;
            } else {
              this.upisShow = false;
              this.ylShow = true;
              this.xzShow = true;
            }

            this.xzfj1();
          }
        } else if (str == "规划用地定点测量") {
          this.stage = ["规划用地定点测量报告", "规划用地定点测量成果表附图"];
          this.dqhj = "成果汇交";
          this.upisShow = true;
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
          if (this.download.CHJD == "项目用地测量") {
            this.stage = [
              "宗地测量成果",
              "权籍调查成果报告",
              "权籍调查成果附件",
              "测绘机构权籍调查审核申请表",
              "权籍调查测绘委托合同",
              "权调报告及宗地信息",
            ];
            this.dqhj = "成果汇交";
            this.upisShow = true;
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
            console.log(this.fileList);
          } else if (this.download.CHJD == "竣工及不动产登记测量") {
            this.stage = ["房屋实测", "不动产权籍调查报告"];
            this.dqhj = "成果汇交";
            this.upisShow = true;
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
        } else if (str == "土地勘测定界") {
          this.stage = ["勘测定界图", "土地勘测定界技术报告书", "用地范围略图"];
          this.dqhj = "成果汇交";
          this.upisShow = true;
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
          this.upisShow = true;
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
          this.upisShow = true;
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
          this.upisShow = true;
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
          this.upisShow = true;
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
          this.upisShow = true;
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
          this.upisShow = true;
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
          this.upisShow = true;
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
        if (window.localStorage.getItem("xzfjid")) {
          console.log(22222222222222222222222);
          if (this.download.LOGDQHJ == "成果汇交") {
            if (!this.download.ywblItem) {
              this.upisShow = true;
              this.ylShow = false;
              this.xzShow = false;
            } else {
              this.upisShow = false;
              this.ylShow = true;
              this.xzShow = true;
            }
          } else {
            this.upisShow = false;
            this.ylShow = true;
            this.xzShow = true;
          }
          this.xzfj1();
        }
      } else if (
        this.download.LOGDQHJ == "测绘实施附件" ||
        this.download.LOGDQHJ == "成果汇交附件" ||
        this.download.LOGDQHJ == "成果负责人确认附件" ||
        this.download.LOGDQHJ == "成果验收附件" ||
        this.download.LOGDQHJ == "成果审核附件"
      ) {
        this.dqhj = "测绘实施";
        this.upisShow = true;
        this.stage = ["材料"];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 1,
          },
        ];
        if (window.localStorage.getItem("xzfjids")) {
          this.upisShow = false;
          this.ylShow = true;
          this.xzShow = true;
          this.xzfj2();
        }
      } else if (this.download.LOGDQHJ == "chdwzc_zzzs") {
        this.dqhj = "测绘单位注册";
        this.upisShow = true;
        this.stage = [
          "资质证书扫描件",
          "营业执照（或法人证明）",
          "仪器设备清单",
          "中介机构承诺书",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
        ];
      } else if (this.download.LOGDQHJ == "zzsh") {
        this.dqhj = "资质审核";
        this.upisShow = true;
        this.stage = [
          "资质证书扫描件",
          "营业执照（或法人证明）",
          "仪器设备清单",
          "中介机构承诺书",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
        ];

        if (window.localStorage.getItem("xzfjid")) {
          this.upisShow = false;
          this.ylShow = true;
          this.xzShow = true;
          this.xzfj1();
        }
      } else if (this.download.LOGDQHJ == "zzsh_ryxx") {
        this.dqhj = "资质变更人员信息";
        this.upisShow = true;
        this.stage = [
          "身份证扫描件",
          "资格证扫描件",
          "作业证扫描件",
          "劳动合同扫描件",
          "社保证明扫描件",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
          {
            filename: this.stage[4],
            children: [],
            id: 45,
          },
          {
            filename: this.stage[5],
            children: [],
            id: 46,
          },
        ];
        if (window.localStorage.getItem("xzfjids")) {
          this.upisShow = false;
          this.ylShow = true;
          this.xzShow = true;
          this.xzfj2();
        }
      } else if (this.download.LOGDQHJ == "jdcx") {
        this.dqhj = "进度查询";
        this.upisShow = false;
        this.stage = [
          "资质证书扫描件",
          "营业执照（或法人证明）",
          "仪器设备清单",
          "中介机构承诺书",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
        ];
        if (window.localStorage.getItem("xzfjid")) {
          this.upisShow = false;
          this.ylShow = true;
          this.xzShow = true;
          this.xzfj1();
        }
      } else if (this.download.LOGDQHJ == "jdcx_tuihui") {
        this.dqhj = "进度查询";
        this.upisShow = true;
        this.stage = [
          "资质证书扫描件",
          "营业执照（或法人证明）",
          "仪器设备清单",
          "中介机构承诺书",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
        ];

        if (window.localStorage.getItem("xzfjid")) {
          this.upisShow = true;
          this.ylShow = false;
          this.xzShow = false;
          this.xzfj1();
        }
      } else if (this.download.LOGDQHJ == "zzbg") {
        this.dqhj = "资质变更";
        this.upisShow = false;
        this.stage = [
          "资质证书扫描件",
          "营业执照（或法人证明）",
          "仪器设备清单",
          "中介机构承诺书",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
        ];

        if (window.localStorage.getItem("xzfjid")) {
          this.upisShow = false;
          this.ylShow = true;
          this.xzShow = true;
          this.xzfj1();
        }
      } else if (this.download.LOGDQHJ == "zzbg_bg") {
        this.dqhj = "资质变更";
        this.upisShow = true;
        this.stage = [
          "资质证书扫描件",
          "营业执照（或法人证明）",
          "仪器设备清单",
          "中介机构承诺书",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
        ];

        if (window.localStorage.getItem("xzfjid")) {
          this.upisShow = true;
          this.ylShow = false;
          this.xzShow = false;
          this.xzfj1();
        }
      } else if (this.download.LOGDQHJ == "chdwzc_zzzs_tjry") {
        this.dqhj = "测绘单位个人注册";
        this.upisShow = true;
        this.stage = [
          "身份证扫描件",
          "资格证扫描件",
          "作业证扫描件",
          "劳动合同扫描件",
          "社保证明扫描件",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
          {
            filename: this.stage[4],
            children: [],
            id: 45,
          },
          {
            filename: this.stage[5],
            children: [],
            id: 46,
          },
        ];
        if (window.localStorage.getItem("xzfjids")) {
          this.upisShow = true;
          this.ylShow = false;
          this.xzShow = false;
          this.xzfj2();
        }
      } else if (this.download.LOGDQHJ == "jdcx_ryxx") {
        this.dqhj = "进度查询人员信息";
        this.upisShow = true;
        this.stage = [
          "身份证扫描件",
          "资格证扫描件",
          "作业证扫描件",
          "劳动合同扫描件",
          "社保证明扫描件",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
          {
            filename: this.stage[4],
            children: [],
            id: 45,
          },
          {
            filename: this.stage[5],
            children: [],
            id: 46,
          },
        ];
        if (window.localStorage.getItem("xzfjids")) {
          this.ylShow = true;
          this.xzShow = true;
          this.upisShow = false;
          this.xzfj2();
        }
      } else if (this.download.LOGDQHJ == "jdcx_ryxx_tuihui") {
        debugger;
        this.dqhj = "进度查询人员信息";
        this.upisShow = true;
        this.stage = [
          "身份证扫描件",
          "资格证扫描件",
          "作业证扫描件",
          "劳动合同扫描件",
          "社保证明扫描件",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
          {
            filename: this.stage[4],
            children: [],
            id: 45,
          },
          {
            filename: this.stage[5],
            children: [],
            id: 46,
          },
        ];
        if (window.localStorage.getItem("xzfjids")) {
          this.upisShow = true;
          this.ylShow = false;
          this.xzShow = false;
          this.xzfj2();
        }
      } else if (this.download.LOGDQHJ == "zzbg_ryxx") {
        this.dqhj = "资质变更人员信息";
        this.upisShow = false;
        this.stage = [
          "身份证扫描件",
          "资格证扫描件",
          "作业证扫描件",
          "劳动合同扫描件",
          "社保证明扫描件",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
          {
            filename: this.stage[4],
            children: [],
            id: 45,
          },
          {
            filename: this.stage[5],
            children: [],
            id: 46,
          },
        ];
        if (window.localStorage.getItem("xzfjids")) {
          this.upisShow = false;
          this.ylShow = true;
          this.xzShow = true;
          this.xzfj2();
        }
      } else if (this.download.LOGDQHJ == "zzbg_ryxx_bg") {
        this.dqhj = "资质变更人员信息";
        this.upisShow = true;
        this.stage = [
          "身份证扫描件",
          "资格证扫描件",
          "作业证扫描件",
          "劳动合同扫描件",
          "社保证明扫描件",
          "其他材料",
        ];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 41,
          },
          {
            filename: this.stage[1],
            children: [],
            id: 42,
          },
          {
            filename: this.stage[2],
            children: [],
            id: 43,
          },
          {
            filename: this.stage[3],
            children: [],
            id: 44,
          },
          {
            filename: this.stage[4],
            children: [],
            id: 45,
          },
          {
            filename: this.stage[5],
            children: [],
            id: 46,
          },
        ];
        if (window.localStorage.getItem("xzfjids")) {
          this.upisShow = true;
          this.ylShow = false;
          this.xzShow = false;
          this.xzfj2();
        }
      } else if (this.download.LOGDQHJ == "合同上传") {
        this.dqhj = "合同上传";
        this.upisShow = true;
        this.stage = ["合同信息"];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 5,
          },
        ];
        if (window.localStorage.getItem("xzfjid")) {
          this.upisShow = true;
          this.ylShow = false;
          this.xzShow = false;
          this.xzfj1();
        }
      } else if (this.download.LOGDQHJ == "合同确认") {
        console.log(this.download);
        this.dqhj = "合同确认";
        this.upisShow = false;
        this.stage = ["合同信息"];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 6,
          },
        ];
        console.log(this.fileList);

        if (window.localStorage.getItem("xzfjid")) {
          this.upisShow = false;
          this.ylShow = true;
          this.xzShow = true;
          this.xzfj1();
        }
      } else if (this.download.LOGDQHJ == "信息管理") {
        console.log(this.download);
        this.dqhj = "信息管理";
        this.upisShow = true;
        this.stage = ["信息管理"];
        this.fileList = [
          {
            filename: this.stage[0],
            children: [],
            id: 6,
          },
        ];
        console.log(this.fileList);

        if (window.localStorage.getItem("xzfjid")) {
          this.upisShow = true;
          this.ylShow = false;
          this.xzShow = false;
          this.xzfj1();
        }
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
          value.filename == "建（构）筑物实测竣工图"
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
          value.shuoming =
            "图片分类按顺序命名(例如：出让合同1-n，划拨决定书1-n)";
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
    xzfj1() {
      debugger;
      let xzfj = window.localStorage.getItem("xzfjid");
      let fileFormData = new FormData();
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      fileFormData.append("folderid", xzfj);
      this.$http
        .post(
          // "http://222.90.207.221/dchy/dchy/ftpService/listFile2",
          "ftpService/listFile2",
          fileFormData,
          requestConfig
        )
        .then((res) => {
          console.log(res);
          this.waipath = res.data.path;
          this.parentid = res.data.id;
          for (let value of res.data.dchyFjFolders) {
            for (let values of this.fileList) {
              if (value.path == values.filename) {
                values.children = value.dchyFjRecords;
              } else {
              }
            }
          }
          console.log(this.fileList);
          window.localStorage.setItem("fjList", JSON.stringify(this.fileList));
        })
        .catch((err) => {
          console.log(err);
        });
    },
    xzfj2() {
      debugger;
      let xzfj = window.localStorage.getItem("xzfjids");
      let fileFormData = new FormData();
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      fileFormData.append("folderid", xzfj);
      this.$http
        .post(
          // "http://222.90.207.221/dchy/dchy/ftpService/listFile2",
          "ftpService/listFile2",
          fileFormData,
          requestConfig
        )
        .then((res) => {
          // console.log(res);
          //
          this.waipath = res.data.path;
          for (let value of res.data.dchyFjFolders) {
            for (let values of this.fileList) {
              if (value.path == values.filename) {
                values.children = value.dchyFjRecords;
                // console.log(values.children);
                //
              }
            }
          }
          window.localStorage.setItem("fjLists", JSON.stringify(this.fileList));
          //
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //统一删除
    async delall() {
      console.log(this.delparamslist);
      debugger;
      const responses = [];
      for (let i = 0; i < this.delparamslist.length; i++) {
        responses.push(
          await this.$http
            .get("ftpService/deleteFiles", { params: this.delparamslist[i] })
            .then(
              (res) => {
                console.log(res);
                debugger;
              },
              (err) => {
                console.log(err);
              }
            )
        );
      }
      this.delparamslist = [];
      console.log(responses);
      debugger;
    },
    //点击删除
    onDelete(scope, fileList) {
      debugger;
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
              debugger;
              params.filename = item.filename;
              if (this.rootpath) {
                params.filepath = `/${this.rootpath}/${item.filepath}`;
              }
              if (this.waipath) {
                params.filepath = `/${this.waipath}/${item.filepath}`;
              }
              params.fjid = scope.row.id;
              this.delparamslist.push(params);
            }
          }
        }
      }
      debugger;
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
          console.log(this.formdatalist);
        }
      }

      if (
        this.download.LOGDQHJ == "测绘实施附件" ||
        this.download.LOGDQHJ == "成果汇交附件" ||
        this.download.LOGDQHJ == "成果负责人确认附件" ||
        this.download.LOGDQHJ == "成果验收附件" ||
        this.download.LOGDQHJ == "成果审核附件" ||
        this.download.LOGDQHJ == "zzsh_ryxx" ||
        this.download.LOGDQHJ == "chdwzc_zzzs_tjry" ||
        this.download.LOGDQHJ == "jdcx_ryxx" ||
        this.download.LOGDQHJ == "zzbg_ryxx" ||
        this.download.LOGDQHJ == "zzbg_ryxx_bg" ||
        this.download.LOGDQHJ == "jdcx_ryxx_tuihui"
      ) {
        window.localStorage.setItem("fjLists", JSON.stringify(this.fileList));
      } else {
        window.localStorage.setItem("fjList", JSON.stringify(this.fileList));
      }
      debugger;
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
      console.log(responses);
      // this.loading = false;
      debugger;
    },
    // 上传预处理
    upload(val) {
      debugger;
      // return;
      // this.loading = true;
      let fileFormData = new FormData();
      // 验证文件大小
      if (val.file.size == 0) {
        this.$message.error("不能上传空文件，请重新上传");
        this.loading = false;
        return;
      }
      debugger;
      // return;
      // 验证格式
      // 转大写
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
      fileFormData.append("parentid", this.download.id);
      var filepath = `${val.data.row.filename}`;
      fileFormData.append("filepath", filepath);
      fileFormData.append("files", val.file);
      fileFormData.append("userid", this.userinfo.id);
      fileFormData.append("username", this.userinfo.frxm);
      fileFormData.append("filename", val.file.name);

      // console.log(fileFormData.get("filepath"));
      this.formdatalist.push(fileFormData);

      console.log(val);
      debugger;
      val.file.filename = val.file.name;
      val.file.filepath = filepath;
      val.file.id = guid();
      console.log(val.file);
      debugger;
      for (let value of this.fileList) {
        if (value.filename == val.data.row.filename) {
          value.children = [...value.children, val.file];
        }
      }
      if (
        this.download.LOGDQHJ == "测绘实施附件" ||
        this.download.LOGDQHJ == "成果汇交附件" ||
        this.download.LOGDQHJ == "成果负责人确认附件" ||
        this.download.LOGDQHJ == "成果验收附件" ||
        this.download.LOGDQHJ == "成果审核附件" ||
        this.download.LOGDQHJ == "zzsh_ryxx" ||
        this.download.LOGDQHJ == "chdwzc_zzzs_tjry" ||
        this.download.LOGDQHJ == "jdcx_ryxx" ||
        this.download.LOGDQHJ == "zzbg_ryxx" ||
        this.download.LOGDQHJ == "zzbg_ryxx_bg" ||
        this.download.LOGDQHJ == "jdcx_ryxx_tuihui"
      ) {
        window.localStorage.setItem("fjLists", JSON.stringify(this.fileList));
      } else {
        console.log(this.fileList);
        debugger;
        window.localStorage.setItem("fjList", JSON.stringify(this.fileList));
      }
    },
    yulan(scope, fileList) {
      debugger;
      console.log("预览");
      debugger;
      var path = encodeURI(
        `${this.waipath}/${scope.row.filepath}/${scope.row.filename}`
      );
      // console.log(path);
      window.open(
        `http://xagh.org.cn:8083/view/url?url=http://10.7.120.10/file/${path}`,
        "_blank"
      );
    },
    downloaditem(scope, fileList) {
      debugger;
      // var url = "http://222.90.207.221/dchy/dchy/ftpService/downloadfile";
      var url = `${callService.downloadFile}`;
      var params = {
        filename: `${this.waipath}/${scope.row.filepath}/${scope.row.filename}`,
      };
      console.log(params);
      downloadFile(url, params);
    },
    mbxz(row) {
      console.log(row);
      // var url = "ftpService/downloadfile";
      var url = `${callService.downloadFile}`;
      if (row.filename == "合同信息") {
        var params = {
          filename: `${attachmentUpload.mbwj}/${templateName.htmb}.docx`,
        };
        downloadFile(url, params);
      }
      if (row.filename == "中介机构承诺书") {
        var params = {
          filename: `${attachmentUpload.mbwj}/${templateName.zjjgcns}.docx`,
        };
        downloadFile(url, params);
      }
    },
  },
  created() {
    debugger;
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    if (window.localStorage.getItem("processList")) {
      this.processList = JSON.parse(window.localStorage.getItem("processList"));
    }
    console.log("传入附件的信息", this.download);
    debugger;
    this.rootpath = this.download.rootpath;
    this.drawtable();
  },
  mounted() {
    // console.log(this.fileList);
  },
  destroyed() {
    // if (window.localStorage.getItem("xzfjids")) {
    //   window.localStorage.removeItem("xzfjids");
    // }
  },
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