package org.ametyst.budgeting.register;

import java.util.UUID;

public class RegisterTransferDto {
    private UUID targetRegisterUUID;
    private Double amount;

    public UUID getTargetRegisterUUID() {
        return targetRegisterUUID;
    }

    public RegisterTransferDto setTargetRegisterUUID(UUID targetRegisterUUID) {
        this.targetRegisterUUID = targetRegisterUUID;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public RegisterTransferDto setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}
