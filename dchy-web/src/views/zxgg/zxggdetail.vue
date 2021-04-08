<template>
  <div>
    <Header></Header>
    <Navbar></Navbar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>中选公告</el-breadcrumb-item>
        <el-breadcrumb-item>中选公告详情</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 主体内容 -->
    <div class="zxggdetail-content">
      <el-card style="border: 2px solid #ccc; background: #efefea42">
        <div class="companyTitle">
          关于为【{{ companydata.XMMC }}】选取测绘服务机构的中选结果公告
        </div>
        <div style="text-align: center; margin: 10px 0">
          发布时间：{{ companydata.BMKSSJ }}
        </div>
        <table
          v-loading="loading"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          style="width: 100%; border-color: #ccc; border-collapse: collapse"
        >
          <tr>
            <td class="td1">项目名称:</td>
            <td class="td2">{{ companydata.XMMC }}</td>
          </tr>
          <tr>
            <td class="td1">中选结果:</td>
            <!--<span size="medium" v-if="scope.row.FB && scope.row.FB.indexOf('废标1') != -1" v-html="changeColor('公示期内没有单位申请导致废标')"></span>
            <span size="medium" v-else-if="scope.row.FB && scope.row.FB.indexOf('废标2') != -1" v-html="changeColor('超过期限公示期，业主单位未选取导致废标')"></span>
            <span size="medium"v-else-if="scope.row.FB && scope.row.FB.indexOf('废标3') != -1" v-html="changeColor(scope.row.XMZT)"></span>
            &lt;!&ndash;             <span size="medium" v-else v-html="changeColor(scope.row.FB)" ></span>&ndash;&gt;-->
            <td class="td2">
              {{
                companydata.FB && companydata.FB.indexOf("废标") !== -1
                  ? "废标"
                  : companydata.FB
              }}
              <template v-if="companydata.FBYY">
                {{ '（原因：'+companydata.FBYY+'）' }}
              </template>
            </td>
          </tr>
          <tr>
            <td class="td1">采购单位:</td>
            <td class="td2">{{ companydata.WTDW }}</td>
          </tr>
          <tr>
            <td class="td1">中选机构:</td>
            <td class="td2">
              <a href="javascript:;" @click="change">{{
                companydata.CHDWMC
              }}</a>
            </td>
          </tr>
          <tr>
            <td class="td1">测绘阶段:</td>
            <td class="td2">{{ companydata.CHJD }}</td>
          </tr>
          <tr>
            <td class="td1">服务金额/元:</td>
            <td class="td2">{{ companydata.XMBJ }}</td>
          </tr>
          <tr>
            <td class="td1">中选日期:</td>
            <td class="td2">{{ companydata.WTSJ }}</td>
          </tr>
        </table>
        <div style="margin-top: 10px">
          提示：如对该公告内容存在异议可致电西安市“多测合一”信息平台管理部门
        </div>
      </el-card>
    </div>
    <Buttom></Buttom>
  </div>
</template>

<script>
import Buttom from "@/components/Bottom.vue";
import Header from "@/components/Header.vue";
import Navbar from "@/components/NavBar.vue";
export default {
  name: "zxggdetail",
  components: {
    Buttom,
    Header,
    Navbar,
  },
  data() {
    return {
      companydata: {
        chdwid: "",
        xmmc: "",
        zxjg: "",
        wtdw: "",
        chdw: "",
        fwje: "",
        wtsj: "",
      },
      loading: true,
    };
  },
  mounted() {
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    if (window.localStorage.getItem("processList")) {
      this.processList = JSON.parse(window.localStorage.getItem("processList"));
    }
    this.getlist();
  },
  methods: {
    // 跳转
    change() {
      let id = this.companydata.CHDWID;
      this.$router.push({
        name: "qymlkdetail",
        query: { did: id },
      });
    },
    async getlist() {
      // 获取详情
      let id = this.$route.query.id;
      let params = {
        pageIndex: 1,
        pageSize: 100,
        str: id,
      };
      this.$http.post("xmwt/dchySqyx/getZbGg", params).then((res) => {
        if (res.data.data) {
          debugger;
          let _data = [...res.data.data];
          _data[0].BMKSSJ = this.$moment(_data[0].BMKSSJ).format(
            "YYYY-MM-DD HH:mm:ss"
          );
          _data[0].BMJSSJ = this.$moment(_data[0].BMJSSJ).format(
            "YYYY-MM-DD HH:mm:ss"
          );
          if (_data[0].WTSJ)
            _data[0].WTSJ = this.$moment(_data[0].WTSJ).format(
              "YYYY-MM-DD HH:mm:ss"
            );
          if (_data[0].CHJD)
            _data[0].CHJD = _.filter(this.processList,item=>{
                return item.id === _data[0].CHJD;
              })[0].processAlias;
        /*<span size="medium" v-if="scope.row.FB && scope.row.FB.indexOf('废标1') != -1" v-html="changeColor('公示期内没有单位申请导致废标')"></span>
                  <span size="medium" v-else-if="scope.row.FB && scope.row.FB.indexOf('废标2') != -1" v-html="changeColor('超过期限公示期，业主单位未选取导致废标')"></span>
                  <span size="medium"v-else-if="scope.row.FB && scope.row.FB.indexOf('废标3') != -1" v-html="changeColor(scope.row.XMZT)"></span>
                  <!--             <span size="medium" v-else v-html="changeColor(scope.row.FB)" ></span>-->*/
          if (_data[0].FB.indexOf('废标1') != -1){
            _data[0].FBYY = '因无人报价，竞价取消。';
          }else if(_data[0].FB.indexOf('废标2') != -1){
            _data[0].FBYY = '因逾期未选取，项目废标。';
          }else if(_data[0].FB.indexOf('废标3') != -1){
            _data[0].FBYY = _data[0].FBYY;
          }else{

          }
          this.companydata = _data[0];
          this.loading = false;
        }
      });
      /*this.$http
        .get("xmwt/dchyFbxx/findAllInfoById?fbxxId=" + id)
        .then((res) => {
          let resData = res.data.data;
          if (resData) {
            if (resData.sqyxList && resData.sqyxList.length > 0) {
              for (let item of resData.sqyxList) {
                if (item.wtqr === "Y") {
                  this.companydata.chdwid = item.chdwid;
                  this.$http
                    .get("user/findCompanybyId?id=" + item.chdwid)
                    .then((res) => {
                      if (res.data) {
                        this.companydata.chdw = res.data.dwmc;
                      }
                    });
                  this.companydata.chdw = item.chdwid;
                  this.companydata.fwje = item.xmbj;
                  this.companydata.wtsj = this.$moment(item.wtsj).format(
                    "YYYY-MM-DD HH:mm:ss"
                  );
                  break;
                }
              }
              this.companydata.xmmc = resData.xmmc;
              this.companydata.wtdw = resData.wtdw;
              this.companydata.zxjg = "中选";
              this.companydata.chjd = resData.chjd;
            }
          }

          this.zzxxdata = res.data.zzxxdate;
          this.loading = false;
        });*/
    },
  },
};
</script>

<style scoped>
a {
  text-decoration: none;
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
  /* width:17%; */
  text-align: right;
  padding-right: 10px;
  font-weight: 700;
}
.td2 {
  border: 1px solid #ccc;
  width: 80%;
  padding-left: 10px;
  width: 889px;
}
</style>