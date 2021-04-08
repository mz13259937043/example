package com.github.DchyService.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.dao.DchySqyxDao;
import com.github.DchyService.entity.DchyFbxx;
import com.github.DchyService.entity.DchyLog;
import com.github.DchyService.entity.DchySqyx;
import com.github.DchyService.entity.ProjectState;
import com.github.DchyService.service.DchyFbxxService;
import com.github.DchyService.service.DchyLogService;
import com.github.DchyService.service.DchySqyxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchySqyx)表服务实现类
 *
 * @author makejava
 * @since 2020-10-21 09:03:56
 */
@Service("dchySqyxService")
public class DchySqyxServiceImpl implements DchySqyxService {
    private Logger log = LoggerFactory.getLogger(DchySqyxServiceImpl.class);
    @Resource
    private DchySqyxDao dchySqyxDao;
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
    public DchySqyx queryById(String id) {
        return this.dchySqyxDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<DchySqyx> queryAllByLimit(int offset, int limit) {
        return this.dchySqyxDao.queryAllByLimit(offset, limit);
    }

    /**
     * 提交申请
     *
     * @param dchySqyx 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public DchySqyx insert(DchySqyx dchySqyx) {
//        提交申请
        try{
                this.dchySqyxDao.insert(dchySqyx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return dchySqyx;
    }

    /**
     * 委托确认
     * 修改数据
     *
     * @param data 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Map<String,Object> update(String data) {
        Map<String,Object> res = new HashMap<>(16);
        JSONObject fbxxAndLog = JSON.parseObject(data);
        DchySqyx sqyx = fbxxAndLog.getJSONObject("sqyx").toJavaObject(DchySqyx.class);
        DchyLog log = fbxxAndLog.getJSONObject("log").toJavaObject(DchyLog.class);
        String lastLogId = fbxxAndLog.getString("lastLogId");
        DchyLog lastLog = new DchyLog();
        DchyFbxx fbxx = new DchyFbxx();

        if(!(sqyx!=null&&log!=null)){
            res.put("msg","参数为空！！");
            return res;
        }

        Timestamp curr = new Timestamp(System.currentTimeMillis());
//                委托确认开始操作日志对象
//        确认申请
        sqyx.setPid(log.getPid());
        sqyx.setWtsj(curr);
        try{
            int x = this.dchySqyxDao.update(sqyx);
            dchySqyxDao.updateBypid(log.getPid(),sqyx.getId());
            log.setJssj(curr);
            log.setFssj(curr);
            log.setCjsj(curr);
            log = this.dchyLogService.insert(log);
            //            修改上条为已办
            lastLog.setId(lastLogId);
            lastLog.setClzt("已办");
            this.dchyLogService.update(lastLog);
//            修改主表状态，合同期
            fbxx.setId(log.getPid());
            fbxx.setXmzt(ProjectState.HTQ.getValue());
            this.dchyFbxxService.renewFbxx(fbxx);
            res.put("log",log);
            res.put("sqyx",sqyx);
            res.put("fbxx",fbxx);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.dchySqyxDao.deleteById(id) > 0;
    }

    /**
    * @Description //查询申请意向列表
    * @Author maoZhan
    * @Date 2020/10/23 15:44
    * @param  * @param dchySqyx
    * @return java.util.List<com.github.DchyService.entity.DchySqyx>
    **/
    @Override
    public List<DchySqyx> queryAll(DchySqyx dchySqyx) {
        return dchySqyxDao.queryAll(dchySqyx);
    }

    @Override
    public List<Map<String, Object>> getSqyxListByFbxxid(String str,String id) {
        return dchySqyxDao.getSqyxListByFbxxid(str,id);
    }

    @Override
    public List<Map<String, Object>> getZbGg(String str) {

        /*
        结束后无人申请
        选取时间过后不选取
         两种情况设置为废标*/
        List<Map<String, Object>> zbcg = dchySqyxDao.getZbGg(str);

        return zbcg;
    }

    @Override
    public  List<DchySqyx> getByPidAndChdwid(Map<String, Object> arg) {
        String pid = (String) arg.get("pid");
        String chdwid = (String) arg.get("chdwid");
        return dchySqyxDao.getByPidAndChdwid(pid,chdwid);
    }

    @Override
    public DchySqyx modifySqyx(DchySqyx sqyx) {
        if (sqyx!=null&&sqyx.getId()!=null){
            if (dchySqyxDao.update(sqyx)==1){
                return sqyx;
            }
        }
        return null;
    }

}