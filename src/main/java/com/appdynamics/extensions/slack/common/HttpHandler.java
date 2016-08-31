/**
 * Copyright 2016 AppDynamics, Inc.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdynamics.extensions.slack.common;

import com.appdynamics.TaskInputArgs;
import com.appdynamics.extensions.http.Response;
import com.appdynamics.extensions.http.SimpleHttpClient;
import com.appdynamics.extensions.slack.Configuration;
import com.appdynamics.extensions.slack.Proxy;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by balakrishnavadavalasa on 31/08/16.
 */
public class HttpHandler {

    private static Logger logger = Logger.getLogger(HttpHandler.class);

    private final Configuration config;

    public HttpHandler(Configuration config) {
        this.config = config;
    }

    public Response postAlert(String data) {
        Map<String, String> httpConfigMap = createHttpConfigMap();
        logger.debug("Building the httpClient");
        SimpleHttpClient simpleHttpClient = SimpleHttpClient.builder(httpConfigMap)
                .connectionTimeout(config.getConnectTimeout())
                .socketTimeout(config.getSocketTimeout())
                .build();
        String targetUrl = config.getWebhookUrl();
        Response response = simpleHttpClient.target(targetUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .post(data);
        logger.debug("HTTP Response status from slack " + response.getStatus());
        return response;
    }

    private Map<String, String> createHttpConfigMap() {
        Map<String,String> map = new HashMap<String, String>();

        Proxy proxy = this.config.getProxy();
        if(proxy != null) {
            if (!Strings.isNullOrEmpty(proxy.getHost())) {
                map.put(TaskInputArgs.PROXY_HOST, proxy.getHost());
            }
            if (!Strings.isNullOrEmpty(proxy.getPort())) {
                map.put(TaskInputArgs.PROXY_PORT, proxy.getPort());
            }
            if (!Strings.isNullOrEmpty(proxy.getUri())) {
                map.put(TaskInputArgs.PROXY_URI, proxy.getUri());
            }
            if (!Strings.isNullOrEmpty(proxy.getUsername())) {
                map.put(TaskInputArgs.PROXY_USER, proxy.getUsername());
                // Don't put any password if not specified
                if (!Strings.isNullOrEmpty(proxy.getPassword())) {
                    map.put(TaskInputArgs.PROXY_PASSWORD, proxy.getPassword());
                }
            }
        }
        return map;
    }
}
