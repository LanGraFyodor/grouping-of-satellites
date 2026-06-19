package org.example;

import org.springframework.stereotype.Component;

@Component
public class CommunicationSatelliteFactory implements SatelliteFactory {
    @Override
    public Satellite createSatelliteWithParameter(SatelliteParam param) {
        if (!(param instanceof CommunicationSatelliteParam communicationParam)) {
            throw new SpaceOperationException(
                    "CommunicationSatelliteFactory не поддерживает параметр " +
                            parameterName(param)
            );
        }

        return new CommunicationSatellite(
                communicationParam.getName(),
                createEnergySystem(communicationParam.getBatteryLevel()),
                communicationParam.getBandwidth()
        );
    }

    @Override
    public boolean isSatelliteTypeSupported(SatelliteType type) {
        return type == SatelliteType.COMMUNICATION;
    }

    private EnergySystem createEnergySystem(double batteryLevel) {
        return EnergySystem.builder()
                .batteryLevel(batteryLevel)
                .lowBatteryThreshold(0.2)
                .minBattery(0.0)
                .maxBattery(1.0)
                .build();
    }

    private String parameterName(SatelliteParam param) {
        return param == null ? "null" : param.getClass().getSimpleName();
    }
}
