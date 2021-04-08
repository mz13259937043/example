package com.github.DchyService.controller.bsdt;

import com.github.DchyService.entity.*;
import com.github.DchyService.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class DchyBsznController {
	private Logger log = LoggerFactory.getLogger(DchyBsznController.class);
	@Autowired
	private DchyBsznService dchyBsznService;
	@Autowired
	private FjService fjService;

	// 根据ID删除(真删除)
	@GetMapping("/api/deletePolicy")
	@CrossOrigin
	public Result deleteById(@RequestParam("id") String id) {
		dchyBsznService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	// 修改
	@PostMapping("/api/updatePolicy")
	@CrossOrigin
	public Result updateById(@RequestBody Policy policy) {
		Date date = new Date();
		policy.setUpdatetime(date);
		dchyBsznService.updateById(policy);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	// 增加
	@PostMapping("/api/addPolicy")
	@CrossOrigin
	public Map<String, Object> save(@RequestBody Policy policy) {
		String id = dchyBsznService.save(policy);
		Map<String, Object> map = new HashMap<>();
		map.put("flag", true);
		map.put("StatusCode", StatusCode.OK);
		map.put("message", "新增成功");
		map.put("id", id);
		return map;
	}

	// 分页-mybatisplus
	@PostMapping("/api/findPolicyList")
	@CrossOrigin
	public Map<String, Object> findByPage(@RequestBody Map<String, Object> map) {
		Integer pageNum = (Integer) map.get("pageNum");
		Integer pageSize = (Integer) map.get("pageSize");
//        Page<Policy> pageData = dchyBsznService.findByPage(map, pageNum, pageSize);
//
//        Map<String, Object> Result = new HashMap<String, Object>();
//        Result.put("code", 0);
//        Result.put("msg", "ok");
//        Result.put("count", pageData.getRecords().size());
//        Result.put("data", pageData.getRecords());
		Map<String, Object> result = new HashMap<String, Object>();
		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> res = dchyBsznService.getPolicyList(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(res);

		result.put("code", 200);
		result.put("msg", "OK");
		result.put("data", pageInfo.getList());
		result.put("count", pageInfo.getTotal());

		return result;
	}

	// 根据ID查询
	@GetMapping("/api/findPolicyById")
	@CrossOrigin
	public Result findById(@RequestParam("id") String id) {
		Policy policy = dchyBsznService.findByPolicyId(id);
		List<Map> filelist = new ArrayList<Map>();
		if (policy.getFjid() != null) {
			String fjid = policy.getFjid();
			DchyFjFolder fjfolder = fjService.getFolderByKey(fjid);
//			List<String> filename = new ArrayList<String>();
//			List<String> filepath = new ArrayList<String>();
			Map<String, String> file = new HashMap<String, String>();
			List<DchyFjFolder> FjFolderList = fjfolder.getDchyFjFolders();
			if (FjFolderList != null && FjFolderList.size() > 0) {
				for (DchyFjFolder dchyFjFolder : FjFolderList) {
					List<DchyFjRecord> fjrecordsList = dchyFjFolder.getDchyFjRecords();
					if (fjrecordsList.size() > 0 && fjrecordsList != null) {
						for (DchyFjRecord fjrecords : fjrecordsList) {
							file.put(fjrecords.getFilename(),
									fjfolder.getPath() + "/" + fjrecords.getFilepath() + "/" + fjrecords.getFilename());
							filelist.add(file);
//							filename.add(fjrecords.getFilename());
//							filepath.add(fjfolder.getPath() + "/" + fjrecords.getFilename());
						}
					}
				}
			}
		}
		return new Result(true, StatusCode.OK, "查询成功", policy,filelist);
	}

	@GetMapping("/api/findPolicyByItem")
	@CrossOrigin
	public Result findByItem(@RequestParam("item") String item) {
		Policy policy = dchyBsznService.findByPolicyItem(item);
		return new Result(true, StatusCode.OK, "查询成功", policy);
	}

	// 根据ID修改状态
	@GetMapping("/api/updateStateById")
	@CrossOrigin
	public Result updateStateById(@RequestParam("id") String id, @RequestParam("state") String state) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = df.format(date);
		dchyBsznService.updateStateById(id, state, dateStr);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	// 根据关键字模糊查询
	@PostMapping("/api/selectBykey")
	@CrossOrigin
	public Map<String, Object> selectByKey(@RequestBody Map<String, Object> map) {
		Integer pageNum = (Integer) map.get("pageNum");
		Integer pageSize = (Integer) map.get("pageSize");
		String key = (String) map.get("key");
		PageHelper.startPage(pageNum, pageSize);
		List<Policy> list = dchyBsznService.selectBykey(key);
		PageInfo<Policy> pageInfo = new PageInfo<Policy>(list);
		Map<String, Object> Result = new HashMap<String, Object>();
		Result.put("code", 0);
		Result.put("msg", "ok");
		Result.put("count", pageInfo.getTotal());
		Result.put("data", pageInfo.getList());
		return Result;
	}

}
