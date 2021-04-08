package com.github.DchyService.service;

import com.github.DchyService.entity.DchyFbxx;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchyFbxx)表服务接口
 *
 * @author makejava
 * @since 2020-10-20 18:37:24
 */
public interface DchyFbxxService {

//获取某个发布信息
	DchyFbxx getFbxx(DchyFbxx fbxx);

//获取发布信息列表
	List<DchyFbxx> getFbxxList(DchyFbxx fbxx);

// 查询业务列表
	List<Map<String, Object>> findMyProByFbxx(DchyFbxx fbxx);

	int renewFbxx(DchyFbxx fbxx);

	List<Map<String, Object>> getFbxxGg(String str);

	boolean deleteFbxxByKey(String id);

	/**
	 * @Description //操作件的，里面包含对采购公告的保存修改，发布、立即发布
	 * @Author maoZhan
	 * @Date 2020/11/4 10:08
	 * @param * @param data
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> operFbxx(String data);

	// 添加发布信息
	Map<String, Object> addFbxx(String data);

	// 公示发布信息
	Map<String, Object> releaseFbxx(String data);

	// 获取全周期主表信息
	List<DchyFbxx> getFbxxLifecycle();

	Integer findfwCount(String did, String state);

	List<Map<String, Object>> getFbxxGgByDid(String str, String did, String wtqr);

	/**
	 * @category 注册测绘师项目查询
	 * @return list
	 */
	List<DchyFbxx> getFbxxByPersonal(String idcard);
}