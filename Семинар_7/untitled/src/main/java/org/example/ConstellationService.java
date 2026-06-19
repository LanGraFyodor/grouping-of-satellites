package org.example;

import org.springframework.stereotype.Service;

@Service
public class ConstellationService {
    private final ConstellationRepository constellationRepository;

    public ConstellationService(ConstellationRepository constellationRepository) {
        this.constellationRepository = constellationRepository;
    }

    public void createAndSaveConstellation(String name) {
        if (constellationRepository.contains(name)) {
            throw new IllegalArgumentException("Группировка уже существует: " + name);
        }
        constellationRepository.save(new SatelliteConstellation(name));
        System.out.println("Создана и сохранена спутниковая группировка: " + name);
    }

    public void addSatelliteToConstellation(String constellationName, Satellite satellite) {
        requireConstellation(constellationName).addSatellite(satellite);
    }

    public void executeConstellationMission(String constellationName) {
        requireConstellation(constellationName).executeAllMissions();
    }

    public void executeConstellationMission(String constellationName, SatelliteType type) {
        requireConstellation(constellationName).executeMissions(type);
    }

    public void executeSatelliteMission(String constellationName, String satelliteName) {
        requireConstellation(constellationName).executeMission(satelliteName);
    }

    public boolean activateSatellite(String constellationName, String satelliteName) {
        return requireConstellation(constellationName).activateSatellite(satelliteName);
    }

    public void activateAllSatellites(String constellationName) {
        SatelliteConstellation constellation = requireConstellation(constellationName);
        System.out.println("АКТИВАЦИЯ СПУТНИКОВ: " + constellationName);
        for (Satellite satellite : constellation.getSatellites()) {
            if (satellite.activate()) {
                System.out.println("✅ " + satellite.getName() + ": активация успешна");
            } else {
                System.out.println("🛑 " + satellite.getName() +
                        ": активация невозможна, заряд " +
                        Math.round(satellite.getEnergy().getBatteryLevel() * 100) + "%");
            }
        }
    }

    public void showConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = requireConstellation(constellationName);
        System.out.println("СТАТУС ГРУППИРОВКИ: " + constellationName);
        for (Satellite satellite : constellation.getSatellites()) {
            System.out.println(satellite.getName() + ": " + satellite.getState() +
                    ", заряд=" + satellite.getEnergy().getBatteryLevel());
        }
    }

    public ConstellationStatus getConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = requireConstellation(constellationName);
        int activeCount = (int) constellation.getSatellites().stream()
                .filter(satellite -> satellite.getState().isActive())
                .count();
        return new ConstellationStatus(
                constellationName,
                constellation.getSatellites().size(),
                activeCount
        );
    }

    public int rechargeAllSatellites(String constellationName, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Количество энергии должно быть положительным");
        }
        return (int) requireConstellation(constellationName).getSatellites().stream()
                .filter(satellite -> satellite.getEnergy().recharge(amount))
                .count();
    }

    public boolean containsConstellation(String name) {
        return constellationRepository.contains(name);
    }

    public SatelliteConstellation requireConstellation(String name) {
        SatelliteConstellation constellation = constellationRepository.findByName(name);
        if (constellation == null) {
            throw new IllegalArgumentException("Группировка не найдена: " + name);
        }
        return constellation;
    }
}
