# Mission Scheduler

Отдельный микросервис-планировщик на порту `8081`. База данных не используется:
конфигурация хранится в YAML, зарегистрированные задачи — в памяти.

## Запуск

Сначала запустите `space-operation-center`, затем:

```powershell
mvn test
mvn spring-boot:run
```

## Конфигурация

Настройки находятся в `src/main/resources/application.yml`:

```yaml
server:
  port: 8081

app:
  space-center-service:
    url: "http://localhost:8080/api"
    missions:
      - targetType: CONSTELLATION
        constellationName: "GeoStationary"
        cron: "0 0 */6 * * *"

      - targetType: SINGLE_SATELLITE
        constellationName: "LowOrbit"
        satelliteName: "Sat-1"
        cron: "0 30 8 * * MON"
```

Поля миссии:

- `targetType` — `CONSTELLATION` или `SINGLE_SATELLITE`;
- `constellationName` — имя группировки;
- `satelliteName` — обязательно только для `SINGLE_SATELLITE`;
- `cron` — шестипольное cron-выражение Spring, первым полем идут секунды.

Для `CONSTELLATION` поле `satelliteName` указывать нельзя.

## Логика работы

При старте `ConfiguredMissionScheduler`:

1. читает список из `SpaceCenterProperties`;
2. валидирует каждую миссию;
3. регистрирует `Runnable` через `TaskScheduler` и `CronTrigger`;
4. в назначенное время отправляет `POST /api/missions` через `RestClient`;
5. логирует успех или ошибку.

Ошибка основного сервиса не останавливает планировщик и не отменяет остальные
задачи.

Основной сервис хранит данные в памяти. Группировки из YAML необходимо заранее
создать через его REST API, иначе планировщик получит `400 Bad Request`, запишет
ошибку в лог и продолжит работу.
