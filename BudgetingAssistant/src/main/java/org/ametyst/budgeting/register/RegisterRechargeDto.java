package org.ametyst.budgeting.register;

import java.io.Serializable;
import javax.validation.constraints.Min;

public class RegisterRechargeDto implements Serializable {
    @Min(value = 0, message = "Cannot recharge for negative amount")
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public RegisterRechargeDto setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}
