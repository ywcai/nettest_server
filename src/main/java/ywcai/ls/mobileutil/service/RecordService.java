package ywcai.ls.mobileutil.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ywcai.ls.mobileutil.bean.LogEntity;
import ywcai.ls.mobileutil.bean.LogIndex;
import ywcai.ls.mobileutil.bean.ResultState;
import ywcai.ls.mobileutil.entity.MyRecord;
import ywcai.ls.mobileutil.log.MyLog;
import ywcai.ls.mobileutil.repository.RecordRepository;

@Service
@Qualifier(value="RecordService")
public class RecordService {
	@Autowired
	RecordRepository recordRepository;

	public MyRecord addRecord(MyRecord record)
	{
		MyRecord temp=recordRepository.saveAndFlush(record);
		return temp;
	}

	@Transactional
	public boolean addRecords(long userid,List<LogEntity> logEntitys)
	{
		for(int i=0;i<logEntitys.size();i++)
		{
			MyRecord record=new MyRecord();
			record.setUserid(userid);
			record.saveForEntity(logEntitys.get(i));
			MyRecord temp=addRecord(record);
			if(temp==null)
			{
				return false;
			}
		}
		return true;
	}

	public List<LogIndex> getRecords(long userid) {
		List<LogIndex> list=new ArrayList<LogIndex>();
		List<MyRecord> records=recordRepository.findByUseridOrderByCreatetimeDesc(userid);
		if(records==null)
		{
			return list;
		}
		for(int i=0;i<records.size();i++)
		{
			LogIndex logIndex=new LogIndex();
			logIndex.addr=records.get(i).getAddr();
			logIndex.aliasFileName=records.get(i).getAliasname();
			logIndex.cacheFileName=records.get(i).getFilename();
			logIndex.cacheTypeIndex=records.get(i).getRecordtype();
			logIndex.remarks=records.get(i).getRemarks();
			logIndex.logTime=records.get(i).getCreatetime();
			logIndex.recordId=records.get(i).getId();
			list.add(logIndex);
		}
		return list;
	}


	public List<LogIndex> getRecordForPageable(long userid, Pageable pageable) {
		Page<MyRecord> pages=recordRepository.findByUserid(userid,pageable);
		List<LogIndex> list=new ArrayList<LogIndex>();
		for (MyRecord record : pages.getContent()) {
			LogIndex logIndex=new LogIndex();
			logIndex.addr=record.getAddr();
			logIndex.cacheFileName=record.getAddr();
			logIndex.aliasFileName=record.getAliasname();
			logIndex.remarks=record.getRemarks();
			logIndex.logTime=record.getCreatetime();
			logIndex.recordId=record.getId();
			logIndex.cacheTypeIndex=record.getRecordtype();
			list.add(logIndex);
		}
		// TODO Auto-generated method stub
		return list;
	}

	public LogEntity getDetailRecord(long  userid, int pos,ResultState state) {
		// TODO Auto-generated method stub
		List<Integer> collection=new ArrayList<Integer>();
		for(int i=0;i<state.isShow.length;i++)
		{
			if(state.isShow[i]==1)
			{
				collection.add(i);
			}
		}
		List<MyRecord> records=recordRepository.findByUseridAndRecordtypeIn(userid,collection);
		if(records==null)
		{
			return null;
		}
		if(records.size()==0)
		{
			return null;
		}
		if(pos<1)
		{
			pos=1;
		}
		if(pos>records.size())
		{
			pos=records.size();
		}
		LogEntity logEntity=new LogEntity();
		logEntity.data=records.get(pos-1).getContent();
		logEntity.max=records.size();
		LogIndex logIndex=new LogIndex();
		logIndex.addr=records.get(pos-1).getAddr();
		logIndex.cacheFileName=records.get(pos-1).getAddr();
		logIndex.aliasFileName=records.get(pos-1).getAliasname();
		logIndex.remarks=records.get(pos-1).getRemarks();
		logIndex.logTime=records.get(pos-1).getCreatetime();
		logIndex.recordId=records.get(pos-1).getId();
		logIndex.cacheTypeIndex=records.get(pos-1).getRecordtype();
		logEntity.logIndex=logIndex;
		return logEntity;
	}


	@Transactional
	public LogEntity editRecordAliasName(long userid, long recordid, String aliasname) {
		// TODO Auto-generated method stub
		MyRecord record=recordRepository.findByIdAndUserid(recordid,userid);
		if(record==null)
		{
			return null;
		}
		record.setAliasname(aliasname);
		MyRecord temp=recordRepository.saveAndFlush(record);
		if(temp==null)
		{
			return null;
		}
		LogEntity logEntity=new LogEntity();
		logEntity.data=record.getContent();
		logEntity.max=-1;
		LogIndex logIndex=new LogIndex();
		logIndex.addr=record .getAddr();
		logIndex.cacheFileName=record.getAddr();
		logIndex.aliasFileName=record.getAliasname();
		logIndex.remarks=record.getRemarks();
		logIndex.logTime=record.getCreatetime();
		logIndex.recordId=record.getId();
		logIndex.cacheTypeIndex=record.getRecordtype();
		logEntity.logIndex=logIndex;
		return logEntity;
	}

	@Transactional
	public LogEntity delRecord(long userid, long recordid, int pos, ResultState state) {
		// TODO Auto-generated method stub
		MyRecord record=recordRepository.findOne(recordid);
		if(record==null)
		{
			return null;
		}
		recordRepository.delete(recordid);
		LogEntity logEntity=getDetailRecord(userid,pos,state);
		if(logEntity==null)
		{
			logEntity=new LogEntity();
			return logEntity;
		}
		return logEntity;
	}



}
