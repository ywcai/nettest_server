package ywcai.ls.bean;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {
	@Bean(name = "jsonData")
	@Qualifier(value = "jsonData")
	public JsonData jsonData()
	{
		return new JsonData();
	}
	@Bean(name = "chartData")
	@Qualifier(value = "chartData")
	public ChartData chartData()
	{
		return new ChartData();
	}
//	
//	
}
