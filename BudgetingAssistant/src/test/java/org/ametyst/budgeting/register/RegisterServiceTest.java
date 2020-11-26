package org.ametyst.budgeting.register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import javax.transaction.Transactional;
import org.ametyst.budgeting.SpringBootTestWithData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RegisterServiceTest extends SpringBootTestWithData {

    @Autowired
    private RegisterService registerService;

    @Test
    void shouldGetAllDefaultRegistersWithTestOne() {
        // act
        List<Register> allRegisters = registerService.getAllRegisters();

        // assert
        assertEquals(5, allRegisters.size());
    }

    @Transactional
    @Test
    void shouldChargeSelectedRegister() {
        // arrange

        // act
        Register register = registerService.chargeRegister(TEST_REGISTER_UUID, 100.0);

        // assert
        assertEquals(200.0, register.getBalance());
    }
}