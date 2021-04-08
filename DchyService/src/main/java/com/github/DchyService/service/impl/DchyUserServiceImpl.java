package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyUserDao;
import com.github.DchyService.entity.DchyCompany;
import com.github.DchyService.entity.DchyPersonal;
import com.github.DchyService.service.DchyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * (DchyUser)表服务实现类
 *
 * @author makejava
 * @since 2020-09-28 15:03:01
 */
@Service
public class DchyUserServiceImpl implements DchyUserService {
	private Logger log = LoggerFactory.getLogger(DchyUserServiceImpl.class);
    @Resource
    private DchyUserDao dchyUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DchyCompany queryCompanyByLicenseNo(String tyshxydm) {
        return this.dchyUserDao.queryCompanyByLicenseNo(tyshxydm);
    }

    /**
     * 新增数据
     *
     * @param DchyUser 实例对象
     * @return 实例对象
     */
    @Override
    public int insertCompany(DchyCompany company) {
        int i = dchyUserDao.insertCompany(company);
        return i;
    }
    @Override
    public int insertPersonal(DchyPersonal personal) {
    	int i = dchyUserDao.insertPersonal(personal);
    	return i;
    }

    /**
     * 修改数据
     *
     * @param DchyUser 实例对象
     * @return 实例对象
     */
    @Override
    public int updateCompany(DchyCompany company) {
        int count = dchyUserDao.updateCompany(company);
        return count;
    }
	@Override
	public int updatePersonal(DchyPersonal personal) {
		 int count = dchyUserDao.updatePersonal(personal);
	        return count;
	}
	@Override
	public String getIdByLicenseNo(String tyshxydm) {
		
		return dchyUserDao.getIdByLicenseNo(tyshxydm);
	}
	@Override
	public DchyPersonal getPersonalByidCard(String idcard) {
		return dchyUserDao.getPersonalByidCard(idcard);
	}

	@Override
	public DchyCompany findCompanybyId(String id) {
		return dchyUserDao.findCompanybyId(id);
	}

	@Override
	public DchyPersonal findPersonalbyId(String id) {
		return dchyUserDao.findPersonalbyId(id);
	}

	@Override
	public DchyPersonal loginBytel(DchyPersonal user) {
		return dchyUserDao.loginBytel(user);
	}


}