package com.github.DchyService.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.DchyService.dao.*;
import com.github.DchyService.entity.*;
import com.github.pagehelper.StringUtil;

import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DchyBsznService {
	@Autowired
	private DchyBsznDao dchyBsznDao;

	

	@Value("${spring.newsFilePath}")
	private String newsFilePath;



	public String save(Policy policy) {
        String id = IdUtil.simpleUUID();
        Date date = new Date();
        policy.setCreatetime(date);
        policy.setId(id);
		// 新增
        dchyBsznDao.insert(policy);
		return id;
	}

	public void updateById(Policy policy) {
		// 根据主键id修改
		dchyBsznDao.updateById(policy);

	}

	public void deleteById(String articleId) {
		dchyBsznDao.deleteById(articleId);
	}

	 
	
	public Page<Policy> findByPage(Map<String, Object> map, Integer page, Integer size) {
	        //设置查询条件
	        EntityWrapper<Policy> wrapper = new EntityWrapper<>();
	        Set<String> keySet = map.keySet();
	        for (String key : keySet) {
	            // if (map.get(key) != null) {
	            //     wrapper.eq(key, map.get(key));
	            // }
	            if (!key.equals("pageNum") && !key.equals("pageSize")){
	                //上面if语句可以替换为下面的实现动态sql的语句
	            	if ("title".equals(key) || "item".equals(key)) {
	            		if (StringUtil.isNotEmpty(map.get(key).toString())) {
	            			wrapper.like(key, map.get(key).toString());
	            		}
	            	} else {
	            		wrapper.eq(map.get(key) != null, key, map.get(key));
	            	}
	            }
	        }
	        //设置分页参数
	        Page<Policy> pageData = new Page<>(page, size);
	        //执行查询，第一个是分页参数，第二个是查询条件
	        List<Policy> list = dchyBsznDao.selectPage(pageData, wrapper);
	        pageData.setRecords(list);

	        //返回
	        return pageData;
	    }
	
	public List<Map<String, Object>> getPolicyList(Map<String, Object> map) {
		return dchyBsznDao.getPolicyList(map);
	}

	// 获取html中的src地址
	public static Set<String> getImgStr(String htmlStr) {
		Set<String> pics = new HashSet<>();
		String img = "";
		Pattern p_image;
		Matcher m_image;
		// 图片链接地址
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			// 得到<img />数据
			img = m_image.group();
			// 匹配<img>中的src数据
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

	// 提取src地址中的图片地址(src包含图片和表情)
	public List<String> getImages(List<String> imgSrc) {
		List<String> images = new ArrayList<>();
		String[] nfps = newsFilePath.split("/");
		for (String img : imgSrc) {
			String[] strings = img.split("/");
			for (int i = 0; i < strings.length; i++) {
				if (strings[i].indexOf(nfps[1]) != -1) {
					images.add(img);
				}
			}
		}
		return images;
	}

	public Policy findByPolicyId(String id) {
		dchyBsznDao.updatePageViewsById(id);
		return dchyBsznDao.selectByPolicyId(id);
	}

	public Policy findByPolicyItem(String item) {
//		dchyBsznDao.updatePageViewsById(item);
		return dchyBsznDao.selectByPolicyItem(item);
	}

	public void updateStateById(String id,String state, String dateStr) {
	    dchyBsznDao.updateStateById(id,state,dateStr);	
	   }

	public List<Policy> selectBykey(String key) {
		
		return dchyBsznDao.selectBykey(key);
	}
}
