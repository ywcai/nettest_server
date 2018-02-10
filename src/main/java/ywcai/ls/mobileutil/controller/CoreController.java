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
import ywcai.ls.mobileutil.entity.MyRecord;
import ywcai.ls.mobileutil.entity.MyUser;
import ywcai.ls.mobileutil.log.MyLog;
import ywcai.ls.mobileutil.service.RecordService;
import ywcai.ls.mobileutil.service.UserService;



@Controller
public class CoreController {

	@Autowired
	private RecordService recordService;
	@Autowired
	private UserService userService;


	@RequestMapping(value="/user/get/{openID}/{channelID}",method=RequestMethod.GET)
	@ResponseBody
	HttpBaseEntity<MyUser> getMyUser(@PathVariable String openID,@PathVariable int channelID) {
		HttpBaseEntity<MyUser> baseEntity=new HttpBaseEntity<MyUser>();
		MyUser myUser=userService.getUser(openID, channelID);
		if(myUser==null||openID==null)
		{
			myUser=userService.createNewUser(openID,channelID);
			if(myUser==null)
			{
				baseEntity.code=-1;
				baseEntity.msg="fail:创建用户异常";
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
