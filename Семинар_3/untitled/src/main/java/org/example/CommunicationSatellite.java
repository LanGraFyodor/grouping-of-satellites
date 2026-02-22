package org.example;

public class CommunicationSatellite extends Satellite {
    private double bandwidth;

    public CommunicationSatellite(String name, double batteryLevel, double bandwidth) {
        super(name, batteryLevel);
        this.bandwidth = bandwidth;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    @Override
    protected void performMission() {
        if (state.isActive()) {
            System.out.println(name + ": Передача данных со скоростью " + bandwidth + " Мбит/с");
            sendData(bandwidth);
            energy.consume(0.05);
            if (energy.getBatteryLevel() < 0.2) {
                state.deactivate();
            }
        } else {
            System.out.println("🛑 " + name + ": Не может передать данные - не активен");
        }
    }

    public void sendData(double amount) {
        if (state.isActive()) {
            System.out.println(name + ": Отправил " + amount + " Мбит данных!");
        }
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
