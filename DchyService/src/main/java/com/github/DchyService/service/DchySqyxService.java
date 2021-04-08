package com.github.DchyService.service;

import com.github.DchyService.entity.DchySqyx;

import java.util.List;
import java.util.Map;

/**
 * (DchySqyx)表服务接口
 *
 * @author makejava
 * @since 2020-10-21 09:03:55
 */
public interface DchySqyxService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DchySqyx queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DchySqyx> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param dchySqyx 实例对象
     * @return 实例对象
     */
    DchySqyx insert(DchySqyx dchySqyx);

    /**
     * 修改数据
     *
     * @param dchySqyx 实例对象
     * @return 实例对象
     */
    Map<String,Object> update(String data);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    List<DchySqyx> queryAll(DchySqyx dchySqyx);

    List<Map<String, Object>> getSqyxListByFbxxid(String str,String id);

    List<Map<String, Object>> getZbGg(String str);

    List<DchySqyx> getByPidAndChdwid(Map<String, Object> arg);

    DchySqyx modifySqyx(DchySqyx sqyx);
}