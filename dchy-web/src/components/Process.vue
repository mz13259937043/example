<template>
  <div class="process">
    <div
      v-for="(item, index) in processList"
      :key="index"
      class="process_item"
      :class="{
        xiaoyuColor: index + 1 < condition,
        inprogress: index + 1 == condition,
      }"
    >
      <div class="process_item_left">第{{ index + 1 }}步</div>
      <div class="process_item_right">{{ item.title }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    condition: {
      type: Number,
      default: 1,
    },
  },
  data() {
    return {
      processList: [],
    };
  },
  methods: {},
  created() {
    this.processList = [
      {
        ind: 1,
        title: "测绘实施",
      },
      {
        ind: 2,
        title: "成果汇交",
      },
      {
        ind: 3,
        title: "成果负责人确认",
      },
      {
        ind: 4,
        title: "成果验收",
      },
      {
        ind: 5,
        title: "成果审核",
      },
    ];
  },
};
</script>

<style  scoped>
.process {
  display: flex;
  width: 100%;
  /* height: 100%; */
  /* margin-left: 16px; */
  /* color: #fff; */
  font-size: 14px;
  padding: 0 383px;
  margin-top: 20px;
}
.processList {
  display: flex;
  height: 40px;
}
.process_item {
  flex: 1;
  height: 40px;
  background: url("../assets/jdt/notdone.png") no-repeat;
  background-size: 100% 100%;
  /* margin-right: 10px; */
  display: flex;
  align-items: center;
  justify-content: space-around;
  color: #fff;
  font-size: 14px;
  padding-right: 30px;
}
.process :last-child {
  margin-right: 0px;
}
.process_item_left {
  margin-left: 20%;
}
/* .process_item_right {
  margin-right: 15%;
} */
.xiaoyuColor {
  background: url("../assets/jdt/done.png") no-repeat;
  background-size: 100% 100%;
}
.inprogress {
  background: url("../assets/jdt/inprogress.png") no-repeat;
  background-size: 100% 100%;
}
</style>