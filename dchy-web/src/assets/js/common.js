//  引入vue
import Vue from "vue";
//  引入moment时间插件
import moment from "moment";
import "moment/locale/zh-cn";
import ElementUI from "element-ui";
import { Message } from "element-ui";
// 引入封装后的axios
import http from "@/api/http.js";
import {
  attachmentUpload,
  templateName,
  callService,
  requestConfig,
} from "@/assets/js/config.js";
function formatTen(num) {
  return num > 9 ? num + "" : "0" + num;
}
export var vilidateCode = "";
//  设置全局方法
export function formatDate(date) {
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  var hour = date.getHours();
  var minute = date.getMinutes();
  var second = date.getSeconds();
  return year + "-" + formatTen(month) + "-" + formatTen(day);
}
export function formatDate2(date) {
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  var hour = date.getHours();
  var minute = date.getMinutes();
  var second = date.getSeconds();
  return (
    year +
    "-" +
    formatTen(month) +
    "-" +
    formatTen(day) +
    " " +
    formatTen(hour) +
    ":" +
    formatTen(minute) +
    ":" +
    formatTen(second)
  );
}
// 表单提交下载
export function downloadFile(url, param) {
  var temp = document.createElement("form");
  temp.action = callService.baseurl + url;
  temp.method = "post";
  temp.style.display = "none";
  for (var x in param) {
    var opt = document.createElement("textarea");
    opt.name = x;
    opt.value = param[x];
    temp.appendChild(opt);
  }
  document.body.appendChild(temp);
  temp.submit();
  return temp;
}
// 将数字金额转为大写
export function convertCurrency(money) {
  //汉字的数字
  var cnNums = new Array(
    "零",
    "壹",
    "贰",
    "叁",
    "肆",
    "伍",
    "陆",
    "柒",
    "捌",
    "玖"
  );
  //基本单位
  var cnIntRadice = new Array("", "拾", "佰", "仟");
  //对应整数部分扩展单位
  var cnIntUnits = new Array("", "万", "亿", "兆");
  //对应小数部分单位
  var cnDecUnits = new Array("角", "分", "毫", "厘");
  //整数金额时后面跟的字符
  var cnInteger = "整";
  //整型完以后的单位
  var cnIntLast = "元";
  //最大处理的数字
  var maxNum = 999999999999999.9999;
  //金额整数部分
  var integerNum;
  //金额小数部分
  var decimalNum;
  //输出的中文金额字符串
  var chineseStr = "";
  //分离金额后用的数组，预定义
  var parts;
  if (money == "") {
    return "";
  }
  money = parseFloat(money);
  if (money >= maxNum) {
    //超出最大处理数字
    return "";
  }
  if (money == 0) {
    chineseStr = cnNums[0] + cnIntLast + cnInteger;
    return chineseStr;
  }
  //转换为字符串
  money = money.toString();
  if (money.indexOf(".") == -1) {
    integerNum = money;
    decimalNum = "";
  } else {
    parts = money.split(".");
    integerNum = parts[0];
    decimalNum = parts[1].substr(0, 4);
  }
  //获取整型部分转换
  if (parseInt(integerNum, 10) > 0) {
    var zeroCount = 0;
    var IntLen = integerNum.length;
    for (var i = 0; i < IntLen; i++) {
      var n = integerNum.substr(i, 1);
      var p = IntLen - i - 1;
      var q = p / 4;
      var m = p % 4;
      if (n == "0") {
        zeroCount++;
      } else {
        if (zeroCount > 0) {
          chineseStr += cnNums[0];
        }
        //归零
        zeroCount = 0;
        chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
      }
      if (m == 0 && zeroCount < 4) {
        chineseStr += cnIntUnits[q];
      }
    }
    chineseStr += cnIntLast;
  }
  //小数部分
  if (decimalNum != "") {
    var decLen = decimalNum.length;
    for (var i = 0; i < decLen; i++) {
      var n = decimalNum.substr(i, 1);
      if (n != "0") {
        chineseStr += cnNums[Number(n)] + cnDecUnits[i];
      }
    }
  }
  if (chineseStr == "") {
    chineseStr += cnNums[0] + cnIntLast + cnInteger;
  } else if (decimalNum == "") {
    chineseStr += cnInteger;
  }
  return chineseStr;
}
export function deepClone(source) {
  if (!source && typeof source !== "object") {
    throw new Error("error arguments", "deepClone");
  }
  const targetObj = source.constructor === Array ? [] : {};
  Object.keys(source).forEach((keys) => {
    if (source[keys] && typeof source[keys] === "object") {
      targetObj[keys] = deepClone(source[keys]);
    } else {
      targetObj[keys] = source[keys];
    }
  });
  return targetObj;
}
// 消息提示
export function doMessage(mess, success, isclose) {
  if (success) {
    Message({
      message: mess,
      type: "success",
      duration: 1500,
      offset: 200,
      onClose: () => {
        isclose = isclose && true; //默认不关闭页面
        isclose ? closePage() : "";
        // this.$router.back();
      },
    });
  } else {
    Message({
      message: mess,
      type: "warning",
      offset: 200,
    });
  }
}
export function closePage() {
  window.location.href = "about:blank";
  window.close();
}
export async function getAllProcess() {
  let arg = {
    pageSize: 10,
    pageIndex: 1,
    str: "",
  };
  arg = {};
  let aa = http.post("xmwt/dchyFbxx/getAllProcess", arg).then((res) => {
    console.log(res.data);
    debugger;
    //  构建二级结构
    if (res.data.data) {
      let _data = [...res.data.data];
      for (let i = 0; i < _data.length; i++) {
        let children = [];
        for (let j = 0; j < _data.length; j++) {
          // 相等，则算为子
          if (_data[j].pid === _data[i].id) {
            children.push(_data[j]);
            _data.splice(j, 1);
            j--;
          }
        }
        _data[i].children = children;
      }
      return _data;
    }
  });
  if (aa != null) {
    return aa;
  }
}
export async function parseFwnr(targetArr, sourceArr) {
  let res = [];
  for (let i = 0; i < targetArr.length; i++) {
    for (let j = 0; j < sourceArr.length; j++) {
      if (sourceArr[j].children.length > 0) {
        for (let p = 0; p < sourceArr[j].children.length; p++) {
          if (targetArr[i] === sourceArr[j].children[p].id) {
            res.push(sourceArr[j].children[p]);
          }
        }
      }
    }
  }
  return res;
}
// 设置随机uid
export function guid() {
  function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
  }
  return (
    S4() +
    S4() +
    "-" +
    S4() +
    "-" +
    S4() +
    "-" +
    S4() +
    "-" +
    S4() +
    S4() +
    S4()
  );
}
// 请求发送验证码    6位数字的短信
export function getValCode(arg) {
  let msg = "";
  let valcode = "";
  for (var i = 0; i < 6; i++) {
    valcode += Math.floor(Math.random() * 10);
  }
  msg = `验证码：${valcode}，您正在撤回“${arg[1]}”的报名，请勿向他人泄露您的验证码。`;
  sendMessage(arg[0], msg);
  // doMessage(msg,true);
  return valcode;
}
// 发送短信通知
export function sendMessage(phone, message) {
  let formdata = new FormData();
  formdata.append("telphone", phone);
  formdata.append("msg", message);
  http.post(callService.smsService, formdata, requestConfig).then((res) => {
    if (res.data) {
      doMessage("短信发送成功", true);
    } else {
      doMessage("短信发送失败，请联系系统管理员！", false);
    }
  });
}

// 根据文件id查询父文件夹path与父文件夹id
export async function uploadsearch(id) {
  let fileFormData = new FormData();
  let requestConfig = {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  };
  fileFormData.append("folderid", id);
  let result = await http.post(
    // "http://222.90.207.221/dchy/dchy/ftpService/listFile2",
    "ftpService/listFile2",
    fileFormData,
    requestConfig
  );
  debugger;
  return result;
}

// 根据生成的文件夹路径创建文件夹
export async function uploadcreate(path) {
  let fileFormData = new FormData();
  let requestConfig = {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  };
  fileFormData.append("filepath", path);
  let result = await http.post(
    "ftpService/fileUpload",
    fileFormData,
    requestConfig
  );
  return result;
}

moment.locale("zh-cn");
Vue.prototype.$moment = moment;
