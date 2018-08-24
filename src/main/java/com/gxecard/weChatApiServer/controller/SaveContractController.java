package com.gxecard.weChatApiServer.controller;

import com.google.common.base.Strings;
import com.gxecard.weChatApiServer.constant.ContractQueryResultConstant;
import com.gxecard.weChatApiServer.dao.ContractDao;
import com.gxecard.weChatApiServer.entity.Contract;
import com.gxecard.weChatApiServer.exception.MessageException;
import com.gxecard.weChatApiServer.service.ContractService;
import com.gxecard.weChatApiServer.service.MiniProgramConfService;
import com.gxecard.weChatApiServer.util.WeChatUtil;
import com.gxecard.weChatApiServer.vo.ContractVo;
import com.gxecard.weChatApiServer.vo.MiniProgramConfVo;
import com.gxecard.weChatApiServer.vo.SaveContractResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯车主签约关系保存接口
 */
@Controller
@RequestMapping("/saveContract")
@Slf4j
public class SaveContractController extends BaseController{

    @Autowired
    private MiniProgramConfService miniProgramConfService;
    @Autowired
    private ContractService contractService;

    private SaveContractResultVo saveContractResultVo;
    private Contract contract;
    private ContractVo contractVo;


    @RequestMapping("/save")
    @ResponseBody
    public DeferredResult saveContract(String phone,String contractId,String userNo){
        checkParamsIsNotNull(phone,contractId,userNo);
        MiniProgramConfVo confs = miniProgramConfService.getConfs();
        Map queryContractRequstMap = sealQueryContractRequstMap(confs, contractId);
        String queryContractXml = WeChatUtil.getQueryContractXmlByMap(queryContractRequstMap, queryContractConstant.apiKey);
        log.info("请求腾讯查询签约信息报文："+queryContractXml);
        Map<String, Object> queryPreResult = WeChatUtil.httpXmlRequest(queryContractConstant.queryContractUrl, "POST", queryContractXml);
        log.info("腾讯响应查询结果："+queryPreResult);
        checkQueryPreResultIsSuccess(queryPreResult);
        getContractFromDatabaseAndMakeSureNotNull(contractId);
        analyzeQueryPreResult2Vo(queryPreResult,userNo);
        BeanUtils.copyProperties(contractVo,contract);
        saveOrUpdateContract();
        makeSuccessReturn();
        DeferredResult deferredResult=new DeferredResult();
        sealSuccess(deferredResult,saveContractResultVo);
        return deferredResult;
    }


    private void checkParamsIsNotNull(String phone, String contractId,String uerNo) {
        if (Strings.isNullOrEmpty(phone)){
            throw new MessageException("phone不能为空！");
        }
        if (Strings.isNullOrEmpty(contractId)){
            throw new MessageException("contractId不能为空！");
        }
        if (Strings.isNullOrEmpty(uerNo)){
            throw new MessageException("uerNo不能为空！");
        }
    }

    private Map sealQueryContractRequstMap(MiniProgramConfVo confs, String contractId) {
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("appid",confs.getAppId());
        requestMap.put("mch_id",confs.getMchId());
        requestMap.put("sub_mch_id",confs.getSubMchId());
        requestMap.put("contract_id",contractId);
        requestMap.put("version", queryContractConstant.version);
        return requestMap;
    }

    /**
     * 检查查询结果是否响应成功
     * @param queryPreResult
     */
    private void checkQueryPreResultIsSuccess(Map<String, Object> queryPreResult) {
        if (queryPreResult==null){
            throw new MessageException("上游腾讯侧服务器无响应，保存失败！");
        }
        if (Strings.isNullOrEmpty((String) queryPreResult.get("return_code"))){
            throw new MessageException("上游腾讯侧服务器响应异常，保存失败！");
        }
        if (Strings.isNullOrEmpty((String) queryPreResult.get("result_code"))){
            throw new MessageException("上游腾讯侧业务无响应，保存失败！");
        }
        if (ContractQueryResultConstant.RETURN_CODE_FAIL.equalsIgnoreCase((String) queryPreResult.get("return_code"))){
            throw new MessageException("上游腾讯侧服务器通信异常，保存失败！");
        }
        if (ContractQueryResultConstant.RESULT_CODE_FAIL.equalsIgnoreCase((String) queryPreResult.get("result_code"))){
            throw new MessageException("上游腾讯侧签约信息查询失败，请核实上送签约信息是否正确！");
        }
    }

    private void getContractFromDatabaseAndMakeSureNotNull(String contractId) {
        contract=contractService.getContractByContractId(contractId);
        if (contract==null){
            contract=new Contract();
        }
    }

    private void analyzeQueryPreResult2Vo(Map<String, Object> queryPreResult,String userNo) {
        contractVo.setContractId((String) queryPreResult.get("contract_id"));
        contractVo.setRequestSerial((String) queryPreResult.get("request_serial"));
        contractVo.setContractCode((String) queryPreResult.get("contract_code"));
        contractVo.setContractDisplayAccount((String) queryPreResult.get("contract_display_account"));
        int contractState = Integer.parseInt((String) queryPreResult.get("contract_state"));
        contractVo.setContractState(contractState);
        contractVo.setContractSignedTime((String) queryPreResult.get("contract_signed_time"));
        contractVo.setContractExpiredTime((String) queryPreResult.get("contract_expired_time"));
        contractVo.setContractTerminatedTime((String) queryPreResult.get("contract_terminated_time"));
        int contractTerminationMode = Integer.parseInt((String) queryPreResult.get("contract_termination_mode"));
        contractVo.setContractTerminationMode(contractTerminationMode);
        contractVo.setContractTerminationRemark((String) queryPreResult.get("contract_termination_remark"));
        contractVo.setOpenid((String) queryPreResult.get("openid"));
        contractVo.setSubOpenid((String) queryPreResult.get("sub_openid"));
        contractVo.setUserNo(userNo);
    }

    private void saveOrUpdateContract() {
        contractService.saveOrUpdateContract(contract);
    }

    private void makeSuccessReturn() {
        saveContractResultVo.setSave(true);
        saveContractResultVo.setSaveMessage("保存成功！");
    }
}
