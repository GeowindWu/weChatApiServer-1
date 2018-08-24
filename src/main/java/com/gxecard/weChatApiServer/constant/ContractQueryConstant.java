package com.gxecard.weChatApiServer.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ContractQueryConstant {
     public String version="1.0";
     @Value("${weChat.apiKey}")
     public String apiKey;
     @Value("${weChat.queryContractUrl}")
     public String queryContractUrl;
}
