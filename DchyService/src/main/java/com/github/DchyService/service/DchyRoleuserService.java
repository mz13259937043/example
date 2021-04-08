package com.github.DchyService.service;

import com.github.DchyService.entity.DchyRoleuser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * (DchyRoleuser)表服务接口
 *
 * @author makejava
 * @since 2020-10-30 11:38:44
 */
public interface DchyRoleuserService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyRoleuser queryById(String id);

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyRoleuser> queryAllByLimit();

	List<DchyRoleuser> queryAllByUid(String uid);

	List<DchyRoleuser> queryAllByBgUid(String bguid);

	/**
	 * 通过ROLEID查询单条数据
	 *
	 * @param roleid 角色主键
	 * @return 实例对象列表
	 */
	List<DchyRoleuser> queryByRoleId(String roleid);

	/**
	 * 通过ROLEID查询多条分页数据
	 *
	 * @param roleid 角色主键
	 * @return 实例对象列表
	 */
	List<DchyRoleuser> queryByRoleIdLimit(@Param("roleid") String roleid, @Param("offset") int offset,
			@Param("limit") int limit);

	/**
	 * 新增数据
	 *
	 * @param dchyRoleuser 实例对象
	 * @return 实例对象
	 */
	DchyRoleuser insert(DchyRoleuser dchyRoleuser);

	/**
	 * 修改数据
	 *
	 * @param dchyRoleuser 实例对象
	 * @return 实例对象
	 */
	DchyRoleuser update(DchyRoleuser dchyRoleuser);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	boolean deleteById(String id);

	/**
	 * 通过用户主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	boolean deleteByUid(String uid);

	boolean deleteByRoleId(String roleid);
	
	boolean deleteRoleByUser(String userid,String roleid);

}