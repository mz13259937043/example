package com.github.DchyService.service;

import com.github.DchyService.entity.DchyLspersonal;

import java.util.List;

/**
 * (DchyLspersonal)表服务接口
 *
 * @author qufan
 * @since 2020-10-27 19:20:52
 */
public interface DchyLspersonalService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyLspersonal queryById(String id);

	/**
	 * 通过dID查询单条数据
	 *
	 * @param did 企业主键
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
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyLspersonal> queryAllByLimit(int offset, int limit);

	/**
	 * 新增数据
	 *
	 * @param dchyLspersonal 实例对象
	 * @return 实例对象
	 */
	boolean insert(DchyLspersonal dchyLspersonal);

	/**
	 * 修改数据
	 *
	 * @param dchyLspersonal 实例对象
	 * @return 实例对象
	 */
	boolean update(DchyLspersonal dchyLspersonal);

}