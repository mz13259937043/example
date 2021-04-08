import Vue from "vue";
import VueRouter from "vue-router";

import oneMenu from "@/compontents/menus/one";
import twoMenu from "@/compontents/menus/two";

import oneoneMenu from "@/compontents/menus/one-one";
import onetwoMenu from "@/compontents/menus/one-two";
import twooneMenu from "@/compontents/menus/two-one";
import twotwoMenu from "@/compontents/menus/two-two";

import home from "@/compontents/resources/home";




Vue.use(VueRouter);

//解决路由跳转报错
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

/*const routes = [
  { path: "/", redirect: "./index" },
  {
    path: "/index",
    name: "index",
    component: () => import("@/views/index/index.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () => import("@/views/login/login.vue"),
  },
  {
    path: "/wtgg",
    name: "wtgg",
    meta: {
      name: "采购公告",
    },
    component: () => import("@/views/wtgg/wtgg.vue"),
  },
  {
    path: "/wtggdetail",
    name: "wtggdetail",
    meta: {
      name: "采购公告详情页",
    },
    component: () => import("@/views/wtgg/wtggdetail.vue"),
  },
  {
    path: "/zxgg",
    name: "zxgg",
    meta: {
      name: "中选公告",
    },
    component: () => import("@/views/zxgg/zxgg.vue"),
  },
  {
    path: "/zxggdetail",
    name: "zxggdetail",
    meta: {
      name: "中选公告详情页",
    },
    component: () => import("@/views/zxgg/zxggdetail.vue"),
  },
  {
    path: "/zcfg",
    name: "zcfg",
    meta: {
      name: "政策法规",
    },
    component: () => import("@/views/zcfg/zcfg.vue"),
  },
  {
    path: "/zcfgdetail",
    name: "zcfgdetail",
    meta: {
      name: "政策法规详情页",
    },
    component: () => import("@/views/zcfg/zcfgdetail.vue"),
  },
  {
    path: "/qymlk",
    name: "qymlk",
    meta: {
      name: "入驻机构",
    },
    component: () => import("@/views/qymlk/qymlk.vue"),
  },
  {
    path: "/qymlkdetail",
    name: "qymlkdetail",
    meta: {
      name: "入驻机构详情页",
    },
    component: () => import("@/views/qymlk/qymlkdetail.vue"),
  },
  {
    path: "/gggl",
    name: "gggl",
    meta: {
      name: "公告管理",
    },
    component: () => import("@/views/gggl/gggl.vue"),
  },
  {
    path: "/rygl",
    name: "rygl",
    meta: {
      name: "人员管理",
    },
    component: () => import("@/views/rygl/rygl.vue"),
  },
  {
    path: "/yhgl",
    name: "yhgl",
    meta: {
      name: "人员管理",
    },
    component: () => import("@/views/rygl/yhgl.vue"),
  },
  {
    path: "/bszn",
    name: "bszn",
    meta: {
      name: "办事指南",
    },
    component: () => import("@/views/bszn/bszn.vue"),
  },
  {
    path: "/ywbl",
    name: "ywbl",
    meta: {
      name: "委托测绘",
    },
    component: () => import("@/views/ywbl/ywbl.vue"),
  },
  {
    path: "/grzx",
    name: "grzx",
    meta: {
      name: "个人中心",
    },
    component: () => import("@/views/grzx/grzx.vue"),
  },
  {
    path: "/admin",
    name: "admin",
    meta: {
      name: "管理员登录",
    },
    component: () => import("@/views/grzx/admin/admin.vue"),
  },
  {
    path: "/xxgl",
    name: "xxgl",
    meta: {
      name: "信息管理",
    },
    component: () => import("@/views/grzx/xxgl/xxgl.vue"),
  },
  {
    path: "/xxbj",
    name: "xxbj",
    meta: {
      name: "信息编辑",
    },
    component: () => import("@/views/grzx/xxgl/xxbj.vue"),
  },
  {
    path: "/myyw",
    name: "myyw",
    meta: {
      name: "我的业务",
    },
    component: () => import("@/views/myyw/myyw.vue"),
  },
  {
    path: "/zzsh",
    name: "zzsh",
    meta: {
      name: "资质审核",
    },
    component: () => import("@/views/myyw/zzsh.vue"),
  },
  {
    path: "/jdcx",
    name: "jdcx",
    meta: {
      name: "资质审核进度",
    },
    component: () => import("@/views/myyw/jdcx.vue"),
  },
  {
    path: "/chdwzc",
    name: "chdwzc",
    meta: {
      name: "测绘单位注册详情页",
    },
    component: () => import("@/views/chdwzc/chdwzc.vue"),
  },
  {
    path: "/xmfb",
    name: "xmfb",
    meta: {
      name: "项目发布",
    },
    component: () => import("@/views/xmfb/xmfb.vue"),
    children: [
      {
        path: "/",
        redirect: "./xxfb",
      },
      {
        path: "xxfb",
        name: "xxfb",
        meta: {
          name: "项目发布",
        },
        component: () => import("@/views/xmfb/xmfbxq/xxfb"),
      },
    ],
  },
  {
    path: "/xmqzq",
    name: "xmqzq",
    meta: {
      name: "项目监管列表",
    },
    component: () => import("@/views/xmqzq/xmqzq.vue"),
  },
  {
    path: "/xmqzqdetail",
    name: "xmqzqdetail",
    meta: {
      name: "项目监管详情",
    },
    component: () => import("@/views/xmqzq/xmqzqdetail.vue"),
  },
  {
    path: "/htqzq",
    name: "htqzq",
    meta: {
      name: "项目管理列表",
    },
    component: () => import("@/views/htqzq/htqzq.vue"),
  },
  {
    path: "/htqzqdetail",
    name: "htqzqdetail",
    meta: {
      name: "项目管理详情",
    },
    component: () => import("@/views/htqzq/htqzqdetail.vue"),
  },
  {
    path: "/ywxqym",
    name: "ywxqym",
    meta: {
      name: "测绘业务详情页面",
    },
    component: () => import("@/views/ywxqym/ywxqym.vue"),
  },
  {
    path: "/chdwzcdetail",
    name: "chdwzcdetail",
    meta: {
      name: "测绘单位注册详情页",
    },
    component: () => import("@/views/myyw/chdwzcdetail.vue"),
  },
  {
    path: "/zzbg",
    name: "zzbg",
    meta: {
      name: "资质变更",
    },
    component: () => import("@/views/zzbg/zzbg.vue"),
  },
  {
    path: "/backdetail",
    name: "backdetail",
    meta: {
      name: "资质变更退回详情",
    },
    component: () => import("@/views/zzbg/backdetail.vue"),
  },
  {
    path: "/htqzq",
    name: "htqzq",
    meta: {
      name: "项目管理列表页",
    },
    component: () => import("@/views/htqzq/htqzq.vue"),
  },
  {
    path: "/htqzqdetail",
    name: "htqzqdetail",
    meta: {
      name: "项目管理详情页",
    },
    component: () => import("@/views/htqzq/htqzqdetail.vue"),
  },
  {
    path: "/xmqzq",
    name: "xmqzq",
    meta: {
      name: "项目监管列表页",
    },
    component: () => import("@/views/xmqzq/xmqzq.vue"),
  },
  {
    path: "/xmqzqdetail",
    name: "xmqzqdetail",
    meta: {
      name: "项目监管详情页",
    },
    component: () => import("@/views/xmqzq/xmqzqdetail.vue"),
  },
  {
    path: "/cgshym",
    name: "cgshym",
    meta: {
      name: "成果审核结论详情",
    },
    component: () => import("@/views/qrcodePages/cgshym.vue"),
  },
];*/


/*
  路由就是项目的跳转的模块
  第一项是默认调转地址
  先是首部导航栏，再是一级菜单下的二级菜单


 */
const routes = [
  { path: "/", redirect: "./home" },
  {
    path: "/home",
    name: "home",
    component: home,
  },{
    path: '/one',
    name: '一级菜单',
    component: oneMenu,
    children: [
      {
        path: '/one-one',
        name: '1-1',
        component: oneoneMenu,
      },
      {
        path: '/one-two',
        name: '1-2',
        component: onetwoMenu
      }
    ]
  },{
    path: '/two',
    name: '二级菜单',
    component: twoMenu,
    children: [
      {
        path: '/two-one',
        name: '2-1',
        component: twooneMenu,
      },
      {
        path: '/two-two',
        name: '2-2',
        component: twotwoMenu,
      }
    ]
  }
]
const router = new VueRouter({
  routes,
});

export default router;
