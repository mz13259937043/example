package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyRoleuser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyRoleuser)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-30 11:38:44
 */
@Mapper
public interface DchyRoleuserDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyRoleuser queryById(String id);

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
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyRoleuser> queryAllByLimit();

	List<DchyRoleuser> queryAllByUid(String uid);

	List<DchyRoleuser> queryAllByBgUid(String bguid);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyRoleuser 实例对象
	 * @return 对象列表
	 */
	List<DchyRoleuser> queryAll(DchyRoleuser dchyRoleuser);

	/**
	 * 新增数据
	 *
	 * @param dchyRoleuser 实例对象
	 * @return 影响行数
	 */
	int insert(DchyRoleuser dchyRoleuser);

	/**
	 * 批量新增数据（MyBatis原生foreach方法）
	 *
	 * @param entities List<DchyRoleuser> 实例对象列表
	 * @return 影响行数
	 */
	int insertBatch(@Param("entities") List<DchyRoleuser> entities);

	/**
	 * 批量新增或按主键更新数据（MyBatis原生foreach方法）
	 *
	 * @param entities List<DchyRoleuser> 实例对象列表
	 * @return 影响行数
	 */
	int insertOrUpdateBatch(@Param("entities") List<DchyRoleuser> entities);

	/**
	 * 修改数据
	 *
	 * @param dchyRoleuser 实例对象
	 * @return 影响行数
	 */
	int update(DchyRoleuser dchyRoleuser);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	int deleteById(String id);

	/**
	 * 通过用户主键删除数据
	 *
	 * @param uid 用户主键
	 * @return 影响行数
	 */
	int deleteByUid(String userid);

	int deleteByRoleId(String roleid);

	int deleteRoleByUser(@Param("userid") String userid, @Param("roleid") String roleid);

}