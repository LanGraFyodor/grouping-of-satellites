package org.example;

public final class CommunicationSatelliteParam extends SatelliteParam {
    private final double bandwidth;

    public CommunicationSatelliteParam(String name, double batteryLevel, double bandwidth) {
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
