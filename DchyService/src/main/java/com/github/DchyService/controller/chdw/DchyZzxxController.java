package com.github.DchyService.controller.chdw;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.VO.BgZzxxVO;
import com.github.DchyService.VO.ZzxxVO;
import com.github.DchyService.entity.DchyBgpersonal;
import com.github.DchyService.entity.DchyBgxx;
import com.github.DchyService.entity.DchyBgzzxx;
import com.github.DchyService.entity.DchyCompany;
import com.github.DchyService.entity.DchyLog;
import com.github.DchyService.entity.DchyLspersonal;
import com.github.DchyService.entity.DchyLszzxx;
import com.github.DchyService.entity.DchyPersonal;
import com.github.DchyService.entity.DchyZzxx;
import com.github.DchyService.service.DchyBgpersonalService;
import com.github.DchyService.service.DchyBgxxService;
import com.github.DchyService.service.DchyBgzzxxService;
import com.github.DchyService.service.DchyFbxxService;
import com.github.DchyService.service.DchyLogService;
import com.github.DchyService.service.DchyLspersonalService;
import com.github.DchyService.service.DchyLszzxxService;
import com.github.DchyService.service.DchyPersonalService;
import com.github.DchyService.service.DchyRoleuserService;
import com.github.DchyService.service.DchyUserService;
import com.github.DchyService.service.DchyZzxxService;
import com.github.DchyService.service.FjService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * (DchyZzxx)表控制层
 *
 * @author qufan
 * @since 2020-10-27 19:21:33
 */
@RestController
@RequestMapping("/zzxx")
@CrossOrigin
public class DchyZzxxController {
	private Logger log = LoggerFactory.getLogger(DchyZzxxController.class);
	/**
	 * 服务对象
	 */
	@Resource
	private DchyBgxxService dchyBgxxService;

	@Resource
	private DchyZzxxService dchyZzxxService;

	@Resource
	private DchyBgzzxxService dchyBgZzxxService;

	@Resource
	private DchyLszzxxService dchylSZzxxService;

	@Resource
	private DchyUserService dchyUserService;

	@Resource
	private DchyBgpersonalService dchybgpersonalService;

	@Resource
	private DchyLogService dchyLogService;

	@Resource
	private DchyPersonalService dchyPersonalService;

	@Resource
	private DchyLspersonalService dchyLspersonalService;

	@Resource
	private DchyRoleuserService dchyRoleuserService;

	@Resource
	private FjService fjService;

	@Resource
	private DchyFbxxService dchyFbxxService;

	/**
	 * @category 新增资质信息等待审批
	 * @author qufan
	 * @param bgzzxx对象
	 * @return 结果信息
	 */

	@RequestMapping(value = "/addBgZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> addZzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		DchyBgxx bgxx = new DchyBgxx();
		// 获取人员信息
		JSONArray listjson = jsons.getJSONArray("bgpersonallistjson");
		List<DchyBgpersonal> bgpersonallist = listjson.toJavaList(DchyBgpersonal.class);
		// 获取资质信息
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx = bgzzxxjson.toJavaObject(DchyBgzzxx.class);

		// 获取日志信息
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);

		// 获取企业信息
		JSONObject companyjson = jsons.getJSONObject("companyjson");
		DchyCompany company = companyjson.toJavaObject(DchyCompany.class);
		// 更新企业信息
		dchyUserService.updateCompany(company);

		BgZzxxVO zzxx = dchyBgZzxxService.queryByDid(bgzzxx.getBgdid());
		if (zzxx == null) {
			bgzzxx.setBgsqsj(new Timestamp(new Date().getTime()));
			log.setFssj(new Timestamp(new Date().getTime()));
			bgxx = new DchyBgxx();
			bgxx.setBgdid(bgzzxx.getBgdid());
			bgxx.setBgyy("首次申请");
			// 变更信息表插入数据返回对象
			bgxx = dchyBgxxService.insert(bgxx);
			log.setPid(bgzzxx.getId());
			// 插入日志信息
			DchyLog dchylog = dchyLogService.insert(log);
			DchyBgxx bgxx1 = dchyBgxxService.queryByDidAndState(bgzzxx.getBgdid(), "待审核");

			bgzzxx.setBgbid(bgxx1.getId());
			bgzzxx.setLogid(dchylog.getId());
			bgzzxx = dchyBgZzxxService.insert(bgzzxx);

			if (bgpersonallist != null && bgpersonallist.size() > 0) {
				// 人员部分代码
				for (DchyBgpersonal dchyBgpersonal2 : bgpersonallist) {
					DchyPersonal personal = dchyPersonalService.queryByIdcard(dchyBgpersonal2.getBgidcard());
					DchyPersonal personal2 = dchyPersonalService.queryByPhone(dchyBgpersonal2.getBgphonenum());
					DchyBgpersonal bgpersonal = dchybgpersonalService.queryByIdcard(dchyBgpersonal2.getBgidcard());
					DchyBgpersonal bgpersonal2 = dchybgpersonalService.queryByPhone(dchyBgpersonal2.getBgphonenum());
					if (personal != null || bgpersonal != null || personal2 != null || bgpersonal2 != null) {
						throw new ArithmeticException("该人员已注册到其他单位");
					} else {
						bgxx = dchyBgxxService.queryByDidAndState(dchyBgpersonal2.getBgdid(), "待审核");
						dchyBgpersonal2.setBgbid(bgxx1.getId());
						dchyBgpersonal2.setLogid(log.getId());
						dchyBgpersonal2.setRoleid("0");
						dchybgpersonalService.insert(dchyBgpersonal2);
					}
				}
			}

			result.put("msg", "添加成功");
			result.put("dchylog", dchylog);
			result.put("bgxx", bgxx);
		} else {
			result.put("msg", "提交失败，重复提交数据");
			result.put("dchylog", null);
			result.put("bgxx", null);
		}
		return result;
	}

	/**
	 * @category 变更资质信息等待审批
	 * @author qufan
	 * @param bgzzxx对象
	 * @return 结果信息
	 */

	@RequestMapping(value = "/addUpdateZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> addUpdateZzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		DchyBgxx bgxx = new DchyBgxx();
		// 获取人员信息
		JSONArray listjson = jsons.getJSONArray("bgpersonallistjson");
		List<DchyBgpersonal> bgpersonallist = listjson.toJavaList(DchyBgpersonal.class);
		// 获取资质信息
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		// 获取日志信息
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);

		// 获取企业信息
		JSONObject companyjson = jsons.getJSONObject("companyjson");
		DchyCompany company = companyjson.toJavaObject(DchyCompany.class);
		// 更新企业信息
		dchyUserService.updateCompany(company);

		BgZzxxVO zzxx = dchyBgZzxxService.queryByDid(bgzzxx.getBgdid());
		if (zzxx == null) {
			bgzzxx.setBgsqsj(new Timestamp(new Date().getTime()));
			log.setFssj(new Timestamp(new Date().getTime()));
			bgxx = new DchyBgxx();
			bgxx.setBgdid(bgzzxx.getBgdid());
			bgxx.setBgyy("资质变更");
			// 变更信息表插入数据返回对象
			bgxx = dchyBgxxService.insert(bgxx);
			// 插入日志信息
			log.setPid(bgzzxx.getId());
			DchyLog dchylog = dchyLogService.insert(log);
			DchyBgxx bgxx1 = dchyBgxxService.queryByDidAndState(bgzzxx.getBgdid(), "待审核");
			bgzzxx.setBgbid(bgxx1.getId());
			bgzzxx.setLogid(dchylog.getId());
			bgzzxx = dchyBgZzxxService.insert(bgzzxx);

			if (bgpersonallist != null && bgpersonallist.size() > 0) {
				// 人员部分代码
				for (DchyBgpersonal dchyBgpersonal2 : bgpersonallist) {
					DchyPersonal personal = dchyPersonalService.queryByIdcard(dchyBgpersonal2.getBgidcard());
					DchyPersonal personal2 = dchyPersonalService.queryByPhone(dchyBgpersonal2.getBgphonenum());
					DchyBgpersonal bgpersonal = dchybgpersonalService.queryByIdcard(dchyBgpersonal2.getBgidcard());
					DchyBgpersonal bgpersonal2 = dchybgpersonalService.queryByPhone(dchyBgpersonal2.getBgphonenum());
					if (((personal != null && !personal.getDid().equals(dchyBgpersonal2.getBgdid()) && personal2 != null
							&& personal2.getDid().equals(dchyBgpersonal2.getBgdid()))
							|| (personal != null && !personal.getDid().equals(dchyBgpersonal2.getBgdid())
									&& bgpersonal2 != null
									&& bgpersonal2.getBgdid().equals(dchyBgpersonal2.getBgdid())))
							|| ((bgpersonal != null && !bgpersonal.getBgdid().equals(dchyBgpersonal2.getBgdid()))
									&& personal2 != null && personal2.getDid().equals(dchyBgpersonal2.getBgdid())
									|| (bgpersonal != null && !bgpersonal.getBgdid().equals(dchyBgpersonal2.getBgdid()))
											&& bgpersonal2 != null
											&& bgpersonal2.getBgdid().equals(dchyBgpersonal2.getBgdid()))) {
						throw new ArithmeticException("该人员已注册到其他单位");
					} else {
						bgxx = dchyBgxxService.queryByDidAndState(dchyBgpersonal2.getBgdid(), "待审核");
						dchyBgpersonal2.setBgbid(bgxx.getId());
						dchyBgpersonal2.setLogid(log.getId());
						dchybgpersonalService.insert(dchyBgpersonal2);
					}
				}
			}
			result.put("msg", "添加成功");
			result.put("dchylog", dchylog);
			result.put("bgxx", bgxx);
		} else {
			result.put("msg", "提交失败，重复提交数据");
			result.put("dchylog", null);
			result.put("bgxx", null);
		}
		return result;
	}

	/**
	 * @category 显示待审批资质信息详情页
	 * @author qufan
	 * @param did
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectBgZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectBgZzxx(@RequestBody DchyBgzzxx bgzzxx) {
		Map<String, Object> result = new HashMap<>();
		BgZzxxVO rebgzzxx = dchyBgZzxxService.queryByDidAndState(bgzzxx.getBgdid(), "待审核");
		DchyCompany company = dchyUserService.findCompanybyId(rebgzzxx.getBgdid());
		DchyBgxx bgxx = dchyBgxxService.queryById(rebgzzxx.getBgbid());
		// DchyFjFolder flod= fjService.getFolderByKey(bgzzxx.getBgzzfj());
		List<DchyBgpersonal> personal = dchybgpersonalService.queryByDidAndState(bgzzxx.getBgdid(), "待审核");
		DchyLog log = dchyLogService.findById(rebgzzxx.getLogid());
		log.setJssj(new Timestamp(new Date().getTime()));
		log.setSfyd("已读");
		DchyLog log1 = dchyLogService.update(log);
		if (rebgzzxx.getId() != null) {
			result.put("msg", "查询成功");
			result.put("zzxxdate", rebgzzxx);
			result.put("personallist", personal);
			result.put("companydate", company);
			result.put("bgxx", bgxx);
			result.put("log", log1);
			// result.put("flod", flod);
		} else {
			result.put("msg", "查询失败");
			result.put("zzxxdate", null);
			result.put("personallist", null);
			result.put("companydate", company);
			result.put("bgxx", null);
			result.put("log", null);
			// result.put("flod", null);
		}
		return result;
	}

	/**
	 * @category 显示初审状态资质信息详情页
	 * @author qufan
	 * @param did
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectcsBgZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectcsBgZzxx(@RequestBody DchyBgzzxx bgzzxx) {
		Map<String, Object> result = new HashMap<>();
		BgZzxxVO rebgzzxx = dchyBgZzxxService.queryByDidAndState(bgzzxx.getBgdid(), "初审");
		DchyCompany company = dchyUserService.findCompanybyId(rebgzzxx.getBgdid());
		DchyBgxx bgxx = dchyBgxxService.queryById(rebgzzxx.getBgbid());
		// DchyFjFolder flod= fjService.getFolderByKey(bgzzxx.getBgzzfj());
		List<DchyBgpersonal> personal = dchybgpersonalService.queryByDidAndState(bgzzxx.getBgdid(), "待审核");
		DchyLog log = dchyLogService.findById(rebgzzxx.getLogid());
		log.setJssj(new Timestamp(new Date().getTime()));
		log.setSfyd("已读");
		DchyLog log1 = dchyLogService.update(log);
		if (rebgzzxx != null && company != null) {
			result.put("msg", "查询成功");
			result.put("zzxxdate", rebgzzxx);
			result.put("companydate", company);
			result.put("personallist", personal);
			result.put("bgxx", bgxx);
			result.put("log", log1);
			// result.put("flod", flod);
		} else {
			result.put("msg", "未查询到资质信息");
			result.put("zzxxdate", null);
			result.put("personallist", null);
			result.put("companydate", company);
			result.put("bgxx", null);
			result.put("log", null);
			// result.put("flod", null);
		}
		return result;
	}

	/**
	 * @category 显示终审状态资质信息详情页
	 * @author qufan
	 * @param did
	 * @return 结果信息
	 */
	/*
	 * @RequestMapping(value = "/selectzsBgZzxx", method = RequestMethod.POST)
	 * 
	 * @CrossOrigin
	 * 
	 * @Transactional public Map<String, Object> selectzsBgZzxx(@RequestBody
	 * DchyBgzzxx bgzzxx) { Map<String, Object> result = new HashMap<>(); DchyBgzzxx
	 * rebgzzxx = dchyBgZzxxService.queryByDidAndState(bgzzxx.getBgdid(), "终审");
	 * DchyCompany company = dchyUserService.findCompanybyId(bgzzxx.getBgdid());
	 * DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx.getBgbid());
	 * List<DchyBgpersonal> personal =
	 * dchybgpersonalService.queryByDidAndState(bgzzxx.getBgdid(), "待审核"); DchyLog
	 * log = dchyLogService.findById(bgzzxx.getLogid()); log.setJssj(new
	 * Timestamp(new Date().getTime())); log.setSfyd("已读"); DchyLog log1 =
	 * dchyLogService.update(log); Map<DchyBgpersonal, List<DchyRoleuser>> map = new
	 * HashMap<>(); List<Map<DchyBgpersonal, List<DchyRoleuser>>> personallist = new
	 * ArrayList<>(); for (DchyBgpersonal dchyBgpersonal : personal) {
	 * List<DchyRoleuser> roleuser =
	 * dchyRoleuserService.queryAllByUid(dchyBgpersonal.getId());
	 * map.put(dchyBgpersonal, roleuser); personallist.add(map); } if (rebgzzxx !=
	 * null && company != null) { result.put("msg", "查询成功"); result.put("zzxxdate",
	 * rebgzzxx); result.put("companydate", company); result.put("personallist",
	 * personallist); result.put("bgxx", bgxx); result.put("log", log1); } else {
	 * result.put("msg", "查询失败"); } return result; }
	 */
	/**
	 * @category 显示未通过状态资质信息详情页
	 * @author qufan
	 * @param did
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectwtgBgZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectwtgBgZzxx(@RequestBody DchyBgzzxx bgzzxx) {
		Map<String, Object> result = new HashMap<>();
		BgZzxxVO rebgzzxx = dchyBgZzxxService.queryByDidAndState(bgzzxx.getBgdid(), "未通过");
		DchyCompany company = dchyUserService.findCompanybyId(rebgzzxx.getBgdid());
		DchyBgxx bgxx = dchyBgxxService.queryById(rebgzzxx.getBgbid());
		// DchyFjFolder flod= fjService.getFolderByKey(bgzzxx.getBgzzfj());
		List<DchyBgpersonal> personal = dchybgpersonalService.queryByDidAndState(bgzzxx.getBgdid(), "待审核");
		DchyLog log = dchyLogService.findById(rebgzzxx.getLogid());
		log.setJssj(new Timestamp(new Date().getTime()));
		log.setSfyd("已读");
		DchyLog log1 = dchyLogService.update(log);
		if (rebgzzxx != null && company != null) {
			result.put("msg", "查询成功");
			result.put("zzxxdate", rebgzzxx);
			result.put("companydate", company);
			result.put("personallist", personal);
			result.put("bgxx", bgxx);
			result.put("log", log1);
			// result.put("flod", flod);
		} else {
			result.put("msg", "查询失败");
			result.put("zzxxdate", null);
			result.put("personallist", null);
			result.put("companydate", company);
			result.put("bgxx", null);
			result.put("log", null);
			// result.put("flod", null);
		}
		return result;
	}

	/**
	 * @category 显示正式资质信息详情页
	 * @author qufan
	 * @param did
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectZzxx(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		String did = (String) map.get("did");
		DchyZzxx zzxx = dchyZzxxService.queryByDidAndState(did, "正常");
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(did);
		DchyCompany company = dchyUserService.findCompanybyId(did);
		if (company != null) {
			result.put("msg", "查询成功");
			result.put("zzxxdate", zzxx);
			result.put("companydate", company);
			result.put("bgzzxxdate", bgzzxx);
		} else {
			result.put("msg", "该企业暂无资质信息");
			result.put("zzxxdate", null);
			result.put("companydate", company);
			result.put("bgzzxxdate", null);
		}
		return result;
	}

	/**
	 * @category 根据对象查询资质
	 * @author qufan
	 * @param did
	 * @return 结果信息
	 */
	@RequestMapping(value = "/findZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> findZzxx(@RequestBody DchyZzxx zzxx) {
		Map<String, Object> result = new HashMap<>();
		zzxx.setState("正常");
		dchyZzxxService.queryByZzxxAll(zzxx);
		List<ZzxxVO> list = new ArrayList<ZzxxVO>();
		PageHelper.startPage(zzxx.getPageNum(), zzxx.getPageSize());
		list = dchyZzxxService.queryByZzxxAll(zzxx);
		PageInfo<ZzxxVO> pageInfo = new PageInfo<ZzxxVO>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("zzxxdate", pageInfo.getList());
//		zzxx.setLimit((int) (zzxx.getPageSize() * zzxx.getPageNum()));
//		zzxx.setOffset((int) (1 + zzxx.getPageSize() * (zzxx.getPageNum() - 1)));
//		
//		
//		List<ZzxxVO> zzxxlist = dchyZzxxService.queryByZzxx(zzxx);
//		List<ZzxxVO> data2 = dchyZzxxService.queryByZzxxAll(zzxx);
//		result.put("listSize", data2.size());
//		result.put("zzxxdate", zzxxlist);
		return result;
	}

	/**
	 * @category 资质信息审批通过
	 * @author qufan
	 * @param bgzzxx对象
	 * @return 结果信息
	 */
	@RequestMapping(value = "/updateBgzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updateBgzzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		// 获取人员信息
		JSONArray listjson = jsons.getJSONArray("bgpersonallistjson");
		List<DchyBgpersonal> bgpersonallist = listjson.toJavaList(DchyBgpersonal.class);
		// 获取资质信息
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx1 = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(bgzzxx1.getBgdid());

		// 获取日志信息,插入新日志
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);
		log.setFssj(new Timestamp(new Date().getTime()));
		log = dchyLogService.insert(log);

		// 在资质表查询是否存该公司资质
		DchyZzxx rezzxx = dchyZzxxService.queryByDid(bgzzxx.getBgdid());
		// 按变更表id查找出变更表的信息
		DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx.getBgbid());
		// 修改变更状态
		bgxx.setBgjg("审核通过");
		bgxx.setBgstate("审核通过");
		// 更新变更记录表
		dchyBgxxService.update(bgxx);
		// 变更旧的日志表
		DchyLog dchyLog = dchyLogService.findById(bgzzxx.getLogid());
		dchyLog.setClzt("已办");
		dchyLog.setJssj(new Timestamp(new Date().getTime()));
		dchyLogService.update(dchyLog);
		// 资质表未找到记录
		if (rezzxx == null) {
			// 新建历史数据，将变更前资质信息插入历史数据
			DchyLszzxx lszzxx1 = new DchyLszzxx();
			lszzxx1.setLsdid(null);
			lszzxx1.setLszsbh(null);
			lszzxx1.setLszsyxq(null);
			lszzxx1.setLszzdj(null);
			lszzxx1.setLsfzbm(null);
			lszzxx1.setLszzfj(null);
			lszzxx1.setLszyfw(null);
			lszzxx1.setBgbid(bgzzxx.getBgbid());
			lszzxx1.setLsstate("变更前");
			lszzxx1.setLsdchyywfw(bgzzxx.getBgdchyywfw());
			lszzxx1 = dchylSZzxxService.insert(lszzxx1);
			// 新建资质信息，插入资质信息表
			DchyZzxx zzxx = new DchyZzxx();
			zzxx.setDid(bgzzxx.getBgdid());
			zzxx.setZsbh(bgzzxx.getBgzsbh());
			zzxx.setZsyxq(bgzzxx.getBgzsyxq());
			zzxx.setZzdj(bgzzxx.getBgzzdj());
			zzxx.setFzbm(bgzzxx.getBgfzbm());
			zzxx.setZzfj(bgzzxx.getBgzzfj());
			zzxx.setZyfw(bgzzxx.getBgzyfw());
			zzxx.setDchyywfw(bgzzxx.getBgdchyywfw());
			zzxx = dchyZzxxService.insert(zzxx);
			// 新建历史数据，将变更后资质信息插入历史数据
			DchyLszzxx lszzxx2 = new DchyLszzxx();
			lszzxx2.setLsdid(bgzzxx.getBgdid());
			lszzxx2.setLszsbh(bgzzxx.getBgzsbh());
			lszzxx2.setLszsyxq(bgzzxx.getBgzsyxq());
			lszzxx2.setLszzdj(bgzzxx.getBgzzdj());
			lszzxx2.setLsfzbm(bgzzxx.getBgfzbm());
			lszzxx2.setLszzfj(bgzzxx.getBgzzfj());
			lszzxx2.setLszyfw(bgzzxx.getBgzyfw());
			lszzxx2.setBgbid(bgzzxx.getBgbid());
			lszzxx2.setLsdchyywfw(bgzzxx.getBgdchyywfw());
			lszzxx2.setLsstate("变更后");
			lszzxx2 = dchylSZzxxService.insert(lszzxx2);
			// 删除审批表数据
			dchyBgZzxxService.deleteByDid(bgzzxx.getBgdid());

		} else {
			// 若资质信息存在
			// 将查询到的资质信息存入变更前信息
			DchyLszzxx lszzxx1 = new DchyLszzxx();
			lszzxx1.setLsdid(rezzxx.getDid());
			lszzxx1.setLszsbh(rezzxx.getZsbh());
			lszzxx1.setLszsyxq(rezzxx.getZsyxq());
			lszzxx1.setLszzdj(rezzxx.getZzdj());
			lszzxx1.setLsfzbm(rezzxx.getFzbm());
			lszzxx1.setLszzfj(rezzxx.getZzfj());
			lszzxx1.setLszyfw(rezzxx.getZyfw());
			lszzxx1.setBgbid(bgzzxx.getBgbid());
			lszzxx1.setLsstate("变更前");
			lszzxx1.setLsdchyywfw(bgzzxx.getBgdchyywfw());
			dchylSZzxxService.insert(lszzxx1);

			// 更改查询到的资质信息为审批表信息
			rezzxx.setDid(bgzzxx.getBgdid());
			rezzxx.setZsbh(bgzzxx.getBgzsbh());
			rezzxx.setZsyxq(bgzzxx.getBgzsyxq());
			rezzxx.setZzdj(bgzzxx.getBgzzdj());
			rezzxx.setFzbm(bgzzxx.getBgfzbm());
			rezzxx.setZzfj(bgzzxx.getBgzzfj());
			rezzxx.setZyfw(bgzzxx.getBgzyfw());
			rezzxx.setDchyywfw(bgzzxx.getBgdchyywfw());

			dchyZzxxService.update(rezzxx);

			// 将审批表数据写入历史变更后数据
			DchyLszzxx lszzxx2 = new DchyLszzxx();
			lszzxx2.setLsdid(bgzzxx.getBgdid());
			lszzxx2.setLszsbh(bgzzxx.getBgzsbh());
			lszzxx2.setLszsyxq(bgzzxx.getBgzsyxq());
			lszzxx2.setLszzdj(bgzzxx.getBgzzdj());
			lszzxx2.setLsfzbm(bgzzxx.getBgfzbm());
			lszzxx2.setLszzfj(bgzzxx.getBgzzfj());
			lszzxx2.setLszyfw(bgzzxx.getBgzyfw());
			lszzxx2.setBgbid(bgzzxx.getBgbid());
			lszzxx2.setLsdchyywfw(bgzzxx.getBgdchyywfw());
			lszzxx2.setLsstate("变更后");
			dchylSZzxxService.insert(lszzxx2);
			// 删除审批表数据
			dchyBgZzxxService.deleteByDid(bgzzxx.getBgdid());
		}
		if (bgpersonallist != null && bgpersonallist.size() > 0) {
			List<DchyPersonal> dchyPersonallist = dchyPersonalService.queryByDid(bgzzxx.getBgdid());
			for (DchyPersonal dchyPersonal : dchyPersonallist) {
				DchyLspersonal lspersonal1 = new DchyLspersonal();
				lspersonal1.setBgbid(bgxx.getId());
				lspersonal1.setLscreatetime(dchyPersonal.getCreatetime());
				lspersonal1.setLsdid(dchyPersonal.getDid());
				lspersonal1.setLsidcard(dchyPersonal.getIdcard());
				lspersonal1.setLsinfor(dchyPersonal.getInfor());
				lspersonal1.setLsname(dchyPersonal.getName());
				lspersonal1.setLspassword(dchyPersonal.getPassword());
				lspersonal1.setLsphonenum(dchyPersonal.getPhonenum());
				lspersonal1.setLstype(dchyPersonal.getType());
				lspersonal1.setLsstate("变更前");
				// 添加变更前历史数据
				dchyLspersonalService.insert(lspersonal1);
			}
			dchyPersonalService.deleteByDid(bgzzxx.getBgdid());
			for (DchyBgpersonal bg : bgpersonallist) {
				// 遍历传入人员
				// DchyPersonal dchyPersonal =
				// dchyPersonalService.queryByIdcard(bg.getBgidcard());
				// 判断在正式表是否有数据
//				if (dchyPersonal != null) {
//					// 有数据就遍历查询到的正式表数据，插入历史表
//					DchyLspersonal lspersonal1 = new DchyLspersonal();
//					lspersonal1.setBgbid(bg.getBgbid());
//					lspersonal1.setLscreatetime(dchyPersonal.getCreatetime());
//					lspersonal1.setLsdid(dchyPersonal.getDid());
//					lspersonal1.setLsidcard(dchyPersonal.getIdcard());
//					lspersonal1.setLsinfor(dchyPersonal.getInfor());
//					lspersonal1.setLsname(dchyPersonal.getName());
//					lspersonal1.setLspassword(dchyPersonal.getPassword());
//					lspersonal1.setLsphonenum(dchyPersonal.getPhonenum());
//					lspersonal1.setLstype(dchyPersonal.getType());
//					lspersonal1.setLsstate("删除人员");
//					// 添加变更前历史数据
//					dchyLspersonalService.insert(lspersonal1);
//
//					DchyLspersonal lspersonal2 = new DchyLspersonal();
//					lspersonal2.setBgbid(bg.getBgbid());
//					lspersonal2.setLscreatetime(bg.getBgcreatetime());
//					lspersonal2.setLsdid(bg.getBgdid());
//					lspersonal2.setLsidcard(bg.getBgidcard());
//					lspersonal2.setLsinfor(bg.getBginfor());
//					lspersonal2.setLsname(bg.getBgname());
//					lspersonal2.setLspassword(bg.getBgpassword());
//					lspersonal2.setLsphonenum(bg.getBgphonenum());
//					lspersonal2.setLstype(bg.getBgtype());
//					lspersonal2.setLsstate("添加人员");
//					// 添加变更后历史数据
//					dchyLspersonalService.insert(lspersonal2);
//
//					DchyPersonal personal2 = new DchyPersonal();
//					personal2.setCreatetime(bg.getBgcreatetime());
//					personal2.setDid(bg.getBgdid());
//					personal2.setIdcard(bg.getBgidcard());
//					personal2.setInfor(bg.getBginfor());
//					personal2.setName(bg.getBgname());
//					personal2.setPassword(bg.getBgpassword());
//					personal2.setPhonenum(bg.getBgphonenum());
//					personal2.setType(bg.getBgtype());
//					personal2.setState("正常");
//					// 删除正式表数据
//					dchyPersonalService.deleteById(dchyPersonal.getId());
//					// 添加正式表数据
//					dchyPersonalService.insert(personal2);
//					// 删除审核表数据
//					dchybgpersonalService.deleteById(bg.getId());
//				} else {

				// 无数据则变更前不需要添加历史数据
				DchyLspersonal lspersonal2 = new DchyLspersonal();
				lspersonal2.setBgbid(bg.getBgbid());
				lspersonal2.setLscreatetime(bg.getBgcreatetime());
				lspersonal2.setLsdid(bg.getBgdid());
				lspersonal2.setLsidcard(bg.getBgidcard());
				lspersonal2.setLsinfor(bg.getBginfor());
				lspersonal2.setLsname(bg.getBgname());
				lspersonal2.setLspassword(new Date().getTime() + "");
				lspersonal2.setLsphonenum(bg.getBgphonenum());
				lspersonal2.setLstype(bg.getBgtype());
				lspersonal2.setLsstate("变更后");
				// 变更后历史数据添加
				dchyLspersonalService.insert(lspersonal2);

				DchyPersonal personal2 = new DchyPersonal();
				personal2.setCreatetime(bg.getBgcreatetime());
				personal2.setDid(bg.getBgdid());
				personal2.setIdcard(bg.getBgidcard());
				personal2.setInfor(bg.getBginfor());
				personal2.setName(bg.getBgname());
				personal2.setPassword(new Date().getTime() + "");
				personal2.setPhonenum(bg.getBgphonenum());
				personal2.setType(bg.getBgtype());
				personal2.setState("正常");
				// 正式表数据添加
				dchyPersonalService.insert(personal2);

				// }
				// 审核表数据删除
				dchybgpersonalService.deleteByDid(bgzzxx.getBgdid());
			}
		}
		result.put("msg", "审核成功");
		return result;
	}

	/**
	 * @category 查询所有未冻结资质
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectAllZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectAllZzxx(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		int pageSize = (int) map.get("pageSize");
		int pageNum = (int) map.get("pageNum");
		String state = (String) map.get("state");
		List<ZzxxVO> list = new ArrayList<ZzxxVO>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyZzxxService.queryAllZzxx(state);
		if (list.size() > 0) {
			for (ZzxxVO zzxxVO : list) {
				if (zzxxVO != null) {
					zzxxVO.setBjl(dchyFbxxService.findfwCount(zzxxVO.getDid(), "已办结"));
				}
			}
		}
		PageInfo<ZzxxVO> pageInfo = new PageInfo<ZzxxVO>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("data", pageInfo.getList());
		return result;
	}

	/**
	 * @category 未通过审批资质信息被退回
	 * @author qufan
	 * @param bgzzxx对象
	 * @return 结果信息
	 */
	@RequestMapping(value = "/updatethBgzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updatethBgzzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();

		// 获取资质信息
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx1 = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(bgzzxx1.getBgdid());
		// 按变更表id查找出变更表的信息
		DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx.getBgbid());
		// 修改状态
		bgxx.setBgstate("未通过");
		boolean flag1 = dchyBgxxService.update(bgxx);
		// 变更日志表
		DchyLog dchyLog = dchyLogService.findById(bgzzxx.getLogid());
		dchyLog.setClzt("已办");
		dchyLog.setJssj(new Timestamp(new Date().getTime()));
		dchyLogService.update(dchyLog);

		// 获取日志信息,插入新日志
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);
		log.setFssj(new Timestamp(new Date().getTime()));
		log = dchyLogService.insert(log);

		// 修改状态
		DchyBgzzxx lszzxx2 = new DchyBgzzxx();
		lszzxx2.setId(bgzzxx.getId());
		lszzxx2.setBgstate("未通过");
		// 增加原因
		lszzxx2.setBgwtgyy(bgzzxx1.getBgwtgyy());
		lszzxx2.setLogid(log.getId());
		boolean rels2 = dchyBgZzxxService.update(lszzxx2);

		if (rels2 && flag1) {
			result.put("msg", "审核成功");
		} else {
			result.put("msg", "审核失败");
		}

		return result;
	}

	/**
	 * @category 未通过资质信息修改再提交审核
	 * @author qufan
	 * @param bgzzxx对象
	 * @return 结果信息
	 */
	@RequestMapping(value = "/updatewtBgzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updatewtBgzzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();

		// 获取资质信息
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx1 = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		// 获取人员信息
		JSONArray listjson = jsons.getJSONArray("bgpersonallistjson");
		List<DchyBgpersonal> bgpersonallist = listjson.toJavaList(DchyBgpersonal.class);

		// 按变更表id查找出变更表的信息
		DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx1.getBgbid());
		// 修改状态
		bgxx.setBgstate("待审核");
		dchyBgxxService.update(bgxx);
		// 变更日志表
		DchyLog dchyLog = dchyLogService.findById(bgzzxx1.getLogid());
		dchyLog.setClzt("已办");
		dchyLog.setJssj(new Timestamp(new Date().getTime()));
		dchyLogService.update(dchyLog);

		// 获取日志信息,插入新日志
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);
		log.setFssj(new Timestamp(new Date().getTime()));
		log = dchyLogService.insert(log);

		// 修改状态
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(bgzzxx1.getBgdid());
		bgzzxx1.setId(bgzzxx.getId());
		bgzzxx1.setBgstate("待审核");
		bgzzxx1.setBgwtgyy(null);
		bgzzxx1.setLogid(log.getId());
		dchyBgZzxxService.update(bgzzxx1);
		boolean flag = false;
		if (bgpersonallist != null && bgpersonallist.size() > 0) {
			flag = true;
			for (DchyBgpersonal bg : bgpersonallist) {
				// 遍历传入人员
				if (flag) {
					// 删除审批表数据
					dchybgpersonalService.deleteByDid(bg.getBgdid());
					flag = false;
				}
				DchyBgpersonal dchybgPersonal = dchybgpersonalService.queryByIdcard(bg.getBgidcard());
				DchyBgpersonal dchybgPersonal2 = dchybgpersonalService.queryByPhone(bg.getBgphonenum());
				DchyPersonal personal = dchyPersonalService.queryByIdcard(bg.getBgidcard());
				DchyPersonal personal2 = dchyPersonalService.queryByPhone(bg.getBgphonenum());
				if (((personal != null && !personal.getDid().equals(bg.getBgdid()) && personal2 != null
						&& personal2.getDid().equals(bg.getBgdid()))
						|| (personal != null && !personal.getDid().equals(bg.getBgdid()) && dchybgPersonal2 != null
								&& dchybgPersonal2.getBgdid().equals(bg.getBgdid())))
						|| ((dchybgPersonal != null && !dchybgPersonal.getBgdid().equals(bg.getBgdid()))
								&& personal2 != null && personal2.getDid().equals(bg.getBgdid())
								|| (dchybgPersonal != null && !dchybgPersonal.getBgdid().equals(bg.getBgdid()))
										&& dchybgPersonal2 != null
										&& dchybgPersonal2.getBgdid().equals(bg.getBgdid()))) {
					throw new ArithmeticException("该人员已注册到其他单位");
				} else {
					DchyBgpersonal bgpersonal = new DchyBgpersonal();
					bgpersonal.setBgcreatetime(bg.getBgcreatetime());
					bgpersonal.setBgbid(bg.getBgbid());
					bgpersonal.setBgdid(bg.getBgdid());
					bgpersonal.setBgidcard(bg.getBgidcard());
					bgpersonal.setBginfor(bg.getBginfor());
					bgpersonal.setBgname(bg.getBgname());
					bgpersonal.setBgpassword(bg.getBgpassword());
					bgpersonal.setBgphonenum(bg.getBgphonenum());
					bgpersonal.setBgtype(bg.getBgtype());
					bgpersonal.setLogid(bg.getLogid());
					bgpersonal.setRoleid(bg.getRoleid());
					bgpersonal.setBgstate("待审核");
					// 添加审批表数据
					dchybgpersonalService.insert(bgpersonal);
				}
			}
			result.put("msg", "审核成功");
		}
		return result;
	}

	/**
	 * @category 资质信息初审
	 * @author qufan
	 * @param bgzzxx对象
	 * @return 结果信息
	 */
	@RequestMapping(value = "/updatecsBgzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updatecsBgzzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();

		// 获取资质信息
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx1 = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(bgzzxx1.getBgdid());
		// 按变更表id查找出变更表的信息
		DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx.getBgbid());
		// 修改状态
		bgxx.setBgstate("初审");
		boolean flag1 = dchyBgxxService.update(bgxx);
		// 变更日志表
		DchyLog dchyLog = dchyLogService.findById(bgzzxx.getLogid());
		dchyLog.setClzt("已办");
		dchyLog.setJssj(new Timestamp(new Date().getTime()));
		dchyLogService.update(dchyLog);

		// 获取日志信息,插入新日志
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);
		log.setFssj(new Timestamp(new Date().getTime()));
		log = dchyLogService.insert(log);

		// 修改状态
		DchyBgzzxx lszzxx2 = new DchyBgzzxx();
		lszzxx2.setId(bgzzxx.getId());
		lszzxx2.setBgstate("初审");
		lszzxx2.setLogid(log.getId());
		boolean rels2 = dchyBgZzxxService.update(lszzxx2);

		if (rels2 && flag1) {
			result.put("msg", "审核成功");
		} else {
			result.put("msg", "审核失败");
		}

		return result;
	}

	/**
	 * @category 资质信息初审列表
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectAllCsZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectAllCsZzxx(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		// 每页显示数
		int pageSize = (int) map.get("pageSize");
		// 页码数
		int pageNum = (int) map.get("pageNum");

		List<BgZzxxVO> list = new ArrayList<BgZzxxVO>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyBgZzxxService.queryByState("待审核");
		PageInfo<BgZzxxVO> pageInfo = new PageInfo<BgZzxxVO>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("data", pageInfo.getList());
		return result;
	}

	/**
	 * @category 资质信息初审列表
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectAllZsZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectAllZsZzxx(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		// 每页显示数
		int pageSize = (int) map.get("pageSize");
		// 页码数
		int pageNum = (int) map.get("pageNum");

		List<BgZzxxVO> list = new ArrayList<BgZzxxVO>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyBgZzxxService.queryByState("初审");
		PageInfo<BgZzxxVO> pageInfo = new PageInfo<BgZzxxVO>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("data", pageInfo.getList());
		return result;
	}

	/**
	 * @category 查询提交审核信息详情
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectDwZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectDwZzxx(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		String did = (String) map.get("did");
		BgZzxxVO rebgzzxx = dchyBgZzxxService.queryByDid(did);
		DchyCompany company = dchyUserService.findCompanybyId(did);
		DchyBgxx bgxx = dchyBgxxService.queryById(rebgzzxx.getBgbid());
		// DchyFjFolder flod= fjService.getFolderByKey(bgzzxx.getBgzzfj());
		List<DchyBgpersonal> personal = dchybgpersonalService.queryByDidAndState(rebgzzxx.getBgdid(), "待审核");
		DchyLog log = dchyLogService.findById(rebgzzxx.getLogid());
		if (rebgzzxx != null && company != null) {
			result.put("msg", "查询成功");
			result.put("zzxxdate", rebgzzxx);
			result.put("companydate", company);
			result.put("personallist", personal);
			result.put("bgxx", bgxx);
			result.put("log", log);
			// result.put("flod", flod);
		} else {
			result.put("zzxxdate", null);
			result.put("companydate", company);
			result.put("personallist", null);
			result.put("bgxx", null);
			result.put("log", null);
			result.put("msg", "未查找到数据");
		}
		return result;
	}

	/**
	 * @category 资质信息终审
	 * @author qufan
	 * @param bgzzxx对象
	 * @return 结果信息
	 *//*
		 * @RequestMapping(value = "/updatezsBgzzxx", method = RequestMethod.POST)
		 * 
		 * @CrossOrigin
		 * 
		 * @Transactional public Map<String, Object> updatezsBgzzxx(@RequestBody
		 * JSONObject jsons) { Map<String, Object> result = new HashMap<>();
		 * 
		 * // 获取资质信息 JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		 * DchyBgzzxx bgzzxx = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		 * 
		 * // 按变更表id查找出变更表的信息 DchyBgxx bgxx =
		 * dchyBgxxService.queryById(bgzzxx.getBgbid()); // 修改状态 bgxx.setBgstate("终审");
		 * boolean flag1 = dchyBgxxService.update(bgxx); // 变更日志表 DchyLog dchyLog =
		 * dchyLogService.findById(bgzzxx.getLogid()); dchyLog.setClzt("已办");
		 * dchyLog.setJssj(new Timestamp(new Date().getTime()));
		 * dchyLogService.update(dchyLog);
		 * 
		 * // 获取日志信息,插入新日志 JSONObject logjson = jsons.getJSONObject("logjson"); DchyLog
		 * log = logjson.toJavaObject(DchyLog.class); log.setFssj(new Timestamp(new
		 * Date().getTime())); log = dchyLogService.insert(log);
		 * 
		 * // 修改状态 DchyBgzzxx lszzxx2 = new DchyBgzzxx(); lszzxx2.setId(bgzzxx.getId());
		 * lszzxx2.setBgstate("终审"); lszzxx2.setLogid(log.getId()); boolean rels2 =
		 * dchyBgZzxxService.update(lszzxx2);
		 * 
		 * if (rels2 && flag1) { result.put("msg", "审核成功"); } else { result.put("msg",
		 * "审核失败"); }
		 * 
		 * return result; }
		 */

	/**
	 * @category 冻结资质
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/djzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> djZzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		// 获取资质信息
		JSONObject zzxxjson = jsons.getJSONObject("zzxxjson");
		DchyZzxx zzxx = zzxxjson.toJavaObject(DchyZzxx.class);
		zzxx.setState("冻结");
		zzxx.setDjsj(new Timestamp(new Date().getTime()));
		dchyZzxxService.update(zzxx);
		result.put("msg", "冻结成功");
		return result;
	}

	/**
	 * @category 解冻资质
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/jdzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> jdZzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		// 获取资质信息
		JSONObject zzxxjson = jsons.getJSONObject("zzxxjson");
		DchyZzxx zzxx = zzxxjson.toJavaObject(DchyZzxx.class);
		zzxx.setState("正常");
		zzxx.setDjsj(null);
		dchyZzxxService.update(zzxx);
		result.put("msg", "解冻成功");
		return result;
	}

	/**
	 * @category 查询所有资质，分页有问题
	 * @author qufan
	 * @param offset查找数据第几条小值 limit查找数据第几条大值 都可乘当前页码数
	 * @return 结果信息
	 */
//	@RequestMapping(value = "/selectGlZzxx", method = RequestMethod.POST)
//	@CrossOrigin
//	@Transactional
//	public Map<String, Object> selectGlZzxx(@RequestBody Map<String, Object> map) {
//		Map<String, Object> result = new HashMap<>();
//		int pageSize = (int) map.get("pageSize");
//		int pageNum = (int) map.get("pageNum");
//		List<ZzxxVO> data = dchyZzxxService.queryGlZzxx((int) (1 + pageSize * (pageNum - 1)),
//				(int) (pageSize * pageNum));
//		List<ZzxxVO> data2 = dchyZzxxService.queryAllGlZzxx();
//		result.put("listSize", data2.size());
//		result.put("data", data);
//		return result;
//	}

	// 人员权限

}