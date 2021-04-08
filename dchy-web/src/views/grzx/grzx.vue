<template>
  <div class="grzx">
    <header-top></header-top>
    <nav-bar></nav-bar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ this.$route.meta.name }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="grzx-content">
      <div class="grzx-content-grxx">
        <div class="item">
          <div class="item-left">
            <img src="@/assets/img/touxiang.png" alt="" />
          </div>
          <div class="item-right" style="line-height: 35px">
            <div>
              <p style="font-weight: bolder; margin-bottom: 0">
                您好！{{
                  this.userinfo.dwmc ? this.userinfo.dwmc : this.userinfo.name
                }}
              </p>
            </div>
            <div>
              <p>
                如需更改注册信息，请登录
                <a
                  href="https://sfrz.shaanxi.gov.cn/#/login?
                redirect_uri=http%3A%2F%2Fzwfw.xa.gov.cn%3A8085%2Fxian%2FLogin%3Fflag%3Dqy&client_id=000000050"
                  title="点击跳转"
                  >陕西政务服务网</a
                >
                进行更改
              </p>
            </div>
          </div>
        </div>
      </div>
      <Second></Second>
    </div>
    <bottom></bottom>
  </div>
</template>

<script>
import headerTop from "@/components/Header.vue";
import navBar from "@/components/NavBar.vue";
import Second from "@/components/secondist.vue";
import bottom from "@/components/Bottom.vue";

export default {
  components: {
    headerTop,
    navBar,
    bottom,
    Second,
  },
  data() {
    return {
      userinfo: "",
      userlist: [],
      userRole: "",
      hoursTip: "",
    };
  },
  methods: {
    toXxgl(item) {
      this.$router.push({
        name: item.path,
      });
    },
  },
  created() {
    if (window.localStorage.getItem("userRole")) {
      this.userRole = window.localStorage.getItem("userRole");
    }
    // 先获取个人信息
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
  },
};
</script>

<style  scoped>
.grzx {
  width: 100%;
  height: 100%;
}
.grzx-content,
.xxgl {
  width: 100%;
  padding: 0 20.05% 0px 20.05%;
}
.grzx-content {
  padding: 20px 376px;

  min-height: calc(100vh - 365px);
}
.grzx-content-grxx {
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  height: 140px;
  background: #ffffff;
  box-shadow: 0px 2px 3px 0px rgba(181, 194, 204, 0.3);
  border-radius: 4px;
  border: 1px solid #dedede;
  padding: 50px 0px;
  padding: 35px 50px;
  justify-content: flex-start;
}
.item {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.item-left img {
  height: 56px;
}
.item-right {
  margin-left: 50px;
  height: 68px;
  line-height: 68px;
  font-size: 16px;
}
</style>
