package org.geekbang.time.commonmistakes.serialization.enumusedinapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequestMapping("enumusedinapi")
@RestController
public class EnumUsedInAPIController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("getOrderStatusClient")
    public void getOrderStatusClient() {
        StatusEnumClient result = restTemplate.getForObject("http://localhost:45678/enumusedinapi/getOrderStatus", StatusEnumClient.class);
        log.info("result {}", result);
    }

    @GetMapping("queryOrdersByStatusListClient")
    public void queryOrdersByStatusListClient() {
        List<StatusEnumClient> request = Arrays.asList(StatusEnumClient.CREATED, StatusEnumClient.PAID);
        HttpEntity<List<StatusEnumClient>> entity = new HttpEntity<>(request, new HttpHeaders());
        List<StatusEnumClient> response = restTemplate.exchange("http://localhost:45678/enumusedinapi/queryOrdersByStatusList",
                HttpMethod.POST, entity, new ParameterizedTypeReference<List<StatusEnumClient>>() {
                }).getBody();
        log.info("result {}", response);
    }

    @GetMapping("getOrderStatus")
    public StatusEnumServer getOrderStatus() {
        return StatusEnumServer.CANCELED;
    }

    @PostMapping("queryOrdersByStatusList")
    public List<StatusEnumServer> queryOrdersByStatus(@RequestBody List<StatusEnumServer> enumServers) {
        enumServers.add(StatusEnumServer.CANCELED);
        return enumServers;
    }
}
