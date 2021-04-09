<template>
  <div>
    twoMenu菜单
    <ul id="main">
      <!--      <li v-for="item in  res"><router-link  :to="item.path" >{{ item.name }}</router-link></li>-->
      <el-button v-for="item in res"><a @click="$goRoute(item.path)">{{item.name}}</a></el-button>
    </ul>
    <router-view />
  </div>
</template>

<script>
  export default {
    data() {
      return {
        res:[
          {name:'菜单2-1',path:'/home/two/two-one'},
          {name:'菜单2-2',path:'/home/two/two-two'},
        ],
      };
    },
    methods: {
    },
  };
</script>