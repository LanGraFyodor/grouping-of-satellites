package org.example;

public record ConstellationStatus(
        String constellationName,
        int satelliteCount,
        int activeSatelliteCount
) {
}
