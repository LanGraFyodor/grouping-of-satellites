package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("ЗАПУСК СИСТЕМЫ УПРАВЛЕНИЯ СПУТНИКОВОЙ ГРУППИРОВКОЙ");
        System.out.println("============================================================");
        System.out.println("СОЗДАНИЕ СПЕЦИАЛИЗИРОВАННЫХ СПУТНИКОВ:");
        System.out.println("---------------------------------------------");

        Satellite com1 = new CommunicationSatellite("Связь-1", 0.85, 500.0);
        Satellite com2 = new CommunicationSatellite("Связь-2", 0.75, 1000.0);
        Satellite img1 = new ImagingSatellite("ДЗЗ-1", 0.92, 2.5);
        Satellite img2 = new ImagingSatellite("ДЗЗ-2", 0.45, 1.0);
        Satellite img3 = new ImagingSatellite("ДЗЗ-3", 0.15, 0.5);

        System.out.println("Создан спутник: Связь-1 (заряд: 85%)");
        System.out.println("Создан спутник: Связь-2 (заряд: 75%)");
        System.out.println("Создан спутник: ДЗЗ-1 (заряд: 92%)");
        System.out.println("Создан спутник: ДЗЗ-2 (заряд: 45%)");
        System.out.println("Создан спутник: ДЗЗ-3 (заряд: 15%)");
        System.out.println("---------------------------------------------");

        System.out.println("Создана спутниковая группировка: RU Basic");
        SatelliteConstellation constellation = new SatelliteConstellation("RU Basic");
        System.out.println("---------------------------------------------");

        System.out.println("ФОРМИРОВАНИЕ ГРУППИРОВКИ:");
        System.out.println("-----------------------------------");
        constellation.addSatellite(com1);
        constellation.addSatellite(com2);
        constellation.addSatellite(img1);
        constellation.addSatellite(img2);
        constellation.addSatellite(img3);
        System.out.println("-----------------------------------");

        System.out.println(constellation.getSatellites());
        System.out.println("-----------------------------------");

        System.out.println("АКТИВАЦИЯ СПУТНИКОВ:");
        System.out.println("-------------------------");
        for (Satellite sat : constellation.getSatellites()) {
            boolean activated = sat.activate();
            if (activated) {
                System.out.println("✅ " + sat.name + ": Активация успешна");
            } else {
                int percentage = (int) Math.round(sat.batteryLevel * 100);
                System.out.println("🛑 " + sat.name + ": Ошибка активации (заряд: " + percentage + "%)");
            }
        }

        constellation.executeAllMissions();
        System.out.println(constellation.getSatellites());
    }
}
