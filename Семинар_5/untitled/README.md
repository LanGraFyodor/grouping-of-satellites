# Семинар 5 — Factory и Builder

Проект продолжает систему управления спутниковой группировкой и демонстрирует
два порождающих паттерна GoF.

## Factory Method

Абстрактная фабрика `SatelliteFactory` задаёт общий контракт:

- `createSatellite(name, batteryLevel)` создаёт спутник с параметром по умолчанию;
- `createSatelliteWithParameter(name, batteryLevel, parameter)` создаёт настроенный спутник.

Конкретные фабрики:

- `CommunicationSatelliteFactory`;
- `ImagingSatelliteFactory`.

Клиентский код работает с типом `SatelliteFactory` и не вызывает конструкторы
конкретных спутников.

## Builder

`EnergySystem` создаётся только через `EnergySystem.builder()`. Builder отвечает
за значения по умолчанию и проверяет:

- минимальный и максимальный заряд;
- критический порог;
- допустимость начального уровня заряда.

Пример:

```java
EnergySystem energy = EnergySystem.builder()
        .batteryLevel(0.8)
        .lowBatteryThreshold(0.2)
        .minBattery(0.0)
        .maxBattery(1.0)
        .build();
```

## Проверка

```shell
mvn test
mvn spring-boot:run
```

Тесты проверяют обе конкретные фабрики, параметры создаваемых спутников,
значения Builder, валидацию и операции расхода/подзарядки.
