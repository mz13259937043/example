package com.github.DchyService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * (BdcFj)实体类
 *
 * @author makejava
 * @since 2020-06-05 11:01:51
 */
@Data
public class FjObj implements Serializable {
    
    private String fjId;
    /**
    * 附件名称
    */
    private String fjName;
    /**
    * 附件地址
    */
    private String fjPath;
    /**
    * 附件上传人id
    */
    private String fjUserid;
    /**
    * 项目id
    */
    private String pid;
    /**
    * 附件备注
    */
    private String fjBz;
    /**
    * 附件上传时间
    */
    private Timestamp fjDate;
    /**
    * 是否删除
    */
    private String fjIsdel;



}