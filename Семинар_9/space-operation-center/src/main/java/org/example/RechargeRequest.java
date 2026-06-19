package org.example;

public record RechargeRequest(double amount) {
    public RechargeRequest {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Количество энергии должно быть положительным"
            );
        }
    }
}
