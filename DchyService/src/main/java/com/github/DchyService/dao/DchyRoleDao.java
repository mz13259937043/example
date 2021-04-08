package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyRole)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-30 11:37:50
 */
@Mapper
public interface DchyRoleDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyRole queryById(String id);

	List<DchyRole> findRoleByLimit(@Param("offset")int offset,@Param("limit") int limit,@Param("str") String str);
	
	List<DchyRole> findRole(String str);
	
	
	/**
	 * @param role
	 * @category 根据角色名查询角色信息
	 * @return 
	 */
	DchyRole queryByRole(String role);
	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyRole 实例对象
	 * @return 对象列表
	 */
	List<DchyRole> queryAll(DchyRole dchyRole);

	/**
	 * 新增数据
	 *
	 * @param dchyRole 实例对象
	 * @return 影响行数
	 */
	int insert(DchyRole dchyRole);

	/**
	 * 批量新增数据（MyBatis原生foreach方法）
	 *
	 * @param entities List<DchyRole> 实例对象列表
	 * @return 影响行数
	 */
	int insertBatch(@Param("entities") List<DchyRole> entities);

	/**
	 * 修改数据
	 *
	 * @param dchyRole 实例对象
	 * @return 影响行数
	 */
	int update(DchyRole dchyRole);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	int deleteById(String id);

	List<DchyRole> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

	List<DchyRole> queryAllRole();

}