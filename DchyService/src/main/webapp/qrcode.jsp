<%@page import="org.springframework.core.env.Environment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	//接收传过来的参数
 	String id = request.getParameter("pid");
	String cilnum = request.getParameter("CertificateNumber");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8" name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" >
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>成果审核结论书</title>
		<link href="./libs/plugins/weui/lib/weui.min.css" rel="stylesheet">
		<link href="./libs/plugins/weui/css/jquery-weui.min.css" rel="stylesheet" >
		<script src="./libs/plugins/jquery-1.12.3.min.js"></script>
		<script src="./libs/plugins/jquery.cookie.js"></script>
		<script src="./libs/plugins/weui/js/jquery-weui.min.js"></script>
		<script src="<%=contextPath %>/js/detail.js"></script>
	</head>
	<body style="margin:0px; padding:0px; margin: auto;padding-top: 15px;">
		<div>
			<input id="pid" type="hidden" value="<%=id%>" />
			<input id="cilnum" type="hidden" value="<%=cilnum%>" />
			<input id="contextPath" type="hidden" value="<%=contextPath%>" />
		</div>
		<table id = "showdetail" cellspacing="0" style="width:95%;margin: auto;border-collapse:collapse">
		</table>
	</body>
</html>

