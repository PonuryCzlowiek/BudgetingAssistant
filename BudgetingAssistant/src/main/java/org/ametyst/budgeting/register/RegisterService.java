package org.ametyst.budgeting.register;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterService {

    private final RegisterDao registerDao;

    @Autowired
    public RegisterService(RegisterDao registerDao) {
        this.registerDao = registerDao;
    }

    public List<Register> getAllRegisters() {
        return registerDao.findAll();
    }

    @Transactional
    public Register chargeRegister(UUID uuid, Double chargeAmount) {
        Optional<Register> optionalRegisterToCharge = registerDao.findById(uuid);
        if (optionalRegisterToCharge.isEmpty()) {
            throw new IllegalArgumentException("Target register does not exist.");
        }
        Register register = optionalRegisterToCharge.get();
        register.setBalance(register.getBalance() + chargeAmount);
        return register;
    }

    @Transactional
    public Register transfer(UUID uuid, UUID targetRegisterUUID, Double amount) {
        Optional<Register> optionalSource = registerDao.findById(uuid);
        Optional<Register> optionalTarget = registerDao.findById(targetRegisterUUID);
        if (optionalSource.isEmpty()) {
            throw new IllegalArgumentException("Source register does not exist.");
        }
        if (optionalTarget.isEmpty()) {
            throw new IllegalArgumentException("Target register does not exist.");
        }
        Register source = optionalSource.get();
        if (source.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough money to transfer.");
        }
        Register target = optionalTarget.get();
        source.setBalance(source.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);
        return source;
    }
}
