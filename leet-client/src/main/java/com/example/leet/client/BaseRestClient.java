package com.example.leet.client;

import com.example.leet.utils.ResponseUtils;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.*;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.io.CloseMode;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseRestClient{
    private static RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        if(null == restTemplate) restTemplate = new RestTemplate();
        return restTemplate;
    }
}
