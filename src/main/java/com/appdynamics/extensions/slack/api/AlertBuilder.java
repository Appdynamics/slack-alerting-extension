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
package com.appdynamics.extensions.slack.api;

import com.appdynamics.extensions.alerts.customevents.Event;
import com.appdynamics.extensions.alerts.customevents.HealthRuleViolationEvent;
import com.appdynamics.extensions.alerts.customevents.OtherEvent;
import com.appdynamics.extensions.slack.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

/**
 * Created by balakrishnavadavalasa on 03/02/16.
 */
public class AlertBuilder {

    private Attachment createAlertMessage(Event event) {
        Attachment attachment = new Attachment();
        StringBuilder sb = new StringBuilder();
        if ((event instanceof HealthRuleViolationEvent)) {
            HealthRuleViolationEvent healthRuleViolationEvent = (HealthRuleViolationEvent) event;
            attachment.setTitle("Health rule violation: " + buildHealthRuleTitle(healthRuleViolationEvent));
            attachment.setColor(setMessageColor(healthRuleViolationEvent.getSeverity()));

            sb.append("Application Name: ").append(event.getAppName()).append(", ");
            sb.append("Health rule name: ").append(healthRuleViolationEvent.getHealthRuleName());
            sb.append("\n");
            sb.append("Affected Entity Type: ").append(healthRuleViolationEvent.getAffectedEntityType()).append(", ");
            sb.append("Affected Entity Name: ").append(healthRuleViolationEvent.getAffectedEntityName());
            sb.append("\n");
            sb.append("\n");
            sb.append("Summary message: ").append(healthRuleViolationEvent.getSummaryMessage());
            sb.append("\n");
            sb.append("URL: ").append(event.getDeepLinkUrl()).append(healthRuleViolationEvent.getIncidentID());

        } else {
            OtherEvent oe = (OtherEvent) event;
            attachment.setTitle("Event: " + buildOtherEventTitle(oe));
            attachment.setColor(setMessageColor(oe.getSeverity()));

            sb.append("Application Name: ").append(oe.getAppName()).append(", ");
            sb.append("Event Name: ").append(oe.getEventNotificationName());
            sb.append("\n");
            sb.append("URL: ").append(oe.getDeepLinkUrl()).append(oe.getEventNotificationId()).append("]");
        }
        attachment.setText(sb.toString());
        return attachment;
    }

    private String buildHealthRuleTitle(HealthRuleViolationEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("P").append(event.getPriority()).append(", ");
        sb.append("Severity:").append(event.getSeverity()).append(", ");
        sb.append("Event Time:").append(event.getPvnAlertTime());
        return sb.toString();
    }

    private String buildOtherEventTitle(OtherEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("P").append(event.getPriority()).append(", ");
        sb.append("Severity:").append(event.getSeverity()).append(", ");
        sb.append("Event Time:").append(event.getEventNotificationTime());
        return sb.toString();
    }

    private String setMessageColor(String severity) {
        if ("INFO".equals(severity)) {
            return "good";
        } else if ("ERROR".equals(severity)) {
            return "danger";
        } else if ("WARN".equals(severity)) {
            return "warning";
        }
        return "";
    }

    public String convertIntoJsonString(Alert alert) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(alert);
    }

    public Alert createPayload(Configuration config, Event event) {
        Alert alert = new Alert();
        if (!Strings.isNullOrEmpty(config.getChannel())) {
            alert.setChannel(config.getChannel());
        }
        if (!Strings.isNullOrEmpty(config.getUsername())) {
            alert.setUsername(config.getUsername());
        }

        Attachment alertMessage = createAlertMessage(event);
        Attachment[] attachments = {alertMessage};
        alert.setAttachments(attachments);
        return alert;
    }
}
