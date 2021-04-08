import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import {
  Pagination,
  Dialog,
  Input,
  Radio,
  RadioGroup,
  RadioButton,
  Checkbox,
  CheckboxGroup,
  Timeline,
  TimelineItem,
  Select,
  Option,
  OptionGroup,
  Button,
  ButtonGroup,
  Table,
  Image,
  TableColumn,
  DatePicker,
  Tooltip,
  Breadcrumb,
  BreadcrumbItem,
  Form,
  FormItem,
  Tag,
  Icon,
  Row,
  Col,
  Card,
  Upload,
  Cascader,
  Backtop,
  Collapse,
  CollapseItem,
  Loading,
  MessageBox,
  Message,
} from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
Vue.use(Pagination);
Vue.use(Dialog);
Vue.use(Input);
Vue.use(Radio);
Vue.use(RadioGroup);
Vue.use(RadioButton);
Vue.use(Checkbox);
Vue.use(CheckboxGroup);
Vue.use(Select);
Vue.use(Option);
Vue.use(OptionGroup);
Vue.use(Button);
Vue.use(ButtonGroup);
Vue.use(Loading);
Vue.use(Card);
Vue.use(Image);
Vue.use(Table);
Vue.use(TableColumn);
Vue.use(DatePicker);
Vue.use(Tooltip);
Vue.use(Breadcrumb);
Vue.use(BreadcrumbItem);
Vue.use(Form);
Vue.use(FormItem);
Vue.use(Tag);
Vue.use(Icon);
Vue.use(Row);
Vue.use(Col);
Vue.use(Upload);
Vue.use(Collapse);
Vue.use(CollapseItem);
Vue.use(Cascader);
Vue.use(Timeline);
Vue.use(TimelineItem);
Vue.use(Image);
Vue.use(Backtop);

import _ from "lodash";
//引入富文本编辑器
import VueQuillEditor from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
Vue.use(VueQuillEditor);
Vue.prototype._ = _;
Vue.prototype.$message = Message;
Vue.prototype.$confirm = MessageBox.confirm;
// 引入公共样式
import "@/assets/css/common.css";
// 引入公共方法
import "@/assets/js/common.js";
// 引入封装后的axios
import http from "@/api/http.js";
Vue.prototype.$http = http;
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
  mounted() {
    document.dispatchEvent(new Event("render-event"));
  },
}).$mount("#app");
