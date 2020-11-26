package org.ametyst.budgeting.register;

import java.io.Serializable;

public class RegisterChargeDto implements Serializable {
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public RegisterChargeDto setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}
