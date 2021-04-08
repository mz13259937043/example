package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyLspersonalDao;
import com.github.DchyService.entity.DchyLspersonal;
import com.github.DchyService.service.DchyLspersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * (DchyLspersonal)表服务实现类
 *
 * @author makejava
 * @since 2020-10-27 19:20:52
 */
@Service("dchyLspersonalService")
public class DchyLspersonalServiceImpl implements DchyLspersonalService {
	private Logger log = LoggerFactory.getLogger(DchyLspersonalServiceImpl.class);
	@Resource
	private DchyLspersonalDao dchyLspersonalDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public DchyLspersonal queryById(String id) {
		return this.dchyLspersonalDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	@Override
	public List<DchyLspersonal> queryAllByLimit(int offset, int limit) {
		return this.dchyLspersonalDao.queryAllByLimit(offset, limit);
	}

	/**
	 * 新增数据
	 *
	 * @param dchyLspersonal 实例对象
	 * @return 实例对象
	 */
	@Override
	public boolean insert(DchyLspersonal dchyLspersonal) {
		return dchyLspersonalDao.insert(dchyLspersonal) > 0 ? true : false;
	}

	/**
	 * 修改数据
	 *
	 * @param dchyLspersonal 实例对象
	 * @return 实例对象
	 */
	@Override
	public boolean update(DchyLspersonal dchyLspersonal) {
		return dchyLspersonalDao.update(dchyLspersonal) ? true : false;
	}

	@Override
	public List<DchyLspersonal> queryByDid(String did) {
		return dchyLspersonalDao.queryByDid(did);
	}

	@Override
	public List<DchyLspersonal> queryByBgbid(String bgbid) {
		return dchyLspersonalDao.queryByBgbid(bgbid);
	}

}