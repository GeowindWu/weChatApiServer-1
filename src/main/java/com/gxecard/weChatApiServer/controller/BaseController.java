package com.gxecard.weChatApiServer.controller;

import com.google.common.collect.ImmutableMap;
import com.gxecard.weChatApiServer.constant.ContractQueryConstant;
import com.gxecard.weChatApiServer.enums.ServerStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

@Controller
public class BaseController {

     @Autowired
     ContractQueryConstant queryContractConstant;

    public void sealSuccess(DeferredResult deferredResult,Object data){
        Map resultMap= ImmutableMap.of("code", ServerStatusEnum.OK.getCode(),
                "msg", "",
                "data",data);
        deferredResult.setResult(resultMap);
    }

    public void failse(DeferredResult deferredResult,Object obj,String msg){
        Map resultMap= ImmutableMap.of("code", ServerStatusEnum.ERROR.getCode(),
                "msg", "",
                "data",obj);
        deferredResult.setResult(resultMap);
    }
}
