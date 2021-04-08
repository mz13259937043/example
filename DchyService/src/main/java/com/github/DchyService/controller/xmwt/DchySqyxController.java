package com.github.DchyService.controller.xmwt;

import com.github.DchyService.entity.DchySqyx;
import com.github.DchyService.service.DchySqyxService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DchySqyx)表控制层
 *
 * @author makejava
 * @since 2020-10-21 09:03:56
 */
@RestController
@CrossOrigin
@RequestMapping("xmwt/dchySqyx")
public class DchySqyxController {
    private Logger log = LoggerFactory.getLogger(DchySqyxController.class);
    /**
     * 服务对象
     */
    @Resource
    private DchySqyxService dchySqyxService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne/{id}")
    public DchySqyx selectOne(@PathVariable String id) {
        return this.dchySqyxService.queryById(id);
    }

    /**
     * 通过对象查询数据集合
     *
     * @param dchySqyx
     * @return map
     */
    @PostMapping(value="/getSqyxList")
    @ResponseBody
    public Map<String,Object> queryAll(@RequestBody DchySqyx dchySqyx){
        Map<String, Object> map = new HashMap<String, Object>();
        List<DchySqyx> list = new ArrayList<DchySqyx>();
        PageHelper.startPage(dchySqyx.getPageNum(),dchySqyx.getPageSize());
        list = dchySqyxService.queryAll(dchySqyx);

        PageInfo<DchySqyx> pageInfo = new PageInfo<DchySqyx>(list);
        map.put("code", 200);
        map.put("msg", "ok");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 保存数据
     *
     * @param dchySqyx
     * @return dchySqyx
     */
    @RequestMapping(value="/saveSqyx")
    @ResponseBody
    public DchySqyx saveDchySqyx(@RequestBody DchySqyx dchySqyx){
        return dchySqyxService.insert(dchySqyx);
    }

    /**
    * @Description //委托确认
    * @Author maoZhan
    * @Date 2020/10/30 16:28
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @RequestMapping(value="/updateSqyx")
    @ResponseBody
    public Map<String,Object> updateDchySqyx(@RequestBody String data){
        Map<String, Object> result = new HashMap<>();
        Map<String,Object> res = dchySqyxService.update(data);
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
     * 通过主键删除单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value="deleteSqyxByKey/{id}")
    public boolean deleteById(@PathVariable String id) {
        return dchySqyxService.deleteById(id);
    }

    /**
    * @Description //
    * @Author maoZhan
    * @Date 2020/10/23 16:29
    * @param  * @param id
    * @return boolean
    **/
    @PostMapping("/getSqyxListByFbxxid")
    @CrossOrigin
    public Map<String, Object> getSqyxListByFbxxid(@RequestBody Map<String,Object> arg) {
        Map<String, Object> result = new HashMap<>();
        int pageIndex = (int)arg.get("pageIndex");
        int pageSize = (int)arg.get("pageSize");
        String str = (String) arg.get("str");
        String id = (String) arg.get("id");
        PageHelper.startPage(pageIndex,pageSize);
        List<Map<String, Object>> res =  dchySqyxService.getSqyxListByFbxxid(str,id);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(res);
        if(res!=null){
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


    /**
    * @Description //单纯修改申请提交的表单
    * @Author maoZhan
    * @Date 2020/12/11 16:35
    * @param  * @param sqyx
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @PostMapping("/modifySqyx")
    @CrossOrigin
    public Map<String,Object> modifySqyx(@RequestBody DchySqyx sqyx) {
        Map<String, Object> result = new HashMap<>();
        DchySqyx sqyx1 = dchySqyxService.modifySqyx(sqyx);
        if (sqyx!=null){
            result.put("state",200);
            result.put("msg","修改成功");
            result.put("data",sqyx1);
        }else {
            result.put("state",404);
            result.put("msg","修改失败");
            result.put("data",null);
        }
        return result;
    }
    /**
    * @Description //精确定位测绘单位对某个业务的申请信息
    * @Author maoZhan
    * @Date 2020/12/11 16:36
    * @param  * @param arg
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @PostMapping("/getByPidAndChdwid")
    @CrossOrigin
    public Map<String,Object> getByPidAndChdwid(@RequestBody Map<String,Object> arg) {
        Map<String, Object> result = new HashMap<>();

        List<DchySqyx> sqyxList = dchySqyxService.getByPidAndChdwid(arg);
        if (sqyxList!=null){
            result.put("state",200);
            result.put("msg","查询成功");
            result.put("data",sqyxList);
        }else {
            result.put("state",404);
            result.put("msg","查询失败");
            result.put("data",null);
        }
        return result;
    }
    @PostMapping("/getZbGg")
    @CrossOrigin
    public Map<String,Object> getZbGg(@RequestBody Map<String,Object> arg) {
        Map<String, Object> result = new HashMap<>();
        int pageIndex = (int)arg.get("pageIndex");
        int pageSize = (int)arg.get("pageSize");
        String str = (String) arg.get("str");
        PageHelper.startPage(pageIndex,pageSize);
        List<Map<String, Object>> res = dchySqyxService.getZbGg(str);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(res);
        if(res!=null){
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
}