package com.appdynamics.extensions.slack;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by balakrishnavadavalasa on 17/02/16.
 */
public class EventArgs {

    private void generateAccountInfo(List<String> strings) {
        strings.add("customer1");
        strings.add("12sab23asl23");
    }

    public String[] getHealthRuleViolationEventWithOneEvalEntityAndTriggerNoBaseline() {
        List<String> strings = Lists.newArrayList();
        generateLeadingArgs(strings);

        strings.add("\"1\"");  //number of eval entities
        strings.add("\"APPLICATION_COMPONENT_NODE\"");   //eval entity type
        strings.add("\"MyMacMachineAgentNode1\""); //eval entity name
        strings.add("\"8\"");  //eval entity id
        strings.add("\"1\"");  //number of triggered cond per eval entity
        strings.add("\"APPLICATION_COMPONENT_NODE\""); //scope type 1
        strings.add("\"MyMacMachineAgentNode1\""); //scope name 1
        strings.add("\"8\"");  //scope id 1
        strings.add("\"Hardware Resources|CPU|%Busy Condition\""); // condition name 1
        strings.add("\"113\"");  //condition id 1
        strings.add("\"GREATER_THAN\"");  //operator 1
        strings.add("\"ABSOLUTE\"");  //condition unit type 1
        strings.add("\"4\"");  //threshhold value 1
        strings.add("\"40.0\"");  //observed value 1

        generateTrailingArg(strings);
        generateAccountInfo(strings);
        return Iterables.toArray(strings, String.class);
    }

    public String[] getOtherEvent() {
        List<String> strings = Lists.newArrayList();
        strings.add("\"MyMacMachineAgent\""); //appname
        strings.add("\"2\""); //appID
        strings.add("\"Wed Apr 30 09:42:55 PDT 2014\""); //event notification time
        strings.add("\"1\""); //priority
        strings.add("\"WARN\""); //severity
        strings.add("\"SlackAction\"");  //tag
        strings.add("\"App Server restart\"");  //event notification name
        strings.add("\"24\"");  //event notification id
        strings.add("\"2\"");  //event notification time period in mins
        strings.add("\"2\"");  //number of event types

        strings.add("\"ERROR\"");  //event type 1
        strings.add("\"2\""); //event type num 1
        strings.add("\"AGENT_STATUS\"");  //event type 1
        strings.add("\"1\""); //event type num 1

        strings.add("\"2\""); // num event summaries

        strings.add("\"I\""); //event summary id 1
        strings.add("\"Wed Feb 17 09:42:55 IST 2016\""); //event summary time 1
        strings.add("APP_SERVER_RESTART"); //event summary type 1
        strings.add("ERROR"); //event summary severity 1
        strings.add("summary string"); //event summary string 1

        strings.add("\"II\""); //event summary id 2
        strings.add("\"Wed Feb 17 09:42:55 IST 2016\""); //event summary time 2
        strings.add("DIAGNOSTIC_SESSION"); //event summary type 2
        strings.add("WARN"); //event summary severity 2
        strings.add("summary string"); //event summary string 2

        strings.add("\"http://localhost:8090/controller/#location=APP_EVENT_VIEWER_MODAL&eventSummary=\"");
        generateAccountInfo(strings);
        return Iterables.toArray(strings, String.class);
    }

    private void generateTrailingArg(List<String> strings) {
        //summary message
        strings.add("\"CPU utilization is too high triggerded at Wed Feb 17 09:42:55 IST 2016. This policy was violated because the following conditions were met for the MyMacMachineAgentNode1 Node for the last 1 minute(s):   For Evaluation Entity: MyMacMachineAgentNode1 Node - Hardware Resources|CPU|%Busy Condition is greater than 4. Observed value = 40.0\"");  //observed value 1
        strings.add("\"3\"");  //incident id
        strings.add("\"http://localhost:8090/controller/#location=APP_INCIDENT_DETAIL&incident=\"");  //deep link url
        strings.add("\"POLICY_CANCELED_CRITICAL\"");  //event type
    }

    private void generateLeadingArgs(List<String> strings) {
        strings.add("\"SlackTestApp\""); //appname
        strings.add("\"4\""); //appID
        strings.add("\"Wed Feb 17 09:42:55 IST 2016\""); //pvn alert time
        strings.add("\"1\""); //priority
        strings.add("\"WARN\""); //severity
        strings.add("\"SlackAction\"");  //tag
        strings.add("\"CPU utilization is too high\"");  //health rule name
        strings.add("\"24\"");  //health rule id
        strings.add("\"1\"");  //pvn time period in min
        strings.add("\"APPLICATION_COMPONENT_NODE\"");  //affected entity type
        strings.add("\"MyMacMachineAgentNode1\""); //affected entity name
        strings.add("\"8\"");  //affected entity id
    }
}
