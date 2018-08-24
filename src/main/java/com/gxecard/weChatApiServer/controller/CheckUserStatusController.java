package com.gxecard.weChatApiServer.controller;

import com.alibaba.fastjson.JSON;
import com.gxecard.weChatApiServer.service.ContractService;
import com.gxecard.weChatApiServer.service.MiniProgramConfService;
import com.gxecard.weChatApiServer.util.WeChatUtil;
import com.gxecard.weChatApiServer.vo.ContractVo;
import com.gxecard.weChatApiServer.vo.MiniProgramConfVo;
import com.gxecard.weChatApiServer.vo.UserStatusVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/checkUserStatus")
@RestController
@Slf4j
public class CheckUserStatusController  extends  BaseController{
    @Autowired
    private ContractService contractService;
    @Autowired
    private MiniProgramConfService miniProgramConfService;

    public DeferredResult checkUserStatus(String phone){
        DeferredResult deferredResult = new DeferredResult();
        UserStatusVo userStatusVo = new UserStatusVo();
        ContractVo contractVo = contractService.getContractByPhone(phone);
        if(contractVo == null){
            failse(deferredResult,userStatusVo,"该用户未签约");
        }else{
            MiniProgramConfVo confs = miniProgramConfService.getConfs();
            Map param = buildQueryUserStatusParam(confs, contractVo.getContractId(), contractVo.getOpenid());
            String queryXml = WeChatUtil.getQueryUserStatusXml(param, queryContractConstant.getApiKey());
            log.info("请求腾讯查询用户状态报文："+queryXml);
            Map<String, Object> queryPreResult = WeChatUtil.httpXmlRequest(queryContractConstant.getQueryUserStatus(), "POST", queryXml);
            String result = JSON.toJSONString(queryPreResult);
            log.info("腾讯响应查询结果："+result);
            sealSuccess(deferredResult,result);
        }


        return   deferredResult;
    }

    private Map buildQueryUserStatusParam(MiniProgramConfVo conf,String contractId,String openId){
        HashMap<String, String> param = new HashMap<>();
        param.put("appid",conf.getAppId());
        param.put("sub_appid",conf.getSubAppId());
        param.put("mch_id",conf.getMchId());
        param.put("sub_mch_id",conf.getSubMchId());
        param.put("contract_id",contractId);
        param.put("sign_type","HMAC-SHA256");
        param.put("openid",openId);

        return param;
    }

}
