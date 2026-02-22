package org.example;

public class ImagingSatellite extends Satellite {
    private double resolution;
    private int photosTaken;

    public ImagingSatellite(String name, double batteryLevel, double resolution) {
        super(name, batteryLevel);
        this.resolution = resolution;
        this.photosTaken = 0;
    }

    public double getResolution() {
        return resolution;
    }

    public int getPhotosTaken() {
        return photosTaken;
    }

    @Override
    protected void performMission() {
        if (state.isActive()) {
            System.out.println(name + ": Съемка территории с разрешением " + resolution + " м/пиксель");
            takePhoto();
            energy.consume(0.08);
            if (energy.getBatteryLevel() < 0.2) {
                state.deactivate();
            }
        } else {
            System.out.println("🛑 " + name + ": Не может выполнить съемку - не активен");
        }
    }

    public void takePhoto() {
        if (state.isActive()) {
            photosTaken++;
            System.out.println(name + ": Снимок #" + photosTaken + " сделан!");
        }
    }

    @Override
    public String toString() {
        return "ImagingSatellite{" +
                "resolution=" + resolution +
                ", photosTaken=" + photosTaken +
                ", name='" + name + '\'' +
                ", isActive=" + state.isActive() +
                ", batteryLevel=" + energy.getBatteryLevel() +
                '}';
    }
}
