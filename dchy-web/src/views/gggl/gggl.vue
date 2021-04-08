<template>
  <div class="index">
    <Header></Header>
    <NavBar></NavBar>
    <div class="nav-title">
      <div>当前所在位置：</div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>公告管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="min-height:478px">
    <div class="container" >
    <div style="display: inline-block;border-right: 1px solid #e4e7ed;border-left: 1px solid#e4e7ed;border-top: 1px solid #e4e7ed;float:left">
      <div id="wbdiv" class="item" :class="cgshow" @click="cgggclick" style="display: inline-block">采购公告</div>
      <div id="ybdiv" class="item" :class="zxshow" @click="zxggclick" style="display: inline-block">中选公告</div>
    </div>
       <el-input
        placeholder="请输入内容"
        style="float:left;margin-bottom:20px;margin-left:20px"
        v-model="page.str"
        class="input-with-select"
      >
      <el-button 
          slot="append"
          class="btn"
          @click="getList()"
        >搜索</el-button>
      </el-input>
     
      <!-- 采购公告 -->
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        v-if="cgshow"
        :data="tableData"
        border
        :key="cgshow"
        style="width: 100%; cursor: pointer"
        :row-style="{ height: '32px' }"
        :header-cell-style="{background:'#0091ff'}"
      >
        <el-table-column prop="xmmc" label="项目名称" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMMC)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="xmdz" label="项目地址" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMDZ)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="CHJD"
          label="测绘阶段"
          align="center"
          width="120"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.CHJD)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="ZZDJ"
          label="要求最低资质等级"
          align="center"
          width="70"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.ZZDJ)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="YSJE"
          label="预算金额(单位/元)"
          align="center"
          width="90"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.YSJE)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="XMWCSX"
          label="完成时限(单位/天)"
          align="center"
          width="80"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMWCSX)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="wtdw" label="采购单位" show-overflow-tooltip align="center">
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.WTDW)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="BMKSSJ"
          label="发布时间"
          :formatter="dateFormat"
          align="center"
          width="95"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.BMKSSJ)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="operate" label="操作" show-overflow-tooltip align="center" width="95">
          <template slot-scope="scope">
              <el-button class="btn-danger" size="small" @click="handlecx(scope.row)">撤销</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 中选公告 -->
      <el-table
        v-else
        :data="zxggData"
        border
        style="width: 100%; cursor: pointer; "
        :row-style="{ height: '40px' }"
        :header-cell-style="{background:'#0091ff'}"
      >
        <el-table-column prop="XMMC" show-overflow-tooltip label="项目名称" align="center">
           <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.XMMC)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="WTDW" show-overflow-tooltip label="采购单位" align="center">
           <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.WTDW)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="chjd" show-overflow-tooltip label="测绘阶段" align="center">
           <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.chjd)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="bmkssj" show-overflow-tooltip label="报名开始时间" align="center">
           <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.bmkssj)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="zxsj" show-overflow-tooltip label="中选时间" align="center">
           <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.zxsj)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="CHDW" show-overflow-tooltip label="中选单位" align="center">
           <template slot-scope="scope">
            <span size="medium" v-html="changeColor(scope.row.CHDW)"></span>
          </template>
        </el-table-column>
        <el-table-column prop="operate" label="操作" align="center" width="95">
          <template slot-scope="scope">
              <el-button class="btn-danger" size="small" @click="handlecx(scope.row)">撤销</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        v-if="paginationshow"
        @current-change="handleCurr"
        @size-change="handleSize"
        :current-page="page.pageIndex"
        :page-size="page.pageSize"
        layout="total,sizes, prev, pager, next, jumper"
        :total="page.total"
        :page-sizes="[10, 20]"
        style="margin-bottom:20px;text-align:center"
      ></el-pagination>
      </div>
    </div>
    <Bottom></Bottom>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Bottom from "@/components/Bottom.vue";
import NavBar from "@/components/NavBar.vue";
import { formatDate } from "@/assets/js/common.js";
export default {
  data() {
    return {
      zxshow:'',
      cgshow:'color',
      content:'',
      //  分页组件
      page: {
        total: null,
        pageIndex: 1,
        pageSize: 10,
        str: null,
      },
      paginationshow:false,
      // 表格信息
      tableData: [],
      zxggData:[],
      loading:true
    };
  },
  components: {
    Header,
    Bottom,
    NavBar,
  },
  mounted () {
      this.getList()  
  },
  methods: {
    getList(){
        let params = {
            pageIndex: this.page.pageIndex,
            pageSize: this.page.pageSize,
            str: this.page.str,
        };
        if(this.cgshow){
            this.$http.post("xmwt/dchyFbxx/getFbxxGg", params).then(res=>{
                if(res.data.count>9){
                    this.paginationshow = true
                }else{
                    this.paginationshow = false
                }
                _.each(res.data.data, (v) => {
                    v.BMKSSJ = this.$moment(v.BMKSSJ).format("YYYY-MM-DD");
                });
                this.tableData = res.data.data;
                this.page.total = res.data.count;
                this.loading=false

            })
        }else{
            this.$http.post("xmwt/dchySqyx/getZbGg", params).then((res) => {
                if(res.data.count>9){
                    this.paginationshow = true
                }else{
                    this.paginationshow = false
                }
                this.page.total = res.data.count;
                _.each(res.data.data, (v) => {
                    v.fbsj = this.$moment(v.cjsj).format("YYYY-MM-DD");
                    v.bmkssj = this.$moment(v.BMKSSJ).format("YYYY-MM-DD");
                    v.zxsj = this.$moment(v.WTSJ).format("YYYY-MM-DD");
                });
                this.zxggData = res.data.data;
                 this.loading=false;

            });
        }

    },
    // 撤销
    handlecx(data){
       this.$confirm('确认撤销?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
          id:data.ID,
          xmzt:"草稿"
        }
        this.$http.post("/xmwt/dchyFbxx/renewFbxx", params).then(res=>{
          this.$message.success(res.data.msg)
          this.getList()
        })
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消撤销'
          });          
        });
          
     
    },
    dateFormat(row, column) {
      let rowdate = row[column.property];
      if (rowdate) {
        return formatDate(new Date(rowdate));
      } else {
        return "";
      }
    },
    // 分页部分
    //监听页码值的变化
    handleCurr(page) {
      this.loading=true;
      this.page.pageIndex = page;
      this.getList();
    },
    handleSize(val) {
      this.loading=true;
      this.page.pageSize = val;
      this.page.pageIndex = 1;
      this.getList();
    },
    //关键字标色
    changeColor(val) {
      let tags = this.page.str;
      if (tags !== null && tags !== "" && tags !== undefined && val !== null && val !== "" && val !== undefined) {
        let reg = new RegExp("(" + tags + ")", "g");
        return val.replace(reg, "<font style='color:red'>$1</font>");
      } else {
        return val;
      }
    },
    cgggclick(){
        this.cgshow = "color"
        this.zxshow = ""
        this.getList()
    },
    zxggclick(data){
        this.cgshow = ""
        this.zxshow = "color"
        this.getList()
    },
  },
};
</script>

<style  scoped>
.breadcrumb {
  height: 50px;
  display: flex;
  margin-bottom: 10px;
  padding: 0px 3rem;
  align-items: center;
  background: #0068d1;
}
.container {
  padding: 0 20%;
  height: auto;
  min-height: 572px;
}
.container .el-input {
  width: 40% !important;
}
.el-table {
  width: 100% !important;
  margin-top: 20px;
}
.item{
  height: 40px;
  line-height: 40px;
  color: #909399;
  padding: 0 15px;
  cursor:pointer;
  /* border: 1px solid#e4e7ed; */
  background-color: rgba(0, 0, 0, 0.1);
}
.color{
    height: 40px;
    line-height: 40px;
    padding: 0 15px;
    color: #fff;
    background-color: #006cff;
    border-right: 0px;
    border-bottom: 0px;
}
.btn[data-v-25a0bba5]{
  color: #66b1ff;
  border: 1px solid #66b1ff;

}
.btn:hover{
  color: #fff;
  border: 1px solid #66b1ff;
  background: #006cff;
} 
</style>