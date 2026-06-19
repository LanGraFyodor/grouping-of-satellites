package org.example;

public record ApiMissionRequest(
        MissionTargetType targetType,
        String constellationName,
        String satelliteName,
        boolean activateBeforeMission
) {
    public ApiMissionRequest {
        if (targetType == null) {
            throw new IllegalArgumentException("Тип цели миссии обязателен");
        }
        if (constellationName == null || constellationName.isBlank()) {
            throw new IllegalArgumentException("Название группировки обязательно");
        }
        if (targetType == MissionTargetType.SINGLE_SATELLITE &&
                (satelliteName == null || satelliteName.isBlank())) {
            throw new IllegalArgumentException(
                    "Для SINGLE_SATELLITE необходимо указать satelliteName"
            );
        }
        if (targetType == MissionTargetType.CONSTELLATION &&
                satelliteName != null && !satelliteName.isBlank()) {
            throw new IllegalArgumentException(
                    "Для CONSTELLATION поле satelliteName указывать не нужно"
            );
        }
    }
}
