# Семинар 6 — Strategy и развитие Factory Method

Проект продолжает систему управления спутниковой группировкой. Создание
спутников теперь выполняется через единый сервис, который автоматически
выбирает подходящую фабрику.

## Унифицированные параметры

Тип спутника задаётся перечислением `SatelliteType`:

- `IMAGE`;
- `COMMUNICATION`.

Абстрактный `SatelliteParam` хранит тип, имя и заряд. Конкретные параметры:

- `ImagingSatelliteParam` — добавляет разрешение;
- `CommunicationSatelliteParam` — добавляет пропускную способность.

## Factory Method

Интерфейс `SatelliteFactory` содержит:

```java
Satellite createSatelliteWithParameter(SatelliteParam param);
boolean isSatelliteTypeSupported(SatelliteType type);
```

`CommunicationSatelliteFactory` и `ImagingSatelliteFactory` являются
Spring-компонентами. Каждая фабрика создаёт только свой тип спутника и выдаёт
`SpaceOperationException`, если получила неподдерживаемый параметр.

## Strategy

`SatelliteServiceImpl` получает от Spring список всех реализаций
`SatelliteFactory`. Метод `createSatellite`:

1. определяет тип из `SatelliteParam`;
2. находит фабрику, поддерживающую этот тип;
3. делегирует ей создание спутника.

Добавление нового типа требует новой пары `SatelliteParam` + `SatelliteFactory`.
Сам сервис при этом изменять не нужно.

## Builder

Создание `EnergySystem` по-прежнему выполняется через Builder с дефолтами и
валидацией диапазонов заряда.

## Проверка

```shell
mvn test
mvn spring-boot:run
```

`SatelliteFactoryTest` проверяет конкретные фабрики.
`SatelliteServiceTest` — интеграционный тест с `@SpringBootTest`, проверяющий
автоматический выбор фабрики Spring-сервисом.
