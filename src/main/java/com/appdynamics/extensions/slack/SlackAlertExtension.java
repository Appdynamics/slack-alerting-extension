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

package com.appdynamics.extensions.slack;

import com.appdynamics.extensions.alerts.customevents.Event;
import com.appdynamics.extensions.alerts.customevents.EventBuilder;
import com.appdynamics.extensions.http.Response;
import com.appdynamics.extensions.http.SimpleHttpClient;
import com.appdynamics.extensions.slack.api.Alert;
import com.appdynamics.extensions.slack.api.AlertBuilder;
import com.appdynamics.extensions.yml.YmlReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by balakrishnavadavalasa on 03/02/16.
 */
public class SlackAlertExtension {

    public static final String CONFIG_FILENAME = "." + File.separator + "conf" + File.separator + "config.yaml";
    private static Logger logger = Logger.getLogger(SlackAlertExtension.class);

    Configuration config;

    public SlackAlertExtension(Configuration config) {
        this.config = config;
    }

    public static void main(String[] args) {
        logger.info("*****************START******************");
        String msg = "Using AlertingExtension Version [" + getImplementationTitle() + "]";
        logger.info(msg);

        if (args == null || args.length == 0) {
            logger.error("No arguments passed to the extension, exiting the program.");
            return;
        }
        logger.debug("Arguments passed :: " + Arrays.asList(args));
        Configuration config;
        try {
            config = YmlReader.readFromFile(CONFIG_FILENAME, Configuration.class);
            SlackAlertExtension alertExtension = new SlackAlertExtension(config);
            boolean status = alertExtension.processAnEvent(args);
            if (status) {
                logger.info("Slack Extension completed successfully.");
                logger.info("******************END******************");
                return;
            }
        } catch (Exception e) {
            logger.error("Error processing an event", e);
        }
        logger.error("Slack Extension completed with errors");
    }

    private static String getImplementationTitle() {
        return SlackAlertExtension.class.getPackage().getImplementationTitle();
    }

    /**
     * Creates an AppDynamics event from the command line arguments, builds a Slack
     * payload from the health rule event and posts it to Slack.
     *
     * @param args
     * @return false incase of an error else true;
     */
    public boolean processAnEvent(String[] args) {
        Event event = new EventBuilder().build(args);
        if (event != null) {
            AlertBuilder alertBuilder = new AlertBuilder();
            Alert payload = alertBuilder.createPayload(config, event);
            try {
                String json = alertBuilder.convertIntoJsonString(payload);
                logger.debug("Starting posting data to Slack :: " + json);

                Response response = postAlert(json);
                if (response != null && response.getStatus() == HttpURLConnection.HTTP_OK) {
                    logger.info("Data successfully posted to Slack");
                    return true;
                }
                logger.error("Data post to Slack failed with HTTP status code: " + response.getStatus());
            } catch (JsonProcessingException e) {
                logger.error("Cannot serialized object into Json." + e);
            }
        }
        return false;
    }

    private Response postAlert(String data) {
        logger.debug("Building the httpClient");
        SimpleHttpClient simpleHttpClient = SimpleHttpClient.builder(new HashMap<String, String>())
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
}


