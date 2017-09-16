var manager, g;	

function f_initGrid() {
	g = manager = $("#maingrid").ligerGrid(
			{
				//toolbar: {},
				title : '产品列表',
				columns : [
				           {
				        	   display : '产品ID',
				        	   name : 'productid',
				        	   type : 'int'
				           },
				           {
				        	   display : '产品名称',
				        	   name : 'des',
				        	   editor : {
				        		   type : 'text'
				        	   }
				           },
				           {
				        	   display : '充值编码',
				        	   name : 'packcode',
				        	   editor : {
				        		   type : 'int'
				        	   }
				           },
				           {
				        	   display : '原价(分) ',
				        	   name : 'price'
				           },
				           {
				        	   display : '折扣率(%)',
				        	   name : 'rate',
				        	   editor : {
				        		   type : 'int'
				        	   }
				           },
				           {
				        	   display : '使用范围',
				        	   name : 'local',
				        	   type : 'int',
				        	 
				        	   render : function(item) {
				        		   if (parseInt(item.local) == 1)
				        			   return '本地流量';
				        		   return '全国流量';
				        	   }
				           },
				           {
				        	   display : '运营商',
				        	   name : 'province',
				        	   editor : {
				        		   type : 'text'
				        	   }
				           },
				           {
				        	   display : '产品状态',
				        	   name : 'delflag',
				        	   type : 'int',
				        	   render : function(item) {
				        		   if (parseInt(item.delflag) == 1)
				        			   return "<span style='color:green'>上架</span>";
				        		   return '下架';
				        	   }
				           }
				           ],
				           onSelectRow : function(rowdata, rowindex) {
				        	   $("#txtrowindex").val(rowindex);
				           },
				           url : "../../restful/products/read",
				           method : "GET",
				           enabledEdit : true,
				           clickToEdit : false,
				           isScroll : false,
				           rownumbers : true,
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
}
