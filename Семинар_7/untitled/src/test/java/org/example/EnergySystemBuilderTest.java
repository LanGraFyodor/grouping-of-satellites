package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergySystemBuilderTest {
    @Test
    void buildsConfiguredEnergySystem() {
        EnergySystem energy = EnergySystem.builder()
                .minBattery(0.1)
                .lowBatteryThreshold(0.3)
                .maxBattery(2.0)
                .batteryLevel(1.5)
                .build();

        assertEquals(1.5, energy.getBatteryLevel());
        assertEquals(0.1, energy.getMinBattery());
        assertEquals(0.3, energy.getLowBatteryThreshold());
        assertEquals(2.0, energy.getMaxBattery());
        assertTrue(energy.hasSufficientPower());
    }

    @Test
    void consumesAndRechargesWithinConfiguredBounds() {
        EnergySystem energy = EnergySystem.builder()
                .batteryLevel(0.5)
                .build();

        assertTrue(energy.consume(0.2));
        assertEquals(0.3, energy.getBatteryLevel(), 0.000_001);
        assertTrue(energy.recharge(2.0));
        assertEquals(1.0, energy.getBatteryLevel());
    }

    @Test
    void rejectsInvalidConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> EnergySystem.builder()
                .batteryLevel(1.5)
                .maxBattery(1.0)
                .build());

        assertThrows(IllegalArgumentException.class, () -> EnergySystem.builder()
                .minBattery(0.5)
                .lowBatteryThreshold(0.2)
                .build());
    }
}
