package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SatelliteConstellation {
    private final String constellationName;
    private final List<Satellite> satellites = new ArrayList<>();

    public SatelliteConstellation(String constellationName) {
        if (constellationName == null || constellationName.isBlank()) {
            throw new IllegalArgumentException("Название группировки не может быть пустым");
        }
        this.constellationName = constellationName;
    }

    public String getConstellationName() {
        return constellationName;
    }

    public void addSatellite(Satellite satellite) {
        if (satellite == null) {
            throw new IllegalArgumentException("Нельзя добавить пустой спутник");
        }
        satellites.add(satellite);
        System.out.println(satellite.getName() + " добавлен в группировку '" + constellationName + "'");
    }

    public void executeAllMissions() {
        System.out.println("ВЫПОЛНЕНИЕ МИССИЙ ГРУППИРОВКИ " + constellationName.toUpperCase());
        for (Satellite satellite : satellites) {
            satellite.performMission();
        }
    }

    public void executeMissions(SatelliteType type) {
        System.out.println("ВЫПОЛНЕНИЕ МИССИЙ " + type +
                " В ГРУППИРОВКЕ " + constellationName.toUpperCase());
        satellites.stream()
                .filter(satellite -> satellite.getType() == type)
                .forEach(Satellite::performMission);
    }

    public void executeMission(String satelliteName) {
        findSatellite(satelliteName).performMission();
    }

    public boolean activateSatellite(String satelliteName) {
        return findSatellite(satelliteName).activate();
    }

    private Satellite findSatellite(String satelliteName) {
        return satellites.stream()
                .filter(satellite -> satellite.getName().equals(satelliteName))
                .findFirst()
                .orElseThrow(() -> new SpaceOperationException(
                        "Спутник не найден: " + satelliteName
                ));
    }

    public List<Satellite> getSatellites() {
        return Collections.unmodifiableList(satellites);
    }

    @Override
    public String toString() {
        return "SatelliteConstellation{" +
                "constellationName='" + constellationName + '\'' +
                ", satellites=" + satellites +
                '}';
    }
}
