package com.gxecard.weChatApiServer.util;

import com.alibaba.fastjson.JSONObject;
import com.gxecard.weChatApiServer.exception.MessageException;
import com.gxecard.weChatApiServer.vo.MiniProgramConfVo;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Slf4j
public class WeChatUtil {

    public static String getQueryContractXmlByMap(Map<String, String> requestMap, String partnerKey){
        String sign= getPSHA256Sign(requestMap, partnerKey);
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<appid>").append(requestMap.get("appid")).append("</appid>");
        sb.append("<mch_id>").append(requestMap.get("mch_id")).append("</mch_id>");
        sb.append("<sub_mch_id>").append(requestMap.get("sub_mch_id")).append("</sub_mch_id>");
        sb.append("<contract_id>").append(requestMap.get("contract_id")).append("</contract_id>");
        sb.append("<version>").append(requestMap.get("version")).append("</version>");
        sb.append("<sign>").append(sign).append("</sign>");
        sb.append("</xml>");
        return sb.toString();
    }

    public static String getQueryUserStatusXml(Map<String,String> requestMap,String priKey){
        String sign= getPSHA256Sign(requestMap, priKey);
        StringBuilder sb = new StringBuilder();
        String ramdom = "";
        for(int i =0; i < 16; i++){
            Random rd = new Random();
            ramdom +=rd.nextInt(10);
        }
        sb.append("<xml>");
        sb.append("<appid>").append(requestMap.get("appid")).append("</appid>");
        sb.append("<sub_appid>").append(requestMap.get("sub_appid")).append("</sub_appid>");
        sb.append("<mch_id>").append(requestMap.get("mch_id")).append("</mch_id>");
        sb.append("<sub_mch_id>").append(requestMap.get("sub_mch_id")).append("</sub_mch_id>");
        sb.append("<nonce_str>").append(ramdom).append("</nonce_str>");
        sb.append("<contract_id>").append(requestMap.get("contract_id")).append("</contract_id>");
        sb.append("<sign_type>").append(requestMap.get("sign_type")).append("</sign_type>");
        sb.append("<sign>").append(sign).append("</sign>");
        sb.append("<openid>").append(requestMap.get("openid")).append("</openid>");
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 获取请求签名
     * @param object
     * @param partnerKey
     * @return
     */
    private static String getPSHA256Sign(Object object,String partnerKey){


        Object jsonObject = JSONObject.toJSON(object);
        log.info("jsonObject:"  +  jsonObject.toString());

        Map<String , Object> jsonParamMap = JSONObject.parseObject(jsonObject.toString(), Map.class);
        String argPreSign = getStringByMap(jsonParamMap) + "&key=" + partnerKey;
        log.info(argPreSign);
        String preSign = MD5Util.sha256_HMAC(argPreSign, partnerKey);
        return preSign;
    }

    /**
     * 根据Map获取排序拼接后的字符串
     * @param map
     * @return
     */
    public static String getStringByMap(Map<String, Object> map) {
        SortedMap<String, Object> smap = new TreeMap<String, Object>(map);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            sb.append(m.getKey()).append("=").append(m.getValue()).append("&");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * 发送xml数据,获取返回结果
     * @param requestUrl
     * @param requestMethod
     * @param xmlStr
     * @return
     */
    public static Map<String, Object> httpXmlRequest(String requestUrl, String requestMethod, String xmlStr) {

        // 将解析结果存储在HashMap中
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpsURLConnection urlCon = (HttpsURLConnection) (new URL(requestUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            // 设置请求方式（GET/POST）
            urlCon.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                urlCon.connect();
            }

            urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            // 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            if (null != xmlStr) {
                OutputStream outputStream = urlCon.getOutputStream();
                outputStream.write(xmlStr.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }
            InputStream inputStream = urlCon.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStreamReader);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            urlCon.disconnect();
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new MessageException(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new MessageException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MessageException(e.getMessage());
        }
        return map;
    }
}
