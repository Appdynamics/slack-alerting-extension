package com.appdynamics.extensions.slack;

import com.appdynamics.extensions.yml.YmlReader;
import org.junit.Test;

/**
 * Created by balakrishnavadavalasa on 17/02/16.
 */
public class SlackAlertExtensionTest {

    EventArgs eventArgs = new EventArgs();

    @Test
    public void canPostHealthRuleViolationEvent() {
        Configuration configuration = YmlReader.readFromFile(this.getClass().getResource("/conf/config.yml").getFile(), Configuration.class);
        SlackAlertExtension alertExtension = new SlackAlertExtension(configuration);
        alertExtension.processAnEvent(eventArgs.getHealthRuleViolationEventWithOneEvalEntityAndTriggerNoBaseline());
    }

    @Test
    public void canPostOtherEvent() {
        Configuration configuration = YmlReader.readFromFile(this.getClass().getResource("/conf/config.yml").getFile(), Configuration.class);
        SlackAlertExtension alertExtension = new SlackAlertExtension(configuration);
        alertExtension.processAnEvent(eventArgs.getOtherEvent());
    }

}
