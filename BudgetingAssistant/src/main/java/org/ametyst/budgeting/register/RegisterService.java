package org.ametyst.budgeting.register;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class RegisterService {

    private final RegisterDao registerDao;

    public RegisterService(RegisterDao registerDao) {
        this.registerDao = registerDao;
    }

    public List<Register> getAllRegisters() {
        return registerDao.findAll();
    }

    @Transactional
    public void chargeRegister(UUID uuid, Double chargeValue) {
        Register register = registerDao.getOne(uuid);
        register.setBalance(register.getBalance() + chargeValue);
        registerDao.save(register);
    }
}
