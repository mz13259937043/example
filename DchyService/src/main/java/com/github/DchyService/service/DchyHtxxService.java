package com.github.DchyService.service;

import com.github.DchyService.entity.DchyHtxx;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * (DchyHtxx)表服务接口
 *
 * @author makejava
 * @since 2020-10-27 16:44:33
 */
public interface DchyHtxxService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DchyHtxx queryById(String id);

    /**
     * 查询多条数据
     *
     * @param dchyHtxx
     * @return 对象列表
     */
    List<DchyHtxx> queryAll(DchyHtxx dchyHtxx);

    /**
     * 修改数据
     *
     * @param dchyHtxx 实例对象
     * @return 实例对象
     */
    DchyHtxx update(DchyHtxx dchyHtxx);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
    * @Description //合同确认
    * @Author maoZhan
    * @Date 2020/10/30 17:16
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String, Object> qrHtxx(String data);

    /**
    * @Description //合同退回
    * @Author maoZhan
    * @Date 2020/11/22 10:48
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String, Object> revokeHtxx(String data);

    /**
    * @Description //操作合同件，保存、修改，上传，立即上传
    * @Author maoZhan
    * @Date 2020/11/4 11:38
    * @param  * @param data
    * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String, Object> operHtxx(String data);

    /**
    * @Description //pid关联查询合同信息
    * @Author maoZhan
    * @Date 2020/11/4 14:49
    * @param  * @param pid
    * @return com.github.DchyService.entity.DchyHtxx
    **/
    DchyHtxx getByPid(String pid);

}