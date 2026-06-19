package org.example;

public final class MissionRequest {
    private final String constellationName;
    private final SatelliteType satelliteType;
    private final boolean activateBeforeMission;

    public MissionRequest(
            String constellationName,
            SatelliteType satelliteType,
            boolean activateBeforeMission
    ) {
        if (constellationName == null || constellationName.isBlank()) {
            throw new IllegalArgumentException("Название группировки не может быть пустым");
        }
        this.constellationName = constellationName;
        this.satelliteType = satelliteType;
        this.activateBeforeMission = activateBeforeMission;
    }

    public static MissionRequest forAll(String constellationName, boolean activateBeforeMission) {
        return new MissionRequest(constellationName, null, activateBeforeMission);
    }

    public String getConstellationName() {
        return constellationName;
    }

    public SatelliteType getSatelliteType() {
        return satelliteType;
    }

    public boolean isActivateBeforeMission() {
        return activateBeforeMission;
    }

    public boolean targetsAllSatellites() {
        return satelliteType == null;
    }
}
