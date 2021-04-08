<template>
  <div class="login">
    <div class="header">
      <div class="logo"></div>
      <div class="headline">
        <!-- <span>平台简介</span>
        <span>平台简介</span> -->
      </div>
    </div>
    <div class="content">
      <div class="logo2"></div>
      <div class="login_content">
        <div class="title">登录账号</div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
          <el-form-item prop="username">
            <el-input
              v-model="ruleForm.username"
              placeholder="用户名"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="ruleForm.password"
              type="password"
              placeholder="密码"
            ></el-input>
          </el-form-item>

          <div class="state">
            <div>
              <el-checkbox v-model="checked">记住密码</el-checkbox>
            </div>
            <div @click="forgetPassword" class="forgetmm">忘记密码?</div>
          </div>
          <div class="login_dl" @click="submitForm('ruleForm')">
            <div>登 录</div>
          </div>
        </el-form>
      </div>
    </div>

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
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userinfo: "",
      checked: false,
      ruleForm: {
        username: "",
        password: "",
      },

      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  components: {},
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var params = {
            phonenum: this.ruleForm.username,
            password: this.ruleForm.password,
          };
          this.$http.post("login2", params).then((res) => {
            if (res.data) {
              if (this.checked) {
                window.localStorage.setItem("yhzh", this.ruleForm.username);
                window.localStorage.setItem("yhmm", this.ruleForm.password);
              }
              // 统一存储userID
              window.localStorage.setItem("userID", res.data.id);
              window.localStorage.setItem("userRole", "gr");
              debugger;
              this.$router.push("index");
              //debugger;
            } else {
              this.$message({
                message: "账户或密码错误",
                type: "error",
                offset: 160,
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    forgetPassword() {
      this.$message({
        message: "技术支持电话：029-84251161",
        type: "warning",
        offset: 160,
      });
    },
    togywm() {
      this.$router.push({
        name: "zcfgdetail",
        query: { id: "c5f166ecb7b142f6b299d288f240d58c" },
      });
    },
    tolxwm() {
      this.$router.push({
        name: "zcfgdetail",
        query: { id: "d9502f976b7940ceaf96ef413803794f" },
      });
    },
  },
  created() {
    var _self = this;
    document.onkeydown = function (e) {
      var key = window.event.keyCode;
      if (key == 13) {
        _self.submitForm("ruleForm");
      }
    };
    if (window.localStorage.getItem("yhzh")) {
      this.ruleForm.username = window.localStorage.getItem("yhzh");
      this.ruleForm.password = window.localStorage.getItem("yhmm");
      this.checked = true;
    }
  },
};
</script>

<style scoped>
.header {
  height: 113px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logo {
  height: 108px;
  width: 681px;
  margin-left: 255px;
  background: url("../../assets/img/logo1112.png") no-repeat;
}
.logo2 {
  height: 133px;
  width: 768px;
  margin-left: 283px;
  margin-top: 99px;
  background: url("../../assets/img/loginlogo1.png") no-repeat;
}
.headline {
  margin-right: 339px;
}
.headline span {
  margin-right: 36px;
  font-size: 18px;
  font-family: PingFang SC;
  font-weight: 400;
  line-height: 25px;
  color: #4a4a4a;
  opacity: 1;
}
.headline span:hover {
  cursor: pointer;
}
.content {
  height: calc(100vh - 248px);
  background: url("../../assets/img/loginbg.png") no-repeat;
  display: flex;
  flex-direction: column;
}
.login_content {
  width: 400px;
  height: 400px;
  background: #fff;
  /* margin-left: 1230px; */
  margin-left: 60%;
  margin-right: 400px;
  box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.08);
  opacity: 1;
  border-radius: 10px;
  display: flex;
  align-items: center;
  flex-direction: column;
}
.el-form {
  width: 340px;
  padding: 0 30px;
}
.el-form-item {
  margin-bottom: 0 !important;
}
.el-input {
  height: 50px;
  font-size: 16px;
  margin-top: 30px;
}
.login_content >>> .el-input__inner {
  height: 50px;
}
.login_dl {
  margin-top: 25px;
  height: 56px;
  border: 1px solid rgba(32, 53, 128, 0.16078431372549018);
  background: linear-gradient(302deg, #007ee5 0%, #05a0ff 100%, #5533ff 100%);
  opacity: 1;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.login_dl:hover {
  cursor: pointer;
}
.forgetmm {
  color: #1890ff;
}
.forgetmm:hover {
  cursor: pointer;
}
.login_dl div {
  font-size: 20px;
  font-family: SourceHanSansCN-Regular;
  color: #ffffff;
  opacity: 1;
  text-align: center;
}
.title {
  font-size: 28px;
  font-family: SourceHanSansCN-Bold;
  margin-top: 46px;
  color: #585858;
  opacity: 1;
}
.state {
  height: 20px;
  margin-top: 30px;
  display: flex;
  justify-content: space-between;
  font-size: 15px;
}
.state div {
  height: 20px;
  line-height: 20px;
  display: flex;
  align-items: center;
}
.state >>> .el-checkbox {
  height: 20px;
  line-height: 20px;
  font-size: 15px;
}
.state >>> .el-checkbox__input.is-checked + .el-checkbox__label {
  color: #1890ff !important;
  font-size: 15px;
}
.state >>> .el-checkbox__label {
  font-size: 15px !important;
}
.state >>> .el-checkbox__label {
  /* color: #fff; */
  height: 20px;
  line-height: 20px;
  font-size: 15px;
}

.bottom {
  height: 135px;
  background: #233042;
  display: flex;
  flex-direction: column;
  color: #fff;
  align-items: center;
}
.firstLine {
  margin-top: 20px;
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
  font-size: 14px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #cbcbcb;
}
.item div:hover {
  color: #0091ff;
  cursor: pointer;
}
.item :first-child {
  margin-top: 15px;
}
.item_second {
  margin-top: 15px;
}
</style>