package org.ametyst.budgeting.register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Test
    void shouldGetAllDefaultRegisters() {
        // act
        List<Register> allRegisters = registerService.getAllRegisters();

        // assert
        assertEquals(4, allRegisters.size());
    }
}