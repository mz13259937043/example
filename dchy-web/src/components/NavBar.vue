<template>
  <div class="navbar">
    <div
      v-for="(item, index) in menu"
      :key="index"
      :class="{ activecolor: isActive == '/' + item.path }"
      @click="getTo(item.path, lastPath)"
    >
      {{ item.name }}
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isActive: "",
      lastPath: "",
      menu: [
        { name: "首页", path: "index" },
        { name: "采购公告", path: "wtgg" },
        { name: "中选公告", path: "zxgg" },
        { name: "政策法规", path: "zcfg" },
        { name: "入驻机构", path: "qymlk" },
        { name: "个人中心", path: "grzx" },
      ],
    };
  },
  methods: {
    getTo(path, lastPath) {
      // console.log(lastPath);
      this.isActive = "/" + path;
      // if (lastPath == "index") {
      //   const { href } = this.$router.resolve({
      //     name: path,
      //   });
      //   window.open(href, "_blank");
      // } else {
      //   this.$router.push({ name: path });
      // }
      this.$router.push({ name: path });
    },
  },
  created() {
    // console.log(this.$route);
    this.lastPath = this.$route.name;
    this.isActive = this.$route.path;
  },
  props: {
    userRole: {
      type: String,
      default: "",
    },
  },
};
</script>

<style scoped>
.navbar {
  /* height: 60px; */
  background: #006cff;
  padding: 0 385px;
  display: flex;
}
.navbar :first-child {
  border-left: 1px solid rgba(255, 255, 255, 0.2);
}
.navbar div {
  flex: 1;
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: bold;
  color: #ffffff;
  border-right: 1px solid rgba(255, 255, 255, 0.2);
  cursor: pointer;
}
.navbar div:hover {
  background: #0091ff;
}
.activecolor {
  background: #0091ff;
}
</style>