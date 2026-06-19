package org.example.scheduler.configuration;

import org.example.scheduler.domain.MissionDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.space-center-service")
public record SpaceCenterProperties(
        String url,
        List<MissionDefinition> missions
) {
    public SpaceCenterProperties {
        missions = missions == null ? List.of() : List.copyOf(missions);
    }
}
