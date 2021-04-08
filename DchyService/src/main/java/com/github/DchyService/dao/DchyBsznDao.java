package com.github.DchyService.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.DchyService.entity.Policy;
@Repository
@org.apache.ibatis.annotations.Mapper
public interface DchyBsznDao extends BaseMapper<Policy> {

	Policy selectByPolicyId(String id);

	Policy selectByPolicyItem(String item);

	void updateStateById(String id, String state, String dateStr);

	List<Policy> selectBykey(String key);
    
	List<Map<String, Object>> getPolicyList(Map<String, Object> map);
	
	void updatePageViewsById(String id);
}
