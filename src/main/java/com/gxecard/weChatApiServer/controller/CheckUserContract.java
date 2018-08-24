package com.gxecard.weChatApiServer.controller;


import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.gxecard.weChatApiServer.entity.Contract;
import com.gxecard.weChatApiServer.enums.ServerStatusEnum;
import com.gxecard.weChatApiServer.exception.MessageException;
import com.gxecard.weChatApiServer.service.ContractService;
import com.gxecard.weChatApiServer.vo.ContractVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import java.util.Map;

/**
 * 查询签约关系接口
 */
@RequestMapping("/checkUserContract")
@Controller
public class CheckUserContract extends BaseController{

    @Autowired
    private ContractService contractService;


    @RequestMapping("/check")
    @ResponseBody
    public DeferredResult check(String phone){
        checkPhoneIsNotNull(phone);
        DeferredResult deferredResult=new DeferredResult();
        ContractVo contractByPhone = contractService.getContractByPhone(phone);
        sealSuccess(deferredResult,contractByPhone);
        return deferredResult;
    }

    private void checkPhoneIsNotNull(String phone) {
        if (Strings.isNullOrEmpty(phone)){
            throw new MessageException("phone不能为空！");
        }
    }

}
