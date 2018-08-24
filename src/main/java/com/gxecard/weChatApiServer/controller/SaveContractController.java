package com.gxecard.weChatApiServer.controller;

import com.google.common.base.Strings;
import com.gxecard.weChatApiServer.constant.ContractQueryResultConstant;
import com.gxecard.weChatApiServer.exception.MessageException;
import com.gxecard.weChatApiServer.service.MiniProgramConfService;
import com.gxecard.weChatApiServer.util.WeChatUtil;
import com.gxecard.weChatApiServer.vo.MiniProgramConfVo;
import com.gxecard.weChatApiServer.vo.SaveContractResultVo;
import lombok.extern.slf4j.Slf4j;
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

    private SaveContractResultVo saveContractResultVo;

    @RequestMapping("/save")
    @ResponseBody
    public DeferredResult saveContract(String phone,String contractId,String userNo){
        checkParamsIsNotNull(phone,contractId,userNo);
        MiniProgramConfVo confs = miniProgramConfService.getConfs();
        Map queryContractRequstMap = sealQueryContractRequstMap(confs, contractId);
        String queryContractXml = WeChatUtil.getQueryContractXmlByMap(queryContractRequstMap, queryContractConstant.apiKey);
//        queryContractXml="<xml>\n" +
//                " <sign>019C869758CC7F258C42F05CDB9EE361</sign>\n" +
//                " <mch_id>10000097</mch_id>\n" +
//                "<sub_mch_id>10010405</sub_mch_id>\n" +
//                " <appid>wxf5b5e87a6a0fde94</appid>\n" +
//                " <contract_id>201509160000028648</contract_id>\n" +
//                " <version>1.0</version>\n" +
//                "</xml>";
        log.info("请求腾讯查询签约信息报文："+queryContractXml);
        Map<String, Object> queryPreResult = WeChatUtil.httpXmlRequest(queryContractConstant.queryContractUrl, "POST", queryContractXml);
        log.info("腾讯响应查询结果："+queryPreResult);
        checkQueryPreResultIsSuccess(queryPreResult);
        DeferredResult deferredResult=new DeferredResult();
        sealSuccess(deferredResult,queryPreResult);
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
        if (Strings.isNullOrEmpty((String) queryPreResult.get("return_code"))){
            throw new MessageException("上游腾讯侧服务器无响应，保存失败！");
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
}
