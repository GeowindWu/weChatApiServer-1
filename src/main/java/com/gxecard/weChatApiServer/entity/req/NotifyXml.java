package com.gxecard.weChatApiServer.entity.req;

import com.sun.xml.internal.txw2.annotation.XmlElement;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 关系回调通知的xml bean
 */
@XmlRootElement(name = "xml")
@Data
public class NotifyXml {

    private String return_code = "";
    private String return_msg = "";
//以下字段在return_code为SUCCESS的时候返回
    private String result_code = "";
//以下字段在return_code 和result_code都为SUCCESS的时候有返回
    private String mch_id = "";
    private String sub_mch_id = "";
    private String contract_code = "";
    private String plan_id = "";
    private String openid = "";
    private String sub_openid = "";
    private String sign = "";
    private String change_type = "";
    private String operate_time = "";
    private String contract_id = "";
    private String contract_expired_time = "";
    private Integer contract_termination_mode = 0;//0未解约
    private String request_serial = "";

}
