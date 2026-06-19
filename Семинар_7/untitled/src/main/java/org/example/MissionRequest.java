package org.example;

public final class MissionRequest {
    private final String constellationName;
    private final MissionTargetType targetType;
    private final SatelliteType satelliteType;
    private final String satelliteName;
    private final boolean activateBeforeMission;

    public MissionRequest(
            String constellationName,
            SatelliteType satelliteType,
            boolean activateBeforeMission
    ) {
        this(
                constellationName,
                satelliteType == null
                        ? MissionTargetType.CONSTELLATION
                        : MissionTargetType.SATELLITE_TYPE,
                satelliteType,
                null,
                activateBeforeMission
        );
    }

    public MissionRequest(
            String constellationName,
            MissionTargetType targetType,
            SatelliteType satelliteType,
            String satelliteName,
            boolean activateBeforeMission
    ) {
        if (constellationName == null || constellationName.isBlank()) {
            throw new IllegalArgumentException("Название группировки не может быть пустым");
        }
        if (targetType == null) {
            throw new IllegalArgumentException("Тип цели миссии обязателен");
        }
        if (targetType == MissionTargetType.SATELLITE_TYPE && satelliteType == null) {
            throw new IllegalArgumentException("Для SATELLITE_TYPE необходимо указать тип спутника");
        }
        if (targetType == MissionTargetType.SINGLE_SATELLITE &&
                (satelliteName == null || satelliteName.isBlank())) {
            throw new IllegalArgumentException(
                    "Для SINGLE_SATELLITE необходимо указать имя спутника"
            );
        }
        this.constellationName = constellationName;
        this.targetType = targetType;
        this.satelliteType = satelliteType;
        this.satelliteName = satelliteName;
        this.activateBeforeMission = activateBeforeMission;
    }

    public static MissionRequest forAll(String constellationName, boolean activateBeforeMission) {
        return new MissionRequest(constellationName, null, activateBeforeMission);
    }

    public static MissionRequest forSatellite(
            String constellationName,
            String satelliteName,
            boolean activateBeforeMission
    ) {
        return new MissionRequest(
                constellationName,
                MissionTargetType.SINGLE_SATELLITE,
                null,
                satelliteName,
                activateBeforeMission
        );
    }

    public String getConstellationName() {
        return constellationName;
    }

    public MissionTargetType getTargetType() {
        return targetType;
    }

    public SatelliteType getSatelliteType() {
        return satelliteType;
    }

    public String getSatelliteName() {
        return satelliteName;
    }

    public boolean isActivateBeforeMission() {
        return activateBeforeMission;
    }

    public boolean targetsAllSatellites() {
        return targetType == MissionTargetType.CONSTELLATION;
    }
}
