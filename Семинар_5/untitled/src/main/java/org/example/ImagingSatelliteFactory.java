package org.example;

public class ImagingSatelliteFactory extends SatelliteFactory {
    private static final double DEFAULT_RESOLUTION = 1.0;

    @Override
    public Satellite createSatellite(String name, double batteryLevel) {
        return createSatelliteWithParameter(name, batteryLevel, DEFAULT_RESOLUTION);
    }

    @Override
    public Satellite createSatelliteWithParameter(
            String name,
            double batteryLevel,
            double resolution
    ) {
        return new ImagingSatellite(
                name,
                createEnergySystem(batteryLevel),
                resolution
        );
    }
}
