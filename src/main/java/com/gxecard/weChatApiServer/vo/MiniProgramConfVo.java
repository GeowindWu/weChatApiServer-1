package com.gxecard.weChatApiServer.vo;

import lombok.Data;

@Data
public class MiniProgramConfVo {
    /**
     * 微信签约小程序appid
     */
    private String mainAppId;
    /**
     * 微信支付分配的商户号
     */
    private String mchId;
    /**
     * 微信支付分配的子商户号
     */
    private String subMchId;
    /**
     * 服务商商户号绑定的appid
     */
    private String appId;
    /**
     * 子商户号绑定的appid
     */
    private String subAppId;
    /**
     * 协议模板id
     */
    private String planId;
}
