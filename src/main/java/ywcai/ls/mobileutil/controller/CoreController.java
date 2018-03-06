package ywcai.ls.mobileutil.controller;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ywcai.ls.mobileutil.bean.LogEntity;
import ywcai.ls.mobileutil.bean.LogIndex;
import ywcai.ls.mobileutil.bean.ResultState;
import ywcai.ls.mobileutil.bean.HttpBaseEntity;
import ywcai.ls.mobileutil.bean.UploadResult;
import ywcai.ls.mobileutil.entity.ArticleIndex;
import ywcai.ls.mobileutil.entity.MyRecord;
import ywcai.ls.mobileutil.entity.MyUser;
import ywcai.ls.mobileutil.entity.UserComment;
import ywcai.ls.mobileutil.service.ArticleService;
import ywcai.ls.mobileutil.service.CommentService;
import ywcai.ls.mobileutil.service.RecordService;
import ywcai.ls.mobileutil.service.UserService;


@Controller
public class CoreController {

	@Autowired
	private RecordService recordService;
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;


 
	@RequestMapping(value="/user/get/{openID}/{channelID}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<MyUser> getMyUser(@PathVariable String openID,@PathVariable int channelID)  {
		HttpBaseEntity<MyUser> baseEntity=new HttpBaseEntity<MyUser>();
		if(openID==null||channelID==0)
		{
			baseEntity.code=-1;
			baseEntity.msg="无效参数";
			return baseEntity;
		}
		if(openID.equals(""))
		{
			baseEntity.code=-1;
			baseEntity.msg="无效参数";
			return baseEntity;
		}
		MyUser myUser=userService.getUser(openID, channelID);
		if(myUser==null)
		{
			try
			{
				myUser=userService.createNewUser(openID,channelID);
			}
			catch(Exception e)
			{
				
			}
			if(myUser==null)
			{
				baseEntity.code=-2;
				baseEntity.msg="注册失败";
				return baseEntity;
			}
		}
		baseEntity.code=0;
		baseEntity.msg="success";
		baseEntity.data=myUser;
		return baseEntity;
	}


	@RequestMapping(value="/user/put/nickname/{userid}",method=RequestMethod.POST)
	@ResponseBody
	HttpBaseEntity<MyUser> postNickname(@PathVariable long userid,@RequestParam String nickname) {
		HttpBaseEntity<MyUser> baseEntity=new HttpBaseEntity<MyUser>();
		MyUser myUser=userService.getUser(userid);
		if(myUser==null||nickname==null)
		{
			baseEntity.code=-1;
			baseEntity.msg="fail:服务端校验用户信息错误";
			baseEntity.data=myUser;
		}
		else
		{
			myUser.setNickname(nickname);
			MyUser temp=userService.saveUser(myUser);
			if(temp==null)
			{
				baseEntity.code=-1;
				baseEntity.msg="fail:服务端更新失败";
				baseEntity.data=myUser;
			}
			else	
			{			
				baseEntity.code=0;
				baseEntity.msg="success";
				baseEntity.data=temp;
			}
		}
		return baseEntity;
	}

	@RequestMapping(value="/record/post/single/{userid}",method=RequestMethod.POST)
	@ResponseBody
	HttpBaseEntity<UploadResult> uploadRecord(@PathVariable long userid,@RequestBody LogEntity logEntity)
	{
		HttpBaseEntity<UploadResult> httpBaseEntity=new HttpBaseEntity<UploadResult>();
		if(userid==0)
		{

			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效用户名!";
			return httpBaseEntity;
		}
		if(logEntity==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效数据!";
			return httpBaseEntity;
		}
		if(logEntity.data==null)
		{
			logEntity.data="null";
		}
		MyRecord record=new MyRecord();
		record.saveForEntity(logEntity);
		record.setUserid(userid);
		MyRecord temp=recordService.addRecord(record);
		if(temp==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:数据保存失败!";
			return httpBaseEntity;
		}
		UploadResult uploadResult=new UploadResult();
		uploadResult.uploadSize=1;
		uploadResult.msg="上传成功";
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=uploadResult;
		return httpBaseEntity;
	}

	@RequestMapping(value="/record/post/list/{userid}",method=RequestMethod.POST)
	@ResponseBody
	HttpBaseEntity<UploadResult> uploadRecords(@PathVariable long userid,@RequestBody List<LogEntity> lists) {	
		HttpBaseEntity<UploadResult> httpBaseEntity=new HttpBaseEntity<UploadResult>();
		if(userid==0)
		{

			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效用户名!";
			return httpBaseEntity;
		}
		if(lists==null)
		{

			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效数据!";
			return httpBaseEntity;
		}
		if(lists.size()==0)
		{

			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效数据!";
			return httpBaseEntity;
		}
		boolean temp=recordService.addRecords(userid, lists);
		if(!temp)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效数据!";
			return httpBaseEntity;
		}
		UploadResult uploadResult=new UploadResult();
		uploadResult.uploadSize=lists.size();
		uploadResult.msg="上传成功";
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=uploadResult;
		return httpBaseEntity;
	}

	@RequestMapping(value="/record/get/list/{userid}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<List<LogIndex>> getAllRecords(@PathVariable long userid) {	
		HttpBaseEntity<List<LogIndex>> httpBaseEntity=new HttpBaseEntity<List<LogIndex>>();
		if(userid==0)
		{

			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效用户名!";
			return httpBaseEntity;
		}
		List<LogIndex> list=recordService.getRecords(userid);
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=list;
		return httpBaseEntity;
	}


	@RequestMapping(value="/record/get/page/{userid}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<List<LogIndex>> getRecordsForPageble(
			@PathVariable long userid,
			@PageableDefault(size = 20,page=0,sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {	
		HttpBaseEntity<List<LogIndex>> httpBaseEntity=new HttpBaseEntity<List<LogIndex>>();
		if(userid==0)
		{

			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效用户名!";
			return httpBaseEntity;
		}
		List<LogIndex> list=recordService.getRecordForPageable(userid,pageable);
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=list;
		return httpBaseEntity;
	}


	@RequestMapping(value="/record/get/{userid}/pos/{pos}",method=RequestMethod.POST)
	@ResponseBody
	HttpBaseEntity<LogEntity> getRecordForPos(
			@PathVariable long userid
			,@PathVariable int pos
			,@RequestBody ResultState state) {	
		HttpBaseEntity<LogEntity> httpBaseEntity=new HttpBaseEntity<LogEntity>();
		if(userid==0||state==null)
		{

			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效参数!";
			return httpBaseEntity;
		}
		LogEntity logEntity=recordService.getDetailRecord(userid,pos,state);
		if(logEntity==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:没有查询到数据!";
			return httpBaseEntity;
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=logEntity;
		return httpBaseEntity;
	}
	@RequestMapping(value="/record/put/aliasname/{userid}/{recordid}",method=RequestMethod.POST)
	@ResponseBody
	HttpBaseEntity<LogEntity>  editRecordNickname(@PathVariable long userid,@PathVariable long recordid
			,@RequestParam String aliasname) {	
		HttpBaseEntity<LogEntity> httpBaseEntity=new HttpBaseEntity<LogEntity>();
		if(userid==0||recordid==0||aliasname==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效参数!";
			return httpBaseEntity;
		}
		if(aliasname.length()<4||aliasname.length()>16)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:昵称格式不符合要求";
			return httpBaseEntity;
		}
		LogEntity logEntity=recordService.editRecordAliasName(userid,recordid,aliasname);
		if(logEntity==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:远端数据处理失败";
			return httpBaseEntity;
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=logEntity;
		return httpBaseEntity;
	}

	//pos用于确定删除数据后返回新的记录位置
	@RequestMapping(value="/record/del/{userid}/{recordid}/{pos}",method=RequestMethod.POST)
	@ResponseBody
	HttpBaseEntity<LogEntity>  delRecord(
			@PathVariable long userid
			,@PathVariable long recordid
			,@PathVariable int pos
			,@RequestBody ResultState state) {	
		HttpBaseEntity<LogEntity> httpBaseEntity=new HttpBaseEntity<LogEntity>();
		if(userid==0||recordid==0||state==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效参数!";
			return httpBaseEntity;
		}
		LogEntity logEntity=recordService.delRecord(userid,recordid,pos,state);
		if(logEntity==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:没有要操做的数据!";
			return httpBaseEntity;
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=logEntity;
		return httpBaseEntity;
	}


	@RequestMapping(value="/record/del/{userid}",method=RequestMethod.POST)
	@ResponseBody
	HttpBaseEntity<List<LogEntity>>  delRecords(
			@PathVariable long userid) {	
		HttpBaseEntity<List<LogEntity>> httpBaseEntity=new HttpBaseEntity<List<LogEntity>>();
		if(userid==0)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:无效参数!";
			return httpBaseEntity;
		}
		List<LogEntity> list=recordService.delRecords(userid);
		if(list==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="fail:云端已无数据!";
			return httpBaseEntity;
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=list;
		return httpBaseEntity;
	}


	@RequestMapping(value="/article/get/new/{type}/{size}/{startId}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<List<ArticleIndex>> getNewArticleIndex(
			@PathVariable int type,
			@PathVariable int size,
			@PathVariable long startId
			) {	

		HttpBaseEntity<List<ArticleIndex>> httpBaseEntity=new HttpBaseEntity<List<ArticleIndex>>();
		List<ArticleIndex> list=articleService.getNewArticle(type, size, startId);
		if(list==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="暂时没有新的数据";
			return httpBaseEntity;
		}
		if(list.size()<=0)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="暂时没有新的数据";
			return httpBaseEntity;
		}
		for(int i=0;i<list.size();i++)
		{
			list.get(i).articleContent="";
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=list;
		return httpBaseEntity;
	}

	@RequestMapping(value="/article/get/old/{type}/{size}/{endId}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<List<ArticleIndex>>  getOldArticleIndex(
			@PathVariable int type,
			@PathVariable int size,
			@PathVariable long endId
			) {	

		HttpBaseEntity<List<ArticleIndex>> httpBaseEntity=new HttpBaseEntity<List<ArticleIndex>>();
		List<ArticleIndex> list=articleService.getOlderArticle(type, size, endId);
		if(list==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="已经撸到底了";
			return httpBaseEntity;
		}
		if(list.size()<=0)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="已经撸到底了";
			return httpBaseEntity;
		}
		for(int i=0;i<list.size();i++)
		{
			list.get(i).articleContent="";
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=list;
		return httpBaseEntity;
	}

	@RequestMapping(value="/article/get/near/{type}/{size}/{centerId}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<List<ArticleIndex>>  getNearArticleIndex(
			@PathVariable int type,
			@PathVariable int size,
			@PathVariable long centerId
			) {	

		HttpBaseEntity<List<ArticleIndex>> httpBaseEntity=new HttpBaseEntity<List<ArticleIndex>>();
		List<ArticleIndex> list=articleService.getNearArticle(type, size, centerId);
		if(list==null)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="已经撸到底了";
			return httpBaseEntity;
		}
		if(list.size()<=0)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="已经撸到底了";
			return httpBaseEntity;
		}
		//附近的索引信息则不携带文章正文数据，否则会导致手机端占用太多内存
		for(int i=1;i<list.size();i++)
		{
			list.get(i).articleContent="";
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=list;
		return httpBaseEntity;
	}


	@RequestMapping(value="article/post/comment",method=RequestMethod.POST)
	@ResponseBody
	HttpBaseEntity<UploadResult> postComment(@RequestBody UserComment userComment) throws Exception {	
		HttpBaseEntity<UploadResult> httpBaseEntity=new HttpBaseEntity<UploadResult>();
		UploadResult uploadResult=new UploadResult();
		uploadResult.msg="msg";
		httpBaseEntity.data=uploadResult;
	    if(!commentService.checkRule(userComment))
	    {
			httpBaseEntity.code=-3;
			httpBaseEntity.msg="内容不符合规范";
			return httpBaseEntity;
	    }
		
		if(!commentService.checkSign(userComment))
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="数字签名错误";
			return httpBaseEntity;
		}
		uploadResult.uploadSize=commentService.addComment(userComment);
		if(uploadResult.uploadSize==0)
		{
			httpBaseEntity.code=-2;
			httpBaseEntity.msg="插入数据失败";
			return httpBaseEntity;
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		return httpBaseEntity;
	}

	//获取最优的前5条评论，仅在第一次加载数据时调用
	@RequestMapping(value="article/get/great/comment/{articleId}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<List<UserComment>> getGreatComment(
			@PathVariable long articleId
			) {	
		HttpBaseEntity<List<UserComment>> httpBaseEntity=new HttpBaseEntity<List<UserComment>>();
		if(articleId<=0)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="无效参数";
			return httpBaseEntity;
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		httpBaseEntity.data=null;
		return httpBaseEntity;
	}

	//用于打开界面后的刷新，显示所有未显示的新数据
	@RequestMapping(value="article/get/new/comment/{articleId}/{startId}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<List<UserComment>> getNewComment(
			@PathVariable long articleId,
			@PathVariable long startId
			) {	
		HttpBaseEntity<List<UserComment>> httpBaseEntity=new HttpBaseEntity<List<UserComment>>();
		if(articleId<=0)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="无效参数";
			return httpBaseEntity;
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		List<UserComment> list=commentService.getNewComment(articleId,startId);
		httpBaseEntity.data=list;
		return httpBaseEntity;
	}

	@RequestMapping(value="article/get/old/comment/{articleId}/{endId}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<List<UserComment>> getOldComment(
			@PathVariable long articleId,
			@PathVariable long endId
			) {	
		HttpBaseEntity<List<UserComment>> httpBaseEntity=new HttpBaseEntity<List<UserComment>>();
		if(endId<=0||articleId<=0)
		{
			httpBaseEntity.code=-1;
			httpBaseEntity.msg="无效参数";
			return httpBaseEntity;
		}
		httpBaseEntity.code=0;
		httpBaseEntity.msg="success";
		List<UserComment> list=commentService.getOldComment(articleId,endId);
		httpBaseEntity.data=list;
		return httpBaseEntity;
	}


	@RequestMapping(value="/open/help",method=RequestMethod.GET)
	String help() {

		return "help";
	}

	@RequestMapping(value="/open/us",method=RequestMethod.GET)
	String aboutUs() {

		return "us";
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


}
