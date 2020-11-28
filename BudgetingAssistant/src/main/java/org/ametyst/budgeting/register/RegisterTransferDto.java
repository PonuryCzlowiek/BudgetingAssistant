package org.ametyst.budgeting.register;

import java.util.UUID;
import javax.validation.constraints.Min;

public class RegisterTransferDto {
    private UUID targetRegisterUUID;
    @Min(value = 0, message = "Cannot transfer negative amount")
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
