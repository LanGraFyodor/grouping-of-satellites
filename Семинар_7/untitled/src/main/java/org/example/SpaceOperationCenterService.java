package org.example;

import org.springframework.stereotype.Service;

@Service
@MeasureExecutionTime
public class SpaceOperationCenterService {
    private final SatelliteService satelliteService;
    private final ConstellationService constellationService;

    public SpaceOperationCenterService(
            SatelliteService satelliteService,
            ConstellationService constellationService
    ) {
        this.satelliteService = satelliteService;
        this.constellationService = constellationService;
    }

    public void createConstellation(String constellationName) {
        constellationService.createAndSaveConstellation(constellationName);
    }

    public Satellite addSatellite(AddSatelliteRequest request) {
        String constellationName = request.getConstellationName();
        if (!constellationService.containsConstellation(constellationName)) {
            if (!request.isCreateConstellationIfMissing()) {
                throw new SpaceOperationException(
                        "Группировка не найдена: " + constellationName
                );
            }
            constellationService.createAndSaveConstellation(constellationName);
        }

        Satellite satellite = satelliteService.createSatellite(request.getSatelliteParam());
        constellationService.addSatelliteToConstellation(constellationName, satellite);
        return satellite;
    }

    public void executeMission(MissionRequest request) {
        if (request.isActivateBeforeMission() &&
                request.getTargetType() == MissionTargetType.SINGLE_SATELLITE) {
            constellationService.activateSatellite(
                    request.getConstellationName(),
                    request.getSatelliteName()
            );
        } else if (request.isActivateBeforeMission()) {
            constellationService.activateAllSatellites(request.getConstellationName());
        }

        switch (request.getTargetType()) {
            case CONSTELLATION ->
                    constellationService.executeConstellationMission(
                            request.getConstellationName()
                    );
            case SATELLITE_TYPE ->
                    constellationService.executeConstellationMission(
                            request.getConstellationName(),
                            request.getSatelliteType()
                    );
            case SINGLE_SATELLITE ->
                    constellationService.executeSatelliteMission(
                            request.getConstellationName(),
                            request.getSatelliteName()
                    );
        }
    }

    public ConstellationStatus getConstellationStatus(String constellationName) {
        return constellationService.getConstellationStatus(constellationName);
    }

    public int rechargeConstellation(String constellationName, double amount) {
        return constellationService.rechargeAllSatellites(constellationName, amount);
    }
}
