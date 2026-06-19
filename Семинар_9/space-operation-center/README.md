# Space Operation Center

Основной REST-сервис управления спутниковыми группировками. Работает на порту
`8080`, хранит состояние в памяти и предоставляет Swagger/OpenAPI.

Swagger UI: `http://localhost:8080/swagger-ui.html`.

## Запуск

```powershell
mvn test
mvn spring-boot:run
```

## API

- `POST /api/constellations/{name}` — создать пустую группировку;
- `POST /api/add-satellites` — создать спутник и добавить его в группировку;
- `POST /api/missions` — выполнить миссию;
- `GET /api/overview` — получить общую текстовую сводку;
- `GET /api/constellations/{name}/status` — получить статус группировки;
- `POST /api/constellations/{name}/recharge` — зарядить её спутники;
- `DELETE /api/constellations/{name}/satellites/{satellite}` — вывести спутник
  из эксплуатации.

## Добавление спутника

Поле `satelliteParam.type` определяет конкретный класс параметров.

Спутник связи:

```json
{
  "constellationName": "LowOrbit",
  "createConstellationIfMissing": true,
  "satelliteParam": {
    "type": "COMMUNICATION",
    "name": "Sat-1",
    "batteryLevel": 0.9,
    "bandwidth": 500.0
  }
}
```

```powershell
curl.exe -X POST http://localhost:8080/api/add-satellites `
  -H "Content-Type: application/json" `
  -d '@satellite.json'
```

Для спутника съёмки используются `type: IMAGE` и поле `resolution`.

## Выполнение миссии

Вся группировка:

```json
{
  "targetType": "CONSTELLATION",
  "constellationName": "LowOrbit",
  "satelliteName": null,
  "activateBeforeMission": true
}
```

Один спутник:

```json
{
  "targetType": "SINGLE_SATELLITE",
  "constellationName": "LowOrbit",
  "satelliteName": "Sat-1",
  "activateBeforeMission": true
}
```

При `SINGLE_SATELLITE` активируется и выполняет миссию только указанный спутник.

## Подзарядка

```json
{
  "amount": 0.15
}
```

Значения заряда задаются в диапазоне от `0.0` до `1.0`.
