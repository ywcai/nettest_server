package ywcai.ls.bean;

import java.util.List;
import ywcai.ls.entity.Product;

public class JsonProduct {
	public List<Product> Rows;
	public int Total;
	@Override
	public String toString() {
		return "JsonProduct [Rows=" + Rows + ", Total=" + Total + "]";
	}
	
}
