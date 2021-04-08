package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * (DchyHtxx)实体类
 *
 * @author makejava
 * @since 2020-10-27 16:44:32
 */
public class DchyHtxx implements Serializable {
    private static final long serialVersionUID = -93976810366865628L;

    private String id;
    /**
     * 测绘单位id
     */
    private String chdwid;
    /**
     * 签订日期
     */
    private Timestamp qdrq;
    /**
     * 约定开始时间
     */
    private Timestamp ydkssj;
    /**
     * 约定结束时间
     */
    private Timestamp ydjssj;
    /**
     * 合同价款
     */
    private String htjk;
    /**
     * 项目负责工程师
     */
    private String xmfzgcs;
    /**
     * 合同文件
     */
    private String htwj;
    /**
     * 是否已确认
     */
    private String sfqr;
    /**
     * 确认时间
     */
    private Timestamp qrsj;
    /**
     * 创建时间
     */
    private Timestamp cjsj;
    /**
     * 发布信息ID
     */
    private String pid;
    /**
     * 测绘单位名称
     */
    private String chdwmc;
    /**
     * 委托单位id
     */
    private String wtdwid;
    /**
     * 委托单位名称
     */
    private String wtdwmc;
    /**
     * 补充说明
     */
    private String bcsm;
    /**
     * 合同状态    已确认或只是保存
     */
    private String htzt;
    /**
     * 测绘事项    合同包含那几个小阶段的测绘内容
     */
    private String chsx;
    private String zxtzsid;

    private int pageNum;

    private int pageSize;

    private String searchStr;

    private List<DchyYwxx> ywxxList;
    private DchyFbxx fbxx;

    public String getZxtzsid() {
        return zxtzsid;
    }

    public void setZxtzsid(String zxtzsid) {
        this.zxtzsid = zxtzsid;
    }

    private String htbh;

    public String getJjlx() {
        return jjlx;
    }

    public void setJjlx(String jjlx) {
        this.jjlx = jjlx;
    }

    public Timestamp getScjksj() {
        return scjksj;
    }

    public void setScjksj(Timestamp scjksj) {
        this.scjksj = scjksj;
    }

    public String getScjkje() {
        return scjkje;
    }

    public void setScjkje(String scjkje) {
        this.scjkje = scjkje;
    }

    private String jjlx;
    private Timestamp scjksj;
    private String scjkje;

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public List<DchyYwxx> getYwxxList() {
        return ywxxList;
    }

    public void setYwxxList(List<DchyYwxx> ywxxList) {
        this.ywxxList = ywxxList;
    }

    public DchyFbxx getFbxx() {
        return fbxx;
    }

    public void setFbxx(DchyFbxx fbxx) {
        this.fbxx = fbxx;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChdwid() {
        return chdwid;
    }

    public void setChdwid(String chdwid) {
        this.chdwid = chdwid;
    }

    public Timestamp getQdrq() {
        return qdrq;
    }

    public void setQdrq(Timestamp qdrq) {
        this.qdrq = qdrq;
    }

    public Timestamp getYdkssj() {
        return ydkssj;
    }

    public void setYdkssj(Timestamp ydkssj) {
        this.ydkssj = ydkssj;
    }

    public Timestamp getYdjssj() {
        return ydjssj;
    }

    public void setYdjssj(Timestamp ydjssj) {
        this.ydjssj = ydjssj;
    }

    public String getHtjk() {
        return htjk;
    }

    public void setHtjk(String htjk) {
        this.htjk = htjk;
    }

    public String getXmfzgcs() {
        return xmfzgcs;
    }

    public void setXmfzgcs(String xmfzgcs) {
        this.xmfzgcs = xmfzgcs;
    }

    public String getHtwj() {
        return htwj;
    }

    public void setHtwj(String htwj) {
        this.htwj = htwj;
    }

    public String getSfqr() {
        return sfqr;
    }

    public void setSfqr(String sfqr) {
        this.sfqr = sfqr;
    }

    public Timestamp getQrsj() {
        return qrsj;
    }

    public void setQrsj(Timestamp qrsj) {
        this.qrsj = qrsj;
    }

    public Timestamp getCjsj() {
        return cjsj;
    }

    public void setCjsj(Timestamp cjsj) {
        this.cjsj = cjsj;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getChdwmc() {
        return chdwmc;
    }

    public void setChdwmc(String chdwmc) {
        this.chdwmc = chdwmc;
    }

    public String getWtdwid() {
        return wtdwid;
    }

    public void setWtdwid(String wtdwid) {
        this.wtdwid = wtdwid;
    }

    public String getWtdwmc() {
        return wtdwmc;
    }

    public void setWtdwmc(String wtdwmc) {
        this.wtdwmc = wtdwmc;
    }

    public String getBcsm() {
        return bcsm;
    }

    public void setBcsm(String bcsm) {
        this.bcsm = bcsm;
    }

    public String getHtzt() {
        return htzt;
    }

    public void setHtzt(String htzt) {
        this.htzt = htzt;
    }

    public String getChsx() {
        return chsx;
    }

    public void setChsx(String chsx) {
        this.chsx = chsx;
    }
}