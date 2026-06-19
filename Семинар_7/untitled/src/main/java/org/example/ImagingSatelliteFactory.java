package org.example;

import org.springframework.stereotype.Component;

@Component
public class ImagingSatelliteFactory implements SatelliteFactory {
    @Override
    public Satellite createSatelliteWithParameter(SatelliteParam param) {
        if (!(param instanceof ImagingSatelliteParam imagingParam)) {
            throw new SpaceOperationException(
                    "ImagingSatelliteFactory не поддерживает параметр " +
                            parameterName(param)
            );
        }

        return new ImagingSatellite(
                imagingParam.getName(),
                createEnergySystem(imagingParam.getBatteryLevel()),
                imagingParam.getResolution()
        );
    }

    @Override
    public boolean isSatelliteTypeSupported(SatelliteType type) {
        return type == SatelliteType.IMAGE;
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
