package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyLszzxxDao;
import com.github.DchyService.entity.DchyLszzxx;
import com.github.DchyService.service.DchyLszzxxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DchyLszzxx)表服务实现类
 *
 * @author qufan
 * @since 2020-10-27 19:21:07
 */
@Service("dchyLszzxxService")
public class DchyLszzxxServiceImpl implements DchyLszzxxService {
	private Logger log = LoggerFactory.getLogger(DchyLszzxxServiceImpl.class);
	@Resource
	private DchyLszzxxDao dchyLszzxxDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public DchyLszzxx queryById(String id) {
		return this.dchyLszzxxDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	@Override
	public List<DchyLszzxx> queryAllByLimit(int offset, int limit) {
		return this.dchyLszzxxDao.queryAllByLimit(offset, limit);
	}

	/**
	 * 新增数据
	 *
	 * @param dchyLszzxx 实例对象
	 * @return 实例对象
	 */
	@Override
	public DchyLszzxx insert(DchyLszzxx lszzxx1) {
		dchyLszzxxDao.insert(lszzxx1);
		return lszzxx1;
	}

	/**
	 * 修改数据
	 *
	 * @param dchyLszzxx 实例对象
	 * @return 实例对象
	 */
	@Override
	public boolean update(DchyLszzxx dchyLszzxx) {
		return dchyLszzxxDao.update(dchyLszzxx) > 0 ? true : false;
	}

	/**
	 * 通过DID查询单条数据
	 *
	 * @param did 企业主键
	 * @return 实例对象
	 */
	@Override
	public List<DchyLszzxx> queryByDid(String did) {
		return dchyLszzxxDao.queryByDid(did);
	}

	/**
	 * 通过BGBID查询单条数据
	 *
	 * @param BGBid 变更表主键
	 * @return 实例对象
	 */
	@Override
	public List<DchyLszzxx> queryByBgbid(String bgbid) {
		return dchyLszzxxDao.queryByBgbid(bgbid);
	}

	/**
	 * 通过DID和state查询单条数据
	 *
	 * @param did企业 主键 state状态
	 * @return 实例对象
	 */
	@Override
	public DchyLszzxx queryByDidAndState(String did, String state) {
		return dchyLszzxxDao.queryByDidAndState(did, state);
	}
}