package org.example.scheduler;

import org.example.scheduler.domain.MissionDefinition;
import org.example.scheduler.domain.MissionTargetType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MissionDefinitionTest {
    @Test
    void validatesTargetSpecificSatelliteName() {
        assertDoesNotThrow(() -> new MissionDefinition(
                MissionTargetType.SINGLE_SATELLITE,
                "LowOrbit",
                "Sat-1",
                "0 0 * * * *"
        ).validate());

        assertThrows(IllegalArgumentException.class, () -> new MissionDefinition(
                MissionTargetType.SINGLE_SATELLITE,
                "LowOrbit",
                null,
                "0 0 * * * *"
        ).validate());

        assertThrows(IllegalArgumentException.class, () -> new MissionDefinition(
                MissionTargetType.CONSTELLATION,
                "Geo",
                "Лишний",
                "0 0 * * * *"
        ).validate());
    }
}
