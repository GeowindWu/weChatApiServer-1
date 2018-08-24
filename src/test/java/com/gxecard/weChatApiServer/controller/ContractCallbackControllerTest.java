package com.gxecard.weChatApiServer.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@AutoConfigureMockMvc
public class ContractCallbackControllerTest {
    @Autowired
    private MockMvc mcv;

    @Test
    public void notifyTest() throws Exception {
        String param = "<xml>\n" +
                "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<sign><![CDATA[C380BEC2BFD727A4B6845133519F3AD6]]></sign>\n" +
                "<mch_id>10010404</mch_id>\n" +
                "<sub_mch_id>10010405</sub_mch_id>\n" +
                "<contract_code>100001256</contract_code>\n" +
                "<openid><![CDATA[onqOjjmM1tad-3ROpncN-yUfa6ua]]></openid>\n" +
                "<plan_id><![CDATA[123]]></plan_id>\n" +
                "<change_type><![CDATA[ADD]]></change_type>\n" +
                "<operate_time><![CDATA[2015-07-01 10:00:00]]></operate_time>\n" +
                "<contract_id><![CDATA[Wx15463511252015071056489715]]></contract_id>\n" +
                "<contract_expired_time><![CDATA[2019-07-01 10:00:00]]></contract_expired_time>\n" +
                "</xml>";
        mcv.perform(MockMvcRequestBuilders.post("/contractCallback/notify").contentType(MediaType.APPLICATION_XML).content(param))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());;

    }

}