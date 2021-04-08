<template>
  <div>
    <!--    成果审核页面-->
    <!--    标题-->
    <!--左右结构-->
    <!-- 导航栏头部 -->
    <div class="nav-title"></div>
    <!-- 主体内容 -->
    <div class="cgshym-content">
      <div class="companyTitle">“多测合一”成果审核结论</div>
      <div style="height: 30px"></div>
      <div>
        <table
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          style="width: 100%; border-color: #ccc; border-collapse: collapse"
        >
          <tr>
            <td class="td1">项目名称:</td>
            <td class="td2">{{ content.xmmc }}</td>
          </tr>
          <tr>
            <td class="td1">项目编号:</td>
            <td class="td2">{{ content.xmbh }}</td>
          </tr>
          <tr>
            <td class="td1">业主单位:</td>
            <td class="td2">{{ content.wtdw }}</td>
          </tr>
          <tr>
            <td class="td1">项目地址:</td>
            <td class="td2">{{ content.xmdz }}</td>
          </tr>
          <tr>
            <td class="td1">中选单位:</td>
            <td class="td2">
              <a href="javascript:;" >{{ content.chdw }}</a>
            </td>
          </tr>
          <tr>
            <td class="td1">审核结果:</td>
            <td class="td2">{{ content.shjg }}</td>
          </tr>
          <tr>
            <td class="td1">审核时间:</td>
            <td class="td2">{{ content.shsj }}</td>
          </tr>
        </table>
      </div>

      <div class="buttom"><div style="width:330px;text-align: center;">©西安市自然资源和规划局</div></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "cgshxq",
  components: {},
  data() {
    return {
      loading: true,
      ywxxid: this.$route.query.ywxxid,
      pid: this.$route.query.id,
      content: {
        wtdw: "",
        xmdz: "",
        chdw: "",
        xmbh: "",
        shjg: "",
        shsj: "",
      },
    };
  },
  mounted() {
    this.getDetail();
  },
  methods: {
    toCompanyDetail() {
      let id = this.content.chdwid;
      this.$router.push({
        name: "qymlkdetail",
        query: { did: id },
      });
    },
    getDetail() {
      // 获取详情
      debugger;
      this.$http.get("dchy/dchyYwxx/getNoticeMsgByYwxxid?ywxxid=" + this.ywxxid).then((res) => {
          if (res.data) {
            let _data = { ...res.data.data };
            let _this = this;

            this.content.wtdw = _data.WTDW;
            this.content.xmmc = _data.XMMC;
            this.content.xmdz = _data.XMDZ;
            this.content.xmbh = _data.CGBH;
            this.content.chdw = _data.CHDWMC;
            this.content.chdwid = _data.CHDWID;
            this.loading = false;

            this.pid = _data.pid;
            this.content.shjg = _data.CGSFSH || "已通过";
            this.content.shsj =
              _this.$moment(_data.CHSHSJ).format("YYYY-MM-DD HH:mm:ss") || "无";
            // let params = {
            //   pageIndex: 1,
            //   pageSize: 100,
            //   str: this.pid,
            // };
            // return this.$http.post("xmwt/dchySqyx/getZbGg", params);
          }
        })
              /*.then((res) => {
          if (res.data.data) {
            let _data = [...res.data.data];
            this.content.wtdw = _data[0].WTDW;
            this.content.xmmc = _data[0].XMMC;
            this.content.xmdz = _data[0].XMDZ;
            this.content.xmbh = _data[0].DCHYBH;
            this.content.chdw = _data[0].CHDWMC;
            this.content.chdwid = _data[0].CHDWID;
            this.loading = false;
          }
        });*/
    },
  },
};
</script>

<style scoped>
.buttom {
  text-align: center;
  width: 100%;
}
.buttom {
  width: 300px;
  position: absolute;
  bottom: 20px;
}
.cgshym-content {
  height: 100%;
  margin: 0 20px 0 20px;
}

.zxggdetail-content {
  width: 100%;
  height: auto;
  min-height: 572px;
  padding: 0 20.05% 0px 20.05%;
}
.zxggdetail-content >>> .el-card__body {
  padding: 20px 40px 40px 40px;
}
.breadcrumb {
  height: 50px;
  display: flex;
  margin-bottom: 10px;
  padding: 0px 3rem;
  align-items: center;
  background: #0068d1;
}
.xmxqxx-div {
  width: 100%;
  text-align: right;
  margin-right: 30px;
}
.wtggxq-content {
  width: 100%;
  height: auto;
  padding: 0 20.05% 0px 20.05%;
}
.companyTitle {
  /* font-weight: 600; */
  font-size: 24px;
  text-align: center;
}
.clearfix {
  text-align: center;
  margin-bottom: 10px;
}
tr {
  height: 37px;
}
.td1 {
  border: 1px solid #ccc;
  text-align: right;
  padding-right: 10px;
  font-weight: 700;
}
.td2 {
  border: 1px solid #ccc;
  padding-left: 10px;
}
</style>