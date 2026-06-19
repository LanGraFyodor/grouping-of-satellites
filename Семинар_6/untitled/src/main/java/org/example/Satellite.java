package org.example;

import java.util.Objects;

public abstract class Satellite {
    protected final String name;
    protected final SatelliteState state;
    protected final EnergySystem energy;

    protected Satellite(String name, EnergySystem energy) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя спутника не может быть пустым");
        }
        this.name = name;
        this.state = new SatelliteState();
        this.energy = Objects.requireNonNull(energy, "Энергосистема обязательна");
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
        return energy.hasSufficientPower() && state.activate();
    }

    public void deactivate() {
        state.deactivate();
    }

    protected boolean prepareMission(double energyCost) {
        if (!state.isActive()) {
            return false;
        }
        if (!energy.consume(energyCost)) {
            state.deactivate();
            return false;
        }
        return true;
    }

    protected void finishMission() {
        if (energy.isCritical()) {
            state.deactivate();
        }
    }

    protected abstract void performMission();
}
