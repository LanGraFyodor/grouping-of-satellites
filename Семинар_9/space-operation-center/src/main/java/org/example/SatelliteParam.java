package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = CommunicationSatelliteParam.class,
                name = "COMMUNICATION"
        ),
        @JsonSubTypes.Type(
                value = ImagingSatelliteParam.class,
                name = "IMAGE"
        )
})
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
