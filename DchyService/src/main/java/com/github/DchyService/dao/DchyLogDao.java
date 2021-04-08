package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * (DchyLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-26 19:01:49
 */
@Mapper
public interface DchyLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DchyLog findById(String id);

    /**
     * 查询指定行数据
     *
     * @param dchyLog
     * @return 对象列表
     */
    List<DchyLog> findList(DchyLog dchyLog);

    /**
     * 新增数据
     *
     * @param dchyLog 实例对象
     * @return 影响行数
     */
    int insert(DchyLog dchyLog);

    /**
     * 修改数据
     *
     * @param dchyLog 实例对象
     * @return 影响行数
     */
    int update(DchyLog dchyLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

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

    DchyLog getLogByDqhyAndPid(DchyLog dchyLog);

    DchyLog getLastLog(String pid);

    DchyLog getJsrLastLog(String pid,String jsrid);

    DchyLog getLogByPid(String pid,String userid);

}