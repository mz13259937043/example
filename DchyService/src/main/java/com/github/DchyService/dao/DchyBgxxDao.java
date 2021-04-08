package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyBgxx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyBgxx)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-27 19:20:02
 */
@Mapper
public interface DchyBgxxDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyBgxx queryById(String id);

	/**
	 * 通过DID查询单条数据
	 *
	 * @param did 企业主键
	 * @return 实例对象
	 */
	List<DchyBgxx> queryByDid(String bgdid);

	/**
	 * 通过dID查询和状态数据
	 *
	 * @param did 企业主键 state状态
	 * @return 实例对象
	 */
	DchyBgxx queryByDidAndState(@Param("bgdid") String bgdid, @Param("bgstate") String bgstate);

	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyBgxx> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyBgxx 实例对象
	 * @return 对象列表
	 */
	List<DchyBgxx> queryAll(DchyBgxx dchyBgxx);

	/**
	 * 新增数据
	 *
	 * @param dchyBgxx 实例对象
	 * @return 影响行数
	 */
	int insert(DchyBgxx dchyBgxx);

	/**
	 * 修改数据
	 *
	 * @param dchyBgxx 实例对象
	 * @return 影响行数
	 */
	int update(DchyBgxx dchyBgxx);

}