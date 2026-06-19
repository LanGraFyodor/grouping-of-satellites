package org.example.scheduler;

import org.example.scheduler.configuration.SpaceCenterProperties;
import org.example.scheduler.service.ConfiguredMissionScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionSchedulerContextTest {
    @Autowired
    private SpaceCenterProperties properties;

    @Autowired
    private ConfiguredMissionScheduler scheduler;

    @Test
    void loadsYamlAndRegistersAllConfiguredMissions() {
        assertEquals("http://localhost:8080/api", properties.url());
        assertEquals(3, properties.missions().size());
        assertEquals(3, scheduler.getRegisteredMissions().size());
    }
}
