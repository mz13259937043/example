<template>
  <div class="ywbl">
    <Header></Header>
    <NavBar></NavBar>
    <Bread :name="$route.meta.name"></Bread>
    <div class="container">
      <div class="quyuanjiao">
        <el-input placeholder="请输入内容" v-model="listparams.searchStr">
        </el-input>
        <el-button
          slot="append"
          @click="search()"
          class="btn"
          style="border-radius: 0"
          >搜索</el-button
        >
      </div>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="tableData"
        border
        stripe
        :header-cell-style="{ background: '#0091ff' }"
      >
        <el-table-column
          prop="XMMC"
          label="项目名称"
          width="230"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="CHJD"
          label="业务阶段"
          width="180"
          align="center"
        >
        </el-table-column>
        <el-table-column prop="HTCHDWMC" label="测绘单位" align="center">
        </el-table-column>
        <el-table-column
          prop="QDRQ"
          label="合同签订时间"
          width="110"
          align="center"
          :formatter="formatter"
        >
        </el-table-column>
        <el-table-column
          prop="YDKSSJ"
          label="约定开始时间"
          width="110"
          align="center"
          :formatter="formatter"
        >
        </el-table-column>
        <el-table-column
          prop="YDJSSJ"
          label="约定结束时间"
          width="110"
          align="center"
          :formatter="formatter"
        >
        </el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template slot-scope="scope">
            <el-button
              type="small"
              @click="handleEdit(scope.$index, scope.row)"
              class="blbtn"
              >办理</el-button
            >
            <el-button
              type="small"
              @click="handleFinish(scope.$index, scope.row)"
              class="blbtn"
              >办结</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-if="total > 9"
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-sizes="[10, 20]"
        :page-size="1"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
    <!-- 弹窗 -->
    <el-dialog
      title="请选择业务阶段"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-radio-group v-model="radio">
        <el-radio
          @change="radioChange"
          :label="item.value"
          v-for="(item, index) in radioList"
          :key="index"
          >{{ item.label }}</el-radio
        >
      </el-radio-group>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogqx()">取 消</el-button>
        <el-button type="primary" @click="dialogqd()">确 定</el-button>
      </span>
    </el-dialog>
    <div class="bszn" @click="bsznClick">办事<br />指南</div>
    <Dropdown :propInfo="{ name: '.ywbl', path: 'index' }"></Dropdown>
    <Bottom></Bottom>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Bottom from "@/components/Bottom.vue";
import NavBar from "@/components/NavBar.vue";
import Bread from "@/components/Bread.vue";
import Dropdown from "@/components/dropdown.vue";
import { formatDate } from "@/assets/js/common.js";
import { doMessage } from "../../assets/js/common";
const bsznItem = "多测合一总指南";
export default {
  data() {
    return {
      listparams: {
        wtdwid: "",
        pageNum: "1",
        pageSize: "10",
        searchStr: "",
      },
      total: 0,
      tableData: [],
      //弹窗
      dialogVisible: false,
      radio: "",
      //选择栏的list
      radioList: [],

      // 业务事项
      ywsx: "",
      //用户信息
      userinfo: {},

      // 委托测绘Item临时存储
      ywblItem: "",
      loading: true,
    };
  },
  components: {
    Header,
    Bottom,
    NavBar,
    Bread,
    Dropdown,
  },
  methods: {
    // 条件搜索
    search() {
      this.getList();
    },
    bsznClick() {
      this.$router.push({
        name: "zcfgdetail",
        query: { item: bsznItem }, //默认进入办事指南文档
      });
    },
    //格式化
    formatter(row, column) {
      var rowdate = row[column.property];

      if (rowdate) {
        return formatDate(new Date(rowdate));
      } else {
        return "";
      }
    },
    handleSizeChange(val) {
      // 每页多少条数据

      this.loading = true;
      this.listparams.pageSize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      this.loading = true;
      // 当前是第几页
      this.listparams.pageNum = val;
      this.getList();
    },
    //办理操作
    handleEdit(value1, value2) {
      this.dialogVisible = true;
      //这里临时保存委托测绘item
      this.ywblItem = value2;
      // 根据合同信息所签订的测量事项显示
      let chsxArray = value2.HTCHSX.replace("[", "")
        .replace("]", "")
        .split(",");
      this.radioList = [];
      for (let index = 0; index < chsxArray.length; index++) {
        if (chsxArray[index]) {
          const element = {
            value: index + 1,
            label: chsxArray[index].replace(/\"/g, ""),
          };
          this.radioList.push(element);
        }
      }
    },
    async handleFinish(value1, value2) {
      /*
      查一下该项目还是否有事项未办结
       */
      let param = {
        pid: value2.XXID,
        pageNum: 1,
        pageSize: 100,
      };
      let banjie = true;
      let msg = "";
      await this.$http.post("/dchy/dchyYwxx/getYwxxList", param).then((res) => {
        // 只要有没办完的都不允许办结
        if (res.data.data && res.data.data.length > 0) {
          for (let item of res.data.data) {
            if (item.zt !== "已完成") {
              banjie = false;
              msg = "该业务还有未办结的事项";
              break;
            }
          }
        } else if (res.data.data && res.data.data.length == 0) {
          banjie = false;
          msg = "该业务未进行委托测绘";
        }
      });
      if (!banjie) {
        return doMessage(msg, false);
      }
      // 办结操作
      this.$confirm("确认所有成果都已验收合格，并办结该事项?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          var fbxxid = value2.XXID;
          let param = {
            id: fbxxid,
            xmzt: "已办结",
          };
          this.$http.post("/xmwt/dchyFbxx/renewFbxx", param).then((res) => {
            if (res.data.data) {
              // 从结果中移除此项
              for (let index = 0; index < this.tableData.length; index++) {
                if (fbxxid == this.tableData[index].XXID) {
                  this.tableData.splice(index, 1);
                }
              }
              this.$message({
                message: "办结成功",
                type: "warning",
                offset: 200,
              });
            }
          });
        })
        .catch(() => {});
    },
    handleClose(done) {
      done();
    },
    // 单选框变化
    radioChange(val) {
      //临时保存业务事项

      this.ywsx = this.radioList[val - 1].label;
    },
    // dialog取消操作
    dialogqx() {
      this.dialogVisible = false;
    },
    //dialog确定
    dialogqd() {
      // 点击确定全局保存ywblItem
      var obj = JSON.stringify(this.ywblItem);
      window.localStorage.setItem("ywblItem", obj);
      window.localStorage.setItem("listZT", "委托测绘");
      // 全局保存ywsx
      if (this.ywsx) {
        window.localStorage.setItem("ywsx", this.ywsx);
        this.$router.push({
          name: "ywxqym",
        });
      } else {
        this.$message({
          message: "合同签订时未选择测量事项，请修改合同信息！",
          type: "warning",
          offset: 200,
        });
      }
    },
    getList() {
      this.$http
        .post("xmwt/dchyFbxx/findMyProByFbxx", this.listparams)
        .then((res) => {
          this.total = res.data.count;
          this.tableData = res.data.data;
          this.XXID = res.data.data.XXID;
          this.loading = false;
        });
    },
  },
  created() {
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));

      this.listparams.wtdwid = this.userinfo.id;
    }
    this.getList();
  },
};
</script>

<style  scoped>
.ywbl {
  height: 100vh;
  overflow-x: hidden;
}
.breadcrumb {
  height: 50px;
  display: flex;
  margin-bottom: 10px;
  padding: 0px 3rem;
  align-items: center;
  background: #0068d1;
}
.xmggmore {
  width: 900px;
  height: calc(100% - 323px);
  margin: 0 auto;
  /* border: 1px solid rgb(199, 196, 196); */
}

.chdwcx-inp {
  width: 40%;
}

.cz-btn {
  width: 100%;
  background-color: #f3f3f3;
  border-color: 0;
  border: 1px solid #626262;
  color: #000;
  outline: 0;
}

.cz-btn:hover {
  background-color: #f3f3f3;
}

.container {
  padding: 0 385px;
  margin-top: 20px;
  height: auto;
  min-height: 567px;
}

.el-input {
  width: 500px !important;
}
.el-table {
  width: 100% !important;

  margin-top: 20px;
}
.el-pagination {
  margin-bottom: 15px;
  display: flex;
  justify-content: center;
}
.el-radio-group {
  display: flex;
  flex-direction: column;
}
.el-radio {
  margin-top: 20px;
}
.el-radio-group :first-child {
  margin-top: 0px;
}
.blbtn {
  color: #66b1ff;
  border: 1px solid #66b1ff;
}
.blbtn:hover {
  color: #fff !important;
  background: #006cff !important;
}
/* .blbtn {
  display: inline;
  color: #fff !important;
  border: 1px solid #66b1ff !important;
  background: #66b1ff !important;
} */

.bszn {
  width: 50px;
  height: 50px;
  position: fixed;
  bottom: 270px;
  right: 170px;
  color: #fff;
  text-align: center;
  line-height: 25px;
  font-size: 16px;
  background: #66b1ff;
}
.bszn:hover {
  cursor: pointer;
}

.quyuanjiao >>> .el-input__inner {
  border-radius: 0px;
}
</style>