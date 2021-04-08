<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
         <el-breadcrumb-item>政策法规</el-breadcrumb-item>
        <el-breadcrumb-item>政策法规详情</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div
      class="zcfgxq-content"
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
    >
      <div class="article-content">
        <div class="article-title">{{ articleData.title }}</div>
        <!-- <ul class="list-inline">
              <li>本文访问量：122</li>
              <li>文章来源：</li>
              <li>发布时间：{{articleData.createtime}}</li>
            </ul> -->
        <div class="article-info">
          <span>本文访问量：{{ articleData.pageviews }}</span>
          <!-- <span>文章来源：</span> -->
          <span style="margin-left: 10px"
            >更新时间：{{ articleData.updatetime }}</span
          >
        </div>
        <hr />
      </div>
      <div class="ql-container ql-editor" v-html="editorhtml"></div>
      <div v-if="fileList.length > 0">
        <div>附件：</div>
        <div>
          <el-form ref="form">
            <template v-for="(items, index) in fileList">
              <div class="fjyl" @click="yulan(Object.values(items)[index])">
                <el-row>
                  <el-col style="height: 30px">
                    <el-form-item>{{ Object.keys(items)[index] }}</el-form-item>
                  </el-col>
                </el-row>
              </div>
            </template>
          </el-form>
        </div>
      </div>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import Bottom from "@/components/Bottom.vue";
import Header from "@/components/Header.vue";
export default {
  components: {
    NavBar,
    Header,
    Bottom,
  },
  data() {
    return {
      editorhtml: "",
      id: "",
      articleData: {},
      loading: true,
      flag: "",
      fileList: [],
      items: "",
    };
  },
  mounted() {
    //获取政策法规详情
    this.getzcfgDetail();
  },
  methods: {
    yulan(filepath) {
      var path = encodeURI(filepath);
      // console.log(path);
      window.open(
        `http://xagh.org.cn:8083/view/url?url=http://10.7.120.10/file/${path}`,
        "_blank"
      );
    },
    getzcfgDetail() {
      let item = this.$route.query.item;
      let id = (this.id = this.$route.query.id);
      this.flag = this.$route.query.flag;

      let url =
        id && id !== ""
          ? `api/findPolicyById?id=${id}`
          : `api/findPolicyByItem?item=${item}`;
      this.$http.get(url).then((res) => {
        this.editorhtml = res.data.data.editorhtml;
        this.articleData = res.data.data;
        this.fileList = res.data.fileList || [];

        // _.each(res.data.data, v=> {
        //   v.fbsj = this.$moment(v.cjsj).format('YYYY-MM-DD');
        //   v.bmkssj = this.$moment(v.BMKSSJ).format('YYYY-MM-DD');
        //   v.zxsj = this.$moment(v.WTSJ).format('YYYY-MM-DD')
        //   this.zxggData.push(v);
        // })
        this.loading = false;
      });
    },
  },
  watch: {
    $route() {
      this.getzcfgDetail();
    },
  },
};
</script>

<style scoped>
.zcfgxq-content {
  width: 100%;
  height: auto;
  min-height: 572px;
  padding: 0 20.05% 20px 20.05%;
}
.list-inline {
  padding-left: 0;
  list-style: none;
  margin-left: -5px;
}
.article-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  font-family: "微软雅黑";
}
.list-inline {
  width: 700px;
  height: 50px;
  list-style: none;
  margin: 0;
  padding: 0;
}
.ui li {
  width: 70px;
  margin: 0 10px;
  float: left;
}
.ui li a {
  width: 70px;
  height: 50px;
  padding: 0 20px;
  font-size: 12px;
  line-height: 50px;
  background: red;
  display: inline-block;
}

/* li {
    text-align: center;
}
ul, li {
    padding: 0;
    margin: 0;
    list-style: none;
    font-size: 14px;
} */

.article-info {
  text-align: center;
  margin: 12px 12px;
  font-family: "微软雅黑";
}

.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="FangSong_GB2312"]::before,
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="FangSong_GB2312"]::before {
  content: "仿宋GB2312";
  font-family: "FangSong_GB2312";
}
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="FZXiaoBiaoSong-B05S"]::before,
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="FZXiaoBiaoSong-B05S"]::before {
  content: "方正小标宋简体";
  font-family: "FZXiaoBiaoSong-B05S";
}
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="SimSun"]::before,
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="SimSun"]::before {
  content: "宋体";
  font-family: "SimSun";
}
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="SimHei"]::before,
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="SimHei"]::before {
  content: "黑体";
  font-family: "SimHei";
}
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="KaiTi"]::before,
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="KaiTi"]::before {
  content: "楷体";
  font-family: "KaiTi";
}
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="Microsoft-YaHei"]::before,
.zcfgxq-content
  >>> .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="Microsoft-YaHei"]::before {
  content: "微软雅黑";
  font-family: "Microsoft YaHei";
}

.zcfgxq-content >>> .ql-font-SimSun {
  font-family: "SimSun";
}
.zcfgxq-content >>> .ql-font-SimHei {
  font-family: "SimHei";
}
.zcfgxq-content >>> .ql-font-FangSong_GB2312 {
  font-family: "FangSong_GB2312";
}
.zcfgxq-content >>> .ql-font-FZXiaoBiaoSong-B05S {
  font-family: "FZXiaoBiaoSong B05S";
}
.zcfgxq-content >>> .ql-font-KaiTi {
  font-family: "KaiTi";
}
.zcfgxq-content >>> .ql-font-Microsoft-YaHei {
  font-family: "Microsoft YaHei";
}
.fjyl :hover {
  color: rgb(13, 66, 240);
  cursor: pointer;
}
</style>