<template>
  <div class="">
    <header>
      <navbar></navbar>
    </header>
    <el-container style="display: flex;flex-direction: row">
      <div id="leftArea">
        <leftMenus />
      </div>
      <div id="rightArea">
            <router-view />
      </div>
    </el-container>

  </div>
</template>

<script>
import navbar from "@/components/resources/navbar";
import leftMenus from "@/components/resources/leftMenus";
export default {
  components: {
    navbar,
    leftMenus,
  },
  data() {
    return {
      res:[
        {name:'菜单1',path:'/home/one'},
        {name:'菜单2',path:'/home/two'},
      ],
    };
  },
  methods: {
    togywm() {
      this.$router.push({
        name: "zcfgdetail",
        query: { id: "c5f166ecb7b142f6b299d288f240d58c", flag: "gywm" },
      });
      window.open(href, "_blank");
    },
    tolxwm() {
      this.$router.push({
        name: "zcfgdetail",
        query: { id: "d9502f976b7940ceaf96ef413803794f", flag: "lxwm" },
      });
    },
  },
};
</script>

<style scoped>
#leftArea{
  width: 300px;
}

.bottom {
  height: 180px;
  background: #233042;
  display: flex;
  flex-direction: column;
  color: #fff;
  align-items: center;
}
.firstLine {
  margin-top: 30px;
  color: #6d7278;
}
.firstLine span:hover {
  color: #0091ff;
  cursor: pointer;
}
.firstLine span {
  height: 16px;
  font-size: 16px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #dedede;
  line-height: 16px;
}
.firstLine :first-child {
  margin-right: 60px;
}
.firstLine :last-child {
  margin-left: 60px;
}
.secondLine {
  display: flex;
  /* height: 150px; */
}
.threeLine {
  display: flex;
  /* height: 150px; */
}
.fimg {
  /* margin-left: 378px; */
  margin-top: 36px;
  width: 58px;
  height: 80px;
}
.limg {
  width: 150px;
  margin-left: 60px;
  height: 150px;
  margin-top: -35px;
}
.item {
  margin-left: 48px;
}
.item div {
  width: 270px;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #cbcbcb;
}
.item div:hover {
  /* color: #0091ff; */
  /* cursor: pointer; */
}
.item :first-child {
  margin-top: 15px;
}
.item_second {
  margin-top: 15px;
}

.item2 {
  margin-left: 48px;
}
.item2 div {
  width: 500px;
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #cbcbcb;
}
.item2 div:hover {
  /* cursor: pointer; */
}
.item2 :first-child {
  margin-top: 15px;
}
.item2_second {
  margin-top: 15px;
}
.acolor >>> element.style {
  color: #cbcbcb;
}
.item2 >>> a {
  color: #cbcbcb;
}
.item2 >>> a :hover {
  color: #0091ff;
  cursor: pointer;
}
</style>