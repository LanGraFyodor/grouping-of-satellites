package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ImagingSatelliteParam extends SatelliteParam {
    private final double resolution;

    @JsonCreator
    public ImagingSatelliteParam(
            @JsonProperty("name") String name,
            @JsonProperty("batteryLevel") double batteryLevel,
            @JsonProperty("resolution") double resolution
    ) {
        super(SatelliteType.IMAGE, name, batteryLevel);
        if (resolution <= 0) {
            throw new IllegalArgumentException("Разрешение должно быть положительным");
        }
        this.resolution = resolution;
    }

    public double getResolution() {
        return resolution;
    }
}
