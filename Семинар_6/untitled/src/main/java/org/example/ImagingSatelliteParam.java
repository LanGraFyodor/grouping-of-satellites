package org.example;

public final class ImagingSatelliteParam extends SatelliteParam {
    private final double resolution;

    public ImagingSatelliteParam(String name, double batteryLevel, double resolution) {
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
