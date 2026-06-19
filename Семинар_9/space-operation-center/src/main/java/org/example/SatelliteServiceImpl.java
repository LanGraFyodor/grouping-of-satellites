package org.example;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SatelliteServiceImpl implements SatelliteService {
    private final List<SatelliteFactory> factories;

    public SatelliteServiceImpl(List<SatelliteFactory> factories) {
        this.factories = List.copyOf(factories);
    }

    @Override
    public Satellite createSatellite(SatelliteParam param) {
        Objects.requireNonNull(param, "Параметры спутника обязательны");

        SatelliteFactory factory = factories.stream()
                .filter(candidate -> candidate.isSatelliteTypeSupported(param.getType()))
                .findFirst()
                .orElseThrow(() -> new SpaceOperationException(
                        "Не найдена фабрика для типа спутника " + param.getType()
                ));

        return factory.createSatelliteWithParameter(param);
    }
}
