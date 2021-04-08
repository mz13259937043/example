package com.github.DchyService.service;

import com.github.DchyService.entity.DchyBgxx;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * (DchyBgxx)表服务接口
 *
 * @author qufan
 * @since 2020-10-27 19:20:02
 */
public interface DchyBgxxService {

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
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyBgxx> queryAllByLimit(int offset, int limit);

	/**
	 * 新增数据
	 *
	 * @param dchyBgxx 实例对象
	 * @return 实例对象
	 */
	DchyBgxx insert(DchyBgxx dchyBgxx);

	/**
	 * 修改数据
	 *
	 * @param dchyBgxx 实例对象
	 * @return 实例对象
	 */
	boolean update(DchyBgxx dchyBgxx);

}