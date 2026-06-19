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
        if (request.isActivateBeforeMission()) {
            constellationService.activateAllSatellites(request.getConstellationName());
        }

        if (request.targetsAllSatellites()) {
            constellationService.executeConstellationMission(request.getConstellationName());
        } else {
            constellationService.executeConstellationMission(
                    request.getConstellationName(),
                    request.getSatelliteType()
            );
        }
    }

    public void executeMission(ApiMissionRequest request) {
        if (request.activateBeforeMission() &&
                request.targetType() == MissionTargetType.SINGLE_SATELLITE) {
            constellationService.activateSatellite(
                    request.constellationName(),
                    request.satelliteName()
            );
        } else if (request.activateBeforeMission()) {
            constellationService.activateAllSatellites(request.constellationName());
        }
        if (request.targetType() == MissionTargetType.SINGLE_SATELLITE) {
            constellationService.executeSatelliteMission(
                    request.constellationName(),
                    request.satelliteName()
            );
        } else {
            constellationService.executeConstellationMission(request.constellationName());
        }
    }

    public ConstellationStatus getConstellationStatus(String constellationName) {
        return constellationService.getConstellationStatus(constellationName);
    }

    public int rechargeConstellation(String constellationName, double amount) {
        return constellationService.rechargeAllSatellites(constellationName, amount);
    }

    public Satellite retireSatellite(String constellationName, String satelliteName) {
        return constellationService.removeSatellite(constellationName, satelliteName);
    }

    public String getOverview() {
        return constellationService.getOverview();
    }
}
