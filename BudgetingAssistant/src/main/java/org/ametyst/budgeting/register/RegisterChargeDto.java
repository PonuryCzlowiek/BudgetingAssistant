package org.ametyst.budgeting.register;

import java.io.Serializable;
import javax.validation.constraints.Min;

public class RegisterChargeDto implements Serializable {
    @Min(value = 0, message = "Cannot charge for negative amount")
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public RegisterChargeDto setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}
