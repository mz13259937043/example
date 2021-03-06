package com.github.DchyService.controller;

import com.github.DchyService.dao.DchyFbxxDao;
import com.github.DchyService.dao.DchyLogDao;
import com.github.DchyService.dao.DchySqyxDao;
import com.github.DchyService.entity.*;
import com.github.DchyService.service.DchyFbxxService;
import com.github.DchyService.service.DchyLogService;
import com.github.DchyService.service.DchyYwxxService;
import com.github.DchyService.service.DchyZzxxService;
import com.github.DchyService.tools.ConvertyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (DchyLog)表控制层
 *
 * @author makejava
 * @since 2020-10-26 15:23:02
 */
@RestController
@CrossOrigin
@RequestMapping("dchy/dchyLog")
public class DchyLogController {
    private Logger log = LoggerFactory.getLogger(DchyLogController.class);
    /**
     * 服务对象
     */
    @Resource
    private DchyLogService dchyLogService;
    @Resource
    private DchyLogDao dchyLogDao;

    @Resource
    private DchyYwxxService dchyYwxxService;

    @Resource
    private DchyZzxxService dchyZzxxService;

    @Resource
    private DchyFbxxService dchyFbxxService;

    @Resource
    private DchyFbxxDao dchyFbxxDao;

    @Resource
    private DchySqyxDao dchySqyxDao;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("findLogById")
    public DchyLog findById(String id) {
        return this.dchyLogService.findById(id);
    }

    /**
     * 通过对象查询数据集合
     *
     * @param dchyLog
     * @return map
     */
    @PostMapping(value="/getLogList")
    @ResponseBody
    @CrossOrigin
    public Map<String,Object> findList(@RequestBody DchyLog dchyLog){
        Map<String, Object> map = new HashMap<String, Object>();
        List<DchyLog> list = new ArrayList<DchyLog>();
        PageHelper.startPage(dchyLog.getPageNum(),dchyLog.getPageSize());
        list = dchyLogService.findList(dchyLog);

        PageInfo<DchyLog> pageInfo = new PageInfo<DchyLog>(list);
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 保存数据
     *
     * @param dchyLogs
     * @return DchyHtxx
     */
    @RequestMapping(value="/saveLog")
    @ResponseBody
    @CrossOrigin
    public Map<String,Object> saveLog(@RequestBody List<DchyLog>  dchyLogs){
        List<DchyLog>  list = dchyLogs;
        Map<String, Object> map = new HashMap<String, Object>();
        List<DchyLog> returnVal = new ArrayList();
        int i = 0;
        for ( i = 0;i<list.size();i++){
            DchyLog dlog = dchyLogService.insert(list.get(i));
            returnVal.add(dlog);
        }
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("data", returnVal);
        return map;
    }

    /**
     * 更新数据
     *
     * @param dchyLog
     * @return DchyHtxx
     */
    @RequestMapping(value="/updateLog")
    @ResponseBody
    @CrossOrigin
    public DchyLog update(@RequestBody DchyLog dchyLog){
        return dchyLogService.update(dchyLog);
    }

    /**
     * 通过主键删除单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value="deleteLogByKey")
    @CrossOrigin
    public boolean deleteById(String id) {
        return dchyLogService.deleteById(id);
    }

    /**
     * 通过人员id和状态查询待办已办信息
     *
     * @param dlog
     * @return 单条数据
     */
    @RequestMapping(value="/getDealingList")
    @ResponseBody
    @CrossOrigin
    public Map<String,Object> getToDoList(@RequestBody DchyLog dlog){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> ywxxList = dchyLogService.findYwxxDealingList(dlog);
        List<Map<String,Object>> newList = new ArrayList<>();
        if(ywxxList.size() > 0 && dlog.getClzt().equals("已办")){ // 已办列表才查询最新一条日志
            String pid1 = "";
            String logclzt1 = "";
            String jsrid1 = "";
            Date cjsj1 = null;
<<<<<<< .mine
            /*
            我想干啥？
            我的项目啊，我发过的和我收到的都在这里，大部分都是公共字段，
            当前环节，处理时间，待办已办啊，都需要特殊处理
            获取当前人的最新操作就可以，是已办就已办，是待办就待办
             */
||||||| .r35
=======
            /*
            * 就是我动过的件都要，但是需要显示我哪个件在什么环节和状态
            * 那就用fsr和jsr一起查，将本人最新的操作放入
            * 刚开始我发出去，已办+
            *  */
>>>>>>> .r69
            for (int i = 0; i < ywxxList.size() ; i++) { // 获取最新一条日志
                String ywxxid = (String)ywxxList.get(i).get("YWXXID");
                String pid2 = (String) ywxxList.get(i).get("FBXXID");
                String logdqhj = (String) ywxxList.get(i).get("LOGDQHJ");
                String jsrid2 = (String) ywxxList.get(i).get("LOGJSRID");
                Date cjsj2 = (Date) ywxxList.get(i).get("LOGCJSJ");
                String chdwmc = (String) ywxxList.get(i).get("HTXXCHDWMC");
                String chdwid = (String) ywxxList.get(i).get("HTXXCHDWID");
                String logclzt2 = (String) ywxxList.get(i).get("LOGCLZT");

                if (pid1.equals(pid2)){
//                    如果有更早的，将当前环节更新
<<<<<<< .mine
||||||| .r35
                    if (cjsj2.before(cjsj1)){
                        for(int j = 0; j < newList.size() ; j++){
                            if(newList.get(j).get("FBXXID").equals(pid1)){
                                newList.get(j).put("LOGDQHJ",logdqhj);
                                newList.get(j).put("JSSJ",cjsj2);
                                newList.get(j).put("LOGCLZT",logclzt2);
                            }
                        }
                    }
=======
                    if (cjsj2.before(cjsj1)){
                        for(int j = 0; j < newList.size() ; j++){
                            if(newList.get(j).get("FBXXID").equals(pid1)){
                                newList.get(j).put("LOGDQHJ",logdqhj);
                                newList.get(j).put("JSSJ",cjsj2);
                            }
                        }
                    }
>>>>>>> .r69
                    continue;
                }
                cjsj1 = cjsj2;
                jsrid1 = jsrid2;
                pid1 = pid2;
                logclzt1 = logclzt2;
                Map<String,Object> a =  dchyFbxxDao.getCustomizeFbxx(pid1);
                DchyLog ywxxLastLog =  dchyLogService.getJsrLastLog(ywxxid);
                // 加入到返回值中
//                map1.put("ywxxLastLog",ywxxLastLog);
                a.put("HTXXCHDWMC",chdwmc);
                a.put("HTXXCHDWID",chdwid);
                a.put("LOGDQHJ",ywxxLastLog.getDqhj());
                a.put("JSSJ",ywxxLastLog.getJssj());
                a.put("STAGE","成果");
<<<<<<< .mine
                a.put("LOGCLZT",ywxxLastLog.getClzt());
||||||| .r35
                a.put("LOGCLZT",logclzt1);
=======
//                用户操作过的最新log
                DchyLog userLastestLog =  dchyLogDao.getLogByPid(ywxxid,dlog.getJsrid());
//                当前项目最新的log
                DchyLog ywxxLastLog =  dchyLogDao.getLastLog(ywxxid);
                if(dlog.getJsrid().equals(userLastestLog.getFsrid())){
                    a.put("LOGCLZT","已办");
                }else{
                    a.put("LOGCLZT","待办");
                }
                // 加入到返回值中
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("ywxxLastLog",ywxxLastLog);
                map1.put("userLastestLog",userLastestLog);
                a.putAll(map1);

>>>>>>> .r69
                newList.add(a);
            }
            /*for (int i = 0; i < ywxxList.size() ; i++) { // 获取最新一条日志
                String ywxxid = (String)ywxxList.get(i).get("YWXXID");
                DchyLog ywxxLastLog =  dchyLogService.getLastLog(ywxxid);
                // 加入到返回值中
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("ywxxLastLog",ywxxLastLog);
                ywxxList.get(i).putAll(map1);
            }*/
        }
        List<Map<String,Object>> fbxxList = dchyLogService.findFbxxDealingList(dlog);
        if(fbxxList.size() > 0 && dlog.getClzt().equals("已办")) { // 已办列表才查询最新一条日志
//            筛选掉已经进入成果阶段的项目
            for (int i = 0; i < fbxxList.size(); i++) { // 获取最新一条日志
                String fbxxid = (String) fbxxList.get(i).get("FBXXID");
                boolean go = true;
                if (newList.size()>0) {
                    for (int j = 0; j < newList.size(); j++) {
                        if (newList.get(j).get("FBXXID").equals(fbxxList.get(i).get("FBXXID"))) {
                            fbxxList.remove(i);
                            i--;
                            go = false;
                            break;
                        }
                    }
                }
                if (!go)continue;
                DchyLog fbxxLastLog = dchyLogService.getLastLog(fbxxid);
                // 加入到返回值中
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("fbxxLastLog", fbxxLastLog);
                if ("合同已签订".equals(fbxxList.get(i).get("LOGDQHJ"))){
                    map2.put("STAGE", "成果");
                }else if("合同已签订".equals(fbxxLastLog.getDqhj())){
                    map2.put("STAGE", "成果");
                }
                fbxxList.get(i).putAll(map2);
            }
        }
        List<Map<String,Object>> zzxxList = dchyLogService.findZzxxDealingList(dlog);
        boolean compare = true;
        if(newList.size() > 0){
            returnList.addAll(newList);
        }
        if(fbxxList.size() > 0){
            returnList.addAll(fbxxList);
        }
        if(zzxxList.size() > 0){
            returnList.addAll(zzxxList);
        }
//        将废标的筛除/标记
        List<Map<String, Object>> zbggList = dchySqyxDao.getZbGg(null);
        for (int i=0;i<returnList.size();i++){
            String pid1 = (String) returnList.get(i).get("FBXXID");
            for (int j=0;j<zbggList.size();j++) {
                String pid2 = (String) zbggList.get(j).get("ID");
                String fb = (String) zbggList.get(j).get("FB");
                if (StringUtil.isNotEmpty(fb)&&fb.indexOf("废标")!=-1){
                    if (pid1.equals(pid2)){
                        returnList.get(i).put("LOGCLZT",fb);
//                        returnList.remove(i);
//                        zbggList.remove(j);
//                        i--;
//                        j--;
                    }
                }
            }
        }
        if (compare) {
            Collections.sort(returnList, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    String name1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o1.get("JSSJ"));//name1是从你list里面拿出来的一个
                    String name2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o2.get("JSSJ")); //name1是从你list里面拿出来的第二个name
                    return name2.compareTo(name1);
                }
            });
        }
        int count = 0;
        if(returnList != null && returnList.size() > 0) { // 分页
            count = returnList.size();
            int fromIndex = (dlog.getPageNum() - 1) * dlog.getPageSize();
            int toIndex = dlog.getPageNum() * dlog.getPageSize();
            if (toIndex > count) {
                toIndex = count;
            }
            resultList = returnList.subList(fromIndex, toIndex);
        }
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("count", count);
        map.put("data", resultList);
        return map;
    }
    /**
     * @Description //项目撤销
     * @Author maoZhan
     * @Date 2020/11/10 16:48
     * @param  * @param djbh
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @PostMapping("revokeProject")
    @CrossOrigin
    public Map<String, Object> revokeProject(@RequestBody String data) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        Map<String, Object> res = dchyLogService.revokeProject(data);
        if(res.size()>0){
            result.put("state",200);
            result.put("msg","修改成功");
            result.put("data",res);
        }else{
            result.put("state",200);
            result.put("msg","修改失败");
            result.put("data",res);
        }
        return result;
    }
    /**
    * @Description //获取当前项目上调日志
    * @Author maoZhan
    * @Date 2020/11/11 17:40
    * @param  * @param pid
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @GetMapping("getLastLog")
    @CrossOrigin
    public Map<String, Object> getLastLog(@RequestParam String pid) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        DchyLog log = dchyLogService.getLastLog(pid);
        if(StringUtil.isNotEmpty(log.getId())){
            result.put("state",200);
            result.put("msg","查询成功");
            result.put("data",log);
        }else{
            result.put("state",200);
            result.put("msg","查询失败");
            result.put("data",log);
        }
        return result;
    }
}
