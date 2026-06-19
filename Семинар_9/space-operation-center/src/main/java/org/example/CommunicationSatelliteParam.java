package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class CommunicationSatelliteParam extends SatelliteParam {
    private final double bandwidth;

    @JsonCreator
    public CommunicationSatelliteParam(
            @JsonProperty("name") String name,
            @JsonProperty("batteryLevel") double batteryLevel,
            @JsonProperty("bandwidth") double bandwidth
    ) {
        super(SatelliteType.COMMUNICATION, name, batteryLevel);
        if (bandwidth <= 0) {
            throw new IllegalArgumentException("Пропускная способность должна быть положительной");
        }
        this.bandwidth = bandwidth;
    }

    public double getBandwidth() {
        return bandwidth;
    }
}
