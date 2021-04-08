<template>
  <div class="xmfb">
    <headers></headers>
    <navbar></navbar>
    <div class="nav-title">
      <div>当前所在位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{
          navName ? navName : $route.meta.name
        }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="xmfb-content">
      <div class="process" v-if="$route.query.dqhj == '办结'">
        <div
          class="jdt"
          v-for="(item, index) in list2"
          :key="item.id"
          :style="{
            'background-image': 'url(' + item.color + ')',
            width: width,
            'background-size': '100% 100%',
          }"
        >
          <!--          @click="djjdt(index,item)"-->
          <span>第{{ index + 1 }}步</span>
          <span>{{ item.content }}</span>
        </div>
      </div>
      <div class="process" v-else>
        <div
          class="jdt"
          v-for="(item, index) in list"
          :key="item.id"
          :style="{
            'background-image': 'url(' + item.color + ')',
            width: width,
            'background-size': '100% 100%',
          }"
        >
          <!--          @click="djjdt(index,item)"-->
          <span>第{{ index + 1 }}步</span>
          <span>{{ item.content }}</span>
        </div>
      </div>

      <div class="component">
        <xxfb v-if="index === 0" :navIndex="navIndex"></xxfb>
        <xxfb v-if="index === 1" :navIndex="navIndex"></xxfb>
        <xxfb v-if="index === 2" :navIndex="navIndex"></xxfb>
        <xxfb v-if="index === 3" :navIndex="navIndex"></xxfb>
        <xxfb v-if="index === 4" :navIndex="navIndex"></xxfb>
      </div>
    </div>
    <bottom></bottom>
  </div>
</template>

<script>
import Xxfb from "./xmfbxq/xxfb";
import jdt from "@/assets/jdt/inprogress.png"; //正在办理中...
import ywc from "@/assets/jdt/notdone.png"; //未办理34
import ybj from "@/assets/jdt/done.png"; //已办结
import bottom from "@/components/Bottom.vue";
import headers from "@/components/Header.vue";
import navbar from "@/components/NavBar.vue";
const list = [
  {
    id: 1,
    // color: "#FF703D",
    color: require("@/assets/jdt/inprogress.png"),
    content: "项目发布",
  },
  // {
  //   id: 2,
  //   color: require("@/assets/jdt/notdone.png"),
  //   content: "测绘单位申请",
  // },
  {
    id: 3,
    color: require("@/assets/jdt/notdone.png"),
    content: "采购确认",
  },
  {
    id: 5,
    color: require("@/assets/jdt/notdone.png"),
    content: "合同上传",
  },
  {
    id: 4,
    color: require("@/assets/jdt/notdone.png"),
    content: "合同确认",
  },
];
const list2 = [
  {
    id: 1,
    // color: "#FF703D",
    color: require("@/assets/jdt/done.png"),
    content: "项目发布",
  },
  // {
  //   id: 2,
  //   color: require("@/assets/jdt/notdone.png"),
  //   content: "测绘单位申请",
  // },
  {
    id: 3,
    color: require("@/assets/jdt/done.png"),
    content: "采购确认",
  },
  {
    id: 5,
    color: require("@/assets/jdt/done.png"),
    content: "合同上传",
  },
  {
    id: 4,
    color: require("@/assets/jdt/done.png"),
    content: "合同确认",
  },
];
export default {
  components: {
    Xxfb,
    headers,
    bottom,
    navbar,
  },
  data() {
    return {
      list,
      list2,
      index: 0,
      navName: "",
      navIndex: 0,
    };
  },
  computed: {
    width() {
      let res = 100 / this.list.length - (0.5 * this.list.length - 1) + "%";
      return res;
    },
  },
  created() {
    debugger;
    if (!this.$route.query.dqhj) {
      this.djjdt(0, list[0]);
      window.localStorage.removeItem("listZT");
    }
    for (let i = 0; i < list.length; i++) {
      if (this.$route.query.dqhj == "合同已签订") {
        this.djjdt(3, list[3]);
      }
      if (this.$route.query.dqhj == list[i].content) {
        this.djjdt(i, list[i]);
        break;
      }
    }
  },
  methods: {
    djjdt(index, item) {
      this.navIndex = index;
      this.navName = item.content;
      this.index = index;
      _.each(this.list, (item) => {
        item.color = ywc;
      });
      for (let i = 0; i < index; i++) {
        this.list[i].color = ybj;
      }
      this.list[index].color = jdt;
    },
  },
};
</script>

<style scoped>
html {
  font-size: 100px;
}

.left {
  width: 0;
  height: 0;
  position: absolute;
  left: 0;
  top: 0;
  border-top: 18px solid transparent;
  border-left: 36px solid #fff;
  border-bottom: 18px solid transparent;
  z-index: 11;
}

.right {
  width: 0;
  height: 0;
  position: absolute;
  right: -36px;
  top: 0;
  border-top: 18px solid transparent;
  border-left: 36px solid red;
  border-bottom: 18px solid transparent;
  z-index: 99;
}

.xmfb {
  width: 100%;
  height: 100%;
  overflow: auto;
}

.xmfb-content {
  padding: 0 20%;
  width: 100%;
  /* height: 100%; */
  margin: 0 auto;
  /* padding-bottom: 150px; */
  height: auto;
  min-height: 572px;
}

.breadcrumb {
  height: 40px;
  display: flex;
  margin-bottom: 18px;
  padding: 0px 3rem;
  align-items: center;
  background: #269fd6;
}

.xmfb-title {
  width: 100%;
  height: 5.52%;
  padding: 20px 0 16px 19.3%;
  display: flex;
  background: #0068d1;
  /* margin-bottom: 44px; */
  color: #ffffff;
  opacity: 1;
}

.process {
  display: flex;
  /* height: 100%; */
  /* margin-left: 16px; */
  color: #fff;
  font-size: 14px;
}

.process span {
  padding: 0 10px;
}

.process .jdt {
  width: 25% !important;
  height: 40px;
  cursor: pointer;
  line-height: 40px;
  text-align: center;
  /* padding: 0px 3rem; */
  /* margin-right: 16px; */
  /* background: #68b253; */
}

.jdtt {
  width: 20%;
  height: 36px;
  line-height: 36px;
  text-align: center;
  position: relative;
  margin-left: 0.5%;
  float: left;
}

.component {
  height: 80%;
  overflow-y: auto;
  overflow-x: hidden;
}
</style>