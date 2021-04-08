<%@page import="org.springframework.core.env.Environment"%>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Environment env;

	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	//接收传过来的参数
 	String _id = request.getParameter("proid");
 	String _data = request.getParameter("data");
	JSONObject _data2 = JSON.parseObject(_data);
	String temp = "暂无信息";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0">
		<title>成果审核详情</title>
		<link href="./libs/plugins/weui/lib/weui.min.css" rel="stylesheet">
		<link href="./libs/plugins/weui/css/jquery-weui.min.css" rel="stylesheet" >
		<link href="./libs/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="./css/phoneView.css" rel="stylesheet">
		
		<!-- <script src="./js/common/config.js?v="></script> -->
		<script src="./libs/plugins/jquery-1.12.3.min.js"></script>
		<script src="./libs/plugins/jquery.cookie.js"></script>
		<script src="./libs/plugins/weui/js/jquery-weui.min.js"></script>
	</head>
	<body style="margin:0px; padding:0px;overflow:hidden;">
<%--	项目名称、业主单位、测绘单位、多测合一项目编号、成果审核通过时间--%>
		<div>
			<input id="cxid" type="hidden" value="<%=_id%>" />
			<input id="proUrl" type="hidden" value="<%=basePath%>" />
		</div>
		<!-- pdf预览 -->
    	<div id="pop" class="pop" style="display: none;z-index:999; height:100% ; width:100%;background:white; position:absolute;top:0px;left:0px;margin:0px;padding:0px;">
	        
	        <iframe src="" frameborder="0" id="pdfContainer" name="pdfContainer" scrolling="auto" allowtransparency="true" style="width:100%; position:absolute;top:0px;left:0px;margin:0px;padding:0px;"></iframe>
    		<a href="javascript:close()" style="position: absolute;color:#c2c2c2;right:48px;top:48px;z-index: 10000;display: inline-block;" id="close">
    			<li class="fa fa-times-circle fa-3x"></li>
    		</a>
    	</div>
    	<div style="height:100% ; width:100%;background:white; position:absolute;top:0px;left:0px;margin:0px;padding:0px;overflow:auto;">
			<div class="weui_tab">
			    <div class="weui_tab_bd container" id="container">
					<div class="tabbar">
				        <div class="weui_tab">
							 <div class="weui_tab_bd" id="content">
					            <div class="weui_media_box weui_media_text title_bot">
						            <h4>成果审核详情</h4>
					        	</div>
								<div class="bd">
									<div class="weui_cells">
									  	<div class="weui_cell">
											<div class="weui_cell_bd weui_cell_primary">
												 <p class="weui_media_desc">
													 <span class="sub_title"><i class="fa fa-tag"></i>项目名称：</span>
													 <label id="proId"><%=_data2.getString("fwmc")%></label>
												 </p>
											</div>
										</div>
										<div class="weui_cell">
											<div class="weui_cell_bd weui_cell_primary">
												 <p class="weui_media_desc">
													 <span class="sub_title"><i class="fa fa-hashtag"></i>业主单位：</span>
													 <label id="serialid"><%=_data2.getString("cgdw")%></label>
												 </p>
											</div>
										</div>
										<div class="weui_cell">
											<div class="weui_cell_bd weui_cell_primary">
												 <p class="weui_media_desc">
													 <span class="sub_title"><i class="fa fa-book"></i>测绘单位：</span>
													 <label id="proName"><%=_data2.getString("zxjg")%></label>
												 </p>
											</div>
										</div>
										<div class="weui_cell">
											<div class="weui_cell_bd weui_cell_primary">
												 <p class="weui_media_desc">
													 <span class="sub_title"><i class="fa fa-university"></i>多测合一项目编号：</span>
													 <label id="jsUnit"><%=_data2.getString("xmbh")%></label>
												 </p>
											</div>
										</div>
										<div class="weui_cell">
											<div class="weui_cell_bd weui_cell_primary">
												 <p class="weui_media_desc">
													 <span class="sub_title"><i class="fa fa-user"></i>成果审核通过时间：</span>
													 <label id="signer"><%=_data2.getString("date")%></label>
												 </p>
											</div>
										</div>
										<div class="weui_cell">
											<div class="weui_cell_bd weui_cell_primary">
												 <p class="weui_media_desc">
													 <span class="sub_title"><i class="fa fa-phone"></i>联系电话：</span>
													 <label id="phone"><%=_data2.getString("zxje")%></label>
												 </p>
											</div>
										</div>
									</div>
								</div>
								<%--<div class="weui_cells_title">相关附件</div>
								<div class="weui_media_box weui_media_text dirmeun" id="hasAttachmentView" style="display:none;">
									<ul class="weui_media_info">
										<li class="weui_media_info_meta" onclick="backRoot(this);"><input id="rootPath" class="rootPath" type="hidden" value="" /><i class="fa fa-home"></i>根目录</li>
										<li class="weui_media_info_meta weui_media_info_meta_extra" onclick="backDir();" id="backDir" style="display: none;"><i class="fa fa-reply"></i>返回上级</li>
									</ul>
								</div>
								<div id="noAttachmentView" style="margin-left:16px;">
										 暂无附件
								</div>
								<div class="weui_cells" id="attachment"></div>--%>
							</div>
				        </div>
					</div>
				</div>
			
			<!-- 附件下载 -->
			<!-- 
			  	<div class="weui_tabbar">
	                <a href="javascript:;" class="weui-btn weui-btn_default" style="width:100%;padding:6px 0px;display:none" id="btnDownloadFile">
	                	<div class="weui_tabbar_icon">
		                	<i class="fa fa-file-text" ></i>
		            	</div>
	                    <p class="weui_tabbar_label">附件下载</p>
	                </a>
	            </div>
	            -->
	         
	            
            </div>
		</div>
	</body>
	<script>
	</script>
	<script src="./libs/pdf.js"></script>
	<script>
			var time=new Date().getTime();
			document.write('<script language="javascript" src="./js/common/config.js?v='+time+'"><\/script\>')
			document.write('<script src="./js/phoneView.js?v=' + time +'"><\/script\>')
		
	</script>
</html>

