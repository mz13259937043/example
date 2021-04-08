package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyBgxxDao;
import com.github.DchyService.entity.DchyBgxx;
import com.github.DchyService.service.DchyBgxxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DchyBgxx)表服务实现类
 *
 * @author qufan
 * @since 2020-10-27 19:20:02
 */
@Service("dchyBgxxService")
public class DchyBgxxServiceImpl implements DchyBgxxService {
	private Logger log = LoggerFactory.getLogger(DchyBgxxServiceImpl.class);
	@Resource
	private DchyBgxxDao dchyBgxxDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public DchyBgxx queryById(String id) {
		return this.dchyBgxxDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	@Override
	public List<DchyBgxx> queryAllByLimit(int offset, int limit) {
		return this.dchyBgxxDao.queryAllByLimit(offset, limit);
	}

	/**
	 * 新增数据
	 *
	 * @param dchyBgxx 实例对象
	 * @return 实例对象
	 */
	@Override
	public DchyBgxx insert(DchyBgxx bgxx) {
		this.dchyBgxxDao.insert(bgxx);
		return bgxx;
	}

	/**
	 * 修改数据
	 *
	 * @param dchyBgxx 实例对象
	 * @return 实例对象
	 */
	@Override
	public boolean update(DchyBgxx dchyBgxx) {
		return dchyBgxxDao.update(dchyBgxx) > 0 ? true : false;
	}
	/**
	 * 通过DID查询单条数据
	 *
	 * @param did 企业主键
	 * @return 实例对象
	 */
	@Override
	public List<DchyBgxx> queryByDid(String bgdid) {
		return dchyBgxxDao.queryByDid(bgdid);
	}
	/**
	 * 通过dID查询和状态数据
	 *
	 * @param did 企业主键 state状态
	 * @return 实例对象
	 */
	@Override
	public DchyBgxx queryByDidAndState(String bgdid, String bgstate) {
		return dchyBgxxDao.queryByDidAndState(bgdid, bgstate);
	}

}