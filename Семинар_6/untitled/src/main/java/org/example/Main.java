package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        SpaceOperationCenterService operationCenter =
                context.getBean(SpaceOperationCenterService.class);
        SatelliteService satelliteService = context.getBean(SatelliteService.class);

        Satellite com1 = satelliteService.createSatellite(
                new CommunicationSatelliteParam("Связь-1", 0.85, 500.0)
        );
        Satellite com2 = satelliteService.createSatellite(
                new CommunicationSatelliteParam("Связь-2", 0.75, 1000.0)
        );
        Satellite img1 = satelliteService.createSatellite(
                new ImagingSatelliteParam("ДЗЗ-1", 0.92, 2.5)
        );
        Satellite img2 = satelliteService.createSatellite(
                new ImagingSatelliteParam("ДЗЗ-2", 0.45, 1.0)
        );
        Satellite img3 = satelliteService.createSatellite(
                new ImagingSatelliteParam("ДЗЗ-3", 0.15, 0.5)
        );

        operationCenter.createAndSaveConstellation("Орбита-1");
        operationCenter.createAndSaveConstellation("Орбита-2");

        operationCenter.addSatelliteToConstellation("Орбита-1", com1);
        operationCenter.addSatelliteToConstellation("Орбита-1", img1);
        operationCenter.addSatelliteToConstellation("Орбита-1", img2);
        operationCenter.addSatelliteToConstellation("Орбита-2", com2);
        operationCenter.addSatelliteToConstellation("Орбита-2", img3);

        operationCenter.activateAllSatellites("Орбита-1");
        operationCenter.executeConstellationMission("Орбита-1");
        operationCenter.showConstellationStatus("Орбита-1");

        context.close();
    }
}
