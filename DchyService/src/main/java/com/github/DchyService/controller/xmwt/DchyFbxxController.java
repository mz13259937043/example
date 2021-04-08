package com.github.DchyService.controller.xmwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.controller.DchyYwxxController;
import com.github.DchyService.dao.DchyProcessDao;
import com.github.DchyService.entity.*;
import com.github.DchyService.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import oracle.jdbc.proxy.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchyFbxx)表控制层
 *
 * @author makejava
 * @since 2020-10-20 18:37:25
 */
@RestController // 控制器接口
@RequestMapping("/xmwt/dchyFbxx")
@CrossOrigin
public class DchyFbxxController {
    private Logger log = LoggerFactory.getLogger(DchyFbxxController.class);
    /**
     * 服务对象
     */
    @Resource
    private DchyFbxxService dchyFbxxService;

    @Resource
    private DchyHtxxService dchyHtxxService;

    @Resource
    private DchySqyxService dchySqyxService;

    @Resource
    private DchyYwxxService dchyYwxxService;

    @Resource
    private DchyLogService dchyLogService;
    @Resource
    private DchyProcessDao dchyProcessDao;

    /**
    * @Description //操作件的，里面包含对采购公告的保存修改，发布、立即发布
    * @Author maoZhan
    * @Date 2020/10/21 9:02
    * @param  * @param fbxx
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @RequestMapping(value = "/operFbxx", method = RequestMethod.POST)
    @CrossOrigin
    public Map<String, Object> operFbxx(@RequestBody String data) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> res = dchyFbxxService.operFbxx(data);
        if(res!=null){
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
    * @Description //查询单个发布信息
    * @Author maoZhan
    * @Date 2020/10/21 9:04
    * @param  * @param fbxx
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @RequestMapping(value = "/getFbxx", method = RequestMethod.POST)
    @CrossOrigin
    public Map<String, Object> getFbxx(@RequestBody DchyFbxx fbxx) {
        Map<String, Object> result = new HashMap<>();
        fbxx = dchyFbxxService.getFbxx(fbxx);
        if(fbxx!=null){
            result.put("state",200);
            result.put("msg","修改成功");
            result.put("data",fbxx);
        }else{
            result.put("state",200);
            result.put("msg","修改失败");
            result.put("data",fbxx);
        }
        return result;
    }

    /**
    * @Description //查询发布信息列表
    * @Author maoZhan
    * @Date 2020/10/21 9:19
    * @param  * @param params
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @RequestMapping(value = "/getFbxxList", method = RequestMethod.POST)
    @CrossOrigin
    public Map<String, Object> getFbxxList(@RequestBody DchyFbxx fbxx) {
        Map<String, Object> result = new HashMap<>();

        PageHelper.startPage(fbxx.getPageNum(),fbxx.getPageSize());
        List<DchyFbxx> res = dchyFbxxService.getFbxxList(fbxx);
        PageInfo<DchyFbxx> pageInfo = new PageInfo<DchyFbxx>(res);
        result.put("state",200);
        result.put("msg","ok");
        result.put("data",pageInfo.getList());
        result.put("count",pageInfo.getTotal());
        return result;
    }
    /**
    * @Description //调整信息状态
    * @Author maoZhan
    * @Date 2020/10/21 10:28
    * @param  * @param fbxx
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @RequestMapping(value = "/renewFbxx", method = RequestMethod.POST)
    @CrossOrigin
    public Map<String, Object> renewFbxx(@RequestBody DchyFbxx fbxx) {
        Map<String, Object> result = new HashMap<>();
        int success = dchyFbxxService.renewFbxx(fbxx);
        if(success>0){
            result.put("state",200);
            result.put("msg","修改成功");
            result.put("data",success);
        }else{
            result.put("state",200);
            result.put("msg","修改失败");
            result.put("data",success);
        }
        return result;
    }
    /**
    * @Description //获取公告的发布信息列表
    * @Author maoZhan
    * @Date 2020/10/23 10:09
    * @param  * @param fbxx
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @PostMapping("/getFbxxGg")
    @CrossOrigin
    public Map<String, Object> getFbxxGg(@RequestBody Map<String,Object> arg) {
        Map<String, Object> result = new HashMap<>();
        int pageIndex = (int)arg.get("pageIndex");
        int pageSize = (int)arg.get("pageSize");
        String str = (String) arg.get("str");
//        使用默认分页参数
        PageHelper.startPage(pageIndex,pageSize);
        List<Map<String,Object>> res = dchyFbxxService.getFbxxGg(str);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(res);
        if(res.size()>0){
            result.put("state",200);
            result.put("msg","修改成功");
            result.put("data",pageInfo.getList());
            result.put("count",pageInfo.getTotal());
        }else{
            result.put("state",200);
            result.put("msg","修改失败");
            result.put("data",res);
            result.put("count",0);
        }
        return result;
    }
    @GetMapping(value="deleteFbxxByKey/{id}")
    public boolean deleteFbxxByKey(@PathVariable String id) {
        return dchyFbxxService.deleteFbxxByKey(id);
    }
    /**
    * @Description //根据fbxxid 查询详情
    * @Author
    * @Date
    * @param  * @param fbxxId ywxxId
    * @return Map<String, Object>
    **/
    @GetMapping("findAllInfoById")
    @CrossOrigin
    public Map<String, Object> getFbxxAllById(String fbxxId,String ywxxId) {
        // 用业务信息ID来区分是查业务的详情页还是发布信息的详情页
        DchyFbxx fbxx = new DchyFbxx();
        fbxx.setId(fbxxId);
        // 获取发布信息
        DchyFbxx returnVal = dchyFbxxService.getFbxx(fbxx);
        if(("").equals(returnVal) && returnVal != null){

        }
        // 获取合同信息
        DchyHtxx htxx = new DchyHtxx();
        htxx.setPid(fbxxId);
        List<DchyHtxx> returnHtxx = dchyHtxxService.queryAll(htxx);
        if(returnHtxx.size() > 0){
            returnVal.setHtxxList(returnHtxx);
        }
        // 获取申请意向
        DchySqyx sqyx = new DchySqyx();
        sqyx.setPid(fbxxId);
        List<DchySqyx> returnSqyx = dchySqyxService.queryAll(sqyx);
        if(returnSqyx.size() > 0){
            returnVal.setSqyxList(returnSqyx);
        }
        // 为空时为项目发布请求详细数据   日志表的PID  为发布信息的ID
        if(("").equals(ywxxId) || (ywxxId == null)){
            // 获取日志信息
            DchyLog dchyLog = new DchyLog();
            dchyLog.setPid(fbxxId);
            List<DchyLog> returnLog = dchyLogService.findList(dchyLog);
            if(returnLog.size() > 0){
                returnVal.setLogsList(returnLog);
            }
        } else{
            // 获取业务信息
            DchyYwxx dchyYwxx = new DchyYwxx();
            dchyYwxx.setPid(fbxxId);
            List<DchyYwxx> returnYwxx = dchyYwxxService.findList(dchyYwxx);
            returnVal.setYwxxList(returnYwxx);
            // 获取日志信息   表pid为wuxxid
            DchyLog dchyLog = new DchyLog();
            dchyLog.setPid(ywxxId);
            List<DchyLog> returnLog = dchyLogService.findList(dchyLog);
            if(returnLog.size() > 0){
                returnVal.setLogsList(returnLog);
            }
        }
        // 数据返回
        Map<String, Object> result = new HashMap<>();
        result.put("msg","获取成功");
        result.put("state","200");
        result.put("data",returnVal);
        return result;
    }


    /**
     * @param fbxx
     * 查询所有在办理中的项目   可模糊查询
     *  @return Map<String, Object>
     */
    @PostMapping("findMyProByFbxx")
    @CrossOrigin
    public Map<String, Object> findMyProByFbxx(@RequestBody DchyFbxx fbxx) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.startPage(fbxx.getPageNum(),fbxx.getPageSize());
        // 查询所有在办理中的项目   可模糊查询
        fbxx.setXmzt("办理中");
        List<Map<String, Object>> retVal = dchyFbxxService.findMyProByFbxx(fbxx);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(retVal);
        result.put("code", 0);
        result.put("msg", "ok");
        result.put("count", pageInfo.getTotal());
        result.put("data", pageInfo.getList());
        return result;
    }

    /**
     * @param
     *
     *  @return Map<String, Object>
     */
    @PostMapping("getGroundLifecycle")
    @CrossOrigin
    public Map<String, Object> getAllProStage() {
        Map<String, Object> result = new HashMap<String, Object>();

        // 获取土地全阶段的业务信息
        List<DchyFbxx> fbxxList = dchyFbxxService.getFbxxLifecycle();
        for (int i = 0; i < fbxxList.size(); i++) {
            // 根据fbxxid查询合同信息
            DchyHtxx htxx = dchyHtxxService.getByPid(fbxxList.get(0).getId());
            List<DchyHtxx> htxxList = new ArrayList<DchyHtxx>();
            htxxList.add(htxx);
            // 加入合同信息返回数据
            fbxxList.get(i).setHtxxList(htxxList);
            // 根据fbxxid查询业务信息
            DchyYwxx ywxx = new DchyYwxx();
            ywxx.setPid(fbxxList.get(0).getId());
            List<DchyYwxx> ywxxList = dchyYwxxService.findList(ywxx);
            if (ywxxList.size() > 0) {
                for (int j = 0; j < ywxxList.size(); j++) {
                    // 获取每个业务信息的日志信息
                    DchyLog log = new DchyLog();
                    log.setPid(ywxxList.get(j).getId());
                    List<DchyLog> logList = dchyLogService.findList(log);
                    if (logList.size() > 0) { // 为每个业务信息添加日志信息
                        ywxxList.get(j).setLogsList(logList);
                    } else {
                        ywxxList.get(j).setLogsList(new ArrayList<DchyLog>());
                    }
                }
                // 加入业务信息返回数据
                fbxxList.get(i).setYwxxList(ywxxList);
            } else {
                fbxxList.get(i).setYwxxList(new ArrayList<DchyYwxx>());
            }
        }
//        for (int k1 = 0; k1 < fbxxList.size(); k1++) {  // 组装数据部分 暂时搁置
//            for (int k2 = 0; k2 < fbxxList.size(); k2++) {
//
//                  fbxxList.get(k1).getYwxxList()
//            }
//        }
        result.put("code", 0);
        result.put("msg", "ok");
        result.put("data", fbxxList);
        return result;
    }
  
    @GetMapping(value="findfwCount/{did}")
    @CrossOrigin
    public Map<String, Integer> findfwCount(@PathVariable String did) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        Integer dqfwxms = dchyFbxxService.findfwCount(did,"办理中");
        Integer bjl = dchyFbxxService.findfwCount(did,"已办结");
        result.put("dqfwxms", dqfwxms);
        result.put("bjl", bjl);
        return result;
    }
    @PostMapping(value="getFbxxGgByDid")
    @CrossOrigin
    public Map<String, Object> getFbxxGgByDid(@RequestBody Map<String,Object> arg) {
        Map<String, Object> result = new HashMap<>();
        int pageIndex = (int)arg.get("pageIndex");
        int pageSize = (int)arg.get("pageSize");
        String str = (String) arg.get("str");
        String did =(String) arg.get("did");
        String wtqr =(String) arg.get("wtqr");
//        使用默认分页参数
        PageHelper.startPage(pageIndex,pageSize);
        List<Map<String,Object>> res = dchyFbxxService.getFbxxGgByDid(str,did,wtqr);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(res);
        if(res.size()>0){
            result.put("state",200);
            result.put("msg","修改成功");
            result.put("data",pageInfo.getList());
            result.put("count",pageInfo.getTotal());
        }else{
            result.put("state",200);
            result.put("msg","修改失败");
            result.put("data",res);
            result.put("count",0);
        }
        return result;
    }

    @PostMapping(value="getAllProcess")
    @CrossOrigin
    public Map<String, Object> getAllProcess(@RequestBody Map<String,Object> arg) {
        Map<String, Object> result = new HashMap<>();
        Integer pageIndex = null;
        Integer pageSize = null;
        String str = "";
        if (arg.size()>0){
            pageIndex = (int)arg.get("pageIndex");
            pageSize = (int)arg.get("pageSize");
            str = (String) arg.get("str");
            PageHelper.startPage(pageIndex,pageSize);
        }
        List<DchyProcess> res = dchyProcessDao.getList(str);
        if (arg.size()>0){
            PageInfo<DchyProcess> pageInfo = new PageInfo<>(res);
            result.put("data",pageInfo.getList());
            result.put("count",pageInfo.getTotal());
        }else{
            result.put("data",res);
            result.put("count",res.size());
        }
        if(res.size()>0){
            result.put("state",200);
            result.put("msg","查询成功");
        }else{
            result.put("state",500);
            result.put("msg","查询失败");
            result.put("data",res);
            result.put("count",0);
        }
        return result;
    }

}