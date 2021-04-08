package com.github.DchyService.service;

import com.github.DchyService.entity.DchyFjFolder;
import com.github.DchyService.entity.DchyFjRecord;

import java.util.List;

/**
 * @author 14874
 * @title: FjService
 * @projectName DchyService
 * @description: TODO
 * @date 2020/10/26 18:18
 */
public interface FjService {
//    路径查询
    DchyFjFolder getFolderByPath(String filepath);

    DchyFjRecord addRecord(DchyFjRecord record);

    DchyFjFolder addFolder(DchyFjFolder folder);
//    id查询
    DchyFjFolder getFolderByKey(String id);
//  路径名和文件名定位删除
    boolean deleteRecordByPath(String filepath, String name);

    List<DchyFjRecord> getRecordByFolderId(String id);

    DchyFjRecord getRecordByKey(String recordid);

    boolean deleteFolderByKey(String fjid);

    boolean deleteRecordByKey(String fjid);

    Boolean deleteDir(String id);
}
