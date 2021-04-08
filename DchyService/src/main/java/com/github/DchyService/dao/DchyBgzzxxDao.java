package com.github.DchyService.dao;

import com.github.DchyService.VO.BgZzxxVO;
import com.github.DchyService.entity.DchyBgzzxx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyBgzzxx)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-27 19:20:28
 */
@Mapper
public interface DchyBgzzxxDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyBgzzxx queryById(String id);

	/**
	 * 通过DID查询
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	BgZzxxVO queryByDid(String bgdid);

	/**
	 * 通过状态查询
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	List<BgZzxxVO> queryByState(@Param("bgstate") String bgstate);

	/**
	 * 通过DID state查询单条数据
	 *
	 * @param did state
	 * @return 实例对象
	 */
	BgZzxxVO queryByDidAndState(@Param("bgdid") String bgdid, @Param("bgstate") String bgstate);

	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyBgzzxx> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyBgzzxx 实例对象
	 * @return 对象列表
	 */
	List<DchyBgzzxx> queryAll(DchyBgzzxx dchyBgzzxx);

	/**
	 * 新增数据
	 *
	 * @param dchyBgzzxx 实例对象
	 * @return 影响行数
	 */
	int insert(DchyBgzzxx dchyBgzzxx);

	/**
	 * 修改数据
	 *
	 * @param dchyBgzzxx 实例对象
	 * @return 影响行数
	 */
	int update(DchyBgzzxx dchyBgzzxx);

	/**
	 * 通过did删除数据
	 *
	 * @param did
	 * @return 影响行数
	 */
	int deleteByDid(String bgdid);

}