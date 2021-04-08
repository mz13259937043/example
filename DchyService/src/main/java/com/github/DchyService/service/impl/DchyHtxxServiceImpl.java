package com.github.DchyService.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.dao.DchyHtxxDao;
import com.github.DchyService.entity.*;
import com.github.DchyService.service.DchyFbxxService;
import com.github.DchyService.service.DchyHtxxService;
import com.github.DchyService.service.DchyLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchyHtxx)表服务实现类
 *
 * @author makejava
 * @since 2020-10-27 16:44:34
 */
@Service("dchyHtxxService")
public class DchyHtxxServiceImpl implements DchyHtxxService {
    private Logger log = LoggerFactory.getLogger(DchyHtxxServiceImpl.class);
    @Resource
    private DchyHtxxDao dchyHtxxDao;
    @Resource
    private DchyLogService dchyLogService;
    @Resource
    private DchyFbxxService dchyFbxxService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DchyHtxx queryById(String id) {
        return this.dchyHtxxDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param dchyHtxx
     * @return 对象列表
     */
    @Override
    public List<DchyHtxx> queryAll(DchyHtxx dchyHtxx) {
        return this.dchyHtxxDao.queryAll(dchyHtxx);
    }

    /**
     * 修改数据
     *
     * @param dchyHtxx 实例对象
     * @return 实例对象
     */
    @Override
    public DchyHtxx update(DchyHtxx dchyHtxx) {
        this.dchyHtxxDao.update(dchyHtxx);
        return this.queryById(dchyHtxx.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.dchyHtxxDao.deleteById(id) > 0;
    }

    /**
    * @Description //合同确认,同时修改
    * @Author maoZhan
    * @Date 2020/10/30 17:17
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @Override
    @Transactional
    public Map<String, Object> qrHtxx(String data) {
        Map<String,Object> res = new HashMap<>(16);
        JSONObject htxxAndLog = JSON.parseObject(data);
        DchyHtxx htxx = htxxAndLog.getJSONObject("htxx").toJavaObject(DchyHtxx.class);
        DchyLog log = htxxAndLog.getJSONObject("log").toJavaObject(DchyLog.class);
        String lastLogId = htxxAndLog.getString("lastLogId");

        if(!(htxx!=null&&log!=null)){
            res.put("msg","参数为空！！");
            return res;
        }

        DchyFbxx fbxx = new DchyFbxx();
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        DchyLog lastLog  = new DchyLog();

//        合同确认，
        try {
//            更新合同表信息
            htxx.setQrsj(new Timestamp(System.currentTimeMillis()));
            this.dchyHtxxDao.update(htxx);
//            添加日志
            log.setJssj(curr);
            log.setFssj(curr);
            log.setCjsj(curr);
            this.dchyLogService.insert(log);
//            修改上条为已办
            lastLog.setId(lastLogId);
            lastLog.setClzt("已办");
            this.dchyLogService.update(lastLog);
//            更新主表状态
            fbxx.setId(log.getPid());
            fbxx.setXmzt(ProjectState.BLZ.getValue());
            this.dchyFbxxService.renewFbxx(fbxx);
            res.put("log",log);
            res.put("htxx",htxx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
    * @Description //合同退回
    * @Author maoZhan
    * @Date 2020/11/22 10:49
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @Override
    @Transactional
    public Map<String, Object> revokeHtxx(String data) {
        Map<String,Object> res = new HashMap<>(16);
        JSONObject htxxAndLog = JSON.parseObject(data);
        DchyHtxx htxx = htxxAndLog.getJSONObject("htxx").toJavaObject(DchyHtxx.class);
        DchyLog log = htxxAndLog.getJSONObject("log").toJavaObject(DchyLog.class);
        String lastLogId = htxxAndLog.getString("lastLogId");

        if(!(htxx!=null&&log!=null)){
            res.put("msg","参数为空！！");
            return res;
        }

        DchyFbxx fbxx = new DchyFbxx();
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        DchyLog lastLog  = new DchyLog();

//        合同退回，
        try {
//            更新合同表信息
            htxx.setQrsj(new Timestamp(System.currentTimeMillis()));
            this.dchyHtxxDao.update(htxx);
//            添加日志
            log.setJssj(curr);
            log.setFssj(curr);
            log.setCjsj(curr);
            this.dchyLogService.insert(log);
//            修改上条为已办
            lastLog.setId(lastLogId);
            lastLog.setClzt("已办");
            this.dchyLogService.update(lastLog);
//            更新主表状态
            fbxx.setId(log.getPid());
            fbxx.setXmzt(ProjectState.HTQ.getValue());
            this.dchyFbxxService.renewFbxx(fbxx);
            res.put("log",log);
            res.put("htxx",htxx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public Map<String, Object> operHtxx(String data) {
        Map<String, Object> res = new HashMap<>(16);
        JSONObject fbxxAndLog = JSON.parseObject(data);
        DchyHtxx htxx = fbxxAndLog.getJSONObject("htxx").toJavaObject(DchyHtxx.class);
        DchyLog log = fbxxAndLog.getJSONObject("log").toJavaObject(DchyLog.class);;
        String handle = fbxxAndLog.getString("handle");
        try {
            switch (handle){
                case "保存":
                    htxx = addHtxx(htxx);
                    res.put("htxx",htxx);
                    break;
                case "上传":
                    res = uploadHtxx(data);
                    break;
                case "立即上传":
                    htxx = addHtxx(htxx);
                    res = uploadHtxx(data);
                    break;
                case "编辑":
                    update(htxx);
                    res.put("htxx",htxx);
                    break;
                case "合同确认":
                    res = qrHtxx(data);
                    break;
                case "合同退回":
                    res = revokeHtxx(data);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + handle);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return res;
    }

    @Override
    public DchyHtxx getByPid(String pid) {
        return this.dchyHtxxDao.getByPid(pid);
    }

    //    合同上传操作
    public Map<String, Object> uploadHtxx(String data) {
        Map<String,Object> res = new HashMap<>(16);
//        合同上传操作日志，修改主表状态
        JSONObject htxxAndLog = JSON.parseObject(data);
        DchyHtxx htxx = htxxAndLog.getJSONObject("htxx").toJavaObject(DchyHtxx.class);
        DchyLog log = htxxAndLog.getJSONObject("log").toJavaObject(DchyLog.class);

        if(!(htxx!=null&&log!=null)){
            res.put("msg","参数为空！！");
            return res;
        }

        DchyLog lastLog = new DchyLog();            //上条日志
        String lastLogId = htxxAndLog.getString("lastLogId");
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        try {
//            上传前自动修改合同信息
            String htxxid = htxx.getId();
            if(!("").equals(htxxid) && htxxid != null){
                this.dchyHtxxDao.update(htxx);
            } else {
                this.dchyHtxxDao.insert(htxx);
            }
//            添加日志记录

            log.setDqhj("合同确认");
            log.setJssj(curr);
            log.setFssj(curr);
            log.setCjsj(curr);
            this.dchyLogService.insert(log);
            //            修改上条为已办
            lastLog.setId(lastLogId);
            lastLog.setClzt("已办");
            this.dchyLogService.update(lastLog);
//            修改主表状态
            res.put("log",log);
            res.put("htxx",htxx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return res;
    }
    @Transactional
    public DchyHtxx addHtxx(DchyHtxx htxx) {
        if(htxx==null){
            return htxx;
        }

        try{
//            保存合同信息
            this.dchyHtxxDao.insert(htxx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return htxx;
    }
}