package com.github.DchyService.service;

import com.github.DchyService.entity.DchyLog;

import java.util.List;
import java.util.Map;

/**
 * (DchyLog)表服务接口
 *
 * @author makejava
 * @since 2020-10-26 19:01:50
 */
public interface DchyLogService {


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DchyLog findById(String id);

    /**
     * 查询多条数据
     *
     * @param dchyLog
     * @return 对象列表
     */
    List<DchyLog> findList(DchyLog dchyLog);

    /**
     * 新增数据
     *
     * @param dchyLog 实例对象
     * @return 实例对象
     */
    DchyLog insert(DchyLog dchyLog);

    /**
     * 修改数据
     *
     * @param dchyLog 实例对象
     * @return 实例对象
     */
    DchyLog update(DchyLog dchyLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);


    /**
     * 查询资质信息待办
     *
     * @param dchyLog 实例对象
     * @return 影响行数
     */
    List<Map<String,Object>> findZzxxDealingList(DchyLog dchyLog);

    /**
     * 查询业务信息待办
     *
     * @param dchyLog 实例对象
     * @return 影响行数
     */
    List<Map<String,Object>> findYwxxDealingList(DchyLog dchyLog);

    /**
     * 查询发布信息待办
     *
     * @param dchyLog 实例对象
     * @return 影响行数
     */
    List<Map<String,Object>> findFbxxDealingList(DchyLog dchyLog);

    /**
     * @Description //项目撤销
     * @Author maoZhan
     * @Date 2020/11/10 17:13
     * @param  * @param log
     * @return com.github.DchyService.entity.DchyLog
     **/
    Map<String,Object> revokeProject(String data);

    DchyLog getLastLog(String pid);
}