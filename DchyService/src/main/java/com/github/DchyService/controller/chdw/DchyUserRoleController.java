package com.github.DchyService.controller.chdw;

import com.github.DchyService.VO.ZzxxVO;
import com.github.DchyService.entity.DchyPersonal;
import com.github.DchyService.entity.DchyRole;
import com.github.DchyService.entity.DchyRoleuser;
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
@RequestMapping("/roleuser")
public class DchyUserRoleController {
	private Logger log = LoggerFactory.getLogger(DchyUserRoleController.class);
	/**
	 * 服务对象
	 */
	@Resource
	private DchyPersonalService dchyPersonalService;

	@Resource
	private DchyRoleService dchyroleservice;

	@Resource
	private DchyRoleuserService dchyroleuserservice;

	/**
	 * @category 新增角色信息
	 * @author qufan
	 * @param List<rolelist>
	 * @return 结果信息
	 */
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> addRole(@RequestBody List<DchyRole> rolelist) {
		Map<String, Object> result = new HashMap<>();
		if (rolelist != null && rolelist.size() > 0) {
			for (DchyRole role : rolelist) {
				dchyroleservice.insert(role);
			}
		}
		result.put("msg", "添加成功");
		return result;
	}

	/**
	 * @category 修改角色信息
	 * @author qufan
	 * @param List<rolelist>
	 * @return 结果信息
	 */
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> updateRole(@RequestBody List<DchyRole> rolelist) {
		Map<String, Object> result = new HashMap<>();
		if (rolelist != null && rolelist.size() > 0) {
			for (DchyRole role : rolelist) {
				dchyroleservice.update(role);
			}
		}
		result.put("msg", "修改成功");
		return result;
	}

	/**
	 * 
	 * @category 删除角色方法
	 * @author qufan
	 * @param List<DchyRole>
	 * @return 结果信息
	 */
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> deleteRole(@RequestBody List<DchyRole> rolelist) {
		Map<String, Object> result = new HashMap<>();
		if (rolelist != null && rolelist.size() > 0) {
			for (DchyRole role : rolelist) {
				dchyroleuserservice.deleteByRoleId(role.getId());
				dchyroleservice.deleteById(role.getId());
			}
		}
		result.put("msg", "删除成功");
		return result;
	}

	/**
	 * 
	 * @category 删除用户的角色方法
	 * @author qufan
	 * @param List<DchyRole>
	 * @return 结果信息
	 */
	@RequestMapping(value = "/deleteRoleByUser", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> deleteRoleByUser(@RequestBody List<DchyRoleuser> roleuserlist) {
		Map<String, Object> result = new HashMap<>();
		if (roleuserlist != null && roleuserlist.size() > 0) {
			for (DchyRoleuser roleuser : roleuserlist) {
				dchyroleuserservice.deleteRoleByUser(roleuser.getUserid(), roleuser.getRoleid());
			}
		}
		result.put("msg", "删除成功");
		return result;
	}

	/**
	 * 
	 * @category 角色里删除用户方法
	 * @author qufan
	 * @param List<DchyRole>
	 * @return 结果信息
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> deleteUser(@RequestBody List<String> userroleidlist) {
		Map<String, Object> result = new HashMap<>();
		if (userroleidlist != null && userroleidlist.size() > 0) {
			for (String userroleid : userroleidlist) {
				dchyroleuserservice.deleteById(userroleid);
			}
		}
		result.put("msg", "删除成功");
		return result;
	}

	/**
	 * @category 查询所有角色
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectRole", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectRole(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		int pageSize = (int) map.get("pageSize");
		int pageNum = (int) map.get("pageNum");
		List<DchyRole> list = new ArrayList<DchyRole>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyroleservice.queryAll();
		PageInfo<DchyRole> pageInfo = new PageInfo<DchyRole>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("rolelist", pageInfo.getList());
//		List<DchyRole> data = dchyroleservice.queryAllByLimit((int) (1 + pageSize * (pageNum - 1)),
//				(int) (pageSize * pageNum));
//		List<DchyRole> data2 = dchyroleservice.queryAll();
//		result.put("rolelist", data);
//		result.put("listSize", data2.size());
		return result;
	}

	/**
	 * @category 查询模糊查询角色
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/findRole", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> findRole(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		int pageSize = (int) map.get("pageSize");
		int pageNum = (int) map.get("pageNum");
		String str1 = (String) map.get("str");
		List<DchyRole> list = new ArrayList<DchyRole>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyroleservice.findRole(str1);
		PageInfo<DchyRole> pageInfo = new PageInfo<DchyRole>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("rolelist", pageInfo.getList());
//		List<DchyRole> data = dchyroleservice.findRoleByLimit((int) (1 + pageSize * (pageNum - 1)),
//				(int) (pageSize * pageNum), str1);
//		List<DchyRole> data2 = dchyroleservice.findRole(str1);
//		result.put("rolelist", data);
//		result.put("listSize", data2.size());
		return result;
	}

	/**
	 * @category 查询角色下所有用户
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectByRole", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectByRole(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		int pageSize = (int) map.get("pageSize");
		int pageNum = (int) map.get("pageNum");
		String roleid = (String) map.get("roleid");

		List<DchyRoleuser> list = new ArrayList<DchyRoleuser>();
		PageHelper.startPage(pageNum, pageSize);
		list = dchyroleuserservice.queryByRoleId(roleid);
		if (list != null && list.size() > 0) {
			for (DchyRoleuser dchyRoleuser : list) {
				dchyRoleuser.setPersonal(dchyPersonalService.queryById(dchyRoleuser.getUserid()));
				dchyRoleuser.setRole(dchyroleservice.queryById(dchyRoleuser.getRoleid()));
			}
		}
		PageInfo<DchyRoleuser> pageInfo = new PageInfo<DchyRoleuser>(list);
		result.put("listSize", pageInfo.getTotal());
		result.put("roleuserlist", pageInfo.getList());
//		List<DchyRoleuser> data = dchyroleuserservice.queryByRoleIdLimit(roleid, (int) (1 + pageSize * (pageNum - 1)),
//				(int) (pageSize * pageNum));
//		if (data != null && data.size() > 0) {
//			for (DchyRoleuser dchyRoleuser : data) {
//				dchyRoleuser.setPersonal(dchyPersonalService.queryById(dchyRoleuser.getUserid()));
//				dchyRoleuser.setRole(dchyroleservice.queryById(dchyRoleuser.getRoleid()));
//			}
//		}
//		List<DchyRoleuser> data2 = dchyroleuserservice.queryByRoleId(roleid);
//		result.put("roleuserlist", data);
//		result.put("listSize", data2.size());
		return result;
	}

	/**
	 * @category 查询角色下所有用户
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/findByRole", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> findByRole(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		String role = (String) map.get("role");
		List<DchyRoleuser> list = new ArrayList<DchyRoleuser>();
		DchyRole dchyRole = dchyroleservice.queryByRole(role);
		list = dchyroleuserservice.queryByRoleId(dchyRole.getId());
		List<DchyPersonal> list2 = new ArrayList<DchyPersonal>();
		if (list != null && list.size() > 0) {
			for (DchyRoleuser dchyRoleuser : list) {
				DchyPersonal personal = dchyPersonalService.queryById(dchyRoleuser.getUserid());
				// dchyRoleuser.setPersonal(dchyPersonalService.queryById(dchyRoleuser.getUserid()));
				// dchyRoleuser.setRole(dchyroleservice.queryById(dchyRoleuser.getRoleid()));
				list2.add(personal);
			}
		}
		result.put("userlist", list2);
//		List<DchyRoleuser> data = dchyroleuserservice.queryByRoleIdLimit(roleid, (int) (1 + pageSize * (pageNum - 1)),
//				(int) (pageSize * pageNum));
//		if (data != null && data.size() > 0) {
//			for (DchyRoleuser dchyRoleuser : data) {
//				dchyRoleuser.setPersonal(dchyPersonalService.queryById(dchyRoleuser.getUserid()));
//				dchyRoleuser.setRole(dchyroleservice.queryById(dchyRoleuser.getRoleid()));
//			}
//		}
//		List<DchyRoleuser> data2 = dchyroleuserservice.queryByRoleId(roleid);
//		result.put("roleuserlist", data);
//		result.put("listSize", data2.size());
		return result;
	}

	/**
	 * @category 查询所有用户
	 * @author qufan
	 * @param
	 * @return 结果信息
	 */
	@RequestMapping(value = "/selectAllUser", method = RequestMethod.POST)
	@CrossOrigin
	@Transactional
	public Map<String, Object> selectAllUser() {
		Map<String, Object> result = new HashMap<>();
		List<DchyPersonal> data = dchyPersonalService.queryAll();
		List<DchyPersonal> data2 = new ArrayList<DchyPersonal>();
		List<DchyPersonal> data3 = new ArrayList<DchyPersonal>();
		if (data != null && data.size() > 0) {
			for (DchyPersonal dchyPersonal : data) {
				if (dchyPersonal.getType().equals("审批人员") || dchyPersonal.getType().equals("管理人员")
						|| dchyPersonal.getType().equals("监管人员")) {
					data2.add(dchyPersonal);
				} else {
					data3.add(dchyPersonal);
				}
			}
		}
		result.put("userdata", data2);
		result.put("personaldata", data3);
		return result;
	}

}