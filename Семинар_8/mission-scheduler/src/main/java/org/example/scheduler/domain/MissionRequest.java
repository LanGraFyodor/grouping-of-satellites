package org.example.scheduler.domain;

public record MissionRequest(
        MissionTargetType targetType,
        String constellationName,
        String satelliteName,
        boolean activateBeforeMission
) {
    public static MissionRequest from(MissionDefinition definition) {
        return new MissionRequest(
                definition.targetType(),
                definition.constellationName(),
                definition.satelliteName(),
                true
        );
    }
}
