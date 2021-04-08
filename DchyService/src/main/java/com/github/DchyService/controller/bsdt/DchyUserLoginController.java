package com.github.DchyService.controller.bsdt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.DchyService.tools.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.entity.DchyCompany;
import com.github.DchyService.entity.DchyPersonal;
import com.github.DchyService.entity.DchyRole;
import com.github.DchyService.entity.DchyRoleuser;
import com.github.DchyService.entity.DchyZzxx;
import com.github.DchyService.service.DchyRoleService;
import com.github.DchyService.service.DchyRoleuserService;
import com.github.DchyService.service.DchyUserService;
import com.github.DchyService.service.DchyZzxxService;
import com.github.DchyService.tools.HttpClint;
import com.github.DchyService.tools.JwtUtil;
import com.github.DchyService.tools.Md5;

import cn.hutool.core.util.IdUtil;

@Controller
public class DchyUserLoginController {
	private Logger log = LoggerFactory.getLogger(DchyUserLoginController.class);
	@Value("${APP_ID}")
	private String APP_ID;
	@Value("${AppSecret}")
	private String AppSecret;
	@Value("${spring.loaduserurl}")
	private String loaduserurl;
	@Value("${spring.saveUserUrl}")
	private String saveUserUrl;
	@Value("${spring.redirectUrl}")
	private String redirectUrl;
	@Value("${cas-server-logout-url}")
	private String serverLogoutUrl;
	@Value("${cas-logout-url}")
	private String logoutUrl;
	@Value("${cas-logout-url1}")
	private String logoutUrl1;
	@Value("${cas-server-getTokenByReq}")
	private String getTokenByReq;

	@Resource
	private HttpClint httpClint;
	@Resource
	private DchyUserService dchyUserService;
	@Resource
	private DchyRoleuserService dchyRoleuserService;

	private String guid = "";

	private String pType = "";

	private int isZzxx = 0;

	private String info = "";

	@Resource
	private DchyZzxxService dchyZzxxService;
	@Resource
	private DchyRoleService dchyRoleService;

	private List<String> grRole = new ArrayList<String>();

	@GetMapping("/login")
	// @ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse reponse, HttpSession session, String tel,
			String password) throws UnsupportedEncodingException {
		String userid = request.getRemoteUser();
		// String userid = "254684481098235904";
		info = request.getParameter("info");
		// String userid = "516606647832895488";
		Map<String, Object> map = new HashMap<String, Object>();
		String simpleUUID = IdUtil.simpleUUID();
		try {
			String token = JwtUtil.createJWT(simpleUUID, userid, JSON.toJSONString(map), 60 * 60 * 5 * 1000);
			Map<String, Object> userMap = loaduser(userid);
			map.put("token", token);
			map.put("userMap", userMap);
			// map转String
			String json = JSON.toJSONString(map);
			String userJson = JSON.toJSONString(userMap);
			// 对存入cookie数据进行编码，否则报错
			String encodeCookie = URLEncoder.encode(json, "utf-8");
//            System.out.println(encodeCookie);
//            JSONObject jsonObject = JSON.parseObject(json);//String转json
//            Cookie tokencookie = new Cookie("token", token);

			Cookie cookie = new Cookie("userCookie", encodeCookie);
			Map<String, Object> info_information = (Map<String, Object>) userMap.get("INFO_INFORMATION");
			String register_type = (String) info_information.get("REGISTER_TYPE");
			 if("register:company".equals(register_type)) { 
				 if(info.equals("chsgcs")) {
				  Map<String, Object> map1 = new HashMap<>(); map1.put("userid", userid);
				  Map<String, Object> tokenByReq = this.getTokenByReq(map1);
				  Map m =(Map)tokenByReq.get("data"); 
				  String token1 = (String)m.get("token");
		          return "redirect:" + logoutUrl1 +token1+"&redirect_uri=http://127.0.0.1:8080/index.html?logout=123";
		        }	
		  } else if("register:personal".equals(register_type)) {
			if(info.equals("chdw")||info.equals("yzdw")) {
					 Map<String, Object> map1 = new HashMap<>();
					 map1.put("userid", userid);
					 Map<String, Object> tokenByReq = this.getTokenByReq(map1);
					 Map m =(Map)tokenByReq.get("data"); 
					 String token1 = (String)m.get("token");
			      return "redirect:" + logoutUrl1 +token1+"&redirect_uri=http://127.0.0.1:8080/index.html?logout=123";
				}
			 }
			// 发送请求存储用户数据
			ResponseEntity<String> call = httpClint.call(saveUserUrl, userJson);
			String body = call.getBody();
			JSONObject jsons = JSON.parseObject(body);
			String code = jsons.getString("code");

			// 要取它里面的值的时候，再对其进行解码：
			/*
			 * String decode = URLDecoder.decode(encodeCookie, "utf-8");
			 * System.out.println(decode);
			 */

			reponse.addCookie(cookie);

			// 跳转路径 "redirect:" +
			// "http://127.0.0.1/frontweb/view/jsscPage/form/register.jsp?code="+code
//            String url = request.getScheme() + "://" + request.getServerName() + ":" + redirectPort + redirectPath;

			if (info.equals("chdw")) {
				return "redirect:" + redirectUrl + "?code=" + code + "&id=" + guid + "&zwwid=" + userid + "&state="
						+ isZzxx + "&info=" + info;

			} else if (info.equals("gr")) {
				grRole.clear();
				return "redirect:" + redirectUrl + "?code=" + code + "&id=" + guid + "&zwwid=" + userid + "&pType="
						+ pType + "&info=" + info;

			} else {
				return "redirect:" + redirectUrl + "?code=" + code + "&id=" + guid + "&zwwid=" + userid + "&info="
						+ info;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 跳转路径 "redirect:" +
			// "http://127.0.0.1/frontweb/view/jsscPage/form/register.jsp?code=error"
			return "redirect:" + redirectUrl + "?code=error";
		}

	}

	@PostMapping("/login2")
	@ResponseBody
	public DchyPersonal login2(HttpServletRequest request, HttpServletResponse reponse, HttpSession session,
			@RequestBody DchyPersonal user) throws Exception {
		if (user != null) {
			if (user.getPassword().isEmpty() || user.getPhonenum().isEmpty()) {
				return null;
			}
		}
		try {
			user = dchyUserService.loginBytel(user);
		} catch (Exception e) {
			log.info("登录出错！，电话或密码错误");
			e.printStackTrace();
			return null;
		}

		return user;
	}

	public String listToString(List<String> list, char separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
	}

	private Map<String, Object> loaduser(String userid) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> UNI_BSS_HEAD = new HashMap<>();
		Map<String, Object> UNI_BSS_BODY = new HashMap<>();
		Map<String, Object> UNI_BSS_ATTACHED = new HashMap<>();
		UNI_BSS_HEAD.put("APP_ID", APP_ID);
		long time = new Date().getTime();
		String TOKEN = Md5.tokenValue(APP_ID, time + "", time + "100111111", AppSecret);
		UNI_BSS_HEAD.put("TIMESTAMP", time + "");
		UNI_BSS_HEAD.put("TRANS_ID", time + "100111111");
		UNI_BSS_HEAD.put("TOKEN", TOKEN);
		Map<String, Object> SELECT_DETAILS_BY_ID_REQ = new HashMap<>();
		SELECT_DETAILS_BY_ID_REQ.put("USER_ID", userid);
		UNI_BSS_BODY.put("SELECT_DETAILS_BY_ID_REQ", SELECT_DETAILS_BY_ID_REQ);
		UNI_BSS_ATTACHED.put("MEDIA_INFO", "");
		map.put("UNI_BSS_HEAD", UNI_BSS_HEAD);
		map.put("UNI_BSS_BODY", UNI_BSS_BODY);
		map.put("UNI_BSS_ATTACHED", UNI_BSS_ATTACHED);
		System.out.println(JSON.toJSONString(map));

		ResponseEntity<String> call = httpClint.call(loaduserurl, JSON.toJSONString(map));
		System.out.println(call);
		String body = call.getBody();
		JSONObject jsons = JSON.parseObject(body);
		JSONObject UNI_BSS_BODY1 = (JSONObject) jsons.get("UNI_BSS_BODY");
		JSONObject SELECT_DETAILS_BY_ID_RSP = (JSONObject) UNI_BSS_BODY1.get("SELECT_DETAILS_BY_ID_RSP");

		Object info_person = SELECT_DETAILS_BY_ID_RSP.get("INFO_PERSON");
		Object info_information = SELECT_DETAILS_BY_ID_RSP.get("INFO_INFORMATION");
		Object info_legal = SELECT_DETAILS_BY_ID_RSP.get("INFO_LEGAL");
		if (!info_person.equals("")) {
			JSONObject INFO_PERSON = (JSONObject) SELECT_DETAILS_BY_ID_RSP.get("INFO_PERSON");
			map.put("INFO_PERSON", INFO_PERSON);
		}
		if (!info_information.equals("")) {
			JSONObject INFO_INFORMATION = (JSONObject) SELECT_DETAILS_BY_ID_RSP.get("INFO_INFORMATION");
			map.put("INFO_INFORMATION", INFO_INFORMATION);
		}
		if (!info_legal.equals("")) {
			JSONObject INFO_LEGAL = (JSONObject) SELECT_DETAILS_BY_ID_RSP.get("INFO_LEGAL");
			map.put("INFO_LEGAL", INFO_LEGAL);
		}
		map.put("USER_ID", userid);
		return map;
	}

	/**
	 * 保存用户
	 *
	 * @param map
	 *
	 */
	@PostMapping("/user/save")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> saveUser(@RequestBody Map map) {
		Map<String, Object> info_person = (Map<String, Object>) map.get("INFO_PERSON");
		Map<String, Object> info_information = (Map<String, Object>) map.get("INFO_INFORMATION");
		Map<String, Object> info_legal = (Map<String, Object>) map.get("INFO_LEGAL");
		String register_type = (String) info_information.get("REGISTER_TYPE");
		// String idCard = (String) info_person.get("ID_NUMBER");
		// Date date = new Date(System.currentTimeMillis());
		DchyCompany dchyCompany = new DchyCompany();
		Map<String, Object> msg = new HashMap<>();
		// 法人登录
		if (register_type.equals("register:company")) {
			String licenseNo = (String) info_legal.get("LICENSE_NO");
			DchyCompany Company = dchyUserService.queryCompanyByLicenseNo(licenseNo);
			// 不是第一次登陆
			if (Company != null) {
				String dwmc = (String) info_legal.get("ENTERPRISE_NAME");
				String dwxz = (String) info_legal.get("ORG_TYPE");
				String qydz = (String) info_legal.get("ENTERPRISE_ADDRESS");
				String jyfw = (String) info_legal.get("BUSI_ARRANGE");
				String frxm = (String) info_legal.get("LEGAL_NAME");
				String frsfz = (String) info_legal.get("LEGAL_ID_NUMBER");
				String frdh = (String) info_legal.get("LINK_PHONE");
				String hylxmc = (String) info_legal.get("INDUSTRY_TYPE_NAME");
				String jyqx = (String) info_legal.get("OPERATING_PERIOD");
				String zczj = (String) info_legal.get("REGISTERED_CAPITAL");
				String cjsjStr = (String) info_legal.get("REGISTERED_DATE");
				if (!cjsjStr.equals("")) {
					String year = cjsjStr.substring(0, 4);
					String mounth = cjsjStr.substring(4, 6);
					String dateStr = cjsjStr.substring(6, 8);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String dateStr1 = year + "-" + mounth + "-" + dateStr;
					Date cjsj = null;
					try {
						cjsj = format.parse(dateStr1);
						dchyCompany.setCjsj(cjsj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				dchyCompany.setTyshxydm(licenseNo);
				dchyCompany.setDwmc(dwmc);
				dchyCompany.setDwxz(dwxz);
				dchyCompany.setQydz(qydz);
				dchyCompany.setJyfw(jyfw);
				dchyCompany.setFrxm(frxm);
				dchyCompany.setFrsfz(frsfz);
				dchyCompany.setFrdh(frdh);
				dchyCompany.setHylxmc(hylxmc);
				dchyCompany.setJyqx(jyqx);
				dchyCompany.setZczj(zczj);
				int i = dchyUserService.updateCompany(dchyCompany);
				guid = dchyUserService.getIdByLicenseNo(licenseNo);
				if (info.equals("chdw")) {
					DchyZzxx zxxx = dchyZzxxService.queryByDid(guid);
					if (zxxx != null) {
						isZzxx = 1;
					} else {
						isZzxx = 0;

					}
				}

				if (i > 0) {
					msg.put("code", "success");
					msg.put("data", "");
				}
				return msg;
			} else {
				String dwmc = (String) info_legal.get("ENTERPRISE_NAME");
				String dwxz = (String) info_legal.get("ORG_TYPE");
				String qydz = (String) info_legal.get("ENTERPRISE_ADDRESS");
				String jyfw = (String) info_legal.get("BUSI_ARRANGE");
				String frxm = (String) info_legal.get("LEGAL_NAME");
				String frsfz = (String) info_legal.get("LEGAL_ID_NUMBER");
				String frdh = (String) info_legal.get("LINK_PHONE");
				String hylxmc = (String) info_legal.get("INDUSTRY_TYPE_NAME");
				String jyqx = (String) info_legal.get("OPERATING_PERIOD");
				String zczj = (String) info_legal.get("REGISTERED_CAPITAL");
				String cjsjStr = (String) info_legal.get("REGISTERED_DATE");
				if (!cjsjStr.equals("")) {
					String year = cjsjStr.substring(0, 4);
					String mounth = cjsjStr.substring(4, 6);
					String dateStr = cjsjStr.substring(6, 8);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String dateStr1 = year + "-" + mounth + "-" + dateStr;
					Date cjsj = null;
					try {
						cjsj = format.parse(dateStr1);
						dchyCompany.setCjsj(cjsj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				dchyCompany.setTyshxydm(licenseNo);
				dchyCompany.setDwmc(dwmc);
				dchyCompany.setDwxz(dwxz);
				dchyCompany.setQydz(qydz);
				dchyCompany.setJyfw(jyfw);
				dchyCompany.setFrxm(frxm);
				dchyCompany.setFrsfz(frsfz);
				dchyCompany.setFrdh(frdh);
				dchyCompany.setHylxmc(hylxmc);
				dchyCompany.setJyqx(jyqx);
				dchyCompany.setZczj(zczj);
				int i = dchyUserService.insertCompany(dchyCompany);
				guid = dchyUserService.getIdByLicenseNo(licenseNo);
				if (info.equals("chdw")) {
					DchyZzxx zxxx = dchyZzxxService.queryByDid(guid);
					if (zxxx != null) {
						isZzxx = 1;
					} else {
						isZzxx = 0;

					}
				}
				if (i > 0) {
					msg.put("code", "success");
					msg.put("data", "");
				}
				return msg;
			}

		} else {
			if (register_type.equals("register:personal")) {
				String idcard = (String) info_person.get("ID_NUMBER");
				DchyPersonal personal = dchyUserService.getPersonalByidCard(idcard);
				if (personal != null) {
					guid = personal.getId();
					List<DchyRoleuser> dchyRoleusers = dchyRoleuserService.queryAllByUid(guid);
					if (dchyRoleusers.size() > 0) {
						for (DchyRoleuser dchyRoleuser : dchyRoleusers) {
							String roleid = dchyRoleuser.getRoleid();
							DchyRole dchyRole = dchyRoleService.queryById(roleid);
							if (dchyRole != null) {
								String role = dchyRole.getRole();
								if (role.equals("局内审批")) {
									grRole.add("jnsp");
								} else if (role.equals("测绘工程师")) {
									grRole.add("chgcs");
								} else if (role.equals("高级工程师")) {
									grRole.add("gjgcs");
								}
							}

						}
					}

					pType = listToString(grRole, ',');
				} else {
					guid = "";
					pType = "ylry";

				}
				msg.put("code", "success");
				msg.put("data", "");
				return msg;

			}

		}

		msg.put("code", "fail");
		msg.put("data", "");
		return msg;
	}

	@GetMapping("/user/findCompanybyId")
	@ResponseBody
	@CrossOrigin
	public DchyCompany findCompanybyId(String id) {
		DchyCompany company = dchyUserService.findCompanybyId(id);
		DchyZzxx zzxx = dchyZzxxService.queryByDid(id);
		if (zzxx != null) {
			company.setZzxx(zzxx);
		}
		return company;
	}

	@GetMapping("/user/findCompanybyLicenseNo")
	@ResponseBody
	@CrossOrigin
	public DchyCompany findCompanybyLicenseNo(String licenseNo) {
		String id = dchyUserService.getIdByLicenseNo(licenseNo);
		if (id == null) {
			return null;
		}
		DchyCompany company = dchyUserService.findCompanybyId(id);
		DchyZzxx zzxx = dchyZzxxService.queryByDid(id);
		if (zzxx != null) {
			company.setZzxx(zzxx);
		}
		return company;
	}

	@GetMapping("/user/findPersonalbyId")
	@ResponseBody
	@CrossOrigin
	public DchyPersonal findPersonalbyId(String id) {
		DchyPersonal personal = dchyUserService.findPersonalbyId(id);
		List<DchyRoleuser> roleuserlist = dchyRoleuserService.queryAllByUid(id);
		List<DchyRole> rolelist = new ArrayList<DchyRole>();
		for (DchyRoleuser dchyRoleuser : roleuserlist) {
			rolelist.add(dchyRoleService.queryById(dchyRoleuser.getRoleid()));
		}
		personal.setRolelist(rolelist);
		return personal;
	}

	@GetMapping("/user/findPersonalbyidCard")
	@ResponseBody
	@CrossOrigin
	public DchyPersonal findPersonalbyIdcard(String idCard) {
		DchyPersonal personal = dchyUserService.getPersonalByidCard(idCard);
		if (personal == null) {
			return null;
		}
		List<DchyRoleuser> roleuserlist = dchyRoleuserService.queryAllByUid(personal.getId());
		List<DchyRole> rolelist = new ArrayList<DchyRole>();
		for (DchyRoleuser dchyRoleuser : roleuserlist) {
			rolelist.add(dchyRoleService.queryById(dchyRoleuser.getRoleid()));
		}
		personal.setRolelist(rolelist);
		return personal;
	}

	/**
	 * @Description //清楚自己本地登录信息
	 * @Author maoZhan
	 * @Date 2020/12/1 9:41
	 * @param *        @param session
	 * @param request
	 * @param response
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@PostMapping("/loginout")
	@ResponseBody
	@CrossOrigin
	public Map<String, String> loginout2(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map = new HashMap<>();
		try {
			String jsessionid = CookieUtil.getUid(request, "JSESSIONID");
//			String ftoken = CookieUtil.getUid(request, "ftoken");
			String userCookie = CookieUtil.getUid(request, "userCookie");
			if (jsessionid != null) {
				CookieUtil.removeCookie(response, "JSESSIONID");
			}
			/*
			 * if (ftoken != null) { CookieUtil.removeCookie(response, "ftoken"); }
			 */
			if (userCookie != null) {
				CookieUtil.removeCookie(response, "userCookie");
			}
			session.invalidate();
			map.put("type", "success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("type", "error");
			map.put("message", e.getMessage());
			return map;
		}
	}

	/**
	 * @Description //从后台那个url地址进行返回首页（暂时用不到）
	 * @Author maoZhan
	 * @Date 2020/12/1 9:34
	 * @param * @param
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@PostMapping("/loadloginouturl")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> loadLoginoutUrl() {
		Map<String, Object> map = new HashMap<>();
		try {
			String loginouturl = serverLogoutUrl + "?service=" + logoutUrl;
			map.put("loginouturl", loginouturl);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("type", "error");
			map.put("message", e.getMessage());
			return map;
		}
	}

	/**
	 * @Description //从政务网的回调拿到token，用户信息
	 * @Author maoZhan
	 * @Date 2020/12/1 9:34
	 * @param * @param json
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@RequestMapping("/getTokenByReq")
	@ResponseBody
	@CrossOrigin
	public Map<String, Object> getTokenByReq(@RequestBody Map<String, Object> json) {
		Map<String, Object> map = new HashMap<>();
//        Map<String, Object> maps1 = httpClint.dopost(getTokenByReq + json.get("userid").toString(), "");
		ResponseEntity<String> entity = httpClint.call(getTokenByReq + json.get("userid").toString(), "");
		String body = entity.getBody();
		map = JSON.parseObject(body);
		return map;
	}

	/**
	 * 修改企业信息
	 */
	@PostMapping("/updateCompany")
	@ResponseBody
	@CrossOrigin
	public int updateCompany(@RequestBody DchyCompany company) {
		return dchyUserService.updateCompany(company);
	}
}
