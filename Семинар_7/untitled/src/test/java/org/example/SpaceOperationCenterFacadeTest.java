package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpaceOperationCenterFacadeTest {
    @Autowired
    private SpaceOperationCenterService operationCenter;

    @Autowired
    private ExecutionTimeAspect executionTimeAspect;

    @Test
    void addSatelliteCreatesMissingConstellationWhenRequested() {
        String constellationName = uniqueName("Авто");

        Satellite satellite = operationCenter.addSatellite(new AddSatelliteRequest(
                constellationName,
                new ImagingSatelliteParam("ДЗЗ-Фасад", 0.9, 0.6),
                true
        ));

        assertInstanceOf(ImagingSatellite.class, satellite);
        ConstellationStatus status =
                operationCenter.getConstellationStatus(constellationName);
        assertEquals(1, status.satelliteCount());
        assertEquals(0, status.activeSatelliteCount());
    }

    @Test
    void executesMissionOnlyForRequestedSatelliteType() {
        String constellationName = uniqueName("Выборочная");
        CommunicationSatellite communication = assertInstanceOf(
                CommunicationSatellite.class,
                operationCenter.addSatellite(new AddSatelliteRequest(
                        constellationName,
                        new CommunicationSatelliteParam("Связь-Фасад", 0.8, 600.0),
                        true
                ))
        );
        ImagingSatellite imaging = assertInstanceOf(
                ImagingSatellite.class,
                operationCenter.addSatellite(new AddSatelliteRequest(
                        constellationName,
                        new ImagingSatelliteParam("ДЗЗ-Фасад", 0.8, 1.2),
                        false
                ))
        );

        operationCenter.executeMission(
                new MissionRequest(constellationName, SatelliteType.IMAGE, true)
        );

        assertEquals(0.8, communication.getEnergy().getBatteryLevel());
        assertEquals(0.72, imaging.getEnergy().getBatteryLevel(), 0.000_001);
        assertEquals(1, imaging.getPhotosTaken());
    }

    @Test
    void refusesToAddSatelliteToMissingConstellationWithoutPermission() {
        assertThrows(
                SpaceOperationException.class,
                () -> operationCenter.addSatellite(new AddSatelliteRequest(
                        uniqueName("Отказ"),
                        new ImagingSatelliteParam("ДЗЗ", 0.8, 1.0),
                        false
                ))
        );
    }

    @Test
    void executesMissionForOneSatelliteWithoutActivatingTheWholeConstellation() {
        String constellationName = uniqueName("Один");
        CommunicationSatellite communication = assertInstanceOf(
                CommunicationSatellite.class,
                operationCenter.addSatellite(new AddSatelliteRequest(
                        constellationName,
                        new CommunicationSatelliteParam("Связь-Одна", 0.8, 600.0),
                        true
                ))
        );
        ImagingSatellite imaging = assertInstanceOf(
                ImagingSatellite.class,
                operationCenter.addSatellite(new AddSatelliteRequest(
                        constellationName,
                        new ImagingSatelliteParam("ДЗЗ-Один", 0.8, 1.0),
                        false
                ))
        );

        operationCenter.executeMission(
                MissionRequest.forSatellite(constellationName, "ДЗЗ-Один", true)
        );

        assertFalse(communication.getState().isActive());
        assertEquals(0.8, communication.getEnergy().getBatteryLevel());
        assertEquals(1, imaging.getPhotosTaken());
        assertEquals(0.72, imaging.getEnergy().getBatteryLevel(), 0.000_001);
    }

    @Test
    void facadeCallsAreMeasuredByAspect() {
        long invocationsBefore = executionTimeAspect.getMeasuredInvocationCount();
        String constellationName = uniqueName("AOP");

        operationCenter.createConstellation(constellationName);

        assertEquals(
                invocationsBefore + 1,
                executionTimeAspect.getMeasuredInvocationCount()
        );
    }

    private String uniqueName(String prefix) {
        return prefix + "-" + UUID.randomUUID();
    }
}
