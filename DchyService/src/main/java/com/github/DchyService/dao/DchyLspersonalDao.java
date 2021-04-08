package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyLspersonal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyLspersonal)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-27 19:20:52
 */
@Mapper
public interface DchyLspersonalDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyLspersonal queryById(String id);

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	List<DchyLspersonal> queryByDid(String did);

	/**
	 * 通过BGBID查询单条数据
	 *
	 * @param bgbid 变更表主键
	 * @return 实例对象
	 */
	List<DchyLspersonal> queryByBgbid(String bgbid);

	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyLspersonal> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyLspersonal 实例对象
	 * @return 对象列表
	 */
	List<DchyLspersonal> queryAll(DchyLspersonal dchyLspersonal);

	/**
	 * 新增数据
	 *
	 * @param dchyLspersonal 实例对象
	 * @return 影响行数
	 */
	int insert(DchyLspersonal dchyLspersonal);

	/**
	 * 修改数据
	 *
	 * @param dchyLspersonal 实例对象
	 * @return 影响行数
	 */
	boolean update(DchyLspersonal dchyLspersonal);

}