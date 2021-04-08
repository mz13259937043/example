package com.github.DchyService.service;

import com.github.DchyService.entity.DchyCompany;
import com.github.DchyService.entity.DchyPersonal;



/**
 * (DchyUser)表服务接口
 *
 * @author makejava
 * @since 2020-09-28 15:03:00
 */
public interface DchyUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
	DchyCompany queryCompanyByLicenseNo(String tyshxydm);

    /** 新增数据
    *
    * @param DchyCompany 实例对象
    * @return 影响行数
    */
   int insertCompany(DchyCompany dchyUser);


    /** 修改数据
    *
    * @param dchyCompany 实例对象
    * @return 影响行数
    */
   int updateCompany(DchyCompany dchyCompany);

	int insertPersonal(DchyPersonal personal);

	int updatePersonal(DchyPersonal personal);

	String getIdByLicenseNo(String tyshxydm);

	DchyPersonal getPersonalByidCard(String idCard);

	DchyCompany findCompanybyId(String id);

	DchyPersonal findPersonalbyId(String id);


	//电话登录
    DchyPersonal loginBytel(DchyPersonal user);
}