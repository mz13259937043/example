package com.github.DchyService.dao;

import com.github.DchyService.entity.DchyFbxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchyFbxx)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-20 18:37:24
 */
@Repository
@org.apache.ibatis.annotations.Mapper
public interface DchyFbxxDao {

    int addFbxx(DchyFbxx fbxx);

    DchyFbxx getFbxx(DchyFbxx fbxx);

    List<DchyFbxx> getFbxxList(DchyFbxx fbxx);
    List<Map<String,Object>> findMyProByFbxx(DchyFbxx fbxx);

    int renewFbxx(DchyFbxx fbxx);

    List<Map<String, Object>> getFbxxGg(String str);

    boolean deleteFbxxByKey(String id);

    List<DchyFbxx> getFbxxLifecycle();

	Integer findfwCount(String did, String state);

	List<Map<String,Object>> getFbxxGgByDid(String str, String did,String wtqr);

    String getSequence(@Param("seqName")String seqName);
    
    List<DchyFbxx> getFbxxByPersonal(String idcard);

    Map<String,Object> getCustomizeFbxx(String id);
}