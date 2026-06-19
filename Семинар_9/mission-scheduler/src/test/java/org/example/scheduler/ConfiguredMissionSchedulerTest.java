package org.example.scheduler;

import org.example.scheduler.client.SpaceOperationGateway;
import org.example.scheduler.configuration.SpaceCenterProperties;
import org.example.scheduler.domain.MissionDefinition;
import org.example.scheduler.domain.MissionRequest;
import org.example.scheduler.domain.MissionTargetType;
import org.example.scheduler.service.ConfiguredMissionScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfiguredMissionSchedulerTest {
    @Test
    void registersConfiguredMissionsAndExecutesScheduledTask() {
        CapturingTaskScheduler taskScheduler = new CapturingTaskScheduler();
        CapturingGateway gateway = new CapturingGateway();
        MissionDefinition mission = new MissionDefinition(
                MissionTargetType.CONSTELLATION,
                "Geo",
                null,
                "0 0 * * * *"
        );
        ConfiguredMissionScheduler scheduler = new ConfiguredMissionScheduler(
                taskScheduler,
                gateway,
                new SpaceCenterProperties(
                        "http://localhost:8080/api",
                        List.of(mission)
                )
        );

        scheduler.registerConfiguredMissions();

        assertEquals(1, scheduler.getRegisteredMissions().size());
        assertEquals(1, taskScheduler.tasks.size());

        taskScheduler.tasks.getFirst().run();
        assertEquals(1, gateway.requests.size());
        assertEquals("Geo", gateway.requests.getFirst().constellationName());
    }

    private static final class CapturingGateway implements SpaceOperationGateway {
        private final List<MissionRequest> requests = new ArrayList<>();

        @Override
        public void executeMission(MissionRequest request) {
            requests.add(request);
        }
    }

    private static final class CapturingTaskScheduler implements TaskScheduler {
        private final List<Runnable> tasks = new ArrayList<>();

        @Override
        public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
            tasks.add(task);
            return null;
        }

        @Override
        public ScheduledFuture<?> schedule(Runnable task, Instant startTime) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(
                Runnable task,
                Instant startTime,
                Duration period
        ) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(
                Runnable task,
                Instant startTime,
                Duration delay
        ) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Duration delay) {
            throw new UnsupportedOperationException();
        }
    }
}
