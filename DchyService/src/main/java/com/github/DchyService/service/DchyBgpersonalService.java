package com.github.DchyService.service;

import com.github.DchyService.entity.DchyBgpersonal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * (DchyBgpersonal)表服务接口
 *
 * @author qufan
 * @since 2020-10-27 19:10:58
 */
public interface DchyBgpersonalService {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	DchyBgpersonal queryById(String id);

	DchyBgpersonal queryByPhone(String phonenum);

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
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	List<DchyBgpersonal> queryAllByLimit(int offset, int limit);

	/**
	 * 新增数据
	 *
	 * @param dchyBgpersonal 实例对象
	 * @return 实例对象
	 */
	DchyBgpersonal insert(DchyBgpersonal dchyBgpersonal);

	/**
	 * 通过id删除数据
	 *
	 * @param 主键
	 * @return 是否成功
	 */
	boolean deleteById(String id);

	/**
	 * 通过did删除数据
	 *
	 * @param did 企业主键
	 * @return 是否成功
	 */
	boolean deleteByDid(String did);

	/**
	 * 修改数据
	 *
	 * @param did
	 * @return 实例对象
	 */
	boolean update(DchyBgpersonal bglist);

}