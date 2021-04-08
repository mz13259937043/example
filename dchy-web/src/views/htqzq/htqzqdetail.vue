<template>
  <div class="htqjdxq">
    <headers></headers>
    <navbar></navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <div>当前位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理详情页</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="main-content">
      <!-- <div class="htqjdxq-content" v-for="item in htqjdSjzArr" :key="item.id">
        <div class="htqjd-title">{{ item.titleItem }}</div> -->
      <div class="htqjdxq-content" :key="htqjdSjzArr[0].id" v-if="xmydShow">
        <div class="htqjd-title">{{ htqjdSjzArr[0].titleItem }}</div>
        <div class="htqjd">
          <div class="htqjd-first">
            <div class="item-frist-bz"></div>
            <div class="item-xt"></div>
          </div>
          <div class="htqjd-content">
            <div
              class="htqjd-item"
              v-for="v in htqjdSjzArr[0].titleArr"
              :key="v.id"
            >
              <div
                class="htqjd-item-title"
              >
                <span> {{ v.title }}</span>
              </div>
              <div class="htqjd-bzitem">
                <div
                  @click="chsxClick(v)"
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
                      @click="chdqhjClick(v, activity)"
                        class="aaaa"
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
          <div class="htqjd-first">
            <div class="item-frist-bz"></div>
          </div>
        </div>
      </div>
      <div class="htqjdxq-content" :key="htqjdSjzArr[1].id" v-if="gcssShow">
        <div class="htqjd-title">{{ htqjdSjzArr[1].titleItem }}</div>
        <div class="htqjd">
          <div class="htqjd-first">
            <div class="item-frist-bz"></div>
            <div class="item-xt"></div>
          </div>
          <div class="htqjd-content">
            <div
              class="htqjd-item"
              v-for="v in htqjdSjzArr[1].titleArr"
              :key="v.id"
            >
              <div class="htqjd-item-title">
                <span> {{ v.title }}</span>
              </div>
              <div class="htqjd-bzitem">
                <div
                  @click="chsxClick(v)"
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
                        @click="chdqhjClick(v, activity)"
                        class="aaaa" 
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
          <div class="htqjd-first">
            <div class="item-frist-bz"></div>
          </div>
        </div>
      </div>
      <div class="htqjdxq-content" :key="htqjdSjzArr[2].id" v-if="jgjbdcShow">
        <div class="htqjd-title">{{ htqjdSjzArr[2].titleItem }}</div>
        <div class="htqjd">
          <div class="htqjd-first">
            <div class="item-frist-bz"></div>
            <div class="item-xt"></div>
          </div>
          <div class="htqjd-content">
            <div
              class="htqjd-item"
              v-for="v in htqjdSjzArr[2].titleArr"
              :key="v.id"
            >
              <div class="htqjd-item-title">
                <span> {{ v.title }}</span>
              </div>
              <div class="htqjd-bzitem">
                <div
                  @click="chsxClick(v)"
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
                        @click="chdqhjClick(v, activity)"
                        class="aaaa" 
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
          <div class="htqjd-first">
            <div class="item-frist-bz"></div>
          </div>
        </div>
      </div>
      <el-table :data="tableData" border style="width: 100%; cursor: pointer;" :header-cell-style="{background:'#006CFF'}">
        <el-table-column prop="xmmc" label="项目名称" align="center">
        </el-table-column>
        <el-table-column prop="chjd" label="测绘阶段" align="center" width="130">
        </el-table-column>
        <el-table-column prop="clsx" label="测量事项" align="center" width="130">
        </el-table-column>
        <el-table-column prop="djbh" label="地籍编号" align="center" width="110">
        </el-table-column>
        <el-table-column prop="wtdw" label="采购单位" align="center" width="210">
        </el-table-column>
        <el-table-column prop="chdw" label="测绘单位" align="center" width="210">
        </el-table-column>
        <el-table-column
          prop="jssj"
          label="办理时间"
          align="center"
          width="110"
        >
        </el-table-column>

        <!-- <el-table-column prop="operate" label="操作" align="center" width="180">
          <template slot-scope="scope">
            <el-button
              class="btn1"
              @click="handleShenQing(scope.row, $event)"
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
        :page-sizes="[1, 2]"
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
import { formatDate } from "@/assets/js/common.js";

const htqjdSjzArr = [
  {
    id: 11,
    titleItem: "项目用地测量",
    titleArr: [
      {
        id: 1,
        num: 1,
        title: "1：500地形图测绘",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 10px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 2,
        num: 2,
        title: "土地勘测定界",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 3,
        num: 3,
        title: "规划用地定点测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 4,
        num: 4,
        title: "权籍调查",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
    ],
  },
  {
    id: 22,
    titleItem: "工程实施测量",
    titleArr: [
      {
        id: 1,
        num: 1,
        title: "建(构)筑物的定线测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 10px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 2,
        num: 2,
        title: "建(构)筑物的验线测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 3,
        num: 3,
        title: "道路、地下管线、管网、管沟的定线测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 4,
        num: 4,
        title: "道路、地下管线、管网、管沟的验线测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
       {
        id: 5,
        num: 5,
        title: "房屋面积预测",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
    ],
  },
  {
    id: 33,
    titleItem: "竣工及不动产登记测量",
    titleArr: [
      {
        id: 1,
        num: 1,
        title: "规划竣工测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 10px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
     
      {
        id: 2,
        num: 2,
        title: "权籍调查",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
      {
        id: 3,
        num: 3,
        title: "庭院内管线及附属设施测量",
        width: "21%",
        height: "30px",
        padding: "0 50px 0px 6px",
        border: "1px solid #CECECE",
        backgroundColor: "#CECECE",
        children: [
          {
            content: "测绘实施",
            color: "#CECECE",
          },
          {
            content: "成果汇交",
            color: "#CECECE",
          },
          {
            content: "成果负责人确认",
            color: "#CECECE",
          },
          {
            content: "成果验收",
            color: "#CECECE",
          },
          {
            content: "成果审核",
            color: "#CECECE",
          },
        ],
      },
      
    ],
  },
];

const tableData = [
  {
    slbh: "22343243243",
    dchybh: "202000430031",
    slsj: "2020-04-28 08:48:35",
    rksj: "2020-04-29 00:00:00",
    xmzt: "已办结",
  },
  {
    slbh: "22343243243",
    dchybh: "202000430031",
    slsj: "2020-04-28 08:48:35",
    rksj: "2020-04-29 00:00:00",
    xmzt: "已办结",
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
      htqjdSjzArr,
      tableData: [],
      xmydShow: false,
      gcssShow: false,
      jgjbdcShow: false,
      //  分页组件
      page: {
        total: null,
        pageIndex: 1,
        pageSize: 10,
        str: null,
      },
      //paramObj: this.$route.query,
      htqzqJson: JSON.parse(window.localStorage.getItem("htqzqJson")),
    };
  },
  mounted() {
    this.init(this.htqzqJson);
    this.initTable(this.htqzqJson, "", "");
  },
  methods: {
    // 初始化   #8CC5FD蓝色  #EA5236红色  #CECECE灰色
    init(htqzqJson) {
      if (htqzqJson.fbxx.chjd === "项目用地测量") {
        this.xmydShow = true;
        this.gcssShow = false;
        this.jgjbdcShow = false;
      } else if (htqzqJson.fbxx.chjd === "工程实施测量") {
        this.xmydShow = false;
        this.gcssShow = true;
        this.jgjbdcShow = false;
      } else {
        this.xmydShow = false;
        this.gcssShow = false;
        this.jgjbdcShow = true;
      }
      let _this = this;
      let ywxxList = htqzqJson.ywxxList;
      let logList = null;
      //console.log("fbxx",htqzqJson.fbxx);
      //console.log("ysxxList",htqzqJson.ywxxList);
      _.each(_this.htqjdSjzArr, (v) => {
        //v.titleItem = htqzqJson.fbxx.chjd;
        _.each(v.titleArr, (clsx) => {
          //循环渲染主时间轴(测量事项)
          _.each(ywxxList, (ywxx) => {
            if (clsx.title === ywxx.ywmc) {
              if (ywxx.zt === "办理中") {
                clsx.backgroundColor = "#EA5236";
              } else if (ywxx.zt === "办结") {
                clsx.backgroundColor = "#8CC5FD";
              }
              //渲染主轴节点下的每个小节点(当前阶段)
              _.each(clsx.children, (v) => {
                _.each(ywxx.logsList, (log) => {
                  // debugger;
                  if (v.content === log.dqhj) {
                    if (v.color != "#8CC5FD") {
                      if (log.clzt === "待办") {
                        v.color = "#EA5236";
                      } else if (log.clzt === "已办") {
                        v.color = "#8CC5FD";
                      }
                    }
                  }
                });
              });
            }
          });
        });
      });
    },

    initTable(htqzqJson, clsx, dqhj) {
      //debugger;
      this.tableData = [];
      let logList = null;
      debugger
      let fbxx = htqzqJson.fbxx;
      _.each(htqzqJson.ywxxList, (ywxx) => {
        if (clsx && clsx != "") {
          if (ywxx.ywmc === clsx) {
            _.each(ywxx.logsList, (log) => {
              let sigData = {
                chjd: fbxx.chjd,
                xmmc: fbxx.xmmc,
                djbh: fbxx.djbh,
                clsx: log.clsx,
                wtdw: fbxx.wtdw,
                chdw: htqzqJson.chdwmc,
                jssj: "",
                dqhj: "",
              };
              sigData.jssj = this.$moment(log.jssj).format(
                "YYYY-MM-DD HH:MM:SS"
              );
              sigData.dqhj = log.dqhj;
              if (dqhj && dqhj != "") {
                if (dqhj === log.dqhj) {
                  this.tableData.push(sigData);
                }
              } else {
                this.tableData.push(sigData);
              }
            });
          }
        } else {
          _.each(ywxx.logsList, (log) => {
            //debugger;
            let sigData = {
              chjd: fbxx.chjd,
              xmmc: fbxx.xmmc,
              djbh: ywxx.djh,
              clsx: ywxx.ywmc,
              wtdw: fbxx.wtdw,
              chdw: htqzqJson.chdwmc,
              jssj: "",
              dqhj: "",
            };
            sigData.jssj = this.$moment(log.jssj).format("YYYY-MM-DD HH:MM:SS");
            sigData.dqhj = log.dqhj;
            this.tableData.push(sigData);
          });
        }
      });
    },

    //  点击测绘事项事件
    chsxClick(v) {
      this.initTable(this.htqzqJson, v.title, "");
      console.log(v);
    },

    //  点击测绘阶段事件
    chdqhjClick(v, item) {
      this.initTable(this.htqzqJson, v.title, item.content);
      console.log(item);
    },

    /**
     * 分页部分
     */
    //监听页码值的变化
    handleCurr(page) {
      console.log(page);
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
.htqjdxq {
  width: 100%;
  height: 100%;
}

.main-content {
  padding: 0 20%;
  height: auto;
  min-height: 572px;
  }

.htqjd-title {
  font-weight: 600;
  font-size: 20px;
}
.htqjd {
  width: 100%;
  display: flex;
  min-height: 200px;
  margin-top: 20px;
  justify-content: center;
}

.htqjd-content {
  display: flex;
  margin-top: 20px;
  font-size: 14px;
  font-weight: 600;
}

.htqjd-item-title {
  margin-top: -20px;
  padding-right: 20px;
  min-width: 120px;
  cursor: pointer;
}

.htqjd-item {
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

.htqjd-first {
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

.htqjd-bzitem {
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

.htqjdxq >>> .el-timeline-item__node--normal {
  left: -2px;
  width: 15px;
  height: 15px;
}

.htqjdxq >>> .el-timeline-item__wrapper {
  padding-left: 0;
  margin-left: 28px;
}
.aaaa {
  cursor: pointer;
  width: 160px;
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