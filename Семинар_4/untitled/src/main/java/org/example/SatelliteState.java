package org.example;

public class SatelliteState {
    private boolean active;
    private String statusMessage;

    public SatelliteState() {
        this.active = false;
        this.statusMessage = "Не активирован";
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
        this.statusMessage = "Активен";
    }

    public void deactivate() {
        this.active = false;
        this.statusMessage = "Не активирован";
    }

    @Override
    public String toString() {
        return "SatelliteState{isActive=" + active + ", statusMessage='" + statusMessage + "'}";
    }
}
