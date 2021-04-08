<template>
  <div class="dropdown">
    <el-backtop
      :target="propInfo.name"
      :visibility-height="10"
      :bottom="220"
      :right="170"
    >
      <div
        class="iconTop"
        @mouseover="mouseenter($event)"
        @mouseout="mouseleave($event)"
      >
        <div class="hddb">回到<br />顶部</div>
        <!--      <div class="iconTop"  >-->
        <i class="el-icon-top"></i>
      </div>
    </el-backtop>
  </div>
</template>

<script>
var yiru = 0;
var yichu = 0;
export default {
  data() {
    return {};
  },
  props: {
    propInfo: {
      type: Object,
      default: {},
    },
  },
  created() {
    // console.log(this.propInfo);
  },
  methods: {
    mouseenter(e) {
      let hddb = document.getElementsByClassName("hddb");
      let hddb0 = hddb[0];
      hddb0.style.display = "block";
      // hddb0.setAttribute('display', 'block');
      console.log("移入" + yichu++);
    },
    mouseleave(e) {
      let hddb = document.getElementsByClassName("hddb");
      let hddb0 = hddb[0];
      hddb0.style.display = "none";
      // hddb0.setAttribute('display', 'block');
      console.log("移出" + yiru++);
    },
  },
};
</script>

<style scoped>
.el-backtop {
  /*background: #409eff;*/
  color: #fff;
  /* width: 20px; */
  height: 50px;
  width: 50px;
  border-radius: 0%;
}
.hddb {
  display: none;
  width: 50px;
  height: 50px;
  position: fixed;
  bottom: 220px;
  right: 170px;
  color: #fff;
  text-align: center;
  line-height: 25px;
  font-size: 16px;
  background: #66b1ff;
}
.iconTop {
  height: 50px;
  width: 50px;
  background-color: #f2f5f6;
  text-align: center;
  line-height: 50px;
  color: #1989fa;
  font-size: 30px;
}
</style>