package org.example;

import java.util.Objects;

public abstract class SatelliteParam {
    private final SatelliteType type;
    private final String name;
    private final double batteryLevel;

    protected SatelliteParam(SatelliteType type, String name, double batteryLevel) {
        this.type = Objects.requireNonNull(type, "Тип спутника обязателен");
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя спутника не может быть пустым");
        }
        this.name = name;
        this.batteryLevel = batteryLevel;
    }

    public SatelliteType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }
}
