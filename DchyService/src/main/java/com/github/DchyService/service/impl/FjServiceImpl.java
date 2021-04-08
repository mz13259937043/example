package com.github.DchyService.service.impl;

import com.github.DchyService.dao.DchyFjFolderDao;
import com.github.DchyService.entity.DchyFjFolder;
import com.github.DchyService.entity.DchyFjRecord;
import com.github.DchyService.service.FjService;
import com.github.DchyService.tools.ConvertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description //附件service
* @Author maoZhan
* @Date 2020/10/26 18:20
* @param  * @param null
* @return
**/
@Service
public class FjServiceImpl implements FjService {
	private Logger log = LoggerFactory.getLogger(FjServiceImpl.class);

	@Autowired
	private DchyFjFolderDao dchyFjFolderDao;

	@Override
	public DchyFjFolder getFolderByPath(String filepath) {
		return dchyFjFolderDao.getFolderByPath(filepath);
	}

	@Override
	public DchyFjRecord addRecord(DchyFjRecord record) {
		int x = dchyFjFolderDao.addRecord(record);
		return record;
	}

	@Override
	public DchyFjFolder addFolder(DchyFjFolder folder) {

		int x = dchyFjFolderDao.addFolder(folder);
		return folder;
	}

	@Override
	public DchyFjFolder getFolderByKey(String id) {
		return dchyFjFolderDao.getFolderByKey(id);
	}

	@Override
	public boolean deleteRecordByPath(String filepath, String name) {
		int x = dchyFjFolderDao.deleteRecordByPath(filepath,name);
		return x==1?true:false;
	}

	@Override
	public List<DchyFjRecord> getRecordByFolderId(String id) {
		return dchyFjFolderDao.getRecordByFolderId(id);
	}

	@Override
	public DchyFjRecord getRecordByKey(String id) {
		return dchyFjFolderDao.getRecordByKey(id);
	}

	@Override
	public boolean deleteFolderByKey(String fjid) {
		return dchyFjFolderDao.deleteFolderByKey(fjid);
	}

	@Override
	public boolean deleteRecordByKey(String fjid) {
		return dchyFjFolderDao.deleteRecordByKey(fjid);
	}

//	删除文件夹内部的东西，包括文件和文件夹
	@Override
	@Transactional
	public Boolean deleteDir(String id) {
		DchyFjFolder folder = dchyFjFolderDao.getFolderByKey(id);
		Boolean suc = false;
		List<DchyFjFolder> folders = folder.getDchyFjFolders();
		List<DchyFjRecord> records = folder.getDchyFjRecords();
//		这个folder里面有他的子文件和子文件夹
		try {
			if (records.size()>0){
				for (DchyFjRecord record:records) {
					suc = dchyFjFolderDao.deleteRecordByKey(record.getId());
				}
			}
			if (folders.size()>0){
				for (DchyFjFolder folder1:folders) {
					suc = deleteDir(folder1.getId());
//				suc = dchyFjFolderDao.deleteFolderByKey(folder1.getId());
				}
			}
//			删除文件夹
			dchyFjFolderDao.deleteFolderByKey(id);
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return suc;
	}
}
