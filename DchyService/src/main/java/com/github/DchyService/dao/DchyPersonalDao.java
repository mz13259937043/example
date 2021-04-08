package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyPersonal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (DchyPersonal)表数据库访问层
 *
 * @author qufan
 * @since 2020-10-27 19:21:19
 */
/**
 * @author pydestiny
 *
 */
/**
 * @author pydestiny
 *
 */
@Mapper
@Repository
public interface DchyPersonalDao {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyPersonal queryById(String id);

	/**
	 * 通过dID查询单条数据
	 *
	 * @param did 企业主键
	 * @return 实例对象
	 */
	
	
	List<DchyPersonal> queryByDid(String did);
	
	/**
	 * @param phonenum手机唯一性验证
	 * @return list
	 */
	DchyPersonal queryByPhone(String phonenum);

	/**
	 * 通过str模糊查询
	 *
	 * @param str
	 * @return 实例对象列表
	 */
	List<DchyPersonal> queryUserByStr(String str);

	/**
	 * 通过IDcard查询单条数据
	 *
	 * @param idcard
	 * @return 实例对象
	 */
	DchyPersonal queryByIdcard(String idcard);

	/**
	 * 通过phone查询password单条数据
	 *
	 * @param idcard
	 * @return 实例对象
	 */
	DchyPersonal queryByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

	/**
	 * 查询指定行数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyPersonal> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

	/**
	 * 通过str模糊查询
	 *
	 * @param str
	 * @return 实例对象列表
	 */
	List<DchyPersonal> queryUserByStrLimit(@Param("offset") int offset, @Param("limit") int limit,
			@Param("str") String str);

	/**
	 * 查询多条数据
	 *
	 * @return 对象列表
	 */
	List<DchyPersonal> findAll();

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param dchyPersonal 实例对象
	 * @return 对象列表
	 */
	List<DchyPersonal> queryAll(DchyPersonal dchyPersonal);

	/**
	 * 新增数据
	 *
	 * @param dchyPersonal 实例对象
	 * @return 影响行数
	 */
	int insert(DchyPersonal dchyPersonal);

	/**
	 * 修改数据
	 *
	 * @param dchyPersonal 实例对象
	 * @return 影响行数
	 */
	int update(DchyPersonal dchyPersonal);

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
	 * @param did 主键
	 * @return 影响行数
	 */
	int deleteByDid(String did);
}