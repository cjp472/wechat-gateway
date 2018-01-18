package com.wangxiaobao.wechatgateway.service.constantcode;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.entity.constantcode.ConstantCode;
import com.wangxiaobao.wechatgateway.repository.constantcode.ConstantCodeRepository;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
@Service
@Transactional
public class ConstantCodeService extends BaseService {
	@Autowired
	private ConstantCodeRepository constantCodeRepository;
	
	public ConstantCode findConstantCode(ConstantCode constantCode){
		Example<ConstantCode> example = Example.of(constantCode);
		return constantCodeRepository.findOne(example);
	}
}
