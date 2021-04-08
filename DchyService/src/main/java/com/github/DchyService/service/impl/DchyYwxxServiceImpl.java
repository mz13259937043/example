package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyYwxxDao;
import com.github.DchyService.entity.DchyYwxx;
import com.github.DchyService.service.DchyYwxxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DchyYwxx)表服务实现类
 *
 * @author makejava
 * @since 2020-10-28 10:23:15
 */
@Service("dchyYwxxService")
public class DchyYwxxServiceImpl implements DchyYwxxService {
    private Logger log = LoggerFactory.getLogger(DchyYwxxServiceImpl.class);
    @Resource
    private DchyYwxxDao dchyYwxxDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DchyYwxx findById(String id) {
        return this.dchyYwxxDao.findById(id);
    }

    /**
     * 查询多条数据
     *
     * @param dchyYwxx
     * @return 对象列表
     */
    @Override
    public List<DchyYwxx> findList(DchyYwxx dchyYwxx) {
        return this.dchyYwxxDao.findList(dchyYwxx);
    }

    /**
     * 新增数据
     *
     * @param dchyYwxx 实例对象
     * @return 实例对象
     */
    @Override
    public DchyYwxx insert(DchyYwxx dchyYwxx) {
        this.dchyYwxxDao.insert(dchyYwxx);
        return dchyYwxx;
    }

    /**
     * 修改数据
     *
     * @param dchyYwxx 实例对象
     * @return 实例对象
     */
    @Override
    public DchyYwxx update(DchyYwxx dchyYwxx) {
        this.dchyYwxxDao.update(dchyYwxx);
        return this.findById(dchyYwxx.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.dchyYwxxDao.deleteById(id) > 0;
    }
}