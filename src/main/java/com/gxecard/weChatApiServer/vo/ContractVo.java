package com.gxecard.weChatApiServer.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ContractVo {
    /**
     * 委托代扣协议id
     */
    private String contractId;
    /**
     * 请求序列号
     */
    private String requestSerial;
    /**
     * 签约协议号
     */
    private String contractCode;
    /**
     * 用户账户展示名称
     */
    private String contractDisplayAccount;
    /**
     * 协议状态:0-签约中  1-解约
     */
    private Integer contractState;
    /**
     * 协议签署时间
     */
    private String contractSignedTime;
    /**
     * 协议到期时间
     */
    private String contractExpiredTime;
    /**
     * 协议解约时间,当contractState=1时，该值有效
     */
    private String contractTerminatedTime;
    /**
     * 协议解约方式,当contractState=1时，该值有效 ,
     * 0-未解约
     * 1-有效期过自动解约
     * 2-用户主动解约
     * 3-商户API解约
     * 4-商户平台解约
     * 5-注销
     */
    private Integer contractTerminationMode;
    /**
     * 解约备注,当contractState=1时，该值有效
     */
    private String contractTerminationRemark;
    /**
     * 用户标识,商户appid下的用户唯一标识
     */
    private String openid;
    /**
     * 用户子标识,sub_appid下，用户的唯一标识
     */
    private String subOpenid;
    /**
     * 统一账户用户编号
     */
    private String userNo;
}
