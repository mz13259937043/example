package com.github.DchyService.service;

import com.github.DchyService.entity.DchyLszzxx;

import java.util.List;

/**
 * (DchyLszzxx)表服务接口
 *
 * @author qufan
 * @since 2020-10-27 19:21:07
 */
public interface DchyLszzxxService {

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
	 * @param did 企业主键
	 * @return 实例对象
	 */
	List<DchyLszzxx> queryByDid(String did);

	/**
	 * 通过bgbid查询单条数据
	 *
	 * @param did 企业主键
	 * @return 实例对象
	 */
	List<DchyLszzxx> queryByBgbid(String bgbid);

	/**
	 * 通过dID查询单条数据
	 *
	 * @param did 企业主键
	 * @return 实例对象
	 */
	DchyLszzxx queryByDidAndState(String did, String state);

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyLszzxx> queryAllByLimit(int offset, int limit);

	/**
	 * 新增数据
	 *
	 * @param dchyLszzxx 实例对象
	 * @return 实例对象
	 */
	DchyLszzxx insert(DchyLszzxx dchyLszzxx);

	/**
	 * 修改数据
	 *
	 * @param dchyLszzxx 实例对象
	 * @return 实例对象
	 */
	boolean update(DchyLszzxx dchyLszzxx);


}