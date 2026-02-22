package org.example;

import org.springframework.stereotype.Service;

@Service
public class SpaceOperationCenterService {
    private final ConstellationRepository constellationRepository;

    public SpaceOperationCenterService(ConstellationRepository constellationRepository) {
        this.constellationRepository = constellationRepository;
    }

    public void createAndSaveConstellation(String name) {
        SatelliteConstellation constellation = new SatelliteConstellation(name);
        constellationRepository.save(constellation);
        System.out.println("Создана спутниковая группировка: " + name);
        System.out.println("Сохранена группировка: " + name);
    }

    public void addSatelliteToConstellation(String constellationName, Satellite satellite) {
        SatelliteConstellation constellation = constellationRepository.findByName(constellationName);
        if (constellation != null) {
            constellation.addSatellite(satellite);
            System.out.println("Добавлен спутник " + satellite.getName() + " в группировку " + constellationName);
        }
    }

    public void executeConstellationMission(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.findByName(constellationName);
        if (constellation != null) {
            System.out.println("\n=== ВЫПОЛНЕНИЕ МИССИЙ ДЛЯ ГРУППИРОВКИ: " + constellationName + " ===");
            constellation.executeAllMissions();
        }
    }

    public void activateAllSatellites(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.findByName(constellationName);
        if (constellation != null) {
            System.out.println("\n=== АКТИВАЦИЯ СПУТНИКОВ В ГРУППИРОВКЕ: " + constellationName + " ===");
            for (Satellite sat : constellation.getSatellites()) {
                boolean activated = sat.activate();
                if (activated) {
                    System.out.println("✅ " + sat.getName() + ": Активация успешна");
                } else {
                    int percentage = (int) Math.round(sat.getEnergy().getBatteryLevel() * 100);
                    System.out.println("🛑 " + sat.getName() + ": Ошибка активации (заряд: " + percentage + "%)");
                }
            }
        }
    }

    public void showConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.findByName(constellationName);
        if (constellation != null) {
            System.out.println("\n=== СТАТУС ГРУППИРОВКИ: " + constellationName + " ===");
            System.out.println("Количество спутников: " + constellation.getSatellites().size());
            for (Satellite sat : constellation.getSatellites()) {
                System.out.println(sat.getState().toString());
            }
        }
    }
}
