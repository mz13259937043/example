<template>
  <div class="xxgl">
    <header-top></header-top>
    <nav-bar></nav-bar>
    <!-- 导航栏头部 -->
    <div class="nav-title">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item>信息列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 信息管理 -->
    <div class="xxgl-content" >
      <el-form
        ref="xxglForm"
        :model="xxglForm"
        autocomplete="off"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item align="left">
              <el-input placeholder="请输入标题、事项" v-model="page.str" class="input" style="">
              </el-input>
            </el-form-item>

          </el-col>
          <el-col :span="2">
            <el-form-item align="left">
              <el-button  class="btn"  @click="init()" style="border-radius: 0px;">搜索</el-button>
            </el-form-item>
          </el-col>

          <!-- <el-col :span="6">
            <el-form-item label="信息类型" prop="type" label-width="80px">
              <el-select v-model="xxglForm.type" placeholder="请选择类型">
                <el-option label="政策法规" value="政策法规"></el-option>
                <el-option label="办事指南" value="办事指南"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="2">
            <el-form-item label="">
              <el-button type="primary" @click="init()"> 搜索 </el-button>
            </el-form-item>
          </el-col> -->
          
          <el-col :span="14">
            <el-form-item align="right">
              <el-button class="btn" @click="addNew()"> 新增 </el-button>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-table
             v-loading="loading"
             element-loading-text="拼命加载中"
             element-loading-spinner="el-icon-loading"
              border
              ref="multipleTable"
              :data="dataSource"
              tooltip-effect="dark"
              style="width: 100%"
              @row-click="openDetails"
              :header-cell-style="{ background: '#0091ff' }"
            >
              <el-table-column label="标题" prop="title" align="center" width="300"> </el-table-column>
              <el-table-column label="类型" prop="type" align="center" width="100"> </el-table-column>
              <el-table-column label="事项" prop="item" align="center"> </el-table-column>
              <el-table-column label="状态" prop="state" align="center" width="80"> </el-table-column>
              <!-- <el-table-column label="发布人" prop="username" align="center"> </el-table-column> -->
              <el-table-column label="更新时间" prop="updatetime" align="center"> </el-table-column>
              <el-table-column label="操作" align="center" width="210">
                <template slot-scope="scope">
                  <!-- <div class="czaction">
                    <a href="javascript:;" @click="clickFb(scope.row)" v-if="scope.row.state !== '发布'">发布</a>
                    <a href="javascript:;" @click="clickCh(scope.row)" v-if="scope.row.state !== '草稿'">撤回</a>
                    <a href="javascript:;" @click="addNew(scope.row)" v-if="scope.row.state !== '发布'">编辑</a>
                    <a href="javascript:;" @click="clickSc(scope.row)" v-if="scope.row.state !== '发布'">删除</a>
                  </div> -->
                <div @click.stop>
                  <el-button size="mini" class="btn" @click="clickFb(scope.row)" v-if="scope.row.state !== '发布'">发布</el-button>
<!--                  <el-button size="mini" class="btn" @click="uploadFile(scope.row)" v-if="scope.row.state !== '发布'">上传附件</el-button>-->
                  <el-button size="mini" class="btn" @click="clickCh(scope.row)" v-if="scope.row.state !== '草稿'">撤回</el-button>
                  <el-button size="mini" class="btn" @click="addNew(scope.row)" v-if="scope.row.state !== '发布'">编辑</el-button>
                  <el-button size="mini" class="btn" @click="clickSc(scope.row)" v-if="scope.row.state !== '发布'">删除</el-button>
                </div>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
                background
                v-if="paginationshow"
                @current-change="handleCurr"
                @size-change="handleSize"
                :current-page="page.pageNum"
                :page-size="page.pageSize"
                layout="total,sizes, prev, pager, next, jumper"
                :total="page.total"
                :page-sizes="[10,20]"
                style="margin-bottom:20px;text-align:center"
            ></el-pagination>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <bottom></bottom>
  </div>
</template>

<script>
import headerTop from "@/components/Header.vue";
import navBar from "@/components/NavBar.vue";
import bottom from "@/components/Bottom.vue";

const xxglForm = {
  title: "",
  type: ""
};
const dataSource = [
  {
    key: "0",
    title: "1",
    type: "文件1",
    state: "2",
    username: "32",
    updatetime: "3",
  },
  {
    key: "1",
    title: "1",
    type: "文件1",
    state: "2",
    username: "32",
    createtime: "3",
  },
];
export default {
  components: {
    headerTop,
    navBar,
    bottom,
  },
  data() {
    return {
      xxglForm,
      dataSource,
      paginationshow:false,
      //  分页组件
      page:{
          total:null,
          pageNum:1,
          pageSize:10,
          str:""
      },
       loading:true
    };
  },
  mounted() {
    this.init()
  },
  methods: {
    // 请求数据
    init() {
      let _this = this
      let params = {
        pageNum:this.page.pageNum,
        pageSize:this.page.pageSize,
        str:this.page.str
      };
      // if(_this.xxglForm&&_this.xxglForm.title){
      //   params.title = _this.xxglForm.title
      // }
      // if(_this.xxglForm&&_this.xxglForm.type){
      //   params.type = _this.xxglForm.type
      // }
      _this.$http.post('api/findPolicyList', params).then(res => {
        if(res.status === 200) {
          if(res.data.count>9){
            _this.paginationshow = true
          }else{
            _this.paginationshow = false
          }
          _this.dataSource = res.data.data
          _this.page.total = res.data.count;
        }
          _this.loading=false;

      })
    },
    // 上传附件
    uploadFile(){
      // 关于政策法规的附件部分，怎么操作
      /*
      * 上传，删除，展示，修改，还要保证提交才有效
      * 上传，检测是否已有该目录，无就创目录，有就向里加文件，
      * 删除，得有一个文件列表，点那个删除那个
      * 下载，点那个下载那个
      * 展示，文件列表，如何展示，进来会查一下该条数据，有附件会点击展开，
      * 详情页呢，是否也要展示该文件列表，这里方便多了，直接用哪个组件操作
      *  */
    },
    //  发布
    clickFb(record) {
      const _this = this
      _this.$http.get(`api/updateStateById?id=${record.id}&state=发布`).then(res => {
        if(res.status === 200) {
          _this.$message.success("发布成功")
          _this.init()

        }
      })
    },
    // 撤回
    clickCh(record) {
      this.$confirm('确认撤回?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const _this = this
      _this.$http.get(`api/updateStateById?id=${record.id}&state=草稿`).then(res => {
        if(res.status === 200) {
          _this.$message.success("撤回成功")
          _this.init()

        }
      })
          
        }).catch(() => {
       
        });

    
    },
    //  编辑
    clickBj() {
      
    },
    //  删除
    clickSc(record) {
       this.$confirm('确认删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
       const _this = this
      _this.$http.get(`api/updateStateById?id=${record.id}&state=删除`).then(res => {
        if(res.status === 200) {
          _this.$message.success("删除成功")
          _this.init()

        }
      })         
        }).catch(() => {
         
        });
    
    },
    openDetails(row) {
      const { href } = this.$router.resolve({
        name: "zcfgdetail",
        query: { id: row.id },
      });
      window.open(href, "_blank");
    },
    // 跳转页面
    addNew(record) {
      debugger
      if(record){
        const { href } = this.$router.resolve({
        name: "xxbj",
        query:record
        });
        window.open(href, "_blank");
      }else{
        const { href } = this.$router.resolve({
        name: "xxbj",
        });
        window.open(href, "_blank");
      }
    },
    // 分页
    //监听页码值的变化
      handleCurr(page) {
          this.loading=true;
          this.page.pageNum = page;
          this.init();
      },
      // 页数的改变
      handleSize(val) {
          this.loading=true;
          this.page.pageSize = val;
          this.page.pageNum = 1;
          this.init();
      },
  }
};
</script>

<style scoped>
.xxgl {
  width: 100%;
  height: 100%;
}
.xxgl-content>>>.el-input__inner {
  border-radius: 0;
}
.xxgl-content {
  padding: 0 20%;
  min-height: 572px;
  overflow: auto;
  height: auto;
}
.el-select,
.el-input {
  width: 100% ;
}
.czaction {
  display: flex;
  justify-content: center;
}
.czaction a {
  display: inline;
  margin: 0 6px;
}
</style>
