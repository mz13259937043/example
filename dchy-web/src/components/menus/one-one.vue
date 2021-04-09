<template>
  <div>
    oneoneMenu菜单
    <ul id="main">
      <el-button v-for="item in res"><a @click="$goRoute(item.path)">{{item.name}}</a></el-button>
    </ul>
    <router-view/>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        res:[
          {name:'尾部',path:'/home/one/one-one/end'},
        ]
      };
    },
    methods: {
    },
  };
</script>