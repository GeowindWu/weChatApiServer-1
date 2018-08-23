package com.gxecard.weChatApiServer.controller;

import com.google.common.collect.ImmutableMap;
import com.gxecard.weChatApiServer.enums.ServerStatusEnum;
import com.gxecard.weChatApiServer.service.MiniProgramConfService;
import com.gxecard.weChatApiServer.vo.MiniProgramConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/getMiniProgramConf")
public class GetMiniProgramConfController {

    @Autowired
    private MiniProgramConfService miniProgramConfService;

    @RequestMapping("/get")
    @ResponseBody
    public DeferredResult getConf(){
        DeferredResult deferredResult=new DeferredResult();
        MiniProgramConf confs = miniProgramConfService.getConfs();
        Map resultMap= ImmutableMap.of("code", ServerStatusEnum.OK.getCode(),
                "msg", "",
                "data",confs);
        deferredResult.setResult(resultMap);
        return deferredResult;
    }
}
