package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        SpaceOperationCenterService service = context.getBean(SpaceOperationCenterService.class);

        SatelliteFactory communicationFactory = new CommunicationSatelliteFactory();
        SatelliteFactory imagingFactory = new ImagingSatelliteFactory();

        Satellite com1 = communicationFactory.createSatelliteWithParameter("Связь-1", 0.85, 500.0);
        Satellite com2 = communicationFactory.createSatelliteWithParameter("Связь-2", 0.75, 1000.0);
        Satellite img1 = imagingFactory.createSatelliteWithParameter("ДЗЗ-1", 0.92, 2.5);
        Satellite img2 = imagingFactory.createSatelliteWithParameter("ДЗЗ-2", 0.45, 1.0);
        Satellite img3 = imagingFactory.createSatelliteWithParameter("ДЗЗ-3", 0.15, 0.5);

        service.createAndSaveConstellation("Орбита-1");
        service.createAndSaveConstellation("Орбита-2");

        service.addSatelliteToConstellation("Орбита-1", com1);
        service.addSatelliteToConstellation("Орбита-1", img1);
        service.addSatelliteToConstellation("Орбита-1", img2);
        service.addSatelliteToConstellation("Орбита-2", com2);
        service.addSatelliteToConstellation("Орбита-2", img3);

        service.activateAllSatellites("Орбита-1");
        service.executeConstellationMission("Орбита-1");
        service.showConstellationStatus("Орбита-1");

        context.close();
    }
}
