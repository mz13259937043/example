package com.github.DchyService.entity;/**
 * @title: DchyProcess
 * @projectName DchyService
 * @description: TODO
 * @author 14874
 * @date 2021/1/8 12:19
 */

import lombok.Data;

/**
 *@ClassName DchyProcess
 *@Description TODO
 *@Author maoZhan
 *@Date DATE{TIME}
 */
@Data
public class DchyProcess {

    private String id;
    private String processName;
    private String processAlias;
    private String processSeqname;
    private String serialCode;
    private String pid;
}
