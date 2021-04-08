package com.github.DchyService.entity;

import java.util.List;
import java.util.Map;

public class Result {

	private Boolean flag;
	private Integer code;
	private String message;
	private Object data;
	private List fileList;

	public Result() {
	}

	public Result(Boolean flag, Integer code, String message) {
		this.flag = flag;
		this.code = code;
		this.message = message;
	}

	public Result(Boolean flag, Integer code, String message, Object data) {
		this.flag = flag;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Result(Boolean flag, Integer code, String message, Object data,  List filelist) {
		this.flag = flag;
		this.code = code;
		this.message = message;
		this.data = data;
		this.fileList = filelist;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result{" + "flag=" + flag + ", code=" + code + ", message='" + message + '\'' + ", data=" + data + '}';
	}

	public List getFileList() {
		return fileList;
	}

	public void setFileList(List fileList) {
		this.fileList = fileList;
	}

}