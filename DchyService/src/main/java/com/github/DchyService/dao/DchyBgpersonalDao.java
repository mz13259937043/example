package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyBgpersonal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DchyBgpersonal)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-27 19:10:58
 */
@Mapper
public interface DchyBgpersonalDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyBgpersonal queryById(String id);

	DchyBgpersonal queryByPhone(String bgphonenum);

	/**
	 * 通过dID查询单条数据
	 *
	 * @param did 企业主键 state
	 * @return 实例对象
	 */
	List<DchyBgpersonal> queryByDidAndState(@Param("bgdid") String bgdid, @Param("bgstate") String bgstate);

	/**
	 * 通过IDcard查询单条数据
	 *
	 * @param idcard
	 * @return 实例对象
	 */
	DchyBgpersonal queryByIdcard(String bgidcard);

	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyBgpersonal> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyBgpersonal 实例对象
	 * @return 对象列表
	 */
	List<DchyBgpersonal> queryAll(DchyBgpersonal dchyBgpersonal);

	/**
	 * 新增数据
	 *
	 * @param dchyBgpersonal 实例对象
	 * @return 影响行数
	 */
	int insert(DchyBgpersonal dchyBgpersonal);

	/**
	 * 修改数据
	 *
	 * @param dchyBgpersonal 实例对象
	 * @return 影响行数
	 */
	int update(DchyBgpersonal dchyBgpersonal);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	int deleteById(String id);

	/**
	 * 通过企业主键删除数据
	 *
	 * @param did 企业主键
	 * @return 影响行数
	 */
	int deleteByDid(String bgdid);

}