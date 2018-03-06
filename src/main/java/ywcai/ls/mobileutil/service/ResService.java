package ywcai.ls.mobileutil.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ywcai.ls.mobileutil.entity.NumberRes;
import ywcai.ls.mobileutil.repository.ResRepository;


@Service
@Qualifier(value="ResService")
public class ResService {
	@Autowired
	ResRepository resRepository;

	//未新用户指派一个号码资源
	@Transactional
	public NumberRes getNormalNumber(long userGid) throws Exception
	{
		NumberRes number=resRepository.findTop1ByIsPrettyFalseAndIsValidFalseOrderByIdAsc();
		number.usergid=userGid;
		number.isValid=true;
		NumberRes number1 =resRepository.saveAndFlush(number);
		return number1;
	}
 
	public NumberRes getPrettyNumber(long userGid)
	{
		NumberRes number=resRepository.findTop1ByIsPrettyTrueAndIsValidFalseOrderByIdAsc();
		number.usergid=userGid;
		number.isValid=true;
		NumberRes number1 =resRepository.saveAndFlush(number);
		return number1;
	}
}
