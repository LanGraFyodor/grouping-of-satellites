package org.example;

public abstract class Satellite {
    protected String name;
    protected boolean isActive;
    protected double batteryLevel;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.batteryLevel = batteryLevel;
        this.isActive = false;
    }

    public boolean activate() {
        if (this.batteryLevel > 0.2) {
            this.isActive = true;
            return true;
        }
        return false;
    }

    public void deactivate() {
        if (this.isActive) {
            this.isActive = false;
        }
    }

    public void consumeBattery(double amount) {
        if (this.batteryLevel >= amount) {
            this.batteryLevel -= amount;
        } else {
            this.batteryLevel = 0.0;
        }

        if (this.batteryLevel < 0.2) {
            deactivate();
        }
    }

    protected abstract void performMission();
}
