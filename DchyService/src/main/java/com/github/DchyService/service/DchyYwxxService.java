package com.github.DchyService.service;

import com.github.DchyService.entity.DchyYwxx;

import java.util.List;

/**
 * (DchyYwxx)表服务接口
 *
 * @author makejava
 * @since 2020-10-28 10:23:15
 */
public interface DchyYwxxService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DchyYwxx findById(String id);

    /**
     * 查询多条数据
     *
     * @param dchyYwxx
     * @return 对象列表
     */
    List<DchyYwxx> findList(DchyYwxx dchyYwxx);

    /**
     * 新增数据
     *
     * @param dchyYwxx 实例对象
     * @return 实例对象
     */
    DchyYwxx insert(DchyYwxx dchyYwxx);

    /**
     * 修改数据
     *
     * @param dchyYwxx 实例对象
     * @return 实例对象
     */
    DchyYwxx update(DchyYwxx dchyYwxx);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}