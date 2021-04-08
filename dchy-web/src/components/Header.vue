<template>
  <div class="header">
    <div class="logo">
      <img src="@/assets/img/logo1112.png" alt="" />
    </div>
    <div class="info">
      <div v-if="!userinfo" class="login_welcome"></div>
      <div v-else class="login_info">
        <img src="@/assets/img/login.png" />
        <!--<div v-if="userinfo.frxm" class="login_info_username">
          {{ userinfo.frxm }}
        </div>-->
        <div v-if="userinfo.dwmc" class="login_info_username">
          {{ userinfo.dwmc }}
        </div>
        <div v-if="userinfo.name" class="login_info_username">
          {{ userinfo.name }}
        </div>
        <div
          class="login_out"
          @click="loginout"
          title="注销登录"
          @mouseover="selectStyle()"
          @mouseout="outStyle()"
          :class="{ active_style: ishover }"
        >
          退出
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userinfo: "",
      ishover: false,
      serachInfo: "",
      zwwid: "",
    };
  },
  methods: {
    loginout() {
      debugger;
      var userid = {
        userid: this.zwwid,
      };
      var config = this.$http.defaults.redirectURL;
      console.log(config);
      console.log(userid);
      if (
        window.localStorage.getItem("userRole") != "yzdw" &&
        window.localStorage.getItem("userRole") != "chdw" &&
        window.localStorage.getItem("userRole") != "chsgcs"
      ) {
        debugger;
        var username = "";
        var password = "";
        if (window.localStorage.getItem("yhzh")) {
          username = window.localStorage.getItem("yhzh");
          password = window.localStorage.getItem("yhmm");
        }
        window.localStorage.clear();
        if (username) {
          window.localStorage.setItem("yhzh", username);
          window.localStorage.setItem("yhmm", password);
        }
        this.userInfomation = "";

        if (this.$route.name == "index") {
          location.reload();
        } else {
          this.$router.push("/");
        }
        return;
      }
      console.log("准备进入消除token");
      debugger;
      this.$http
        .post("getTokenByReq", userid)
        .then((res) => {
          debugger;
          // var result = JSON.parse(res.data);
          let message = res.data.message;
          if (message == "userid为空") {
            this.$message.warning(message);
          } else if (message == "出错了") {
            this.$message.warning("服务器开小差了，请您稍后再试！");
          } else if (message == "成功") {
            var username = "";
            var password = "";
            if (window.localStorage.getItem("yhzh")) {
              username = window.localStorage.getItem("yhzh");
              password = window.localStorage.getItem("yhmm");
            }
            window.localStorage.clear();
            if (username) {
              window.localStorage.setItem("yhzh", username);
              window.localStorage.setItem("yhmm", password);
            }
            this.userInfomation = "";

            var token = res.data.data.token;
            var token1 = {
              token: token,
            };
            console.log(token);
            console.log(token1);
            debugger;
            var ur =
              "http://sfrz.shaanxi.gov.cn/sysauthserver/logout?client_id=000000047&client_secret=53dc5c87b35b5fb5199d986c02a33e55&access_token=" +
              token +
              "&redirect_uri=" +
              config +
              "/index.html?logout=123";
            console.log(
              "http://sfrz.shaanxi.gov.cn/sysauthserver/logout?client_id=000000047&client_secret=53dc5c87b35b5fb5199d986c02a33e55&access_token=" +
                token +
                "&redirect_uri=" +
                config +
                "?logout=123"
            );
            debugger;
            window.location.href =
              "http://sfrz.shaanxi.gov.cn/sysauthserver/logout?client_id=000000047&client_secret=53dc5c87b35b5fb5199d986c02a33e55&access_token=" +
              token +
              "&redirect_uri=" +
              config +
              "index.html?logout=123";
            // if (this.$route.name == "index") {
            //   location.reload();
            // } else {
            //   this.$router.push("/");
            // }
          }
        })
        .catch((err) => {
          this.$message.error("请求出错！请稍后再试");
          console.log(err);
        });
    },
    selectStyle() {
      this.ishover = true;
    },
    outStyle() {
      this.ishover = false;
    },
  },
  created() {
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }

    if (window.localStorage.getItem("zwwid")) {
      this.zwwid = window.localStorage.getItem("zwwid");
    }
  },
};
</script>

<style scoped>
.header {
  height: 80px;
  background: #ffffff;
  display: flex;
  justify-content: space-between;
  padding: 0 385px 0 370px;
}
.logo {
  height: 80px;
  display: flex;
  align-items: center;
}
.logo > img {
  height: 80px;
}

.logo_headline .logo_title {
  width: 253px;
  height: 23px;
  font-size: 23px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #3a3a3a;
  line-height: 23px;
}
.logo_english {
  height: 12px;
  font-size: 12px;
  font-family: FZLTCHK--GBK1-0, FZLTCHK--GBK1;
  font-weight: 600;
  color: #4a4949;
  line-height: 12px;
  margin-top: 6px;
}
.search {
  display: flex;
  align-items: center;
  height: 80px;
  margin-left: 363px;
}
.el-input {
  width: 300px !important;
}
.search input {
  width: 250px;
  height: 34px;
  background: #ffffff;
  border-radius: 3px;
  border: 1px solid #cbcbcb;
  padding-left: 20px;
  outline: none;
}
.info {
  height: 80px;
  display: flex;
  align-items: center;
  /* margin-left: 48px; */
  font-size: 16px;
  justify-content: flex-end;
}
.info .login_info {
  display: flex;
  align-items: center;
}
.login_info img {
  margin-left: 5px;
}
.login_welcome {
  margin-left: 15px;
}
.login_info_username {
  margin-left: 5px;
}
.login_out {
  margin-left: 10px;
  cursor: pointer;
}
.active_style {
  border-bottom: 1px solid blue;
  color: blue;
}
.loggin_out_a :hover {
  color: #317ac9;
  text-decoration: underline;
  cursor: pointer;
}

.login_welcome {
  font-size: 16px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #6d7278;
}
input::-webkit-input-placeholder {
  /* WebKit browsers */
  color: #ccc;
  margin-left: 20px;
}
input:-moz-placeholder {
  /* Mozilla Firefox 4 to 18 */
  color: #ccc;
  margin-left: 20px;
}
input::-moz-placeholder {
  /* Mozilla Firefox 19+ */
  color: #ccc;
  margin-left: 20px;
}
input:-ms-input-placeholder {
  /* Internet Explorer 10+ */
  color: #ccc;
}
</style>