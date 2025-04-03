package com.vlad.exchangerate.client;

import com.vlad.exchangerate.dto.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rates", url = "${external.provider.exchange-rates-api.url}")
public interface ExternalExchangeRateApi {
    @GetMapping("/latest")
    ExchangeRateResponse getExchangeRate(@RequestParam("access_key") String apiKey);
}
