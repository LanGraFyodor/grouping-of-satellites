# Семинар 8 — REST API и микросервис-планировщик

Решение состоит из двух независимых Spring Boot-приложений:

- `space-operation-center` — основной сервис управления спутниками, порт `8080`;
- `mission-scheduler` — планировщик миссий, порт `8081`.

## Запуск

В первом терминале:

```powershell
cd .\space-operation-center
mvn spring-boot:run
```

Во втором терминале:

```powershell
cd .\mission-scheduler
mvn spring-boot:run
```

Основной сервис необходимо запускать первым. Swagger UI доступен по адресу
`http://localhost:8080/swagger-ui.html`.

## Проверка

```powershell
cd .\space-operation-center
mvn test

cd ..\mission-scheduler
mvn test
```

Основной сервис хранит группировки только в оперативной памяти. Поэтому перед
выполнением запланированной миссии соответствующая группировка и спутники
должны быть созданы через REST API.

Подробные форматы запросов и конфигурации приведены в README каждого приложения.
