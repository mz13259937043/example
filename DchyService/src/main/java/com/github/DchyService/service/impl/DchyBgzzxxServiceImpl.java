package com.github.DchyService.service.impl;

import com.github.DchyService.VO.BgZzxxVO;
import com.github.DchyService.dao.DchyBgzzxxDao;
import com.github.DchyService.entity.DchyBgzzxx;
import com.github.DchyService.service.DchyBgzzxxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DchyBgzzxx)表服务实现类
 *
 * @author qufan
 * @since 2020-10-27 19:20:28
 */
@Service("dchyBgzzxxService")
public class DchyBgzzxxServiceImpl implements DchyBgzzxxService {
	private Logger log = LoggerFactory.getLogger(DchyBgzzxxServiceImpl.class);
	@Resource
	private DchyBgzzxxDao dchyBgzzxxDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public DchyBgzzxx queryById(String id) {
		return this.dchyBgzzxxDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	@Override
	public List<DchyBgzzxx> queryAllByLimit(int offset, int limit) {
		return this.dchyBgzzxxDao.queryAllByLimit(offset, limit);
	}

	/**
	 * 新增数据
	 *
	 * @param dchyBgzzxx 实例对象
	 * @return 实例对象
	 */
	@Override
	public DchyBgzzxx insert(DchyBgzzxx bgzzxx) {
		this.dchyBgzzxxDao.insert(bgzzxx);
		return bgzzxx;
	}

	/**
	 * 修改数据
	 *
	 * @param dchyBgzzxx 实例对象
	 * @return 实例对象
	 */
	@Override
	public boolean update(DchyBgzzxx dchyBgzzxx) {
		return dchyBgzzxxDao.update(dchyBgzzxx) > 0 ? true : false;
	}

	/**
	 * 通过did查询数据
	 *
	 * @param did
	 * @return 对象
	 */

	@Override
	public BgZzxxVO queryByDid(String did) {
		return dchyBgzzxxDao.queryByDid(did);
	}

	/**
	 * 通过did 和 state查询数据
	 *
	 * @param did state
	 * @return 对象
	 */
	@Override
	public BgZzxxVO queryByDidAndState(String did, String state) {
		return dchyBgzzxxDao.queryByDidAndState(did, state);
	}

	/**
	 * 通过did删除数据
	 *
	 * @param did
	 * @return 影响行数
	 */
	@Override
	public boolean deleteByDid(String did) {
		return dchyBgzzxxDao.deleteByDid(did) > 0 ? true : false;
	}

	@Override
	public List<BgZzxxVO> queryByState(String state) {
		return dchyBgzzxxDao.queryByState(state);
	}
}