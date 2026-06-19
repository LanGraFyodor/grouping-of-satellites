package org.example.scheduler.service;

import jakarta.annotation.PostConstruct;
import org.example.scheduler.client.SpaceOperationGateway;
import org.example.scheduler.configuration.SpaceCenterProperties;
import org.example.scheduler.domain.MissionDefinition;
import org.example.scheduler.domain.MissionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ConfiguredMissionScheduler {
    private static final Logger log =
            LoggerFactory.getLogger(ConfiguredMissionScheduler.class);

    private final TaskScheduler taskScheduler;
    private final SpaceOperationGateway operationClient;
    private final SpaceCenterProperties properties;
    private final List<MissionDefinition> registeredMissions = new ArrayList<>();

    public ConfiguredMissionScheduler(
            TaskScheduler taskScheduler,
            SpaceOperationGateway operationClient,
            SpaceCenterProperties properties
    ) {
        this.taskScheduler = taskScheduler;
        this.operationClient = operationClient;
        this.properties = properties;
    }

    @PostConstruct
    public void registerConfiguredMissions() {
        for (MissionDefinition mission : properties.missions()) {
            registerMission(mission);
        }
        log.info("Зарегистрировано миссий: {}", registeredMissions.size());
    }

    public void registerMission(MissionDefinition mission) {
        mission.validate();
        CronTrigger trigger = new CronTrigger(mission.cron());
        taskScheduler.schedule(() -> executeSafely(mission), trigger);
        registeredMissions.add(mission);
        log.info(
                "Миссия {} для группировки '{}' запланирована: {}",
                mission.targetType(),
                mission.constellationName(),
                mission.cron()
        );
    }

    public List<MissionDefinition> getRegisteredMissions() {
        return Collections.unmodifiableList(registeredMissions);
    }

    void executeSafely(MissionDefinition mission) {
        log.info(
                "Запуск миссии {} для группировки '{}'",
                mission.targetType(),
                mission.constellationName()
        );
        try {
            operationClient.executeMission(MissionRequest.from(mission));
            log.info(
                    "Миссия для группировки '{}' успешно отправлена",
                    mission.constellationName()
            );
        } catch (RuntimeException exception) {
            log.error(
                    "Не удалось выполнить миссию для группировки '{}': {}",
                    mission.constellationName(),
                    exception.getMessage()
            );
        }
    }
}
