package com.github.DchyService.controller.xmwt;

import com.github.DchyService.entity.DchyFbxx;
import com.github.DchyService.entity.DchyHtxx;
import com.github.DchyService.entity.DchyLog;
import com.github.DchyService.entity.DchyYwxx;
import com.github.DchyService.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * (DchyHtxx)表控制层
 *
 * @author makejava
 * @since 2020-10-21 09:03:44
 */
@RestController
@CrossOrigin
@RequestMapping("xmwt/dchyHtxx")
public class DchyHtxxController {
    private Logger log = LoggerFactory.getLogger(DchyHtxxController.class);
    /**
     * 服务对象
     */
    @Resource
    private DchyHtxxService dchyHtxxService;

    @Resource
    private DchyFbxxService dchyFbxxService;

    @Resource
    private DchyYwxxService dchyYwxxService;

    @Resource
    private DchyLogService dchyLogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value="selectHtxxByKey")
    public DchyHtxx selectOne(String id) {
        return this.dchyHtxxService.queryById(id);
    }

    @GetMapping(value="getByPid")
    public DchyHtxx getByPid(String pid) {
        return this.dchyHtxxService.getByPid(pid);
    }

    /**
     * 通过对象查询数据集合
     *
     * @param dchyHtxx
     * @return map
     */
    @PostMapping(value="/getHtxxList")
    @ResponseBody
    public Map<String,Object> queryAll(@RequestBody DchyHtxx dchyHtxx){
        Map<String, Object> map = new HashMap<String, Object>();
        List<DchyHtxx> list = new ArrayList<DchyHtxx>();
        PageHelper.startPage(dchyHtxx.getPageNum(),dchyHtxx.getPageSize());
        list = dchyHtxxService.queryAll(dchyHtxx);

        PageInfo<DchyHtxx> pageInfo = new PageInfo<DchyHtxx>(list);
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 保存数据         暂不使用
     *
     * @param data
     * @return DchyHtxx
     */
    @RequestMapping(value="/saveHtxx")
    @ResponseBody
    @CrossOrigin
    public Map<String, Object> saveDchyHtxx(@RequestBody String data ){
        Map<String, Object> result = new HashMap<>();
        Map<String,Object> res = dchyHtxxService.operHtxx(data);
        if(res!=null){
            result.put("state",200);
            result.put("msg","合同上传成功");
            result.put("data",res);
        }else{
            result.put("state",200);
            result.put("msg","合同上传失败");
            result.put("data",res);
        }
        return result;
    }

    /**
     * 修改合同
     *
     * @param dchyHtxx
     * @return DchyHtxx
     */
    @RequestMapping(value="/updateHtxx")
    @ResponseBody
    public DchyHtxx update(@RequestBody DchyHtxx dchyHtxx){
        return dchyHtxxService.update(dchyHtxx);
    }

    /**
    * @Description //操作合同件
    * @Author maoZhan
    * @Date 2020/11/4 11:37
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @RequestMapping(value = "/operHtxx", method = RequestMethod.POST)
    @CrossOrigin
    public Map<String, Object> operHtxx(@RequestBody String data) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> res = dchyHtxxService.operHtxx(data);
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
     * 合同确认
     *
     * @param data
     * @return DchyHtxx
     */
    @RequestMapping(value="/qrHtxx")
    @ResponseBody
    @CrossOrigin
    public Map<String, Object> qrHtxx(@RequestBody String data){
        Map<String, Object> result = new HashMap<>();
        Map<String,Object> res = dchyHtxxService.qrHtxx(data);
        if(res!=null){
            result.put("state",200);
            result.put("msg","合同上传成功");
            result.put("data",res);
        }else{
            result.put("state",200);
            result.put("msg","合同上传失败");
            result.put("data",res);
        }
        return result;
    }

    /**
     * 通过主键删除单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value="deleteHtxxByKey")
    public boolean deleteById(String id) {
        return dchyHtxxService.deleteById(id);
    }

    /**
     * 通过业主单位ID 获取合同信息
     *
     * @param dchyHtxx
     * @return List
     */
    @PostMapping(value="/getHtQzqList")
    @ResponseBody
    public Map<String,Object> getHtQzq(@RequestBody DchyHtxx dchyHtxx) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DchyHtxx> retList = new ArrayList<DchyHtxx>();
        PageHelper.startPage(dchyHtxx.getPageNum(), dchyHtxx.getPageSize());
        retList = dchyHtxxService.queryAll(dchyHtxx);
        if (retList.size() > 0) {
            for (int i = 0; i < retList.size(); i++) {
                String fbxxid = retList.get(i).getPid();
                // 获取发布信息
                DchyFbxx fbxx = new DchyFbxx();
                fbxx.setId(fbxxid);
                DchyFbxx retfbxx = dchyFbxxService.getFbxx(fbxx);
                // 加入发布信息返回数据
                retList.get(i).setFbxx(retfbxx);
                // 获取业务信息
                DchyYwxx ywxx = new DchyYwxx();
                ywxx.setPid(fbxxid);
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
                    retList.get(i).setYwxxList(ywxxList);
                } else {
                    retList.get(i).setYwxxList(new ArrayList<DchyYwxx>());
                }
            }
        }
        PageInfo<DchyHtxx> pageInfo = new PageInfo<DchyHtxx>(retList);
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
}
