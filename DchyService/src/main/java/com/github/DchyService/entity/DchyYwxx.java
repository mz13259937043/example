package com.github.DchyService.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * (DchyYwxx)实体类
 *
 * @author makejava
 * @since 2020-10-28 10:23:13
 */
public class DchyYwxx implements Serializable {
    private static final long serialVersionUID = -76382272229009964L;

    private String id;
    /**
     * 项目id
     */
    private String pid;
    /**
     * 测绘起始时间
     */
    private Timestamp chqssj;
    /**
     * 测绘完成时间
     */
    private Timestamp chwcsj;
    /**
     * 测绘实施补充说明
     */
    private String chssbcsm;
    /**
     * 测绘实施附件
     */
    private String ssfj;
    /**
     * 成果汇交附件
     */
    private String cghjfj;
    /**
     * 测绘成果负责人
     */
    private String chcgfzr;
    /**
     * 负责人电话
     */
    private String fzrdh;
    /**
     * 成果汇交补充说明
     */
    private String cghjbcsm;
    /**
     * 成果确认附件
     */
    private String cgqrfj;
    /**
     * 成果确认补充说明
     */
    private String cgqrbcsm;
    /**
     * 成果验收附件
     */
    private String cgysfj;
    /**
     * 成果验收补充说明
     */
    private String cgysbcsm;
    /**
     * 成果审核附件
     */
    private String cgshfj;
    /**
     * 成果审核补充说明
     */
    private String cgshbcsm;
    /**
     * 预留1  存储
     */
    private String yl1;
    /**
     * 预留2    前台传递业务ID用了
     */
    private String yl2;

    public String getYl3() {
        return yl3;
    }

    public void setYl3(String yl3) {
        this.yl3 = yl3;
    }

    private String yl3;   // 用来存储 测绘阶段的id  已被使用
    /**
     * 业务名称
     */
    private String ywmc;
    /**
     * 创建时间
     */
    private Timestamp cjsj;
    /**
     * 业务状态（已完成/办理中）
     */
    private String zt;
    /**
     * 负责人身份证号
     */
    private String fzrsfz;
    /**
     * 质检报告附件
     */
    private String zjbg;
    /**
     * 成果未确认原因
     */
    private String wqryy;
    /**
     * 成果确认时间
     */
    private Timestamp cgqrsj;
    /**
     * 成果是否通过审核
     */
    private String cgsfsh;
    /**
     * 成果是否确认
     */
    private String cgshqr;
    /**
     * 未通过审核意见
     */
    private String wgsyy;
    /**
     * 成果审核时间
     */
    private Timestamp chshsj;

    private int pageNum;

    private int pageSize;

    private String fszt; // 发送状态   用来控制后台修改内容

    private String xmjd; // 项目阶段  项目所属大阶段

    private String dqhj; // 当前环节

    private String xyhj; // 下一环节

    private String cghjzt; // 成果汇交状态 （保存/提交）

    private String cgyszt; // 成果验收状态 （通过/未通过）

    private Timestamp cghjsj; // 成果汇交时间

    private String searchStr; // 查询字段

    private List<DchyLog> logsList;

    private String djh; // 地籍号

    private String bdcdyh; // 不动产单元号

    private String cgshr; // 成果审核人

    private String cgshrid;     // 成果审核人id

    private String cgshyj;      // 成果审核意见

    private String cgbh;        // 成果编号

    public String getCgbh() {
        return cgbh;
    }

    public void setCgbh(String cgbh) {
        this.cgbh = cgbh;
    }

    private Timestamp cgyssj;   // 成果验收时间
    
    private Object cgxx;
    
	public Object getCgxx() {
		return cgxx;
	}

	public void setCgxx(Object cgxx) {
		this.cgxx = cgxx;
	}

	public String getCgshjlsid() {
        return cgshjlsid;
    }

    public void setCgshjlsid(String cgshjlsid) {
        this.cgshjlsid = cgshjlsid;
    }

    private String cgshjlsid; // 成果验收时间

    public Timestamp getCgyssj() {
        return cgyssj;
    }

    public void setCgyssj(Timestamp cgyssj) {
        this.cgyssj = cgyssj;
    }

    public String getCgshr() {
        return cgshr;
    }

    public void setCgshr(String cgshr) {
        this.cgshr = cgshr;
    }

    public String getCgshrid() {
        return cgshrid;
    }

    public void setCgshrid(String cgshrid) {
        this.cgshrid = cgshrid;
    }

    public String getCgshyj() {
        return cgshyj;
    }

    public void setCgshyj(String cgshyj) {
        this.cgshyj = cgshyj;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public List<DchyLog> getLogsList() {
        return logsList;
    }

    public void setLogsList(List<DchyLog> logsList) {
        this.logsList = logsList;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public String getCgyszt() {
        return cgyszt;
    }

    public void setCgyszt(String cgyszt) {
        this.cgyszt = cgyszt;
    }

    public String getXmjd() {
        return xmjd;
    }

    public Timestamp getCghjsj() {
        return cghjsj;
    }

    public void setCghjsj(Timestamp cghjsj) {
        this.cghjsj = cghjsj;
    }

    public String getDqhj() {
        return dqhj;
    }

    public String getCghjzt() {
        return cghjzt;
    }

    public void setCghjzt(String cghjzt) {
        this.cghjzt = cghjzt;
    }

    public void setDqhj(String dqhj) {
        this.dqhj = dqhj;
    }

    public String getXyhj() {
        return xyhj;
    }

    public void setXyhj(String xyhj) {
        this.xyhj = xyhj;
    }

    public void setXmjd(String xmjd) {
        this.xmjd = xmjd;
    }

    public String getFszt() {
        return fszt;
    }

    public void setFszt(String fszt) {
        this.fszt = fszt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Timestamp getChqssj() {
        return chqssj;
    }

    public void setChqssj(Timestamp chqssj) {
        this.chqssj = chqssj;
    }

    public Timestamp getChwcsj() {
        return chwcsj;
    }

    public void setChwcsj(Timestamp chwcsj) {
        this.chwcsj = chwcsj;
    }

    public String getChssbcsm() {
        return chssbcsm;
    }

    public void setChssbcsm(String chssbcsm) {
        this.chssbcsm = chssbcsm;
    }

    public String getSsfj() {
        return ssfj;
    }

    public void setSsfj(String ssfj) {
        this.ssfj = ssfj;
    }

    public String getCghjfj() {
        return cghjfj;
    }

    public void setCghjfj(String cghjfj) {
        this.cghjfj = cghjfj;
    }

    public String getChcgfzr() {
        return chcgfzr;
    }

    public void setChcgfzr(String chcgfzr) {
        this.chcgfzr = chcgfzr;
    }

    public String getFzrdh() {
        return fzrdh;
    }

    public void setFzrdh(String fzrdh) {
        this.fzrdh = fzrdh;
    }

    public String getCghjbcsm() {
        return cghjbcsm;
    }

    public void setCghjbcsm(String cghjbcsm) {
        this.cghjbcsm = cghjbcsm;
    }

    public String getCgqrfj() {
        return cgqrfj;
    }

    public void setCgqrfj(String cgqrfj) {
        this.cgqrfj = cgqrfj;
    }

    public String getCgqrbcsm() {
        return cgqrbcsm;
    }

    public void setCgqrbcsm(String cgqrbcsm) {
        this.cgqrbcsm = cgqrbcsm;
    }

    public String getCgysfj() {
        return cgysfj;
    }

    public void setCgysfj(String cgysfj) {
        this.cgysfj = cgysfj;
    }

    public String getCgysbcsm() {
        return cgysbcsm;
    }

    public void setCgysbcsm(String cgysbcsm) {
        this.cgysbcsm = cgysbcsm;
    }

    public String getCgshfj() {
        return cgshfj;
    }

    public void setCgshfj(String cgshfj) {
        this.cgshfj = cgshfj;
    }

    public String getCgshbcsm() {
        return cgshbcsm;
    }

    public void setCgshbcsm(String cgshbcsm) {
        this.cgshbcsm = cgshbcsm;
    }

    public String getYl1() {
        return yl1;
    }

    public void setYl1(String yl1) {
        this.yl1 = yl1;
    }

    public String getYl2() {
        return yl2;
    }

    public void setYl2(String yl2) {
        this.yl2 = yl2;
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public Timestamp getCjsj() {
        return cjsj;
    }

    public void setCjsj(Timestamp cjsj) {
        this.cjsj = cjsj;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getFzrsfz() {
        return fzrsfz;
    }

    public void setFzrsfz(String fzrsfz) {
        this.fzrsfz = fzrsfz;
    }

    public String getZjbg() {
        return zjbg;
    }

    public void setZjbg(String zjbg) {
        this.zjbg = zjbg;
    }

    public String getWqryy() {
        return wqryy;
    }

    public void setWqryy(String wqryy) {
        this.wqryy = wqryy;
    }

    public Timestamp getCgqrsj() {
        return cgqrsj;
    }

    public void setCgqrsj(Timestamp cgqrsj) {
        this.cgqrsj = cgqrsj;
    }

    public String getCgsfsh() {
        return cgsfsh;
    }

    public void setCgsfsh(String cgsfsh) {
        this.cgsfsh = cgsfsh;
    }

    public String getCgshqr() {
        return cgshqr;
    }

    public void setCgshqr(String cgshqr) {
        this.cgshqr = cgshqr;
    }

    public String getWgsyy() {
        return wgsyy;
    }

    public void setWgsyy(String wgsyy) {
        this.wgsyy = wgsyy;
    }

    public Timestamp getChshsj() {
        return chshsj;
    }

    public void setChshsj(Timestamp chshsj) {
        this.chshsj = chshsj;
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