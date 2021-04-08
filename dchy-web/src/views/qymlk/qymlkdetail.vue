<template>
  <div>
    <Header></Header>
    <NavBar></NavBar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>入驻机构</el-breadcrumb-item>
        <el-breadcrumb-item>入驻机构详情</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="qymlkdetail-content">
      <el-card style="border: 2px solid #ccc; background: #efefea42">
        <div class="companyTitle">{{ companydata.dwmc }}</div>
        <table
          style="width: 100%; border-color: #ccc; border-collapse: collapse"
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
        >
          <tr>
            <td class="td1">服务质量指数:</td>
            <td class="td2" style="color: red">{{ xydj }}</td>
          </tr>
          <tr>
            <td class="td1">办件量:</td>
            <td class="td2">{{ bjl }}</td>
          </tr>
          <tr>
            <td class="td1">当前服务项目数:</td>
            <td class="td2">{{ dqfwxms }}</td>
          </tr>
          <tr>
            <td class="td1">统一社会信用代码:</td>
            <td class="td2">{{ companydata.tyshxydm }}</td>
          </tr>
          <tr>
            <td class="td1">企业名称:</td>
            <td class="td2">{{ companydata.dwmc }}</td>
          </tr>
          <tr>
            <td class="td1">办公地址:</td>
            <td class="td2">{{ companydata.qydz }}</td>
          </tr>
          <tr>
            <td class="td1">法定代表人:</td>
            <td class="td2">{{ companydata.frxm }}</td>
          </tr>
          <tr>
            <td class="td1">法人联系方式:</td>
            <td class="td2">{{ companydata.frdh }}</td>
          </tr>
          <tr>
            <td class="td1">联系人:</td>
            <td class="td2">{{ companydata.lxr }}</td>
          </tr>
          <tr>
            <td class="td1">联系方式:</td>
            <td class="td2">{{ companydata.lxdh }}</td>
          </tr>
          <tr>
            <td class="td1">注册资金:</td>
            <td class="td2">{{ companydata.zczj }}</td>
          </tr>
          <tr>
            <td class="td1">经营范围:</td>
            <td class="td2">{{ companydata.jyfw }}</td>
          </tr>
          <tr>
            <td class="td1">资质证书(附件):</td>
            <td class="td2 td2look" @click="zzzslook">查看</td>
          </tr>
          <tr>
            <td class="td1">证书编号:</td>
            <td class="td2">{{ companydata.zsbh }}</td>
          </tr>
          <tr>
            <td class="td1">资质等级:</td>
            <td class="td2">{{ companydata.zzdj }}</td>
          </tr>
          <tr>
            <td class="td1">有效日期:</td>
            <td class="td2">{{ companydata.zsyxq }}</td>
          </tr>
          <tr>
            <td class="td1">发证部门:</td>
            <td class="td2">{{ companydata.fzbm }}</td>
          </tr>
          <tr>
            <td class="td1">资质审核部门:</td>
            <td class="td2">{{ companydata.zzshbm }}</td>
          </tr>
          <tr>
            <td class="td1">专业范围:</td>
            <td class="td2">{{ companydata.zyfw }}</td>
          </tr>
          <tr>
            <td class="td1">“多测合一”业务范围:</td>
            <td class="td2">{{ companydata.dchyywfw }}</td>
          </tr>
        </table>
      </el-card>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import Bottom from "@/components/Bottom.vue";
import Header from "@/components/Header.vue";
export default {
  name: "qymlkdetail",
  components: {
    NavBar,
    Header,
    Bottom,
  },
  data() {
    return {
      did: "",
      companydata: {},
      zzxxdata: {},
      bjl: "",
      dqfwxms: "",
      xydj: "★★★★★(5.0)",
      loading: true,
    };
  },
  mounted() {
    // 获取企业资质详情
    this.did = this.$route.query.did;
    this.$http
      .post("zzxx/selectZzxx", {
        did: this.did,
      })
      .then((res) => {
        this.companydata = res.data.companydate;
        this.zzxxdata = res.data.zzxxdate;
        this.companydata = Object.assign(this.companydata, this.zzxxdata);
      });
    this.$http.get("xmwt/dchyFbxx/findfwCount/" + this.did, {}).then((res) => {
      this.bjl = res.data.bjl;
      this.dqfwxms = res.data.dqfwxms;
      this.loading = false;
    });
  },
  methods: {
    zzzslook() {
      var xzfj = this.zzxxdata.zzfj;

      let fileFormData = new FormData();
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      fileFormData.append("folderid", xzfj);
      this.$http
        .post(
          // "http://222.90.207.221/dchy/dchy/ftpService/listFile2",
          "ftpService/listFile2",
          fileFormData,
          requestConfig
        )
        .then((res) => {
          var path = "";
          var id = res.data.id;
          for (let item of res.data.dchyFjFolders) {
            if (item.path == "资质证书扫描件") {
              path = `${res.data.path}/${item.path}/${item.dchyFjRecords[0].filename}`;
            }
          }
          path = encodeURI(`${path}`);
          // console.log(path);
          window.open(
            `http://xagh.org.cn:8083/view/url?url=http://10.7.120.10/file/${path}`,
            "_blank"
          );
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style scoped>
.qymlkdetail-content {
  width: 100%;
  height: auto;
  padding: 0 20.05% 20px 20.05%;
}
.qymlkdetail-content >>> .el-card__body {
  padding: 20px 40px 40px 40px;
}
.xmxqxx-div {
  width: 100%;
  text-align: right;
  margin-right: 30px;
}
.wtggxq-content {
  width: 100%;
  height: auto;
  padding: 0 20.05% 100px 20.05%;
}
.qymlkdetail-content .companyTitle {
  /* font-weight: 600; */
  font-size: 24px;
  text-align: center;
  margin-bottom: 10px;
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
  /* width:20%; */
  text-align: right;
  padding-right: 10px;
  font-weight: 700;
}
.td2 {
  border: 1px solid #ccc;
  width: 850px;
  padding-left: 10px;
}
.td2look {
  color: blue;
}
.td2look:hover {
  cursor: pointer;
}
</style>