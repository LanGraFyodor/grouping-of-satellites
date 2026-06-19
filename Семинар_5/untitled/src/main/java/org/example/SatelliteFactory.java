package org.example;

public abstract class SatelliteFactory {
    public abstract Satellite createSatellite(String name, double batteryLevel);

    public abstract Satellite createSatelliteWithParameter(
            String name,
            double batteryLevel,
            double parameter
    );

    protected EnergySystem createEnergySystem(double batteryLevel) {
        return EnergySystem.builder()
                .batteryLevel(batteryLevel)
                .lowBatteryThreshold(0.2)
                .minBattery(0.0)
                .maxBattery(1.0)
                .build();
    }
}
