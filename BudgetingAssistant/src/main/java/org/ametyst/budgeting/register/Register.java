package org.ametyst.budgeting.register;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "registers")
public class Register implements Serializable {

    @Id
    private UUID uuid;
    private String name;
    private Double balance;

    public UUID getUuid() {
        return uuid;
    }

    public Register setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Register setName(String name) {
        this.name = name;
        return this;
    }

    public Double getBalance() {
        return balance;
    }

    public Register setBalance(Double balance) {
        this.balance = balance;
        return this;
    }
}
