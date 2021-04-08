package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyBgpersonalDao;
import com.github.DchyService.dao.DchyLspersonalDao;
import com.github.DchyService.dao.DchyPersonalDao;
import com.github.DchyService.entity.DchyBgpersonal;
import com.github.DchyService.entity.DchyBgxx;
import com.github.DchyService.entity.DchyLspersonal;
import com.github.DchyService.entity.DchyPersonal;
import com.github.DchyService.service.DchyBgpersonalService;
import com.github.DchyService.service.DchyBgxxService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchyBgpersonal)表服务实现类
 *
 * @author qufan
 * @since 2020-10-27 19:10:58
 */
@Service("dchyBgpersonalService")
public class DchyBgpersonalServiceImpl implements DchyBgpersonalService {
	private Logger log = LoggerFactory.getLogger(DchyBgpersonalServiceImpl.class);
	@Resource
	private DchyBgpersonalDao dchyBgpersonalDao;
	@Resource
	private DchyLspersonalDao dchyLspersonalDao;
	@Resource
	private DchyPersonalDao dchyPersonalDao;
	@Resource
	private DchyBgxxService dchyBgxxService;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public DchyBgpersonal queryById(String id) {
		return this.dchyBgpersonalDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	@Override
	public List<DchyBgpersonal> queryAllByLimit(int offset, int limit) {
		return this.dchyBgpersonalDao.queryAllByLimit(offset, limit);
	}

	/**
	 * 新增数据
	 *
	 * @param dchyBgpersonal 实例对象
	 * @return 实例对象
	 */
	@Override
	public DchyBgpersonal insert(DchyBgpersonal dchyBgpersonal) {
		dchyBgpersonalDao.insert(dchyBgpersonal);
		return dchyBgpersonal;
	}

	/**
	 * 修改数据
	 *
	 * @param dchyBgpersonal 实例对象
	 * @return 实例对象
	 */
	@Override
	public boolean update(DchyBgpersonal bglist) {
		return dchyBgpersonalDao.update(bglist) > 0 ? true : false;
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(String id) {
		return this.dchyBgpersonalDao.deleteById(id) > 0 ? true : false;
	}

	@Override
	public List<DchyBgpersonal> queryByDidAndState(String bgdid, String bgstate) {
		return dchyBgpersonalDao.queryByDidAndState(bgdid, bgstate);
	}

	@Override
	public DchyBgpersonal queryByIdcard(String bgidcard) {
		return dchyBgpersonalDao.queryByIdcard(bgidcard);
	}

	@Override
	public boolean deleteByDid(String did) {
		return dchyBgpersonalDao.deleteByDid(did) > 0;
	}

	@Override
	public DchyBgpersonal queryByPhone(String phonenum) {
		return dchyBgpersonalDao.queryByPhone(phonenum);
	}

}