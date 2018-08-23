package com.gxecard.weChatApiServer.controller;

import com.gxecard.weChatApiServer.exception.MessageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class Demo {

    @ResponseBody
    @RequestMapping("/")
    public String demo(){
        throw new MessageException("统一异常处理！");
        //return  "it works!";
    }
}
