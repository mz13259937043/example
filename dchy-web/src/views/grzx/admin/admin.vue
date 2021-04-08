<template>
  <div class="admin">
    <div class="login_form">
      <div class="titile">管理员登录</div>
      <el-form label-width="100px">
        <el-form-item label="账号">
          <el-input v-model="username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="请确认密码">
          <el-input v-model="password2" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >提交</el-button
          >
        </el-form-item>
      </el-form>
    </div>

    <Bottom></Bottom>
  </div>
</template>

<script>
import Bottom from "@/components/Bottom.vue";
export default {
  data() {
    return {
      username: "admin",
      password: "admin",
      password2: "admin",
      userinfo: "",
    };
  },
  components: {
    Bottom,
  },
  methods: {
    submitForm() {
      var params = {
        id: "admin",
      };
      window.localStorage.setItem("userRole", "gr");
      window.localStorage.setItem("userID", "admin");
      this.$router.push("index");
    },
  },
};
</script>

<style scoped>
.admin {
  height: 100%;
}
.titile {
  font-size: 28px;
  margin-left: 60px;
  padding: 20px 0;
}
.login_form {
  height: calc(100% - 240px);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.el-form {
  width: 400px;
}
.el-form-item {
  margin-top: 20px;
}
.el-button {
  margin-left: 211px;
}
</style>