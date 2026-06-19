package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SatelliteFactoryTest {
    private final SatelliteFactory communicationFactory =
            new CommunicationSatelliteFactory();
    private final SatelliteFactory imagingFactory =
            new ImagingSatelliteFactory();

    @Test
    void communicationFactoryCreatesCommunicationSatelliteFromParameter() {
        SatelliteParam param =
                new CommunicationSatelliteParam("Связь-Тест", 0.8, 750.0);

        CommunicationSatellite satellite = assertInstanceOf(
                CommunicationSatellite.class,
                communicationFactory.createSatelliteWithParameter(param)
        );

        assertEquals("Связь-Тест", satellite.getName());
        assertEquals(0.8, satellite.getEnergy().getBatteryLevel());
        assertEquals(750.0, satellite.getBandwidth());
    }

    @Test
    void imagingFactoryCreatesImagingSatelliteFromParameter() {
        SatelliteParam param =
                new ImagingSatelliteParam("ДЗЗ-Тест", 0.9, 0.5);

        ImagingSatellite satellite = assertInstanceOf(
                ImagingSatellite.class,
                imagingFactory.createSatelliteWithParameter(param)
        );

        assertEquals("ДЗЗ-Тест", satellite.getName());
        assertEquals(0.9, satellite.getEnergy().getBatteryLevel());
        assertEquals(0.5, satellite.getResolution());
    }

    @Test
    void factoriesReportOnlyTheirSupportedType() {
        assertTrue(communicationFactory.isSatelliteTypeSupported(SatelliteType.COMMUNICATION));
        assertFalse(communicationFactory.isSatelliteTypeSupported(SatelliteType.IMAGE));
        assertTrue(imagingFactory.isSatelliteTypeSupported(SatelliteType.IMAGE));
        assertFalse(imagingFactory.isSatelliteTypeSupported(SatelliteType.COMMUNICATION));
    }

    @Test
    void factoryRejectsParameterOfAnotherType() {
        SatelliteParam imagingParam =
                new ImagingSatelliteParam("ДЗЗ", 0.7, 1.0);

        assertThrows(
                SpaceOperationException.class,
                () -> communicationFactory.createSatelliteWithParameter(imagingParam)
        );
    }

    @Test
    void lowBatterySatelliteCannotBeActivated() {
        Satellite satellite = imagingFactory.createSatelliteWithParameter(
                new ImagingSatelliteParam("ДЗЗ-Критический", 0.2, 1.0)
        );

        assertFalse(satellite.activate());
        assertFalse(satellite.getState().isActive());
    }
}
