package com.github.DchyService.dao;

import com.github.DchyService.VO.ZzxxVO;
import com.github.DchyService.entity.DchyZzxx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyZzxx)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-27 19:21:33
 */
@Mapper
public interface DchyZzxxDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyZzxx queryById(String id);

	/**
	 * 通过DID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyZzxx queryByDid(String did);

	/**
	 * 通过资质信息查询
	 *
	 * @param id 主键
	 * @return 实例对象
	 *//*
		 * List<DchyZzxx> queryByZzxx(DchyZzxx zzxx);
		 */

	/**
	 * 通过企业id和状态查询单条数据
	 *
	 * @param did state
	 * @return 实例对象
	 */
	DchyZzxx queryByDidAndState(@Param("did") String did, @Param("state") String state);

	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<ZzxxVO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit,@Param("state") String state);
	
	
	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<ZzxxVO> queryGlZzxx(@Param("offset") int offset, @Param("limit") int limit);
	

	
	List<ZzxxVO> queryAllGlZzxx();
	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyZzxx 实例对象
	 * @return 对象列表
	 */
	List<ZzxxVO> queryAll(DchyZzxx dchyZzxx);
	List<ZzxxVO> queryZzxxAll(DchyZzxx zzxx);
	
	List<ZzxxVO> queryAllZzxx(String state);

	/**
	 * 新增数据
	 *
	 * @param dchyZzxx 实例对象
	 * @return 影响行数
	 */
	int insert(DchyZzxx dchyZzxx);

	/**
	 * 修改数据
	 *
	 * @param dchyZzxx 实例对象
	 * @return 影响行数
	 */
	int update(DchyZzxx dchyZzxx);

}