package org.example.scheduler.domain;

public record MissionDefinition(
        MissionTargetType targetType,
        String constellationName,
        String satelliteName,
        String cron
) {
    public void validate() {
        if (targetType == null) {
            throw new IllegalArgumentException("targetType обязателен");
        }
        if (constellationName == null || constellationName.isBlank()) {
            throw new IllegalArgumentException("constellationName обязателен");
        }
        if (cron == null || cron.isBlank()) {
            throw new IllegalArgumentException("cron обязателен");
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
                    "Для CONSTELLATION поле satelliteName должно отсутствовать"
            );
        }
    }
}
