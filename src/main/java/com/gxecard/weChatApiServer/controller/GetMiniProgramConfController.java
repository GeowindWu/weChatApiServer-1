package com.gxecard.weChatApiServer.controller;

import com.google.common.collect.ImmutableMap;
import com.gxecard.weChatApiServer.enums.ServerStatusEnum;
import com.gxecard.weChatApiServer.service.MiniProgramConfService;
import com.gxecard.weChatApiServer.vo.MiniProgramConfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

/**
 * 小程序配置信息获取接口
 */
@Controller
@RequestMapping("/getMiniProgramConf")
public class GetMiniProgramConfController extends BaseController{

    @Autowired
    private MiniProgramConfService miniProgramConfService;

    @RequestMapping("/get")
    @ResponseBody
    public DeferredResult getConf(){
        DeferredResult deferredResult=new DeferredResult();
        MiniProgramConfVo confs = miniProgramConfService.getConfs();
        sealSuccess(deferredResult,confs);
        return deferredResult;
    }
}
