package com.github.DchyService.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * (DchyLog)实体类
 *
 * @author makejava
 * @since 2020-10-26 19:01:48
 */
public class DchyLog implements Serializable {
    private static final long serialVersionUID = 666183355329009923L;

    private String id;
    /**
     * DchyFbxx
     */
    private String fsrxm;
    /**
     * 发送人id
     */
    private String fsrid;
    /**
     * 发送时间
     */
    private Timestamp fssj;
    /**
     * 接收人姓名
     */
    private String jsrxm;
    /**
     * 接收人id
     */
    private String jsrid;
    /**
     * 接收时间
     */
    private Timestamp jssj;
    /**
     * 当前环节
     */
    private String dqhj;
    /**
     * 项目id
     */
    private String pid;
    /**
     * 处理状态（已办/待办）
     */
    private String clzt;
    /**
     * 是否会签
     */
    private String sfhq;
    /**
     * 是否会签完成
     */
    private String hqwc;
    /**
     * cjsj
     */
    private Timestamp cjsj;
    /**
     * 预留字段
     */
    private String sfyd;
    /**
     * 预留字段
     */
    private String ywlx;

    public String getCzsm() {
        return czsm;
    }

    public void setCzsm(String czsm) {
        this.czsm = czsm;
    }

    private String czsm;

    private int pageNum;

    private int pageSize;

    private DchyFbxx fbxx;

    private DchyYwxx ywxx;

    private DchyZzxx zzxx;

    private String clsx; // 测量事项

    private String searchStr; // 查询字段

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public String getClsx() {
        return clsx;
    }

    public DchyYwxx getYwxx() {
        return ywxx;
    }

    public void setYwxx(DchyYwxx ywxx) {
        this.ywxx = ywxx;
    }

    public DchyZzxx getZzxx() {
        return zzxx;
    }

    public void setZzxx(DchyZzxx zzxx) {
        this.zzxx = zzxx;
    }

    public void setClsx(String clsx) {
        this.clsx = clsx;
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

    public String getFsrxm() {
        return fsrxm;
    }

    public void setFsrxm(String fsrxm) {
        this.fsrxm = fsrxm;
    }

    public String getFsrid() {
        return fsrid;
    }

    public void setFsrid(String fsrid) {
        this.fsrid = fsrid;
    }

    public Timestamp getFssj() {
        return fssj;
    }

    public void setFssj(Timestamp fssj) {
        this.fssj = fssj;
    }

    public String getJsrxm() {
        return jsrxm;
    }

    public void setJsrxm(String jsrxm) {
        this.jsrxm = jsrxm;
    }

    public String getJsrid() {
        return jsrid;
    }

    public void setJsrid(String jsrid) {
        this.jsrid = jsrid;
    }

    public Timestamp getJssj() {
        return jssj;
    }

    public void setJssj(Timestamp jssj) {
        this.jssj = jssj;
    }

    public String getDqhj() {
        return dqhj;
    }

    public void setDqhj(String dqhj) {
        this.dqhj = dqhj;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getClzt() {
        return clzt;
    }

    public void setClzt(String clzt) {
        this.clzt = clzt;
    }

    public String getSfhq() {
        return sfhq;
    }

    public void setSfhq(String sfhq) {
        this.sfhq = sfhq;
    }

    public String getHqwc() {
        return hqwc;
    }

    public void setHqwc(String hqwc) {
        this.hqwc = hqwc;
    }

    public Timestamp getCjsj() {
        return cjsj;
    }

    public void setCjsj(Timestamp cjsj) {
        this.cjsj = cjsj;
    }

    public String getSfyd() {
        return sfyd;
    }
    public void setSfyd(String sfyd) {
        this.sfyd = sfyd;
    }

    public String getYwlx() {
        return ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    public DchyFbxx getFbxx() {
        return fbxx;
    }

    public void setFbxx(DchyFbxx fbxx) {
        this.fbxx = fbxx;
    }
}