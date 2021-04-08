package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyRoleuserDao;
import com.github.DchyService.entity.DchyRoleuser;
import com.github.DchyService.service.DchyRoleuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DchyRoleuser)表服务实现类
 *
 * @author qufan
 * @since 2020-10-30 11:38:44
 */
@Service("dchyRoleuserService")
public class DchyRoleuserServiceImpl implements DchyRoleuserService {
	private Logger log = LoggerFactory.getLogger(DchyRoleuserServiceImpl.class);
	@Resource
	private DchyRoleuserDao dchyRoleuserDao;

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public DchyRoleuser queryById(String id) {
		return this.dchyRoleuserDao.queryById(id);
	}

	/**
	 * 查询多条数据
	 *
	 * @param offset 查询起始位置
	 * @param limit  查询条数
	 * @return 对象列表
	 */
	@Override
	public List<DchyRoleuser> queryAllByLimit() {
		return this.dchyRoleuserDao.queryAllByLimit();
	}

	/**
	 * 新增数据
	 *
	 * @param dchyRoleuser 实例对象
	 * @return 实例对象
	 */
	@Override
	public DchyRoleuser insert(DchyRoleuser dchyRoleuser) {
		this.dchyRoleuserDao.insert(dchyRoleuser);
		return dchyRoleuser;
	}

	/**
	 * 修改数据
	 *
	 * @param dchyRoleuser 实例对象
	 * @return 实例对象
	 */
	@Override
	public DchyRoleuser update(DchyRoleuser dchyRoleuser) {
		this.dchyRoleuserDao.update(dchyRoleuser);
		return this.queryById(dchyRoleuser.getId());
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public boolean deleteById(String id) {
		return this.dchyRoleuserDao.deleteById(id) > 0;
	}

	@Override
	public List<DchyRoleuser> queryAllByUid(String uid) {
		return dchyRoleuserDao.queryAllByUid(uid);
	}

	@Override
	public List<DchyRoleuser> queryAllByBgUid(String bguid) {
		return dchyRoleuserDao.queryAllByBgUid(bguid);
	}

	@Override
	public boolean deleteByUid(String uid) {
		return dchyRoleuserDao.deleteByUid(uid) > 0;
	}

	@Override
	public boolean deleteByRoleId(String roleid) {
		return dchyRoleuserDao.deleteByRoleId(roleid) > 0;
	}

	@Override
	public List<DchyRoleuser> queryByRoleId(String roleid) {
		return dchyRoleuserDao.queryByRoleId(roleid);
	}

	@Override
	public List<DchyRoleuser> queryByRoleIdLimit(String roleid, int offset, int limit) {
		return dchyRoleuserDao.queryByRoleIdLimit(roleid, offset, limit);
	}

	@Override
	public boolean deleteRoleByUser(String userid, String roleid) {
		return dchyRoleuserDao.deleteRoleByUser(userid, roleid) > 0;
	}
}