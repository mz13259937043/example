package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyRoleDao;
import com.github.DchyService.entity.DchyRole;
import com.github.DchyService.service.DchyRoleService;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DchyRole)表服务实现类
 *
 * @author qufan
 * @since 2020-10-30 11:37:50
 */
@Service("dchyRoleService")
public class DchyRoleServiceImpl implements DchyRoleService {
    private Logger log = LoggerFactory.getLogger(DchyRoleServiceImpl.class);
    @Resource
    private DchyRoleDao dchyRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DchyRole queryById(String id) {
        return this.dchyRoleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<DchyRole> queryAllByLimit(int offset,int limit) {
        return this.dchyRoleDao.queryAllByLimit(offset,limit);
    }
    @Override
    public List<DchyRole> queryAll() {
        return this.dchyRoleDao.queryAllRole();
    }
   

    /**
     * 新增数据
     *
     * @param dchyRole 实例对象
     * @return 实例对象
     */
    @Override
    public DchyRole insert(DchyRole dchyRole) {
        this.dchyRoleDao.insert(dchyRole);
        return dchyRole;
    }

    /**
     * 修改数据
     *
     * @param dchyRole 实例对象
     * @return 实例对象
     */
    @Override
    public DchyRole update(DchyRole dchyRole) {
        this.dchyRoleDao.update(dchyRole);
        return this.queryById(dchyRole.getId());
    }

    
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.dchyRoleDao.deleteById(id) > 0;
    }

	@Override
	public List<DchyRole> findRoleByLimit(int offset, int limit, String str) {
		return dchyRoleDao.findRoleByLimit(offset, limit, str);
	}
	
	@Override
	public List<DchyRole> findRole(String str) {
		return dchyRoleDao.findRole(str);
	}

	@Override
	public DchyRole queryByRole(String role) {
		return dchyRoleDao.queryByRole(role);
	}
}