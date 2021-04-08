package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyFjFolder;
import com.github.DchyService.entity.DchyFjRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (DchyFjFolder)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-26 17:20:54
 */
@Repository
@org.apache.ibatis.annotations.Mapper
public interface DchyFjFolderDao {

    DchyFjFolder getFolderByPath(String filepath);

    int addRecord(DchyFjRecord record);

    int addFolder(DchyFjFolder folder);
    /**
     * 通过ID查询单条数据
     *
     * @param 主键
     * @return 实例对象
     */
    DchyFjFolder queryById();

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DchyFjFolder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param DchyFjFolder 实例对象
     * @return 对象列表
     */
    List<DchyFjFolder> queryAll(DchyFjFolder DchyFjFolder);

    /**
     * 新增数据
     *
     * @param DchyFjFolder 实例对象
     * @return 影响行数
     */
    int insert(DchyFjFolder DchyFjFolder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DchyFjFolder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DchyFjFolder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DchyFjFolder> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DchyFjFolder> entities);

    /**
     * 修改数据
     *
     * @param DchyFjFolder 实例对象
     * @return 影响行数
     */
    int update(DchyFjFolder DchyFjFolder);

    /**
     * 通过主键删除数据
     *
     * @param 主键
     * @return 影响行数
     */
    int deleteById();


    DchyFjFolder getFolderByKey(String id);


    int deleteRecordByPath(String filepath, String name);

    List<DchyFjRecord> getRecordByFolderId(String id);

    DchyFjRecord getRecordByKey(String id);

    boolean deleteFolderByKey(String id);

    boolean deleteRecordByKey(String id);

//    List<DchyFjFolder> getFolderByFolderId(String id);
}