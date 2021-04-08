import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userRole: "",
    userInfo: {},
    Login: false,
    readonly: true,
    isbg: false,
    applyArg: {
      pid: "",
      sqyxid: "",
      visible: false,
    },
    ywxqyminformation: "",
    fjidList: [],
  },
  mutations: {
    setuserRole(state, payload) {
      state.userRole = payload.userRole;
    },
    changeApplyArg(state, payload) {
      debugger;
      state.applyArg = payload.applyArg;
    },

    changebg(state, payload) {
      state.isbg = payload.isbg;
      // Vue.set(state.isbg, "name3", "添加响应式属性");
    },
    changereadonly(state, payload) {
      state.readonly = payload.readonly;
      // Vue.set(state.isbg, "name3", "添加响应式属性");
    },
    changeywxqym(state, payload) {
      // console.log(payload);
      if (payload) {
        state.ywxqyminformation = payload;
      } else {
        state.ywxqyminformation = "";
      }
    },
    changefjidList(state, payload) {
      // console.log(payload);
      // console.log(state.fjidList);
      // debugger;
      if (payload) {
        // debugger;
        state.fjidList = [...state.fjidList, payload];
      } else {
        state.fjidList = [];
        // debugger;
      }
      // console.log(state.fjidList);
    },
  },
  actions: {},
  modules: {},
});
