package org.example;

public class EnergySystem {
    private double batteryLevel;

    public EnergySystem(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void consume(double amount) {
        if (this.batteryLevel >= amount) {
            this.batteryLevel -= amount;
        } else {
            this.batteryLevel = 0.0;
        }
    }
}
