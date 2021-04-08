<template>
  <div class="apply">
    <el-dialog title="申请信息" :visible="$store.state.applyArg.visible" width="30%" :before-close="onQuit">
      <el-form
              ref="sqyxForm"
              :model="sqyxForm"
              autocomplete="off"
              :rules="rules"
              class="xxxform"
      >
        <el-row>
          <el-col :span="24">
            <el-form-item label="项目报价/元" prop="xmbj" label-width="100px">
              <el-input
                      type="xm"
                      v-model="sqyxForm.xmbj"
                      placeholder="请输入报价"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系人" prop="lxr" label-width="100px">
              <el-input
                      type="xm"
                      v-model="sqyxForm.lxr"
                      placeholder="请输入联系人"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="联系电话" prop="lxrdh" label-width="100px">
              <el-input
                      type="xm"
                      v-model="sqyxForm.lxrdh"
                      placeholder="请输入联系电话"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onQuit">取 消</el-button>
        <el-button type="primary" @click="clickTrue">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {doMessage} from "../assets/js/common";

var vilidateCode='';
        export default {
  //企业的报名信息和基本信息有关，关联项目pid，测绘单位id，
  data() {
    return {
      userinfo:'',
      pid:'',
      // 表单是否显示
      visible:false,
      // 表单基础信息
      sqyxForm: {
        id:'',
        chdwid: '', //测绘单位id
        dxxz: '', //是否定向选择
        pid: "", //主项目的id
        xmbj: '', //项目报价
        lxr: "", //联系人
        lxrdh: "" //联系人电话
      },
      //验证规则
      rules:{
        lxr: [{ required: true, message: "不能为空",trigger: "blur"}],
        lxrdh: [
          { required: true, message: "不能为空",trigger: "blur"},
          { pattern: /^1[3456789]\d{9}$/, message: "请输入正确的手机号" },
        ],
        xmbj: [
          { required: true, message: "不能为空",trigger: "blur" },
          {
            pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
            message: "请输入正确的数值",
          },
        ],
      },
      // 验证修改
      formUpdate: {
        formDataText1: '',
        formDataText2: '',
        isValid: this.$route.params.isValid
      },
    };
  },
  props:{
    propInfo:{
      pid:'',
      visible:'',
    }
  },
  methods: {

    onQuit(){
      debugger
      this.$store.commit("changeApplyArg",{applyArg:{pid:'',sqyxid:'',visible:false}})
      // this.$emit("child2par",true);
    },
    // 初始化申请意向表单信息
    initSqyx(){
      let param = {
        pid:this.pid,
        chdwid:this.userinfo.id,
      }
      // 用pid查到对应的
      this.$http.get(`xmwt/dchySqyx/selectOne/${this.sqyxForm.id}`).then((res) => {
        if(res.data){
          debugger
          this.sqyxForm = res.data;
          // 记录表单前
          this.formUpdate.formDataText1 = JSON.stringify({
            dataform: this.sqyxForm
          });
        }
      })
    },
    clickTrue(){
      debugger
      if (this.formUpdate.formDataText1&&this.formUpdate.formDataText1!=''){
        this.modifyApply();
      }else{
        this.submitApply();
      }
    },
    // 提交报名
    submitApply(){
      let _sqyxform = this.sqyxForm;
      // 验证表单
      let isSub = false;
      this.$refs["sqyxForm"].validate((valid) => {
        if (valid) {
          isSub = true;
        }
      });
      if (!isSub) {
        return doMessage("输入信息格式有误",false);
      }
      // 提交报名
      let param = {..._sqyxform};
      param.wtqr = 'M';
      this.$http.post("xmwt/dchySqyx/saveSqyx", param).then((res) => {
        if (res.data){
          doMessage("报名成功",true);
        }else{
          doMessage("报名失败",false);
        }
        this.onQuit();
      })
      // 提示成功，和数据回显

    },
    // 验证表单是否修改 , 退回退件时候需要调用 ,将原表单的值和新值流化对比
    validUpdate() {
      this.formUpdate.formDataText2 = JSON.stringify({
        dataform: this.sqyxForm
      });
      if (this.formUpdate.formDataText2 == this.formUpdate.formDataText1) {
        return false;
      } else {
        return true;
      }
    },
    // 修改报名
    modifyApply(){
      let _sqyxform = this.sqyxForm;
      // 是否修改，未修改就提示
      if(!this.validUpdate()){
        return doMessage("未修改不可提交！",false);
      }
      // 验证表单
      let isSub = false;
      this.$refs["sqyxForm"].validate((valid) => {
        if (valid) {
          isSub = true;
        }
      });
      if (!isSub){return; }
      let param = {..._sqyxform};
      param.id = null;    //id为空插入
      param.wtqr = 'M';
      // 提交修改
      this.$http.post("xmwt/dchySqyx/saveSqyx", param).then((res) => {
        if (res.data){
          doMessage("修改成功",true);
        }else{
          doMessage("修改失败",false);
        }
        this.onQuit();
      })
    },
    // 初始化表单
  },
  async created() {
    debugger
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = {...JSON.parse(window.localStorage.getItem("userinfo"))};
    }
    this.sqyxForm.id = this.$store.state.applyArg.sqyxid;
    this.sqyxForm.chdwid = this.userinfo.id;
    this.sqyxForm.pid = this.propInfo.pid;
    this.pid = this.propInfo.pid;
    this.visible = this.propInfo.visible;
    if (this.sqyxForm.id){
      await this.initSqyx();
    }
    console.log(this.propInfo);
  },
};
</script>

<style scoped>
</style>