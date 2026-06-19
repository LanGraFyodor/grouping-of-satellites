package org.example;

public final class EnergySystem {
    private double batteryLevel;
    private final double lowBatteryThreshold;
    private final double maxBattery;
    private final double minBattery;

    private EnergySystem(Builder builder) {
        this.batteryLevel = builder.batteryLevel;
        this.lowBatteryThreshold = builder.lowBatteryThreshold;
        this.maxBattery = builder.maxBattery;
        this.minBattery = builder.minBattery;
    }

    public static Builder builder() {
        return new Builder();
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public double getLowBatteryThreshold() {
        return lowBatteryThreshold;
    }

    public double getMaxBattery() {
        return maxBattery;
    }

    public double getMinBattery() {
        return minBattery;
    }

    public boolean consume(double amount) {
        if (amount <= 0 || batteryLevel - amount < minBattery) {
            return false;
        }
        batteryLevel -= amount;
        return true;
    }

    public boolean recharge(double amount) {
        if (amount <= 0 || batteryLevel >= maxBattery) {
            return false;
        }
        batteryLevel = Math.min(maxBattery, batteryLevel + amount);
        return true;
    }

    public boolean hasSufficientPower() {
        return batteryLevel > lowBatteryThreshold;
    }

    public boolean isCritical() {
        return batteryLevel <= lowBatteryThreshold;
    }

    @Override
    public String toString() {
        return "EnergySystem{" +
                "batteryLevel=" + batteryLevel +
                ", lowBatteryThreshold=" + lowBatteryThreshold +
                ", maxBattery=" + maxBattery +
                ", minBattery=" + minBattery +
                '}';
    }

    public static final class Builder {
        private double batteryLevel = 1.0;
        private double lowBatteryThreshold = 0.2;
        private double maxBattery = 1.0;
        private double minBattery = 0.0;

        private Builder() {
        }

        public Builder batteryLevel(double batteryLevel) {
            this.batteryLevel = batteryLevel;
            return this;
        }

        public Builder lowBatteryThreshold(double lowBatteryThreshold) {
            this.lowBatteryThreshold = lowBatteryThreshold;
            return this;
        }

        public Builder maxBattery(double maxBattery) {
            this.maxBattery = maxBattery;
            return this;
        }

        public Builder minBattery(double minBattery) {
            this.minBattery = minBattery;
            return this;
        }

        public EnergySystem build() {
            if (minBattery < 0) {
                throw new IllegalArgumentException("Минимальный заряд не может быть отрицательным");
            }
            if (maxBattery <= minBattery) {
                throw new IllegalArgumentException("Максимальный заряд должен быть больше минимального");
            }
            if (lowBatteryThreshold < minBattery || lowBatteryThreshold >= maxBattery) {
                throw new IllegalArgumentException("Критический порог должен находиться между minBattery и maxBattery");
            }
            if (batteryLevel < minBattery || batteryLevel > maxBattery) {
                throw new IllegalArgumentException("Начальный заряд должен находиться между minBattery и maxBattery");
            }
            return new EnergySystem(this);
        }
    }
}
