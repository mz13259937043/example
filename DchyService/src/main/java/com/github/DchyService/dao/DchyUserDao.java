package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyCompany;
import com.github.DchyService.entity.DchyPersonal;

import org.apache.ibatis.annotations.Mapper;


/**
 * (DchyUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-28 15:03:00
 */
@Mapper
public interface DchyUserDao {

    /**
     * 通过统一社会信用代码查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
	DchyCompany queryCompanyByLicenseNo(String tyshxydm);

     /** 修改数据
     *
     * @param DchyUser 实例对象
     * @return 影响行数
     */
    int updateCompany(DchyCompany company);


	int insertCompany(DchyCompany company);

	int insertPersonal(DchyPersonal personal);

	int updatePersonal(DchyPersonal personal);

	String getIdByLicenseNo(String tyshxydm);

	DchyPersonal getPersonalByidCard(String idcard);

	DchyCompany findCompanybyId(String id);

	DchyPersonal findPersonalbyId(String id);


    DchyPersonal loginBytel(DchyPersonal user);
}