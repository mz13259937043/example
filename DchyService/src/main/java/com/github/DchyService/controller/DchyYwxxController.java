package com.github.DchyService.controller;

import com.github.DchyService.dao.DchyYwxxDao;
import com.github.DchyService.entity.DchyFbxx;
import com.github.DchyService.entity.DchyHtxx;
import com.github.DchyService.entity.DchyLog;
import com.github.DchyService.entity.DchyPersonal;
import com.github.DchyService.entity.DchyYwxx;
import com.github.DchyService.service.DchyFbxxService;
import com.github.DchyService.service.DchyHtxxService;
import com.github.DchyService.service.DchyLogService;
import com.github.DchyService.service.DchyPersonalService;
import com.github.DchyService.service.DchyYwxxService;
import com.github.DchyService.tools.ConvertyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;


/**
 * (DchyYwxx)表控制层
 *
 * @author makejava
 * @since 2020-10-28 10:23:16
 */
@RestController
@RequestMapping("dchy/dchyYwxx")
public class DchyYwxxController {

    private Logger log = LoggerFactory.getLogger(DchyYwxxController.class);
    /**
     * 服务对象
     */
    @Resource
    private DchyYwxxService dchyYwxxService;

    @Resource
    private DchyYwxxDao dchyYwxxDao;

    @Resource
    private DchyHtxxService dchyHtxxService;

    @Resource
    private DchyLogService dchyLogService;

    @Resource
    private DchyPersonalService dchyPersonalService;
    
    @Resource
    private DchyFbxxService dchyFbxxService;
    @Resource
    private ConvertyUtil convertyUtil;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("findYwxxById")
    public DchyYwxx findById(String id) {
        return this.dchyYwxxService.findById(id);
    }

    /**
     * 通过对象查询数据集合
     *
     * @param String id
     * @return map
     */
    @GetMapping(value="/getNoticeMsgByYwxxid")
    @ResponseBody
    public Map<String,Object> getNoticeMsgByYwxxid(String ywxxid){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> ywxx = new HashMap<String, Object>();
        ywxx = dchyYwxxDao.getNoticeMsgByYwxxid(ywxxid);
        if (ywxx!=null){
            map.put("code", 200);
            map.put("msg", "ok");
            map.put("data", ywxx);
        }else{
            map.put("code", 0);
            map.put("msg", "ok");
            map.put("data", null);
        }
        return map;
    }
    @PostMapping(value="/getYwxxList")
    @ResponseBody
    public Map<String,Object> findList(@RequestBody DchyYwxx dchyYwxx){
        Map<String, Object> map = new HashMap<String, Object>();
        List<DchyYwxx> list = new ArrayList<DchyYwxx>();
        PageHelper.startPage(dchyYwxx.getPageNum(),dchyYwxx.getPageSize());
        list = dchyYwxxService.findList(dchyYwxx);
        if (list.size()==0){
            /*DchyYwxx ywxx = new DchyYwxx();
            List<DchyLog> logList = new ArrayList<>();
            DchyLog log = new DchyLog();

            log.setDqhj("成果汇交");
            logList.add(log);
            ywxx.setLogsList(logList);
            list.add(ywxx);*/
        }else {
            for (int i = 0; i < list.size(); i++) {
                String listid = list.get(i).getId();
                DchyLog dl = dchyLogService.getLastLog(listid);
                List<DchyLog> findList = new ArrayList<DchyLog>();
                findList.add(dl);
                list.get(i).setLogsList(findList);
            }
        }
        PageInfo<DchyYwxx> pageInfo = new PageInfo<DchyYwxx>(list);
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 保存数据
     *
     * @param dchyYwxx
     * @return DchyHtxx
     */
    @RequestMapping(value="/saveYwxx")
    @ResponseBody
    public Map<String,Object> saveLog(@RequestBody List<DchyYwxx>  dchyYwxx){
        List<DchyYwxx>  list = dchyYwxx;
        Map<String, Object> map = new HashMap<String, Object>();
        List<DchyYwxx> returnVal = new ArrayList();
        int i = 0;
        for ( i = 0;i<list.size();i++){
            DchyYwxx ywxx = dchyYwxxService.insert(list.get(i));
            returnVal.add(ywxx);
        }
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("data", returnVal);
        return map;
    }

    /**
     * 更新数据
     *
     * @param dchyYwxx
     * @return DchyHtxx
     */
    @RequestMapping(value="/updateYwxx")
    @ResponseBody
    public DchyYwxx update(@RequestBody DchyYwxx dchyYwxx){
        return dchyYwxxService.update(dchyYwxx);
    }



    /**
     * 通过主键删除单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value="deleteYwxxByKey")
    public boolean deleteById(String id) {
        return dchyYwxxService.deleteById(id);
    }

    /**
     * 根据操作更新数据
     *  成果汇交
     * @param dchyYwxx
     * @return DchyHtxx
     */
    @RequestMapping(value="/ywxxSend")
    @ResponseBody
    public Map<String,Object> ywxxSend(@RequestBody DchyYwxx dchyYwxx){
        Timestamp nowTimeStamp = new Timestamp(new Date().getTime());
        Map<String, Object> map = new HashMap<String, Object>();
        DchyLog returenLog = new DchyLog();
        DchyLog nowlog = new DchyLog();
        DchyFbxx fbxx= new DchyFbxx();
        List<DchyYwxx> returnYwxx = new ArrayList<>();
//        DchyPersonal person = new DchyPersonal();

        fbxx.setId(dchyYwxx.getPid());
        fbxx = dchyFbxxService.getFbxx(fbxx);
        // 发送状态
        String fszt = dchyYwxx.getFszt();
        // 根据pid获取合同信息
        DchyHtxx htxx = new DchyHtxx();
        htxx.setPid(dchyYwxx.getPid());
        List<DchyHtxx> htxxList = dchyHtxxService.queryAll(htxx);
        DchyHtxx retHtxx = htxxList.get(0);
        // 根据身份证号查询人员信息 获取人员信息所用
//        String idCard = dchyYwxx.getFzrsfz();
//        if(!("").equals(idCard) && (idCard!=null)){
//            person =  dchyPersonalService.queryByIdcard(idCard);
//        }
        // 根据ID 获取 日志信息
        DchyLog dchylog = new DchyLog();
        dchylog.setId(dchyYwxx.getYl2());
        List<DchyLog> logLists = dchyLogService.findList(dchylog);
        dchyYwxx.setYl2(""); // yl2只用来借助传递数据  不做保存
        if(logLists.size() > 0){
            nowlog = logLists.get(0);
        }
        DchyLog newDchylog = new DchyLog();
        newDchylog.setFssj(nowTimeStamp);
        newDchylog.setJssj(nowTimeStamp);
        newDchylog.setClsx(nowlog.getClsx());
        newDchylog.setYwlx(dchyYwxx.getXmjd());
        newDchylog.setDqhj(dchyYwxx.getXyhj());
        newDchylog.setFsrid(nowlog.getJsrid());
        newDchylog.setFsrxm(nowlog.getJsrxm());
        newDchylog.setPid(nowlog.getPid());
        newDchylog.setClzt("待办");
        newDchylog.setCjsj(nowTimeStamp);
        // 前台传递的成果信息
        ArrayList cgxx = (ArrayList)dchyYwxx.getCgxx();
        if("1".equals(fszt)){ // 状态（1）  对应 成果汇交 --> 成果确认
        	// 更新主表的负责人信息    不需要负责人了
//        	DchyFbxx df = new DchyFbxx();
//        	df.setId(dchyYwxx.getPid());
//        	df.setChcgfzr(dchyYwxx.getChcgfzr());
//        	df.setChcgfzrdh(dchyYwxx.getFzrdh());
//        	df.setChcgfzrsfzh(dchyYwxx.getFzrsfz());
//        	if(dchyYwxx.getChcgfzr() != null && !("").equals(dchyYwxx.getChcgfzr())){
//                dchyFbxxService.renewFbxx(df);
//            }
        	// 循环处理成果信息
        	for (int i = 0; i < cgxx.size(); i++) {
        		Map cgmap = (Map) cgxx.get(i);
        		// 获得测量事项
        		String clsx = (String) cgmap.get("name");

        		// 获得每一项的成果信息数组
        		ArrayList chgxx = (ArrayList) cgmap.get("children");
        		if(chgxx.size() > 0) {
	        		for (int j = 0; j < chgxx.size(); j++) {
	        			// 获取具体的成果信息
	        			Map realCg = (Map) chgxx.get(j);
	        			// 获取id  如果是二次提交的话不在重复插入数据
	        			String yxid = (String) realCg.get("id");
	        			// 获取地籍编号
	        			String djbh = (String) realCg.get("djbh");
	        			// 获取宗地代码
	        			String zddm = (String) realCg.get("zddm");
	        			// 获取成果附件ID
	        			String fjid = (String) realCg.get("cgfj");
	        			// 获取成果审核人
	        			String fjjbr = (String) realCg.get("fjjbr");
	        			// 获取成果审核人id
	        			String fjjbrid = (String) realCg.get("fjjbrid");
	        			// 获取成果汇交状态
	        			String cghjzt = (String) realCg.get("cghjzt");
	        			// 每个小阶段对应的数据库id
                        String cgjdid = (String) realCg.get("cgid");
	        			// 根据	项目id，测绘阶段，地籍编号，宗地代码   查询是否已有数据
	        			DchyYwxx findDY = new DchyYwxx();
	        			List<DchyYwxx> isHaveData = new ArrayList<DchyYwxx>();
	        			if(yxid != null && !("").equals(yxid)) {
	        				findDY.setId(yxid);
	        				isHaveData = dchyYwxxService.findList(findDY);
	        			}
	        			findDY.setYwmc(clsx);
                        findDY.setYl3(cgjdid);
	        			findDY.setDjh(djbh);
	        			findDY.setBdcdyh(zddm);
	        			findDY.setPid(dchyYwxx.getPid());
	        			findDY.setCghjfj(fjid);
	        			findDY.setCgshr(fjjbr);
	        			findDY.setCgshrid(fjjbrid);
	        			findDY.setXmjd(dchyYwxx.getXmjd());
	        			findDY.setCghjsj(nowTimeStamp);
	        			findDY.setCghjzt(cghjzt);
	        			// 如果有业务信息数据则更新数据  
	        			if(isHaveData.size() > 0) {
	        				String befcghjzt = isHaveData.get(0).getCghjzt();
	        				findDY.setId(isHaveData.get(0).getId());
	        				DchyYwxx updateYwxx = dchyYwxxService.update(findDY);
	        				returnYwxx.add(dchyYwxxService.update(findDY));
	        				String shzt = updateYwxx.getCgshqr();
	        				String cgyszt = updateYwxx.getCgyszt();
	        				String cgsfsh = updateYwxx.getCgsfsh();
	        				//如果有退回操作   或者状态由保存变为提交   则新插入日志信息
	        				if( (shzt != null && shzt.equals("未通过")) ||(cgyszt != null && cgyszt.equals("未通过"))
	        						|| (cgsfsh != null && cgsfsh.equals("未通过")) || (("保存").equals(befcghjzt) && ("提交").equals(cghjzt))) {
	        					// 有几个结果循环插入多条新的 测绘单位发送给测绘工程师的信息 为测绘工程师确认
//	        					newDchylog.setJsrid(person.getId());
//	        					newDchylog.setJsrxm(dchyYwxx.getChcgfzr());
	        					// 下一阶段变为   测绘单位确认
	        					newDchylog.setJsrid(retHtxx.getWtdwid());
	        	                newDchylog.setJsrxm(retHtxx.getWtdwmc());
	        					// 日志的pid 为ywxx表的id
	        					newDchylog.setPid(updateYwxx.getId());
	        					returenLog = dchyLogService.insert(newDchylog);
	        				}
	        			} else { // 没有业务信息数据则插入数据
	        				DchyYwxx insertYwxx = dchyYwxxService.insert(findDY);
	        				if(!("保存").equals(cghjzt)) { // 保存不添加日志信息
	        					// 有几个结果循环插入多条新的 测绘单位发送给测绘工程师的信息 为测绘工程师确认
//	        					newDchylog.setJsrid(person.getId());
//	        					newDchylog.setJsrxm(dchyYwxx.getChcgfzr());
	        					// 下一阶段变为   测绘单位确认
	        					newDchylog.setJsrid(retHtxx.getWtdwid());
	        	                newDchylog.setJsrxm(retHtxx.getWtdwmc());
	        					// 日志的pid 为ywxx表的id
	        					newDchylog.setPid(insertYwxx.getId());
	        					returenLog = dchyLogService.insert(newDchylog);
	        				}
	        				returnYwxx.add(insertYwxx);
	        			}
					}
        		}
			}
        	//更新本人当前已办为待办
            nowlog.setClzt("已办");
            dchyLogService.update(nowlog);
        } else if ("2".equals(fszt)){   // 状态（2）	对应  成果确认-->成果验收
        	// 测绘工程师是否通过														
            String sfqr = dchyYwxx.getCgshqr();
            //更新本人当前待办为已办
            nowlog.setClzt("已办");
            dchyLogService.update(nowlog);
            if(("已通过").equals(sfqr)) { // 已确认   
            	// 测绘工程师确认时间
            	dchyYwxx.setCgqrsj(nowTimeStamp);
                // 插入一条新的 测绘工程师发送给委托单位的信息 为成果验收
                newDchylog.setJsrid(retHtxx.getWtdwid());
                newDchylog.setJsrxm(retHtxx.getWtdwmc());
                newDchylog.setCzsm(dchyYwxx.getWqryy());
                returenLog = dchyLogService.insert(newDchylog);
            } else if(("未通过").equals(sfqr)) {
            	// 插入一条新的 测绘工程师发送给测绘单位的信息 为成果汇交
            	newDchylog.setJsrid(retHtxx.getChdwid());
            	newDchylog.setJsrxm(retHtxx.getChdwmc());
            	newDchylog.setDqhj("成果汇交");
            	newDchylog.setCzsm(dchyYwxx.getWqryy());
                returenLog = dchyLogService.insert(newDchylog);
            }
            // 更新业务信息
            returnYwxx.add(dchyYwxxService.update(dchyYwxx));
        } else if(("3").equals(fszt)){ // 状态（3） 对应 成果验收--> 成果审核
        	// 委托单位是否通过验收
        	String yszt = dchyYwxx.getCgyszt();
        	//更新本人当前待办为已办
        	nowlog.setClzt("已办");
        	dchyLogService.update(nowlog);
            if(("已通过").equals(yszt)) { // 已通过   
            	// 成果验收时间
            	dchyYwxx.setCgyssj(nowTimeStamp);
                // 插入一条新的 测绘工程师发送给审核人的信息 为成果验收
                newDchylog.setJsrid(dchyYwxx.getCgshrid());
                newDchylog.setJsrxm(dchyYwxx.getCgshr());
                newDchylog.setCzsm(dchyYwxx.getWgsyy());
                returenLog = dchyLogService.insert(newDchylog);
            } else if(("未通过").equals(yszt)) {
            	// 插入一条新的 测绘工程师发送给测绘单位的信息 为成果汇交
            	newDchylog.setJsrid(retHtxx.getChdwid());
            	newDchylog.setJsrxm(retHtxx.getChdwmc());
            	newDchylog.setCzsm(dchyYwxx.getWgsyy());
            	newDchylog.setDqhj("成果汇交");
                returenLog = dchyLogService.insert(newDchylog);
            }
            // 更新业务信息
            returnYwxx.add(dchyYwxxService.update(dchyYwxx));
        }else if(("4").equals(fszt)){ // 状态（4）  成果审核--> 通过审核
        	// 分局经办人是否通过审核
        	String shzt = dchyYwxx.getCgsfsh();
        	//更新本人当前待办为已办
            nowlog.setClzt("已办");
            dchyLogService.update(nowlog);
            if(("已通过").equals(shzt)) { // 已通过

                //            插入成果编号
                DchyYwxx dchyYwxx2 = dchyYwxxService.findById(dchyYwxx.getId());
//                String [] chjd = new String[2];
//                chjd[0] = fbxx.getChjd();
                String cgid = dchyYwxx2.getYl3();
                String code = convertyUtil.createCode(cgid);
                dchyYwxx.setCgbh(code);

            	// 成果验收时间
            	dchyYwxx.setChshsj(nowTimeStamp);
                // 插入一条新的 审核人发送给业主单位的信息 为审核通过
                newDchylog.setJsrid(retHtxx.getWtdwid());
            	newDchylog.setJsrxm(retHtxx.getWtdwmc());
            	newDchylog.setCzsm(dchyYwxx.getCgshyj());
				returenLog = dchyLogService.insert(newDchylog);
            } else if(("未通过").equals(shzt)) {
            	// 插入一条新的 测绘工程师发送给业主单位的信息 为成果验收
            	newDchylog.setJsrid(retHtxx.getWtdwid());
            	newDchylog.setJsrxm(retHtxx.getWtdwmc());
            	newDchylog.setDqhj("业主单位验收");
            	newDchylog.setCzsm(dchyYwxx.getCgshyj());
                returenLog = dchyLogService.insert(newDchylog);
            }
            // 更新业务信息
            returnYwxx.add(dchyYwxxService.update(dchyYwxx));
        }
        // 审核通过之后要进行各种状态判断和保存
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("logDate", returenLog);
        map.put("htxxDate", retHtxx);
        map.put("ywxxDate", returnYwxx);
        return map;
    }
}