<template>
  <div class="Bread">
    <div>当前所在位置：</div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>{{ name }}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  props: {
    name: {
      type: String,
      default: "",
    },
  },
};
</script>

<style scoped>
.Bread {
  opacity: 1;
  width: 100%;
  height: 55px;
  display: flex;
  align-items: center;
  color: #000;
  background: #fafafa;
  border: 1px solid #e6e6e6;
  padding: 20px 0 16px 383px;
}
</style>