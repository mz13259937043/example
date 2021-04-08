package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyFbxx;
import com.github.DchyService.entity.DchyProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * (DchyFbxx)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-20 18:37:24
 */
@Repository
@Mapper
public interface DchyProcessDao {

    int addByEntity(DchyProcess process);

    int delById(String id);

    int updByEntity(DchyProcess process);

    List<DchyProcess> getList(String str);

    DchyProcess getByName(String name);

    DchyProcess getByAlias(String alias);

    DchyProcess getById(String id);

    DchyProcess getBy1and2(String oneAlias, String twoAlias);
}