package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyPersonalDao;
import com.github.DchyService.entity.DchyPersonal;
import com.github.DchyService.service.DchyPersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchyPersonal)表服务实现类
 *
 * @author qufan
 * @since 2020-10-27 19:21:20
 */
@Service("dchyPersonalService")
public class DchyPersonalServiceImpl implements DchyPersonalService {
	private Logger log = LoggerFactory.getLogger(DchyPersonalServiceImpl.class);
	@Resource
	private DchyPersonalDao dchyPersonalDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public DchyPersonal queryById(String id) {
		return this.dchyPersonalDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	@Override
	public List<DchyPersonal> queryAllByLimit(int offset, int limit) {
		return this.dchyPersonalDao.queryAllByLimit(offset, limit);
	}

	/**
	 * 新增数据
	 *
	 * @param dchyPersonal 实例对象
	 * @return 实例对象
	 */
	@Override
	public boolean insert(DchyPersonal dchyPersonal) {
		return dchyPersonalDao.insert(dchyPersonal) > 0 ? true : false;
	}

	/**
	 * 修改数据
	 *
	 * @param dchyPersonal 实例对象
	 * @return 实例对象
	 */
	@Override
	public boolean update(DchyPersonal dchyPersonal) {
		return dchyPersonalDao.update(dchyPersonal) > 0 ? true : false;
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(String id) {
		return dchyPersonalDao.deleteById(id) > 0 ? true : false;
	}

	@Override
	public List<DchyPersonal> queryByDid(String did) {
		return dchyPersonalDao.queryByDid(did);
	}

	@Override
	public DchyPersonal queryByIdcard(String idcard) {
		return dchyPersonalDao.queryByIdcard(idcard);
	}

	@Override
	public DchyPersonal queryByPhoneAndPassword(String phone, String password) {
		return dchyPersonalDao.queryByPhoneAndPassword(phone, password);
	}

	@Override
	public List<DchyPersonal> queryAll() {
		return dchyPersonalDao.findAll();
	}

	@Override
	public List<DchyPersonal> queryUserByStr(String str) {
		return dchyPersonalDao.queryUserByStr(str);
	}

	@Override
	public List<DchyPersonal> queryUserByStrLimit(int offset, int limit, String str) {
		return dchyPersonalDao.queryUserByStrLimit(offset, limit, str);
	}

	@Override
	public DchyPersonal queryByPhone(String phonenum) {
		return dchyPersonalDao.queryByPhone(phonenum);
	}

	@Override
	public boolean deleteByDid(String did) {
		return dchyPersonalDao.deleteByDid(did) > 0;
	}
}