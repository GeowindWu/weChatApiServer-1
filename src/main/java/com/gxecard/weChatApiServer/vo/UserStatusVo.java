package com.gxecard.weChatApiServer.vo;

import lombok.Data;

@Data
public class UserStatusVo {

    private String return_code = "";
    private String return_msg = "";
    //以下字段在return_code为SUCCESS的时候返回
    private String appid = "";
    private String sub_appid = "";
    private String mch_id = "";
    private String sub_mch_id = "";

    private String nonce_str = "";
    private String sign = "";
    private String result_code = "";
    private String err_code = "";
    private String err_code_des = "";

    private String user_state = "";
    private String contract_id = "";
    private String plan_id = "";
    private String contract_code = "";
    private String contract_state = "";

}
