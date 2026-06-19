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

        operationCenter.addSatellite(new AddSatelliteRequest(
                "Орбита-1",
                new CommunicationSatelliteParam("Связь-1", 0.85, 500.0),
                true
        ));
        operationCenter.addSatellite(new AddSatelliteRequest(
                "Орбита-1",
                new ImagingSatelliteParam("ДЗЗ-1", 0.92, 2.5),
                false
        ));
        operationCenter.addSatellite(new AddSatelliteRequest(
                "Орбита-1",
                new ImagingSatelliteParam("ДЗЗ-2", 0.45, 1.0),
                false
        ));

        operationCenter.executeMission(
                MissionRequest.forAll("Орбита-1", true)
        );

        System.out.println(operationCenter.getConstellationStatus("Орбита-1"));
        context.close();
    }
}
