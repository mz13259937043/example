package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyHtxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (DchyHtxx)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-27 16:44:33
 */
@Mapper
public interface DchyHtxxDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DchyHtxx queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DchyHtxx> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dchyHtxx 实例对象
     * @return 对象列表
     */
    List<DchyHtxx> queryAll(DchyHtxx dchyHtxx);

    /**
     * 新增数据
     *
     * @param dchyHtxx 实例对象
     * @return 影响行数
     */
    int insert(DchyHtxx dchyHtxx);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DchyHtxx> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DchyHtxx> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DchyHtxx> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DchyHtxx> entities);

    /**
     * 修改数据
     *
     * @param dchyHtxx 实例对象
     * @return 影响行数
     */
    int update(DchyHtxx dchyHtxx);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
    * @Description //用pid查询
    * @Author maoZhan
    * @Date 2020/11/4 15:04
    * @param  * @param pid
    * @return com.github.DchyService.entity.DchyHtxx
    **/
    DchyHtxx getByPid(String pid);

}