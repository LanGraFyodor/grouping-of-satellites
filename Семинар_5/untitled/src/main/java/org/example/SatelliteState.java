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

    public String getStatusMessage() {
        return statusMessage;
    }

    public boolean activate() {
        if (active) {
            return false;
        }
        active = true;
        statusMessage = "Активен";
        return true;
    }

    public void deactivate() {
        active = false;
        statusMessage = "Не активирован";
    }

    @Override
    public String toString() {
        return "SatelliteState{isActive=" + active +
                ", statusMessage='" + statusMessage + "'}";
    }
}
