package org.example;

public class CommunicationSatellite extends Satellite {
    private static final double MISSION_ENERGY_COST = 0.05;

    private final double bandwidth;

    CommunicationSatellite(String name, EnergySystem energy, double bandwidth) {
        super(name, energy);
        if (bandwidth <= 0) {
            throw new IllegalArgumentException("Пропускная способность должна быть положительной");
        }
        this.bandwidth = bandwidth;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    @Override
    public SatelliteType getType() {
        return SatelliteType.COMMUNICATION;
    }

    @Override
    protected void performMission() {
        if (!state.isActive()) {
            System.out.println("🛑 " + name + ": не может передать данные — спутник не активен");
            return;
        }
        if (!prepareMission(MISSION_ENERGY_COST)) {
            System.out.println("🛑 " + name + ": недостаточно энергии для передачи данных");
            return;
        }

        System.out.println(name + ": передача данных со скоростью " + bandwidth + " Мбит/с");
        sendData();
        finishMission();
    }

    public void sendData() {
        System.out.println(name + ": данные отправлены");
    }

    @Override
    public String toString() {
        return "CommunicationSatellite{" +
                "bandwidth=" + bandwidth +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", energy=" + energy +
                '}';
    }
}
