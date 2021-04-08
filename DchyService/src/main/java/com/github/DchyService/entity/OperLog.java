package com.github.DchyService.entity;/**
 * @title: OperLog
 * @projectName DchyService
 * @description: TODO
 * @author 14874
 * @date 2020/11/3 17:13
 */

import com.github.DchyService.service.DchyLogService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 *@ClassName OperLog
 *@Description TODO
 *@Author maoZhan
 *@Date DATE{TIME}
 */
@Component
public class OperLog {

    @Resource
    private DchyLogService dchyLogService;

    public OperLog() {

    }
    /**
    * @Description //操作日志方法
    * @Author maoZhan
    * @Date 2020/11/3 17:15
    * @param  * @param
    * @return boolean
    **/
    public List<DchyLog> toOperLog(List<DchyLog> logList,String pid) {
        boolean succ = false;
        try {
            for (DchyLog log:logList) {
                Timestamp curr = new Timestamp(System.currentTimeMillis());
//                多条日志将之前改为已办       这块提前给日志排个序
                if (logList.size()>1){
                    log.setClzt("已办");
                }
                log.setPid(pid);
                log.setCjsj(curr);
                log.setFssj(curr);
                log.setJssj(curr);
                this.dchyLogService.insert(log);
            }
        }catch (Exception e){
            System.out.println("操作日志出错--------------------------------");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return logList;
    }
}
