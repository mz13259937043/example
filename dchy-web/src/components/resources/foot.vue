<template>
  <div class="bottom">
    <div class="firstLine">
      <span @click="togywm">关于我们</span>
      <span>|</span>
      <span @click="tolxwm">联系我们</span>
    </div>
    <div class="secondLine">
      <div class="item">
        <div>主办单位：西安市自然资源和规划局</div>
        <div class="item_second">技术支持：西安市自然资源和规划信息中心</div>
      </div>
      <div class="item">
        <div>业务咨询电话：029-86787067</div>
        <div class="item_second">陕公网备案： 61011202000231号</div>
      </div>
      <div class="item">
        <div>技术支持电话：029-84251161</div>
        <div class="item_second">网站标识码：6101000068</div>
      </div>
      <div class="item">
        <div>地址：西安市未央区凤城八路109号</div>
        <div class="item_second">备案：陕ICP备19021816号</div>
      </div>
    </div>
    <div class="threeLine">
      <div class="item2">
        <div>
          建议使用1920*1080分辨率，推荐浏览器为Chrome、360浏览器<a
            style="color: #0091ff"
            href="https://www.google.cn/chrome/"
            target="_blank"
            >（点击下载）</a
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
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