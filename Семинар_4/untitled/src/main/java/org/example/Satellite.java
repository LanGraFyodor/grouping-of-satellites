package org.example;

public abstract class Satellite {
    protected String name;
    protected SatelliteState state;
    protected EnergySystem energy;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.state = new SatelliteState();
        this.energy = new EnergySystem(batteryLevel);
    }

    public String getName() {
        return name;
    }

    public EnergySystem getEnergy() {
        return energy;
    }

    public SatelliteState getState() {
        return state;
    }

    public boolean activate() {
        if (energy.getBatteryLevel() > 0.2) {
            state.activate();
            return true;
        }
        return false;
    }

    public void deactivate() {
        if (state.isActive()) {
            state.deactivate();
        }
    }

    protected abstract void performMission();
}
