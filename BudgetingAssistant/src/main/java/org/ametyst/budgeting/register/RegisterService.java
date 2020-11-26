package org.ametyst.budgeting.register;

import java.util.List;
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
        Register register = registerDao.getOne(uuid);
        register.setBalance(register.getBalance() + chargeAmount);
        return registerDao.save(register);
    }
}
