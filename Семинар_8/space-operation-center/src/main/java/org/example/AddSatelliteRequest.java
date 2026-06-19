package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class AddSatelliteRequest {
    private final String constellationName;
    private final SatelliteParam satelliteParam;
    private final boolean createConstellationIfMissing;

    @JsonCreator
    public AddSatelliteRequest(
            @JsonProperty("constellationName") String constellationName,
            @JsonProperty("satelliteParam") SatelliteParam satelliteParam,
            @JsonProperty("createConstellationIfMissing") boolean createConstellationIfMissing
    ) {
        if (constellationName == null || constellationName.isBlank()) {
            throw new IllegalArgumentException("Название группировки не может быть пустым");
        }
        this.constellationName = constellationName;
        this.satelliteParam = Objects.requireNonNull(
                satelliteParam,
                "Параметры спутника обязательны"
        );
        this.createConstellationIfMissing = createConstellationIfMissing;
    }

    public String getConstellationName() {
        return constellationName;
    }

    public SatelliteParam getSatelliteParam() {
        return satelliteParam;
    }

    public boolean isCreateConstellationIfMissing() {
        return createConstellationIfMissing;
    }
}
