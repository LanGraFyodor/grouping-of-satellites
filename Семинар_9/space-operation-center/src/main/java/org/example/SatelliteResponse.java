package org.example;

public record SatelliteResponse(
        String name,
        SatelliteType type,
        double batteryLevel,
        boolean active
) {
    public static SatelliteResponse from(Satellite satellite) {
        return new SatelliteResponse(
                satellite.getName(),
                satellite.getType(),
                satellite.getEnergy().getBatteryLevel(),
                satellite.getState().isActive()
        );
    }
}
