package com.github.DchyService.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * (DchyFjRecord)实体类
 *
 * @author makejava
 * @since 2020-10-26 17:20:54
 */
@JsonIgnoreProperties(value = { "handler" })
public class DchyFjRecord implements Serializable {
    private static final long serialVersionUID = -47626468774310667L;

    private String id;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 文件大小
     */
    private String filesize;
    /**
     * 文件路径
     */
    private String filepath;
    /**
     * 文件夹ID
     */
    private String folderid;
    /**
     * 上传人
     */
    private String username;
    /**
     * 上传人ID
     */
    private String userid;
    /**
     * 上传时间
     */
    private Object creattime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFolderid() {
        return folderid;
    }

    public void setFolderid(String folderid) {
        this.folderid = folderid;
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

    public void setCreattime(Object creattime) {
        this.creattime = creattime;
    }

    public DchyFjRecord(String filename,String filepath, String filesize, String folderid, String userid,String username,  Object creattime) {
        this.filename = filename;
        this.filepath = filepath;
        this.filesize = filesize;
        this.folderid = folderid;
        this.userid = userid;
        this.username = username;
        this.creattime = creattime;
    }
    public DchyFjRecord(){}
}