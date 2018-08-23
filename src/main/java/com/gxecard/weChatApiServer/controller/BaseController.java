package com.gxecard.weChatApiServer.controller;

import com.google.common.collect.ImmutableMap;
import com.gxecard.weChatApiServer.enums.ServerStatusEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

public class BaseController {
    //DeferredResult deferredResult=new DeferredResult();
    public void sealSuccess(DeferredResult deferredResult,Object data){
        Map resultMap= ImmutableMap.of("code", ServerStatusEnum.OK.getCode(),
                "msg", "",
                "data",data);
        deferredResult.setResult(resultMap);
    }
}
