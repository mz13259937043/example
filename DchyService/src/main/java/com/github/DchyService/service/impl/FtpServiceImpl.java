package com.github.DchyService.service.impl;

import com.github.DchyService.service.FtpFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qufan
 * @version v1.0
 * @Date 创建时间：2020-10-20 14:35:11
 * @Description 测绘单位注册、审核、冻结业务逻辑层实现
 */
@Service
public class FtpServiceImpl implements FtpFileService {
    private Logger log = LoggerFactory.getLogger(FtpServiceImpl.class);
}
