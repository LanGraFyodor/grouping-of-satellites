package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SatelliteServiceTest {
    @Autowired
    private SatelliteService satelliteService;

    @Test
    void selectsImagingFactoryByParameterType() {
        ImagingSatellite satellite = assertInstanceOf(
                ImagingSatellite.class,
                satelliteService.createSatellite(
                        new ImagingSatelliteParam("ДЗЗ-Сервис", 0.88, 0.7)
                )
        );

        assertEquals("ДЗЗ-Сервис", satellite.getName());
        assertEquals(0.88, satellite.getEnergy().getBatteryLevel());
        assertEquals(0.7, satellite.getResolution());
    }

    @Test
    void selectsCommunicationFactoryByParameterType() {
        CommunicationSatellite satellite = assertInstanceOf(
                CommunicationSatellite.class,
                satelliteService.createSatellite(
                        new CommunicationSatelliteParam("Связь-Сервис", 0.76, 900.0)
                )
        );

        assertEquals("Связь-Сервис", satellite.getName());
        assertEquals(0.76, satellite.getEnergy().getBatteryLevel());
        assertEquals(900.0, satellite.getBandwidth());
    }

    @Test
    void throwsExceptionWhenFactoryForTypeIsMissing() {
        SatelliteService serviceWithOneStrategy = new SatelliteServiceImpl(
                List.of(new CommunicationSatelliteFactory())
        );

        assertThrows(
                SpaceOperationException.class,
                () -> serviceWithOneStrategy.createSatellite(
                        new ImagingSatelliteParam("ДЗЗ-без-фабрики", 0.8, 1.0)
                )
        );
    }
}
