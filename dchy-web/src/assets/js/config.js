//  引入vue
import Vue from "vue";
//  引入moment时间插件
import moment from "moment";
import "moment/locale/zh-cn";
// 引入封装后的axios
import http from "@/api/http.js";

export const requestConfig = {
  headers: {
    "Content-Type": "multipart/form-data",
  },
};
/**
 * @Description //各种基本的url
 * @Author maoZhan
 * @Date 2020/12/17 15:37
 * @param  * @param null
 * @return
 **/

export const callService = {
  // baseurl: "http://127.0.0.1:8888/", //相当于使用http.js的url地址，如果用到的话
  baseurl:'http://www.xadchy.cn/DchyService/',
  uploadFile: "/ftpService/fileUpload",
  deleteFile: "/ftpService/deleteFiles",
  listFile: "/ftpService/listFile2",
  downloadFile: "/ftpService/downloadfile", //普通文件下载
  createNoticeFile: "/ftpService/createNoticeFile", //生成对应的模板通知
  downloadTemplate: "/ftpService/downlaodTemplateFile", //模板文件的下载
  // smsService : "http://10.7.120.72:9527/alibaba/sendmessage",          //短信服务
  smsService: "/additionServices/sendmessage2", //短信服务
};
/**
 * @Description //附件配置
 * @Author maoZhan
 * @Date 2020/12/17 15:37
 * @param  * @param null
 * @return
 **/
let year = new Date().getFullYear();
let dchy = "DCHY";
let secondName = {
  xmtz: "项目通知",
  mbwj: "模板文件",
  htxx: "合同信息",
  cgxx: "成果信息",
  fjxx: "附件信息",
  ryxx: "人员信息",
  xxgl: "信息管理",
  zzxx: "资质信息",
};
export const templateName = {
  zxtzs: "中选通知书", //中选通知书
  zjjgcns: "中介机构承诺书", //中介机构承诺书
  // htmb: `西安市工程建设项目“多测合一”服务合同1120`,
  htmb: `西安市工程建设项目“多测合一”服务合同0112`,
  cgshjls: `成果审核结论书`,
};
export const attachmentUpload = {
  mbwj: `${dchy}/${secondName.mbwj}`,
  xmtz: `${dchy}/${year}/${secondName.xmtz}`,
  htxx: `${dchy}/${year}/${secondName.htxx}`,
  cgxx: `${dchy}/${year}/${secondName.cgxx}`,
  fjxx: `${dchy}/${year}/${secondName.fjxx}`,
  ryxx: `${dchy}/${year}/${secondName.ryxx}`,
  xxgl: `${dchy}/${year}/${secondName.xxgl}`,
  zzxx: `${dchy}/${year}/${secondName.zzxx}`,
};

moment.locale("zh-cn");
Vue.prototype.$moment = moment;
export default {
  attachmentUpload,
  callService,
};
