package com.github.DchyService.service.impl;

import com.github.DchyService.VO.ZzxxVO;
import com.github.DchyService.dao.DchyZzxxDao;
import com.github.DchyService.entity.DchyZzxx;
import com.github.DchyService.service.DchyZzxxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DchyZzxx)表服务实现类
 *
 * @author qufan
 * @since 2020-10-27 19:21:33
 */
@Service("dchyZzxxService")
public class DchyZzxxServiceImpl implements DchyZzxxService {
	private Logger log = LoggerFactory.getLogger(DchyZzxxServiceImpl.class);
	@Resource
	private DchyZzxxDao dchyZzxxDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public DchyZzxx queryById(String id) {
		return this.dchyZzxxDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	@Override
	public List<ZzxxVO> queryAllByLimit(int offset, int limit,String state) {
		return this.dchyZzxxDao.queryAllByLimit(offset, limit,state);
	}

	/**
	 * 新增数据
	 *
	 * @param dchyZzxx 实例对象
	 * @return 实例对象
	 */
	@Override
	public DchyZzxx insert(DchyZzxx zzxx) {
		this.dchyZzxxDao.insert(zzxx);
		return zzxx;
	}

	/**
	 * 修改数据
	 *
	 * @param dchyZzxx 实例对象
	 * @return boolean
	 */
	@Override
	public boolean update(DchyZzxx dchyZzxx) {
		return dchyZzxxDao.update(dchyZzxx) > 0 ? true : false;
	}

	/**
	 * 通过dID查询单条数据
	 *
	 * @param did 企业主键
	 * @return 实例对象
	 */
	@Override
	public DchyZzxx queryByDid(String did) {
		return dchyZzxxDao.queryByDid(did);
	}

	/**
	 * 通过企业ID和状态查询单条数据
	 *
	 * @param 企业主键和状态
	 * @return 实例对象
	 */
	@Override
	public DchyZzxx queryByDidAndState(String did, String state) {
		return dchyZzxxDao.queryByDidAndState(did, state);
	}

	@Override
	public List<ZzxxVO> queryByZzxx(DchyZzxx zzxx) {
		return dchyZzxxDao.queryAll(zzxx);
	}

	@Override
	public List<ZzxxVO> queryAllZzxx(String state) {
		return dchyZzxxDao.queryAllZzxx(state);
	}

	@Override
	public List<ZzxxVO> queryGlZzxx(int offset, int limit) {
		return dchyZzxxDao.queryGlZzxx(offset,limit);
	}

	@Override
	public List<ZzxxVO> queryAllGlZzxx() {
		return dchyZzxxDao.queryAllGlZzxx();
	}

	@Override
	public List<ZzxxVO> queryByZzxxAll(DchyZzxx zzxx) {
		return dchyZzxxDao.queryZzxxAll(zzxx);
	}
}