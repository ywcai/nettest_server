package ywcai.ls.controller;




import javax.ws.rs.FormParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ywcai.ls.bean.JsonProduct;
import ywcai.ls.entity.Product;
import ywcai.ls.entity.User;
import ywcai.ls.service.CustomUserService;
import ywcai.ls.service.inf.AccountProcessInf;
import ywcai.ls.service.inf.ProductProcessInf;

@Controller
public class CoreController {
	@Autowired
	private JsonProduct jsonProduct;
	@Autowired
	private ProductProcessInf productProcess;
	@Autowired
	private AccountProcessInf accountProcess;
	@Autowired
	private CustomUserService customUserService;

	@RequestMapping("/index")
	@Secured("ROLE_USER")
	String manage(Model model) {
		//		String result=loginValidate.getAuthorByName("luosheng4");
		//		model.addAttribute("username", result);	
		return "index";
	}


	@RequestMapping(value="/manage/productList",method=RequestMethod.GET)
	@Secured("ROLE_USER")
	String productHtml() {
		return "manage/productList";
	}

	@RequestMapping("/manage/orderList")
	@Secured("ROLE_USER")
	String orders(Model model) {

		return "manage/orderList";
	}
	@RequestMapping("/manage/home")
	@Secured("ROLE_USER")
	String welcome(Model model) {

		return "manage/home";
	}

	@RequestMapping("/manage/addProductForm")
	@Secured("ROLE_USER")
	String popAddProductForm(Model model) {
		return "manage/addProductForm";
	}





	@RequestMapping(value="/restful/products/read",method=RequestMethod.GET)
	@Secured("ROLE_USER")
	@ResponseBody
	public JsonProduct getProducts(@PageableDefault(size = 20,page=0,sort = { "productid" }, direction = Sort.Direction.ASC) Pageable pageable
			,@RequestParam(value="sort", defaultValue = "productid") String sort,
			@RequestParam(value="sortorder",defaultValue = "asc") String sortorder)
	{
		Sort.Direction dire=sortorder.equals("desc")?Direction.DESC:Direction.ASC;
		Sort dsSort=new Sort(dire,sort);
		Page<Product> products=productProcess.getAllProduct(pageable,dsSort);
		jsonProduct=new JsonProduct();
		jsonProduct.Rows=products.getContent();
		jsonProduct.Total=(int) products.getTotalElements();
		return jsonProduct;
	}

	@RequestMapping(value="/restful/product/update",method=RequestMethod.POST)
	@Secured("ROLE_USER")
	@ResponseBody
	public String updateProduct(@RequestBody Product product)
	{
		String result=productProcess.updateProductInfo(product);
		return result;
	}

	@RequestMapping(value="/restful/product/del",method=RequestMethod.POST)
	@Secured("ROLE_USER")
	@ResponseBody
	public String delProduct(@RequestBody int[] uids)
	{
		System.out.println(uids[0]);
		String result=productProcess.delProducts(uids);
		return result;
	}
	@RequestMapping(value="/restful/product/add",method=RequestMethod.POST)
	@Secured("ROLE_USER")
	@ResponseBody
	public String createNewProduct(Product newProduct)
	{
		System.out.println(newProduct.toString());
		String result=productProcess.createProduct(newProduct);
		return result;
	}
	
	@RequestMapping(value="/restful/manager/add",method=RequestMethod.POST)
	//@Secured("ROLE_ADMIN")
	@ResponseBody
	String addManager(User user) {
		System.out.println("test");
		System.out.println(user.getUsername()+"|"+user.getPassword()+"|");
		String result=accountProcess.addUser(user.getUsername(), user.getPassword(), "USER");
		return result;
	}
	
	

	@RequestMapping("/login")
	String login(String username,String password) {
		customUserService.loadUserByUsername(username);
		return "login";
	}

	
}
