package org.ametyst.budgeting.register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import javax.transaction.Transactional;
import org.ametyst.budgeting.SpringBootTestWithData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

class RegisterControllerTest extends SpringBootTestWithData {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldGetAllRegisters() {
        // act
        List<Register> registers = this.restTemplate.getForObject("http://localhost:" + port + "/registers", List.class);

        // assert
        assertEquals(DEFAULT_REGISTER_AMOUNT + TEST_REGISTER_AMOUNT, registers.size());
    }

    @Test
    @Transactional
    void shouldChargeRegisterWithGivenAmount() {
        // arrange
        RegisterChargeDto request = new RegisterChargeDto();
        request.setAmount(50.0);

        // act
        Register register = this.restTemplate
            .postForObject("http://localhost:" + port + "/registers/" + TEST_REGISTER_UUID + "/charge", request, Register.class);

        // assert
        assertEquals(150, register.getBalance());
        assertEquals(TEST_REGISTER_UUID, register.getUuid());
    }
}