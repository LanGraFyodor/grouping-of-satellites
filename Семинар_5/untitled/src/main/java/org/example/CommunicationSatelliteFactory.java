package org.example;

public class CommunicationSatelliteFactory extends SatelliteFactory {
    private static final double DEFAULT_BANDWIDTH = 100.0;

    @Override
    public Satellite createSatellite(String name, double batteryLevel) {
        return createSatelliteWithParameter(name, batteryLevel, DEFAULT_BANDWIDTH);
    }

    @Override
    public Satellite createSatelliteWithParameter(
            String name,
            double batteryLevel,
            double bandwidth
    ) {
        return new CommunicationSatellite(
                name,
                createEnergySystem(batteryLevel),
                bandwidth
        );
    }
}
