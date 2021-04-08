package com.github.DchyService.controller.addition;/**
 * @title: callController
 * @projectName DchyService
 * @description: TODO
 * @author 14874
 * @date 2020/12/26 18:27
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DchyService.tools.AES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName callController
 *@Description TODO
 *@Author maoZhan
 *@Date DATE{TIME}
 */
@RestController // 控制器接口
@RequestMapping("/additionServices")
@CrossOrigin
public class CallController {
    @Value("${attition.smsSercice}")
    private String shortMessageSite;
    @Resource
    private RestTemplate restTemplate;


    @RequestMapping(value="/sendmessage1",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @CrossOrigin
    public String sendmessage(@RequestBody String json) {
//		String sendmessage="https://www.xazygh.cn/springmvc/index";
        String password = "bdchlw";
        // 加密
        byte[] encode = AES.encrypt(json, password);
        //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
        String code = AES.parseByte2HexStr(encode);
        /* System.out.println("密文字符串：" + code);*/
        Map<String,Object> map1=new HashMap<>();
        map1.put("key",new String(code));
        return this.call1(shortMessageSite, JSON.toJSONString(map1)).getBody();
    }
    private ResponseEntity<String> call1(String url, String json) {
        //初始化头部
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //封装请求体
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return response;
    }

    //调用短信服务
    @RequestMapping(value="/sendmessage2",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> sendmsg(@RequestParam("telphone") String telphone, @RequestParam("msg") String msg) {
        JSONObject JSON = new JSONObject();
        JSON.put("telphone", telphone);
        JSON.put("msg", msg);
//        String password = "bdchlw";
        // 加密
//        byte[] encode = AES.encrypt(JSON.toJSONString(), password);
        //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
//        String code = AES.parseByte2HexStr(encode);
        /* System.out.println("密文字符串：" + code);*/
        System.out.println(JSON);
//        Map<String,Object> map1=new HashMap<>();
//        map1.put("key",new String(code));
        ResponseEntity<String> response = this.call(shortMessageSite, JSON.toJSONString());
        return response;
        /*if (response.getStatusCode() == HttpStatus.OK) {
            return new CommonResult(200, "验证码发送成功");
        } else {
            return new CommonResult(444, "验证码发送失败");
        }*/

    }

    private ResponseEntity<String> call(String url, String json) {
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        //初始化头部
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //封装请求体

        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        /* logger.info("调用不动产登记接口："+ url+ json);*/
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            /* logger.info("调用不动产登记接口成功，返回：{}", response.getBody());*/
        } else {
            /*  logger.error("调用不动产登记接口异常，{}", response.getBody());*/
        }
        return response;
    }

}
