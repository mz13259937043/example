<template>
  <div>
    oneMenu菜单
    <ul id="main">
      <!--      <li v-for="item in  res"><router-link  :to="item.path" >{{ item.name }}</router-link></li>-->
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
        {name:'菜单1-1',path:'/home/one/one-one'},
        {name:'菜单1-2',path:'/home/one/one-two'},
      ],
    };
  },
  methods: {
  },
};
</script>