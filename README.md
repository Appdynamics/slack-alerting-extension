# AppDynamics Slack - Alerting Extension

This extension works only with a dedicated SaaS controller or an on-prem controller.

## Use Case

The Slack alerting extension enables AppDynamics to post custom notifications as messages to Slack channels. Users can see a brief description of the health rule violation or event and get more detail on AppDynamics by following the URL provided in the alert message.
This extension utilizes Slack Incoming Webhooks to post messages into slack.

### Prerequisites

- Webhook URL is required for this extension to post events to Slack. Setup incoming Webhook integration for your slack team if not configured. For details, please refer [here](https://api.slack.com/incoming-webhooks)

## Installation Steps

1. To build from source, clone this repository and run `mvn clean install`. This will produce a slack-alert.zip in the target directory. Alternatively, download the latest release archive from [GitHub](https://github.com/Appdynamics/slack-alerting-extension/releases)

2. Unzip the slack-alert.zip file into `<CONTROLLER_HOME_DIR>/custom/actions/`.

3. Check if you have custom.xml file in `<CONTROLLER_HOME_DIR>/custom/actions/` directory. If yes, add the following xml to the <custom-actions> element.
       
      ```
          <action>
                  <type>slack-alert</type>
              <!-- For Linux/Unix *.sh -->
                  <executable>slack-alert.sh</executable>
              <!-- For windows *.bat -->
                  <!--<executable>slack-alert.bat</executable>-->
          </action>
      ```
       
   If you don't have custom.xml already, create one with the below xml content   
       
      ```
          <custom-actions>
             <action>
                <type>slack-alert</type>
                <!-- For Linux/Unix *.sh -->
                <executable>slack-alert.sh</executable>
                <!-- For windows *.bat -->
               <!--<executable>slack-alert.bat</executable>-->
             </action>
          </custom-actions>
      ```
            
   Uncomment the appropriate executable tag based on windows or linux/unix machine.

4. Update the config.yml file in `<CONTROLLER_HOME_DIR>/custom/actions/slack-alert/conf/` directory with the required parameters. Please make sure to not use tab (\t) while editing yaml files. You may want to validate the yaml file using a yaml validator http://yamllint.com/
  
      ```
      # Slack Webhook URL to post events from AppD to Slack
      # https://hooks.slack.com/services/****/***/*****)
      webhookUrl: ""    
      # (optional) Overrides channel and username configured when creating Webhook.
      # Specify if events to be posted to a different channel and with a different identity.
      channel: ""
      username: ""
      #http timeouts
      connectTimeout: 10000
      socketTimeout: 10000 
      ```
  
5. Please refer to the following docs to create Custom Actions

     * [Creating custom action](http://docs.appdynamics.com/display/PRO14S/Custom+Actions)
     * [Build an Alerting Extension](http://docs.appdynamics.com/display/PRO14S/Build+an+Alerting+Extension)
         
   Now you are ready to use this extension as a custom action. In the AppDynamics UI, go to Alert & Respond -> Actions. Click Create Action. Select Custom Action and click OK. In the drop-down menu you can find the action called 'slack-alert'.

6. Look for the newest created message in Slack.


## Contributing

Find out more in the [AppDynamics Exchange](https://www.appdynamics.com/community/exchange/extension/slack-alerting-extension/)

## Support

For any questions or feature request, please contact [AppDynamics Center of Excellence](mailto:help@appdynamics.com).

**Version:** 1.0
**Controller Compatibility:** 3.7+
