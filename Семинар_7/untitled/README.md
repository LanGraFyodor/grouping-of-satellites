# Семинар 7 — Facade и AOP-декоратор

Проект продолжает систему управления спутниковой группировкой и добавляет
структурный паттерн Facade, а также прокси-декоратор на основе Spring AOP.

## Facade

Прежний `SpaceOperationCenterService`, который управлял только группировками,
переименован в `ConstellationService`.

Новый `SpaceOperationCenterService` — единая точка входа в бизнес-сценарии. Он
координирует:

- `SatelliteService`, выбирающий фабрику и создающий спутник;
- `ConstellationService`, управляющий группировками и миссиями.

Основные операции фасада:

```java
Satellite addSatellite(AddSatelliteRequest request);
void executeMission(MissionRequest request);
void createConstellation(String name);
ConstellationStatus getConstellationStatus(String name);
int rechargeConstellation(String name, double amount);
```

`AddSatelliteRequest` позволяет одной операцией создать отсутствующую
группировку, создать спутник через фабрику и добавить его в группировку.

`MissionRequest` задаёт группировку, необходимость предварительной активации и
цель миссии:

- `CONSTELLATION` — вся группировка;
- `SATELLITE_TYPE` — все спутники выбранного типа;
- `SINGLE_SATELLITE` — конкретный спутник по имени.

Для точечной миссии активируется только выбранный спутник.

## Decorator и Spring AOP

Создана аннотация:

```java
@MeasureExecutionTime
```

`ExecutionTimeAspect` перехватывает аннотированные методы через `@Around`,
измеряет время выполнения и выводит результат в консоль. Фасад помечен этой
аннотацией целиком, поэтому Spring оборачивает его прокси-объектом с
дополнительным поведением без изменения бизнес-методов.

Пример вывода:

```text
⏱ SpaceOperationCenterService.addSatellite(..) выполнен за 1.245 мс
```

## Сохранившиеся паттерны

- Builder — создание `EnergySystem`;
- Factory Method — конкретные фабрики спутников;
- Strategy — выбор фабрики в `SatelliteServiceImpl`;
- Facade — объединение бизнес-сценариев;
- Decorator/Proxy — измерение времени через Spring AOP.

## Проверка

```shell
mvn test
mvn spring-boot:run
```

Интеграционные тесты проверяют фасад, миссии для группировки, типа и конкретного
спутника, а также работу аспекта.
