package com.gxecard.weChatApiServer.controller;

import com.gxecard.weChatApiServer.entity.Contract;
import com.gxecard.weChatApiServer.entity.req.NotifyXml;
import com.gxecard.weChatApiServer.service.ContractService;
import com.gxecard.weChatApiServer.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/contractCallback")
public class ContractCallbackController extends BaseController {
    @Autowired
    private ContractService contractService;

    @RequestMapping("/notify")
    private DeferredResult notify(@RequestBody  NotifyXml notifyXml){
        //收到通知
        String returnMsg = "<xml>\n" +
                "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferredResult.setResult(returnMsg);
        if(notifyXml.getReturn_code().equalsIgnoreCase("SUCCESS") && notifyXml.getResult_code().equalsIgnoreCase("SUCCESS")){
            try {
                //签约成功
                String contract_id = notifyXml.getContract_id();
                String changeType = notifyXml.getChange_type();
                Contract contract = contractService.getContractByContractId(contract_id);
                if(contract == null){
                    contract = new Contract();
                }
                contract.setContractId(contract_id);
                contract.setRequestSerial(notifyXml.getRequest_serial());
                contract.setContractCode(notifyXml.getContract_code());
                if(changeType.equalsIgnoreCase("ADD")){
                    contract.setContractExpiredTime(notifyXml.getContract_expired_time());
                }else if(changeType.equalsIgnoreCase("DELETE")){
                    contract.setContractTerminationMode(notifyXml.getContract_termination_mode());
                }


                contract.setOpenid(notifyXml.getOpenid());
                contract.setSubOpenid(notifyXml.getSub_openid());
                contractService.saveOrUpdateContract(contract);
            }catch (Exception e){
                ExceptionUtil.getStackTrace(e);
            }

        }

        return deferredResult;
    }
}
