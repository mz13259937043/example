import axios from "axios";
// 服务器环境
// axios.defaults.baseURL = "http://www.xadchy.cn/DchyService/";
// axios.defaults.redirectURL = "http://www.xadchy.cn/";
// 本地测试环境
axios.defaults.baseURL = "http://127.0.0.1:8888/";
axios.defaults.redirectURL = "http://127.0.0.1:8080/";

// 跨域过程中允许携带凭证
axios.defaults.withCredentials = true;

/*
*
设置POST请求头，告诉服务器请求主题的数据格式，默认是把对象转化为JSON对象传递给后台

设置请求传递数据的格式
x-www-form-urlencoded  一般后台是这种格式，如果后台接收的是JOSN格式的，那就不用设置
*/
// axios.defaults.headers["Content-Type"] = "application/x-www-form-urlencoded";
// axios.defaults.transformRequest = (data) => qs.stringify(data);
//data就是请求主体的内容
export default axios;
