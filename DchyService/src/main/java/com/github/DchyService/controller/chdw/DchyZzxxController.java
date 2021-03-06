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
 * (DchyZzxx)????????????
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
	 * ????????????
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
	 * @category ??????????????????????????????
	 * @author qufan
	 * @param bgzzxx??????
	 * @return ????????????
	 */

	@RequestMapping(value = "/addBgZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> addZzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		DchyBgxx bgxx = new DchyBgxx();
		// ??????????????????
		JSONArray listjson = jsons.getJSONArray("bgpersonallistjson");
		List<DchyBgpersonal> bgpersonallist = listjson.toJavaList(DchyBgpersonal.class);
		// ??????????????????
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx = bgzzxxjson.toJavaObject(DchyBgzzxx.class);

		// ??????????????????
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);

		// ??????????????????
		JSONObject companyjson = jsons.getJSONObject("companyjson");
		DchyCompany company = companyjson.toJavaObject(DchyCompany.class);
		// ??????????????????
		dchyUserService.updateCompany(company);

		BgZzxxVO zzxx = dchyBgZzxxService.queryByDid(bgzzxx.getBgdid());
		if (zzxx == null) {
			bgzzxx.setBgsqsj(new Timestamp(new Date().getTime()));
			log.setFssj(new Timestamp(new Date().getTime()));
			bgxx = new DchyBgxx();
			bgxx.setBgdid(bgzzxx.getBgdid());
			bgxx.setBgyy("????????????");
			// ???????????????????????????????????????
			bgxx = dchyBgxxService.insert(bgxx);
			log.setPid(bgzzxx.getId());
			// ??????????????????
			DchyLog dchylog = dchyLogService.insert(log);
			DchyBgxx bgxx1 = dchyBgxxService.queryByDidAndState(bgzzxx.getBgdid(), "?????????");

			bgzzxx.setBgbid(bgxx1.getId());
			bgzzxx.setLogid(dchylog.getId());
			bgzzxx = dchyBgZzxxService.insert(bgzzxx);

			if (bgpersonallist != null && bgpersonallist.size() > 0) {
				// ??????????????????
				for (DchyBgpersonal dchyBgpersonal2 : bgpersonallist) {
					DchyPersonal personal = dchyPersonalService.queryByIdcard(dchyBgpersonal2.getBgidcard());
					DchyPersonal personal2 = dchyPersonalService.queryByPhone(dchyBgpersonal2.getBgphonenum());
					DchyBgpersonal bgpersonal = dchybgpersonalService.queryByIdcard(dchyBgpersonal2.getBgidcard());
					DchyBgpersonal bgpersonal2 = dchybgpersonalService.queryByPhone(dchyBgpersonal2.getBgphonenum());
					if (personal != null || bgpersonal != null || personal2 != null || bgpersonal2 != null) {
						throw new ArithmeticException("?????????????????????????????????");
					} else {
						bgxx = dchyBgxxService.queryByDidAndState(dchyBgpersonal2.getBgdid(), "?????????");
						dchyBgpersonal2.setBgbid(bgxx1.getId());
						dchyBgpersonal2.setLogid(log.getId());
						dchyBgpersonal2.setRoleid("0");
						dchybgpersonalService.insert(dchyBgpersonal2);
					}
				}
			}

			result.put("msg", "????????????");
			result.put("dchylog", dchylog);
			result.put("bgxx", bgxx);
		} else {
			result.put("msg", "?????????????????????????????????");
			result.put("dchylog", null);
			result.put("bgxx", null);
		}
		return result;
	}

	/**
	 * @category ??????????????????????????????
	 * @author qufan
	 * @param bgzzxx??????
	 * @return ????????????
	 */

	@RequestMapping(value = "/addUpdateZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> addUpdateZzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		DchyBgxx bgxx = new DchyBgxx();
		// ??????????????????
		JSONArray listjson = jsons.getJSONArray("bgpersonallistjson");
		List<DchyBgpersonal> bgpersonallist = listjson.toJavaList(DchyBgpersonal.class);
		// ??????????????????
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		// ??????????????????
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);

		// ??????????????????
		JSONObject companyjson = jsons.getJSONObject("companyjson");
		DchyCompany company = companyjson.toJavaObject(DchyCompany.class);
		// ??????????????????
		dchyUserService.updateCompany(company);

		BgZzxxVO zzxx = dchyBgZzxxService.queryByDid(bgzzxx.getBgdid());
		if (zzxx == null) {
			bgzzxx.setBgsqsj(new Timestamp(new Date().getTime()));
			log.setFssj(new Timestamp(new Date().getTime()));
			bgxx = new DchyBgxx();
			bgxx.setBgdid(bgzzxx.getBgdid());
			bgxx.setBgyy("????????????");
			// ???????????????????????????????????????
			bgxx = dchyBgxxService.insert(bgxx);
			// ??????????????????
			log.setPid(bgzzxx.getId());
			DchyLog dchylog = dchyLogService.insert(log);
			DchyBgxx bgxx1 = dchyBgxxService.queryByDidAndState(bgzzxx.getBgdid(), "?????????");
			bgzzxx.setBgbid(bgxx1.getId());
			bgzzxx.setLogid(dchylog.getId());
			bgzzxx = dchyBgZzxxService.insert(bgzzxx);

			if (bgpersonallist != null && bgpersonallist.size() > 0) {
				// ??????????????????
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
						throw new ArithmeticException("?????????????????????????????????");
					} else {
						bgxx = dchyBgxxService.queryByDidAndState(dchyBgpersonal2.getBgdid(), "?????????");
						dchyBgpersonal2.setBgbid(bgxx.getId());
						dchyBgpersonal2.setLogid(log.getId());
						dchybgpersonalService.insert(dchyBgpersonal2);
					}
				}
			}
			result.put("msg", "????????????");
			result.put("dchylog", dchylog);
			result.put("bgxx", bgxx);
		} else {
			result.put("msg", "?????????????????????????????????");
			result.put("dchylog", null);
			result.put("bgxx", null);
		}
		return result;
	}

	/**
	 * @category ????????????????????????????????????
	 * @author qufan
	 * @param did
	 * @return ????????????
	 */
	@RequestMapping(value = "/selectBgZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectBgZzxx(@RequestBody DchyBgzzxx bgzzxx) {
		Map<String, Object> result = new HashMap<>();
		BgZzxxVO rebgzzxx = dchyBgZzxxService.queryByDidAndState(bgzzxx.getBgdid(), "?????????");
		DchyCompany company = dchyUserService.findCompanybyId(rebgzzxx.getBgdid());
		DchyBgxx bgxx = dchyBgxxService.queryById(rebgzzxx.getBgbid());
		// DchyFjFolder flod= fjService.getFolderByKey(bgzzxx.getBgzzfj());
		List<DchyBgpersonal> personal = dchybgpersonalService.queryByDidAndState(bgzzxx.getBgdid(), "?????????");
		DchyLog log = dchyLogService.findById(rebgzzxx.getLogid());
		log.setJssj(new Timestamp(new Date().getTime()));
		log.setSfyd("??????");
		DchyLog log1 = dchyLogService.update(log);
		if (rebgzzxx.getId() != null) {
			result.put("msg", "????????????");
			result.put("zzxxdate", rebgzzxx);
			result.put("personallist", personal);
			result.put("companydate", company);
			result.put("bgxx", bgxx);
			result.put("log", log1);
			// result.put("flod", flod);
		} else {
			result.put("msg", "????????????");
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
	 * @category ???????????????????????????????????????
	 * @author qufan
	 * @param did
	 * @return ????????????
	 */
	@RequestMapping(value = "/selectcsBgZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectcsBgZzxx(@RequestBody DchyBgzzxx bgzzxx) {
		Map<String, Object> result = new HashMap<>();
		BgZzxxVO rebgzzxx = dchyBgZzxxService.queryByDidAndState(bgzzxx.getBgdid(), "??????");
		DchyCompany company = dchyUserService.findCompanybyId(rebgzzxx.getBgdid());
		DchyBgxx bgxx = dchyBgxxService.queryById(rebgzzxx.getBgbid());
		// DchyFjFolder flod= fjService.getFolderByKey(bgzzxx.getBgzzfj());
		List<DchyBgpersonal> personal = dchybgpersonalService.queryByDidAndState(bgzzxx.getBgdid(), "?????????");
		DchyLog log = dchyLogService.findById(rebgzzxx.getLogid());
		log.setJssj(new Timestamp(new Date().getTime()));
		log.setSfyd("??????");
		DchyLog log1 = dchyLogService.update(log);
		if (rebgzzxx != null && company != null) {
			result.put("msg", "????????????");
			result.put("zzxxdate", rebgzzxx);
			result.put("companydate", company);
			result.put("personallist", personal);
			result.put("bgxx", bgxx);
			result.put("log", log1);
			// result.put("flod", flod);
		} else {
			result.put("msg", "????????????????????????");
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
	 * @category ???????????????????????????????????????
	 * @author qufan
	 * @param did
	 * @return ????????????
	 */
	/*
	 * @RequestMapping(value = "/selectzsBgZzxx", method = RequestMethod.POST)
	 * 
	 * @CrossOrigin
	 * 
	 * @Transactional public Map<String, Object> selectzsBgZzxx(@RequestBody
	 * DchyBgzzxx bgzzxx) { Map<String, Object> result = new HashMap<>(); DchyBgzzxx
	 * rebgzzxx = dchyBgZzxxService.queryByDidAndState(bgzzxx.getBgdid(), "??????");
	 * DchyCompany company = dchyUserService.findCompanybyId(bgzzxx.getBgdid());
	 * DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx.getBgbid());
	 * List<DchyBgpersonal> personal =
	 * dchybgpersonalService.queryByDidAndState(bgzzxx.getBgdid(), "?????????"); DchyLog
	 * log = dchyLogService.findById(bgzzxx.getLogid()); log.setJssj(new
	 * Timestamp(new Date().getTime())); log.setSfyd("??????"); DchyLog log1 =
	 * dchyLogService.update(log); Map<DchyBgpersonal, List<DchyRoleuser>> map = new
	 * HashMap<>(); List<Map<DchyBgpersonal, List<DchyRoleuser>>> personallist = new
	 * ArrayList<>(); for (DchyBgpersonal dchyBgpersonal : personal) {
	 * List<DchyRoleuser> roleuser =
	 * dchyRoleuserService.queryAllByUid(dchyBgpersonal.getId());
	 * map.put(dchyBgpersonal, roleuser); personallist.add(map); } if (rebgzzxx !=
	 * null && company != null) { result.put("msg", "????????????"); result.put("zzxxdate",
	 * rebgzzxx); result.put("companydate", company); result.put("personallist",
	 * personallist); result.put("bgxx", bgxx); result.put("log", log1); } else {
	 * result.put("msg", "????????????"); } return result; }
	 */
	/**
	 * @category ??????????????????????????????????????????
	 * @author qufan
	 * @param did
	 * @return ????????????
	 */
	@RequestMapping(value = "/selectwtgBgZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectwtgBgZzxx(@RequestBody DchyBgzzxx bgzzxx) {
		Map<String, Object> result = new HashMap<>();
		BgZzxxVO rebgzzxx = dchyBgZzxxService.queryByDidAndState(bgzzxx.getBgdid(), "?????????");
		DchyCompany company = dchyUserService.findCompanybyId(rebgzzxx.getBgdid());
		DchyBgxx bgxx = dchyBgxxService.queryById(rebgzzxx.getBgbid());
		// DchyFjFolder flod= fjService.getFolderByKey(bgzzxx.getBgzzfj());
		List<DchyBgpersonal> personal = dchybgpersonalService.queryByDidAndState(bgzzxx.getBgdid(), "?????????");
		DchyLog log = dchyLogService.findById(rebgzzxx.getLogid());
		log.setJssj(new Timestamp(new Date().getTime()));
		log.setSfyd("??????");
		DchyLog log1 = dchyLogService.update(log);
		if (rebgzzxx != null && company != null) {
			result.put("msg", "????????????");
			result.put("zzxxdate", rebgzzxx);
			result.put("companydate", company);
			result.put("personallist", personal);
			result.put("bgxx", bgxx);
			result.put("log", log1);
			// result.put("flod", flod);
		} else {
			result.put("msg", "????????????");
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
	 * @category ?????????????????????????????????
	 * @author qufan
	 * @param did
	 * @return ????????????
	 */
	@RequestMapping(value = "/selectZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectZzxx(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		String did = (String) map.get("did");
		DchyZzxx zzxx = dchyZzxxService.queryByDidAndState(did, "??????");
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(did);
		DchyCompany company = dchyUserService.findCompanybyId(did);
		if (company != null) {
			result.put("msg", "????????????");
			result.put("zzxxdate", zzxx);
			result.put("companydate", company);
			result.put("bgzzxxdate", bgzzxx);
		} else {
			result.put("msg", "???????????????????????????");
			result.put("zzxxdate", null);
			result.put("companydate", company);
			result.put("bgzzxxdate", null);
		}
		return result;
	}

	/**
	 * @category ????????????????????????
	 * @author qufan
	 * @param did
	 * @return ????????????
	 */
	@RequestMapping(value = "/findZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> findZzxx(@RequestBody DchyZzxx zzxx) {
		Map<String, Object> result = new HashMap<>();
		zzxx.setState("??????");
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
	 * @category ????????????????????????
	 * @author qufan
	 * @param bgzzxx??????
	 * @return ????????????
	 */
	@RequestMapping(value = "/updateBgzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updateBgzzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		// ??????????????????
		JSONArray listjson = jsons.getJSONArray("bgpersonallistjson");
		List<DchyBgpersonal> bgpersonallist = listjson.toJavaList(DchyBgpersonal.class);
		// ??????????????????
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx1 = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(bgzzxx1.getBgdid());

		// ??????????????????,???????????????
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);
		log.setFssj(new Timestamp(new Date().getTime()));
		log = dchyLogService.insert(log);

		// ??????????????????????????????????????????
		DchyZzxx rezzxx = dchyZzxxService.queryByDid(bgzzxx.getBgdid());
		// ????????????id???????????????????????????
		DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx.getBgbid());
		// ??????????????????
		bgxx.setBgjg("????????????");
		bgxx.setBgstate("????????????");
		// ?????????????????????
		dchyBgxxService.update(bgxx);
		// ?????????????????????
		DchyLog dchyLog = dchyLogService.findById(bgzzxx.getLogid());
		dchyLog.setClzt("??????");
		dchyLog.setJssj(new Timestamp(new Date().getTime()));
		dchyLogService.update(dchyLog);
		// ????????????????????????
		if (rezzxx == null) {
			// ???????????????????????????????????????????????????????????????
			DchyLszzxx lszzxx1 = new DchyLszzxx();
			lszzxx1.setLsdid(null);
			lszzxx1.setLszsbh(null);
			lszzxx1.setLszsyxq(null);
			lszzxx1.setLszzdj(null);
			lszzxx1.setLsfzbm(null);
			lszzxx1.setLszzfj(null);
			lszzxx1.setLszyfw(null);
			lszzxx1.setBgbid(bgzzxx.getBgbid());
			lszzxx1.setLsstate("?????????");
			lszzxx1.setLsdchyywfw(bgzzxx.getBgdchyywfw());
			lszzxx1 = dchylSZzxxService.insert(lszzxx1);
			// ??????????????????????????????????????????
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
			// ???????????????????????????????????????????????????????????????
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
			lszzxx2.setLsstate("?????????");
			lszzxx2 = dchylSZzxxService.insert(lszzxx2);
			// ?????????????????????
			dchyBgZzxxService.deleteByDid(bgzzxx.getBgdid());

		} else {
			// ?????????????????????
			// ????????????????????????????????????????????????
			DchyLszzxx lszzxx1 = new DchyLszzxx();
			lszzxx1.setLsdid(rezzxx.getDid());
			lszzxx1.setLszsbh(rezzxx.getZsbh());
			lszzxx1.setLszsyxq(rezzxx.getZsyxq());
			lszzxx1.setLszzdj(rezzxx.getZzdj());
			lszzxx1.setLsfzbm(rezzxx.getFzbm());
			lszzxx1.setLszzfj(rezzxx.getZzfj());
			lszzxx1.setLszyfw(rezzxx.getZyfw());
			lszzxx1.setBgbid(bgzzxx.getBgbid());
			lszzxx1.setLsstate("?????????");
			lszzxx1.setLsdchyywfw(bgzzxx.getBgdchyywfw());
			dchylSZzxxService.insert(lszzxx1);

			// ????????????????????????????????????????????????
			rezzxx.setDid(bgzzxx.getBgdid());
			rezzxx.setZsbh(bgzzxx.getBgzsbh());
			rezzxx.setZsyxq(bgzzxx.getBgzsyxq());
			rezzxx.setZzdj(bgzzxx.getBgzzdj());
			rezzxx.setFzbm(bgzzxx.getBgfzbm());
			rezzxx.setZzfj(bgzzxx.getBgzzfj());
			rezzxx.setZyfw(bgzzxx.getBgzyfw());
			rezzxx.setDchyywfw(bgzzxx.getBgdchyywfw());

			dchyZzxxService.update(rezzxx);

			// ?????????????????????????????????????????????
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
			lszzxx2.setLsstate("?????????");
			dchylSZzxxService.insert(lszzxx2);
			// ?????????????????????
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
				lspersonal1.setLsstate("?????????");
				// ???????????????????????????
				dchyLspersonalService.insert(lspersonal1);
			}
			dchyPersonalService.deleteByDid(bgzzxx.getBgdid());
			for (DchyBgpersonal bg : bgpersonallist) {
				// ??????????????????
				// DchyPersonal dchyPersonal =
				// dchyPersonalService.queryByIdcard(bg.getBgidcard());
				// ?????????????????????????????????
//				if (dchyPersonal != null) {
//					// ???????????????????????????????????????????????????????????????
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
//					lspersonal1.setLsstate("????????????");
//					// ???????????????????????????
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
//					lspersonal2.setLsstate("????????????");
//					// ???????????????????????????
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
//					personal2.setState("??????");
//					// ?????????????????????
//					dchyPersonalService.deleteById(dchyPersonal.getId());
//					// ?????????????????????
//					dchyPersonalService.insert(personal2);
//					// ?????????????????????
//					dchybgpersonalService.deleteById(bg.getId());
//				} else {

				// ????????????????????????????????????????????????
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
				lspersonal2.setLsstate("?????????");
				// ???????????????????????????
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
				personal2.setState("??????");
				// ?????????????????????
				dchyPersonalService.insert(personal2);

				// }
				// ?????????????????????
				dchybgpersonalService.deleteByDid(bgzzxx.getBgdid());
			}
		}
		result.put("msg", "????????????");
		return result;
	}

	/**
	 * @category ???????????????????????????
	 * @author qufan
	 * @param
	 * @return ????????????
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
					zzxxVO.setBjl(dchyFbxxService.findfwCount(zzxxVO.getDid(), "?????????"));
				}
			}
		}
		PageInfo<ZzxxVO> pageInfo = new PageInfo<ZzxxVO>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("data", pageInfo.getList());
		return result;
	}

	/**
	 * @category ????????????????????????????????????
	 * @author qufan
	 * @param bgzzxx??????
	 * @return ????????????
	 */
	@RequestMapping(value = "/updatethBgzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updatethBgzzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();

		// ??????????????????
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx1 = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(bgzzxx1.getBgdid());
		// ????????????id???????????????????????????
		DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx.getBgbid());
		// ????????????
		bgxx.setBgstate("?????????");
		boolean flag1 = dchyBgxxService.update(bgxx);
		// ???????????????
		DchyLog dchyLog = dchyLogService.findById(bgzzxx.getLogid());
		dchyLog.setClzt("??????");
		dchyLog.setJssj(new Timestamp(new Date().getTime()));
		dchyLogService.update(dchyLog);

		// ??????????????????,???????????????
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);
		log.setFssj(new Timestamp(new Date().getTime()));
		log = dchyLogService.insert(log);

		// ????????????
		DchyBgzzxx lszzxx2 = new DchyBgzzxx();
		lszzxx2.setId(bgzzxx.getId());
		lszzxx2.setBgstate("?????????");
		// ????????????
		lszzxx2.setBgwtgyy(bgzzxx1.getBgwtgyy());
		lszzxx2.setLogid(log.getId());
		boolean rels2 = dchyBgZzxxService.update(lszzxx2);

		if (rels2 && flag1) {
			result.put("msg", "????????????");
		} else {
			result.put("msg", "????????????");
		}

		return result;
	}

	/**
	 * @category ??????????????????????????????????????????
	 * @author qufan
	 * @param bgzzxx??????
	 * @return ????????????
	 */
	@RequestMapping(value = "/updatewtBgzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updatewtBgzzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();

		// ??????????????????
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx1 = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		// ??????????????????
		JSONArray listjson = jsons.getJSONArray("bgpersonallistjson");
		List<DchyBgpersonal> bgpersonallist = listjson.toJavaList(DchyBgpersonal.class);

		// ????????????id???????????????????????????
		DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx1.getBgbid());
		// ????????????
		bgxx.setBgstate("?????????");
		dchyBgxxService.update(bgxx);
		// ???????????????
		DchyLog dchyLog = dchyLogService.findById(bgzzxx1.getLogid());
		dchyLog.setClzt("??????");
		dchyLog.setJssj(new Timestamp(new Date().getTime()));
		dchyLogService.update(dchyLog);

		// ??????????????????,???????????????
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);
		log.setFssj(new Timestamp(new Date().getTime()));
		log = dchyLogService.insert(log);

		// ????????????
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(bgzzxx1.getBgdid());
		bgzzxx1.setId(bgzzxx.getId());
		bgzzxx1.setBgstate("?????????");
		bgzzxx1.setBgwtgyy(null);
		bgzzxx1.setLogid(log.getId());
		dchyBgZzxxService.update(bgzzxx1);
		boolean flag = false;
		if (bgpersonallist != null && bgpersonallist.size() > 0) {
			flag = true;
			for (DchyBgpersonal bg : bgpersonallist) {
				// ??????????????????
				if (flag) {
					// ?????????????????????
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
					throw new ArithmeticException("?????????????????????????????????");
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
					bgpersonal.setBgstate("?????????");
					// ?????????????????????
					dchybgpersonalService.insert(bgpersonal);
				}
			}
			result.put("msg", "????????????");
		}
		return result;
	}

	/**
	 * @category ??????????????????
	 * @author qufan
	 * @param bgzzxx??????
	 * @return ????????????
	 */
	@RequestMapping(value = "/updatecsBgzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updatecsBgzzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();

		// ??????????????????
		JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		DchyBgzzxx bgzzxx1 = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		BgZzxxVO bgzzxx = dchyBgZzxxService.queryByDid(bgzzxx1.getBgdid());
		// ????????????id???????????????????????????
		DchyBgxx bgxx = dchyBgxxService.queryById(bgzzxx.getBgbid());
		// ????????????
		bgxx.setBgstate("??????");
		boolean flag1 = dchyBgxxService.update(bgxx);
		// ???????????????
		DchyLog dchyLog = dchyLogService.findById(bgzzxx.getLogid());
		dchyLog.setClzt("??????");
		dchyLog.setJssj(new Timestamp(new Date().getTime()));
		dchyLogService.update(dchyLog);

		// ??????????????????,???????????????
		JSONObject logjson = jsons.getJSONObject("logjson");
		DchyLog log = logjson.toJavaObject(DchyLog.class);
		log.setFssj(new Timestamp(new Date().getTime()));
		log = dchyLogService.insert(log);

		// ????????????
		DchyBgzzxx lszzxx2 = new DchyBgzzxx();
		lszzxx2.setId(bgzzxx.getId());
		lszzxx2.setBgstate("??????");
		lszzxx2.setLogid(log.getId());
		boolean rels2 = dchyBgZzxxService.update(lszzxx2);

		if (rels2 && flag1) {
			result.put("msg", "????????????");
		} else {
			result.put("msg", "????????????");
		}

		return result;
	}

	/**
	 * @category ????????????????????????
	 * @author qufan
	 * @param
	 * @return ????????????
	 */
	@RequestMapping(value = "/selectAllCsZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectAllCsZzxx(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		// ???????????????
		int pageSize = (int) map.get("pageSize");
		// ?????????
		int pageNum = (int) map.get("pageNum");

		List<BgZzxxVO> list = new ArrayList<BgZzxxVO>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyBgZzxxService.queryByState("?????????");
		PageInfo<BgZzxxVO> pageInfo = new PageInfo<BgZzxxVO>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("data", pageInfo.getList());
		return result;
	}

	/**
	 * @category ????????????????????????
	 * @author qufan
	 * @param
	 * @return ????????????
	 */
	@RequestMapping(value = "/selectAllZsZzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectAllZsZzxx(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		// ???????????????
		int pageSize = (int) map.get("pageSize");
		// ?????????
		int pageNum = (int) map.get("pageNum");

		List<BgZzxxVO> list = new ArrayList<BgZzxxVO>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyBgZzxxService.queryByState("??????");
		PageInfo<BgZzxxVO> pageInfo = new PageInfo<BgZzxxVO>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("data", pageInfo.getList());
		return result;
	}

	/**
	 * @category ??????????????????????????????
	 * @author qufan
	 * @param
	 * @return ????????????
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
		List<DchyBgpersonal> personal = dchybgpersonalService.queryByDidAndState(rebgzzxx.getBgdid(), "?????????");
		DchyLog log = dchyLogService.findById(rebgzzxx.getLogid());
		if (rebgzzxx != null && company != null) {
			result.put("msg", "????????????");
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
			result.put("msg", "??????????????????");
		}
		return result;
	}

	/**
	 * @category ??????????????????
	 * @author qufan
	 * @param bgzzxx??????
	 * @return ????????????
	 *//*
		 * @RequestMapping(value = "/updatezsBgzzxx", method = RequestMethod.POST)
		 * 
		 * @CrossOrigin
		 * 
		 * @Transactional public Map<String, Object> updatezsBgzzxx(@RequestBody
		 * JSONObject jsons) { Map<String, Object> result = new HashMap<>();
		 * 
		 * // ?????????????????? JSONObject bgzzxxjson = jsons.getJSONObject("bgzzxxjson");
		 * DchyBgzzxx bgzzxx = bgzzxxjson.toJavaObject(DchyBgzzxx.class);
		 * 
		 * // ????????????id??????????????????????????? DchyBgxx bgxx =
		 * dchyBgxxService.queryById(bgzzxx.getBgbid()); // ???????????? bgxx.setBgstate("??????");
		 * boolean flag1 = dchyBgxxService.update(bgxx); // ??????????????? DchyLog dchyLog =
		 * dchyLogService.findById(bgzzxx.getLogid()); dchyLog.setClzt("??????");
		 * dchyLog.setJssj(new Timestamp(new Date().getTime()));
		 * dchyLogService.update(dchyLog);
		 * 
		 * // ??????????????????,??????????????? JSONObject logjson = jsons.getJSONObject("logjson"); DchyLog
		 * log = logjson.toJavaObject(DchyLog.class); log.setFssj(new Timestamp(new
		 * Date().getTime())); log = dchyLogService.insert(log);
		 * 
		 * // ???????????? DchyBgzzxx lszzxx2 = new DchyBgzzxx(); lszzxx2.setId(bgzzxx.getId());
		 * lszzxx2.setBgstate("??????"); lszzxx2.setLogid(log.getId()); boolean rels2 =
		 * dchyBgZzxxService.update(lszzxx2);
		 * 
		 * if (rels2 && flag1) { result.put("msg", "????????????"); } else { result.put("msg",
		 * "????????????"); }
		 * 
		 * return result; }
		 */

	/**
	 * @category ????????????
	 * @author qufan
	 * @param
	 * @return ????????????
	 */
	@RequestMapping(value = "/djzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> djZzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		// ??????????????????
		JSONObject zzxxjson = jsons.getJSONObject("zzxxjson");
		DchyZzxx zzxx = zzxxjson.toJavaObject(DchyZzxx.class);
		zzxx.setState("??????");
		zzxx.setDjsj(new Timestamp(new Date().getTime()));
		dchyZzxxService.update(zzxx);
		result.put("msg", "????????????");
		return result;
	}

	/**
	 * @category ????????????
	 * @author qufan
	 * @param
	 * @return ????????????
	 */
	@RequestMapping(value = "/jdzzxx", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> jdZzxx(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		// ??????????????????
		JSONObject zzxxjson = jsons.getJSONObject("zzxxjson");
		DchyZzxx zzxx = zzxxjson.toJavaObject(DchyZzxx.class);
		zzxx.setState("??????");
		zzxx.setDjsj(null);
		dchyZzxxService.update(zzxx);
		result.put("msg", "????????????");
		return result;
	}

	/**
	 * @category ????????????????????????????????????
	 * @author qufan
	 * @param offset??????????????????????????? limit??????????????????????????? ????????????????????????
	 * @return ????????????
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

	// ????????????

}