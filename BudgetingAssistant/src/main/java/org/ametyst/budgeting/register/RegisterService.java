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
    public Register rechargeRegister(UUID uuid, Double rechargeAmount) {
        Optional<Register> optionalRegisterToRecharge = registerDao.findById(uuid);
        if (optionalRegisterToRecharge.isEmpty()) {
            throw new IllegalArgumentException("Target register does not exist.");
        }
        Register register = optionalRegisterToRecharge.get();
        register.setBalance(register.getBalance() + rechargeAmount);
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
