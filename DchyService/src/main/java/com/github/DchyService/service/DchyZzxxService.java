package com.github.DchyService.service;

import com.github.DchyService.VO.ZzxxVO;
import com.github.DchyService.entity.DchyZzxx;

import java.util.List;

/**
 * (DchyZzxx)表服务接口
 *
 * @author makejava
 * @since 2020-10-27 19:21:33
 */
public interface DchyZzxxService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyZzxx queryById(String id);

	/**
	 * 通过dID查询单条数据
	 *
	 * @param 企业信息 主键
	 * @return 实例对象
	 */
	DchyZzxx queryByDid(String did);

	/**
	 * 通过实体查询数据
	 *
	 * @param zzxx实体
	 * @return 实例对象
	 */
	List<ZzxxVO> queryByZzxx(DchyZzxx zzxx);
	
	/**
	 * 通过实体查询数据
	 *
	 * @param zzxx实体
	 * @return 实例对象
	 */
	List<ZzxxVO> queryByZzxxAll(DchyZzxx zzxx);

	/**
	 * 通过实体查询数据
	 *
	 * @param zzxx实体
	 * @return 实例对象
	 */
	List<ZzxxVO> queryAllZzxx(String state);

	/**
	 * 通过企业id和状态查询单条数据
	 *
	 * @param 企业信息 主键
	 * @return 实例对象
	 */
	DchyZzxx queryByDidAndState(String did, String state);

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<ZzxxVO> queryAllByLimit(int offset, int limit, String state);

	/**
	 * 新增数据
	 *
	 * @param dchyZzxx 实例对象
	 * @return 实例对象
	 */
	DchyZzxx insert(DchyZzxx dchyZzxx);

	/**
	 * 修改数据
	 *
	 * @param dchyZzxx 实例对象
	 * @return 实例对象
	 */
	boolean update(DchyZzxx dchyZzxx);

	List<ZzxxVO> queryGlZzxx(int offset, int limit);

	List<ZzxxVO> queryAllGlZzxx();

}