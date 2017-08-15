package ywcai.ls.bean;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

	@Bean(name = "jsonProduct")
	@Qualifier(value = "jsonProduct")
	public JsonProduct jsonProduct()
	{
		return new JsonProduct();
	}
}
