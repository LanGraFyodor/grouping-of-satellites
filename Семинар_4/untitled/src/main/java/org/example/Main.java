package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("ЗАПУСК СИСТЕМЫ УПРАВЛЕНИЯ СПУТНИКОВОЙ ГРУППИРОВКОЙ");
        System.out.println("============================================================\n");
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        ConstellationRepository constellationRepository = context.getBean(ConstellationRepository.class);
        SpaceOperationCenterService service = context.getBean(SpaceOperationCenterService.class);

        System.out.println("\n\nСОЗДАНИЕ СПЕЦИАЛИЗИРОВАННЫХ СПУТНИКОВ:");
        System.out.println("---------------------------------------------");

        Satellite com1 = new CommunicationSatellite("Связь-1", 0.85, 500.0);
        Satellite com2 = new CommunicationSatellite("Связь-2", 0.75, 1000.0);
        Satellite img1 = new ImagingSatellite("ДЗЗ-1", 0.92, 2.5);
        Satellite img2 = new ImagingSatellite("ДЗЗ-2", 0.45, 1.0);
        Satellite img3 = new ImagingSatellite("ДЗЗ-3", 0.15, 0.5);

        System.out.println("Создан спутник: Связь-1 (0.85)");
        System.out.println("Создан спутник: Связь-2 (0.75)");
        System.out.println("Создан спутник: ДЗЗ-1 (0.92)");
        System.out.println("Создан спутник: ДЗЗ-2 (0.45)");
        System.out.println("Создан спутник: ДЗЗ-3 (0.15)");
        System.out.println("---------------------------------------------");

        service.createAndSaveConstellation("Орбита-1");
        service.createAndSaveConstellation("Орбита-2");
        System.out.println("---------------------------------------------");

        System.out.println("\n📡 ДОБАВЛЕНИЕ СПУТНИКОВ:");
        service.addSatelliteToConstellation("Орбита-1", com1);
        service.addSatelliteToConstellation("Орбита-1", img1);
        service.addSatelliteToConstellation("Орбита-1", img2);

        service.addSatelliteToConstellation("Орбита-2", com2);
        service.addSatelliteToConstellation("Орбита-2", img3);
        System.out.println("-----------------------------------");

        service.activateAllSatellites("Орбита-1");

        service.executeConstellationMission("Орбита-1");

        service.showConstellationStatus("Орбита-1");

        System.out.println(constellationRepository.findAll());
    }
}
