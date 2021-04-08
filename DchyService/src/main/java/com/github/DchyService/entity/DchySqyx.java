package com.github.DchyService.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * (DchySqyx)实体类
 *
 * @author makejava
 * @since 2020-10-21 09:03:54
 */
public class DchySqyx implements Serializable {
    private static final long serialVersionUID = 850162126924508458L;

    private String id;
    /**
     * 测绘单位id
     */
    private String chdwid;
    /**
     * 发布信息id
     */
    private String pid;
    /**
     * 项目报价
     */
    private String xmbj;
    /**
     * 联系人电话
     */
    private String lxrdh;
    /**
     * 联系人
     */
    private String lxr;
    /**
     * 是否定向选择
     */
    private String dxxz;
    /**
     * 是否委托确认
     */
    private String wtqr;
    /**
     * 委托时间
     */
    private Timestamp wtsj;
    /**
     * 创建时间
     */
    private Timestamp cjsj;

    private int pageNum;

    private int pageSize;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getXmbj() {
        return xmbj;
    }

    public void setXmbj(String xmbj) {
        this.xmbj = xmbj;
    }

    public String getLxrdh() {
        return lxrdh;
    }

    public void setLxrdh(String lxrdh) {
        this.lxrdh = lxrdh;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getDxxz() {
        return dxxz;
    }

    public void setDxxz(String dxxz) {
        this.dxxz = dxxz;
    }

    public String getWtqr() {
        return wtqr;
    }

    public void setWtqr(String wtqr) {
        this.wtqr = wtqr;
    }

    public Timestamp getWtsj() {
        return wtsj;
    }

    public void setWtsj(Timestamp wtsj) {
        this.wtsj = wtsj;
    }

    public Timestamp getCjsj() {
        return cjsj;
    }

    public void setCjsj(Timestamp cjsj) {
        this.cjsj = cjsj;
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
}