<template>
  <div>
    onetwoMenu菜单
    <ul id="main">
      <el-button v-for="item in res"><a @click="$goRoute(item.path)">{{item.name}}</a></el-button>
    </ul>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        res:[
          {name:'首页',path:'/home'},
        ]
      };
    },
    methods: {
    },
  };
</script>