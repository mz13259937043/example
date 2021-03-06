package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyFjRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyFjRecord)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-26 17:20:54
 */
public interface DchyFjRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    DchyFjRecord queryById();

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DchyFjRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dchyFjRecord 实例对象
     * @return 对象列表
     */
    List<DchyFjRecord> queryAll(DchyFjRecord dchyFjRecord);

    /**
     * 新增数据
     *
     * @param dchyFjRecord 实例对象
     * @return 影响行数
     */
    int insert(DchyFjRecord dchyFjRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DchyFjRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DchyFjRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DchyFjRecord> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DchyFjRecord> entities);

    /**
     * 修改数据
     *
     * @param dchyFjRecord 实例对象
     * @return 影响行数
     */
    int update(DchyFjRecord dchyFjRecord);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @return 影响行数
     */
    int deleteById();

}