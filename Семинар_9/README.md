# Семинар 9 — Docker и Docker Compose

Два Spring Boot-приложения из Семинара 8 упакованы в отдельные Docker-образы и
запускаются совместно через Docker Compose.

## Архитектура

```text
браузер / Postman
        │ localhost:8080
        ▼
space-operation-center (service: server)
        ▲
        │ http://server:8080/api
        │ пользовательская Docker-сеть
        │
mission-scheduler (service: mission-service)
        │ localhost:8081
```

- `server` — основной REST-сервис и Swagger UI;
- `mission-service` — планировщик, вызывающий сервер через `RestClient`;
- `satellite-seminar-9-network` — явная пользовательская bridge-сеть;
- имя `server` используется встроенным DNS Docker.

## Требования

- Docker Engine или Docker Desktop;
- Docker Compose v2.

Для запуска через Docker локальная установка Maven и Java не требуется.

## Сборка и запуск

Из каталога `Семинар_9`:

```powershell
docker compose up --build -d --wait
```

Проверить состояние:

```powershell
docker compose ps
```

Оба контейнера должны иметь статус `healthy`.

## Адреса

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- основной REST API: `http://localhost:8080/api`
- health сервера: `http://localhost:8080/actuator/health`
- health планировщика: `http://localhost:8081/actuator/health`

Swagger обычно отвечает перенаправлением на фактическую страницу UI — это
нормальное поведение.

## Переменные окружения

Compose передаёт приложениям внешнюю конфигурацию:

| Сервис | Переменная | Значение |
|---|---|---|
| `server` | `SERVER_PORT` | `8080` |
| `mission-service` | `SERVER_PORT` | `8081` |
| `mission-service` | `SERVER_URL` | `http://server:8080/api` |

Локальные значения по умолчанию остаются в `application.yml`, поэтому
приложения можно запускать и без Docker.

## Dockerfile

Оба Dockerfile используют multi-stage build:

1. `maven:3.9.9-eclipse-temurin-21-alpine` компилирует приложение;
2. `eclipse-temurin:21-jre-alpine` запускает только готовый JAR.

В runtime-образ не копируются исходники, README и каталог `target`. Приложения
запускаются непривилегированным пользователем `spring`, а не `root`.

Проверка пользователя:

```powershell
docker compose exec server id
docker compose exec mission-service id
```

## Healthcheck и порядок запуска

Compose проверяет readiness endpoint:

```text
/actuator/health/readiness
```

`mission-service` использует:

```yaml
depends_on:
  server:
    condition: service_healthy
```

Поэтому планировщик запускается только после готовности основного сервиса.

## Быстрая проверка API

Создать спутник и группировку:

```powershell
$body = @{
  constellationName = "TestConstellation"
  createConstellationIfMissing = $true
  satelliteParam = @{
    type = "COMMUNICATION"
    name = "Docker-Sat"
    batteryLevel = 0.9
    bandwidth = 500.0
  }
} | ConvertTo-Json -Depth 5

Invoke-RestMethod `
  -Method Post `
  -Uri "http://localhost:8080/api/add-satellites" `
  -ContentType "application/json" `
  -Body $body
```

В конфигурации планировщика миссия для `TestConstellation` запускается каждую
минуту. После её выполнения в логах появится сообщение об успешной отправке:

```powershell
docker compose logs -f mission-service
docker compose logs -f server
```

Если группировка ещё не создана, сервер вернёт `400`, планировщик запишет ошибку
в лог и продолжит выполнять следующие задачи.

## Тесты без Docker

```powershell
cd .\space-operation-center
mvn test

cd ..\mission-scheduler
mvn test
```

## Остановка

```powershell
docker compose down
```

Удаление контейнеров и локально собранных образов:

```powershell
docker compose down --rmi local
```

Образы имеют явные версии:

- `satellite/space-operation-center:1.0.0`;
- `satellite/mission-scheduler:1.0.0`.
