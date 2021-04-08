<template>
  <div class="xxfb" v-loading.fullscreen.lock="fullscreenLoading">
    <!-- 项目发布 -->
    <div class="jbxx">
      <el-form
        ref="form"
        :model="form"
        autocomplete="off"
        label-width="135px"
        :rules="rules"
        :disabled="form.disable"
      >
        <el-row>
          <!-- 申请单位( 测绘单位申请 ) -->
          <div class="chdwsq" v-if="navIndex === 1">
            <el-col :span="24">
              <div class="global-title">
                <span class="global-flag" style="color: #0091ff">申请单位</span>
                <el-button
                  v-if="xmzt != '已废标'"
                  type="default"
                  size="mini"
                  @click="finishPro"
                  >结束项目</el-button
                >
              </div>
            </el-col>

            <!-- 测绘单位申请 -->
            <el-col :span="24">
              <el-table
                border
                ref="multipleTable"
                :data="sqyxList"
                tooltip-effect="dark"
                style="width: 100%"
                :header-cell-style="{
                  background: '#0091ff',
                  'text-align': 'center',
                }"
                :cell-style="{ 'text-align': 'center' }"
              >
                <el-table-column label="单位名称">
                  <template slot-scope="scope" class="lxr">
                    <a
                      style="font-size: 16px; color: #1d94fc; cursor: pointer"
                      disable-transitions
                      @click="gotoqymlkdetail(scope.row)"
                      >{{ scope.row.UNIT_NAME }}</a
                    >
                  </template>
                </el-table-column>
                <el-table-column label="资质等级" prop="zzdj">
                  <template slot-scope="scope">{{
                    scope.row.ZZXXZZDJ
                  }}</template>
                </el-table-column>
                <el-table-column label="企业性质" prop="qyxz">
                  <template slot-scope="scope">{{
                    scope.row.UNIT_TYPE
                  }}</template>
                </el-table-column>
                <el-table-column label="项目报价/元" prop="xmbj">
                  <template slot-scope="scope">{{ scope.row.XMBJ }}</template>
                </el-table-column>
                <el-table-column label="服务质量指数" prop="xydj"
                  ><span style="color: red"> ★★★★★(5.0)</span></el-table-column
                >
                <el-table-column label="联系人" prop="lxr">
                  <template slot-scope="scope">{{ scope.row.LXR }}</template>
                </el-table-column>
                <el-table-column label="联系人电话" prop="lxrdh">
                  <template slot-scope="scope">{{ scope.row.LXRDH }}</template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <span v-if="xmzt == '已废标'" style="color: red"
                      >已废标</span
                    >
                    <el-button
                      v-if="xmzt != '已废标'"
                      :disabled="
                        listZT == '待办' || listZT == '' ? false : true
                      "
                      size="mini"
                      @click="qrProject(scope.row, $event)"
                      >选取</el-button
                    >
                    <!--<div>
                      <el-tooltip :content="scope.row.msg" >
                        <el-button :type="scope.row.buttonType" :disabled="false" @click="qrProject(scope.row, $event)" >同意</el-button>
                      </el-tooltip>
                    </div>-->
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </div>

          <!-- 采购确认 -->
          <div class="wtqr" v-if="false">
            <el-col :span="24">
              <div class="global-title">
                <span class="global-flag" style="color: #0091ff">申请单位</span>
              </div>
            </el-col>

            <el-col :span="24">
              <div class="global-sxtitle">
                <span
                  >恭喜，你申请的XXXX项目项目用地测量业务，已被采购方XXXX开发有限公司同意办理，请确认采购信息。</span
                >
              </div>
            </el-col>

            <el-col :span="24">
              <div class="global-right-btn">
                <el-button type="primary" @click="qrHtxx">确认</el-button>
                <el-button type="danger" @click="refuseWtqr">拒绝</el-button>
              </div>
            </el-col>
          </div>

          <!-- 合同上传 -->
          <div class="htsc" v-if="navIndex === 2" id="htxx">
            <!-- 下载中选通知书  -->
            <el-col :span="24">
              <div
                class="global-sxtitle-danger"
                style="color: red; font-weight: bold"
                v-if="czsm && czsm != ''"
              >
                <span>不通过原因：</span>
                <span>{{ czsm }}</span>
              </div>
              <div class="global-sxtitle">
                <span style="font-size: 16px"
                  >您所申请的{{ form.jbxxForm.wtdw
                  }}{{ form.jbxxForm.xmmc }}项目已被选中，点击下载</span
                >
                <span
                  style="font-size: 16px; color: #1d94fc; cursor: pointer"
                  @click="downloadZxtzs"
                  >中选通知书</span
                >。
              </div>
            </el-col>
            <el-col :span="24">
              <!--              合同表单信息-->
              <!--              测绘单位cchdwmc、签订日期qdrq、合同约定开始时间ydkssj、合同约定结束时间ydjssj、合同价款htjk、补充说明bcsm、测绘事项chsx、项目负责工程师xmfzgcs-->
              <el-col :span="24">
                <div class="global-title" @click="openorclose">
                  <span class="global-flag" style="color: #0091ff"
                    >合同信息</span
                  >
                  <i
                    class="el-icon-arrow-right"
                    v-if="navIndex !== 0 && isShow"
                  ></i>
                  <i
                    class="el-icon-arrow-down"
                    v-if="navIndex !== 0 && !isShow"
                  ></i>
                </div>
              </el-col>
              <div v-show="navIndex === 0 || !isShow" class="selfDefinedStyle">
                <el-col :span="12">
                  <el-form-item label="测绘单位">
                    <el-input v-model="form.htxxForm.chdwmc" disabled />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="签订日期" prop="htxxForm.qdrq">
                    <el-date-picker
                      v-model="form.htxxForm.qdrq"
                      :picker-options="qdrqLimit"
                      :popper-append-to-body="true"
                      type="date"
                      :disabled="this.listZT == '已办' ? true : false"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="合同约定开始时间" prop="htxxForm.ydkssj">
                    <el-date-picker
                      v-model="form.htxxForm.ydkssj"
                      :picker-options="htydkssjLimit"
                      :popper-append-to-body="true"
                      type="date"
                      @change="checkYdkssj"
                      :disabled="this.listZT == '已办' ? true : false"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="合同约定结束时间" prop="htxxForm.ydjssj">
                    <el-date-picker
                      v-model="form.htxxForm.ydjssj"
                      :picker-options="htydjssjLimit"
                      :popper-append-to-body="true"
                      type="date"
                      @change="checkYdjssj"
                      :disabled="this.listZT == '已办' ? true : false"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <!-- <el-col :span="12">
                <el-form-item label="竞价类型" prop="htxxForm.wtdw">
                  <el-input v-model="form.htxxForm.jjlx" />
                </el-form-item>
                </el-col> -->
                <el-col :span="12">
                  <el-form-item label="项目编号" prop="htxxForm.htbh">
                    <el-input v-model="form.htxxForm.htbh" disabled />
                  </el-form-item>
                </el-col>
                <!--<el-col :span="12">
                  <el-form-item label="首次结款时间" prop="htxxForm.scjksj">
                    <el-date-picker
                      v-model="form.htxxForm.scjksj"
                      :picker-options="scjksjLimit"
                      type="date"
                      :disabled="this.listZT == '已办' ? true : false"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="首次结款金额/元" prop="htxxForm.scjkje">
                    <el-input
                      v-model.number="form.htxxForm.scjkje"
                      :disabled="this.listZT == '已办' ? true : false"
                    />
                  </el-form-item>
                </el-col>-->
                <el-col :span="12">
                  <el-form-item label="合同价款/元" prop="htxxForm.htjk">
                    <el-input
                      v-model.number="form.htxxForm.htjk"
                      :disabled="this.listZT == '已办' ? true : false"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="补充说明" prop="htxxForm.bcsm">
                    <el-input
                      type="textarea"
                      v-model="form.htxxForm.bcsm"
                      rows="4"
                      resize="none"
                      :disabled="this.listZT == '已办' ? true : false"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <!--                测绘阶段和键值相等时form.jbxxForm.chjd ===-->
                  <el-form-item label="测量事项" prop="jbxxForm.fwnr">
                    <el-input type="textarea" v-model="form.chsxs" disabled/>
                    <!--<template v-for="item in chsxs">
                      <el-input :value="item.processAlias">
                      </el-input>
                    </template>-->
                    <!--<el-select
                      :popper-append-to-body="false"
                      v-model="form.htxxForm.chsx"
                      multiple
                      placeholder="请选择测量事项"
                      :disabled="this.listZT == '已办' ? true : true"
                    >
                      <template
                        v-for="(item1, ind1) in processList"
                        v-if="item1.key === form.jbxxForm.chjd"
                      >
                        <el-option
                          v-for="item2 in item1.value"
                          :key="item2.value"
                          :label="item2.label"
                          :value="item2.value"
                        ></el-option>
                      </template>
                    </el-select>-->
                  </el-form-item>
                </el-col>

                <el-col :span="24">
                  <div class="global-sxtitle">
                    <span>温馨提示：请上传盖章后的合同扫描件及其附件。</span>
                  </div>
                </el-col>
                <el-col :span="24">
                  <Upload
                    v-if="htpathid"
                    :download="{
                      jbxx: form.jbxxForm,
                      htxx: form.htxxForm,
                      LOGDQHJ: dqhj,
                      CLSX: form.jbxxForm.chjd,
                      id: htpathid,
                      rootpath: this.htrootpath,
                    }"
                    ref="htfj"
                  ></Upload>
                </el-col>
                <div class="htsc-title" v-if="listZT == '待办' || listZT == ''">
                  <el-col :span="24">
                    <div class="global-right-btn">
                      <el-button
                        type="primary"
                        @click="
                          operHtxx(
                            form.htxxForm.id && form.htxxForm.id !== ''
                              ? '编辑'
                              : '保存'
                          )
                        "
                        >保存</el-button
                      >
                      <el-button
                        type="primary"
                        :disabled="htxxBtn"
                        @click="
                          operHtxx(
                            form.htxxForm.id && form.htxxForm.id === ''
                              ? '立即上传'
                              : '上传'
                          )
                        "
                        >提交</el-button
                      >
                    </div>
                  </el-col>
                </div>
              </div>
            </el-col>
          </div>

          <!-- 合同确认 -->
          <div class="htqr" v-if="navIndex === 3">
            <el-col :span="24">
              <div class="global-sxtitle">
                <span>温馨提示：请确认合同信息。</span>
              </div>
            </el-col>

            <el-col :span="12">
              <el-form-item label="测绘单位" prop="wtdw">
                <el-input v-model="form.htxxForm.chdwmc" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="签订日期" prop="wtdw">
                <el-date-picker
                  disabled
                  v-model="form.htxxForm.qdrq"
                  type="date"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="合同约定开始时间" prop="wtdw">
                <el-date-picker
                  disabled
                  v-model="form.htxxForm.ydkssj"
                  type="date"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="合同约定结束时间" prop="wtdw">
                <el-date-picker
                  disabled
                  v-model="form.htxxForm.ydjssj"
                  type="date"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <!-- <el-col :span="12">
            <el-form-item label="竞价类型" prop="wtdw">
              <el-input v-model="htxxForm.jjlx" />
            </el-form-item>
            </el-col> -->
            <el-col :span="12">
              <el-form-item label="项目编号" prop="wtdw">
                <el-input v-model="form.htxxForm.htbh" disabled />
              </el-form-item>
            </el-col>
            <!-- <el-col :span="12">
              <el-form-item label="首次结款时间" prop="wtdw">
                <el-date-picker
                  v-model="form.htxxForm.scjksj"
                  disabled
                  type="date"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="首次结款金额/元" prop="wtdw">
                <el-input v-model.number="form.htxxForm.scjkje" disabled />
              </el-form-item>
            </el-col> -->
            <el-col :span="12">
              <el-form-item label="合同价款/元" prop="wtdw">
                <el-input v-model="form.htxxForm.htjk" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="补充说明" prop="bcsm">
                <el-input
                  disabled
                  type="textarea"
                  v-model="form.htxxForm.bcsm"
                  rows="4"
                  resize="none"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <!--                测绘阶段和键值相等时jbxxForm.chjd ===-->
              <el-form-item label="测量事项" prop="jbxxForm.fwnr">
                  <el-input type="textarea" v-model="form.chsxs" disabled/>
                  placeholder="请选择测量事项"
                >
                  <template
                    v-for="(item1, ind1) in processList"
                    v-if="item1.key === form.jbxxForm.chjd"
                  >
                    <el-option
                      v-for="item2 in item1.value"
                      :key="item2.value"
                      :label="item2.label"
                      :value="item2.value"
                    ></el-option>
                  </template>
                </el-select>-->
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <Upload
                :download="{
                  jbxx: form.jbxxForm,
                  htxx: form.htxxForm,
                  LOGDQHJ: '合同确认',
                  CLSX: form.jbxxForm.chjd,
                  id: htpathid,
                  rootpath: htrootpath,
                }"
                v-if="htpathid"
                ref="htfj"
              ></Upload>
            </el-col>

            <div class="htsc-title" v-if="listZT == '待办' || listZT == ''">
              <el-col :span="24">
                <div class="global-right-btn">
                  <el-button
                    type="danger"
                    :disabled="htxxBtn"
                    @click="toRevokeHtxx('合同退回', $event)"
                    >不通过</el-button
                  >
                  <el-button
                    type="primary"
                    :disabled="htxxBtn"
                    @click="operHtxx('合同确认')"
                    >确认</el-button
                  >
                </div>
              </el-col>
            </div>
          </div>
          <!-- 基本信息 -->
          <el-col :span="24">
            <div class="global-title" @click="openorclose2">
              <span class="global-flag" style="color: #0091ff">基本信息</span>
              <i
                class="el-icon-arrow-right"
                v-if="navIndex !== 0 && isShowJbxx"
              ></i>
              <i
                class="el-icon-arrow-down"
                v-if="navIndex !== 0 && !isShowJbxx"
              ></i>
            </div>
          </el-col>

          <div v-show="navIndex === 0 || !isShowJbxx" id="jbxx">
            <el-col :span="12">
              <el-form-item label="业主单位" prop="jbxxForm.wtdw">
                <el-input v-model="form.jbxxForm.wtdw" disabled />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="业主单位地址" prop="jbxxForm.wtdwdz">
                <el-input v-model="form.jbxxForm.wtdwdz" disabled />
                <!--disabled  暂时放开  因为有单位可能从政务网拿不到地址-->
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="联系人" prop="jbxxForm.yzdwlxr">
                <el-input
                  v-model="form.jbxxForm.yzdwlxr"
                  placeholder="请输入联系人姓名"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col>
            <!-- <el-input v-show="navIndex != 0" v-model="form.jbxxForm.yzdwlxr" /> -->
            <el-col :span="12">
              <el-form-item label="联系人电话" prop="jbxxForm.yzdwlxrdh">
                <el-input
                  v-model="form.jbxxForm.yzdwlxrdh"
                  placeholder="请输入联系电话"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col>
          </div>

          <!-- 业务信息 -->
          <el-col :span="24">
            <div class="global-title" @click="openorclose3">
              <span class="global-flag" style="color: #0091ff">业务信息</span>
              <i
                class="el-icon-arrow-right"
                v-if="navIndex !== 0 && isShowYwxx"
              ></i>
              <i
                class="el-icon-arrow-down"
                v-if="navIndex !== 0 && !isShowYwxx"
              ></i>
            </div>
          </el-col>

          <div v-show="navIndex === 0 || !isShowYwxx" id="ywxx" class="selfDefinedStyle">
            <el-col :span="12">
              <el-form-item label="项目名称" prop="jbxxForm.xmmc">
                <el-input
                  v-model="form.jbxxForm.xmmc"
                  placeholder="请输入项目名称"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="项目地址" prop="jbxxForm.xmdz">
                <el-input
                  v-model="form.jbxxForm.xmdz"
                  placeholder="请输入项目地址"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="测绘阶段" prop="jbxxForm.chjd">
                <el-select
                  :popper-append-to-body="false"
                  v-model="form.jbxxForm.chjd"
                  placeholder="请选择测绘阶段"
                  @change="chjdChange"
                  :disabled="navIndex === 0 ? false : true"
                >
                  <template v-for="item in processList">
                    <el-option  :label="item.processAlias" :value="item.id"></el-option>
                  </template>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="服务内容" prop="jbxxForm.fwnr">
                <el-input type="textarea" v-model="form.chsxs" disabled />
                <!--<el-select
                  :popper-append-to-body="false"
                  v-model="form.jbxxForm.fwnr"
                  multiple
                  placeholder="请选择服务内容"
                  :disabled="navIndex === 0 ? true : true"
                >
                  <template
                    v-for="(item1, ind1) in chsxs"
                  >
                    <el-option
                      :key="item1.id"
                      :label="item1.processName"
                      :value="item1.processAlias"
                    ></el-option>
                  </template>
                </el-select>-->
              </el-form-item>
            </el-col>
            <!-- <el-col :span="12">
              <el-form-item label="地籍编号" prop="jbxxForm.djbh">
                <el-input v-model="form.jbxxForm.djbh" placeholder="请输入地籍编号" :disabled=" navIndex === 0 ? false : true"/>
              </el-form-item>
            </el-col> -->

            <el-col :span="12">
              <el-form-item label="预算金额/元" prop="jbxxForm.ysje">
                <el-input
                  v-model="form.jbxxForm.ysje"
                  placeholder="请输入预算金额（最多保留两位小数）"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col>

            <!-- <el-col :span="12">
              <el-form-item label="资金来源" prop="jbxxForm.zjly">
                <el-input
                  v-model="form.jbxxForm.zjly"
                  placeholder="请输入资金来源"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col> -->

            <el-col :span="12">
              <el-form-item label="项目所属行政区" prop="jbxxForm.xmssxzq">
                <el-select
                  :popper-append-to-body="false"
                  v-model="form.jbxxForm.xmssxzq"
                  placeholder="请选择项目所属行政区"
                  :disabled="navIndex === 0 ? false : true"
                >
                  <el-option label="新城区" value="新城区"></el-option>
                  <el-option label="碑林区" value="碑林区"></el-option>
                  <el-option label="莲湖区" value="莲湖区"></el-option>
                  <el-option label="雁塔区" value="雁塔区"></el-option>
                  <el-option label="未央区" value="未央区"></el-option>
                  <el-option label="灞桥区" value="灞桥区"></el-option>
                  <el-option label="长安区" value="长安区"></el-option>
                  <el-option label="临潼区" value="临潼区"></el-option>
                  <el-option label="阎良区" value="阎良区"></el-option>
                  <el-option label="高陵区" value="高陵区"></el-option>
                  <el-option label="鄠邑区" value="鄠邑区"></el-option>
                  <el-option label="蓝田县" value="蓝田县"></el-option>
                  <el-option label="周至县" value="周至县"></el-option>
                  <el-option label="高新区" value="高新区"></el-option>
                  <el-option
                    label="经济技术开发区"
                    value="经济技术开发区"
                  ></el-option>
                  <el-option label="曲江新区" value="曲江新区"></el-option>
                  <el-option label="浐灞生态区" value="浐灞生态区"></el-option>
                  <el-option label="航天基地" value="航天基地"></el-option>
                  <el-option label="航空基地" value="航空基地"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="最低资质等级" prop="jbxxForm.zzdj">
                <el-select
                  :popper-append-to-body="false"
                  v-model="form.jbxxForm.zzdj"
                  placeholder="请选择资质等级"
                  :disabled="navIndex === 0 ? false : true"
                >
                  <el-option label="甲级" value="甲级"></el-option>
                  <el-option label="乙级" value="乙级"></el-option>
                  <el-option label="丙级" value="丙级"></el-option>
                  <el-option label="丁级" value="丁级"></el-option>
                  <el-option label="不限" value="不限"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="报名开始时间" prop="jbxxForm.bmkssj">
                <el-date-picker
                  v-model="form.jbxxForm.bmkssj"
                  placeholder="请选择报名开始时间"
                  type="datetime"
                  @change="kssjChange"
                  :picker-options="pickerStartOptions"
                  :disabled="navIndex === 0 ? false : true"
                  format="yyyy-MM-dd HH:mm"
                  @focus="bmksclick"
                ></el-date-picker>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="报名结束时间" prop="jbxxForm.bmjssj">
                <el-date-picker
                  v-model="form.jbxxForm.bmjssj"
                  placeholder="根据报名开始时间自动计算"
                  type="datetime"
                  :picker-options="pickerEndOptions"
                  :disabled="navIndex === 0 ? false : true"
                  format="yyyy-MM-dd HH:mm"
                ></el-date-picker>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="完成时限/天" prop="jbxxForm.xmwcsx">
                <el-input
                  v-model="form.jbxxForm.xmwcsx"
                  placeholder="请输入完成时限"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="附加条件" prop="jbxxForm.fjtj">
                <el-input
                  type="textarea"
                  v-model="form.jbxxForm.fjtj"
                  placeholder="对采购服务的其他条件要求"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="备注" prop="jbxxForm.bz">
                <el-input
                  type="textarea"
                  placeholder="添加备注信息"
                  v-model="form.jbxxForm.bz"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col>

            <!-- <el-col :span="24">
              <el-form-item label="立项依据" prop="jbxxForm.lxyj">
                <el-input
                  type="textarea"
                  placeholder="请输入立项依据"
                  v-model="form.jbxxForm.lxyj"
                  :rows="6"
                  :disabled="navIndex === 0 ? false : true"
                />
              </el-form-item>
            </el-col> -->
          </div>
        </el-row>
      </el-form>
    </div>
    <el-col :span="24" v-if="navIndex === 0">
      <div class="global-right-btn" v-if="listZT == '待办' || listZT == ''">
        <el-button
          type="primary"
          @click="operProject(pid === '' ? '保存' : '编辑', flag)"
        >
          保存
        </el-button>
        <el-button
          type="primary"
          @click="operProject(pid === '' ? '立即发布' : '发布', flag)"
          :disabled="flag ? true : false"
        >
          立即发布
        </el-button>
      </div>
    </el-col>
    <el-dialog title="不通过意见" :visible.sync="revokeDiag.visib" width="30%">
      <el-input
        type="textarea"
        :rows="4"
        placeholder="请输入内容"
        v-model="revokeDiag.czsm"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="revokeDiag.visib = false">取 消</el-button>
        <el-button type="primary" @click="operHtxx('合同退回')"
          >确 定</el-button
        >
      </span>
    </el-dialog>

    <el-dialog
      title="废标原因"
      :visible.sync="finishProvisib.visib"
      width="30%"
    >
      <el-input
        type="textarea"
        :rows="4"
        placeholder="请输入内容"
        v-model="finishProvisib.reason"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="finishProvisib.visib = false">取 消</el-button>
        <el-button type="primary" @click="qrfinishPro()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  guid,
  convertCurrency,
  downloadFile,
  closePage,
  doMessage,
  parseFwnr,
} from "@/assets/js/common.js";
import {
  attachmentUpload,
  templateName,
  requestConfig,
  callService,
} from "@/assets/js/config.js";
import Upload from "@/components/Upload.vue";
var validFun = (rule, value, callback) => {
  if (value) {
    callback(new Error("申报数量不能大于计划数量减去已申报数量的差"));
  } else {
    callback();
  }
};
const rules = {
  jbxxForm: {
    wtdw: [{ required: true, message: "不能为空", trigger: "blur" }],
    wtdwdz: [{ required: true, message: "不能为空", trigger: "blur" }],
    yzdwlxr: [{ required: true, message: "不能为空", trigger: "blur" }],
    yzdwlxrdh: [
      { required: true, message: "不能为空", trigger: "blur" },
      { pattern: /^1[3456789]\d{9}$/, message: "请输入正确的手机号" },
    ],
    xmmc: [{ required: true, message: "不能为空", trigger: "blur" }],
    xmdz: [{ required: true, message: "不能为空", trigger: "blur" }],
    chjd: [{ required: true, message: "不能为空", trigger: "change" }],
    xmssxzq: [{ required: true, message: "不能为空", trigger: "blur" }],

    ysje: [
      { required: true, message: "不能为空" },
      {
        pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
        message: "请输入正确的数值",
      },
    ],
    zjly: [{ required: true, message: "不能为空", trigger: "blur" }],
    fwnr: [{ required: true, message: "不能为空", trigger: "blur" }],
    zzdj: [{ required: true, message: "不能为空", trigger: "change" }],
    xmwcsx: [
      { required: true, message: "不能为空", trigger: "blur" },
      { pattern: /^[1-9][0-9]*$/, message: "请输入正确的天数" },
    ],
    bmkssj: [
      { required: true, message: "不能为空", trigger: ["blur", "change"] },
    ],
    bmjssj: [
      { required: true, message: "不能为空", trigger: ["blur", "change"] },
    ],
    lxyj: [{ required: true, message: "不能为空", trigger: "blur" }],
  },
  // htxx部分
  htxxForm: {
    qdrq: [
      { required: true, message: "不能为空", trigger: ["blur", "change"] },
    ],
    ydkssj: [
      {
        required: true,
        message: "请输入约定开始时间",
        trigger: ["blur", "change"],
      },
    ],
    //得先有约定开始时间
    ydjssj: [
      {
        required: true,
        message: "请输入约定结束时间",
        trigger: ["blur", "change"],
      },
    ],
    //得先有约定结束时间和约定结束时间
    scjksj: [
      {
        required: false,
        message: "请先输入约定开始时间和约定结束时间",
        trigger: ["blur", "change"],
      },
    ],
    scjkje: [
      {
        required: false,
        message: "请输入首次结款金额",
        trigger: "blur",
      },
      {
        type: "number",
        message: "金额必须为数字值",
      },
    ],
    htjk: [
      {
        required: true,
        message: "请输入合同价款",
        trigger: "blur",
      },
      {
        type: "number",
        message: "金额必须为数字值",
      },
    ],
    chsx: [
      {
        required: true,
        message: "请输入测量事项",
        trigger: ["blur", "change"],
      },
    ],
    htbh:[
      {
        required: true,
        message: "请输入项目编号",
        trigger: ["blur"],
      },
    ],
    // bcsm: [{
    //   required: true,
    //   message: "请输入补充说明",
    //   trigger: "blur"
    // }],
  },
};
export default {
  data() {
    return {
      flag: false,
      bmsqje: "", // 报名申请金额
      rules: rules,
      userinfo: "",
      isShow: false,
      isShowYwxx: false,
      isShowJbxx: false,
      isShowFjxx: false,
      fullscreenLoading: false,
      zxtz: {
        zxtzs: "", //中选通知书
      },
      form: {
        disable: false,
        // 项目主表信息对象
        jbxxForm: {
          id: "",
          wtdw: "",
          wtdwdz: "",
          wtdwid: "",
          yzdwlxr: "",
          yzdwlxrdh: "",
          xmmc: "",
          xmdz: "",
          sqzsbs: "",
          djbh: "",
          ysje: "",
          zjly: "",
          chjd: "",
          zzdj: "",
          yqxydj: "",
          jyfs: "",
          fwnr: "",
          xmwcsx: "",
          bmkssj: new Date(),
          bmjssj: new Date().getTime() + 3 * 24 * 3600 * 1000,
          fjtj: "",
          bz: "",
          lxyj: "",
          chdw: "",
          xmssxzq: "",
          dchybh: "",
        },
        htxxForm: {
          id: null,
          qdrq: null, //签订日期
          ydkssj: null, //约定开始时间
          ydjssj: null, //约定结束时间
          htjk: "", //合同价款
          xmfzgcs: null, //项目负责工程师
          chdwmc: null, //测绘单位名称
          bcsm: null, //补充说明
          chsx: null, //测绘事项
          wtdwmc: null, //采购单位名称
          pid: null, //pid
          wtdwid: null, //采购单位id
          chdwid: null, //测绘单位id
          htwj: null, //合同文件
          jjlx: null, //竞价类型
          htbh: null, //合同编号
          scjksj: null, //首次结款时间
          scjkje: "", //首次结款金额
        },
        chsxs:'',
      },
      pid: this.$route.query.pid,
      fsrid: this.$route.query.fsrid,
      fsrxm: this.$route.query.fsrxm,
      lastLogId: this.$route.query.lastLogId,
      htxxId: this.$route.query.htxxId,
      dqhj: this.$route.query.dqhj,
      sqyxList: [],
      revokeDiag: {
        visib: false,
        data: null,
        czsm: "",
      },
      finishProvisib: {
        visib: false,
        reason: "",
      },
      processList:[],
      chsxs:[],
      // 测绘事项列表
      // processList: [
      //   {
      //     key: "项目用地测量",
      //     value: [
      //       {
      //         //项目用地测量
      //         id: 1,
      //         label: "1：500地形图测绘",
      //         value: "1：500地形图测绘",
      //       },
      //       {
      //         id: 2,
      //         label: "土地勘测定界",
      //         value: "土地勘测定界",
      //       },
      //       {
      //         id: 3,
      //         label: "权籍调查",
      //         value: "权籍调查",
      //       },
      //       {
      //         id: 3,
      //         label: "规划用地定点测量",
      //         value: "规划用地定点测量",
      //       },
      //     ],
      //   },
      //   {
      //     key: "工程实施测量",
      //     value: [
      //       {
      //         //工程实施测量
      //         id: 1,
      //         label: "建（构）筑物的定线测量",
      //         value: "建（构）筑物的定线测量",
      //       },
      //       {
      //         id: 2,
      //         label: "建（构）筑物的验线测量",
      //         value: "建（构）筑物的验线测量",
      //       },
      //       {
      //         id: 3,
      //         label: "道路、地下管线、管网、管沟的定线测量",
      //         value: "道路、地下管线、管网、管沟的定线测量",
      //       },
      //       {
      //         id: 4,
      //         label: "道路、地下管线、管网、管沟的验线测量",
      //         value: "道路、地下管线、管网、管沟的验线测量",
      //       },
      //       {
      //         id: 5,
      //         label: "房屋面积预测",
      //         value: "房屋面积预测",
      //       },
      //     ],
      //   },
      //   {
      //     key: "竣工及不动产登记测量",
      //     value: [
      //       {
      //         //竣工及不动产登记测量
      //         id: 1,
      //         label: "规划竣工测量",
      //         value: "规划竣工测量",
      //       },
      //       {
      //         id: 2,
      //         label: "权籍调查",
      //         value: "权籍调查",
      //       },
      //       {
      //         id: 3,
      //         label: "庭院内管线及附属设施测量",
      //         value: "庭院内管线及附属设施测量",
      //       },
      //     ],
      //   },
      // ],
      // 合信息表单对象
      htxxBtn: false,   //是否禁用合同按钮
      czsm: "",         //拒绝原因
      stage: "测绘成果",
      fileList: [
        {
          name: "测绘成果",
          children: [],
          uid: 1,
        },
      ],
      // 列表状态   待办或已办
      listZT: "",
      xmzt: "",
      fbyy: "",
      //合同rootpath
      htrootpath: "",
      htpathid: "",

      wtsj: "", //委托时间
      //结束日期限制
      pickerEndOptions: {
        disabledDate: (time) => {
          let beginTime = this.form.jbxxForm.bmkssj;
          if (beginTime) {
            // 报名开始时间不能大于报名结束时间
            return time.getTime() < new Date(beginTime).getTime();
          } else {
            return time.getTime() < new Date().getTime() + 8.64e7;
          }
        },
      },
      //开始时间限制
      pickerStartOptions: {
        disabledDate: (time) => {
          let beginTime = this.form.jbxxForm.bmkssj;
          let five = new Date(beginTime).getTime() + 5 * 24 * 3600 * 1000;
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
      // 签订日期限制
      qdrqLimit: {
        disabledDate: (time) => {
          // 签订日期大于委托确认时间，将委托确认之前的时间禁用
          return time.getTime() < this.wtsj - 8.64e7;
        },
      },
      // 约定开始时间限制
      htydkssjLimit: {
        disabledDate: (time) => {
          // 开始时间>签订日期——签订日期前禁用
          let time2 = null;
          if (this.form.htxxForm.qdrq != null) {
            time2 = new Date(this.form.htxxForm.qdrq);
          } else {
            time2 = this.wtsj;
          }
          return time.getTime() < time2;
        },
      },
      // 约定结束时间
      htydjssjLimit: {
        disabledDate: (time) => {
          // 约定结束时间自动算出 = 约定开始时间 + 办理时限     如果手动调整的话，就是>约定开始时间 <约定开始时间 + 办理时限
          let fir = new Date(this.form.htxxForm.ydkssj);
          let xmwcsx = parseInt(this.form.jbxxForm.xmwcsx);
          let sec = new Date(this.form.htxxForm.ydkssj);
          sec.setDate(fir.getDate() + xmwcsx);
          return time.getTime() < fir || time.getTime()>sec;
          // return time.getTime() < fir || time.getTime() > sec;
          // if (time.getTime()<fir){
          //   return  false;
          // }
        },
      },
      // 首次结款时间限制
      scjksjLimit: {
        disabledDate: (time) => {
          // 首次结款时间 - 在开始结束之内，外部禁用
          let ydkssj = new Date(this.form.htxxForm.ydkssj);
          let ydjssj = new Date(this.form.htxxForm.ydjssj);
          return time.getTime() < ydkssj || time.getTime() > ydjssj;
        },
      },
    };
  },
  components: {
    Upload,
  },
  props: {
    navIndex: {
      type: Number,
    },
  },
  created() {
    if (window.localStorage.getItem("listZT")) {
      this.listZT = window.localStorage.getItem("listZT");
    }
    if (window.localStorage.getItem("userinfo")) {
      this.userinfo = JSON.parse(window.localStorage.getItem("userinfo"));
    }
    if (window.localStorage.getItem("processList")) {
      this.processList = JSON.parse(window.localStorage.getItem("processList"));
    }
    this.getDetail();
    this.getSyxList();

    // this.getsqyx();
  },
  mounted() {},
  methods: {
    checkYdjssj(val) {
      this.form.htxxForm.ydjssj = val;
      /*if (val){
        this.form.htxxForm.ydjssj = val;
      }else{
        this.form.htxxForm.ydjssj = '';
      }*/
    },
    checkYdkssj(val) {
        this.form.htxxForm.ydkssj = val;
        let js = new Date(this.form.htxxForm.ydkssj).getDate() + parseInt(this.form.jbxxForm.xmwcsx);
        this.form.htxxForm.ydjssj = new Date(this.form.htxxForm.ydkssj).setDate(js);
    },
    // 提前看值是否有
   /* prepareCheck(event) {
      /*合同结束时间，检测是否有开始时间
      首次结款时间，检测手否有开始和结束
      let el = event.currentTarget;
      el.innerText;
      switch (el.innerText) {
        case "首次开始时间":
          break;
        case "首次结款时间":
          if (this.form.htxxForm.ydkssj == null) {
            doMessage("请先输入约定开始时间", false);
          }
          break;
        default:
          break;
      }
    },*/
    // 合同回退
    toRevokeHtxx(val, event) {
      event.stopPropagation();
      this.revokeDiag.visib = true;
      this.revokeDiag.data = val;
    },
    // 申请单位详情页i
    gotoqymlkdetail(val) {
      this.$router.push({
        name: "qymlkdetail",
        query: { did: val.CHDWID },
      });
    },
    chjdChange() {

      // 默认选中合同的测量事项
      for (let item of this.processList) {
          this.chsxs = [];
          this.form.jbxxForm.fwnr = [];
        if (item.id === this.form.jbxxForm.chjd) {
          for (let item2 of item.children) {
            this.form.jbxxForm.fwnr.push(item2.id);
            // this.form.htxxForm.chsx.push(item2.id);
            this.chsxs.push(item2);
          }
          break;
        }
      }
      this.form.chsxs = '';
      _.each(this.chsxs,(item,ind)=>{
        if (ind!=this.chsxs.length-1){
          this.form.chsxs+=(item.processAlias+'，');
        }else{
          this.form.chsxs+=(item.processAlias);
        }
      })
      /*for (let item of this.processList) {
        if (item.processAlias === this.form.jbxxForm.chjd) {
          this.form.jbxxForm.fwnr = [];
          for (let item2 of item.children) {
            this.form.jbxxForm.fwnr.push(item2.processAlias);
          }
          break;
        }
      }*/
    },
    getSyxList() {
      if (this.pid) {
        let param = {
          pageIndex: 1,
          pageSize: 100,
          id: this.pid,
          str: "",
        };
        let userinfo = this.userinfo;
        this.$http
          .post("xmwt/dchySqyx/getSqyxListByFbxxid", param)
          .then((res) => {
            if (res.data.data) {
              ;
              let resData = res.data.data;
              if (resData.length > 0) {
                for (let i = 0; i < resData.length; i++) {
                  if (resData[i].WTQR !== "M") {
                    resData.splice(i, 1);
                    i--;
                  }
                }
              }
              /*for (let item of resData) {
                if (item.WTQR === "M") {
                  if (item.WTSJ != null) {
                    this.wtsj = new Date(item.WTSJ);
                  }
                  let msg = "";
                  item.buttonType = "primary";
                  item.msg = "点击确认申请";
                } else {
                  resData.splice(resData.indexOf(item), 1);
                }
                // 判断是否可以确认，公示期结束才可确认
                /*if(new Date(item.bmkssj)>new Date()){
                  msg = '您已经申请过该项目，不可以重复申请';
                  item.buttonType = 'info';
                  item.msg = msg;
                }else{
                  item.buttonType = 'primary';
                  item.msg = '点击确认申请';
                }
              }*/
              this.sqyxList = resData;
            }
          });
      }
    },
    finishPro() {
      this.finishProvisib.visib = true;
    },
    qrfinishPro() {
      this.finishProvisib.visib = false;
      let param = {
        xmzt: "已废标",
        fbyy: this.finishProvisib.reason,
        id: this.pid,
      };
      // 修改项目状态为废标
      this.$http.post("xmwt/dchyFbxx/renewFbxx", param).then((res) => {
        if (res.data.data == "1") {
          doMessage("废标成功！");
        } else {
          doMessage("废标失败，请联系管理员！", false);
        }
      });
    },
    openorclose() {
      this.isShow = !this.isShow;
    },
    openorclose2() {
      this.isShowJbxx = !this.isShowJbxx;
    },
    openorclose3() {
      this.isShowYwxx = !this.isShowYwxx;
    },
    // 是否展开
    isOpen() {
      this.isShow = false;
    },
    isOpenYwxx() {
      this.isShowYwxx = true;
    },
    isOpenFjxx() {
      this.isShowFjxx = false;
    },
    // 是否关闭
    isClose() {
      this.isShow = true;
    },
    isCloseYwxx() {
      this.isShowYwxx = false;
    },
    isCloseFjxx() {
      this.isShowFjxx = true;
    },
    // 表单必填项验证
    testForm(formVal) {
      if (!formVal.yzdwlxr) {
        return false;
      }
      if (!/^[1][3,4,5,7,8][0-9]{9}$/.test(formVal.yzdwlxrdh)) {
        return false;
      }
      if (!formVal.wtdwdz) {
        return false;
      }
      if (!formVal.xmmc) {
        return false;
      }
      if (!formVal.xmdz) {
        return false;
      }
      if (!formVal.chjd) {
        return false;
      }
      if (!formVal.ysje) {
        return false;
      }
      /*if (!formVal.zjly) {
        return false;
      }*/
      if (!formVal.xmssxzq) {
        return false;
      }
      if (!formVal.zzdj) {
        return false;
      }
      if (formVal.fwnr.length == 0) {
        return false;
      }
      if (!formVal.xmwcsx) {
        return false;
      }
      if (!formVal.bmkssj) {
        return false;
      }
      if (!formVal.bmjssj) {
        return false;
      }
      /*if (!formVal.lxyj) {
        return false;
      }*/
      return true;
    },
    testHtForm(formVal) {
      let fjList = JSON.parse(window.localStorage.getItem("fjList"));
      if (fjList[0].children && fjList[0].children.length == 0) {
        // 判断是否已上传了附件
        return "请上传附件";
      }
      if (!formVal.qdrq) {
        //签订日期
        return false;
      }
      if (!formVal.ydkssj) {
        //约定开始时间
        return false;
      }
      if (!formVal.ydjssj) {
        //约定结束时间
        return false;
      }
      // if (
      //   !/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(
      //     formVal.scjkje
      //   )
      // ) {
      //   return "首次结款金额请输入正整数或保留两位小数";
      // }
      if (
        !/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(
          formVal.htjk
        )
      ) {
        return "合同价款请输入正整数或保留两位小数";
      }
      // if (formVal.scjkje > formVal.htjk) {
      //   return "首次结款金额不能大于合同价款";
      // }

      /*if (!formVal.chsx) {
        //测绘事项
        return false;
      }*/
      if (!formVal.htwj) {
        //合同文件
        return false;
      }
      // if (!formVal.jjlx) {
      //   //竞价类型
      //   return false;
      // }
      if (!formVal.htbh) {
        //合同编号
        return false;
      }
      // if (!formVal.scjksj) {
      //   //首次结款时间
      //   return false;
      // }

      return true;
    },
    // 操作件
    operProject(arg, flag) {

      this.form.jbxxForm.cjr = this.userinfo.dwmc;
      this.form.jbxxForm.cjrid = this.userinfo.id;
      let dwdz = this.userinfo.qydz;
      if (!dwdz) {
        // 如果没有地址信息更新地址信息
        let company = {
          tyshxydm: this.userinfo.tyshxydm,
          qydz: this.form.jbxxForm.wtdwdz,
        };
        this.$http.post("updateCompany", company).then((res) => {});
      }
      let param = {
        log: {
          fsrxm: this.userinfo.dwmc,
          fsrid: this.userinfo.id,
          jsrxm: this.userinfo.dwmc,
          jsrid: this.userinfo.id,
          pid: this.pid,
          dqhj: "项目发布",
          clzt: "待办",
          ywlx: "信息发布",
        },
        fbxx: this.form.jbxxForm,
        lastLogId: this.lastLogId,
        handle: arg,
      };
      switch (arg) {
        case "保存":
          param.log.dqhj = "项目发布";
          break;
        case "发布":
          if (!this.testForm(param.fbxx)) {
            this.$message({
              message: "请填写必填信息",
              type: "warning",
              offset: 200,
            });
            return;
          }
          param.log.dqhj = "采购确认";
          break;
        case "立即发布":
          if (!this.testForm(param.fbxx)) {
            this.$message({
              message: "请填写必填信息",
              type: "warning",
              offset: 200,
            });
            return;
          }
          param.log.dqhj = "项目发布";
          break;
        case "编辑":
          break;
      }

      if (!(arg.indexOf("发布") == -1)) {
        //发布
        this.$confirm("确认发布？该操作不可撤销！", "提示", {
          confirmButtonText: "确认",
          cancelButtonText: "取消",
          type: "warning",
        }).then(async () => {
          this.$http.post("xmwt/dchyFbxx/operFbxx", param).then((res) => {
            let data = res.data.data;
            if (data) {
              if (data.fbxx.id != "") {
                this.form.jbxxForm.id = data.fbxx.id;
                this.pid = data.fbxx.id;
              }
              doMessage(arg + "成功！", true);
              this.flag = true;
              this.$router.go(-1);
            } else {
              doMessage(arg + "失败!", false);
            }
          });
        });
      } else {
        //保存修改不弹确认框
        this.$http.post("xmwt/dchyFbxx/operFbxx", param).then((res) => {
          let data = res.data.data;
          if (data) {
            if (data.fbxx && data.fbxx.id != "") {
              this.form.jbxxForm.id = data.fbxx.id;
              this.pid = data.fbxx.id;
            }
            if (data.log && data.log.id != "") {
              this.lastLogId = data.log.id;
            }
            this.$message({
              message: "保存成功",
              offset: 200,
              type: "success",
            });
          } else {
            doMessage(arg + "失败!", false);
          }
        });
      }
      // 开始操作
    },
    getsqyx() {
      let param = {
        chdwid: this.userinfo.id,
        pid: this.pid,
        pageSize: 10,
        pageNum: 1,
      };
      this.$http.post("xmwt/dchySqyx/getSqyxList", param).then((res) => {
        if (res.data.data[0].xmbj) {
          this.bmsqje = res.data.data[0].xmbj;
        } else {
          this.bmsqje = 0;
        }
      });
    },
    downloadZxtzs(event) {
      let el = event.currentTarget;

      // 查一下当前的zxtzsid
      let formData = new FormData();
      formData.append("folderid", this.form.htxxForm.zxtzsid);
      this.$confirm(`是否下载${el.innerText}?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$http
          .post("ftpService/listFile2", formData, requestConfig)
          .then((res) => {
            if (res.data) {
              let _data = { ...res.data };

              // 下载中选通知书，前提是已上传了
              var url = `${callService.downloadFile}`;
              var params = {};
              if (_data.dchyFjRecords && _data.dchyFjRecords.length > 0) {
                params.filename = _data.dchyFjRecords[0].filepath;
              } else {
                params.filename = `${_data.path}/${templateName.zxtzs}.pdf`;
              }
              downloadFile(url, params);
            } else {
              doMessage(`下载${el.innerText}失败！，请检查文件路径`, false);
            }
          });
      });
    },
    //  合同上传
    operHtxx(arg) {
      // 充实合同信息
      this.form.htxxForm.wtdwid = this.fsrid;
      this.form.htxxForm.wtdwmc = this.fsrxm;
      this.form.htxxForm.chdwid = this.userinfo.id;

      this.form.htxxForm.htwj = this.htpathid;
      let param = {
        log: {
          fsrxm: this.userinfo.dwmc, //测绘单位
          fsrid: this.userinfo.id,
          jsrxm: this.fsrxm, //采购单位，从那块获取
          jsrid: this.fsrid,
          pid: this.pid,
          dqhj: "合同确认",
          clzt: "待办",
          ywlx: "信息发布",
        },
        htxx: this.form.htxxForm,
        lastLogId: this.lastLogId, //上条日志id
        handle: arg,
      };

      let val = "";
      switch (arg) {
        case "保存":
          // 充实合同信息
          param.htxx.wtdwid = this.fsrid;
          param.htxx.wtdwmc = this.fsrxm;
          param.htxx.chdwid = this.userinfo.id;
          param.htxx.pid = this.pid;
          break;
        case "上传":
          val = this.testHtForm(param.htxx);
          if (val == false || typeof val == "string") {
            this.$message({
              message: val == false ? "请填写必填信息" : val,
              type: "warning",
              offset: 200,
            });
            return;
          }
          param.htxx.pid = this.pid;
          param.log.dqhj = "合同上传";
          param.log.fsrxm = this.userinfo.dwmc; //测绘单位
          param.log.fsrid = this.userinfo.id;
          param.log.jsrxm = this.fsrxm; //采购单位，从那块获取
          param.log.jsrid = this.fsrid;
          break;
        case "立即上传":
          val = this.testHtForm(param.htxx);
          if (val == false || typeof val == "string") {
            this.$message({
              message: val == false ? "请填写必填信息" : "请上传附件",
              type: "warning",
              offset: 200,
            });
            return;
          }
          param.htxx.wtdwid = this.fsrid;
          param.htxx.wtdwmc = this.fsrxm;
          param.htxx.chdwid = this.userinfo.id;
          param.htxx.pid = this.pid;

          param.log.fsrxm = this.userinfo.dwmc; //测绘单位
          param.log.fsrid = this.userinfo.id;
          param.log.jsrxm = this.fsrxm; //采购单位，从那块获取
          param.log.jsrid = this.fsrid;
          param.log.dqhj = "合同确认";
          break;
        case "合同确认":
          param.htxx.sfqr = "Y";
          param.log.dqhj = "合同已签订";
          param.log.fsrxm = this.userinfo.dwmc;
          param.log.fsrid = this.userinfo.id;
          param.log.jsrxm = this.fsrxm;
          param.log.jsrid = this.fsrid;
          param.htxx.pid = this.pid;
          param.htxx.chdwid = this.fsrid;
          param.htxx.chdwmc = this.fsrxm;
          param.htxx.wtdwid = this.userinfo.id;
          param.htxx.wtdwmc = this.userinfo.dwmc;
          break;
        case "合同退回":
          param.htxx.sfqr = "X";
          param.htxx.pid = this.pid;
          param.htxx.chdwid = this.fsrid;
          param.htxx.chdwmc = this.fsrxm;
          param.htxx.wtdwid = this.userinfo.id;
          param.htxx.wtdwmc = this.userinfo.dwmc;

          param.log.dqhj = "合同上传";
          param.log.fsrxm = this.userinfo.dwmc;
          param.log.fsrid = this.userinfo.id;
          param.log.jsrxm = this.fsrxm;
          param.log.jsrid = this.fsrid;
          param.log.czsm = this.revokeDiag.czsm; //退回原因

          break;
        case "编辑":
          break;
      }
      if (this.revokeDiag.visib) {
        this.revokeDiag.visib = false;
      }
      if (!(arg === "保存" || arg === "编辑" || arg === "合同退回")) {
        //发送
        this.$confirm(`确认进行${arg}操作？该操作不可撤销！`, "提示", {
          confirmButtonText: "确认",
          cancelButtonText: "取消",
          type: "warning",
        }).then(async () => {
          this.$http.post("xmwt/dchyHtxx/saveHtxx", param).then(async (res) => {
            if (res.data.data) {
              await this.$refs.htfj.uploadFun();
              await this.$refs.htfj.delall();
              //  上传禁用
              this.htxxBtn = true;
              doMessage(arg + "成功！", true);
              this.$router.go(-1);
            } else {
              doMessage(arg + "失败！", false);
            }
          });
        });
      } else {
        //保存修改
        this.$http.post("xmwt/dchyHtxx/saveHtxx", param).then(async (res) => {
          if (res.data.data) {
            await this.$refs.htfj.uploadFun();
            await this.$refs.htfj.delall();
            if (arg === "合同退回") {
              doMessage("合同不通过成功", true);
              this.$router.go(-1);
            }
            // 保存编辑
            return this.$message({
              message: "保存成功",
              type: "success",
              duration: 2000,
              offset: 200,
            });
          } else {
            return doMessage(arg + "失败！", false);
          }
        });
      }
    },
    //  拒绝
    refuseWtqr() {},
    //  上传文件
    beforeUpload(file) {},
    // 采购确认
    qrProject(val, event) {
      let el = event.currentTarget;
      // 资格判定   公示期结束才可以点确定
      let msg = "";
      if (new Date(this.form.jbxxForm.bmjssj).getTime() > new Date()) {
        msg = "请在报名时间结束后进行采购确认";
      }
      if (msg !== "") {
        return doMessage(msg, false);
      }
      this.$confirm(
        "确认选中测绘机构并发送中选通知书？该操作不可撤销！",
        "提示",
        {
          confirmButtonText: "确认",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(async () => {
          this.fullscreenLoading = true;
          let param = {
            log: {
              fsrid: this.userinfo.id,
              fsrxm: this.userinfo.dwmc, //当前登陆人
              jsrxm: val.UNIT_NAME,
              jsrid: val.CHDWID, //申请单位id
              dqhj: "合同上传", //环节是合同上传
              clzt: "待办",
              ywlx: "信息发布",
              pid: this.pid,
            },
            sqyx: {
              id: val.SQYXID, //这条申请的id
              wtqr: "MY", //确认采购
            },
            lastLogId: this.lastLogId, //上条日志id
          };
          // 更新意向表为确认
          return this.$http.post("xmwt/dchySqyx/updateSqyx", param);
        })
        .then(async (res) => {
          this.form.htxxForm.htwj = "";
          this.form.htxxForm.htbh = this.form.jbxxForm.dchybh;
          this.form.htxxForm.chdwid = val.CHDWID;
          this.form.htxxForm.chdwmc = val.UNIT_NAME;
          this.form.htxxForm.wtdwid = this.userinfo.id;
          this.form.htxxForm.wtdwmc = this.userinfo.dwmc;
          this.form.htxxForm.pid = this.pid;

          let param = {
            log: {
              jsrxm: val.UNIT_NAME, //测绘单位
              jsrid: val.CHDWID,
              fsrxm: this.userinfo.dwmc, //采购单位，从那块获取
              fsrid: this.userinfo.id,
              pid: this.pid,
              dqhj: "合同上传",
              clzt: "待办",
              ywlx: "信息发布",
            },
            htxx: this.form.htxxForm,
            lastLogId: this.lastLogId, //上条日志id
            handle: "保存",
          };
          // 生成合同表基本信息
          return await this.$http.post("xmwt/dchyHtxx/saveHtxx", param);
        })
        .then((res) => {
          if (res.data) {
            // 生成中选通知书
            let formdata = new FormData();
            formdata.append("downloadPath", `${attachmentUpload.mbwj}`);
            formdata.append("downloadName", `${templateName.zxtzs}.ftl`);
            formdata.append("uploadPath", `${attachmentUpload.xmtz}/${guid()}`);
            formdata.append("uploadName", `${templateName.zxtzs}.pdf`);
            formdata.append("userid", this.form.jbxxForm.wtdwid);
            formdata.append("username", this.form.jbxxForm.wtdw);
            formdata.append("pid", this.pid);
            formdata.append("withQrcode", false);
            formdata.append("downloadType", `合同`);
            return this.$http.post(
              `${callService.createNoticeFile}`,
              formdata,
              requestConfig
            );
          }
        })
        .then((res) => {
          // if (res.data) {  // 创建文件并没有返回值  所以注释本行，否则成功也会进失败的逻辑
          doMessage("选取成功！", true);
          // this.getSyxList();
          this.fullscreenLoading = false;
          this.$router.go(-1);
          // } else {
          //   doMessage("选取失败！", false);
          // }
        });
    },
    // 合同确认
    qrHtxx(event) {
      let el = event.currentTarget;
      let param = {
        log: {
          fsrid: this.userinfo.id,
          fsrxm: this.userinfo.dwmc, //当前登陆人
          // jsrxm: this.userinfo.id, //自己同意自己
          // jsrid: this.userinfo.dwmc,
          dqhj: "办结", //环节是合同上传
          clzt: "待办",
          ywlx: "信息发布",
          pid: this.pid,
        },
        lastLogId: this.lastLogId, //上条日志id
        htxx: {
          id: this.htxxId, //合同信息id
          sfqr: "Y",
        },
      };
      this.$http.post("xmwt/dchyHtxx/qrHtxx", param).then((res) => {
        if (res.data.data) {
          doMessage(el.innerText + "成功！", true);
        } else {
          doMessage(el.innerText + "失败！", false);
        }
      });
    },
    // 初始化获取项目数据
    async getDetail() {
      /*
       * 三部分，
       * 基本信息、如果有wrdwid就查，没有就缓存中取
       * fbxx、有就查没有就空
       * 合同信息
       * * */
      if (this.pid) {
        await this.$http
          .post("xmwt/dchyFbxx/getFbxx", { id: this.pid })
          .then(async (res) => {
            let formData = res.data.data;
            if (res.data.data) {
              this.xmzt = formData.xmzt;
              this.fbyy = formData.fbyy;
              //    格式转换
              debugger
              let x= 0;
              if (formData.fwnr != null){
                let fwnrids = JSON.parse(formData.fwnr);
                this.chsxs = await parseFwnr(fwnrids,this.processList);
                _.each(this.chsxs,(item,ind)=>{
                  if (ind!=this.chsxs.length-1){
                    this.form.chsxs+=(item.processAlias+'，');
                  }else{
                    this.form.chsxs+=(item.processAlias);
                  }
                })
                // alert(this.chsxs[0].processAlias)
              }
              if (formData.yzdwlxrdh != null)
                formData.yzdwlxrdh = parseInt(formData.yzdwlxrdh);
              if (formData.ysje != null)
                formData.ysje = parseInt(formData.ysje);
              if (this.dqhj === "项目发布") formData.dchybh = "";
              if (formData.xmwcsx != null)
                formData.xmwcsx = parseInt(formData.xmwcsx);
              // 合同编号赋值
              if (formData.dchybh != "") formData.dchybh = formData.dchybh;
              this.form.jbxxForm = formData;
              if (this.listZT == "已办" || this.listZT == "") {
                this.$refs.form.disabled = true;
              }
              await this.$http
                .get("user/findCompanybyId?id=" + formData.wtdwid)
                .then((res) => {
                  if (res.data) {
                    this.form.jbxxForm.wtdw = "";
                    this.form.jbxxForm.wtdwdz = res.data.qydz;
                    this.form.jbxxForm.wtdw = res.data.dwmc;
                    this.form.jbxxForm.wtdwid = res.data.id;
                  }
                });
            }
          });
      } else {
        // 第一次进来，基本信息赋值
        // alert('qydz'+this.userinfo.qydz);
        this.form.jbxxForm.wtdw = this.userinfo.dwmc;
        this.form.jbxxForm.wtdwid = this.userinfo.id;
        this.form.jbxxForm.wtdwdz = this.userinfo.qydz;
      }

      if (this.pid) {
        // 初始化合同信息
        await this.$http
          .get("xmwt/dchyHtxx/getByPid?pid=" + this.pid)
          .then(async (res) => {

            if (res.data) {
              let _data = res.data;
              let _chsx = []; //自动给出的测绘事项
              let _formData = new FormData();
              let url = "";
              /*if (_data.chsx != null){      //已有
                let chsxids = JSON.parse(_data.chsx);
                this.chsxs = await parseFwnr(chsxids,this.processList);
                _.each(this.chsxs,(item,ind)=>{
                  if (ind!=this.chsxs.length-1){
                    this.form.chsxs+=(item.processAlias+'，');
                  }else{
                    this.form.chsxs+=(item.processAlias);
                  }
                })
              }else{                        //没有就初始化
                _data.chsx   = this.form.jbxxForm.fwnr;
                _.each(this.chsxs,(item,ind)=>{
                  if (ind!=this.chsxs.length-1){
                    this.form.chsxs+=(item.processAlias+'，');
                  }else{
                    this.form.chsxs+=(item.processAlias);
                  }
                })
              }*/
              /*
              for (let item of this.processList) {
                if (item.processName === this.form.jbxxForm.chjd) {
                  _chsx = [];
                  for (let item2 of item.children) {
                    _chsx.push(item2.processAlias);
                  }
                  break;
                }
              }*/
              if (_data) {
                // 有合同的话，初始化表单+附件
                _data.htjk = isNaN(parseInt(_data.htjk))
                  ? ""
                  : parseInt(_data.htjk); //有数字类型才转
                if (_data.htwj) {
                  _formData.append("folderid", _data.htwj);
                  url = callService.listFile;
                } else {
                  this.htrootpath = `${attachmentUpload.htxx}/${guid()}`;
                  // 没有合同，给默认，创建文件夹
                  _formData.append("filename", "");
                  _formData.append("filepath", this.htrootpath);
                  _formData.append("folderid", "");
                  _formData.append("parentid", "");
                  _formData.append("userid", this.userinfo.id);
                  _formData.append("username", this.userinfo.wtdw);
                  url = callService.uploadFile;
                }
              }
              debugger
              this.form.htxxForm = _data;
              // this.form.htxxForm.chsx = _chsx;
              return this.$http.post(url, _formData, requestConfig);
            }
          })
          .then((res) => {
            if (res.data) {
              if (res.config.url.indexOf(callService.uploadFile) != -1) {
                this.htpathid = res.data.data.id;
                this.form.htxxForm.htwj = this.htpathid;
                window.localStorage.setItem("xzfjid", this.form.htxxForm.htwj);
                // 将id更新入htwj字段
                let param = {
                  log: {
                    fsrxm: this.userinfo.dwmc, //测绘单位
                    fsrid: this.userinfo.id,
                    jsrxm: this.fsrxm, //采购单位，从那块获取
                    jsrid: this.fsrid,
                    pid: this.pid,
                    dqhj: "合同确认",
                    clzt: "待办",
                    ywlx: "信息发布",
                  },
                  htxx: this.form.htxxForm,
                  lastLogId: this.lastLogId, //上条日志id
                  handle: "编辑",
                };
                this.$http.post("xmwt/dchyHtxx/saveHtxx", param);
              } else {
                this.htpathid = res.data.id;
                this.htrootpath = res.data.path;
                this.form.htxxForm.htwj = this.htpathid;
                window.localStorage.setItem("xzfjid", this.form.htxxForm.htwj);
              }
            } else {
              // doMessage("",false);
            }
          });
      }
      // 初始化日志信息
      await this.$http
        .get(`dchy/dchyLog/findLogById?id=${this.lastLogId}`)
        .then((res) => {
          if (res.data) {
            if (res.data.czsm && res.data.czsm != null) {
              this.czsm = res.data.czsm;
            }
          }
        });
    },
    kssjChange(val) {
      var nowTime = new Date();

      if (val) {
        let threeDays = new Date(val).getTime() + 24 * 60 * 60 * 1000 * 3;
        this.form.jbxxForm.bmjssj = threeDays;
      } else {
        this.form.jbxxForm.bmjssj = "";
      }
    },
    bmksclick($event) {
      var data = document.getElementsByClassName("el-date-table");
    },
  },
 /* watch: {
    "form.htxxForm.ydkssj": {
      handler() {
        let js =new Date(this.form.htxxForm.ydkssj).getDate() +parseInt(this.form.jbxxForm.xmwcsx);
        this.form.htxxForm.ydjssj = new Date(this.form.htxxForm.ydkssj).setDate(js);
      },
    },
  },*/
};
</script>

<style scoped>
/* .anticon {
  padding-right: 10px;
} */
.xxfb {
  padding: 22px 0px;
}
.jbxx {
  width: 100%;
  height: 100%;
}
.el-textarea .el-textarea__inner {
  font-size: 14px;
  color : #606266;
  height: 85px;
}
.editable-cell {
  position: relative;
}

.editable-cell-input-wrapper,
.editable-cell-text-wrapper {
  padding-right: 24px;
}

.editable-cell-text-wrapper {
  padding: 5px 24px 5px 5px;
}

.editable-cell-icon,
.editable-cell-icon-check {
  position: absolute;
  right: 0;
  width: 20px;
  cursor: pointer;
}

.editable-cell-icon {
  line-height: 18px;
  display: none;
}

.editable-cell-icon-check {
  line-height: 28px;
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-cell-icon:hover,
.editable-cell-icon-check:hover {
  color: #108ee9;
}

.editable-add-btn {
  margin-bottom: 8px;
  left: 50%;
  position: absolute;
  top: 10px;
  transform: translate(-50%);
}
.img {
  width: 30px;
  padding-right: 10px;
}

hr {
  margin-top: 10px;
  margin-bottom: 20px;
}

.fjxx {
  margin-bottom: 25px;
}

.global-right-btn {
  margin-bottom: 25px;
}

.selfDefinedStyle >>> .el-textarea .el-textarea__inner {
  font-size: 14px;
  height: 85px;
  color : #606266;
}

.dwmc {
  color: #70c6ff;
  border-bottom: 1px solid #70c6ff;
}

.global-right-btn button:last-child {
  margin-right: 0 !important;
}

.upload-demo {
  color: red;
  font-weight: 500;
}

.el-select {
  width: 100%;
}
#clsx .el-select >>> .el-input {
  height: 95px;
}
#clsx .el-select >>> .el-input__inner {
  height: 95px;
}

#clsx .el-select >>> .el-input__inner::-webkit-input-placeholder {
  position: relative;
  top: -25px;
}
.el-select >>> .el-tag.el-tag--info {
  color: #606266;
}
</style>