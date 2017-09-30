package ywcai.ls.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import ywcai.ls.bean.ChartData;
import ywcai.ls.log.MyLog;
import ywcai.ls.repository.OrderRepository;
import ywcai.ls.service.inf.AnalysisOrderInf;


@Service
@Qualifier(value="analysisOrder")
public class AnalysisOrder implements AnalysisOrderInf {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public ChartData getDatasets(String  analysisType) {
		// TODO Auto-generated method stub
		
		if(analysisType.equals("productId"))
		{			
			return findGroupByProductid();
		}
		if(analysisType.equals("orderStatus"))
		{
			
			return findGroupByOrderStatus();
		}
		if(analysisType.equals("dayCounts"))
		{
			return findOrderCountByDay();
		}
		ChartData chartData=new ChartData();
		chartData.resultCode="Fail";
		return chartData;
	}
	

	public ChartData findGroupByProductid( ) {
		// TODO Auto-generated method stub
		ChartData chartData=new ChartData();
		chartData.resultCode="SUCCESS";
		chartData.datas=new ArrayList<int[]>();
		chartData.label=new String[]{"订购产品数量"};
		List<Object> list=new ArrayList<Object>();
		try {

	         list=orderRepository.findGroupByProductid();
		}
		catch (Exception e) {
			// TODO: handle exception
			MyLog.WARN(e.toString());
		}
		chartData.labels=new String[list.size()];
		int[] data=new int[list.size()];
		int index=0;
		for (Object row : list) {
			Object[] cells=(Object[])row;
			chartData.labels[index]=cells[0].toString();
			data[index]=Integer.parseInt(cells[1].toString());
			index++;
		}
		chartData.datas.add(data);
		return chartData;
	}


	public ChartData findGroupByOrderStatus( ) {
		// TODO Auto-generated method stub
		ChartData chartData=new ChartData();
		chartData.resultCode="SUCCESS";
		chartData.datas=new ArrayList<int[]>();
		List<Object> list=new ArrayList<Object>();
		try {
	         list=orderRepository.findGroupByOrderStatus();
		}
		catch (Exception e) {
			// TODO: handle exception
			MyLog.WARN(e.toString());
		}
		chartData.labels=new String[list.size()];
		int[] data=new int[list.size()];
		int index=0;
		for (Object row : list) {
			Object[] cells=(Object[])row;
			String temp=
				Integer.parseInt(cells[0].toString())==0?"未支付":
				Integer.parseInt(cells[0].toString())==1?"正在充值":
				Integer.parseInt(cells[0].toString())==2?"充值成功":
				Integer.parseInt(cells[0].toString())==3?"提交充值失败-第三方平台异常":
				Integer.parseInt(cells[0].toString())==4?"充值失败-退款失败":
				Integer.parseInt(cells[0].toString())==5?"充值失败-退款成功":"未知错误订单";
			chartData.labels[index]=temp;
			data[index]=Integer.parseInt(cells[1].toString());
			index++;
		}
		chartData.datas.add(data);
		chartData.label=new String[]{"按订单完成状态统计"};
		return chartData;
	}


//	public ChartData findGroupByIsLocal( ) {
//		// TODO Auto-generated method stub
//		ChartData chartData=new ChartData();
//		chartData.resultCode="SUCCESS";
//		chartData.labels=new String[]{"Operator","Operator2","Operator3","Operator4"};
//		chartData.datas=new ArrayList<int[]>();
//		int[] data=new int[]{5,10,15,15};
//		int[] data2=new int[]{15,30,45,60};
//		chartData.datas.add(data);
//		chartData.datas.add(data2);
//		chartData.label=new String[]{"本地套餐","全国套餐"};
//		return chartData;
//	}


	private ChartData findOrderCountByDay() {
		// TODO Auto-generated method stub
		ChartData chartData=new ChartData();
		chartData.resultCode="SUCCESS";
		chartData.datas=new ArrayList<int[]>();
		//首先构建日历标签;
		chartData.labels=getLabelsByDay();
		//首先数据集标签;
		chartData.label=new String[]{"所有",	"未支付","正在充值","充值成功","提交充值失败-第三方平台异常","充值失败-退款失败","充值失败-退款成功"};
		int[] data_all=getDataByDay(chartData.labels,-1);
		int[] data_0=getDataByDay(chartData.labels,0);
		int[] data_1=getDataByDay(chartData.labels,1);
		int[] data_2=getDataByDay(chartData.labels,2);
		int[] data_3=getDataByDay(chartData.labels,3);
		int[] data_4=getDataByDay(chartData.labels,4);
		int[] data_5=getDataByDay(chartData.labels,5);
		chartData.datas.add(data_all);
		chartData.datas.add(data_0);
		chartData.datas.add(data_1);
		chartData.datas.add(data_2);
		chartData.datas.add(data_3);
		chartData.datas.add(data_4);
		chartData.datas.add(data_5);
		return chartData;
	}
	private String[] getLabelsByDay() {
		String strMonth="";
		SimpleDateFormat month=new SimpleDateFormat("YYYYMM");
		strMonth=month.format(Calendar.getInstance().getTime());
		int intMonth=Integer.parseInt(strMonth);
	    Calendar c = Calendar.getInstance();  
        c.set(Calendar.YEAR, intMonth/100);
        c.set(Calendar.MONTH, (intMonth%100)-1); //从0索引  
        int maxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		//构建从1号到今天的标签
		String[] labels=new String[maxDay];
		for(int i=1;i<=maxDay;i++)
		{
			labels[i-1]=strMonth;
			if(i<10)
			{
				labels[i-1]+="0";
			}
			labels[i-1]+=i;
		}
		return labels;
		
	}

	private int[] getDataByDay(String[] labels,int status) {
		
		int[] data=new int[labels.length];
		List<Object> list=new ArrayList<Object>();
		try {
			switch(status)
			{
			case -1:	//所有订单数量	
	          list=orderRepository.findAllCountByDay();
	          break;
			case 0 :	    //未支付		
		      list=orderRepository.findStatus0CountByDay();
		      break;
			case 1:	    //正在充值		
			      list=orderRepository.findStatus1CountByDay();
		          break;
			case 2:	    //充值成功		
			      list=orderRepository.findStatus2CountByDay();
		          break;
			case 3:	    //失败未退款		
			      list=orderRepository.findStatus3CountByDay();
		          break;
			case 4:	    //退款失败		
			      list=orderRepository.findStatus4CountByDay();
		          break;   
			case 5 :	    //退款成功		
			      list=orderRepository.findStatus5CountByDay();
		          break;  
			}
		}
		catch (Exception e) {
			MyLog.WARN(e.toString());
		}

		for (Object row : list) {
			Object[] cells=(Object[])row;
			int counts=Integer.parseInt(cells[1].toString());
			int index=Integer.parseInt(cells[0].toString())-Integer.parseInt(labels[0]);
			data[index]=counts;
		}
		return data;
	}

}
