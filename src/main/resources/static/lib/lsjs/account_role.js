var manager, g;	
var mdialog;
//点开窗后该用户的初始权限值，用于比对修改后差异
var list_data;
var accountName;
function f_initGrid() {
	g = manager = $("#maingrid").ligerGrid(
			{
				title : '管理账号列表',
				columns : [
				           {
				        	   width:"5%",
				        	   display : '账户ID',
				        	   name : 'id',
				        	   type : 'int',
				        	   isSort : false
				           },
				           {
				        	   width:"20%",
				        	   display : '账号名称',
				        	   name : 'username',
				        	   type : 'text',
				        	   isSort : false
				           },
				           {
				        	   width:"65%",
				        	   display : '权限列表',
				        	   name : 'roles',
				        	   type : 'text',
				        	   isSort : false
				           },
				           {
				        	   width:"10%",
				        	   display : '修改权限',
				        	   isSort : false,
				        	   render : function(rowdata, rowindex, value) {
				        		   var h = "<a href='javascript:setRoles(" + rowindex + ")'>修改</a>";
				        		   return h;
				        	   } 

				           }
				           ],
				           onSelectRow : function(rowdata, rowindex) {
				        	   $("#txtrowindex").val(rowindex);
				           },
				           url : "../../restful/account/read",
				           method : "POST",
				           enabledEdit : false,
				           clickToEdit : false,
				           isScroll : false,
				           rownumbers : false,
				           width: "99%",
				           height: 'auto', 
				           resizable:false,
				           checkbox : false,
				           hideLoadButton:true,
				           frozen: false,
				           rowHeight: 25,                        
				           headerRowHeight: 25,
				           pageSize: 20
			});
	mdialog = $.ligerDialog.open({
		target: $("#editform"),
		height : 400,
//		url : '/account/s/form',
		width : 500,
		showMax : false,
		showToggle : false,
		show: false, 
		showMin : false,
		isResize : false,
		isHidden:true,
		modal : true,
		title : "修改权限",
		buttons : [ {
			text : '确认提交',
			onclick : function(item, dialog) {
				sub();		
				dialog.hidden();
			}
		} ]
	});
	$("#listbox1,#listbox2").ligerListBox({
		isShowCheckBox : false,
		isMultiSelect : false,
		height : 200
	});
	mdialog.hidden();
}

//打开编辑权限窗口
function setRoles(rowid) {
	accountName=manager.getData()[rowid].username;
	mdialog.show();
	loadEditForm(rowid);
}


//加载权限编辑窗口初始数据
function loadEditForm(rowid)
{

	var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
	$(box1.data).each(function(i,d){
		box1.removeItems(d);
	});
	$(box2.data).each(function(i,d){
		box2.removeItems(d);
	});
	list_data=new Array();
	var role_l =new Array();
	var role_r =new Array();
	var now_roles=manager.getData()[rowid].roles;
	var allRoles=["ACCOUNT_RW","PRODUCT_R","PRODUCT_RW","ORDER_R","ORDER_RW"];
	$(allRoles).each(function(i,d){
		var flag=false;
		$(now_roles).each(function(ii,dd){
			if(d == dd )
			{
				flag=true;
			}});
		var obj=new Object();
		obj.text=d;
		obj.id=i+"1";
		if(!flag)
		{
			role_r.push(obj);
		}
		else
		{
			role_l.push(obj);
			list_data.push(obj);
		}
	});
	box1.setData(role_l);
	box2.setData(role_r);
}

function remove() {
	var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
	var selecteds = box1.getSelectedItems();
	if (!selecteds || !selecteds.length)
		return;
	box1.removeItems(selecteds);
	box2.addItems(selecteds);

}
function add() {
	var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
	var selecteds = box2.getSelectedItems();
	if (!selecteds || !selecteds.length)
		return;
	box2.removeItems(selecteds);
	box1.addItems(selecteds);
}

function sub() {
	var delData=new Array();
	var addData=new Array();
	var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
	$(box1.data).each(function(i, d) {
		var flag = false;
		$(list_data).each(function(ii, dd) {
//			alert("add: i="+i+" ii="+ii+" | "+"d="+d.text+" dd="+dd.text);
			if (d.text == dd.text) {
				flag = true;
			}
		});
		if (!flag) {
			addData.push(d.text);
		}
	});

	$(list_data).each(function(i, d) {
		var flag = false;
		$(box1.data).each(function(ii, dd) {
//			alert("del: i="+i+" ii="+ii+" | "+"d="+d.text+" dd="+dd.text);
			if (d.text == dd.text) {
				flag = true;
			}
		});
		if (!flag) {
			delData.push(d.text);
		}
	});
	updateRoles(accountName,addData,delData);
}





function updateRoles(accountName,addData,delData) {
	if(addData==null&&delData==null)
	{
		$.ligerDialog.waitting("你没有进行任何修改");
		setTimeout(function() {
			$.ligerDialog.closeWaitting();
		}, 1500);
		return ;
	}
	if(addData.length==0&&delData.length==0)
	{
		$.ligerDialog.waitting("你没有进行任何修改");
		setTimeout(function() {
			$.ligerDialog.closeWaitting();
		}, 1500);
		return ;
	}

	var object=new Object();
	object.account=accountName;
	object.add=addData;
	object.del=delData;
	lsAjaxPost2("../../restful/roles/set"
			,"application/json"
			,JSON.stringify(object)
			,"变更权限");
}

function lsAjaxPost2(postUrl,contentType,data,tip) {
	$.ajax({
		//提交数据的类型 POST GET
		type : "POST",
		//提交的网址
		contentType : contentType,
		url : postUrl,
		//提交的数据
		data : data,
		//返回数据的格式
		datatype : "text",//"xml", "html", "script", "json", "jsonp", "text".
		//成功返回之后调用的函数             
		success : function(result) {
			if (result == "SUCCESS") {
				$.ligerDialog.waitting(tip+'成功');
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
				}, 1000);

			} else {
				$.ligerDialog.waitting(tip+'失败:' + result);
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
				}, 1000);
			}
			manager.loadData();
		},
		//调用出错执行的函数
		error : function() {
			$.ligerDialog.waitting(tip+'失败:网络请求错误!');
			setTimeout(function() {
				$.ligerDialog.closeWaitting();
			}, 1000);
			manager.loadData();
		}
	});
}