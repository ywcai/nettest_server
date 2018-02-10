var manager, g;	

function f_initGrid() {
	g = manager = $("#maingrid").ligerGrid(
			{
				title : '小区列表',			
				columns : [
				           {
				        	   width:'100',
				        	   display : '所属网格',
				        	   name : 'county'
				           },			    
				           {
				        	   width:'220',
				        	   display : '小区名称',
				        	   name : 'plot'
				           },
				           {
				        	   width:'95',
				        	   display : '小区ID',
				        	   name : 'id'
				           },
				           {
				        	   width:'60',
				        	   display : 'OLT数量',
				        	   name : 'oltCount'
				           },
				           {
				        	   width:'60',
				        	   display : 'ONU（子接口）数量 ',
				        	   name : 'onuCount'
				           },
				           {
				        	   width:'70',
				        	   display : '光分数量',
				        	   name : 'spliterCount',
				           }
				           ],
//				           onSelectRow : function(rowdata, rowindex) {
//				        	   $("#txtrowindex").val(rowindex);
//				           },
				           url : "../../restful/plot/query",
				           method : "POST",
				           enabledEdit : true,
				           clickToEdit : false,
				           isScroll : false,
				           rownumbers : true,
				           width: 'auto',
				           height: 'auto', 
				           resizable:false,
				           checkbox : false,
				           hideLoadButton:true,
				           frozen: false,
				           rowHeight: 'auto',                        
				           headerRowHeight: 25,
				           pageSize: 20
			});
}

