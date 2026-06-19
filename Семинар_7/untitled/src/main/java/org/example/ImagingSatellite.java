package org.example;

public class ImagingSatellite extends Satellite {
    private static final double MISSION_ENERGY_COST = 0.08;

    private final double resolution;
    private int photosTaken;

    ImagingSatellite(String name, EnergySystem energy, double resolution) {
        super(name, energy);
        if (resolution <= 0) {
            throw new IllegalArgumentException("Разрешение должно быть положительным");
        }
        this.resolution = resolution;
    }

    public double getResolution() {
        return resolution;
    }

    public int getPhotosTaken() {
        return photosTaken;
    }

    @Override
    public SatelliteType getType() {
        return SatelliteType.IMAGE;
    }

    @Override
    protected void performMission() {
        if (!state.isActive()) {
            System.out.println("🛑 " + name + ": не может выполнить съёмку — спутник не активен");
            return;
        }
        if (!prepareMission(MISSION_ENERGY_COST)) {
            System.out.println("🛑 " + name + ": недостаточно энергии для съёмки");
            return;
        }

        System.out.println(name + ": съёмка с разрешением " + resolution + " м/пиксель");
        takePhoto();
        finishMission();
    }

    public void takePhoto() {
        photosTaken++;
        System.out.println(name + ": снимок №" + photosTaken + " сделан");
    }

    @Override
    public String toString() {
        return "ImagingSatellite{" +
                "resolution=" + resolution +
                ", photosTaken=" + photosTaken +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", energy=" + energy +
                '}';
    }
}
