package com.github.DchyService.service;

import com.github.DchyService.VO.BgZzxxVO;
import com.github.DchyService.entity.DchyBgzzxx;

import java.util.List;

/**
 * (DchyBgzzxx)表服务接口
 *
 * @author qufan
 * @since 2020-10-27 19:20:28
 */
public interface DchyBgzzxxService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyBgzzxx queryById(String id);

	/**
	 * 通过DID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	BgZzxxVO queryByDid(String bgdid);

	/**
	 * 通过状态查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	List<BgZzxxVO> queryByState(String state);

	/**
	 * 通过DID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	BgZzxxVO queryByDidAndState(String did, String state);

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyBgzzxx> queryAllByLimit(int offset, int limit);

	/**
	 * 新增数据
	 *
	 * @param dchyBgzzxx 实例对象
	 * @return 实例对象
	 */
	DchyBgzzxx insert(DchyBgzzxx dchyBgzzxx);

	/**
	 * 修改数据
	 *
	 * @param dchyBgzzxx 实例对象
	 * @return 实例对象
	 */
	boolean update(DchyBgzzxx dchyBgzzxx);

	/**
	 * 通过did删除数据
	 *
	 * @param did
	 * @return 影响行数
	 */
	boolean deleteByDid(String did);
}