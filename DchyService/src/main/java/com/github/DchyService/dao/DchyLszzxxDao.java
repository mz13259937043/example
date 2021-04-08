package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyLszzxx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyLszzxx)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-27 19:21:07
 */
@Mapper
public interface DchyLszzxxDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyLszzxx queryById(String id);

	/**
	 * 通过dID查询单条数据
	 *
	 * @param did
	 * @return 实例对象
	 */
	List<DchyLszzxx> queryByDid(String lsdid);

	/**
	 * 通过bgbid查询单条数据
	 *
	 * @param bgbid
	 * @return 实例对象
	 */
	List<DchyLszzxx> queryByBgbid(String bgbid);

	/**
	 * 通过DID查询单条数据
	 *
	 * @param did state
	 * @return 实例对象
	 */
	DchyLszzxx queryByDidAndState(@Param("lsdid") String lsdid, @Param("lsstate") String lsstate);

	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyLszzxx> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyLszzxx 实例对象
	 * @return 对象列表
	 */
	List<DchyLszzxx> queryAll(DchyLszzxx dchyLszzxx);

	/**
	 * 新增数据
	 *
	 * @param dchyLszzxx 实例对象
	 * @return 影响行数
	 */
	int insert(DchyLszzxx dchyLszzxx);

	/**
	 * 修改数据
	 *
	 * @param dchyLszzxx 实例对象
	 * @return 影响行数
	 */
	int update(DchyLszzxx dchyLszzxx);

	/**
	 * 按企业id删除数据
	 *
	 * @param dchyLszzxx 实例对象
	 * @return 影响行数
	 */
	int deleteByDid(String did);

}