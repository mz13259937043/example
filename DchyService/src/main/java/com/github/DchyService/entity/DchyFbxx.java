package com.github.DchyService.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * (DchyFbxx)实体类
 *
 * @author makejava
 * @since 2020-10-20 18:37:24
 */
@JsonIgnoreProperties(value = { "handler" })
public class DchyFbxx implements Serializable {
    private static final long serialVersionUID = 483468914880605910L;

    private String id;
    private String xmmc;        //项目名称
    private String wtdw;        //委托单位
    private String wtdwid;      //委托单位id
    private String xmdz;        //项目地址
    private String chjd;        //测绘阶段
    private String zzdj;        //资质等级
    private String yqxydj;      //要求信用等级
    private String jyfs;        //交易方式
    private String ysje;        //预算金额
    private String djbh;        //地籍编号
    private String lxyj;        //立项依据
    private String zjly;        //资金来源
    private String xmwcsx;      //项目完成时限
    private String fwnr;        //服务内容
    private Timestamp bmkssj;   //报名开始时间
    private Timestamp bmjssj;   //报名结束时间
    private String yzdwlxr;     //业主单位联系人
    private String yzdwlxrdh;   //业主单位联系人联系电话
    private String bz;      //备注
    private String dchybh;      //多测合一编号
    private String xmzt;        //项目状态
    private String xmssxzq;      //项目所属行政区
    private String fjtj;      //附加条件
    private String cjr;      //创建人
    private String cjrid;      //创建人id
    private String fbyy;      //废标原因
    private String chcgfzr;      //成果负责人
    private String chcgfzrdh;    //成果负责人电话
    private String chcgfzrsfzh;  //成果负责人身份证号
    private Timestamp cjsj;     //创建时间
    private int pageSize;
    private int pageNum;
    private String searchStr;
    private List<DchyHtxx> htxxList;
    private List<DchySqyx> sqyxList;
    private List<DchyYwxx> ywxxList;
    private List<DchyLog> logsList;

    
    public String getChcgfzr() {
		return chcgfzr;
	}

	public void setChcgfzr(String chcgfzr) {
		this.chcgfzr = chcgfzr;
	}

	public String getChcgfzrdh() {
		return chcgfzrdh;
	}

	public void setChcgfzrdh(String chcgfzrdh) {
		this.chcgfzrdh = chcgfzrdh;
	}

	public String getChcgfzrsfzh() {
		return chcgfzrsfzh;
	}

	public void setChcgfzrsfzh(String chcgfzrsfzh) {
		this.chcgfzrsfzh = chcgfzrsfzh;
	}

	public String getXmssxzq() {
        return xmssxzq;
    }
    
    public String getFbyy() {
		return fbyy;
	}

	public void setFbyy(String fbyy) {
		this.fbyy = fbyy;
	}

	public void setXmssxzq(String xmssxzq) {
        this.xmssxzq = xmssxzq;
    }
    public String getCjr() {
        return cjr;
    }
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }
    public String getCjrid() {
        return cjrid;
    }
    public void setCjrid(String cjrid) {
        this.cjrid = cjrid;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageNum() {
        return pageNum;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getDchybh() {
        return dchybh;
    }

    public void setDchybh(String dchybh) {
        this.dchybh = dchybh;
    }

  

    public List<DchyHtxx> getHtxxList() {
        return htxxList;
    }

    public void setHtxxList(List<DchyHtxx> htxxList) {
        this.htxxList = htxxList;
    }

    public List<DchyLog> getLogsList() {
        return logsList;
    }

    public void setLogsList(List<DchyLog> logsList) {
        this.logsList = logsList;
    }

    public List<DchySqyx> getSqyxList() {
        return sqyxList;
    }

    public void setSqyxList(List<DchySqyx> sqyxList) {
        this.sqyxList = sqyxList;
    }

    public List<DchyYwxx> getYwxxList() {
        return ywxxList;
    }

    public void setYwxxList(List<DchyYwxx> ywxxList) {
        this.ywxxList = ywxxList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getWtdw() {
        return wtdw;
    }

    public void setWtdw(String wtdw) {
        this.wtdw = wtdw;
    }

    public String getWtdwid() {
        return wtdwid;
    }

    public void setWtdwid(String wtdwid) {
        this.wtdwid = wtdwid;
    }

    public String getXmdz() {
        return xmdz;
    }

    public void setXmdz(String xmdz) {
        this.xmdz = xmdz;
    }

    public String getChjd() {
        return chjd;
    }

    public void setChjd(String chjd) {
        this.chjd = chjd;
    }

    public String getZzdj() {
        return zzdj;
    }

    public void setZzdj(String zzdj) {
        this.zzdj = zzdj;
    }

    public String getYqxydj() {
        return yqxydj;
    }

    public void setYqxydj(String yqxydj) {
        this.yqxydj = yqxydj;
    }

    public String getJyfs() {
        return jyfs;
    }

    public void setJyfs(String jyfs) {
        this.jyfs = jyfs;
    }

    public String getYsje() {
        return ysje;
    }

    public void setYsje(String ysje) {
        this.ysje = ysje;
    }

    public String getDjbh() {
        return djbh;
    }

    public void setDjbh(String djbh) {
        this.djbh = djbh;
    }

    public String getLxyj() {
        return lxyj;
    }

    public void setLxyj(String lxyj) {
        this.lxyj = lxyj;
    }

    public String getZjly() {
        return zjly;
    }

    public void setZjly(String zjly) {
        this.zjly = zjly;
    }

    public String getXmwcsx() {
        return xmwcsx;
    }

    public void setXmwcsx(String xmwcsx) {
        this.xmwcsx = xmwcsx;
    }

    public String getFwnr() {
        return fwnr;
    }

    public void setFwnr(String fwnr) {
        this.fwnr = fwnr;
    }

    public Timestamp getBmkssj() {
        return bmkssj;
    }

    public void setBmkssj(Timestamp bmkssj) {
        this.bmkssj = bmkssj;
    }

    public Timestamp getBmjssj() {
        return bmjssj;
    }

    public void setBmjssj(Timestamp bmjssj) {
        this.bmjssj = bmjssj;
    }

    public String getYzdwlxr() {
        return yzdwlxr;
    }

    public void setYzdwlxr(String yzdwlxr) {
        this.yzdwlxr = yzdwlxr;
    }

    public String getYzdwlxrdh() {
        return yzdwlxrdh;
    }

    public void setYzdwlxrdh(String yzdwlxrdh) {
        this.yzdwlxrdh = yzdwlxrdh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFjtj() {
        return fjtj;
    }

    public void setFjtj(String fjtj) {
        this.fjtj = fjtj;
    }

    public Timestamp getCjsj() {
        return cjsj;
    }

    public void setCjsj(Timestamp cjsj) {
        this.cjsj = cjsj;
    }

    public String getXmzt() {
        return xmzt;
    }

    public void setXmzt(String xmzt) {
        this.xmzt = xmzt;
    }

}