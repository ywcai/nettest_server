var manager, g;	
function pullPlotList()
{
	lsAjaxPost("../../restful/plot/pull",null,"获取小区列表");
	$.ligerDialog.waitting("正在处理数据");
}
function pullOltList(start,end)
{
	var data={"startNum":start,"endNum":end};
	lsAjaxPost("../../restful/olt/pull",data,"获取并同步OLT信息","application/json");
	$.ligerDialog.waitting("正在处理数据第 "+start+"条至第 "+end+"条的数据");
}

function pullOnuList(start,end)
{
	var data={"startNum":start,"endNum":end};
	lsAjaxPost("../../restful/onu/pull",data,"获取并同步OLT下的ONU信息","application/json");
	$.ligerDialog.waitting("正在处理数据第 "+start+"条至第 "+end+"条的数据");
}

function updateOltBaseData()
{
	lsAjaxPost("../../restful/oltbase/pull",null,"获取并同步OLT基础信息","application/json");
	$.ligerDialog.waitting("正在同步OLT基础信息");
}
function updateOltAndOnuData()
{
	lsAjaxPost("../../restful/nulldataolt/pull",null,"获取并同步OLT基础信息","application/json");
	$.ligerDialog.waitting("同步比对无数据的光分器ONU基础信息");
}





function refreshUi(data)
{
	$.ligerDialog.waitting(data);
	setTimeout(function() {
		$.ligerDialog.closeWaitting();
	}, 1000);
	$("#result").text(data);

}

function lsAjaxPost(postUrl,data,tip)
{
	lsAjaxPost(postUrl,data,tip,"x-www-form-urlencode");
}

function lsAjaxPost(postUrl,data,tip,contextType) {
	$.ajax({
		//提交数据的类型 POST GET
		type : "POST",
		//提交的网址
		contentType : contextType,
		url : postUrl,
		//提交的数据
		data : JSON.stringify(data),
		//返回数据的格式
		datatype : "text",//"xml", "html", "script", "json", "jsonp", "text".
		//成功返回之后调用的函数             
		success : function(result) {
			refreshUi("共写入"+result+"条数据");
//			manager.loadData();
		},
		//调用出错执行的函数
		error : function() {
			$.ligerDialog.waitting(tip+'失败:网络请求错误!');
			setTimeout(function() {
				$.ligerDialog.closeWaitting();
			}, 1000);
//			manager.loadData();
		}
	});
}