var cilName,pid; // 证本名称
var contextPath;
var leftPad = "100pt";
$(document).ready(function () {
//	xzyjs:建设项目用地预审与选址意见书（选址意见书） 
//	ydghxkz:建设用地规划许可证  
//	gcghxkz:建设工程规划许可证  
//	xcghxkz:乡村规划许可证
	var _url;
	var cilnum = $("#cilnum").val().trim();
	pid = $("#pid").val().trim();
	contextPath = $("#contextPath").val();
	if(cilnum.indexOf("用字第") != -1) { // “用字第” --> 建设项目用地预审与选址意见书（选址意见书）
		cilName = "建设项目用地预审与选址意见书";
		leftPad = "148pt";
	}else if(cilnum.indexOf("地字第") != -1) {// “地字第” --> 建设用地规划许可证
		cilName = "建设用地规划许可证";
	}else if(cilnum.indexOf("建字第") != -1) { // “建字第” --> 建设工程规划许可证
		cilName = "建设工程规划许可证";
	}else if(cilnum.indexOf("乡字第") != -1) { // “乡字第” --> 乡村规划许可证
		cilName = "乡村规划许可证";
	}else {
		cilName = "建设工程规划条件核实";
	}
	_url = "http://xagh.org.cn:8599/ApprovalService/api/approval/getQrcode?cilnum="+cilnum,
	getdata(_url,cilnum);
	$("title").text(cilnum);
});
// 根据id获取项目情况
function findPathById(pid){
	var result;
	$.ajax({
		type: "get",
	    async:false, 
		url:"http://xagh.org.cn:8599/ApprovalService/api/approval/findPathById?pid="+pid,
		success:function(data, textStatus, jqXHR) {
				result = data;
			},
		error:function(jqXHR, textStatus, errorThrown) {
				result = "";
				console.log(errorThrown);
			}
	});
	return result;
}

// 获取路径下的附件
function getMaterPathByPath(path){
	var result = "";
	$.ajax({
		type: "POST",
		async: false,
	    dataType:'json',
		contentType:"application/json",
		url:"http://xagh.org.cn:8030/DocService/ftpfile/list2.do",
		data:JSON.stringify({"target":path,"parentId":"-1","username":"123","userId":"123"}),
		success:function(data, textStatus, jqXHR) {
				result = data;
			},
		error:function(jqXHR, textStatus, errorThrown) {
				result = "";
				console.log(errorThrown);
			}
	});
	return result;
}

//获取数据
function getdata(_url,cilnum){
	var url = _url;
	$.ajax({
		type: "get",
		dataType:"json",
	    async:false, 
		url: url,
		success: function(data, textStatus, jqXHR) {
				showView(data);
			},
		error: function(jqXHR, textStatus, errorThrown) {
				console.log(errorThrown);
			}
	});
}

function showView(data){
	var data = sortData(data);
	var ftfjmc,tr,imgName,zt,zydmj,jydmj,dzdlmj,dzldmj,mjcontent,publicpath,ahref;
	// 用于匹配URL的正则表达式
	var match = /[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/;
	if(data['状态'] == 0){
		zt = "wuxiao.png";
	}else{
		zt = "youxiao.png";
	}
	var title = "<tr>"
				+"<td colspan='2' style='text-align:center;color:#060606;font-size:16pt;position:relative;'>"
				+"<img alt='' src='"+contextPath+"/img/"+zt+"' style='padding-left:"+leftPad+";position:absolute;'>"
				+cilName+"</td>"
				+"<tr>";
	$("#showdetail").append(title); // 添加头部
	Object.keys(data).forEach(function(key){
		if(key == "附图及附件名称"){
			ftfjmc="<tr style='width:100%; border-bottom: 1px solid #E2E2E2;'>" 
				+"<td style='color:#3296FA;width:45%;font-size:11pt;padding:5px;text-align:justify;text-align-last:justify;'>"
				+"<img alt='' src='"+contextPath+"/img/lujing176.png' style='width:12pt;height:12pt;padding-right:5px;'>" +key+"：</td>"
				+"<td style='width:55%;font-size:10pt;padding:2px;color:#000000;'>&nbsp;</td>" 
				+"</tr>"
				+"<tr style='width:100%;'>" 
				+"<td style='width:100%;font-size:12pt;padding:5px;color:#060606;'  colspan='2'>" 
				+"<textarea style='width:100%;border:0;color:#060606;resize:none;overflow-y:auto; min-height:100px;' readonly>" +data[key]+"</textarea></td>"
				+"</tr>";
			return; 
		}else if(key == "建设单位(个人)" || key == "用地单位" || key == "批准用地机关" || key == "建设单位"){
			imgName = "zu43.png";
		}else if(key == "证本流水编号" || key == "批准用地文号" || key == "项目代码" || key == "项目编号" || key == "意见书编号"){
			imgName = "zu16.png";
		}else if(key == "建设位置" || key == "用地位置" || key == "项目拟选位置" || key == "建设项目位置"){
			imgName = "weizhi.png";
		}else if(key == "发证日期" || key == "实测时间"){
			imgName = "riqi.png";
		}else if(key == "建设项目名称" || key == "项目名称"){
			imgName = "bumen.png";
		}else if(key == "建设规模" || key == "拟建设规模" || key == "具体建设内容" || key == "建设规模地上" || key == "建设规模地下"){
			imgName = "jianzhu.png";
		}else if(key == "许可证编号" || key == "建设工程规划许可证编号"){
			imgName = "leibie.png";
		}else if(key == "用地面积" || key == "拟用地面积(含各地类明细)"){
			imgName = "mianji.png";
		}else if(key == "土地用途" || key == "项目建设依据"){
			imgName = "yongtu.png";
		}else if(key == "土地取得方式"){
			imgName = "zu41.png";
		}else if(key == "建设单位名称" || key == "竣工实测成果编制单位"){
			imgName = "zu42.png";
		}
		tr ="<tr style='width:100%; border-bottom: 1px solid #E2E2E2;'>" 
			+"<td style='color:#3296FA;width:45%;font-size:11pt;padding:5px;'>" 
			+"<img alt='' src='"+contextPath+"/img/"+imgName+"' style='width:11pt;height:11pt;padding-right:5px;'>"
		    +"<div style='width:80%;display:inline-block;text-align:justify;text-align-last:justify;'>"+key+"：</div></td>"
			+"<td style='width:55%;font-size:10pt;padding:2px;color:#000000;'>" +data[key]+"</td>" 
			+"</tr>";
		if(key == "拟用地面积(含各地类明细)"){
			tr ="<tr style='width:100%; border-bottom: 1px solid #E2E2E2;'>" 
			+"<td style='color:#3296FA;width:45%;font-size:11pt;padding:5px;'>" 
			+"<img alt='' src='"+contextPath+"/img/"+imgName+"' style='width:11pt;height:11pt;padding-right:5px;'>"
		    +"<div style='width:70%;display:inline-block;text-align:justify;text-align-last:justify;'>拟用地面积</div>"
			 +"<div style='width:90%;display:inline-block;text-align:justify;text-align-last:justify;'>(含各地类明细)：</div></td>"
			+"<td style='width:55%;font-size:10pt;padding:2px;color:#000000;'>" +data[key]+"</td>" 
			+"</tr>";
		} else if(key == "状态"){
			tr = '';
		} else if(key == "用地面积"){
			zydmj = data[key];
			tr = '';
		}else if(key == "净用地面积"){
			jydmj = data[key];
			tr = '';
		}else if(key == "代征道路面积"){
			dzdlmj = data[key];
			tr = '';
		}else if(key == "代征绿地面积"){
			dzldmj = data[key];
			tr = '';
		} else if(key == "公开地址"){
			publicpath = data[key];
			tr = '';
		} else if(key == "建设工程规划许可证编号"){
			tr ="<tr style='width:100%; border-bottom: 1px solid #E2E2E2;'>" 
			+"<td style='color:#3296FA;width:45%;font-size:11pt;padding:5px;'>" 
			+"<img alt='' src='"+contextPath+"/img/"+imgName+"' style='width:11pt;height:11pt;padding-right:5px;'>"
		    +"<div style='width:70%;display:inline-block;text-align:justify;text-align-last:justify;'>建设工程规划</div>"
			 +"<div style='width:80%;display:inline-block;margin-left: 19px;text-align:justify;text-align-last:justify;'>许可证编号：</div></td>"
			+"<td style='width:55%;font-size:10pt;padding:2px;color:#000000;'>" +data[key]+"</td>" 
			+"</tr>";
		}else if(key == "竣工实测成果编制单位"){
			tr ="<tr style='width:100%; border-bottom: 1px solid #E2E2E2;'>" 
			+"<td style='color:#3296FA;width:45%;font-size:11pt;padding:5px;'>" 
			+"<img alt='' src='"+contextPath+"/img/"+imgName+"' style='width:11pt;height:11pt;padding-right:5px;'>"
		    +"<div style='width:70%;display:inline-block;text-align:justify;text-align-last:justify;'>竣工实测成果</div>"
			 +"<div style='width:80%;display:inline-block;margin-left: 19px;text-align:justify;text-align-last:justify;'>编制单位：</div></td>"
			+"<td style='width:55%;font-size:10pt;padding:2px;color:#000000;'>" +data[key]+"</td>" 
			+"</tr>";
		}
		$("#showdetail").append(tr);
	});
	// 用地面积逻辑拼接
	if(jydmj && !dzdlmj && !dzldmj){
		mjcontent = "总用地面积"+zydmj+"亩(其中:净用地"+jydmj+"亩)";
	}else if(!jydmj && dzdlmj && !dzldmj){
		mjcontent = "总用地面积"+zydmj+"亩(其中:代征道路面积"+dzdlmj+"亩)";
	}else if(!jydmj && !dzdlmj && dzldmj){
		mjcontent = "总用地面积"+zydmj+"亩(其中:代征绿地面积"+dzldmj+"亩)";
	}else if(jydmj && dzdlmj && !dzldmj){
		mjcontent = "总用地面积"+zydmj+"亩(其中:净用地"+jydmj+"亩、代征道路面积"+dzdlmj+"亩)";
	}else if(jydmj && !dzdlmj && dzldmj){
		mjcontent = "总用地面积"+zydmj+"亩(其中:净用地"+jydmj+"亩、代征绿地面积"+dzldmj+"亩)";
	}else if(!jydmj && dzdlmj && dzldmj){
		mjcontent = "总用地面积"+zydmj+"亩(其中:代征道路面积"+dzdlmj+"亩、代征绿地面积"+dzldmj+"亩)";
	}else if(jydmj && dzdlmj && dzldmj){
		mjcontent = "总用地面积"+zydmj+"亩(其中:净用地"+jydmj+"亩、代征道路面积"+dzdlmj+"亩、代征绿地面积"+dzldmj+"亩)";
	}
	var ydmjtr ="<tr style='width:100%; border-bottom: 1px solid #E2E2E2;'>" 
		+"<td style='color:#3296FA;width:45%;font-size:11pt;padding:5px;'>" 
		+"<img alt='' src='"+contextPath+"/img/mianji.png' style='width:11pt;height:11pt;padding-right:5px;'>"
	    +"<div style='width:80%;display:inline-block;text-align:justify;text-align-last:justify;'>用地面积：</div></td>"
		+"<td style='width:55%;font-size:10pt;padding:2px;color:#000000;'>" +mjcontent+"</td>" 
		+"</tr>";
	
	if(publicpath && match.test(publicpath)){
		ahref = publicpath;
	} else {
		ahref = 'http://zygh.xa.gov.cn/ywpd/cxghgsgb/ghglphgg/1.html';
	}
	var srctr = "<tr>"
				+"<td colspan='2' style='text-align:center;'>"
				+"<a href="+ahref+" style='color:#3296FA;font-size:12pt;'>查看项目公示信息</a></td>"
				+"<tr>";
	if(cilName == "建设用地规划许可证"){
		$("#showdetail").append(ydmjtr);
	} else if(cilName == "建设工程规划条件核实"){
		var attachpath = findPathById(pid);
		var target= attachpath+"/竣工规划实测成果纸质扫描件";
		var file = getMaterPathByPath(target);
		ftfjmc="<tr style='width:100%; border-bottom: 1px solid #E2E2E2;'>" 
			+"<td style='color:#3296FA;width:45%;font-size:11pt;padding:5px;'>"
			+"<img alt='' src='"+contextPath+"/img/lujing176.png' style='width:12pt;height:12pt;padding-right:5px;'>附件：</td>"
			+"<td style='width:55%;font-size:10pt;padding:2px;color:#000000;'>&nbsp;</td>" 
			+"</tr>"
			+"<tr style='width:100%;'>" 
			+"<td style='width:100%;font-size:12pt;padding:5px;color:#060606;'  colspan='2'>" 
			+"<a href=http://xagh.org.cn:3799/view/url?url=http://10.6.144.133:8083/file"+file.fileInfolist[0].path+" style='color:#3296FA;font-size:12pt;'>建设项目竣工实测成果</a></td>"
			+"</tr>";
	}
	$("#showdetail").append(ftfjmc);
	if(cilName != "建设工程规划条件核实"){
		$("#showdetail").append(srctr);
	}
}

function sortData(data){ // 对数据进行二次封装排序
	var result = {};
	if(cilName == "建设用地规划许可证"){
		result["状态"] = data["状态"];
		result["许可证编号"] = data["许可证编号"];
		result["证本流水编号"] = data["证本流水编号"]? data["证本流水编号"] : "";
		result["发证日期"] = data["发证日期"];
		result["用地单位"] = data["用地单位"];
		result["项目名称"] = data["项目名称"];
		result["批准用地机关"] = data["批准用地机关"];
		result["批准用地文号"] = data["批准用地文号"];
		result["用地位置"] = data["用地位置"];
		result["用地面积"] = data["用地面积"];
		result["净用地面积"] = data["净用地面积"];
		result["代征道路面积"] = data["代征道路面积"];
		result["代征绿地面积"] = data["代征绿地面积"];
		result["土地用途"] = data["土地用途"];
		result["建设规模"] = data["建设规模"];
		result["具体建设内容"] = data["具体建设内容"];
		result["土地取得方式"] = data["土地取得方式"];
		result["附图及附件名称"] = data["附图及附件名称"];
		result["公开地址"] = data["公开地址"];
	} else if(cilName == "建设项目用地预审与选址意见书"){
		result["状态"] = data["状态"];
		result["许可证编号"] = data["许可证编号"];
		result["证本流水编号"] = data["证本流水编号"]? data["证本流水编号"] : "";
		result["发证日期"] = data["发证日期"];
		result["项目名称"] = data["项目名称"];
		result["项目代码"] = data["项目代码"];
		result["建设单位名称"] = data["建设单位名称"];
		result["项目建设依据"] = data["项目建设依据"];
		result["项目拟选位置"] = data["项目拟选位置"];
		result["拟用地面积(含各地类明细)"] = data["拟用地面积(含各地类明细)"];
		result["拟建设规模"] = data["拟建设规模"];
		result["附图及附件名称"] = data["附图及附件名称"];
		result["公开地址"] = data["公开地址"];
	} else if(cilName == "建设工程规划许可证" || cilName == "乡村规划许可证"){
		result["状态"] = data["状态"];
		result["许可证编号"] = data["许可证编号"];
		result["证本流水编号"] = data["证本流水编号"]? data["证本流水编号"] : "";
		result["发证日期"] = data["发证日期"];
		result["建设单位（个人）"] = data["建设单位（个人）"];
		result["建设项目名称"] = data["建设项目名称"];
		result["建设位置"] = data["建设位置"];
		result["建设规模"] = data["建设规模"];
		result["具体建设内容"] = data["具体建设内容"];
		result["附图及附件名称"] = data["附图及附件名称"];
		result["公开地址"] = data["公开地址"];
	}else if(cilName == "建设工程规划条件核实"){
		result["状态"] = data["状态"];
		result["意见书编号"] = data["意见书编号"];
		result["证本流水编号"] = data["证本流水编号"]? data["证本流水编号"] : "";
		result["发证日期"] = data["发证日期"];
		result["建设单位"] = data["建设单位"];
		result["建设项目名称"] = data["建设项目名称"];
		result["项目编号"] = data["项目编号"];
		result["建设项目位置"] = data["建设项目位置"];
		result["建设规模地上"] = data["建设规模地上"];
		result["建设规模地下"] = data["建设规模地下"];
		result["建设工程规划许可证编号"] = data["建设工程规划许可证编号"];
		result["竣工实测成果编制单位"] = data["竣工实测成果编制单位"];
		result["实测时间"] = data["实测时间"];
	}
	return result;
}