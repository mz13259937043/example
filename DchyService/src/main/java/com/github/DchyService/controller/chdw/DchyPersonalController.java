package com.github.DchyService.controller.chdw;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.entity.DchyBgpersonal;
import com.github.DchyService.entity.DchyFbxx;
import com.github.DchyService.entity.DchyHtxx;
import com.github.DchyService.entity.DchyPersonal;
import com.github.DchyService.entity.DchyRole;
import com.github.DchyService.entity.DchyRoleuser;
import com.github.DchyService.service.DchyBgpersonalService;
import com.github.DchyService.service.DchyBgxxService;
import com.github.DchyService.service.DchyFbxxService;
import com.github.DchyService.service.DchyLspersonalService;
import com.github.DchyService.service.DchyPersonalService;
import com.github.DchyService.service.DchyRoleService;
import com.github.DchyService.service.DchyRoleuserService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * (DchyPersonal)表控制层
 *
 * @author qufan
 * @since 2020-10-27 19:21:20
 */
@RestController
@RequestMapping("/personal")
public class DchyPersonalController {
	private Logger log = LoggerFactory.getLogger(DchyPersonalController.class);
	/**
	 * 服务对象
	 */
	@Resource
	private DchyPersonalService dchyPersonalService;

	@Resource
	private DchyBgpersonalService dchybgpersonalService;

	@Resource
	private DchyBgxxService dchyBgxxService;

	@Resource
	private DchyLspersonalService dchyLspersonalService;

	@Resource
	private DchyRoleuserService dchyRoleuserService;

	@Resource
	private DchyRoleService dchyRoleService;

	@Resource
	private DchyFbxxService dchyFbxxService;

	/**
	 * @category 新增管理审批人员
	 * @author qufan
	 * @param 人员列表
	 * @return 结果信息
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> addUser(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		// 获取人员信息
		JSONArray listjson = jsons.getJSONArray("personallistjson");
		List<DchyPersonal> personallist = listjson.toJavaList(DchyPersonal.class);
		if (personallist != null && personallist.size() > 0) {
			// 人员部分代码
			for (DchyPersonal dchyPersonal : personallist) {
				DchyPersonal personallist1 = dchyPersonalService.queryByIdcard(dchyPersonal.getIdcard());
				DchyPersonal personallist2 = dchyPersonalService.queryByPhone(dchyPersonal.getPhonenum());
				DchyBgpersonal bgpersonal = dchybgpersonalService.queryByPhone(dchyPersonal.getPhonenum());
				DchyBgpersonal bgpersonal2 = dchybgpersonalService.queryByIdcard(dchyPersonal.getIdcard());
				if (personallist2 == null && personallist1 == null && bgpersonal2 == null && bgpersonal == null) {
					dchyPersonal.setDid(null);
					dchyPersonalService.insert(dchyPersonal);
//					List<DchyPersonal> personallist3 = dchyPersonalService.queryByPhone(DchyPersonal.getPhonenum());
//					List<String> roleidlist2 = DchyPersonal.getRoleidlist();
//					if (roleidlist2 != null && roleidlist2.size() > 0) {
//						for (String roleid : roleidlist2) {
//							DchyRoleuser dchyRoleuser = new DchyRoleuser();
//							dchyRoleuser.setRoleid(roleid);
//							for (DchyPersonal dchyPersonal2 : personallist3) {
//								dchyRoleuser.setUserid(dchyPersonal2.getId());
//							}
//							dchyRoleuserService.insert(dchyRoleuser);
//						}
//					}
					result.put("msg", "添加成功");
				} else {
					if (personallist1 != null && personallist2 != null
							|| personallist1 != null && bgpersonal != null && bgpersonal2 != null) {
						List<String> roleidlist = dchyPersonal.getRoleidlist();
						if (roleidlist != null && roleidlist.size() > 0) {
							boolean flag = false;
							for (String roleid : roleidlist) {
								List<DchyRoleuser> roleuserlist = dchyRoleuserService.queryByRoleId(roleid);
								for (DchyRoleuser roleuser : roleuserlist) {
									if (roleuser.getUserid().equals(dchyPersonal.getId())) {
										flag = true;
										break;
									}
								}
							}
							for (String roleid : roleidlist) {
								if (!flag) {
									DchyRoleuser dchyRoleuser = new DchyRoleuser();
									dchyRoleuser.setRoleid(roleid);
									dchyRoleuser.setUserid(dchyPersonal.getId());
									dchyRoleuserService.insert(dchyRoleuser);
								}
							}
						}
						result.put("msg", "添加成功");
					} else {
						if (personallist1 == null && personallist2 != null && bgpersonal2 == null
								|| personallist1 == null && bgpersonal != null && bgpersonal2 == null) {
							result.put("msg", "手机号已存在");
						} else {
							result.put("msg", "身份证号已存在");
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * @category 变更管理审批人员
	 * @author qufan
	 * @param 人员列表
	 * @return 结果信息
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updateUser(@RequestBody JSONObject jsons) {
		Map<String, Object> result = new HashMap<>();
		// 获取人员信息
		JSONArray listjson = jsons.getJSONArray("personallistjson");
		List<DchyPersonal> personallist = listjson.toJavaList(DchyPersonal.class);
		if (personallist != null && personallist.size() > 0) {
			// 遍历
			for (DchyPersonal dchyPersonal : personallist) {
				DchyPersonal personallist1 = dchyPersonalService.queryByIdcard(dchyPersonal.getIdcard());
				DchyPersonal personallist2 = dchyPersonalService.queryByPhone(dchyPersonal.getPhonenum());
				DchyBgpersonal bgpersonal = dchybgpersonalService.queryByPhone(dchyPersonal.getPhonenum());
				DchyBgpersonal bgpersonal2 = dchybgpersonalService.queryByIdcard(dchyPersonal.getIdcard());
				// 判断手机号身份证号是否重复
				if ((personallist2 == null || personallist2.getId().equals(dchyPersonal.getId()))
						&& (personallist1 == null || personallist1.getId().equals(dchyPersonal.getId()))
						&& bgpersonal == null || bgpersonal2 == null) {
					dchyPersonal.setDid(null);
					dchyPersonalService.update(dchyPersonal);
//					List<String> roleidlist = DchyPersonal.getRoleidlist();
//					if (roleidlist != null && roleidlist.size() > 0) {
//						dchyRoleuserService.deleteByUid(DchyPersonal.getId());
//						for (String roleid : roleidlist) {
//							DchyRoleuser dchyRoleuser = new DchyRoleuser();
//							dchyRoleuser.setRoleid(roleid);
//							dchyRoleuser.setUserid(DchyPersonal.getId());
//							dchyRoleuserService.insert(dchyRoleuser);
//						}
//						List<String> roleidlist = DchyPersonal.getRoleidlist();
//						if (roleidlist != null && roleidlist.size() > 0) {
//							for (String roleid : roleidlist) {
//								List<DchyRoleuser> roleuserlist = dchyRoleuserService
//										.queryAllByUid(DchyPersonal.getId());
//								if (roleuserlist.size() > 0) {
//									for (DchyRoleuser roleuser : roleuserlist) {
//										if (roleid.equals(roleuser.getRoleid())) {
//											continue;
//										} else {
//											DchyRoleuser dchyRoleuser = new DchyRoleuser();
//											dchyRoleuser.setRoleid(roleid);
//											dchyRoleuser.setUserid(DchyPersonal.getId());
//											dchyRoleuserService.insert(dchyRoleuser);
//										}
//									}
//								} else {
//									DchyRoleuser dchyRoleuser = new DchyRoleuser();
//									dchyRoleuser.setRoleid(roleid);
//									dchyRoleuser.setUserid(DchyPersonal.getId());
//									dchyRoleuserService.insert(dchyRoleuser);
//								}
//							}
//						}
//						result.put("msg", "更新成功");
//					} else {
//						result.put("msg", "手机号已存在");
//					}
					result.put("msg", "修改成功");
				} else {
					if ((personallist2 == null || !personallist2.getId().equals(dchyPersonal.getId()))
							&& personallist2 != null && bgpersonal2 == null
							|| (personallist1 == null || !personallist1.getId().equals(dchyPersonal.getId()))
									&& bgpersonal != null && bgpersonal2 == null) {
						result.put("msg", "手机号已存在");
					} else {
						result.put("msg", "身份证号已存在");
					}
				}
//				dchyPersonalService.update(DchyPersonal);
//				List<String> roleidlist = DchyPersonal.getRoleidlist();
//				if (roleidlist != null && roleidlist.size() > 0) {
//					dchyRoleuserService.deleteByUid(DchyPersonal.getId());
//					for (String roleid : roleidlist) {
//						DchyRoleuser dchyRoleuser = new DchyRoleuser();
//						dchyRoleuser.setRoleid(roleid);
//						dchyRoleuser.setUserid(DchyPersonal.getId());
//						dchyRoleuserService.insert(dchyRoleuser);
//					}
//				}
			}
		}
		return result;
	}

	/**
	 * @category 删除方法
	 * @author qufan
	 * @param List<id>
	 * @return 结果信息
	 */
	@RequestMapping(value = "/deletePersonal", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> deletePersonal(@RequestBody List<String> idlist) {
		Map<String, Object> result = new HashMap<>();
		if (idlist != null && idlist.size() > 0) {
			for (String id : idlist) {
				dchyRoleuserService.deleteByUid(id);
				dchyPersonalService.deleteById(id);
			}
		}
		result.put("msg", "删除成功");
		return result;
	}

	/**
	 * @category 根据注册测绘师查找发布信息
	 * @author qufan
	 * @param List<id>
	 * @return 结果信息
	 */
	@RequestMapping(value = "/getFbxxByPersonal", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> getFbxxByPersonal(@RequestBody DchyPersonal personal) {
		Map<String, Object> result = new HashMap<>();
		List<DchyFbxx> fbxxlist = new ArrayList<DchyFbxx>();
		if (personal != null && personal.getIdcard() != null) {
			fbxxlist = dchyFbxxService.getFbxxByPersonal(personal.getIdcard());
			if (fbxxlist != null && fbxxlist.size() > 0) {
				result.put("msg", "查询成功");
				result.put("data", fbxxlist.size());
			} else {
				result.put("msg", "查询成功");
				result.put("data", 0);
			}
		}
		return result;
	}

	/**
	 * @category 删除方法
	 * @author qufan
	 * @param List<DchyBgpersonal>
	 * @return 结果信息
	 */
	@RequestMapping(value = "/deleteBgPersonal", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public List<Map<String, Object>> deleteBgPersonal(@RequestBody List<DchyBgpersonal> bglist) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> result = new HashMap<>();
		if (bglist != null && bglist.size() > 0) {
			for (DchyBgpersonal bg : bglist) {
				String re = dchybgpersonalService.deleteById(bg.getId()) ? "成功" : "失败";
				result.put(re, bg);
				list.add(result);
			}
		}
		return list;
	}

	/**
	 * @category 角色管理模糊查询用户
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectUser", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectUser(@RequestBody Map<String, Object> str) {
		Map<String, Object> result = new HashMap<>();
		String str1 = (String) str.get("str");
		List<DchyPersonal> data = dchyPersonalService.queryUserByStr(str1);
		List<DchyPersonal> data2 = new ArrayList<DchyPersonal>();
		if (data != null && data.size() > 0) {
			for (DchyPersonal dchyPersonal : data) {
				if (dchyPersonal.getType().equals("审批人员") || dchyPersonal.getType().equals("管理人员")
						|| dchyPersonal.getType().equals("监管人员")) {
					data2.add(dchyPersonal);
				}
			}
		}
		result.put("data", data2);
		return result;
	}

	/**
	 * @category 人员管理模糊查询用户
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/findUser", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> findUser(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		int pageSize = (int) map.get("pageSize");
		int pageNum = (int) map.get("pageNum");
		String str1 = (String) map.get("str");
		List<DchyPersonal> list = new ArrayList<DchyPersonal>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyPersonalService.queryUserByStr(str1);
		PageInfo<DchyPersonal> pageInfo = new PageInfo<DchyPersonal>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("data", pageInfo.getList());

//		List<DchyPersonal> list = new ArrayList<DchyPersonal>();
//		PageHelper.startPage(pageNum,pageSize);
//		list = dchyPersonalService.queryUserByStr(str1);
////		List<DchyPersonal> data = dchyPersonalService.queryUserByStrLimit((int) (1 + pageSize * (pageNum - 1)),
////				(int) (pageSize * pageNum), str1);
////		List<DchyPersonal> data2 = dchyPersonalService.queryUserByStr(str1);
//		PageInfo<DchyPersonal> pageInfo = new PageInfo<DchyPersonal>(list);
//		result.put("listSize", pageInfo.getTotal());
//		result.put("data", pageInfo.getList());
		return result;
	}

	/**
	 * @category 查询所有用户，带角色
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectAllPersonal", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectAllPersonal(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		int pageSize = (int) map.get("pageSize");
		int pageNum = (int) map.get("pageNum");

		List<DchyPersonal> list = new ArrayList<DchyPersonal>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyPersonalService.queryAll();
		if (list != null && list.size() > 0) {
			for (DchyPersonal dchyPersonal : list) {
				List<DchyRole> rolelist = new ArrayList<DchyRole>();
				List<DchyRoleuser> roleuserlist = dchyRoleuserService.queryAllByUid(dchyPersonal.getId());
				if (roleuserlist.size() > 0) {
					for (DchyRoleuser roleuser : roleuserlist) {
						DchyRole role = dchyRoleService.queryById(roleuser.getRoleid());
						rolelist.add(role);
						dchyPersonal.setRolelist(rolelist);
					}
				}
			}
		}
		PageInfo<DchyPersonal> pageInfo = new PageInfo<DchyPersonal>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("data", pageInfo.getList());

//		List<DchyPersonal> data = dchyPersonalService.queryAllByLimit((int) (1 + pageSize * (pageNum - 1)),
//				(int) (pageSize * pageNum));
//		List<DchyPersonal> data2 = dchyPersonalService.queryAll();
//		if (data != null && data.size() > 0) {
//			for (DchyPersonal dchyPersonal : data) {
//				List<DchyRole> rolelist = new ArrayList<DchyRole>();
//				List<DchyRoleuser> roleuserlist = dchyRoleuserService.queryAllByUid(dchyPersonal.getId());
//				if (roleuserlist.size() > 0) {
//					for (DchyRoleuser roleuser : roleuserlist) {
//						DchyRole role = dchyRoleService.queryById(roleuser.getRoleid());
//						rolelist.add(role);
//						dchyPersonal.setRolelist(rolelist);
//					}
//				}
//			}
//		}
//		result.put("data", data);
//		result.put("listSize", data2.size());
		return result;
	}

	/**
	 * @category 按照企业id查询测绘人员
	 * @author qufan
	 * @param did
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectpersonal", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectPersonal(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		List<DchyPersonal> list = new ArrayList<DchyPersonal>();
		String did = (String) map.get("did");
		list = dchyPersonalService.queryByDid(did);
		result.put("data", list);
		return result;
	}

}