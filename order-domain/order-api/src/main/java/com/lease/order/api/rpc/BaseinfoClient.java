package com.lease.order.api.rpc;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "products", contextId = "BaseinfoClient")
public interface BaseinfoClient {

    @PutMapping("/product/productlnfo/update")
    JSONObject lock(@RequestBody JSONObject request);
}
