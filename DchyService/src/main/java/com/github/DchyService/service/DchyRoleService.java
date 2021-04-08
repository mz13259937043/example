package com.github.DchyService.service;

import com.github.DchyService.entity.DchyRole;

import java.util.List;

/**
 * (DchyRole)表服务接口
 *
 * @author makejava
 * @since 2020-10-30 11:37:50
 */
public interface DchyRoleService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyRole queryById(String id);

	List<DchyRole> findRoleByLimit(int offset, int limit, String str);

	DchyRole queryByRole(String role);
	List<DchyRole> findRole(String str);

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyRole> queryAllByLimit(int offset, int limit);

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyRole> queryAll();

	/**
	 * 新增数据
	 *
	 * @param dchyRole 实例对象
	 * @return 实例对象
	 */
	DchyRole insert(DchyRole dchyRole);

	/**
	 * 修改数据
	 *
	 * @param dchyRole 实例对象
	 * @return 实例对象
	 */
	DchyRole update(DchyRole dchyRole);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	boolean deleteById(String id);

}