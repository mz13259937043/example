package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyYwxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * (DchyYwxx)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-28 10:23:14
 */
@Mapper
@Repository
public interface DchyYwxxDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DchyYwxx findById(String id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param dchyYwxx 实例对象
     * @return 对象列表
     */
    List<DchyYwxx> findList(DchyYwxx dchyYwxx);

    /**
     * 新增数据
     *
     * @param dchyYwxx 实例对象
     * @return 影响行数
     */
    int insert(DchyYwxx dchyYwxx);


    /**
     * 修改数据
     *
     * @param dchyYwxx 实例对象
     * @return 影响行数
     */
    int update(DchyYwxx dchyYwxx);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    DchyYwxx getByPid(String pid);

    Map<String,Object> getNoticeMsgByYwxxid(String ywxxid);

}