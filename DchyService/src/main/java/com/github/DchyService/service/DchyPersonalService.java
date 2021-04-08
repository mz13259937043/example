package com.github.DchyService.service;

import com.github.DchyService.entity.DchyPersonal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * (DchyPersonal)表服务接口
 *
 * @author qufan
 * @since 2020-10-27 19:21:20
 */
public interface DchyPersonalService {

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
	 * 手机唯一性验证
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
	 * 通过str模糊查询
	 *
	 * @param str
	 * @return 实例对象列表
	 */
	List<DchyPersonal> queryUserByStrLimit(int offset, int limit,String str);
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
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyPersonal> queryAllByLimit(int offset, int limit);

	/**
	 * 查询多条数据
	 *
	 * @return 对象列表
	 */
	List<DchyPersonal> queryAll();

	/**
	 * 新增数据
	 *
	 * @param dchyPersonal 实例对象
	 * @return 实例对象
	 */
	boolean insert(DchyPersonal dchyPersonal);

	/**
	 * 修改数据
	 *
	 * @param dchyPersonal 实例对象
	 * @return 实例对象
	 */
	boolean update(DchyPersonal dchyPersonal);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	boolean deleteById(String id);
	
	boolean deleteByDid(String did);

}