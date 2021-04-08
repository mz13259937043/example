package com.github.DchyService.dao;

import com.github.DchyService.entity.DchySqyx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * (DchySqyx)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-21 09:03:55
 */
@Mapper
@Repository
public interface DchySqyxDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DchySqyx queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DchySqyx> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dchySqyx 实例对象
     * @return 对象列表
     */
    List<DchySqyx> queryAll(DchySqyx dchySqyx);

    /**
     * 新增数据
     *
     * @param dchySqyx 实例对象
     * @return 影响行数
     */
    int insert(DchySqyx dchySqyx);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DchySqyx> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DchySqyx> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DchySqyx> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DchySqyx> entities);

    /**
     * 修改数据
     *
     * @param dchySqyx 实例对象
     * @return 影响行数
     */
    int update(DchySqyx dchySqyx);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    List<Map<String, Object>> getSqyxListByFbxxid(String str,String id);
    /**
    * @Description //获取中标公告
    * @Author maoZhan
    * @Date 2020/10/28 19:53
    * @param  * @param
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String, Object>> getZbGg(String str);
    
    int updateBypid(String pid,String id);

    List<DchySqyx> getByPidAndChdwid(String pid, String chdwid);
}