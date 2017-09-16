package ywcai.ls.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ywcai.ls.bean.JsonData;
import ywcai.ls.bean.RoleBody;
import ywcai.ls.bean.UserInfo;
import ywcai.ls.entity.Orders;
import ywcai.ls.entity.Product;
import ywcai.ls.entity.Roles;
import ywcai.ls.entity.User;
import ywcai.ls.service.CustomUserService;
import ywcai.ls.service.inf.AccountProcessInf;
import ywcai.ls.service.inf.OrderProcessInf;
import ywcai.ls.service.inf.ProductProcessInf;

@Controller
public class CoreController {

	@Autowired
	private JsonData jsonData;
	@Autowired
	private ProductProcessInf productProcess;
	@Autowired
	private AccountProcessInf accountProcess;
	@Autowired
	private OrderProcessInf orderProcess;
	@Autowired
	private CustomUserService customUserService;



	@RequestMapping(value="/login",method=RequestMethod.POST)
	String loginAction(String username,String password) {
		customUserService.loadUserByUsername(username);
		return "login";
	}


	@RequestMapping(value="/login",method=RequestMethod.GET)
	String loginForm() {
		return "login";
	}

	/*
	 * 首页框架，基础用户登录权限
	 * 
	 * */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	@RolesAllowed("USER") 
	String manage(Model model,HttpSession session) {

		String account_name=((SecurityContextImpl)(session.getAttribute("SPRING_SECURITY_CONTEXT"))).getAuthentication().getName();	
//		System.out.println(userDetails.getUsername()+"\n\n");
		model.addAttribute("accountname", account_name);
		return "index";
	}

	/*
	 * 欢迎，基础用户登录权限
	 * 
	 * */
	@RequestMapping(value="/base/welcome",method=RequestMethod.GET)
	@RolesAllowed("USER") 
	String welcome(Model model) {

		return "welcome";
	}

	/*
	 * 错误页面
	 * 
	 * */
	@RequestMapping(value="/error",method=RequestMethod.GET)
	@RolesAllowed("USER") 
	String err(Model model) {

		return "error";
	}


	/*
	 * 读产品权限
	 * 
	 * */
	@RequestMapping(value="/product/r/list",method=RequestMethod.GET)
	@RolesAllowed({"ROLE_PRODUCT_R","ROLE_PRODUCT_RW"})
	String prl() {
		return "service/productList";
	}

	/*
	 * 写产品权限
	 * 
	 * */
	@RequestMapping(value="/product/w/list",method=RequestMethod.GET)
	@RolesAllowed("ROLE_PRODUCT_RW")
	String pwl() {
		return "service/productManageList";
	}

	/*
	 * 写产品权限，添加产品表单
	 * 
	 * */
	@RequestMapping(value="/product/w/form",method=RequestMethod.GET)
	@RolesAllowed("ROLE_PRODUCT_RW")
	String pwf(Model model) {
		return "service/productAddForm";
	}

	/*
	 * 读订单权限
	 * 
	 * */
	@RequestMapping(value="/order/r/list",method=RequestMethod.GET)
	@RolesAllowed({"ROLE_ORDER_R","ROLE_ORDER_RW"})
	String orl(Model model) {

		return "service/orderList";
	}

	/*
	 * 写订单权限
	 * 
	 * */
	@RequestMapping("/order/w/list")
	@RolesAllowed({"ROLE_ORDER_RW"})
	String owl(Model model) {

		return "service/orderManageList";
	}


	/*
	 * 超级管理员权限，操作所有用户权限
	 * 
	 * */
	@RequestMapping("/account/s/role")
	@RolesAllowed("ROLE_ACCOUNT_RW")
	String asl(Model model) {

		return "account/modity_role";
	}


	/*
	 * 超级管理员权限，操作所有用户权限
	 * 
	 * */
	@RequestMapping("/account/s/update")
	@RolesAllowed("ROLE_ACCOUNT_RW")
	String asp(Model model) {

		return "account/update_view";
	}

	//以下是restful接口路由
	/*
	 * 产品读取权限或订单写权限均可访问
	 * 
	 * */
	@RequestMapping(value="/restful/products/read",method=RequestMethod.GET)
	@RolesAllowed({"ROLE_PRODUCT_R","ROLE_PRODUCT_RW"})
	@ResponseBody
	public JsonData getProducts(@PageableDefault(size = 20,page=0,sort = { "productid" }, direction = Sort.Direction.ASC) Pageable pageable
			,@RequestParam(value="sort", defaultValue = "productid") String sort,
			@RequestParam(value="sortorder",defaultValue = "asc") String sortorder)
	{
		Sort.Direction dire=sortorder.equals("desc")?Direction.DESC:Direction.ASC;
		Sort dsSort=new Sort(dire,sort);
		Page<Product> products=productProcess.getAllProduct(pageable,dsSort);
		jsonData.Rows=products.getContent();
		jsonData.Total=(int) products.getTotalElements();
		return jsonData;
	}


	/*
	 * 产品写权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/product/update",method=RequestMethod.POST)
	@RolesAllowed("ROLE_PRODUCT_RW")
	@ResponseBody
	public String updateProduct(@RequestBody Product product)
	{
		String result=productProcess.updateProductInfo(product);
		return result;
	}

	/*
	 * 产品写权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/product/del",method=RequestMethod.POST)
	@RolesAllowed("ROLE_PRODUCT_RW")
	@ResponseBody
	public String delProduct(@RequestBody int[] uids)
	{
		System.out.println(uids[0]);
		String result=productProcess.delProducts(uids);
		return result;
	}

	/*
	 * 产品写权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/product/add",method=RequestMethod.POST)
	@RolesAllowed({"ROLE_PRODUCT_RW"})
	@ResponseBody
	public String createNewProduct(Product newProduct)
	{
		String result=productProcess.createProduct(newProduct);
		return result;
	}
	
	
	
	/*
	 * 订单读权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/order/read",method=RequestMethod.GET)
	@RolesAllowed({"ROLE_ORDER_R","ROLE_ORDER_RW"})
	@ResponseBody
	public JsonData getOrder(@PageableDefault(size = 20,page=0,sort = { "ordernum" }, direction = Sort.Direction.DESC) Pageable pageable
			,@RequestParam(value="sort", defaultValue = "ordernum") String sort,
			@RequestParam(value="sortorder",defaultValue = "desc") String sortorder)
	{

		Sort.Direction dire=sortorder.equals("desc")?Direction.DESC:Direction.ASC;
		Sort dsSort=new Sort(dire,sort);
		Page<Orders> orders=orderProcess.getAllOrder(pageable,dsSort);
		jsonData.Rows=orders.getContent();
		jsonData.Total=(int) orders.getTotalElements();
		return jsonData;
	}

	
	
	/*
	 * 订单写权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/order/del",method=RequestMethod.POST)
	@RolesAllowed("ROLE_ORDER_RW")
	@ResponseBody
	public String delOrder(@RequestBody int[] uids)
	{
		String result=orderProcess.delOrder(uids);
		return result;
	}

	
	

	/*
	 * 超级管理权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/account/read",method=RequestMethod.POST)
	@RolesAllowed("ROLE_ACCOUNT_RW")
	@ResponseBody
	public JsonData readAccounts(@PageableDefault(size = 20,page=0,sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable) {
		Page<User> users=accountProcess.getAllUser(pageable);
		List<UserInfo> list=new ArrayList<UserInfo>();
		for (User user : users.getContent()) {
			UserInfo userInfo=new UserInfo();
			userInfo.id=user.getId();
			userInfo.username=user.getUsername();
			for (Roles roles : user.getRolelist()) {
				userInfo.roles.add(roles.getRolename());
			}
			list.add(userInfo);
		}
		jsonData.Rows=list;
		jsonData.Total=(int) users.getTotalElements();
		return jsonData;
	}

	/*
	 * 超级管理权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/account/add",method=RequestMethod.POST)
	@RolesAllowed("ROLE_ACCOUNT_RW")
	@ResponseBody
	String addAccountr(@RequestBody User user) {
		System.out.println(user.getUsername()+"|"+user.getPassword()+"|");
		String result=accountProcess.addUser(user, "USER");
		return result;
	}



	/*
	 * 超级管理权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/account/modity_psw",method=RequestMethod.POST)
	@RolesAllowed("ROLE_ACCOUNT_RW")
	@ResponseBody
	String modityPsw(@RequestBody User user) {
		System.out.println(user.getUsername()+"|"+user.getPassword());
		if(user.getUsername().equals("Admin"))
		{
			return "FAIL:不能对内置Admin账号进行任何操作";
		}
		String result=accountProcess.updateUser(user);
		return result;
	}


	/*
	 * 超级管理权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/roles/set",method=RequestMethod.POST)
	@RolesAllowed("ROLE_ACCOUNT_RW")
	@ResponseBody
	String setRole(@RequestBody RoleBody roleBody) {
		//		System.out.println(user.getUsername());
		//		if(user.getUsername().equals(""))
		//		{
		//			return "FAIL:用户不能为空";	
		//		}
		if(roleBody.add==null&&roleBody.del==null)
		{
			return "FAIL:客户端未提交更新内容";	
		}
		if(roleBody.add.length==0&&roleBody.del.length==0)
		{
			return "FAIL:客户端未提交更新内容!";	
		}
		if(roleBody.account.equals("Admin"))
		{
			return "FAIL:不能对内置Admin账号进行任何操作";
		}
		String result=accountProcess.editRole(roleBody);
		return result;
	}

	/*
	 * 超级管理权限可访问
	 * 
	 * */
	@RequestMapping(value="/restful/account/del",method=RequestMethod.POST)
	@RolesAllowed("ROLE_ACCOUNT_RW")
	@ResponseBody
	String delAccountr(@RequestBody User user) {
		if(user.getUsername().equals(""))
		{
			return "FAIL:用户不能为空";	
		}
		if(user.getUsername().equals("Admin"))
		{
			return "FAIL:不能对内置Admin账号进行任何操作";
		}
		String result=accountProcess.delUser(user);
		return result;
	}

}
