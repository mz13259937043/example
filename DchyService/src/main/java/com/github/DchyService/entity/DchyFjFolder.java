package com.github.DchyService.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * (DchyFjFolder)实体类
 *
 * @author makejava
 * @since 2020-10-26 17:20:54
 */
@JsonIgnoreProperties(value = { "handler" })
public class DchyFjFolder implements Serializable {
    private static final long serialVersionUID = -49121305094218694L;

    private String id;
    /**
     * 文件夹名
     */
    private String name;
    /**
     * 文件夹路径
     */
    private String path;
    /**
     * 自关联本表，存储多级目录使用
     */
    private String parentid;
    /**
     * 创建人
     */
    private String username;
    /**
     * 创建人ID
     */
    private String userid;
    /**
     * 创建时间
     */
    private Timestamp creattime;
    private List<DchyFjFolder> dchyFjFolders;

    public List<DchyFjFolder> getDchyFjFolders() {
        return dchyFjFolders;
    }

    public void setDchyFjFolders(List<DchyFjFolder> dchyFjFolders) {
        this.dchyFjFolders = dchyFjFolders;
    }

    public List<DchyFjRecord> getDchyFjRecords() {
        return dchyFjRecords;
    }

    public void setDchyFjRecords(List<DchyFjRecord> dchyFjRecords) {
        this.dchyFjRecords = dchyFjRecords;
    }

    private List<DchyFjRecord> dchyFjRecords;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Object getCreattime() {
        return creattime;
    }

    public void setCreattime(Timestamp creattime) {
        this.creattime = creattime;
    }

    public DchyFjFolder(){}
    public DchyFjFolder(String name, String path, String parentid, String userid, String username, Timestamp creattime, List<DchyFjFolder> dchyFjFolders, List<DchyFjRecord> dchyFjRecords) {
        this.name = name;
        this.path = path;
        this.parentid = parentid;
        this.username = username;
        this.userid = userid;
        this.creattime = creattime;
        this.dchyFjFolders = dchyFjFolders;
        this.dchyFjRecords = dchyFjRecords;
    }
}