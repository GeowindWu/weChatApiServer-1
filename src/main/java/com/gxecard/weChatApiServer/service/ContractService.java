package com.gxecard.weChatApiServer.service;

import com.gxecard.weChatApiServer.dao.ContractDao;
import com.gxecard.weChatApiServer.entity.Contract;
import com.gxecard.weChatApiServer.vo.ContractVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    @Autowired
    private ContractDao contractDao;

    public ContractVo getContractByPhone(String phone){
        ContractVo contractVo=new ContractVo();
        Contract byPhoneEquals = contractDao.findByPhoneEquals(phone);
        if (byPhoneEquals!=null){
            BeanUtils.copyProperties(byPhoneEquals,contractVo);
        }
        return contractVo;
    }

    public ContractVo getContractByPhone2(String phone){
        ContractVo contractVo = null;
        Contract byPhoneEquals = contractDao.findByPhoneEquals(phone);
        if (byPhoneEquals!=null){
            contractVo =new ContractVo();
            BeanUtils.copyProperties(byPhoneEquals,contractVo);
        }
        return contractVo;
    }

    public Contract getContractByContractId(String contractId){
        return contractDao.findByContractIdEquals(contractId);
    }

    public void saveOrUpdateContract(Contract contract){
        contractDao.save(contract);
    }
}
