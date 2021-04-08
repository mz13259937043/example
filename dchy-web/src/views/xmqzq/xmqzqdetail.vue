<template>
  <div class="xmqzqxq">
    <headers></headers>
    <navbar></navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <div>当前位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目监管详情页</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="main-content">
      <div class="xmqzqxq-content" v-for="item in xmqzqSjzArr" :key="item.id">
        <div class="xmqzq-title">{{ item.titleItem }}</div>
        <div class="xmqzq">
          <div class="xmqzq-first">
            <div class="item-frist-bz"></div>
            <div class="item-xt"></div>
          </div>
          <div class="xmqzq-content">
            <div class="xmqzq-item" v-for="v in item.titleArr" :key="v.id">
              <div class="xmqzq-item-title">
                <span> {{ v.title }}</span>
              </div>
              <div class="xmqzq-bzitem">
                <div
                  @click="cljdClick(v)"
                  class="item-bz"
                  :style="{
                    backgroundColor: v.backgroundColor,
                  }"
                >
                  {{ v.num }}
                </div>
                <div class="item-xt"></div>
              </div>
              <div
                class="content"
                :style="{ padding: v.padding }"
                v-if="v.children"
              >
                <div class="connect-parent">
                  <div class="connect-sz"></div>
                </div>
                <div>
                  <el-timeline style="font-size: 12px">
                    <el-timeline-item
                      v-for="(activity, i) in v.children"
                      :key="i"
                      :color="activity.color"
                    >
                      <div
                        @click="clsxClick(v, activity)"
                        class="connect-parent-content"
                        :style="{ background: activity.color }"
                      >
                        {{ activity.content }}
                      </div>
                    </el-timeline-item>
                  </el-timeline>
                </div>
              </div>
            </div>
          </div>
          <div class="xmqzq-first">
            <div class="item-frist-bz"></div>
          </div>
        </div>
      </div>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="tableData"
        border
        style="width: 100%; cursor: pointer"
        :header-cell-style="{ background: '#006CFF' }"
        @row-click="toDetailProject"
      >
        <el-table-column prop="chjd" label="测绘阶段" align="center">
        </el-table-column>
        <el-table-column prop="clsx" label="测量事项" align="center">
        </el-table-column>
        <el-table-column prop="xmmc" label="项目名称" align="center">
        </el-table-column>
        <el-table-column prop="djbh" label="地籍编号" align="center">
        </el-table-column>
        <el-table-column prop="wtdw" label="采购单位" align="center">
        </el-table-column>
        <el-table-column prop="chdw" label="测绘单位" align="center">
        </el-table-column>
        <el-table-column
          prop="jssj"
          label="办结时间"
          align="center"
          width="150"
        >
        </el-table-column>
        <!-- <el-table-column prop="operate" label="操作" align="center" width="180">
          <template slot-scope="scope">
            <el-button class="btn1" @click="handleShenQing(scope.row, $event)"
              >查看</el-button
            >
          </template>
        </el-table-column> -->
      </el-table>
      <!-- <el-pagination
        @current-change="handleCurr"
        @size-change="handleSize"
        :current-page="page.pageIndex"
        :page-size="page.pageSize"
        layout="total,sizes, prev, pager, next, jumper"
        :total="page.total"
        :page-sizes="[10, 20]"
        style="margin-bottom: 20px; text-align: center"
      ></el-pagination> -->
    </div>
    <bottom></bottom>
  </div>
</template>

<script>
import headers from "@/components/Header.vue";
import bottom from "@/components/Bottom.vue";
import navbar from "@/components/NavBar.vue";

const xmqzqSjzArr = [
  {
    id: 11,
    titleItem: "",
    titleArr: [
      {
        id: 1,
        num: 1,
        title: "项目用地测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 10px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "1:500地形图测绘",
            color: "#CECECE",
          },
          {
            content: "土地勘测定界",
            color: "#CECECE",
          },
          {
            content: "规划用地定点测量",
            color: "#CECECE",
          },
          {
            content: "权籍调查",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 2,
        num: 2,
        title: "工程实施测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "建(构)筑物的定线测量",
            color: "#CECECE",
          },
          {
            content: "建(构)筑物的验线测量",
            color: "#CECECE",
          },
          {
            content: "道路、地下管线、管网、管沟的定线测量",
            color: "#CECECE",
          },
          {
            content: "道路、地下管线、管网、管沟的验线测量",
            color: "#CECECE",
          },
          {
            content: "房屋面积预测",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 3,
        num: 3,
        title: "竣工及不动产登记测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "规划竣工测量",
            color: "#CECECE",
          },
          {
            content: "权籍调查",
            color: "#CECECE",
          },
          {
            content: "庭院内管线及附属设施测量",
            color: "#CECECE",
          },
        ],
      },
    ],
  },
];

export default {
  components: {
    headers,
    bottom,
    navbar,
  },
  data() {
    return {
      xmqzqSjzArr,
      tableData: [],
      //  分页组件
      page: {
        total: null,
        pageIndex: 1,
        pageSize: 10,
        str: null,
      },
      xmqzqJson: JSON.parse(window.localStorage.getItem("xmqzqJson")),
      loading: true,
    };
  },
  mounted() {
    this.init(this.xmqzqJson);
    this.initTable(this.xmqzqJson, "", "");
  },
  methods: {
    init(xmqzqJson) {
      let _this = this;
      let ywxxList = xmqzqJson.ywxxList;
      _.each(_this.xmqzqSjzArr, (v) => {
        _.each(v.titleArr, (chjd) => {
          if (chjd.title === xmqzqJson.chjd) {
            chjd.backgroundColor = "#EA5236";
            _.each(chjd.children, (clsx) => {
              _.each(ywxxList, (ywxx) => {
                if (clsx.content === ywxx.ywmc) {
                  if (ywxx.zt === "办理中") {
                    clsx.color = "#EA5236";
                  } else if (ywxx.zt === "办结") {
                    clsx.color = "#8CC5FD";
                  }
                }
              });
            });
          }
        });
      });
    },

    initTable(xmqzqJson, chjd, clsx) {
      let logList = null;
      const tempArr = [];
      const clsxArr = [];
      // if(chjd == ''){
      //   for (const ywxx of xmqzqJson.ywxxList) {
      //     let tempData = {
      //             chjd: xmqzqJson.chjd,
      //             xmmc: xmqzqJson.xmmc,
      //             djbh: xmqzqJson.djbh,
      //             clsx: ywxx.ywmc,
      //             wtdw: xmqzqJson.wtdw,
      //             chdw: xmqzqJson.htxxList[0].chdwmc,
      //             jssj: "",
      //     }
      //     if(ywxx.cgqrsj){
      //       tempData.jssj = this.$moment(ywxx.cgqrsj).format("YYYY-MM-DD HH:MM:SS");
      //     } else {
      //       tempData.jssj = "";
      //     }
      //     clsxArr.push(tempData);
      //   }
      //   this.tableData = clsxArr;
      // } else if(chjd == xmqzqJson.chjd){

      // }
      if (chjd == "" || chjd == xmqzqJson.chjd) {
        _.each(xmqzqJson.ywxxList, (ywxx) => {
          if (clsx && clsx != "") {
            if (ywxx.ywmc === clsx) {
              _.each(ywxx.logsList, (log) => {
                let sigData = {
                  chjd: xmqzqJson.chjd,
                  xmmc: xmqzqJson.xmmc,
                  djbh: xmqzqJson.djbh,
                  clsx: log.clsx,
                  wtdw: xmqzqJson.wtdw,
                  chdw: xmqzqJson.htxxList[0].chdwmc,
                  jssj: "",
                };
                sigData.jssj = this.$moment(log.jssj).format(
                  "YYYY-MM-DD HH:MM:SS"
                );
                clsxArr.push(sigData);
              });
              this.tableData = clsxArr;
            } else {
              this.tableData = [];
            }
          } else {
            _.each(ywxx.logsList, (log) => {
              let sigData = {
                chjd: xmqzqJson.chjd,
                xmmc: xmqzqJson.xmmc,
                djbh: xmqzqJson.djbh,
                clsx: log.clsx,
                wtdw: xmqzqJson.wtdw,
                chdw: xmqzqJson.htxxList[0].chdwmc,
                jssj: "",
              };
              sigData.jssj = this.$moment(log.jssj).format(
                "YYYY-MM-DD HH:MM:SS"
              );
              tempArr.push(sigData);
            });
            this.tableData = tempArr;
          }
        });
      } else {
        this.tableData = [];
      }
      this.loading = false;
    },

    //  点击圆圈事件
    cljdClick(v) {
      this.initTable(this.xmqzqJson, v.title, "");
    },

    //  点击事项事件
    clsxClick(v, item) {
      this.initTable(this.xmqzqJson, v.title, item.content);
    },
    toDetailProject(val) {
      window.localStorage.setItem("listZT", "土地全周期");
      if (this.listZT == "已办") {
        var obj = JSON.stringify(val);
        window.localStorage.setItem("qzqListItem", obj);
      }
      this.$router.push({
        path: "ywxqym",
      });
    },
    /**
     * 分页部分
     */
    //监听页码值的变化
    handleCurr(page) {
      this.page.pageIndex = page;
    },
    handleSize(val) {
      this.page.pageSize = val;
      this.page.pageIndex = 1;
    },
  },
};
</script>

<style scoped>
.xmqzqxq {
  width: 100%;
  height: 100%;
}

.main-content {
  padding: 0 20%;
  height: auto;
  min-height: 572px;
}

.xmqzq-title {
  font-weight: 600;
  font-size: 20px;
}
.xmqzq {
  width: 100%;
  display: flex;
  min-height: 200px;
  margin-top: 20px;
  justify-content: center;
}

.xmqzq-content {
  display: flex;
  margin-top: 20px;
  font-size: 14px;
  font-weight: 600;
}

.xmqzq-item-title {
  margin-top: -20px;
  padding-right: 20px;
  min-width: 120px;
}

.xmqzq-item {
  white-space: nowrap;
  width: 100%;
  height: auto;
}
.item-bz,
.item-frist-bz {
  cursor: pointer;
  font-weight: 400;
  color: #fff;
  font-size: 12px;
  text-align: center;
  line-height: 30px;
  width: 35px;
  height: 30px;
  border-radius: 50%;
}

.xmqzq-first {
  margin-top: 20px;
  width: 140px;
  height: 30px;
  display: flex;
  align-items: center;
}

.item-frist-bz {
  width: 10px;
  height: 10px;
  border: 1px solid#CECECE;
  background-color: #cecece;
}

.xmqzq-bzitem {
  display: flex;
  align-items: center;
}

.item-xt {
  width: 100%;
  height: 2px;
  margin: 0px auto;
  padding: 0px;
  background-color: #d9d9d9;
  overflow: hidden;
}

.connect-parent {
  position: relative;
  width: 100px;
  height: 80px;
}

.connect-sz {
  position: absolute;
  top: 0;
  left: 4px;
  width: 2px;
  background-color: #e4e7ed;
  margin: auto;
  height: 100px;
}

.xmqzqxq >>> .el-timeline-item__node--normal {
  left: -2px;
  width: 15px;
  height: 15px;
}

.xmqzqxq >>> .el-timeline-item__wrapper {
  padding-left: 0;
  margin-left: 28px;
}
.connect-parent-content {
  cursor: pointer;
  width: 240px;
  color: #fff;
  padding-left: 10px;
  line-height: 25px;
}
.btn1 {
  color: #66b1ff;
  border: 1px solid #66b1ff;
}
.btn1:hover {
  color: #fff;
  border: 1px solid #66b1ff;
  background: #006cff;
}
</style>