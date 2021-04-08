package com.github.DchyService.tools;

import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component
public class HttpClint {
    @Resource
    private RestTemplate restTemplate;

    public ResponseEntity<String> call(String url, String json) {
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //初始化头部
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept-Encoding"," ");
        headers.set("Content-Type","application/json");
        //封装请求体
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response;
        } else {
            throw new RuntimeException();
        }
    }
  

}
