package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SatelliteFactoryTest {
    @Test
    void communicationFactoryCreatesCommunicationSatellite() {
        SatelliteFactory factory = new CommunicationSatelliteFactory();

        Satellite satellite = factory.createSatelliteWithParameter("Связь-Тест", 0.8, 750.0);

        CommunicationSatellite communication = assertInstanceOf(
                CommunicationSatellite.class,
                satellite
        );
        assertEquals("Связь-Тест", communication.getName());
        assertEquals(0.8, communication.getEnergy().getBatteryLevel());
        assertEquals(750.0, communication.getBandwidth());
    }

    @Test
    void imagingFactoryCreatesImagingSatellite() {
        SatelliteFactory factory = new ImagingSatelliteFactory();

        Satellite satellite = factory.createSatelliteWithParameter("ДЗЗ-Тест", 0.9, 0.5);

        ImagingSatellite imaging = assertInstanceOf(ImagingSatellite.class, satellite);
        assertEquals("ДЗЗ-Тест", imaging.getName());
        assertEquals(0.9, imaging.getEnergy().getBatteryLevel());
        assertEquals(0.5, imaging.getResolution());
        assertEquals(0, imaging.getPhotosTaken());
    }

    @Test
    void defaultFactoryMethodsUseDefaultParameters() {
        CommunicationSatellite communication = assertInstanceOf(
                CommunicationSatellite.class,
                new CommunicationSatelliteFactory().createSatellite("Связь", 0.7)
        );
        ImagingSatellite imaging = assertInstanceOf(
                ImagingSatellite.class,
                new ImagingSatelliteFactory().createSatellite("ДЗЗ", 0.7)
        );

        assertEquals(100.0, communication.getBandwidth());
        assertEquals(1.0, imaging.getResolution());
    }

    @Test
    void lowBatterySatelliteCannotBeActivated() {
        Satellite satellite = new ImagingSatelliteFactory()
                .createSatellite("ДЗЗ-Критический", 0.2);

        assertFalse(satellite.activate());
        assertFalse(satellite.getState().isActive());
    }
}
