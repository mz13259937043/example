package com.github.DchyService.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.dao.DchyFbxxDao;
import com.github.DchyService.dao.DchySqyxDao;
import com.github.DchyService.entity.*;
import com.github.DchyService.service.DchyFbxxService;
import com.github.DchyService.service.DchyLogService;
import com.github.DchyService.tools.ConvertyUtil;
import com.github.pagehelper.StringUtil;
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
 * (DchyFbxx)表服务实现类
 *
 * @author makejava
 * @since 2020-10-20 18:37:24
 */
@Service("dchyFbxxService")
public class DchyFbxxServiceImpl implements DchyFbxxService {
    private Logger log = LoggerFactory.getLogger(DchyFbxxServiceImpl.class);
    @Resource
    private DchyFbxxDao dchyFbxxDao;
    @Resource
    private DchyLogService dchyLogService;
    @Resource
    private OperLog operLog;
    @Resource
    private DchySqyxDao dchySqyxDao;
    @Resource
    private ConvertyUtil convertyUtil;


    /**
    * @Description //保存
    * @Author maoZhan
    * @Date 2020/11/3 18:46
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @Override
    public Map<String,Object> addFbxx(String data) {
        Map<String,Object> res = new HashMap<>(16);
//      保存操作
        JSONObject fbxxAndLog = JSON.parseObject(data);
        DchyFbxx fbxx = fbxxAndLog.getJSONObject("fbxx").toJavaObject(DchyFbxx.class);
        DchyLog log = fbxxAndLog.getJSONObject("log").toJavaObject(DchyLog.class);
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        try {
//            插入主表
            fbxx.setXmzt("草稿");
            int x = this.dchyFbxxDao.addFbxx(fbxx);
//            插入日志
            if (x==1){
                log.setDqhj("项目发布");
                log.setPid(fbxx.getId());
                log.setJssj(curr);
                log.setCjsj(curr);
                log.setFssj(curr);
                this.dchyLogService.insert(log);
            }
            res.put("log",log);
            res.put("fbxx",fbxx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res;
    }
    /**
    * @Description //发布
    * @Author maoZhan
    * @Date 2020/11/3 18:47
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @Override
    public Map<String,Object> releaseFbxx(String data) {
        Map<String,Object> res = new HashMap<>(16);
//      添加发布，自己发给自己
        JSONObject fbxxAndLog = JSON.parseObject(data);
        DchyFbxx fbxx = fbxxAndLog.getJSONObject("fbxx").toJavaObject(DchyFbxx.class);
        DchyLog log = fbxxAndLog.getJSONObject("log").toJavaObject(DchyLog.class);
        DchyLog lastLog =new DchyLog();
        String lastLogId = fbxxAndLog.getString("lastLogId");
        Timestamp curr = new Timestamp(System.currentTimeMillis()+5000);
        try {
//            插入日志
            log.setDqhj("采购确认");
            log.setPid(fbxx.getId());
            log.setJssj(curr);
            log.setCjsj(curr);
            log.setFssj(curr);
            this.dchyLogService.insert(log);
//            修改上调日志
            if (StringUtil.isNotEmpty(lastLogId)){
                lastLog.setId(lastLogId);
                lastLog.setClzt("已办");
                this.dchyLogService.update(lastLog);
            }
//            处理主表状态
            fbxx.setXmzt(ProjectState.GSZ.getValue());          //公示中
//            在此更新数据得编号
            if (StringUtil.isNotEmpty(fbxx.getChjd())){
//            有了判断有无dchybh
                if (StringUtil.isEmpty(fbxx.getDchybh())){
//                    String[] chjd= new String[1];
//                    chjd[0] = fbxx.getChjd();
                    String chjdId = fbxx.getChjd();
                    String code = convertyUtil.createCode(chjdId);
//              序列
                    fbxx.setDchybh(code);
                }
            }
            int x = this.dchyFbxxDao.renewFbxx(fbxx);
            fbxx = this.dchyFbxxDao.getFbxx(fbxx);

            res.put("log",log);
            res.put("fbxx",fbxx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public List<DchyFbxx> getFbxxLifecycle() {
        return dchyFbxxDao.getFbxxLifecycle();
    }

    /**
    * @Description //添加发布信息
    * @Author maoZhan
    * @Date 2020/10/31 14:07
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    public Map<String,Object> addFbxx1(String data) {
        Map<String,Object> res = new HashMap<>(16);
//      添加发布，自己发给自己
        JSONObject fbxxAndLog = JSON.parseObject(data);
        DchyFbxx fbxx = fbxxAndLog.getJSONObject("fbxx").toJavaObject(DchyFbxx.class);
        List<DchyLog> logList = fbxxAndLog.getJSONArray("logList").toJavaList(DchyLog.class);
        String type = fbxxAndLog.getString("type");
        String lastLogId = fbxxAndLog.getString("lastLogId");

        if(!(fbxx!=null&&log!=null)){
            res.put("msg","参数为空！！");
            return res;
        }
        try {
            switch (type){
                case "保存":
//                插入fbxx
                    this.dchyFbxxDao.addFbxx(fbxx);
//                操作日志
                    logList = this.operLog.toOperLog(logList,fbxx.getId());       //一条
                    break;
                case "编辑":
                    this.dchyFbxxDao.renewFbxx(fbxx);
                    break;
                case "发布":
//                发送前更新数据
                    this.dchyFbxxDao.renewFbxx(fbxx);
//                操作日志
                    logList = this.operLog.toOperLog(logList,fbxx.getId());
//                    修改为已办
                    DchyLog log = logList.get(0);
                    log.setClzt("已办");
                    this.dchyLogService.update(log);
                    break;
                case "立即发布":
//                插入fbxx
                    this.dchyFbxxDao.addFbxx(fbxx);
//                操作日志
                    logList = this.operLog.toOperLog(logList,fbxx.getId());       //两条
                    break;
            }
            res.put("logList",logList);
            res.put("fbxx",fbxx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public DchyFbxx getFbxx(DchyFbxx fbxx) {
        return dchyFbxxDao.getFbxx(fbxx);
    }

    @Override
    public List<DchyFbxx> getFbxxList(DchyFbxx fbxx) {
        return dchyFbxxDao.getFbxxList(fbxx);
    }

    @Override
    public List<Map<String, Object>> findMyProByFbxx(DchyFbxx fbxx) {
        return dchyFbxxDao.findMyProByFbxx(fbxx);
    }

    @Override
    public int renewFbxx(DchyFbxx fbxx) {
        return dchyFbxxDao.renewFbxx(fbxx);
    }

    @Override
    public List<Map<String, Object>> getFbxxGg(String str) {
        List<Map<String, Object>> fbxxGg =  dchyFbxxDao.getFbxxGg(str);
        for(Map<String,Object> fbxx:fbxxGg){
            List<Map<String, Object>> sqyxList = this.dchySqyxDao.getSqyxListByFbxxid("",(String) fbxx.get("ID"));
            fbxx.put("sqyxList",sqyxList);
        }
        return fbxxGg;
    }

    @Override
    public boolean deleteFbxxByKey(String id) {
        return dchyFbxxDao.deleteFbxxByKey(id);
    }
//    操作件
    @Override
    @Transactional
    public Map<String, Object> operFbxx(String data) {
        Map<String, Object> res = new HashMap<>(16);
        JSONObject fbxxAndLog = JSON.parseObject(data);
        DchyFbxx fbxx = fbxxAndLog.getJSONObject("fbxx").toJavaObject(DchyFbxx.class);
        DchyLog log;
        String handle = fbxxAndLog.getString("handle");
        try {
            switch (handle){
                case "保存":
                    res = addFbxx(data);
                    break;
                case "发布":
                    res = releaseFbxx(data);
                    break;
                case "立即发布":
                    res = addFbxx(data);
//                这块需要把上调日志id传入来修改为已办，怎么做呢,
                    log = (DchyLog) res.get("log");
                    fbxxAndLog.put("lastLogId",log.getId());
                    fbxxAndLog.put("fbxx",res.get("fbxx"));
                    data = JSON.toJSONString(fbxxAndLog);
                    res = releaseFbxx(data);
                    break;
                case "编辑":
                    renewFbxx(fbxx);
                    res.put("fbxx",fbxx);
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
	public Integer findfwCount(String did, String state) {
		return dchyFbxxDao.findfwCount(did,state);
	}
	@Override
	public List<Map<String,Object>> getFbxxGgByDid(String str, String did,String wtqr) {
//        将中标的标记
        List<Map<String,Object>> sqyxList = dchyFbxxDao.getFbxxGgByDid(str,did,wtqr);
        List<Map<String,Object>> zbggList = dchySqyxDao.getZbGg(null);
        for (int i=0;i<sqyxList.size();i++){
            String pid1 = (String) sqyxList.get(i).get("ID");
            for (int j=0;j<zbggList.size();j++) {
                String pid2 = (String) zbggList.get(j).get("ID");
                String fb = (String) zbggList.get(j).get("FB");
                if (StringUtil.isNotEmpty(fb)&&fb.indexOf("废标")!=-1){
                    if (pid1.equals(pid2)){
                        sqyxList.get(i).put("STATE","废标");
//                        returnList.remove(i);
//                        zbggList.remove(j);
//                        i--;
//                        j--;
                    }
                }
            }
        }
		return sqyxList;
	}
	@Override
	public List<DchyFbxx> getFbxxByPersonal(String idcard) {
		return dchyFbxxDao.getFbxxByPersonal(idcard);
	}

}