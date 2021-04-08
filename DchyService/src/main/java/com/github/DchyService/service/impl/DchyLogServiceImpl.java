package com.github.DchyService.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.dao.DchyLogDao;
import com.github.DchyService.dao.DchySqyxDao;
import com.github.DchyService.entity.DchyFbxx;
import com.github.DchyService.entity.DchyLog;
import com.github.DchyService.entity.DchySqyx;
import com.github.DchyService.service.DchyFbxxService;
import com.github.DchyService.service.DchyLogService;
import com.github.DchyService.service.DchySqyxService;
import com.github.pagehelper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchyLog)表服务实现类
 *
 * @author makejava
 * @since 2020-10-26 19:01:50
 */
@Service("dchyLogService")
public class DchyLogServiceImpl implements DchyLogService {
    private Logger log = LoggerFactory.getLogger(DchyLogServiceImpl.class);
    @Resource
    private DchyLogDao dchyLogDao;
    @Resource
    private DchyFbxxService dchyFbxxService;
    @Resource
    private DchySqyxDao dchySqyxDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DchyLog findById(String id) {
        return this.dchyLogDao.findById(id);
    }

    /**
     * 查询多条数据
     *
     * @param dchyLog
     * @return 对象列表
     */
    @Override
    public List<DchyLog> findList(DchyLog dchyLog) {
        return this.dchyLogDao.findList(dchyLog);
    }

    /**
     * 新增数据
     *
     * @param dchyLog 实例对象
     * @return 实例对象
     */
    @Override
    public DchyLog insert(DchyLog dchyLog) {
        this.dchyLogDao.insert(dchyLog);
        return dchyLog;
    }

    /**
     * 修改数据
     *
     * @param dchyLog 实例对象
     * @return 实例对象
     */
    @Override
    public DchyLog update(DchyLog dchyLog) {
        this.dchyLogDao.update(dchyLog);
        return dchyLogDao.findById(dchyLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.dchyLogDao.deleteById(id) > 0;
    }

    /**
     * 查询资质信息待办
     *
     * @param dchyLog 实例对象
     * @return 影响行数
     */
    @Override
    public List<Map<String, Object>> findZzxxDealingList(DchyLog dchyLog) {
        return dchyLogDao.findZzxxDealingList(dchyLog);
    }

    /**
     * 查询业务信息待办
     *
     * @param dchyLog 实例对象
     * @return 影响行数
     */
    @Override
    public List<Map<String, Object>> findYwxxDealingList(DchyLog dchyLog) {
        return dchyLogDao.findYwxxDealingList(dchyLog);
    }

    /**
     * 查询发布信息待办
     *
     * @param dchyLog 实例对象
     * @return 影响行数
     */
    @Override
    public List<Map<String, Object>> findFbxxDealingList(DchyLog dchyLog) {
        return dchyLogDao.findFbxxDealingList(dchyLog);
    }
//    项目正常撤回
    @Override
    @Transactional
    public Map<String,Object> revokeProject(String data) {
        JSONObject jObj = JSON.parseObject(data);
        DchyLog log = jObj.getJSONObject("log").toJavaObject(DchyLog.class);
        DchyLog lastLog = new DchyLog();
        DchyFbxx fbxx = new DchyFbxx();
        String lastLogId = jObj.getString("lastLogId");
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        String pid = log.getPid();
        String dqhj = log.getDqhj();
        String dqhj2 = "";
        Map<String,Object> res = new HashMap<>(16);

        log.setClzt("待办");
        log.setFssj(curr);
        log.setJssj(curr);
        log.setCjsj(curr);
        switch (dqhj){
            case "采购确认":
                dqhj2 = "公示期";
                break;
            case "项目发布":
                dqhj2 = "草稿";
                break;
            default:
//                        这会儿要只改申请意向为空
                revokeSqyx(pid);
                return res;
        }
        try {
//            插入日志,修改上条为已办
            dchyLogDao.insert(log);
            lastLog.setClzt("已办");
            lastLog.setId(lastLogId);
            dchyLogDao.update(lastLog);
//            修改项目状态
            if (StringUtil.isNotEmpty(pid)) {
                fbxx.setId(pid);
                fbxx.setXmzt("采购确认".equals(dqhj) ? "公示中" : "草稿");
                dchyFbxxService.renewFbxx(fbxx);
            }
//            修改申请确认为空
            List<Map<String,Object>> sqyxList = revokeSqyx(pid);
            res.put("sqyxList",sqyxList);
            res.put("log",log);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res;
    }
//    项目不正常撤回
    @Transactional
    public List<Map<String,Object>> revokeSqyx(String pid) {
        List<Map<String,Object>> sqyxList = dchySqyxDao.getSqyxListByFbxxid("",pid);
        DchySqyx sqyx = new DchySqyx();
        if(sqyxList.size()>0){
            for (Map<String,Object> sq:sqyxList){
                sqyx.setWtqr("X");          //x表示撤销
                sqyx.setId((String) sq.get("SQYXID"));
//                每个都设置为未确认
                dchySqyxDao.update(sqyx);
            }
        }
        return sqyxList;
    }
    @Transactional
    public Map<String,Object> revokeProject1(String data) {
        JSONObject jObj = JSON.parseObject(data);
        DchyLog log = jObj.getJSONObject("log").toJavaObject(DchyLog.class);
        DchyLog lastLog = new DchyLog();
        DchyFbxx fbxx = new DchyFbxx();
        String lastLogId = jObj.getString("lastLogId");
        Map<String,Object> res = new HashMap<>(16);
//        在此做判断，是中标公告的撤销，还是采购公告的撤销
        Timestamp curr = new Timestamp(System.currentTimeMillis());
        String dqhj = log.getDqhj();
        String dqhj2 = "";

        log.setClzt("待办");
        log.setFssj(curr);
        log.setJssj(curr);
        log.setCjsj(curr);
        switch (dqhj){
            case "采购确认":
                dqhj2 = "公示期";
                break;
            case "项目发布":
                dqhj2 = "草稿";
                break;
            default:
//                        这会儿要只改申请意向为空
        }
        try {
            dchyLogDao.insert(log);
            String pid =log.getPid();
            if (StringUtil.isNotEmpty(pid)){
                fbxx.setId(pid);

                fbxx.setXmzt(dqhj2);
                dchyFbxxService.renewFbxx(fbxx);
//                修改上条为已办，
                lastLog.setId(lastLogId);
                lastLog.setClzt("已办");
                dchyLogDao.update(lastLog);
//                查一下新的fbxx
                fbxx.setId(pid);
                fbxx = dchyFbxxService.getFbxx(fbxx);
                res.put("fbxx",fbxx);
                res.put("log",log);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
    @Override
    public  DchyLog getLastLog(String pid) {
        return dchyLogDao.getLastLog(pid);
    }
}